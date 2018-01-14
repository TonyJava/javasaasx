
notify = {

	/**
	 *
	 * @param message
	 * @param type 'info','success','warning','danger','rose','primary
	 * @param timer 消失时间 默认2000
	 * @param from
	 * @param align
	 */
	show: function( message, type, timer, from, align){
		type = comm.isEmpty(type) ? 'success' : type;
		timer = comm.isEmpty(timer) ? 2000 : timer;
		align = comm.isEmpty(align) ? 'center' : align;
		$.notify({
			icon: "notifications",
			message: message

		},{
			type: this.getType(type),
			timer: timer,
			delay: 1,
			placement: {
				from: from,
				align: align
			}
		});
	},
	autoShow: function(message, timer, from, align){
		if(comm.isEmpty(message))
			return;
		var type = 'info';
		if(message.indexOf("失败") != -1 || message.indexOf("错误") != -1 || message.indexOf("异常") != -1){
			type = 'error';
		}
		if(message.indexOf("成功") != -1 || message.indexOf("完成") != -1){
			type = 'success';
		}
		this.show(message, type, timer, from, align);
	},
	getType: function(type){
		if("success" == type){
			return "success";
		}else if("error" == type){
			return "danger";
		}else if("warn" == type){
			return "warning";
		}
		return type;
	}
}

dialog = {
	/**
	 * 确认对话框
	 * @param title
	 * @param text
	 * @param callback
	 * @param confirmButtonText
	 * @param cancelButtonText
	 */
	swalConfirm: function(title, text, callback, confirmButtonText, cancelButtonText){
		confirmButtonText = comm.isEmpty(confirmButtonText)?"确定":confirmButtonText;
		cancelButtonText = comm.isEmpty(cancelButtonText)?"取消":cancelButtonText;
		swal({
			title: title,
			text: text,
			showCancelButton: true,
			confirmButtonClass: 'btn btn-success',
			cancelButtonClass: 'btn btn-danger',
			confirmButtonText: confirmButtonText,
			cancelButtonText: cancelButtonText,
			buttonsStyling: false
		}).then(function() {
			log("ok...");
			if(typeof callback == "function"){
				callback();
			}else{
				window.location.href = callback;
			}
		});
		return false;
	}

}

comm = {
	isEmpty: function(str) {
		return (str == undefined || str == null || str.length == 0 || str == "undefined");
	},
	isNotEmpty: function(str) {
		return !this.isEmpty(str);
	},
	isArray: function(obj) {
		return Object.prototype.toString.call(obj) === '[object Array]';
	},
	containsArray: function(array, item) {
		//console.log("isArray:"+isArray(array)+"  "+array+"  --- "+item);
		var selectItem = false;
		if(isNotEmpty(array)){
			if(isArray(array)){
				for(var dex in array){
					//console.log(array[dex]+"="+item);
					if(array[dex] == item){
						selectItem = true;
						break;
					}
				}
			}else{
				//console.log(array+"="+item);
				if(array == item){
					selectItem = true;
				}
			}
		}
		return selectItem;
	},
	toText: function(obj){
		if(isEmpty(obj))
			return "";
		return obj;
	},
	getTime: function(){
		return new Date().getTime();
	},
	getRandom: function (){
		return Math.ceil(Math.random()*1000000000000);
	},
	trim: function (str) {
		return str.replace(/^\s+|\s+$/g, "");
	},
	getMapLength: function (obj){
		var count = 0;
		for(var i in obj){
			if(obj.hasOwnProperty(i)){
				count++;
			}
		}
		return count;
	},
	/**
	 * 滚动条向下 滚动
	 * @param scrollNum 滚动数 px
	 */
	scrollDown: function(scrollNum){
		$(window).scrollTop(scrollNum);
	}

}


/**
 * 绑定高亮搜索
 * @param scopeId 作用域ID
 * @param inputSearchId 搜索输入框ID
 * @param searchFormId 搜索表单ID
 */
