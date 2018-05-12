package session;

import controller.CredentialController;
import model.dao.CredentialDAO;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

@Path("auth")
public class Authentication {
    final static Logger logger = Logger.getLogger(Authentication.class);

    //POST /rest/auth
    //Content-Type: application/json
    //Accept: application/json
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(String json) {
        logger.debug("rest/auth POST recevied: " + json);
//        CredentialController.login
//        JSONObject jsonObject = new JSONObject(json);
//
//        CredentialDAO credentialDAO = new CredentialDAO();
//        Credential credential = credentialDAO.checkLogin(jsonObject.getString("email"),
//                jsonObject.getString("password"));
//        if (credential != null) {
//            // Login ok, then create session.
//            String token = credentialDAO.startSession(credential.getId());
//            NewCookie authcookie = new NewCookie("sid", token, "/awd18/rest", "", "", 1800, false);
//            //restituiamo il token come testo della risposta e anche come cookie
//            return Response.ok().cookie(authcookie).build();
//        } else {
//            return Response.status(Response.Status.FORBIDDEN).entity("Invalid username or password").build();
//        }
//
        String token = CredentialController.login(json);
        if (token != null) {
            NewCookie authcookie = new NewCookie("sid", token, "/awd18/rest", "", "", 1800, false);
            //restituiamo il token come testo della risposta e anche come cookie
            return Response.ok().cookie(authcookie).build();
        } else {
            return Response.status(Response.Status.FORBIDDEN).entity("Invalid username or password").build();
        }
    }


    //DELETE /rest/auth/{SID}
    //Content-Type: application/json
    @Path("auth")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout(@CookieParam("sid") Cookie authcookie) {
        if (authcookie != null) {
            //dovremmo eliminare dalla base di dati il token authcookie.getValue()

            new CredentialDAO().endSession(authcookie.getValue());
            NewCookie resetauthcookie = new NewCookie(authcookie, null, 0, false);
            //resettiamo il cookie sul client
            return Response.ok("Logout successful").cookie(resetauthcookie).build();
        } else {
            return Response.ok("No active session").build();
        }
    }

    /*
     * Metodo accessibile solo previa verifica della sessione
     * il session identifier viene letto dal cookie impostato tramite la procedura di login
     *
     */
    //POST /rest/sesson1/fun1
    //Content-Type: application/json
//    @GET
//    @Path("fun1")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response insession(@CookieParam("sid") String token) {
//        if (token != null /* && isValidToken(token) */) {
//            //dovremmo controllare se il token è presente sulla base di dati...
//            return Response.ok("fun1 attivata").build();
//        } else {
//            return Response.status(Response.Status.FORBIDDEN).entity("Invalid Session").build();
//        }
//    }
//
//    /*
//     * Metodo accessibile solo previa verifica della sessione, versione alternativa
//     * il session identifier viene letto dal cookie impostato tramite la procedura di login
//     * oppure dal campo query "sid" concatenato alla url
//     *
//     */
//    //POST /rest/session1/fun2
//    //Content-Type: application/json
//    @GET
//    @Path("fun2")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response insession(@CookieParam("sid") String ctoken, @QueryParam("sid") String qtoken) {
//        if ((ctoken != null /* && isValidToken(ctoken) */)
//                || (qtoken != null /* && isValidToken(qtoken) */)) {
//            //dovremmo controllare se il token è presente sulla base di dati...
//            return Response.ok("fun2 attivata").build();
//        } else {
//            return Response.status(Response.Status.FORBIDDEN).entity("Invalid Session").build();
//        }
//    }

    /*
     * Semplice procedura di logout (tramite DELETE sulla stessa url di login)
     * aspetta il session identifier come cookie, ma potrebbe essere modificato
     * come il metodo insession2 per accettarlo anche come query parameter
     * se la logout ha successo, cancella l'eventuale cookie dal client
     *
     */


}
