var velocityChartTitle = "风速";
var densityChartTitle = "密度";
var absoluteChartSubtitle = "绝对值";
var relativeChartSubtitle = "相对值";
const densityRadioValue = "2";
const velocityRadioValue = "1";



var group1 = new Group(), group2 = new Group(), group3 = new Group(), group4 = new Group();

var groupArr = [group1, group2, group3, group4];

/**
 * 通过divID查询Group
 * @param curveId
 * @returns {*}
 */
function findGroupByCurveId(curveId) {
    var targetGroup = null;
    for (var x in groupArr) {
        var tempGroup = groupArr[x];
        if (tempGroup) {
            var $div = tempGroup.divJqueryDom;
            if ($div) {
                var currentDivId = $div.attr("id");
                var tagIndex = curveId.indexOf("\#");
                if (tagIndex === 0) {
                    curveId = curveId.substring(1);
                }
                // console.log("tagIndex:" + tagIndex);
                if (curveId === currentDivId) {
                    targetGroup = tempGroup;
                    break;
                }
            }
        }
    }
    return targetGroup;
}
function findGroupByChartDivId(chartDivid){
    var targetGroup;
    for(var x in groupArr){
        var tempGroup = groupArr[x];
        if(tempGroup){
            var chartArray = tempGroup.getChartArray();
            for(var m in chartArray){
                var tempChartDivId = getDivIdFromChartByChart(chartArray[m]);
                if(tempChartDivId === chartDivid){
                    targetGroup = tempGroup;
                    break;
                }
            }
            if(targetGroup){
                break;
            }
        }
    }
    return targetGroup;
}
/**
 * 根据当前正在显示的图表chart，将当前按钮显示为“选中”
 * 并将其它按钮的“选中”样式，去除。
 * @param $curveOrBar
 * @returns {*}
 */
function changeBtnStyleByShowCurveOrBar($curveOrBar) {
    /*
    * 1、根据当前显示的chart，寻找到对应的divID
    * 2、遍历group内的chart，每个chart的divId与当前的divId进行比较
    * 3、找到当前group
    * 4、查询当前group内所有的btn
    * 5、将group内的btn的选中样式都去除。
    * 6、遍历所有的btn，根据btn与chart的对应关系，将对应的btn增加选中样式。
    * */
    var targetGroup = null;
    var $showBtn = null;
    var divId;
    var btnSelectedClass = "currentBtnClass";
    if ($curveOrBar) {
        divId = $curveOrBar.attr("id");
        if (divId) {
            var targetGroup;
            for (var x in groupArr) {
                var tempGroup = groupArr[x];
                if (tempGroup) {
                    if (getDivIdFromChartByChart(tempGroup.curveForDensity) === divId || getDivIdFromChartByChart(tempGroup.curveForVelocity) === divId) {
                        targetGroup = tempGroup;
                        break;
                    } else if (getDivIdFromChartByChart(tempGroup.barAbsoluteForDensity) === divId || getDivIdFromChartByChart(tempGroup.barAbsoluteForVelocity) === divId) {
                        targetGroup = tempGroup;
                        break;
                    } else if (getDivIdFromChartByChart(tempGroup.barRelativeForDensity) === divId || getDivIdFromChartByChart(tempGroup.barRelativeForVelocity) === divId) {
                        targetGroup = tempGroup;
                        break;
                    }

                }
            }
            if (targetGroup) {
                var curveBtn = targetGroup.curveBtn;
                var barAbsoluteBtn = targetGroup.barAbsoluteBtn;
                var barRelativeBtn = targetGroup.barRelativeBtn;
                if (getDivIdFromChartByChart(targetGroup.curveForDensity) === divId || getDivIdFromChartByChart(targetGroup.curveForVelocity) === divId) {
                    $showBtn = curveBtn;
                } else if (getDivIdFromChartByChart(targetGroup.barAbsoluteForDensity) === divId || getDivIdFromChartByChart(targetGroup.barAbsoluteForVelocity) === divId) {
                    $showBtn = barAbsoluteBtn;
                } else if (getDivIdFromChartByChart(targetGroup.barRelativeForDensity) === divId || getDivIdFromChartByChart(targetGroup.barRelativeForVelocity) === divId) {
                    $showBtn = barRelativeBtn;
                }
                if ($showBtn) {
                    if (curveBtn) {
                        curveBtn.removeClass(btnSelectedClass);
                    }
                    if (barAbsoluteBtn) {
                        barAbsoluteBtn.removeClass(btnSelectedClass);
                    }
                    if (barRelativeBtn) {
                        barRelativeBtn.removeClass(btnSelectedClass);
                    }

                    $showBtn.addClass("currentBtnClass");
                }
            }
        }
    }
    return targetGroup;
}

