import org.apache.log4j.Logger;
import rest.AuthenticationResource;
import rest.CompanyResource;
import rest.InternshipResource;
import rest.StudentResource;
import utils.CustomJacksonJsonProvider;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Set;

@ApplicationPath("rest")
public class JAXRSApplication extends Application {
    final static Logger logger = Logger.getLogger(JAXRSApplication.class);

    public JAXRSApplication() {
        logger.debug("COSTRUTTORE CHIAMATO!");
    }

        //Iniettiamo un riferimento alla DataSource che gestisce il pool di connessioni.
//        @Resource(name = "jdbc/webdb") //reference to the resource-ref in the deployment descriptor
//        private DataSource ds;


        HashSet<Class<?>> c = new HashSet<Class<?>>();
        //aggiungiamo tutte le *root resurces* (cioè quelle
        //con l'annotazione Path) che vogliamo pubblicare
//        c.add(Resource1.class);
        c.add(AuthenticationResource.class);
        c.add(StudentResource.class);
        c.add(InternshipResource.class);
        c.add(CompanyResource.class);
        c.add(CandidacyResource.class);

//    @Override
//    public Set<Object> getSingletons() {
//        logger.debug("getSingletons CHIAMATO!");
//        Set<Object> set = new HashSet<>();
//        set.add(new CustomJacksonJsonProvider());
//
//        return set;
//    }

//    @Override
//    public Map<String, Object> getProperties() {
//        logger.debug("getProperties CHIAMATO!");
////        Map<String, Object> map = new HashMap<>();
////        map.put("jersey.config.disableMoxyJson.server", true);
////        return map;
//    }

    @Override
    public Set<Class<?>> getClasses() {
        logger.debug("getClasses CHIAMATO!");
        Set<Class<?>> resources = new java.util.HashSet<>();
        resources.add(AuthenticationResource.class);
        resources.add(StudentResource.class);
        resources.add(InternshipResource.class);
        resources.add(CompanyResource.class);
//        resources.add(JacksonObjectMapperProvider.class);
//        resources.add(MarshallingFeature.class);
        resources.add(CustomJacksonJsonProvider.class);
        return resources;
    }
}