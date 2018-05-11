package model.dao.inter;

import model.Credential;

public interface CredentialDAO_Interface {
    public Credential checkLogin(String email, String password);

    String startSession(int id);
}
