
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="{{ locale.global_html_lang }}" lang="{{ locale.global_html_lang }}">
<head>
	<meta http-equiv="content-type" content="text/html" />
	<title>{{ site.siteName }}{{ locale.welcome_welcome }} </title>

<script type="text/javascript">
var andSoItBegins = (new Date()).getTime();
</script>
    <link rel="shortcut icon" href="{{ site.staticContentPath }}/web-gallery/v2/favicon.ico" type="image/vnd.microsoft.icon" />
    <link rel="alternate" type="application/rss+xml" title="{{ site.siteName }}{{ locale.welcome_rss }}" href="{{ site.sitePath }}/articles/rss.xml" />
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
var habboName = "{{ locale.welcome_session_loggedin_playerdetails_getname|escape('js') }} "" }}";
var ad_keywords = "";
var habboReqPath = "{{ site.sitePath }}";
var habboStaticFilePath = "{{ site.staticContentPath }}/web-gallery";
var habboImagerUrl = "{{ site.staticContentPath }}/habbo-imaging/";
var habboPartner = "";
window.name = "habboMain";
if (typeof HabboClient != "undefined") { HabboClient.windowName = "client"; }

</script>

<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/welcome.css" type="text/css" />


<meta name="description" content="{{ locale.welcome_join_the_world_s_largest_virtual_hangout_where_you_can_meet_and_make_friends_design_your_own_rooms_collect_cool_furniture_throw_parties_and_so_much_more_create_your_free }} {{ site.siteName }} {{ locale.welcome_today }}" />
<meta name="keywords" content="{{ site.siteName }}{{ locale.welcome_virtual_world_join_groups_forums_play_games_online_friends_teens_collecting_social_network_create_collect_connect_furniture_virtual_goods_sharing_badges_social_networking_hangout_safe_music_celebrity_celebrity_visits_cele }}" />

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
<meta name="build" content="{{ locale.welcome_keplerweb }}" />
</head>
<body id="welcome" class=" ">
<div id="overlay"></div>
<div id="header-container">
	<div id="header" class="clearfix">
		<h1><a href="{{ site.sitePath }}/"></a></h1>
       <div id="subnavi">
			<div id="subnavi-user">
				<ul>
					<li id="myfriends"><a href="#"><span>{{ locale.welcome_my_friends }}</span></a><span class="r"></span></li>
					<li id="mygroups"><a href="#"><span>{{ locale.welcome_my_groups }}</span></a><span class="r"></span></li>
					<li id="myrooms"><a href="#"><span>{{ locale.welcome_my_rooms }}</span></a><span class="r"></span></li>
				</ul>
			</div>
            <div id="subnavi-search">
                <div id="subnavi-search-upper">

                <ul id="subnavi-search-links">
                    <li><a href="{{ site.sitePath }}/help" target="habbohelp" onclick="openOrFocusHelp(this); return false">{{ locale.welcome_help }}</a></li>
					<li><a href="{{ site.sitePath }}/account/logout" class="userlink" id="signout">{{ locale.welcome_sign_out }}</a></li>
				</ul>
                </div>
            </div>
            <div id="to-hotel">
					    <a href="{{ site.sitePath }}/client" class="new-button green-button" target="client" onclick="HabboClient.openOrFocus(this); return false;"><b>{{ locale.welcome_enter }} {{ site.siteName }} {{ locale.welcome_hotel }}</b><i></i></a>
			</div>
        </div>
		<script type="text/javascript">
		L10N.put("purchase.group.title", "{{ locale.welcome_create_a_group|escape('js') }}");
		document.observe("dom:loaded", function() {
            $("signout").observe("click", function() {
                HabboClient.close();
            });
        });
        </script>
<ul id="navi">
		        <li class="selected">
			<strong>{{ locale.welcome_alex }} </strong>			<span></span>
		</li>
				<li>
			<a href="{{ site.sitePath }}/community">{{ locale.welcome_community }}</a>			<span></span>
		</li>
		<li>
			<a href="{{ site.sitePath }}/credits">{{ locale.welcome_coins }}</a>			<span></span>
		</li>
		</ul>

        <div id="habbos-online"><div class="rounded"><span>0 {{ site.siteName }}{{ locale.welcome_s_online }}</span></div></div>
	</div>
</div>

