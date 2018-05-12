package database;

import org.apache.log4j.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Database {
    //    @Resource(lookup = "jdbc/awd_db")
    private static DataSource dataSource;
    private static InitialContext ctx;

    final static Logger logger = Logger.getLogger(Database.class);

    private static void init() {
        try {
            ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/awd_db");
            logger.debug("Datasource initialized.");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public static DataSource getDatasource() {
        if (dataSource == null)
            init();
        return dataSource;
    }
}
