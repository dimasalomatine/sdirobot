<%-- 
    Document   : index
    Created on : Aug 24, 2016, 11:05:57 AM
    Author     : dmitry
--%>

<%@page import="webapi.Info"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="bootstrap-3.3.7-dist/css/bootstrap.min.css">

        <!-- jQuery (necessary for Bootstrap's JavaScript plugins)--> 
        <script src="jquery-1.12.4.min.js"></script>

        <script src="bootstrap-3.3.7-dist/js/bootstrap.min.js" type="text/javascript"></script>

        <title>JSP Page</title>
        
        <script type="text/javascript">
         var g_burl="<%= request.getRequestURL() %>";
         var cusess="<%= session.getId() %>";
         
          function f1(_ob_refelid,_ob_dfi,_ob_data){
           $("#cur"+_ob_refelid+"title").html("...");
           $("#"+_ob_refelid).html(_ob_dfi);
       }
       
       function f77(_ob_refelid,_ob_dfi,_ob_data){
           
           if( _ob_data.arr.length>0){
               var b=_ob_data.arr[0];
               if(b==='ok'){
                  $('#'+_ob_refelid).hide(); 
                  $('#logout').show();
                  $('#nav').show();
               }
          }
       }
       
       function f78(_ob_refelid,_ob_dfi,_ob_data){
           
           if( _ob_data.arr.length>0){
               var b=_ob_data.arr[0];
               if(b==='ok'){
                  $('#'+_ob_refelid).show(); 
                  $('#logout').hide();
                  $('#nav').hide();
               }
          }
       }
       
       
        
        $(document).ready(function(){
      $("#main").click(function(){
        $.post("Enter",
        {
          op:"main",
          refelid:"contentbx",
          reffunc:"f1"
        },
        function(data,status){
            var a_contentbx=jQuery.parseJSON(data);
            eval(a_contentbx.reffunc+'(a_contentbx.refelid,a_contentbx.dfi,a_contentbx.data)');
        });
    });    
    
    $("#logonsess").click(function(){
        var p=$("#psw").val();
         var u=$("#username").val();
        $.post("Enter",
        {
          op:"logon",
          refelid:"lform",
          reffunc:"f77",
          pass:p,
          user:u
        },
        function(data,status){
            var a_contentbx=jQuery.parseJSON(data);
            eval(a_contentbx.reffunc+'(a_contentbx.refelid,a_contentbx.dfi,a_contentbx.data)');
          
        });
    });
    
    $("#logout").click(function(){
        var p=$("#psw").val();
         var u=$("#username").val();
        $.post("Enter",
        {
          op:"logout",
          refelid:"lform",
          reffunc:"f78",
          pass:p,
          user:u
        },
        function(data,status){
            var a_contentbx=jQuery.parseJSON(data);
            eval(a_contentbx.reffunc+'(a_contentbx.refelid,a_contentbx.dfi,a_contentbx.data)');
          
        });
    });
            
    $("#btnsg").click(function(){
            var idv=$("#oo_id").val();
            var sessv=$("#oo_sessionid").val();
            var rtypev=$("#oo_rtype").val();
            var behv=$("#oo_behaviour").val();
            $.get("MonitorConsole?id="+idv+"&session="+sessv+"&rtype="+rtypev+"&behaviour="+behv, function(data, status){
            if(status==='success'){
             alert("Data received: " + data.responce + "\nWith status: " + status);
             eval(data.responce);
            }
        });
        
        
    });
     $("#btnsp").click(function(){
        var idv=$("#oo_id").val();
        var sessv=$("#oo_sessionid").val();
        var rtypev=$("#oo_rtype").val();
        var behv=$("#oo_behaviour").val();
            $.post("MonitorConsole",
            {
        id: idv,
        session: sessv,
        rtype:rtypev,
        behaviour:behv
    }, function(data, status){
            if(status==='success'){
             alert("Data received: " + data + "\nWith status: " + status);
             eval(data);
            }
        });
        
        
    });
});
                                
    </script>
    
    <style>
div.container {
    width: 100%;
    border: 1px solid gray;
}

header, footer {
    padding: 1em;
    color: white;
    background-color: black;
    clear: left;
    text-align: center;
}

nav {
    float: left;
    max-width: 160px;
    margin: 0;
    padding: 1em;
}

nav ul {
    list-style-type: none;
    padding: 0;
}
			
nav ul a {
    text-decoration: none;
}

article {
    margin-left: 170px;
    border-left: 1px solid gray;
    padding: 1em;
    overflow: hidden;
}
</style>

    </head>
    <body>
        <div class="container">
        <header><h1>RoboPi</h1></header>
        <nav>
            
                
                 <div id="lform">
     User name:<br>
     <input type="text" id="username" style="width:120px;"><br>
  User password:<br>
  <input type="password" id="psw" style="width:120px;">
  <input type="button" id="logonsess" value="Login"></div>   
                
                <button id="logout" style="display:none;">Logout</button></li>
        <ul id="nav" style="display:none;">
                <li><button id="main">Main</button></li>       
         </ul> 
        </nav>
        <article>
        <div id="contentbx"></div>
  
     
  
        <div class="panel panel-info">
            <div class="panel-heading">
              <h3 class="panel-title">Triger Simulation</h3>
            </div>
            <div class="panel-body">
                <form action="#" id="iframeDemoForm">
                
                <p><label for="oo_id" >CIV. ID:</label><input type="text"  id="oo_id" class="form-control" size="20" value="123456782"></p>
                <p><label for="oo_sessionid" >SESSION</label><input type="text"  id="oo_sessionid" class="form-control" size="10" value="abcdef123456"></p>
                <p><label for="oo_rtype" >RTYPE</label><input type="text"  id="oo_rtype" class="form-control" size="10" value="json"></p>
                <p><label for="oo_behaviour" >Behaviour</label><input type="text"  id="oo_behaviour" class="form-control" size="10" value="immediate" ></p>
                <p><input type="button" class="btn btn-lg btn-primary btn-block" value="Do GET" id="btnsg"></p>
                <p><input type="button" class="btn btn-lg btn-primary btn-block" value="Do POST" id="btnsp"></p>

                </form>
            </div>
          </div>
         </article>
    
        <script type="text/javascript">
   
   function getUrlVars() {
    var vars = {};
    var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi,    
    function(m,key,value) {
      vars[key] = value;
    });
    return vars;
  }

 var a=document.getElementById("oo_id");
  /*a.value=getUrlVars()['cid'];*/
  
  var b=document.getElementById("oo_sessionid");
 /* b.value=getUrlVars()['sessid'];*/

    </script>
    
    <footer>
        Pi:<%=Info.getPiInfo()%>
        Pi:<%=Info.getDBCon()%>
        Version:<%=Info.getVer()%>
        Mod:<%=Info.getMod()%>
    </footer>
    </div>
    </body>
</html>
