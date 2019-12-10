/**
 * 聊天前台
 */

var ws = null;	//websocket对象
var targetId = 0;	//聊天对象ID
var staffId = 0;	//当前用户ID
var staffName = "";	//当前用户姓名
var power = "N";	//当前用户权限
var userCount = 0;	//当前在线用户数
var headUrl = "";	//当前用户头像
var count = 0;		//消息板消息数量
var cc = new Array(0,0);	//私聊未查看的消息数目
var recentMsg = new Array("","");	//最近微信消息
var state = "A";	//用户状态
var flag = 0;		//检查微信面板开闭状态
var wechatMsgs = "";	//未读微信消息
var wechatCount = 0;	//未读微信消息数目


//初始化方法
function startWebSocket() {
	// TODO cookie
	/*var usercookie = getCookie("username");
	if(null != usercookie && "\"\"" != usercookie){
	}
	else{
		logout("logout");
	}*/

    var localhost = window.location.host;

    if ('WebSocket' in window) {
        try {
            ws = new WebSocket("ws://"+localhost+"/websocket");
        } catch (e) {
            $("#tipsContent").text("建立连接失败");
            $("#tips").modal('show');
        }
    } else if ('MozWebSocket' in window) {
        ws = new MozWebSocket("ws://"+localhost+"/websocket");
    } else {
        $("#tipsContent").text("抱歉，您的浏览器不支持WebSocket");
        $("#tips").modal('show');
    }

    ws.onmessage = function(evt) {
        if("1" == evt.data){
            logout("othersLogin");
        }else if("kick" == evt.data){
            logout("kick");
        }else if("silence" == evt.data){
            state = "X";
            $("#tipsContent").text("你已经被管理员禁言！");
            $('#tips').modal('show');
            $('#send').attr("class","btn  btn-success disabled");
            $('#send').attr("onclick","");
            $('#sendkey').text("Ctrl+Enter你也发不了~");
        }else{
            say(evt.data);
        }
    };

    ws.onclose = function(evt) {
        //alert("服务器已断开!");
        //window.location.href = "logout.html";
    };
    ws.onopen = function(evt) {
        onOpen(evt);
    };
}

//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
window.onbeforeunload = function(){
    ws.close();
};

//建立webSocket连接时的方法
function onOpen(evt) {
	/*document.getElementById("chatBox").onkeydown = function(event) {
		if (13 == event.keyCode) {
			sendMsg();
		};
	};*/
}

