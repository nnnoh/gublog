layui.define(['ajaxUtil', 'cookieUtil', 'adminUtil', 'dateTransfer', 'jquery', 'form', 'layer', 'upload'], function (exports) {
    var ajaxUtil = layui.ajaxUtil;
    var cookieUtil = layui.cookieUtil;
    var adminUtil = layui.adminUtil;
    var dateTransfer = layui.dateTransfer;
    var $ = layui.jquery;
    var form = layui.form;
    var layer = layui.layer;
    var upload = layui.upload;

    var dataDetail = {};
    var zIndex = 20191203;
    var toBeUpload = false;
    var layerIndex;

    function init(param) {
        loadView();

        // 初始化参数
        if (typeof param != 'undefined') {
            dataDetail = param;
        }
    }

    function loadView() {
        $.ajax({
            url: '/admin/subBody/bams/article/articleDetail.html',
            success: function (data) {
                // 弹出详情页
                layerIndex = layer.open({
                    type: 1,
                    title: (dataDetail.updateMode == adminUtil.UPDATE_MODE.NEW ? '新增文章' : '修改文章'),
                    content: data,
                    zIndex: zIndex++,
                    area: [window.innerWidth * 0.9 + 'px', window.innerHeight * 0.9 + 'px'],
                    success: initLayer,
                    cancel: closeLayer
                });
            }
        });
    }

    function initLayer() {
        initView();
        initEvent();
    }

    function initView() {
        // 分类选项
        $.ajax({
            url: '/api/category/list',
            success: function (res) {
                if (res.code == 0) {
                    var $sel = $('#ad-category');
                    $.each(res.data, function (index, value) {
                        var $option = $('<option value=' + value.id + '>' + value.name + '</option>');
                        $sel.append($option);
                    })

                    form.render();
                    if (dataDetail.updateMode == adminUtil.UPDATE_MODE.UPDATE) {
                        form.val("articleDetail", {
                            'categoryId': dataDetail.data.categoryId
                        });
                    }
                } else {
                    alert(res.message);
                }
            }
        });

        // 表单依赖
        form.render();

        // 初始化数据
        if (dataDetail.updateMode == adminUtil.UPDATE_MODE.UPDATE) {
            $("#ad-title").val(dataDetail.data.title == null ? "" : dataDetail.data.title);
            $("#ad-cover").val(dataDetail.data.cover == null ? "" : dataDetail.data.cover);
            $("#ad-content").val(dataDetail.data.content == null ? "" : dataDetail.data.content);
            $("#ad-covername").val(dataDetail.data.cover == null ? "" : dataDetail.data.cover.substr(dataDetail.data.cover.lastIndexOf("/") + 1));
            if (dataDetail.data.status == 2) {
                $("#ad-status").prop("checked", "true");
                // 需重新加载一下
                form.render();
            }
        }

        initMd();
    }

    function initMd(){
        contentMd = editormd("content-md", {
            width: "100%",
            height: 500,
            syncScrolling: "single",
            saveHTMLToTextarea : false,
            placeholder: "请输入内容",
            htmlDecode : "style,script,iframe|on*",
            path: "/common/lib/editor.md/lib/"
        });
    }

    function initEvent() {
        // 题图上传
        upload.render({
            elem: '#uploadCover',
            url: '/admin/file',
            data: { business: 'article-cover' },
            acceptMime: 'image/*',
            auto: false,
            bindAction: "#uploadClickBtn",
            choose: function (obj) {
                //将每次选择的文件追加到文件队列
                var files = obj.pushFile();
                //预读本地文件，如果是多文件，则会遍历。(不支持ie8/9)
                obj.preview(function (index, file, result) {
                    $("#ad-covername").val(file.name);
                })

                toBeUpload = true;
            },
            headers: { 'Authorizration': cookieUtil.getCookie('token') },
            done: function (res, index, upload) {
                if (res.status == 200) {
                    // 重置状态
                    toBeUpload = false;

                    // 删除原题图
                    if ($("#ad-cover").val() != "") {
                        $.ajax({
                            url: "/admin/file",
                            type: "DELETE",
                            data: {
                                url: $("#ad-cover").val()
                            }
                        });
                    }

                    $("#ad-cover").val(res.data.url);

                    // 提交表单
                    saveArticle();
                }
            }
        });

        // 表单提交
        $("#submitBtn").click(function () {
            // todo 表单校验

            if (toBeUpload) {
                // 图片上传后提交表单
                $("#uploadClickBtn").click();
            } else {
                saveArticle();
            }
        });
    }

    function saveArticle() {

        var data = form.val("articleDetail");

        data.userId = cookieUtil.getCookie("userId"),
            data.editTime = dateTransfer.format(new Date(), "yyyy-MM-dd HH:mm:ss");

        var url, type;
        if (dataDetail.updateMode == adminUtil.UPDATE_MODE.NEW) {
            data.createTime = data.editTime;
            dataDetail.updateMode = adminUtil.UPDATE_MODE.UPDATE;

            // 新增 api
            url = "/admin/article";
            type = "POST";
        } else if (dataDetail.updateMode == adminUtil.UPDATE_MODE.UPDATE) {
            data = $.extend(dataDetail.data, data);

            // 更改 api
            url = "/admin/article/" + dataDetail.data.id;
            type = "PUT";
        } else {
            alert("参数配置存在问题！");
            return;
        }

        // checkbox
        if ($("#ad-status")[0].checked) {
            data.status = 2;
        } else {
            data.status = 1;
        }

        if (data.status == 2) {
            data.publishTime = data.editTime;
        }

        ajaxUtil.submitFormWithToken({
            url: url,
            type: type,
            data: data,
            success: ajaxUtil.successCallback(function (res) {
                dataDetail = $.extend(true, dataDetail, { data: res });
                alert("操作成功！");
                layer.title("修改文章", layerIndex);
            })
        }, undefined, true);
    }

    function closeLayer() {
        if (typeof dataDetail.close == 'function') {
            dataDetail.close();
        }
    }

    exports('articleDetail', {
        init: init
    });
});
