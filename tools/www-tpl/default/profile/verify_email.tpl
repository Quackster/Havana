
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="{{ locale.global_html_lang }}" lang="{{ locale.global_html_lang }}">
<head>
	<meta http-equiv="content-type" content="text/html" />
	<title>{{ site.siteName }}{{ locale.profile_verify_email_verify_email }} </title>

<script type="text/javascript">
var andSoItBegins = (new Date()).getTime();
</script>
    <link rel="shortcut icon" href="{{ site.staticContentPath }}/web-gallery/v2/favicon.ico" type="image/vnd.microsoft.icon" />
    <link rel="alternate" type="application/rss+xml" title="{{ site.siteName }}{{ locale.profile_verify_email_rss }}" href="{{ site.sitePath }}/articles/rss.xml" />
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
var habboName = "{{ locale.profile_verify_email_session_loggedin_playerdetails_getname|escape('js') }} "" }}";
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


<meta name="description" content="{{ locale.profile_verify_email_join_the_world_s_largest_virtual_hangout_where_you_can_meet_and_make_friends_design_your_own_rooms_collect_cool_furniture_throw_parties_and_so_much_more_create_your_free }} {{ site.siteName }} {{ locale.profile_verify_email_today }}" />
<meta name="keywords" content="{{ site.siteName }}{{ locale.profile_verify_email_virtual_world_join_groups_forums_play_games_online_friends_teens_collecting_social_network_create_collect_connect_furniture_virtual_goods_sharing_badges_social_networking_hangout_safe_music_celebrity_celebrity_visits_cele }}" />

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
<meta name="build" content="{{ locale.profile_verify_email_havanaweb }}" />
</head>

{% include "../base/header.tpl" %}

<div id="content-container">

	<div id="navi2-container" class="pngbg">
    <div id="navi2" class="pngbg clearfix">
		<ul>
			<li class="">
				<a href="{{ site.sitePath }}/me">{{ locale.profile_verify_email_home }}</a>			</li>
    		<li class="">
				<a href="{{ site.sitePath }}/home/{{ playerDetails.getName() }}">{{ locale.profile_verify_email_my_page }}</a>    		</li>
    		<li class="selected">
				{{ locale.profile_verify_email_account_settings }}    		</li>
			<li class=" last">
				<a href="{{ site.sitePath }}/club">{{ site.siteName }} {{ locale.profile_verify_email_club }}</a>
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

<h2 class="title">{{ locale.profile_verify_email_account_settings }}</h2>
<div class="box-content">
            <div id="settingsNavigation">
            <ul>
				<li><a href="{{ site.sitePath }}/profile?tab=1">{{ locale.profile_verify_email_my_clothing }}</a>
                </li><li><a href="{{ site.sitePath }}/profile?tab=2">{{ locale.profile_verify_email_my_preferences }}</a>
                </li><li class="selected">{% if accountActivated %}{{ locale.profile_verify_email_my_email }}{% else %}{{ locale.profile_verify_email_email_changing_verification }}{% endif %}
                </li><li><a href="{{ site.sitePath }}/profile?tab=4">{{ locale.profile_verify_email_my_password }}</a>
                </li><li><a href="{{ site.sitePath }}/profile?tab=5">{{ locale.profile_verify_email_friend_management }}</a>
								</li><li><a href="{{ site.sitePath }}/profile?tab=6">{{ locale.profile_verify_email_trade_settings }}</a>
                </li>            </ul>
            </div>
</div></div>
    {% include "profile/profile_widgets/join_club.tpl" %}
</div>
    <div class="habblet-container " style="float:left; width: 560px;">
        <div class="cbb clearfix settings">

            <h2 class="title">{{ locale.profile_verify_email_change_and_verify_your_email }}</h2>
            <div class="box-content">

{% if alert.hasAlert %}
<div class="rounded rounded-{{ alert.colour }}">{{ alert.message }}<br />	</div><br />
{% endif %}

<form action="{{ site.sitePath }}/profile/send_email" method="post" id="emailform">
<input type="hidden" name="tab" value="3" />
<input type="hidden" name="__app_key" value="{{ locale.profile_verify_email_havanaweb }}" />

<div class="settings-step">

	<h4>{{ locale.profile_verify_email_you_haven_t_confirmed_that_your_email_is_valid }}</h4><br />
	<p>{{ locale.profile_verify_email_how_to_activate_and_confirm_my_email_click_the_button_and_send_a_confirmation_email_to_your_email_account_click_the_link_from_the_email_message_and_you_are_done_click_the_button_below_if_you_want_us_to_send_you_another_account_activation_message }}</p>
	<div class="settings-buttons">
		<a href="#" class="new-button" style="display: none" id="emailform-submit"><b>{{ locale.profile_verify_email_activate_my_email_address }}</b><i></i></a>
		<noscript><input type="submit" value="{{ locale.profile_verify_email_activate_my_email_address }}" name="save" class="submit" /></noscript>
	</div>
</div>
<br /><br /><hr>
<div class="settings-step">
	<p>{{ locale.profile_verify_email_we_have_noticed_that_you_have_logged_in_with_your_habbo_account_in_order_to_change_your_email_you_need_to_go_to_the_account_management_settings_page_click_the_link_below_to_enter_the_habbo_account_change_email_page }}</p>
	<p><a href="{{ site.sitePath }}/profile?tab=3">{{ locale.profile_verify_email_go_to_the_habbo_account_change_email_page }}</a></p>
</div>
</div>
</div>
                      
</form>

<script type="text/javascript">
$("emailform-submit").observe("click", function(e) { e.stop(); $("emailform").submit(); });
$("emailform-submit").show();
</script>
<script type="text/javascript">
$("confirmform-texts").hide();
</script>                
</div></div></div></div>
</div>

<script type="text/javascript">
HabboView.run();

</script>


</body>
</html>