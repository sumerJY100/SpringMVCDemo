$(document).ready(function () {

    $("#dataLoad").hide(); //页面加载完毕后即将DIV隐藏

    //日期插件
    var currentDate = new Date();
    var beginDate = new Date(currentDate.getTime() - 24 * 60 * 60 * 1000);
    initDateTimePicker("startInputTime", beginDate);
    initDateTimePicker("endInputTime", currentDate);

    initQueryTime();


    $queryBtn = $("#queryBtn");
    var $endTimeInput = $($("[name='endInputTime']")[0]);
    var endTimeInputLeft = $endTimeInput.position().left;
    $endTimeInput.css("left", endTimeInputLeft - 200);
    console.log("endTimeInputLeft:" + endTimeInputLeft + "," + $endTimeInput.position().left);
    var queryBtnLeft = $queryBtn.position().left;
    var queryBtnMarginTop = $queryBtn.css("margin-top");
    var queryBtnMarginLeft = $queryBtn.css("margin-left");
    $("#velocityRadioLabel").css("left", queryBtnLeft - 200).css("margin-top", queryBtnMarginTop).css("margin-left", queryBtnMarginLeft);
    $("#densityRadioLabel").css("left", queryBtnLeft - 100).css("margin-top", queryBtnMarginTop).css("margin-left", queryBtnMarginLeft);


    var $queryBtnDiv = $("#queryBtnDiv");
    $queryBtnDiv.css("left", 100);

    //给所有的单选按钮点击添加处理
    // language=JQuery-CSS
    $("input[type='radio']").click(function () {
        //找出和当前name一样的单选按钮对应的label，并去除选中的样式的class
        $("input[type='radio'][name='" + $(this).attr('name') + "']").parent().removeClass("checked");
        //给自己对应的label
        $(this).parent().addClass("checked");
    });


    $("[name='densityOrVelocityRadio']").bind("change", function () {
        var densityOrVelocityValue = $(this).val();
        if (densityOrVelocityValue === "density") {
            //显示密度曲线
            changeChartDataToDensity();
        } else if (densityOrVelocityValue === "velocity") {
            //显示风速曲线
            changeChartDataToVelocity();
        }
    })

    //查询按钮绑定
    $("#queryBtn").bind("click", initHistoryData);
    $("#queryBtn").click();

    $("#densityRadio").click();
    //左右移动按钮
    $("#queryLeftBtn1Hours").bind("click", function () {
        queryDataByTimeWithHours(-1);
    })
    $("#queryLeftBtn2Hours").bind("click", function () {
        queryDataByTimeWithHours(-2);
    })
    $("#queryLeftBtn4Hours").bind("click", function () {
        queryDataByTimeWithHours(-4);
    })
    $("#queryLeftBtn6Hours").bind("click", function () {
        queryDataByTimeWithHours(-6);
    })

    $("#queryRightBtn1Hours").bind("click", function () {
        queryDataByTimeWithHours(1);
    })
    $("#queryRightBtn2Hours").bind("click", function () {
        queryDataByTimeWithHours(2);
    })
    $("#queryRightBtn4Hours").bind("click", function () {
        queryDataByTimeWithHours(4);
    })
    $("#queryRightBtn6Hours").bind("click", function () {
        queryDataByTimeWithHours(6);
    })
});

/**
 * 打开XYV的历史曲线页面
 * @param millLocation
 * @param pipeId
 */
function openPipeXYPage(millLocation,pipeId,millId){
    var url =  "../jumpOriginalXYVPage?mill="+millLocation+"&pipeId="+pipeId+"&millId="+millId;
    window.open(url);
}
var data = [],
    detailChart, //四条曲线数据
    contrastChart,//对比曲线数据
    container = document.getElementById('containerDensity'),
    detailContainer = null,
    contrastContainer = null,
    masterContainer = null,
    masterChart = null;


/*
 * 创建 detailContainer 并 append 到 container 中
 */
detailContainer = document.createElement('div');
container.appendChild(detailContainer);
/*
 * 创建 masterContainer 并 append 到 container 中
 */
masterContainer = document.createElement('div');
masterContainer.style.position = 'absolute';
masterContainer.style.top = '550px';
masterContainer.style.height = '100px';
masterContainer.style.width = '100%';
container.appendChild(masterContainer);

contrastContainer = document.createElement("div");
contrastContainer.style.position = "absolute";
contrastContainer.style.top = '0px';
contrastContainer.style.height = '200px';
contrastContainer.style.width = '100%';
container.appendChild(contrastContainer);

/**
 * 初始化时间框，结束时间默认时间为当前时间，开始时间为一小时前
 */
function initQueryTime() {
    var now = new Date();
    var endText = dateFtt("yyyy-MM-dd hh:mm", now);
    var begin = new Date(now.getTime() - 1 * 60 * 60 * 1000);
    var beginText = dateFtt("yyyy-MM-dd hh:mm", begin);
    $("#startInputTime").val(beginText);
    $("#endInputTime").val(endText);
    // alert($("#startInputTime").text());
    $("#queryBtn").click();

    // initTable();
}


function PipeHistoryData(type, name, color, data) {
    this.type = type;
    this.name = name;
    this.color = color;
    this.data = data;
    this.getData = function () {
        // for(var i=0;i<this.data.length;i++){
        //     this.data[i] = this.data[i].substr(0,5);
        //     console.log("data:"+this.data[i]);
        // }
        return this.data.concat();
    };
}

function queryDataByTimeWithHours(hours) {
    var h = parseInt(hours);
    var queryBeginTxt, queryEndTxt;
    var startTime = new Date($("#startInputTime").val());
    var endTime = new Date($("#endInputTime").val());
    // if(h > 0){

    var afterTime = new Date(endTime.getTime() + hours * 3600 * 1000);
    var beforeTime = new Date(startTime.getTime() + hours * 3600 * 1000);
    queryBeginTxt = dateFtt("yyyy-MM-dd hh:mm", beforeTime);
    queryEndTxt = dateFtt("yyyy-MM-dd hh:mm", afterTime);

    // }else if (h< 0){
    //
    //     var beforeTime = new Date(startTime.getTime() + hours*3600 * 1000);
    //     queryBeginTxt = dateFtt("yyyy-MM-dd hh:mm", beforeTime);
    //     queryEndTxt = dateFtt("yyyy-MM-dd hh:mm", endTime);
    // }
    queryDataByTime(queryBeginTxt, queryEndTxt);
}

/**
 * 根据指定时间查询曲线数据
 * @param beginTimeText
 * @param endTimeText
 */
function queryDataByTime(beginTimeText, endTimeText) {
    $("#startInputTime").val(beginTimeText);
    $("#endInputTime").val(endTimeText);
    $("#queryBtn").click();
}


/**************************************时间格式化处理************************************/
function dateFtt(fmt, date) { //author: meizz
    var o = {
        "M+": date.getMonth() + 1,                 //月份
        "d+": date.getDate(),                    //日
        "h+": date.getHours(),                   //小时
        "m+": date.getMinutes(),                 //分
        "s+": date.getSeconds(),                 //秒
        "q+": Math.floor((date.getMonth() + 3) / 3), //季度
        "S": date.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

function initDateTimePicker(inputId, initDate) {
    $("#" + inputId).datetimepicker({
        language: 'zh_CN',
        format: 'yyyy-mm-dd hh:ii',//显示格式
        //todayHighlight: 1,//今天高亮
        //minView: "month",//设置只显示到月份
        startView: 2,
        forceParse: true,
        showMeridian: 1,
        initialDate: initDate,
        autoclose: 1//选择后自动关闭
    });
}