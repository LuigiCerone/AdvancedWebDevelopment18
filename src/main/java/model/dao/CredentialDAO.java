package model.dao;

import model.Credential;
import model.dao.inter.CredentialDAO_Interface;
import org.apache.log4j.Logger;
import utils.SecurePassword;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CredentialDAO implements CredentialDAO_Interface {

    //    @Resource(name = "jdbc/awd_db")
//    private DataSource dataSource;
//
    private InitialContext ctx;
    private DataSource dataSource;

    final static Logger logger = Logger.getLogger(CredentialDAO.class);

    public CredentialDAO() {
        try {
            ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/awd_db");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkLogin(String email, String password) {
        Credential credential = null;
        String query = "SELECT * FROM credential WHERE credential.email = ?;";
        PreparedStatement preparedStatement;
        try (Connection conn = dataSource.getConnection()) {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                credential = new Credential(
                        resultSet.getInt(Credential.ID),
                        resultSet.getString(Credential.EMAIL),
                        resultSet.getBytes(Credential.SALT),
                        resultSet.getString(Credential.HASHED_PSW),
                        resultSet.getTimestamp(Credential.CREATED_AT),
                        resultSet.getTimestamp(Credential.LAST_SEEN),
                        resultSet.getString(Credential.TOKEN),
                        resultSet.getInt(Credential.TOKEN),
                        resultSet.getInt(Credential.USER_TYPE),
                        resultSet.getInt(Credential.USER_FK)
                );
            }
            conn.close();

            String newHashedPsw = SecurePassword.getSHA1Password(password, credential.getSalt());
            if (newHashedPsw.equals(credential.getHashedPassword()))
                return true;
            else return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void insert(Credential credential, String password) {
        String query = "INSERT INTO credential VALUES (NULL, ?, ?, ?, NOW(), NOW(), NULL,0,?,?);";
        PreparedStatement preparedStatement;

        try (Connection conn = dataSource.getConnection()) {
            preparedStatement = conn.prepareStatement(query);

            preparedStatement.setString(1, credential.getEmail());
            byte[] salt = SecurePassword.getSalt();
            preparedStatement.setBytes(2, salt);
            preparedStatement.setString(3, SecurePassword.getSHA1Password(password, salt));
            preparedStatement.setInt(4, 0);
            preparedStatement.setInt(5, 1);
            preparedStatement.execute();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void test() {
        logger.debug("Received a request");
        try (Connection conn = dataSource.getConnection()) {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



