//package session;
//
//import java.net.URI;
//import java.util.UUID;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.DELETE;
//import javax.ws.rs.GET;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import javax.ws.rs.core.UriInfo;
//
///**
// *
// * @author Giuseppe Della Penna
// */
//@Path("session2")
//public class Authentication_url {
//
//    /*
//     * Semplice procedura di login (visto come una INSERT di sessione, quindi POST sulla url session)
//     * accetta un oggetto json contenente i campi username e password (vedi LoginDetails)
//     * se la login ha successo, produce un session identifier e lo ritrasmette nella risposta
//     * inserendolo nel path
//     *
//     */
//    //POST /rest/session2
//    //Content-Type: application/json
//    //Accept: application/json
//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response login(@Context UriInfo c, LoginDetails ld) {
//        if (ld.isValid()) {
//            String token = UUID.randomUUID().toString();
//            //restituiamo una URL che punta a una funzionalità di controllo della sessione
//            //l'elemento importante è il session identifier che vi è contenuto
//            URI u = c.getBaseUriBuilder()
//                    .path(Authentication_url.class)
//                    .path(Authentication_url.class, "status")
//                    .build(token);
//
//            return Response.created(u).build();
//
//        } else {
//            return Response.status(Response.Status.FORBIDDEN).entity("Invalid username or password").build();
//        }
//    }
//
//    /*
//     * Metodo accessibile solo previa verifica della sessione
//     * il session identifier viene letto dal path.
//     *
//     */
//    //POST /rest/sesson2/<sid>/status
//    //Content-Type: application/json
//    @GET
//    @Path("{sid: [a-z0-9-]+}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response status(@PathParam("sid") String token) {
//        if (token != null /* && isValidToken(token) */) {
//            //dovremmo controllare se il token è presente sulla base di dati...
//            return Response.ok("Il tuo profilo!").build();
//        } else {
//            return Response.status(Response.Status.FORBIDDEN).entity("Invalid Session Identifier").build();
//        }
//    }
//
//    /*
//     * Metodo accessibile solo previa verifica della sessione
//     * il session identifier viene letto dal path
//     *
//     */
//    //POST /rest/session2/<sid>/status
//    //Content-Type: application/json
//    @GET
//    @Path("{sid: [a-z0-9-]+}/fun1")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response insession(@PathParam("sid") String ctoken) {
//        if ((ctoken != null /* && isValidToken(ctoken) */)) {
//            //dovremmo controllare se il token è presente sulla base di dati...
//            return Response.ok("fun1 attivata").build();
//        } else {
//            return Response.status(Response.Status.FORBIDDEN).entity("Invalid Session").build();
//        }
//    }
//
//    /*
//     * Semplice procedura di logout (tramite DELETE sulla stessa url restituita dalla login)
//     *
//     */
//    //DELETE /rest/session2/<sid>
//    @DELETE
//    @Path("{sid: [a-z0-9-]+}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response logout(@PathParam("sid") String sid) {
//        //dovremmo eliminare dalla base di dati il token
//        return Response.noContent().build();
//    }
//}
