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
import model.Users_Languages;


@SuppressWarnings("ALL")
public class JDBCUsers_LanguagesDAOImpl implements Users_LanguagesDAO {

  private static final Logger logger = Logger.getLogger(JDBCUsers_LanguagesDAOImpl.class.getName());
  private Connection conn;

  @Override
  public List<Users_Languages> getAll() {

    if (conn == null) {
      return null;
    }

    ArrayList<Users_Languages> users_LanguagesList = new ArrayList<Users_Languages>();
    try {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT * FROM Users_Languages");

      while (rs.next()) {
        Users_Languages users_Languages = new Users_Languages();
        users_Languages.setIdu(rs.getLong("idu"));
        users_Languages.setIdl(rs.getLong("idl"));
        users_Languages.setLevel(rs.getString("level"));
        users_Languages.setLevelSpeaking(rs.getString("level_speaking"));
        users_Languages.setLevelWriting(rs.getString("level_writing"));
        users_Languages.setLevelReading(rs.getString("level_reading"));

        users_LanguagesList.add(users_Languages);
        logger.info(
            "fetching users_LanguagesList: " + users_Languages.getIdu() + " " + users_Languages
                .getIdl() + " " + users_Languages.getLevel());

      }

    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return users_LanguagesList;
  }

  @Override
  public List<Users_Languages> getAllByUser(long idu) {

    if (conn == null) {
      return null;
    }

    ArrayList<Users_Languages> users_LanguagesList = new ArrayList<Users_Languages>();
    try {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT * FROM Users_Languages WHERE idu=" + idu);

      while (rs.next()) {
        Users_Languages users_Languages = new Users_Languages();
        users_Languages.setIdu(rs.getLong("idu"));
        users_Languages.setIdl(rs.getLong("idl"));
        users_Languages.setLevel(rs.getString("level"));
        users_Languages.setLevelSpeaking(rs.getString("level_speaking"));
        users_Languages.setLevelWriting(rs.getString("level_writing"));
        users_Languages.setLevelReading(rs.getString("level_reading"));

        users_LanguagesList.add(users_Languages);
        logger.info(
            "fetching users_LanguagesList: " + users_Languages.getIdu() + " " + users_Languages
                .getIdl() + " " + users_Languages.getLevel());

      }

    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return users_LanguagesList;
  }

  @Override
  public List<Users_Languages> getAllByLanguage(long idl) {

    if (conn == null) {
      return null;
    }

    ArrayList<Users_Languages> users_LanguagesList = new ArrayList<Users_Languages>();
    try {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT * FROM Users_Languages WHERE idl=" + idl);

      while (rs.next()) {
        Users_Languages users_Languages = new Users_Languages();
        users_Languages.setIdu(rs.getLong("idu"));
        users_Languages.setIdl(rs.getLong("idl"));
        users_Languages.setLevel(rs.getString("level"));
        users_Languages.setLevelSpeaking(rs.getString("level_speaking"));
        users_Languages.setLevelWriting(rs.getString("level_writing"));
        users_Languages.setLevelReading(rs.getString("level_reading"));

        users_LanguagesList.add(users_Languages);
        logger.info(
            "fetching users_LanguagesList: " + users_Languages.getIdu() + " " + users_Languages
                .getIdl() + " " + users_Languages.getLevel());

      }

    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return users_LanguagesList;
  }


  @Override
  public Users_Languages get(long idu, long idl) {
    if (conn == null) {
      return null;
    }

    Users_Languages users_Languages = null;

    try {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt
          .executeQuery("SELECT * FROM Users_Languages WHERE idu=" + idu + " AND idl=" + idl);
      if (!rs.next()) {
        return null;
      }
      users_Languages = new Users_Languages();
      users_Languages.setIdu(rs.getLong("idu"));
      users_Languages.setIdl(rs.getLong("idl"));
      users_Languages.setLevel(rs.getString("level"));
      users_Languages.setLevelSpeaking(rs.getString("level_speaking"));
      users_Languages.setLevelWriting(rs.getString("level_writing"));
      users_Languages.setLevelReading(rs.getString("level_reading"));

      logger.info("fetching users_Languages by idu: " + users_Languages.getIdu() + "  and idl: "
          + users_Languages.getIdl() + " " + users_Languages.getLevel());

    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return users_Languages;
  }


  @Override
  public boolean add(Users_Languages users_Languages) {
    boolean done = false;
    if (conn != null) {

      Statement stmt;
      try {
        stmt = conn.createStatement();
        stmt.executeUpdate(
            "INSERT INTO Users_Languages (idu,idl,level, level_speaking, level_writing, level_reading) VALUES('"
                +
                users_Languages.getIdu() + "','" +
                users_Languages.getIdl() + "','" +
                users_Languages.getLevel() + "','" +
                users_Languages.getLevelSpeaking() + "','" +
                users_Languages.getLevelWriting() + "','" +
                users_Languages.getLevelReading()+"')");

        logger.info(
            "creating Users_Languages:(" + users_Languages.getIdu() + " " + users_Languages.getIdl()
                + " " + users_Languages.getLevel());
        done = true;
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    return done;
  }

  @Override
  public boolean save(Users_Languages users_Languages) {
    boolean done = false;
    if (conn != null) {

      Statement stmt;
      try {
        stmt = conn.createStatement();
        stmt.executeUpdate("UPDATE Users_Languages SET level_speaking='" +
            users_Languages.getLevelSpeaking() + "', level_reading='" +
            users_Languages.getLevelReading() + "', level_writing='" +
            users_Languages.getLevelWriting() + "' WHERE idu = " + users_Languages.getIdu() + " AND idl="
            + users_Languages.getIdl());

        logger.info("updating Users_Languages: " + users_Languages.getIdu() + "  and idl: "
            + users_Languages.getIdl() + " " + users_Languages.getLevel());

        done = true;
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    return done;
  }

  @Override
  public boolean delete(long idu, long idl) {
    boolean done = false;
    if (conn != null) {

      Statement stmt;
      try {
        stmt = conn.createStatement();
        stmt.executeUpdate("DELETE FROM Users_Languages WHERE idu =" + idu + " AND idl=" + idl);
        logger.info("deleting Users_Languages: " + idu + " , idl=" + idl);
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


}
