layui.define([], function(exports) {
    var self={};

    var cookieObj = {};
    /**
     * cookie 字符串，
     */
    var cookieStr;

    /**
     * 获取cookie对象
     */
    self.getCookieObj = function() {
        if (cookieStr != document.cookie) {
            cookieObj = {};
            cookieStr = document.cookie;
            var cookieArr = cookieStr.split(";");
            for (kvStr of cookieArr) {
                var kv = kvStr.split("=");
                if (kv.length == 2) {
                    cookieObj[kv[0]] = kv[1];
                }
            }
        }
        return cookieObj;
    }

    /**
     * 添加cookie
     * @param {*} obj
     * @param {*} expires
     */
    self.setCookieObj = function(obj, expiresSec) {
        var exp = new Date();
        exp.setTime(expiresSec);
        var kv = "";
        // 每次设置cookie只能设置一个kv及其属性
        for (key of Object.keys(obj)) {
            kv = key + "=" + escape(obj[key]) + ";";
            document.cookie = kv + "expires=" + exp.toGMTString();
        }
    }

    /**
     * 获取 Cookie 值
     * @param {*} key
     */
    self.getCookie = function(key) {
        self.getCookieObj();
        return cookieObj[key];
    }

    /**
     * 删除指定cookie
     * @param {*} name
     */
    self.delCookie = function(name) {
        var exp = new Date();
        // exp.setTime(exp.getTime() - 1);
        document.cookie = name + "=0;expires=" + exp.toGMTString();
    }

    self.delAllCookie = function(){
        self.getCookieObj();
        var exp = new Date();
        for (key of Object.keys(obj)) {
            kv = key + "=" + escape(obj[key]) + ";";
            document.cookie = key + "=0;expires=" + exp.toGMTString();
        }
    }

    exports('cookieUtil', self);
});
