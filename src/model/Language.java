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
public class Language {
	private long idl;
	private String langname;
	
	
	
	public boolean validate(List<String> validationMessages) {
	if (langname == null || langname.trim().isEmpty()) {
		validationMessages.add("Rellena el nombre de usuario.");
	} else if (langname.length() > 16) {
		validationMessages.add("El nombre del lenguaje no puede sobrepasar 16 caracteres.");
	} else if (langname.length() < 3) {
		validationMessages.add("El nombre del lenguaje debe ser de al menos 3 caracteres.");
	} else if (langname.contains(" ")) {
		validationMessages.add("El nombre del lenguaje no puede contener espacios.");
	} else if (!langname.matches("[a-zA-Z][a-zA-Z]*")) {
		validationMessages.add("Nombre de lenguaje no v�lido.");
	}

	if (validationMessages.isEmpty())
		return true;
	else
		return false;
	}
	
	public long getIdl() {
		return idl;
	}
	public void setIdl(long idl) {
		this.idl = idl;
	}
	public String getLangname() {
		return langname;
	}
	public void setLangname(String langname) {
		this.langname = langname;
	}
	
	
	


}
