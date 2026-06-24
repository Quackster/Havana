<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="{{ locale.global_html_lang }}" lang="{{ locale.global_html_lang }}">
<head>
	<meta http-equiv="content-type" content="text/html" />
	<title>{{ site.siteName }} {{ locale.games_games }} </title>

<script type="text/javascript">
var andSoItBegins = (new Date()).getTime();
</script>
    <link rel="shortcut icon" href="{{ site.staticContentPath }}/web-gallery/v2/favicon.ico" type="image/vnd.microsoft.icon" />
    <link rel="alternate" type="application/rss+xml" title="{{ locale.games_habbo_rss }}" href="https://classichabbo.com/articles/rss.xml" />
<script src="{{ site.staticContentPath }}/web-gallery/static/js/libs2.js" type="text/javascript"></script>
<script src="{{ site.staticContentPath }}/web-gallery/static/js/visual.js" type="text/javascript"></script>
<script src="{{ site.staticContentPath }}/web-gallery/static/js/libs.js" type="text/javascript"></script>
<script src="{{ site.staticContentPath }}/web-gallery/static/js/common.js" type="text/javascript"></script>
<script src="{{ site.staticContentPath }}/web-gallery/static/js/fullcontent.js" type="text/javascript"></script>
<script src="{{ site.staticContentPath }}/web-gallery/static/js/libs2.js" type="text/javascript"></script>
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/style.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/buttons.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/boxes.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/tooltips.css" type="text/css" />
<script src="/js/local/com.js" type="text/javascript"></script>


<script type="text/javascript">
document.habboLoggedIn = {{ session.loggedIn }};
var habboName = "{{ locale.games_session_loggedin_playerdetails_getname|escape('js') }} "" }}";
var ad_keywords = "";
var habboReqPath = "{{ site.sitePath }}";
var habboStaticFilePath = "{{ site.staticContentPath }}/web-gallery";
var habboImagerUrl = "{{ site.staticContentPath }}/habbo-imaging/";
var habboPartner = "";
window.name = "habboMain";
if (typeof HabboClient != "undefined") { HabboClient.windowName = "client"; }

</script>

<style>
.habblet-text {
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

<meta name="description" content="{{ locale.games_join_the_world_s_largest_virtual_hangout_where_you_can_meet_and_make_friends_design_your_own_rooms_collect_cool_furniture_throw_parties_and_so_much_more_create_your_free_habbo_today }}" />
<meta name="keywords" content="{{ locale.games_habbo_virtual_world_join_groups_forums_play_games_online_friends_teens_collecting_social_network_create_collect_connect_furniture_virtual_goods_sharing_badges_social_networking_hangout_safe_music_celebrity_celebrity_visits_celebrities_room_design_rares_rare_furni_furni_free_avatar_online_teen_roleplaying_play_expression_mmo_mmorpg_massively_multiplayer_chat }}" />

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
body { behavior: url(/js/csshover.htc); }
</style>
<![endif]-->
<meta name="build" content="{{ locale.games_two_one_zero_five_three_two_zero_zero_eight_zero_four_zero_three_zero_five_four_zero_four_nine_com }}" />
</head>
<meta name="build" content="{{ locale.games_havanaweb }}" />
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
				{{ locale.games_games_text }}
				
			</li>
    		<li class="">
				<a href="/groups/battleball_rebound">{{ locale.games_battleball_rebound }}</a>
    		</li>
    		<li class="">
				<a href="/groups/snow_storm">{{ locale.games_snowstorm }}</a>
    		</li>
    		<li class="">
				<a href="/groups/wobble_squabble">{{ locale.games_wobble_squabble }}</a>
    		</li>
    		<li class=" last">
				<a href="/groups/lido">{{ locale.games_lido_diving }}</a>
    		</li>
	</ul>
    </div>
</div>

<div id="container">
	<div id="content" style="position: relative" class="clearfix">
    <div id="column1" class="column">
				<div class="habblet-container ">		
						<div class="cbb clearfix green ">
	
							<h2 class="title">{{ locale.games_recommended_games }}
							</h2>
						<div class="game-links-top">
<div><a href="/groups/battleball_rebound"><img src="{{ site.staticContentPath }}/web-gallery/v2/images/games/battleball.png" alt="{{ locale.games_battleball_rebound_text }}" width="450" height="99" /></a></div>
<div><a href="/groups/snow_storm"><img src="{{ site.staticContentPath }}/web-gallery/v2/images/games/snowstorm.png" alt="{{ locale.games_snowstorm_text }}" width="450" height="99" /></a></div>
</div>

<ul class="game-links">
	<li><a href="/groups/battleball_rebound">{{ locale.games_battleball_rebound_text }}</a></li>
	<li><a href="/groups/snow_storm">{{ locale.games_snowstorm_text }}</a></li>
	<li><a href="/groups/wobble_squabble">{{ locale.games_wobble_squabble_text }}</a></li>
	<li><a href="/groups/lido">{{ locale.games_lido_diving_text }}</a></li>
</ul>
	
						
					</div>
				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
			 
				<div class="habblet-container ">		
						<div class="cbb clearfix orange ">
	
							<h2 class="title">{{ locale.games_high_scores }}
							</h2>
{% include "habblet/personalhighscores.tpl" %}

<script type="text/javascript">
new HighscoreHabblet("h116");
</script>
	
						
					</div>
				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
			 
</div>
<div id="column2" class="column">
				<div class="habblet-container ">		
						<div class="cbb clearfix green ">
	
							<h2 class="title">{{ locale.games_your_ticket_to_excitement }}
							</h2>
						<div class="box-content">
	<div id="game-promo">
		{{ locale.games_game_tickets_cost_one_coin_for_two_or_you_can_purchase_two_zero_tickets_for_six_coins }}
	</div>
</div>
	
						
					</div>
				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
				
			<div class="habblet-container ">		
				<div class="cbb clearfix">
					<h2 class="title" style="background-color: lightblue">{{ locale.games_scoring }}</h2>
					<div class="habblet-text">							</h2>
						<div class="row">
							<div class="column" style="width: 100px; margin-top:10px; margin-bottom:10px; margin-right:6px">
								<img class="credits-image" src="https://i.imgur.com/AOd2pRV.gif" alt="">
							</div>
							<div class="column" style="width: 170px;">
								{% if viewMonthlyScores %}
								<p style="margin-top: 10px">{{ locale.games_the_following_results_are_scores_earned_month_to_month }}</p>
								<p>{{ locale.games_to_view_scores_earned_all_time_since_game_scoring_has_been_collected_please_click }} <a href="{{ site.sitePath }}/games/score_all_time">{{ locale.games_here }}</a>.</p>
								{% else %}
								<p style="margin-top: 10px">{{ locale.games_the_following_results_are_scores_earned_all_time }}</p>
								<p>{{ locale.games_to_view_scores_earned_month_to_month_since_game_scoring_has_been_collected_please_click }} <a href="{{ site.sitePath }}/games">{{ locale.games_here }}</a>.</p>								
								{% endif %}
							</div>
						</div>
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


</body>
</html>