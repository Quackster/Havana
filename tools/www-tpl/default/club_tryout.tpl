
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="{{ locale.global_html_lang }}" lang="{{ locale.global_html_lang }}">
<head>
	<meta http-equiv="content-type" content="text/html" />
	<title>{{ site.siteName }}{{ locale.club_tryout_club_tryout }} </title>

<script type="text/javascript">
var andSoItBegins = (new Date()).getTime();
</script>
    <link rel="shortcut icon" href="{{ site.staticContentPath }}/web-gallery/v2/favicon.ico" type="image/vnd.microsoft.icon" />
    <link rel="alternate" type="application/rss+xml" title="{{ site.siteName }}{{ locale.club_tryout_rss }}" href="{{ site.sitePath }}/articles/rss.xml" />
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
var habboName = "{{ locale.club_tryout_session_loggedin_playerdetails_getname|escape('js') }} "" }}";
var ad_keywords = "";
var habboReqPath = "{{ site.sitePath }}";
var habboStaticFilePath = "{{ site.staticContentPath }}/web-gallery";
var habboImagerUrl = "{{ site.staticContentPath }}/habbo-imaging/";
var habboPartner = "";
window.name = "habboMain";
if (typeof HabboClient != "undefined") { HabboClient.windowName = "client"; }

</script>



<meta name="description" content="{{ locale.club_tryout_join_the_world_s_largest_virtual_hangout_where_you_can_meet_and_make_friends_design_your_own_rooms_collect_cool_furniture_throw_parties_and_so_much_more_create_your_free }} {{ site.siteName }} {{ locale.club_tryout_today }}" />
<meta name="keywords" content="{{ site.siteName }}{{ locale.club_tryout_virtual_world_join_groups_forums_play_games_online_friends_teens_collecting_social_network_create_collect_connect_furniture_virtual_goods_sharing_badges_social_networking_hangout_safe_music_celebrity_celebrity_visits_cele }}" />

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
<meta name="build" content="{{ locale.club_tryout_havanaweb }}" />
</head>

{% if session.loggedIn == false %}
<body id="home" class="anonymous ">
{% else %}
<body id="home" class=" ">
{% endif %}

{% include "base/header.tpl" %}

<div id="content-container">

<div id="navi2-container" class="pngbg">
    <div id="navi2" class="pngbg clearfix">
		<ul>
			<li class="">
				<a href="{{ site.sitePath }}/credits">{{ locale.club_tryout_coins }}</a>			</li>
			<li class="">
				<a href="{{ site.sitePath }}/credits/club">{{ site.siteName }} {{ locale.club_tryout_club }}</a>			</li>
			<li class="">
				<a href="{{ site.sitePath }}/credits/collectables">{{ locale.club_tryout_collectables }}</a>			</li>
    		<li class=" last">
				<a href="{{ site.sitePath }}/credits/pixels">{{ locale.club_tryout_pixels }}</a>    		</li>
		</ul>
    </div>
</div>
<div id="container">
	<div id="content" style="position: relative" class="clearfix">
    <div id="column1" class="column">

				<div class="habblet-container ">		
						<div class="cbb clearfix red ">
	
							<h2 class="title">{{ site.siteName }} {{ locale.club_tryout_club_test_wardrobe }}							</h2>
						<div id="habboclub-tryout" class="box-content">

    <div class="rounded rounded-lightbrown clearfix">
       <p class="habboclub-logo heading">{{ locale.club_tryout_try_on_the_club_clothes_for_free_here_and_then_use_the_menu_on_the_right_to_become_a_member_and_wear_the_clothes_in_the_hotel }}<br /><br />{{ locale.club_tryout_if_you_just_want_to_change_your_look_without_using_club_clothes_or_joining }} {{ site.siteName }} {{ locale.club_tryout_club_please_go_back_to_your }} <a href="{{ site.sitePath }}/profile">{{ locale.club_tryout_account_settings }}</a></p>
    </div>

    <div id="flashcontent">
        {{ locale.club_tryout_you_need_to_have_a_flash_player_installed_on_your_computer_before_being_able_to_edit_your }} {{ site.siteName }} {{ locale.club_tryout_character_you_can_download_the_player_from_here }} <a target="_blank" href="http://www.adobe.com/go/getflashplayer" >http://www.adobe.com/go/getflashplayer</a>
    </div>    
