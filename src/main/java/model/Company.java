package model;

public class Company {
    private int id;
    private String socialRegion;
    private String legalAddress;
    private String piva;
    private String lawyerFirstName;
    private String lawyerLastName;
    private String personFirstName;
    private String personLastName;
    private int personTelNumber;
    private String personEmail;
    private String legalForum;
    private boolean active;
    private boolean visible;
    private Credential credential;


    public Company() {
        this.id = 0;
        this.socialRegion = "";
        this.legalAddress = "";
        this.piva = "";
        this.lawyerFirstName = "";
        this.lawyerLastName = "";
        this.personFirstName = "";
        this.personLastName = "";
        this.personTelNumber = 0;
        this.personEmail = "";
        this.legalForum = "";
        this.active = false;
        this.visible = false;
        this.credential = null;
    }

    public Company(int id, String socialRegion, String legalAddress, String piva, String lawyerFirstName, String lawyerLastName, String personFirstName, String personLastName, int personTelNumber, String personEmail, String legalForum, boolean active, boolean visible, Credential credential) {
        this.id = id;
        this.socialRegion = socialRegion;
        this.legalAddress = legalAddress;
        this.piva = piva;
        this.lawyerFirstName = lawyerFirstName;
        this.lawyerLastName = lawyerLastName;
        this.personFirstName = personFirstName;
        this.personLastName = personLastName;
        this.personTelNumber = personTelNumber;
        this.personEmail = personEmail;
        this.legalForum = legalForum;
        this.active = active;
        this.visible = visible;
        this.credential = credential;
    }

    public String getSocialRegion() {
        return socialRegion;
    }

    public void setSocialRegion(String socialRegion) {
        this.socialRegion = socialRegion;
    }

    public String getLegalAddress() {
        return legalAddress;
    }

    public void setLegalAddress(String legalAddress) {
        this.legalAddress = legalAddress;
    }

    public String getPiva() {
        return piva;
    }

    public void setPiva(String piva) {
        this.piva = piva;
    }

    public String getLawyerFirstName() {
        return lawyerFirstName;
    }

    public void setLawyerFirstName(String lawyerFirstName) {
        this.lawyerFirstName = lawyerFirstName;
    }

    public String getLawyerLastName() {
        return lawyerLastName;
    }

    public void setLawyerLastName(String lawyerLastName) {
        this.lawyerLastName = lawyerLastName;
    }

    public String getPersonFirstName() {
        return personFirstName;
    }

    public void setPersonFirstName(String personFirstName) {
        this.personFirstName = personFirstName;
    }

    public String getPersonLastName() {
        return personLastName;
    }

    public void setPersonLastName(String personLastName) {
        this.personLastName = personLastName;
    }

    public int getPersonTelNumber() {
        return personTelNumber;
    }

    public void setPersonTelNumber(int personTelNumber) {
        this.personTelNumber = personTelNumber;
    }

    public String getPersonEmail() {
        return personEmail;
    }

    public void setPersonEmail(String personEmail) {
        this.personEmail = personEmail;
    }

    public String getLegalForum() {
        return legalForum;
    }

    public void setLegalForum(String legalForum) {
        this.legalForum = legalForum;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Credential getCredential() {
        return credential;
    }

    public void setCredential(Credential credential) {
        this.credential = credential;
    }
}
