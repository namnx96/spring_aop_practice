package com.namnx.spring_aop.repository;

import com.namnx.spring_aop.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
