package model.dao.inter;

import model.Credential;

import java.sql.Timestamp;

public interface CredentialDAO_Interface {
//    public Credential checkLogin(String email, String password);

    Credential getCredentialFromEmailAndPassword(String email, String password);

//    String startSession(int id);

    boolean startSession(int id, String token, Timestamp timestamp);

    boolean endSession(String value);
}
