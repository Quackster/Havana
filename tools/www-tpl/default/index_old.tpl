
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="{{ locale.global_html_lang }}" lang="{{ locale.global_html_lang }}">
<head>
	<meta http-equiv="content-type" content="text/html" />
	<title>{{ site.siteName }}{{ locale.index_old_home }} </title>

<script type="text/javascript">
var andSoItBegins = (new Date()).getTime();
</script>
    <link rel="shortcut icon" href="{{ site.staticContentPath }}/web-gallery/v2/favicon.ico" type="image/vnd.microsoft.icon" />
    <link rel="alternate" type="application/rss+xml" title="{{ site.siteName }}{{ locale.index_old_rss }}" href="{{ site.sitePath }}/articles/rss.xml" />

<script src="{{ site.staticContentPath }}/web-gallery/static/js/libs2.js" type="text/javascript"></script>
<script src="{{ site.staticContentPath }}/web-gallery/static/js/landing.js" type="text/javascript"></script>
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/style.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/buttons.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/boxes.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/tooltips.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/styles/local/com.css" type="text/css" />

<script src="{{ site.staticContentPath }}/web-gallery/js/local/com.js" type="text/javascript"></script>
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/process.css" type="text/css" />

<script type="text/javascript">
document.habboLoggedIn = false;
var habboName = null;
var ad_keywords = "";
var habboReqPath = "{{ site.sitePath }}";
var habboStaticFilePath = "{{ site.staticContentPath }}/web-gallery";
var habboImagerUrl = "{{ site.staticContentPath }}/habbo-imaging/";
var habboPartner = "";
var habboDefaultClientPopupUrl = "{{ site.sitePath }}/client";
window.name = "habboMain";
if (typeof HabboClient != "undefined") { HabboClient.windowName = "client"; }

</script>


<meta name="description" content="{{ locale.index_old_join_the_world_s_largest_virtual_hangout_where_you_can_meet_and_make_friends_design_your_own_rooms_collect_cool_furniture_throw_parties_and_so_much_more_create_your_free }} {{ site.siteName }} {{ locale.index_old_today }}" />
<meta name="keywords" content="{{ site.siteName }}{{ locale.index_old_virtual_world_join_groups_forums_play_games_online_friends_teens_collecting_social_network_create_collect_connect_furniture_virtual_goods_sharing_badges_social_networking_hangout_safe_music_celebrity_celebrity_visits_cele }}" />

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
<meta name="build" content="{{ locale.index_old_havanaweb }}" />
</head>
<body id="landing" class="process-template">

<div id="overlay"></div>

<div id="container">
	<div class="cbb process-template-box clearfix">
		<div id="content">
			{% include "base/frontpage_header.tpl" %}
			<div id="process-content">
	        	<div id="column1" class="column">
			     		
				<div class="habblet-container " id="create-habbo">		
	
				{% if isValentinesMonth %}
					<div id="create-habbo" class="layout-static">
						<div id="create-habbo-nonflash" style="background-image: url({{ site.staticContentPath }}/web-gallery/v2/images/landing/custom/{{ randomValentinesImage }})">
							<div class="landing-text-1"><span>{{ locale.index_old_valentine_s_day }}</span></div>
							{% if randomValentinesImage == "valentines_2_ultra.png" %}
							<div class="landing-text-2" style="left:60px"><span>{{ locale.index_old_find_your_partner }}</span></div>
							<div class="landing-text-3" style="left:170px"><span>{{ locale.index_old_and_make_new_friends }}</span></div>
							{% else %}
							<div class="landing-text-2"><span>{{ locale.index_old_find_your_partner }}</span></div>
							<div class="landing-text-3"><span>{{ locale.index_old_and_make_new_friends }}</span></div>							
							{% endif %}
							<div id="landing-register-text"><a href="https://classichabbo.com/register"><span>{{ locale.index_old_join_now_it_s_free }}</span></a></div>
							<div id="landing-promotional-text"><span>{{ locale.index_old_habbo_is_a_virtual_world_where_you_can_meet_and_find_your_true_love }}</span></div>
						</div>
					</div>			
				{% else %}
					<div id="create-habbo" class="layout-static">
						<div id="create-habbo-nonflash" style="background-image: url({{ site.staticContentPath }}/web-gallery/v2/images/landing/pixel.gif)">
							<div class="landing-text-1"><span>{{ locale.index_old_virtual_world_real_fun }}</span></div>
							<div class="landing-text-2"><span>{{ locale.index_old_create_your_habbo }}</span></div>
							<div class="landing-text-3"><span>{{ locale.index_old_and_make_new_friends }}</span></div>
							<div id="landing-register-text"><a href="https://classichabbo.com/register"><span>{{ locale.index_old_join_now_it_s_free }}</span></a></div>
							<div id="landing-promotional-text"><span>{{ locale.index_old_habbo_is_a_virtual_world_where_you_can_meet_and_make_friends }}</span></div>
						</div>
					</div>
				{% endif %}
