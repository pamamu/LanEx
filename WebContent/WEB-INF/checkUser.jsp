
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

<html lang="es">

<head>
    <title>LanEx - Confirmación</title>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/panel_usuario.css">
    <link rel="icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon"/>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon"/>
</head>

<body>
<!-- HEADER -->

<header>
    <jsp:include page="includes/header.jsp" />
</header>

<!-- CONTENIDO -->
<main>
    <!--SECCION 1-->
    <section id="introd">
        <h1>Confirmación de ${CheckType}</h1>
    </section>

    <!--SECCION 5-->
    <section class="info-usuario">
        <div class="contenedor">
            <div class="foto">
                <img src="${pageContext.request.contextPath}/images/personas/img_avatar${userb.imagen}.png" alt="Avatar de Hombre">
            </div>
            <div id="info_personal">
                <h2>
                    Información Personal:
                </h2>
                <p><b>Nombre:</b> ${userb.nombre}</p>
                <p><b>Apellidos:</b> ${userb.apellidos}</p>
                <p><b>Email:</b> ${userb.email}</p>
                <p><b>Nacionalidad:</b> ${userb.nacionalidad}</p>
                <p><b>Fecha nacimiento:</b> ${userb.fec_nac}</p>
                <c:forEach var="userLanguages" items="${usersLanguages}">
                    <p><b>Idioma:</b> ${userLanguages.first.langname} </p>
                    <ul>
                        <li><p><b>Speaking:</b> ${userLanguages.second.levelSpeaking}</p></li>
                        <li><p><b>Writing:</b> ${userLanguages.second.levelWriting}</p></li>
                        <li><p><b></b>Reading:</b> ${userLanguages.second.levelReading}</p></li>
                    </ul>
                </c:forEach>
            </div>
            <div class="redes">
                    <div class="icon-twitter">
                        ${userb.twitter}
                    </div>
                <div class="icon-whatsapp">
                    ${userb.telefono}
                </div>
            </div>
        </div>
        <div class="boton">
            <form method="POST">
                <input id="button" type="submit" value="${requestScope.CheckType} Usuario"/>
            </form>
        </div>
    </section>
</main>
<!--FOOTER-->

<footer>
    <jsp:include page="includes/footer.jsp" />
</footer>

<!--FIN DE FOOTER-->

</body>

</html>
