package com.ylg.project.service;

import com.ylg.common.utils.IdWorker;
import com.ylg.project.dao.SignUpLimitDao;
import com.ylg.project.domain.SignUpLimit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignUpLimitService {
    @Autowired
    private SignUpLimitDao signUpLimitDao;

    @Autowired
    private IdWorker idWorker;

    public void save(SignUpLimit signUpLimit) {
        signUpLimit.setId(idWorker.nextId()+"");
        signUpLimitDao.save(signUpLimit);
    }

    public void update(SignUpLimit signUpTime){
        signUpLimitDao.save(signUpTime);
    }

    public SignUpLimit find(String organizationId){
        return signUpLimitDao.findByOrganizationId(organizationId);
    }

    public void deleteById(String id) {
        signUpLimitDao.deleteById(id);
    }

    public SignUpLimit findByOrganizationId(String id){
        return signUpLimitDao.findByOrganizationId(id);
    }
}
