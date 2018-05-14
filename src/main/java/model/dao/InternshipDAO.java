package model.dao;

import database.Database;
import model.Internship;
import model.dao.inter.InternshipDAO_Interface;
import org.apache.log4j.Logger;

import java.sql.*;

public class InternshipDAO implements InternshipDAO_Interface {
    final static Logger logger = Logger.getLogger(InternshipDAO.class);

    public InternshipDAO() {
    }

    /**
     * Insert an internship into the DB.
     *
     * @param internship object that will be stored in the DB.
     * @return inserted ID if ok, -1 otherwise.
     */
    @Override
    public int insert(Internship internship) {
        String query = "INSERT INTO internship VALUES (NULL,?,?,?,?,?,?,?,?,?,?);";
        PreparedStatement preparedStatement;
        int lastInsertedId = -1;

        try (Connection conn = Database.getDatasource().getConnection()) {
            preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, internship.getPlace());
            preparedStatement.setBoolean(2, internship.isRemote());
            preparedStatement.setTimestamp(3, internship.getStartTime());
            preparedStatement.setTimestamp(4, internship.getEndTime());
            preparedStatement.setInt(5, internship.getNumberHour());
            preparedStatement.setString(6, internship.getGoals());
            preparedStatement.setString(7, internship.getWorkType());
            preparedStatement.setFloat(8, internship.getRefund());
            preparedStatement.setString(9, internship.getFacilitations());
            preparedStatement.setInt(10, internship.getCompany_fk());

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
