package by.audit.dao;

import by.audit.entity.Audit;
import by.audit.utils.ConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class AuditDao {

    private ConnectionManager connectionManager;

    @Autowired
    public AuditDao(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }
    private final String SAVE_AUDIT = """
            INSERT INTO audit.audit (username, method_name, time, action_result) VALUES (?,?,?,?)
            """;
    public Audit save(Audit audit){
        try(Connection connection = connectionManager.get();
            PreparedStatement statement = connection.prepareStatement(SAVE_AUDIT, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1,audit.getUsername());
            statement.setString(2,audit.getMethodName());
            statement.setTimestamp(3, Timestamp.valueOf(audit.getTime()));
            statement.setString(4,audit.getActionResult());
            statement.executeUpdate();
            ResultSet key = statement.getGeneratedKeys();
            key.next();
            audit.setId(key.getObject("id", Long.class));
            return audit;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}