;(function(window) {

  var svgSprite = '<svg>' +
    '' +
    '<symbol id="icon-wode" viewBox="0 0 1024 1024">' +
    '' +
    '<path d="M768 300.816c0-141.152-114.848-256-256-256s-256 114.848-256 256 114.848 256 256 256S768 441.968 768 300.816zM512 524.816c-123.52 0-224-100.48-224-224s100.48-224 224-224 224 100.48 224 224S635.52 524.816 512 524.816z"  ></path>' +
    '' +
    '<path d="M354.976 562.24c-2.336 1.136-232.96 117.52-232.96 398.352 0 8.848 7.168 16 16 16s16-7.152 16-16c0-260.448 206.288-365.312 215.056-369.648 7.92-3.904 11.184-13.504 7.28-21.424C372.448 561.616 362.88 558.352 354.976 562.24z"  ></path>' +
    '' +
    '<path d="M669.024 564.832c-7.952-3.888-17.504-0.624-21.392 7.296-3.904 7.92-0.64 17.52 7.28 21.424 8.784 4.336 215.056 109.184 215.056 369.648 0 8.848 7.152 16 16 16s16-7.152 16-16C901.984 682.336 671.36 565.968 669.024 564.832z"  ></path>' +
    '' +
    '</symbol>' +
    '' +
    '<symbol id="icon-jifen" viewBox="0 0 1024 1024">' +
    '' +
    '<path d="M512 512C758.327152 512 960 414.898259 960 288 960 161.101741 758.327152 64 512 64 265.672848 64 64 161.101741 64 288 64 414.898259 265.672848 512 512 512L512 512ZM512 480C281.153128 480 96 390.852197 96 288 96 185.147803 281.153128 96 512 96 742.846872 96 928 185.147803 928 288 928 390.852197 742.846872 480 512 480ZM64 512C64 638.898259 265.672848 736 512 736 758.327152 736 960 638.898259 960 512 960 503.163445 952.836555 496 944 496 935.163445 496 928 503.163445 928 512 928 614.852197 742.846872 704 512 704 281.153128 704 96 614.852197 96 512 96 503.163445 88.836556 496 80 496 71.163444 496 64 503.163445 64 512ZM64 736C64 862.898259 265.672848 960 512 960 758.327152 960 960 862.898259 960 736 960 727.163445 952.836555 720 944 720 935.163445 720 928 727.163445 928 736 928 838.852197 742.846872 928 512 928 281.153128 928 96 838.852197 96 736 96 727.163445 88.836556 720 80 720 71.163444 720 64 727.163445 64 736Z"  ></path>' +
    '' +
    '</symbol>' +
    '' +
    '<symbol id="icon-shouye" viewBox="0 0 1024 1024">' +
    '' +
    '<path d="M949.082218 519.343245 508.704442 107.590414 68.326667 518.133697c-8.615215 8.03193-9.096169 21.538549-1.043772 30.144554 8.043187 8.599865 21.566178 9.085936 30.175253 1.035586l411.214573-383.337665 411.232992 384.505257c4.125971 3.854794 9.363252 5.760191 14.5903 5.760191 5.690606 0 11.384281-2.260483 15.58393-6.757914C958.138478 540.883841 957.695387 527.388479 949.082218 519.343245L949.082218 519.343245zM949.082218 519.343245"  ></path>' +
    '' +
    '<path d="M814.699602 527.800871c-11.787464 0-21.349237 9.555633-21.349237 21.327748l0 327.037405L622.552373 876.166023 622.552373 648.662543 394.824789 648.662543l0 227.503481L224.032938 876.166023 224.032938 549.128619c0-11.772115-9.55154-21.327748-21.348214-21.327748-11.802814 0-21.35333 9.555633-21.35333 21.327748l0 369.691877 256.19494 0L437.526333 691.318038l142.329613 0 0 227.502457 256.1888 0L836.044746 549.128619C836.045769 537.356504 826.481949 527.800871 814.699602 527.800871L814.699602 527.800871zM814.699602 527.800871"  ></path>' +
    '' +
    '<path d="M665.254941 222.095307l128.095423 0 0 113.74867c0 11.789511 9.562796 21.332864 21.349237 21.332864 11.783371 0 21.346167-9.543354 21.346167-21.332864L836.045769 179.439812 665.254941 179.439812c-11.789511 0-21.35333 9.538237-21.35333 21.327748C643.900587 212.554 653.464407 222.095307 665.254941 222.095307L665.254941 222.095307zM665.254941 222.095307"  ></path>' +
    '' +
    '</symbol>' +
    '' +
    '<symbol id="icon-iconfontdingdan" viewBox="0 0 1024 1024">' +
    '' +
    '<path d="M823.736 962h-623.471c-34.214 0-62.049-27.836-62.049-62.049v-775.902c0-34.214 27.836-62.049 62.049-62.049h623.471c34.214 0 62.049 27.836 62.049 62.049v775.901c0.001 34.214-27.835 62.050-62.049 62.050zM200.264 79.41c-24.614 0-44.639 20.024-44.639 44.639v775.901c0 24.614 20.024 44.639 44.639 44.639h623.471c24.614 0 44.639-20.024 44.639-44.639v-775.901c0-24.614-20.024-44.639-44.639-44.639h-623.471z"  ></path>' +
    '' +
    '<path d="M586.961 174.178h-149.921c-34.724 0-62.976-28.25-62.976-62.976v-40.498h17.41v40.498c0 25.124 20.44 45.565 45.565 45.565h149.92c25.124 0 45.565-20.44 45.565-45.565v-40.498h17.41v40.498c0 34.725-28.25 62.976-62.975 62.976z"  ></path>' +
    '' +
    '<path d="M275.559 322.374h472.884v17.41h-472.884v-17.41z"  ></path>' +
    '' +
    '<path d="M275.559 451.603h472.884v17.41h-472.884v-17.41z"  ></path>' +
    '' +
    '<path d="M275.559 580.831h472.884v17.41h-472.884v-17.41z"  ></path>' +
    '' +
    '<path d="M275.559 710.062h472.884v17.41h-472.884v-17.41z"  ></path>' +
    '' +
    '</symbol>' +
    '' +
    '</svg>'
  var script = function() {
    var scripts = document.getElementsByTagName('script')
    return scripts[scripts.length - 1]
  }()
  var shouldInjectCss = script.getAttribute("data-injectcss")

  /**
   * document ready
   */
  var ready = function(fn) {
    if (document.addEventListener) {
      if (~["complete", "loaded", "interactive"].indexOf(document.readyState)) {
        setTimeout(fn, 0)
      } else {
        var loadFn = function() {
          document.removeEventListener("DOMContentLoaded", loadFn, false)
          fn()
        }
        document.addEventListener("DOMContentLoaded", loadFn, false)
      }
    } else if (document.attachEvent) {
      IEContentLoaded(window, fn)
    }

    function IEContentLoaded(w, fn) {
      var d = w.document,
        done = false,
        // only fire once
        init = function() {
          if (!done) {
            done = true
            fn()
          }
        }
        // polling for no errors
      var polling = function() {
        try {
          // throws errors until after ondocumentready
          d.documentElement.doScroll('left')
        } catch (e) {
          setTimeout(polling, 50)
          return
        }
        // no errors, fire

        init()
      };

      polling()
        // trying to always fire before onload
      d.onreadystatechange = function() {
        if (d.readyState == 'complete') {
          d.onreadystatechange = null
          init()
        }
      }
    }
  }

  /**
   * Insert el before target
   *
   * @param {Element} el
   * @param {Element} target
   */

  var before = function(el, target) {
    target.parentNode.insertBefore(el, target)
  }

  /**
   * Prepend el to target
   *
   * @param {Element} el
   * @param {Element} target
   */

  var prepend = function(el, target) {
    if (target.firstChild) {
      before(el, target.firstChild)
    } else {
      target.appendChild(el)
    }
  }

  function appendSvg() {
    var div, svg

    div = document.createElement('div')
    div.innerHTML = svgSprite
    svgSprite = null
    svg = div.getElementsByTagName('svg')[0]
    if (svg) {
      svg.setAttribute('aria-hidden', 'true')
      svg.style.position = 'absolute'
      svg.style.width = 0
      svg.style.height = 0
      svg.style.overflow = 'hidden'
      prepend(svg, document.body)
    }
  }

  if (shouldInjectCss && !window.__iconfont__svg__cssinject__) {
    window.__iconfont__svg__cssinject__ = true
    try {
      document.write("<style>.svgfont {display: inline-block;width: 1em;height: 1em;fill: currentColor;vertical-align: -0.1em;font-size:16px;}</style>");
    } catch (e) {
      console && console.log(e)
    }
  }

  ready(appendSvg)


})(window)