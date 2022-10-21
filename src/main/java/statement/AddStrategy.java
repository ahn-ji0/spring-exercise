package statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddStrategy implements StatementStrategy{

    @Override
    public PreparedStatement makeStatement(Connection c) throws SQLException {
        return c.prepareStatement("INSERT INTO users(id,name,password) VALUES(?,?,?");
    }
}
