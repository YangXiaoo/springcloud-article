package online.yangxiao.article.service;

import online.yangxiao.common.entity.User;

import java.util.List;

public interface UserService {

    int regist(User user);

    User loginByEmail(String email, String password);

    User findByEmail(String email);

    User findByUsername(String username);

    User findById(Integer id);

//    void deleteByEmail(String email);

    void update(User user);

//    void deleteById(Integer cid);

}
