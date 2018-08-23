$(document).ready(function () {
    /** ***************************************画图************************************************************* */
    $("#linkStateSvg").svg({
        onLoad: drawIntro
    });


});
var rectSettings = {
    fill: "#F0F0F0",
    stroke: "#000000",
    strokeWidth: 1
};

function drawRect(svg, locationX, locationY, width, height) {
    var tempRectSvg = svg.rect(locationX, locationY, width, height, rectSettings);
    return tempRectSvg;
}

var location_top = "top", location_bottom = "bottom", location_left = "left", location_right = "right";

function drawLineBewteenRect(svg, rectFrom, fromLocation, rectTo, toLocation) {
    var fromX, fromY, toX, toY;
    var rectFromAttr = rectFrom.attributes,
        rectToAttr = rectTo.attributes;
    var rectFromX = parseInt(rectFromAttr.x.value),
        rectFromY = parseInt(rectFromAttr.y.value),
        rectFromWidth = parseInt(rectFromAttr.width.value),
        rectFromHeight = parseInt(rectFromAttr.height.value);
    var rectToX = parseInt(rectToAttr.x.value),
        rectToY = parseInt(rectToAttr.y.value),
        rectToWidth = parseInt(rectToAttr.width.value),
        rectToHight = parseInt(rectToAttr.height.value);
    if (fromLocation === location_top) {
        fromX = rectFromX + rectFromWidth / 2;
        fromY = rectFromY;
    } else if (fromLocation === location_bottom) {
        fromX = rectFromX + rectFromWidth / 2;
        fromY = rectFromY + rectFromHeight;
    } else if (fromLocation === location_left) {
        fromX = rectFromX;
        fromY = rectFromY + rectFromHeight / 2;
    } else if (fromLocation === location_right) {
        fromX = rectFromX + rectFromWidth;
        fromY = rectFromY + rectFromHeight / 2;
    }
    if (toLocation === location_top) {
        toX = rectToX + rectFromWidth / 2;
        toY = rectToY;
    } else if (toLocation === location_bottom) {
        toX = rectToX + rectToWidth / 2;
        toY = rectToY + rectToHight;
    } else if (toLocation === location_left) {
        toX = rectToX;
        toY = rectToY + rectFromHeight / 2;
    } else if (toLocation === location_right) {
        toX = rectToX + rectToWidth;
        toY = rectToY + rectToHight / 2;
    }
    // var svgLine =  svg.line(fromX   , fromY, toX, toY, {
    //     stroke : "green",
    //     strokeWidth : "2px"
    // });
    return {"fromX":fromX,"fromY":fromY,"toX":toX,"toY":toY};

}
function drawLinesBetweenMillAndComputer(svg, rectFrom, fromLocation, rectTo, toLocation){
    var location = drawLineBewteenRect(svg, rectFrom, fromLocation, rectTo, toLocation);
    var fromHeight = 20;
    var point1 = [location.fromX, location.fromY];
    var point2 = [location.fromX, location.fromY - fromHeight];
    var point3 = [location.toX, location.fromY - fromHeight];
    var point4 = [location.toX, location.toY];

    return drawCommonPolyLine(svg,[point1,point2,point3,point4]);
}
function drawCommonPolyLine(svg,pointsArr){
    return svg.polyline(pointsArr, {
        "fill": "white",
        "fill-opacity": 0,
        "stroke": "green",
        "strokeWidth": "2px"
    });
}

function drawLinesBetweenDCSAndComputer(svg, rectFrom, fromLocation, rectTo, toLocation){
    var location = drawLineBewteenRect(svg, rectFrom, fromLocation, rectTo, toLocation);
    var point1 = [location.fromX,location.fromY];
    var point2 = [location.toX,location.fromY];
    var point3 = [location.toX,toLocation.toY];
    return drawCommonPolyLine(svg,[point1,point2,point3]);

}
var rectHeight = 120, rectWidth = 80, rectInterval = 30;

function drawMillPiping(svg, initX, initY) {
    var millAPipe1Rect = drawRect(svg, initX, initY, rectWidth, rectHeight);
    var millAPipe2Rect = drawRect(svg, initX + rectInterval + rectWidth, initY, rectWidth, rectHeight);
    var millAPipe3Rect = drawRect(svg, initX + (rectInterval + rectWidth) * 2, initY, rectWidth, rectHeight);
    var millAPipe4Rect = drawRect(svg, initX + (rectInterval + rectWidth) * 3, initY, rectWidth, rectHeight);

    return [millAPipe1Rect, millAPipe2Rect, millAPipe3Rect, millAPipe4Rect];
}

function drawLines(svg, millRects, targetRect) {
    for (var i = 0; i < millRects.length; i++) {
        var fromRect = millRects[i];
        drawLinesBetweenMillAndComputer(svg, fromRect, location_top, targetRect, location_bottom);
    }

}

