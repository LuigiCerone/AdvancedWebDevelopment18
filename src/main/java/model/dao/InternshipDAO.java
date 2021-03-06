package model.dao;

import database.Database;
import model.Internship;
import model.dao.inter.InternshipDAO_Interface;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.LinkedList;

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
        String query = "INSERT INTO internship VALUES (NULL,?,?,?,?,?,?,?,?,?,?,?,?);";
        PreparedStatement preparedStatement;
        int lastInsertedId = -1;

        try (Connection conn = Database.getDatasource().getConnection()) {
            preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, internship.getPlace());
            preparedStatement.setBoolean(2, internship.isRemote());
            preparedStatement.setTime(3, internship.getStartTime());
            preparedStatement.setTime(4, internship.getEndTime());
            preparedStatement.setInt(5, internship.getNumberHour());
            preparedStatement.setString(6, internship.getGoals());
            preparedStatement.setString(7, internship.getWorkType());
            preparedStatement.setFloat(8, internship.getRefund());
            preparedStatement.setString(9, internship.getFacilitations());
            preparedStatement.setInt(10, internship.getCompany_fk());
            preparedStatement.setDate(11, internship.getStartDate());
            preparedStatement.setDate(12, internship.getEndDate());

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

    @Override
    public LinkedList<Internship> getAllInternships() {
        LinkedList<Internship> list = new LinkedList<>();
        String query = "SELECT  * FROM internship;";

        PreparedStatement preparedStatement;
        try (Connection conn = Database.getDatasource().getConnection()) {
            preparedStatement = conn.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Internship internship = new Internship(
                        resultSet.getInt(Internship.ID),
                        resultSet.getString(Internship.PLACE),
                        resultSet.getBoolean(Internship.REMOTE),
                        resultSet.getTime(Internship.START_TIME),
                        resultSet.getTime(Internship.END_TIME),
                        resultSet.getInt(Internship.N_HOURS),
                        resultSet.getString(Internship.GOALS),
                        resultSet.getString(Internship.WORK_TYPE),
                        resultSet.getFloat(Internship.REFUND),
                        resultSet.getString(Internship.FACILITATIONS),
                        resultSet.getInt(Internship.COMPANY_FK),
                        resultSet.getDate(Internship.START_DATE),
                        resultSet.getDate(Internship.END_DATE)
                );
                list.add(internship);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    @Override
    public Internship getInternshipFromID(int id) {
        Internship internship = null;
        String query = "SELECT * FROM internship WHERE id = ?;";
        PreparedStatement preparedStatement;
        try (Connection conn = Database.getDatasource().getConnection()) {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                internship = new Internship(resultSet.getInt(Internship.ID),
                        resultSet.getString(Internship.PLACE),
                        resultSet.getBoolean(Internship.REMOTE),
                        resultSet.getTime(Internship.START_TIME),
                        resultSet.getTime(Internship.END_TIME),
                        resultSet.getInt(Internship.N_HOURS),
                        resultSet.getString(Internship.GOALS),
                        resultSet.getString(Internship.WORK_TYPE),
                        resultSet.getFloat(Internship.REFUND),
                        resultSet.getString(Internship.FACILITATIONS),
                        resultSet.getInt(Internship.COMPANY_FK),
                        resultSet.getDate(Internship.START_DATE),
                        resultSet.getDate(Internship.END_DATE)
                );
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return internship;
    }

    @Override
    public LinkedList<Internship> getIntershipByCompanyID(int idCompany) {
        LinkedList<Internship> internshipList = new LinkedList<>();
        String query = "SELECT * FROM internship WHERE  company_fk = ?;";
        PreparedStatement preparedStatement;
        try (Connection conn = Database.getDatasource().getConnection()) {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, idCompany);


            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Internship internship = new Internship(
                        resultSet.getInt(Internship.ID),
                        resultSet.getString(Internship.PLACE),
                        resultSet.getBoolean(Internship.REMOTE),
                        resultSet.getTime(Internship.START_TIME),
                        resultSet.getTime(Internship.END_TIME),
                        resultSet.getInt(Internship.N_HOURS),
                        resultSet.getString(Internship.GOALS),
                        resultSet.getString(Internship.WORK_TYPE),
                        resultSet.getFloat(Internship.REFUND),
                        resultSet.getString(Internship.FACILITATIONS),
                        resultSet.getInt(Internship.COMPANY_FK),
                        resultSet.getDate(Internship.START_DATE),
                        resultSet.getDate(Internship.END_DATE)
                );
                internshipList.add(internship);

            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return internshipList;
    }

    @Override
    public LinkedList<Internship> getInternshipByRange(int first, int last) {
        LinkedList<Internship> internshipList = new LinkedList<>();

        String query = null;
        if (last != 0)
            query = "SELECT * FROM internship WHERE  id >= ? && id <= ?;";
        else
            query = "SELECT * FROM internship WHERE  id >= ?;";
        PreparedStatement preparedStatement;

        try (Connection conn = Database.getDatasource().getConnection()) {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, first);

            if (last != 0)
                preparedStatement.setInt(2, last);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Internship internship = new Internship(
                        resultSet.getInt(Internship.ID),
                        resultSet.getString(Internship.PLACE),
                        resultSet.getBoolean(Internship.REMOTE),
                        resultSet.getTime(Internship.START_TIME),
                        resultSet.getTime(Internship.END_TIME),
                        resultSet.getInt(Internship.N_HOURS),
                        resultSet.getString(Internship.GOALS),
                        resultSet.getString(Internship.WORK_TYPE),
                        resultSet.getFloat(Internship.REFUND),
                        resultSet.getString(Internship.FACILITATIONS),
                        resultSet.getInt(Internship.COMPANY_FK),
                        resultSet.getDate(Internship.START_DATE),
                        resultSet.getDate(Internship.END_DATE)
                );
                internshipList.add(internship);

            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return internshipList;
    }

    @Override
    public LinkedList<Internship> getInternshipByRangeByCompany(int companyId, int first, int last) {
        LinkedList<Internship> internshipList = new LinkedList<>();

        String query = null;
        if (last != 0)
            query = "SELECT * FROM internship WHERE  internship.company_fk = ? && id >= ? && id <= ?;";
        else
            query = "SELECT * FROM internship WHERE  internship.company_fk = ? && id >= ?;";
        PreparedStatement preparedStatement;

        try (Connection conn = Database.getDatasource().getConnection()) {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, companyId);
            preparedStatement.setInt(2, first);

            if (last != 0)
                preparedStatement.setInt(3, last);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Internship internship = new Internship(
                        resultSet.getInt(Internship.ID),
                        resultSet.getString(Internship.PLACE),
                        resultSet.getBoolean(Internship.REMOTE),
                        resultSet.getTime(Internship.START_TIME),
                        resultSet.getTime(Internship.END_TIME),
                        resultSet.getInt(Internship.N_HOURS),
                        resultSet.getString(Internship.GOALS),
                        resultSet.getString(Internship.WORK_TYPE),
                        resultSet.getFloat(Internship.REFUND),
                        resultSet.getString(Internship.FACILITATIONS),
                        resultSet.getInt(Internship.COMPANY_FK),
                        resultSet.getDate(Internship.START_DATE),
                        resultSet.getDate(Internship.END_DATE)
                );
                internshipList.add(internship);

            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return internshipList;
    }
}
