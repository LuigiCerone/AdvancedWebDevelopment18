package rest;

import com.sun.jndi.toolkit.url.Uri;
import controller.CredentialController;
import model.Candidacy;
import model.dao.CandidacyDAO;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;

public class CandidacyResource {
    final static Logger logger = Logger.getLogger(CandidacyResource.class);

    private Cookie authcookie;
    private int internshipId;

    public CandidacyResource(Cookie authcookie, int id) {
        this.authcookie = authcookie;
        this.internshipId = id;
    }


//    POST rest/auth/offerte/{id: [0-9]+}/candidati
//    Accept: application/json
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
    public Response updateCandidacy(@PathParam("idc") int idc) {
        /*
         * L'annotazione @PathParam permette di "iniettare"
         * su un parametro del metodo il valore effettivo del
         * parametro della URL col nome indicato. JAX-RS proverà
         * a convertire il parametro della URL nel tipo richiesto
         * dal metodo.
         */
        return Response.ok(idc).build();
    }

    //DELETE rest/auth/offerte/{id: [0-9]+}/candidati/{idc: [0-9]+}
    //Accept: application/json
    @DELETE
    @Path("{idc: [0-9]+}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteCandidacy(@PathParam("idc") int idc) {
        /*
         * L'annotazione @PathParam permette di "iniettare"
         * su un parametro del metodo il valore effettivo del
         * parametro della URL col nome indicato. JAX-RS proverà
         * a convertire il parametro della URL nel tipo richiesto
         * dal metodo.
         */
        return Response.ok(idc).build();
    }


    //GET rest/auth/offerte/{id: [0-9]+}/candidati/{idc: [0-9]+}/progetto-formativo
    //Accept: application/json
    @GET
    @Path("{idc: [0-9]+}/progetto-formativo")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getCandidacy(@PathParam("idc") int idc) {
        /*
         * L'annotazione @PathParam permette di "iniettare"
         * su un parametro del metodo il valore effettivo del
         * parametro della URL col nome indicato. JAX-RS proverà
         * a convertire il parametro della URL nel tipo richiesto
         * dal metodo.
         */
        return Response.ok(idc).build();
    }
}