function bindHighlightSearch(scopeId, inputSearchId, searchFormId){
	var key = "highlight_search";
	var keyword = store.get(key);
	//log("get highlight_search:"+keyword);
	if(isNotEmpty(keyword)){
		setTimeout(function(){
			$("#"+scopeId).highlight(keyword);//高亮
		},  00);
		store.set(key, null);
	}

	//表单提交时 设值
	$("#"+searchFormId).bind("submit", function(){
		keyword = $("#"+inputSearchId).val();
		if(isNotEmpty(keyword))
			store.set(key, keyword);
	});
}


/**
 * 绑定 字数统计 （初始化页面时调用）
 * @param textareaId 文本域属性id
 * @param tipId 提示标签id
 */
function bindWordCount(textareaId, tipId){
	//键盘松开事件
	$("textarea[id="+textareaId+"]").keyup(function(){
		$("#"+tipId).text($(this).val().length);
	});
}

/**
 * 获得 文本框组 值
 * @param inputId
 * @returns {Array}
 */
function getInputGroupVal(inputId) {
	var vals = new Array();
	$("#"+inputId).each(function(){
		var val = $(this).val();
		if(isNotEmpty(val))
			vals.push(val);
	});
	return vals;
}



/**
 * 睡眠（建议不超过1秒，慎用，会导致页面卡死）
 * @param milliseconds
 */
function common_sleep(milliseconds) {
	console.log("begin sleep:"+milliseconds);
	var start = new Date().getTime();
	for (var i = 0; i < 1e7; i++) {
		if ((new Date().getTime() - start) > milliseconds){
			break;
		}
	}
	console.log("end sleep:"+milliseconds);
}

/**
 * 打印日志
 * @param msg
 */
function log(msg){
	console.log(msg);
}

function logParam(p1, p2, p3, p4, p5, p6, p7, p8){
	console.log("param:"+getStrVal(p1)+getStrVal(p2)+getStrVal(p3)+getStrVal(p4)+getStrVal(p5)+getStrVal(p6)+getStrVal(p7)+getStrVal(p8));
}

function getStrVal(param, appendPrefix){
	appendPrefix = appendPrefix==undefined?"|":appendPrefix;
	return param==undefined?"":appendPrefix+param;
}

function getValLength( value, element ) {
	if(isNotEmpty(element)){
		switch( element.nodeName.toLowerCase() ) {
			case "select":
				return $("option:selected", element).length;
			case "input":
				if ( this.isCheckable( element) ) {
					return this.findElementByName(element.name).filter(":checked").length;
				}
		}
	}
	return value.length;
}

String.prototype.startWith=function(str){
	var reg=new RegExp("^"+str);
	return reg.test(this);
}
String.prototype.endWith=function(str){
	var reg=new RegExp(str+"$");
	return reg.test(this);
}


/**
 * 调用
 * var buffer = new StringBuffer();
 *	buffer.append("Hello ").append("javascript");
 *	alert(buffer.toString();
 */
/**
 *字符串 拼接
 * */
function StringBuffer() {
	this.__strings__ = new Array();
}
StringBuffer.prototype.append = function (str) {
	this.__strings__.push(str);
	return this;    //方便链式操作
}
StringBuffer.prototype.toString = function () {
	return this.__strings__.join("");
}
//清除
StringBuffer.prototype.clear = function () {
	this.__strings__ = new Array();
	return this;
}
/*扩展　String 原生方法*/
String.prototype.startWith=function(str){
	var reg=new RegExp("^"+str);
	return reg.test(this);
}
String.prototype.endWith=function(str){
	var reg=new RegExp(str+"$");
	return reg.test(this);
}
String.prototype.replaceAll = function(s1, s2){
	return this.replace(new RegExp(s1,"gm"), s2);
}


/*
 * @brief: 定义队列类
 * @remark:实现队列基本功能
 */
