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

import model.Language;


public interface LanguageDAO {

	/**
	 * Asocia la conexi�n a la base de datos con este DAO.
	 * 
	 * @param conn
	 *            Conexi�n a la base de datos.
	 */
	public void setConnection(Connection conn);

	/**
	 * Obtiene un lenguaje de la base de datos.
	 * 
	 * @param idl
	 *            Identificador del lenguaje.
	 * 
	 * @return Lenguaje con el identificador pasado.
	 */
	public Language get(long idl);

	/**
	 * Obtiene un lenguaje dado su nombre.
	 * 
	 * @param langname
	 *            Nombre del lenguaje que se pretende recuperar.
	 * 
	 * @return Lenguaje con el nombre pasado.
	 */
	public Language get(String langname);

	/**
	 * Obtiene todos los lenguajes de la base de datos.
	 * 
	 * @return Lista con todos los lenguajes de la base de datos.
	 */
	public List<Language> getAll();

	/**
	 * A�ade un lenguaje a la base de datos.
	 * 
	 * @param language
	 *            Objeto que contiene la informaci�n relativa al lenguaje que se pretende a�adir.
	 * 
	 * @return Identificador de lenguaje introducido o -1 si ha fallado la
	 *         operaci�n.
	 */
	public long add(Language language);

	/**
	 * Actualiza un lenguaje ya existente.
	 * 
	 * @param language
	 *            Lenguaje que se pretende actualizar.
	 * 
	 * @return True si la operaci�n ha tenido �xito. False en caso contrario.
	 */
	public boolean save(Language language);

	/**
	 * Elimina un lenguaje de la base de datos.
	 * 
	 * @param idl
	 *            Identificador del lenguaje que se pretende eliminar.
	 * 
	 * @return True si la operaci�n ha tenido �xito. False en caso contrario.
	 */
	public boolean delete(long idl);
}
