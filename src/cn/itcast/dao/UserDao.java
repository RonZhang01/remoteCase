package cn.itcast.dao;

import cn.itcast.domain.User;

import java.util.List;
import java.util.Map;

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

    public int findTotalCount(Map<String, String[]> condition);

    public List<User> findByPage(int start, int rows, Map<String, String[]> condition);
}
