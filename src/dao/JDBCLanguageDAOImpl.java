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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import model.Language;

public class JDBCLanguageDAOImpl implements LanguageDAO {

	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCLanguageDAOImpl.class.getName());
	
	@Override
	public Language get(long idl) {
		if (conn == null) return null;
		
		Language language = null;		
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Language WHERE idl ="+idl);			 
			if (!rs.next()) return null; 
			language  = new Language();	 
			language.setIdl(rs.getLong("idl"));
			language.setLangname(rs.getString("langname"));
			
			logger.info("fetching Language by idl: "+idl+" -> "+language.getIdl()+" "+language.getLangname());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return language;
	}
	
	@Override
	public Language get(String langname) {
		if (conn == null) return null;
		
		Language language = null;		
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Language WHERE langname ='"+langname+"'");			 
			if (!rs.next()) return null; 
			language  = new Language();	 
			language.setIdl(rs.getLong("idl"));
			language.setLangname(rs.getString("langname"));
			
			logger.info("fetching Language by langname: "+ langname + " -> "+ language.getIdl()+" "+language.getLangname());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return language;
	}
	
	
	public List<Language> getAll() {

		if (conn == null) return null;
		
		ArrayList<Language> languages = new ArrayList<Language>();
		try {
			Statement stmt;
			ResultSet rs;
			synchronized(conn){
			  stmt = conn.createStatement();
			  rs = stmt.executeQuery("SELECT * FROM Language");
			}
			while ( rs.next() ) {
				Language language = new Language();
				language.setIdl(rs.getLong("idl"));
				language.setLangname(rs.getString("langname"));
				
				languages.add(language);
				logger.info("fetching languages: "+language.getIdl()+" "+language.getLangname());
								
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return languages;
	}
	

	@Override
	public long add(Language language) {
		long idl=-1;
		long lastidl=-1;
		if (conn != null){

			Statement stmt;
			
			
			try {
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM sqlite_sequence WHERE name ='Language'");			 
				if (!rs.next()) return -1; 
				lastidl=rs.getLong("seq");
								
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO Language (langname) VALUES('"
									+language.getLangname()+"')");
				
								
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM sqlite_sequence WHERE name ='Language'");			 
				if (!rs.next()) return -1; 
				idl=rs.getLong("seq");
				if (idl<=lastidl) return -1;
											
				logger.info("CREATING Language("+idl+"): "+language.getLangname());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return idl;
	}

	@Override
	public boolean save(Language language) {
		boolean done = false;
		if (conn != null){
			
			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("UPDATE Language SET langname='"+language.getLangname()
									+"' WHERE idl = "+language.getIdl());
				logger.info("updating Language: "+language.getIdl()+" "+language.getLangname());
				done= true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return done;

	}

	@Override
	public boolean delete(long idl) {
		boolean done = false;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("DELETE FROM Language WHERE idl ="+idl);
				logger.info("deleting Language: "+idl);
				done= true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public void setConnection(Connection conn) {
		// TODO Auto-generated method stub
		this.conn = conn;
	}

	
}
