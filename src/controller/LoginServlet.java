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
import dao.UserDAO;
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
import util.Encriptar;

/**
 * Created by pablomaciasmu.
 */
@WebServlet(name = "LoginServlet", urlPatterns = "/login", description = "Servlet para realizar el login en la aplicación")
public class LoginServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;
  private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

  protected void doGet(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {

    HttpSession session = request.getSession();
    if (session.getAttribute("user") != null) {
      response.sendRedirect("lanex/panelusuario");
    } else {
      RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/login.jsp");
      view.forward(request, response);
    }
  }


  protected void doPost(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {

    logger.info("Atendiendo POST");
    Connection conn = (Connection) getServletContext().getAttribute("dbConn");
    UserDAO userDAO = new JDBCUserDAOImpl();
    userDAO.setConnection(conn);

    String username = request.getParameter("username");
    String password = Encriptar.sha1(request.getParameter("password"));

    logger.info("credentials: " + username + " - " + password);

    List<User> users = userDAO.getAll();
    User user = userDAO.get(username);

    if (user != null && user.getPassword().equals(password)) {
      HttpSession session = request.getSession();
      session.setAttribute("user", user);
      response.sendRedirect("pages/inicio.html");
      System.out.println("Contraseña correcta");
    } else {
      request.setAttribute("messages", "Usuario o contraseña incorrecta!!");
      RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/login.jsp");
      view.forward(request, response);
    }

  }
}
