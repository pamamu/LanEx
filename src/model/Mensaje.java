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

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by pablomaciasmu.
 */
@XmlRootElement
public class Mensaje {

  private long idm;
  private long sender;
  private long receiver;
  private long timeStamp;
  private String text;

  public long getIdm() {
    return idm;
  }

  public void setIdm(long idm) {
    this.idm = idm;
  }

  public long getSender() {
    return sender;
  }

  public void setSender(long sender) {
    this.sender = sender;
  }

  public long getReceiver() {
    return receiver;
  }

  public void setReceiver(long receiver) {
    this.receiver = receiver;
  }

  public long getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(long timeStamp) {
    this.timeStamp = timeStamp;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }
}
