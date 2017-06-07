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

import model.Comment;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class TestCommentDAO {

	static DBConn dbConn;
	static CommentDAO commentDAO;
	static Connection conn;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dbConn = new DBConn();
		conn = dbConn.create();
	    commentDAO = new JDBCCommentDAOImpl();
		commentDAO.setConnection(conn);
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
		
		List<Comment> commentList = commentDAO.getAll();
		
		long id = 0;
		for(Comment comment: commentList){
			assertEquals(comment.getIdc(),id);			
			id++;
		}
	}
	
	@Test
	public void test2BaseDataBySender() {
		
		List<Comment> commentList = commentDAO.getAllBySender(0);
		for(Comment comment: commentList)			
			assertEquals(comment.getSender(),0);			
	}
	
	@Test
	public void test3BaseDataByReceiver() {
		
		List<Comment> commentList = commentDAO.getAllByReceiver(0);
		for(Comment comment: commentList)			
			assertEquals(comment.getReceiver(),0);			
	}
	
	@Test
	public void test4BaseDataBySenderReceiver() {
		
		List<Comment> commentList = commentDAO.getAllBySenderReceiver(0,1);
		for(Comment comment: commentList){			
			assertEquals(comment.getSender(),0);
			assertEquals(comment.getReceiver(),1);
		}
	}
	
	@Test
	public void test5Add(){
		Comment comment01 = new Comment();
		comment01.setSender(4);
		comment01.setReceiver(0);
		comment01.setText("La fuerza es poderosa en esta aplicaci�n");
		
		
		long id= commentDAO.add(comment01);
		Comment comment02 = commentDAO.get(id);
		
		
		assertEquals(comment01.getText(),comment02.getText());
		assertEquals(comment01.getSender(),comment02.getSender());
		assertEquals(comment01.getReceiver(),comment02.getReceiver());
	}
	
	@Test	
	public void test6BaseDataBySearch() {
		
		List<Comment> commentList = commentDAO.getAllBySearch("poderosa");
		for(Comment comment: commentList)		{	
			assertEquals(comment.getReceiver(),0);
			assertEquals(comment.getSender(),4);
		}
	}
	
	

	
	
	@Test
	public void test7Modify(){
		
		Comment comment01 = commentDAO.get(4);
		comment01.setText("Actualizado: La fuerza es todav�a m�s poderosa en esta aplicaci�n");
		commentDAO.save(comment01);
		
		List<Comment> commentList = commentDAO.getAll();
		Comment comment02 = commentList.get(4);
		assertEquals(comment01.getText(),comment02.getText());
	}
	
	@Test
	public void test8Delete(){
		 
		
		List<Comment> commentList = commentDAO.getAll();
		 commentDAO.delete(4);
		commentList = commentDAO.getAll();
		 for(model.Comment comment: commentList)
				assertNotEquals(comment.getIdc(),4);
		 
	}

}
