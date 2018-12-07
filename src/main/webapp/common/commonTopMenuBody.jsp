<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<div id="smoothmenu1" class="ddsmoothmenu" style="z-index:9999999;width:100%;margin-top:-20px;">
    <ul>
        <li><a href="${ctx}/main.jsp">首页</a></li>
        <li><a href="${ctx}/readlTimePages/indexMillA.jsp">实时数据</a>
            <ul>
                <li><a href="${ctx}/readlTimePages/indexMillA.jsp">实时数据【#10磨】</a></li>
                <li><a href="${ctx}/readlTimePages/indexMillB.jsp">实时数据【#20磨】</a></li>
                <li><a href="${ctx}/readlTimePages/indexMillC.jsp">实时数据【#30磨】</a></li>
                <li><a href="${ctx}/readlTimePages/indexMillD.jsp">实时数据【#40磨】</a></li>
            </ul>
        </li>
        <li><a href="${ctx}/historyPages/historyA.jsp">历史曲线</a>
            <ul>
                <li><a href="${ctx}/historyPages/historyA.jsp">历史曲线【#10磨】</a></li>
                <li><a href="${ctx}/historyPages/historyB.jsp">历史曲线【#20磨】</a></li>
                <li><a href="${ctx}/historyPages/historyC.jsp">历史曲线【#30磨】</a></li>
                <li><a href="${ctx}/historyPages/historyD.jsp">历史曲线【#40磨】</a></li>

                <li><a href="${ctx}/historyPages/historyOriginalA.jsp">历史曲线原始【#10磨】</a></li>
                <li><a href="${ctx}/historyPages/historyOriginalB.jsp">历史曲线原始【#20磨】</a></li>
                <li><a href="${ctx}/historyPages/historyOriginalC.jsp">历史曲线原始【#30磨】</a></li>
                <li><a href="${ctx}/historyPages/historyOriginalD.jsp">历史曲线原始【#40磨】</a></li>
            </ul>
        </li>
        <li><a href="${ctx}/reportPages/index.jsp">历史报表</a></li>
        <li><a href="${ctx}/set/index">参数设置</a></li>
        <li><a href="${ctx}/alarmPages/index.jsp">告警信息</a></li>
        <li><a href="${ctx}/communicationPages/index.jsp">通讯状态</a></li>
        <li><a href="${ctx}/dcsConfig/dcsConfigIndex.jsp">DCS配置</a>
            <ul>
                <li><a href="${ctx}/dcsConfig/dcsConfigIndex.jsp">DCS参数配置</a></li>
                <li><a href="${ctx}/dcsConfig/getDataIndex.jsp">上传数据配置</a></li>
                <li><a href="${ctx}/dcsConfig/sendDataIndex.jsp">下载数据配置</a></li>
            </ul>
        </li>
    </ul>
    <br style="clear: left">
</div>
<img src="${ctx}/resources/image/logo.jpg" style="height:80px;right:0;top:0;position:absolute;"/>
<div style="position:absolute;top:8px;right:320px;">
    <span style="color: red; font-size:30px;font-family: '楷体',serif;font-weight: bold;">华能杨柳青电厂5#机组一次风粉在线监测</span>
</div>


<style type="text/css">

</style>