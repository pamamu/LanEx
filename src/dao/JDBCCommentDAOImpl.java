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
import model.Comment;
import util.Triplet;


@SuppressWarnings("ALL")
public class JDBCCommentDAOImpl implements CommentDAO {

  private static final Logger logger = Logger.getLogger(JDBCCommentDAOImpl.class.getName());
  private Connection conn;

  @Override
  public List<Comment> getAll() {

    if (conn == null) {
      return null;
    }

    ArrayList<Comment> commentList = new ArrayList<Comment>();
    try {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT * FROM Comment");

      while (rs.next()) {
        Comment comment = new Comment();
        comment.setIdc(rs.getLong("idc"));
        comment.setSender(rs.getLong("sender"));
        comment.setReceiver(rs.getLong("receiver"));
        comment.setTimeStamp(rs.getLong("timestamp"));
        comment.setText(rs.getString("text"));

        commentList.add(comment);
        logger.info(
            "fetching commentList: " + comment.getIdc() + " " + comment.getSender() + " " + comment
                .getReceiver() + " "
                + comment.getTimeStamp() + " " + comment.getText());

      }

    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return commentList;
  }

  @Override
  public List<Comment> getAllBySender(long sender) {

    if (conn == null) {
      return null;
    }

    ArrayList<Comment> commentList = new ArrayList<Comment>();
    try {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT * FROM Comment WHERE sender=" + sender);

      while (rs.next()) {
        Comment comment = new Comment();
        comment.setIdc(rs.getLong("idc"));
        comment.setSender(rs.getLong("sender"));
        comment.setReceiver(rs.getLong("receiver"));
        comment.setTimeStamp(rs.getLong("timestamp"));
        comment.setText(rs.getString("text"));

        commentList.add(comment);

        logger.info(
            "fetching commentList by sender(" + sender + ": " + comment.getIdc() + " " + comment
                .getSender() + " " + comment.getReceiver() + " "
                + comment.getTimeStamp() + " " + comment.getText());

      }

    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return commentList;
  }

  @Override
  public List<Comment> getAllByReceiver(long receiver) {

    if (conn == null) {
      return null;
    }

    ArrayList<Comment> commentList = new ArrayList<Comment>();
    try {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT * FROM Comment WHERE receiver =" + receiver);

      while (rs.next()) {
        Comment comment = new Comment();
        comment.setIdc(rs.getLong("idc"));
        comment.setSender(rs.getLong("sender"));
        comment.setReceiver(rs.getLong("receiver"));
        comment.setTimeStamp(rs.getLong("timestamp"));
        comment.setText(rs.getString("text"));

        commentList.add(comment);

        logger.info(
            "fetching commentList by receiver(" + receiver + ": " + comment.getIdc() + " " + comment
                .getSender() + " " + comment.getReceiver() + " "
                + comment.getTimeStamp() + " " + comment.getText());

      }

    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return commentList;
  }

  @Override
  public List<Comment> getAllBySenderReceiver(long sender, long receiver) {

    if (conn == null) {
      return null;
    }

    ArrayList<Comment> commentList = new ArrayList<Comment>();
    try {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(
          "SELECT * FROM Comment WHERE sender=" + sender + " AND receiver=" + receiver);

      while (rs.next()) {
        Comment comment = new Comment();
        comment.setIdc(rs.getLong("idc"));
        comment.setSender(rs.getLong("sender"));
        comment.setReceiver(rs.getLong("receiver"));
        comment.setTimeStamp(rs.getLong("timestamp"));
        comment.setText(rs.getString("text"));

        commentList.add(comment);

        logger.info(
            "fetching commentList by sender and receiver(" + receiver + ": " + comment.getIdc()
                + " " + comment.getSender() + " " + comment.getReceiver() + " "
                + comment.getTimeStamp() + " " + comment.getText());

      }

    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return commentList;
  }


  @Override
  public Comment get(long idc) {
    if (conn == null) {
      return null;
    }

    Comment comment = null;

    try {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT * FROM Comment WHERE idc =" + idc);
      if (!rs.next()) {
        return null;
      }
      comment = new Comment();
      comment.setIdc(rs.getLong("idc"));
      comment.setSender(rs.getLong("sender"));
      comment.setReceiver(rs.getLong("receiver"));
      comment.setTimeStamp(rs.getLong("timestamp"));
      comment.setText(rs.getString("text"));

      logger.info(
          "fetching comment by idc(" + idc + ": " + comment.getIdc() + " " + comment.getSender()
              + " " + comment.getReceiver() + " "
              + comment.getTimeStamp() + " " + comment.getText());
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return comment;
  }


  public List<Comment> getAllBySearch(String search) {
    search = search.toUpperCase();
    if (conn == null) {
      return null;
    }

    ArrayList<Comment> commentList = new ArrayList<Comment>();
    try {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt
          .executeQuery("SELECT * FROM Comment WHERE UPPER(text) LIKE '%" + search + "%'");

      while (rs.next()) {
        Comment comment = new Comment();

        comment.setIdc(rs.getLong("idc"));
        comment.setSender(rs.getLong("sender"));
        comment.setReceiver(rs.getLong("receiver"));
        comment.setTimeStamp(rs.getLong("timestamp"));
        comment.setText(rs.getString("text"));

        commentList.add(comment);

        logger.info(
            "fetching commentList by text(" + search + ": " + comment.getIdc() + " " + comment
                .getSender() + " " + comment.getReceiver() + " "
                + comment.getTimeStamp() + " " + comment.getText());

      }

    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return commentList;
  }


  @Override
  public long add(Comment comment) {
    long idc = -1;
    long lastidc = -1;
    if (conn != null) {

      comment.setTimeStamp(System.currentTimeMillis());

      Statement stmt;

      try {
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM sqlite_sequence WHERE name ='Comment'");
        if (!rs.next()) {
          return -1;
        }
        lastidc = rs.getLong("seq");

      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

      try {
        stmt = conn.createStatement();
        stmt.executeUpdate("INSERT INTO Comment (sender,receiver,timestamp,text) VALUES('" +
            comment.getSender() + "','" +
            comment.getReceiver() + "','" +
            comment.getTimeStamp() + "','" +
            comment.getText() + "')");

      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

      try {
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM sqlite_sequence WHERE name ='Comment'");
        if (!rs.next()) {
          return -1;
        }
        idc = rs.getLong("seq");
        if (idc <= lastidc) {
          return -1;
        }

        logger.info(
            "CREATING Comment:(" + idc + ": " + comment.getSender() + " " + comment.getReceiver()
                + " " + comment.getTimeStamp()
                + " " + comment.getText());
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

    }
    return idc;
  }

  @Override
  public boolean save(Comment comment) {
    boolean done = false;
    if (conn != null) {

      Statement stmt;
      try {
        stmt = conn.createStatement();
        stmt.executeUpdate("UPDATE Comment SET text='" +
            comment.getText() + "' WHERE idc = " + comment.getIdc());
        logger.info(
            "updating Comment: " + comment.getIdc() + " " + comment.getSender() + " " + comment
                .getReceiver()
                + " " + comment.getText());
        done = true;
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        return false;
      }
    }
    return done;
  }

  @Override
  public boolean delete(long idc) {
    boolean done = false;
    if (conn != null) {

      Statement stmt;
      try {
        stmt = conn.createStatement();
        stmt.executeUpdate("DELETE FROM Comment WHERE idc =" + idc);
        logger.info("deleting Comment: " + idc);
        done = true;
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        return false;
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
  public List<Triplet<Integer, String, Comment>> getAllBySenderName(long sender) {
    if (conn == null) {
      return null;
    }

    ArrayList<Triplet<Integer, String, Comment>> commentList = new ArrayList<>();
    try {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT\n"
          + "  User2.imagen imagen,\n"
          + "  User2.username n_receiver,\n"
          + "  Comment.*\n"
          + "FROM Comment\n"
          + "  INNER JOIN User AS User1 ON (Comment.sender = User1.idu)\n"
          + "  INNER JOIN User AS User2 ON (Comment.receiver = User2.idu)\n"
          + "WHERE sender = " + sender);
      String n_receiver;
      int imagen_receptor;
      while (rs.next()) {
        imagen_receptor = rs.getInt("imagen");
        n_receiver = rs.getString("n_receiver");
        Comment comment = new Comment();
        comment.setIdc(rs.getLong("idc"));
        comment.setSender(rs.getLong("sender"));
        comment.setReceiver(rs.getLong("receiver"));
        comment.setTimeStamp(rs.getLong("timestamp"));
        comment.setText(rs.getString("text"));

        commentList.add(new Triplet<>(imagen_receptor, n_receiver, comment));

        logger.info(
            "fetching commentList by sender(" + sender + ": " + comment.getIdc() + " " + comment
                .getSender() + " " + comment.getReceiver() + " "
                + comment.getTimeStamp() + " " + comment.getText());

      }

    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return commentList;
  }

  @Override
  public List<Triplet<String, Integer, Comment>> getAllByReceiverName(long receiver) {
    if (conn == null) {
      return null;
    }

    ArrayList<Triplet<String, Integer, Comment>> commentList = new ArrayList<>();
    try {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT\n"
          + "  User1.username n_sender,\n"
          + "  User1.imagen imagen,\n"
          + "  Comment.*\n"
          + "FROM Comment\n"
          + "  INNER JOIN User AS User1 ON (Comment.sender = User1.idu)\n"
          + "  INNER JOIN User AS User2 ON (Comment.receiver = User2.idu)\n"
          + "WHERE receiver = " + receiver);
      String n_sender;
      int imagen_emisor;
      while (rs.next()) {
        n_sender = rs.getString("n_sender");
        imagen_emisor = rs.getInt("imagen");
        Comment comment = new Comment();
        comment.setIdc(rs.getLong("idc"));
        comment.setSender(rs.getLong("sender"));
        comment.setReceiver(rs.getLong("receiver"));
        comment.setTimeStamp(rs.getLong("timestamp"));
        comment.setText(rs.getString("text"));

        commentList.add(new Triplet<>(n_sender, imagen_emisor, comment));

        logger.info(
            "fetching commentList by sender(" + receiver + ": " + comment.getIdc() + " " + comment
                .getSender() + " " + comment.getReceiver() + " "
                + comment.getTimeStamp() + " " + comment.getText());

      }

    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return commentList;
  }
}
