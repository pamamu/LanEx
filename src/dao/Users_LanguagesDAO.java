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

import model.Users_Languages;



public interface Users_LanguagesDAO {

	/**
	 * Asocia la conexi�n a la base de datos con este DAO.
	 * 
	 * @param conn
	 *            Conexi�n a la base de datos.
	 */
	public void setConnection(Connection conn);

	/**
	 * Obtiene todos los lenguajes de todos los usuarios de la base de datos.
	 * 
	 * @return Lista con todos los lenguajes de los usuarios de la base de datos.
	 */
	public List<Users_Languages> getAll();

	/**
	 * Obtiene todos los usuarios-lenguajes-nivel en funci�n de un usuario dado.
	 * 
	 * @param idu
	 *            Identificador del usuario del que se quieren recuperar todos los usuarios-lenguajes-nivel
	 * 
	 * @return Lista con todos los usuarios-lenguajes-nivel en funci�n de un usuario dado.
	 */
	public List<Users_Languages> getAllByUser(long idu);
	
	/**
	 * Obtiene todos los usuarios-lenguajes-nivel en funci�n de un lenguaje dado.
	 * 
	 * @param idl
	 *            Identificador del lenguaje del que se quieren recuperar todos los usuarios-lenguajes-nivel
	 * 
	 * @return Lista con todos los usuarios-lenguajes-nivel en funci�n de un lenguaje dado.
	 */
	public List<Users_Languages> getAllByLanguage(long idl);

	/**
	 * Obtiene el usuario-lenguaje-nivel en funci�n de un usuario y un lenguaje dado.
	
	 * @param idu
	 *            Identificador del usuario del que se quieren recuperar usuario-lenguaje-nivel
	 * 	 * 
	 * @param idl
	 *            Identificador del lenguaje del que se quieren recuperar usuario-lenguaje-nivel
	 * 
	 * @return  usuario-lenguaje-nivel en funci�n de un usuario y un lenguaje dado.
	 * */
	public Users_Languages get(long idu,long idl);

	/**
	 * A�ade un usuario-lenguaje-nivel a la base de datos.
	 * 
	 * @param user_language
	 *            Objeto que contiene la informaci�n relativa al usuario-lenguaje-nivel que
	 *            se pretende a�adir.
	 * 
	 * @return True si la operaci�n ha tenido �xito. False en caso contrario.
	 */
	public boolean add(Users_Languages user_language);

	/**
	 * Actualiza un usuario-lenguaje-nivel ya existente.
	 * 
	 * @param user_language
	 *            usuario-lenguaje-nivel que se pretende actualizar.
	 * 
	 * @return True si la operaci�n ha tenido �xito. False en caso contrario.
	 */
	public boolean save(Users_Languages user_language);

	/**
	 * Elimina un usuario-lenguaje-nivel de la base de datos.
	 * 
	 *  @param idu
	 *            Identificador del usuario del que se quiere eliminar usuario-lenguaje-nivel
	 * 	 * 
	 * @param idl
	 *            Identificador del lenguaje del que se quiere eliminar usuario-lenguaje-nivel
	 * 
	 * 
	 * @return True si la operaci�n ha tenido �xito. False en caso contrario.
	 */
	public boolean delete(long idu, long idl);
}