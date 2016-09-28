var fullWidth = 1920; // px

function initResizeWindowHandler() {
    window.onresize = resize;
}

function resize() {
    document.body.setAttribute("style", "transform: scale(" + document.documentElement.offsetWidth / fullWidth + ")")
}
