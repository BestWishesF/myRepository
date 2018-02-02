(function() {
  if (!exports) var exports = window;
  var BARS       = [212222,222122,222221,121223,121322,131222,122213,122312,132212,221213,221312,231212,112232,122132,122231,113222,123122,123221,223211,221132,221231,213212,223112,312131,311222,321122,321221,312212,322112,322211,212123,212321,232121,111323,131123,131321,112313,132113,132311,211313,231113,231311,112133,112331,132131,113123,113321,133121,313121,211331,231131,213113,213311,213131,311123,311321,331121,312113,312311,332111,314111,221411,431111,111224,111422,121124,121421,141122,141221,112214,112412,122114,122411,142112,142211,241211,221114,413111,241112,134111,111242,121142,121241,114212,124112,124211,411212,421112,421211,212141,214121,412121,111143,111341,131141,114113,114311,411113,411311,113141,114131,311141,411131,211412,211214,211232,23311120]
    , START_BASE = 38
    , STOP       = 106  ;
  function code128(code, barcodeType,type) {
    if (arguments.length<2)
    	 barcodeType = code128Detect(code);
    if (barcodeType=='C' && code.length%2==1) 
    	code = '0'+code; 
    var a = parseBarcode(code,  barcodeType);
    if(type==1){
      return bar2html(a.join(''),type) + '<label style="clear:both;display:block;text-align:center;font: 0.125in/100% helvetica;">' + code + '</label>';
    }else if(type==2){
      return bar2html(a.join(''),type) + '<label style="clear:both;display:block;text-align:center;font: 0.4cm/100% helvetica;">' + code + '</label>';
    }
  }

  function bar2html(s,type) {
    for(var pos=0, sb=[]; pos<s.length; pos+=2) {
      var a=0;
      var b=0;
      var c="";
      if(type==1){
        switch  (parseInt(s.charAt(pos))){
          case 1:a=1;break;
          case 2:a=2;break;
          case 3:a=3;break;
          case 4:a=4;break;
        }
        switch  (parseInt(s.charAt(pos+1))){
          case 0:b=0;break;
          case 1:b=1;break;
          case 2:b=2;break;
          case 3:b=3;break;
          case 4:b=4;break;
        }
        c="0.35in";
      }else  if(type==2){
        switch  (parseInt(s.charAt(pos))){
          case 1:a=1;break;
          case 2:a=3;break;
          case 3:a=4;break;
          case 4:a=5;break;
        }
        switch  (parseInt(s.charAt(pos+1))){
          case 0:b=0;break;
          case 1:b=1;break;
          case 2:b=3;break;
          case 3:b=4;break;
          case 4:b=5;break;
        }
        c="1.2cm";
      }
      /*sb.push('<div class="bar' + s.charAt(pos) + ' space' + s.charAt(pos+1) + '"></div>');*/
      sb.push('<div style="float:left;height: '+c+';border-left:'+a+'px solid black;margin-right:'+b+'px;"></div>');
      /*sb.push('<div style="float: left; font-size: 0px; width:0; border-left: '+s.charAt(pos)+'px solid #000000; height: 30px;"></div>' +
          '<div style="float: left; font-size: 0px; background-color: #FFFFFF; height: 30px; width: '+s.charAt(pos+1)+'px"></div>');*/
    }
    return sb.join('');
  }

  function code128Detect(code) {
    if (/^[0-9]+$/.test(code)) return 'C';
    if (/[a-z]/.test(code)) return 'B';
    return 'A';
  }

  function parseBarcode(barcode, barcodeType) {
    var bars = [];
    bars.add = function(nr) {
      var nrCode = BARS[nr];
      this.check = this.length==0 ? nr : this.check + nr*this.length;
      this.push( nrCode || ("UNDEFINED: "+nr+"->"+nrCode) );
    };
    bars.add(START_BASE + barcodeType.charCodeAt(0));
    for(var i=0; i<barcode.length; i++) {
      var code = barcodeType=='C' ? +barcode.substr(i++, 2) : barcode.charCodeAt(i);
      converted = fromType[barcodeType](code);
      if (isNaN(converted) || converted<0 || converted>106) throw new Error("Unrecognized character ("+code+") at position "+i+" in code '"+barcode+"'.");
      bars.add( converted );
    }
    bars.push(BARS[bars.check % 103], BARS[STOP]);
    return bars;
  }
  var fromType = {
    A: function(charCode) {
      if (charCode>=0 && charCode<32) return charCode+64;
      if (charCode>=32 && charCode<96) return charCode-32;
      return charCode;
    },
    B: function(charCode) {
      if (charCode>=32 && charCode<128) return charCode-32;
      return charCode;
    },
    C: function(charCode) {
      return charCode;
    }
  };
  
  //--| Export
  exports.code128 = code128;
})();

/*
	showDiv��������Ҫ��ʾ��divID��
	textVlaue �� ������Ҫ���ɵ�ֵ��
	barcodeType��������������(A��B��C)��������
	
*/
function createBarcode(showDiv,textValue,barcodeType){
	var divElement = document.getElementById(showDiv);
    	divElement.innerHTML = code128(textValue,barcodeType);
}
