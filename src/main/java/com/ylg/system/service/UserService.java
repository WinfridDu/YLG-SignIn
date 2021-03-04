package com.ylg.system.service;

import com.ylg.common.utils.IdWorker;
import com.ylg.domain.organization.Department;
import com.ylg.domain.organization.Organization;
import com.ylg.domain.system.Role;
import com.ylg.domain.system.User;
import com.ylg.organization.dao.DepartmentDao;
import com.ylg.organization.dao.OrganizationDao;
import com.ylg.system.dao.RoleDao;
import com.ylg.system.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private OrganizationDao organizationDao;

    @Autowired
    private DepartmentDao departmentDao;

    /**
     * 根据openid查询用户
     */
    public User findByOpenid(String openid) {
        return userDao.findByOpenid(openid);
    }

    /**
     * 1.保存用户
     */
    public void save(User user) {
        //设置主键的值
        String id = idWorker.nextId()+"";
        user.setEnableState(0);
        user.setId(id);
        user.setCreateTime(new Date());
        //添加组织id
        String organizationId = user.getOrganizationId();
        if(null!=organizationId){
            Organization organization = organizationDao.findById(organizationId).get();
            user.setOrganizationId(organizationId);
            user.setOrganizationName(organization.getName());
        }
        //查询部门id
        if(null!=user.getDepartmentId()){
            Department department = departmentDao.findByIdAndOrganizationId(user.getDepartmentId(), organizationId);
            user.setDepartmentId(user.getDepartmentId());
            user.setDepartmentName(department.getName());
        }
        //调用dao保存用户
        userDao.save(user);
    }

    /**
     * 2.更新用户
     */
    public void update(User user) {
        //1.根据id查询用户
        User target = userDao.findById(user.getId()).get();
        //2.设置用户属性
        target.setName(user.getName());
        target.setMobile(user.getMobile());
        target.setEnableState(user.getEnableState());
        target.setInServiceStatus(user.getInServiceStatus());
        if(null!=user.getDepartmentId()){
            target.setDepartmentId(user.getDepartmentId());
            target.setDepartmentName(user.getDepartmentName());
        }
        if(null!=user.getOrganizationId()){
            target.setOrganizationId(user.getOrganizationId());
            target.setOrganizationName(user.getOrganizationName());
        }
        //3.更新用户
        userDao.save(target);
    }

    /**
     * 3.根据id查询用户
     */
    public User findById(String id) {
        return userDao.findById(id).get();
    }

    /**
     * 4.查询全部用户列表
     *      参数：map集合的形式
     *          hasDept
     *          departmentId
     *          organizationId
     *
     */
    public List<User> findAll(Map<String,Object> map) {
        //1.需要查询条件
        Specification<User> spec = new Specification<User>() {
            /**
             * 动态拼接查询条件
             * @return
             */
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                //根据请求的organizationId是否为空构造查询条件
                if(!StringUtils.isEmpty(map.get("organizationId"))) {
                    list.add(criteriaBuilder.equal(root.get("organizationId").as(String.class),(String)map.get("organizationId")));
                }
                //根据请求的部门id构造查询条件
                if(!StringUtils.isEmpty(map.get("departmentId"))) {
                    list.add(criteriaBuilder.equal(root.get("departmentId").as(String.class),(String)(map.get("departmentId"))));
                }
                //根据状态构造查询条件
                if(!StringUtils.isEmpty(map.get("enableState"))) {
                    list.add(criteriaBuilder.equal(root.get("enableState").as(Integer.class),Integer.parseInt((String)map.get("enableState"))));
                }
                //根据等级构造查询条件
                if(!StringUtils.isEmpty(map.get("level"))) {
                    list.add(criteriaBuilder.equal(root.get("level").as(String.class),(String)map.get("level")));
                }
                list.add(criteriaBuilder.equal(root.get("inServiceStatus").as(Integer.class), 1));
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        List<User> list = userDao.findAll(spec);
        return list;
    }

    /**
     * 5.根据id删除用户
     */
    public void deleteById(String id) {
        userDao.deleteById(id);
    }

    /**
     * 分配角色
     */
    public void assignRoles(String userId,List<String> roleIds) {
        //1.根据id查询用户
        User user = userDao.findById(userId).get();
        //2.设置用户的角色集合
        Set<Role> roles = new HashSet<>();
        for (String roleId : roleIds) {
            Role role = roleDao.findById(roleId).get();
            roles.add(role);
        }
        //设置用户和角色集合的关系
        user.setRoles(roles);
        //3.更新用户
        userDao.save(user);
    }
}
