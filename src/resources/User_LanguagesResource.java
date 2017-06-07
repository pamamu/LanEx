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

package resources;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import dao.JDBCLanguageDAOImpl;
import dao.JDBCUserDAOImpl;
import dao.JDBCUsers_LanguagesDAOImpl;
import dao.LanguageDAO;
import dao.UserDAO;
import dao.Users_LanguagesDAO;
import model.Language;
import model.User;
import model.Users_Languages;
import resources.exceptions.CustomBadRequestException;

/**
 * Created by pablomaciasmu.
 */

@Path("/userlanguage")
public class User_LanguagesResource {

	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Users_Languages> getUserLanguages(@Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		Users_LanguagesDAO users_languagesDAO = new JDBCUsers_LanguagesDAOImpl();
		users_languagesDAO.setConnection(conn);

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		List<Users_Languages> users_languages;

		if (user == null) {
			throw new CustomBadRequestException("Error en usuario");
		}

		users_languages = users_languagesDAO.getAllByUser(user.getIdu());

		return users_languages;

	}

	@GET
	@Path("/{lw: [abc12]*}/{ls: [abc12]*}/{lr: [abc12]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Long> getUserLanguagesSkill(@Context HttpServletRequest request, @PathParam("lw") String lw,
			@PathParam("ls") String ls, @PathParam("lr") String lr) {

		Connection conn = (Connection) sc.getAttribute("dbConn");
		Users_LanguagesDAO users_languagesDAO = new JDBCUsers_LanguagesDAOImpl();
		users_languagesDAO.setConnection(conn);

		if (lw.equals(""))
			lw = "a1";
		if (ls.equals(""))
			ls = "a1";
		if (lr.equals(""))
			lr = "a1";

		List<Users_Languages> users_languages = users_languagesDAO.getAll();
		ArrayList<Long> users = new ArrayList<>();
		int w, s, r;
		for (Users_Languages ul : users_languages) {
			w = lw.compareTo(ul.getLevelWriting());
			s = ls.compareTo(ul.getLevelSpeaking());
			r = lr.compareTo(ul.getLevelReading());
			if (w <= 0 && s <= 0 && r <= 0 && !users.contains(ul.getIdu())) {
				users.add(ul.getIdu());
			}
		}

		return users;
	}

