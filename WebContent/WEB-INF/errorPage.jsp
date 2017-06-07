<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
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
    <title>LanEx - Login</title>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/panel_usuario.css">
    <link rel="icon" href="/favicon.ico" type="image/x-icon"/>
    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon"/>
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
        <h1>ERROR</h1>
        <p>
            SE HA PRODUCIDO UN ERROR ${pageContext.errorData.statusCode}
        </p>
    </section>

    <!--SECCION 5-->
    <section id="usuarios">
        <p>
            Página no encontrada: ${pageContext.request.scheme}://${header.host}${pageContext.errorData.requestURI}
        </p>
        <div class="boton">
            <a href="<c:url value='/index'/>">Volver a Inicio</a>
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
