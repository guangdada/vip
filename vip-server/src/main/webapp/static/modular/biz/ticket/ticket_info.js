/**
 * 初始化小票详情对话框
 */
var TicketInfoDlg = {
    ticketInfoData : {}
};

/**
 * 清除数据
 */
TicketInfoDlg.clearData = function() {
    this.ticketInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TicketInfoDlg.set = function(key, val) {
    this.ticketInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TicketInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
TicketInfoDlg.close = function() {
    parent.layer.close(window.parent.Ticket.layerIndex);
}

/**
 * 收集数据
 */
TicketInfoDlg.collectData = function() {
	var specType = $("input[name='specType']:checked").val();
    this.set('id').set('specType',specType).set('title').set('remark');
}

/**
 * 提交添加
 */
TicketInfoDlg.addSubmit = function() {
    this.clearData();
    this.collectData();
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/ticket/add", function(data){
        Feng.success("添加成功!");
        window.parent.Ticket.table.refresh();
        TicketInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.ticketInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
TicketInfoDlg.editSubmit = function() {
    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/ticket/update", function(data){
        Feng.success("修改成功!");
        window.parent.Ticket.table.refresh();
        TicketInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.ticketInfoData);
    ajax.start();
}

$(function() {
	Feng.initValidator("ticketForm", TicketInfoDlg.validateFields);
});
