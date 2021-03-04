package com.ylg.common.service;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class BaseService<T> {
    protected Specification<T> getSpec(String organizationId) {
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder cb) {
                return cb.equal(root.get("organizationId").as(String.class),organizationId);
            }
        };
    }

}
