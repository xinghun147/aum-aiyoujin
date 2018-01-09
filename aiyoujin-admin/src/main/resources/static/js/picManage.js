layui.config({
	base : "../layui/lay/modules/"
}).use(['flow','form','layer','upload'],function(){
    var flow = layui.flow,
        form = layui.form,
        upload = layui.upload,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        $ = layui.jquery;

    //加载产品列表
    $.get("/pic/queryProductList.html",function(data){
        //模拟插入
        var html = "<li><h2>产品列表</h2></li>";
        var obj = eval('(' + data + ')');
        for(var i = 0;i<obj.length;i++){
            var a = obj[i];
            var b = obj[i];
            html += '<li class="site-tree-noicon ">'
                +'<a href="#" class="showPic" productId="'+obj[i].id+'">'
                +'<cite>'+obj[i].name+'</cite>'
                +'<em>'+obj[i].code+'</em>'
                +'</a></li>';
        }
        $("#prdList").html(html);
    });

    var productId = $("#productId").val();
    if(productId != null && productId != ''){
        showPic(productId);
    }

    $("body").on("click",".showPic",function(){
        var id = $(this).attr("productId");
        $("#productId").val(id);
        location.href="picManage.html?id="+$("#productId").val();
    });
    
    function showPic(id) {
        //流加载图片
        $("#Images").html("");
        var imgNums = 15;  //单页显示图片数量
        flow.load({
            elem: '#Images', //流加载容器
            done: function(page, next){ //加载下一页
                $.get("/pic/queryProductPictures.html?productId="+id,function(obj){
                    //
                    var data = eval('(' + obj + ')');
                    var imgList = [];//模拟插入
                    var maxPage = imgNums*page < data.length ? imgNums*page : data.length;
                    for(var i=imgNums*(page-1); i<maxPage; i++){
                        imgList.push('<li><img src="'+ data[i].path +'"><div class="operate"><div class="check"><input type="checkbox" name="belle" lay-filter="choose" lay-skin="primary" title="'+typeToStr(data[i].type)+'" picId="'+data[i].id+'"></div><i picId="'+data[i].id+'" class="layui-icon img_modify">&#xe642;</i><i picId="'+data[i].id+'" class="layui-icon img_del">&#xe640;</i></div></li>')
                    }
                    next(imgList.join(''), page < (data.length/imgNums));
                    form.render();
                });
            }
        });
    }

    function typeToStr(type) {
        if(type == 0){
            return "缩略图";
        }else if(type == 1){
            return "大图";
        }else if(type ==2){
            return "中图";
        }else{
            return "未命名";
        }
    }

    console.log($("#productId").val());
    var uploadInst = upload.render({
        elem: '#addPic'
        ,url: '/pic/uploadPicture.html'
        ,data:{'productId':$("#productId").val()}
        ,before: function(obj){ //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
            console.log($("#productId").val());
            layer.load('图片正在上传中，请稍后......',{icon: 16,time:false,shade:0.8}); //上传loading
        }
        ,done: function(res){
            //如果上传失败
            if(res.code > 0){
                layer.closeAll('loading'); //关闭loading
                return layer.msg('上传失败');
            }
            layer.closeAll('loading'); //关闭loading
            //上传成功
            location.href="picManage.html?id="+$("#productId").val();

        }
        ,error: function(index, upload){
            layer.closeAll('loading');
            return layer.msg('上传失败');
        }
    });

    $("body").on("click",".img_modify",function () {
        var id = $(this).attr("picId");
        //Ajax获取
        form_html(id,'编辑图片信息');
    });

    function form_html(id,title){
        $.post('/pic/picEdit.html', {id:id}, function(data){
            layer.open({
                type: 1,
                title: title
                ,area: ['550px', '300px']
                ,shade: 0
                ,content: data
                ,yes: function(index, layero){
                    layer.close(index);
                },
                end: function () {
                	 location.href="/pic/picManage.html?id="+$("#productId").val();
                }
            });
        });
    };


    //删除单张图片
    $("body").on("click",".img_del",function(){
        var _this = $(this);
        layer.confirm('确定删除图片"'+_this.siblings().find("input").attr("title")+'"吗？',{icon:3, title:'提示信息'},function(index){
            var ids = [];
            ids.push(_this.attr("picId"));
            $.post('/pic/delProductPic.html', {ids:ids}, function(result){
                if(result != null){
                    if(result == 0) {
                        _this.parents("li").hide(1000);
                        setTimeout(function(){_this.parents("li").remove();},950);
                        return ;
                    }
                }
                layer.msg('删除图片失败');
            });
            layer.close(index);
        });
    })

    //全选
    form.on('checkbox(selectAll)', function(data){
        var child = $("#Images li input[type='checkbox']");
        child.each(function(index, item){
            item.checked = data.elem.checked;
        });
        form.render('checkbox');
    });

    //通过判断文章是否全部选中来确定全选按钮是否选中
    form.on("checkbox(choose)",function(data){
        var child = $(data.elem).parents('#Images').find('li input[type="checkbox"]');
        var childChecked = $(data.elem).parents('#Images').find('li input[type="checkbox"]:checked');
        if(childChecked.length == child.length){
            $(data.elem).parents('#Images').siblings("blockquote").find('input#selectAll').get(0).checked = true;
        }else{
            $(data.elem).parents('#Images').siblings("blockquote").find('input#selectAll').get(0).checked = false;
        }
        form.render('checkbox');
    })

    //批量删除
    $(".batchDel").click(function(){
        var $checkbox = $('#Images li input[type="checkbox"]');
        var $checked = $('#Images li input[type="checkbox"]:checked');
        if($checkbox.is(":checked")){
            layer.confirm('确定删除选中的图片？',{icon:3, title:'提示信息'},function(index){
                var index = layer.msg('删除中，请稍候',{icon: 16,time:false,shade:0.8});
                    //删除数据
                var ids = [];
                $checked.each(function(){
                    ids.push($(this).attr("picId"));
                });
                $.post('/pic/delProductPic.html', {ids:ids}, function(result){
                    if(result != null){
                        if(result == 0) {
                            $checked.each(function(){
                                $(this).parents("li").hide(1000);
                                setTimeout(function(){$(this).parents("li").remove();},950);
                                $('#Images li input[type="checkbox"]').prop("checked",false);
                                form.render();
                                layer.close(index);
                                layer.msg("删除成功");
                            });
                            return ;
                        }
                    }
                    layer.msg('删除图片失败');
                });
            })
        }else{
            layer.msg("请选择需要删除的图片");
        }
    })

})