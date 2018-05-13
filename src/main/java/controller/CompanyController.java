package controller;

import model.Company;
import model.dao.CompanyDAO;
import org.apache.log4j.Logger;

public class CompanyController {
    final static Logger logger = Logger.getLogger(CompanyController.class);

    private CompanyDAO companyDAO;

    public CompanyController() {
        this.companyDAO = new CompanyDAO();
    }

    /**
     * Method used to update an existing company in the DB. First we need to find the already stored record, then fetch
     * all values, modify them according to userType and update the row in the DB.
     *
     * @param company  company instance
     * @param userType user type of the requesting user.
     * @return true if updated, false otherwise.
     */
    public boolean selectiveUpdate(Company company, int userType) {
        Company companyStored = companyDAO.getCompanyFromPIVA(company.getPiva());
        if (companyStored != null) {
            // According to userType change values.
            switch (userType) {
                case 1: { // Company: can change all except id,piva and credentials.
                    changeNotNullFiels(companyStored, company);
                    // Update company.
                    return companyDAO.updateCompany(companyStored);
                }
                case 2: { // Admin: can change only active values.
                    companyStored.setActive(company.isActive());
                    // Update company.
                    return companyDAO.updateCompany(companyStored);
                }
            }
        } else return false;
        return false;
    }

    private void changeNotNullFiels(Company companyStored, Company company) {

    }
}

