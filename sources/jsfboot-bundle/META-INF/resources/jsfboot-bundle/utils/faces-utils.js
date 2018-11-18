
/*关闭弹出的primefaces窗口，用于判断当前输入验证与后台是否执行成功，如果成功就关闭窗口，否则抖动窗口
 * 使用方法：后台bean方法：当bean方法执行成功后调用 FacesUtils.setAjaxHaveCompleted(true);
 * 			前台按钮事件：oncomplete="completeCloseDialog('moveFileDlg',xhr,status,args);"
 * dlgId 窗口id
 * xhr, status, args 标准参数 */
function completeCloseDialog(dlgId, xhr, status, args) {
    if(args.validationFailed || !args.allowCloseDialog) {
        PF(dlgId).jq.effect("shake", {times:5}, 100);
    }
    else {
        PF(dlgId).hide();
    }
}

/**
 * 日期组件汉化
 */
PrimeFaces.locales['cn'] = {
    closeText: '关闭',
    prevText: '上月',
    nextText: '下月',
    currentText: '今天',
    monthNames: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],
    monthNamesShort: ['一','二','三','四','五','六','七','八','九','十','十一','十二'],
    dayNames: ['星期天','星期一','星期二','星期三','星期四','星期五','星期六'],
    dayNamesShort: ['星期天','星期一','星期二','星期三','星期四','星期五','星期六'],
    dayNamesMin: ['日','一','二','三','四','五','六'],
    weekHeader: 'Hf',
    firstDay: 1,
    isRTL: false,
    showMonthAfterYear: true,
    yearSuffix: '',
    month: '月',
    week: '星期',
    day: '天',
    allDayText : '全天'
};