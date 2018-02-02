$(document).ready(function() {
    var token = getcookie("sra");
    /*======================================获取快递列表====================================*/
    var region = eval('(' + sessionStorage.getItem('region') + ")");
    var json = {
        "region_id":region.region,
    };
    var res = getUrl("/suyh/app/3/token/findExpressCompany", json, token);
    if(res != ""){
        var data = JSON.parse(res);
        if(data.mark==1){
            alert("该区域没有开通寄件服务")
            window.location.href = "send.html";
        }else{
            var str = data.obj;
            for (var i = 0; i < str.length; i++) {
                var html=[];
                html+= '<div class="logo">';
                html+= '<img src="'+str[i].dict_img+'"/>';
                html+= '<input class="input" type="radio" id="a'+str[i].dict_id+'" name="opt" value="'+str[i].code_name+','+str[i].dict_id+'" />';
                html+='<label for="a'+str[i].dict_id+'" class="radio">'+str[i].code_name+'</label>'
                html+= '</div>';
                $(".site").append(html);
            }
            $("#btn").click(function(){
                var val=$('input:radio[name="opt"]:checked').val();/*选中快递*/
                var ss=val.split(",");
                var storage=window.sessionStorage;/*保存数据*/
                var courier={
                    name:ss[0],
                    id:ss[1]
                }
                var d=JSON.stringify(courier);
                storage.setItem("courier",d);
                window.location.href = "send.html";
            })
        }
    }
})
