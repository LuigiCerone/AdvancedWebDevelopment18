package model.dao;

import database.Database;
import model.Internship;
import model.dao.inter.InternshipDAO_Interface;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

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

    public Internship getInternshipByDate(Timestamp t){
        Internship internship = null;
        String query="SELECT * FROM internship WHERE start_time => ?;";
        PreparedStatement preparedStatement;
        try (Connection conn = Database.getDatasource().getConnection()) {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setTimestamp(1, t);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                internship = new Internship(
                        resultSet.getInt(Internship.ID);
                resultSet.getString(Internship.PLACE);
                resultSet.getBoolean(Internship.REMOTE);
                resultSet.getTimestamp(Internship.START_TIME);
                resultSet.getTimestamp(Internship.END_TIME);
                resultSet.getInt(Internship.N_HOURS);
                resultSet.getString(Internship.GOALS);
                resultSet.getString(Internship.WORK_TYPE);
                resultSet.getFloat(Internship.REFUND);
                resultSet.getString(Internship.FACILITATIONS);
                );
            }
            conn.close();

            return internship;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Internship getInternshipByTwoDate(Timestamp t1, Timestamp t2){
        Internship internship = null;
        String query="SELECT * FROM internship WHERE start_time => ? AND internship.end_time <= ?;";
        PreparedStatement preparedStatement;
        try (Connection conn = Database.getDatasource().getConnection()) {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setTimestamp(1, t1);
            preparedStatement.setTimestamp(1, t2);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                internship = new Internship(
                        resultSet.getInt(Internship.ID);
                resultSet.getString(Internship.PLACE);
                resultSet.getBoolean(Internship.REMOTE);
                resultSet.getTimestamp(Internship.START_TIME);
                resultSet.getTimestamp(Internship.END_TIME);
                resultSet.getInt(Internship.N_HOURS);
                resultSet.getString(Internship.GOALS);
                resultSet.getString(Internship.WORK_TYPE);
                resultSet.getFloat(Internship.REFUND);
                resultSet.getString(Internship.FACILITATIONS);
                );
            }
            conn.close();

            return internship;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Internship getInternshipFromID(int id){
        Internship internship = null;
        String query="SELECT * FROM internship WHERE id = ?;";
        PreparedStatement preparedStatement;
        try (Connection conn = Database.getDatasource().getConnection()) {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setTimestamp(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                internship = new Internship(
                        resultSet.getInt(Internship.ID);
                resultSet.getString(Internship.PLACE);
                resultSet.getBoolean(Internship.REMOTE);
                resultSet.getTimestamp(Internship.START_TIME);
                resultSet.getTimestamp(Internship.END_TIME);
                resultSet.getInt(Internship.N_HOURS);
                resultSet.getString(Internship.GOALS);
                resultSet.getString(Internship.WORK_TYPE);
                resultSet.getFloat(Internship.REFUND);
                resultSet.getString(Internship.FACILITATIONS);
                );
            }
            conn.close();

            return internship;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<Internship> getIntershipByCompanyID(int id) {
        List<Internship> internshipList = null;
        String query="SELECT * FROM internship WHERE  company_fk = ?;";
        PreparedStatement preparedStatement;
        try (Connection conn = Database.getDatasource().getConnection()) {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setTimestamp(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                internshipList = new LinkedList<Internship>(
                resultSet.getString(Internship.PLACE),
                resultSet.getBoolean(Internship.REMOTE),
                resultSet.getTimestamp(Internship.START_TIME),
                resultSet.getTimestamp(Internship.END_TIME),
                resultSet.getInt(Internship.N_HOURS),
                resultSet.getString(Internship.GOALS),
                resultSet.getString(Internship.WORK_TYPE),
                resultSet.getFloat(Internship.REFUND),
                resultSet.getString(Internship.FACILITATIONS),
                );
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return internshipList;
    }
}
