package model.dao;

import model.Credential;
import model.dao.inter.CredentialDAO_Interface;
import org.apache.log4j.Logger;
import utils.SecurePassword;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.Calendar;
import java.util.UUID;

public class CredentialDAO implements CredentialDAO_Interface {

    //    @Resource(lookup = "jdbc/awd_db")
    private DataSource dataSource;
    //
    private InitialContext ctx;

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
    public Credential checkLogin(String email, String password) {
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
                        resultSet.getTimestamp(Credential.EXPIRY),
                        resultSet.getInt(Credential.USER_TYPE),
                        resultSet.getInt(Credential.USER_FK)
                );
            }
            conn.close();

            String newHashedPsw = SecurePassword.getSHA1Password(password, credential.getSalt());
            if (newHashedPsw.equals(credential.getHashedPassword()))
                return credential;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String startSession(int id) {
        //il token va salvato nella base di dati per controlli successivi
        String token = UUID.randomUUID().toString().replace("-", "");

        int status = 0;

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, 30);
        Timestamp timestamp = new Timestamp(cal.getTimeInMillis());


        String query = "UPDATE credential SET token = ?, expiry = ?;";
        PreparedStatement preparedStatement;
        try (Connection conn = dataSource.getConnection()) {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, token);
            preparedStatement.setTimestamp(2, timestamp);
            status = preparedStatement.executeUpdate();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (status == 1) return token;
        else
            return null;
    }

    @Override
    public void endSession(String token) {
        String query = "UPDATE credential SET token = NULL, expiry = 0 WHERE token = ?;";
        PreparedStatement preparedStatement;
        try (Connection conn = dataSource.getConnection()) {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, token);
            preparedStatement.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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



