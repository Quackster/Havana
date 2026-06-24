
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="{{ locale.global_html_lang }}" lang="{{ locale.global_html_lang }}">
<head>
	<meta http-equiv="content-type" content="text/html" />
	<title>{{ site.siteName }}{{ locale.club_club }} </title>

<script type="text/javascript">
var andSoItBegins = (new Date()).getTime();
</script>
    <link rel="shortcut icon" href="{{ site.staticContentPath }}/web-gallery/v2/favicon.ico" type="image/vnd.microsoft.icon" />
    <link rel="alternate" type="application/rss+xml" title="{{ site.siteName }}{{ locale.club_rss }}" href="{{ site.sitePath }}/articles/rss.xml" />
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
var habboName = "{{ playerDetails.getName() }}";
var ad_keywords = "";
var habboReqPath = "{{ site.sitePath }}";
var habboStaticFilePath = "{{ site.staticContentPath }}/web-gallery";
var habboImagerUrl = "{{ site.staticContentPath }}/habbo-imaging/";
var habboPartner = "";
window.name = "habboMain";
if (typeof HabboClient != "undefined") { HabboClient.windowName = "client"; }

</script>



<meta name="description" content="{{ locale.club_join_the_world_s_largest_virtual_hangout_where_you_can_meet_and_make_friends_design_your_own_rooms_collect_cool_furniture_throw_parties_and_so_much_more_create_your_free }} {{ site.siteName }} {{ locale.club_today }}" />
<meta name="keywords" content="{{ site.siteName }}{{ locale.club_virtual_world_join_groups_forums_play_games_online_friends_teens_collecting_social_network_create_collect_connect_furniture_virtual_goods_sharing_badges_social_networking_hangout_safe_music_celebrity_celebrity_visits_cele }}" />

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
<meta name="build" content="{{ locale.club_havanaweb }}" />
</head>

{% if session.loggedIn == false %}
<body id="home" class="anonymous ">
{% else %}
<body id="home" class=" ">
{% endif %}

{% include "base/header.tpl" %}

<div id="content-container">

{% if session.currentPage == "credits" %}
<div id="navi2-container" class="pngbg">
    <div id="navi2" class="pngbg clearfix">
		<ul>
			<li class="">
				<a href="{{ site.sitePath }}/credits">{{ locale.club_coins }}</a>			</li>
			<li class="selected">
				{{ site.siteName }} {{ locale.club_club_text }}			</li>
			<li class="">
				<a href="{{ site.sitePath }}/credits/collectables">{{ locale.club_collectables }}</a>			</li>
    		<li class=" last">
				<a href="{{ site.sitePath }}/credits/pixels">{{ locale.club_pixels }}</a>    		</li>
		</ul>
    </div>
</div>
{% endif %}

{% if session.currentPage == "me" %}
<div id="navi2-container" class="pngbg">
    <div id="navi2" class="pngbg clearfix">
		<ul>
			<li class="">
				<a href="{{ site.sitePath }}/me">{{ locale.club_home }}</a>			</li>
    		<li class="">
				<a href="{{ site.sitePath }}/home/{{ playerDetails.getName() }}">{{ locale.club_my_page }}</a>    		</li>
			<li class="">
				<a href="{{ site.sitePath }}/profile">{{ locale.club_account_settings }}</a>			</li>
				<li class="selected{% if gameConfig.getInteger('guides.group.id') == 0 %} last{% endif %}">
			<a href="{{ site.sitePath }}/club">{{ site.siteName }} {{ locale.club_club_text }}</a>
				</li>
				{% if gameConfig.getInteger('guides.group.id') > 0 %}
				<li class=" last">
					<a href="{{ site.sitePath }}/groups/officialhabboguides">{{ locale.club_habbo_guides }}</a>
				</li>
				{% endif %}
		</ul>
    </div>
</div>
{% endif %}

<div id="container">
	<div id="content" style="position: relative" class="clearfix">
    <div id="column1" class="column">

				<div class="habblet-container ">		
						<div class="cbb clearfix hcred ">
	
							<h2 class="title">{{ site.siteName }} {{ locale.club_club_become_a_vip }}							</h2>
						<div id ="habboclub-products">
    <div id="habboclub-clothes-container">
        <div class="habboclub-extra-image"></div>
        <div class="habboclub-clothes-image"></div>
    </div>

    <div class="clearfix"></div>
    <div id="habboclub-furniture-container">
        <div class="habboclub-furniture-image"></div>
    </div>
