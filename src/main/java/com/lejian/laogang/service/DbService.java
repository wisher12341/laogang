package com.lejian.laogang.service;

import com.google.common.collect.Maps;
import com.lejian.laogang.repository.AbstractRepository;
import com.lejian.laogang.repository.AbstractSpecificationRepository;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DbService implements ApplicationContextAware {


    private Map<String,AbstractRepository> map = Maps.newHashMap();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContext.getBeansOfType(AbstractRepository.class).forEach((k,v)->{
            map.put(v.getTableName(),v);
        });
    }

    public void delete(List<Integer> idList, String table) {
        AbstractRepository repository = map.get(table);
        if (repository!=null){
            idList.forEach(repository::deleteById);
        }
    }
}
