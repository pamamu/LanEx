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

import dao.JDBCLanguageDAOImpl;
import dao.LanguageDAO;
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
import model.Language;

/**
 * Created by pablomaciasmu.
 */
@WebServlet(name = "BuscarUsuarioServlet", urlPatterns = "/lanex/searchusers")
public class BuscarUsuarioServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;
  private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

  protected void doGet(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {

    Connection conn = (Connection) getServletContext().getAttribute("dbConn");
    LanguageDAO languageDAO = new JDBCLanguageDAOImpl();
    languageDAO.setConnection(conn);

    List<Language> languages = languageDAO.getAll();
    request.setAttribute("idiomas", languages);
    RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/buscar_usuarios.jsp");
    view.forward(request, response);

  }


  protected void doPost(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {

    doGet(request,response);

  }
}
