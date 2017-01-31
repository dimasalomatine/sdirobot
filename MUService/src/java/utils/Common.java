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
package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author dmitry
 */
public class Common {
    public static String appname="muservice";
    private static final String recidexpr = "left(replace(NEWID(),'-','' ),@size@)";

    public static String recidexpr() {
        return recidexpr(15);
    }

    /**
     * recidexpr
     * @param size
     * @return size min 15 max 36
     */
    public static String recidexpr(int size) {
        if(size<=0)size=15;
        else if(size>36)size=36;
        return recidexpr.replace("@size@", "" + size);
    }

    public static String recidByUUID(int size) {
        UUID uuid = UUID.randomUUID();
        String urepl = uuid.toString().replace("-", "");
        return urepl.substring(0, (size <= urepl.length() ? size : urepl.length())).toUpperCase();
    }

    public static String goodsqllike(String _str) {
        String ret = _str;
        ret = ret.replace("[", "%");
        ret = ret.replace("]", "%");
        return ret;
    }

    public static boolean cmptxt(String s1, String s2) {
        if (s1 == null) {
            s1 = "";
        }
        if (s2 == null) {
            s2 = "";
        }
        return s1 != null && s1.equalsIgnoreCase(s2.toLowerCase());
    }
   
    public static boolean emptyString(String s) {
        boolean ret = false;
        if (s == null || s.equals("")) {
            ret = true;
        }
        return ret;
    }

