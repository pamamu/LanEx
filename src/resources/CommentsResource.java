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

import dao.CommentDAO;
import dao.JDBCCommentDAOImpl;
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
import model.Comment;
import model.User;
import resources.exceptions.CustomBadRequestException;

/**
 * Created by pablomaciasmu.
 */

@Path("/comments")
public class CommentsResource {

  @Context
  ServletContext sc;
  @Context
  UriInfo uriInfo;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Comment> getCommentsJSON(@Context HttpServletRequest request) {

    Connection conn = (Connection) sc.getAttribute("dbConn");
    CommentDAO commentDAO = new JDBCCommentDAOImpl();
    commentDAO.setConnection(conn);

    HttpSession session = request.getSession();
    User user = (User) session.getAttribute("user");
    List<Comment> comentarios;

    if (user == null) {
      throw new CustomBadRequestException("Error en usuario");
    }

    comentarios = commentDAO.getAllByReceiver(user.getIdu());

    return comentarios;

  }

  @GET
  @Path("/send")
  @Produces(MediaType.APPLICATION_JSON)
  public List<Comment> getCommentsENviadosJSON(@Context HttpServletRequest request) {

    Connection conn = (Connection) sc.getAttribute("dbConn");
    CommentDAO commentDAO = new JDBCCommentDAOImpl();
    commentDAO.setConnection(conn);

    HttpSession session = request.getSession();
    User user = (User) session.getAttribute("user");
    List<Comment> comentarios;

    if (user == null) {
      throw new CustomBadRequestException("Error en usuario");
    }

    comentarios = commentDAO.getAllBySender(user.getIdu());

    return comentarios;

  }

  @GET
  @Path("/{commentid: [0-9]+}")
  @Produces(MediaType.APPLICATION_JSON)
  public Comment getCommentsJSON(@PathParam("commentid") long commentid,
      @Context HttpServletRequest request) {

    Connection conn = (Connection) sc.getAttribute("dbConn");
    CommentDAO commentDAO = new JDBCCommentDAOImpl();
    commentDAO.setConnection(conn);

    HttpSession session = request.getSession();
    User user = (User) session.getAttribute("user");

    Comment com = commentDAO.get(commentid);

    if (com == null || user == null || com.getSender() != user.getIdu()) {
      throw new CustomBadRequestException("Comentario no encontrado");
    }

    return com;

  }

  @GET
  @Path("/emisor")
  @Produces(MediaType.APPLICATION_JSON)
  public List<Comment> getAllbySender(@Context HttpServletRequest request,
      @QueryParam("id") long userid) {

    Connection conn = (Connection) sc.getAttribute("dbConn");
    CommentDAO commentDAO = new JDBCCommentDAOImpl();
    commentDAO.setConnection(conn);
    List<Comment> comentarios;

    comentarios = commentDAO.getAllBySender(userid);

    return comentarios;

  }

