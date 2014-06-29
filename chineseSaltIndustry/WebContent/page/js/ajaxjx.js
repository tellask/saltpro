/**
 * jquery-jx 支持ie6+、谷歌、火狐浏览器
 * @arguments  綁定html控件
 * @version     1.0
 * @Creator    wangyangtoy <wangyangtoy@gmail.com>
 * @Depend     jQuery 1.3+
 * @Date       2012/06/13
 *
 **/
var ajaxJx = {
	/**
	 * 定义的参数
	 *
	 **/
	options : {
		'method' : 'get',
		'data'   : ''
		},
	/**
	 * 初始化 暂时设定三种常用情况 a select button
	 *
	 **/
	init:function() {
		//a 标签绑定
		$("a[jx-href]:not([jx-bind])").each(function(){
			var link = $(this);
			ajaxJx.setBind(link);
			link.bind('click', ajaxJx.ajaxClick); 
		});
		//下拉框绑定
		$('select[jx-href]:not([jx-bind])').each(function(){
			var select = $(this);
			ajaxJx.setBind(select);
			select.bind('change', ajaxJx.ajaxClick); 
		}); 
		//按钮绑定
		$('button[jx-href]:not([jx-bind])').each(function(){
			var button = $(this);
			ajaxJx.setBind(button);
			button.bind('click', ajaxJx.ajaxClick); 
		});
	},

	/**
	 * 控件綁定，表示已經在使用jx了
	 * 原因：
	 * 1 因為使用live無效，ajax加載的元素，無法使用jx，所以只能再加載一邊ajaxJx.init()，這裡是為了避免重複綁定統一元素
	 * 2 获取options
	 **/
	setBind:function($obj) {
		$obj.attr("jx-bind",1);
	},
	/**
	 * 賦值
	 * @return $this 當前對象
	 **/
	setOptions:function($this, option) {
		if ("undefined" == option || null == option) {
			ajaxJx.options = $.extend({}, ajaxJx.options, {
						"url"      : $this.attr("jx-href"),
						"method"   : $this.attr("jx-method"),
						"complete" : $this.attr("jx-complete"),
						"into"     : $this.attr("jx-into"),
						"cache"    : $this.attr("jx-cache"),
						"params"   : $this.attr("jx-params")
					  });
		} else {
			ajaxJx.options = $.extend({}, ajaxJx.options, option);
		}
		return $this;
		
	},
	
	/**
	 * ajax 事件  这里callback特意采用json格式，如果有需要可以在这里修改
	 * 
	 **/
	ajaxClick:function(event) {
		
		//获取当前对象
		$this = ajaxJx.setOptions($(this));
		
		
		//变为大写
		ajaxJx.options.method = ajaxJx.options.method.toUpperCase();
		/**
		 * 设置参数
		 * v value;t text
		 **/ 
		var _paramsArr = "";
		if ( ajaxJx.options.params ) {
			_paramsArr = ajaxJx.attr2param();
			if ("GET" == ajaxJx.options.method) {
				ajaxJx.options.url +="?"+$.param(_paramsArr);
			} else {
				ajaxJx.options.data = $.param(_paramsArr);
			}
		}
		

		/**
		 * get才使用緩存缓存
		 **/
		if (!window.localStorage && ajaxJx.options.cache) ajaxJx.options.cache = 1; 
		//是否缓存当前页面
		var _is_page_cache  = ajaxJx.options.cache && 1 >= ajaxJx.options.cache;
		if (_is_page_cache && ajaxJx.getPageCache($this)) return;
		//是否缓存在本地
		var _is_local_cache = ajaxJx.options.cache && 1 <  ajaxJx.options.cache;
		if (_is_local_cache && ajaxJx.getLocalCache(ajaxJx.options.url,ajaxJx.options.cache)) return;
		
		
		

		$.ajax({
			type: ajaxJx.options.method,
			url : ajaxJx.options.url,
			dataType: 'json',
			data: ajaxJx.options.data,
			timeout: 50000,
			cache: false,
			error: function(XMLHttpRequest, status, thrownError) {
			//alert('Error loading ' + url +', Please send it again!');
			},
			success: function(msg) {		
				if (!ajaxJx.callBackFunction(msg)) return;
				
				if (_is_page_cache){
					//缓存在当前页面
					$this.data("msg",msg);
				} else if (_is_local_cache) {
					//缓存在本地
					msg.cache_time = parseInt(Date.parse(new Date()))/1000;
					//json对象转换为字符串
					localStorage.setItem(ajaxJx.options.url,JSON.stringify(msg));
				}
				
			}
		});
	},
	
	/**
	 * ajax成功後執行方法
	 *
	 **/
	callBackFunction:function(msg) {
		if ( (undefined == msg) || (null == msg) )
			return false;
		
		if (ajaxJx.options.complete) {
			//自定义方法
			eval(ajaxJx.options.complete+"(msg)");
			return;
		}
		if ("success" == msg.status && "" != ajaxJx.options.into) 
			$("#"+ajaxJx.options.into).html(msg.msg);
			
		return true;
	},
	
	/**
	 * 頁面缓存
	 * @param 对象
	 * @return 是否使用缓存 
	 *
	 **/
	 
	getPageCache:function($obj) {
		var _cache_msg = $obj.data("msg");
		if ( (undefined != _cache_msg) && (null != _cache_msg) ) {
			ajaxJx.callBackFunction(_cache_msg)
			return true;
		}
		return false;
	},
	
	/**
	 * 本地緩存
	 * @param key 緩存的key
	 * @param time 換時間
	 * @return 是否使用缓存 
	 *
	 **/
	 
	getLocalCache:function(r_key,r_time) {
		var _cache_msg = localStorage.getItem(r_key);
		if ( (undefined != _cache_msg) && (null != _cache_msg) ) {
			
			eval('var cache_msg = (' + _cache_msg + ')');
			//當前時間
			var _timestamp = parseInt(Date.parse(new Date()))/1000;
			//如果大於緩存時間，就重新查詢
			if (_timestamp-cache_msg.cache_time>r_time)
				return false;
			
			ajaxJx.callBackFunction(cache_msg)
			return true;
		}
		return false;
	},
	
	/**
	 * 属相转换为参数
	 **/
	attr2param:function() {
		var _paramsArr = {};
		eval('var _eval_params = (' + ajaxJx.options.params + ')');
		
		$.each(_eval_params, function(param, type) {
			//var $temp = $("[name="+param+"]");//临时对象
			var $temp = $("#"+param);//临时对象
			var _val  = "";//获取值
			switch (type) {
				case "t":
					_val = $temp.text();
					break;
					
				case "v":
					_val = $temp.val();
					break;
					
				default:
				    _val = type;
					
			}
			_paramsArr[param] = _val;
		});
		return _paramsArr;
	}
}

/**
 * 插件方式
 * 使用方法$(this).ajaxJx({url:"tmpl/1.php",into:"right"});
 */
!function( $ ) {
	$.fn.ajaxJx = function ( option ) {
		ajaxJx.setOptions($(this) ,option);
		ajaxJx.ajaxClick();
    }
}( window.jQuery );

$(document).ready(function() {
	ajaxJx.init();
});