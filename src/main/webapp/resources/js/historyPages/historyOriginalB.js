function getQueryUrl(){
    return "../getMillHistoryOriginalDataWithMill?mill=B";
}
$(document).ready(function(){

    $("#pipe1").bind("click",function(){
        var millLocation = "B",pipeId=21;
        openPipeXYPage(millLocation,pipeId);
    });
    $("#pipe2").bind("click",function(){
        var millLocation = "B",pipeId=22;
        openPipeXYPage(millLocation,pipeId);
    });
    $("#pipe3").bind("click",function(){
        var millLocation = "B",pipeId=23;
        openPipeXYPage(millLocation,pipeId);
    });
    $("#pipe4").bind("click",function(){
        var millLocation = "B",pipeId=24;
        openPipeXYPage(millLocation,pipeId);
    });
});