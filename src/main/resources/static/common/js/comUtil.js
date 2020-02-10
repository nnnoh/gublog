layui.define([], function (exports) {
    var self = {};

    /**
     * 判断是否包含属性
     */
    self.containKey = function (obj, pro) {
        for (key of Object.keys(obj)) {
            if (key == pro) {
                return true;
            }
        }
        return false;
    }

    exports('comUtil', self);
});
