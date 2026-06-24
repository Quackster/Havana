
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="{{ locale.global_html_lang }}" lang="{{ locale.global_html_lang }}">
<head>
	<meta http-equiv="content-type" content="text/html" />
	<title>{{ site.siteName }}:  </title>

<script type="text/javascript">
var andSoItBegins = (new Date()).getTime();
</script>
    <link rel="shortcut icon" href="{{ site.staticContentPath }}/web-gallery/v2/favicon.ico" type="image/vnd.microsoft.icon" />
    <link rel="alternate" type="application/rss+xml" title="{{ site.siteName }}{{ locale.faq_rss }}" href="{{ site.sitePath }}/articles/rss.xml" />
<script src="{{ site.staticContentPath }}/web-gallery/static/js/visual.js" type="text/javascript"></script>

<script src="{{ site.staticContentPath }}/web-gallery/static/js/libs.js" type="text/javascript"></script>
<script src="{{ site.staticContentPath }}/web-gallery/static/js/common.js" type="text/javascript"></script>
<script src="{{ site.staticContentPath }}/web-gallery/static/js/fullcontent.js" type="text/javascript"></script>
<script src="{{ site.staticContentPath }}/web-gallery/static/js/libs2.js" type="text/javascript"></script>
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/style.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/buttons.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/boxes.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/tooltips.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/styles/local/com.css" type="text/css" />

<script src="{{ site.staticContentPath }}/web-gallery/js/local/com.js" type="text/javascript"></script>

<script type="text/javascript">
document.habboLoggedIn = {{ session.loggedIn }};
var habboName = "{{ locale.faq_session_loggedin_playerdetails_getname|escape('js') }} "" }}";
var ad_keywords = "";
var habboReqPath = "{{ site.sitePath }}";
var habboStaticFilePath = "{{ site.staticContentPath }}/web-gallery";
var habboImagerUrl = "{{ site.staticContentPath }}/habbo-imaging/";
var habboPartner = "";
window.name = "habboMain";

</script>



<meta name="description" content="{{ locale.faq_join_the_world_s_largest_virtual_hangout_where_you_can_meet_and_make_friends_design_your_own_rooms_collect_cool_furniture_throw_parties_and_so_much_more_create_your_free }} {{ site.siteName }} {{ locale.faq_today }}" />
<meta name="keywords" content="{{ site.siteName }}{{ locale.faq_virtual_world_join_groups_forums_play_games_online_friends_teens_collecting_social_network_create_collect_connect_furniture_virtual_goods_sharing_badges_social_networking_hangout_safe_music_celebrity_celebrity_visits_cele }}" />

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
<meta name="build" content="{{ locale.faq_havanaweb }}" />
</head>
<body id="faq" class="plain-template">
<script src="{{ site.staticContentPath }}/web-gallery/static/js/faq.js" type="text/javascript"></script>
<div id="faq" class="clearfix">
<div id="faq-header" class="clearfix"><img src="{{ site.staticContentPath }}/web-gallery/v2/images/faq/faq_header.png" /><form method="post" action="{{ site.sitePath }}/help/faqsearch" class="search-box"><input type="text" id="faq-search" name="query" class="search-box-query search-box-onfocus" size="50" value="{{ locale.faq_search_placeholder }}"/><input type="submit" value="" title="{{ locale.faq_search }}" class="search" /></form></div>
<div id="faq-container" class="clearfix">
<div id="faq-category-list">
<ul class="faq">
<li><a href="{{ site.sitePath }}/help/1" name=""><span class="faq-link">{{ locale.faq_contact_us }}</span></a></li>
</ul>
</div>
<div id="faq-category-content" class="clearfix" >
<p class="faq-category-description"></p>
<h4 id="faq-item-header-2" class="faq-item-header faq-toggle "><span class="faq-toggle selected" id="faq-header-text-2">{{ locale.faq_how_do_i_contact }} {{ site.siteName }}?</span></h4>
	<div id="faq-item-content-2" class="faq-item-content clearfix">
	    <div class="faq-item-content clearfix">{{ locale.faq_please_use_the }} <a href="{{ site.sitePath }}/iot/go">{{ locale.faq_help_tool }}</a> {{ locale.faq_to_email_us }}</div>
	<div class="faq-close-container">
	<div id="faq-close-button-2" class="faq-close-button clearfix faq-toggle" style="display:none">{{ locale.faq_close_faq }} <img id="faq-close-image-2" class="faq-toggle" src="{{ site.staticContentPath }}/web-gallery/v2/images/faq/close_btn.png"/></div>
	</div>
	</div>

	<script type="text/javascript">
	    
	    $("faq-close-button-2").show();
	</script>
<h4 id="faq-item-header-3" class="faq-item-header faq-toggle "><span class="faq-toggle selected" id="faq-header-text-3">{{ locale.faq_will_sending_my_issue_twice_get_a_faster_reply }}</span></h4>
	<div id="faq-item-content-3" class="faq-item-content clearfix">
	    <div class="faq-item-content clearfix">{{ locale.faq_sending_more_than_one_email_will_slow_down_the_player_support_staff_as_they_will_have_more_emails_to_read_through_if_you_have_received_no_response_after_a_week_check_your_spam_junk_mail_folder_if_there_is_still_no_response_then_there_must_have_been_a_technical_glitch_and_you_should_send_your_email_again }}</div>
	<div class="faq-close-container">
	<div id="faq-close-button-3" class="faq-close-button clearfix faq-toggle" style="display:none">{{ locale.faq_close_faq }} <img id="faq-close-image-3" class="faq-toggle" src="{{ site.staticContentPath }}/web-gallery/v2/images/faq/close_btn.png"/></div>
	</div>
	</div>

	<script type="text/javascript">
	    $("faq-item-content-3").hide();
	    $("faq-close-button-3").show();
	</script>
<script type="text/javascript">
    FaqItems.init();
    SearchBoxHelper.init();
</script>
</div>

</div>

<div id="faq-footer" class="clearfix"><p><a href="http://localhost/" target="_self">{{ locale.faq_homepage }}</a> | <a href="http://localhost/papers/disclaimer" target="_self">{{ locale.faq_disclaimer }}</a> | <a href="http://localhost/papers/privacy" target="_self">{{ locale.faq_privacy_policy }}</a> | <a href="http://localhost/help/1" target="_new">{{ locale.faq_contact_us }}</a></p>
		<p>{{ locale.faq_this_is_an_unofficial_habbo_fansite_to_see_what_habbo_looked_like_in_two_zero_zero_nine }}<br />{{ locale.faq_habbo_is_a_registered_trademark_of_sulake_corporation_all_rights_reserved_to_their_respective_owner_s }}</p>
	</div>			</div>
        </div>
    </div>
	</div>
<script type="text/javascript">
HabboView.run();
</script>


</body>
</html>
