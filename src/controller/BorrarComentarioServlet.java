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

package controller;

import dao.CommentDAO;
import dao.JDBCCommentDAOImpl;
import dao.JDBCUserDAOImpl;
import dao.UserDAO;
import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Comment;
import model.User;

/**
 * Created by pablomaciasmu.
 */
@SuppressWarnings("Duplicates")
@WebServlet(name = "BorrarComentarioServlet", urlPatterns = "/lanex/deletecomment")
public class BorrarComentarioServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;
  private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

  protected void doGet(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {

    Connection conn = (Connection) getServletContext().getAttribute("dbConn");
    CommentDAO commentDAO = new JDBCCommentDAOImpl();
    UserDAO userDAO = new JDBCUserDAOImpl();
    userDAO.setConnection(conn);
    commentDAO.setConnection(conn);

    try {

      String id = request.getParameter("id");
      long oid = 0;
      oid = Long.parseLong(id);
      Comment comment = commentDAO.get(oid);
      HttpSession session = request.getSession();
      User user = (User) session.getAttribute("user");
      User user_dest = userDAO.get(comment.getReceiver());
      if (comment != null && user.getIdu() == comment.getSender()) {
        commentDAO.delete(oid);
      }
    } catch (Exception e) {
      response.sendRedirect("panelusuario");
    }
    response.sendRedirect("panelusuario");

  }


  protected void doPost(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {
    doGet(request, response);
  }
}
