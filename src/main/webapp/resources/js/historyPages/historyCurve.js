/**
 * 改变chart的数据为风速的数据
 */
function changeChartDataToVelocity(){
    chartName = "风速历史曲线";
    console.log("change to Velocity");

    var pipeHistoryDataArrCopy = $.extend(true,{},velocityDataArr);
    pipeHistoryDataArrCopy[4] = millDatas;

    createMaster(queryStartTime, queryEndTime, pipeHistoryDataArrCopy,chartName);
    /*// masterChart.setTitle({text:"风速历史曲线"});
    detailChart.setTitle({text:chartName});
    contrastChart.setTitle({text:chartName});
    //改变masterChart的数据
    //改变detailChart的数据
    //改变contrastChart的数据
    var chartSeriesArr = masterChart.series;
    var velocityDataArrCopy = jQuery.extend(true,{},velocityDataArr);
    var millDataCopy = jQuery.extend(true,{},millDatas);
    for(var x in chartSeriesArr){
        var chartSeries = chartSeriesArr[x];
        var chartSeriesName = chartSeries.name;
        for(var m in velocityDataArrCopy){
            var velocityData = velocityDataArrCopy[m];
            var currentVelocityDataName = velocityData.name;
            if(currentVelocityDataName === chartSeriesName){
                console.log("chartSeriesName:" + chartSeriesName)
                // console.log("赋值 前： " + velocityData.getData());
                //将velocityData赋值给当前的series
                var targetData = [];
                for(var i=0;i<velocityData.getData().length;i++){
                    targetData.push(velocityData.getData()[i]);
                }
                chartSeries.setData(targetData,false);
                // console.log("赋值 后：" +velocityData.getData());
            }
        }
    }
    masterChart.redraw();
    var maxAndMinObj = getMaxAndMinFunction();
    var max = maxAndMinObj.max;
    var min = maxAndMinObj.min;
    changeDetailChartData(max,min);*/
}

/**
 * 改变chart的数据为浓度的数据
 */
function changeChartDataToDensity(){
    console.log("change to Density");
    // masterChart.setTitle({text:"密度历史曲线"});
    detailChart.setTitle({text:"密度历史曲线"});
    contrastChart.setTitle({text:"密度历史曲线"});
    var pipeHistoryDataArrCopy = $.extend(true,{},densityDataArr);
    pipeHistoryDataArrCopy[4] = millDatas;
    createMaster(queryStartTime, queryEndTime, pipeHistoryDataArrCopy,chartName);

/*


    var chartSeriesArr = masterChart.series;
    var densityDataArrCopy = jQuery.extend(true,{},densityDataArr);
    for(var x in chartSeriesArr){
        var chartSeries = chartSeriesArr[x];
        var chartSeriesName = chartSeries.name;
        for(var m in densityDataArrCopy){
            var densityData = densityDataArrCopy[m];
            var currentDensityDataName = densityData.name;
            if(currentDensityDataName === chartSeriesName){
                //将velocityData赋值给当前的series
                var targetData = [];
                for(var i=0;i<densityData.getData().length;i++){
                    targetData.push(densityData.getData()[i]);
                }
                chartSeries.setData(targetData,false);
                // console.log(densityData.getData())
            }
        }
    }
    masterChart.redraw();
    var maxAndMinObj = getMaxAndMinFunction();
    var max = maxAndMinObj.max;
    var min = maxAndMinObj.min;
    changeDetailChartData(max,min);*/
}






function changeDetailChartData(max,min){

    var seriesArr = masterChart.series;
    var detailSeriesArr = detailChart.series;
    var seriesDataArr = [];
    for (var x in seriesArr) {
        var detailData = [];
        var masterSeries = seriesArr[x];
        Highcharts.each(masterSeries.data, function (d) {
            if (d.x > min && d.x < max) {
                detailData.push([d.x, d.y]);
            }
        });
        var masterSeriesName = masterSeries.name;
        for (var m in detailSeriesArr) {
            // var detailSeries = detailSeriesArr[m];
            // var detailSeriesName = detailSeries.name;
            // if (detailSeriesName === masterSeriesName) {
            //     detailSeries.setData(detailData, false);
            // }

            var series = generatorDetailSerial(detailData, min, masterSeries.name, masterSeries.color);
            seriesDataArr.push(series);
        }

    }
    detailChart = generatorWithDataDetailChart(seriesDataArr,chartName);
    // detailChart.redraw();
    //对磨煤量曲线进行重绘
    // contrastChart.series[0].setData(millDatas.data);


    var constraintSeries = {
        name: '磨煤量',
        color: 'red',
        pointStart: min,
        pointInterval: 1 * 1000,
        lineWidth: 1,
        data: millDatas.data
    };
    contrastChart = generatorWithDataContrastChart(constraintSeries,chartName);




    /* Highcharts.each(this.series[0].data, function(d) {
         if(d.x > min && d.x < max) {
             detailData.push([d.x, d.y]);
         }
     });*/
    // move the plot bands to reflect the new detail span
   /* xAxis.removePlotBand('mask-before');
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
    });*/
    /*detailChart.series[0].setData(detailData);*/
    initTable();
}














function getMaxAndMinFunction(){
    var max,min;
    var plotBandsArr = masterChart.xAxis[0].plotLinesAndBands;
    var plotBandsForBefore ,plotBandsForAfter;
    for(var x in plotBandsArr){
        var plotBands = plotBandsArr[x];
        if(plotBands){
            if(plotBands.id === "mask-before"){plotBandsForBefore = plotBands;}
            if(plotBands.id === "mask-after"){plotBandsForAfter = plotBands;}
        }
    }
    if(plotBandsForBefore && plotBandsForAfter){
        max = plotBandsForAfter.options.from;
        min = plotBandsForBefore.options.to;
    }else{
        max = masterChart.xAxis[0].dataMax;
        min = masterChart.xAxis[0].dataMin;
    }




    var tempMax = -1,tempMin = -1;
    var xAxisData = masterChart.xAxis[0].data;
    for(var i in xAxisData){
        var tempData = xAxisData[i];
        if(tempMax != -1 && tempMin != -1){
            break;
        }
        if(tempMax != -1) {
            if (tempData >= max) {
                tempMax = tempData;
                max = tempMax;
            }
        }
        if(tempMin != -1){
            if(tempData<=min){
                tempMin = tempData;
                min = tempMin;
            }
        }
    }

    return {"max":max,"min":min};
}