function getQueryUrl(){
    return "../getMillHistoryOriginalDataWithMill?mill=B";
}
$(document).ready(function(){

    $("#pipe1").bind("click",function(){
        var millLocation = "B",pipeId=21,millId=20;
        openPipeXYPage(millLocation,pipeId,millId);
    });
    $("#pipe2").bind("click",function(){
        var millLocation = "B",pipeId=22,millId=20;
        openPipeXYPage(millLocation,pipeId,millId);
    });
    $("#pipe3").bind("click",function(){
        var millLocation = "B",pipeId=23,millId=20;
        openPipeXYPage(millLocation,pipeId,millId);
    });
    $("#pipe4").bind("click",function(){
        var millLocation = "B",pipeId=24,millId=20;
        openPipeXYPage(millLocation,pipeId,millId);
    });
});