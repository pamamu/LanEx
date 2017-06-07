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
import dao.FavoritoDAO;
import dao.JDBCCommentDAOImpl;
import dao.JDBCFavoritoImpl;
import dao.JDBCLanguageDAOImpl;
import dao.JDBCMensajeImpl;
import dao.JDBCUserDAOImpl;
import dao.JDBCUsers_LanguagesDAOImpl;
import dao.LanguageDAO;
import dao.MensajeDAO;
import dao.UserDAO;
import dao.Users_LanguagesDAO;
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
import model.Comment;
import model.Mensaje;
import model.User;
import model.Users_Languages;
import util.Pair;
import util.Triplet;

/**
 * Created by pablomaciasmu.
 */
@WebServlet(name = "PanelUsuarioServlet", urlPatterns = "/lanex/panelusuario", description = "Servlet encargado de mostrar la información de los usuarios")
public class PanelUsuarioServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;
  private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

  protected void doGet(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {

    Connection conn = (Connection) getServletContext().getAttribute("dbConn");
    Users_LanguagesDAO users_languagesDAO = new JDBCUsers_LanguagesDAOImpl();
    users_languagesDAO.setConnection(conn);
    UserDAO userDAO = new JDBCUserDAOImpl();
    userDAO.setConnection(conn);
    LanguageDAO languageDAO = new JDBCLanguageDAOImpl();
    languageDAO.setConnection(conn);
    CommentDAO commentDAO = new JDBCCommentDAOImpl();
    commentDAO.setConnection(conn);
    FavoritoDAO favoritoDAO = new JDBCFavoritoImpl();
    favoritoDAO.setConnection(conn);
    MensajeDAO mensajeDAO = new JDBCMensajeImpl();
    mensajeDAO.setConnection(conn);
    HttpSession session = request.getSession();

    //GET DE ID DE USUARIO Y DE PARAMETRO
    String id = request.getParameter("id");
    long oid = 0;
    if (id != null) {
      oid = Long.parseLong(id);
    }

    User user = (User) session.getAttribute("user");

    if (id == null || (id != null && oid == user.getIdu())) {
      request.setAttribute("tipo", "logueado");
      session.removeAttribute("idv");
    } else {
      session.setAttribute("idv", id);
      user = userDAO.get(oid);
    }

    if (user==null){
      response.sendRedirect("panelusuario");
      return;
    }
    //PARTE IDIOMAS

    List<Users_Languages> users_languages = users_languagesDAO.getAllByUser(user.getIdu());
    List<Pair<String, Users_Languages>> usersLanguages = new ArrayList<>();

    String nombre_idioma;
    for (Users_Languages obj : users_languages) {
      nombre_idioma = languageDAO.get(obj.getIdl()).getLangname();
      usersLanguages.add(new Pair<>(nombre_idioma, obj));
    }
    request.setAttribute("usersLanguages", usersLanguages);

    //PARTE COMENTARIOS RECIBIDOS

    List<Triplet<String, Integer, Comment>> commentsReceiver = commentDAO
        .getAllByReceiverName(user.getIdu());
    request.setAttribute("commentsReceiver", commentsReceiver);

    //PARTE COMENTARIOS ENVIADOS

    List<Triplet<Integer, String, Comment>> commentsSender = commentDAO
        .getAllBySenderName(user.getIdu());
    request.setAttribute("commentsSender", commentsSender);

    List<Triplet<String, Integer, Mensaje>> mensajesRecibidos = mensajeDAO.getAllByReceiverName(user.getIdu());
    request.setAttribute("mensajesrecibidos", mensajesRecibidos);

    List<Triplet<Integer, String, Mensaje>> mensajesEnviados = mensajeDAO.getAllBySenderName(user.getIdu());
    request.setAttribute("mensajesenviados", mensajesEnviados);


    //PARTE CONTACTOS FAVORITOS

    List<User> favoritos = favoritoDAO.getAllByUser(user.getIdu());
    session.setAttribute("favoritos", favoritos);

    //REDIRECCIÓN

    request.setAttribute("userv", user);
    RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/panel_usuario.jsp");
    view.forward(request, response);


  }


  protected void doPost(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {

    doGet(request, response);

  }
}
