
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="{{ locale.global_html_lang }}" lang="{{ locale.global_html_lang }}">
<head>
	<meta http-equiv="content-type" content="text/html" />
	<title>{{ site.siteName }}{{ locale.old_shockwave_app_portable_client }}</title>

<script type="text/javascript">
var andSoItBegins = (new Date()).getTime();
</script>
    <link rel="shortcut icon" href="{{ site.staticContentPath }}/web-gallery/v2/favicon.ico" type="image/vnd.microsoft.icon" />
    <link rel="alternate" type="application/rss+xml" title="{{ site.siteName }}{{ locale.old_shockwave_app_rss }}" href="{{ site.sitePath }}/articles/rss.xml" />
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
var habboName = "{{ locale.old_shockwave_app_session_loggedin_playerdetails_getname|escape('js') }} "" }}";
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
}

.text-column {
  float: left;
  margin-top:10px; 
  margin-bottom:10px; 
  margin-left:10px; 
  margin-right:10px;
}

/* Clear floats after the columns */
.row:after {
  content: "";
  display: table;
  clear: both;
}

.collapsible-content {
  padding: 0 10px;
  width:90%;
  max-height: 0;
  overflow: hidden;
  transition: max-height 0.2s ease-out;
}

.old-pc {
image-rendering:-moz-crisp-edges;          /* Firefox        */
image-rendering:-o-crisp-edges;            /* Opera          */
image-rendering:-webkit-optimize-contrast; /* Safari         */
image-rendering:optimize-contrast;         /* CSS3 Proposed  */
-ms-interpolation-mode:nearest-neighbor;   /* IE8+           */
}

</style>


<meta name="description" content="{{ locale.old_shockwave_app_join_the_world_s_largest_virtual_hangout_where_you_can_meet_and_make_friends_design_your_own_rooms_collect_cool_furniture_throw_parties_and_so_much_more_create_your_free }} {{ site.siteName }} {{ locale.old_shockwave_app_today }}" />
<meta name="keywords" content="{{ site.siteName }}{{ locale.old_shockwave_app_virtual_world_join_groups_forums_play_games_online_friends_teens_collecting_social_network_create_collect_connect_furniture_virtual_goods_sharing_badges_social_networking_hangout_safe_music_celebrity_celebrity_visits_cele }}" />

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
<meta name="build" content="{{ locale.old_shockwave_app_havanaweb }}" />
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
			<li>
				<a href="{{ site.sitePath }}/help/install_shockwave">{{ locale.old_shockwave_app_shockwave_help }}</a></li>
			<li class="selected">
				{{ locale.old_shockwave_app_portable_client_text }}			</li>
			<li class=" last">
				<a href="{{ site.sitePath }}/help/1">{{ locale.old_shockwave_app_faq }}</a>
			</li>
		</ul>
    </div>
</div>

