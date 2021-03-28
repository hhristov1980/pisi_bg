package pisibg.model.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import pisibg.model.dto.UserWithoutPassDTO;
import pisibg.model.pojo.User;

import javax.persistence.Transient;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Component
public class UserDAO extends AbstractDAO {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    public void deleteUser(int id) throws SQLException {
        String sql = "UPDATE users SET email='deleted', password='deleted', first_name='deleted', last_name='deleted'," +
                " phone_number='deleted',address='deleted' , is_subscribed='0',deleted_at = ? WHERE id = ?;";
        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            statement.setInt(2, id);
            statement.executeUpdate();
        }
    }

    public int countAdmin() throws SQLException{
        String sql = "SELECT * FROM users WHERE is_admin = 1";
        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);){
            return statement.executeUpdate();
        }
    }
}
