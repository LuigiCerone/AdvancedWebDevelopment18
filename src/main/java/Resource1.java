import model.Credential;
import model.dao.CredentialDAO;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/*
 * l'annotazione @Path sulla classe la definisce come
 * root resource, che sarà pubblicata dalla servlet
 * di JAX-RS immediatamente sotto il suo URL pattern
 * quindi nel nostro caso /rest/res1
 */
@Path("res1")
public class Resource1 {
    final static Logger logger = Logger.getLogger(Resource1.class);

    /*
     * i metodi non marcati con annotazioni JAX-RS restano interni alla classe
     * e non sono esposti tramite l'API RESTful
     */
    public int pippo() {
        return 0;
    }

    /*
     * l'annotazione @GET indica a JAX-RS che
     * questo metodo verrà chimato quando si richiederà
     * la risorsa-classe (cioè /rest/res1) con il verbo
     * HTTP GET
     * l'annotazione @Produces indica a JAX-RS che, in
     * particolare, il metodo verrà chiamato se si richiede
     * (header Accept della richiesta) una risposta in formato
     * "text/plain"
     */
    //GET /rest/res1
    //Accept: text/plain
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get_text() {
        /*
         * JAX-RS trasforma la stringa restituita nel contenuto
         * della risposta HTTP
         */
        return "ciao";
    }

