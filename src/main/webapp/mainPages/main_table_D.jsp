<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<style type="text/css" href="<c:url value='/resources/css/main/coalMillTable.css'/>"></style>
<div id="coalMillTableDDiv" class="coalMillTableDivClass">
    <table cellpadding="0" cellspacing="0">
        <tr>
            <th rowspan=5 style="width:50px;" class="detailTableMillNameClass">#40<br/>磨煤机</th>
            <th></th>
            <th style="width:10px;height:30px;"></th>

            <th class="detailTableThClass" style="width:100px;">管道 41</th>
            <th  class="detailTableThClass" style="width:100px;">管道 42</th>
            <th class="detailTableThClass" style="width:100px;">管道 43</th>
            <th class="detailTableThClass" style="width:100px;">管道 44</th>
        </tr>
        <jsp:include page="millTable.jsp"></jsp:include>
    </table>

</div>