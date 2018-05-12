package model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.sql.Date;

public class Student {

    // DB fields name.
    private static final String ID = "id";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String BIRTH_DATE = "birth_date";
    private static final String BIRTH_PLACE = "birth_place";
    private static final String BIRTH_PLACE_PROVINCE = "birth_place_province";
    private static final String RESIDENCE_PLACE = "residence_place";
    private static final String RESIDENCE_PLACE_PROVINCE = "residence_place_province";
    private static final String CF = "cf";
    private static final String TEL = "telnumber";
    private static final String UNIVERSITY_LEVEL = "university_level";
    private static final String UNIVERSITY_COURSE = "university_course";
    private static final String HANDICAP = "handicap";


    private int id;
    private String firstName;
    private String lastName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm a z")
    private Date birthDate;

    private String birthPlace;
    private String birthPlaceProvince;
    private String residencePlace;
    private String residencePlaceProvince;
    private String cf;
    private int telnumber;
    private String universityLevel;
    private String universityCourse;
    private boolean handicap;

    @JsonUnwrapped
    private Credential credential;


    public Student() {
        this.id = 0;
        this.firstName = "";
        this.lastName = "";
        this.birthDate = null;
        this.birthPlace = "";
        this.birthPlaceProvince = "";
        this.residencePlace = "";
        this.residencePlaceProvince = "";
        this.cf = "";
        this.telnumber = 0;
        this.universityLevel = "";
        this.universityCourse = "";
        this.handicap = false;
        this.credential = new Credential();
    }

    public Student(int id, String firstName, String lastName, Date birthDate, String birthPlace, String birthPlaceProvince, String residencePlace, String residencePlaceProvince, String cf, int telnumber, String universityLevel, String universityCourse, boolean handicap, String email, String password, Credential credential) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.birthPlace = birthPlace;
        this.birthPlaceProvince = birthPlaceProvince;
        this.residencePlace = residencePlace;
        this.residencePlaceProvince = residencePlaceProvince;
        this.cf = cf;
        this.telnumber = telnumber;
        this.universityLevel = universityLevel;
        this.universityCourse = universityCourse;
        this.handicap = handicap;
        this.credential = new Credential(credential);
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getBirthPlaceProvince() {
        return birthPlaceProvince;
    }

    public void setBirthPlaceProvince(String birthPlaceProvince) {
        this.birthPlaceProvince = birthPlaceProvince;
    }

    public String getResidencePlace() {
        return residencePlace;
    }

    public void setResidencePlace(String residencePlace) {
        this.residencePlace = residencePlace;
    }

    public String getResidencePlaceProvince() {
        return residencePlaceProvince;
    }

    public void setResidencePlaceProvince(String residencePlaceProvince) {
        this.residencePlaceProvince = residencePlaceProvince;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public int getTelnumber() {
        return telnumber;
    }

    public void setTelnumber(int telnumber) {
        this.telnumber = telnumber;
    }

    public String getUniversityLevel() {
        return universityLevel;
    }

    public void setUniversityLevel(String universityLevel) {
        this.universityLevel = universityLevel;
    }

    public String getUniversityCourse() {
        return universityCourse;
    }

    public void setUniversityCourse(String universityCourse) {
        this.universityCourse = universityCourse;
    }

    public boolean isHandicap() {
        return handicap;
    }

    public void setHandicap(boolean handicap) {
        this.handicap = handicap;
    }

    public Credential getCredential() {
        return credential;
    }

    public void setCredential(Credential credential) {
        this.credential = credential;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", birthPlace='" + birthPlace + '\'' +
                ", birthPlaceProvince='" + birthPlaceProvince + '\'' +
                ", residencePlace='" + residencePlace + '\'' +
                ", residencePlaceProvince='" + residencePlaceProvince + '\'' +
                ", cf='" + cf + '\'' +
                ", telnumber=" + telnumber +
                ", universityLevel='" + universityLevel + '\'' +
                ", universityCourse='" + universityCourse + '\'' +
                ", handicap=" + handicap +
                ", credential=" + credential +
                '}';
    }
}
