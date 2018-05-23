package controller;

import model.Company;
import model.Internship;
import model.dao.CompanyDAO;
import model.dao.CredentialDAO;
import model.dao.InternshipDAO;
import org.apache.log4j.Logger;

public class CompanyController {
    final static Logger logger = Logger.getLogger(CompanyController.class);

    private CompanyDAO companyDAO;
    private InternshipDAO internshipDAO;
    r
    private CredentialDAO credentialDAO;

    public CompanyController() {
        this.companyDAO = new CompanyDAO();
        this.internshipDAO = new InternshipDAO();
    }

    /**
     * Method used to update an existing company in the DB. First we need to find the already stored record, then fetch
     * all values, modify them according to userType and update the row in the DB.
     *
     * @param company   company instance
     * @param userType  user type of the requesting user.
     * @param idCompany company id.
     * @return true if updated, false otherwise.
     */
    public boolean selectiveUpdate(Company company, int userType, int idCompany) {
        Company companyStored = companyDAO.getCompanyFromId(idCompany);
        if (companyStored != null) {
            // According to userType change values.
            switch (userType) {
                case 1: { // Company: can change all except id,piva and credentials.
                    changeNotNullFields(companyStored, company);
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

    private void changeNotNullFields(Company companyStored, Company company) {
        // For all editable fields.
        if (!company.getSocialRegion().equals("")) companyStored.setSocialRegion(company.getSocialRegion());
        if (!company.getLegalAddress().equals("")) companyStored.setLegalAddress(company.getLegalAddress());
        if (!company.getLawyerFirstName().equals("")) companyStored.setLawyerFirstName(company.getLawyerFirstName());
        if (!company.getLawyerLastName().equals("")) companyStored.setLawyerLastName(company.getLawyerLastName());
        if (!company.getPersonFirstName().equals("")) companyStored.setPersonFirstName(company.getPersonFirstName());
        if (!company.getPersonLastName().equals("")) companyStored.setPersonLastName(company.getPersonLastName());
        if (company.getPersonTelNumber() != 0) companyStored.setPersonTelNumber(company.getPersonTelNumber());
        if (!company.getLegalForum().equals("")) companyStored.setLegalForum(company.getLegalForum());
    }

    /**
     * Method used to first check if the company has the right to post new internship offer and then add the offer
     * to the DB.
     *
     * @param companyId  company id.
     * @param internship internship to add.
     * @return > 0 if ok  (id of internship), 0 if the company can't post, -1 otherwise.
     */
    public int addInternshipIfAllowed(int companyId, Internship internship) {
        int status = -1;

        // Check if the company has the right to add internship.
        if (companyDAO.hasRightToPost(companyId)) {
            // Add internship into the DB and get the last inserted id.
            status = internshipDAO.insert(internship);
        }
        return status;
    }

    public Company getCompanyByID(int companyId) {
        return companyDAO.getCompanyFromID(companyId);
    }

    public int insertCompany(Company companyToInsert) {
        // In order to insert a company first we need to check if the email is available, then we register the company,
        // then the we get the last inserted id and insert a credential instance in the table.
        if (credentialDAO.checkEmailAvailable(companyToInsert.getCredential().getEmail())) {
            // Email is available, then insert student.
            int id = credentialDAO.insert(companyToInsert.getCredential(), companyToInsert.getCredential().getPassword());
            if (id != -1) {
                // Student inserted correctly.
                companyToInsert.setId(id);
                // Then insert the credential.
                companyToInsert.getCredential().setUserType(1); // Company.
                // company.getCredential().setUserFk(id);
                if (companyDAO.insert(companyToInsert)) {
                    // OK.
                    return companyToInsert.getId();
                } else {
                    logger.error("Error while inserting credential object.");
                }
            } else {
                logger.error("Error while inserting company object.");
            }
        } else {
            logger.error("Email is not available.");
        }
        return -1;
    }
}

