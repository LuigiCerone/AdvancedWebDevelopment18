package model.dao;

import database.Database;
import model.Candidacy;
import model.Student;
import model.dao.inter.CandidacyDAO_Interface;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class CandidacyDAO implements CandidacyDAO_Interface {
    final static Logger logger = Logger.getLogger(CandidacyDAO.class);

    @Override
    public List<Candidacy> getAllActiveCandidacyForCompany(Timestamp now, int idCompany) {
        List<Candidacy> list = new LinkedList<>();

        String query = "SELECT * FROM candidacy JOIN internship ON candidacy.id = internship.company_fk WHERE " +
                " internship.company_fk = ? AND end_time < ? AND start_time > ? AND candidacy.status = 2;";
        // 2 means accepted.
        PreparedStatement preparedStatement;
        Student student = null;

        try (Connection conn = Database.getDatasource().getConnection()) {
            preparedStatement = conn.prepareStatement(query);

            preparedStatement.setInt(1, idCompany);
            preparedStatement.setTimestamp(2, now);
            preparedStatement.setTimestamp(3, now);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Candidacy candidacy = new Candidacy(
                        resultSet.getInt(Candidacy.ID),
                        resultSet.getInt(Candidacy.INTERNSHIP_FK),
                        resultSet.getInt(Candidacy.STUDENT_FK),
                        resultSet.getInt(Candidacy.STATUS),
                        resultSet.getInt(Candidacy.N_CFU),
                        resultSet.getString(Candidacy.FIRST_NAME_REFERENT),
                        resultSet.getString(Candidacy.LAST_NAME_REFERENT),
                        resultSet.getString(Candidacy.EMAIL_REFERENT),
                        resultSet.getDate(Candidacy.START_DATE),
                        resultSet.getDate(Candidacy.END_DATE)
                );
                list.add(candidacy);
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Candidacy getCandidacyFromId(int candidacyId) {
        String query = "SELECT * FROM candidacy WHERE id = ?";
        PreparedStatement preparedStatement;
        Candidacy candidacy = null;
        try (Connection conn = Database.getDatasource().getConnection()) {
            preparedStatement = conn.prepareStatement(query);

            preparedStatement.setInt(1, candidacyId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                candidacy = new Candidacy(
                        resultSet.getInt(Candidacy.ID),
                        resultSet.getInt(Candidacy.INTERNSHIP_FK),
                        resultSet.getInt(Candidacy.STUDENT_FK),
                        resultSet.getInt(Candidacy.STATUS),
                        resultSet.getInt(Candidacy.N_CFU),
                        resultSet.getString(Candidacy.FIRST_NAME_REFERENT),
                        resultSet.getString(Candidacy.LAST_NAME_REFERENT),
                        resultSet.getString(Candidacy.EMAIL_REFERENT),
                        resultSet.getDate(Candidacy.START_DATE),
                        resultSet.getDate(Candidacy.END_DATE)
                );
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return candidacy;
    }

    @Override
    public boolean update(Candidacy candidacy) {
        String query = "UPDATE candidacy SET internship_fk = ?, student_fk = ?, status = ?, n_cfu=?, first_name_referent = ?," +
                "last_name_referent=?, email_referent = ?, start_date = ?, end_date = ? WHERE id = ?";
        PreparedStatement preparedStatement;
        int rows = 0;

        try (Connection conn = Database.getDatasource().getConnection()) {
            preparedStatement = conn.prepareStatement(query);

            preparedStatement.setInt(1, candidacy.getInternshipFk());
            preparedStatement.setInt(2, candidacy.getStudentFk());
            preparedStatement.setInt(3, candidacy.getStatus());
            preparedStatement.setInt(4, candidacy.getNumCFU());
            preparedStatement.setString(5, candidacy.getFirstNameReferent());
            preparedStatement.setString(6, candidacy.getLastNameReferent());
            preparedStatement.setString(7, candidacy.getEmailReferent());
            preparedStatement.setDate(8, candidacy.getStartDate());
            preparedStatement.setDate(9, candidacy.getEndDate());

            rows = preparedStatement.executeUpdate();

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows == 1;
    }

    @Override
    public boolean rejectCandidacyById(int candidacyId) {
        String query = "UPDATE candidacy SET status = 1 WHERE id = ?";
        PreparedStatement preparedStatement;
        int rows = 0;

        try (Connection conn = Database.getDatasource().getConnection()) {
            preparedStatement = conn.prepareStatement(query);

            preparedStatement.setInt(1, candidacyId);

            rows = preparedStatement.executeUpdate();

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows == 1;
    }
}
