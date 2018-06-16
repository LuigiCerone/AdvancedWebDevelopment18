package model.dao.inter;

import model.Company;

import java.util.LinkedList;

public interface CompanyDAO_Interface {
    Company getCompanyFromPIVA(String piva);

    boolean updateCompany(Company company);

    boolean hasRightToPost(int companyId);

    Company getCompanyFromID(int companyId);

    boolean insert(Company company);

    LinkedList<Company> getAllCompanies();
}
