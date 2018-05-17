package model.dao;

import database.Database;
import model.Credential;
import model.dao.inter.CredentialDAO_Interface;
import org.apache.log4j.Logger;
import utils.SecurePassword;

import java.sql.*;

public class CredentialDAO implements CredentialDAO_Interface {
    final static Logger logger = Logger.getLogger(CredentialDAO.class);

    public CredentialDAO() {
    }

    @Override
    public Credential getCredentialFromEmailAndPassword(String email, String password) {
        Credential credential = null;
        String query = "SELECT * FROM credential WHERE credential.email = ?;";
        PreparedStatement preparedStatement;
        try (Connection conn = Database.getDatasource().getConnection()) {
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
                        resultSet.getInt(Credential.USER_TYPE));
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
    public boolean startSession(int id, String token, Timestamp timestamp) {
        int status = 0;

        String query = "UPDATE credential SET token = ?, expiry = ? WHERE id = ?;";
        PreparedStatement preparedStatement;
        try (Connection conn = Database.getDatasource().getConnection()) {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, token);
            preparedStatement.setTimestamp(2, timestamp);
            preparedStatement.setInt(3, id);
            status = preparedStatement.executeUpdate();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status == 1;
    }

    @Override
    public boolean endSession(String token) {
        int n = 0;
        String query = "UPDATE credential SET token = NULL, expiry = 0 WHERE token = ?;";
        PreparedStatement preparedStatement;
        try (Connection conn = Database.getDatasource().getConnection()) {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, token);
            n = preparedStatement.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return n == 1;
    }

    @Override
    public boolean checkEmailAvailable(String email) {
        String query = "SELECT COUNT(*) AS count FROM credential WHERE credential.email = ?;";
        PreparedStatement preparedStatement;
        int rows = 0;

        try (Connection conn = Database.getDatasource().getConnection()) {
            preparedStatement = conn.prepareStatement(query);

            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                rows = resultSet.getInt("count");
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows == 0;
    }

    @Override
    public int checkSessionActive(String cookieValue, Timestamp nowTimestamp) {
        String query = "SELECT id FROM credential WHERE credential.token = ? AND " +
                "(credential.expiry IS NOT NULL AND credential.expiry > ?);;";
        PreparedStatement preparedStatement;
        int id = -1;

        try (Connection conn = Database.getDatasource().getConnection()) {
            preparedStatement = conn.prepareStatement(query);

            preparedStatement.setString(1, cookieValue);
            preparedStatement.setTimestamp(2, nowTimestamp);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt(Credential.ID);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public int getUserTypeFromCredentialId(int idCredential) {
        String query = "SELECT user_type FROM credential WHERE credential.id = ?;";
        PreparedStatement preparedStatement;
        int type = 0;

        try (Connection conn = Database.getDatasource().getConnection()) {
            preparedStatement = conn.prepareStatement(query);

            preparedStatement.setInt(1, idCredential);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                type = resultSet.getInt(Credential.USER_TYPE);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return type;
    }

    @Override
    public int getUserIdByCookie(String cookie) {
        String query = "SELECT id FROM credential WHERE credential.token = ?;";
        PreparedStatement preparedStatement;
        int id = -1;

        try (Connection conn = Database.getDatasource().getConnection()) {
            preparedStatement = conn.prepareStatement(query);

            preparedStatement.setString(1, cookie);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt(Credential.ID);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public int insert(Credential credential, String passwordToHash) {
        String query = "INSERT INTO credential VALUES (NULL, ?, ?, ?, NOW(), NOW(), NULL,0,?);";
        PreparedStatement preparedStatement;
        int lastInsertedId = -1;

        try (Connection conn = Database.getDatasource().getConnection()) {
            preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, credential.getEmail());
            byte[] salt = SecurePassword.getSalt();
            preparedStatement.setBytes(2, salt);
            preparedStatement.setString(3, SecurePassword.getSHA1Password(passwordToHash, salt));
            preparedStatement.setInt(4, 0);
            lastInsertedId = preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                lastInsertedId = rs.getInt(1);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastInsertedId;
    }

    public void test() {
        logger.debug("Received a request");
        try (Connection conn = Database.getDatasource().getConnection()) {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



