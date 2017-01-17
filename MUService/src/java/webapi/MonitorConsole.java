/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapi;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author dmitry
 */
@WebServlet(name = "MonitorConsole", urlPatterns = {"/MonitorConsole"})
public class MonitorConsole extends HttpServlet {

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
        Enumeration e = request.getParameterNames() ;
        String sessionid="";
        String id="";
        String rtype="json";
        String behaviour="immediate";
        while ( e.hasMoreElements() )
			{
				String tField = e.nextElement().toString().toLowerCase().trim();
				if (tField.equals("session"))
				{
					sessionid = request.getParameter(tField);        
				}else if (tField.equals("id"))
				{
					id = request.getParameter(tField);
				}else if (tField.equals("rtype"))
				{
					rtype = request.getParameter(tField);
				}
                                else if (tField.equals("behaviour"))
				{
					behaviour = request.getParameter(tField);
				}
		    }
        if(behaviour==null||!(behaviour.equals("immediate")||behaviour.equals("w4o"))){
          behaviour="immediate";
        }
        
        if(rtype==null||!(rtype.equals("json")||rtype.equals("html"))){
          rtype="json";
        }
        
        setContentTypeI(rtype,response);
        String respout=getResult(rtype,sessionid,id,behaviour);
        PrintWriter out=null;
        try {
            out = response.getWriter();
            out.println(respout);
        }finally{
        if(out!=null)out.flush();
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

    private void setContentTypeI(String rtype, HttpServletResponse response) {
        switch(rtype){
            case "html":
                response.setContentType("text/html;charset=UTF-8");
                break;
            default:
                response.setContentType("application/json;charset=UTF-8");
                break;

        }
    }

    private String getResult(String rtype, String sessionid, String id, String behaviour) {
        List<String[]> ret;
        String respout="";
        if(behaviour.equals("w4o"))
        {
        ret=initiateclients(false,sessionid,id);
        }else{
        ret=initiateclients(true,sessionid,id);
        }
        switch(rtype){
            case "html":
                respout="<!DOCTYPE html>";
            respout+="<html>";
            respout+="<head>";
            respout+="<title>Runlink</title>";            
            respout+="</head>";
            respout+="<body><div id=\"data\">";
            for(String r[]:ret){
                  if(r!=null&&r.length>0&&r[0].trim().length()>0){
                      respout+="<div>"+r[0]+"</div><div>"+(r.length>1&&r[1].trim().length()>0?r[1].trim():"")+"</div><br>";
                  }
                }
            respout+="</div></body>";
            respout+="</html>";
                break;
            default:
                respout="{";
                for(String r[]:ret){
                  if(r!=null&&r.length>0&&r[0].trim().length()>0){
                      respout+="\""+r[0]+"\":\""+(r.length>1&&r[1].trim().length()>0?r[1].trim():"")+"\",";
                  }
                }
                respout+="}";
                break;

        }
        return respout;
    }

    private List<String[]> initiateclients(boolean nowait, String sessionid, String id) {
        
        List<String[]> result=new ArrayList<>(1);
        ClientRuner cr=new ClientRuner(sessionid,id);
        String pccheck[]=cr.precondition();
        
        if(nowait){
        //begin thread within client a side and immediate return "process"
        Thread __th = new Thread(cr, sessionid);
        if(pccheck[0].equals("200")){
        __th.start();
        result.add(new String[]{"started","process"});
        }else{
         result.add(new String[]{"abandoned","process"});
         result.add(new String[]{"reason","precondition"});
        }
        
        }else{
         //execute client regular way and return real status 
         if(pccheck[0].equals("200")){
         result=cr.runlocal();
         result.add(new String[]{"started","immediate"});
         }else{
         result.add(new String[]{"abandoned","process"});
         result.add(new String[]{"reason","precondition"});
        }
        }
        result.add(new String[]{"reasoncode",pccheck[0]});
        result.add(new String[]{"reasondesc",pccheck[1]});
        return result;
    }
}
