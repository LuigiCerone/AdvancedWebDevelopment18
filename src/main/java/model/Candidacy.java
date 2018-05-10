package model;

public class Candidacy {
    private int id;
    private int internetshipFk;
    private int studentFk;
    private int status;
    private int numCFU;
    private String firstNameReferent;
    private String lastNameReferent;
    private String emailReferent;

    public Candidacy() {
        this.id = 0;
        this.internetshipFk = 0;
        this.studentFk = 0;
        this.status = 0;
        this.numCFU = 0;
        this.firstNameReferent = "";
        this.lastNameReferent = "";
        this.emailReferent = "";
    }

    public Candidacy(int id, int internetshipFk, int studentFk, int status, int numCFU, String firstNameReferent, String lastNameReferent, String emailReferent) {
        this.id = id;
        this.internetshipFk = internetshipFk;
        this.studentFk = studentFk;
        this.status = status;
        this.numCFU = numCFU;
        this.firstNameReferent = firstNameReferent;
        this.lastNameReferent = lastNameReferent;
        this.emailReferent = emailReferent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInternetshipFk() {
        return internetshipFk;
    }

    public void setInternetshipFk(int internetshipFk) {
        this.internetshipFk = internetshipFk;
    }

    public int getStudentFk() {
        return studentFk;
    }

    public void setStudentFk(int studentFk) {
        this.studentFk = studentFk;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getNumCFU() {
        return numCFU;
    }

    public void setNumCFU(int numCFU) {
        this.numCFU = numCFU;
    }

    public String getFirstNameReferent() {
        return firstNameReferent;
    }

    public void setFirstNameReferent(String firstNameReferent) {
        this.firstNameReferent = firstNameReferent;
    }

    public String getLastNameReferent() {
        return lastNameReferent;
    }

    public void setLastNameReferent(String lastNameReferent) {
        this.lastNameReferent = lastNameReferent;
    }

    public String getEmailReferent() {
        return emailReferent;
    }

    public void setEmailReferent(String emailReferent) {
        this.emailReferent = emailReferent;
    }
}
