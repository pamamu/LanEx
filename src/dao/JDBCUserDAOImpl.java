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

package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import model.User;

public class JDBCUserDAOImpl implements UserDAO {

  private static final Logger logger = Logger.getLogger(JDBCUserDAOImpl.class.getName());
  private Connection conn;

  @Override
  public User get(long idu) {
    if (conn == null) {
      return null;
    }

    User user = null;

    try {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT * FROM User WHERE idu =" + idu);
      if (!rs.next()) {
        return null;
      }
      user = new User();
      user.setIdu(rs.getLong("idu"));
      user.setUsername(rs.getString("username"));
      user.setEmail(rs.getString("email"));
      user.setPassword(rs.getString("password"));
      user.setNombre(rs.getString("nombre"));
      user.setApellidos(rs.getString("apellidos"));
      user.setNacionalidad(rs.getString("nacionalidad"));
      user.setFec_nac(rs.getString("fec_nac"));
      user.setTelefono(rs.getString("telefono"));
      user.setTwitter(rs.getString("twitter"));
      user.setImagen(rs.getInt("imagen"));
      user.setPuntos(rs.getInt("puntos"));
      user.setContacto_preferido(rs.getString("contacto_preferido"));
      logger.info(
          "fetching User by idu: " + idu + " -> " + user.getIdu() + " " + user.getUsername() + " "
              + user.getEmail() + " " + user.getPassword());
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return user;
  }

  @Override
  public User get(String username) {
    if (conn == null) {
      return null;
    }

    User user = null;

    try {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT * FROM User WHERE username ='" + username + "'");
      if (!rs.next()) {
        return null;
      }
      user = new User();
      user.setIdu(rs.getLong("idu"));
      user.setUsername(rs.getString("username"));
      user.setEmail(rs.getString("email"));
      user.setPassword(rs.getString("password"));
      user.setNombre(rs.getString("nombre"));
      user.setApellidos(rs.getString("apellidos"));
      user.setNacionalidad(rs.getString("nacionalidad"));
      user.setFec_nac(rs.getString("fec_nac"));
      user.setTelefono(rs.getString("telefono"));
      user.setImagen(rs.getInt("imagen"));
      user.setTwitter(rs.getString("twitter"));
      user.setImagen(rs.getInt("imagen"));
      user.setPuntos(rs.getInt("puntos"));
      user.setContacto_preferido(rs.getString("contacto_preferido"));
      logger.info(
          "fetching User by name: " + username + " -> " + user.getIdu() + " " + user.getUsername()
              + " " + user.getEmail() + " " + user.getPassword());
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return user;
  }


  public List<User> getAll() {

    if (conn == null) {
      return null;
    }

    ArrayList<User> users = new ArrayList<User>();
    try {
      Statement stmt;
      ResultSet rs;
      synchronized (conn) {
        stmt = conn.createStatement();
        rs = stmt.executeQuery("SELECT * FROM User");
      }
      while (rs.next()) {
        User user = new User();
        user.setIdu(rs.getLong("idu"));
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        //user.setPassword(rs.getString("password"));
        user.setPassword(
            "********");//al devolver todos los usuarios no devolvemos la contrase�a visible
        user.setNombre(rs.getString("nombre"));
        user.setApellidos(rs.getString("apellidos"));
        user.setNacionalidad(rs.getString("nacionalidad"));
        user.setFec_nac(rs.getString("fec_nac"));
        user.setTelefono(rs.getString("telefono"));
        user.setImagen(rs.getInt("imagen"));
        user.setTwitter(rs.getString("twitter"));
        user.setImagen(rs.getInt("imagen"));
        user.setPuntos(rs.getInt("puntos"));
        user.setContacto_preferido(rs.getString("contacto_preferido"));
        users.add(user);
        logger.info(
            "fetching users: " + user.getIdu() + " " + user.getUsername() + " " + user.getEmail()
                + " " + user.getPassword());

      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return users;
  }


  @Override
  public long add(User user) {
    long idu = -1;
    long lastidu = -1;
    if (conn != null) {

      Statement stmt;

      try {
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM sqlite_sequence WHERE name ='User'");
        if (!rs.next()) {
          return -1;
        }
        lastidu = rs.getLong("seq");

      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

      try {
        stmt = conn.createStatement();
        stmt.executeUpdate(
            "INSERT INTO User (username,email,password, nombre, apellidos, nacionalidad, fec_nac, telefono, twitter, contacto_preferido, puntos,imagen) VALUES('"
                + user.getUsername() + "','"
                + user.getEmail() + "','"
                + user.getPassword() + "','"
                + user.getNombre() + "','"
                + user.getApellidos() + "','"
                + user.getNacionalidad() + "','"
                + user.getFec_nac() + "','"
                + user.getTelefono() + "','"
                + user.getTwitter() + "','"
                + user.getContacto_preferido() + "','"
                + user.getPuntos() + "','"
                + user.getImagen() + "')");
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

      try {
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM sqlite_sequence WHERE name ='User'");
        if (!rs.next()) {
          return -1;
        }
        idu = rs.getLong("seq");
        if (idu <= lastidu) {
          return -1;
        }

        logger.info(
            "CREATING User(" + idu + "): " + user.getUsername() + " " + user.getEmail() + " " + user
                .getPassword());
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

    }
    return idu;
  }

  @Override
  public boolean save(User user) {
    boolean done = false;
    if (conn != null) {

      Statement stmt;
      try {
        stmt = conn.createStatement();
        stmt.executeUpdate("UPDATE User SET username='" + user.getUsername()
            + "', email='" + user.getEmail()
            + "', password='" + user.getPassword()
            + "', nombre='" + user.getNombre()
            + "', apellidos='" + user.getApellidos()
            + "', nacionalidad='" + user.getNacionalidad()
            + "', fec_nac='" + user.getFec_nac()
            + "', telefono='" + user.getTelefono()
            + "', twitter='" + user.getTwitter()
            + "', imagen='" + user.getImagen()
            + "', contacto_preferido='" + user.getContacto_preferido()
            + "', puntos='" + user.getPuntos()
            + "' WHERE idu = " + user.getIdu());
        logger.info(
            "updating User: " + user.getIdu() + " " + user.getUsername() + " " + user.getEmail()
                + " " + user.getPassword());
        done = true;
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

    }
    return done;

  }

  @Override
  public boolean delete(long idu) {
    boolean done = false;
    if (conn != null) {

      Statement stmt;
      try {
        stmt = conn.createStatement();
        stmt.executeUpdate("PRAGMA FOREIGN_KEYS = ON ");
        stmt.executeUpdate("DELETE FROM User WHERE idu =" + idu);
        logger.info("deleting User: " + idu);
        done = true;
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    return done;
  }

  @Override
  public void setConnection(Connection conn) {
    // TODO Auto-generated method stub
    this.conn = conn;
  }

  @Override
  public List<User> getDetails(String username, String nacionalidad, String idioma, String nivel,
      String skill, boolean ordenar) {
    if (conn == null) {
      return null;
    }

    ArrayList<User> users = new ArrayList<User>();
    try {
      Statement stmt;
      ResultSet rs;
      synchronized (conn) {
        stmt = conn.createStatement();

        if (ordenar) {
          rs = stmt.executeQuery("SELECT DISTINCT u.*\n"
              + "FROM User u INNER JOIN Users_Languages ul ON u.idu = ul.idu\n"
              + "  INNER JOIN Language l ON ul.idl = l.idl\n"
              + "WHERE (u.username LIKE '" + username + "') AND (u.nacionalidad LIKE '"
              + nacionalidad
              + "') AND (l.langname LIKE '" + idioma + "') AND\n"
              + "      (ul." + skill + " LIKE '" + nivel + "')\n"
              + "ORDER BY u.puntos DESC ;");
        } else {
          rs = stmt.executeQuery("SELECT DISTINCT u.*\n"
              + "FROM User u INNER JOIN Users_Languages ul ON u.idu = ul.idu\n"
              + "  INNER JOIN Language l ON ul.idl = l.idl\n"
              + "WHERE (u.username LIKE '" + username + "') AND (u.nacionalidad LIKE '"
              + nacionalidad
              + "') AND (l.langname LIKE '" + idioma + "') AND\n"
              + "      (ul." + skill + " LIKE '" + nivel + "');");
        }
      }
      while (rs.next()) {
        User user = new User();
        user.setIdu(rs.getLong("idu"));
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        //user.setPassword(rs.getString("password"));
        user.setPassword(
            "********");//al devolver todos los usuarios no devolvemos la contrase�a visible
        user.setNombre(rs.getString("nombre"));
        user.setApellidos(rs.getString("apellidos"));
        user.setNacionalidad(rs.getString("nacionalidad"));
        user.setFec_nac(rs.getString("fec_nac"));
        user.setTelefono(rs.getString("telefono"));
        user.setImagen(rs.getInt("imagen"));
        user.setTwitter(rs.getString("twitter"));
        user.setPuntos(rs.getInt("puntos"));
        user.setContacto_preferido(rs.getString("contacto_preferido"));
        users.add(user);
        logger.info(
            "fetching users: " + user.getIdu() + " " + user.getUsername() + " " + user.getEmail()
                + " " + user.getPassword());

      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return users;
  }

  @Override
  public void darpuntos(long idu, int puntos) {
    if (conn != null) {
      Statement stmt;
      try {
        stmt = conn.createStatement();
        stmt.executeUpdate("UPDATE User SET puntos = " + puntos + " WHERE idu = " + idu);
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }

@Override
public User getSC(long idu) {
	if (conn == null) {
	      return null;
	    }

	    User user = null;

	    try {
	      Statement stmt = conn.createStatement();
	      ResultSet rs = stmt.executeQuery("SELECT * FROM User WHERE idu =" + idu);
	      if (!rs.next()) {
	        return null;
	      }
	      user = new User();
	      user.setIdu(rs.getLong("idu"));
	      user.setUsername(rs.getString("username"));
	      user.setEmail(rs.getString("email"));
	      user.setPassword("********");
	      user.setNombre(rs.getString("nombre"));
	      user.setApellidos(rs.getString("apellidos"));
	      user.setNacionalidad(rs.getString("nacionalidad"));
	      user.setFec_nac(rs.getString("fec_nac"));
	      user.setTelefono(rs.getString("telefono"));
	      user.setTwitter(rs.getString("twitter"));
	      user.setImagen(rs.getInt("imagen"));
	      user.setPuntos(rs.getInt("puntos"));
	      user.setContacto_preferido(rs.getString("contacto_preferido"));
	      logger.info(
	          "fetching User by idu: " + idu + " -> " + user.getIdu() + " " + user.getUsername() + " "
	              + user.getEmail() + " " + user.getPassword());
	    } catch (SQLException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	    }
	    return user;
}

@Override
public User getSC(String username) {
	if (conn == null) {
	      return null;
	    }

	    User user = null;

	    try {
	      Statement stmt = conn.createStatement();
	      ResultSet rs = stmt.executeQuery("SELECT * FROM User WHERE username ='" + username + "'");
	      if (!rs.next()) {
	        return null;
	      }
	      user = new User();
	      user.setIdu(rs.getLong("idu"));
	      user.setUsername(rs.getString("username"));
	      user.setEmail(rs.getString("email"));
	      user.setPassword("********");
	      user.setNombre(rs.getString("nombre"));
	      user.setApellidos(rs.getString("apellidos"));
	      user.setNacionalidad(rs.getString("nacionalidad"));
	      user.setFec_nac(rs.getString("fec_nac"));
	      user.setTelefono(rs.getString("telefono"));
	      user.setImagen(rs.getInt("imagen"));
	      user.setTwitter(rs.getString("twitter"));
	      user.setImagen(rs.getInt("imagen"));
	      user.setPuntos(rs.getInt("puntos"));
	      user.setContacto_preferido(rs.getString("contacto_preferido"));
	      logger.info(
	          "fetching User by name: " + username + " -> " + user.getIdu() + " " + user.getUsername()
	              + " " + user.getEmail() + " " + user.getPassword());
	    } catch (SQLException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	    }
	    return user;
}
  
  
  
}
