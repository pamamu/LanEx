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
    <title>LanEx - Panel Usuario</title>
    <meta charset="UTF-8">
    <base href="${pageContext.request.contextPath}/"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="assets/css/panel_usuario.css">
    <link rel="icon" href="favicon.ico" type="image/x-icon"/>
    <link rel="shortcut icon" href="favicon.ico" type="image/x-icon"/>
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
        <h1>Panel de Usuario</h1>
        <p>
            A continuación se muestra el panel de usuario con la información del registro.
        </p>
        <c:forEach var="Mensaje" items="${messages}">
            <p><b>${Mensaje}</b></p>
        </c:forEach>
    </section>

    <!--SECCION 5-->
    <section class="info-usuario">
        <div class="contenedor">
            <div class="foto">
                <img src="images/personas/img_avatar${userv.imagen}.png" alt="Avatar de Hombre">
            </div>
            <div id="info_personal">
                <h2>
                    Información Personal:
                </h2>
                <p><b>Nombre:</b> ${userv.nombre}</p>
                <p><b>Apellidos:</b> ${userv.apellidos}</p>
                <p><b>Email:</b> ${userv.email}</p>
                <p><b>Nacionalidad:</b> ${userv.nacionalidad}</p>
                <p><b>Fecha nacimiento:</b> ${userv.fec_nac}</p>
                <p><b>LanEx Points:</b> ${userv.puntos}
                    <span class="rating">
                <c:choose>
                    <c:when test="${userv.puntos <= 10}">
                        <img src="assets/css/images/estrella.png" alt="Estrella">
                    </c:when>
                    <c:when test="${userv.puntos > 10 && userv.puntos <30}">
                        <img src="assets/css/images/estrella.png" alt="Estrella">
                        <img src="assets/css/images/estrella.png" alt="Estrella">
                    </c:when>
                    <c:otherwise>
                        <img src="assets/css/images/estrella.png" alt="Estrella">
                        <img src="assets/css/images/estrella.png" alt="Estrella">
                        <img src="assets/css/images/estrella.png" alt="Estrella">
                    </c:otherwise>
                </c:choose>
                </span>
                </p>
                <c:forEach var="userLanguages" items="${usersLanguages}">
                    <p><b>Idioma:</b> ${userLanguages.first} </p>
                    <ul>
                        <li><p><b>Speaking:</b> ${userLanguages.second.levelSpeaking}</p></li>
                        <li><p><b>Writing:</b> ${userLanguages.second.levelWriting}</p></li>
                        <li><p><b>Reading:</b> ${userLanguages.second.levelReading}</p></li>
                    </ul>
                </c:forEach>
            </div>
            <div class="redes">
                <a href="https://twitter.com/${userv.twitter}" target="_blank">
                    <div class="icon-twitter">
                        ${userv.twitter}
                    </div>
                </a>
                <div class="icon-whatsapp">
                    ${userv.telefono}
                </div>
            </div>
        </div>
        <div class="boton">
            <a href="lanex/searchusers">Ver Usuarios</a>
            <c:if test="${tipo == 'logueado'}">
                <a href="lanex/modifyuser">Modificar Perfil</a>
            </c:if>
            <a href="lanex/newcomment">Nuevo Comentario</a>
            <c:if test="${tipo != 'logueado'}">
                <a href="<c:url value='lanex/contactar?id=${userv.idu}'/>">Contactar</a>
            </c:if>
            <a href="lanex/newmensaje">Mensaje</a>
            <c:if test="${tipo == 'logueado'}">
                <a href="lanex/deleteuser" id="borrar">Borrar</a>
            </c:if>
        </div>
        <c:if test="${tipo != 'logueado'}">
            <div id="valoracion_l">
                <h2>Valoración</h2>
                <div class="contenedor">
                    <a href="<c:url value='lanex/valorar?id=${userv.idu}&punt=1'/>">
                        <img src="images/like.png" id="añadir" alt="añadir favorito">
                    </a>
                    <a href="<c:url value='lanex/valorar?id=${userv.idu}&punt=0'/>">
                        <img src="images/dislike.png" id="añadir" alt="eliminar favorito">
                    </a>
                </div>
            </div>
        </c:if>
        <c:if test="${tipo == 'logueado'}">
            <div id="contactos">
                <h2>Contactos Favoritos</h2>
                <div class="contenedor">
                    <c:forEach var="user_favorito" items="${favoritos}">
                        <a href="<c:url value='lanex/panelusuario?id=${user_favorito.idu}'/>">
                            <div class="chip">
                                <img src="images/personas/img_avatar${user_favorito.imagen}.png"
                                     alt="Avatar de ${user_favorito.username}">
                                    ${user_favorito.nombre}
                            </div>
                        </a>
                    </c:forEach>
                    <a href="lanex/newfavorito">
                        <img src="images/add_user.png" class="anadir" alt="añadir favorito">
                    </a>
                    <a href="lanex/deletefavorito">
                        <img src="images/delete_user.png" class="anadir" alt="eliminar favorito">
                    </a>
                </div>
            </div>
        </c:if>
    </section>
    <section class="valoraciones">
        <h2>
            Comentarios Recibidos
        </h2>
        <div class="contenedor">
            <c:forEach var="comment" items="${commentsReceiver}">
                <div class="tarjeta">
                    <a href="<c:url value='lanex/panelusuario?id=${comment.third.sender}'/>">
                        <img src="images/personas/img_avatar${comment.second}.png"
                             alt="Avatar de ${comment.first}">
                    </a>
                    <div class="valoracion">
                        <h3>${comment.first}</h3>
                        <p>${comment.third.text}</p>
                    </div>
                    <c:if test="${user.idu == comment.third.sender}">
                        <div class="accion">
                            <a href="<c:url value='lanex/modifycomment?id=${comment.third.idc}'/>"><img
                                    src="images/editar.png"
                                    alt="Editar comentario ${comment.third.idc}"/></a>
                            <a href="<c:url value='lanex/deletecomment?id=${comment.third.idc}'/>"><img
                                    src="images/borrar.png"
                                    alt="Borrar comentario ${comment.third.idc}"/></a>
                        </div>
                    </c:if>
                </div>
            </c:forEach>
        </div>
    </section>
    <section class="valoraciones">
        <h2>
            Comentarios Enviados
        </h2>
        <div class="contenedor">
            <c:forEach var="comment" items="${commentsSender}">
                <div class="tarjeta">
                    <a href="<c:url value='lanex/panelusuario?id=${comment.third.receiver}'/>">
                        <img src="images/personas/img_avatar${comment.first}.png"
                             alt="Avatar de ${comment.second}">
                    </a>
                    <div class="valoracion">
                        <h3>${comment.second}</h3>
                        <p>${comment.third.text}</p>
                    </div>
                    <c:if test="${user.idu == comment.third.sender}">
                        <div class="accion">
                            <a href="<c:url value='lanex/modifycomment?id=${comment.third.idc}'/>"><img
                                    src="images/editar.png"
                                    alt="Editar comentario ${comment.third.idc}"/></a>
                            <a href="<c:url value='lanex/deletecomment?id=${comment.third.idc}'/>"><img
                                    src="images/borrar.png"
                                    alt="Borrar comentario ${comment.third.idc}"/></a>
                        </div>
                    </c:if>
                </div>
            </c:forEach>
        </div>
    </section>
    <c:if test="${tipo == 'logueado'}">
        <section class="valoraciones">
            <h2>
                Mensajes recibidos
            </h2>
            <div class="contenedor">
                <c:forEach var="mensaje" items="${mensajesrecibidos}">
                    <div class="tarjeta">
                        <a href="<c:url value='lanex/panelusuario?id=${mensaje.third.receiver}'/>">
                            <img src="images/personas/img_avatar${mensaje.second}.png"
                                 alt="Avatar de ${mensaje.first}">
                        </a>
                        <div class="valoracion">
                            <h3>${mensaje.first}</h3>
                            <p>${mensaje.third.text}</p>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </section>

        <section class="valoraciones">
            <h2>
                Mensajes enviados
            </h2>
            <div class="contenedor">
                <c:forEach var="mensaje" items="${mensajesenviados}">
                    <div class="tarjeta">
                        <a href="<c:url value='lanex/panelusuario?id=${mensaje.third.receiver}'/>">
                            <img src="images/personas/img_avatar${mensaje.first}.png"
                                 alt="Avatar de ${mensaje.second}">
                        </a>
                        <div class="valoracion">
                            <h3>${mensaje.second}</h3>
                            <p>${mensaje.third.text}</p>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </section>
    </c:if>
</main>
<!--FOOTER-->

<footer>
    <jsp:include page="includes/footer.jsp"/>
</footer>

<!--FIN DE FOOTER-->

</body>

</html>