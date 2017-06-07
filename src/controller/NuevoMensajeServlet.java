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

import dao.JDBCMensajeImpl;
import dao.JDBCUserDAOImpl;
import dao.MensajeDAO;
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
import model.Mensaje;
import model.User;

/**
 * Created by pablomaciasmu.
 */
@SuppressWarnings("Duplicates")
@WebServlet(name = "NuevoMensajeServlet", urlPatterns = "/lanex/newmensaje", description = "Servlet que permite enviar mensajes")
public class NuevoMensajeServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;
  private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

  protected void doGet(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {

    Connection conn = (Connection) getServletContext().getAttribute("dbConn");
    UserDAO userDAO = new JDBCUserDAOImpl();
    userDAO.setConnection(conn);

    HttpSession session = request.getSession();
    session.removeAttribute("n_receiver");
    session.removeAttribute("mensaje");

    List<User> users = userDAO.getAll();

    //SI ES DISTINTO DEL USUARIO LOGUEADO, INSERTA INFO EN CAMPO PARA

    User user = (User) session.getAttribute("user");
    if (session.getAttribute("idv") != null) {
      long idv = Long.parseLong((String) session.getAttribute("idv"));
      if (user.getIdu() != idv) {
        String n_receiver = userDAO.get(idv).getUsername();
        session.setAttribute("n_receiver", n_receiver);
      }

    }

    request.setAttribute("CheckType", "Mandar");
    session.setAttribute("users", users);
    RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/edit_mensaje.jsp");
    view.forward(request, response);

  }


  protected void doPost(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {

    request.setCharacterEncoding("UTF-8");

    Connection conn = (Connection) getServletContext().getAttribute("dbConn");
    MensajeDAO mensajeDAO = new JDBCMensajeImpl();
    mensajeDAO.setConnection(conn);
    UserDAO userDAO = new JDBCUserDAOImpl();
    userDAO.setConnection(conn);
    List<String> messages = new ArrayList<String>();
    HttpSession session = request.getSession();

    Mensaje mensaje = new Mensaje();

    long id_emisor = ((User) session.getAttribute("user")).getIdu();

    User user_destino = userDAO.get(request.getParameter("receptor"));

    if (user_destino == null) {
      messages.add("Usuario Receptor no encontrado");
    } else {
      mensaje.setSender(id_emisor);
      mensaje.setReceiver(user_destino.getIdu());
      mensaje.setText(request.getParameter("mensaje"));
    }

    if (messages.size() > 0) {
      request.setAttribute("CheckType", "Mandar");
      request.setAttribute("messages", messages);
      RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/edit_mensaje.jsp");
      view.forward(request, response);
      return;
    }

    mensajeDAO.add(mensaje);
    mensaje = null;
    user_destino = null;
    session.removeAttribute("users");

    if (session.getAttribute("idv") != null) {
      response.sendRedirect("panelusuario?id=" + session.getAttribute("idv"));
    } else {
      response.sendRedirect("panelusuario");
    }

  }

}

