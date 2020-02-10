layui.use(['jquery', 'element', 'ajaxUtil', 'cookieUtil'],function(exports){
    var ajaxUtil = layui.ajaxUtil;
    var cookieUtil = layui.cookieUtil;
    var $ = layui.jquery;

    // div居中
    var $back = $("#loginBack");
    var $topDiv = $("#loginDiv");

    $(".layui-tab-content").css("height", "");
    $back.css({
        'box-sizing': 'border-box',
        'height': window.innerHeight + 'px',
        'padding': (window.innerHeight - $topDiv.height()) / 2 - 20 + 'px ' + (window.innerWidth - $topDiv.width()) / 2 + 'px'
    });

    // 按钮事件
    $("#loginBtn").click(function () {
        ajaxUtil.submitFormWithToken({
            url: "/api/login",
            type: "POST",
            success: ajaxUtil.successCallback(function(data){
                console.log(data);
                var timeStr = data.expiresTime.substring(0,19);
                timeStr = timeStr.replace(/-/g,'/'); 
                var timestamp = new Date(timeStr)
                cookieUtil.setCookieObj(data, timestamp);

                // 跳转
                location.href="/admin";
            })
        },
        "#loginTab input");
    });
});