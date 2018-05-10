package model.dao;

import model.Credential;
import model.dao.inter.CredentialDAO_Interface;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CredentialDAO implements CredentialDAO_Interface {

    @Resource(name = "jdbc/UsersDB")
    private DataSource dataSource;

    public CredentialDAO() {
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

            String newHashedPsw = get_SHA_1_SecurePassword(password, credential.getSalt());
            if (newHashedPsw.equals(credential.getHashedPassword()))
                return true;
            else return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static String get_SHA_1_SecurePassword(String passwordToHash, byte[] salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(salt);
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
}


//    //Add salt
//    private static byte[] getSalt() throws NoSuchAlgorithmException {
//        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
//        byte[] salt = new byte[16];
//        sr.nextBytes(salt);
//        return salt;
//    }
//}
