/**
 * 我的JS通用工具
 * @version     1.0
 * @Creator    addy <taddy4944@163.com>
 * @Depend     jQuery 1.3+
 * @Date       2014/5/6
 *
 **/
//菜单加载事件
 function ajaxGetMenus(url,paramVal,accordid){
	 var divHtml="";
	 var treeIds=new Array();
	 var treeMnsData=new Array();
	 $.ajax({
    	 type:'get',
    	 url:url,
    	 dataType: 'json',
    	 data:paramVal,
    	 cache: false,
    	 error: function(XMLHttpRequest, status, thrownError) {
    		 //失败
 			},
 		 success: function(msg){
 			$.each(msg, function(i, n){
 				if(n.id!=null && n.parentMenusId==0){
 					divHtml+="<div title='"+n.menusName+"'>";
 					divHtml+="<ul id='treeMns"+n.id+"' style='margin-top:3px;'></ul>";
 					divHtml+="</div>";
 					treeIds.push(n.id);
 				}else{
 					treeMnsData.push(n);
 				}
 			})
 			 $("#"+accordid).ligerAccordion('reload',divHtml);
 			if(treeIds.length>0){
 				//循环添加菜单树结构
 				$.each(treeIds, function(i, tid){
 					var mndataArray=new Array();
 					$.each(treeMnsData,function(m,mndata){
 						var treeCode=mndata.menusCode;
 						if(treeCode.length>1){
 							//根据菜单编号的编码规则，截取第一位字符，进行判断标准
 							treeCode=treeCode.substring(0,1);
 							if(treeCode==tid){
 								mndataArray.push(mndata);
 							}
 						}
 					})
 					if(mndataArray.length>0){
 						$("#treeMns"+tid).ligerTree({  
 	 						 data:mndataArray,
 	 						 checkbox:false,
 	 						 idFieldName :'id',
 	 						 parentIDFieldName :'parentMenusId',
 	 						 textFieldName:'menusName',
 	 						attribute:['id','menusUrl'],
 	 						onSelect: function (node){
 	 							if (!node.data.menusUrl) return;
 	 							$("#home",parent.document.body).attr("src",node.data.menusUrl);
 	 						}
 	 					});
 					}
 				})
 			}
 		 }
     })
 }
  //获取所有表单值，并拼接成参数
  function getParamVal(paramDiv){
	//获取DIV中所有text对象
	var txtElm=$("#"+paramDiv+" :text");
	//获取所有select对象
	var selectElm=$("#"+paramDiv+" :selected");
	//获取所有textarea对象
	//var textareaElm=$("#"+paramDiv+" :textarea");
	//获取所有radio对象
	var radioElm=$("#"+paramDiv+" input[type='radio']");
	//获取所有checkbox对象
	var checkboxElm=$("#"+paramDiv+" input[type='checkbox']");
	//获取所有checkbox对象
	var hiddenElm=$("#"+paramDiv+" input[type='hidden']");
	//声明参数
	var paramVal="";
	//开始循环
	$.each(txtElm, function(i, el){
		if(el.value!=''){
			paramVal+=el.id+"="+el.value+"&";
		}
	})
	$.each(selectElm, function(i, el){
		if(el.value!=''){
			paramVal+=el.id+"="+el.value+"&";
		}
	})
	$.each(radioElm, function(i, el){
		if(el.checked){
			paramVal+=el.name+"="+el.value+"&";
		}
	})
	$.each(hiddenElm, function(i, el){
		if(el.value!=''){
			paramVal+=el.id+"="+el.value+"&";
		}
	})
	//声明保存name的集合
	var checkNameArray=new Array();
	$.each(checkboxElm, function(i, el){
		var isExist=false;
		$.each(checkNameArray, function(m, ma){
			if(ma==el.name){
				isExist=true;
			}
		})
		//如果不存在，则添加
		if(!isExist){
			checkNameArray.push(el.name);
		}
	})
	//开始循环name
	$.each(checkNameArray, function(i, el){
		var checkVal="";
		$('input[name="'+el+'"]:checked').each(function(){    
			checkVal+=$(this).val()+",";    
		});
		if(checkVal.length>0){
			//去掉最后一个逗号
			paramVal+=el+"="+checkVal.substring(0,(checkVal.length-1))+"&";
		}
	})
	if(paramVal.length>0){
		//去掉最后一个连接符
		paramVal=paramVal.substring(0,(paramVal.length-1));
	}
	return paramVal;
  }
  
  
  
  
  
  
  
  
  
  