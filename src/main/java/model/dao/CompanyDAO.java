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
                        null
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
        return false;
    }
}
