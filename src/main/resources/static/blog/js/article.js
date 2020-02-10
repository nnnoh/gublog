layui.define(['blogConf', 'nav', 'jquery'], function (exports) {
    var $ = layui.jquery;
    var blogConf = layui.blogConf;
    var nav = layui.nav;
    var laypage = layui.laypage;

    var isFirstLoadPage = true;
    function init() {
        // 加载导航栏
        nav.init(blogConf.getMenu(1));

        var id = location.pathname.substr(location.pathname.lastIndexOf('/')+1);
        // 加载文章
        if (!isNaN(id))  {
            loadArticle(id);
        }
    };

    function loadArticle(id) {
        $.ajax({
            url: "/api/article/" + id,
            success: function (res) {
                if (res.status == '200') {
                    setArticleValue(res.data.title, res.data.username, res.data.publishTime, res.data.content);
                    initMdView();
                } else if (res.status == '404') {
                    // 文章不存在
                    setArticleValue('文章不存在', '', '', blogConf.ARTICLE_PAGE_CONF.ARTICLE_404);
                }
            }
        });
    }

    function setArticleValue(title, author, publishTime, content) {
        $('.a-title').html(title);
        $('title').text(title);
        $('.a-author').html(author);
        $('.a-publishTime').html(publishTime);
        $('.a-content').html(content);
    }

    function initMdView(){
        var testView = editormd.markdownToHTML("markdown-view", {
            // markdown : "[TOC]\n### Hello world!\n## Heading 2", // Also, you can dynamic set Markdown text
            // htmlDecode : true,  // Enable / disable HTML tag encode.
            // htmlDecode : "style,script,iframe",  // Note: If enabled, you should filter some dangerous HTML tags for website security.
        });
    }

    exports('article', {
        init: init
    });
});
