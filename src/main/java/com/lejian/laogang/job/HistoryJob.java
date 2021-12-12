package com.lejian.laogang.job;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lejian.laogang.pojo.bo.JpaSpecBo;
import com.lejian.laogang.repository.HistoryRepository;
import com.lejian.laogang.repository.OldmanAttrRepository;
import com.lejian.laogang.repository.OldmanRepository;
import com.lejian.laogang.repository.entity.HistoryEntity;
import com.lejian.laogang.repository.entity.OldmanAttrEntity;
import com.lejian.laogang.repository.entity.OldmanEntity;
import com.lejian.laogang.util.DateUtils;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.swing.text.html.parser.Entity;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class HistoryJob {


    @Autowired
    private OldmanRepository oldmanRepository;
    @Autowired
    private OldmanAttrRepository oldmanAttrRepository;
    @Autowired
    private HistoryRepository historyRepository;

    @Transactional
    @Scheduled(cron = "0 0 1 1 * ?")
    public void job(){
        //KEY: areaVillage
        Map<String,HistoryBo> map = Maps.newHashMap();
        JpaSpecBo jpaSpecBo = new JpaSpecBo();
        jpaSpecBo.getEqualMap().put("status","0");
        jpaSpecBo.getNotEqualMap().put("areaVillage","");
        int pageNo = 0;
        List<OldmanEntity> oldmanEntityList = oldmanRepository.findEntityByPageWithSpec(pageNo,500,jpaSpecBo);
        while (CollectionUtils.isNotEmpty(oldmanEntityList)){
            JpaSpecBo attr = new JpaSpecBo();
            attr.getInMap().put("oldmanId",oldmanEntityList.stream().map(OldmanEntity::getId).collect(Collectors.toList()));
            Map<Integer,List<OldmanAttrEntity>> oldmanAttrEntityMap = oldmanAttrRepository.findEntityWithSpec(attr).stream().collect(Collectors.groupingBy(OldmanAttrEntity::getOldmanId));

            oldmanEntityList.forEach(oldman->{
                HistoryBo bo = map.computeIfAbsent(oldman.getAreaVillage(),k->new HistoryBo());
                bo.getGender().put(oldman.getMale(),bo.getGender().computeIfAbsent(oldman.getMale(),k->0L)+1);
                bo.getHuji().put(oldman.getHuji(),bo.getHuji().computeIfAbsent(oldman.getHuji(),k->0L)+1);
                Integer ageSection = findAgeSection(oldman.getBirthday());
                if (ageSection!=null) {
                    bo.getAge().put(ageSection, bo.getAge().computeIfAbsent(ageSection, k -> 0L) + 1);
                    bo.setSizSum(bo.getSizSum()+1);
                }
                List<Integer> hasType = Lists.newArrayList();
                if(oldmanAttrEntityMap.containsKey(oldman.getId())) {
                    for (OldmanAttrEntity oldmanAttr : oldmanAttrEntityMap.get(oldman.getId())) {
                        switch (oldmanAttr.getType()) {
                            case 2:
                                bo.getFamily().put(oldmanAttr.getValue(), bo.getFamily().computeIfAbsent(oldmanAttr.getValue(), k-> 0L) + 1);
                                break;
                            case 4:
                                bo.getEconomic().put(oldmanAttr.getValue(), bo.getEconomic().computeIfAbsent(oldmanAttr.getValue(), k -> 0L) + 1);
                                break;
                            case 8:
                            case 9:
                            case 10:
                            case 11:
                            case 12:
                                if (!hasType.contains(oldmanAttr.getType())) {
                                    bo.getHealth().put(oldmanAttr.getType(), bo.getHealth().computeIfAbsent(oldmanAttr.getType(), k -> 0L) + 1);
                                    hasType.add(oldmanAttr.getType());
                                }
                                break;
                        }
                    }
                }
            });


            pageNo++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            oldmanEntityList = oldmanRepository.findEntityByPageWithSpec(pageNo,500,jpaSpecBo);
        }

        List<HistoryEntity> historyEntityList =Lists.newArrayList();

        map.forEach((k,v)->{
            if (MapUtils.isNotEmpty(v.getAge())){
                v.getAge().forEach((x,y)->{
                    HistoryEntity historyEntity = new HistoryEntity();
                    historyEntity.setAreaVillage(k);
                    historyEntity.setCount(y);
                    historyEntity.setType(102);
                    historyEntity.setValue(x);
                    historyEntity.setTime(LocalDate.now().format(DateUtils.YY_MM));
                    historyEntityList.add(historyEntity);
                });
            }
            if (MapUtils.isNotEmpty(v.getGender())){
                v.getGender().forEach((x,y)->{
                    if (x!=null && x!=0) {
                        HistoryEntity historyEntity = new HistoryEntity();
                        historyEntity.setAreaVillage(k);
                        historyEntity.setCount(y);
                        historyEntity.setType(100);
                        historyEntity.setValue(x);
                        historyEntity.setTime(LocalDate.now().format(DateUtils.YY_MM));
                        historyEntityList.add(historyEntity);
                    }
                });
            }
            if (MapUtils.isNotEmpty(v.getHuji())){
                v.getHuji().forEach((x,y)->{
                    if (x!=null && x!=0) {
                        HistoryEntity historyEntity = new HistoryEntity();
                        historyEntity.setAreaVillage(k);
                        historyEntity.setCount(y);
                        historyEntity.setType(101);
                        historyEntity.setValue(x);
                        historyEntity.setTime(LocalDate.now().format(DateUtils.YY_MM));
                        historyEntityList.add(historyEntity);
                    }
                });
            }
            HistoryEntity historyEntity1 = new HistoryEntity();
            historyEntity1.setAreaVillage(k);
            historyEntity1.setCount(v.getSizSum());
            historyEntity1.setType(103);
            historyEntity1.setTime(LocalDate.now().format(DateUtils.YY_MM));
            historyEntityList.add(historyEntity1);

            if (MapUtils.isNotEmpty(v.getFamily())){
                v.getFamily().forEach((x,y)->{
                    if (x!=null && x!=0) {
                        HistoryEntity historyEntity = new HistoryEntity();
                        historyEntity.setAreaVillage(k);
                        historyEntity.setCount(y);
                        historyEntity.setType(2);
                        historyEntity.setValue(x);
                        historyEntity.setTime(LocalDate.now().format(DateUtils.YY_MM));
                        historyEntityList.add(historyEntity);
                    }
                });
            }
            if (MapUtils.isNotEmpty(v.getEconomic())){
                v.getEconomic().forEach((x,y)->{
                    if (x!=null && x!=0) {
                        HistoryEntity historyEntity = new HistoryEntity();
                        historyEntity.setAreaVillage(k);
                        historyEntity.setCount(y);
                        historyEntity.setType(4);
                        historyEntity.setValue(x);
                        historyEntity.setTime(LocalDate.now().format(DateUtils.YY_MM));
                        historyEntityList.add(historyEntity);
                    }
                });
            }
            if (MapUtils.isNotEmpty(v.getHealth())){
                v.getHealth().forEach((x,y)->{
                    if (x!=null && x!=0) {
                        HistoryEntity historyEntity = new HistoryEntity();
                        historyEntity.setAreaVillage(k);
                        historyEntity.setCount(y);
                        historyEntity.setType(999);
                        historyEntity.setValue(x);
                        historyEntity.setTime(LocalDate.now().format(DateUtils.YY_MM));
                        historyEntityList.add(historyEntity);
                    }
                });
            }

        });
        historyRepository.batchInsertEntity(historyEntityList);
    }

    private Integer findAgeSection(LocalDate birthday) {
        Integer age = DateUtils.birthdayToAge(birthday);
        if (age >=100){
            return 5;
        }
        if (age>=90){
            return 4;
        }
        if (age>=80){
            return 3;
        }
        if (age>=70){
            return 2;
        }
        if (age>=60){
            return 1;
        }
        return null;
    }


    @Data
    class HistoryBo{
        private Map<Integer,Long> gender=Maps.newHashMap();
        private Map<Integer,Long> huji=Maps.newHashMap();
        private Map<Integer,Long> age=Maps.newHashMap();
        private Long sizSum=0L;
        private Map<Integer,Long> family=Maps.newHashMap();
        private Map<Integer,Long> economic=Maps.newHashMap();
        private Map<Integer,Long> health=Maps.newHashMap();
    }
}
