function getQueryUrl(){
    return "../getMillHistoryOriginalDataWithMill?mill=A";
}
$(document).ready(function(){

    $("#pipe1").bind("click",function(){
        var millLocation = "A",pipeId=11,millId=10;
        openPipeXYPage(millLocation,pipeId,millId);
    });
    $("#pipe2").bind("click",function(){
        var millLocation = "A",pipeId=12,millId=10;
        openPipeXYPage(millLocation,pipeId,millId);
    });
    $("#pipe3").bind("click",function(){
        var millLocation = "A",pipeId=13,millId=10;
        openPipeXYPage(millLocation,pipeId,millId);
    });
    $("#pipe4").bind("click",function(){
        var millLocation = "A",pipeId=14,millId=10;
        openPipeXYPage(millLocation,pipeId,millId);
    });
});