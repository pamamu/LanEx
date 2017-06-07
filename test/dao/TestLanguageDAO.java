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


import model.Language;



@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class TestLanguageDAO {

	static DBConn dbConn;
	static LanguageDAO languageDAO;
	static Connection conn;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dbConn = new DBConn();
		conn = dbConn.create();
	    languageDAO = new JDBCLanguageDAOImpl();
		languageDAO.setConnection(conn);
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
		
		Language language01 = languageDAO.get(0);
		assertEquals(language01.getIdl(),0);
		assertEquals(language01.getLangname(),"English");

		Language language02 = languageDAO.get(1);
		assertEquals(language02.getIdl(),1);
		assertEquals(language02.getLangname(),"Spanish");
		
		Language language03 = languageDAO.get(2);
		assertEquals(language03.getIdl(),2);
		assertEquals(language03.getLangname(),"French");
		
		Language language04 = languageDAO.get(3);
		assertEquals(language04.getIdl(),3);
		assertEquals(language04.getLangname(),"German");
		
		Language language05 = languageDAO.get(4);
		assertEquals(language05.getIdl(),4);
		assertEquals(language05.getLangname(),"Portuguese");
		
						
		languageDAO.getAll();
		
		
		
	}
	
	
	@Test
	public void test2Add(){
		Language language01 = new Language();
		language01.setLangname("newLanguage");
		languageDAO.add(language01);
		
		Language language02 = languageDAO.get(10);
		assertEquals(language01.getLangname(),language02.getLangname());
		
		languageDAO.getAll();
	}
	
	@Test
	public void test3Modify(){
		Language language01 = languageDAO.get(10);
		language01.setIdl(10);
		language01.setLangname("changedLanguage");
		languageDAO.save(language01);
		
		Language language02 = languageDAO.get(10);		
		assertEquals(language01.getLangname(),language02.getLangname());
		
		languageDAO.getAll();
	}
	
	@Test
	public void test4Delete(){
		 languageDAO.delete(10);
		 
		 Language language02 = languageDAO.get(10);
 		 assertEquals(null, language02);
 		 
 		languageDAO.getAll();
	}

}
