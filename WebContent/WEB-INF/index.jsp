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
    <title>LanEx</title>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="assets/css/main.css">
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
    <div id="banner">
        <img src="images/banner.png" alt="Banner de la web">
    </div>
    <!--SECCION 1-->
    <section id="introd">
        <h2>¿Qué es LanEx?</h2>
        <p>
            Este proyecto consiste en una web donde los usuarios pueden contactar con personas para
            hacer intercambios de idiomas. A esta funcionalidad principal se le añaden diversas
            funciones para mejorar el servicio que se le ofrece al usuario.
        </p>
        <p>
            Esta aplicación se basará en el sistema de <b>“Comunidad de Aprendizaje de idiomas”</b>.
            Esto consiste en un entorno donde estudiantes de lenguas trabajan en un rol diferente al
            que están acostumbrado, se convierten en profesores de otros alumnos. Los estudiantes
            que desempeñan el papel de profesor suelen ser nativos de la lengua que le interesa a
            estudiante o estudiantes. De este modo, una misma persona puede actuar como profesor (de
            su lengua nativa) y como alumno (de la lengua que quiere aprender).
        </p>
    </section>

    <!--SECCION 2-->
    <section id="mensajeria">
        <h2>Mensajería entre usuarios</h2>
        <p>
            A través del servicio de mensajería los usuarios pueden chatear con nativos o
            estudiantes de la lengua que prefieran.
        </p>
        <img src="images/mensajeria.png" alt="Imagen de Mensajería">
    </section>

    <!--SECCION 3-->
    <section id="intercambios">
        <h2>Intercambio y Estancias</h2>
        <p>
            En este apartado se facilita a los usuarios la posibilidad de hacer una estancia en el
            extranjero en un alojamiento de otro usuario.
        </p>
        <img src="images/intercambio.png" alt="Imagen de Intercambio">
    </section>

    <!--SECCION 4-->
    <section id="correccion">
        <h2>Corrección de documentos</h2>
        <p>
            En esta parte de la web se ofrece una plataforma donde un usuario puede mandar
            documentos o actividades en un determinado idioma para que un nativo del idioma del
            documento se lo corrija.
        </p>
        <img src="images/correcion.png" alt="Imagen de Corrección">
    </section>

    <!--SECCION 5-->
    <section id="usuarios">
        <h3>Estos usuarios ya están disfrutando de la aplicación</h3>
        <div class="contenedor">
            <div class="usuarios">
                <img src="images/personas/img_avatar2.png" alt="Avatar de Hombre">
                <h4>Pablo</h4>
            </div>
            <div class="usuarios">
                <img src="images/personas/img_avatar5.png" alt="Avatar de Mujer">
                <h4>Lucía</h4>
            </div>
            <div class="usuarios">
                <img src="images/personas/img_avatar3.png" alt="Avatar de Hombre">
                <h4>Álvaro</h4>
            </div>
            <div class="usuarios">
                <img src="images/personas/img_avatar4.png" alt="">
                <h4>Andrea</h4>
            </div>
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
