
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="{{ locale.global_html_lang }}" lang="{{ locale.global_html_lang }}">
<head>
	<meta http-equiv="content-type" content="text/html" />
	<title>{{ site.siteName }}{{ locale.profile_change_preferences_my_preferences }} </title>

<script type="text/javascript">
var andSoItBegins = (new Date()).getTime();
</script>
    <link rel="shortcut icon" href="{{ site.staticContentPath }}/web-gallery/v2/favicon.ico" type="image/vnd.microsoft.icon" />
    <link rel="alternate" type="application/rss+xml" title="{{ site.siteName }}{{ locale.profile_change_preferences_rss }}" href="{{ site.sitePath }}/articles/rss.xml" />
<script src="{{ site.staticContentPath }}/web-gallery/static/js/libs2.js" type="text/javascript"></script>
<script src="{{ site.staticContentPath }}/web-gallery/static/js/visual.js" type="text/javascript"></script>
<script src="{{ site.staticContentPath }}/web-gallery/static/js/libs.js" type="text/javascript"></script>
<script src="{{ site.staticContentPath }}/web-gallery/static/js/common.js" type="text/javascript"></script>
<script src="{{ site.staticContentPath }}/web-gallery/static/js/fullcontent.js" type="text/javascript"></script>
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/style.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/buttons.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/boxes.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/tooltips.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/styles/local/com.css" type="text/css" />

<script src="{{ site.staticContentPath }}/web-gallery/js/local/com.js" type="text/javascript"></script>

<script type="text/javascript">
document.habboLoggedIn = {{ session.loggedIn }};
var habboName = "{{ locale.profile_change_preferences_session_loggedin_playerdetails_getname|escape('js') }} "" }}";
var ad_keywords = "";
var habboReqPath = "{{ site.sitePath }}";
var habboStaticFilePath = "{{ site.staticContentPath }}/web-gallery";
var habboImagerUrl = "{{ site.staticContentPath }}/habbo-imaging/";
var habboPartner = "";
window.name = "habboMain";
if (typeof HabboClient != "undefined") { HabboClient.windowName = "client"; }

</script>

<script src="{{ site.staticContentPath }}/web-gallery/static/js/settings.js" type="text/javascript"></script>
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/settings.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/friendmanagement.css" type="text/css" />


<meta name="description" content="{{ locale.profile_change_preferences_join_the_world_s_largest_virtual_hangout_where_you_can_meet_and_make_friends_design_your_own_rooms_collect_cool_furniture_throw_parties_and_so_much_more_create_your_free }} {{ site.siteName }} {{ locale.profile_change_preferences_today }}" />
<meta name="keywords" content="{{ site.siteName }}{{ locale.profile_change_preferences_virtual_world_join_groups_forums_play_games_online_friends_teens_collecting_social_network_create_collect_connect_furniture_virtual_goods_sharing_badges_social_networking_hangout_safe_music_celebrity_celebrity_visits_cele }}" />

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
<meta name="build" content="{{ locale.profile_change_preferences_havanaweb }}" />
</head>

{% include "../base/header.tpl" %}

<div id="content-container">

	<div id="navi2-container" class="pngbg">
    <div id="navi2" class="pngbg clearfix">
		<ul>
			<li class="">
				<a href="{{ site.sitePath }}/me">{{ locale.profile_change_preferences_home }}</a>			</li>
    		<li class="">
				<a href="{{ site.sitePath }}/home/{{ playerDetails.getName() }}">{{ locale.profile_change_preferences_my_page }}</a>    		</li>
    		<li class="selected">
				{{ locale.profile_change_preferences_account_settings }}    		</li>
			<li class=" last">
				<a href="{{ site.sitePath }}/club">{{ site.siteName }} {{ locale.profile_change_preferences_club }}</a>
			</li>
		</ul>
    </div>
</div>
	
<div id="container">
	<div id="content" style="position: relative" class="clearfix">
    <div>
<div class="content">
<div class="habblet-container" style="float:left; width:210px;">
<div class="cbb settings">

<h2 class="title">{{ locale.profile_change_preferences_account_settings }}</h2>
<div class="box-content">
            <div id="settingsNavigation">
            <ul>
				<li><a href="{{ site.sitePath }}/profile?tab=1">{{ locale.profile_change_preferences_my_clothing }}</a>
                </li><li class="selected">{{ locale.profile_change_preferences_my_preferences_text }}
				{% if accountActivated %}
                </li><li><a href="{{ site.sitePath }}/profile?tab=3">{{ locale.profile_change_preferences_my_email }}</a>
				{% else %}
				</li><li><a href="{{ site.sitePath }}/profile/verify">{{ locale.profile_change_preferences_email_changing_verification }}</a>
				{% endif %}
                </li><li><a href="{{ site.sitePath }}/profile?tab=4">{{ locale.profile_change_preferences_my_password }}</a>
                </li><li><a href="{{ site.sitePath }}/profile?tab=5">{{ locale.profile_change_preferences_friend_management }}</a>
								</li><li><a href="{{ site.sitePath }}/profile?tab=6">{{ locale.profile_change_preferences_trade_settings }}</a>
                </li>            </ul>
            </div>
</div></div>
    {% include "profile/profile_widgets/join_club.tpl" %}
</div>
    <div class="habblet-container " style="float:left; width: 560px;">
        <div class="cbb clearfix settings">

            <h2 class="title">{{ locale.profile_change_preferences_change_your_profile }}</h2>
            <div class="box-content">

