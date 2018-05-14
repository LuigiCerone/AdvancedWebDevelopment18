package model.dao.inter;

import model.Company;

public interface CompanyDAO_Interface {
    Company getCompanyFromPIVA(String piva);

    boolean updateCompany(Company company);

    boolean hasRightToPost(int companyId);
}
