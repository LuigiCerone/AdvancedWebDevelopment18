package model.dao;

import database.Database;
import model.Credential;
import model.Student;
import model.dao.inter.StudentDAO_Interface;

import java.sql.*;

public class StudentDAO implements StudentDAO_Interface {

    /**
     * Insert a student into the DB.
     *
     * @param student object that will be stored in the DB.
     * @return inseted ID if ok, -1 otherwise.
     */
    @Override
    public boolean insert(Student student) {
        String query = "INSERT INTO student VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);";
        PreparedStatement preparedStatement;
        int status = 0;

        try (Connection conn = Database.getDatasource().getConnection()) {
            preparedStatement = conn.prepareStatement(query);

            preparedStatement.setInt(1, student.getId());
            preparedStatement.setString(2, student.getFirstName());
            preparedStatement.setString(3, student.getLastName());
            preparedStatement.setDate(4, (Date) student.getBirthDate());
            preparedStatement.setString(5, student.getBirthPlace());
            preparedStatement.setString(6, student.getBirthPlaceProvince());
            preparedStatement.setString(7, student.getResidencePlace());
            preparedStatement.setString(8, student.getResidencePlaceProvince());
            preparedStatement.setString(9, student.getCf());
            preparedStatement.setInt(10, student.getTelnumber());
            preparedStatement.setString(11, student.getUniversityLevel());
            preparedStatement.setString(12, student.getUniversityCourse());
            preparedStatement.setBoolean(13, student.isHandicap());

            status = preparedStatement.executeUpdate();

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status == 1;
    }

    @Override
    public Student getStudentById(int id) {
        String query = "SELECT * FROM student WHERE id = ?;";
        PreparedStatement preparedStatement;
        Student student = null;

        try (Connection conn = Database.getDatasource().getConnection()) {
            preparedStatement = conn.prepareStatement(query);

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                student = new Student(resultSet.getInt(Student.ID),
                        resultSet.getString(Student.FIRST_NAME),
                        resultSet.getString(Student.LAST_NAME),
                        (java.util.Date) resultSet.getDate(Student.BIRTH_DATE),
                        resultSet.getString(Student.BIRTH_PLACE),
                        resultSet.getString(Student.BIRTH_PLACE_PROVINCE),
                        resultSet.getString(Student.RESIDENCE_PLACE),
                        resultSet.getString(Student.RESIDENCE_PLACE_PROVINCE),
                        resultSet.getString(Student.CF),
                        resultSet.getInt(Student.TEL),
                        resultSet.getString(Student.UNIVERSITY_LEVEL),
                        resultSet.getString(Student.UNIVERSITY_COURSE),
                        resultSet.getBoolean(Student.HANDICAP),
                        new Credential());
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }
}
