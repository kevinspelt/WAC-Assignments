package nl.hu.v1wac.template.persistence;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class PostgresBaseDao {
    public Connection getConnection() {
        Connection conn = null;
        try {
            InitialContext ic = new InitialContext();
            DataSource ds = (DataSource) ic.lookup("java:comp/env/jdbc/PostgresDS");
            conn = ds.getConnection();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
