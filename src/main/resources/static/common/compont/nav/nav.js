layui.define(['jquery', 'element'], function (exports) {
    var $ = layui.jquery;
    var element = layui.element;

    // [{  // <ul class='layui-nav'>
    //     id: '',
    //     orient: '', // class='layui-nav left/right'
    //     // <li class='layui-nav-item'>
    //     li: [{
    //         id: '',
    //         this: false, // class='layui-nav-item layui-this
    //         title: '',
    //         href: '',
    //         attr: '', // <a href='javascript:;' target='_blank'></a>\
    //         // <dl class='layui-nav-child'>
    //         //   <dd><a href="javascript:;"></a></dd>
    //         dldd: [{
    //             id: '',
    //             title: '',
    //             href: ''
    //         }]
    //     }]
    // }]

    function init(conf) {

        // 构造导航栏菜单
        $navMenu = $('.layui-main');
        for (var a = 0; a < conf.length; a++) {
            // ul
            var ulConf = conf[a];
            var $navUl = $("<ul class='layui-nav " + ulConf.orient + "' id='" + ulConf.id + "'></ul>")

            if (typeof ulConf != 'undefined') {
                for (var b = 0; b < ulConf.li.length; b++) {
                    // li
                    var liConf = ulConf.li[b];
                    var $navLi = $("<li class='layui-nav-item " + (liConf.this ? "layui-this" : "") + " " + liConf.orient + "' id='" + liConf.id + "''></li>");
                    var $navLiA = $("<a href='" + liConf.href + "' " + liConf.attr + ">" + liConf.title + "</a>");
                    $navLi.append($navLiA);

                    if (typeof liConf.dldd != 'undefined') {
                        // dl
                        var $navLiDl = $("<dl class='layui-nav-child'></dl>");

                        for (var c = 0; c < liConf.dldd.length; c++) {
                            // dd
                            var dlConfig = liConf.dldd[c];
                            var $navLiDLDd = $("<dd id='" + ulConf.id + "'><a href='" + dlConfig.href + "'>" + dlConfig.title + "</a></dd>");
                            $navLiDl.append($navLiDLDd);
                        }
                        $navLi.append($navLiDl);
                    }
                    $navUl.append($navLi);
                }
                $navMenu.append($navUl);
            }
        }

        // 重新加载一下
        element.render();
    }

    exports('nav', {
        init: init
    });
});
