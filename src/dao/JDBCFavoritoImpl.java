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

/**
 * Created by pablomaciasmu.
 */
public class JDBCFavoritoImpl implements FavoritoDAO {

  private static final Logger logger = Logger.getLogger(JDBCUsers_LanguagesDAOImpl.class.getName());
  private Connection conn;

  @Override
  public List<User> getAllByUser(long idu) {
    if (conn == null) {
      return null;
    }

    ArrayList<User> user_favoritos = new ArrayList<User>();
    try {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(
          "SELECT u.* FROM Favoritos f INNER JOIN User u ON f.idfavorito = u.idu WHERE f.idu = "
              + idu);

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
        user_favoritos.add(user);
      }

    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return user_favoritos;
  }

  @Override
  public boolean add(long idu, long idfavorito) {
    if (conn != null) {

      Statement stmt;
      try {
        stmt = conn.createStatement();
        stmt.executeUpdate(
            "INSERT INTO Favoritos (idu, idfavorito) VALUES ('" + idu + "', '" + idfavorito + "')");

      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        return false;
      }
      return true;
    }
    return false;

  }

  @Override
  public boolean delete(long idu, long idfavorito) {

    if (conn != null) {

      Statement stmt;
      try {
        stmt = conn.createStatement();
        stmt.executeUpdate(
            "DELETE FROM Favoritos WHERE idu=" + idu + " AND idfavorito = " + idfavorito);

      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        return false;
      }
      return true;
    }
    return false;

  }

  @Override
  public void setConnection(Connection conn) {
    this.conn = conn;
  }
}
