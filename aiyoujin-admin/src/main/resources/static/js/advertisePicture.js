layui.config({
	base : "../layui/lay/modules/"
}).use(['flow','form','layer','upload'],function(){
    var flow = layui.flow,
        form = layui.form,
        upload = layui.upload,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        $ = layui.jquery;

        //流加载图片
        $("#Images").html("");
        var imgNums = 15;  //单页显示图片数量
        flow.load({
            elem: '#Images', //流加载容器
            done: function(page, next){ //加载下一页
                $.get("/advertise/queryAdversePictureList.html",function(obj){
                    //
                    var data = eval('(' + obj + ')');
                    var imgList = [];//模拟插入
                    var maxPage = imgNums*page < data.length ? imgNums*page : data.length;
                    for(var i=imgNums*(page-1); i<maxPage; i++){
                        imgList.push('<li><img src="'+ data[i].path +'"><div class="operate"><div class="check"><input type="checkbox" name="belle" lay-filter="choose" lay-skin="primary" title="'+(data[i].name==null)?"未命名":data[i].name+'" picId="'+data[i].id+'"></div><i picId="'+data[i].id+'" class="layui-icon img_modify">&#xe642;</i><i picId="'+data[i].id+'" class="layui-icon img_del">&#xe640;</i></div></li>')
                    }
                    next(imgList.join(''), page < (data.length/imgNums));
                    form.render();
                });
            }
        });

    var uploadInst = upload.render({
        elem: '#addPic'
        ,url: '/advertise/uploadPicture.html'
        /*,data:{'productId':$("#productId").val()}*/
        ,before: function(obj){ //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
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
            location.href="advertisePicture.html";

        }
    });

    $("body").on("click",".img_modify",function () {
        var id = $(this).attr("picId");
        //Ajax获取
        form_html(id,'编辑图片信息');
    });

    function form_html(id,title){
        $.post('/advertise/advertisePictureAdd.html', {id:id}, function(data){
            layer.open({
                type: 1,
                title: title
                ,area: ['650px', '300px']
                ,shade: 0
                ,content: data
                ,yes: function(index, layero){
                    layer.close(index);
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
            $.post('/advertise/advertisePictureDel.html', {ids:ids}, function(result){
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
                $.post('/advertise/advertisePictureDel.html', {ids:ids}, function(result){
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