</div>

<script type="text/javascript" language="JavaScript">
    var swfobj = new SWFObject("{{ site.sitePath }}/flash/HabboRegistration.swf", "habboreg", "435", "400", "8");
    swfobj.addParam("base", "{{ site.sitePath }}/flash/");
    swfobj.addParam("wmode", "opaque");
    swfobj.addParam("AllowScriptAccess", "always");
    swfobj.addVariable("figuredata_url", "{{ site.sitePath }}/xml/figuredata.xml");
    swfobj.addVariable("draworder_url", "{{ site.sitePath }}/xml/draworder.xml");
    swfobj.addVariable("localization_url", "{{ site.sitePath }}/xml/figure_editor.xml");
    swfobj.addVariable("habbos_url", "{{ site.sitePath }}/xml/promo_habbos_v2.xml");
    swfobj.addVariable("figure", "{{ ("figure" {{ locale.club_tryout_is_present_figure|escape('js') }} "" }}");
    swfobj.addVariable("gender", "{{ ("sex" {{ locale.club_tryout_is_present_sex|escape('js') }} ""  }}");
    swfobj.addVariable("showClubSelections", "1");
    if (deconcept.SWFObjectUtil.getPlayerVersion()["major"] >= 8) {
	    $("flashcontent").setStyle({ textAlign: "center", "marginTop" : "10px" });
	    swfobj.write("flashcontent");	    
    }
</script>			
					</div>
				</div>

				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>

</div>
<div id="column2" class="column">
			     		
				<div class="habblet-container ">		
						<div class="cbb clearfix hcred ">
	
							<h2 class="title">{{ locale.club_tryout_my_membership }}							</h2>
							

						<script src="{{ site.staticContentPath }}/web-gallery/static/js/habboclub.js" type="text/javascript"></script>
						{% include "base/hc_status.tpl" %}
					</div>
				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>

				<div class="habblet-container ">		
						<div class="cbb clearfix lightbrown ">
	
							<h2 class="title">{{ locale.club_tryout_what_is }} {{ site.siteName }} {{ locale.club_tryout_club_label }}							</h2>

						<div id="habboclub-info" class="box-content">
    <p>{{ site.siteName }} {{ locale.club_tryout_club_is_our_vip_members_only_club_absolutely_no_riff_raff_admitted_members_enjoy_a_wide_range_of_benefits_including_exclusive_clothes_free_gifts_and_an_extended_friends_list_see_below_for_all_the_sparkly_attractive_reasons_to_join }}</p>
    <h3 class="heading">{{ locale.club_tryout_one_extra_clothes_and_accessories }}</h3>
    <h3 class="heading">{{ locale.club_tryout_two_free_furni }}</h3>
    <h3 class="heading">{{ locale.club_tryout_three_exclusive_room_layouts }}</h3>
    <h3 class="heading">{{ locale.club_tryout_four_access_all_areas }}</h3>

    <h3 class="heading">{{ locale.club_tryout_five_homepage_upgrades }}</h3>
    <h3 class="heading">{{ locale.club_tryout_six_more_friends }}</h3>
    <h3 class="heading">{{ locale.club_tryout_seven_special_commands }}</h3>
    <p class="read-more"><a href="{{ site.sitePath }}/credits/club">{{ locale.club_tryout_read_more_raquo }}</a></p>
</div>
	
						
					</div>
				</div>

				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>

</div>
<script type="text/javascript">
HabboView.run();

</script>


<div id="column3" class="column">
				<div class="habblet-container ">
						<div class="ad-container">
						{% include "base/ads_container.tpl" %}
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
{% include "base/footer.tpl" %}

<script type="text/javascript">
HabboView.run();
</script>


</body>
</html>