    /*
     * stesso verbo HTTP sulla stessa risprsa,
     * ma in questo caso il metodo verrà chiamato
     * se la richiesta indica JSON come tipo preferito
     * per il formato di ritorno. JAX-RS proverà a convertire
     * il dato restituito dal metodo nel tipo dichiarato utilizzando
     * i provider noti (nel nostro caso Jackson)
     * Inoltre, invece di restituire direttamente il dato
     * (una stringa in questo caso), usiamo la classe
     * Response di JAX-RS. Questa ci fornisce maggiore
     * versatilità, ad esempio nel codice HTTP da restituire
     * assieme al contenuto.
     */
    //GET /rest/res1
    //Accept: application/json
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get_json() {
        /*
         * impostiamo il codice 200 (OK),
         * alleghiamo i dati da trasmettere e generiamo
         * la response  da restituire
         */
        //equivalente a return "ciao" con return type String
        return Response.ok("ciao").build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("insert")
    public Response insert() {
//        new CredentialDAO().test();
//        return Response.ok("inserted").build();
        Credential credential = new Credential();
        credential.setEmail("luigi@test.com");
        new CredentialDAO().insert(credential, "password");
        return Response.ok("inserted").build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("check")
    public Response check() {

//        boolean status = new CredentialDAO().checkLogin("luigi@test.com", "password1");
//        logger.debug("Status : " + status);
        return Response.ok("ok").build();
    }

    /*
     * Tramite l'annotazione @Path definiamo un sotto-path
     * a cui questo metodo dovrà rispondere, in particolare
     * se si invia una richiesta GET con tipo di ritorno
     * application/json sul path /rest/res1/lista
     * Notare come JAX-RS, tramite Jackson, converte correttamente
     * la List di Java in una lista JSON.
     */
    //GET /rest/res1/lista
    //Accept: application/json
    @GET
    @Path("lista")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get_lista_json() {
        List<String> l = new ArrayList<>();
        l.add("A");
        l.add("B");
        return Response.ok(l).build();
    }

    /*
     * restituendo un oggetto che implementa
     * StreamingOutput possiamo scrivere la risposta
     * su uno stream, quindi trasmettere anche contenuti
     * di grosse dimensioni, come un binario
     */
    //GET /rest/res1/binario
    //Accept: application/octet-stream
//    @GET
//    @Path("binario")
//    @Produces(MediaType.APPLICATION_OCTET_STREAM)
//    public Response get_binario_json() {
//        final byte[] dummyData = new byte[1024];
//        StreamingOutput result = new StreamingOutput() {
//            @Override
//            public void write(OutputStream out) throws IOException {
//                out.write(dummyData);
//            }
//        };
//        //notare come incapsuliamo lo StreamingOutput nella Result,
//        //aggiungendo anche un header che facilita il download del
//        //contenuto come file...
//        return Response.ok(result).header("content-disposition", "attachment; filename=prova.txt").build();
//    }

    /*
     * vediamo ora come è possibile restituire direttamente
     * delle strutture dati Java (dei bean, precisamente)
     * lasciando a JAX-RS l'onere di convertirle nel formato
     * dati richiesto per l'output.
     * Questo metodo verrà chiamato se il client effettua
     * una GET sulla url /rest/res1/bean chiedendo
     * output di tipo JSON
     */
    //GET /rest/res1/bean
    //Accept: application/json
//    @GET
//    @Path("bean")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response get_bean_json() {
//        // Creiamo una classe dati giocattolo
//        SimpleClass c = new SimpleClass("classe1", 1,
//                new SimpleClass("classe2", 3,
//                        null));
//        //JAX-RS la serializza automaticamente in JSON se Jackson è tra le librerie!
//        return Response.ok(c).build();
//    }
//
//    //GET /rest/res1/listabean
//    //Accept: application/json
//    @GET
//    @Path("listabean")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response get_bean_list_json() {
//        List<SimpleClass> l = new ArrayList<>();
//        l.add(new SimpleClass("classe1", 1,
//                new SimpleClass("classe2", 3,
//                        null)));
//        l.add(new SimpleClass("classe3", 4,
//                new SimpleClass("classe4", 5,
//                        null)));
//        return Response.ok(l).build();
//    }

    /*
     * proviamo ora a gestire path parametrici, del tipo
     * collezione/ID-elemento. In paticolare, scriviamo
     * un @Path che contiene un parametro chiamato "id"
     * definito da un'espressione regolare.
     * Questo metodo verrà quindi chiamato se si invia
     * una righiesta GET con tipo di ritorno application/json
     * su URL del tipo /rest/res1/pippo/<numero>
     */
    //GET /rest/res1/pippo/<numero>
    //Accept: application/json
    @GET
    @Path("pippo/{id: [0-9]+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get_pippo_item_by_id(@PathParam("id") int n) {
        /*
         * L'annotazione @PathParam permette di "iniettare"
         * su un parametro del metodo il valore effettivo del
         * parametro della URL col nome indicato. JAX-RS proverà
         * a convertire il parametro della URL nel tipo richiesto
         * dal metodo.
         */
        return Response.ok(n).build();
    }

    /*
     * in questo esempio mostriamo come iniettare tra i
     * parametri di un metodo il valore di un campo inserito
     * nella stringa di query della URL. Questo metodo verrà
     * chiamato quindi con url del tipo
     * /rest/res1/pippo?filtro=valore
     */
    //GET /rest/res1/pippo?filtro=x
    //Accept: application/json
    @GET
    @Path("pippo")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get_pippo_collection_with_query(@QueryParam("filtro") String f) {
        /*
         * L'annotazione @QueryParam permette di "iniettare"
         * su un parametro del metodo il valore effettivo del
         * parametro della query string col nome indicato. JAX-RS proverà
         * a convertire il parametro della query string nel tipo richiesto
         * dal metodo. Se il parametro non è spacificato, verrà impostato
         * su null.
         */
        if (f != null) {
            return Response.ok(f).build();
        } else {
            return Response.serverError().build();
        }
    }

    /*
     * Rispondere alla delete è semplice: basta usare
     * l'annotazione @DELETE sul metodo. Si possono eventualmente
     * usare sempre le annotazioni @Path e @PathParam
     * per fare in modo che il metodo risponda a sotto URL
     *
     */
    //DELETE /rest/res1
    @DELETE
    public Response delete() {
        return Response.noContent().build();
    }

    /*
     * Vediamo ora come rispondere alle richieste che
     * contengono un payload, come una POST.
     * Come indicato dall'annotazione @POST sul metodo,
     * questo metodo verrà chiamato se si effettua una
     * POST sulla risorsa /rest/res1 (visto che non
     * c'è alcuna annotazione @Path) inviando dei dati in
     * formato JSON, come indicato dall'annotazione
     * @Consumes
     */
    //POST /rest/res1
    //Content-Type: application/json
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add_item(
            //tramite l'annotazione @Context, iniettiamo
            //tra i parametri un utile oggetto fornito da
            //JAX-RS
            @Context UriInfo c,
            //il parametro senza annotazioni viene invece
            //riempito da JAX-RS col payload della richiesta
            //si può usare anche il tipo InputStream per ricevere
            //il payload sotto forma di uno stream leggibile
            //incrementalmente
            String data) {

        /*
         * Qui dovremmo interpretare il payload, magari
         * parsandolo in un JSONObject, e usarlo, probabilmente
         * per effettuare un inserimento dati. Alla fine, restituiremo
         * la URL per accedere all'elemento appena inserito.
         */
        /*
         * un modo conveniente (ma non imposto) per creare
         * la URI da restituire è usare l'UriInfo iniettato
         * tra i parametri. Qui chiediamo che sia costruita
         * la URI necessaria ad attivare questa classe e poi chiamarne
         * il metodo get_pippo_item_by.
         * Visto che get_pippo_item_by_id è annotato con un @Path
         * parametrico, passiamo a build il valore per questo
         * parametro, che dovrebbe essere la chiave dell'elemento
         * appena creato...
         */
        URI u = c.getBaseUriBuilder()
                .path(Resource1.class) //arriviamo alla risorsa Resource1
                .path(Resource1.class, "get_pippo_item_by_id") //passiamo alla Resource1sub1 tramite il suo metodo toSub1
                .build(4);
        /* se invece si volesse costruire il path verso una sotto-risorsa
         * si dovrebbe procedere inserendo uno a uno i segmenti di path che portano
         * ad essa, concludendo con il path verso il metodo finale.
         */
        /* se invece si volesse costruire il path verso una sotto-risorsa
         * si dovrebbe procedere inserendo uno a uno i segmenti di path che portano
         * ad essa, concludendo con il path verso il metodo finale, ad esempio
         */
//      URI u2 = c.getBaseUriBuilder()
//             .path(Resource1.class) //arriviamo alla risorsa Resource1
//             .path(Resource1.class,"toSub1") //passiamo alla Resource1sub1 tramite il suo metodo toSub1
//             .path(Resource1sub1.class, "get_json2") //chiamiamo uno specifico metodo della Resource1sub1 (un esempio non presente nel codice)
//             .build()); //supponiamo che nel path costruito non ci siano parametri

        /* ATTENZIONE: la sintassi alternativa sotto riportata, invece, APPENDE il path
         * per il metodo get_pippo_item_by_id al path assoluto della CHIAMATA
         * corrente (non della risorsa Resource1!).
         */
//        URI u = c.getAbsolutePathBuilder()
//                .path(Resource1.class, "get_pippo_item_by_id")
//                .build(4);
        return Response.created(u).build();
    }

    /*
     * Stesso esempio del metodo precedente, ma qui rispondiamo
     * al POST sulla risorsa /rest/res1/bean
     */
    //POST /rest/res1/bean
    //Content-Type: application/json
//    @POST
//    @Path("bean")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response add_bean(
//            @Context UriInfo c,
//            // Possiamo anche (per POST e PUT) dire a JAX-RS
//            // di decodificare automaticamente il payload in un
//            // oggetto, se compatibile.
//            SimpleClass data) {
//
//        /*
//         * Attenzione: per poter essere deserializzato l'oggetto
//         * deve essere dotato di un construttore di default
//         * (senza parametri), oltre ovviamente ad avere campo
//         * mappabil su quelli del JSON del payload.
//         */
//        URI u = c.getBaseUriBuilder()
//                .path(Resource1.class) //arriviamo alla risorsa Resource1
//                .path(Resource1.class, "get_pippo_item_by_id") //passiamo alla Resource1sub1 tramite il suo metodo toSub1
//                .build(4);
//
//        //o alternativamente possiamo costruire la URI "a mano"
//        //u = new URI("http://pippo.com/uri/restituita");
//        return Response.created(u).build();
//    }
//
//    /*
//     * Stesso esempio del metodo precedente, ma qui rispondiamo
//     * al POST sulla risorsa /rest/res1/beanlist e decodifichiamo
//     * una lista di classi
//     */
//    //POST /rest/res1/beanlist
//    //Content-Type: application/json
//    @POST
//    @Path("listabean")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response add_beanlist(
//            @Context UriInfo c,
//            List<SimpleClass> data) {
//
//        URI u = c.getBaseUriBuilder()
//                .path(Resource1.class) //arriviamo alla risorsa Resource1
//                .path(Resource1.class, "get_pippo_item_by_id") //passiamo alla Resource1sub1 tramite il suo metodo toSub1
//                .build(5);
//
//        return Response.created(u).build();
//    }
//
//    /*
//     * Quanto detto per POST vale anche per il metodo PUT.
//     */
//    //PUT /rest/res1/bean
//    //Content-Type: application/json
//    @PUT
//    @Path("bean")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response put_bean(SimpleClass c) {
//        //di solito una PUT restituisce NO CONTENT
//        return Response.noContent().build();
//    }
//
//    /*
//     * In questo caso il metodo POST accetta più tipi diversi come payload.
//     * Non potendo eseguire una decodifica immediata, riceviamo il payload come
//     * stringa e poi decidiamo in base al tipo effettivo come trattarlo.
//     * Da notare l'uso dell'annotazione @Context qui per iniettare un altro elemento
//     * del contesto JAX-RS: gli header della richiesta.
//     */
//    //POST /rest/res1/multitype
//    //Content-Type: application/json o text/plain
//    @POST
//    @Path("multitype")
//    @Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
//    public Response add_beanlist(
//            @Context UriInfo c, @Context HttpHeaders headers, String payload) {
//        MediaType mediaType = headers.getMediaType();
//
//        UriBuilder ub = c.getBaseUriBuilder()
//                .path(Resource1.class) //arriviamo alla risorsa Resource1
//                .path(Resource1.class, "get_pippo_item_by_id"); //passiamo alla Resource1sub1 tramite il suo metodo toSub1
//
//        if (null != mediaType) {
//            switch (mediaType.toString()) {
//                case MediaType.APPLICATION_JSON:
//                    return Response.created(ub.build(8)).build();
//                case MediaType.TEXT_PLAIN:
//                    return Response.created(ub.build(7)).build();
//            }
//        }
//        return Response.status(Response.Status.UNSUPPORTED_MEDIA_TYPE).build();
//
//    }
//
//    /*
//     * un metodo con annotazione @Path ma senza verbo http
//     * e senza produces/consumes fa sì che JAX-RS passi il controllo
//     * alla classe-risorsa restituita dal metodo. La scansione della URL
//     * e la ricerca del metodo da chiamare continueranno quindi nell'oggetto
//     * restituito
//     */
//    //<qualsiasi metodo> /rest/res1/sub1
//    @Path("sub1")
//    public Resource1sub1 toSub1() {
//        return new Resource1sub1();
//    }
//
//    //<qualsiasi metodo> /rest/res1/sub2/<numero>
//    @Path("sub2/{subid: [0-9]+}")
//    public Resource1sub2 toSub2() {
//        return new Resource1sub2();
//    }
//
//    /*
//     * Possiamo ovviamente configurare l'oggetto restituito. In questo modo,
//     * è anche possibile riusare lo stesso oggetto su path diversi: basterà
//     * renderlo "cosciente" del path che lo ha attivato usando i parametri passati,
//     * ad esempio, al costruttore.
//     */
//    //<qualsiasi metodo> /rest/res1/sub3
//    @Path("sub3")
//    public Resource1sub3 toSub3() {
//        return new Resource1sub3(3);
//    }
//
//    //<qualsiasi metodo> /rest/res1/sub3a
//    @Path("sub3a")
//    public Resource1sub3 toSub3a() {
//        return new Resource1sub3(4);
//    }
//
//    //<qualsiasi metodo> /rest/res1/sub3b/<numero>
//    @Path("sub3b/{subid: [0-9]+}")
//    public Resource1sub3 toSub3b(@PathParam("subid") int subid) {
//        return new Resource1sub3(subid);
//    }
//
//    /*
//     * La sotto-risorsa che segue può essere attivata anche come risorsa radice,
//     * con il path rest/res1sub4
//     */
//    //<qualsiasi metodo> /rest/res1/sub4
//    @Path("sub4")
//    public Resource1sub4 toSub4() {
//        return new Resource1sub4(3);
//    }

}
