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
    private UserRepository userRepository;

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public void save(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreated(new Date());
        user.setValid(true);

        Set<UserAuthority> authorities = new HashSet<>();
        authorities.add(new UserAuthority(user, 2)); // 使用整数值表示权限
        user.setAuthorities(authorities);

        userRepository.save(user);
    }
}
