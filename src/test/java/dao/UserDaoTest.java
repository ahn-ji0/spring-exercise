package dao;

import connectionmaker.AWSConnectionMaker;
import connectionmaker.ConnectionMaker;
import domain.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {

    @Autowired
    ApplicationContext context;

    UserDao userDao;
    User user1;
    User user2;
    User user3;

    @BeforeEach
    void setUp() throws SQLException, ClassNotFoundException {
        this.userDao = context.getBean("AWSUserDao", UserDao.class);
        this.user1 = new User("1", "Choco", "913");
        this.user2 = new User("2", "Chicon", "214");
        this.user3 = new User("3", "Ppang", "1210");
        userDao.deleteAll();
    }

    @Test
    @DisplayName("insert and select")
    void insertAndSelect() throws SQLException, ClassNotFoundException {

        assertEquals(0, userDao.getCount());

        userDao.insert(user1);
        assertEquals(1, userDao.getCount());

        User user = userDao.selectId("1");
        assertEquals(user1.getName(), user.getName());
        assertEquals(user1.getPassword(), user.getPassword());
    }
    @Test
    void getAll(){
        List<User> users = userDao.getAll();
        assertEquals(0,users.size());

        userDao.insert(user1);
        userDao.insert(user2);
        userDao.insert(user3);

        users = userDao.getAll();
        assertEquals(3,users.size());

    }
    @Test
    void count() throws SQLException, ClassNotFoundException {

        userDao.insert(user1);
        assertEquals(1, userDao.getCount());

        userDao.insert(user2);
        assertEquals(2, userDao.getCount());

        userDao.insert(user3);
        assertEquals(3, userDao.getCount());
    }

    @Test
    @DisplayName("selectId")
    void selectId() throws SQLException, ClassNotFoundException {
        userDao.insert(user1);
        assertThrows(EmptyResultDataAccessException.class, () -> {
            userDao.selectId("5");
        });
    }
}