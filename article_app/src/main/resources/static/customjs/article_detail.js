
function like(){
    var isLiked = $("#like").attr("value")
    var uId = $.cookie("uId")
    var oId = $("#main").attr("value")
    if(isLiked == "true"){ //取消点赞
        $.post("http://localhost:8080/detail/unlike",{
                "oId":oId,
                "uId":uId
            },function(data){
                if(data["code"]=='1000'){
                    $("#like").text("点赞")
                    var number = parseInt($("#like_number").text())
                    $("#like_number").text(number-1)
                    alert("点赞成功!")
                } else {
                    alert(data["info"])
                }
            },"json")
    } else{ //点赞
        $.post("http://localhost:8080/detail/like",{
                "oId":oId,
                "uId":uId
            },function(data){
                if(data["code"]=='1000'){
                    $("#like").text("取消点赞")
                    var number = parseInt($("#like_number").text())
                    $("#like_number").text(number+1)
                    alert("点赞成功!")
                } else {
                    alert(data["info"])
                }
            },"json")
    }

}

function clickComment(){
    $("#comment_area").show()
}

function submitComment(){
    var comm = $("#comment_text").val()
    var uId = $.cookie("uId")
    var oId = $("#main").attr("value")
    var title = $("#detail_title").text()
    $.post("http://localhost:8080/detail/comment",{
        "uId":uId,
        "oId":oId,
        "comment":comm
    },function(data){
        if(data["code"]=='1000'){
            window.location="http://localhost:8080/a/"+oId+"?title="+title
        } else {
            alert(data["info"])
        }
    },"json")
}