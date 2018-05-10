package model;

public class Review {
    private int id;
    private int studentId;
    private String text;
    private boolean approved;

    public Review(){
        this.id = 0;
        this.studentId = 0;
        this.text = "";
        this.approved = false;
    }

    public Review(int id, int studentId, String text, boolean approved) {
        this.id = id;
        this.studentId = studentId;
        this.text = text;
        this.approved = approved;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
