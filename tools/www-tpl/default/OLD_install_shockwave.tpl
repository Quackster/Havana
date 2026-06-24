
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="{{ locale.global_html_lang }}" lang="{{ locale.global_html_lang }}">
<head>
	<meta http-equiv="content-type" content="text/html" />
	<title>{{ site.siteName }}{{ locale.old_install_shockwave_shockwave_help }}</title>

<script type="text/javascript">
var andSoItBegins = (new Date()).getTime();
</script>
    <link rel="shortcut icon" href="{{ site.staticContentPath }}/web-gallery/v2/favicon.ico" type="image/vnd.microsoft.icon" />
    <link rel="alternate" type="application/rss+xml" title="{{ site.siteName }}{{ locale.old_install_shockwave_rss }}" href="{{ site.sitePath }}/articles/rss.xml" />
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
var habboName = "{{ locale.old_install_shockwave_session_loggedin_playerdetails_getname|escape('js') }} "" }}";
var ad_keywords = "";
var habboReqPath = "{{ site.sitePath }}";
var habboStaticFilePath = "{{ site.staticContentPath }}/web-gallery";
var habboImagerUrl = "{{ site.staticContentPath }}/habbo-imaging/";
var habboPartner = "";
window.name = "habboMain";
if (typeof HabboClient != "undefined") { HabboClient.windowName = "client"; }

</script>

<style>
.tutorial-text {
  margin-left: 10px;
  margin-right: 10px;
}
/* Create two equal columns that floats next to each other */
.column {
  float: left;
  width: 137px;
}

/* Clear floats after the columns */
.row:after {
  content: "";
  display: table;
  clear: both;
}
</style>


<meta name="description" content="{{ locale.old_install_shockwave_join_the_world_s_largest_virtual_hangout_where_you_can_meet_and_make_friends_design_your_own_rooms_collect_cool_furniture_throw_parties_and_so_much_more_create_your_free }} {{ site.siteName }} {{ locale.old_install_shockwave_today }}" />
<meta name="keywords" content="{{ site.siteName }}{{ locale.old_install_shockwave_virtual_world_join_groups_forums_play_games_online_friends_teens_collecting_social_network_create_collect_connect_furniture_virtual_goods_sharing_badges_social_networking_hangout_safe_music_celebrity_celebrity_visits_cele }}" />

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
<meta name="build" content="{{ locale.old_install_shockwave_havanaweb }}" />
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
			<li class="selected">
				{{ locale.old_install_shockwave_shockwave_help_text }}</li>
			<li>
				<a href="{{ site.sitePath }}/help/shockwave_app">{{ locale.old_install_shockwave_portable_client }}</a></li>
			<li class=" last">
				<a href="{{ site.sitePath }}/help/1">{{ locale.old_install_shockwave_faq }}</a>
			</li>
		</ul>
    </div>
</div>

