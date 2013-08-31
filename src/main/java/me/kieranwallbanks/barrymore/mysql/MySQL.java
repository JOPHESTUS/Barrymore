package me.kieranwallbanks.barrymore.mysql;

import java.sql.Connection;
import java.sql.DriverManager;

import me.kieranwallbanks.barrymore.configuration.Configuration;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

/**
 * A utility class with one method, {@link #getContext(Configuration)}
 */
public class MySQL {

    /**
     * Connects to the MySQL database, opens a connection and returns the
     * connection as a {@link DSLContext} for jOOQ querying.
     *
     * @param conf The current {@link Configuration} file
     *
     * @return the connection as a DSLContext
     */
    public static DSLContext getContext(Configuration conf) {
        Connection connection = null;

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:mysql://" + conf.MySQL_Hostname + "/" + conf.MySQL_Database, conf.MySQL_Username, conf.MySQL_Password);
        } catch (Exception e) {
            e.printStackTrace(); // TODO better exception handling. Maybe email me?
            System.exit(1);
        }

        return DSL.using(connection, SQLDialect.MYSQL);
    }

}
