function getQueryUrl(){
    return "../getMillHistoryOriginalDataWithMill?mill=A";
}
$(document).ready(function(){

    $("#pipe1").bind("click",function(){
        var millLocation = "A",pipeId=11;
        openPipeXYPage(millLocation,pipeId);
    });
    $("#pipe2").bind("click",function(){
        var millLocation = "A",pipeId=12;
        openPipeXYPage(millLocation,pipeId);
    });
    $("#pipe3").bind("click",function(){
        var millLocation = "A",pipeId=13;
        openPipeXYPage(millLocation,pipeId);
    });
    $("#pipe4").bind("click",function(){
        var millLocation = "A",pipeId=14;
        openPipeXYPage(millLocation,pipeId);
    });
});