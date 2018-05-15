package rest;

import controller.CredentialController;
import controller.InternshipController;
import model.Candidacy;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;

public class CandidacyResource {
    final static Logger logger = Logger.getLogger(CandidacyResource.class);

    private Cookie authcookie;
    private int internshipId;

    public CandidacyResource(Cookie authcookie, int id) {
        this.authcookie = authcookie;
        this.internshipId = id;
    }


    //POST rest/auth/offerte/{id: [0-9]+}/candidati
    //Accept: application/json
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertCandidacy(@PathParam("id") int n) {
        /*
         * L'annotazione @PathParam permette di "iniettare"
         * su un parametro del metodo il valore effettivo del
         * parametro della URL col nome indicato. JAX-RS proverà
         * a convertire il parametro della URL nel tipo richiesto
         * dal metodo.
         */
        return Response.ok(n).build();
    }

    //PUT rest/auth/offerte/{id: [0-9]+}/candidati/{idc: [0-9]+}
    //Accept: application/json
    @PUT
    @Path("{idc: [0-9]+}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCandidacy(@PathParam("idc") int candidacyId, Candidacy candidacy) {
        if (authcookie != null) {
            int userType = new CredentialController().checkCookieAndGetUserType(authcookie.getValue());
            if (userType != -1) {
                // userType contains an integer 0=student, 1=company, 2=admin.
                if (userType == 1) {
                    int idLoggedUser = new CredentialController().getUserIdByCookie(authcookie.getValue());
                    if (idLoggedUser != -1) {
                        int status = new InternshipController().addCandidacyToInternship(idLoggedUser, candidacyId, candidacy);
                        if (status == 1) {
                            // OK, then noContent.
                            return Response.noContent().build();
                        } else if (status == 0) {
                            // Then forbidden.
                            return Response.status(403).build();
                        } else
                            return Response.serverError().build();
                    } else return Response.serverError().build();
                } else
                    return Response.status(403).build();
            } else {
                return Response.ok("No active session").build();
            }
        } else return Response.ok("No active session").build();
    }

    //DELETE rest/auth/offerte/{id: [0-9]+}/candidati/{idc: [0-9]+}
    //Accept: application/json
    @DELETE
    @Path("{idc: [0-9]+}")
    public Response deleteCandidacy(@PathParam("idc") int candidacyId) {
        if (authcookie != null) {
            int userType = new CredentialController().checkCookieAndGetUserType(authcookie.getValue());
            if (userType != -1) {
                // userType contains an integer 0=student, 1=company, 2=admin.
                if (userType == 1) {
                    int idLoggedUser = new CredentialController().getUserIdByCookie(authcookie.getValue());
                    if (idLoggedUser != -1) {
                        int status = new InternshipController().deleteCandidacyToInternship(idLoggedUser, candidacyId);
                        if (status == 1) {
                            // DELETED, then noContent.
                            return Response.noContent().build();
                        } else if (status == 0) {
                            // Then forbidden.
                            return Response.status(400).build();
                        } else
                            return Response.serverError().build();
                    } else return Response.serverError().build();
                } else
                    return Response.status(403).build();
            } else {
                return Response.ok("No active session").build();
            }
        } else return Response.ok("No active session").build();
    }


    //GET rest/auth/offerte/{id: [0-9]+}/candidati/{idc: [0-9]+}/progetto-formativo
    //Accept: application/json
    @GET
    @Path("{idc: [0-9]+}/progetto-formativo")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getCandidacy(@PathParam("idc") int candidacyId) {
        if (authcookie != null) {
            int userType = new CredentialController().checkCookieAndGetUserType(authcookie.getValue());
            if (userType != -1) {
                // userType contains an integer 0=student, 1=company, 2=admin.
                int idLoggedUser = new CredentialController().getUserIdByCookie(authcookie.getValue());
                if (idLoggedUser != -1) {
                    int status = new InternshipController().checkIfAllowed(idLoggedUser, candidacyId, userType);
                    if (status == 1) {
                        // Retrun pdf.
                        File file = new InternshipController().getPdfForCandidacy(candidacyId);
                        return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
                                .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"") //optional
                                .build();
                    } else if (status == 0) {
                        // Then forbidden.
                        return Response.status(400).build();
                    } else
                        return Response.serverError().build();
                } else return Response.serverError().build();
            } else {
                return Response.ok("No active session").build();
            }
        } else return Response.ok("No active session").build();
    }
}
