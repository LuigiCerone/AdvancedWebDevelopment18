package model.dao.inter;


import model.Internship;

import java.util.LinkedList;

public interface InternshipDAO_Interface {

    int insert(Internship internship);

    LinkedList<Internship> getAllInternships();


    Internship getInternshipFromID(int id);

    LinkedList<Internship> getInternshipByRange(int first, int last);


    LinkedList<Internship> getInternshipByRangeByCompany(int companyId, int first, int last);

}
