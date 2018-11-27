
/**
 * 历史曲线的toolTip
 * @type {{valueSuffix: string, split: boolean, distance: number, padding: number, style: {fontSize: string, fontFamily: string, fontWeight: string}, dateTimeLabelFormats: {millisecond: string, second: string, minute: string, hour: string, day: string}}}
 */
var curveToolTip = {
    valueSuffix: '',
    // split: true,
    distance: 20,
    padding: 10,
    shared: true,
    style: {
        fontSize: "14px",
        fontFamily: "微软雅黑",
        fontWeight: "light"
    },
    dateTimeLabelFormats: {
        millisecond: '%Y-%m-%d %H:%M:%S',
        second: '%Y-%m-%d %H:%M:%S',
        minute: '%Y-%m-%d %H:%M:%S',
        hour: '%Y-%m-%d %H:%M:%S',
        day: '%Y-%m-%d %H:%M:%S'
    }
};

/**
 *
 * @param masterChart
 * @param startTime
 * @param endTime
 * @param seriesDataArr    对象数组，[{type:,name:,data:,color:}]
 * @param chartName
 */
function createDetailWithSerialData(masterChart, startTime, endTime, seriesDataArr,chartName){
    console.log("创建detailchart");
    var detailStart = startTime;
    var chartSeriesDataArr = [];
    for(var x in seriesDataArr){
        var serialObj = seriesDataArr[x];
        var serialData = serialObj.data;
        var serialName = serialObj.name;
        var serialColor = serialObj.color;
        var series = generatorDetailSerial(serialData,detailStart,serialName,serialColor);
        chartSeriesDataArr.push(series);
    }
    console.log("detailchart" + "," + chartSeriesDataArr.length);
    detailChart = generatorWithDataDetailChart(chartSeriesDataArr,chartName);
}
/**
 * 创建详细数据图
 * @param masterChart
 */
function createDetail(masterChart, startTime, endTime, seriesData,chartName) {

    var detailStart = startTime;
    var seriesDataArr = [];
    var masterChartSeriesArr = masterChart.series;
    var max = 0, min = 0;
    for (var x in masterChartSeriesArr) {
        if(x>=(masterChartSeriesArr.length-1)){
            break;
        }

        var masterChartSeries = masterChartSeriesArr[x];
        var detailData = [];
        Highcharts.each(masterChartSeries.data, function (d) {
            if (d.x >= detailStart) {
                detailData.push(d.y);
                if (d.y > max) {max = d.y;}
                if (d.y < min) {min = d.y;}
            }
        });
        var series = generatorDetailSerial(detailData,detailStart,masterChartSeries.name,masterChartSeries.color);

        seriesDataArr.push(series);
    }
    // if (min === max) {
    //生成一个没有数据的detailChart
    // detailChart = generatorNoDataDetailChart();
    // } else {
    //生成一个有数据的detailChart
    // detailChart = generatorWithDataDetailChart(seriesDataArr);
    // }
    detailChart = generatorWithDataDetailChart(seriesDataArr,chartName);
}

function generatorDetailSerial(serialData,pointStart,seriesName,seriesColor){
    var series = {
        yAxis: 0,
        name: seriesName,
        color: seriesColor,
        pointStart: pointStart,
        pointInterval: 1 * 1000,
        lineWidth: 1,
        data: serialData,
        // data: [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1],
        events: detailClickEvent
    };
    return series;
}

/**
 * detailChart鼠标点击事件
 * @type {{click: detailClickEvent.click}}
 */
