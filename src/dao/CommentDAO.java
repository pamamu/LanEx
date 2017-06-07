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

package dao;

import java.sql.Connection;
import java.util.List;

import model.Comment;
import util.Pair;
import util.Triplet;


public interface CommentDAO {

	/**
	 * Asocia la conexi�n a la base de datos con este DAO.
	 * 
	 * @param conn
	 *            Conexi�n a la base de datos.
	 */
	public void setConnection(Connection conn);

	/**
	 * Obtiene todos los comentarios de la base de datos.
	 * 
	 * @return Lista con todos los usuarios de la base de datos.
	 */
	public List<Comment> getAll();

	/**
	 * Obtiene todos los comentarios realizados por un usuario dado.
	 * 
	 * @param sender
	 *            Identificador del usuario del que se quiere recuperar los
	 *            comentarios realizados.
	 * 
	 * @return Lista con todos los comentarios enviados por el usuario.
	 */
	public List<Comment> getAllBySender(long sender);


	public List<Triplet<Integer, String, Comment>> getAllBySenderName(long sender);

	/**
	 * Obtiene todos los comentarios recibidos por un usuario dado.
	 * 
	 * @param receiver
	 *            Identificador del usuario del que se quiere recuperar los comentarios recibidos.
	 * 
	 * @return Lista con todos los comentarios recibidos por el usuario.
	 */
	public List<Comment> getAllByReceiver(long receiver);

	public List<Triplet<String, Integer, Comment>> getAllByReceiverName(long receiver);
	
	/**
	 * Obtiene todos los comentarios realizados por un usuario dado y recibidos por otro usuario dado.
	 * 
	 * @param sender
	 *            Identificador del usuario del que se quiere recuperar los comentarios realizados.
	 * 
	 * @param receiver
	 *            Identificador del usuario del que se quiere recuperar los comentarios recibidos.
	 * 
	 * @return Lista con todos los comentarios enviados por un usuario y recibidos por el otro usuario.
	 */
	public List<Comment> getAllBySenderReceiver(long sender,long receiver);

	/**
	 * Obtiene un comentario de la base de datos.
	 * 
	 * @param idc
	 *            Identificador del comentario.
	 * 
	 * @return Comentario con el identificador pasado.
	 */
	public Comment get(long idc);

	/**
	 * Obtiene todos los comentarios que contengan un texto dado.
	 * 
	 * @param search
	 *            Cadena de texto a buscar en los comentarios.
	 * 
	 * @return Lista con todos los comentarios que contienen una cadena de texto.
	 */	
	public List<Comment> getAllBySearch(String search);
	/**
	 * A�ade un comentario a la base de datos.
	 * 
	 * @param comment
	 *            Objeto que contiene la informaci�n relativa al comentario que
	 *            se pretende a�adir.
	 * 
	 * @return Identificador de comentario introducido o -1 si ha fallado la
	 *         operaci�n.
	 *         
	 */
	public long add(Comment comment);

	/**
	 * Actualiza un comentario ya existente.
	 * 
	 * @param comment
	 *            Comentario que se pretende actualizar.
	 * 
	 * @return True si la operaci�n ha tenido �xito. False en caso contrario.
	 */
	public boolean save(Comment comment);

	/**
	 * Elimina un comentario de la base de datos.
	 * 
	 * @param id
	 *            Identificador del comentario que se pretende eliminar.
	 * 
	 * @return True si la operaci�n ha tenido �xito. False en caso contrario.
	 */
	public boolean delete(long id);
}