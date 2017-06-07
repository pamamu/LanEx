<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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

<input type="checkbox" id="btn-menu">
<label for="btn-menu"><img src="${pageContext.request.contextPath}/images/menu-icon.png" alt="Icono de Menú"></label>
<nav class="menu">
    <ul>
        <li><a href="${pageContext.request.contextPath}/index">Inicio</a></li>
        <c:if test="${empty user}">
            <li><a href="registro.html">Registro</a></li>
            <li><a href="${pageContext.request.contextPath}/login">Inicio Sesión</a></li>
        </c:if>
        <c:if test="${not empty user}">
            <li><a href="${pageContext.request.contextPath}/pages/inicio.html">${user.username}</a></li>
            <li><a href="${pageContext.request.contextPath}/logout">Cerrar Sesión</a></li>
        </c:if>
    </ul>
</nav>