</div>
	
						
					
				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
			 

</div>
<div id="column2" class="column">
				<div class="habblet-container ">
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
    <h2 class="title">{{ locale.index_old_sign_in }}</h2>

    <div class="box-content clearfix" id="login-habblet">
        <form action="{{ site.sitePath }}/account/submit" method="post" class="login-habblet">
			            <ul>
                <li>
                    <label for="login-username" class="login-text">{{ locale.index_old_username }}</label>
                    <input tabindex="1" type="text" class="login-field" name="username" id="login-username" value="{{ username }}"/>
                </li>
                <li>
                    <label for="login-password" class="login-text">{{ locale.index_old_password }}</label>
                    <input tabindex="2" type="password" class="login-field" name="password" id="login-password" />
	                <input type="submit" value="{{ locale.index_old_sign_in }}" class="submit" id="login-submit-button"/>
	                <a href="#" id="login-submit-new-button" class="new-button" style="float: left; margin-left: 0;display:none"><b style="padding-left: 10px; padding-right: 7px; width: 55px">{{ locale.index_old_sign_in }}</b><i></i></a>
                </li>
                <li class="no-label">
										{% if rememberMe %}
                    <input tabindex="3" type="checkbox" value="true" name="_login_remember_me" id="login-remember-me" checked="true"/>
										{% else %}
                    <input tabindex="3" type="checkbox" value="true" name="_login_remember_me" id="login-remember-me"/>
										{% endif %}
										<label for="login-remember-me">{{ locale.index_old_remember_me }}</label>
                </li>
                <li class="no-label">
                    <a href="{{ site.sitePath }}/register" class="login-register-link"><span>{{ locale.index_old_register_for_free }}</span></a>
                </li>
                <li class="no-label">
                    <a href="{{ site.sitePath }}/account/password/forgot" id="forgot-password"><span>{{ locale.index_old_i_forgot_my_username_password }}</span></a>
                </li>
            </ul>
        </form>

    </div>
</div>
<div id="remember-me-notification" class="bottom-bubble" style="display:none;">
	<div class="bottom-bubble-t"><div></div></div>
	<div class="bottom-bubble-c">
	{{ locale.index_old_by_selecting_remember_me_you_will_stay_signed_in_on_this_computer_until_you_click_sign_out_if_this_is_a_public_computer_please_do_not_use_this_feature }}	</div>
	<div class="bottom-bubble-b"><div></div></div>
</div>
<script type="text/javascript">
	HabboView.add(LoginFormUI.init);
	HabboView.add(RememberMeUI.init);
</script>



								</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>

				<div class="habblet-container ">

						<div class="ad-container">
<div id="geoip-ad" style="display:none"></div>
</div>



				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>

				<div class="habblet-container ">





				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>

				<div class="habblet-container "> <!--
	<div class="rounded" style="background-color: orange; color: white">
			<strong>{{ locale.index_old_attention }}</strong><br />
			{{ locale.index_old_this_server_has_been_wiped_since_december_one_one_th_two_zero_one_nine_due_to_an_unfortunate_incident }}<br />
		</div>
		<br /> -->
				
						<div class="ad-container">
<!-- <a href="{{ site.sitePath }}/register"><img src="{{ site.staticContentPath }}/web-gallery/v2/images/landing/filler_ad.png" alt="" /></a> -->
<a href="{{ site.sitePath }}/games"><img src="https://i.imgur.com/ehgdoyS.png" alt="" /></a>

</div>
	
						
					
				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
			 

</div>
<div id="column3" class="column">
</div>
<div id="column-footer">
		
				<div class="habblet-container ">		
	
						<!-- <div class="habblet box-content" id="tag-cloud-slim">
    <span class="tags-habbos-like">{{ site.siteName }}{{ locale.index_old_s_like }}</span>
{{ locale.index_old_no_tags_to_display }}</div> -->

				<div class="habblet box-content" id="tag-cloud-slim">
				<span class="tags-habbos-like">{{ site.siteName }}{{ locale.index_old_s_like }}</span>
				{% autoescape 'html' %}
					{% if tagCloud|length > 0 %}
						{% for kvp in tagCloud %}
							{% set tag = kvp.getKey() %}
							{% set size = kvp.getValue() %}
							<ul class="tag-list">
								<li><a href="{{ site.sitePath }}/tag/{{ tag }}" class="tag" style="font-size:{{ size }}px">{{ tag }}</a> </li>
							</ul>
						{% endfor %}
					{% else %}
						{{ locale.index_old_no_tags_to_display }}
					{% endif %}	
				{% endautoescape %}
					</div>
				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
			 
 
<!--[if lt IE 7]>
<script type="text/javascript">
Pngfix.doPngImageFix();
</script>
<![endif]-->

{% include "base/footer.tpl" %}

<script type="text/javascript">
HabboView.run();
</script>


</body>
</html>