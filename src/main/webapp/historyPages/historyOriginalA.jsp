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
    <%--<title>Title</title>--%>
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
    <link rel="stylesheet" href="<c:url value='/resources/css/historyPage/main.css'/>">
    <style type="text/css">


    </style>

</head>
<body>

<jsp:include page="/common/commonTopMenuBody.jsp"></jsp:include>
<div align="center">

    <DIV class="titleDivClass">
        磨煤机【10】原始数据
    </DIV>
    <jsp:include page="/historyPages/historyQueryBody.jsp"></jsp:include>
    <%--<div style="height:50px;">&nbsp;</div>--%>
    <%--<div id="containerVelocity" style="min-width:400px;height:400px;position: relative;width:90%;"></div>--%>
</div>
<jsp:include page="/historyPages/historyTableBody.jsp"></jsp:include>




<jsp:include page="/historyPages/historyFoot.jsp"></jsp:include>
<script src="<c:url value='/resources/js/historyPages/historyOriginalA.js'/>"></script>


<script type="text/javascript">
    $(document).ready(function(){

    });

</script>
</body>
</html>
