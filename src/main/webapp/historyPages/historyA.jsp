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
        磨煤机【10】
    </DIV>
    <jsp:include page="/historyPages/historyQueryBody.jsp"></jsp:include>
    <div style="position: absolute;top:100px;width:400px;padding-left:0px;">
        <input type="text" value="10" id="millValue" style="width:40px;padding-top:-100px;margin-top:10px;"/>
        <input type="checkbox" value="1" id="forceSetMillValue" value="force"/>
    </div>
</div>
<jsp:include page="/historyPages/historyTableBody.jsp"></jsp:include>


<jsp:include page="/historyPages/historyFoot.jsp"></jsp:include>
<script src="<c:url value='/resources/js/historyPages/historyA.js'/>"></script>


<script type="text/javascript">


</script>
</body>
</html>
