<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2018/6/27
  Time: 13:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Title</title>
    <jsp:include page="/common/commonTopMenuJsAndMenu.jsp"></jsp:include>
    <script src="<c:url value='/resources/js/jquery-1.10.2.js'/>"></script>

    <script src="<c:url value='/resources/js/Highcharts/highcharts.src.js'/>"></script>
    <script src="<c:url value='/resources/js/Highcharts/modules/exporting.js'/>"></script>
    <script src="https://img.hcharts.cn/highcharts-plugins/highcharts-zh_CN.js"></script>
    <script src="<c:url value='/resources/js/Highcharts/themes/dark-unica.js'/>"></script>


    <link rel="stylesheet" href="<c:url value='/resources/js/BootstrapV2/css/bootstrap.css'/>">
    <link rel="stylesheet" href="<c:url value='/resources/js/bootstrap-datetimepicker/css/datetimepicker.css'/>">
    <link rel="stylesheet" href="<c:url value='/resources/css/radioAndCheckBox.css'/>">

    <script src="<c:url value='/resources/js/BootstrapV2/js/bootstrap.js'/>"></script>
    <script src="<c:url value='/resources/js/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js'/>"></script>
    <script src="<c:url value='/resources/js/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh_CN.js'/>"></script>
    <style type="text/css" >
        th{
            border:1px solid black;
        }
        td{
            border:1px solid black;
            text-align:center;
        }

    </style>

</head>
<body>

<jsp:include page="/common/commonTopMenuBody.jsp"></jsp:include>
<div align="center">

    <div class="row from-inline">
        <form id="queryForm" role="form" class="form-inline" style="margin-top:10px;margin-bottom:10px;">

            <label for="startInputTime">开始时间</label>
            <input type="text" class="form-control" value="" size="16" id="startInputTime" name="startInputTime"/>
            <label for="endInputTime">结束时间</label>
            <input type="text" class="form-control" id="endInputTime" name="endInputTime"/>

        </form>
        <div>


            <label for="densityRadio" class="radio_label" id="densityRadioLabel">密度<input type="radio" value="density" id="densityRadio" checked="true" style="display: none" name="densityOrVelocityRadio"/></label>

            <label for="velocityRadio" class="radio_label" id="velocityRadioLabel">风速
                <input type="radio" value="velocity" id="velocityRadio" style="display:none"
                       name="densityOrVelocityRadio"/>
            </label>

            <button class="btn btn-primary form-inline" id="queryBtn" style="margin-bottom:10px;">查询</button>
        </div>

    </div>
    <div id="containerDensity"
         style="min-width:400px;height:500px;position: relative;width:90%;margin-top:0px;"></div>
    <%--<div style="height:50px;">&nbsp;</div>--%>
    <%--<div id="containerVelocity" style="min-width:400px;height:400px;position: relative;width:90%;"></div>--%>
</div>
<div style="text-align: center;margin-top:10px;">
    <table id="dataTable" style="width:92%;margin:auto;border:1px solid black;">

        <tr>
            <th>名称</th>
            <th>左侧时间</th>
            <th>值</th>
            <th>单位</th>
            <th>右侧时间</th>
            <th>值</th>
            <th>单位</th>
            <th>最大值时间</th>
            <th>最大值</th>
            <th>最小值时间</th>
            <th>最小值</th>
            <th>平均值</th>
        </tr>
        <tr>
            <td>磨煤机</td>
            <td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
        </tr>
        <tr>
            <td>pipe1</td>
            <td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
        </tr>
        <tr>
            <td>pipe2</td>
            <td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
        </tr>
        <tr>
            <td>pipe3</td>
            <td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
        </tr>
        <tr>
            <td>pipe4</td>
            <td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
        </tr>
    </table>

</div>













<script src="<c:url value='/resources/js/historyPages/indexForDensityContrast.js'/>"></script>
<script src="<c:url value='/resources/js/historyPages/indexForDensityDetail.js'/>"></script>
<%--<script src="${ctx}/js/historyPages/indexForVelocityForReal.js"></script>--%>
<script src="${ctx}/js/historyPages/indexForDensity.js"></script>

<script src="<c:url value='/resources/js/historyPages/historyCurve.js'/>"></script>


<script type="text/javascript">

    $(document).ready(function () {
        //日期插件
        var currentDate = new Date();
        var beginDate = new Date(currentDate.getTime() - 24 * 60 * 60 * 1000);
        initDateTimePicker("startInputTime", beginDate);
        initDateTimePicker("endInputTime", currentDate);

        initQueryTime();


        $queryBtn = $("#queryBtn");
        var queryBtnLeft = $queryBtn.position().left;
        $("#velocityRadioLabel").css("left",queryBtnLeft - 200);
        $("#densityRadioLabel").css("left",queryBtnLeft - 100);


        //给所有的单选按钮点击添加处理
        // language=JQuery-CSS
        $("input[type='radio']").click(function () {
            //找出和当前name一样的单选按钮对应的label，并去除选中的样式的class
            $("input[type='radio'][name='" + $(this).attr('name') + "']").parent().removeClass("checked");
            //给自己对应的label
            $(this).parent().addClass("checked");
        });
        $("#densityRadio").click();

        $("[name='densityOrVelocityRadio']").bind("change",function(){
            var densityOrVelocityValue = $(this).val();
            if(densityOrVelocityValue === "density"){
                //显示密度曲线
                changeChartDataToDensity();
            }else if(densityOrVelocityValue === "velocity"){
                //显示风速曲线
                changeChartDataToVelocity();
            }
        })
    });

    /**
     * 初始化时间框，结束时间默认时间为当前时间，开始时间为一小时前
     */
    function initQueryTime(){
        var now = new Date();
        var endText = dateFtt("yyyy-MM-dd hh:mm",now);
        var begin = new Date(now.getTime() - 1* 60 * 60 * 1000);
        var beginText = dateFtt("yyyy-MM-dd hh:mm",begin);
        $("#startInputTime").val(beginText);
        $("#endInputTime").val(endText);
        // alert($("#startInputTime").text());
        $("#queryBtn").click();

        // initTable();
    }
    /**************************************时间格式化处理************************************/
    function dateFtt(fmt,date)
    { //author: meizz
        var o = {
            "M+" : date.getMonth()+1,                 //月份
            "d+" : date.getDate(),                    //日
            "h+" : date.getHours(),                   //小时
            "m+" : date.getMinutes(),                 //分
            "s+" : date.getSeconds(),                 //秒
            "q+" : Math.floor((date.getMonth()+3)/3), //季度
            "S"  : date.getMilliseconds()             //毫秒
        };
        if(/(y+)/.test(fmt))
            fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));
        for(var k in o)
            if(new RegExp("("+ k +")").test(fmt))
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        return fmt;
    }
    function initDateTimePicker(inputId, initDate) {
        $("#" + inputId).datetimepicker({
            language: 'zh_CN',
            format: 'yyyy-mm-dd hh:ii',//显示格式
            //todayHighlight: 1,//今天高亮
            //minView: "month",//设置只显示到月份
            startView: 2,
            forceParse: true,
            showMeridian: 1,
            initialDate: initDate,
            autoclose: 1//选择后自动关闭
        });
    }
</script>
</body>
</html>
