<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

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
        <tr style="background-color: #ff0000;color:#ffffff;">
            <td>pipe1</td>
            <c:forEach begin="1" end="15">
                <td></td>
            </c:forEach>
        </tr>
        <tr style="background-color: #00ff00">
            <td>pipe2</td>
            <c:forEach begin="1" end="15">
                <td></td>
            </c:forEach>
        </tr>
        <tr style="background-color: #1200ff;color:#ffffff;">
            <td>pipe3</td>
            <c:forEach begin="1" end="15">
                <td></td>
            </c:forEach>
        </tr>
        <tr style="background-color: #00eaff">
            <td>pipe4</td>
            <c:forEach begin="1" end="15">
                <td></td>
            </c:forEach>
        </tr>
    </table>

</div>

<div style="position: absolute;top:75px;">

    <input type="button" value="管道1" id="pipe1">
    <input type="button" value="管道2" id="pipe2">
    <input type="button" value="管道3" id="pipe3">
    <input type="button" value="管道4" id="pipe4">
</div>