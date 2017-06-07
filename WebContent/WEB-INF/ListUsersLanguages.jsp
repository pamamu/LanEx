<%@ page language="java" contentType="text/html; charset=UTF-8"     pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  ~ Copyright (C) 2017 by Pablo Macias
  ~ pamaciasm@alumnos.unex.es
  ~
  ~ This program is free software; you can redistribute it andor modify
  ~ it under the terms of the GNU General Public License as published by
  ~ the Free Software Foundation; either version 2 of the License, or
  ~ (at your option) any later version.
  ~
  ~ This program is distributed in the hope that it will be useful,
  ~ but WITHOUT ANY WARRANTY; without even the implied warranty of
  ~ MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  ~ GNU General Public License for more details.
  ~
  ~ You should have received a copy of the GNU General Public License
  ~ along with this program; if not, write to the
  ~ Free Software Foundation, Inc.,
  ~ 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
  --%>

<!DOCTYPE html>
<html id="list">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet"  href="${pageContext.request.contextPath}/css/stylesheet.css"   />
<title>List of users and languages</title>
</head>
<body>
<h1> List of users and languages:</h1> 
	    
		<table>
		<thead>
		<tr>
		<th>Username</th>
		<th>User email</th>
		<th>Language</th>
		<th>Level</th>
		<th>LevelSpeaking</th>
		<th>LevelWriting</th>
		<th>LevelReading</th>
		</thead>
		<tbody>
		<c:forEach var="userLanguage" items="${usersLanguages}">
		<tr> 
		<td>${userLanguage.first.username}</td>
		<td>${userLanguage.first.email}</td>
	    <td>${userLanguage.second.langname}</td>
	    <td>${userLanguage.third.level}</td>
	    <td>${userLanguage.third.levelSpeaking}</td>
	    <td>${userLanguage.third.levelWriting}</td>
	    <td>${userLanguage.third.levelReading}</td>
		</tr>	
	    </c:forEach>
	    </tbody>	
		</table>	
</body>
</html>
