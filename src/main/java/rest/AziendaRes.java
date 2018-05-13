package rest;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("azienda")
public class AziendaRes {

    /*
     * Questa root resource può essere utilizzata anche come sub-resource
     * in base al parametro passato nel costruttore.
     * Se il parametro viene omesso, si tratterà di una risoursa root che
     * rappresenta tutti gli articoli, altrimenti sarà una sub-resource che
     * rappresenta i soli articoli dell'issue identificato dal parametro stesso.
     */

    private int id = 0;

    public AziendaRes() {
        this.id = 0;
    }

    public AziendaRes(int id) {
        this.id = id;
    }

    @GET
    @Path("azienda/{id: [0-9]+}/offerte")
    @Produces(MediaType.APPLICATION_JSON)
    public Response InternshipByCompany(@PathParam(id) int m){

        return Response.ok(m).build();

    }
}

