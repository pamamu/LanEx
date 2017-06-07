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
import static org.junit.Assert.assertNotEquals;

import java.sql.Connection;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import model.Users_Languages;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class TestUsers_LanguagesDAO {

	static DBConn dbConn;
	static Users_LanguagesDAO users_LanguagesDAO;
	static Connection conn;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dbConn = new DBConn();
		conn = dbConn.create();
	    users_LanguagesDAO = new JDBCUsers_LanguagesDAOImpl();
		users_LanguagesDAO.setConnection(conn);
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
		
		List<Users_Languages> users_LanguagesList = users_LanguagesDAO.getAll();
		
		Users_Languages users_Languages = users_LanguagesDAO.get(0,0);
		
		assertEquals(users_Languages.getLevel(),"Native");
		
		assertEquals(users_LanguagesList.get(0).getLevel(),users_Languages.getLevel());			
			
		
	}
	
	@Test
	public void test2BaseDataByUser() {
		
		List<Users_Languages> users_LanguagesList = users_LanguagesDAO.getAllByUser(0);
		for(Users_Languages users_Languages: users_LanguagesList)			
			assertEquals(users_Languages.getIdu(),0);			
	}
	
	@Test
	public void test3BaseDataByLanguage() {
		
		List<Users_Languages> users_LanguagesList = users_LanguagesDAO.getAllByLanguage(0);
		for(Users_Languages users_Languages: users_LanguagesList)			
			assertEquals(users_Languages.getIdl(),0);			
	}
	
	@Test
	public void test4Add(){
		Users_Languages users_Languages01 = new Users_Languages();
		users_Languages01.setIdu(4);
		users_Languages01.setIdl(2);
		users_Languages01.setLevel("B2");
		
		
		users_LanguagesDAO.add(users_Languages01);
		Users_Languages users_Languages02 = users_LanguagesDAO.get(4,2);
		
		
		assertEquals(users_Languages01.getLevel(),users_Languages02.getLevel());
		assertEquals(users_Languages01.getIdu(),users_Languages02.getIdu());
		assertEquals(users_Languages01.getIdl(),users_Languages02.getIdl());
	}
	
	

	
	
	@Test
	public void test5Modify(){
		
		Users_Languages users_Languages01 = users_LanguagesDAO.get(4,2);
		users_Languages01.setLevel("C1");
		users_LanguagesDAO.save(users_Languages01);
		
		List<Users_Languages> users_LanguagesList = users_LanguagesDAO.getAll();
		Users_Languages users_Languages02 = users_LanguagesList.get(10);
		assertEquals(users_Languages01.getLevel(),users_Languages02.getLevel());
		assertEquals(users_Languages01.getIdu(),users_Languages02.getIdu());
		assertEquals(users_Languages01.getIdl(),users_Languages02.getIdl());
	}
	
	@Test
	public void test6Delete(){
		 
		
		List<Users_Languages> users_LanguagesList = users_LanguagesDAO.getAll();
		 users_LanguagesDAO.delete(4,2);
		 Users_Languages users_Languages01 = new Users_Languages();
			users_Languages01.setIdu(4);
			users_Languages01.setIdl(2);
			users_Languages01.setLevel("C1");
		users_LanguagesList = users_LanguagesDAO.getAll();
		 for(model.Users_Languages users_Languages: users_LanguagesList)
				assertNotEquals(users_Languages,users_Languages01);
		 		
		 
	}

}
