package model;

import java.sql.Timestamp;

public class Internship {

    // DB fields.
    public static final String ID = "id";
    public static final String PLACE = "place";
    public static final String REMOTE = "remote";
    public static final String START_TIME = "start_time";
    public static final String END_TIME = "end_time";
    public static final String N_HOURS = "num_hours";
    public static final String GOALS = "goals";
    public static final String WORK_TYPE = "work_type";
    public static final String REFUND = "refund";
    public static final String FACILITATIONS = "facilitations";
    public static final String COMPANY_FK = "company_fk";

    private int id;
    private String place;
    private boolean remote;
    private Timestamp startTime;
    private Timestamp endTime;
    private int numberHour;
    private String goals;
    private String workType;
    private Float refund;
    private String facilitations;
    private int company_fk;

    public Internship() {
        this.id = 0;
        this.place = "";
        this.remote = false;
        this.startTime = null;
        this.endTime = null;
        this.numberHour = 0;
        this.goals = ""; // Array?
        this.workType = "";
        this.refund = 0.0f;
        this.facilitations = ""; // Array?
        this.company_fk = 0;
    }

    public Internship(String place, boolean remote, Timestamp startTime, Timestamp endTime, int numberHour, String goals, String workType, Float refund, String facilitations, int id, int company_fk) {
        this.id = id;
        this.place = place;
        this.remote = remote;
        this.startTime = startTime;
        this.endTime = endTime;
        this.numberHour = numberHour;
        this.goals = goals;
        this.workType = workType;
        this.refund = refund;
        this.facilitations = facilitations;
        this.company_fk = company_fk;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompany_fk() {
        return company_fk;
    }

    public void setCompany_fk(int company_fk) {
        this.company_fk = company_fk;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public boolean isRemote() {
        return remote;
    }

    public void setRemote(boolean remote) {
        this.remote = remote;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public int getNumberHour() {
        return numberHour;
    }

    public void setNumberHour(int numberHour) {
        this.numberHour = numberHour;
    }

    public String getGoals() {
        return goals;
    }

    public void setGoals(String goals) {
        this.goals = goals;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public Float getRefund() {
        return refund;
    }

    public void setRefund(Float refund) {
        this.refund = refund;
    }

    public String getFacilitations() {
        return facilitations;
    }

    public void setFacilitations(String facilitations) {
        this.facilitations = facilitations;
    }
}
