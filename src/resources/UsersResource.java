package resources;

import dao.JDBCLanguageDAOImpl;
import dao.JDBCUserDAOImpl;
import dao.JDBCUsers_LanguagesDAOImpl;
import dao.LanguageDAO;
import dao.UserDAO;
import dao.Users_LanguagesDAO;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import model.Language;
import model.User;
import model.Users_Languages;
import resources.exceptions.CustomBadRequestException;
import util.Encriptar;
import util.Pair;

@Path("/users")
public class UsersResource {

  @Context
  ServletContext sc;
  @Context
  UriInfo uriInfo;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<User> getUsersJSON(@Context HttpServletRequest request) {
    Connection conn = (Connection) sc.getAttribute("dbConn");
    UserDAO userDAO = new JDBCUserDAOImpl();
    userDAO.setConnection(conn);
    return userDAO.getAll();

  }

  @GET
  @Path("/me$")
  @Produces(MediaType.APPLICATION_JSON)
  public User getUseJSON(@Context HttpServletRequest request) {
    Connection conn = (Connection) sc.getAttribute("dbConn");
    UserDAO userDAO = new JDBCUserDAOImpl();
    userDAO.setConnection(conn);
    
    HttpSession session = request.getSession();
    User userl = (User) session.getAttribute("user");
    
    if (userl == null) {
    	throw new CustomBadRequestException("Usuario no encontrado");
	}
    
    User user = userDAO.get(userl.getIdu());
  
    return user;

  }


  @GET
  @Path("/{userid: [0-9]+}")
  @Produces(MediaType.APPLICATION_JSON)
  public User getUserJSON(@PathParam("userid") long userid, @Context HttpServletRequest request) {
    Connection conn = (Connection) sc.getAttribute("dbConn");
    UserDAO userDAO = new JDBCUserDAOImpl();
    userDAO.setConnection(conn);
    HttpSession session = request.getSession();
    User userl = (User) session.getAttribute("user");

    if (userl != null && userl.getIdu() == userid) {
      return userl;
    }
    User user = userDAO.getSC(userid);

    if (user == null) {
      throw new CustomBadRequestException("Usuario no encontrado");
    }
    return user;

  }

