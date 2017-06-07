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
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.User;

/**
 * Created by pablomaciasmu.
 */
@WebServlet(name = "ValorarServlet", urlPatterns = "/lanex/valorar", description = "Servlet que permite valorar al usuario")
public class ValorarServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;
  private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

  protected void doGet(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {

    Connection conn = (Connection) getServletContext().getAttribute("dbConn");
    UserDAO userDAO = new JDBCUserDAOImpl();
    userDAO.setConnection(conn);

    String id_s = request.getParameter("id");
    String punt_s = request.getParameter("punt");

    if (id_s != null && punt_s != null && (punt_s.equals("1") || punt_s.equals("0"))) {
      long idu = Long.parseLong(id_s);
      int punt = Integer.parseInt(punt_s), puntos;
      User user = userDAO.get(idu);
      if (punt == 1) {
        userDAO.darpuntos(idu, user.getPuntos() + 1);
      } else {
        puntos = user.getPuntos() - 1;
        if (puntos > 0) {
          userDAO.darpuntos(idu, puntos);
        } else {
          userDAO.darpuntos(idu, 0);
        }
      }
      response.sendRedirect("panelusuario?id=" + request.getParameter("id"));
    } else {
      response.sendRedirect("panelusuario");
    }

  }


  protected void doPost(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {

    doPost(request, response);

  }
}
