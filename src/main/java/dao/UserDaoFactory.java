package dao;

import connectionmaker.AWSConnectionMaker;
import connectionmaker.ConnectionMaker;
import domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
@Configuration
public class UserDaoFactory {

    @Bean
    public UserDao AWSUserDao(){
        AWSConnectionMaker awsConnectionMaker = new AWSConnectionMaker();
        UserDao userDao = new UserDao(awsConnectionMaker);
        return userDao;
    }
}
