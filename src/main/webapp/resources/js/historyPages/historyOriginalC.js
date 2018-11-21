function getQueryUrl(){
    return "../getMillHistoryOriginalDataWithMill?mill=C";
}
$(document).ready(function(){

    $("#pipe1").bind("click",function(){
        var millLocation = "C",pipeId=31;
        openPipeXYPage(millLocation,pipeId);
    });
    $("#pipe2").bind("click",function(){
        var millLocation = "C",pipeId=32;
        openPipeXYPage(millLocation,pipeId);
    });
    $("#pipe3").bind("click",function(){
        var millLocation = "C",pipeId=33;
        openPipeXYPage(millLocation,pipeId);
    });
    $("#pipe4").bind("click",function(){
        var millLocation = "C",pipeId=34;
        openPipeXYPage(millLocation,pipeId);
    });
});