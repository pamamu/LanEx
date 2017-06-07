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

package util;

/**
 * Created by pablomaciasmu.
 */
public class Encriptar {

  /* Retorna un hash a partir de un tipo y un texto */
  public static String getHash(String txt, String hashType) {
    try {
      java.security.MessageDigest md = java.security.MessageDigest
          .getInstance(hashType);
      byte[] array = md.digest(txt.getBytes());
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < array.length; ++i) {
        sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
            .substring(1, 3));
      }
      return sb.toString();
    } catch (java.security.NoSuchAlgorithmException e) {
      System.out.println(e.getMessage());
    }
    return null;
  }

  /* Retorna un hash MD5 a partir de un texto */
  public static String md5(String txt) {
    return Encriptar.getHash(txt, "MD5");
  }

  /* Retorna un hash SHA1 a partir de un texto */
  public static String sha1(String txt) {
    return Encriptar.getHash(txt, "SHA1");
  }

/*
  public static void main(String[] args) {
    System.out.println("Pamamu63 - " + sha1("Pamamu63"));
    System.out.println("Andrea63 - " + sha1("Andrea63"));
    System.out.println("skywalker - " + sha1("skywalker"));
    System.out.println("palpatine - " + sha1("palpatine"));
    System.out.println("P4m4mu - " + sha1("P4m4mu"));
    System.out.println("vader - " + sha1("vader"));
    System.out.println("juanfranAA33 - " + sha1("juanfranAA33"));
    System.out.println("prueba00A - " + sha1("prueba00A"));
    System.out.println("Alvarito385 - " + sha1("Alvarito385"));
    System.out.println("Elenita33 - " + sha1("Elenita33"));
  }
*/
}
