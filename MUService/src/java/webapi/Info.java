/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
}