function Queue(){
	//存储元素数组
	var aElement = new Array();
	/*
	 * @param: obj 元素
	 * @return: 返回当前队列元素个数
	 */
	Queue.prototype.add = function(obj){
		aElement.push(obj);
		console.log("add after:"+aElement);
	}

	/*
	 * @return: 弹出队尾元素 ,当队列元素为空时,返回null
	 */
	Queue.prototype.pop = function(){
		if (aElement.length == 0)
			return null;
		else{
			var obj = aElement.pop();
			console.log("pop after:"+aElement);
			return obj;
		}
	}

	/*
	 * @brief: 获取队列元素个数
	 * @return: 元素个数
	 */
	Queue.prototype.size = function(){
		return aElement.length;
	}
	/*
	 * @brief: 返回队头素值
	 * @return: vElement
	 * @remark: 若队列为空则返回null
	 */
	Queue.prototype.getHead = function(){
		if (aElement.length == 0)
			return null;
		else
			return aElement[0];
	}
	/*
	 * @brief: 返回队尾素值
	 * @return: vElement
	 * @remark: 若队列为空则返回null
	 */
	Queue.prototype.getEnd = function(){
		var len = aElement.length;
		if (len == 0)
			return null;
		else
			return aElement[len - 1];
	}

	/*
	 * @brief: 弹出指定元素
	 * @return: vElement
	 * @remark: 若队列为空则返回null
	 */
	Queue.prototype.popObj = function(obj){
		var len = aElement.length;
		if (len == 0)
			return null;
		else{
			for (var i = 0; i < len; i++) {
				var ele = aElement[i];
				if(obj == ele){
					var popObj = aElement.splice(i, 1);
					console.log("popObj after:"+aElement);
					return popObj[0];
				}
			}
			return null;
		}
	}

	/*
	 * @brief: 获取下标位置
	 * @return: index
	 * @remark: 若队列为空则返回-1
	 */
	Queue.prototype.indexOf = function(obj){
		if (aElement.length == 0)
			return -1;
		else{
			for (var i = 0; i < aElement.length; i++) {
				var ele = aElement[i];
				if(obj == ele){
					return i;
				}
			}
			return -1;
		}
	}

	/*
	 * @brief: 判断队列是否为空
	 * @return: 队列为空返回true,否则返回false
	 */
	Queue.prototype.isEmpty = function(){
		if (aElement.length == 0)
			return true;
		else
			return false;
	}
	/*
	 * @brief: 将队列元素转化为字符串
	 * @return: 队列元素字符串
	 */
	Queue.prototype.toString = function(){
		var sResult = (aElement.reverse()).toString();
		aElement.reverse()
		return sResult;
	}
}

//***************** form *****************

function setInputVal(inputEl, val){
	$(inputEl).val(val);
}

function refreshPage(){
	parent.window.location.href = parent.window.location.href;
}


/**
 * 绑定删除按钮
 * @param btnId 按钮ID
 * @param checkBoxName checkbox名称
 * @param url 执行url
 * @param succCall
 * @param filterCheckboxRule 过滤复选框规则 (只筛选有效的数据)
 * @param filterCheckboxRuleTip 规则提示语
 */
function bindBtnDeleteRows(btnId, checkBoxName, url, succCall, filterCheckboxRule, filterCheckboxRuleTip, groupId){
	bindBtnBatchOperRows(btnId, checkBoxName, "您确定要删除吗？", "删除", url, succCall, filterCheckboxRule, filterCheckboxRuleTip, groupId);
}


/**
 * 绑定 按钮批量操作行
 * @param btnId 按钮ID
 * @param checkBoxName checkbox名称
 * @param confirmTitle 确认提示语
 * @param actionTitle  操作标题（删除/修改 等等）
 * @param url     执行url
 * @param succCall    成功回调函数 缺省则刷新当前页面
 * @param filterCheckboxRule   过滤复选框规则 (只筛选有效的数据)
 * @param filterCheckboxRuleTip  规则提示语
 * @param groupId  组ID
 * @param beforeSendFun  提交前 函数
 */
