function show2(){if(document.all&&document.getElementById);else{thelement=document.getElementById?document.getElementById("clockTime"):document.all.clockTime;var e=new Date,t=e.getHours(),o=e.getMinutes(),n=e.getSeconds(),c="PM";12>t&&(c="AM"),t>12&&(t-=12),0===t&&(t=12),9>=o&&(o="0"+o),9>=n&&(n="0"+n);var d=t+":"+o+":"+n+" "+c;thelement.innerHTML=d,setTimeout("show2()",1e3)}}function openClose(){document.getElementById("sideNav").checked?(document.getElementById("appMain").setAttribute("class","content"),document.getElementById("cgNav").setAttribute("class","side-nav")):(document.getElementById("appMain").setAttribute("class","content full"),document.getElementById("cgNav").setAttribute("class","side-nav close"))}function dropNew(){document.getElementById("dropTop").checked?document.getElementById("popTop").setAttribute("class","grid"):document.getElementById("popTop").setAttribute("class","grid dropped")}window.addEventListener("load",function(){window.scrollTo(0,0)}),window.onload=show2;for(var a=document.getElementsByTagName("a"),i=0;i<a.length;i++)a[i].onclick=function(){return window.location=this.getAttribute("href"),!1};!function(e){var t=0,o=!1,n=function(e){for(var o=e.target;o!==document.body;){var n=window.getComputedStyle(o),c=n.getPropertyValue("-webkit-overflow-scrolling"),d=n.getPropertyValue("overflow-y"),i=parseInt(n.getPropertyValue("height"),10),l="touch"===c&&("auto"===d||"scroll"===d),r=o.scrollHeight>o.offsetHeight;if(l&&r){var u=e.touches?e.touches[0].screenY:e.screenY,s=u>=t&&0===o.scrollTop,a=t>=u&&o.scrollHeight-o.scrollTop===i;return void((s||a)&&e.preventDefault())}o=o.parentNode}e.preventDefault()},c=function(e){t=e.touches?e.touches[0].screenY:e.screenY},d=function(){window.addEventListener("touchstart",c,!1),window.addEventListener("touchmove",n,!1),o=!0},i=function(){window.removeEventListener("touchstart",c,!1),window.removeEventListener("touchmove",n,!1),o=!1},l=function(){return o},r=document.createElement("div");document.documentElement.appendChild(r),r.style.WebkitOverflowScrolling="touch";var u="getComputedStyle"in window&&"touch"===window.getComputedStyle(r)["-webkit-overflow-scrolling"];document.documentElement.removeChild(r),u&&d();var s={enable:d,disable:i,isEnabled:l};"undefined"!=typeof module&&module.exports&&(module.exports=s),"function"==typeof e.define?!function(e){e(function(){return s})}(e.define):e.iNoBounce=s}(this);