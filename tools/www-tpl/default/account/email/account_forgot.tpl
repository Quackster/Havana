
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="{{ locale.global_html_lang }}" lang="{{ locale.global_html_lang }}">
<head>
	<meta http-equiv="content-type" content="text/html" />
	<title>{{ site.siteName }}{{ locale.account_email_forgotten_password }} </title>

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
		div.left-column { float: left; width: 50% }
		div.right-column { float: right; width: 49% }
		label { display: block }
		input { width: 98% }
		input.process-button { width: auto; float: right }
	</style>

			<div id="process-content">
	        	<div class="left-column">
<div class="cbb clearfix">
    <h2 class="title">{{ locale.account_email_forgotten_your_password }}</h2>
    <div class="box-content">
		{% if invalidForgetPassword %}
		<div class="rounded rounded-red">
                {{ locale.account_email_invalid_username_or_e_mail_address }} <br />
        </div>
        <div class="clear"></div>
		{% endif %}
		{% if validForgetPassword %}
		<div class="rounded rounded-green">
                {{ locale.account_email_an_email_has_been_sent_with_recovery_details }} <br />
        </div>
        <div class="clear"></div>
		{% endif %}
        <p>{{ locale.account_email_don_t_panic_please_enter_your_account_information_below_and_we_ll_send_you_an_email_telling_you_how_to_reset_your_password }}</p>

        <div class="clear"></div>

        <form method="post" action="forgot" id="forgottenpw-form">
            <p>
            <label for="forgottenpw-username">{{ locale.account_email_username }}</label>
            <input type="text" name="forgottenpw-username" id="forgottenpw-username" value="" />
            </p>

            <p>
            <label for="forgottenpw-email">{{ locale.account_email_email_address }}</label>
            <input type="text" name="forgottenpw-email" id="forgottenpw-email" value="" />
            </p>

            <p>
            <input type="submit" value="{{ locale.account_email_request_password_email }}" name="actionForgot" class="submit process-button" id="forgottenpw-submit" />
            </p>
            <input type="hidden" value="{{ locale.account_email_default }}" name="origin" />
        </form>
    </div>
</div>

</div>


<div class="right-column">

<div class="cbb clearfix">
    <h2 class="title">{{ locale.account_email_forgotten_your_habbo_name }}</h2>
    <div class="box-content">
		{% if invalidForgetName %}
		<div class="rounded rounded-red">
                {{ locale.account_email_invalid_username_or_e_mail_address }} <br />
        </div>
        <div class="clear"></div>
		{% endif %}
		{% if validForgetName %}
		<div class="rounded rounded-green">
                {{ locale.account_email_a_list_of_names_have_been_sent_to_the_e_mail_address }} <br />
        </div>
        <div class="clear"></div>
		{% endif %}
        <p>{{ locale.account_email_no_problem_just_enter_your_email_address_below_and_we_ll_send_you_a_list_of_your_accounts }}</p>

        <div class="clear"></div>

        <form method="post" action="forgot" id="accountlist-form">
            <p>

            <label for="accountlist-owner-email">{{ locale.account_email_email_address }}</label>
            <input type="text" name="ownerEmailAddress" id="accountlist-owner-email" value="" />
            </p>

            <p>
            <input type="submit" value="{{ locale.account_email_get_my_accounts }}" name="actionList" class="submit process-button" id="accountlist-submit" />
            </p>
            <input type="hidden" value="{{ locale.account_email_default }}" name="origin" />
        </form>

    </div>
</div>

<div class="cbb clearfix">
    <h2 class="title">{{ locale.account_email_false_alarm }}</h2>
    <div class="box-content">
        <p>{{ locale.account_email_if_you_have_remembered_your_password_or_if_you_just_came_here_by_accident_click_the_link_below_to_return_to_the_homepage }}</p>
        <p><a href="{{ site.sitePath }}">{{ locale.account_email_back_to_homepage_raquo }}</a></p>
    </div>
</div>

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