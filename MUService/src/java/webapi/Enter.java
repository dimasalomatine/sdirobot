/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapi;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author dmitry
 */
public class Enter extends HttpServlet {

    private static final CManagerMap _m_conn = new CManagerMap();
    private static Context _ctx;
    private static ServletContext _ctx2;
    private  ServletConfig sc = getServletConfig();
    
    
    
    /**
     * @param config
     * @throws javax.servlet.ServletException
     * @see Servlet#init(ServletConfig)
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        if(sc==null)sc=config;

        try {
            if(sc!=null)
            {
            _ctx2 = sc.getServletContext();
            _ctx = new InitialContext();
            _ctx = (Context) _ctx.lookup("java:comp/env");
            NamingEnumeration e = _ctx.listBindings("jdbc");
            CManagerMap.enumerate(_ctx, e, "jdbc");
            _ctx.close();
            }
        } catch (NamingException n) {
            System.out.println(n.getMessage());
        } 
    }

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
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
            if(sc==null)
           {
               sc = getServletConfig();
                if(sc!=null) init(sc);
           }
        

            Map<String, String> __params = new HashMap<String, String>();
            Enumeration __e = request.getParameterNames();
            while (__e.hasMoreElements()) {
                String tField = __e.nextElement().toString();
                __params.put(tField, request.getParameter(tField));
            }
            __params.put("contextpath", request.getContextPath());
            String __result=getResponce(__params);
            out.println(__result);
        } finally {  
            out.flush();
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
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
     * Handles the HTTP
     * <code>POST</code> method.
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
/**
 * 
 * @param __params
 * @return 
 */
    private String getResponce(Map<String, String> __params) {
        String ret="";
        String refelid=__params.get("refelid");
        String reffunc=__params.get("reffunc");
        String op=__params.get("op");
        String data[]=getdata(op,__params);
        String var="a_"+refelid;
        String dret=//"var "+var+"="
                 "{"
                + "\"refelid\":\""+refelid+"\","
                 + "\"reffunc\":\""+reffunc+"\","
                + "\"dfi\":\""+data[0]+"\","
                 + "\"data\":{"+data[1]+"}"
                + "}\n";
        ret+=dret;//+reffunc+"("+var+");";
        return ret;
    }

    private String[] getdata(String op, Map<String, String> __params) {
        String[] ret=new String[]{"",""};
        switch(op){
            case "main":
                ret=new String[]{"<p>This service helps to manage all needed parameters of owned MTA<br>with multidomain suport and muliple planc targethosts</p>","\"arr\":[1,2,3,4]"};
                break;
            case "users":
                ret=new String[]{"<table border =1 id='idusers'><tbody><tr><th>Id</th><th>Application</th><th>Email</th><th>Status</th><th>Op</th></tr></tbody></table>","\"arr\":[[1,2,\"user1@domain.com\",true],[2,2,\"user2@domain.com\",false],[3,2,\"user3@domain.com\",true]]"};
                break;
             case "domains":
                ret=new String[]{"<table border =1 id='iddomains'><tbody><tr><th>Gid</th><th>Domain</th><th>Transport</th><th>Status</th><th>Op</th></tr></tbody></table>","\"arr\":[[1004,\"skyzcrm.co.il\",\"virtual:\",true],[1001,\"connectall.biz\",\"virtual:\",true],[1000,\"mail.connectall.biz\",\"[smtp.hotufi.net]:25\",true],[1,\"domain.com\",\"virtual:\",true],[2,\"domain2.com\",\"local\",false],[3,\"@domain3.com\",\"virtual\",true]]"};
                break;
             case "logon":
                ret=new String[]{"","\"arr\":[\"ok\",\"sessid1\"]"};
                break;
             case "logout":
                ret=new String[]{"","\"arr\":[\"ok\",\"sessid1\"]"};
                break;
            default:
                ret=new String[]{"",""};
                break;
        }
        return ret;
    }
}
