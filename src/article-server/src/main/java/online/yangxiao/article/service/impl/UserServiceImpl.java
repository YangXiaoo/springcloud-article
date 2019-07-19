package online.yangxiao.article.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import online.yangxiao.article.dao.UserMapper;
import online.yangxiao.common.entity.User;
import online.yangxiao.article.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;

    @Transactional
    public int regist(User user) {
        int i = userMapper.insertSelective(user);
        return i;
    }

    public User loginByEmail(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        return userMapper.selectOne(user);
    }

    public User findByEmail(String email) {
        User user = new User();
        user.setEmail(email);
        return userMapper.selectOne(user);
    }

    public User findByUsername(String username) {
        User user = new User();
        user.setUsername(username);
        return userMapper.selectOne(user);
    }

    public User findById(Integer id) {
        User user = new User();
        user.setId(id);
        return userMapper.selectOne(user);
    }

    public void update(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }
}