function say(msg) {

	var firstChar = msg.substr(0, 1);
	var obj = JSON.parse(msg); 
	
	//1.普通类型的json 代表后台推送的一条消息
	if("{" == firstChar){
		//1.1 普通消息
		if(obj.messageType == 1){
			
			//1.1.1当前消息面板与消息显示面板符合
			if((obj.staffId == staffId && obj.targetId == targetId) 
					|| (obj.staffId == targetId && obj.targetId == staffId)
					|| (obj.targetId ==0 && targetId == 0)){
				
				count++;       
		        var a = $("#first").last();
				//1.1.1.1文本消息
				if(obj.type == "N" || obj.type == "W"){
			        a.append(" <div><img src="+ obj.headUrl + " class=\"img-circle\"><span class=\"label label-info\">"+obj.staffName+"</span>"+new Date(obj.createdDt).pattern("yyyy-MM-dd EEE hh:mm:ss")+"<div class=\"amessage\" id=\"singlemessage"+count+"\"><pre class=\"prettyprint\">"+obj.content+"</pre></div></div>");
					//获取文本并解析为表情
		        	$jq("#singlemessage"+count).replaceface($("#singlemessage"+count).html());
				}
				//1.1.1.2图片类型
				else if(obj.type == "I"){
					a.append(" <div><img src="+ obj.headUrl + " class=\"img-circle\"><span class=\"label label-info\">"+obj.staffName+"</span>"+new Date(obj.createdDt).pattern("yyyy-MM-dd EEE hh:mm:ss")+"<div class=\"amessage\" id=\"singlemessage"+count+"\"><img id=\"receive_img\" src="+obj.content+" /></div></div>");
				}
				//1.1.1.3禁言或踢人
				else if(obj.type == "X" || obj.type == "K" || obj.type == "U"){
					a.append("<div class='alert alert-info'><strong>系统提示：</strong>"+obj.content+"</div>");
				}else{
					$("#tipsContent").text("未知消息类型");
					$("#tips").modal('show');
				}
		        $("#form").animate({
		          scrollTop: $("#first").last().outerHeight()
		        });
			}
			//1.1.2.当前消息面板与消息显示面板不符合
			else{
				
				//这里添加在对方头像上显示消息数目，每发一条数目+1
				//1.1.2.1群聊消息
				if(obj.targetId == 0){
					cc[0] = cc[0] + 1;
					if(cc[0] == 1){
						$("#user"+0).append("<span class=\"badge badge-success\" id='mc"+0+"'>"+cc[0]+"</span>");
					}
					else{
						$("#mc"+0).html(cc[0]);
					}
				}
				//1.1.2.2私聊消息
				else{
					if(obj.type == "W"){
						if(flag == 1){
							var a = $("#wechats").last();
							a.append(" <div><img src="+ obj.headUrl + " class=\"img-circle\"><span class=\"label label-info\">"
									+obj.staffName+"</span>"+new Date(obj.createdDt).pattern("yyyy-MM-dd EEE hh:mm:ss")+"<div class=\"amessage\"><pre class=\"prettyprint\">"+obj.content+"</pre></div></div>");
						}else{
							wechatMsgs += " <div><img src="+ obj.headUrl + " class=\"img-circle\"><span class=\"label label-info\">"
							+obj.staffName+"</span>"+new Date(obj.createdDt).pattern("yyyy-MM-dd EEE hh:mm:ss")+"<div class=\"amessage\"><pre class=\"prettyprint\">"+obj.content+"</pre></div></div>";
							wechatCount++;
							if(wechatCount == 1){
								$("#extra").append("<code id='new'>"+wechatCount+"</code>");
							}else{
								$("new").html(wechatCount);
							}
							
						}
						
					}else{
						cc[obj.staffId] = cc[obj.staffId] + 1;
						if(cc[obj.staffId] == 1){
							$("#user"+obj.staffId).append("<span class=\"badge badge-success\" id='mc"+obj.staffId+"'>"+cc[obj.staffId]+"</span>");
						}
						else{
							$("#mc"+obj.staffId).html(cc[obj.staffId]);
						}
					}
				}
			}
		}
		//1.2 信号消息，取得并保存用户id,name和power
		else if(obj.messageType == 0){
			staffId = obj.id;
			staffName = obj.name;
			power = obj.power;
			state = obj.state;
			if("I" == state){
				logout();
			}
			if("X" == state){
				$("#tipsContent").text("你已经被管理员禁言！");
				$('#tips').modal('show');
				$('#send').attr("class","btn  btn-success disabled");
				$('#send').attr("onclick","");
				$('#sendkey').text("Ctrl+Enter你也发不了~");
			}
			if("S" == power){
				$('.nav').append('<li id="li3"><a href="javascript:void(0)"onclick="window.open(\'SuperManagement\')">超级管理</a></li>');
			}
			headUrl = obj.headUrl;
			document.getElementById("Label_username").innerHTML = "欢迎，"+staffName;
			$("#user0").attr("style","color:green");
			cc[staffId] = 0;
		}
		//1.3 上线通知消息
		else if(obj.messageType == 2 && obj.id != staffId){
			var a = $("#userlist");
			if($("#user_info"+obj.id).length <= 0){
				a.append("<div id=\"user_info"+obj.id+"\"><div class=\"div_img\"><img class='userHead img-circle' src="+obj.headUrl +" onclick=\"chat("+obj.id+")\"></div><div class=\"div_name\" id = \"user"+obj.id+"\">"+obj.name+"</div><p></p></div>");		
			}
			superman();
			cc[obj.id] = 0;
			userCount++;
			search();
		}
		//1.4 下线通知消息
		else if(obj.messageType == 3){
			if(obj.id != staffId){
				$("#user_info"+obj.id).remove();
				userCount--;
				search();
			}
		}
	}
	//2.数组类型的json、在线用户列表或者消息记录
	else if("[" == firstChar){
		//2.1.接受的json串为历史记录
		if(obj[0].messageType == 4){
			//对消息类型的处理
			var msg10 = "";
			var b = new Array();
			var c = 0;
			for (var i in obj) {
				count++;
				if("I" == obj[i].type){
					b[b.length] = i;
					msg10 += "<div><img src="+ obj[i].headUrl + " class=\"img-circle\"><span class=\"label label-info\">"+obj[i].staffName+"</span>"+new Date(obj[i].createdDt).pattern("yyyy-MM-dd EEE hh:mm:ss")
					+"<div class=\"amessage\" id=\"singlemessage"+count+"\"><img id=\"receive_img\" src="+obj[i].content+" /></div></div>";
				}else if("X" == obj[i].type||"K" == obj[i].type ||"U"==obj[i].type){
					msg10 += "<div class='alert alert-info' id=\"singlemessage"+count+"\"><strong>系统提示：</strong>"+obj[i].content+"</div>";
				}else if("W" == obj[i].type){
					msg10 += "<div><img src="+ obj[i].headUrl + " class=\"img-circle\"><span class=\"label label-info\">"
					+obj[i].staffName+"</span>"+new Date(obj[i].createdDt).pattern("yyyy-MM-dd EEE hh:mm:ss")+"<div class=\"amessage\" id=\"singlemessage"+count+"\"><pre class=\"prettyprint\">"+obj[i].content+"</pre></div></div>";
				}
				else{
					msg10 += "<div><img src="+ obj[i].headUrl + " class=\"img-circle\">" 
	    			+"<span class=\"label label-info\">"+obj[i].staffName
	    			+"</span>"+new Date(obj[i].createdDt).pattern("yyyy-MM-dd EEE hh:mm:ss")+"<div class=\"amessage\" id=\"singlemessage"
	    			+count+"\"><pre class=\"prettyprint\">"+obj[i].content+"</pre></div></div>"; 					
				}    	        	        
			}
			var a = $("#first").last();
			if("" != msg10){
				a.append(msg10);
			}
			for(i = c + 1; i <= count; i++){
				//获取文本并解析为表情
				$jq("#singlemessage"+i).replaceface($("#singlemessage"+i).html());
			}
	        $("#form").animate({
	          scrollTop: $("#first").last().outerHeight()
	        });
		}
		//2.2.接收的json串为用户列表信息
		else if(obj[0].messageType ==2){
			var a = $("#userlist");
			var users = "";
			for (var i in obj) {
				if(obj[i].id == staffId){
					users = "<div id=\"user_info"+obj[i].id+"\"><div class=\"div_img\"><img class='userHead img-circle' src="
					+obj[i].headUrl +" onclick=\"chat("
					+obj[i].id+")\"></div><div class=\"div_name\" id = \"user"
					+obj[i].id+"\">"+obj[i].name+"</div><p></p></div>" + users;
				}else{
					users +="<div id=\"user_info"+obj[i].id+"\"><div class=\"div_img\"><img class='userHead img-circle' src="
					+obj[i].headUrl +" onclick=\"chat("
					+obj[i].id+")\"></div><div class=\"div_name\" id = \"user"+obj[i].id+"\">"
					+obj[i].name+"</div><p></p></div>";			
				}
				userCount++;
			}
			a.append(users);
			superman();
		}
		//2.3json串不正确
		else{
			$("#tipsContent").text("未知json数组");
			$("#tips").modal('show');
		}
	}
	//3.消息类型不正确
	else{
		$("#tipsContent").text("消息类型不正确");
		$("#tips").modal('show');
	}
	
	while (10<$("#first").children().length) {
        $("#first").children().eq(0).remove();
	}
}

