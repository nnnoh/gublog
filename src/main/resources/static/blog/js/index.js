layui.define(['blogConf', 'nav', 'jquery', 'laypage'], function (exports) {
    var $ = layui.jquery;
    var blogConf = layui.blogConf;
    var nav = layui.nav;
    var laypage = layui.laypage;

    var isFirstLoadPage = true;
    function init() {
        // 加载导航栏
        nav.init(blogConf.getMenu());

        var page = location.hash.replace('#!page=', '');
        // 默认显示第一页
        page == "" ? page = 1 : "";
        loadArticle(page, blogConf.ARTICLE_PAGE_CONF.LIMIT);
    };

    /**
     * 加载分页栏
     * @param {*} count 
     */
    function loadPageBar(count, limit) {
        // 分页
        laypage.render({
            elem: 'articlesPage'
            , count: count
            , curr: location.hash.replace('#!page=', '')
            , hash: 'page'
            , limit: limit
            , jump: function (obj, first) {
                // 首次不执行
                if (!first) {
                    console.log(obj.curr);
                    loadArticle(obj.curr, obj.limit);
                }
            }
        });
    }

    /**
     * 加载分页文章
     * @param {*} page 
     * @param {*} limit 
     */
    function loadArticle(page, limit) {
        $.ajax({
            url: "/api/article/list",
            data: {
                page: page,
                limit: limit,
                isPreview: true,
                status: 2,
                sort: 'publishTime',
                order: 'desc'
            },
            success: function (res) {
                if (res.code == '0') {
                    var $list = $('.articles-list');
                    $list.html("");
                    if (res.data.length > 0) {
                        $.each(res.data, function (index, value) {
                            $articleDiv = $('<div id="articleDiv-' + value.id + '" class="article-div"></div>');
                            $articleCover = $('<div class="article-cover"><img class="article-img" src="' + (value.cover != "" ? value.cover : blogConf.ARTICLE_PAGE_CONF.DEFAULT_COVER) + '"></div>');
                            $articleBrief = $('<div class="article-brief"></div>');
                            $articleTitle = $('<div id="articleTitle-' + value.id + '" class="article-title"><a class="layui-text" href="/blog/article/' + value.id + '">' + value.title + '</a></div>');
                            $articleMeta = $('<div class="article-meta"><span>' + value.username + '</span><span>' + value.publishTime + '</span></div>');
                            $articleContent = $('<div class="article-content">' + value.content + '</div>');
                            $articleBrief.append($articleTitle).append($articleMeta).append($articleContent);
                            $articleDiv.append($articleCover).append($articleBrief);
                            $list.append($articleDiv);
                        });
                    } else {
                        $list.append(blogConf.ARTICLE_PAGE_CONF.ARTICLE_404)
                    }

                    // 在初次显示分页时 设置分页count（每次都更新分页count亦可）
                    if(isFirstLoadPage){
                        loadPageBar(res.count, limit);
                        isFirstLoadPage = false;
                    }
                }
            }
        })
    }

    exports('blogIndex', {
        init: init
    });
});
