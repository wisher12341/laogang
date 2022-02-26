package com.lejian.laogang.util;

import com.lejian.laogang.LaogangApplication;
import com.lejian.laogang.ServletInitializer;
import com.lejian.laogang.pojo.bo.JpaSpecBo;
import com.lejian.laogang.pojo.bo.OldmanBo;
import com.lejian.laogang.repository.OldmanRepository;
import com.lejian.laogang.service.OldmanService;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = LaogangApplication.class)
public class Data {

    @Autowired
    private OldmanRepository oldmanRepository;

    @Test
    @Transactional
    public void test(){
        int pageNo =0;
        List<OldmanBo> oldmanBoList = oldmanRepository.findByPageWithSpec(pageNo, 200, new JpaSpecBo());
        pageNo++;
        while (CollectionUtils.isNotEmpty(oldmanBoList)){
            oldmanRepository.batchUpdate(oldmanBoList);
            oldmanBoList = oldmanRepository.findByPageWithSpec(pageNo, 200, new JpaSpecBo());
            pageNo++;
        }
    }
}
