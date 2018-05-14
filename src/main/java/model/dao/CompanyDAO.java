package model.dao;

import database.Database;
import model.Company;
import model.dao.inter.CompanyDAO_Interface;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyDAO implements CompanyDAO_Interface {
    final static Logger logger = Logger.getLogger(CompanyDAO.class);

    public CompanyDAO() {
    }

    @Override
    public Company getCompanyFromPIVA(String piva) {
        Company company = null;
        String query = "SELECT * FROM company WHERE company.piva = ?;";
        PreparedStatement preparedStatement;
        try (Connection conn = Database.getDatasource().getConnection()) {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, piva);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                company = new Company(
                        resultSet.getInt(Company.ID),
                        resultSet.getString(Company.SOCIAL_REGION),
                        resultSet.getString(Company.LEGAL_ADDRESS),
                        resultSet.getString(Company.PIVA),
                        resultSet.getString(Company.LAWYER_FIRST_NAME),
                        resultSet.getString(Company.LAWYER_LAST_NAME),
                        resultSet.getString(Company.PERSON_FIRST_NAME),
                        resultSet.getString(Company.PERSON_LAST_NAME),
                        resultSet.getInt(Company.PERSON_TEL),
                        resultSet.getString(Company.LEGAL_FORUM),
                        resultSet.getBoolean(Company.ACTIVE),
                        resultSet.getBoolean(Company.VISIBLE),
                        null,
                        resultSet.getInt(Company.COMPANY_FK)
                );
            }
            conn.close();

            return company;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateCompany(Company company) {
        int rows = 0;
        String query = "UPDATE company SET social_region = ?, legal_address = ?, lawyer_first_name = ?, " +
                "lawyer_last_name = ?, person_first_name = ?, person_last_name =? , person_telnumber = ?, " +
                "legal_forum = ?, active = ?, visible = ? WHERE company.id = ?";
        PreparedStatement preparedStatement;
        try (Connection conn = Database.getDatasource().getConnection()) {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, company.getSocialRegion());
            preparedStatement.setString(2, company.getLegalAddress());
            preparedStatement.setString(3, company.getLawyerFirstName());
            preparedStatement.setString(4, company.getLawyerLastName());
            preparedStatement.setString(5, company.getPersonFirstName());
            preparedStatement.setString(6, company.getPersonLastName());
            preparedStatement.setInt(6, company.getPersonTelNumber());
            preparedStatement.setString(6, company.getLegalForum());
            preparedStatement.setBoolean(6, company.isActive());
            preparedStatement.setBoolean(6, company.isVisible());

            rows = preparedStatement.executeUpdate();

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows == 1;
    }

    @Override
    public boolean hasRightToPost(int companyId) {
        boolean allowed = false;
        String query = "SELECT active FROM company WHERE id = ?";
        PreparedStatement preparedStatement;
        try (Connection conn = Database.getDatasource().getConnection()) {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, companyId);


            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                allowed = resultSet.getBoolean(Company.ACTIVE);
            }

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allowed;
    }
}
