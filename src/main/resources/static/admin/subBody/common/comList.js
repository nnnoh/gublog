/**
 * 通用列表页面
 */
layui.define(['adminUtil', 'ajaxUtil', 'jquery', 'table'], function (exports) {
    var adminUtil = layui.adminUtil;
    var ajaxUtil = layui.ajaxUtil;
    var table = layui.table;
    var $ = layui.jquery;

    var tableIndex;
    var initParam;


    /**
     * 初始化
     * @param {*} param
     *      {
     *          listUrl: "",    // 表格数据 url
     *          deleteUrlPrefix: "",    // 删除数据 url
     *          cols: [[]],     // 表头参数
     *          conditionDom: "",   // 查询条件框 html
     *          getWhereParam: function(){},    // 获取查询条件参数方法
     *          detailObj: ""   // 详细页对象名
     *      }
     */
    function init(param) {
        initParam = param;

        loadDom();
        initTable();
        initBtnEvent();
    }

    function loadDom() {
        // 查询条件框
        $("#listCondition").prepend(initParam.conditionDom);
    }

    function initTable() {
        // 渲染表格
        tableIndex = table.render({
            elem: '#tbl'
            , url: initParam.listUrl //数据接口
            , page: true //开启分页
            , height: $("#mainDiv").height() - $("#listCondition").height() - 10
            , autoSort: false //禁用前端自动排序。
            , cols: initParam.cols
        });

        // 监听工具条 操作
        table.on('tool(test)', function (obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的 DOM 对象（如果有的话）

            if (layEvent === 'del') { //删除
                layer.confirm('真的删除行么', function (index) {
                    obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                    layer.close(index);
                    //向服务端发送删除指令

                    ajaxUtil.submitFormWithToken({
                        url: initParam.deleteUrlPrefix + data.id,
                        type: "DELETE"
                    }, undefined, true);
                });
            } else if (layEvent === 'edit') { //编辑
                //  打开详细页
                var dataDetail = { data: data };
                dataDetail.updateMode = adminUtil.UPDATE_MODE.UPDATE;
                dataDetail.close = function () {
                    tableIndex.reload({});
                };
                openDetail(dataDetail);
            }
        });

        // 监听排序事件
        table.on('sort(test)', function (obj) { //注：sort 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
            // console.log(obj.field); //当前排序的字段名
            // console.log(obj.type); //当前排序类型：desc（降序）、asc（升序）、null（空对象，默认排序）
            // console.log(this); //当前排序的 th 对象

            //尽管我们的 table 自带排序功能，但并没有请求服务端。
            //有些时候，你可能需要根据当前排序的字段，重新向服务端发送请求，从而实现服务端排序，如：
            tableIndex.reload({
                initSort: obj //记录初始排序，如果不设的话，将无法标记表头的排序状态。
                , where: { //请求参数（注意：这里面的参数可任意定义，并非固定的格式）
                    sort: obj.field //排序字段
                    , order: obj.type //排序方式
                }
            });
        });
    }

    function initBtnEvent() {
        // 条件查询
        $("#queryBtn").click(function () {
            if (typeof initParam.getWhereParam == 'function') {
                var whereParam = initParam.getWhereParam();
                tableIndex.reload({
                    where: whereParam
                });
            }
        });

        // 重置
        $("#resetBtn").click(function () {
            // 清空输入框
            $("#listCondition input").val("")
            tableIndex.reload({
                initSort: { field: 'id', type: null }
                , where: null
            });
        });

        // 新增文章
        $("#addBtn").click(function () {
            var dataDetail = {};
            dataDetail.updateMode = adminUtil.UPDATE_MODE.NEW;
            dataDetail.close = function () {
                tableIndex.reload({});
            };
            openDetail(dataDetail);
        });
    }

    function openDetail(dataDetail) {
        layui.use(initParam.detailObj, function (args) {
            layui[initParam.detailObj].init(dataDetail);
        });
    }

    exports('comList', {
        init: init
    });
});