<div id="container">
	<div id="content" style="position: relative" class="clearfix">
		<div id="column1" class="column" style="width: 50%">
			<div class="habblet-container ">		
				<div class="cbb clearfix blue ">
					<h2 class="title">{{ locale.old_install_shockwave_how_to_use_shockwave }}</h2>
					<div class="tutorial-text">	
						<p style="margin-top: 10px">{{ locale.old_install_shockwave_in_order_to_load_the_shockwave_hotel_you_must_follow_these_steps_and_ensure_you_have_the_prequisities_required }}</p>
						<p><b>{{ locale.old_install_shockwave_pale_moon }}</b></p>
						<p>{{ locale.old_install_shockwave_pale_moon_is_a_necessity_to_run_shockwave_correctly_as_it_s_one_of_the_few_browsers_that_still_supports_npapi_plugins_correctly }}</p>
						<p>{{ locale.old_install_shockwave_since_shockwave_is_quite_old_the }} <b>{{ locale.old_install_shockwave_three_two_bit }}</b> {{ locale.old_install_shockwave_version_of_pale_moon_is_required_here_you_can_download_the }} <a href="https://www.palemoon.org/download.shtml#Portable_versions">{{ locale.old_install_shockwave_portable }}</a> {{ locale.old_install_shockwave_or_the }} <a href="https://www.palemoon.org/download.shtml">{{ locale.old_install_shockwave_full }}</a> {{ locale.old_install_shockwave_version }}</p>
						<p><b>{{ locale.old_install_shockwave_shockwave_one_two }}</b></p>
						<p>{{ locale.old_install_shockwave_you_must_install_the_shockwave_one_two_msi_first_and_then_proceed_to_install_the_visual_studio_two_zero_zero_eight_c_x_eight_six_redist }}</p>
						<p><i>{{ locale.old_install_shockwave_download_list }}</i></p>
						<p>{{ locale.old_install_shockwave_adobe_shockwave_one_two_three_msi }} <a href="https://alex-dev.org/shockwave/12.3/sw_lic_full_installer.msi" target="_blank">{{ locale.old_install_shockwave_download }}</a></p>
						<p>{{ locale.old_install_shockwave_microsoft_visual_c_two_zero_zero_eight_redistributable_package_x_eight_six }} <a href="https://www.microsoft.com/en-au/download/details.aspx?id=29" target="_blank">{{ locale.old_install_shockwave_download }}</a></p>
						<p>{{ locale.old_install_shockwave_also_please_make_sure_you }} <b>{{ locale.old_install_shockwave_do_not_have_a_browser_open }}</b> {{ locale.old_install_shockwave_when_installing_the_shockwave_msi_as_you_will_need_to_start_a_fresh_windows_installation_since_the_current_installation_breaks_with_a_browser_open }}</p>
						<p><b>{{ locale.old_install_shockwave_shockwave_one_one }}</b></p>
						<p>{{ locale.old_install_shockwave_while_shockwave_one_one_six_is_older_than_the_latest_version_that_is_shockwave_one_two_the_latest_version_experiences_issues_with_crashing_while_playing_music_from_the_trax_machine_or_jukebox_and_also_messsages_in_the_instant_messenger_are_always_stuck_at_one_two_zero_zero }}</p>
						<p>{{ locale.old_install_shockwave_for_these_reasons_shockwave_one_one_is_recommended_to_install_instead_since_these_issues_are_not_present_in_this_version }}</p>
						<p>{{ locale.old_install_shockwave_you_can_download_the_official_shockwave_one_one_six_installer_msi }} <a href="https://alex-dev.org/shockwave/11.6/sw_lic_full_installer.msi">{{ locale.old_install_shockwave_here }}</a>.</p>
						
						<!-- <p><b>{{ locale.old_install_shockwave_shockwave_one_two }}</b></p>
						<p>{{ locale.old_install_shockwave_if_the_shockwave_one_one_steps_do_not_work_there_is_another_option_to_install_shockwave_one_two_but_this_version_brings_issues_that_shockwave_one_one_does_not_have }}</p>
						<p>{{ locale.old_install_shockwave_you_can_download_the_official_shockwave_one_two_full_installer }} <a href="https://www.palemoon.org/download.shtml">{{ locale.old_install_shockwave_here }}</a>.</p>
						<p>{{ locale.old_install_shockwave_please_download_the }} <a href="">{{ locale.old_install_shockwave_xtras }}</a> {{ locale.old_install_shockwave_and_extract_these_folders_into }} <i>C:/Windows</i>{{ locale.old_install_shockwave_these_will_apply_the_shockwave_files_that_would_otherwise_be_automatically_downloaded }}</p>
						<p></p> -->
					</div>
				</div>

			</div>
			<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
			<div class="habblet-container ">		
				<div class="cbb clearfix red ">
					<h2 class="title">{{ locale.old_install_shockwave_why_should_i_use_shockwave }}</h2>
					<div class="tutorial-text">	
						<p style="margin-top: 10px">{{ locale.old_install_shockwave_as_of_right_now_there_are_two_clients_to_play_the_hotel_on_the_first_is_the_shockwave_hotel_and_the_second_is_flash }}</p>
						<p>{{ locale.old_install_shockwave_it_is_highly_recommended_to_play_the_shockwave_version_because_it_s_filled_with_far_more_features_that_cannot_be_experienced_on_the_flash_client }}</p>
						<p>{{ locale.old_install_shockwave_the_features_that_shockwave_contains_which_are_not_present_in_the_flash_version_are_listed_below }}</p>
						<div class="row">
							<div class="column" style="margin-top:10px; margin-bottom:10px; margin-right:10px">
								<img src="{{ site.staticContentPath }}/c_images/stickers/sticker_submarine.gif" alt="">
							</div>
							<div class="column" style="margin-top:10px; margin-bottom:10px; margin-left:10px; width: 275px;">
								<p><b>{{ locale.old_install_shockwave_battleball_diving_wobble_squabble_trax_machines_jukeboxes_american_idol_tic_tac_toe_chess_battleships_poker }}</b> {{ locale.old_install_shockwave_and_some_nostalgic_habbo_components_such_as_the_hand_and_the_room_o_matic }}</p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="column2" class="column" style="width: 305px">	     		
			<div class="habblet-container ">		
				<div class="cbb clearfix">
					<h2 class="title" style="background-color: darkorange">{{ locale.old_install_shockwave_prerequisities }}</h2>
					<div class="tutorial-text">							</h2>
						<div class="row">
							<div class="column" style="width: 100px; margin-top:10px; margin-bottom:10px; margin-right:10px">
								<img class="credits-image" src="https://i.imgur.com/6zrNiqZ.gif" alt="" width="100" height="100">
							</div>
							<div class="column" style="width: 165px;">
								<p style="margin-top: 10px">{{ locale.old_install_shockwave_the_following_items_are_required_to_use_shockwave_are_listed_below }}</p>
								<p>{{ locale.old_install_shockwave_if_you_fail_to_meet_these_requirements_you_will_only_be_able_to_play_the_flash_version }}</p>
							</div>
						</div>
						<p><b>{{ locale.old_install_shockwave_requirements }}</b></p> 
						<p> {{ locale.old_install_shockwave_microsoft_windows_or }}</p>
						<p> {{ locale.old_install_shockwave_wine_for_linux_and_macos_not_supported_by_classic_staff_as_may_be_unreliable }}</p>
						<p> {{ locale.old_install_shockwave_shockwave_at_least_one_one_six_or_higher }}</p>
						<p> {{ locale.old_install_shockwave_pale_moon_three_two_bit }}</p>
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
</div>
{% include "base/footer.tpl" %}

<script type="text/javascript">
	HabboView.run();
</script>


</body>
</html>