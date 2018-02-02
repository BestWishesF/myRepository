<%--
  Created by IntelliJ IDEA.
  User: lizhun
  Date: 15/12/9
  Time: 下午2:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title></title>
  <script type="text/javascript" src="resource/jquery-1.9.1.min.js"></script>
  <script type="text/javascript" src="resource/jquery.form.js"></script>
  <script type="text/javascript" src="resource/md5.js"></script>
</head>
<body>
<button class="btn btn-lg btn-confirm" id="submit_bt">登录</button>
</br>

</body>
<script type="text/javascript">
 $(document).ready(function(){
   function GetJsonData() {
     var json = {
       "memb_phone": "13369698569",
       "memb_password": "5585896",
       "push_token": "555656"
     };
     return json;
   }
    $("#submit_bt").click(function(){
      var data = "memb_phone="+"13369698569"+"&memb_password="+"5585896"+"&push_token="+"555656";
      var time = Date.parse(new Date());
      var hash = hex_md5(time +"hotol");

      $.ajax({
        contentType: "application/json; charset=utf-8",
        headers: {
          "token":"79c8b3b2-fe23-402e-8f6b-8701a582c62b",
          "version":"3",
          "client_type":"1",
          "Timestamp":time,
          "SignInfo":hash,
           "Access-Control-Allow-Origin":"*",
         },
        type:'post',
        url:'http://localhost:8080/suyh/app/3/login',
        dataType : 'json',
        data: JSON.stringify(GetJsonData()),
        accpet:"application/json",

        success:function(data){
          alert(data.mark);
          if(data.mark=="0"){
            alert(data.tip);
          }else{
            alert(data.tip);
          }
        }

      });
    });
  });
</script>
</html>
