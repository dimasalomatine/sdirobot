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

import utils.CManagerMap;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author dmitry
 */
@WebServlet(name = "InitStatic", urlPatterns = {"/InitStatic"})
public class InitStatic extends HttpServlet {
    
    private static final CManagerMap _m_conn = new CManagerMap();
    private static Context _ctx;
    private static ServletContext _ctx2;

    static List<String[]> getUsers(String _ut) {
        List<String[]> ret=new ArrayList<>(3);
        ResultSet rs=null;
        Statement stmt=null;
        try {
            Connection conn = CManagerMap.getCon(Info.dbcon);
             stmt = conn.createStatement();
             String q="";
             if("su".equalsIgnoreCase(_ut)){
             q="SELECT id,atype,name,pv FROM tbl_init";
             }else{
               q="SELECT id,atype,name,pv FROM tbl_init WHERE atype = \""+_ut+"\"";
             }
             rs = stmt.executeQuery(q);
            if(rs!=null){ 
                while(rs.next())
                {
                String id=rs.getString(1);
                String atype=rs.getString(2);
                String name=rs.getString(3);
                String pv=rs.getString(4);
                ret.add(new String[]{id,atype,name,pv});
            }
            }
        } catch (SQLException ex) {
            Logger.getLogger(InitStatic.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
           if(stmt!=null)try {
               stmt.close();
           } catch (SQLException ex) {
               Logger.getLogger(InitStatic.class.getName()).log(Level.SEVERE, null, ex);
           }
           if(rs!=null)try {
               rs.close();
           } catch (SQLException ex) {
               Logger.getLogger(InitStatic.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
        return ret;
    }
    private  ServletConfig sc = getServletConfig();

    public InitStatic(){
    super();
    //do
    
    }
    private void initmy(){
    
    String __initpi4j = getServletConfig().getInitParameter("initpi4j");
    String __testenv = getServletConfig().getInitParameter("env");
    Info.dbtype = getServletConfig().getInitParameter("dbtype");
    
    Info.dbcon=(!__testenv.isEmpty()?__testenv+"_":"")+"rpirobotapp"+"_"+Info.dbtype;
    
    if(Boolean.parseBoolean(__initpi4j)){
     StaticHolder.getInstance();
    }

        try {
            if(sc==null){
            sc = getServletConfig();
            }
            if(sc!=null)
            {
            _ctx2 = sc.getServletContext();
            _ctx = new InitialContext();
            _ctx = (Context) _ctx.lookup("java:comp/env");
            NamingEnumeration e = _ctx.listBindings("jdbc");
            CManagerMap.enumerate(_ctx, e, "jdbc",Info.dbtype);
            _ctx.close();
            }
        } catch (NamingException n) {
            System.out.println(n.getMessage());
        }
        
        Connection conn = CManagerMap.getCon(Info.dbcon);
        if("sqlite".equals(Info.dbtype)){
         loaddb(conn);
        }
        
        
    
    }
    @Override
    public void init() throws ServletException {   
    super.init();
    //do
    initmy();
    
    
    }
   /* @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        if(sc==null)sc=config;
        initmy();
    }*/
    
    /*@Override
    public void destroy(){
        //do
    Connection conn = CManagerMap.getCon(Info.dbcon);
        if("sqlite".equals(Info.dbtype)){
         savedb(conn);
        }
    super.destroy();
    }*/
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet InitStatic</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet InitStatic at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private boolean loaddb(Connection conn) {
        boolean ret=false;
        ResultSet rs=null;
        Statement stmt=null;
        String path=System.getProperty("user.dir")+File.separator+"rpirobotapp.db";
        
        try {
            stmt = conn.createStatement();
            File fp=new File(path);
            if(fp.exists()){
            stmt.execute("restore from "+path);
            }else{
              stmt.execute("CREATE TABLE tbl_init(id INTEGER, atype VARCHAR(20), name VARCHAR(20), pv VARCHAR(60))");
              stmt.executeUpdate("insert into tbl_init values(1,\"su\", \"master\", \"access\")");
              stmt.executeUpdate("insert into tbl_init values(2, \"api\", \"user1\", \"access2\")");
              stmt.executeUpdate("insert into tbl_init values(3, \"weak\", \"qrcode1\", \"accesstoken1234567\")");
              stmt.execute("CREATE TABLE tbl_hosts(id INTEGER,ipname VARCHAR(20), name VARCHAR(20), pv VARCHAR(60))");
              stmt.executeUpdate("insert into tbl_hosts values(1, \"192.168.9.86\",\"userh2\", \"access2\")");
              stmt.executeUpdate("backup to "+path);
            }
        } catch (SQLException ex) {
            Logger.getLogger(InitStatic.class.getName()).log(Level.SEVERE, null, ex);
                
        }
        try {
            stmt = conn.createStatement();
             rs = stmt.executeQuery("SELECT id FROM tbl_init WHERE name = \"master\"");
            if(rs!=null &&rs.next()){
            ret=true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(InitStatic.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
           if(stmt!=null)try {
               stmt.close();
           } catch (SQLException ex) {
               Logger.getLogger(InitStatic.class.getName()).log(Level.SEVERE, null, ex);
           }
           if(rs!=null)try {
               rs.close();
           } catch (SQLException ex) {
               Logger.getLogger(InitStatic.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
        return ret;
    }

    private void savedb(Connection conn) {
        String path=System.getProperty("user.dir")+File.separator+"rpirobotapp.db";
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("backup to "+path);
        } catch (SQLException ex) {
            Logger.getLogger(InitStatic.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public static String getloginright(String _u,String _p) {
        String ret="none";
        ResultSet rs=null;
        Statement stmt=null;
        try {
            Connection conn = CManagerMap.getCon(Info.dbcon);
             stmt = conn.createStatement();
             rs = stmt.executeQuery("SELECT atype FROM tbl_init WHERE name = \""+_u+"\" and pv= \""+_p+"\"");
            if(rs!=null &&rs.next()){
                ret=rs.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(InitStatic.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
           if(stmt!=null)try {
               stmt.close();
           } catch (SQLException ex) {
               Logger.getLogger(InitStatic.class.getName()).log(Level.SEVERE, null, ex);
           }
           if(rs!=null)try {
               rs.close();
           } catch (SQLException ex) {
               Logger.getLogger(InitStatic.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
        return ret;
        
    }

}
