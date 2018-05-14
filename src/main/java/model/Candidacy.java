package model;

import java.sql.Date;

public class Candidacy {

    //DB fields.
    public final static String ID = "id";
    public final static String INTERNSHIP_FK = "internship_fk";
    public final static String STUDENT_FK = "student_fk";
    public final static String STATUS = "status";
    public final static String N_CFU = "n_cfu";
    public final static String FIRST_NAME_REFERENT = "first_name_referent";
    public final static String LAST_NAME_REFERENT = "last_name_referent";
    public final static String EMAIL_REFERENT = "email_referent";
    public final static String START_DATE = "start_date";
    public final static String END_DATE = "end_date";

    private int id;
    private int internshipFk;
    private int studentFk;
    private int status;
    private int numCFU;
    private String firstNameReferent;
    private String lastNameReferent;
    private String emailReferent;
    private Date startDate;
    private Date endDate;

    public Candidacy() {
        this.id = 0;
        this.internshipFk = 0;
        this.studentFk = 0;
        this.status = 0;
        this.numCFU = 0;
        this.firstNameReferent = "";
        this.lastNameReferent = "";
        this.emailReferent = "";
        this.startDate = null;
        this.endDate = null;
    }

    public Candidacy(int id, int interneshipFk, int studentFk, int status, int numCFU, String firstNameReferent, String lastNameReferent, String emailReferent, Date startDate, Date endDate) {
        this.id = id;
        this.internshipFk = interneshipFk;
        this.studentFk = studentFk;
        this.status = status;
        this.numCFU = numCFU;
        this.firstNameReferent = firstNameReferent;
        this.lastNameReferent = lastNameReferent;
        this.emailReferent = emailReferent;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInternshipFk() {
        return internshipFk;
    }

    public void setInternshipFk(int internshipFk) {
        this.internshipFk = internshipFk;
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
