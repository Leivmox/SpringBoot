package com.rabbiter.bms.service.impl;

import com.rabbiter.bms.mapper.BorrowMapper;
import com.rabbiter.bms.mapper.UserMapper;
import com.rabbiter.bms.model.User;
import com.rabbiter.bms.service.UserService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * UserServiceImpl 实现了 UserService 接口，提供用户相关的服务方法。
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;  // 用于访问用户相关的数据库操作

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;  // 用于操作 Redis 缓存

    @Resource
    private BorrowMapper borrowMapper;  // 用于访问借阅记录相关的数据库操作

    /**
     * 用户登录方法，根据用户名、密码和身份查询用户是否存在。
     *
     * @param user 包含用户名、密码和身份信息的 User 对象
     * @return 查询到的 User 对象，如果用户不存在则返回 null
     */
    @Override
    public User login(User user) {
        return userMapper.selectByUsernameAndPasswordAndIsAdmin(
                user.getUsername(),
                user.getUserpassword(),
                user.getIsadmin()
        );
    }

    /**
     * 将用户信息保存到 Redis 缓存中，作为登录状态的凭证。
     *
     * @param token 用户的唯一标识符（通常由前端保存并发送给后端）
     * @param user 用户对象，包含用户的基本信息
     */
    @Override
    public void saveUser(String token, User user) {
        // 设置 redisTemplate 对象 key 的序列化方式为字符串
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // 保存用户信息到 Redis，设置过期时间为 1 小时
        redisTemplate.opsForValue().set(token, user, 1, TimeUnit.HOURS);
    }

    /**
     * 根据 token 从 Redis 中获取用户信息。
     *
     * @param token 用户的唯一标识符
     * @return 缓存中的 User 对象，如果不存在则返回 null
     */
    @Override
    public User getUser(String token) {
        return (User) redisTemplate.opsForValue().get(token);
    }

    /**
     * 从 Redis 中移除用户信息，表示用户退出登录。
     *
     * @param token 用户的唯一标识符
     */
    @Override
    public void removeUser(String token) {
        redisTemplate.delete(token);
    }

    /**
     * 注册新用户，先检查用户名是否重复。
     *
     * @param username 用户名
     * @param password 密码
     * @return 注册结果：0 表示用户名已存在，1 表示成功
     */
    @Override
    public Integer register(String username, String password) {
        User tmp = userMapper.selectByUsername(username);
        if (tmp != null) return 0;  // 用户名已存在，注册失败

        User user = new User();
        user.setUsername(username);
        user.setUserpassword(password);
        user.setIsadmin((byte) 0);  // 默认注册为普通用户
        return userMapper.insertSelective(user);
    }

    /**
     * 更新用户密码。
     *
     * @param id 用户 ID
     * @param password 新密码
     */
    @Override
    public void setPassword(Integer id, String password) {
        User user = new User();
        user.setUserid(id);
        user.setUserpassword(password);
        userMapper.updateByPrimaryKeySelective(user);  // 只更新密码字段
    }

    /**
     * 获取系统中用户的总数。
     *
     * @return 用户总数
     */
    @Override
    public Integer getCount() {
        return userMapper.selectCount();
    }

    /**
     * 查询所有用户信息。
     *
     * @return 包含所有用户的 List 列表
     */
    @Override
    public List<User> queryUsers() {
        return userMapper.selectAll();
    }

    /**
     * 获取符合查询条件的用户数量，用于分页查询。
     *
     * @param params 包含查询条件的 Map 对象
     * @return 符合条件的用户数量
     */
    @Override
    public int getSearchCount(Map<String, Object> params) {
        return userMapper.selectCountBySearch(params);
    }

    /**
     * 分页查询符合条件的用户列表。
     *
     * @param params 包含查询条件和分页参数的 Map 对象
     * @return 符合条件的用户列表
     */
    @Override
    public List<User> searchUsersByPage(Map<String, Object> params) {
        return userMapper.selectBySearch(params);
    }

    /**
     * 添加新用户。
     *
     * @param user 用户对象
     * @return 插入操作的结果
     */
    @Override
    public Integer addUser(User user) {
        return userMapper.insertSelective(user);
    }

    /**
     * 删除指定的用户，进行必要的检查。
     *
     * @param user 用户对象，包含待删除用户的信息
     * @return 删除结果：-2 表示不能删除 ID 为 1 的管理员用户，-1 表示用户有未归还的借阅记录，其他值为成功删除的记录数
     */
    @Override
    public Integer deleteUser(User user) {
        if (user.getUserid() == 1) return -2;  // 不能删除 ID 为 1 的管理员用户

        Map<String, Object> map = new HashMap<>();
        map.put("userid", user.getUserid());

        // 检查用户是否有未归还的借阅记录
        if (borrowMapper.selectCountBySearch(map) > 0) {
            return -1;  // 用户有未归还的借阅记录，删除失败
        }

        return userMapper.deleteByPrimaryKey(user.getUserid());
    }

    /**
     * 批量删除用户。
     *
     * @param users 待删除用户列表
     * @return 成功删除的用户数量
     */
    @Override
    public Integer deleteUsers(List<User> users) {
        int count = 0;
        for (User user : users) {
            count += deleteUser(user);  // 遍历删除每个用户
        }
        return count;
    }

    /**
     * 更新用户信息。
     *
     * @param user 包含新信息的用户对象
     * @return 更新操作的结果
     */
    @Override
    public Integer updateUser(User user) {
        return userMapper.updateByPrimaryKeySelective(user);  // 选择性更新用户信息
    }
}
