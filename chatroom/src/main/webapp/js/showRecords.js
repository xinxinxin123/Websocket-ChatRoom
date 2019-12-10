/*
 * 聊天记录前台
 */

var recd = undefined;
var i = 0;
var c = 0;
var e = 0;
var z = 0;
var d = 0;
var flag = 0;
$(document).ready(function(){
	$("#rec").click(function(){
		$.ajax({url:"AServlet",data:{staffId:staffId,targetId:targetId},
			success:function(data,status){
			recd = JSON.parse(data);
			i = 0;
			c = 0;
			flag = 0;
			d = recd.length%10;
			if(d==0){
				z = recd.length/10;
			}
			else{
				z = Math.ceil(recd.length/10);
				flag = 1;
			}
			var b = $(".pagination");
			b.empty();
			var pageno = "<ul><li><a href='javascript:void(0)' onclick='show(-1)'><</a></li></ul>";
			for(var x = 1;x<=z;x++){
				pageno+="<ul><li><a href='javascript:void(0)' onclick='show("+ x +")'>"+ x +"</a></li></ul>";
			}
			pageno+="<ul><li><a href='javascript:void(0)' onclick='show(0)'>></a></li></ul>";
			b.append(pageno);
			$('.span4').append("<button class='btn' onclick='closeSpan()'>关闭</button>");
			show(1);
		}});
	});
	
});

//显示对应页码内容
function show(number){
	if(number==-1){
		number = e-1;
		if(0 == number){
			$("#tipsContent").text("已到首页");
			$("#tips").modal('show');
			number = 1;
		}
	}
	else if(number==0){
		number = e+1;
		if((z+1) == number){
			$("#tipsContent").text("已到尾页");
			$("#tips").modal('show');
			number = z;
		}
	}
	e = number;
	var a = $("#records");
	var p = 0;
	a.empty();
	//i++;
	$("#page").html("第 "+number+" 页");
	if(number==z && flag ==1){
		p = (z-1)*10;
		for(var j = d-1;j>=0;j--){
			if(recd[p+j].type == "I"){
				a.append("<div><img src="+ recd[p+j].headUrl + " class=\"img-circle\"><span class=\"label label-info\">"+recd[p+j].staffName+"</span>"+new Date(recd[p+j].createdDt).pattern("yyyy-MM-dd EEE hh:mm:ss")+"<div class=\"amessage\" id=\"singleRec"+j+"\"><img id=\"receive_img\" src=\""+recd[p+j].content+"\"></div><br></div>");
				
			}else if(recd[p+j].type == "W"){
				recd[p+j].staffName += "(来自微信端)";
				a.append("<div><img src="+ recd[p+j].headUrl + " class=\"img-circle\"><span class=\"label label-info\">"
						+recd[p+j].staffName+"</span>"+new Date(recd[p+j].createdDt).pattern("yyyy-MM-dd EEE hh:mm:ss")+"<div class=\"amessage\"><pre class=\"prettyprint\">"+recd[p+j].content+"</pre></div></div>");
			}
			else{
				a.append("<div><img src="+ recd[p+j].headUrl + " class=\"img-circle\"><span class=\"label label-info\">"+recd[p+j].staffName+"</span>"+new Date(recd[p+j].createdDt).pattern("yyyy-MM-dd EEE hh:mm:ss")+"<div class=\"amessage\" id=\"singleRec"+j+"\"><pre class=\"prettyprint\"id=\"content"+count+"\"></pre></div><br></div>");
				
			   $("#content"+j).text(recd[p+j].content);
				
				
				$jq("#singleRec"+j).replaceface($("#singleRec"+j).html());
			}
			$("#records").animate({
		          scrollTop: $("#records").last().outerHeight()
		    });
		}
	}
	else{
		p = number*10-1;
		for(c in recd){
			if(recd[p].type == "I"){
				a.append("<div><img src="+ recd[p].headUrl + " class=\"img-circle\"><span class=\"label label-info\">"+recd[p].staffName+"</span>"+new Date(recd[p].createdDt).pattern("yyyy-MM-dd EEE hh:mm:ss")+"<div class=\"amessage\" id=\"singleRec"+p+"\"><img id=\"receive_img\" src=\""+recd[p].content+"\"></div><br></div>");
			}else if(recd[p].type == "W"){
				recd[p].staffName += "(来自微信端)";
				a.append("<div><img src="+ recd[p].headUrl + " class=\"img-circle\"><span class=\"label label-info\">"
						+recd[p].staffName+"</span>"+new Date(recd[p].createdDt).pattern("yyyy-MM-dd EEE hh:mm:ss")+"<div class=\"amessage\"><pre class=\"prettyprint\">"+recd[p].content+"</pre></div></div>");
			}
			else{
				a.append("<div><img src="+ recd[p].headUrl + " class=\"img-circle\"><span class=\"label label-info\">"+recd[p].staffName+"</span>"+new Date(recd[p].createdDt).pattern("yyyy-MM-dd EEE hh:mm:ss")+"<div class=\"amessage\" id=\"singleRec"+p+"\"><pre class=\"prettyprint\"id=\"content"+count+"\"></pre></div><br></div>");
				$("#content"+p).text(recd[p].content);
				$jq("#singleRec"+p).replaceface($("#singleRec"+p).html());
			}
			$("#records").animate({
		          scrollTop: $("#records").last().outerHeight()
		    });
        	p--;
			if(c==9){
				c++;
				break;
			}
		}
	}
}
