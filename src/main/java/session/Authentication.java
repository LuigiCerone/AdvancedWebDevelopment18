package session;

import java.util.UUID;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

@Path("auth")
public class Authentication {

    //POST /rest/auth
    //Content-Type: application/json
    //Accept: application/json
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(LoginDetails ld) {
        if (ld.isValid()) {
            String token = UUID.randomUUID().toString();
            //il token va salvato nella base di dati per controlli successivi
            NewCookie authcookie = new NewCookie("sid", token);
            //restituiamo il token come testo della risposta e anche come cookie
            return Response.ok(token).cookie(authcookie).build();
        } else {
            return Response.status(Response.Status.FORBIDDEN).entity("Invalid username or password").build();
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
    //DELETE /rest/auth/{SID}
    //Content-Type: application/json
//    @Path("auth/{sid: [a-z0-9-]+}")
    @Path("auth")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout(@CookieParam("sid") Cookie authcookie) {
        if (authcookie != null) {
            //dovremmo eliminare dalla base di dati il token authcookie.getValue()            
            NewCookie resetauthcookie = new NewCookie(authcookie, null, 0, false);
            //resettiamo il cookie sul client
            return Response.ok("Logout successful").cookie(resetauthcookie).build();
        } else {
            return Response.ok("No active session").build();
        }
    }
    
}
