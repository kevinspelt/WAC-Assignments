package nl.hu.v1wac.template.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserPostgresDaoImpl extends PostgresBaseDao implements UserDao {
    @Override
    public String findRoleForUser(String name, String pass) {
        String role = null;
        try (Connection conn = super.getConnection()){
            PreparedStatement stmt = conn.prepareStatement("" +
                    "SELECT role " +
                    "FROM useraccount " +
                    "WHERE username=? " +
                    "AND password=?");
            stmt.setString(1, name);
            stmt.setString(2, pass);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                role = rs.getString("role");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return role;
    }
}