function drawComputer(svg) {
    var rectSettingWhite = {
        fill: "white",
        stroke: "black",
        strokeWidth: "2px"
    };
    var rectSetting = {fill: "black"};

    var computerLocationX = (millCDLeft + (rectWidth + rectInterval) * 4 - millABLeft) / 2 + millABLeft - 40;
    var computerLocationY = epCommunicationStateMargintop + 30 - 120;
    var computerWidth = 80;
    var computerHeight = 50;
    svg.rect(computerLocationX, computerLocationY, computerWidth, computerHeight, {
        stroke: "black",
        strokeWidth: "2px",
        fill: "none"
    });

    var computerLocation1X = computerLocationX;
    var computerLocation1Y = computerLocationY + computerHeight - 5;
    svg.rect(computerLocation1X, computerLocation1Y, computerWidth, 5, {
        stroke: "black",
        strokeWidth: "2px"
    });

    var computerViewSwitchLocationX = computerLocationX + computerWidth - 5;
    var computerViewSwitchLocationY = computerLocationY + computerHeight - 2;
    svg.circle(computerViewSwitchLocationX, computerViewSwitchLocationY, 1.5, {
        stroke: "white",
        strokeWidth: "1px",
        fill: "white"
    });

    var computerViewHolderWidth = 20;
    var computerViewHolderHeight = 10;
    var computerViewHolderLocationX = computerLocationX + computerWidth / 2 - computerViewHolderWidth / 2;
    var computerViewHolderLocationY = computerLocationY + computerHeight;
    svg.rect(computerViewHolderLocationX, computerViewHolderLocationY, computerViewHolderWidth, computerViewHolderHeight, {
        stroke: "black",
        fill: "black"
    });

    var computerViewHolderEllipseHeight = 6;
    var computerViewHolderEllipseLocationX = computerLocationX + computerWidth / 2;
    var computerViewHolderEllipseLocationY = computerLocationY + computerHeight + computerViewHolderHeight;
    svg.ellipse(computerViewHolderEllipseLocationX, computerViewHolderEllipseLocationY, 20, computerViewHolderEllipseHeight, rectSetting);

    var computerCaseWidth = 80;
    var computerCaseLocationX = computerLocationX;
    var computerCaseLocationY = computerViewHolderEllipseLocationY + computerViewHolderEllipseHeight + 5;
    var rect = svg.rect(computerCaseLocationX, computerCaseLocationY, computerCaseWidth, 30, 3, 3, rectSettingWhite);

    var computerCase1Height = 25;
    var computerCase1LocationX = computerLocationX + computerCaseWidth - 15;
    var computerCase1LocationY = computerCaseLocationY + 2.5;
    svg.rect(computerCase1LocationX, computerCase1LocationY, 10, computerCase1Height, rectSetting);
    svg.rect(computerCase1LocationX - 8, computerCase1LocationY, 4, computerCase1Height, rectSetting);
    svg.rect(computerCase1LocationX - 8 * 2, computerCase1LocationY, 4, computerCase1Height, rectSetting);
    svg.rect(computerCase1LocationX - 8 * 3, computerCase1LocationY, 4, computerCase1Height, rectSetting);

    var computerCaseSwitchLocationX = computerCase1LocationX - 40;
    var computerCaseSwitchLocationY = computerCase1LocationY + 12;
    svg.circle(computerCaseSwitchLocationX, computerCaseSwitchLocationY, 5, rectSettingWhite);

    return rect;
}
var epCommunicationStateMargintop = 120;
var millABLeft = 150,
    millACTop = 250,
    millCDLeft = 700,
    millBDTOP = 450;
var dcsX = millCDLeft + (rectWidth + rectInterval) * 2 ,
    dcsY = epCommunicationStateMargintop  + 10,
    dcsWidth = 120,
    dcsHeight = 50;

function drawIntro(svg) {
    var millARects = drawMillPiping(svg, millABLeft, millACTop);
    var millBRects = drawMillPiping(svg, millABLeft, millBDTOP);
    var millCRects = drawMillPiping(svg, millCDLeft, millACTop);
    var millDRects = drawMillPiping(svg, millCDLeft, millBDTOP);
    var DCSRect = drawRect(svg,dcsX,dcsY,dcsWidth,dcsHeight);
    var computerRect = drawComputer(svg);
    drawLines(svg, millARects, computerRect);
    drawLines(svg, millBRects, computerRect);
    drawLines(svg, millCRects, computerRect);
    drawLines(svg, millDRects, computerRect);
    drawLinesBetweenDCSAndComputer(svg,DCSRect,location_left,computerRect,location_bottom);


}




// function drawText(svg) {
//     var epLocationX = epCommunicationStateMarginLeft + (eachCommunicationStateRectWidth + eachCommunicationStateIntervalWidth) * 2;
//     var epLocationY = epCommunicationStateMargintop - 50;
//     svg.text(epLocationX, epLocationY, "高  压", {
//         fontFamily : "微软雅黑",
//         fill : "#000000",
//         fontSize : "20px",
//         fontWeight : "bolder"
//     });
//
//     var lowDeviceLocationX = lowDeviceCommunicationStateMarginLeft + (eachCommunicationStateRectWidth + eachCommunicationStateIntervalWidth) * 2;
//     svg.text(lowDeviceLocationX, epLocationY, "低  压", {
//         fontFamily : "微软雅黑",
//         fill : "#000000",
//         fontSize : "20px",
//         fontWeight : "bolder"
//     });
// }