/**
 * 获取chart的divId
 * @param chart
 * @returns {string}
 */
function getDivIdFromChartByChart(chart) {
    var targetDivId = "null";
    if (chart) {
        targetDivId = chart.renderTo.id;
    }
    return targetDivId;
}

/**
 * 初始化Group
 */
function initGroup() {
    var absoluteBarForDensity = getAbsoluteBar("container1_absoluteBar", densityChartTitle, absoluteChartSubtitle);
    var relativeBarForDensity = getRelativeBar("container1_relativeBar", densityChartTitle, relativeChartSubtitle);
    var curveForDensity = getCurveChart("container1", densityChartTitle, "");

    var absoluteBarForDensity_2 = getAbsoluteBar("container2_absoluteBar", densityChartTitle, absoluteChartSubtitle);
    var relativeBarForDensity_2 = getRelativeBar("container2_relativeBar", densityChartTitle, relativeChartSubtitle);
    var curveForDensity_2 = getCurveChart("container2", densityChartTitle, "");

    var absoluteBarForDensity_3 = getAbsoluteBar("container3_absoluteBar", densityChartTitle, absoluteChartSubtitle);
    var relativeBarForDensity_3 = getRelativeBar("container3_relativeBar", densityChartTitle, relativeChartSubtitle);
    var curveForDensity_3 = getCurveChart("container3", densityChartTitle, "");

    var relativeBarForDensity_4 = getRelativeBar("container4_relativeBar", densityChartTitle, relativeChartSubtitle);
    var absoluteBarForDensity_4 = getAbsoluteBar("container4_absoluteBar", densityChartTitle, absoluteChartSubtitle);
    var curveForDensity_4 = getCurveChart("container4", densityChartTitle, "");

    var absoluteBarForVelocity = getAbsoluteBar("container1V_absoluteBar", velocityChartTitle, absoluteChartSubtitle);
    var relativeBarForVelocity = getRelativeBar("container1V_relativeBar", velocityChartTitle, relativeChartSubtitle);
    var curveForVelocity = getCurveChart("container1V", velocityChartTitle, "");

    var absoluteBarForVelocity_2 = getAbsoluteBar("container2V_absoluteBar", velocityChartTitle, absoluteChartSubtitle);
    var relativeBarForVelocity_2 = getRelativeBar("container2V_relativeBar", velocityChartTitle, relativeChartSubtitle);
    var curveForVelocity_2 = getCurveChart("container2V", velocityChartTitle, "");

    var absoluteBarForVelocity_3 = getAbsoluteBar("container3V_absoluteBar", velocityChartTitle, absoluteChartSubtitle);
    var relativeBarForVelocity_3 = getRelativeBar("container3V_relativeBar", velocityChartTitle, relativeChartSubtitle);
    var curveForVelocity_3 = getCurveChart("container3V", velocityChartTitle, "");

    var relativeBarForVelocity_4 = getRelativeBar("container4V_relativeBar", velocityChartTitle, relativeChartSubtitle);
    var absoluteBarForVelocity_4 = getAbsoluteBar("container4V_absoluteBar", velocityChartTitle, absoluteChartSubtitle);
    var curveForVelocity_4 = getCurveChart("container4V", velocityChartTitle, "");


    group1.divJqueryDom = $('#container1');
    group1.curveForDensity = curveForDensity;
    group1.barRelativeForDensity = relativeBarForDensity;
    group1.barAbsoluteForDensity = absoluteBarForDensity;
    group1.curveForVelocity = curveForVelocity;
    group1.barRelativeForVelocity = relativeBarForVelocity;
    group1.barAbsoluteForVelocity = absoluteBarForVelocity;
    group1.densityCurveUrl = "getInitTimeData";
    group1.velocityCurveUrl = "getInitTimeData";
    group2.divJqueryDom = $("#container2");
    group2.curveForDensity = curveForDensity_2;
    group2.barRelativeForDensity = relativeBarForDensity_2;
    group2.barAbsoluteForDensity = absoluteBarForDensity_2;
    group2.curveForVelocity = curveForVelocity_2;
    group2.barRelativeForVelocity = relativeBarForVelocity_2;
    group2.barAbsoluteForVelocity = absoluteBarForVelocity_2;
    group2.densityCurveUrl = "getInitTimeData";
    group2.velocityCurveUrl = "getInitTimeData";
    group3.divJqueryDom = $("#container3");
    group3.curveForDensity = curveForDensity_3;
    group3.barRelativeForDensity = relativeBarForDensity_3;
    group3.barAbsoluteForDensity = absoluteBarForDensity_3;
    group3.curveForVelocity = curveForVelocity_3;
    group3.barRelativeForVelocity = relativeBarForVelocity_3;
    group3.barAbsoluteForVelocity = absoluteBarForVelocity_3;
    group3.densityCurveUrl = "getInitTimeData";
    group3.velocityCurveUrl = "getInitTimeData";
    group4.divJqueryDom = $("#container4");
    group4.curveForDensity = curveForDensity_4;
    group4.barRelativeForDensity = relativeBarForDensity_4;
    group4.barAbsoluteForDensity = absoluteBarForDensity_4;
    group4.curveForVelocity = curveForVelocity_4;
    group4.barRelativeForVelocity = relativeBarForVelocity_4;
    group4.barAbsoluteForVelocity = absoluteBarForVelocity_4;
    group4.densityCurveUrl = "getInitTimeData";
    group4.velocityCurveUrl = "getInitTimeData";
}


