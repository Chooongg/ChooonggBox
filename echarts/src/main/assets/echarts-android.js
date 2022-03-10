var charts;

$(function(){
    getECharts();
})

function getECharts(isDark){
    if(charts != undefined){
        return charts;
    }
    if(isDark == true){
        charts = echarts.init(document.getElementById('main', 'dark'));
    }else{
        charts = echarts.init(document.getElementById('main'));
    }
    return charts;
}

function setOption(echartsJson){
    Android.log(echartsJson);
    var option = JSON.parse(echartsJson);
    option = preTask(option);
    getECharts(false).setOption(option);
}

function preTask(obj) {
    var result;
    if(typeof(obj) == 'object') {
        if(obj instanceof Array) {
            result = new Array();
            for (var i = 0, len = obj.length; i < len ; i++) {
                 result.push(preTask(obj[i]));
            }
            return result;
        } else if(obj instanceof RegExp){
            return obj;
        } else {
            result = new Object();
            for (var prop in obj) {
                result[prop] = preTask(obj[prop]);
            }
            return result;
        }
    } else if(typeof(obj) == 'string'){
        try {
            if(typeof(eval(obj)) == 'function'){
                return eval(obj);
            } else if (typeof(eval(obj) == 'object') && (eval(obj) instanceof Array || eval(obj) instanceof CanvasGradient)) {
                return eval(obj);
            }
        }catch(e) {
            return obj;
        }
        return obj;
    } else {
        return obj;
    }
}

function myChartShowLoading() {
    getMyChart().showLoading();
}

function myChartHideLoading() {
    getMyChart().hideLoading();
}