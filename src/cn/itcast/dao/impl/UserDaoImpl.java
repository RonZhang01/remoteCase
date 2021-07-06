package cn.itcast.dao.impl;

import cn.itcast.dao.UserDao;
import cn.itcast.domain.User;
import cn.itcast.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName UserDaoImpl
 * @Description TODO
 * @Author zhangrh
 * @Date 2021/6/22 22:15
 */
public class UserDaoImpl implements UserDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<User> findAll() {
        //使用JDBC操作数据库
        String sql = "select * from user";
        List<User> users = template.query(sql, new BeanPropertyRowMapper<User>(User.class));
        return users;
    }

    @Override
    public List<User> findUserByUsernameAndPassword(String username, String password) {

        try {
            String sql = "select * from user where username = ? and password = ?";
//            User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
            List<User> users = template.query(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
            return users;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int addUser(User user) {
        String sql = "INSERT INTO user (id,name,gender,age,address,qq,email) VALUES (NULL,?,?,?,?,?,?)";
        int i = template.update(sql, user.getName(), user.getGender(), user.getAge(), user.getAddress(), user.getQq(), user.getEmail());
        return i;
    }

    @Override
    public int del(int id) {
        String sql = "delete from user where id = ?";
        int i = template.update(sql, id);
        return i;
    }

    @Override
    public User findUserById(int id) {
        String sql = "select * from user where id = ?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), id);
    }

    @Override
    public int updateById(User user) {
        String sql = "update user set gender = ?, age = ?, address = ?, qq = ?, email = ? where id = ?";
        return template.update(sql,user.getGender(),user.getAge(),user.getAddress(),user.getQq(),user.getEmail(),user.getId());
    }

    @Override
    public int findTotalCount(Map<String, String[]> condition) {
        //定义模板初始化sql
        String sql = "select count(*) from user where 1 = 1 ";

        StringBuilder stringBuilder = new StringBuilder(sql);

        List<Object> params = new ArrayList<Object>();

        //遍历map
        Set<String> keySet = condition.keySet();
        for (String key : keySet) {
            if ("currentPage".equals(key) || "rows".equals(key)){
                continue;
            }

            //获取value
            String value = condition.get(key)[0];
            //判断value是否有值
            if (value != null && !"".equals(value)){
                stringBuilder.append(" and "+key+" like ? ");
                params.add("%" + value + "%");
            }
        }

        System.out.println(params.toString());
        System.out.println(stringBuilder.toString());

        return template.queryForObject(stringBuilder.toString(),Integer.class,params.toArray());
    }

    @Override
    public List<User> findByPage(int start, int rows, Map<String, String[]> condition) {
        String sql = "select * from user where 1 = 1 ";

        StringBuilder stringBuilder = new StringBuilder(sql);

        List<Object> params = new ArrayList<Object>();

        //遍历map
        Set<String> keySet = condition.keySet();
        for (String key : keySet) {
            if ("currentPage".equals(key) || "rows".equals(key)){
                continue;
            }
            //获取value
            String value = condition.get(key)[0];
            //判断value是否有值
            if (value != null && !"".equals(value)){
                stringBuilder.append(" and "+key+" like ? ");
                params.add("%" + value + "%");
            }
        }

        stringBuilder.append(" limit ?,?");
        params.add(start);
        params.add(rows);

        return template.query(stringBuilder.toString(),new BeanPropertyRowMapper<User>(User.class), params.toArray());
    }
}
