<%-- 
    Document   : index
    Created on : Aug 24, 2016, 11:05:57 AM
    Author     : dmitry
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="bootstrap-3.3.7-dist/css/bootstrap.css">

        <!-- jQuery (necessary for Bootstrap's JavaScript plugins)--> 
        <script src="jquery-1.12.4.min.js"></script>

        <script src="bootstrap-3.3.7-dist/js/bootstrap.min.js" type="text/javascript"></script>

        <title>JSP Page</title>
        
        <script type="text/javascript">
        $(document).ready(function(){
    $("#btnsg").click(function(){
            var idv=$("#oo_id").val();
            var sessv=$("#oo_sessionid").val();
            var rtypev=$("#oo_rtype").val();
            var behv=$("#oo_behaviour").val();
            $.get("Runlink?id="+idv+"&session="+sessv+"&rtype="+rtypev+"&behaviour="+behv, function(data, status){
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
            $.post("Runlink",
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
    </head>
    <body>
        <h1>RoboPi</h1>
        <div class="panel panel-info">
            <div class="panel-heading">
              <h3 class="panel-title">סימולציה לטריגר</h3>
            </div>
            <div class="panel-body">
                <form action="#" id="iframeDemoForm">
                
                <p><label for="oo_id" >ת.ז:</label><input type="text"  id="oo_id" class="form-control" size="20" value="123456782"></p>
                <p><label for="oo_sessionid" >ססיה:</label><input type="text"  id="oo_sessionid" class="form-control" size="10" value="abcdef123456"></p>
                <p><label for="oo_rtype" >ססיה:</label><input type="text"  id="oo_rtype" class="form-control" size="10" value="json"></p>
                <p><label for="oo_behaviour" >התנהגות</label><input type="text"  id="oo_behaviour" class="form-control" size="10" value="immediate" ></p>
                <p><input type="button" class="btn btn-lg btn-primary btn-block" value="סימולציה GET" id="btnsg"></p>
                <p><input type="button" class="btn btn-lg btn-primary btn-block" value="סימולציהPOST" id="btnsp"></p>

                </form>
            </div>
          </div>
    
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
    
    </body>
</html>
