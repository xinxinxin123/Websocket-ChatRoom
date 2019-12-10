var head_url_new = "";
function showVcard() {
	$("#vcard").modal("show");
	$("#head_old").attr("src", headUrl);

	$("#chooseHeadImg").change(function() {
		uploadHead();
	});
}

//上传头像
function uploadHead() {
	var file = $("#chooseHeadImg");
	if (isImage(file) != -1) {
		$.ajaxFileUpload({
			url: "upload?action_type=uploadHead",
			secureuri: false, //一般设置为false
			fileElementId: 'chooseHeadImg', // 上传文件的id、name属性名
			dataType: 'json', //返回值类型
			success: function(data, status) {
				var image_url = data.url;
				head_url_new = image_url;
				$("#head_new").attr("src",image_url);
			},
			error: function(data, status, e) {
				$("#tipsContent").text("上传失败");
				$("#tips").modal('show');
			}
		});
		$("#chooseHeadImg").change(function() {
		uploadHead();
	});
	} else {
		$("#tipsContent").text("请选择图片");
		$("#tips").modal('show');
	}
}
//确定修改头像
function changeHeadImg() {
	var staff = {
		"id":staffId,
		"headUrl":head_url_new
	}
	$.ajax({
		type:"post",
		url:"/usr/updateHead",
        contentType: 'application/json',
		async:true,
		data:JSON.stringify(staff),
		complete:function(data){ //TODO 无论成功或者失败都是调用complete。要改
			//location.reload();
		}
			
	});
}
//取消修改头像
function cancleChangeHead(){
	$("#head_new").attr("src","img/head_fail.png");
}
//展示修改密码面板
function showPWDPanel(){
	$("#edit_pwd").modal("show");
	
}
//确认修改密码
function changePwd(){
	var pwd = $("#pwd")[0].value;
	var pwd1 = $("#pwd1")[0].value;
	var pwd2 = $("#pwd2")[0].value;
	if(""==pwd || ""==pwd1 || ""==pwd2){
		$("#div_errormsg2").html("<div class='alert alert-error' onclick='reset()'>请输入密码</div>");
	}else{
		if(pwd1 != pwd2){
			$("#div_errormsg2").html("<div class='alert alert-error' onclick='reset()'>两次密码不一致</div>");
		}else{
			$.ajax({
				type:"get",
				url:"/usr/updatePwd?staffId="+staffId+"&pwd_old="+pwd+"&pwd_new="+pwd1,
				async:false,
				dataType:"json",
				success:function(data){
					var res  = data.res;
					if(res=="原密码输入错误"){
						$("#div_errormsg2").html("<div class='alert alert-error' onclick='reset()'>"+res+"</div>");
					}
					else{
						$('#edit_pwd_success').modal({
						    backdrop:false,
						    keyboard:false,
						    show:true
						});
					}
				},
			});
		}
	}
}
function reset(){
	$("#div_errormsg2").empty();
}
