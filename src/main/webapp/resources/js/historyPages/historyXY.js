$(document).ready(function(){
    $("#xTr").css("backgroundColor",pipe1Color);
    $("#yTr").css("backgroundColor",pipe2Color);
    $("#vTr").css("backgroundColor",pipe3Color);
});

function getQueryUrl(millLocation,pipeId) {
    var millLocation = $("#millLocation").val();
    var pipeId = $("#pipeId").val();
    return "getMillHistoryXYDataWithMillAndPipeId?mill="+millLocation+"&pipeId="+pipeId;
}

// JS 代码
Highcharts.setOptions({
    global: {
        useUTC: false,
        animation: false,
        enableMouseTracking: false,

    }
});
var millLocation = "A";
var targetPipeId = 11 ;
var xDatas, yDatas, vDatas, millDatas;

/**
 * 通过访问后台获取数据
 */
function initHistoryData() {
    // var url = "../getAMillHistoryData";
    millLocation = $("#millLocation").val();
    targetPipeId = $("#pipeId").val();
    var url = getQueryUrl(millLocation,targetPipeId);
    var queryData = $("#queryForm").serialize();
    chartName = "密度历史曲线";
    $("#dataLoad").show(); //页面加载完毕后即将DIV隐藏
    $.getJSON(url, queryData, function (result) {
        var densityType = "density",
            velocityType = "velocity";


        xDatas = new PipeHistoryData(densityType, "x", pipe1Color, result.xDatas);
        yDatas = new PipeHistoryData(densityType, "y", pipe2Color, result.yDatas);
        vDatas = new PipeHistoryData(velocityType, "v", pipe3Color, result.vDatas);
        millDatas = new PipeHistoryData(velocityType, "mill", millColor, result.coalMillDatas);


        //创建masterChart
        queryStartTime = result.startTime;
        queryEndTime = result.endTime;
        // masterChart = createMaster(result.startTime, result.endTime, [pipe1ForDensity, pipe2ForDensity, pipe3ForDensity, pipe4ForDensity,millDatas],chartName);


        var seriesObjArr = [];
        var xyDatasCopy = $.extend(true, {}, [xDatas, yDatas, millDatas]);

        var t001 = new Date().getTime();
        for (var x in xyDatasCopy) {
            // if(x>=4){
            //     break;
            // }
            var p = xyDatasCopy[x];
            var targetData = [];
            var sourceData = p.data;
            for (var m in sourceData) {
                targetData.push(sourceData[m]);
            }
            var color = p.color;
            var series = generatorMasterSeries(queryStartTime, targetData, p.name, color);
            if (x >= 2) {
                series.visible = false;
            }
            seriesObjArr.push(series);
        }


        var t002 = new Date().getTime();
        console.log("数据处理的时间：" + (t002 - t001));

        var chart = Highcharts.chart(masterContainer, {
            chart: generatorMasterChartOption(queryStartTime, queryEndTime),
            title: {text: null},
            xAxis: createMasterChartXAxis(queryStartTime, queryEndTime),
            yAxis: masterChartYAxis,
            tooltip: {
                formatter: function () {
                    return false;
                }
            },
            legend: masterChartLegend,
            credits: {enabled: false},
            plotOptions: masterChartPlotOptions,
            series: seriesObjArr,
            exporting: {enabled: false}
        }, function (masterChart) {
            var t01 = new Date().getTime();
            createDetailWithSerialData(masterChart, queryStartTime, queryEndTime, [xDatas, yDatas], chartName);
            var t02 = new Date().getTime();
            console.log("创建detailChart的时间:" + (t02 - t01));
            createContrastChartWithSerialData(masterChart, queryStartTime, queryEndTime, [millDatas], chartName);
            var t03 = new Date().getTime();
            console.log("创建磨煤机磨煤量图表的时间:" + (t03 - t02));
        });
        var t003 = new Date().getTime();
        console.log("创建整个chart的时间：" + (t003 - t002));


        initTable();

        $("#dataLoad").hide(); //页面加载完毕后即将DIV隐藏
    });
}


