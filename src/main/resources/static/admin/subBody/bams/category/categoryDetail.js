layui.define(['ajaxUtil', 'cookieUtil', 'adminUtil', 'dateTransfer', 'jquery', 'form', 'layer'], function (exports) {
    var ajaxUtil = layui.ajaxUtil;
    var cookieUtil = layui.cookieUtil;
    var adminUtil = layui.adminUtil;
    var dateTransfer = layui.dateTransfer;
    var $ = layui.jquery;
    var form = layui.form;
    var layer = layui.layer;

    var dataDetail = {};
    var zIndex = 20191205;
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
            url: '/admin/subBody/bams/category/categoryDetail.html',
            success: function (data) {
                // 弹出详情页
                layerIndex = layer.open({
                    type: 1,
                    title: (dataDetail.updateMode == adminUtil.UPDATE_MODE.NEW?'新增分类':'修改分类'),
                    content: data,
                    zIndex: zIndex++,
                    area: [],
                    success: initLayer,
                    cancel : closeLayer
                });
            }
        });
    }

    function initLayer() {
        initView();
        initEvent();
    }

    function initView() {
      

        // 初始化数据
        if (dataDetail.updateMode == adminUtil.UPDATE_MODE.UPDATE) {
            $("#cd-name").val(dataDetail.data.name == null ? "" : dataDetail.data.name);
        }
    }

    function initEvent() {
        // 表单提交
        $("#submitBtn").click(function () {
            // todo 表单校验

            saveCategory();
            
        });
    }

    function saveCategory() {

        var data = {
            userId: cookieUtil.getCookie("userId"),
            updateTime: dateTransfer.format(new Date(), "yyyy-MM-dd HH:mm:ss"),
        }

        var url, type;
        if (dataDetail.updateMode == adminUtil.UPDATE_MODE.NEW) {
            data.createTime = data.updateTime;
            dataDetail.updateMode = adminUtil.UPDATE_MODE.UPDATE;

            // 新增 api
            url = "/admin/category";
            type = "POST";
        } else if (dataDetail.updateMode == adminUtil.UPDATE_MODE.UPDATE) {
            data = $.extend(dataDetail.data, data);

            // 更改 api
            url = "/admin/category/" + dataDetail.data.id;
            type = "PUT";
        } else {
            alert("参数配置存在问题！");
            return;
        }


        ajaxUtil.submitFormWithToken({
            url: url,
            type: type,
            data: data,
            success: ajaxUtil.successCallback(function (res) {
                dataDetail = $.extend(true, dataDetail, {data:res});
                alert("操作成功！");
                layer.title("修改分类",layerIndex);
            })
        }, "#categoryDetailMainDiv .layui-form input", true);
    }

    function closeLayer() {
        if(typeof dataDetail.close == 'function'){
            dataDetail.close();
        }
    }

    exports('categoryDetail', {
        init: init
    });
});
