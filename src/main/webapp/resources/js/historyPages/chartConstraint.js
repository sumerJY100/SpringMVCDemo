/**
 * 磨煤机莫煤量曲线提示
 * @type {{valueSuffix: string, distance: number, padding: number, style: {fontSize: string, fontFamily: string, fontWeight: string}, dateTimeLabelFormats: {millisecond: string, second: string, minute: string, hour: string, day: string}}}
 */
var curveToolTipForContrast = {
    valueSuffix: '',
    // split: true,
    distance: 20,
    padding: 10,
    // shared:true,
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
 * 磨煤机煤量曲线
 * @param master
 * @param startTime
 * @param endTime
 * @param seriesData
 */
function createContrastChart(master, startTime, endTime, seriesData,chartName) {
    var seriesDataForBe = [], max = 0, min = 0;
    Highcharts.each(master.series[4].data, function (d) {
        if (d.x >= startTime) {
            seriesDataForBe.push(d.y);
            if (d.y > max) {max = d.y;}
            if (d.y < min) {min = d.y;}
        }

    });
    var constraintSeries = {
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
    contrastChart = generatorWithDataContrastChart(constraintSeries,chartName);
    // }
}



var contrastChartOptions = {
    reflow: false,
    borderWidth: 0,
    backgroundColor: null,
    marginLeft: 50,
    marginRight: 50/*,
    zoomType: 'x'*/
};
var contrastPlotOptions =  {
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
        enableMouseTracking: true
    }
};
var contrastYAxis = {
    gridLineWidth: 1,
    labels: {
        enabled: true
    },
    title: {
        text: null
    },
    // min: 0.6,
    // max : 10,
    showFirstLabel: false,
    opposite: true,
    tickPositioner: function () {
        var positions = [],
            tick = Math.floor(this.dataMin),
            increment = Math.ceil((this.dataMax - this.dataMin) / 6);
        for (tick; tick - increment <= this.dataMax; tick += increment) {
            positions.push(tick);
        }
        return positions;
    }
};
var contrastXAxis = {
    visible: false,
    type: 'datetime',
    showLastTickLabel: true,
    maxZoom: 10 * 5 * 1000, // 50秒
    title: {
        text: null
    }
};
var contrastYAxis =  [{
    title: {
        text: null
    },
    // min: 0.6,
    // max: 10,
    tickInterval:0.1,
    startOnTick: false,
    opposite: true,
    tickPositioner: function () {
        // console.log(this.dataMin,this.dataMax)
        // if(this.dataMin)
        // this.dataMin = 0.1;
        // this.dataMax = 0.1;
        var positions = [],
            tick = Math.floor(this.dataMin),
            increment = Math.ceil((this.dataMax - this.dataMin) / 6);
        var intervalForY =   (this.dataMax - this.dataMin) / 6;
        intervalForY = intervalForY.toFixed(2);
        // console.log("intervalForY:" + intervalForY)
        // console.log("increment:" + increment);
        if(increment == 0){
            // positions.push(tick - 1);
            positions.push(tick);
            console.log("intervalForY---------:" + intervalForY)
            // positions.push(tick + intervalForY);
            // positions.push(tick - intervalForY);
            positions.push(tick + 1);
            positions.push(tick + 2);
        }else {
            for (tick; tick - increment <= this.dataMax; tick += increment) {
                positions.push(tick);
            }
        }
        // console.log("positions:" + positions.length)
        return positions;
    }
    ,
    maxZoom: 0.1

}];

/**
 * 创建一个有数据的contrastChart
 * @param series
 * @returns {Highcharts.Chart}
 */
function generatorWithDataContrastChart(series,chartName) {
    return Highcharts.chart(contrastContainer, {
        chart: contrastChartOptions,
        //  title: {text: chartName,margin:0},
        title:{text:""},
        xAxis: contrastXAxis,
        yAxis: contrastYAxis,
        tooltip: curveToolTipForContrast,
        legend: {enabled: false},
        credits: {enabled: false},
        plotOptions:contrastPlotOptions,
        series: [series],
        exporting: {enabled: false}

    })
}

function generatorNoDataContrastChart() {

}