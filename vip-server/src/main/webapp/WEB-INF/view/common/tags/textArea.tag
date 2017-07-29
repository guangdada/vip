@/*
    表单中textarea标签中各个参数的说明:
    id : textarea的id
    name : textarea的名称
    readonly : readonly属性
    rows : rows属性
    cols : cols属性
    style : 附加的css属性
@*/
<div class="form-group">
    <label class="col-sm-3 control-label">${name}</label>
    <div class="col-sm-9">
        <textarea class="form-control" id="${id}" name="${id}"
               @if(isNotEmpty(value)){
                    value="${tool.dateType(value)}"
               @}
               @if(isNotEmpty(readonly)){
                    readonly="${readonly}"
               @}
               @if(isNotEmpty(style)){
                    style="${style}"
               @}
               @if(isNotEmpty(disabled)){
                    disabled="${disabled}"
               @}
               @if(isNotEmpty(rows)){
                    rows="${rows}"
               @}
               @if(isNotEmpty(cols)){
                    cols="${cols}"
               @}
        ></textarea>
    </div>
</div>
@if(isNotEmpty(underline) && underline == 'true'){
    <div class="hr-line-dashed"></div>
@}


