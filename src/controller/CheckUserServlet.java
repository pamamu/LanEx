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
import model.Language;
import model.User;
import model.Users_Languages;
import util.Pair;

/**
 * Created by pablomaciasmu.
 */
@WebServlet(name = "CheckUserServlet", urlPatterns = "/checkUser", description = "Server que confirma registro de usuario")
public class CheckUserServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;
  private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

  protected void doGet(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {
    HttpSession session = request.getSession();

    request.setAttribute("CheckType", "Registro");
    RequestDispatcher view;
    if (session.getAttribute("userb") != null) {
      view = request.getRequestDispatcher("/WEB-INF/checkUser.jsp");
      view.forward(request, response);
    } else {
      response.sendRedirect("registro");
    }


  }


  protected void doPost(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {

    logger.info("Atendiendo POST");
    Connection conn = (Connection) getServletContext().getAttribute("dbConn");
    UserDAO userDAO = new JDBCUserDAOImpl();
    userDAO.setConnection(conn);
    Users_LanguagesDAO users_languagesDAO = new JDBCUsers_LanguagesDAOImpl();
    users_languagesDAO.setConnection(conn);

    HttpSession session = request.getSession();
    logger.info("Usuario confirmado con id de sesión: " + session.getId());
    User user = (User) session.getAttribute("userb");
    List<Pair<Language, Users_Languages>> usersLanguages = (List<Pair<Language, Users_Languages>>) session
        .getAttribute("usersLanguages");
    long id = -1;
    if (user != null) {
      userDAO.add(user);
      id = userDAO.get(user.getUsername()).getIdu();
      user.setIdu(id);
      session.removeAttribute("userb");
      user = null;
    }

    if (usersLanguages != null) {
      for (Pair<Language, Users_Languages> elem : usersLanguages) {
        elem.getSecond().setIdu(id);
        users_languagesDAO.add(elem.getSecond());
      }
      session.removeAttribute("usersLanguages");
      usersLanguages = null;
    }

    response.sendRedirect("login");

  }
}
