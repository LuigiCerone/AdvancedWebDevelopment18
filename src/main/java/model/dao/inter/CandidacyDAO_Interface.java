package model.dao.inter;

import model.Candidacy;

import java.sql.Timestamp;
import java.util.List;

public interface CandidacyDAO_Interface {
    List<Candidacy> getAllActiveCandidacyForCompany(Timestamp timestamp, int idCompany);
}