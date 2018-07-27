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
    <script src="js/jquery-1.10.2.js"></script>

    <script src="${ctx}/js/Highcharts/highcharts.src.js"></script>
    <script src="${ctx}/js/Highcharts/modules/exporting.js"></script>
    <script src="https://img.hcharts.cn/highcharts-plugins/highcharts-zh_CN.js"></script>
    <script src="${ctx}/js/Highcharts/themes/dark-unica.js"></script>

    <style type="text/css">
        .container{
            position: absolute;
            height:400px;
        }
        .containerCurve{
            width:850px;
            left:50px;
            top:100px;
        }
        .containerRelativeBar{
            width:400px;
            left: 950px;
            top:100px;
        }
        .containerAbsoluteBar{
            width:400px;
            left: 1400px;
            top:100px;
        }  .containerVCurve{
            width:850px;
            left:50px;
            top:550px;
        }
        .containerVRelativeBar{
            width:400px;
            left: 950px;
            top:550px;
        }
        .containerVAbsoluteBar{
            width:400px;
            left: 1400px;
            top:550px;
        }

    </style>
</head>
<body>

<jsp:include page="/common/commonTopMenuBody.jsp"></jsp:include>

<div id="container" class="container containerCurve " style=""></div>
<div id="container_relativeBar" class="container containerRelativeBar " style="">R</div>
<div id="container_absoluteBar" class="container containerAbsoluteBar " style="">A</div>
实时曲线
<div id="container_V" class="container containerVCurve " style=""></div>
<div id="container_V_relativeBar" class="container containerVRelativeBar " style="">R</div>
<div id="container_V_absoluteBar" class="container containerVAbsoluteBar " style="">A</div>

<script src="${ctx}/js/curveAndBar/curve.js" type="text/javascript"></script>
<script src="${ctx}/js/curveAndBar/absoluteBar.js" type="text/javascript"></script>
<script src="${ctx}/js/curveAndBar/relativeBar.js" type="text/javascript"></script>

<script src="${ctx}/js/realTimePages/main.js" type="text/javascript"></script>
<script type="text/javascript">


</script>
</body>
</html>
