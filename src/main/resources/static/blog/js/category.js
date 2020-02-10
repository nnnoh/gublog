layui.define(['blogConf', 'nav', 'jquery', 'laypage'], function (exports) {
    var $ = layui.jquery;
    var blogConf = layui.blogConf;
    var nav = layui.nav;
    var laypage = layui.laypage;

    function init() {
        // 加载导航栏
        nav.init(blogConf.getMenu(2));

        loadArticle();
    };


    /**
     * 加载文章
     */
    function loadArticle() {
        $.ajax({
            url: "/api/article/list/category",
            success: function (res) {
                if (res.status == '200') {
                    var $clist = $('.category-list');
                    $clist.html("");
                    $.each(res.data, function(i,v){
                        var $ctitle = $('<div class="category-title">'+v.category+'</div>');
                        var $alist = $('<div class="articles-list"></div>');
                        if (v.data.length > 0) {
                            $.each(v.data, function (index, value) {
                                $articleDiv = $('<div id="articleDiv-' + value.id + '" class="article-div"></div>');
                                $articleCover = $('<div class="article-cover"><img class="article-img" src="' + (value.cover != "" ? value.cover : blogConf.ARTICLE_PAGE_CONF.DEFAULT_COVER) + '"></div>');
                                $articleBrief = $('<div class="article-brief"></div>');
                                $articleTitle = $('<div id="articleTitle-' + value.id + '" class="article-title"><a class="layui-text" href="/blog/article/' + value.id + '">' + value.title + '</a></div>');
                                $articleMeta = $('<div class="article-meta"><span>' + value.username + '</span><span>' + value.publishTime + '</span></div>');
                                $articleContent = $('<div class="article-content">' + value.content + '</div>');
                                $articleBrief.append($articleTitle).append($articleMeta).append($articleContent);
                                $articleDiv.append($articleCover).append($articleBrief);
                                $alist.append($articleDiv);
                            });
                        } else {
                            $alist.append(blogConf.ARTICLE_PAGE_CONF.ARTICLE_404)
                        }
                        $clist.append($ctitle);
                        $clist.append($alist);
                    });
                }
            }
        })
    }

    exports('category', {
        init: init
    });
});
