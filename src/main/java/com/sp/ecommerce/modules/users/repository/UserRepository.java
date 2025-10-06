package com.sp.ecommerce.modules.users.repository;

import com.sp.ecommerce.modules.users.entity.UserEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID>,
        JpaSpecificationExecutor<UserEntity> {

    // TODO : avoid * in query
    @Query(value = "Select * from commerce.users u " +
            "WHERE u.business_email = :businessEmail " +
            "OR u.business_phone = :businessPhone limit 1", nativeQuery = true)
    Optional<UserEntity> findByEmailOrPhone(
            @Param("businessEmail") String businessEmail,
            @Param("businessPhone") String businessPhone);


    // UUID parsed to String?
    @Query(value = "Select * from commerce.users u " +
            "WHERE u.user_id = :userId", nativeQuery = true)
    Optional<UserEntity> findByUserId(
            @Param("userId") UUID userId);

    // why modifying?
    @Modifying
    @Transactional
    @Query(value = "UPDATE commerce.users " +
            "SET is_deleted = true " +
            "WHERE user_id = :userId", nativeQuery = true)
    void softDeleteByUserId(@Param("userId") UUID userId);
}
