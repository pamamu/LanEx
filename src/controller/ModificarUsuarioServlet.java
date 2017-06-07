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
import dao.JDBCUserDAOImpl;
import dao.JDBCUsers_LanguagesDAOImpl;
import dao.LanguageDAO;
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
import model.Language;
import model.User;
import model.Users_Languages;
import util.Encriptar;
import util.Pair;

/**
 * Created by pablomaciasmu.
 */
@SuppressWarnings("ALL")
@WebServlet(name = "ModificarUsuarioServlet", urlPatterns = "/lanex/modifyuser", description = "Servlet que se encarga de modificar los daros del usuario que está activo en la aplicación")
public class ModificarUsuarioServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;
  private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

  protected void doGet(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {

    //CONEXION Y CREACIÓN DE DAOS
    Connection conn = (Connection) getServletContext().getAttribute("dbConn");
    Users_LanguagesDAO users_languagesDAO = new JDBCUsers_LanguagesDAOImpl();
    users_languagesDAO.setConnection(conn);
    LanguageDAO languageDAO = new JDBCLanguageDAOImpl();
    languageDAO.setConnection(conn);
    HttpSession session = request.getSession();

    //GET DE USUARIO DE LA SESION
    User user = (User) session.getAttribute("user");

    //OBTIENE LOS IDIOMAS QUE HABLA EL USUARIO
    List<Users_Languages> users_languages = users_languagesDAO.getAllByUser(user.getIdu());

    //OBTIENE TODOS LOS IDIOMAS
    List<Language> languages = languageDAO.getAll();

    request.setAttribute("usersLanguages", users_languages);
    request.setAttribute("idiomas", languages);
    request.setAttribute("n_idiomas", users_languages.size());

    request.setAttribute("CheckType", "Modificar Perfil");
    RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/editUser.jsp");
    view.forward(request, response);


  }


  protected void doPost(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {

    request.setCharacterEncoding("UTF-8");

    //CONEXION Y CREACIÓN DE DAOS
    Connection conn = (Connection) getServletContext().getAttribute("dbConn");
    UserDAO userDAO = new JDBCUserDAOImpl();
    userDAO.setConnection(conn);
    LanguageDAO languageDAO = new JDBCLanguageDAOImpl();
    languageDAO.setConnection(conn);
    Users_LanguagesDAO users_languagesDAO = new JDBCUsers_LanguagesDAOImpl();
    users_languagesDAO.setConnection(conn);
    HttpSession session = request.getSession();

    //PARTE USER

    long idu = ((User) session.getAttribute("user")).getIdu();
    User user = new User();

    user.setIdu(idu);
    user.setNombre(request.getParameter("nombre"));
    user.setUsername(request.getParameter("username"));
    user.setApellidos(request.getParameter("apellidos"));
    user.setNacionalidad(request.getParameter("nacionalidad"));
    user.setEmail(request.getParameter("email"));
    user.setFec_nac(request.getParameter("nacim"));
    user.setPassword(request.getParameter("pass"));
    user.setTelefono(request.getParameter("telefono"));
    user.setTwitter(request.getParameter("twitter"));
    user.setImagen(Integer.parseInt(request.getParameter("imagen")));

    logger.info("Nombre usuario: " + user.getUsername());

    List<String> messages = new ArrayList<String>();
    if (!user.validate(messages)) {
      request.setAttribute("messages", messages);
      RequestDispatcher view = request.getRequestDispatcher("WEB-INF/editUser.jsp");
      view.forward(request, response);
      return;
    }

    //ENCRIPTACIÓN DE CONTRASEÑA
    String contraseñaEncriptada = Encriptar.sha1(user.getPassword());
    user.setPassword(contraseñaEncriptada);
    //PARTE USER_LANGUAGES
    int id;
    String idioma;

    Language language;

    List<Pair<Language, Users_Languages>> usersLanguages = new ArrayList<>();

    if (request.getParameter("idio1") != null) {
      Users_Languages usersLanguages1 = new Users_Languages();
      idioma = request.getParameter("idioma1");
      language = languageDAO.get(idioma);
      id = (int) language.getIdl();
      usersLanguages1.setIdl(id);
      usersLanguages1.setLevelWriting(request.getParameter("idioma1_escrito"));
      usersLanguages1.setLevelSpeaking(request.getParameter("idioma1_oral"));
      usersLanguages1.setLevelReading(request.getParameter("idioma1_auditiva"));
      usersLanguages.add(new Pair<>(language, usersLanguages1));
    }

    if (request.getParameter("idio2") != null) {
      Users_Languages usersLanguages2 = new Users_Languages();
      idioma = request.getParameter("idioma2");
      language = languageDAO.get(idioma);
      id = (int) language.getIdl();
      usersLanguages2.setIdl(id);
      usersLanguages2.setLevelWriting(request.getParameter("idioma2_escrito"));
      usersLanguages2.setLevelSpeaking(request.getParameter("idioma2_oral"));
      usersLanguages2.setLevelReading(request.getParameter("idioma2_auditiva"));
      usersLanguages.add(new Pair<>(language, usersLanguages2));
    }
    if (request.getParameter("idio3") != null) {
      Users_Languages usersLanguages3 = new Users_Languages();
      idioma = request.getParameter("idioma3");
      language = languageDAO.get(idioma);
      id = (int) language.getIdl();
      usersLanguages3.setIdl(id);
      usersLanguages3.setLevelWriting(request.getParameter("idioma3_escrito"));
      usersLanguages3.setLevelSpeaking(request.getParameter("idioma3_oral"));
      usersLanguages3.setLevelReading(request.getParameter("idioma3_auditiva"));
      usersLanguages.add(new Pair<>(language, usersLanguages3));
    }

    //BORRADO DE TODOS LOS IDIOMAS QUE HABLA
    List<Users_Languages> idiomas_hablados = users_languagesDAO.getAllByUser(user.getIdu());
    for (Users_Languages usersLanguages1 :
        idiomas_hablados) {
      users_languagesDAO.delete(user.getIdu(), usersLanguages1.getIdl());
    }

    //ACTUALIZACIÓN

    userDAO.save(user);
    for (Pair<Language, Users_Languages> elem : usersLanguages) {
      elem.getSecond().setIdu(user.getIdu());
      users_languagesDAO.add(elem.getSecond());
    }

    //BORRADO Y REDIRECCION

    session.removeAttribute("user");
    session.setAttribute("user", user);

    user = null;
    usersLanguages = null;

    response.sendRedirect("panelusuario");


  }
}