/**
 * 刷新当前正在显示的图表
 * @param result
 */
function freshCurrentChartAndTable(result) {
    var time = result.time;
    var millA = result.millA;
    var millB = result.millB;
    var millC = result.millC;
    var millD = result.millD;

    var millADensityData = getDensityDataFromMill(millA);
    var millBDensityData = getDensityDataFromMill(millB);
    var millCDensityData = getDensityDataFromMill(millC);
    var millDDensityData = getDensityDataFromMill(millD);

    var millAVelocityData = getVelocityDataFromMill(millA);
    var millBVelocityData = getVelocityDataFromMill(millB);
    var millCVelocityData = getVelocityDataFromMill(millC);
    var millDVelocityData = getVelocityDataFromMill(millD);

    var millADensityDataForRelative = getMillDataFromAbsoluteToRelative(millADensityData);
    var millBDensityDataForRelative = getMillDataFromAbsoluteToRelative(millBDensityData);
    var millCDensityDataForRelative = getMillDataFromAbsoluteToRelative(millCDensityData);
    var millDDensityDataForRelative = getMillDataFromAbsoluteToRelative(millDDensityData);

    var millAVelocityDataForRelative = getMillDataFromAbsoluteToRelative(millAVelocityData);
    var millBVelocityDataForRelative = getMillDataFromAbsoluteToRelative(millBVelocityData);
    var millCVelocityDataForRelative = getMillDataFromAbsoluteToRelative(millCVelocityData);
    var millDVelocityDataForRelative = getMillDataFromAbsoluteToRelative(millDVelocityData);

    //判定当前正在显示的图表，刷新正在显示的图表

    group1.realTime = time;
    group1.densityRealData = millADensityData;
    group1.densityRealDataForRelative = millADensityDataForRelative;
    group1.velocityRealData = millAVelocityData;
    group1.velocityRealDataForRelative = millAVelocityDataForRelative;

    group2.realTime = time;
    group2.densityRealData = millBDensityData;
    group2.densityRealDataForRelative = millBDensityDataForRelative;
    group2.velocityRealData = millBVelocityData;
    group2.velocityRealDataForRelative = millBVelocityDataForRelative;

    group3.realTime = time;
    group3.densityRealData = millCDensityData;
    group3.densityRealDataForRelative = millCDensityDataForRelative;
    group3.velocityRealData = millCVelocityData;
    group3.velocityRealDataForRelative = millCVelocityDataForRelative;

    group4.realTime = time;
    group4.densityRealData = millDDensityData;
    group4.densityRealDataForRelative = millDDensityDataForRelative;
    group4.velocityRealData = millDVelocityData;
    group4.velocityRealDataForRelative = millDVelocityDataForRelative;

    group1.freshCurrentChart();
    group2.freshCurrentChart();
    group3.freshCurrentChart();
    group4.freshCurrentChart();
//    freshAbsoluteBar(absoluteBarForDensity, millADensityData);


    freshMainPageTable(group1,"coalMillTableADiv");
    freshMainPageTable(group2,"coalMillTableBDiv");
    freshMainPageTable(group3,"coalMillTableCDiv");
    freshMainPageTable(group4,"coalMillTableDDiv");


}
function freshMainPageTable(group,tableDivId){
    var velocityData = group.velocityRealData;
    var velocityDataForRelative = group.velocityRealDataForRelative;
    var densityData = group.densityRealData;
    var densityDataForRelative = group.densityRealDataForRelative;
    var tableArr = $("#"+tableDivId+">table>tbody");
    if(tableArr){

        var $tableA = $(tableArr[0]);
        // alert($tableA.html())
        //pipe1绝对浓度
        $tableA.find("tr:eq(1) td:eq(1)").html(velocityData.pipe1Data);
        $tableA.find("tr:eq(1) td:eq(2)").html(velocityData.pipe2Data);
        $tableA.find("tr:eq(1) td:eq(3)").html(velocityData.pipe3Data);
        $tableA.find("tr:eq(1) td:eq(4)").html(velocityData.pipe4Data);

        $tableA.find("tr:eq(2) td:eq(1)").html(velocityDataForRelative.pipe1Data);
        $tableA.find("tr:eq(2) td:eq(2)").html(velocityDataForRelative.pipe2Data);
        $tableA.find("tr:eq(2) td:eq(3)").html(velocityDataForRelative.pipe3Data);
        $tableA.find("tr:eq(2) td:eq(4)").html(velocityDataForRelative.pipe4Data);

        $tableA.find("tr:eq(3) td:eq(2)").html(densityData.pipe1Data);
        $tableA.find("tr:eq(3) td:eq(3)").html(densityData.pipe2Data);
        $tableA.find("tr:eq(3) td:eq(4)").html(densityData.pipe3Data);
        $tableA.find("tr:eq(3) td:eq(5)").html(densityData.pipe4Data);

        $tableA.find("tr:eq(4) td:eq(1)").html(densityDataForRelative.pipe1Data);
        $tableA.find("tr:eq(4) td:eq(2)").html(densityDataForRelative.pipe2Data);
        $tableA.find("tr:eq(4) td:eq(3)").html(densityDataForRelative.pipe3Data);
        $tableA.find("tr:eq(4) td:eq(4)").html(densityDataForRelative.pipe4Data);
        // alert(tddom.html())
    }
}
function MillData(pipe1Data, pipe2Data, pipe3Data, pipe4Data) {
    this.pipe1Data = pipe1Data;
    this.pipe2Data = pipe2Data;
    this.pipe3Data = pipe3Data;
    this.pipe4Data = pipe4Data;
}

