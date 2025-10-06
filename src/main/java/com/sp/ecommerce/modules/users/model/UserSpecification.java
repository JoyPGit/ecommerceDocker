package com.sp.ecommerce.modules.users.model;

import com.sp.ecommerce.modules.users.entity.*;
import com.sp.ecommerce.modules.users.model.enumType.UserType;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class UserSpecification {

    public static Specification<UserEntity> hasType(String type) {
        return (root, query, cb) -> {
            if (type == null || type.isEmpty()) return cb.conjunction(); // no filter
            try {
                UserType userType = UserType.valueOf(type.toUpperCase());
                return cb.equal(root.get("type"), userType);
            } catch (IllegalArgumentException e) {
                // if invalid type string, return false condition
                return cb.disjunction();
            }
        };
    }

    public static Specification<UserEntity> globalSearch(String searchBy) {
        return (root, query, cb) -> {
            String likePattern = "%" + searchBy.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("businessName")), likePattern),
                    cb.like(cb.lower(root.get("businessEmail")), likePattern),
                    cb.like(cb.lower(root.get("businessPhone")), likePattern)
            );
        };
    }
}
