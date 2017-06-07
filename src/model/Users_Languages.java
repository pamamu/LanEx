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
public class Users_Languages {

  private long idu;
  private long idl;
  private String level;
  private String levelSpeaking;
  private String levelWriting;
  private String levelReading;


  public boolean validate(List<String> validationMessages) {
    if (level == null || level.trim().isEmpty()) {
      validationMessages.add("Rellena el nivel del usuario.");
    } else if (level.length() > 7) {
      validationMessages.add("El nivel no puede sobrepasar 6 caracteres.");
    } else if (level.length() < 2) {
      validationMessages.add("El nivel debe ser de al menos 2 caracteres.");
    } else if (level.contains(" ")) {
      validationMessages.add("El nivel no puede contener espacios.");
    } else if (!level.matches("[a-zA-Z][a-zA-Z0-9]*")) {
      validationMessages.add("Nivel de lenguaje no v�lido.");
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

  public long getIdl() {
    return idl;
  }

  public void setIdl(long idl) {
    this.idl = idl;
  }

  public String getLevel() {
    return level;
  }

  public void setLevel(String name) {
    this.level = name;
  }

  public String getLevelSpeaking() {
    return levelSpeaking;
  }

  public void setLevelSpeaking(String levelSpeaking) {
    this.levelSpeaking = levelSpeaking;
  }

  public String getLevelWriting() {
    return levelWriting;
  }

  public void setLevelWriting(String levelWriting) {
    this.levelWriting = levelWriting;
  }

  public String getLevelReading() {
    return levelReading;
  }

  public void setLevelReading(String levelReading) {
    this.levelReading = levelReading;
  }
}
