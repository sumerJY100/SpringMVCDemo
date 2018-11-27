


// JS 代码
Highcharts.setOptions({
    global: {
        useUTC: false,
        animation: false,
        enableMouseTracking: false,

    }
});




/*
 * 开始创建导航图，详细的图是在导航图的回调函数中创建的
 * 代码入口
 */
//var masterChart = createMaster();



function initTable() {
    var detailXAxis = detailChart.xAxis[0];
    var seriesArr = detailChart.series;
    // var detailXAxis = masterChart.xAxis[0];
    // var seriesArr = masterChart.series;
    var xMax = detailXAxis.dataMax;
    var xMin = detailXAxis.dataMin;
    var millSeriesArr = contrastChart.series;

    var allArr = new Array();
    for(var x in seriesArr){
        allArr.push(seriesArr[x]);
    }
    allArr.push(millSeriesArr[0]);

    for (var x in allArr) {
        var series = allArr[x];
        var currentSeriesName = series.name;

        var data, left, right, totalCount, avg, totalValues, max, maxValue, min, minValue = 0, leftValue, rightValue;
        data = series.data;
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
        initTableRow(currentSeriesName, left, leftValue, right, rightValue, max, maxValue, min, minValue, avg)
    }
    //进行偏差数据分析
    var $table = $("#dataTable");
    var reg1 = new RegExp(" ","g");
    var reg2 = new RegExp(",","g");
    var pipe1Data =  $table.find("tr:eq(2)").find("td:eq(13)").html().replace(reg1,"").replace(reg2,"");
    var pipe2Data =  $table.find("tr:eq(3)").find("td:eq(13)").html().replace(reg1,"").replace(reg2,"");
    var pipe3Data =  $table.find("tr:eq(4)").find("td:eq(13)").html().replace(reg1,"").replace(reg2,"");
    var pipe4Data =  $table.find("tr:eq(5)").find("td:eq(13)").html().replace(reg1,"").replace(reg2,"");

    var pipeAvg = (parseFloat(pipe1Data) + parseFloat(pipe2Data) + parseFloat(pipe3Data) + parseFloat(pipe4Data)) /parseFloat("4");
    var pipe1Dif = parseFloat(pipe1Data) - pipeAvg;
    var pipe2Dif = parseFloat(pipe2Data) - pipeAvg;
    var pipe3Dif = parseFloat(pipe3Data) - pipeAvg;
    var pipe4Dif = parseFloat(pipe4Data) - pipeAvg;


    $table.find("tr:eq(2)").find("td:eq(14)").html(pipe1Dif.toFixed(2));
    $table.find("tr:eq(3)").find("td:eq(14)").html(pipe2Dif.toFixed(2));
    $table.find("tr:eq(4)").find("td:eq(14)").html(pipe3Dif.toFixed(2));
    $table.find("tr:eq(5)").find("td:eq(14)").html(pipe4Dif.toFixed(2));

    if(pipeAvg === 0){
        $table.find("tr:eq(2)").find("td:eq(15)").html("0" + "%");
        $table.find("tr:eq(3)").find("td:eq(15)").html("0" + "%");
        $table.find("tr:eq(4)").find("td:eq(15)").html("0" + "%");
        $table.find("tr:eq(5)").find("td:eq(15)").html("0" + "%");
    }else{
        $table.find("tr:eq(2)").find("td:eq(15)").html((pipe1Dif/pipeAvg * 100).toFixed(2) + "%");
        $table.find("tr:eq(3)").find("td:eq(15)").html((pipe2Dif/pipeAvg * 100).toFixed(2) + "%");
        $table.find("tr:eq(4)").find("td:eq(15)").html((pipe3Dif/pipeAvg * 100).toFixed(2) + "%");
        $table.find("tr:eq(5)").find("td:eq(15)").html((pipe4Dif/pipeAvg * 100).toFixed(2) + "%");
    }


}