//点选头像选择聊天对象
function chat(who){
	closeSpan();
	var jsonMsg;
	cc[who] = 0;
	if("" != $.trim($("#newmessage").val())){
		recentMsg[targetId] = $("#newmessage").val();
	}
	$("#newmessage").val("");
	$("#newmessage").focus();
	if(recentMsg[who] != ""){
		$("#newmessage").val(recentMsg[who]);
	}
	$('#mc'+who).remove();
	$('#nowchat').text($('#user'+who).text());
	if(0 == who){
		$('#nowchat').text("群聊");
		jsonMsg = {
				"targetType":"initPublic",
				"targetId":"0"
		};
		targetType = "public";
	}
	else{
		jsonMsg = {
				"targetType":"initPrivate",	
				"targetId":who
		};
		targetType = "private";
	}
	$("#user"+targetId).attr("style","font-style:normal");
	$("#user"+who).attr("style","color:green");
	$("#first").children().remove();
	count = 0;

	ws.send(JSON.stringify(jsonMsg));	
	targetId = who;
}


//
function getCookie(name)
{
	var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
	if(arr=document.cookie.match(reg)) 
		return unescape(arr[2]);
	else return null;
}

//发送消息
function sendMessage(){
	var message = $.trim($("#newmessage").val());
    if (""==message) {
    	  $("#newmessage").val("");
          $("#newmessage").attr("placeholder","说点什么再点发送吧~");
          $("#newmessage").focus();
    }
    else{
    	recentMsg[targetId] = "";
    	var jsonMsg = {
    			"targetType":"send",
    			"message":message,
    			"type":"N",
    			"targetId":targetId
    	};
    	ws.send(JSON.stringify(jsonMsg));  	    
    	$("#newmessage").attr("placeholder","快来冒个泡吧~");
    	document.getElementById("newmessage").value="";    	   
  }    
}

