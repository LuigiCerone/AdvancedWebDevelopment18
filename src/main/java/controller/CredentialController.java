package controller;

import model.Credential;
import model.dao.CredentialDAO;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.UUID;

/**
 * Controller used to manage all the credential's related stuff such as login, sign in, session etc.
 */
public class CredentialController {
    final static Logger logger = Logger.getLogger(CredentialController.class);

    CredentialDAO credentialDAO;

    public CredentialController() {
        credentialDAO = new CredentialDAO();
    }

    /**
     * Method used to check login credentials, start session if they are correct by creating a token session.
     *
     * @param json String that contains user email and password.
     * @return token String or null if login is not correct.
     */
    public String login(String json) {
        String token;

        // Create a JSON form the string recevied.
        // TODO Remove JSONObject, use Jackson deserialization.
        JSONObject jsonObject = new JSONObject(json);

        // Get credential object from DB with the given email.
        Credential credential = credentialDAO.getCredentialFromEmailAndPassword(jsonObject.getString("email"),
                jsonObject.getString("password"));

        // Login ok, then create session.
        if (credential != null) {

            //il token va salvato nella base di dati per controlli successivi
            token = UUID.randomUUID().toString().replace("-", "");

            // Cookie will expire in 30mins.
            // TODO Parametrize this with web.xml resource.
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, 30);
            Timestamp timestamp = new Timestamp(cal.getTimeInMillis());

            // Start session by storing data into the DB.
            if (credentialDAO.startSession(credential.getId(), token, timestamp)) {
                // Session started.
                return token;
            } else {
                logger.error("Error while creating session.");
            }
        }
        // If credential is null there is not entry with the given data.
        return null;
    }

    /**
     * Method used to terminate the session of client by reseting his/her token in the DB,
     *
     * @param token token sid.
     * @return true if OK, false otherwise.
     */
    public boolean logout(String token) {
        return credentialDAO.endSession(token);
    }

    public int checkCookieAndGetUserType(String cookieValue) {
        // First we need to check for session.
        Calendar calendar = Calendar.getInstance();
        int idCredential = -1;
        idCredential = credentialDAO.checkSessionActive(cookieValue, new Timestamp(calendar.getTimeInMillis()));
        if (idCredential != -1) {
            // Session active.
            return credentialDAO.getUserTypeFromCredentialId(idCredential);
        }
        return -1;
    }

    /**
     * Method used to get the currently logged user'ID.
     *
     * @param cookie cookie used to query.
     * @return userId, -1 otherwise.
     */
    public int getUserIdByCookie(String cookie) {
        return credentialDAO.getUserIdByCookie(cookie);
    }
}