function bindBtnBatchOperRows(btnId, checkBoxName, confirmTitle, actionTitle, url, succCall, filterCheckboxRule, filterCheckboxRuleTip, groupId,  beforeSendFun){
	$("#"+btnId).click(function(){
		var selectVals;
		if(isNotEmpty(groupId))
			selectVals= getSelectGroupCheckBoxVal(groupId);
		else
			selectVals = getSelectCheckBoxVal(checkBoxName, filterCheckboxRule, filterCheckboxRuleTip);
		if(selectVals.length == 0){
			layerAlert("请至少选择一条信息操作！", "warning");
			return;
		}
		//确认
		layerConfirm(confirmTitle, function(){
			ajaxPostSelectVals(actionTitle, url, selectVals, succCall, beforeSendFun);
		});
	});
}


/**
 * 绑定 按钮操作行
 * @param btnId 按钮ID
 * @param checkBoxName checkbox名称
 * @param confirmTitle 确认提示语
 * @param actionTitle  操作标题（删除/修改 等等）
 * @param url     执行url
 * @param succCall    成功回调函数 缺省则刷新当前页面
 * @param filterCheckboxRule   过滤复选框规则 (只筛选有效的数据)
 * @param filterCheckboxRuleTip  规则提示语
 * @param groupId  组ID
 * @param beforeSendFun  提交前 函数
 */
function bindBtnOperOneRow(btnId, checkBoxName, confirmTitle, actionTitle, url, succCall, filterCheckboxRule, filterCheckboxRuleTip, groupId,  beforeSendFun){
	$("#"+btnId).click(function(){
		var selectVals;
		if(isNotEmpty(groupId))
			selectVals= getSelectGroupCheckBoxVal(groupId);
		else
			selectVals = getSelectCheckBoxVal(checkBoxName, filterCheckboxRule, filterCheckboxRuleTip);
		if(selectVals.length != 1){
			layerAlert("有且只能选1条数据进行"+actionTitle+"！", "warning");
			return;
		}
		if(confirmTitle == null){
			ajaxPostSelectVals(actionTitle, url, selectVals, succCall, beforeSendFun);
		}else{
			//确认
			layerConfirm(confirmTitle, function(){
				ajaxPostSelectVals(actionTitle, url, selectVals, succCall, beforeSendFun);
			});
		}

	});
}

/**
 * 异步提交 选中值
 * @param actionTitle
 * @param url
 * @param selectVals
 * @param succCall
 * @param beforeSendFun
 */
function ajaxPostSelectVals(actionTitle, url, selectVals, succCall, beforeSendFun){
	$.ajax({
		type: "post",
		dataType: "json",
		url: ctx + url,
		data: {"selectVals": selectVals},
		beforeSend: function(){
			if(typeof beforeSendFun == 'function'){
				beforeSendFun();
			}else{
				loading("正在为您处理，请稍等...");
			}
		},
		success: function (data) {;
			if(data.statusCode == 200){
				if(typeof succCall == 'function'){
					succCall(data.obj);
				}else{
					layerTip(actionTitle+"成功！");
					setTimeout(function(){
						window.location.href = window.location.href;
					}, 1500);
				}
			}else{
				log(data.exception)
				layerTip(data.message, "error");
			}
		},
		error: function(e){
			log_error("bindBtnBatchOperRows", e);
			ajaxErrorTip(e, actionTitle+"失败，请联系管理员！");
		}
	});
}

/**
 * 绑定按钮 导出 Excel
 * @param btnId
 * @param checkBoxName
 * @param searchFormId
 * @param exportUrl
 */
