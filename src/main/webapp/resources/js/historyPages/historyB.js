function getQueryUrl() {
    var $forceSetMillValue = $("#forceSetMillValue");
    var url = "../getMillHistoryDataWithMill?mill=B";
    if ($forceSetMillValue[0].checked === true) {
        var millValue = $("#millValue").val();
        url = url + "&millValue=" + millValue;
    }


    return url;
}