  @GET
  @Path("/receptor")
  @Produces(MediaType.APPLICATION_JSON)
  public List<Comment> getAllbyReceiver(@Context HttpServletRequest request,
      @QueryParam("id") long userid) {

    Connection conn = (Connection) sc.getAttribute("dbConn");
    CommentDAO commentDAO = new JDBCCommentDAOImpl();
    commentDAO.setConnection(conn);
    List<Comment> comentarios;

    comentarios = commentDAO.getAllByReceiver(userid);

    return comentarios;

  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response post(Comment comment, @Context HttpServletRequest request) throws Exception {
    Connection conn = (Connection) sc.getAttribute("dbConn");
    CommentDAO commentDAO = new JDBCCommentDAOImpl();
    commentDAO.setConnection(conn);
    UserDAO userDAO = new JDBCUserDAOImpl();
    userDAO.setConnection(conn);

    List<String> messages = new ArrayList<String>();
    comment.validate(messages);

    if (messages.size() > 0) {
      throw new CustomBadRequestException("Errors in parameters");
    }

    commentDAO.add(comment);

    return Response.noContent().build();

  }

  @POST
  @Consumes("application/x-www-form-urlencoded")
  public Response post(MultivaluedMap<String, String> formParams,
      @Context HttpServletRequest request) {
    Connection conn = (Connection) sc.getAttribute("dbConn");
    CommentDAO commentDAO = new JDBCCommentDAOImpl();
    commentDAO.setConnection(conn);
    UserDAO userDAO = new JDBCUserDAOImpl();
    userDAO.setConnection(conn);

    HttpSession session = request.getSession();
    User user = (User) session.getAttribute("user");

    if (user != null) {
      System.out.println("usuario: " + user.getNombre());
    }

    Comment comment = new Comment();
    long id_emisor = user.getIdu();
    long oid = 0;
    try {
      String id = formParams.getFirst("receptor");
      oid = Long.parseLong(id);
    } catch (Exception e) {
      throw new CustomBadRequestException("Errors in parameters");
    }
    User user_destino = userDAO.get(oid);

    if (user_destino == null) {
      throw new CustomBadRequestException("Errors in parameters");
    } else {
      comment.setSender(id_emisor);
      comment.setReceiver(user_destino.getIdu());
      comment.setText(formParams.getFirst("comment"));
    }

    List<String> messages = new ArrayList<String>();
    comment.validate(messages);

    if (messages.size() > 0) {
      throw new CustomBadRequestException("Errors in parameters");
    }

    long id = commentDAO.add(comment);

    Response res = Response // return 201 and Location: /orders/newid
        .created(uriInfo.getAbsolutePathBuilder().path(Long.toString(id)).build())
        .contentLocation(uriInfo.getAbsolutePathBuilder().path(Long.toString(id)).build()).build();

    return res;

  }

  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  public Response putComment(Comment comment, @Context HttpServletRequest request)
      throws Exception {
    Connection conn = (Connection) sc.getAttribute("dbConn");
    CommentDAO commentDAO = new JDBCCommentDAOImpl();
    commentDAO.setConnection(conn);

    if (commentDAO.save(comment)) {
      return Response.noContent().build();
    }

    throw new CustomBadRequestException("Error en comentario");
  }

  @PUT
  @Path("/{commentid: [0-9]+}")
  @Consumes("application/x-www-form-urlencoded")
  public Response put(MultivaluedMap<String, String> formParams,
      @Context HttpServletRequest request,
      @PathParam("commentid") long commentid) {

    Connection conn = (Connection) sc.getAttribute("dbConn");
    CommentDAO commentDAO = new JDBCCommentDAOImpl();
    commentDAO.setConnection(conn);
    Comment comment = commentDAO.get(commentid);
    UserDAO userDAO = new JDBCUserDAOImpl();
    userDAO.setConnection(conn);
    Response res;

    HttpSession session = request.getSession();
    User user = (User) session.getAttribute("user");

    if (user != null) {
      System.out.println("usuario: " + user.getNombre());
    } else {
      throw new CustomBadRequestException("Error en usuario");
    }

    if (comment != null && user.getIdu() == comment.getSender()) {
      Comment comment_mod = new Comment();
      comment_mod.setIdc(comment.getIdc());
      comment_mod.setReceiver(comment.getReceiver());
      comment_mod.setSender(comment.getSender());
      comment_mod.setText(formParams.getFirst("comment"));
      List<String> messages = new ArrayList<String>();
      comment_mod.validate(messages);
      if (messages.size() > 0) {
        throw new CustomBadRequestException("Error en comentario");
      }
      commentDAO.save(comment_mod);
      res = Response // return 201 and Location: /orders/newid
          .created(uriInfo.getAbsolutePathBuilder().build())
          .contentLocation(uriInfo.getAbsolutePathBuilder().build()).build();
    } else {
      throw new CustomBadRequestException("Error en usuario o id del comentario");
    }
    return res;
  }

  @DELETE
  @Path("/{commentid: [0-9]+}")
  public Response deleteComment(@PathParam("commentid") long commentid,
      @Context HttpServletRequest request) {

    Connection conn = (Connection) sc.getAttribute("dbConn");
    CommentDAO commentDAO = new JDBCCommentDAOImpl();
    commentDAO.setConnection(conn);
    Comment comment = commentDAO.get(commentid);
    HttpSession session = request.getSession();
    User user = (User) session.getAttribute("user");

    if (comment != null && user.getIdu() == comment.getSender()) {
      commentDAO.delete(commentid);
      return Response.noContent().build(); // 204 no content
    } else {
      throw new CustomBadRequestException("Error en usuario o id del comentario");
    }

  }
}
