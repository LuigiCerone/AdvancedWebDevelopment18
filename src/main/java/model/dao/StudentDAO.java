package model.dao;

import database.Database;
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
    public int insert(Student student) {
        String query = "INSERT INTO student VALUES (NULL,?,?,?,?,?,?,?,?,?,?,?,?);";
        PreparedStatement preparedStatement;
        int lastInsertedId = -1;

        try (Connection conn = Database.getDatasource().getConnection()) {
            preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setDate(3, student.getBirthDate());
            preparedStatement.setString(4, student.getBirthPlace());
            preparedStatement.setString(5, student.getBirthPlaceProvince());
            preparedStatement.setString(6, student.getResidencePlace());
            preparedStatement.setString(7, student.getResidencePlaceProvince());
            preparedStatement.setString(8, student.getCf());
            preparedStatement.setInt(9, student.getTelnumber());
            preparedStatement.setString(10, student.getUniversityLevel());
            preparedStatement.setString(11, student.getUniversityCourse());
            preparedStatement.setBoolean(12, student.isHandicap());

            lastInsertedId = preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                lastInsertedId = rs.getInt(1);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastInsertedId;
    }
}
