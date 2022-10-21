package statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteAllStrategy implements StatementStrategy{

    @Override
    public PreparedStatement makeStatement(Connection c) throws SQLException {
        return c.prepareStatement("DELETE FROM users ");
    }
}
