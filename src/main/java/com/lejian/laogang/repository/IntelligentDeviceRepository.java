package com.lejian.laogang.repository;


import com.lejian.laogang.log.LogRecord;
import com.lejian.laogang.pojo.bo.HomBedBo;
import com.lejian.laogang.pojo.bo.IntelligentDeviceBo;
import com.lejian.laogang.repository.dao.HomeBedDao;
import com.lejian.laogang.repository.dao.IntelligentDeviceDao;
import com.lejian.laogang.repository.entity.HomeBedEntity;
import com.lejian.laogang.repository.entity.IntelligentDeviceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@LogRecord(table = "intelligent_device")
@Repository
public class IntelligentDeviceRepository extends AbstractSpecificationRepository<IntelligentDeviceBo,IntelligentDeviceEntity> {

    @Autowired
    private IntelligentDeviceDao dao;


    @Override
    protected JpaRepository getDao() {
        return dao;
    }

    @Override
    protected IntelligentDeviceBo convert(IntelligentDeviceEntity entity) {
        return IntelligentDeviceBo.convert(entity);
    }

    @Override
    public String getTableName() {
        return "intelligent_device";
    }

    public IntelligentDeviceBo findByOldmanId(Integer id) {
        return IntelligentDeviceBo.convert(dao.findByOldmanId(id));
    }
}
