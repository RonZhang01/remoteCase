package cn.itcast.dao;

import cn.itcast.domain.User;

import java.util.List;

/**
 * 用户操作的DAO
 */
public interface UserDao {

    public List<User> findAll();

    public User findUserByUsernameAndPassword(String username, String password);

    public int addUser(User user);

    public int del(int id);

    public User findUserById(int id);

    public int updateById(User user);

    public int findTotalCount();

    public List<User> findByPage(int start, int rows);
}
