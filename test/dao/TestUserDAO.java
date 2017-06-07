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
import static org.junit.Assert.assertEquals;

import java.sql.Connection;


import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.Test;


import model.User;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class TestUserDAO {

	static DBConn dbConn;
	static UserDAO userDAO;
	static Connection conn;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dbConn = new DBConn();
		conn = dbConn.create();
	    userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
		dbConn.destroy(conn);
	    
	}

	@Before
	public void setUpBeforeMethod() throws Exception {
	
	}

	@Test
	public void test1BaseData() {
		
		User user01 = userDAO.get(0);
		assertEquals(user01.getIdu(),0);
		assertEquals(user01.getUsername(),"darth");
		assertEquals(user01.getPassword(),"vader");
		assertEquals(user01.getEmail(),"darth.vader@darksideoftheforce.org");
		
		User user02 = userDAO.get(1);
		assertEquals(user02.getIdu(),1);
		assertEquals(user02.getUsername(),"han");
		assertEquals(user02.getPassword(),"solo");
		assertEquals(user02.getEmail(),"han.solo@lightsideoftheforce.org");
		
		User user03 = userDAO.get("leia");
		assertEquals(user03.getIdu(),2);
		assertEquals(user03.getUsername(),"leia");
		assertEquals(user03.getPassword(),"organa");
		assertEquals(user03.getEmail(),"leia.organa@lightsideoftheforce.org");
		
		userDAO.getAll();
		
		
		
	}
	
	
	@Test
	public void test2Add(){
		User user01 = new User();
		user01.setUsername("newUser");
		user01.setEmail("newUser@unex.es");
		user01.setPassword("12345");
		userDAO.add(user01);
		
		User user02 = userDAO.get(5);
		assertEquals(user01.getUsername(),user02.getUsername());
		assertEquals(user01.getEmail(),user02.getEmail());
		assertEquals(user01.getPassword(),user02.getPassword());
		
		userDAO.getAll();
	}
	
	@Test
	public void test3Modify(){
		User user01 = userDAO.get(5);
		user01.setIdu(5);
		user01.setUsername("newUser - cambiado");
		userDAO.save(user01);
		
		User user02 = userDAO.get(5);		
		assertEquals(user01.getUsername(),user02.getUsername());
		
		userDAO.getAll();
	}
	
	@Test
	public void test4Delete(){
		 userDAO.delete(5);
		 
		 User user02 = userDAO.get(5);
 		 assertEquals(null, user02);
 		 
 		userDAO.getAll();
	}

}
