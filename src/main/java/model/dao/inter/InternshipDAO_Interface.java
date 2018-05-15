package model.dao.inter;


import model.Internship;

import java.sql.Timestamp;
import java.util.List;
import java.util.LinkedList;

public interface InternshipDAO_Interface {

    int insert(Internship internship);

    LinkedList<Internship> getAllInternships();

    LinkedList<Internship> getInternshipfilteredByCriteria(int i, String n);

    Internship getInternshipFromID(int id);

    LinkedList<Internship> getIntershipByCompanyID(int m);

}
