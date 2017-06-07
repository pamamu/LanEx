/*
 * Copyright (C) 2017 by Pablo Macias
 * pamaciasm@alumnos.unex.es
 *
 * This program is free software; you can redistribute it andor modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the
 * Free Software Foundation, Inc.,
 * 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

// Descomentar si se se va a usar un Listener para iniciar la conexi�n:
//package listener;
//Comentar si se va a usar un Listner para iniciar la conexi�n:
package listener;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

// Descomentar si se se va a usar un Listener para iniciar la conexi�n:


//Descomentar si se se va a usar un Listener para iniciar la conexi�n:
@WebListener
public class ServletContextListener implements javax.servlet.ServletContextListener {

//Comentar si se va a usar un Listener para iniciar la conexi�n:

//public class DBConnection {

  private static final Logger logger = Logger.getLogger(ServletContextListener.class.getName());

  //Descomentar si se se va a usar un Listener para iniciar la conexi�n:
  public void contextInitialized(ServletContextEvent event) {

    //Comentar si se va a usar un Listener para iniciar la conexi�n:
    //public Connection create(){

    logger.info("Connecting DB");
    Connection conn = null;

    try {
      Class.forName("org.sqlite.JDBC");
      String dbURL =
          "jdbc:sqlite:file:" + System.getProperty("user.home") + "/sqlite_dbs/exchange.db";
      conn = DriverManager.getConnection(dbURL);
      if (conn != null) {
        System.out.println("Connected to the database");
        DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
        System.out.println("Driver name: " + dm.getDriverName());
        System.out.println("Driver version: " + dm.getDriverVersion());
        System.out.println("Product name: " + dm.getDatabaseProductName());
        System.out.println("Product version: " + dm.getDatabaseProductVersion());
        ServletContext sc = event.getServletContext();
        sc.setAttribute("dbConn", conn);

        Statement stmt = conn.createStatement();


      }
    } catch (ClassNotFoundException ex) {
      ex.printStackTrace();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }

    logger.info("DB connected");


  }

  //Descomentar si se se va a usar un Listener para destruir la conexi�n:
  public void contextDestroyed(ServletContextEvent arg0) {
    //Comentar si se va a usar un Listener para destruir la conexi�n:
    //public void destroy(Connection conn){

    logger.info("Destroying DB");
    try {
      logger.info("DB shutdown start");
      //Descomentar si se se va a usar un Listener para destruir la conexi�n:
      ServletContext sc = arg0.getServletContext();
      Connection conn = (Connection) sc.getAttribute("dbConn");
      conn.close();
      Enumeration<Driver> drivers = DriverManager.getDrivers();
      while (drivers.hasMoreElements()) {
        logger.info("DB deregistering drivers");
        Driver driver = drivers.nextElement();
        try {
          DriverManager.deregisterDriver(driver);
          logger.info(String.format("deregistering jdbc driver: %s", driver));
        } catch (SQLException e) {
          logger.severe(String.format("Error deregistering driver %s %s", driver, e));
        }

      }

    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    logger.info("DB destroyed");
  }


}
