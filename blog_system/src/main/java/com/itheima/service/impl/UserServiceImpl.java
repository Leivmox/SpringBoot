package com.itheima.service.impl;

import com.itheima.dao.UserRepository;
import com.itheima.model.domain.User;
import com.itheima.model.domain.UserAuthority;
import com.itheima.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository; // 使用 Spring Data JPA 进行数据库操作

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username); // 检查用户名是否存在
    }

    @Override
    public void save(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // 创建密码编码器
        user.setPassword(passwordEncoder.encode(user.getPassword())); // 对用户密码进行加密
        user.setCreated(new Date()); // 设置用户创建时间
        user.setValid(true); // 设置用户有效性

        // 设置用户权限
        Set<UserAuthority> authorities = new HashSet<>();
        authorities.add(new UserAuthority(user, 2)); // 使用整数值表示权限，假设 2 表示普通用户
        user.setAuthorities(authorities);

        userRepository.save(user); // 将用户信息保存到数据库
    }
}
