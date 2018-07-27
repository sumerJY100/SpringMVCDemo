$(document).ready(function () {
    var curveChart = getChart("container","密度","");
    var relativeBar = getRelativeBar("container_relativeBar","密度","相对值");
    var absoluteBar = getAbsoluteBar("container_absoluteBar","密度","绝对值");

    var volocityCurveChart = getChart("container_V","风速","");
    var volocityRrelativeBar = getRelativeBar("container_V_relativeBar","风速","相对值");
    var volocityAbsoluteBar = getAbsoluteBar("container_V_absoluteBar","风速","绝对值");

})