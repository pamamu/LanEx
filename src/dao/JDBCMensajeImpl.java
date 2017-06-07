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
import model.Mensaje;
import util.Triplet;

/**
 * Created by pablomaciasmu.
 */
public class JDBCMensajeImpl implements MensajeDAO {

  private static final Logger logger = Logger.getLogger(JDBCCommentDAOImpl.class.getName());
  private Connection conn;


  @Override
  public void setConnection(Connection conn) {
    this.conn = conn;
  }

  @Override
  public List<Triplet<Integer, String, Mensaje>> getAllBySenderName(long sender) {
    if (conn == null) {
      return null;
    }

    ArrayList<Triplet<Integer, String, Mensaje>> mensajes = new ArrayList<>();
    try {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT\n"
          + "  User2.username n_receiver,\n"
          + "  User2.imagen imagen,\n"
          + "  Mensaje.*\n"
          + "FROM Mensaje\n"
          + "  INNER JOIN User AS User1 ON (Mensaje.sender = User1.idu)\n"
          + "  INNER JOIN User AS User2 ON (Mensaje.receiver = User2.idu)\n"
          + "WHERE sender = " + sender);
      String n_receiver;
      int imagen_emisor;
      while (rs.next()) {
        n_receiver = rs.getString("n_receiver");
        imagen_emisor = rs.getInt("imagen");
        Mensaje mensaje = new Mensaje();
        mensaje.setIdm(rs.getLong("idm"));
        mensaje.setSender(rs.getLong("sender"));
        mensaje.setReceiver(rs.getLong("receiver"));
        mensaje.setTimeStamp(rs.getLong("timestamp"));
        mensaje.setText(rs.getString("text"));

        mensajes.add(new Triplet<>(imagen_emisor, n_receiver, mensaje));


      }

    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return mensajes;
  }

  @Override
  public List<Triplet<String, Integer, Mensaje>> getAllByReceiverName(long receiver) {

    if (conn == null) {
      return null;
    }

    ArrayList<Triplet<String, Integer, Mensaje>> mensajes = new ArrayList<>();
    try {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT\n"
          + "  User1.username n_sender,\n"
          + "  User1.imagen imagen,\n"
          + "  Mensaje.*\n"
          + "FROM Mensaje\n"
          + "  INNER JOIN User AS User1 ON (Mensaje.sender = User1.idu)\n"
          + "  INNER JOIN User AS User2 ON (Mensaje.receiver = User2.idu)\n"
          + "WHERE receiver = " + receiver);
      String n_sender;
      int imagen_emisor;
      while (rs.next()) {
        n_sender = rs.getString("n_sender");
        imagen_emisor = rs.getInt("imagen");
        Mensaje mensaje = new Mensaje();
        mensaje.setIdm(rs.getLong("idm"));
        mensaje.setSender(rs.getLong("sender"));
        mensaje.setReceiver(rs.getLong("receiver"));
        mensaje.setTimeStamp(rs.getLong("timestamp"));
        mensaje.setText(rs.getString("text"));

        mensajes.add(new Triplet<>(n_sender, imagen_emisor, mensaje));


      }

    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return mensajes;
  }

  @Override
  public boolean add(Mensaje mensaje) {
    if (conn != null) {
      Statement stmt;
      try {
        stmt = conn.createStatement();
        stmt.executeUpdate(
            "INSERT INTO Mensaje (sender, receiver, text) VALUES ('" + mensaje.getSender() + "', '"
                + mensaje.getReceiver() + "', '" + mensaje.getText() + "')");

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
  public List<Mensaje> getAllByReceiver(long receiver) {

    if (conn == null) {
      return null;
    }

    ArrayList<Mensaje> mensajes = new ArrayList<>();
    try {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT *\n"
          + "FROM Mensaje \n"
          + "WHERE receiver = " + receiver);
      while (rs.next()) {
        Mensaje mensaje = new Mensaje();
        mensaje.setIdm(rs.getLong("idm"));
        mensaje.setSender(rs.getLong("sender"));
        mensaje.setReceiver(rs.getLong("receiver"));
        mensaje.setTimeStamp(rs.getLong("timestamp"));
        mensaje.setText(rs.getString("text"));

        mensajes.add(mensaje);

      }

    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return mensajes;
  }

  @Override
  public List<Mensaje> getAllBySender(long sender) {
    if (conn == null) {
      return null;
    }

    ArrayList<Mensaje> mensajes = new ArrayList<>();
    try {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT *\n"
          + "FROM Mensaje \n"
          + "WHERE sender = " + sender);
      while (rs.next()) {
        Mensaje mensaje = new Mensaje();
        mensaje.setIdm(rs.getLong("idm"));
        mensaje.setSender(rs.getLong("sender"));
        mensaje.setReceiver(rs.getLong("receiver"));
        mensaje.setTimeStamp(rs.getLong("timestamp"));
        mensaje.setText(rs.getString("text"));

        mensajes.add(mensaje);

      }

    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return mensajes;
  }
}
