layui.define(['element','jquery','adminUtil'],function(exports){
    var $ = layui.jquery;
    var adminUtil=layui.adminUtil;

    function init(){
        // 初始化 .layui-body
        adminUtil.loadSubBody(window.location.hash, ".layui-body");
        // hash 改变时重新加载 .layui-body
        window.addEventListener('hashchange',function(event){
           adminUtil.loadSubBody(window.location.hash, ".layui-body");
        })
    }
    exports('adminIndex',{
        init: init
    });
});
