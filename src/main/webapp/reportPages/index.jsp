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
    <style type="text/css">

        .radio_label{
            position: absolute;
            /*设置背景*/
            background: url(${ctx}/image/radio02.png) repeat;
            /*设置背景大小：	 (这里我的背景图是两个图标上下接在一起的，故高度为背景的两倍)*/
            background-size：20px 40px;
            width:150px;
            height:100px;
            /*background-size：contain;*/
            /*width: 20px;*/
            /*height:20px;*/
            /*设置显示方式：	（设置label为行内块元素，让它能有宽高，而且不换行）*/
            display: inline-block;
            /*设置高度和行高：*/
            /*height: 20px;*/
            /*line-height: 20px;*/
            /*设置文字靠右一点：*/
            text-indent: 50px;
        }
    </style>
</head>
<body>

<jsp:include page="/common/commonTopMenuBody.jsp"></jsp:include>
<label class="radio_label">报表</label><input type="radio"/></body>
</body>
</html>
