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
    <title>LanEx - Directorio</title>
    <base href="${pageContext.request.contextPath}/"/>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="assets/css/directorio_vi.css">
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
        <h1>Directorio de usuarios</h1>
        <p>
            A continuación se muestra el directorio de los usuarios registrados en el sistema y
            filtros para mostrarlos ordenados por algún atributo.
        </p>
    </section>

    <section id="usuarios">

        <div class="contenedor">
            <c:forEach var="user" items="${users}">
                <div class="tarjeta">
                    <div class="imagen">
                        <img class="image" src="images/personas/img_avatar${user.imagen}.png"
                             alt="Avatar de ${user.username}">
                        <div class="texto-mitad">
                            <a href="<c:url value='lanex/panelusuario?id=${user.idu}'/>">
                                <div class="texto-m">Ver Perfil</div>
                            </a>
                        </div>
                    </div>
                    <a href="<c:url value='lanex/panelusuario?id=${user.idu}'/>">
                        <h2>${user.username}</h2>
                        <h3>${user.nombre} ${user.apellidos}</h3>
                    </a>
                </div>
            </c:forEach>
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
