package model.dao;

import database.Database;
import model.Student;
import model.dao.inter.StudentDAO_Interface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class StudentDAO implements StudentDAO_Interface {

    /**
     * Insert a student into the DB.
     *
     * @param student object that will be stored in the DB.
     * @return inseted ID if ok, -1 otherwise.
     */
    @Override
    public int insert(Student student) {
        String query = "INSERT INTO student VALUES (NULL,?,?,?,?,?,?,?,?,?,?,?,?);";
        PreparedStatement preparedStatement;
        int lastInsertedId = -1;

        try (Connection conn = Database.getDatasource().getConnection()) {
            preparedStatement = conn.prepareStatement(query);

            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setDate(3, student.getBirthDate());
            preparedStatement.setString(4, student.getBirthPlace());
            preparedStatement.setString(5, student.getBirthPlaceProvince());
            preparedStatement.setString(6, student.getResidencePlace());
            preparedStatement.setString(6, student.getResidencePlaceProvince());
            preparedStatement.setString(6, student.getCf());
            preparedStatement.setInt(6, student.getTelnumber());
            preparedStatement.setString(6, student.getUniversityLevel());
            preparedStatement.setString(6, student.getUniversityCourse());
            preparedStatement.setBoolean(6, student.isHandicap());

            lastInsertedId = preparedStatement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastInsertedId;
    }
}