function bindBtnExportExcel(btnId, checkBoxName, searchFormId, exportUrl){
	$("#"+btnId).click(function(){
		var selectVals = getSelectCheckBoxVal(checkBoxName);
		if(selectVals.length == 0){
			layerAlert("请至少选择一条信息操作！", "warning");
			return;
		}
		$("input[name=selectVals]").val(selectVals.join());
		var oldAction = $("#"+searchFormId).attr("action");
		$("#"+searchFormId).attr("action", ctx + exportUrl);
		$("#"+searchFormId).submit();
		//还原地址
		setTimeout(function(){
			$("#"+searchFormId).attr("action", oldAction);
		}, 100);
	});
}



//*****************************begin 复选框***************************************//

/**
 * 获取 选中行 单元格值
 * @param checkBoxName
 * @param colIndex
 * @returns {Array}
 */
function getSelectCheckBoxRowColVal(checkBoxName, colIndex){
	var array = new Array();
	$("input[type=checkbox][name="+checkBoxName+"]:checked").each(function(){
		var tr = $(this).closest("tr");
		var text = tr.find("td:eq("+colIndex+")").text();
		array.push(trim(text));
	});
	return array;
}

/**
 * 获取 复选框值
 * @param checkBoxName
 * @param filterCheckboxRule 过滤复选框规则 (只筛选有效的数据)
 * @param filterCheckboxRuleTip 规则提示语
 * @returns {Array}
 */
function getSelectCheckBoxVal(checkBoxName, filterCheckboxRule, filterCheckboxRuleTip){
	var array = new Array();
	$("input[type=checkbox][name="+checkBoxName+"]:checked").each(function(){
		if(isNotEmpty(filterCheckboxRule)){
			var arr = filterCheckboxRule.split("=");
			var attrVal = $(this).attr(arr[0]);
			if(attrVal != arr[1]){
				layerAlert(filterCheckboxRuleTip, "error")
				throw new Error('异常, 终止脚本.');
				return false;
			}
		}
		array.push($(this).val());
	});
	return array;
}


/**
 * 获取 组的 复选框值
 * @param groupId 组ID 要求input加 groupId属性
 * @returns {Array}
 */
function getSelectGroupCheckBoxVal(groupId){
	var array = new Array();
	$("input[type=checkbox][groupId="+groupId+"]:checked").each(function(){
		array.push($(this).val());
	});
	return array;
}


/**
 * 绑定 checkbox 全选 eg: bindCheckBoxSelectAll("wordSelectAllId", "wordInputName");
 * @param checkBoxAllId 全选框ID
 * @param childBoxName 子checkbox 名称
 */
function bindCheckBoxSelectAll(checkBoxAllId, childBoxName){
	$("#"+checkBoxAllId).click(function(){
		var isChecked = $(this).is(":checked");
		$("input[type=checkbox][name="+childBoxName+"]").prop("checked", isChecked);
	});
}

/**
 * 绑定 checkbox 全选 eg: bindCheckBoxGroupSelectAll("wordSelectAllId", "wordInputName");
 * @param checkBoxAllId 全选框ID
 * @param childBoxName 子checkbox 名称
 */
function bindCheckBoxGroupSelectAll(checkBoxAllId, groupName){
	$("#"+checkBoxAllId).click(function(){
		var isChecked = $(this).is(":checked");
		$("input[type=checkbox][group="+groupName+"]").prop("checked", isChecked);
	});
}
//*****************************end 复选框***************************************//

/**
 * 弹出窗口
 * @param btnId 按钮ID
 * @param title 标题
 * @param url 路径
 * @param width
 * @param height
 */
function layerOpenWin(btnId, title, url, width, height) {
	width = isEmpty(width)?500:width;
	height = isEmpty(height)?500:height;
	$("#" + btnId).click(function () {
		title = title == undefined ? "操作" : title;
		//打开窗口
		layer.open({
			type: 2,
			title: title,
			shadeClose: true,
			shade: false,
			maxmin: true, //开启最大化最小化按钮
			area: [width+'px', height+'px'],
			content: url/*,
			 end: function () {
			 location.reload();
			 }*/   //end End
		}); //layerEnd
	});  //click End
};


