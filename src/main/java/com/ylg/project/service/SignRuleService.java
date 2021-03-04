package com.ylg.project.service;

import com.ylg.common.utils.IdWorker;
import com.ylg.project.dao.SignRuleDao;
import com.ylg.project.domain.SignRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignRuleService {
    @Autowired
    private SignRuleDao signRuleDao;

    @Autowired
    private IdWorker idWorker;

    public void save(SignRule signRule){
        signRule.setId(idWorker.nextId()+"");
        signRuleDao.save(signRule);
    }

    public SignRule findByOrganizationId(String organizationId){
        return signRuleDao.findByOrganizationId(organizationId);
    }

    public void update(SignRule signRule){
        signRuleDao.save(signRule);
    }
}
