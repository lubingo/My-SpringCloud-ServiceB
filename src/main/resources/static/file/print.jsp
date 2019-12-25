<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>

    <title>打印页面</title>


</head>

<body>

<script language="javascript" src="${webRoot}/plug-in/printlodop/LodopFuncs.js"></script>
<object  id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0>
    <embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed>
</object>

<a href="javascript:bai_print1()">打印预览1</a>
<a href="javascript:bai_print2()">打印预览2</a>
<a href="javascript:bai_print3()">打印预览3</a>
<a href="javascript:bai_print4()">打印预览4</a>

<div id="printcontent" style="display: none;">
    <img border="0" src="aaa-png.png">
</div>


<script language="javascript" type="text/javascript">
    var LODOP; //声明为全局变量
    function bai_print1(){
        LODOP=getLodop();
        LODOP.PRINT_INIT("打印常用证明1");
        LODOP.ADD_PRINT_HTM(0,0,"100%","100%",document.getElementById("printcontent").innerHTML);
        //LODOP.PREVIEW();
        LODOP.PRINT();
    };
    function bai_print2() {
        var LODOP=getLodop();
        LODOP.PRINT_INIT("打印常用证明2");
        var strHTML="<body style='margin:0;background-color: white'>"+document.getElementById("printcontent").innerHTML+"</body>";
        LODOP.ADD_PRINT_HTM("0mm",0,"RightMargin:0.1cm","BottomMargin:1mm",strHTML);
        LODOP.PREVIEW();
    };
    function bai_print3() {
        LODOP=getLodop();
        LODOP.PRINT_INIT("打印常用证明3");
        LODOP.ADD_PRINT_IMAGE(0,0,"100%","100%",document.getElementById("printcontent").innerHTML);
        LODOP.SET_PRINT_STYLEA(0,"Stretch",2);//按原图比例(不变形)缩放模式
        LODOP.PREVIEW();
    };
    function bai_print4() {
        LODOP=getLodop();
        LODOP.PRINT_INIT("打印常用证明3");
        LODOP.ADD_PRINT_IMAGE(0,0,"100%","100%",document.getElementById("printcontent").innerHTML);
        LODOP.SET_PRINT_STYLEA(0,"Stretch",2);//按原图比例(不变形)缩放模式
        LODOP.SET_PRINT_COPIES(5);
        //LODOP.PREVIEW();
        LODOP.PRINT();
    };
</script>

</body>
</html>