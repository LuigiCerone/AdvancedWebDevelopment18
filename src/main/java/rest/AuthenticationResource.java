package rest;

import controller.CredentialController;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

@Path("auth")
public class AuthenticationResource {
    final static Logger logger = Logger.getLogger(AuthenticationResource.class);

    //POST /rest/auth
    //Content-Type: application/json
    //Accept: application/json
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(String json) {
//        logger.debug("rest/auth POST recevied: " + json);
        String token = new CredentialController().login(json);
        if (token != null) {
            NewCookie authcookie = new NewCookie("sid", token, null, null, null, 1800, false);
            //restituiamo il token come testo della risposta e anche come cookie
            return Response.ok("ok").cookie(authcookie).build();
        } else {
            return Response.status(Response.Status.FORBIDDEN).entity("Invalid username or password").build();
        }
    }


    //DELETE /rest/auth/{SID}
    //Content-Type: application/json
    @DELETE
    public Response logout(@CookieParam("sid") Cookie authcookie) {
        if (authcookie != null && new CredentialController().logout(authcookie.getValue())) {
            NewCookie resetauthcookie = new NewCookie(authcookie, null, 0, false);
            //resettiamo il cookie sul client.
            return Response.ok("Logout successful").cookie(resetauthcookie).build();
        } else {
            return Response.ok("No active session").build();
        }
    }

    //ANY /rest/auth/aziende
    //Content-Type: application/json
    @Path("aziende")
    @Produces(MediaType.APPLICATION_JSON)
    public CompanyResource company(@CookieParam("sid") Cookie authcookie) {
        return new CompanyResource(authcookie);
    }

    //ANY /rest/auth/studenti
    //Content-Type: application/json
    @Path("studenti")
    @Produces(MediaType.APPLICATION_JSON)
    public StudentResource student(@CookieParam("sid") Cookie authcookie) {
        logger.debug("Just received the following cookie: " + authcookie);
        return new StudentResource(authcookie);
    }

    //ANY /rest/auth/offerte
    //Content-Type: application/json
    @Path("offerte")
    @Produces(MediaType.APPLICATION_JSON)
    public InternshipResource internship(@CookieParam("sid") Cookie authcookie) {
        return new InternshipResource(authcookie);
    }

    //ANY /rest/auth/offerte/{id: [0-9]+}/candidati
    //Content-Type: application/json
    @Path("offerte/{id: [0-9]+}/candidati")
    @Produces(MediaType.APPLICATION_JSON)
    public CandidacyResource candidacy(@CookieParam("sid") Cookie authcookie, @PathParam("id") int id) {
        return new CandidacyResource(authcookie, id);
    }
}
