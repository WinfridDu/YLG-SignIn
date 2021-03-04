package com.ylg.system.dao;

import com.ylg.domain.system.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserDao extends JpaRepository<User,String>,JpaSpecificationExecutor<User> {

    public User findByOpenid(String openid);
}
