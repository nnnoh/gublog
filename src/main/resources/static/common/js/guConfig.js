// 配置信息
layui.extend({
    'guConfig':'/common/js/guConfig',
    // 通用工具
    'cookieUtil':'/common/js/cookieUtil',
    'ajaxUtil':'/common/js/ajaxUtil',
    'dateTransfer':'/common/js/dateTransfer',
    'comUtil':'/common/js/comUtil',
    // 页面组件
    'nav': '/common/compont/nav/nav',
    // admin
    'adminUtil':'/admin/js/adminUtil',
    'comList':'/admin/subBody/common/comList',
    // bams
    'articleList': '/admin/subBody/bams/article/articleList',
    'articleDetail': '/admin/subBody/bams/article/articleDetail',
    'categoryList': '/admin/subBody/bams/category/categoryList',
    'categoryDetail': '/admin/subBody/bams/category/categoryDetail',
    'tagList': '/admin/subBody/bams/tag/tagList',
    // blog
    'blogConf': '/blog/js/blogConf',
    'article': '/blog/js/article',
    'category': '/blog/js/category'
}).define([],function(exports){
    exports('guConfig',{});
});
