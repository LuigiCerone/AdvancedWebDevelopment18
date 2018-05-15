package model.dao.inter;

import model.Internship;

import java.util.LinkedList;

public interface InternshipDAO_Interface {

    int insert(Internship internship);

    LinkedList<Internship> getAllInternships();

    LinkedList<Internship> getInternshipfilteredByCriteria(int i, String n);
}
