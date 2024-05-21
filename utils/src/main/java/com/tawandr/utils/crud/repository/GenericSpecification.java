package com.tawandr.utils.crud.repository;

import com.tawandr.utils.crud.domain.BaseEntity;
import org.springframework.data.jpa.domain.Specification;

public abstract class GenericSpecification<T extends BaseEntity> {

    public   Specification<T> innerFieldLike(String innerField, String field, String value) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get(innerField).get(field), "%" + value + "%");
    }

    public abstract Specification<T> generateQuery(String ...arg);

    public  Specification<T> like(String field, String value) {

        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get(field), "%" + value + "%");
    }

}
