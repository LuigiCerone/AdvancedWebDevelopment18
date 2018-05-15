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

    /**
     * Method used to get internships filtered by certain criteria.
     *
     * @param i filtering type, 1=city, 2=min, 3=max.
     * @param n name of the city or number for min/max.
     * @return list of internships.
     */
    @Override
    public LinkedList<Internship> getInternshipfilteredByCriteria(int i, String n) {
        LinkedList<Internship> list = new LinkedList<>();
        String query = null;

        // num is used to store the int value of the string param n.
        int num = 0;

        switch (i) {
            case 1: {
                query = "SELECT * FROM internship WHERE place = ?;";
                break;
            }
            case 2: {
                query = "SELECT * FROM internship WHERE num_hours >= ?;";
                num = Integer.valueOf(n);
                break;
            }
            case 3: {
                query = "SELECT * FROM internship WHERE num_hours <= ?;";
                num = Integer.valueOf(n);
                break;
            }
            default: {
                logger.error("Error");
            }
        }
    }

//    public Internship getInternshipByTwoDate(Timestamp t1, Timestamp t2){
//        Internship internship = null;
//        String query="SELECT * FROM internship WHERE start_time => ? AND internship.end_time <= ?;";
//        PreparedStatement preparedStatement;
//        try (Connection conn = Database.getDatasource().getConnection()) {
//            preparedStatement = conn.prepareStatement(query);
//            if (num == 0) {
//                preparedStatement.setString(1, n);
//            } else {
//                preparedStatement.setInt(1, num);
//            }
//
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                Internship internship = new Internship(
//                        resultSet.getInt(Internship.ID),
//                        resultSet.getString(Internship.PLACE),
//                        resultSet.getBoolean(Internship.REMOTE),
//                        resultSet.getTime(Internship.START_TIME),
//                        resultSet.getTime(Internship.END_TIME),
//                        resultSet.getInt(Internship.N_HOURS),
//                        resultSet.getString(Internship.GOALS),
//                        resultSet.getString(Internship.WORK_TYPE),
//                        resultSet.getFloat(Internship.REFUND),
//                        resultSet.getString(Internship.FACILITATIONS),
//                        resultSet.getInt(Internship.COMPANY_FK),
//                        resultSet.getDate(Internship.START_DATE),
//                        resultSet.getDate(Internship.END_DATE)
//                );
//                list.add(internship);
//            }
//            conn.close();
//
//            return internship;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }


    public Internship getInternshipFromID(int id) {
        Internship internship = null;
        String query="SELECT * FROM internship WHERE id = ?;";
        PreparedStatement preparedStatement;
        try (Connection conn = Database.getDatasource().getConnection()) {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setTimestamp(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
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
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return internship;
    }

    public LinkedList<Internship> getIntershipByCompanyID(int m){
            LinkedList<Internship> internshipList = null;
            String query = "SELECT * FROM internship WHERE  company_fk = ?;";
            PreparedStatement preparedStatement;
            try (Connection conn = Database.getDatasource().getConnection()) {
                preparedStatement = conn.prepareStatement(query);
                preparedStatement.setTimestamp(1, m);

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
                    internshipList.add(internshipList);

                }
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return internshipList;
        }

}
