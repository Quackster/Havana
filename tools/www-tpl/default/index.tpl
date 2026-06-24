
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="{{ locale.global_html_lang }}" lang="{{ locale.global_html_lang }}">
   <head>
      <meta http-equiv="content-type" content="text/html" />
      <title>{{ site.siteName }} {{ locale.index_home }} </title>
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
      <meta name="description" content="{{ locale.index_habbo_is_a_virtual_world_where_you_can_meet_and_make_friends }}" />
      <meta name="keywords" content="{{ locale.index_habbo_virtual_world_play_games_enter_competitions_make_friends }}" />
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
      <meta name="build" content="{{ locale.index_three_two_build_eight_two_zero_six_zero_four_two_zero_zero_nine_one_one_five_one_uk }}" />
   </head>
   <body id="frontpage">
      <div id="fp-container">
         <div id="header" class="clearfix">
            <h1><a href="{{ site.sitePath }}"></a></h1>
            <span class="login-register-link">
            {{ locale.index_new_here }}
            <a href="{{ site.sitePath }}/register">{{ locale.index_register_for_free }}</a>
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
                        <h2 class="title">{{ locale.index_sign_in }}</h2>
                        <div class="box-content clearfix" id="login-habblet">
                           <form action="{{ site.sitePath }}/account/submit" method="post" class="login-habblet">
                              <ul>
                                 <li>
                                    <label for="login-username" class="login-text">{{ locale.index_username }}</label>
                                    <input tabindex="1" type="text" class="login-field" name="username" id="login-username" value="{{ username }}" maxlength="32"/>
                                 </li>
                                 <li>
                                    <label for="login-password" class="login-text">{{ locale.index_password }}</label>
                                    <input tabindex="2" type="password" class="login-field" name="password" id="login-password" maxlength="32"/>
                                    <input type="submit" value="{{ locale.index_sign_in }}" class="submit" id="login-submit-button"/>
                                    <a href="#" id="login-submit-new-button" class="new-button" style="margin-left: 0;display:none"><b style="padding-left: 10px; padding-right: 7px; width: 55px">{{ locale.index_sign_in }}</b><i></i></a>
                                 </li>
                                 <li id="remember-me" class="no-label">
									{% if rememberMe %}
                                    <input tabindex="4" type="checkbox" value="true" name="_login_remember_me" id="login-remember-me" checked="true"/>
									{% else %}
									<input tabindex="4" type="checkbox" value="true" name="_login_remember_me" id="login-remember-me"/>
									{% endif %}
                                    <label for="login-remember-me">{{ locale.index_remember_me }}</label>
                                 </li>
                                 <li id="register-link" class="no-label">
                                    <a href="{{ site.sitePath }}/register" class="login-register-link"><span>{{ locale.index_register_for_free_text }}</span></a>
                                 </li>
                                 <li class="no-label">
                                    <a href="{{ site.sitePath }}/account/password/forgot" id="forgot-password"><span>{{ locale.index_i_forgot_my_password_username }}</span></a>
                                 </li>
                              </ul>
                              <div id="remember-me-notification" class="bottom-bubble" style="display:none;">
                                 <div class="bottom-bubble-t">
                                    <div></div>
                                 </div>
                                 <div class="bottom-bubble-c">
                                    {{ locale.index_by_selecting_remember_me_you_will_stay_signed_in_on_this_computer_until_you_click_sign_out_if_this_is_a_public_computer_please_do_not_use_this_feature }}
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
                     L10N.put("authentication.form.name", "{{ locale.index_username|escape('js') }}");
                     L10N.put("authentication.form.password", "{{ locale.index_password|escape('js') }}");
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
					sb.add("fp-bubble-"+i++, "frontpage-image", 489, 143, "{{ locale.index_create_your_own_habbo|escape('js') }}");
					sb.add("fp-bubble-"+i++, "frontpage-image", 478, 254, "{{ locale.index_and_make_new_friends|escape('js') }}");
					sb.add("fp-bubble-"+i++, "frontpage-image", 93, 249, "{{ locale.index_join_us_in_habbo_world_s_biggest_virtual_world|escape('js') }}");
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
                        <li class="stats-online"><span class="stats-fig">{{ site.usersOnline }}</span> {{ site.siteName }}{{ locale.index_s_online_now }}</li>
                        <li class="stats-visited"><span class="stats-fig">{{ site.visits }}</span> {{ locale.index_visits_in_the_last_three_zero_days }}</li>
                     </ul>
                  </div>
               </div>
               <script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
               <div class="habblet-container ">
                  <div class="cbb habblet box-content" id="tag-cloud-slim">
                     <span class="tags-habbos-like">{{ site.siteName }}{{ locale.index_s_like }}</span>
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
						<li><span class="tags-habbos-like">{{ locale.index_no_tags_to_display }}</span></li>
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
         
         	document.write('<' + '{{ locale.index_script_language_javascript_type_text_javascript_src_https_www_googleadservices_com_pagead_conversion_js|escape('js') }}' +        '>' + '</' + 'script' + '>');
         	<!-- Google Code for Signup Conversion Page (HL-12978) END -->
         
         	microsoft_adcenterconversion_domainid = 270630;
         	microsoft_adcenterconversion_cp = 5050; 
         	
         	document.write('<' + '{{ locale.index_script_language_javascript_type_text_javascript_src_https_zero_r_msn_com_scripts_microsoft_adcenterconversion_js|escape('js') }}' +        '>' + '</' + 'script' + '>');	
         
         }
      </script>
      <!-- Start Quantcast tag -->
      <script type="text/javascript" src="https://www.quantserve.com/quant.js"></script>
      <script type="text/javascript">_qacct="p-b5UDx6EsiRfMI";quantserve();</script>
      <noscript>
         <a href="http://www.quantcast.com/p-b5UDx6EsiRfMI" target="_blank"><img src="http://pixel.quantserve.com/pixel/p-b5UDx6EsiRfMI.gif" style="display: none" border="0" height="1" width="1" alt="{{ locale.index_quantcast }}"/></a>
      </noscript>
      <!-- End Quantcast tag -->
   </body>
</html>