/**
 *
 * @param btnId  按钮ID
 * @param cloumnNum 列的号码
 */
function selectOneCloumn(btnId, cloumnNum) {
	$("#" + btnId).click(function () {
		if (this.checked) {
			$("table tr td:nth-child(" + cloumnNum + ")").each(function () {
				$(this).find("[type='checkbox']").prop("checked", true);
			});
		} else {
			$("table tr td:nth-child(" + cloumnNum + ")").each(function () {
				$(this).find("[type='checkbox']").prop("checked", false);
			});
		}
	});
};

/**
 *  下拉框选项被选择时触发事件
 * @param url 地址
 * @param
 */
function getData(url,inputID) {
	var getdata = "";
	//异步提交
	$.ajax({
		type: "get",
		dataType: "json",
		url:  url,
		success: function (data) {
			if(data.statusCode == 200){
				//layerTip("成功！");
				getdata = data.value;
				//alert("getdata:"+getdata);
				$("#"+inputID).val(getdata);
			}
		},
		error: function(e){
			layerTip("失败，请联系管理员！", "error");
		} //error End
	}); //ajax End
}; //onselect End


/**
 * 异步 初始化下拉框值
 * @param url
 * @param selectId
 * @param callBuildOptStr
 */
function ajaxInitSelectVals(url, selectId, callBuildOptStr) {
	ajaxAsyncJsonPost(url, {}, function(data){
		var opts = callBuildOptStr(data);
		$("#"+selectId).append(opts);
	});
};

/**
 *  表单提交
 * @param btnId 提交按钮
 * @param url 提交地址
 * @param formId 表单Id
 * @param redirectUrl 重定向地址
 */
function submitForm(btnId,url,formId,redirectUrl) {
	$("#"+btnId).click(function () {
		//异步提交
		postAjaxForm(url,formId,redirectUrl);
	});
};

select = {
	buildSelectOptsHtml : function(selectBos){
		var opts = new StringBuffer();
		if(comm.isNotEmpty(selectBos)){
			var selectBo = null;
			for(var i = 0; i < selectBos.length; i++){
				selectBo = selectBos[i];
				opts.append('<option value="'+selectBo.value+'" data-tokens="'+selectBo.text+'">'+selectBo.text+'</option>');
			}
		}
		return opts.toString();
	}
}

http = {

	/**
	 * ajax 异步请求
	 * @param url
	 * @param paramArr
	 * @param succCall 或消息
	 * @param beforeSendFun 非必填
	 */
	ajaxAsyncJsonPost: function (url, paramArr, succCall, beforeSendFun){
		$.ajax({
			type: "post",
			dataType: "json",
			url: ctx + url,
			data: paramArr,
			beforeSend: function(){
				if(typeof beforeSendFun == 'function'){
					beforeSendFun();
				}
			},
			success: function (data) {
				if(data.statusCode == 200){
					if(typeof succCall == 'function'){
						succCall(comm.isEmpty(data.obj)?data.message:data.obj);
					}else if(typeof succCall == 'string'){
						notify.show(succCall);
					}else{
						notify.show("succCall参数不能为空", "error");
					}
				}else{
					log("error:"+data.exception)
					notify.show(data.message, "error");
				}
			},
			error: function(e){
				log(e)
				http.ajaxErrorTip(e);
			}
		});
	},
	ajaxErrorTip: function (e, defMessage){
		console.log(e);
		if(e.status == 403){
			notify.show("您没有操作权限，请联系管理员！", "error");
		}else if(e.status == 404){
			notify.show("请求不存在, 请联系管理员!", "error");
		}else if(e.status == 500) {
			notify.show("服务器内部错误，请联系管理员！", "error");
		}else{
			if(comm.isNotEmpty(defMessage))
				notify.show("服务器内部错误，请联系管理员！", "error");
		}
	}
}
