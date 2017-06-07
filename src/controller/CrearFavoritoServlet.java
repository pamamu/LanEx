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

import dao.FavoritoDAO;
import dao.JDBCFavoritoImpl;
import dao.JDBCUserDAOImpl;
import dao.UserDAO;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
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

/**
 * Created by pablomaciasmu.
 */
@WebServlet(name = "CrearFavoritoServlet", urlPatterns = "/lanex/newfavorito")
public class CrearFavoritoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);

		List<User> users = userDAO.getAll();

		request.setAttribute("users", users);
		request.setAttribute("CheckType", "Crear");
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/crearFavorito.jsp");
		view.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		FavoritoDAO favoritoDAO = new JDBCFavoritoImpl();
		favoritoDAO.setConnection(conn);
		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);
		List<User> users = userDAO.getAll();
		List<String> messages = new ArrayList<String>();
		HttpSession session = request.getSession();

		long idu = ((User) session.getAttribute("user")).getIdu();

		User user_destino = userDAO.get(request.getParameter("idfavorito"));
		if (user_destino == null) {
			messages.add("Usuario Receptor no encontrado");
		}
		if (user_destino.getIdu() == idu) {
			messages.add("Usuario Receptor no puede ser el emisor");
		}
		if (!favoritoDAO.add(idu, user_destino.getIdu())) {
			messages.add("Relación ya existente");
		}

		if (messages.size() > 0) {
			request.setAttribute("users", users);
			request.setAttribute("messages", messages);
			request.setAttribute("CheckType", "Crear");
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/crearFavorito.jsp");
			view.forward(request, response);
		} else {
			response.sendRedirect("panelusuario");
		}

	}
}
