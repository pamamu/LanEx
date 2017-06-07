/*
 * Copyright (C) 2017 by Pablo Macias
 * pamaciasm@alumnos.unex.es
 *
 * This program is free software; you can redistribute it andor modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the
 * Free Software Foundation, Inc.,
 * 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {

  private long idu;
  private String username;
  private String password;
  private String email;
  private String nombre;
  private String apellidos;
  private String nacionalidad;
  private String fec_nac;
  private String telefono;
  private int imagen;
  private String twitter;
  private String contacto_preferido;
  private int puntos;


  public boolean validate(List<String> validationMessages) {
    if (username == null || username.trim().isEmpty()) {
      validationMessages.add("Rellena el nombre de usuario.");
    } else if (username.length() > 16) {
      validationMessages.add("El nombre de usuario no puede sobrepasar 16 caracteres.");
    } else if (username.length() < 3) {
      validationMessages.add("El nombre de usuario debe ser de al menos 3 caracteres.");
    } else if (username.contains(" ")) {
      validationMessages.add("El nombre de usuario no puede contener espacios.");
    } else if (!username.matches("[a-zA-Z][a-zA-Z0-9_-]*")) {
      validationMessages.add("Nombre de usuario no válido.");
    }

    if (password == null || password.trim().isEmpty()) {
      validationMessages.add("Rellena la contraseña.");
    } else if (password.length() > 40) {
      validationMessages.add("La contraseña no puede sobrepasar los 40 caracteres.");
    } else if (password.length() < 6) {
      validationMessages.add("La contraseña debe tener al menos 6 caracteres.");
    } else if (password.contains(" ")) {
      validationMessages.add("La contraseña no puede contener espacios.");
    } else if (!password.matches("(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]*")) {
      validationMessages
          .add("Contraseña no valida. Debe contener al menos una mayúscula y un número.");
    }

    if (email == null || email.trim().isEmpty()) {
      validationMessages.add("Rellena el email.");
    } else if (email.length() > 50) {
      validationMessages.add("El email no puede sobrepasar los 50 carácteres.");
    } else if (email.contains(" ")) {
      validationMessages.add("El email no puede contener espacios.");
    } else if (!email.matches("[a-zA-Z0-9_\\.\\+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-\\.]+")) {
      validationMessages.add("Email no válido.");
    }

    if (validationMessages.isEmpty()) {
      return true;
    } else {
      return false;
    }
  }

  public long getIdu() {
    return idu;
  }

  public void setIdu(long idu) {
    this.idu = idu;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String name) {
    this.username = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getApellidos() {
    return apellidos;
  }

  public void setApellidos(String apellidos) {
    this.apellidos = apellidos;
  }

  public String getNacionalidad() {
    return nacionalidad;
  }

  public void setNacionalidad(String nacionalidad) {
    this.nacionalidad = nacionalidad;
  }

  public String getFec_nac() {
    return fec_nac;
  }

  public void setFec_nac(String fec_nac) {
    this.fec_nac = fec_nac;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public int getImagen() {
    return imagen;
  }

  public void setImagen(int imagen) {
    this.imagen = imagen;
  }

  public String getTwitter() {
    return twitter;
  }

  public void setTwitter(String twitter) {
    this.twitter = twitter;
  }

  public String getContacto_preferido() {
    return contacto_preferido;
  }

  public void setContacto_preferido(String contacto_preferido) {
    this.contacto_preferido = contacto_preferido;
  }

  public int getPuntos() {
    return puntos;
  }

  public void setPuntos(int puntos) {
    this.puntos = puntos;
  }

  public int sumarPuntos(int puntos) {
    puntos += puntos;
    if (puntos < 0) {
      puntos = 0;
    }
    return puntos;
  }
}


