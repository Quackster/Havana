
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="{{ locale.global_html_lang }}" lang="{{ locale.global_html_lang }}">
<head>
	<meta http-equiv="content-type" content="text/html" />
	<title>{{ site.siteName }}{{ locale.account_email_recovery }} </title>

<script type="text/javascript">
var andSoItBegins = (new Date()).getTime();
</script>
    <link rel="shortcut icon" href="{{ site.staticContentPath }}/web-gallery/v2/favicon.ico" type="image/vnd.microsoft.icon" />
    <link rel="alternate" type="application/rss+xml" title="{{ site.siteName }}{{ locale.account_email_rss }}" href="{{ site.sitePath }}/articles/rss.xml" />

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


<meta name="description" content="{{ locale.account_email_join_the_world_s_largest_virtual_hangout_where_you_can_meet_and_make_friends_design_your_own_rooms_collect_cool_furniture_throw_parties_and_so_much_more_create_your_free_habbo_today }}" />
<meta name="keywords" content="{{ locale.account_email_habbo_virtual_world_join_groups_forums_play_games_online_friends_teens_collecting_social_network_create_collect_connect_furniture_virtual_goods_sharing_badges_social_networking_hangout_safe_music_celebrity_celebrity_visits_cele }}" />

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
<meta name="build" content="{{ locale.account_email_havanaweb }}" />
</head>
<body  class="process-template">

<div id="overlay"></div>

<div id="container">
	<div class="cbb process-template-box clearfix">
		<div id="content">

			{% include "../../base/frontpage_header.tpl" %}
		<div id="process-content">
<style type="text/css">
		div.left-column { float: left; width: 65% }
		label { display: block }
		p { margin:0px }
	</style>
	<div id="process-content">
		{% if alert.hasAlert %}

		
	<div class="cbb clearfix {{ alert.colour }}">
    <h2 class="title">{{ locale.account_email_account_recovery }}</h2>
    <div class="box-content">
        <p>{{ alert.message }}</p>
		</div>
		</div>
		
		{% else %}
		
		
		<div class="cbb clearfix green">
		<h2 class="title" style="background-color: #429B00;">{{ locale.account_email_account_recovery }}</h2>
		<div class="box-content">
        <p>{{ locale.account_email_please_enter_and_confirm_your_new_password_below_to_recover_your_account }}</p>
		<div class="clear"></div>
        <form method="post" action="recovery" id="accountlist-form">
            <p>
            <label for="password-field">{{ locale.account_email_password }}</label>
            <input type="text" name="password" id="password-field" value="" />
            </p>
			<div class="clear"></div>
			 <p>
            <label for="confirmpassword-field">{{ locale.account_email_confirm_password }}</label>
            <input type="text" name="confirmpassword" id="confirmpassword-field" value="" />
            </p>

            <p>
            <input type="submit" value="{{ locale.account_email_reset_password }}" name="actionList" class="submit process-button" id="accountlist-submit" />
            </p>
            <input type="hidden" value="{{ recoveryCode }}" name="recovery_code" />
			<input type="hidden" value="{{ userId }}" name="user_id" />
        </form>
		<!-- <a href="{{ site.sitePath }}/">{{ locale.account_email_continue_to }} {{ site.siteName }} {{ locale.account_email_front_page }}</a> -->
		
		</div>
		</div>
		
		
		{% endif %}

</div>



<!--[if lt IE 7]>
<script type="text/javascript">
Pngfix.doPngImageFix();
</script>
<![endif]-->

{% include "../../base/footer.tpl" %}

<script type="text/javascript">
HabboView.run();
</script>


</body>
</html>