function initTable() {
    var detailXAxis = detailChart.xAxis[0];
    var seriesArr = detailChart.series;
    // var detailXAxis = masterChart.xAxis[0];
    // var seriesArr = masterChart.series;
    var xMax = detailXAxis.dataMax;
    var xMin = detailXAxis.dataMin;
    var millSeriesArr = contrastChart.series;

    var allArr = new Array();
    for (var x in seriesArr) {
        allArr.push(seriesArr[x]);
    }
    allArr.push(millSeriesArr[0]);

    for (var x in allArr) {
        var series = allArr[x];
        var currentSeriesName = series.name;

        var data, left, right, totalCount, avg, totalValues, max, maxValue, min, minValue = 0, leftValue, rightValue;
        data = series.data;
        if(data.length > 0 ) {

            maxValue = data[0].y;
            minValue = data[0].y;
            max = data[0].x;
            min = data[0].x;

            left = xMin;
            right = xMax;
            totalCount = 0;
            avg = 0;
            totalValues = 0;
            leftValue = data[0].y;
            rightValue = data[data.length - 1].y;


            Highcharts.each(data, function (d) {

                totalCount += 1;
                totalValues += d.y;
                if (d.y > maxValue) {
                    max = d.x;
                    maxValue = d.y;
                }
                if (d.y < minValue) {
                    min = d.x;
                    minValue = d.y;
                }
            });
            avg = totalValues / totalCount;
            initTableRow(currentSeriesName, left, leftValue, right, rightValue, max, maxValue, min, minValue, avg);
        }
    }
    //进行偏差数据分析
    var densityRadioValue = $("#densityRadioLabel").hasClass("checked")

    if(densityRadioValue) {
        var $table = $("#dataTable");
        var reg1 = new RegExp(" ", "g");
        var reg2 = new RegExp(",", "g");
        var XDataHTML = $table.find("tr:eq(2)").find("td:eq(13)").html();
        var YDataHTML = $table.find("tr:eq(3)").find("td:eq(13)").html();


        var xData, yData;
        var xyAvg, xDif, yDif;


        xData = XDataHTML.replace(reg1, "").replace(reg2, "");
        yData = YDataHTML.replace(reg1, "").replace(reg2, "");
        var total = parseFloat(xData) + parseFloat(yData);
        xyAvg = total / 2;
        xDif = parseFloat(xData) - xyAvg;
        yDif = parseFloat(yData) - xyAvg;


        $table.find("tr:eq(2)").find("td:eq(14)").html(xDif.toFixed(2));
        $table.find("tr:eq(3)").find("td:eq(14)").html(yDif.toFixed(2));

        if(xyAvg === 0){
            $table.find("tr:eq(2)").find("td:eq(15)").html("0" + "%");
            $table.find("tr:eq(3)").find("td:eq(15)").html("0" + "%");
        }else {
            $table.find("tr:eq(2)").find("td:eq(15)").html((xDif / xyAvg * 100).toFixed(2) + "%");
            $table.find("tr:eq(3)").find("td:eq(15)").html((yDif / xyAvg * 100).toFixed(2) + "%");
        }
    }
}

function clearRow(row) {
    var $table = $("#dataTable");
    $table.find("tr:gt(" + row + ")").each(function () {
        for (var i = 1; i < 17; i++) {
            $(this).find("td:eq(" + i + ")").html("");
        }
    })


}

function initTableRow(currentSeriesName, left, leftValue, right, rightValue, xMax, maxValue, xMin, minValue, avg) {
    var $table = $("#dataTable");
    $table.find("tr:gt(0)").each(function () {
        //console.log("currentSeriesName:" + currentSeriesName)
        var firstTdHtml = $(this).find("td:eq(0)");
        if (firstTdHtml[0].innerHTML === currentSeriesName) {
            if (currentSeriesName === "y" || currentSeriesName === "v") {
                $(this).find("td:eq(1)").html(dateFtt("yyyy-MM-dd ", new Date(left)));
                $(this).find("td:eq(2)").html(dateFtt(" hh:mm:ss", new Date(left)));
                $(this).find("td:eq(4)").html(dateFtt("yyyy-MM-dd ", new Date(right)));
                $(this).find("td:eq(5)").html(dateFtt("hh:mm:ss", new Date(right)));

                $(this).find("td:eq(11)").css("border-bottom", "0px");
            }
            $(this).find("td:eq(1)").css("border-bottom", "0px").css("border-top", "0px");
            $(this).find("td:eq(2)").css("border-bottom", "0px").css("border-top", "0px");
            $(this).find("td:eq(4)").css("border-bottom", "0px").css("border-top", "0px");
            $(this).find("td:eq(5)").css("border-bottom", "0px").css("border-top", "0px");


            $(this).find("td:eq(7)").html(dateFtt("yyyy-MM-dd ", new Date(xMax)));
            $(this).find("td:eq(8)").html(dateFtt("hh:mm:ss", new Date(xMax)));
            $(this).find("td:eq(10)").html(dateFtt("yyyy-MM-dd ", new Date(xMin)));
            $(this).find("td:eq(11)").html(dateFtt(" hh:mm:ss", new Date(xMin)));

            $(this).find("td:eq(3)").html(leftValue);

            $(this).find("td:eq(6)").html(rightValue);

            $(this).find("td:eq(9)").html(maxValue);

            $(this).find("td:eq(12)").html(minValue);
            $(this).find("td:eq(13)").html(Highcharts.numberFormat(avg, 2));


        }
    });
}


/**
 * 改变chart的数据为风速的数据
 */
function changeChartDataToVelocity() {
    chartName = "风速历史曲线";
    console.log("change to Velocity");
    var dataArr = [vDatas, millDatas];

    // var pipeHistoryDataArrCopy = $.extend(true,{},dataArr);
    // pipeHistoryDataArrCopy[1] = millDatas;

    createMaster(queryStartTime, queryEndTime, dataArr, chartName);
    clearRow(1);
    clearRow(2);

    initTable();
}

/**
 * 改变chart的数据为浓度的数据
 */
function changeChartDataToDensity() {
    console.log("change to Density");
    // masterChart.setTitle({text:"密度历史曲线"});
    detailChart.setTitle({text: "密度历史曲线"});
    contrastChart.setTitle({text: "密度历史曲线"});
    var dataArr = [xDatas, yDatas, millDatas];

    // var pipeHistoryDataArrCopy = $.extend(true,{},dataArr);

    createMaster(queryStartTime, queryEndTime, dataArr, chartName);


    clearRow(3);
    initTable();
}