var detailClickEvent = {
    click: function (e) {
        /*var detailToolTip = detailChart.tooltip;
        detailToolTip.refresh(e.point);*/
        var xAxisForDetail = detailChart.xAxis[0];
        var maxForDetail = xAxisForDetail.dataMax;
        var minForDetail = xAxisForDetail.dataMin;
        xAxisForDetail.removePlotLine("mask-line");
        xAxisForDetail.addPlotLine({
            id: "mask-line",
            value: e.point.x,
            width: 1,
            color: "white"
        });

        //???contrastChart??tooltip??????
        var series = contrastChart.series[0];
        var seriesData = series.data;
        var targetPoint;
        Highcharts.each(seriesData, function (d) {
            if (d.x === e.point.x) {
                targetPoint = d;
                console.log("x: " + d.x)
                // break;
                //return;
            }
        });
        var toolTip = contrastChart.tooltip;

        toolTip.refresh(targetPoint);


        var xAxis = contrastChart.xAxis[0];
        var max = xAxis.dataMax, min = xAxis.dataMin;

        xAxis.removePlotLine("mask-line");
        xAxis.addPlotLine({
            id: "mask-line",
            value: targetPoint.x,
            width: 1,
            color: "white"
        });


    }
};
var detailChartOptions = {
    marginBottom: 120,
    reflow: false,
    marginTop: 200,
    marginLeft: 50,
    marginRight: 50,
    height: 650,
    style: {
        position: 'absolute'
    }
};
var detailYAxis =  [{
    title: {
        text: null
    },
    // min: 0.6,
    // max: 10,
    startOnTick: false,
    tickPositioner: function () {
        console.log(this.dataMin,this.dataMax)
        // if(this.dataMin == null){this.dataMin = 0}
        // if(this.dataMax == null){this.dataMax = 1}
        //
        // // if(this.dataMin)
        // // this.dataMin = 0.1;
        // // this.dataMax = 0.1;
        var positions = [],
            tick = Math.floor(this.dataMin),
            increment = Math.ceil((this.dataMax - this.dataMin) / 6);
        if(increment == 0){
            // positions.push(tick - 1);
            positions.push(tick);
            positions.push(Math.ceil(tick + 1));
            positions.push(Math.ceil(tick + 2));
        }else {
            for (tick; tick - increment <= this.dataMax; tick += increment) {
                positions.push(tick);
            }
        }
        return positions;
    }
    /*,
    maxZoom: 0.1*/

}];
var detailPlotOptions =  {
    series: {
        animation: false,
        enableMouseTracking: true,
        marker: {
            enabled: false,
            states: {
                hover: {
                    enabled: false,
                    radius: 3
                },
                select: {
                    enabled: false
                }
            }
        },
        stickyTracking: true
    }
};
var detailLegend = {
    enabled: false,
    itemStyle: {
        fontFamily: '微软雅黑',
        fontSize: 6,
        fontWeight: 'light'
    }
};
/**
 * 生成一个有数据的detailChart
 * @returns {Highcharts.Chart}
 */
function generatorNoDataDetailChart() {
    return Highcharts.chart(detailContainer, {
        chart:detailChartOptions,
        title: {
            text: 'No data in line chart - with custom options'
        },
        series: [{
            type: 'line',
            name: 'Random data',
            data: []
        }],
        lang: {
            noData: "Nichts zu anzeigen" //真正显示的文本
        },
        noData: {
            // Custom positioning/aligning options
            position: {    //相对于绘图区定位无数据标签的位置。 默认值：[object Object].
                //align: 'right',
                //verticalAlign: 'bottom'
            },
            // Custom svg attributes
            attr: {     //无数据标签中额外的SVG属性
                //'stroke-width': 1,
                //stroke: '#cccccc'
            },
            // Custom css
            style: {    //对无数据标签的CSS样式。 默认值：[object Object].
                //fontWeight: 'bold',
                //fontSize: '15px',
                //color: '#202030'
            }
        }
    });
}

/**
 * 生成一个有数据的detailChart
 * @param seriesDataArr
 * @returns {Highcharts.Chart}
 */
function generatorWithDataDetailChart(seriesDataArr,chartName) {
    return Highcharts.chart(detailContainer, {
        chart: detailChartOptions,
        credits: {enabled: false},
        title: {
            text: chartName,style:titleStyle/*,
            style:{"color":"red"}*/
        },
        subtitle: {text: ''},
        xAxis: {
            type: 'datetime',
            crosshair: false
        },
        yAxis:detailYAxis,
        tooltip: curveToolTip,
        // tooltip: false,
        legend: detailLegend,
        plotOptions:detailPlotOptions,
        series: seriesDataArr,
        exporting: {enabled: false}
    });
}