package com.namnx.spring_aop.web;

import com.namnx.spring_aop.model.UserEntity;
import com.namnx.spring_aop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserAPI {

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<UserEntity> addUser(@RequestBody UserEntity user) {
        return ResponseEntity.ok(userService.addUser(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> detail(@PathVariable Long id) {
        Optional<UserEntity> user = userService.findById(id);
        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(user.get());
        }
    }


    @GetMapping("/all")
    public ResponseEntity<Object> all() {
        Map<String, Object> mapData = new HashMap<>();
        mapData.put("status", "success");
        mapData.put("data", userService.findAll());
        return ResponseEntity.ok(mapData);
    }
}
