/*
 * Copyright (C) 2017 dmitry
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package webapi;

/**
 *
 * @author dmitry
 */
public class Info {
    private static final String ver="0.1RC";

    public static String getVer() {
        return ver;
    }

     public static  String getMod() {
        return mod;
    }
     private static final String mod="17-jan-2017";
     
     public static String getPiInfo(){
     return "not implemented";
     }
     
     public static String dbcon="";
     public static String getDBCon(){
     return dbcon;
     }
     public static String dbtype="";
     public static String getDBType(){
     return dbtype;
     }
}