//监听键盘Ctry+Enter发送消息
function keySend(event) {
  if (event.ctrlKey && event.keyCode == 13 && "A" == state){
    sendMessage();
  }
}

//退出登录
function logout(reason){
	url = "/usr/logout?reason="+reason;
	window.location.href = url;
}

//显示聊天记录面板
function showRec(){
	$(".span4").remove();
	$(".span7").attr("class","span7");
	$(".span2").attr("class","span1");
	$(".maincontent").append("<div class=\"span4\"><legend>聊天记录</legend><form id=\"form\"><div id=\"records\"></div><div id = \"page\"></div></form><div class=\"pagination\"></div></div>");
}

//关闭最右侧面板
function closeSpan(){
	$(".span4").remove();
	$(".span7").attr("class","span7");
	$(".span1").attr("class","span2 offset1");
	$("#li2").attr("class"," ");
	$("#li1").attr("class","active");
	flag = 0;
}

//显示群共享面板
function groupFile(){
	$(".span4").remove();
	$(".span7").attr("class","span7");
	$(".span2").attr("class","span1");
	$("#li1").attr("class"," ");
	$("#li2").attr("class","active");
	$(".maincontent").append("<div class=\"span4\"><legend>共享文件</legend><div id=\"form\"><div id=\"fileList\"></div></div></div>");
	$('.span4').append("<div id=\"test\"><input type=\"file\" id=\"file\" name=\"file\"></div><button class='btn' onclick=\"uploadFile()\">上传文件</button>");
	$('.span4').append("<button class='btn' onclick='closeSpan()'>关闭</button>");
	getFileList();
}

//查询在线用户
function search(){  
	var names = new Array();
	for(var i = 3;i <= userCount+2;i++){
		$("#userlist").children().eq(i).children().show();
	}
	for(var i = 3;i <= userCount+2;i++){
		names[names.length] = $("#userlist").children().eq(i).children().eq(1).text();
	}
    var searchValue = $("#search").val();   
    if(searchValue != ""){      
        var pos;
        var result = [];
        for(var i = 0; i < names.length; i++){
            var srch = names[i]||'';
            pos = find(searchValue, srch);
            if(pos>=0){
                result[result.length] = i+3;
            }
        }  
        for(var i = 3;i <= userCount+2;i++){
        	$("#userlist").children().eq(i).children().hide();
        	for(var j = 0; j<result.length ; j++){
        		if(result[j] == i){
        			$("#userlist").children().eq(i).children().show();
        		}
            }
    	}
    }
}

//用户输入的值和当前在线用户姓名做比较返回匹配位置
function find(searchValue, srch){  
    var nSize = searchValue.length;  
    var nLen = srch.length;   
    var sCompare;  
    if(nSize <= nLen ){  
        for(var i = 0; i <= nLen - nSize + 1; i++){  
            sCompare = srch.substring(i, i + nSize);  
            if(sCompare == searchValue){
                return i;  
            }  
        } 
    }
    return -1;  
}

//发送图片
function sendImage(){
	var file = $("#choose_image");
	if(isImage(file) != -1){
		uploadImage();
	}else{
		$("#tipsContent").text("请选择图片");
		$("#tips").modal('show');
	} 
}