<div id="content-container">

	<div id="navi2-container" class="pngbg">
    <div id="navi2" class="pngbg clearfix">
		<ul>
			<li class="">
				<a href="{{ site.sitePath }}/me">{{ locale.welcome_home }}</a>			</li>
    		<li class="">
				<a href="{{ site.sitePath }}/home/Alex">{{ locale.welcome_my_page }}</a>    		</li>
    		<li class="">
				<a href="{{ site.sitePath }}/profile">{{ locale.welcome_account_settings }}</a>    		</li>
			<li class=" last">
				<a href="{{ site.sitePath }}/club">{{ site.siteName }} {{ locale.welcome_club }}</a>
			</li>
		</ul>
    </div>
</div>
	
<div id="container">
	<div id="content" style="position: relative" class="clearfix">
    <div id="column1" class="column">
				<div class="habblet-container ">
						<div class="cbb clearfix lightgreen ">

							<h2 class="title">{{ locale.welcome_choose_a_pre_decorated_room_and_get_free_furniture }}
							</h2>
						<div id="roomselection-welcome-intro" class="box-content">
    {{ locale.welcome_select_the_room_you_like_best_to_get_a_new_piece_of_furniture_every_day_during_your_first_week_in }} {{ site.siteName }}!
</div>
<ul class="roomselection-welcome clearfix">
	<li class="odd">
	<a class="roomselection-select new-button" href="client?createRoom=0" target="client" onclick="return RoomSelectionHabblet.create(this, 0);"><b>{{ locale.welcome_select }}</b><i></i></a>
	</li>
	<li class="even">
	<a class="roomselection-select new-button" href="client?createRoom=1" target="client" onclick="return RoomSelectionHabblet.create(this, 1);"><b>{{ locale.welcome_select }}</b><i></i></a>
	</li>
	<li class="odd">
	<a class="roomselection-select new-button" href="client?createRoom=2" target="client" onclick="return RoomSelectionHabblet.create(this, 2);"><b>{{ locale.welcome_select }}</b><i></i></a>
	</li>
	<li class="even">
	<a class="roomselection-select new-button" href="client?createRoom=3" target="client" onclick="return RoomSelectionHabblet.create(this, 3);"><b>{{ locale.welcome_select }}</b><i></i></a>
	</li>
	<li class="odd">
	<a class="roomselection-select new-button" href="client?createRoom=4" target="client" onclick="return RoomSelectionHabblet.create(this, 4);"><b>{{ locale.welcome_select }}</b><i></i></a>
	</li>
	<li class="even">
	<a class="roomselection-select new-button" href="client?createRoom=5" target="client" onclick="return RoomSelectionHabblet.create(this, 5);"><b>{{ locale.welcome_select }}</b><i></i></a>
	</li>
</ul>



					</div>
				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>

</div>
<div id="column2" class="column">
				<div class="habblet-container ">

						<div class="cbb clearfix lightgreen">

<div class="welcome-intro clearfix">
	<img alt="{{ site.playerName }}" src="{{ site.sitePath }}/habbo-imaging/avatarimage?figure={{ playerDetails.figure }}&size=b&direction=3&head_direction=3&crr=667&gesture=srp&frame=1" width="64" height="110" class="welcome-habbo" />
    <div id="welcome-intro-welcome-user"  >{{ locale.welcome_welcome_text }} {{ playerDetails.getName() }}!</div>
    <div id="welcome-intro-welcome-party" class="box-content">{{ locale.welcome_when_arriving_to_your_room_you_will_be_asked_if_you_d_like_to_meet }} {{ site.siteName }} {{ locale.welcome_guides }} {{ site.siteName }} {{ locale.welcome_guides_are_experienced }} {{ site.siteName }} {{ locale.welcome_players }}</div>
    </div>
</div>

</div>	
					
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
			 

				     		
				<div class="habblet-container ">		
						<div class="cbb clearfix green ">
	
							<h2 class="title">{{ locale.welcome_got_shockwave }}
							</h2>
						<div class="welcome-shockwave clearfix box-content">
    <div id="welcome-shockwave-text">{{ locale.welcome_when_you_open }} {{ site.siteName }} {{ locale.welcome_hotel_for_the_first_time_you_might_need_to_install_shockwave_but_don_t_worry_it_s_as_easy_as_one_two_three }}</div>
    <div id="welcome-shockwave-logo"><img src="{{ site.staticContentPath }}/web-gallery/v2/images/welcome/shockwave.gif" alt="{{ locale.welcome_shockwave }}" /></div>
</div>


					</div>
				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>

</div>
<script type="text/javascript">
HabboView.run();
</script>
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
{% include "base/footer.tpl" %}

<script type="text/javascript">
HabboView.run();
</script>


</body>
</html>