  @GET
  @Path("/{username: [a-zA-Z_][0-9a-zA-Z_]*}")
  @Produces(MediaType.APPLICATION_JSON)
  public User getUserJSON(@PathParam("username") String username,
      @Context HttpServletRequest request) {
    Connection conn = (Connection) sc.getAttribute("dbConn");
    UserDAO userDAO = new JDBCUserDAOImpl();
    userDAO.setConnection(conn);
    HttpSession session = request.getSession();
    User userl = (User) session.getAttribute("user");

    if (userl != null && userl.getUsername().equals(username)) {
      return userl;
    }

    if (username == null) {
      throw new CustomBadRequestException("Username vacio");
    }

    User user = userDAO.getSC(username);

    if (user == null) {
      throw new CustomBadRequestException("Usuario no encontrado");
    }
    return user;

  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public int post(User user, @Context HttpServletRequest request) {

    Connection conn = (Connection) sc.getAttribute("dbConn");
    UserDAO userDAO = new JDBCUserDAOImpl();
    userDAO.setConnection(conn);

    List<String> messages = new ArrayList<String>();
    if (!user.validate(messages)) {
      throw new CustomBadRequestException("Error en parámetros");
    }

    String contraseñaEncriptada = Encriptar.sha1(user.getPassword());
    user.setPassword(contraseñaEncriptada);

    return (int) userDAO.add(user);


  }

  @POST
  @Consumes("application/x-www-form-urlencoded")
  public Response post(MultivaluedMap<String, String> formParams,
      @Context HttpServletRequest request) {

    Connection conn = (Connection) sc.getAttribute("dbConn");
    UserDAO userDAO = new JDBCUserDAOImpl();
    userDAO.setConnection(conn);

    LanguageDAO languageDAO = new JDBCLanguageDAOImpl();
    languageDAO.setConnection(conn);

    Users_LanguagesDAO users_languagesDAO = new JDBCUsers_LanguagesDAOImpl();
    users_languagesDAO.setConnection(conn);

    //PARTE USER

    User user = new User();
    user.setNombre(formParams.getFirst("nombre"));
    user.setUsername(formParams.getFirst("username"));
    user.setApellidos(formParams.getFirst("apellidos"));
    user.setNacionalidad(formParams.getFirst("nacionalidad"));
    user.setEmail(formParams.getFirst("email"));
    user.setFec_nac(formParams.getFirst("nacim"));
    user.setPassword(formParams.getFirst("pass"));
    user.setTelefono(formParams.getFirst("telefono"));
    user.setTwitter(formParams.getFirst("twitter"));
    user.setPuntos(0);
    user.setImagen(Integer.parseInt(formParams.getFirst("imagen")));
    user.setContacto_preferido(formParams.getFirst("contacto_preferido"));

    List<String> messages = new ArrayList<String>();
    if (!user.validate(messages)) {
      throw new CustomBadRequestException("Error en parámetros");
    }

    //ENCRIPTACIÓN DE CONTRASEÑA
    String contraseñaEncriptada = Encriptar.sha1(user.getPassword());
    user.setPassword(contraseñaEncriptada);

    //PARTE USER_LANGUAGES

    String idioma;

    Language language;

    List<Pair<Language, Users_Languages>> usersLanguages = new ArrayList<>();

    if (formParams.getFirst("idio1") != null) {
      Users_Languages usersLanguages1 = new Users_Languages();
      idioma = formParams.getFirst("idioma1");
      language = languageDAO.get(idioma);
      int id = (int) language.getIdl();
      usersLanguages1.setIdl(id);
      usersLanguages1.setLevelWriting(formParams.getFirst("idioma1_escrito"));
      usersLanguages1.setLevelSpeaking(formParams.getFirst("idioma1_oral"));
      usersLanguages1.setLevelReading(formParams.getFirst("idioma1_auditiva"));
      usersLanguages.add(new Pair<>(language, usersLanguages1));
    }

    if (formParams.getFirst("idio2") != null) {
      Users_Languages usersLanguages2 = new Users_Languages();
      idioma = formParams.getFirst("idioma2");
      language = languageDAO.get(idioma);
      int id = (int) language.getIdl();
      usersLanguages2.setIdl(id);
      usersLanguages2.setLevelWriting(formParams.getFirst("idioma2_escrito"));
      usersLanguages2.setLevelSpeaking(formParams.getFirst("idioma2_oral"));
      usersLanguages2.setLevelReading(formParams.getFirst("idioma2_auditiva"));
      usersLanguages.add(new Pair<>(language, usersLanguages2));
    }
    if (formParams.getFirst("idio3") != null) {
      Users_Languages usersLanguages3 = new Users_Languages();
      idioma = formParams.getFirst("idioma3");
      language = languageDAO.get(idioma);
      int id = (int) language.getIdl();
      usersLanguages3.setIdl(id);
      usersLanguages3.setLevelWriting(formParams.getFirst("idioma3_escrito"));
      usersLanguages3.setLevelSpeaking(formParams.getFirst("idioma3_oral"));
      usersLanguages3.setLevelReading(formParams.getFirst("idioma3_auditiva"));
      usersLanguages.add(new Pair<>(language, usersLanguages3));
    }

    HttpSession session = request.getSession();
    session.removeAttribute("user");
    session.setAttribute("userb", user);

    long id = userDAO.add(user);
    if (id == -1) {
      throw new CustomBadRequestException("Username repetido");
    }
    for (Pair<Language, Users_Languages> elem : usersLanguages) {
      elem.getSecond().setIdu(id);
      users_languagesDAO.add(elem.getSecond());
    }

    Response res = Response // return 201 and Location: /orders/newid
        .created(uriInfo.getAbsolutePathBuilder().path(Long.toString(id)).build())
        .contentLocation(uriInfo.getAbsolutePathBuilder().path(Long.toString(id)).build()).build();

    return res;

  }

  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  public Response put2(User user,
      @Context HttpServletRequest request) {

    Connection conn = (Connection) sc.getAttribute("dbConn");
    UserDAO userDAO = new JDBCUserDAOImpl();
    userDAO.setConnection(conn);
    
    HttpSession session = request.getSession();
    User userl = (User) session.getAttribute("user");
    
    if (userl == null) {
    	throw new CustomBadRequestException("Usuario no encontrado");
	}

    User user1 = userDAO.get(user.getIdu());

    if (user.getPassword().equals(user1.getPassword()) || user.getPassword().equals("")) {
      user.setPassword(user1.getPassword());
    } else {
      user.setPassword(Encriptar.sha1(user.getPassword()));
    }

    if (user.getEmail() == null || user.getEmail().equals("")) {
      user.setEmail(user.getEmail());
    }

    userDAO.save(user);

    return Response.noContent().build();
  }

  @PUT
  @Path("/{userid: [0-9]+}")
  @Consumes("application/x-www-form-urlencoded")
  public Response put(MultivaluedMap<String, String> formParams,
      @Context HttpServletRequest request, @PathParam("userid") long userid) {

    Connection conn = (Connection) sc.getAttribute("dbConn");
    UserDAO userDAO = new JDBCUserDAOImpl();
    userDAO.setConnection(conn);

    LanguageDAO languageDAO = new JDBCLanguageDAOImpl();
    languageDAO.setConnection(conn);

    Users_LanguagesDAO users_languagesDAO = new JDBCUsers_LanguagesDAOImpl();
    users_languagesDAO.setConnection(conn);

    HttpSession session = request.getSession();

    //PARTE USER

    User users = (User) session.getAttribute("user");

    if (users == null || users.getIdu() != userid) {
      throw new CustomBadRequestException("Error en parámetros");
    }

    long idu = users.getIdu();
    User user = new User();

    user.setIdu(idu);
    user.setNombre(formParams.getFirst("nombre"));
    user.setUsername(formParams.getFirst("username"));
    user.setApellidos(formParams.getFirst("apellidos"));
    user.setNacionalidad(formParams.getFirst("nacionalidad"));
    user.setEmail(formParams.getFirst("email"));
    user.setFec_nac(formParams.getFirst("nacim"));
    user.setPassword(formParams.getFirst("pass"));
    user.setTelefono(formParams.getFirst("telefono"));
    user.setTwitter(formParams.getFirst("twitter"));
    user.setImagen(Integer.parseInt(formParams.getFirst("imagen")));
    user.setContacto_preferido(formParams.getFirst("contacto_preferido"));

    List<String> messages = new ArrayList<String>();
    if (!user.validate(messages)) {
      throw new CustomBadRequestException("Error en parámetros");
    }

    //ENCRIPTACIÓN DE CONTRASEÑA
    String contraseñaEncriptada = Encriptar.sha1(user.getPassword());
    user.setPassword(contraseñaEncriptada);

    //PARTE USER_LANGUAGES

    String idioma;

    Language language;

    List<Pair<Language, Users_Languages>> usersLanguages = new ArrayList<>();

    if (formParams.getFirst("idio1") != null) {
      Users_Languages usersLanguages1 = new Users_Languages();
      idioma = formParams.getFirst("idioma1");
      language = languageDAO.get(idioma);
      int id = (int) language.getIdl();
      usersLanguages1.setIdl(id);
      usersLanguages1.setLevelWriting(formParams.getFirst("idioma1_escrito"));
      usersLanguages1.setLevelSpeaking(formParams.getFirst("idioma1_oral"));
      usersLanguages1.setLevelReading(formParams.getFirst("idioma1_auditiva"));
      usersLanguages.add(new Pair<>(language, usersLanguages1));
    }

    if (formParams.getFirst("idio2") != null) {
      Users_Languages usersLanguages2 = new Users_Languages();
      idioma = formParams.getFirst("idioma2");
      language = languageDAO.get(idioma);
      int id = (int) language.getIdl();
      usersLanguages2.setIdl(id);
      usersLanguages2.setLevelWriting(formParams.getFirst("idioma2_escrito"));
      usersLanguages2.setLevelSpeaking(formParams.getFirst("idioma2_oral"));
      usersLanguages2.setLevelReading(formParams.getFirst("idioma2_auditiva"));
      usersLanguages.add(new Pair<>(language, usersLanguages2));
    }
    if (formParams.getFirst("idio3") != null) {
      Users_Languages usersLanguages3 = new Users_Languages();
      idioma = formParams.getFirst("idioma3");
      language = languageDAO.get(idioma);
      int id = (int) language.getIdl();
      usersLanguages3.setIdl(id);
      usersLanguages3.setLevelWriting(formParams.getFirst("idioma3_escrito"));
      usersLanguages3.setLevelSpeaking(formParams.getFirst("idioma3_oral"));
      usersLanguages3.setLevelReading(formParams.getFirst("idioma3_auditiva"));
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

    Response res = Response // return 201 and Location: /orders/newid
        .created(uriInfo.getAbsolutePathBuilder().path(Long.toString(user.getIdu())).build())
        .contentLocation(
            uriInfo.getAbsolutePathBuilder().path(Long.toString(user.getIdu())).build()).build();

    return res;
  }

  @DELETE
  @Path("/{userid: [0-9]+}")
  public Response deleteUser(@PathParam("userid") long userid,
      @Context HttpServletRequest request) {

    Connection conn = (Connection) sc.getAttribute("dbConn");

    UserDAO userDAO = new JDBCUserDAOImpl();
    userDAO.setConnection(conn);

    Users_LanguagesDAO users_languagesDAO = new JDBCUsers_LanguagesDAOImpl();
    users_languagesDAO.setConnection(conn);

    HttpSession session = request.getSession();
    User user = (User) session.getAttribute("user");

    if (user != null && userid == user.getIdu()) {
      userDAO.delete(user.getIdu());
      List<Users_Languages> users_languages = users_languagesDAO.getAllByUser(user.getIdu());
      for (Users_Languages users_languages1 :
          users_languages) {
        users_languagesDAO.delete(user.getIdu(), users_languages1.getIdl());
      }
      session.removeAttribute("user");
      return Response.noContent().build(); // 204 no content
    } else {
      throw new CustomBadRequestException("Error en usuario");
    }
  }

  @POST
  @Path("/{userid: [0-9]+}/p")
  public Response subirPuntos(@PathParam("userid") long userid, @QueryParam("puntos") int puntos,
      @Context HttpServletRequest request) {

    Connection conn = (Connection) sc.getAttribute("dbConn");

    UserDAO userDAO = new JDBCUserDAOImpl();
    userDAO.setConnection(conn);

    HttpSession session = request.getSession();
    User user = (User) session.getAttribute("user");

    User user2 = userDAO.getSC(userid);

    Response res;

    if (user2 == null || user == null) {
      throw new CustomBadRequestException("Usuario no encontrado");
    }

    if (puntos != 0 && (puntos == -1 || puntos == 1)) {
      if (user.getIdu() == userid) {
        throw new CustomBadRequestException("Votación a sí mismo");
      }
      if (puntos == 1) {
        userDAO.darpuntos(userid, user2.getPuntos() + 1);
      } else {
        int punt = user.getPuntos() - 1;
        if (puntos > 0) {
          userDAO.darpuntos(userid, puntos);
        } else {
          userDAO.darpuntos(userid, 0);
        }
      }
      res = Response // return 201 and Location: /orders/newid
          .created(uriInfo.getAbsolutePath())
          .contentLocation(
              uriInfo.getAbsolutePath()).build();

    } else {
      throw new CustomBadRequestException("Error en puntuación");
    }

    return res;

  }

}