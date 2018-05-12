import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import rest.AuthenticationRootResource;
import rest.StudentRootResource;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * "rest" sarà il path di base a cui risponderà il nostro servizio
 */
@ApplicationPath("rest")
public class JAXRSApplication extends Application {

    private final Set<Class<?>> classes;

    public JAXRSApplication() {

        //Iniettiamo un riferimento alla DataSource che gestisce il pool di connessioni.
//        @Resource(name = "jdbc/webdb") //reference to the resource-ref in the deployment descriptor
//        private DataSource ds;


        HashSet<Class<?>> c = new HashSet<Class<?>>();
        //aggiungiamo tutte le *root resurces* (cioè quelle
        //con l'annotazione Path) che vogliamo pubblicare
        c.add(Resource1.class);
        //notare come questo esempio presenti più risorse root
//        c.add(Resource1sub4.class);
        c.add(AuthenticationRootResource.class);
        c.add(StudentRootResource.class);
//        c.add(Authentication_url.class);
//        c.add(ArticlesRes.class);
//        c.add(IssuesRes.class);
        //aggiungiamo il provider Jackson per poter
        //usare i suoi servizi di serializzazione e
        //deserializzazione JSON
        c.add(JacksonJsonProvider.class);
        classes = Collections.unmodifiableSet(c);
    }

    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }
}