<div id="container">
	<div id="content" style="position: relative" class="clearfix">
		<div id="column1" class="column" style="width: 50%">
			<div class="habblet-container ">		
				<div class="cbb clearfix blue ">
					<h2 class="title">{{ locale.old_shockwave_app_portable_shockwave_client }}</h2>
					<div class="tutorial-text">	
						<div class="row">
							<div class="text-column" style="width: 120px">
								<img class="old-pc" src="https://i.imgur.com/XOTfbl8.png" alt="">
							</div>
							<div class="text-column" style="width: 260px">
								<p>{{ locale.old_shockwave_app_as_the_years_go_by_so_do_the_browser_plugins_shockwave_is_a_browser_plugin_that_is_deprecated_in_most_modern_browsers_due_to_this_a_portable_program_that_runs_shockwave_habbo_is_avaliable }}</p>
								<p>{{ locale.old_shockwave_app_this_is_a_macromedia_projector_program_generated_with_director_mx_two_zero_zero_four_written_in_the_same_language_that_the_shockwave_habbo_client_is_written_in }}</p>
							</div>
						</div>
						<p><b>{{ locale.old_shockwave_app_how_does_it_work }}</b></p>
						<p>{{ locale.old_shockwave_app_the_program_is_a_simple_exe_that_sends_a_login_request_to_our_server_and_then_loads_the_client_you_ll_need_to_edit_the_account_ini_file_with_your_login_details }}</p>
						<p>{{ locale.old_shockwave_app_the_app_regardless_of_download_will_work_with_wine_which_is_a_requirement_to_play_on_either_macos_and_linux }}</p>
						<p><b>{{ locale.old_shockwave_app_why_should_i_use_shockwave }}</b></p>
						<p>{{ locale.old_shockwave_app_as_of_right_now_there_are_two_clients_to_play_the_hotel_on_the_first_is_the_shockwave_hotel_and_the_second_is_flash }}</p>
						<p>{{ locale.old_shockwave_app_it_is_highly_recommended_to_play_the_shockwave_version_because_it_s_filled_with_far_more_features_that_cannot_be_experienced_on_the_flash_client }}</p>
						<p><b>{{ locale.old_shockwave_app_pictures }}</b></p>
						<p>{{ locale.old_shockwave_app_below_are_pictures_of_the_program_working_in_action }}</p>
						<div class="article-body">
						<div id="article-wrapper">
						<div class="article-images clearfix">
						<a href="https://i.imgur.com/OrHYgxr.png" style="background-image: url(https://i.imgur.com/OrHYgxr.png); background-position: -0px -0px"></a>
						<a href="https://i.imgur.com/7yWmicl.png" style="background-image: url(https://i.imgur.com/7yWmicl.png); background-position: -0px -0px"></a>
						<a href="https://i.imgur.com/IXE91fD.png" style="background-image: url(https://i.imgur.com/IXE91fD.png); background-position: -0px -0px"></a></div>
						</div>
						</div>
						
						<script type="text/javascript" language="Javascript">
							document.observe("dom:loaded", function() {
								$$('{{ locale.old_shockwave_app_article_images_a|escape('js') }}').each(function(a) {
									Event.observe(a, 'click', function(e) {
										Event.stop(e);
										Overlay.lightbox(a.href, "{{ locale.old_shockwave_app_image_is_loading|escape('js') }}");
									});
								});
								
								$$('a.article-5').each(function(a) {
									a.replace(a.innerHTML);
								});
							});
						</script>
					</div>
				</div>

			</div>
			<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
			<div class="habblet-container ">		
				<div class="cbb clearfix ">
					<h2 class="title" style="background-color: lightblue">{{ locale.old_shockwave_app_changelog }}</h2>
					<div class="tutorial-text">	
						<div class="row">
							<div class="text-column">
								<img src="https://i.imgur.com/yDbopCD.png" alt="">
							</div>
							<div class="text-column" style="width: 365px;">
								<p>{{ locale.old_shockwave_app_the_program_has_existed_since_april_two_zero_one_nine_and_has_had_a_lot_of_changes_over_the_course_you_may_view_them_below }}</p>
							</div>
						</div>
						<a class="new-button collapsible" id="warn-clear-hand-button" href="#"><b>{{ locale.old_shockwave_app_reveal_changelog }}</b><i></i></a>
						<div class="collapsible-content" style="margin-top: 25px">
						<p><b>{{ locale.old_shockwave_app_version_zero_eight }}</b></p>
						<p>{{ locale.old_shockwave_app_fixes_for_cloudflare_changing_how_requests_are_sent_back }}</p>
						<br>
						<p><b>{{ locale.old_shockwave_app_version_zero_seven }}</b></p>
						<p>{{ locale.old_shockwave_app_fixes_for_updating_furniture }}</p>
						<br>
						<p><b>{{ locale.old_shockwave_app_version_zero_six }}</b></p>
						<p>{{ locale.old_shockwave_app_furniture_is_now_stored_and_loaded_from_disk_to_decrease_furniture_load_times }}</p>
						<p>{{ locale.old_shockwave_app_new_furniture_will_be_automatically_downloaded_when_loaded_so_subsequent_loading_will_be_faster }}</p>
						<br>
						<p><b>{{ locale.old_shockwave_app_version_zero_five }}</b></p>
						<br>
						<p>{{ locale.old_shockwave_app_added_hotel_view_support_which_is_an_option_that_can_be_changed_in_your_account_settings }}</p>
						<p>{{ locale.old_shockwave_app_added_always_on_top_feature }}</p>
						<p>{{ locale.old_shockwave_app_added_login_in_the_client_instead_an_opt_in_if_you_don_t_trust_login_details_being_stored_on_text }}</p>
						<br>
						<p><b>{{ locale.old_shockwave_app_version_zero_four }}</b></p>
						<p>{{ locale.old_shockwave_app_added_patches_for_snowstorm_to_become_playable_without_freezing }}</p>
						<br>
						<p><b>{{ locale.old_shockwave_app_version_zero_three }}</b></p>
						<p>{{ locale.old_shockwave_app_fix_for_working_hyperlinks }}</p>
						<p>{{ locale.old_shockwave_app_fix_for_the_reset_tutorial_button_not_properly_sending_request_to_server }}</p>
						<br>
						<p><b>{{ locale.old_shockwave_app_version_zero_two }}</b></p>
						<p>{{ locale.old_shockwave_app_fix_for_miscellaneous_symbols_not_being_allowed_in_passwords }}</p>
						<p>{{ locale.old_shockwave_app_add_joystick_icon_created_by_copyright }}</p>
						<br>
						<p><b>{{ locale.old_shockwave_app_version_zero_one }}</b></p>
						<p>{{ locale.old_shockwave_app_initial_release }}</p>
						</div>
						<!-- <p>{{ locale.old_shockwave_app_the_features_that_shockwave_contains_which_are_not_present_in_the_flash_version_are_listed_below }}</p>
						<div class="row">
							<div class="column" style="margin-top:10px; margin-bottom:10px; margin-right:10px">
								<img src="{{ site.staticContentPath }}/c_images/stickers/sticker_submarine.gif" alt="">
							</div>
							<div class="column" style="margin-top:10px; margin-bottom:10px; margin-left:10px; width: 275px;">
								<p><b>{{ locale.old_shockwave_app_battleball_diving_wobble_squabble_trax_machines_jukeboxes_american_idol_tic_tac_toe_chess_battleships_poker }}</b> {{ locale.old_shockwave_app_and_some_nostalgic_habbo_components_such_as_the_hand_and_the_room_o_matic }}</p>
							</div>
						</div> -->
					</div>
				</div>
			</div>
		</div>
		<div id="column2" class="column" style="width: 305px">	     		
			<div class="habblet-container ">		
				<div class="cbb clearfix">
					<h2 class="title" style="background-color: gray">{{ locale.old_shockwave_app_downloads }}</h2>
					<div class="tutorial-text">							</h2>
						<p style="margin-top:10px; margin-right:10px">{{ locale.old_shockwave_app_there_are_two_versions_to_download_the_standard_download_and_the_lite_download }}</p>
						<p><b>{{ locale.old_shockwave_app_standard_version }}</b></p>
						<p>{{ locale.old_shockwave_app_the_standard_version_contains_all_the_furniture_for_instant_loading_new_furniture_will_be_downloaded_if_the_file_doesn_t_exist }}</p>
						<p> - <a href="https://classichabbo.com/classichabbo_exe.zip">{{ locale.old_shockwave_app_click_here }}</a> {{ locale.old_shockwave_app_to_download_eight_two_mb }}</p>
						<p><b>{{ locale.old_shockwave_app_lite_version }}</b></p>
						<p>{{ locale.old_shockwave_app_contains_no_furniture_new_furniture_will_be_downloaded_if_the_file_doesn_t_exist }}</p>
						<p> - <a href="https://classichabbo.com/classichabbo_exe_lite.zip">{{ locale.old_shockwave_app_click_here }}</a> {{ locale.old_shockwave_app_to_download_two_five_mb }}</p>
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
	
	var coll = document.getElementsByClassName("collapsible");
	var i;

	for (i = 0; i < coll.length; i++) {
		coll[i].addEventListener("click", function() {
			this.classList.toggle("active");
			var content = this.nextElementSibling;
			if (content.style.maxHeight){
				content.style.maxHeight = null;
			} else {
				content.style.maxHeight = content.scrollHeight + "px";
			} 
		});
	}

</script>


</body>
</html>