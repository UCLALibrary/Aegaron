package aegaron.util;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;

import javax.naming.Context;
import java.sql.Connection;
import java.sql.*;
import javax.naming.*;
import javax.sql.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DataSourceFactory{
  private static final Log LOG = LogFactory.getLog(DataSourceFactory.class);
  public static Connection fetchConnection( String name ){
    Connection result = null;
    Context initialContext;
    Context envCtx;
    DataSource datasource;
    try {
          initialContext = new InitialContext();
         if ( initialContext == null){
           LOG.error("JNDI problem. Cannot get InitialContext.");
         }
           envCtx = (Context) initialContext.lookup("java:comp/env");
          datasource = (DataSource)envCtx.lookup(name);
         if (datasource != null) {
           result = datasource.getConnection();
         }
         else {
           LOG.error("Failed to lookup datasource.");
         }
       }
       catch ( NamingException ex ) {
         LOG.error("Cannot get connection: " + ex);
       }
       catch(SQLException ex){
         LOG.error("Cannot get connection: " + ex);
       }

       return result;
  }
}
