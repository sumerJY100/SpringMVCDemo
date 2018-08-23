var velocityChartTitle = "风速";
var densityChartTitle = "密度";
var absoluteChartSubtitle = "绝对值";
var relativeChartSubtitle = "相对值";

var millCurve;

var absoluteBarForDensity;
var relativeBarForDensity;
var curveForDensity ;

var absoluteBarForVelocity;
var relativeBarForVelocity ;
var curveForVelocity ;
$(document).ready(function () {

    //TODO 初始化锅炉负荷实时曲线图
    //TODO 锅炉负荷曲线图、密度曲线图、风速曲线图，三个曲线图实现点击同步。
    //初始化锅炉负荷
    //初始化曲线图，柱图
    //密度
    var chartOptions = {exporting:{enabled:false},legend:{align:"right",verticalAlign:"top",floating:true,y:40,itemDistance:5,itemStyle:{fontFamily:"宋体",fontWeight:'light'}}};

    var curveOptions = $.extend({},chartOptions);
    curveOptions.legend.y = 10;
    millCurve = getCurveChartWithOptionsAndSerieNames("container_Mill", "磨煤机", "",curveOptions,["磨煤量"]);
     absoluteBarForDensity = getAbsoluteBarWithOptions("container_absoluteBar", densityChartTitle+"("+ relativeChartSubtitle + ")" ,"",chartOptions);
     relativeBarForDensity = getRelativeBarWithOptions("container_relativeBar", densityChartTitle+"("+ relativeChartSubtitle + ")" ,"",chartOptions);
     curveForDensity = getCurveChartWithOptions("container", densityChartTitle, "",curveOptions);
    //风速
     absoluteBarForVelocity = getAbsoluteBarWithOptions("container_V_absoluteBar", velocityChartTitle + "(" + absoluteChartSubtitle+")", "",chartOptions);
     relativeBarForVelocity = getRelativeBarWithOptions("container_V_relativeBar", velocityChartTitle+ "(" + absoluteChartSubtitle+")", "",chartOptions);
     curveForVelocity = getCurveChartWithOptions("container_V", velocityChartTitle, "",curveOptions);

     curveForVelocity.len
     //初始化曲线图数据
     var urlForCurveDensity = "../getInitTimeData";
    initCurveChart(urlForCurveDensity,curveForDensity);
    initCurveChart(urlForCurveDensity,curveForVelocity);

    //加载完成后刷新一次
    freshMainPage();
    //定时刷新
    setInterval(freshMainPage,5000);

})
function freshMainPage(){
    var latestTime = 100;
    var url = "../getMillARealTimeData";
    $.get(url, {"latestTime": latestTime}, function (result) {
        freshCurrentChartAndTable(result);
    }, "json");
}
/**
 * 刷新当前正在显示的图表
 * @param result
 */
function freshCurrentChartAndTable(result) {
    var time = result.time;
    var millA = result.millA;

    var millADensityData = getDensityDataFromMill(millA);
    var millAVelocityData = getVelocityDataFromMill(millA);
    var millADensityDataForRelative = getMillDataFromAbsoluteToRelative(millADensityData);
    var millAVelocityDataForRelative = getMillDataFromAbsoluteToRelative(millAVelocityData);


    freshCurveChart(curveForDensity, time, millADensityData);
    freshRelativeBar(relativeBarForDensity,millADensityDataForRelative);
    freshRelativeBar(absoluteBarForDensity,millADensityData);

    freshCurveChart(curveForVelocity, time, millAVelocityData);
    freshRelativeBar(relativeBarForVelocity,millAVelocityDataForRelative);
    freshRelativeBar(absoluteBarForVelocity,millAVelocityData);
//    freshAbsoluteBar(absoluteBarForDensity, millADensityData);

    //刷新表格
    //TODO 刷新实时画面的窗口
    // freshMainPageTable(group1,"coalMillTableADiv");



}

