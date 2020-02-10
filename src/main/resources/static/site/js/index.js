layui.define([ 'nav', 'jquery'], function(exports) {
    var $ = layui.jquery;
    var nav = layui.nav;

    var MENU_CONF=[{
        li: [{
            this: true,
            title: '首页',
            href: 'javascript:;',
        }, {
            this: false,
            title: '工具',
            href: 'javascript:;',
        }, {
            this: false,
            title: '社区',
            href: 'javascript:;',
            attr: 'target="_blank"'
        }]
    }, {
        id: "user",
        orient: 'right',
        li: [{
            title: '<img src="/common/img/defaultIcon.png" class="layui-nav-img">',
            href: 'javascript:;',
        }],
        dldd: [{
            title: '修改信息',
            href: 'javascript:;'
        }, {
            title: '退出',
            href: 'javascript:;'
        }]
    }, {
        id: "login",
        orient: 'right',
        li: [{
            title: '登录/注册',
            href: '/login',
        }]
    }];

    function init() {
        // 加载导航栏
        nav.init(MENU_CONF);
    }

    exports('index',{
        init: init
    });
});
