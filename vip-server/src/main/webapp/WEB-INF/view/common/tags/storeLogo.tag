@/*
    头像参数的说明:
    name : 名称
    id : 头像的id
@*/
<div class="form-group">
    <label class="col-sm-${col} control-label head-scu-label">${name}</label>
    <div class="col-sm-2 auto-width">
        <div id="${id}PreId">
            <div><img width="100px" height="100px"
                @if(isEmpty(avatarImg)){
                      src="${ctxPath}/static/img/girl.gif"></div>
                @}else{
                      src="${avatarImg}"></div>
                @}
        </div>
    </div>
    <div class="col-sm-2 no-left">
        <div class="head-scu-btn upload-btn" id="${id}BtnId">
            <div class="uploadBtn">&nbsp;上传</div>
        </div>
        <div class="progress progress-striped both-no" id="progressTipArea" style="display:none;">
	        <div id="progressBar" style="width: 0%" aria-valuemax="100" aria-valuemin="0" aria-valuenow="0" role="progressbar" class="progress-bar progress-bar-info">
	        </div>
	    </div>
    </div>
    <input type="hidden" id="${id}" value="${avatarImg!}"/>
</div>
@if(isNotEmpty(underline) && underline == 'true'){
    <div class="hr-line-dashed"></div>
@}


