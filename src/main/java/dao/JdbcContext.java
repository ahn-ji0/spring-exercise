package dao;

import statement.StatementStrategy;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcContext {
    private final DataSource dataSource;

    public JdbcContext(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void executeSql(String query){
        workWithStatementStrategy(new StatementStrategy() {
            @Override
            public PreparedStatement makeStatement(Connection c) throws SQLException {
                return c.prepareStatement(query);
            }
        });
    }

    public void workWithStatementStrategy(StatementStrategy stmt){
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = dataSource.getConnection();
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

}