	@GET
	@Path("/{userid: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Users_Languages> getUserLanguagesID(@Context HttpServletRequest request,
			@PathParam("userid") long userid) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		Users_LanguagesDAO users_languagesDAO = new JDBCUsers_LanguagesDAOImpl();
		users_languagesDAO.setConnection(conn);

		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);

		List<Users_Languages> users_languages;

		User user = userDAO.get(userid);

		if (user == null) {
			throw new CustomBadRequestException("Error en usuario");
		}

		users_languages = users_languagesDAO.getAllByUser(user.getIdu());

		return users_languages;

	}

	@GET
	@Path("/n")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getUserLanguagesName(@Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		Users_LanguagesDAO users_languagesDAO = new JDBCUsers_LanguagesDAOImpl();
		users_languagesDAO.setConnection(conn);
		LanguageDAO languageDAO = new JDBCLanguageDAOImpl();
		languageDAO.setConnection(conn);

		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);

		List<Users_Languages> users_languages;

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		List<String> nombres = new ArrayList<>();
		String nombre_idioma = "";
		users_languages = users_languagesDAO.getAllByUser(user.getIdu());
		for (Users_Languages obj : users_languages) {
			nombre_idioma = languageDAO.get(obj.getIdl()).getLangname();
			nombres.add(nombre_idioma);
		}

		return nombres;

	}

	@GET
	@Path("/n/{userid: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getUserLanguagesNameID(@Context HttpServletRequest request, @PathParam("userid") long userid) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		Users_LanguagesDAO users_languagesDAO = new JDBCUsers_LanguagesDAOImpl();
		users_languagesDAO.setConnection(conn);
		LanguageDAO languageDAO = new JDBCLanguageDAOImpl();
		languageDAO.setConnection(conn);

		List<Users_Languages> users_languages;

		List<String> nombres = new ArrayList<>();
		String nombre_idioma = "";
		users_languages = users_languagesDAO.getAllByUser(userid);
		for (Users_Languages obj : users_languages) {
			nombre_idioma = languageDAO.get(obj.getIdl()).getLangname();
			nombres.add(nombre_idioma);
		}

		return nombres;

	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response putUserLanguage(Users_Languages users_languages, @Context HttpServletRequest request)
			throws Exception {

		Connection conn = (Connection) sc.getAttribute("dbConn");

		Users_LanguagesDAO users_languagesDAO = new JDBCUsers_LanguagesDAOImpl();
		users_languagesDAO.setConnection(conn);

		if (users_languagesDAO.save(users_languages)) {
			return Response.noContent().build();
		}

		throw new CustomBadRequestException("Error en usuario");

	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response post(List<Users_Languages> users_languages, @Context HttpServletRequest request) throws Exception {

		Connection conn = (Connection) sc.getAttribute("dbConn");

		LanguageDAO languageDAO = new JDBCLanguageDAOImpl();
		languageDAO.setConnection(conn);

		Users_LanguagesDAO users_languagesDAO = new JDBCUsers_LanguagesDAOImpl();
		users_languagesDAO.setConnection(conn);

		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);

		for (Users_Languages users_Languages2 : users_languages) {
			users_languagesDAO.add(users_Languages2);
		}

		return Response.noContent().build();
	}

	@POST
	@Path("/n")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postA(Users_Languages users_language, @Context HttpServletRequest request) throws Exception {

		Connection conn = (Connection) sc.getAttribute("dbConn");

		LanguageDAO languageDAO = new JDBCLanguageDAOImpl();
		languageDAO.setConnection(conn);

		Users_LanguagesDAO users_languagesDAO = new JDBCUsers_LanguagesDAOImpl();
		users_languagesDAO.setConnection(conn);

		users_languagesDAO.add(users_language);

		return Response.noContent().build();
	}

	@POST
	@Consumes("application/x-www-form-urlencoded")
	public void post(MultivaluedMap<String, String> formParams, @Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");

		LanguageDAO languageDAO = new JDBCLanguageDAOImpl();
		languageDAO.setConnection(conn);

		Users_LanguagesDAO users_languagesDAO = new JDBCUsers_LanguagesDAOImpl();
		users_languagesDAO.setConnection(conn);

		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);

		HttpSession session = request.getSession();
		User user2 = (User) session.getAttribute("user");

		long idu = 0;

		try {
			idu = Long.parseLong(formParams.getFirst("idu"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomBadRequestException("Error en ID de usuario");
		}

		User user = userDAO.get(idu);

		if (user == null || user2 == null || user.getIdu() != user2.getIdu()) {
			throw new CustomBadRequestException("Error en usuario");
		}

		long idl = 0;

		try {
			idl = Long.parseLong(formParams.getFirst("idl"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomBadRequestException("Error en ID de idioma");
		}

		Language language = languageDAO.get(idl);

		if (language == null) {
			throw new CustomBadRequestException("Error en lenguaje");
		}

		Users_Languages usersLanguages1 = new Users_Languages();
		usersLanguages1.setIdl(Long.parseLong(formParams.getFirst("idl")));
		usersLanguages1.setLevelWriting(formParams.getFirst("idioma_escrito"));
		usersLanguages1.setLevelSpeaking(formParams.getFirst("idioma_oral"));
		usersLanguages1.setLevelReading(formParams.getFirst("idioma_auditiva"));
		users_languagesDAO.add(usersLanguages1);

	}

	@DELETE
	@Path("/{userid: [0-9]+}/{idl: [0-9]+}")
	public Response userlanguage(@PathParam("userid") long userid, @PathParam("idl") long idl,
			@Context HttpServletRequest request) {

		Connection conn = (Connection) sc.getAttribute("dbConn");

		LanguageDAO languageDAO = new JDBCLanguageDAOImpl();
		languageDAO.setConnection(conn);

		Users_LanguagesDAO users_languagesDAO = new JDBCUsers_LanguagesDAOImpl();
		users_languagesDAO.setConnection(conn);

		users_languagesDAO.delete(userid, idl);

		return Response.noContent().build();

		// UserDAO userDAO = new JDBCUserDAOImpl();
		// userDAO.setConnection(conn);
		//
		// User user = userDAO.get(userid);
		//
		// HttpSession session = request.getSession();
		// User user2 = (User) session.getAttribute("user");
		//
		// if (user == null || user2 == null || user.getIdu() != user2.getIdu())
		// {
		// throw new CustomBadRequestException("Error en usuario");
		// }
		//
		// // BORRADO DE TODOS LOS IDIOMAS QUE HABLA
		// List<Users_Languages> idiomas_hablados =
		// users_languagesDAO.getAllByUser(userid);
		// for (Users_Languages usersLanguages1 : idiomas_hablados) {
		// users_languagesDAO.delete(userid, usersLanguages1.getIdl());
		// }
		//
		// Response res = Response // return 201 and Location: /orders/newid
		// .created(uriInfo.getAbsolutePathBuilder().path(Long.toString(userid)).build())
		// .contentLocation(uriInfo.getAbsolutePathBuilder().path(Long.toString(userid)).build()).build();
		//
		// return res;

	}

}
