layui.define(['jquery'], function (exports) {
    var $ = layui.jquery;

    var MENU_CONF = [{
        orient: 'right',
        li: [{
            this: true,
            title: '主页',
            href: '/blog/',
        }, {
            this: false,
            title: '文章',
            href: '/blog/',
        }, {
            this: false,
            title: '分类',
            href: '/blog/category',
        }]
    }];

    var ARTICLE_PAGE_CONF = {
        LIMIT : 3,
        DEFAULT_COVER: '/common/img/defaultIcon.png',
        ARTICLE_404: "<div style='text-align:center;color=#aaa;font-size: 18px;'>文章不存在 Orz<div>"
    };

    /**
     * 选中菜单项
     * @param {*} index 
     */
    function getMenu(index){
        if(typeof index == 'undefined'){
            index = 0;
        }else {
            for(var i=0;i<MENU_CONF[0].li.length;i++){
                if(i==index){
                    MENU_CONF[0].li[i].this=true;
                }else{
                    MENU_CONF[0].li[i].this=false;
                }
            }
        }
        return MENU_CONF;
    }

    exports('blogConf', {
        getMenu: getMenu,
        ARTICLE_PAGE_CONF: ARTICLE_PAGE_CONF
    });
});