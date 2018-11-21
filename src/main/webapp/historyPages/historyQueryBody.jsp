<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<div class="row from-inline">
    <form id="queryForm" role="form" class="form-inline"
          style="margin-top:10px;margin-bottom:10px;padding-right:500px;">

        <label for="startInputTime" style="font-size:16">开始时间&nbsp;</label><label style="font-weight: bolder">:&nbsp;</label>
        <input type="text" class="form-control" value="" id="startInputTime" name="startInputTime"
               style="font-size:16;height:40px;text-align:center;width:160px;"/>
        &nbsp;&nbsp;&nbsp;&nbsp;<label for="startInputTime" style="font-size:16">结束时间&nbsp;</label><label
            style="font-weight: bolder">:&nbsp;</label>
        <input type="text" class="form-control" id="endInputTime" name="endInputTime"
               style="font-size:16;height:40px;text-align:center;width:160px;"/>

    </form>
    <div id="queryBtnDiv" style="">
        <label for="densityRadio" class="radio_label" id="densityRadioLabel">密度
            <input type="radio" value="density" id="densityRadio" checked="true" style="display: none"
                   name="densityOrVelocityRadio"/>
        </label>

        <label for="velocityRadio" class="radio_label" id="velocityRadioLabel">风速
            <input type="radio" value="velocity" id="velocityRadio" style="display:none"
                   name="densityOrVelocityRadio"/>
        </label>

        <button class="btn btn-primary form-inline" id="queryBtn"
                style="margin-bottom:0px;margin-top:-48px;margin-left:500px;">查询
        </button>



    </div>

</div>
<div>
    <input type="button" value="左移6小时" id="queryLeftBtn6Hours"/>
    <input type="button" value="左移4小时" id="queryLeftBtn4Hours"/>
    <input type="button" value="左移2小时" id="queryLeftBtn2Hours"/>
    <input type="button" value="左移1小时" id="queryLeftBtn1Hours"/>

    <input type="button" value="右移1小时" id="queryRightBtn1Hours" style="margin-left:100px;"/>
    <input type="button" value="右移2小时" id="queryRightBtn2Hours" />
    <input type="button" value="右移4小时" id="queryRightBtn4Hours"/>
    <input type="button" value="右移6小时" id="queryRightBtn6Hours"/>



</div>
<div id="dataLoad" style="display:block;position:absolute;margin-top:-100px;margin-left:800px;"><!--页面载入显示-->
    <table width=100% height=100% border=0 align=center valign=middle style="border:0px;">
        <tr height=50%><td align=center style="border:0px;">&nbsp;</td></tr>
        <tr><td
                align=center style="border:0px;"><img src="<c:url value='/resources/image/loading.gif'/>"
                                  style="width:50px;height:50px;"/></td></tr>
        <tr><td align=center style="font-weight: bold;color:red;border:0px;">数据载入中，请稍后......</td></tr>
        <tr height=50%><td align=center style="border:0px;">&nbsp;</td></tr>
    </table>
</div>
<div id="containerDensity"
     style="min-width:400px;height:650px;position: relative;width:95%;margin-top:0px;"></div>