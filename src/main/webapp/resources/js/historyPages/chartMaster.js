
/**
 * masterChart 鼠标选择中区域事件
 * @type {{selection: mastreClickEvent.selection}}
 */
//TODO 当detialchart中含有plotLine时，改变区域，需要将plotline去除。plotline包含millchart与detailchart
var mastreClickEvent = function(startTime,endTime){
    return {
        // listen to the selection event on the master chart to update the
        // extremes of the detail chart
        selection: function (event) {
            var beginT0 =  new Date().getTime();
            var extremesObject = event.xAxis[0],
                min = extremesObject.min,
                max = extremesObject.max,
                /*detailData = [],*/
                xAxis = this.xAxis[0];
            var seriesArr = this.series;
            var detailSeriesArr = detailChart.series;





            var seriesDataArr = [];
            var t0 = new Date().getTime();
            for (var x in seriesArr) {
                if(x>=4){
                    break;
                }
                var detailData = [];
                var masterSeries = seriesArr[x];

                Highcharts.each(masterSeries.data, function (d) {
                    if (d.x > min && d.x < max) {
                        detailData.push([d.x, d.y]);
                    }
                });
                var series = generatorDetailSerial(detailData, min, masterSeries.name, masterSeries.color);
                seriesDataArr.push(series);
            }
            detailChart = generatorWithDataDetailChart(seriesDataArr,chartName);

            var t1 = new Date().getTime();
            console.log("重绘4个粉管的时间差：" + (t1-t0));
            /**     使用setData方法进行重绘

             for (var x in seriesArr) {
                var detailData = [];
                var masterSeries = seriesArr[x];
                var t0 = new Date().getTime();
                Highcharts.each(masterSeries.data, function (d) {
                    if (d.x > min && d.x < max) {
                        detailData.push([d.x, d.y]);
                    }
                });
                var t1 = new Date().getTime();
                console.log("t1 - t0 :" + (t1 - t0));
                var masterSeriesName = masterSeries.name;
                for (var m in detailSeriesArr) {
                    var detailSeries = detailSeriesArr[m];
                    var detailSeriesName = detailSeries.name;
                    if (detailSeriesName === masterSeriesName) {
                        var t2 = new Date().getTime();
                        // detailSeries.setData(detailData, false);
                        detailChart.series[m].setData(detailData);
                        // detailChart.series[m].updateData(detailData);
                        // detailChart.redraw(false);
                        // console.log("赋值前："+detailSeriesArr[m].data.length);
                        // detailSeriesArr[m].data = detailData;
                        // console.log("赋值后："+detailSeriesArr[m].data.length);
                        var t3 = new Date().getTime();
                        console.log(detailSeriesName + ",重绘时间：" + "t3 - t2 :" + (t3 - t2));
                    }
                }

            }
             */



                // detailChart.update(detailSeriesArr);

            var beginT1 =  new Date().getTime();
            // console.log("时间差00：" + (beginT1-beginT0));

            //detailChart.redraw();
            // detailChart.series[0].setData();
            // detailChart.series[1].setData();
            // detailChart.series[2].setData();
            // detailChart.series[3].setData();

            var endT = new Date().getTime();


            console.log("时间差01：" + (endT-beginT0));

            //对磨煤量曲线进行重绘
            var endT03 = new Date().getTime();
            var seriesForBe = seriesArr[seriesArr.length-1];
            var seriesDataForBe = [];
            Highcharts.each(seriesForBe.data, function (d) {
                if (d.x > min && d.x < max) {
                    seriesDataForBe.push([d.x, d.y]);
                }
            });

            // contrastChart.series[0].setData(seriesDataForBe);

            /* Highcharts.each(this.series[0].data, function(d) {
                 if(d.x > min && d.x < max) {
                     detailData.push([d.x, d.y]);
                 }
             });*/
            // move the plot bands to reflect the new detail span


            var series = {
                name: '磨煤量',
                color: 'red',
                pointStart: startTime,
                pointInterval: 1 * 1000,
                lineWidth: 1,
                data: seriesDataForBe
            };
            // if(min === max){
            //创建一个没有数据的contrastChart
            // contrastChart = generatorNoDataContrastChart();
            // }else {
            //创建一个有数据的contraschart
            contrastChart = generatorWithDataContrastChart(series,chartName);

            var endT02 = new Date().getTime();
            console.log("磨煤机磨煤量重绘 ，时间差：" + (endT02-endT03));


            xAxis.removePlotBand('mask-before');
            xAxis.addPlotBand({
                id: 'mask-before',
                // from: Date.UTC(2006, 0, 1),
                from: startTime,
                to: min,
                color: 'rgba(0, 255, 255, 0.2)'
            });
            xAxis.removePlotBand('mask-after');
            xAxis.addPlotBand({
                id: 'mask-after',
                from: max,
                // to: Date.UTC(2008, 11, 31),
                to: endTime,
                color: 'rgba(0, 255, 255, 0.2)'
            });


            initTable();
            /*detailChart.series[0].setData(detailData);*/
            return false;
        }
    }
};









