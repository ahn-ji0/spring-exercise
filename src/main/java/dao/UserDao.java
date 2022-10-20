package dao;

import connectionmaker.ConnectionMaker;
import domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private ConnectionMaker connectionMaker;

    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void insert(User user) throws SQLException, ClassNotFoundException {
        Connection connection = this.connectionMaker.makeConnection();
        PreparedStatement ps = connection.prepareStatement("INSERT INTO users(id,name,password) VALUES(?,?,?)");

        ps.setString(1,user.getId());
        ps.setString(2,user.getName());
        ps.setString(3,user.getPassword());

        int status = ps.executeUpdate();
        System.out.println("Status: "+status);

        ps.close();
        connection.close();
    }

    public User selectId(String id) throws SQLException, ClassNotFoundException {
        Connection connection = this.connectionMaker.makeConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT id,name,password FROM users WHERE id = ?");
        ps.setString(1,id);

        ResultSet rs = ps.executeQuery();
        rs.next();

        User user = new User(rs.getString("id"),rs.getString("name"),rs.getString("password"));

        rs.close();
        ps.close();
        connection.close();

        return user;
    }

    public void deleteAll() throws SQLException, ClassNotFoundException {
        Connection connection = this.connectionMaker.makeConnection();
        PreparedStatement ps = connection.prepareStatement("DELETE FROM users");

        int status = ps.executeUpdate();
        System.out.println("Status: "+status);

        ps.close();
        connection.close();
    }

    public int getCount() throws SQLException, ClassNotFoundException {
        Connection connection = this.connectionMaker.makeConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT count(*) FROM users");

        ResultSet rs = ps.executeQuery();
        rs.next();

        int count = rs.getInt(1);

        rs.close();
        ps.close();
        connection.close();

        return count;
    }
}
