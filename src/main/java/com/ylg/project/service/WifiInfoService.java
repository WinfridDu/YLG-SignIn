package com.ylg.project.service;

import com.ylg.common.utils.IdWorker;
import com.ylg.project.dao.WifiInfoDao;
import com.ylg.project.domain.WifiInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WifiInfoService {
    @Autowired
    private WifiInfoDao wifiInfoDao;

    @Autowired
    private IdWorker idWorker;

    public void save(WifiInfo wifiInfo){
        wifiInfo.setId(idWorker.nextId()+"");
        wifiInfoDao.save(wifiInfo);
    }

    public WifiInfo findByOrganizationId(String organizationId){
        return wifiInfoDao.findByOrganizationId(organizationId);
    }

    public void update(WifiInfo wifiInfo){
        wifiInfoDao.save(wifiInfo);
    }

}
