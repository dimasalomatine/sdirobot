/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapi;

import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author dmitry
 */
public class ClientRuner implements Runnable{
    private String sessionid;
    private String id;
    private List<String[]> result=new ArrayList<>(1);

    public List<String[]> getResult() {
        return result;
    }
    public ClientRuner(String sessionid, String id){
      this.sessionid=sessionid;
      this.id=id;
    }
    public static void main(String args[]){
      
    }

    @Override
    public void run() {
        result.clear();
        result=runlocal();
    }
    public List<String[]> runlocal(){
       List<String[]> lresult=new ArrayList<>(1);
       return lresult;
    }

    public String[] precondition() {
        String ret[]=new String[]{"200","ok"};
        if(id==null||id.trim().isEmpty()||sessionid==null||sessionid.trim().isEmpty()){
        ret[1]="fail precondition check on";
        ret[0]="404";
        if(id==null||id.trim().isEmpty()){
         ret[1]=" missing id";
        }
        if(sessionid==null||sessionid.trim().isEmpty()){
         ret[1]+=" missing sessionid";
        }
        }else{
          String exist=registercheck();
          if(!("ok".equals(exist)||exist.isEmpty())){
           ret[1]="fail precondition db check "+exist;
           ret[0]="403";
          }else{
            ret[1]=exist;
          }
        }
        return ret;
    }

    private String registercheck() {
        String ret="ok";
        String __q="exec sp_isregister_pc "+Common.QuotedStr(id)+","+Common.QuotedStr(sessionid);
        //todo exec must return rs will used only top 1 line and first field  as string value
        
        return ret;
    }
}
