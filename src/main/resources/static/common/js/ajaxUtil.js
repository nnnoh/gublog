layui.define(['cookieUtil','jquery'],function(exports){
    var $ = layui.jquery;
    var cookieUtil = layui.cookieUtil;

    var self={};

    var SUCEESS_CODE=200;
    /**
     * ajax 提交selector标签value
     * @param {*} dataSelector
     * @param {*} param
     */
    self.submitFormWithToken = function(ajaxParam, dataSelector, needAuth){
        var exParam={};
        if(typeof dataSelector != undefined){
            exParam.data = self.getFormData(dataSelector)
        }

        // 暂不用 直接使用cookie 认证
        // if(typeof neesAuth!='undefined' && needAuth==true){
        //     exParam.beforeSend = setAuthHrader;
        // }
        var param = $.extend(true, ajaxParam, exParam);
        $.ajax(param);
    }

    self.getFormData=function(dataSelector){
        var data = {};
        var $dom = $(dataSelector);
        for (var i=0;i<$dom.length;i++) {
            var value = $($dom[i]).val();
            if(value!=null){
                data[$($dom[i]).attr('name')] = value;
            }
        }
        return data;
    }

    /**
     * 设置token
     * @param {*} xhr
     */
    function setAuthHrader(xhr){
        xhr.setRequestHeader("Authorization", cookieUtil.getCookie("token"));
    }

    /**
     * 返回通用success回调方法
     * @param {*} callback
     */
    self.successCallback = function(callback){
        return function(result,status,xhr){
            if(result.status==SUCEESS_CODE){
                if(typeof callback=='function'){
                    callback(result.data);
                }
            } else {
                alert(result.message);
            }
        }
    }

    /**
     * ajax get
     * @param {*} url
     * @param {*} callback
     */
    self.simpleAjax = function(url, callback){
        $.ajax({
            url: url,
            success: callback
        });
    }
    exports('ajaxUtil',self);
});