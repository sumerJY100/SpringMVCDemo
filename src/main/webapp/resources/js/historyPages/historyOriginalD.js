function getQueryUrl(){
    return "../getMillHistoryOriginalDataWithMill?mill=D";
}
$(document).ready(function(){

    $("#pipe1").bind("click",function(){
        var millLocation = "D",pipeId=41;
        openPipeXYPage(millLocation,pipeId);
    });
    $("#pipe2").bind("click",function(){
        var millLocation = "D",pipeId=42;
        openPipeXYPage(millLocation,pipeId);
    });
    $("#pipe3").bind("click",function(){
        var millLocation = "D",pipeId=43;
        openPipeXYPage(millLocation,pipeId);
    });
    $("#pipe4").bind("click",function(){
        var millLocation = "D",pipeId=44;
        openPipeXYPage(millLocation,pipeId);
    });
});