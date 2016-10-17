package aegaron.service;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.*;
import javax.naming.*;
import javax.sql.*;

import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.description.AxisService;
import org.apache.axis2.engine.ServiceLifeCycle;
import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDriver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;

public class AegaronServiceLifeCycle implements ServiceLifeCycle {
    private static final Log LOG = LogFactory.getLog(AegaronServiceLifeCycle.class);
    public static final String DB_CONNECTION = "aegaronconnection";
    //private static final String CONNECT_STRING = "jdbc:oracle:thin:dlcs/dlcs@localhost:9001:DLCS";
    //private static final String AEGARON = "aegaron";
    //private static final String VALIDATION_QUERY = "SELECT 1 FROM DUAL";
    public static final String DATASOURCE_CONTEXT="jdbc/Aegaron";

    public void startUp(ConfigurationContext configurationContext, AxisService axisService) {
        LOG.info("AegaronServiceLifeCycle::startUp()");


        Connection result = null;
               try {
                 Context initialContext = new InitialContext();
                  LOG.info("Context created");
                 if ( initialContext == null){
                   LOG.info("JNDI problem. Cannot get InitialContext.");
                 }
                 LOG.info("Context not null");
                 Context envCtx = (Context) initialContext.lookup("java:comp/env");
                 LOG.info("envCtx Looked up");
                 DataSource datasource = (DataSource)envCtx.lookup(DATASOURCE_CONTEXT);
                 LOG.info("datasource Looked up from envctx "+envCtx);
                 if (datasource != null) {
                   result = datasource.getConnection();
                   configurationContext.setProperty(DB_CONNECTION, result);
                 }
                 else {
                   LOG.info("Failed to lookup datasource.");
                 }
               }
               catch ( NamingException ex ) {
                 LOG.error("Cannot get connection: " + ex);
               }
               catch(SQLException ex){
                 LOG.error("Cannot get connection: " + ex);
               }catch (Exception e) {
                LOG.error("AegaronServiceLifeCycle failed to start up " + e);
            }

    }

    public void shutDown(ConfigurationContext configurationContext,
                         AxisService axisService) {
        LOG.info("AegaronServiceLifeCycle::shutDown()");
        // closes the pool
       /* try {
            PoolingDriver driver = (PoolingDriver)DriverManager.getDriver("jdbc:apache:commons:dbcp:");
            driver.closePool(AEGARON);
        } catch (Exception e) {
            LOG.error("AegaronServiceLifeCycle failed to shut down " + e);
        }*/
    }
}
