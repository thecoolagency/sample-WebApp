window.addEventListener("load", function() { window. scrollTo(0, 0); });

function show2() {
    if (document.all&&document.getElementById) {}
    else { 
        thelement = document.getElementById? document.getElementById("clockTime"): document.all.clockTime;
        var Digital = new Date();
        var hours = Digital.getHours();
        var minutes = Digital.getMinutes();
        var seconds = Digital.getSeconds();
        var dn = "PM";
        if (hours < 12) { dn = "AM"; }
        if (hours > 12) { hours = hours - 12; }
        if (hours === 0) { hours = 12; }
        if (minutes <= 9) { minutes = "0"+minutes; }
        if (seconds <= 9) { seconds = "0"+seconds; }
        var ctime = hours+":"+minutes+":"+seconds+" "+dn;
        thelement.innerHTML = ctime;
        setTimeout("show2()", 1000); }
} window.onload = show2;

function openClose() {
    if(document.getElementById('sideNav').checked) {
        document.getElementById('appMain').setAttribute("class", "content");
        document.getElementById('cgNav').setAttribute("class", "side-nav"); }
    else {
        document.getElementById('appMain').setAttribute("class", "content full");
        document.getElementById('cgNav').setAttribute("class", "side-nav close"); }
}

function dropNew() {
    if(document.getElementById('dropTop').checked) {
        document.getElementById('popTop').setAttribute("class", "grid"); }
    else {
        document.getElementById('popTop').setAttribute("class", "grid dropped"); }
}

var a=document.getElementsByTagName("a");
for(var i=0;i<a.length;i++) {
    a[i].onclick=function()
    {
        window.location=this.getAttribute("href");
        return false
    }
}



/*! iNoBounce - v0.1.0
* https://github.com/lazd/iNoBounce/
* Copyright (c) 2013 Larry Davis <lazdnet@gmail.com>; Licensed BSD */
(function(global) {
    // Stores the Y position where the touch started
    var startY = 0;

    // Store enabled status
    var enabled = false;

    var handleTouchmove = function(evt) {
        // Get the element that was scrolled upon
        var el = evt.target;

        // Check all parent elements for scrollability
        while (el !== document.body) {
            // Get some style properties
            var style = window.getComputedStyle(el);
            var scrolling = style.getPropertyValue('-webkit-overflow-scrolling');
            var overflowY = style.getPropertyValue('overflow-y');
            var height = parseInt(style.getPropertyValue('height'), 10);

            // Determine if the element should scroll
            var isScrollable = scrolling === 'touch' && (overflowY === 'auto' || overflowY === 'scroll');
            var canScroll = el.scrollHeight > el.offsetHeight;

            if (isScrollable && canScroll) {
                // Get the current Y position of the touch
                var curY = evt.touches ? evt.touches[0].screenY : evt.screenY;

                // Determine if the user is trying to scroll past the top or bottom
                // In this case, the window will bounce, so we have to prevent scrolling completely
                var isAtTop = (startY <= curY && el.scrollTop === 0);
                var isAtBottom = (startY >= curY && el.scrollHeight - el.scrollTop === height);

                // Stop a bounce bug when at the bottom or top of the scrollable element
                if (isAtTop || isAtBottom) {
                    evt.preventDefault();
                }

                // No need to continue up the DOM, we've done our job
                return;
            }

            // Test the next parent
            el = el.parentNode;
        }

        // Stop the bouncing -- no parents are scrollable
        evt.preventDefault();
    };

    var handleTouchstart = function(evt) {
        // Store the first Y position of the touch
        startY = evt.touches ? evt.touches[0].screenY : evt.screenY;
    };

    var enable = function() {
        // Listen to a couple key touch events
        window.addEventListener('touchstart', handleTouchstart, false);
        window.addEventListener('touchmove', handleTouchmove, false);
        enabled = true;
    };

    var disable = function() {
        // Stop listening
        window.removeEventListener('touchstart', handleTouchstart, false);
        window.removeEventListener('touchmove', handleTouchmove, false);
        enabled = false;
    };

    var isEnabled = function() {
        return enabled;
    };

    // Enable by default if the browser supports -webkit-overflow-scrolling
    // Test this by setting the property with JavaScript on an element that exists in the DOM
    // Then, see if the property is reflected in the computed style
    var testDiv = document.createElement('div');
    document.documentElement.appendChild(testDiv);
    testDiv.style.WebkitOverflowScrolling = 'touch';
    var scrollSupport = 'getComputedStyle' in window && window.getComputedStyle(testDiv)['-webkit-overflow-scrolling'] === 'touch';
    document.documentElement.removeChild(testDiv);

    if (scrollSupport) {
        enable();
    }

    // A module to support enabling/disabling iNoBounce
    var iNoBounce = {
        enable: enable,
        disable: disable,
        isEnabled: isEnabled
    };

    if (typeof module !== 'undefined' && module.exports) {
        // Node.js Support
        module.exports = iNoBounce;
    }
    if (typeof global.define === 'function') {
        // AMD Support
        (function(define) {
            define(function() { return iNoBounce; });
        }(global.define));
    }
    else {
        // Browser support
        global.iNoBounce = iNoBounce;
    }
}(this));
            