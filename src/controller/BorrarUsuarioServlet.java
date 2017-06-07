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

import dao.JDBCUserDAOImpl;
import dao.JDBCUsers_LanguagesDAOImpl;
import dao.UserDAO;
import dao.Users_LanguagesDAO;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.User;
import model.Users_Languages;

/**
 * Created by pablomaciasmu.
 */
@WebServlet(name = "BorrarUsuarioServlet", urlPatterns = "/lanex/deleteuser", description = "Servlet encargado de gestionar el borrado de un usuario")
public class BorrarUsuarioServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;
  private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

  protected void doGet(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {

    HttpSession session = request.getSession();
    User user = (User) session.getAttribute("user");
    session.setAttribute("userb", user);
    request.setAttribute("CheckType", "Borrado");
    RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/checkUser.jsp");
    view.forward(request, response);

  }


  protected void doPost(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {

    Connection conn = (Connection) getServletContext().getAttribute("dbConn");
    UserDAO userDAO = new JDBCUserDAOImpl();
    Users_LanguagesDAO users_languagesDAO = new JDBCUsers_LanguagesDAOImpl();
    users_languagesDAO.setConnection(conn);
    userDAO.setConnection(conn);
    HttpSession session = request.getSession();
    logger.info("Usuario a eliminar confirmado con id de sesión: " + session.getId());
    User user = (User) session.getAttribute("user");

    if (user != null) {
      userDAO.delete(user.getIdu());
      List<Users_Languages> users_languages = users_languagesDAO.getAllByUser(user.getIdu());
      for (Users_Languages users_languages1 :
          users_languages) {
        users_languagesDAO.delete(user.getIdu(), users_languages1.getIdl());
      }
      session.removeAttribute("user");
      user = null;
    }

    response.sendRedirect("index");


  }
}
