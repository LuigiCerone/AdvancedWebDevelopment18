package model.dao;

import database.Database;
import model.Candidacy;
import model.Student;
import model.dao.inter.CandidacyDAO_Interface;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class CandidacyDAO implements CandidacyDAO_Interface {
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
                        resultSet.getString(Candidacy.EMAIL_REFERENT)
                );
                list.add(candidacy);
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int insert(Candidacy candidacy){
        String query = "INSERT INTO candidacy VALUES (NULL,?,?,?,?,?,?,?);";
        PreparedStatement preparedStatement;
        int lastInsertedId = -1;

        try (Connection conn = Database.getDatasource().getConnection()) {
            preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, candidacy.getInternshipFk());
            preparedStatement.setInt(2, candidacy.getStudentFk());
            preparedStatement.setInt(3, candidacy.getStatus());
            preparedStatement.setInt(4, candidacy.setNumCFU(););
            preparedStatement.setString(5, candidacy.getFirstNameReferent());
            preparedStatement.setString(6, candidacy.getLastNameReferent());
            preparedStatement.setString(7, candidacy.getEmailReferent());

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
