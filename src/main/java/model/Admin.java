package model;

public class Admin {
    private int id;
    private String firstName;
    private String lastName;
    private int telnumber;
    private Credential credential;

    public Admin() {
        this.id = 0;
        this.firstName = "";
        this.lastName = "";
        this.telnumber = 0;
        this.credential = null;
    }

    public Admin(int id, String firstName, String lastName, int telnumber, Credential credential) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.telnumber = telnumber;
        this.credential = credential;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getTelnumber() {
        return telnumber;
    }

    public void setTelnumber(int telnumber) {
        this.telnumber = telnumber;
    }

    public Credential getCredential() {
        return credential;
    }

    public void setCredential(Credential credential) {
        this.credential = credential;
    }
}
