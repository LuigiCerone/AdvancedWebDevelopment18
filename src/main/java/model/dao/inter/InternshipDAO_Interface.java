package model.dao.inter;


import model.Internship;

import java.util.LinkedList;

public interface InternshipDAO_Interface {

    int insert(Internship internship);

    LinkedList<Internship> getAllInternships();

    LinkedList<Internship> getInternshipfilteredByCriteria(int i, String n);

    Internship getInternshipFromID(int id);

    LinkedList<Internship> getIntershipByCompanyID(int m);

    LinkedList<Internship> getInternshipByRange(int first, int last);


    LinkedList<Internship> getInternshipGT(int first);

    LinkedList<Internship> getInternshipByRangeByCompany(int companyId, int first, int last);

    LinkedList<Internship> getInternshipGTByCompany(int companyId, int first);
}
