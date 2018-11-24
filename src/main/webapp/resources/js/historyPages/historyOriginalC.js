function getQueryUrl(){
    return "../getMillHistoryOriginalDataWithMill?mill=C";
}
$(document).ready(function(){

    $("#pipe1").bind("click",function(){
        var millLocation = "C",pipeId=31,millId=30;
        openPipeXYPage(millLocation,pipeId,millId);
    });
    $("#pipe2").bind("click",function(){
        var millLocation = "C",pipeId=32,millId=30;
        openPipeXYPage(millLocation,pipeId,millId);
    });
    $("#pipe3").bind("click",function(){
        var millLocation = "C",pipeId=33,millId=30;
        openPipeXYPage(millLocation,pipeId,millId);
    });
    $("#pipe4").bind("click",function(){
        var millLocation = "C",pipeId=34,millId=30;
        openPipeXYPage(millLocation,pipeId,millId);
    });
});