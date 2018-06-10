package model.dao.inter;

import model.Credential;

import java.sql.Timestamp;

public interface CredentialDAO_Interface {
//    public Credential checkLogin(String email, String password);

    Credential getCredentialFromEmailAndPassword(String email, String password);

//    String startSession(int id);

    boolean startSession(int id, String token, Timestamp timestamp);

    boolean endSession(String value);

    boolean checkEmailAvailable(String email);

    int checkSessionActive(String cookieValue, Timestamp nowTimestamp);

    int getUserTypeFromCredentialId(int idCredential);

    int getUserIdByCookie(String cookie);

    int insert(Credential credential, String passwordToHash);

    boolean updateLastSeen(int id);
}
