package net.suaa.utils;



import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

public class DriverMangerListner implements ServletContextListener {
    private final static Logger logger = Logger.getLogger(DriverMangerListner.class);
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        logger.info("[DriverMangerListner]:-------DriverManager deregisterDriver start...");
        //com.mysql.jdbc.AbandonedConnectionCleanupThread.uncheckedShutdown();
        Enumeration<Driver> enumeration = DriverManager.getDrivers();
        while (enumeration.hasMoreElements()) {
            try {
                DriverManager.deregisterDriver(enumeration.nextElement());

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        logger.debug("[DriverMangerListner]:-------DriverManager deregisterDriver end...");

    }
}