function getDensityDataFromMill(mill) {
    return new MillData(mill.pipe1.density, mill.pipe2.density, mill.pipe3.density, mill.pipe4.density);
}

function getVelocityDataFromMill(mill) {
    return new MillData(mill.pipe1.Velocity, mill.pipe2.Velocity, mill.pipe3.Velocity, mill.pipe4.Velocity);
}

function getMillDataFromAbsoluteToRelative(millData) {
    var avg = (millData.pipe1Data + millData.pipe2Data + millData.pipe3Data + millData.pipe4Data) / 4;
    return new MillData(millData.pipe1Data - avg, millData.pipe2Data - avg, millData.pipe3Data - avg, millData.pipe4Data - avg);
}

/**
 * 隐藏与显示曲线或柱状图
 * */
function changeDivShowHide($showDiv, hideDivArr) {
    $showDiv.css("display", "block");
    if (hideDivArr) {
        for (var x in hideDivArr) {
            var $tempHideDiv = hideDivArr[x];
            if ($tempHideDiv) {
                $tempHideDiv.css("display", "none");
            }
        }
    }
    /*监听左侧DIV的显示与隐藏事件，改变按钮的背景颜色
    获取三个btnId，根据当前显示的左侧图标类型的DIV的ID，进行判断
    如果是曲线则将曲线按钮的边框加宽，颜色加深*/
    changeBtnStyleByShowCurveOrBar($showDiv);
    /*如果当前显示的showDiv为曲线的图表
    * 1、重新加载曲线数据*/
    var currentGroup;
    currentGroup = findGroupByChartDivId($showDiv.attr("id"));
    if(currentGroup) {
        currentGroup.initCurveData();
    }
}

