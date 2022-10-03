package com.techcourse.dao;

import com.techcourse.domain.User;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import nextstep.jdbc.DataAccessException;
import nextstep.jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDao {

    private static final Logger log = LoggerFactory.getLogger(UserDao.class);

    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    public UserDao(final DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public UserDao(final JdbcTemplate jdbcTemplate) {
        this.dataSource = null;
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insert(final User user) {
        String sql = "insert into users (account, password, email) values (?, ?, ?)";
        jdbcTemplate.update(sql, user.getAccount(), user.getPassword(), user.getEmail());
    }

    public void update(final User user) {
        String sql = "update users set (account, password, email) = (?, ?, ?) where id = ?";
        jdbcTemplate.update(sql, user.getAccount(), user.getPassword(), user.getEmail(), user.getId());
    }

    public List<User> findAll() {
        String sql = "select id, account, password, email from users";
        return jdbcTemplate.query(sql, rs -> {
            try {
                return new User(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4));
            } catch (SQLException e) {
                throw new DataAccessException();
            }
        });
    }

    public User findById(final Long id) {
        String sql = "select id, account, password, email from users where id = ?";
        List<User> users = jdbcTemplate.query(sql, rs -> {
            try {
                return new User(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4));
            } catch (SQLException e) {
                throw new DataAccessException();
            }
        }, id);

        return users.get(0);
    }

    public User findByAccount(final String account) {
        String sql = "select id, account, password, email from users where account = ?";
        List<User> users = jdbcTemplate.query(sql, rs -> {
            try {
                return new User(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4));
            } catch (SQLException e) {
                throw new DataAccessException();
            }
        }, account);

        return users.get(0);
    }
}
