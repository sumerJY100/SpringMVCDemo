<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/main/coalMillTable.css'/>"/>

<div id="coalMillTableADiv" class="coalMillTableDivClass">
    <table cellpadding="0" cellspacing="0">
        <tr>
            <th rowspan=5 style="width:50px;font-family:'宋体',sans-serif">#10<br/>磨煤机</th>
           <th>状态</th>
            <th style="width:10px;height:30px;"></th>

            <th style="width:100px;">管道11</th>
            <th style="width:100px;">管道12</th>
            <th style="width:100px;">管道13</th>
            <th style="width:100px;">管道14</th>
        </tr>
        <tr>
            <th rowspan="2" style="width:60px;">运行中</th>
            <td>风速[绝对]</td>
            <td>0</td>
            <td>0</td>
            <td>0</td>
            <td>0</td>
        </tr>
        <tr>
            <td>风速[相对]</td>
            <td>0</td>
            <td>0</td>
            <td>0</td>
            <td>0</td>
        </tr>
        <tr>
            <td rowspan="2">0</td>
            <td>浓度[绝对]</td>
            <td>0</td>
            <td>0</td>
            <td>0</td>
            <td>0</td>
        </tr>
        <tr>
            <td>浓度[相对]</td>
            <td>0</td>
            <td>0</td>
            <td>0</td>
            <td>0</td>
        </tr>
    </table>

</div>