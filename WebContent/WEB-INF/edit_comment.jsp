<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
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

<html lang="es">

<head>
    <title>LanEx - ${CheckType} Comentario</title>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/comentario.css">
    <link rel="icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon"/>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico"
          type="image/x-icon"/>
</head>

<body>

<!-- HEADER -->

<header>
    <jsp:include page="includes/header.jsp"/>
</header>

<!-- CONTENIDO -->
<main>
    <!--SECCION 1-->
    <section id="introd">
        <h1>Crear comentario</h1>
        <p>
            Completa el siguiente formulario para ${CheckType} un comentario:
        </p>
        <c:forEach var="Mensaje" items="${messages}">
            <p>${Mensaje}</p>
        </c:forEach>
    </section>

    <!--SECCION 5-->
    <section id="usuarios">
        <div class="contenedor">
            <form action="?" method="post">
                <label for="emisor">De...</label>
                <input type="text" id="emisor" name="emisor"
                       value="${user.username} - ${user.nombre} ${user.apellidos}" readonly>

                <label for="receptor">Para...</label>
                <input list="lista_usuarios" name="receptor" id="receptor" value="${n_receiver}"
                       <c:if test="${not empty n_receiver}">readonly</c:if>>
                <datalist id="lista_usuarios">
                    <c:forEach var="User" items="${users}">
                    <option value="${User.username}">
                        </c:forEach>
                </datalist>

                <label for="texto">Comentario:</label>
                <textarea id="texto" name="comment"
                          placeholder="Write something..">${comment.text}</textarea>

                <input type="submit" value="${CheckType}">
            </form>
        </div>
    </section>
</main>
<!--FOOTER-->

<footer>
    <jsp:include page="includes/footer.jsp"/>
</footer>

<!--FIN DE FOOTER-->

</body>

</html>