    public static String trim(String s) {
        try {
            if (s != null) {
                return s.trim();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    

    public static String changeExtension(String fn, String ext) {
        int p = fn.lastIndexOf(".");
        StringBuilder ret = new StringBuilder();
        if (p == -1) {
            ret.append(fn);
            ret.append(".");
        } else {
            ret.append(fn.substring(0, p + 1));
        }
        ret.append(ext);
        return ret.toString();
    }

    public static String clearExtension(String fn) {
        int p = fn.lastIndexOf(".");
        String ret = null;
        if (p == -1) {
            ret = fn;
        } else {
            ret = fn.substring(0, p);
        }
        return ret;
    }

    public static String extractFileExt(String fn) {
        int p = fn.lastIndexOf(".");
        String ret = "";
        if (p != -1) {
            ret = fn.substring(p + 1);
        }
        return ret;
    }

    public static String extractFileName(String fn) {

        int p = fn.lastIndexOf(System.getProperty("file.separator"));
        String ret = fn;
        if (p != -1) {

            ret = fn.substring(p + 1);
        }
        return ret;
    }

    public static String extractFilePath(String fn) {
        int p = fn.lastIndexOf(System.getProperty("file.separator"));
        String ret = fn;
        if (p != -1) {
            ret = fn.substring(0, p + 1);
        }
        return ret;
    }

    public static boolean fileExists(String fn) {
        File fl = new File(fn);
        return fl.exists();
    }

    public static void copyFile(String fromFileName, String toFileName, String appworkingdir)
            throws IOException {
        File fromFile = new File(fromFileName);
        File toFile = new File(toFileName);

        if (!fromFile.exists()) {
            throw new IOException("FileCopy: " + "no such source file: "
                    + fromFileName);
        }
        if (!fromFile.isFile()) {
            throw new IOException("FileCopy: " + "can't copy directory: "
                    + fromFileName);
        }
        if (!fromFile.canRead()) {
            throw new IOException("FileCopy: " + "source file is unreadable: "
                    + fromFileName);
        }

        if (toFile.isDirectory()) {
            toFile = new File(toFile, fromFile.getName());
        }

        if (toFile.exists()) {
            if (!toFile.canWrite()) {
                throw new IOException("FileCopy: "
                        + "destination file is unwriteable: " + toFileName);
            }
        } else {
            String parent = toFile.getParent();
            if (parent == null) {
                parent = System.getProperty("user.dir");
            }
            File dir = new File(parent);
            if (!dir.exists()) {
                throw new IOException("FileCopy: "
                        + "destination directory doesn't exist: " + parent);
            }
            if (dir.isFile()) {
                throw new IOException("FileCopy: "
                        + "destination is not a directory: " + parent);
            }
            if (!dir.canWrite()) {
                throw new IOException("FileCopy: "
                        + "destination directory is unwriteable: " + parent);
            }
        }

        FileInputStream from = null;
        FileOutputStream to = null;
        try {
            from = new FileInputStream(fromFile);
            to = new FileOutputStream(toFile);
            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = from.read(buffer)) != -1) {
                to.write(buffer, 0, bytesRead); // write
            }
        } finally {
            if (from != null) {
                try {
                    from.close();
                } catch (IOException e) {
                    Common.debugingLine("common.copyFile <EXCEPTION> message=" + e.toString(), appworkingdir);
                }
            }
            if (to != null) {
                try {
                    to.close();
                } catch (IOException e) {
                    Common.debugingLine("common.copyFile <EXCEPTION> message=" + e.toString(), appworkingdir);
                }
            }
        }
    }

    public static String setFileName(String dir, String fn) {
        if (dir.charAt(dir.length() - 1) != '\\') {
            fn = "\\" + fn;
        }
        return dir + fn;
    }

    public static void debugingLine2D(String s) {
        setDebugingLine(s, "eventlog", System.getProperty("user.dir"));
    }
    public static void debugingLine2D(String s, String type) {
        setDebugingLine(s, type, System.getProperty("user.dir"));
    }
    
    public static void debugingLine(String s, String appworkingdir) {
        setDebugingLine(s, "messages", appworkingdir);
    }
    

    public static void setDebugingLine(String s, String subDir, String appworkingdir) {
        FileWriter fr = null;
        try {
            Calendar cl = new GregorianCalendar();
            Date d = new Date();
            cl.setTimeInMillis(d.getTime());

            String dt = "";
            String v;
            dt = String.valueOf(cl.get(GregorianCalendar.YEAR)).substring(2);
            v = String.valueOf(cl.get(GregorianCalendar.MONTH) + 1);
            if (v.length() == 1) {
                v = "0" + v;
            }
            dt += v;
            v = String.valueOf(cl.get(GregorianCalendar.DAY_OF_MONTH));
            if (v.length() == 1) {
                v = "0" + v;
            }
            dt += v;
            v = String.valueOf(cl.get(GregorianCalendar.HOUR_OF_DAY));
            if (v.length() == 1) {
                v = "0" + v;
            }
            dt += " " + v;
            v = String.valueOf(cl.get(GregorianCalendar.MINUTE));
            if (v.length() == 1) {
                v = "0" + v;
            }
            dt += v;
            v = String.valueOf(cl.get(GregorianCalendar.SECOND));
            if (v.length() == 1) {
                v = "0" + v;
            }
            dt += v;
            v = String.valueOf(cl.get(GregorianCalendar.MILLISECOND));
            while (v.length() < 3) {
                v = "0" + v;
            }
            dt += " " + v;

            s = s.replaceAll("\r\n", " ");
            s = s.replaceAll("\r", " ");
            s = s.replaceAll("\n", " ");
            s = s.replaceAll("\t", " ");
            s = appname+"--" + dt + ">" + s;

            String fn = String.valueOf(cl.get(GregorianCalendar.YEAR));
            v = String.valueOf(cl.get(GregorianCalendar.MONTH) + 1);
            if (v.length() == 1) {
                v = "0" + v;
            }
            fn += v;

            String fp = appworkingdir + File.separator+"LogFiles"+File.separator + subDir +File.separator+ "current.log";

            File f = new File(fp);
            if (f.exists()) {
                if (f.length() > 1000000) {
                    String fp1 = appworkingdir + File.separator+"LogFiles"+File.separator + subDir + File.separator + fn + " " + dt.substring(4, 11).replaceAll(" ", "") + ".log";
                    File f1 = new File(fp1);
                    if (!f.renameTo(f1)) {
                        System.out.println("Cant rename:" + fp + " to:" + fp1);
                    }
                }
            }

            fr = new FileWriter(fp, true);
            for (int i = 0; i < s.length(); i++) {
                fr.append(s.charAt(i));
            }
            fr.append('\r');
            fr.append('\n');
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    public static void setDebugingLine(String s, String appworkingdir) {
        FileWriter fr = null;
        try {
            Calendar cl = new GregorianCalendar();
            Date d = new Date();
            cl.setTimeInMillis(d.getTime());

            String dt = "";
            String v;
            dt = String.valueOf(cl.get(GregorianCalendar.YEAR)).substring(2);
            v = String.valueOf(cl.get(GregorianCalendar.MONTH) + 1);
            if (v.length() == 1) {
                v = "0" + v;
            }
            dt += v;
            v = String.valueOf(cl.get(GregorianCalendar.DAY_OF_MONTH));
            if (v.length() == 1) {
                v = "0" + v;
            }
            dt += v;
            v = String.valueOf(cl.get(GregorianCalendar.HOUR_OF_DAY));
            if (v.length() == 1) {
                v = "0" + v;
            }
            dt += " " + v;
            v = String.valueOf(cl.get(GregorianCalendar.MINUTE));
            if (v.length() == 1) {
                v = "0" + v;
            }
            dt += v;
            v = String.valueOf(cl.get(GregorianCalendar.SECOND));
            if (v.length() == 1) {
                v = "0" + v;
            }
            dt += v;
            v = String.valueOf(cl.get(GregorianCalendar.MILLISECOND));
            while (v.length() < 3) {
                v = "0" + v;
            }
            dt += " " + v;

            s = s.replaceAll("\r\n", " ");
            s = s.replaceAll("\r", " ");
            s = s.replaceAll("\n", " ");
            s = s.replaceAll("\t", " ");
            s = "exchsrvr--" + dt + ">" + s;

            String fn = String.valueOf(cl.get(GregorianCalendar.YEAR));
            v = String.valueOf(cl.get(GregorianCalendar.MONTH) + 1);
            if (v.length() == 1) {
                v = "0" + v;
            }
            fn += v;

            String fp = appworkingdir + File.separator+"ServerLog"+File.separator+"current.log";

            File f = new File(fp);
            if (f.exists()) {
                if (f.length() > 1000000) {
                    String fp1 = appworkingdir + File.separator+"ServerLog"+File.separator + fn + " " + dt.substring(4, 11).replaceAll(" ", "") + ".log";
                    File f1 = new File(fp1);
                    if (!f.renameTo(f1)) {
                        System.out.println("Cant rename:" + fp + " to:" + fp1);
                    }
                }
            }

            fr = new FileWriter(fp, true);
            for (int i = 0; i < s.length(); i++) {
                fr.append(s.charAt(i));
            }
            fr.append('\r');
            fr.append('\n');
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    public static void setLogLine(String s, String fn) {
        FileWriter fr = null;
        try {
            fr = new FileWriter(fn, true);
            for (int i = 0; i < s.length(); i++) {
                fr.append(s.charAt(i));
            }
            fr.append('\r');
            fr.append('\n');

        } catch (IOException e) {
            System.out.println("<EXCEPTION>" + e.getMessage());
        } finally {
            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException ex) {
                System.out.println("<EXCEPTION>" + ex.getMessage());
            }
        }
    }

    public static ArrayList<String> readFile(String fn) {
        ArrayList<String> res = new ArrayList<String>();
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(fn);
            br = new BufferedReader(fr);
            while (br.ready()) {
                res.add(br.readLine());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }

        return res;
    }

    public static String extractQuotedStr(String s) {
        String ret = null;
        if (Common.cmptxt(s, "")) {
            ret = "";
        }
        if (s.charAt(0) != '\'') {
            ret = s;
        } else {
            s = s.substring(1, s.length() - 1);
            ret = s.replaceAll("''", "'");
        }
        return ret;
    }

    public static boolean strToBoolDef(String value, boolean def) {
        boolean ret = def;
        if (Common.cmptxt(value, "1")) {
            ret = true;
        } else if (Common.cmptxt(value, "-1")) {
            ret = true;
        } else if (Common.cmptxt(value, "0")) {
            ret = false;
        } else if (Common.cmptxt(value, "true")) {
            ret = true;
        } else if (Common.cmptxt(value, "false")) {
            ret = false;
        } else if (Common.cmptxt(value, "y")) {
            ret = true;
        } else if (Common.cmptxt(value, "n")) {
            ret = false;
        }

        return ret;
    }

    public static String boolToStr(boolean v) {
        return v ? "1" : "0";
    }

    public static String formatDateTime(String format, Date d) {
        String ret = format;
        try {
            Calendar cl = new GregorianCalendar();
            cl.setTimeInMillis(d.getTime());
            String year = String.valueOf(cl.get(GregorianCalendar.YEAR));

            String month = String.valueOf(cl.get(GregorianCalendar.MONTH) + 1);
            if (month.length() == 1 && format.indexOf("mm") >= 0) {
                month = "0" + month;
            }
            String day = String.valueOf(cl.get(GregorianCalendar.DAY_OF_MONTH));
            if (day.length() == 1 && format.indexOf("dd") >= 0) {
                day = "0" + day;
            }
            String hour = String.valueOf(cl.get(GregorianCalendar.HOUR_OF_DAY));
            if (hour.length() == 1) {
                hour = "0" + hour;
            }
            String min = String.valueOf(cl.get(GregorianCalendar.MINUTE));
            if (min.length() == 1) {
                min = "0" + min;
            }
            String sec = String.valueOf(cl.get(GregorianCalendar.SECOND));
            if (sec.length() == 1) {
                sec = "0" + sec;
            }


            ret = ret.replace("dd", day);
            ret = ret.replace("mm", month);
            ret = ret.replace("yyyy", year);
            ret = ret.replace("hh", hour);
            ret = ret.replace("nn", min);
            ret = ret.replace("ss", sec);

        } catch (Exception e) {
            System.err.println("Common.formatDateTime <EXCEPTION> message=" + e.toString());
            ret = ret.replace("dd", "01");
            ret = ret.replace("mm", "01");
            ret = ret.replace("yyyy", "1900");
            ret = ret.replace("hh", "00");
            ret = ret.replace("nn", "00");
            ret = ret.replace("ss", "00");
        }
        return ret;
    }

    public static void copyFile(String fromFileName, String toFileName)
            throws IOException {
        File fromFile = new File(fromFileName);
        File toFile = new File(toFileName);

        if (!fromFile.exists()) {
            throw new IOException("FileCopy: " + "no such source file: "
                    + fromFileName);
        }
        if (!fromFile.isFile()) {
            throw new IOException("FileCopy: " + "can't copy directory: "
                    + fromFileName);
        }
        if (!fromFile.canRead()) {
            throw new IOException("FileCopy: " + "source file is unreadable: "
                    + fromFileName);
        }

        if (toFile.isDirectory()) {
            toFile = new File(toFile, fromFile.getName());
        }

        if (toFile.exists()) {
            if (!toFile.canWrite()) {
                throw new IOException("FileCopy: "
                        + "destination file is unwriteable: " + toFileName);
            }
        } else {
            String parent = toFile.getParent();
            if (parent == null) {
                parent = System.getProperty("user.dir");
            }
            File dir = new File(parent);
            if (!dir.exists()) {
                throw new IOException("FileCopy: "
                        + "destination directory doesn't exist: " + parent);
            }
            if (dir.isFile()) {
                throw new IOException("FileCopy: "
                        + "destination is not a directory: " + parent);
            }
            if (!dir.canWrite()) {
                throw new IOException("FileCopy: "
                        + "destination directory is unwriteable: " + parent);
            }
        }

        FileInputStream from = null;
        FileOutputStream to = null;
        try {
            from = new FileInputStream(fromFile);
            to = new FileOutputStream(toFile);
            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = from.read(buffer)) != -1) {
                to.write(buffer, 0, bytesRead); // write
            }
        } finally {
            if (from != null) {
                try {
                    from.close();
                } catch (IOException e) {
                    System.err.println( "common.copyFile <EXCEPTION> message=" + e.toString());
                }
            }
            if (to != null) {
                try {
                    to.close();
                } catch (IOException e) {
                    System.err.println( "common.copyFile <EXCEPTION> message=" + e.toString());
                }
            }
        }
    }
    //TODO REDUNDAND CODE

    public static String QuotedStr(String s) {
        if (s == null) {
            s = "";
        }
        String r = s.replaceAll("'", "''");
        return "'" + r + "'";
    }

    public static String quotedStr(String s) {
        if (s == null) {
            s = "";
        }
        String r = s.replaceAll("'", "''");
        return "'" + r + "'";
    }

    public static String dateToSQLStr(Date d) {
        try {
            Calendar cl = new GregorianCalendar();
            cl.setTimeInMillis(d.getTime());
            String year = String.valueOf(cl.get(GregorianCalendar.YEAR));
            String month = String.valueOf(cl.get(GregorianCalendar.MONTH) + 1);
            if (month.length() == 1) {
                month = "0" + month;
            }
            String day = String.valueOf(cl.get(GregorianCalendar.DAY_OF_MONTH));
            if (day.length() == 1) {
                day = "0" + day;
            }
            String hour = String.valueOf(cl.get(GregorianCalendar.HOUR_OF_DAY));
            if (hour.length() == 1) {
                hour = "0" + hour;
            }
            String min = String.valueOf(cl.get(GregorianCalendar.MINUTE));
            if (min.length() == 1) {
                min = "0" + min;
            }
            String sec = String.valueOf(cl.get(GregorianCalendar.SECOND));
            if (sec.length() == 1) {
                sec = "0" + sec;
            }

            return month + "/" + day + "/" + year + " " + hour + ":" + min + ":" + sec;
        } catch (Exception e) {
            System.err.println( "common.dateToStr <EXCEPTION> message=" + e.toString());
        }
        return "01/01/1900 00:00:00";
    }

    public static String timeToSQLStr(Date d) {
        try {
            Calendar cl = new GregorianCalendar();
            cl.setTimeInMillis(d.getTime());
            String hour = String.valueOf(cl.get(GregorianCalendar.HOUR_OF_DAY));
            if (hour.length() == 1) {
                hour = "0" + hour;
            }
            String min = String.valueOf(cl.get(GregorianCalendar.MINUTE));
            if (min.length() == 1) {
                min = "0" + min;
            }
            String sec = String.valueOf(cl.get(GregorianCalendar.SECOND));
            if (sec.length() == 1) {
                sec = "0" + sec;
            }

            return hour + ":" + min + ":" + sec;
        } catch (Exception e) {
            System.err.println( "common.dateToStr <EXCEPTION> message=" + e.toString());
        }
        return "00:00:00";
    }

    public static boolean EmptyString(String s) {
        if (s == null) {
            return true;
        } else if (s.equals("")) {
            return true;
        } else {
            return false;
        }
    }
    
    public static String getinstringInts(List _l) {

        StringBuilder sb = new StringBuilder("");
        for (Object tv : _l) {
            sb.append((String) tv);
            sb.append(',');
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }
      public static String getinstringInts(String[] _l) {

        StringBuilder sb = new StringBuilder("");
        for (Object tv : _l) {
            sb.append((String) tv);
            sb.append(',');
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }
      public static String getinstringStrings(List _l) {

        StringBuilder sb = new StringBuilder("");
        for (Object tv : _l) {
            sb.append(QuotedStr((String) tv));
            sb.append(',');
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
       public static String getinstringStrings(String[] _l) {

        StringBuilder sb = new StringBuilder("");
        for (Object tv : _l) {
            sb.append(QuotedStr((String) tv));
            sb.append(',');
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }
}
