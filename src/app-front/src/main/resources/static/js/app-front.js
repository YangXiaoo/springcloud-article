layui.use(['layer', 'form', 'element'], function(){
    var layer = layui.layer
        ,element = layui.element
        ,form = layui.form;
});

$(function(){
    $('body').off('click', '.reply-btn');
    $('body').on("click", '.reply-btn', function(event) {
        var _this = $(this);
        var url = _this.data('url');
        var aid = _this.data("aid");
        var pcid = _this.data('pcid');
        var uid = _this.data("uid");
        var puid = _this.data("puid");
        if (uid == "-1") {
            layer.msg("请先登录", { offset: '100px' });
            return;
        }
        var title = '评论';
        // var index = layer.load(1, {
        //     shade: [0.1,'#fff'] //0.1透明度的白色背景
        // });
        layer.prompt({title:title, formType: 2, offset: '100px'}, function(text, index) {
            layer.close(index);
            var dataStr = jQuery.parseJSON( '{"aid":"'+aid+'","pcid":"'+pcid+'","puid":"'+puid+'","content":"'+text+'","uid":"'+uid+'"}' );
            $.ajax({
                type : "post",
                url : url,
                dataType : 'json',
                data : dataStr,
                success : function(data) {
                    if(data.status == '1'){
                        layer.msg(data.message, { offset: '100px' })
                    }else{
                        layer.msg(data.message, { offset: '100px' });
                    }
                }
            });
        });
    });
});