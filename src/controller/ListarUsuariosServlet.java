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
import model.User;

/**
 * Created by pablomaciasmu.
 */
@WebServlet(urlPatterns = "/lanex/listusers")
public class ListarUsuariosServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;
  private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

  protected void doGet(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {

    Connection conn = (Connection) getServletContext().getAttribute("dbConn");
    UserDAO userDAO = new JDBCUserDAOImpl();
    userDAO.setConnection(conn);

    String username = request.getParameter("username");
    String tipo_busq = request.getParameter("tipo_busq");
    switch (tipo_busq){
      case "empieza":
        username = username + "%";
        break;
      case "contiene":
        username = "%" + username + "%";
        break;
      case "termina":
        username = "%" + username;
        break;
    }
    String nacionalidad = request.getParameter("nacionalidad");
    if (nacionalidad.equals("otro")) {
      nacionalidad = request.getParameter("otranacionalidad");
      nacionalidad = "%" + nacionalidad + "%";
    }
    String idioma = request.getParameter("idioma");
    if (idioma.equals("otro-i")) {
      idioma = request.getParameter("otroidioma");
    }
    String nivel = request.getParameter("nivel");
    String skill = request.getParameter("skill");
    skill = "level_" + skill;
    String ordenar_s = request.getParameter("ordenar");
    boolean ordenar = false;
    if(ordenar_s!=null)
    ordenar = (request.getParameter("ordenar").equals("ok"));



    List<User> users = userDAO.getDetails(username, nacionalidad, idioma, nivel, skill, ordenar);

    request.setAttribute("users", users);
    RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/listUsers.jsp");
    view.forward(request, response);

  }


  protected void doPost(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {
    doGet(request, response);
  }
}
