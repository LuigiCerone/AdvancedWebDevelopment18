package utils;

import org.apache.log4j.Logger;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SecurePassword {
    final static Logger logger = Logger.getLogger(SecurePassword.class);

    /**
     * Method used to generate the hash of a given password using a specific salt and SHA-1 algorithm.
     *
     * @param passwordToHash
     * @param salt
     * @return hash of the password.
     */
    public static String getSHA1Password(String passwordToHash, byte[] salt) {
        String generatedPassword = null;
        try {
            MessageDigest msdDigest = MessageDigest.getInstance("SHA-1");
            msdDigest.update(salt);
            generatedPassword = DatatypeConverter.printHexBinary(msdDigest.digest(passwordToHash.getBytes("UTF-8")));
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            logger.error(null, e);
        }
        return generatedPassword;
    }

    /**
     * Method used to generate a random salt.
     *
     * @return salt.
     * @throws NoSuchAlgorithmException
     */
    public static byte[] getSalt() {
        SecureRandom sr = null;
        byte[] salt = new byte[0];
        try {
            sr = SecureRandom.getInstance("SHA1PRNG");
            salt = new byte[16];
            sr.nextBytes(salt);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return salt;
    }
}
