<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>



<script src="<c:url value='/resources/js/historyPages/chartConstraint.js'/>"></script>
<script src="<c:url value='/resources/js/historyPages/chartDetial.js'/>"></script>
<script src="<c:url value='/resources/js/historyPages/chartMaster.js'/>"></script>



<%--<script src="${ctx}/js/historyPages/indexForVelocityForReal.js"></script>--%>
<script src="${ctx}/js/historyPages/indexForDensity.js"></script>

<script src="<c:url value='/resources/js/historyPages/historyCurve.js'/>"></script>
<script src="<c:url value='/resources/js/historyPages/main.js'/>"></script>