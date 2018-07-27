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
    <script src="${ctx}/js/Highcharts/highcharts.src.js"></script>
    <script src="${ctx}/js/Highcharts/modules/exporting.js"></script>
    <script src="https://img.hcharts.cn/highcharts-plugins/highcharts-zh_CN.js"></script>
    <script src="${ctx}/js/Highcharts/themes/dark-unica.js"></script>

    <jsp:include page="/common/commonTopMenuJsAndMenu.jsp"></jsp:include>
</head>
<body>
<jsp:include page="/common/commonTopMenuBody.jsp"></jsp:include>

<div id="container" ></div>
<script src="${ctx}/js/curveAndBar/curve_column.js" type="text/javascript"></script>
<script src="${ctx}/js/curveAndBar/absoluteBar.js" type="text/javascript"></script>
<script src="${ctx}/js/curveAndBar/relativeBar.js" type="text/javascript"></script>
告警页面
<script type="text/javascript">

    $(document).ready(function () {
       var curveChart = getCurveChart("container", "密度", "");
    })
</script>
</body>
</html>
