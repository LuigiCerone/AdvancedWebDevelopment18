package model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;
import java.util.Arrays;

public class Credential {

    // DB fields names.
    public final static String ID = "id";
    public final static String EMAIL = "email";
    public final static String SALT = "salt";
    public final static String HASHED_PSW = "hashed_psw";
    public final static String CREATED_AT = "created_at";
    public final static String LAST_SEEN = "last_seen";
    public final static String TOKEN = "token";
    public final static String EXPIRY = "expiry";
    public final static String USER_TYPE = "user_type";
    public final static String USER_FK = "user_fk";


    @JsonIgnore
    private int id;
    private String email;

    @JsonIgnore
    private byte[] salt;
    @JsonIgnore
    private String hashedPassword;
    private Timestamp createdAt;
    private Timestamp lastSeen;
    @JsonIgnore
    private String token;
    @JsonIgnore
    private Timestamp expiry;
    private int userType;
    private int userFk;
    private String password;

    public Credential() {
        this.id = 0;
        this.email = "";
        this.salt = null;
        this.hashedPassword = "";
        this.createdAt = null;
        this.lastSeen = null;
        this.token = "";
        this.expiry = null;
        this.userType = 0;
        this.userFk = 0;
        this.password = "";
    }

    public Credential(Credential credential) {
        this.id = credential.getId();
        this.email = credential.getEmail();
        this.salt = credential.getSalt();
        this.hashedPassword = credential.getHashedPassword();
        this.createdAt = credential.getCreatedAt();
        this.lastSeen = credential.getLastSeen();
        this.token = credential.getToken();
        this.expiry = credential.getExpiry();
        this.userType = credential.getUserType();
        this.userFk = credential.getUserFk();
        this.password = credential.getPassword();
    }

    public Credential(int id, String email, byte[] salt, String hashedPassword, Timestamp createdAt, Timestamp lastSeen, String token, Timestamp expiry, int userType, int userFk) {
        this.id = id;
        this.email = email;
        this.salt = salt;
        this.hashedPassword = hashedPassword;
        this.createdAt = createdAt;
        this.lastSeen = lastSeen;
        this.token = token;
        this.expiry = expiry;
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

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
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

    public Timestamp getExpiry() {
        return expiry;
    }

    public void setExpiry(Timestamp expiry) {
        this.expiry = expiry;
    }

    @JsonIgnore
    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    @JsonIgnore
    public int getUserFk() {
        return userFk;
    }

    public void setUserFk(int userFk) {
        this.userFk = userFk;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Credential{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", salt=" + Arrays.toString(salt) +
                ", hashedPassword='" + hashedPassword + '\'' +
                ", createdAt=" + createdAt +
                ", lastSeen=" + lastSeen +
                ", token='" + token + '\'' +
                ", expiry=" + expiry +
                ", userType=" + userType +
                ", userFk=" + userFk +
                ", password='" + password + '\'' +
                '}';
    }
}
