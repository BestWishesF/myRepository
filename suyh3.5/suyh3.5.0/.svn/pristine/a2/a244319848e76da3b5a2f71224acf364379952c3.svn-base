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
        "client_type":"4",
        "Timestamp":time,
        "SignInfo":hash,
        "Access-Control-Allow-Origin":"*",
    };
    return headers;
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