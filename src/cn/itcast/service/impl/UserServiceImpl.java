package cn.itcast.service.impl;

import cn.itcast.dao.UserDao;
import cn.itcast.dao.impl.UserDaoImpl;
import cn.itcast.domain.PageBean;
import cn.itcast.domain.User;
import cn.itcast.service.UserService;

import java.util.List;
import java.util.Map;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author zhangrh
 * @Date 2021/6/22 22:11
 */
public class UserServiceImpl implements UserService {


    private UserDao userDao = new UserDaoImpl();

    @Override
    public List<User> findAll() {
        //调用dao 完成查询
        return userDao.findAll();
    }

    @Override
    public List<User> login(User user) {
        return userDao.findUserByUsernameAndPassword(user.getUsername(),user.getPassword());
    }

    @Override
    public int addUser(User user) {
        return userDao.addUser(user);
    }

    @Override
    public int delUser(int id) {
        return userDao.del(id);
    }

    @Override
    public User findUserById(int id) {
        return userDao.findUserById(id);
    }

    @Override
    public int updateUser(User user) {
        return userDao.updateById(user);
    }

    @Override
    public int delUsersById(String[] uids) {
        int del = 0;
        if (uids != null && uids.length>0){
            for (String uid : uids) {
                del += userDao.del(Integer.parseInt(uid));
            }
        }
        return del;
    }

    @Override
    public PageBean<User> findUserByPage(String currentPageStr, String rowsStr, Map<String, String[]> condition) {

        int currentPage = Integer.parseInt(currentPageStr);
        int rows = Integer.parseInt(rowsStr);

        if (currentPage < 1){
            currentPage = 1;
        }


        //创建空的PageBean对象
        PageBean<User> userPageBean = new PageBean<>();


        //调用dao查询totalCount总记录数属性
        int totalCount = userDao.findTotalCount(condition);
        //计算总页码数
        int totalPage = totalCount % rows == 0 ? (totalCount / rows) : (totalCount / rows + 1);

        if (currentPage > totalPage){
            currentPage = totalPage;
        }
        //计算start值（currentPage-1）*rows
        int start = (currentPage-1)*rows;

        //调用dao查询list集合
        List<User> userList = userDao.findByPage(start,rows,condition);


        //设置currentPage和rows属性
        userPageBean.setCurrentPage(currentPage);
        userPageBean.setRows(rows);
        //返回PageBean对象
        userPageBean.setTotalCount(totalCount);
        userPageBean.setTotalPage(totalPage);
        userPageBean.setList(userList);

        return userPageBean;
    }
}
