<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<jsp:include page="/common/commonTopMenuJsAndMenu.jsp"></jsp:include>
<script src="<c:url value='/resources/js/jquery-1.10.2.js'/>"></script>

<script src="<c:url value='/resources/js/Highcharts/highcharts.src.js'/>"></script>
<script src="<c:url value='/resources/js/Highcharts/modules/exporting.js'/>"></script>

<%--<script src="https://img.hcharts.cn/highcharts-plugins/highcharts-zh_CN.js"></script>--%>
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