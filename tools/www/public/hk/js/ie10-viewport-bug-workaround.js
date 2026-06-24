/* IE10 viewport hack for Surface/desktop Windows 8 bug */
(function () {
  if (navigator.userAgent.match(/IEMobile\/10\.0/)) {
    var msViewportStyle = document.createElement("style");
    msViewportStyle.appendChild(document.createTextNode("@-ms-viewport{width:auto!important}"));
    document.head.appendChild(msViewportStyle);
  }
}());
