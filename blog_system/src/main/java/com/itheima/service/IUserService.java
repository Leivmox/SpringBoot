package com.itheima.service;

import com.github.pagehelper.PageInfo;
import com.itheima.model.domain.User;

public interface IUserService {
    boolean existsByUsername(String username);
    void save(User user);
    void deleteUserById(int id);
    PageInfo<User> getAllUsers(int page, int count);
}