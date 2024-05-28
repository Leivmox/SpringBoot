package com.itheima.service;

import com.itheima.model.domain.User;

public interface IUserService {
    boolean existsByUsername(String username);
    void save(User user);
}
