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
    <jsp:include page="/historyPages/historyHead.jsp"></jsp:include>

</head>
<body>

<jsp:include page="/common/commonTopMenuBody.jsp"></jsp:include>
<div align="center">
    <DIV class="titleDivClass">
        磨煤机【${millId}】管道${pipeId}
        <input type="hidden" value="${millLocation}" id="millLocation">
        <input type="hidden" value="${pipeId}" id="pipeId">
    </DIV>
    <jsp:include page="/historyPages/historyQueryBody.jsp"></jsp:include>
</div>
<%--<jsp:include page="/historyPages/historyTableBody.jsp"></jsp:include>--%>

<div style="text-align: center;margin-top:10px;">
    <table id="dataTable" style="width:95%;margin:auto;border:1px solid black;">

        <tr>
            <th>名称</th>
            <th>左侧日期</th>
            <th>左侧时间</th>
            <th>值</th>
            <%--<th>单位</th>--%>
            <th>右侧日期</th>
            <th>右侧时间</th>
            <th>值</th>
            <%--<th>单位</th>--%>
            <th>最大值日期</th>
            <th>最大值时间</th>
            <th>最大值</th>
            <th>最小值日期</th>
            <th>最小值时间</th>
            <th>最小值</th>
            <th>平均值</th>
            <th>偏差值</th>
            <th>偏差百分比</th>
        </tr>
        <tr>
            <td>磨煤量</td>
            <c:forEach begin="1" end="15">
                <td></td>
            </c:forEach>
        </tr>
        <%--       var pipe1Color = "#ff0000",
               pipe2Color = "#00ff00",
               pipe3Color = "#1200ff",
               pipe4Color = "#00eaff";--%>
        <tr id="xTr" style="color:#ffffff;">
            <td>x</td>
            <c:forEach begin="1" end="15">
                <td></td>
            </c:forEach>
        </tr>
        <tr id="yTr" style="">
            <td>y</td>
            <c:forEach begin="1" end="15">
                <td></td>
            </c:forEach>
        </tr>
        <tr id="vTr" style="color:#ffffff;">
            <td>v</td>
            <c:forEach begin="1" end="15">
                <td></td>
            </c:forEach>
        </tr>

    </table>

</div>

<%--<script src="${ctx}/js/historyPages/indexForVelocityForReal.js"></script>--%>

<script src="<c:url value='/resources/js/historyPages/chartConstraint.js'/>"></script>
<script src="<c:url value='/resources/js/historyPages/chartDetial.js'/>"></script>
<script src="<c:url value='/resources/js/historyPages/chartMaster.js'/>"></script>



<%--<script src="${ctx}/js/historyPages/indexForVelocityForReal.js"></script>--%>
<%--<script src="${ctx}/js/historyPages/indexForDensity.js"></script>--%>
<%--<script src="<c:url value='/resources/js/historyPages/indexForDensity.js'/>"></script>--%>

<%--<script src="<c:url value='/resources/js/historyPages/historyCurve.js'/>"></script>--%>
<script src="<c:url value='/resources/js/historyPages/main.js'/>"></script>


<script src="<c:url value='/resources/js/historyPages/historyXY.js'/>"></script>


<script type="text/javascript">
    $(document).ready(function(){

    });

</script>
</body>
</html>
