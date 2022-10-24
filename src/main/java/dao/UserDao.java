package dao;

import connectionmaker.ConnectionMaker;
import domain.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import statement.AddStrategy;
import statement.DeleteAllStrategy;
import statement.StatementStrategy;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDao {

//    private final JdbcContext jdbcContext;
//    private final DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public UserDao(DataSource dataSource) {
//        this.dataSource = dataSource;
//        this.jdbcContext = new JdbcContext(this.dataSource);
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public void insert(final User user){
        this.jdbcTemplate.update("INSERT INTO users(id,name,password) VALUES(?,?,?)",user.getId(),user.getName(),user.getPassword());
    }

    public void deleteAll(){
        this.jdbcTemplate.update("DELETE FROM users");
    }

    public User selectId(String id){
        String sql = "select * from users where id = ?";
        RowMapper<User> rowMapper = new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User(rs.getString("id"),rs.getString("name"),rs.getString("password"));
                return user;
            }
        };
        return this.jdbcTemplate.queryForObject(sql,rowMapper,id);
    }

    public int getCount(){
        return this.jdbcTemplate.queryForObject("select count(*) from users",Integer.class);
    }

    public List<User> getAll(){
        String sql = "select * from users order by id";
        RowMapper<User> rowMapper = new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User(rs.getString("id"),rs.getString("name"),rs.getString("password"));
                return user;
            }
        };
        return this.jdbcTemplate.query(sql,rowMapper);
    }

//    public void insert(User user){
//        jdbcContext.workWithStatementStrategy(new StatementStrategy() {
//            @Override
//            public PreparedStatement makeStatement(Connection c) throws SQLException {
//                PreparedStatement ps = c.prepareStatement("INSERT INTO users(id,name,password) VALUES(?,?,?)");
//                ps.setString(1,user.getId());
//                ps.setString(2,user.getName());
//                ps.setString(3,user.getPassword());
//
//                return ps;
//            }
//        });
//    }

//    public void deleteAll(){
//        jdbcContext.executeSql("DELETE FROM users");
//    }

//    public User selectId(String id) {
//        Connection connection = null;
//        PreparedStatement ps = null;
//        User user = null;
//        ResultSet rs = null;
//        try {
//            connection = dataSource.getConnection();
//            ps = connection.prepareStatement("SELECT id,name,password FROM users WHERE id = ?");
//            ps.setString(1, id);
//
//            user = null;
//            rs = ps.executeQuery();
//            if (rs.next()) {
//                user = new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));
//            }
//
//            if(user == null) throw new EmptyResultDataAccessException(1);
//
//            //return을 try문 내에 넣어도 finally는 실행된다.
//            return user;
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//            if(rs!=null){
//                try{
//                    rs.close();
//                }catch (SQLException e){
//
//                }
//            }
//            if(ps!=null){
//                try{
//                    ps.close();
//                }catch (SQLException e){
//
//                }
//            }
//            if(connection!=null){
//                try{
//                    connection.close();
//                }catch (SQLException e){
//
//                }
//            }
//        }
//    }


//    public int getCount(){
//        Connection connection = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        int count = 0;
//        try {
//            connection = dataSource.getConnection();
//            ps = connection.prepareStatement("SELECT count(*) FROM users");
//
//            rs = ps.executeQuery();
//            rs.next();
//
//            count = rs.getInt(1);
//            return count;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//            if(rs!=null){
//                try{
//                    rs.close();
//                }catch (SQLException e){
//
//                }
//            }
//            if(ps!=null){
//                try{
//                    ps.close();
//                }catch (SQLException e){
//
//                }
//            }
//            if(connection!=null){
//                try{
//                    connection.close();
//                }catch (SQLException e){
//
//                }
//            }
//        }
//    }
}