</div>
	
						
					</div>
				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
			 

			     		
				<div class="habblet-container ">		
						<div class="cbb clearfix lightbrown ">

	
							<h2 class="title">{{ locale.club_benefits }}							</h2>
						<div id="habboclub-info" class="box-content">
    <p>{{ site.siteName }} {{ locale.club_club_is_our_vip_members_only_club_absolutely_no_riff_raff_admitted_members_enjoy_a_wide_range_of_benefits_including_exclusive_clothes_free_gifts_and_an_extended_friends_list_see_below_for_all_the_sparkly_attractive_reasons_to_join }}</p>
    <h3 class="heading">{{ locale.club_one_extra_clothes_and_accessories }}</h3>
    <p class="content habboclub-clothing">{{ locale.club_show_off_your_new_status_with_a_variety_of_extra_clothes_and_accessories_along_with_special_hairstyles_and_colors }}        <br /><br /><a href="{{ site.sitePath }}/credits/club/tryout">{{ locale.club_try_out }} {{ site.siteName }} {{ locale.club_club_clothes_for_yourself }}</a>

    </p>
    <h3 class="heading">{{ locale.club_two_free_furni }}</h3>
    <p class="content habboclub-furni">{{ locale.club_once_a_month_every_month_you_ll_get_an_exclusive_piece_of }} {{ site.siteName }} {{ locale.club_club_furni }}</p>        
    <p class="content">{{ locale.club_important_note_club_time_is_cumulative_this_means_that_if_you_have_a_break_in_membership_and_then_rejoin_you_ll_start_back_in_the_same_place_you_left_off }}</p>
    <h3 class="heading">{{ locale.club_three_exclusive_room_layouts }}</h3>
    <p class="content">{{ locale.club_special_guest_room_layouts_only_for }} {{ site.siteName }} {{ locale.club_club_members_perfect_for_showing_off_your_new_furni }}</p>
    <p class="habboclub-room" />

    <h3 class="heading">{{ locale.club_four_access_all_areas }}</h3>
    <p class="content">{{ locale.club_jump_the_annoying_queues_when_rooms_are_loading_and_that_s_not_all_you_ll_also_get_access_to_hc_only_public_rooms }}</p>
    <h3 class="heading">{{ locale.club_five_homepage_upgrades }}</h3>
    <p class="content">{{ locale.club_join }} {{ site.siteName }} {{ locale.club_club_and_say_goodbye_to_homepage_ads_and_this_means_you_can_make_the_most_of_the_hc_skins_and_backgrounds_too }}</p>
    <h3 class="heading">{{ locale.club_six_more_friends }}</h3>
    <p class="content habboclub-communicator">{{ locale.club_six_zero_zero_people_now_that_s_a_lot_of_buddies_however_you_look_at_it_more_than_you_can_poke_with_a_medium_sized_stick_or_a_big_sized_small_stick }}</p>

    <h3 class="heading">{{ locale.club_seven_special_commands }}</h3>
    <p class="content habboclub-commands right">{{ locale.club_use_the_chooser_command_to_see_a_clickable_list_of_all_the_users_in_the_room_pretty_handy_when_you_want_to_sit_next_to_your_mate_or_kick_out_a_troublemaker }}</p>
    <br />
    <p>{{ locale.club_use_the_furni_command_to_list_all_the_items_in_a_room_everything_is_listed_even_those_sneakily_hidden_items }}</p>
</div>
	
						
					</div>
				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>

</div>
<div id="column2" class="column">
			     		
				<div class="habblet-container ">		
						<div class="cbb clearfix hcred ">
	
							<h2 class="title">{{ locale.club_my_membership }}							</h2>
							

						<script src="{{ site.staticContentPath }}/web-gallery/static/js/habboclub.js" type="text/javascript"></script>
						{% include "base/hc_status.tpl" %}				
					</div>
				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
			 

			     		
				<div class="habblet-container ">		
						<div class="cbb clearfix lightbrown ">
	
							<h2 class="title">{{ locale.club_monthly_gifts }}
							</h2>
						<script src="{{ site.staticContentPath }}/web-gallery/static/js/habboclub.js" type="text/javascript"></script>
<div id="hc-gift-catalog">
  {% include "habblet/habboclubgift.tpl" %}
</div>
	
						
					</div>
				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
			 

</div>
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