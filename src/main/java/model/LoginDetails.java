package model;

import model.dao.StudentDAO;

public class LoginDetails {
    private String email;
    private String password;

    public LoginDetails(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LoginDetails() {
        this.email = "";
        this.password = "";
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    public boolean isValid() {
        return new StudentDAO().checkLogin(this.getEmail(), this.getPassword());
    }        
}
