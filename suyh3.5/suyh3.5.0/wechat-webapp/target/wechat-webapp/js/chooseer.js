$(document).ready(function() {
  var token = getcookie("sra");
    /*======================================获取快递列表====================================*/
    var tate = eval('(' + sessionStorage.getItem('tate') + ")")
    var json = {
        "region_id":tate.add_region
    };
    $("#a").click(function(){
        window.history.go(-1)
    })
    var res = getUrl("/suyh/app/3/token/findExpressCompany" , json, token);
    if(res != ""){
        var data = JSON.parse(res);
        var str = data.obj;
        for (var i = 0; i < str.length; i++) {
            html=[]
            html+= '<div class="logo">';
            html+= '<img src="'+str[i].dict_img+'"/>';
            html+= '<input class="input" type="radio" id="a'+str[i].dict_id+'" name="opt" value="'+str[i].code_name+','+str[i].dict_id+'" />';
            html+='<label for="a'+str[i].dict_id+'" class="radio"><span>'+str[i].code_name+'</span></label>'
            html+= '</div>';
            $(".site").append(html);
        }
        $("#btn").click(function(){
            var val=$('input:radio[name="opt"]:checked').val();/*选中快递*/
            var ss=val.split(",");
            var srr = new Array();
            srr.push(ss[0]);
            srr.push(ss[1]);
            var storage=window.sessionStorage;/*保存数据*/
            var cour={
                name:srr[0],
            }
            var d=JSON.stringify(cour);
            storage.setItem("cour",d);
            var exp_id=parseInt(tate.id);
            var daty={
                exp_ord_id:exp_id,
                express_id:parseInt(srr[1])
            }
            var resp = getUrl("/suyh/app/3/token/modifyExpressCompany", daty, token);
            if(resp != ""){
                var datap = JSON.parse(resp);
                var mark=datap.mark;
                if(mark==1){
                    alert(data.tip)
                    sessionStorage.removeItem("cour");
                    return false
                }else {
                    alert(data.tip)
                    window.history.go(-1)
                }
            }
        })
    }
})
