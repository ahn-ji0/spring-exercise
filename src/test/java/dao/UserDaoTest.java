package dao;

import connectionmaker.AWSConnectionMaker;
import connectionmaker.ConnectionMaker;
import domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {

    @Autowired
    ApplicationContext context;

    @Test
    @DisplayName("insert and select")
    void insertAndSelect() throws SQLException, ClassNotFoundException {
//      UserDao userDao1 = new UserDaoFactory().AWSUserDao(); 대신에

        UserDao userDao = context.getBean("AWSUserDao",UserDao.class);

        userDao.deleteAll();

        User user = new User("200","영지안","1234");
        userDao.insert(user);

        User user2 = userDao.selectId("200");
        assertEquals("영지안",user2.getName());

        assertEquals(1,userDao.getCount());
    }

    @Test
    void count() throws SQLException, ClassNotFoundException {
        UserDao userDao = context.getBean("AWSUserDao",UserDao.class);

        userDao.deleteAll();

        User user1 = new User("1","Choco","913");
        User user2 = new User("2","Chicon","214");
        User user3 = new User("3","Ppang","1210");

        userDao.insert(user1);
        assertEquals(1,userDao.getCount());

        userDao.insert(user2);
        assertEquals(2,userDao.getCount());

        userDao.insert(user3);
        assertEquals(3,userDao.getCount());
    }
}