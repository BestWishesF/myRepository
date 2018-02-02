/**
 * Created by Administrator on 2017-04-01.
 */

function getHeader(token) {
    var time = Date.parse(new Date());
    var hash = hex_md5(time + "hotol");
    time = Encrypt(time);
    hash = Encrypt(hash);
    if(token == null){
        token = "";
    }
    var headers = {
        "token":token,
        "version":"7",
        "client_type":"3",
        "Timestamp":time,
        "SignInfo":hash,
        "Access-Control-Allow-Origin":"*",
    };
    return headers;
}

function getUrl(url, data, token) {
    token = "4e78623d-4610-41b1-ab6f-86632c6725d4";
    var result = "";
    var time = Date.parse(new Date());
    var hash = hex_md5(time + "hotol");
    time = Encrypt(time);
    hash = Encrypt(hash);
    if(token == null){
        token = "";
    }
    $.ajax({
        headers: {
            "token": token,
            "version": "7",
            "client_type": "3",
            "Timestamp": time,
            "SignInfo": hash,
            "Access-Control-Allow-Origin": "*",
        },
        url: url,
        type: "POST",
        data: Encrypt(JSON.stringify(data)),
        contentType: "application/json;charset=UTF-8",
        dataType: "text",
        accpet: "application/json",
        async: false,
        success: function (res) {
            result = Decrypt(res);
        },
        error: function (XMLHttpRequest, textStatus, errorThrown){
            console.log(XMLHttpRequest.status);
            console.logalert(XMLHttpRequest.readyState);
            console.logalert(textStatus);
            alert("服务器繁忙，请稍后再试.")
        }
    })
    return result;
}

function Encrypt(word){
    var key = CryptoJS.enc.Utf8.parse("testtesttesttest");

    var srcs = CryptoJS.enc.Utf8.parse(word);
    var encrypted = CryptoJS.AES.encrypt(srcs, key, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Pkcs7});
    return encrypted.toString();
}

function Decrypt(word){
    var key = CryptoJS.enc.Utf8.parse("testtesttesttest");

    var decrypt = CryptoJS.AES.decrypt(word, key, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Pkcs7});
    return CryptoJS.enc.Utf8.stringify(decrypt).toString();
}