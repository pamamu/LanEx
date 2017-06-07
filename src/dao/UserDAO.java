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
import model.User;


public interface UserDAO {

  /**
   * Asocia la conexi�n a la base de datos con este DAO.
   *
   * @param conn Conexi�n a la base de datos.
   */
  public void setConnection(Connection conn);

  /**
   * Obtiene un usuario de la base de datos.
   *
   * @param idu Identificador del usuario.
   * @return Usuario con el identificador pasado.
   */
  public User get(long idu);
  
  public User getSC(long idu);

  /**
   * Obtiene un usuario dado su nombre.
   *
   * @param username Nombre del usuario que se pretende recuperar.
   * @return Usuario recuperado.
   */
  public User get(String username);
  
  public User getSC(String username);

  public List<User> getDetails(String username, String nacionalidad, String idioma, String nivel,
      String skill, boolean ordenar);

  /**
   * Obtiene todos los usuarios de la base de datos.
   *
   * @return Lista con todos los usuarios de la base de datos.
   */
  public List<User> getAll();

  /**
   * A�ade un usuario a la base de datos.
   *
   * @param user Objeto que contiene la informaci�n relativa al usuario que se pretende a�adir.
   * @return Identificador de usuario introducido o -1 si ha fallado la operaci�n.
   */
  public long add(User user);

  /**
   * Actualiza un usuario ya existente.
   *
   * @param user Usuario que se pretende actualizar.
   * @return True si la operaci�n ha tenido �xito. False en caso contrario.
   */
  public boolean save(User user);

  /**
   * Elimina un usuario de la base de datos.
   *
   * @param idu Identificador del usuario que se pretende eliminar.
   * @return True si la operaci�n ha tenido �xito. False en caso contrario.
   */
  public boolean delete(long idu);

  public void darpuntos(long idu, int puntos);
}
