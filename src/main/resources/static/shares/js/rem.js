// 调整字体大小
(function(doc, win) {
    var docEl = doc.documentElement,
        resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
        recalc = function () {
            var baseFontSize = 100; // 根元素基准字体大小
            var designWidth  = 750; // 设计稿宽度
            var clientWidth = docEl.clientWidth;
            if (!clientWidth) return;
            docEl.style.fontSize = baseFontSize * (clientWidth / designWidth) + 'px';

        };
    // Abort if browser does not support addEventListener
    if (!doc.addEventListener) return;
    win.addEventListener(resizeEvt, recalc, false);
    doc.addEventListener('DOMContentLoaded', recalc, false);
})(document, window);

// if (/iP(hone|od|ad)/.test(navigator.userAgent)) {
//     var v = (navigator.appVersion).match(/OS (\d+)_(\d+)_?(\d+)?/),
//         version = parseInt(v[1], 10);
//     if(version >= 8){
//         document.documentElement.classList.add('hairlines')
//     }
// }