function initTableRow(currentSeriesName, left, leftValue, right, rightValue, xMax, maxValue, xMin, minValue, avg) {
    var $table = $("#dataTable");
    $table.find("tr:gt(0)").each(function () {
        //console.log("currentSeriesName:" + currentSeriesName)
        var firstTdHtml = $(this).find("td:eq(0)");
        if (firstTdHtml[0].innerHTML === currentSeriesName) {
            if(currentSeriesName == "pipe2"){
                $(this).find("td:eq(1)").html(dateFtt("yyyy-MM-dd ", new Date(left)));
                $(this).find("td:eq(2)").html(dateFtt(" hh:mm:ss", new Date(left)));
                $(this).find("td:eq(4)").html(dateFtt("yyyy-MM-dd ", new Date(right)));
                $(this).find("td:eq(5)").html(dateFtt("hh:mm:ss", new Date(right)));

                $(this).find("td:eq(11)").css("border-bottom","0px");
            }
            $(this).find("td:eq(1)").css("border-bottom","0px").css("border-top","0px");
            $(this).find("td:eq(2)").css("border-bottom","0px").css("border-top","0px");
            $(this).find("td:eq(4)").css("border-bottom","0px").css("border-top","0px");
            $(this).find("td:eq(5)").css("border-bottom","0px").css("border-top","0px");




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

var pipe1ForDensity, pipe2ForDensity, pipe3ForDensity, pipe4ForDensity;
var pipe1ForVelocity, pipe2ForVelocity,pipe3ForVelocity, pipe4ForVelocity;
var millDatas;
var densityDataArr = [pipe1ForDensity,pipe2ForDensity,pipe3ForDensity,pipe4ForDensity];
var velocityDataArr = [pipe1ForVelocity,pipe2ForVelocity,pipe3ForVelocity,pipe4ForVelocity];
var queryStartTime ,queryEndTime;
var chartName = "";

/**
 * 通过访问后台获取数据
 */
function initHistoryData() {
    // var url = "../getAMillHistoryData";
    var url = getQueryUrl();
    var queryData = $("#queryForm").serialize();
    chartName = "密度历史曲线";
    $("#dataLoad").show(); //页面加载完毕后即将DIV隐藏
    $.getJSON(url, queryData, function (result) {



        var densityType = "density",
            velocityType = "velocity";


         pipe1ForDensity = new PipeHistoryData(densityType, "pipe1", pipe1Color, result.pipe1Density);
         pipe2ForDensity = new PipeHistoryData(densityType, "pipe2", pipe2Color, result.pipe2Density);
         pipe3ForDensity = new PipeHistoryData(densityType, "pipe3", pipe3Color, result.pipe3Density);
         pipe4ForDensity = new PipeHistoryData(densityType, "pipe4", pipe4Color, result.pipe4Density);

         pipe1ForVelocity = new PipeHistoryData(velocityType, "pipe1", pipe1Color, result.pipe1Velocity);
         pipe2ForVelocity = new PipeHistoryData(velocityType, "pipe2", pipe2Color, result.pipe2Velocity);
         pipe3ForVelocity = new PipeHistoryData(velocityType, "pipe3", pipe3Color, result.pipe3Velocity);
         pipe4ForVelocity = new PipeHistoryData(velocityType, "pipe4", pipe4Color, result.pipe4Velocity);

         millDatas = new PipeHistoryData("","mill",pipe1Color,result.coalMillDatas);

        densityDataArr = [pipe1ForDensity,pipe2ForDensity,pipe3ForDensity,pipe4ForDensity];
        velocityDataArr = [pipe1ForVelocity,pipe2ForVelocity,pipe3ForVelocity,pipe4ForVelocity];

        //创建masterChart
        queryStartTime = result.startTime;
        queryEndTime = result.endTime;
        masterChart = createMaster(result.startTime, result.endTime, [pipe1ForDensity, pipe2ForDensity, pipe3ForDensity, pipe4ForDensity,millDatas],chartName);


        initTable();

        $("#dataLoad").hide(); //页面加载完毕后即将DIV隐藏
        return false;

    });
}