package dao;

import connectionmaker.AWSConnectionMaker;
import connectionmaker.ConnectionMaker;
import domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    @Test
    @DisplayName("insert")
    void insertAndSelect() throws SQLException, ClassNotFoundException {
        ConnectionMaker connectionMaker = new AWSConnectionMaker();
        UserDao userDao = new UserDao(connectionMaker);
        User user = new User("101","영지안","1234");
        userDao.insert(user);

        User user2 = userDao.selectId("101");
        assertEquals("영지안",user2.getName());
    }
}