/**
 * 隐藏与显示曲线或柱状图,event参数
 * @param event
 */
function changeDivShowHideForEvent(event) {


    var $showDiv;
    var radio1Name = event.data.curveId + "Radio";
    var velocityOrDensityValue = $("input[type='radio'][name='" + radio1Name + "']:checked").val();
    if (velocityOrDensityValue === densityRadioValue) {
        $showDiv = event.data.densityChart;
    } else if (velocityOrDensityValue === velocityRadioValue) {
        $showDiv = event.data.velocityChart;
    }
    var divArrs = event.data.divArrs;
    // var $showDiv = event.data.showDiv;
    var hideDivArr = divArrs.concat();
    hideDivArr.splice($.inArray($showDiv, hideDivArr), 1);

    changeDivShowHide($showDiv, hideDivArr);

}


/**
 * 在每个曲线图后面添加按钮
 * 曲线图、相对柱图、绝对柱图，这三个按钮分别用于切换图表显示
 * 2018年7月5日，添加“浓度”与“风速”两个选择按钮
 * 2018年7月6日，添加“A  B  C  D"复选框
 * @param curveId
 */
function divAddBtnAndBindClickFunction(curveId) {

    var $container = $(curveId);
    var $containerRelativeBarDiv = $(curveId + "_relativeBar");
    var $containerAbsoluteBarDiv = $(curveId + "_absoluteBar");
    var $containerV = $(curveId + "V");
    var $containerVRelativeBarDiv = $(curveId + "V" + "_relativeBar");
    var $containerVAbsoluteBarDiv = $(curveId + "V" + "_absoluteBar");

    var divArrs = [$container, $containerRelativeBarDiv, $containerAbsoluteBarDiv, $containerV, $containerVRelativeBarDiv, $containerVAbsoluteBarDiv];


    var baseLeft = $container.position().left + $container.width() + 10;
    var top = $container.position().top;

    //添加风速与浓度两个按钮
    var radioLabelForVelocityTop = top + 140 + "px";
    var radioLabelForDensityTop = top + 180 + "px";
    var radioLabelForVelocityLeft = baseLeft + "px";
    var $velocityRadio = AddDensityAndVelocityRadio($container, curveId, "VelocityRadio", "速度", velocityRadioValue, radioLabelForVelocityLeft, radioLabelForVelocityTop, false);
    var $densityRadio = AddDensityAndVelocityRadio($container, curveId, "DensityRadio", "密度", densityRadioValue, radioLabelForVelocityLeft, radioLabelForDensityTop, true);
    var radio1Name = curveId + "Radio";
    $("input[type='radio'][name='" + radio1Name + "']").bind("change", function () {
        //根据当前显示的图表类型，显示对应的风速的图表
        /*
         1、判定当前选中状态，如果处于
         2、判定当前图表类型
         2、
         */
        var radioValue = $(this).val();
        //判断当前按钮被选择
        var currentGroup = findGroupByCurveId(curveId);
        var currentBtn = currentGroup.findCurrentBtn();
        //改变div的显示与隐藏
        var showDiv = currentGroup.findTargetDivByBtn(currentBtn, radioValue, divArrs);
        if (showDiv) {
            var hideDivArr = divArrs.concat();
            hideDivArr.splice($.inArray(showDiv, hideDivArr), 1);
            changeDivShowHide(showDiv, hideDivArr);
        }
    });


    //添加ABCD四个复选框
    var checkBoxALeft = baseLeft + "px";
    var checkBoxCLeft = checkBoxALeft;
    var checkBoxBLeft = baseLeft + 50 + "px";
    var checkBoxDLeft = checkBoxBLeft;

    var checkBoxATop = top + 220 + "px";
    var checkBoxBTop = checkBoxATop;
    var checkBoxCTop = top + 260 + "px";
    var checkBoxDTop = checkBoxCTop;

    var checkBoxName = curveId + "Checkbox";

    var $ACheckBox = addCheckBox($container, curveId, checkBoxName, "checkBoxIdA", "A", checkBoxALeft, checkBoxATop);
    var $BCheckBox = addCheckBox($container, curveId, checkBoxName, "checkBoxIdB", "B", checkBoxBLeft, checkBoxBTop);
    var $CCheckBox = addCheckBox($container, curveId, checkBoxName, "checkBoxIdC", "C", checkBoxCLeft, checkBoxCTop);
    var $DCheckBox = addCheckBox($container, curveId, checkBoxName, "checkBoxIdD", "D", checkBoxDLeft, checkBoxDTop);

    //柱状图与曲线图切换按钮
    var btnLeft = baseLeft + "px";
    var top_relativeBar = top;
    var top_absoluteBar = top + 40 + "px";
    var top_curve = top + 80 + "px";


    var btn1Id = curveId + "CurveBtn";
    var btn2Id = $containerRelativeBarDiv.attr("id") + "Btn";
    var btn3Id = $containerAbsoluteBarDiv.attr("id") + "Btn";
    /* var showAndHideData1 = getShowAndHideDataFunction(divArrs, {"curveId":curveId,"densityChart":$container,"velocityChart":$containerV});
     var showAndHideData2 = getShowAndHideDataFunction(divArrs, {"curveId":curveId,"densityChart":$containerRelativeBarDiv,"velocityChart":$containerVRelativeBarDiv});
     var showAndHideData3 = getShowAndHideDataFunction(divArrs, {"curveId":curveId,"densityChart":$containerAbsoluteBarDiv,"velocityChart":$containerVAbsoluteBarDiv});*/
    var showAndHideData1 = {
        "divArrs": divArrs,
        "curveId": curveId,
        "densityChart": $container,
        "velocityChart": $containerV
    };
    var showAndHideData2 = {
        "divArrs": divArrs,
        "curveId": curveId,
        "densityChart": $containerRelativeBarDiv,
        "velocityChart": $containerVRelativeBarDiv
    };
    var showAndHideData3 = {
        "divArrs": divArrs,
        "curveId": curveId,
        "densityChart": $containerAbsoluteBarDiv,
        "velocityChart": $containerVAbsoluteBarDiv
    };
    var $curveBtn = AddChangeBarAndCurveBtn($container, btn1Id, "曲线图", showAndHideData1, btnLeft, top_curve);
    var $relativeBtn = AddChangeBarAndCurveBtn($container, btn2Id, "相对柱图", showAndHideData2, btnLeft, top_relativeBar);
    var $absoluteBtn = AddChangeBarAndCurveBtn($container, btn3Id, "绝对柱图", showAndHideData3, btnLeft, top_absoluteBar);


    //根据curveId查找gourpArr，查找到对应的group
    var currentGroup = findGroupByCurveId(curveId);
    if (currentGroup) {
        currentGroup.curveBtn = $curveBtn;
        currentGroup.barAbsoluteBtn = $absoluteBtn;
        currentGroup.barRelativeBtn = $relativeBtn;

        currentGroup.densityRadio = $densityRadio;
        currentGroup.velocityRadio = $velocityRadio;

        currentGroup.ACheckBox = $ACheckBox;
        currentGroup.BCheckBox = $BCheckBox;
        currentGroup.CCheckBox = $CCheckBox;
        currentGroup.DCheckBox = $DCheckBox;
    }
}


