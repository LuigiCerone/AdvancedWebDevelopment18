package model.dao.inter;

import model.Candidacy;

import java.sql.Date;
import java.util.List;

public interface CandidacyDAO_Interface {
    int insert(Candidacy candidacy, int iDInternship);

    List<Candidacy> getAllActiveCandidacyForCompany(Date now, int idCompany);

    Candidacy getCandidacyFromId(int candidacyId);

    boolean update(Candidacy candidacy);

    boolean rejectCandidacyById(int candidacyId);
}
