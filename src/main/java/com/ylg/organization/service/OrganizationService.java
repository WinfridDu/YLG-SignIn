package com.ylg.organization.service;

import com.ylg.common.utils.IdWorker;
import com.ylg.domain.organization.Organization;
import com.ylg.domain.system.User;
import com.ylg.organization.dao.OrganizationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationDao organizationDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 添加组织
     *
     * @param organization 组织信息
     */
    public Organization add(Organization organization) {
        organization.setId(idWorker.nextId() + "");
        organization.setCreateTime(new Date());
        organization.setState(0);    //启用
        organization.setAuditState("0"); //待审核
        return organizationDao.save(organization);
    }
    public Organization update(Organization organization) {
        return organizationDao.save(organization);
    }
    public Organization findById(String id) {
        return organizationDao.findById(id).get();
    }
    public void deleteById(String id) {
        organizationDao.deleteById(id);
    }
    public List<Organization> findAll(Map<String,Object> map) {
        Specification<Organization> spec = new Specification<Organization>() {
            /**
             * 动态拼接查询条件
             * @return
             */
            public Predicate toPredicate(Root<Organization> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                //根据请求的organizationId是否为空构造查询条件
                //根据等级构造查询条件
                if(!StringUtils.isEmpty(map.get("state"))) {
                    list.add(criteriaBuilder.equal(root.get("state").as(Integer.class),Integer.parseInt((String) map.get("state"))));
                }
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };

        //2.分页
        List<Organization> list = organizationDao.findAll(spec);
        return list;
    }
}
