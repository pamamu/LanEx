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

import dao.JDBCLanguageDAOImpl;
import dao.LanguageDAO;
import java.sql.Connection;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import model.Language;
import resources.exceptions.CustomBadRequestException;

/**
 * Created by pablomaciasmu.
 */
@Path("/language")
public class LanguagesResource {

  @Context
  ServletContext sc;
  @Context
  UriInfo uriInfo;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Language> getLanguages(@Context HttpServletRequest request) {
    Connection conn = (Connection) sc.getAttribute("dbConn");
    LanguageDAO languageDAO = new JDBCLanguageDAOImpl();
    languageDAO.setConnection(conn);

    return languageDAO.getAll();
  }


  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/{lid: [0-9]+}")
  public Language getLanguage(@Context HttpServletRequest request, @PathParam("lid") long lid) {
    Connection conn = (Connection) sc.getAttribute("dbConn");
    LanguageDAO languageDAO = new JDBCLanguageDAOImpl();
    languageDAO.setConnection(conn);

    Language language = languageDAO.get(lid);

    if (language==null){
      throw new CustomBadRequestException("Error en ID");
    }
    return language;

  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/{name: [a-zA-Z_áéíóúÁÉÍÓÚñÑ]+}")
  public Language getLanguage(@Context HttpServletRequest request, @PathParam("name") String name) {
    Connection conn = (Connection) sc.getAttribute("dbConn");
    LanguageDAO languageDAO = new JDBCLanguageDAOImpl();
    languageDAO.setConnection(conn);

    if (name==null)
      throw new CustomBadRequestException("Error en Nombre");

    Language language = languageDAO.get(name);

    if (language==null){
      throw new CustomBadRequestException("Error en Nombre");
    }
    return language;

  }



}
