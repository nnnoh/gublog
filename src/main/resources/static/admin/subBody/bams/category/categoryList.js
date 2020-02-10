layui.define(['adminUtil', 'comList', 'jquery', 'table'], function (exports) {
    var comList = layui.comList;
    var adminUtil = layui.adminUtil;
    var $ = layui.jquery;

    function init() {
        var conditionDom = '<div class="layui-inline"><label class="layui-form-label">分类名</label><div class="layui-input-inline">' +
            '<input type="text" name="name" id="cac-name" required lay-verify="required" placeholder="查询分类名" autocomplete="off" class="layui-input"></div></div>';
        comList.init({
            listUrl: "/api/category/list",
            deleteUrlPrefix: "/admin/category/",
            cols: [[
                { field: 'id', title: 'ID', sort: true, fixed: 'left' }
                , { field: 'name', title: '分类名', sort: true }
                , { field: 'createTime', title: '创建时间', sort: true }
                , { field: 'updateTime', title: '更新时间', sort: true }
                , { field: 'operate', title: '操作', minWidth: 150, toolbar: "#tableTrTool" }
            ]],
            conditionDom: conditionDom,
            getWhereParam: function(){
                var whereParam = {
                    name:undefined
                };
                var value = $("#cac-name").val();
                if (value != "") {
                    whereParam.name = value;
                }
                return whereParam;
            },
            detailObj: "categoryDetail"
        });
    }

    exports('categoryList', {
        init: init
    });
});