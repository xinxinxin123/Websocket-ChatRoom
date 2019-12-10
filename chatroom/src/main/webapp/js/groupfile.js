/*
 * 共享文件前台
 */

function getFileList(){
	//加载所有文件
	$.ajax({
			type:"get",
			url:"fileList",
			async:true,
			success:function(data){
				var obj = JSON.parse(data);
				var res = "";
				for(i in obj ){
					var res = res + "<pre class='prettyprint'>"+"文件名："+ obj[i].foreName+ "<br>上传人："+ obj[i].uploader +"<br>上传时间："+obj[i].createDate+"<a href=\"download?fileId="+ obj[i].id+  "\"target=\"view_window\">下载</a></pre>";
				}
				$("#fileList").append(res);
			}
		});
}

//上传文件
function uploadFile(){
	// 开始上传文件时显示一个图片
	$.ajaxFileUpload({
		url: "upload?"+"action_type=uploadFile&staff_name="+encodeURI(encodeURI(staffName)),
		secureuri: false, //一般设置为false
		fileElementId: 'file', // 上传文件的id、name属性名
		dataType: 'json', //返回值类型
		success: function(data, status) {
			var res = data.res;
			$("#tipsContent").text(res);
			$('#tips').modal('show');
			//重新添加input type=file
			$("#file").remove();
			$("#test").append("<input type=\"file\" id=\"file\" name=\"file\">");
			if("上传成功！" == res){
				$("#fileList").children().remove();
				getFileList();
				var obj ={
					 	"targetType":"upload_success",		 	
					 	"message":staffName+"  上传文件:" +data.file_name,
					 	"targetId":"0",
					 	"type":"U"
					 }; 
				ws.send(JSON.stringify(obj));
			}	
		},
		error: function(data, status, e) {
			$("#tipsContent").text("上传失败！");
			$('#tips').modal('show');
		}
	}); 
}