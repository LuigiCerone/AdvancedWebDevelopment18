package model;

import model.dao.StudentDAO;

import java.sql.Timestamp;

public class Credential {
    private int id;
    private String email;
    private String salt;
    private String hashedPassword;
    private Timestamp createdAt;
    private Timestamp lastSeen;
    private String token;
    private int ttl;
    private int userType;
    private int userFk;

    public Credential() {
        this.id = 0;
        this.email = "";
        this.salt = "";
        this.hashedPassword = "";
        this.createdAt = null;
        this.lastSeen = null;
        this.token = "";
        this.ttl = 0;
        this.userType = 0;
        this.userFk = 0;
    }

    public Credential(Credential credential) {
        this.id = credential.getId();
        this.email = credential.getEmail();
        this.salt = credential.getSalt();
        this.hashedPassword = credential.getHashedPassword();
        this.createdAt = credential.getCreatedAt();
        this.lastSeen = credential.getLastSeen();
        this.token = credential.getToken();
        this.ttl = credential.getTtl();
        this.userType = credential.getUserType();
        this.userFk = credential.getUserFk();
    }

    public Credential(int id, String email, String salt, String hashedPassword, Timestamp createdAt, Timestamp lastSeen, String token, int ttl, int userType, int userFk) {
        this.id = id;
        this.email = email;
        this.salt = salt;
        this.hashedPassword = hashedPassword;
        this.createdAt = createdAt;
        this.lastSeen = lastSeen;
        this.token = token;
        this.ttl = ttl;
        this.userType = userType;
        this.userFk = userFk;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(Timestamp lastSeen) {
        this.lastSeen = lastSeen;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getUserFk() {
        return userFk;
    }

    public void setUserFk(int userFk) {
        this.userFk = userFk;
    }

    public boolean isValid() {
        return new StudentDAO().checkLogin(this.getEmail(), this.getHashedPassword());
    }
}