/**
 * 添加复选框 ,并返回
 * @param $container
 * @param curveId
 * @param checkBoxName
 * @param checkBoxId
 * @param checkBoxValue
 * @param checkBoxLeft
 * @param checkBoxTop
 * @returns {*|jQuery|HTMLElement}
 */

function addCheckBox($container, curveId, checkBoxName, checkBoxId, checkBoxValue, checkBoxLeft, checkBoxTop) {
    var checkBoxTargetId = curveId + checkBoxId;
    var $checkBox = $("<input type=\"checkbox\" name=\"" + checkBoxName + "\" id=\"" + checkBoxTargetId + "\" value=\"" + checkBoxValue + "\"  >");
    $checkBox.css({left: checkBoxLeft, top: checkBoxTop});
    var $checkBoxLabel = $("<label for='" + checkBoxTargetId + "' style='height:35px;width:20px;text-indent:0px;line-height:35px;'>" + checkBoxValue + "</label>");
    // console.log(checkBoxLeft)
    $container.after($checkBox);
    var checkBoxLabelLeft = $checkBox.position().left + 40 + "px";
    // console.log(checkBoxLabelLeft)
    //checkbox的top与label的top相同
    $checkBoxLabel.css({position: "absolute", left: checkBoxLabelLeft, top: checkBoxTop});
    $container.after($checkBoxLabel);

    $checkBox.attr("checked", "true");
    /*        $checkBox.attr("readonly","true");
            $checkBoxLabel.attr("enable","false");*/

    return $checkBox;
}

