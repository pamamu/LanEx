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
    <link rel="stylesheet" href="assets/css/directorio.css">
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
            A continuación se muestra el directorie de los usuarios registrados en el sistema y
            filtros para mostrarlos ordenados por algún atributo.
        </p>
        <hr>
        <form method="get" action="lanex/listusers">
            <div class="filtros">
                <input type="text" id="buscar" name="username" placeholder="Buscar Nombre...">
                <h2>Tipo Búsqueda</h2>
                <select name="tipo_busq">
                    <option value="empieza">Empieza por...</option>
                    <option value="contiene">Contiene a...</option>
                    <option value="termina">Termina por...</option>
                </select>

                <h2>Nacionalidad</h2>

                <ul class="paises">
                    <li>
                        <input type="radio" id="alem" value="Alemana" name="nacionalidad"
                               checked="checked">
                        <label for="alem"><img src="images/paises/alemania.png"
                                               alt="bandera de alemania"></label>
                    </li>
                    <li>
                        <input type="radio" id="chin" value="China" name="nacionalidad">
                        <label for="chin"><img src="images/paises/china.jpg" alt="bandera de china"></label>
                    </li>
                    <li>
                        <input type="radio" id="esp" value="Española" name="nacionalidad">
                        <label for="esp"><img src="images/paises/espania.png"
                                              alt="bandera de espania"></label>
                    </li>
                    <li>
                        <input type="radio" id="franc" value="Francesa" name="nacionalidad">
                        <label for="franc"><img src="images/paises/francia.png"
                                                alt="bandera de francia"></label>
                    </li>
                    <li>
                        <input type="radio" id="ing" value="Inglesa" name="nacionalidad">
                        <label for="ing"><img src="images/paises/inglaterra.jpg"
                                              alt="bandera de inglaterra"></label>
                    </li>
                    <li>
                        <input type="radio" id="port" value="Portuguesa" name="nacionalidad">
                        <label for="port"><img src="images/paises/portugal.png"
                                               alt="bandera de portugal"></label>
                    </li>
                    <li>
                        <input type="radio" id="otro" value="otro" name="nacionalidad">
                        <label for="otro"><img src="images/paises/otro.png"
                                               alt="otro"></label>
                    </li>
                    <li>
                        <input type="text" name="otranacionalidad" id="pais-text"
                               placeholder="Otra nacionalidad...">
                    </li>
                </ul>
                <h2>Idioma Hablado</h2>
                <ul class="paises">
                    <li>
                        <input type="radio" id="alem-i" value="Alemán" name="idioma"
                               checked="checked">
                        <label for="alem-i"><img src="images/paises/alemania.png"
                                                 alt="bandera de alemania"></label>
                    </li>
                    <li>
                        <input type="radio" id="chin-i" value="Chino" name="idioma">
                        <label for="chin-i"><img src="images/paises/china.jpg"
                                                 alt="bandera de china"></label>
                    </li>
                    <li>
                        <input type="radio" id="esp-i" value="Español" name="idioma">
                        <label for="esp-i"><img src="images/paises/espania.png"
                                                alt="bandera de espania"></label>
                    </li>
                    <li>
                        <input type="radio" id="franc-i" value="Francés" name="idioma">
                        <label for="franc-i"><img src="images/paises/francia.png"
                                                  alt="bandera de francia"></label>
                    </li>
                    <li>
                        <input type="radio" id="ing-i" value="Inglés" name="idioma">
                        <label for="ing-i"><img src="images/paises/inglaterra.jpg"
                                                alt="bandera de inglaterra"></label>
                    </li>
                    <li>
                        <input type="radio" id="port-i" value="Portugués" name="idioma">
                        <label for="port-i"><img src="images/paises/portugal.png"
                                                 alt="bandera de portugal"></label>
                    </li>
                    <li>
                        <input type="radio" id="otro-i" value="otro-i" name="idioma">
                        <label for="otro-i"><img src="images/paises/otro.png"
                                                 alt="otro"></label>
                    </li>
                    <li>
                        <select name="otroidioma">
                            <c:forEach var="Language" items="${idiomas}">
                                <option value=${Language.langname}>${Language.langname}</option>
                            </c:forEach>
                        </select>
                    </li>
                </ul>
                    <h2>Nivel</h2>
                <ul class="paises">
                    <li>
                        <input type="radio" id="writing" value="writing" name="skill"
                               checked="checked">
                        <label for="writing"><img src="images/niveles/writing.png"
                                                  alt="writing"></label>
                    </li>
                    <li>
                        <input type="radio" id="reading" value="speaking" name="skill">
                        <label for="reading"><img src="images/niveles/speaking.png"
                                                  alt="reading"></label>
                    </li>
                    <li>
                        <input type="radio" id="speaking" value="reading" name="skill">
                        <label for="speaking"><img src="images/niveles/reading.png" alt="speaking"></label>
                    </li>
                </ul>

                <ul class="paises">
                    <li>
                        <input type="radio" id="a1" value="a1" name="nivel" checked>
                        <label for="a1"><img src="images/niveles/A1.png" alt="a1"></label>
                    </li>
                    <li>
                        <input type="radio" id="a2" value="a2" name="nivel">
                        <label for="a2"><img src="images/niveles/A2.png" alt="a2"></label>
                    </li>
                    <li>
                        <input type="radio" id="b1" value="b1" name="nivel">
                        <label for="b1"><img src="images/niveles/B1.png" alt="b1"></label>
                    </li>
                    <li>
                        <input type="radio" id="b2" value="b2" name="nivel">
                        <label for="b2"><img src="images/niveles/B2.png" alt="b2"></label>
                    </li>
                    <li>
                        <input type="radio" id="c1" value="c1" name="nivel">
                        <label for="c1"><img src="images/niveles/C1.png" alt="c1"></label>
                    </li>
                    <li>
                        <input type="radio" id="c2" value="c2" name="nivel">
                        <label for="c2"><img src="images/niveles/C2.png" alt="c2r"></label>
                    </li>
                </ul>
                <ul>
                    <li>
                        <input type="checkbox" name="ordenar" value="ok"> Ordenar por puntuación<br>
                    </li>
                </ul>
                <div class="boton">
                    <input type="submit" value="Buscar">
                </div>
            </div>
        </form>
    </section>

    <!--SECCION 5-->
</main>
<!--FOOTER-->

<footer>
    <jsp:include page="includes/footer.jsp"/>
</footer>
<!--FIN DE FOOTER-->

</body>

</html>
