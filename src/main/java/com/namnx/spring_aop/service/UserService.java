package com.namnx.spring_aop.service;

import com.namnx.spring_aop.aspect.performance_log.PerformanceLoggable;
import com.namnx.spring_aop.model.UserEntity;
import com.namnx.spring_aop.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @PerformanceLoggable
    public UserEntity addUser(UserEntity user) {
        return userRepository.save(user);
    }

    @PerformanceLoggable
    @Transactional(readOnly = true)
    public Optional<UserEntity> findById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @PerformanceLoggable
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }
}