//通过文件判断是否为图片
function isImage(file){
	var filename = file[0].value;
	var fileType = filename.substring(filename.lastIndexOf("."));
	var imageType =[".jpg",".png",".gif",".jpeg",".PNG",".JPG",".GIF",".JPEG"]; 
	return $.inArray(fileType,imageType);
}

function uploadImage(){
	// 开始上传文件时显示一个图片
	$.ajaxFileUpload({
		url: "upload?action_type=sendImage",
		secureuri: false, //一般设置为false
		fileElementId: 'choose_image', // 上传文件的id、name属性名
		dataType: 'json', //返回值类型
		success: function(data, status){
			var image_url = data.url;
			 var obj ={
			 	"targetType":"send",
			 	"type":"I",
			 	"message":image_url,
			 	"targetId":targetId,
			 };
			ws.send(JSON.stringify(obj));
		},
		error: function(data, status, e){
			$("#tipsContent").text("发送消息失败");
			$("#tips").modal('show');
		}
	});
	$("#choose_image").change(function(){
    	sendImage();
	});
}

//超级管理员登录
function superman(){
	if("S" == power){
		$('.userHead').attr('onmousedown','kickPerson(event)');
	}
}

//管理员操作
function kickPerson(event){
	if(event.button == 2){
		personId = event.target.parentNode.parentNode.getAttribute('id').substring(9);
		alert("特殊操作");
		$("#superHeader").text("超级管理");
		$("#superContent").html("<div class='alert'><p><strong>警告</strong> 你要禁言他了，这样不太好吧！<button class='btn btn-danger' data-dismiss='alert' onclick=\"changeState('X',"+personId+");\" style='float:right'>禁言</button></p></div>"+
				"<div class='alert alert-error'><p><strong>警告</strong> 你要停用他了，这样真的不太好吧！<button class='btn btn-danger' data-dismiss='alert' onclick=\"changeState('I',"+personId+");\" style='float:right'>停用</button></p></div>");
		$("#supertips").modal('show');		
	}
}

//改变用户状态
function changeState(state, personId){
	var obj ={
		 	"targetType":"changeState",		 	
		 	"message":state,
		 	"targetId":personId,
		 }; 
	ws.send(JSON.stringify(obj));
	$("#supertips").modal('hide');
}

//显示聊天表情
$(function (){
    $jq("a.face").smohanfacebox({
      Event : "click", 
      divid : "Smohan_FaceBox", 
      textid : "newmessage"
    });
	
	$("#choose_image").change(function(){
    	sendImage();
	});
});

//转换时间格式
/**       
 * 对Date的扩展，将 Date 转化为指定格式的String       
 * 月(M)、日(d)、12小时(h)、24小时(H)、分(m)、秒(s)、周(E)、季度(q) 可以用 1-2 个占位符       
 * 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)       
 * eg:       
 * (new Date()).pattern("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423       
 * (new Date()).pattern("yyyy-MM-dd E HH:mm:ss") ==> 2009-03-10 二 20:09:04       
 * (new Date()).pattern("yyyy-MM-dd EE hh:mm:ss") ==> 2009-03-10 周二 08:09:04       
 * (new Date()).pattern("yyyy-MM-dd EEE hh:mm:ss") ==> 2009-03-10 星期二 08:09:04       
 * (new Date()).pattern("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18       
 */   
Date.prototype.pattern=function(fmt){           
    var o = {           
    	"M+" : this.getMonth()+1, //月份           
    	"d+" : this.getDate(), //日           
    	"h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时           
    	"H+" : this.getHours(), //小时           
    	"m+" : this.getMinutes(), //分           
    	"s+" : this.getSeconds(), //秒           
   		"q+" : Math.floor((this.getMonth()+3)/3), //季度           
    	"S" : this.getMilliseconds() //毫秒           
    };           
    var week = {           
    "0" : "日",           
    "1" : "一",           
    "2" : "二",           
    "3" : "三",           
    "4" : "四",           
    "5" : "五",           
    "6" : "六"          
    };           
    if(/(y+)/.test(fmt)){           
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));           
    }           
    if(/(E+)/.test(fmt)){           
        fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "星期" : "") : "")+week[this.getDay()+""]);           
    }           
    for(var k in o){           
        if(new RegExp("("+ k +")").test(fmt)){           
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));           
        }           
    }           
    return fmt;           
};