/**
 * 添加按钮，并返回按钮的jquery对象
 * @param $container
 * @param btnId
 * @param btnName
 * @param showAndHideData
 * @param btnLeft
 * @param btnTop
 * @returns {*|jQuery|HTMLElement}
 *
 */
function AddChangeBarAndCurveBtn($container, btnId, btnName, showAndHideData, btnLeft, btnTop) {
    var $curveBtn = $("<input type='button' id='" + btnId + "' value='" + btnName + "' class='changeBtnClass' />");
    $curveBtn.on("click", showAndHideData, changeDivShowHideForEvent);

    $curveBtn.css({left: btnLeft, top: btnTop});
    $container.after($curveBtn);
    return $curveBtn;
}

/**
 * 添加“密度”与“风速”的单选框
 * @param $container    曲线容器
 * @param curveId       曲线ID
 * @param radioType     "VelocityRadio"或"DensityRadio"
 * @param labelName     "风速","密度"
 * @param radioValue
 * @param radioLeft
 * @param radioTop
 * @param checked
 * @returns {*|jQuery|HTMLElement}
 */
function AddDensityAndVelocityRadio($container, curveId, radioType, labelName, radioValue, radioLeft, radioTop, checked) {
    var radioName = curveId + "Radio";
    var radioId = curveId + radioType;
    // var densityRadioId = curveId + "DensityRadio";
    var $labelForVelocity = $("<label for='" + radioId + "' class='radio_label '>" + labelName + "</label>");
    var $radioForVelocity = $("<input type=\"radio\" value=\"" + radioValue + "\"  id=\"" + radioId + "\" name=\"" + radioName + "\" />");
    $radioForVelocity.css("display", "none");
    $labelForVelocity.append($radioForVelocity);
    $labelForVelocity.css({left: radioLeft, top: radioTop});
    $container.after($labelForVelocity);
    if (checked) {
        $radioForVelocity.attr("checked", true);
        $labelForVelocity.addClass("checked");
        // console.log($radioForVelocity.attr("checked"))
    }

    return $radioForVelocity;
}

function freshMainPage(){
    var latestTime = 100;
    var url = "getAllMillRealTimeData";
    $.get(url, {"latestTime": latestTime}, function (result) {
        freshCurrentChartAndTable(result);
    }, "json");
}
$(document).ready(function () {
    //初始化Group
    initGroup();

    var containersIdArr = ["#container1", "#container2", "#container3", "#container4"];
    for (var x in containersIdArr) {
        var curveId = containersIdArr[x];
        // 每个曲线图线面添加按钮，并给按钮绑定点击事件
        divAddBtnAndBindClickFunction(curveId);
        var $showDiv = $(curveId + "_relativeBar");
        var $hideDiv1 = $(curveId + "_absoluteBar");
        var $hideDiv2 = $(curveId);

        var $hideDiv3 = $(curveId + "V");
        var $hideDiv4 = $(curveId + "V" + "_relativeBar");
        var $hideDiv5 = $(curveId + "V" + "_absoluteBar");
        //默认显示相对柱图，隐藏绝对柱图与曲线图
        changeDivShowHide($showDiv, [$hideDiv1, $hideDiv2, $hideDiv3, $hideDiv4, $hideDiv5]);
    }


    //给所有的单选按钮点击添加处理
    // language=JQuery-CSS
    $("input[type='radio']").click(function () {
        //找出和当前name一样的单选按钮对应的label，并去除选中的样式的class
        $("input[type='radio'][name='" + $(this).attr('name') + "']").parent().removeClass("checked");
        //给自己对应的label
        $(this).parent().addClass("checked");
    });

    //刷新与初始化图表与表格数据
    freshMainPage();
    //定时刷新当前显示的图表
    //定时刷新表格数据
    setInterval(freshMainPage, 5000);
});

