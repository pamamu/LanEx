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

import dao.JDBCMensajeImpl;
import dao.JDBCUserDAOImpl;
import dao.MensajeDAO;
import dao.UserDAO;
import java.sql.Connection;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import model.Mensaje;
import model.User;
import resources.exceptions.CustomBadRequestException;
import util.Triplet;

/**
 * Created by pablomaciasmu.
 */

@Path("/mensajes")
public class MensajesResource {

  @Context
  ServletContext sc;
  @Context
  UriInfo uriInfo;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Mensaje> getMensajesJSON(
      @Context HttpServletRequest request) {

    Connection conn = (Connection) sc.getAttribute("dbConn");

    MensajeDAO mensajeDAO = new JDBCMensajeImpl();
    mensajeDAO.setConnection(conn);

    HttpSession session = request.getSession();
    User user = (User) session.getAttribute("user");
    List<Mensaje> mensajes;

    if (user == null) {
      throw new CustomBadRequestException("Error en usuario");
    }

    mensajes = mensajeDAO.getAllByReceiver(user.getIdu());

    return mensajes;

  }

  @GET
  @Path("/emisor")
  @Produces(MediaType.APPLICATION_JSON)
  public List<Mensaje> getAllbySender(@Context HttpServletRequest request) {

    Connection conn = (Connection) sc.getAttribute("dbConn");

    MensajeDAO mensajeDAO = new JDBCMensajeImpl();
    mensajeDAO.setConnection(conn);

    UserDAO userDAO = new JDBCUserDAOImpl();
    userDAO.setConnection(conn);

    HttpSession session = request.getSession();
    User user = (User) session.getAttribute("user");
    
    System.out.println("Usuario Borrado");


    if (user == null) {
      throw new CustomBadRequestException("Error in Usuario");
    }

    return mensajeDAO.getAllBySender(user.getIdu());


  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response post2(Mensaje mensaje, @Context HttpServletRequest request){

    Connection conn = (Connection) sc.getAttribute("dbConn");

    MensajeDAO mensajeDAO = new JDBCMensajeImpl();
    mensajeDAO.setConnection(conn);


    HttpSession session = request.getSession();
    User user = (User) session.getAttribute("user");

    if (user.getIdu() != mensaje.getSender())
      throw new CustomBadRequestException("No permitido");

    mensajeDAO.add(mensaje);

    return Response.noContent().build(); // 204 no content
  }

  @POST
  @Consumes("application/x-www-form-urlencoded")
  public Response post(MultivaluedMap<String, String> formParams,
      @Context HttpServletRequest request) {

    Connection conn = (Connection) sc.getAttribute("dbConn");

    MensajeDAO mensajeDAO = new JDBCMensajeImpl();
    mensajeDAO.setConnection(conn);

    UserDAO userDAO = new JDBCUserDAOImpl();
    userDAO.setConnection(conn);

    HttpSession session = request.getSession();
    User user = (User) session.getAttribute("user");

    long id1 = 0, id2= 0;

    try {
      id1 = Long.parseLong(formParams.getFirst("emisor"));
      id2 = Long.parseLong(formParams.getFirst("receptor"));
    }
    catch (Exception e){
      throw new CustomBadRequestException("Errors in parameters");
    }

    User user1 = userDAO.get(id1);
    User user2 = userDAO.get(id2);
    String mensaje = formParams.getFirst("mensaje");

    if (user1 == null || user2==null || user ==null || user1.getIdu() != user.getIdu()){
      throw new CustomBadRequestException("Error in Usuario");
    }

    Mensaje mensaje1 = new Mensaje();
    mensaje1.setSender(user1.getIdu());
    mensaje1.setReceiver(user2.getIdu());
    mensaje1.setText(mensaje);

    mensajeDAO.add(mensaje1);

    return Response.noContent().build();

  }


}
