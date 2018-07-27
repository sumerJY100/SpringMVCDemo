<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<div id="smoothmenu1" class="ddsmoothmenu" style="z-index:9999999">
    <ul>
        <li><a href="${ctx}/main.jsp">首页</a></li>
        <li><a href="${ctx}/readlTimePages/index.jsp">实时数据</a>
            <ul>
                <li><a href="${ctx}/readlTimePages/index.jsp">实时数据【A磨】</a></li>
                <li><a href="${ctx}/readlTimePages/index.jsp">实时数据【B磨】</a></li>
                <li><a href="${ctx}/readlTimePages/index.jsp">实时数据【C磨】</a></li>
                <li><a href="${ctx}/readlTimePages/index.jsp">实时数据【D磨】</a></li>
            </ul>
        </li>
        <li><a href="${ctx}/historyPages/index.jsp">历史曲线</a>
            <ul>
                <li><a href="${ctx}/historyPages/index.jsp">历史曲线【A磨】</a></li>
                <li><a href="${ctx}/historyPages/index.jsp">历史曲线【B磨】</a></li>
                <li><a href="${ctx}/historyPages/index.jsp">历史曲线【C磨】</a></li>
                <li><a href="${ctx}/historyPages/index.jsp">历史曲线【D磨】</a></li>
            </ul>
        </li>
        <li><a href="${ctx}/reportPages/index.jsp">历史报表</a></li>
        <li><a href="${ctx}/set/index">参数设置</a></li>
        <li><a href="${ctx}/alarmPages/index.jsp">告警信息</a></li>
        <li><a href="${ctx}/communicationPages/index.jsp">通讯状态</a></li>

    </ul>
    <br style="clear: left"/>
</div>