package cn.itcast.service;

import cn.itcast.domain.PageBean;
import cn.itcast.domain.User;

import java.util.List;
import java.util.Map;

/**
 * 用户管理的业务接口
 */
public interface UserService {

    /**
     * 查询所有用户信息
     * @return
     */
    public List<User> findAll();

    public List<User> login(User user);

    public int addUser(User user);

    public int delUser(int id);

    public User findUserById(int id);

    public int updateUser(User user);

    public int delUsersById(String[] uids);

    public PageBean<User> findUserByPage(String currentPageStr, String rowsStr, Map<String, String[]> condition);
}
