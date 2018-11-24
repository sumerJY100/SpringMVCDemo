function getQueryUrl(){
    return "../getMillHistoryOriginalDataWithMill?mill=D";
}
$(document).ready(function(){

    $("#pipe1").bind("click",function(){
        var millLocation = "D",pipeId=41,millId=40;
        openPipeXYPage(millLocation,pipeId,millId);
    });
    $("#pipe2").bind("click",function(){
        var millLocation = "D",pipeId=42,millId=40;
        openPipeXYPage(millLocation,pipeId,millId);
    });
    $("#pipe3").bind("click",function(){
        var millLocation = "D",pipeId=43,millId=40;
        openPipeXYPage(millLocation,pipeId,millId);
    });
    $("#pipe4").bind("click",function(){
        var millLocation = "D",pipeId=44,millId=40;
        openPipeXYPage(millLocation,pipeId,millId);
    });
});