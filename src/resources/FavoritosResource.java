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

import dao.FavoritoDAO;
import dao.JDBCFavoritoImpl;
import dao.JDBCUserDAOImpl;
import dao.UserDAO;
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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import model.User;
import resources.exceptions.CustomBadRequestException;

/**
 * Created by pablomaciasmu.
 */

@Path("/favorito")
public class FavoritosResource {

	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getFavoritos(@Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		FavoritoDAO favoritoDAO = new JDBCFavoritoImpl();
		favoritoDAO.setConnection(conn);

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		if (user == null) {
			throw new CustomBadRequestException("Error en usuario");
		}
		return favoritoDAO.getAllByUser(user.getIdu());
	}

	@GET
	@Path("/n")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getNoFavoritos(@Context HttpServletRequest request) {

		Connection conn = (Connection) sc.getAttribute("dbConn");
		FavoritoDAO favoritoDAO = new JDBCFavoritoImpl();
		favoritoDAO.setConnection(conn);

		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		List<User> nofavoritos = new ArrayList<>();

		List<User> favoritos = favoritoDAO.getAllByUser(user.getIdu());

		List<User> users = userDAO.getAll();
		boolean encontrado = false;
		for (User usera : users) {
			encontrado = false;
			if (usera.getIdu() != user.getIdu()) {
				for (User user2 : favoritos) {
					if (usera.getIdu() == user2.getIdu()) {
						encontrado = true;
						break;
					}

				}
				if (!encontrado)
					nofavoritos.add(usera);
			}

		}

		return nofavoritos;

	}

	@POST
	@Consumes("application/x-www-form-urlencoded")
	public Response post(MultivaluedMap<String, String> formParams, @Context HttpServletRequest request) {

		Connection conn = (Connection) sc.getAttribute("dbConn");
		FavoritoDAO favoritoDAO = new JDBCFavoritoImpl();
		favoritoDAO.setConnection(conn);

		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		if (user == null) {
			throw new CustomBadRequestException("Error en usuario");
		}

		User user2 = userDAO.get(formParams.getFirst("idfavorito"));

		if (user2 == null) {
			throw new CustomBadRequestException("Errors in parameters");
		}

		if (!favoritoDAO.add(user.getIdu(), user2.getIdu())) {
			throw new CustomBadRequestException("Relación existente");
		}

		return Response.noContent().build(); // 204 no content

	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response post(User user2, @Context HttpServletRequest request) {

		Connection conn = (Connection) sc.getAttribute("dbConn");
		FavoritoDAO favoritoDAO = new JDBCFavoritoImpl();
		favoritoDAO.setConnection(conn);

		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		if (user2 == null) {
			throw new CustomBadRequestException("Errors in parameters");
		}

		if (user2.getIdu() == user.getIdu()) {
			throw new CustomBadRequestException("Favorito es Usuario Logueado");
		}

		if (!favoritoDAO.add(user.getIdu(), user2.getIdu())) {
			throw new CustomBadRequestException("Relación existente");
		}

		return Response.noContent().build(); // 204 no content

	}

	@DELETE
	@Path("/{username: [a-zA-Z_][0-9a-zA-Z_]*}")
	public Response delete(@PathParam("username") String username, @Context HttpServletRequest request) {

		Connection conn = (Connection) sc.getAttribute("dbConn");

		FavoritoDAO favoritoDAO = new JDBCFavoritoImpl();
		favoritoDAO.setConnection(conn);

		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		if (user == null) {
			throw new CustomBadRequestException("Error en usuario");
		}

		List<User> users = favoritoDAO.getAllByUser(user.getIdu());

		User user2 = userDAO.get(username);

		if (user2 == null) {
			throw new CustomBadRequestException("Errors in parameters");
		}

		if (users.contains(user)) {
			throw new CustomBadRequestException("Favorito existente");
		}

		if (user2.getIdu() == user.getIdu()) {
			throw new CustomBadRequestException("Favorito es Usuario Logueado");
		}

		favoritoDAO.delete(user.getIdu(), user2.getIdu());

		return Response.noContent().build(); // 204 no content

	}

}
