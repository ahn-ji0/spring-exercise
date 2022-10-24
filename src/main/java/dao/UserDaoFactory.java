package dao;

import connectionmaker.AWSConnectionMaker;
import connectionmaker.ConnectionMaker;
import domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Configuration
public class UserDaoFactory {

    @Bean
    public UserDao AWSUserDao(){
//        AWSConnectionMaker awsConnectionMaker = new AWSConnectionMaker();
//        UserDao userDao = new UserDao(awsConnectionMaker);

        UserDao userDao = new UserDao(awsDataSource());
        return userDao;
    }

    @Bean
    DataSource awsDataSource(){
        Map<String,String> env = System.getenv();

        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
        dataSource.setUrl(env.get("DB_HOST"));
        dataSource.setUsername(env.get("DB_USER"));
        dataSource.setPassword(env.get("DB_PASSWORD"));
        return dataSource;
    }
}
