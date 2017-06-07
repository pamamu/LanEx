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
import helper.DateTimeHelper;
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
@WebServlet(name = "RegistroServlet", urlPatterns = "/registro", description = "Servlet encargado de registrar a un usuario en la aplicación")
public class RegistroServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;
  private static final Logger logger =
      Logger.getLogger(HttpServlet.class.getName());

  protected void doPost(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {

    request.setCharacterEncoding("UTF-8");

    Connection conn = (Connection) getServletContext().getAttribute("dbConn");
    LanguageDAO languageDAO = new JDBCLanguageDAOImpl();
    languageDAO.setConnection(conn);

    logger.info("Atendiendo POST");

    //PARTE USER

    User user = new User();

    user.setNombre(request.getParameter("nombre"));
    user.setUsername(request.getParameter("username"));
    user.setApellidos(request.getParameter("apellidos"));
    user.setNacionalidad(request.getParameter("nacionalidad"));
    user.setEmail(request.getParameter("email"));
    user.setFec_nac(request.getParameter("nacim"));
    user.setPassword(request.getParameter("pass"));
    user.setTelefono(request.getParameter("telefono"));
    user.setTwitter(request.getParameter("twitter"));
    user.setPuntos(0);
    user.setImagen(Integer.parseInt(request.getParameter("imagen")));
    user.setContacto_preferido(request.getParameter("contacto_preferido"));

    logger.info("Nombre usuario: " + user.getUsername());

    List<String> messages = new ArrayList<String>();
    if (!user.validate(messages)) {
      request.setAttribute("CheckType", "Registro");
      request.setAttribute("messages", messages);
      RequestDispatcher view = request.getRequestDispatcher("WEB-INF/editUser.jsp");
      view.forward(request, response);
      return;
    }

    //ENCRIPTACIÓN DE CONTRASEÑA
    String contraseñaEncriptada = Encriptar.sha1(user.getPassword());
    user.setPassword(contraseñaEncriptada);

    //PARTE USER_LANGUAGES

    String idioma;

    Language language;

    List<Pair<Language, Users_Languages>> usersLanguages = new ArrayList<>();

    if (request.getParameter("idio1") != null) {
      Users_Languages usersLanguages1 = new Users_Languages();
      idioma = request.getParameter("idioma1");
      language = languageDAO.get(idioma);
      int id = (int) language.getIdl();
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
      int id = (int) language.getIdl();
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
      int id = (int) language.getIdl();
      usersLanguages3.setIdl(id);
      usersLanguages3.setLevelWriting(request.getParameter("idioma3_escrito"));
      usersLanguages3.setLevelSpeaking(request.getParameter("idioma3_oral"));
      usersLanguages3.setLevelReading(request.getParameter("idioma3_auditiva"));
      usersLanguages.add(new Pair<>(language, usersLanguages3));
    }

    HttpSession session = request.getSession();
    session.removeAttribute("user");
    session.setAttribute("userb", user);
    session.setAttribute("usersLanguages", usersLanguages);
    response.sendRedirect("checkUser");

  }

  protected void doGet(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {

    logger.info("Atendiendo GET");
    HttpSession session = request.getSession();

    logger.info("Session id: " + session.getId());
    logger.info("Session new? " + session.isNew());
    logger.info("Session creation time: " + DateTimeHelper.time2Date(session.getCreationTime()));
    logger.info(
        "Session last accessed time: " + DateTimeHelper.time2Date(session.getLastAccessedTime()));
    logger.info(
        "Session max inactive time: " + DateTimeHelper.time2Date(session.getMaxInactiveInterval()));

    Connection conn = (Connection) getServletContext().getAttribute("dbConn");
    LanguageDAO languageDAO = new JDBCLanguageDAOImpl();
    languageDAO.setConnection(conn);

    if (session.getAttribute("user") != null) {
      response.sendRedirect("lanex/panelusuario");
      return;
    }

    List<Language> languages = languageDAO.getAll();

    request.setAttribute("CheckType", "Registro");
    request.setAttribute("idiomas", languages);
    RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/editUser.jsp");
    view.forward(request, response);

  }
}
