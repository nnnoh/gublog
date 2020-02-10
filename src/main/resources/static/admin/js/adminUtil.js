layui.define(['ajaxUtil', 'comUtil', 'jquery'], function (exports) {
    var ajaxUtil = layui.ajaxUtil;
    var comUtil = layui.comUtil;
    var $ = layui.jquery;

    var HASH_NAME_MAP = {
        "#/bams/article": "ARTICLE_LIST",
        "#/bams/category": "CATEGORY_LIST",
        "#/bams/tag": "TAG_LIST"
    };
    var SUBBODY_INFO = {
        "ARTICLE_LIST": {
            HTML_FILE: "/admin/subBody/bams/article/articleList.html",
            JS_OBJ: "articleList",
            TITLE: "文章列表"
        },
        "CATEGORY_LIST": {
            HTML_FILE: "/admin/subBody/common/comList.html",
            JS_OBJ: "categoryList",
            TITLE: "分类管理"
        },
        "TAG_LIST": {
            HTML_FILE: "/admin/subBody/common/comList.html",
            JS_OBJ: "tagList",
            TITLE: "分类管理"
        }
    };

    /**
     * 详情页弹出层 更新模式
     */
    var UPDATE_MODE = {
        NEW: 1,
        UPDATE: 2
    }

    function loadSubBody(hash, selector) {
        if(!comUtil.containKey(HASH_NAME_MAP,hash)){
            return;
        }
        var name = HASH_NAME_MAP[hash];
        $("title").text(SUBBODY_INFO[name].TITLE + ' - GU');
        layui.use([SUBBODY_INFO[name].JS_OBJ],function(args){
            ajaxUtil.simpleAjax(SUBBODY_INFO[name].HTML_FILE, function (data) {
                $(selector).html(data);
                layui[SUBBODY_INFO[name].JS_OBJ].init(selector);
            })
        });

    }
    exports ('adminUtil', {
        loadSubBody: loadSubBody,
        UPDATE_MODE: UPDATE_MODE
    });
});