var masterChartPlotOptions = {
    series: {
        fillColor: {
            linearGradient: [0, 0, 0, 70],
            stops: [
                [0, Highcharts.getOptions().colors[0]],
                [1, 'rgba(255,255,255,0)']
            ]
        },
        lineWidth: 1,
        animation: false,
        marker: {
            enabled: false
        },
        shadow: false,
        states: {
            hover: {
                enabled: false,
                lineWidth: 0.2
            }
        },
        enableMouseTracking: false
    }
};
var masterChartLegend = {
    enabled: false,
    itemStyle: {
        fontFamily: '微软雅黑',
        fontSize: 6,
        fontWeight: 'light'
    }
};
var masterChartYAxis = {
    title: {text: null},
    // startOnTick: false,
    gridLineWidth: 0,
    labels: {enabled: false},
    showFirstLabel: false
};
function createMasterChartXAxis(startTime,endTime) {
    var masterChartXAxis = {
        type: 'datetime',
        showLastTickLabel: true,
        maxZoom: 10 * 5 * 1000, // 50秒
        plotBands: [{
            id: 'mask-before',
            /*  from: Date.UTC(2006, 0, 1),
              to: Date.UTC(2008, 7, 1),*/
            from: startTime,
            to: endTime,
            color: 'rgba(0, 0, 0, 0.2)'
        }],
        title: {
            text: null
        }
    };
    return masterChartXAxis;
}
function generatorMasterChartOption(startTime,endTime){
    var masterChartOption = {
        reflow: false,
        borderWidth: 0,
        backgroundColor: null,
        marginLeft: 50,
        marginRight: 20,
        zoomType: 'x',
        events:mastreClickEvent(startTime,endTime)
    };
    return masterChartOption;
}

function generatorMasterSeries(pointStart,seriesData,seriesName,seriesColor){
    return {
        type: 'line',
        name: seriesName,
        color: seriesColor,
        pointInterval: 1 * 1000,
        pointStart: pointStart,
        data: seriesData
    };
}
/**
 * 创建导航图
 * @returns {Highcharts.Chart}
 */
function createMaster(startTime, endTime, pipeHistoryDataArr,chartName) {
    var seriesObjArr = [];
    var pipeHistoryDataArrCopy = $.extend(true,{},pipeHistoryDataArr);

    var t001 = new Date().getTime();
    for (var x in pipeHistoryDataArrCopy) {
        // if(x>=4){
        //     break;
        // }
        var p = pipeHistoryDataArrCopy[x];
        var targetData = [];
        var sourceData = p.data;
        for(var m in sourceData){
            targetData.push(sourceData[m]);
        }
        var color = p.color;
        var series = generatorMasterSeries(startTime,targetData,p.name,color);
        console.log("pipeHistoryDataArrCopy.length-1:" +(pipeHistoryDataArr.length));
        if(x>=(pipeHistoryDataArr.length-1)) {
            series.visible = false;
        }
        seriesObjArr.push(series);
    }


    var t002 = new Date().getTime();
    console.log("数据处理的时间：" + (t002 - t001));

    var chart = Highcharts.chart(masterContainer, {
        chart: generatorMasterChartOption(startTime,endTime),
        title: {text: null},
        xAxis: createMasterChartXAxis(startTime,endTime),
        yAxis: masterChartYAxis,
        tooltip: {formatter: function () {return false;}},
        legend:masterChartLegend ,
        credits: {enabled: false},
        plotOptions: masterChartPlotOptions,
        series: seriesObjArr,
        exporting: {enabled: false}
    }, function (masterChart) {
        var t01 = new Date().getTime();
        createDetail(masterChart, startTime, endTime, pipeHistoryDataArr,chartName);
        var t02 = new Date().getTime();
        console.log("创建detailChart的时间:" + (t02- t01));
        createContrastChart(masterChart, startTime, endTime, pipeHistoryDataArr,chartName);
        var t03 = new Date().getTime();
        console.log("创建磨煤机磨煤量图表的时间:" + (t03- t02));
    });
    var t003 = new Date().getTime();
    console.log("创建整个chart的时间：" + (t003 - t002));
    return chart;
}