{% if settingsSavedAlert %}
<div class="rounded rounded-green">{{ locale.profile_change_preferences_account_settings_updated_successfully }}<br />	</div><br />
{% endif %}

<form action="{{ site.sitePath }}/profile/profileupdate" method="post" id="profileForm">
<input type="hidden" name="tab" value="2" />
<input type="hidden" name="__app_key" value="{{ locale.profile_change_preferences_havanaweb }}" />


<h3>{{ locale.profile_change_preferences_your_motto }}</h3>

<p>
{{ locale.profile_change_preferences_your_motto_is_what_other }} {{ site.siteName }}{{ locale.profile_change_preferences_s_will_see_on_your }} {{ site.siteName }} {{ locale.profile_change_preferences_home_page_and_beneath_your }} {{ site.siteName }} {{ locale.profile_change_preferences_in_the_hotel }}</p>
{% autoescape 'html' %}
<p>
<span class="label">{{ locale.profile_change_preferences_motto }}</span>
<input type="text" name="motto" size="32" maxlength="32" value="{{ playerDetails.motto }}" id="avatarmotto" />
</p>
{% endautoescape %}

<h3>{{ locale.profile_change_preferences_client_preference }}</h3>

<p>
{{ locale.profile_change_preferences_the_plugin_to_use_when_clicking_the_go_to_hotel_buttons_go_to_room_etc }}<br />
<label><input type="radio" name="clientpreference" value="SHOCKWAVE" {{ SHOCKWAVEenabled }} />{{ locale.profile_change_preferences_shockwave }}</label>

<label><input type="radio" name="clientpreference" value="FLASH" {{ FLASHenabled }} />{{ locale.profile_change_preferences_flash }}</label>
</p>

<h3>{{ locale.profile_change_preferences_your_page }}</h3>

<p>
{{ locale.profile_change_preferences_who_can_view_your_homepage }}<br />
<label><input type="radio" name="visibility" value="EVERYONE" {{ profileVisibleEnabled }} />{{ locale.profile_change_preferences_visible_to_everyone }}</label>

<label><input type="radio" name="visibility" value="NOBODY" {{ profileVisibleDisabled }} />{{ locale.profile_change_preferences_invisible_to_everyone }}</label>
</p>

<!-- <h3>{{ locale.profile_change_preferences_email_alerts }}</h3>

<p>
<label><input type="checkbox" name="emailMiniMailAlertEnabled" value="true" checked="checked" />{{ locale.profile_change_preferences_receive_notifications_on_minimail_messages }}</label> <br />
<label><input type="checkbox" name="emailFriendRequestAlertEnabled" value="true" checked="checked" />{{ locale.profile_change_preferences_receive_notifications_on_friend_requests }}</label>
</p>
-->
<h3>{{ locale.profile_change_preferences_word_filter }}</h3>
<p>
<label><input type="checkbox" name="wordFilterSetting" value="false" {{ wordFilterSetting }}> {{ locale.profile_change_preferences_turn_bad_language_filter_off }}</label>
</p>

<h3>{{ locale.profile_change_preferences_friend_requests }}</h3>
<p>
<label><input type="checkbox" name="allowFriendRequests" value="true" {{ allowFriendRequests }}> {{ locale.profile_change_preferences_friend_requests_enabled }}</label>
</p>

<h3>{{ locale.profile_change_preferences_friend_follow }}</h3>
<p>
{{ locale.profile_change_preferences_ability_for_other_people_to_follow_users_between_rooms }}<br />
<label><input type="radio" name="followFriendSetting" value="true" {{ followFriendEnabled }} />{{ locale.profile_change_preferences_friends }}</label>
<label><input type="radio" name="followFriendSetting" value="false" {{ followFriendDisabled }} />{{ locale.profile_change_preferences_nobody }}</label>
</p>

<h3>{{ locale.profile_change_preferences_online_status }}</h3>
<p>
{{ locale.profile_change_preferences_select_who_can_see_your_online_status }}<br />
<label><input type="radio" name="showOnlineStatus" value="true" {{ onlineStatusEnabled }} />{{ locale.profile_change_preferences_everybody }}</label>
<label><input type="radio" name="showOnlineStatus" value="false" {{ onlineStatusDisabled }} />{{ locale.profile_change_preferences_nobody }}</label>
</p>

<div class="settings-buttons">
<a href="#" class="new-button" style="display: none" id="profileForm-submit"><b>{{ locale.profile_change_preferences_save_changes }}</b><i></i></a>
<noscript><input type="submit" value="{{ locale.profile_change_preferences_save_changes }}" name="save" class="submit" /></noscript>
</div>

</form>

<script type="text/javascript">
$("profileForm-submit").observe("click", function(e) { e.stop(); $("profileForm").submit(); });
$("profileForm-submit").show();

$("profileForm-genToken").observe("click", function(e) { e.stop();
	new Ajax.Request(habboReqPath + "/habblet/ajax/token_generate", {
        onComplete: function(response) {
            document.getElementById('authenticationToken').value = response.responseText;
		}
    });
 });
$("profileForm-genToken").show();

</script>

</div>
</div>
</div>
</div>
</div>

<script type="text/javascript">
HabboView.run();

</script>


<div id="column3" class="column">
				<div class="habblet-container ">
						<div class="ad-container">
						</div>
				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
</div>

<!--[if lt IE 7]>
<script type="text/javascript">
Pngfix.doPngImageFix();
</script>
<![endif]-->
    </div>

{% include "../base/footer.tpl" %}
	
<script type="text/javascript">
HabboView.run();
</script>


</body>
</html>