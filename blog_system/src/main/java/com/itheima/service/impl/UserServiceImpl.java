package com.itheima.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.UserRepository;
import com.itheima.model.domain.User;
import com.itheima.model.domain.UserAuthority;
import com.itheima.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
        authorities.add(new UserAuthority(user, 2));
        user.setAuthorities(authorities);

        userRepository.save(user);
    }

    @Override
    public void deleteUserById(int id) {
        userRepository.deleteById((long) id);
    }

    @Override
    public PageInfo<User> getAllUsers(int page, int count) {
        PageHelper.startPage(page, count);
        List<User> users = userRepository.findAll();
        return new PageInfo<>(users);
    }
}
