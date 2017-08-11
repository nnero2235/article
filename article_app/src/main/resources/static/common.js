var page = 2;
function load_more(){
    var http = new XMLHttpRequest();
    http.open("GET","/goods/more?page="+page,true);
    http.onreadystatechange=function(){
        if (http.readyState==4 && http.status == 200){
            voList = JSON.parse(http.responseText);
            for(var i=0;i < voList.length;i++){
                var tr = document.createElement("tr");
                var th = document.createElement("th");
                th.setAttribute("scope","row")
                th.innerHTML = voList[i]["id"];
                tr.appendChild(th)
                var td = document.createElement("td");
                td.innerHTML = voList[i]["block_type"];
                tr.appendChild(td)
                td = document.createElement("td");
                td.innerHTML = voList[i]["title"];
                td.setAttribute("class","col-md-3")
                tr.appendChild(td)
                td = document.createElement("td");
                td.innerHTML = voList[i]["sub_data_size"];
                tr.appendChild(td)
                td = document.createElement("td");
                td.innerHTML = voList[i]["lastModify"];
                tr.appendChild(td)

                td = document.createElement("td");
                td.appendChild(createButton(voList[i]["id"],"编辑"))
                td.appendChild(createButton(voList[i]["id"],"删除"))
                tr.appendChild(td);
                document.getElementById("table_body").appendChild(tr);
            }
            if(voList.length < 5){
                var btn = document.getElementById("btn_load_more");
                btn.setAttribute("disabled","disabled");
                btn.innerHTML="没有更多了";
            }
        }
    }
    http.send();
    page++;
}

function createButton(id,text){
    var a = document.createElement("a");
    var button = document.createElement("button");
    if("编辑" == text){
        a.setAttribute("href","/goods/edit/"+id);
        button.setAttribute("class","btn btn-primary");
    } else {
        a.setAttribute("href","/goods/delete/"+id);
        button.setAttribute("class","btn btn-danger button-padding");
    }
    button.innerHTML = text;
    a.appendChild(button);
    return a;
}