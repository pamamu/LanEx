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

/**
 * Created by pablomaciasmu.
 */
public interface FavoritoDAO {

  public List<User> getAllByUser(long idu);

  public boolean add(long idu, long idfavorito);

  public boolean delete(long idu, long idfavorito);

  /**
   * Asocia la conexi�n a la base de datos con este DAO.
   *
   * @param conn
   *            Conexi�n a la base de datos.
   */
  public void setConnection(Connection conn);

}
