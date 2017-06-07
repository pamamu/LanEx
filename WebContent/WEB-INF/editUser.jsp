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
    <title>LanEx - ${CheckType}</title>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
    <link rel="icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon"/>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico"
          type="image/x-icon"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/registro.css">
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
        <h1>Registro</h1>
        <p>
            Completa el siguiente formulario para formalizar registro en la Web:
        </p>
        <c:forEach var="Mensaje" items="${messages}">
            <p>${Mensaje}</p>
        </c:forEach>
    </section>


    <!--SECCION 5-->
    <section id="usuarios">
        <form method="post" action="?">
            <div class="contenedor">
                <div class="foto">
                    <c:if test="${empty user}">
                        <img src="${pageContext.request.contextPath}/images/personas/img_avatar1.png"
                             alt="Avatar" id="img_perfil">
                    </c:if>
                    <c:if test="${not empty user}">
                        <img src="${pageContext.request.contextPath}/images/personas/img_avatar${user.imagen}.png"
                             alt="Avatar" id="img_perfil">
                    </c:if>

                    <p id="boton-subir"><select name="imagen" id="select_imagen"
                                                onchange="cambiarImagen()">
                        <optgroup label="Chico">
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="6">3</option>
                            <option value="8">4</option>
                            <option value="9">5</option>
                            <option value="11">6</option>
                            <option value="14">7</option>
                            <option value="15">8</option>
                        </optgroup>
                        <optgroup label="Chica">
                            <option value="3">1</option>
                            <option value="4">2</option>
                            <option value="5">3</option>
                            <option value="7">4</option>
                            <option value="10">5</option>
                            <option value="12">6</option>
                            <option value="13">7</option>
                            <option value="16">8</option>
                        </optgroup>
                    </select>
                        <script>
                          function cambiarImagen() {
                            var x = document.getElementById("select_imagen").value;
                            var y = "${pageContext.request.contextPath}/images/personas/img_avatar";
                            document.getElementById("img_perfil").src = y + x + ".png";
                          }
                        </script>
                    </p>
                </div>
                <div class="formulario">
                    <label for="username">Username</label>
                    <input type="text" name="username" id="username" autofocus
                           placeholder="Username" value="${user.username}" required>
                    <label for="nombre">Nombre</label>
                    <input type="text" name="nombre" id="nombre" placeholder="Nombre"
                           value="${user.nombre}" required>
                    <label for="apellidos">Apellidos</label>
                    <input type="text" name="apellidos" id="apellidos" placeholder="Apellidos"
                           value="${user.apellidos}" required>
                    <label for="nacionalidad">Nacionalidad</label>
                    <input type="text" name="nacionalidad" id="nacionalidad"
                           placeholder="Nacionalidad" value="${user.nacionalidad}"
                           required>
                    <label for="email">Email</label>
                    <input type="email" name="email" id="email" placeholder="Email"
                           value="${user.email}" required>
                    <label for="nacim">Fecha Nacimiento</label>
                    <input type="date" id="nacim" name="nacim" value="${user.fec_nac}">
                    <label for="pass">Contraseña</label>
                    <input type="password" name="pass" id="pass" placeholder="Contraseña" required>
                </div>
                <div class="form_idiomas">
                    <div class="idioma" id="campo_idio1">
                        <label for="btn_idio1">Idioma 1</label>
                        <input type="checkbox" name="idio1" id="btn_idio1"
                               <c:if test="${n_idiomas > 0}">checked</c:if>>
                        <div id="idioma1_selec">
                            Idioma:
                            <select name="idioma1">
                                <c:forEach var="Language" items="${idiomas}">
                                    <option value=${Language.langname} <c:if test="${Language.idl == usersLanguages[0].idl}">selected</c:if>>${Language.langname}</option>
                                </c:forEach>
                            </select>
                            <br> Competencia oral:
                            <select name="idioma1_oral">
                                <optgroup label="Básico">
                                    <option value="a1"
                                            <c:if test="${usersLanguages[0].levelSpeaking == 'a1'}">selected</c:if>>
                                        A1
                                    </option>
                                    <option value="a2"
                                            <c:if test="${usersLanguages[0].levelSpeaking == 'a2'}">selected</c:if>>
                                        A2
                                    </option>
                                </optgroup>
                                <optgroup label="Medio">
                                    <option value="b1"
                                            <c:if test="${usersLanguages[0].levelSpeaking == 'B1.PNG'}">selected</c:if>>
                                        B1
                                    </option>
                                    <option value="b2"
                                            <c:if test="${usersLanguages[0].levelSpeaking == 'b2'}">selected</c:if>>
                                        B2
                                    </option>
                                </optgroup>
                                <optgroup label="Avanzado">
                                    <option value="c1"
                                            <c:if test="${usersLanguages[0].levelSpeaking == 'c1'}">selected</c:if>>
                                        C1
                                    </option>
                                    <option value="c2"
                                            <c:if test="${usersLanguages[0].levelSpeaking == 'c2'}">selected</c:if>>
                                        C2
                                    </option>
                                </optgroup>
                            </select>
                            <br> Competencia escrita:
                            <select name="idioma1_escrito">
                                <optgroup label="Básico">
                                    <option value="a1"
                                            <c:if test="${usersLanguages[0].levelWriting == 'a1'}">selected</c:if>>
                                        A1
                                    </option>
                                    <option value="a2"
                                            <c:if test="${usersLanguages[0].levelWriting == 'a2'}">selected</c:if>>
                                        A2
                                    </option>
                                </optgroup>
                                <optgroup label="Medio">
                                    <option value="b1"
                                            <c:if test="${usersLanguages[0].levelWriting == 'B1.PNG'}">selected</c:if>>
                                        B1
                                    </option>
                                    <option value="b2"
                                            <c:if test="${usersLanguages[0].levelWriting == 'b2'}">selected</c:if>>
                                        B2
                                    </option>
                                </optgroup>
                                <optgroup label="Avanzado">
                                    <option value="c1"
                                            <c:if test="${usersLanguages[0].levelWriting == 'c1'}">selected</c:if>>
                                        C1
                                    </option>
                                    <option value="c2"
                                            <c:if test="${usersLanguages[0].levelWriting == 'c2'}">selected</c:if>>
                                        C2
                                    </option>
                                </optgroup>
                            </select>
                            <br> Competencia auditiva:
                            <select name="idioma1_auditiva">
                                <optgroup label="Básico">
                                    <option value="a1"
                                            <c:if test="${usersLanguages[0].levelReading == 'a1'}">selected</c:if>>
                                        A1
                                    </option>
                                    <option value="a2"
                                            <c:if test="${usersLanguages[0].levelReading == 'a2'}">selected</c:if>>
                                        A2
                                    </option>
                                </optgroup>
                                <optgroup label="Medio">
                                    <option value="b1"
                                            <c:if test="${usersLanguages[0].levelReading == 'B1.PNG'}">selected</c:if>>
                                        B1
                                    </option>
                                    <option value="b2"
                                            <c:if test="${usersLanguages[0].levelReading == 'b2'}">selected</c:if>>
                                        B2
                                    </option>
                                </optgroup>
                                <optgroup label="Avanzado">
                                    <option value="c1"
                                            <c:if test="${usersLanguages[0].levelReading == 'c1'}">selected</c:if>>
                                        C1
                                    </option>
                                    <option value="c2"
                                            <c:if test="${usersLanguages[0].levelReading == 'c2'}">selected</c:if>>
                                        C2
                                    </option>
                                </optgroup>
                            </select>
                        </div>
                    </div>
                    <div class="idioma" id="campo_idio2">
                        <label for="btn_idio2">Idioma 2</label>
                        <input type="checkbox" name="idio2" id="btn_idio2"
                               <c:if test="${n_idiomas > 1}">checked</c:if>>
                        <div id="idioma2_selec">
                            Idioma:
                            <select name="idioma2">
                                <c:forEach var="Language" items="${idiomas}">
                                    <option value=${Language.langname} <c:if test="${Language.idl == usersLanguages[1].idl}">selected</c:if>>${Language.langname}</option>
                                </c:forEach>
                            </select>
                            <br> Competencia oral:
                            <select name="idioma2_oral">
                                <optgroup label="Básico">
                                    <option value="a1"
                                            <c:if test="${usersLanguages[1].levelSpeaking == 'a1'}">selected</c:if>>
                                        A1
                                    </option>
                                    <option value="a2"
                                            <c:if test="${usersLanguages[1].levelSpeaking == 'a2'}">selected</c:if>>
                                        A2
                                    </option>
                                </optgroup>
                                <optgroup label="Medio">
                                    <option value="b1"
                                            <c:if test="${usersLanguages[1].levelSpeaking == 'B1.PNG'}">selected</c:if>>
                                        B1
                                    </option>
                                    <option value="b2"
                                            <c:if test="${usersLanguages[1].levelSpeaking == 'b2'}">selected</c:if>>
                                        B2
                                    </option>
                                </optgroup>
                                <optgroup label="Avanzado">
                                    <option value="c1"
                                            <c:if test="${usersLanguages[1].levelSpeaking == 'c1'}">selected</c:if>>
                                        C1
                                    </option>
                                    <option value="c2"
                                            <c:if test="${usersLanguages[1].levelSpeaking == 'c2'}">selected</c:if>>
                                        C2
                                    </option>
                                </optgroup>
                            </select>
                            <br> Competencia escrita:
                            <select name="idioma2_escrito">
                                <optgroup label="Básico">
                                    <option value="a1"
                                            <c:if test="${usersLanguages[1].levelWriting == 'a1'}">selected</c:if>>
                                        A1
                                    </option>
                                    <option value="a2"
                                            <c:if test="${usersLanguages[1].levelWriting == 'a2'}">selected</c:if>>
                                        A2
                                    </option>
                                </optgroup>
                                <optgroup label="Medio">
                                    <option value="b1"
                                            <c:if test="${usersLanguages[1].levelWriting == 'B1.PNG'}">selected</c:if>>
                                        B1
                                    </option>
                                    <option value="b2"
                                            <c:if test="${usersLanguages[1].levelWriting == 'b2'}">selected</c:if>>
                                        B2
                                    </option>
                                </optgroup>
                                <optgroup label="Avanzado">
                                    <option value="c1"
                                            <c:if test="${usersLanguages[1].levelWriting == 'c1'}">selected</c:if>>
                                        C1
                                    </option>
                                    <option value="c2"
                                            <c:if test="${usersLanguages[1].levelWriting == 'c2'}">selected</c:if>>
                                        C2
                                    </option>
                                </optgroup>
                            </select>
                            <br> Competencia auditiva:
                            <select name="idioma2_auditiva">
                                <optgroup label="Básico">
                                    <option value="a1"
                                            <c:if test="${usersLanguages[1].levelReading == 'a1'}">selected</c:if>>
                                        A1
                                    </option>
                                    <option value="a2"
                                            <c:if test="${usersLanguages[1].levelReading == 'a2'}">selected</c:if>>
                                        A2
                                    </option>
                                </optgroup>
                                <optgroup label="Medio">
                                    <option value="b1"
                                            <c:if test="${usersLanguages[1].levelReading == 'B1.PNG'}">selected</c:if>>
                                        B1
                                    </option>
                                    <option value="b2"
                                            <c:if test="${usersLanguages[1].levelReading == 'b2'}">selected</c:if>>
                                        B2
                                    </option>
                                </optgroup>
                                <optgroup label="Avanzado">
                                    <option value="c1"
                                            <c:if test="${usersLanguages[1].levelReading == 'c1'}">selected</c:if>>
                                        C1
                                    </option>
                                    <option value="c2"
                                            <c:if test="${usersLanguages[1].levelReading == 'c2'}">selected</c:if>>
                                        C2
                                    </option>
                                </optgroup>
                            </select>
                        </div>
                    </div>
                    <div class="idioma" id="campo_idio3">
                        <label for="btn_idio3">Idioma 3</label>
                        <input type="checkbox" name="idio3" id="btn_idio3"
                               <c:if test="${n_idiomas > 2}">checked</c:if>>
                        <div id="idioma3_selec">
                            Idioma:
                            <select name="idioma3">
                                <c:forEach var="Language" items="${idiomas}">
                                    <option value=${Language.langname} <c:if test="${Language.idl == usersLanguages[2].idl}">selected</c:if>>${Language.langname}</option>
                                </c:forEach>
                            </select>
                            <br> Competencia oral:
                            <select name="idioma3_oral">
                                <optgroup label="Básico">
                                    <option value="a1"
                                            <c:if test="${usersLanguages[2].levelSpeaking == 'a1'}">selected</c:if>>
                                        A1
                                    </option>
                                    <option value="a2"
                                            <c:if test="${usersLanguages[2].levelSpeaking == 'a2'}">selected</c:if>>
                                        A2
                                    </option>
                                </optgroup>
                                <optgroup label="Medio">
                                    <option value="b1"
                                            <c:if test="${usersLanguages[2].levelSpeaking == 'B1.PNG'}">selected</c:if>>
                                        B1
                                    </option>
                                    <option value="b2"
                                            <c:if test="${usersLanguages[2].levelSpeaking == 'b2'}">selected</c:if>>
                                        B2
                                    </option>
                                </optgroup>
                                <optgroup label="Avanzado">
                                    <option value="c1"
                                            <c:if test="${usersLanguages[2].levelSpeaking == 'c1'}">selected</c:if>>
                                        C1
                                    </option>
                                    <option value="c2"
                                            <c:if test="${usersLanguages[2].levelSpeaking == 'c2'}">selected</c:if>>
                                        C2
                                    </option>
                                </optgroup>
                            </select>
                            <br> Competencia escrita:
                            <select name="idioma3_escrito">
                                <optgroup label="Básico">
                                    <option value="a1"
                                            <c:if test="${usersLanguages[2].levelWriting == 'a1'}">selected</c:if>>
                                        A1
                                    </option>
                                    <option value="a2"
                                            <c:if test="${usersLanguages[2].levelWriting == 'a2'}">selected</c:if>>
                                        A2
                                    </option>
                                </optgroup>
                                <optgroup label="Medio">
                                    <option value="b1"
                                            <c:if test="${usersLanguages[2].levelWriting == 'B1.PNG'}">selected</c:if>>
                                        B1
                                    </option>
                                    <option value="b2"
                                            <c:if test="${usersLanguages[2].levelWriting == 'b2'}">selected</c:if>>
                                        B2
                                    </option>
                                </optgroup>
                                <optgroup label="Avanzado">
                                    <option value="c1"
                                            <c:if test="${usersLanguages[2].levelWriting == 'c1'}">selected</c:if>>
                                        C1
                                    </option>
                                    <option value="c2"
                                            <c:if test="${usersLanguages[2].levelWriting == 'c2'}">selected</c:if>>
                                        C2
                                    </option>
                                </optgroup>
                            </select>
                            <br> Competencia auditiva:
                            <select name="idioma3_auditiva">
                                <optgroup label="Básico">
                                    <option value="a1"
                                            <c:if test="${usersLanguages[2].levelReading == 'a1'}">selected</c:if>>
                                        A1
                                    </option>
                                    <option value="a2"
                                            <c:if test="${usersLanguages[2].levelReading == 'a2'}">selected</c:if>>
                                        A2
                                    </option>
                                </optgroup>
                                <optgroup label="Medio">
                                    <option value="b1"
                                            <c:if test="${usersLanguages[2].levelReading == 'B1.PNG'}">selected</c:if>>
                                        B1
                                    </option>
                                    <option value="b2"
                                            <c:if test="${usersLanguages[2].levelReading == 'b2'}">selected</c:if>>
                                        B2
                                    </option>
                                </optgroup>
                                <optgroup label="Avanzado">
                                    <option value="c1"
                                            <c:if test="${usersLanguages[2].levelReading == 'c1'}">selected</c:if>>
                                        C1
                                    </option>
                                    <option value="c2"
                                            <c:if test="${usersLanguages[2].levelReading == 'c2'}">selected</c:if>>
                                        C2
                                    </option>
                                </optgroup>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="rrss">
                    <div class="icon-twitter">
                        <input type="text" name="twitter" id="twitter"
                               placeholder="@usuario" value="${user.twitter}"
                               required>
                    </div>
                    <div class="icon-whatsapp">
                        <input type="tel" name="telefono" id="telefono"
                               placeholder="+XX XXX XXX XXX" value="${user.telefono}"
                               required>
                    </div>
                    <label for="contacto_preferido">Contacto Preferido</label>
                    <select name="contacto_preferido" id="contacto_preferido">
                        <option value="Twitter"
                                <c:if test="${user.contacto_preferido == 'Twitter'}">selected</c:if>>
                            Twitter
                        </option>
                        <option value="Teléfono"
                                <c:if test="${user.contacto_preferido == 'Teléfono'}">selected</c:if>>
                            Teléfono
                        </option>
                        <option value="Email"
                                <c:if test="${user.contacto_preferido == 'Email'}">selected</c:if>>
                            Email
                        </option>
                    </select>
                </div>
            </div>
            <div class="boton">
                <input type="submit" value="Enviar">
            </div>
        </form>
    </section>
</main>
<!--FOOTER-->

<footer>
    <jsp:include page="includes/footer.jsp"/>
</footer>

<!--FIN DE FOOTER-->

</body>

</html>
