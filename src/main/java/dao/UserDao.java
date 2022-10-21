package dao;

import connectionmaker.ConnectionMaker;
import domain.User;
import org.springframework.dao.EmptyResultDataAccessException;
import statement.AddStrategy;
import statement.DeleteAllStrategy;
import statement.StatementStrategy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private ConnectionMaker connectionMaker;

    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void jdbcContextWithStatementStrategy(StatementStrategy stmt){
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = this.connectionMaker.makeConnection();
            ps = stmt.makeStatement(connection);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(ps!=null){
                try{
                    ps.close();
                }catch (SQLException e){

                }
            }
            if(connection!=null){
                try{
                    connection.close();
                }catch (SQLException e){

                }
            }
        }
    }

    public void insert(User user){
        jdbcContextWithStatementStrategy(new AddStrategy(user));
    }

    public void deleteAll() {
        this.jdbcContextWithStatementStrategy(new DeleteAllStrategy());
    }

    public User selectId(String id) {
        Connection connection = null;
        PreparedStatement ps = null;
        User user = null;
        ResultSet rs = null;
        try {
            connection = this.connectionMaker.makeConnection();
            ps = connection.prepareStatement("SELECT id,name,password FROM users WHERE id = ?");
            ps.setString(1, id);

            user = null;
            rs = ps.executeQuery();
            if (rs.next()) {
                user = new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));
            }

            if(user == null) throw new EmptyResultDataAccessException(1);

            //return을 try문 내에 넣어도 finally는 실행된다.
            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(rs!=null){
                try{
                    rs.close();
                }catch (SQLException e){

                }
            }
            if(ps!=null){
                try{
                    ps.close();
                }catch (SQLException e){

                }
            }
            if(connection!=null){
                try{
                    connection.close();
                }catch (SQLException e){

                }
            }
        }
    }

    public int getCount(){
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;
        try {
            connection = this.connectionMaker.makeConnection();
            ps = connection.prepareStatement("SELECT count(*) FROM users");

            rs = ps.executeQuery();
            rs.next();

            count = rs.getInt(1);
            return count;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(rs!=null){
                try{
                    rs.close();
                }catch (SQLException e){

                }
            }
            if(ps!=null){
                try{
                    ps.close();
                }catch (SQLException e){

                }
            }
            if(connection!=null){
                try{
                    connection.close();
                }catch (SQLException e){

                }
            }
        }
    }
}
