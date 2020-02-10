layui.define(['adminUtil', 'ajaxUtil','form', 'jquery', 'table'], function (exports) {    
    var adminUtil = layui.adminUtil;
    var table = layui.table;
    var form = layui.form;
    var $ = layui.jquery;

    var tableIndex;

    var STATUS_DICT = {
        1: "暂存",
        2: "发布"
    };

    function init() {
         // 分类查询选项
         $.ajax({
            url: '/api/category/list',
            success: function(res){
                if(res.code==0){
                    var $sel = $('#adc-category');
                    $.each(res.data,function(index,value){
                        var $option = $('<option value='+value.id+'>'+value.name+'</option>');
                        $sel.append($option);
                    })

                    form.render();
                }else{
                    alert(res.message);
                }
            }
        });

        initTable();
        initEvent();
    }

    function initTable() {
        // 渲染表格
        tableIndex = table.render({
            elem: '#articleTbl'
            , url: '/api/article/list' //数据接口
            , page: true //开启分页
            , height: $("#articleMainDiv").height() - $("#articleListTool").height() - 10
            , autoSort: false //禁用前端自动排序。
            , cols: [[ //表头
                { field: 'id', title: 'ID', sort: true, fixed: 'left' }
                , { field: 'title', title: '标题' }
                , { field: 'username', title: '姓名', sort: true }
                , { field: 'category', title: '分类' }
                , { field: 'cover', title: '题图' }
                , { field: 'content', title: '内容' }
                , { field: 'status', title: '状态', sort: true, templet: function (d) { return STATUS_DICT[d.status]; } }
                , { field: 'publishTime', title: '发布时间', sort: true }
                , { field: 'editTime', title: '编辑时间', sort: true }
                , { field: 'createTime', title: '创建时间', sort: true }
                , { field: 'operate', title: '操作', minWidth: 150, toolbar: "#tableListTool" }
            ]]
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

                    $.ajax({
                        url: "/admin/article/" + data.id,
                        type: "DELETE"
                    });
                });
            } else if (layEvent === 'edit') { //编辑
                //  打开详细页
                var param = { data: data };
                param.updateMode = adminUtil.UPDATE_MODE.UPDATE;
                param.close = function () {
                    tableIndex.reload({});
                };
                openDetail(param);
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

    function initEvent() {
        // 条件查询
        $("#queryArticleBtn").click(function () {
            var whereParam = {
                title:null,
                username:null,
                categoryId:null
            };

            var formVal = form.val("articleList");
            // 标题
            if (formVal.title != "") {
                whereParam.title = formVal.title;
            }
            // 作者
            if (formVal.username != "") {
                whereParam.username = formVal.username;
            }
            // 分类id
            if (formVal.categoryId != "") {
                whereParam.categoryId = formVal.categoryId;
            }

            tableIndex.reload({
                where: whereParam
            });
        });

        // 重置
        $("#resetArticleBtn").click(function () {
            // 清空输入框
            $("#adc-title").val("");
            $("#adc-author").val("");
            tableIndex.reload({
                initSort: { field: 'id', type: null }
                , where: null
            });
        });

        // 新增文章
        $("#addArticleBtn").click(function () {
            var param = {};
            param.updateMode = adminUtil.UPDATE_MODE.NEW;
            param.close = function () {
                tableIndex.reload({});
            };
            openDetail(param);
        });
    }

    function openDetail(param) {
        layui.use('articleDetail', function (args) {
            layui.articleDetail.init(param);
        });
    }

    exports('articleList', {
        init: init
    });
});