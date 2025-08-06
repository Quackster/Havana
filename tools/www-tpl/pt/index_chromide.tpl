
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="pt-br" lang="pt-br">
   <head>
      <meta http-equiv="content-type" content="text/html" />
      <title>{{ site.siteName }} ~ Home </title>
      <script type="text/javascript">
         var andSoItBegins = (new Date()).getTime();
      </script>
      <link rel="shortcut icon" href="{{ site.staticContentPath }}/web-gallery/v2/favicon.ico" type="image/vnd.microsoft.icon" />
      <script src="{{ site.staticContentPath }}/web-gallery/static/js/landing.js" type="text/javascript"></script>
      <link rel="stylesheet" href="{{ site.staticContentPath }}/styles/local/uk.css" type="text/css" />
      <script src="{{ site.staticContentPath }}/js/local/uk.js" type="text/javascript"></script>
      <link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/frontpage.css" type="text/css"/>
      <script type="text/javascript">
         document.habboLoggedIn = false;
         var habboName = null;
         var ad_keywords = "";
         var habboReqPath = "";
         var habboStaticFilePath = "{{ site.staticContentPath }}/web-gallery/";
         var habboImagerUrl = "/habbo-imaging/";
         var habboPartner = "";
         window.name = "habboMain";
         if (typeof HabboClient != "undefined") { HabboClient.windowName = "client"; }
         
      </script>
      <style type="text/css">
         body {background-color: #bcd2d4}
      </style>
      <meta name="description" content="Habbo é um mundo virtual onde você pode conhecer e fazer amigos." />
      <meta name="keywords" content="habbo, mundo virtual, jogar, participar de competições, fazer amigos" />
      <!--[if IE 8]>
      <link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/ie8.css" type="text/css" />
      <![endif]-->
      <!--[if lt IE 8]>
      <link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/ie.css" type="text/css" />
      <![endif]-->
      <!--[if lt IE 7]>
      <link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/ie6.css" type="text/css" />
      <script src="{{ site.staticContentPath }}/web-gallery/static/js/pngfix.js" type="text/javascript"></script>
      <script type="text/javascript">
         try { document.execCommand('BackgroundImageCache', false, true); } catch(e) {}
      </script>
      <style type="text/css">
         body { behavior: url({{ site.staticContentPath }}/web-gallery/js/csshover.htc); }
      </style>
      <![endif]-->
      <meta name="build" content="32-BUILD82 - 06.04.2009 11:51 - uk" />
   </head>
   <body id="frontpage">
      <div id="fp-container">
         <div id="header" class="clearfix">
            <h1><a href="{{ site.sitePath }}"></a></h1>
            <span class="login-register-link">
            New here?
            <a href="{{ site.sitePath }}/register">REGISTER FOR FREE</a>
            </span>
         </div>
         <div id="content">
            <div id="column1" class="column">
               <div class="habblet-container ">
                  <div class="logincontainer">
				  				{% if alert.hasAlert %}
				<div class="action-error flash-message">
 <div class="rounded-container"><div style="background-color: rgb(255, 255, 255);"><div style="margin: 0px 4px; height: 1px; overflow: hidden; background-color: rgb(255, 255, 255);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(238, 107, 122);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(231, 40, 62);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(227, 8, 33);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(226, 0, 26);"></div></div></div></div></div><div style="margin: 0px 2px; height: 1px; overflow: hidden; background-color: rgb(255, 255, 255);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(238, 105, 121);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(226, 1, 27);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(226, 0, 26);"></div></div></div></div><div style="margin: 0px 1px; height: 1px; overflow: hidden; background-color: rgb(255, 255, 255);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(233, 64, 83);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(226, 0, 26);"></div></div></div><div style="margin: 0px 1px; height: 1px; overflow: hidden; background-color: rgb(238, 105, 121);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(226, 0, 26);"></div></div><div style="margin: 0px; height: 1px; overflow: hidden; background-color: rgb(255, 255, 255);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(226, 1, 27);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(226, 0, 26);"></div></div></div><div style="margin: 0px; height: 1px; overflow: hidden; background-color: rgb(238, 107, 122);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(226, 0, 26);"></div></div><div style="margin: 0px; height: 1px; overflow: hidden; background-color: rgb(231, 40, 62);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(226, 0, 26);"></div></div><div style="margin: 0px; height: 1px; overflow: hidden; background-color: rgb(227, 8, 33);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(226, 0, 26);"></div></div></div><div class="rounded-done">
  <ul>
   <li>{{ alert.message }}</li>
  </ul>
 </div><div style="background-color: rgb(255, 255, 255);"><div style="margin: 0px; height: 1px; overflow: hidden; background-color: rgb(227, 8, 33);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(226, 0, 26);"></div></div><div style="margin: 0px; height: 1px; overflow: hidden; background-color: rgb(231, 40, 62);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(226, 0, 26);"></div></div><div style="margin: 0px; height: 1px; overflow: hidden; background-color: rgb(238, 107, 122);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(226, 0, 26);"></div></div><div style="margin: 0px; height: 1px; overflow: hidden; background-color: rgb(255, 255, 255);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(226, 1, 27);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(226, 0, 26);"></div></div></div><div style="margin: 0px 1px; height: 1px; overflow: hidden; background-color: rgb(238, 105, 121);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(226, 0, 26);"></div></div><div style="margin: 0px 1px; height: 1px; overflow: hidden; background-color: rgb(255, 255, 255);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(233, 64, 83);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(226, 0, 26);"></div></div></div><div style="margin: 0px 2px; height: 1px; overflow: hidden; background-color: rgb(255, 255, 255);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(238, 105, 121);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(226, 1, 27);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(226, 0, 26);"></div></div></div></div><div style="margin: 0px 4px; height: 1px; overflow: hidden; background-color: rgb(255, 255, 255);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(238, 107, 122);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(231, 40, 62);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(227, 8, 33);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(226, 0, 26);"></div></div></div></div></div></div></div>
</div>
				{% endif %}
                     <div class="cbb loginbox clearfix">
                        <h2 class="title">Sign in</h2>
                        <div class="box-content clearfix" id="login-habblet">
                           <form action="{{ site.sitePath }}/account/submit" method="post" class="login-habblet">
                              <ul>
                                 <li>
                                    <label for="login-username" class="login-text">Username</label>
                                    <input tabindex="1" type="text" class="login-field" name="username" id="login-username" value="{{ username }}" maxlength="32"/>
                                 </li>
                                 <li>
                                    <label for="login-password" class="login-text">Password</label>
                                    <input tabindex="2" type="password" class="login-field" name="password" id="login-password" maxlength="32"/>
                                    <input type="submit" value="Sign in" class="submit" id="login-submit-button"/>
                                    <a href="#" id="login-submit-new-button" class="new-button" style="margin-left: 0;display:none"><b style="padding-left: 10px; padding-right: 7px; width: 55px">Sign in</b><i></i></a>
                                 </li>
                                 <li id="remember-me" class="no-label">
									{% if rememberMe %}
                                    <input tabindex="4" type="checkbox" value="true" name="_login_remember_me" id="login-remember-me" checked="true"/>
									{% else %}
									<input tabindex="4" type="checkbox" value="true" name="_login_remember_me" id="login-remember-me"/>
									{% endif %}
                                    <label for="login-remember-me">Remember me</label>
                                 </li>
                                 <li id="register-link" class="no-label">
                                    <a href="{{ site.sitePath }}/register" class="login-register-link"><span>Register for free</span></a>
                                 </li>
                                 <li class="no-label">
                                    <a href="{{ site.sitePath }}/account/password/forgot" id="forgot-password"><span>I forgot my password/username</span></a>
                                 </li>
                              </ul>
                              <div id="remember-me-notification" class="bottom-bubble" style="display:none;">
                                 <div class="bottom-bubble-t">
                                    <div></div>
                                 </div>
                                 <div class="bottom-bubble-c">
                                    Ao selecionar 'lembrar de mim', você permanecerá conectado neste computador até clicar em 'Sair'. Se este for um computador público, não use este recurso.
                                 </div>
                                 <div class="bottom-bubble-b">
                                    <div></div>
                                 </div>
                              </div>
                           </form>
                        </div>
                     </div>
                  </div>
                  <script type="text/javascript">
                     L10N.put("authentication.form.name", "Username");
                     L10N.put("authentication.form.password", "Password");
                     HabboView.add(function() {LoginFormUI.init();});
                     HabboView.add(function() {window.setTimeout(function() {RememberMeUI.init("newfrontpage");}, 100)});
                  </script>
               </div>
               <script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
               <div class="habblet-container ">
                  <div id="frontpage-image" style="background-image: url('{{ site.staticContentPath }}/c_images/Frontpage_images/frontpage_chromicle_image.png')">
                     <div id="partner-logo"></div>
                  </div>
                  <script type="text/javascript">
                     var sb = new SpeechBubble();
                     var i = 0;
					sb.add("fp-bubble-"+i++, "frontpage-image", 489, 143, "Crie o seu avatar...");
					sb.add("fp-bubble-"+i++, "frontpage-image", 478, 254, "...e faça novos amigos!");
					sb.add("fp-bubble-"+i++, "frontpage-image", 93, 249, "Junte-se ao maior mundo virtual da internet!");
                     HabboView.add(function() {sb.render();});
                  </script>
               </div>
               <script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
            </div>
            <div id="column2" class="column"></div>
			
            <div id="column-footer">
               <div class="habblet-container ">
                  <div class="cbb" id="hotel-stats">
                     <ul class="stats">
                        <li class="stats-online"><span class="stats-fig">{{ site.usersOnline }}</span> {{ site.siteName }}s online agora!</li>
                        <li class="stats-visited"><span class="stats-fig">{{ site.visits }}</span> visits in the last 30 days</li>
                     </ul>
                  </div>
               </div>
               <script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
               <div class="habblet-container ">
                  <div class="cbb habblet box-content" id="tag-cloud-slim">
                     <span class="tags-habbos-like">{{ site.siteName }}s Like</span>
                     <ul class="tag-list">
						{% autoescape 'html' %}
						{% if tagCloud|length > 0 %}
						<ul class="tag-list">
						{% for kvp in tagCloud %}
							{% set tag = kvp.getKey() %}
							{% set size = kvp.getValue() %}
							<li><a href="{{ site.sitePath }}/tag/{{ tag }}" class="tag">{{ tag }}</a> </li>
						{% endfor %}
						{% else %}
						<li><span class="tags-habbos-like">Sem tags para mostrar.</span></li>
						{% endif %}
						{% endautoescape %}
                     </ul>
                  </div>
               </div>
               <script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
            </div>
            <!--[if lt IE 7]>
            <script type="text/javascript">
               Pngfix.doPngImageFix();
            </script>
            <![endif]-->
			{% set indexClass = " class=\"copyright\"" %}
			{% include "base/footer.tpl" %}
      <script data-cfasync="false" src="/cdn-cgi/scripts/5c5dd728/cloudflare-static/email-decode.min.js"></script><script type="text/javascript">
         HabboView.run();
      </script>
      <script src="https://www.google-analytics.com/ga.js" type="text/javascript"></script>
      <script type="text/javascript">
         var pageTracker = _gat._getTracker("UA-448325-6");
         pageTracker._trackPageview();
      </script>
      <script type="text/javascript">
         if (window.location.href.indexOf("/register/welcome") != -1) {
         	var tdTrackBackImg = new Image();
         	var tdTrackBackUrl = "";
         	
         	tdInit();
         	tdTrack();
         
         	<!-- Google Code for Signup Conversion Page (HL-12978) BEGIN -->
         	var google_conversion_id = 1060792141;
         	var google_conversion_language = "en_GB";
         	var google_conversion_format = "1";
         	var google_conversion_color = "666666";
         	var google_conversion_label = "QBXyCIfMPRDNzun5Aw";
         
         	document.write('<' + 'script language="JavaScript" type="text/javascript" src="https://www.googleadservices.com/pagead/conversion.js"' +        '>' + '</' + 'script' + '>');
         	<!-- Google Code for Signup Conversion Page (HL-12978) END -->
         
         	microsoft_adcenterconversion_domainid = 270630;
         	microsoft_adcenterconversion_cp = 5050; 
         	
         	document.write('<' + '<script language="JavaScript" type="text/javascript" src="https://0.r.msn.com/scripts/microsoft_adcenterconversion.js">' +        '>' + '</' + 'script' + '>');	
         
         }
      </script>
      <!-- Start Quantcast tag -->
      <script type="text/javascript" src="https://www.quantserve.com/quant.js"></script>
      <script type="text/javascript">_qacct="p-b5UDx6EsiRfMI";quantserve();</script>
      <noscript>
         <a href="http://www.quantcast.com/p-b5UDx6EsiRfMI" target="_blank"><img src="http://pixel.quantserve.com/pixel/p-b5UDx6EsiRfMI.gif" style="display: none" border="0" height="1" width="1" alt="Quantcast"/></a>
      </noscript>
      <!-- End Quantcast tag -->
   </body>
</html>