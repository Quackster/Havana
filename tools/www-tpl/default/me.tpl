<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="{{ locale.global_html_lang }}" lang="{{ locale.global_html_lang }}">
<head>
	<meta http-equiv="content-type" content="text/html" />
	<title>{{ site.siteName }}{{ locale.me_home }} </title>

<script type="text/javascript">
var andSoItBegins = (new Date()).getTime();
</script>
    <link rel="shortcut icon" href="{{ site.staticContentPath }}/web-gallery/v2/favicon.ico" type="image/vnd.microsoft.icon" />
    <link rel="alternate" type="application/rss+xml" title="{{ site.siteName }}{{ locale.me_rss }}" href="{{ site.sitePath }}/articles/rss.xml" />
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
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/welcome.css" type="text/css" />

<script src="{{ site.staticContentPath }}/web-gallery/js/local/com.js" type="text/javascript"></script>

<script type="text/javascript">
document.habboLoggedIn = {{ session.loggedIn }};
var habboName = "{{ locale.me_session_loggedin_playerdetails_getname|escape('js') }} "" }}";
var ad_keywords = "";
var habboReqPath = "{{ site.sitePath }}";
var habboStaticFilePath = "{{ site.staticContentPath }}/web-gallery";
var habboImagerUrl = "{{ site.staticContentPath }}/habbo-imaging/";
var habboPartner = "";
window.name = "habboMain";
if (typeof HabboClient != "undefined") { HabboClient.windowName = "client"; }

</script>

<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/personal.css" type="text/css" />
<script src="{{ site.staticContentPath }}/web-gallery/static/js/habboclub.js" type="text/javascript"></script>	
							
								<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/minimail.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/styles/myhabbo/control.textarea.css" type="text/css" />
<script src="{{ site.staticContentPath }}/web-gallery/static/js/minimail.js" type="text/javascript"></script>


<meta name="description" content="{{ locale.me_join_the_world_s_largest_virtual_hangout_where_you_can_meet_and_make_friends_design_your_own_rooms_collect_cool_furniture_throw_parties_and_so_much_more_create_your_free }} {{ site.siteName }} {{ locale.me_today }}" />
<meta name="keywords" content="{{ site.siteName }}{{ locale.me_virtual_world_join_groups_forums_play_games_online_friends_teens_collecting_social_network_create_collect_connect_furniture_virtual_goods_sharing_badges_social_networking_hangout_safe_music_celebrity_celebrity_visits_cele }}" />

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
<meta name="build" content="{{ locale.me_havanaweb }}" />
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
					{{ locale.me_home_text }}			</li>
				<li class="">
					<a href="{{ site.sitePath }}/home/{{ playerDetails.getName() }}">{{ locale.me_my_page }}</a>    		</li>
				<li class="">
					<a href="{{ site.sitePath }}/profile">{{ locale.me_account_settings }}</a>    		</li>
				<li class="">
					<a href="{{ site.sitePath }}/club">{{ site.siteName }} {{ locale.me_club }}</a>
				</li>
				<!-- 
				<li class="{% if gameConfig.getInteger('guides.group.id') == 0 %} last{% endif %}">
					<a href="{{ site.sitePath }}/beta_client" target="beta_client" onclick="openOrFocusHabbo(this); return false;" style="color: red">{{ locale.me_try_beta_habbo }}</a>
				</li>
				{% if gameConfig.getInteger('guides.group.id') > 0 %}
				<li class=" last">
					<a href="{{ site.sitePath }}/groups/officialhabboguides">{{ locale.me_habbo_guides }}</a>
				</li>
				{% endif %}
				-->
				<li class=" last">
					<a href="{{ site.sitePath }}/groups/officialhabboguides">{{ locale.me_habbo_guides }}</a>
				</li>
			</ul>
		</div>
	</div>
	
<div id="container">
	<div id="content">
		<div id="column1" class="column">
		<!-- <div class="rounded" style="background-color: red; color: white">
			<strong>{{ locale.me_attention }}</strong><br />
			{{ locale.me_this_server_is_currently_in_beta_that_means_there_may_be_some_incomplete_features_bugs_and_other_forms_of_exploitation }}<br />
		</div>
		<br /> -->
				<div class="habblet-container ">
						<div id="new-personal-info" style="background-image:url({{ site.staticContentPath }}/web-gallery/v2/images/personal_info/hotel_views/htlview_au.png)" />

	{% if site.serverOnline %}
    <div class="enter-hotel-btn">
        <div class="open enter-btn">
            <a href="{{ site.sitePath }}/shockwave_client" target="shockwave_client" onclick="openOrFocusHabbo(this); return false;">{{ locale.me_enter }}<i></i></a>
            <b></b>
        </div>
    </div>

    <div class="enter-beta-btn">
        <div class="open enter-btn">
            <a href="{{ site.sitePath }}/flash_client" target="flash_client" onclick="openOrFocusHabbo(this); return false;">{{ locale.me_enter_flash }} {{ site.siteName }}<i></i></a>
            <b></b>
        </div>
    </div>
	{% else %}
	<div class="enter-hotel-btn">
		<div class="closed enter-btn">
			<span>{{ site.siteName }} {{ locale.me_is_offline }}</span>
			<b></b>
		</div>
	</div>
	{% endif %}
	
	<div id="habbo-plate">
		<a href="{{ site.sitePath }}/profile">
			{% if playerDetails.motto.toLowerCase() == "crikey" %}
			<img src='{{ site.staticContentPath }}/web-gallery/images/sticker_croco.gif' style='margin-top: 57px'>
			{% else %}
			<img alt="{{ playerDetails.getName() }}" src="{{ site.sitePath }}/habbo-imaging/avatarimage?figure={{ playerDetails.figure }}&size=b&direction=3&head_direction=3&crr=0&gesture=sml&frame=1" width="64" height="110" />
			{% endif %}
		</a>
	</div>

	<div id="habbo-info">
		<div id="motto-container" class="clearfix">
			<strong>{{ playerDetails.getName() }}:</strong>
			<div>
				{% autoescape 'html' %}
				{% if playerDetails.motto == "" %}
				<span title="{{ locale.me_click_to_enter_your_motto_status }}">{{ locale.me_click_to_enter_your_motto_status }}</span>
				{% else %}
				<span title="{{ locale.me_click_to_enter_your_motto_status }}">{{ playerDetails.motto }}</span>
				{% endif %}
				{% endautoescape %}
				<p style="display: none"><input type="text" length="30" name="motto" value=""/></p>
			</div>
		</div>
		<div id="motto-links" style="display: none"><a href="#" id="motto-cancel">{{ locale.me_cancel }}</a></div>
	</div>

	<ul id="link-bar" class="clearfix">
        <li class="change-looks"><a href="{{ site.sitePath }}/profile">{{ locale.me_change_looks_raquo }}</a></li>
        <li class="credits">
            <a href="{{ site.sitePath }}/credits">{{ playerDetails.credits }}</a> {{ locale.me_credits }}		</li>
        <li class="club">
            {% if playerDetails.hasClubSubscription() %}
            <a href="{{ site.sitePath }}/club">{{ hcDays }} </a>{{ locale.me_hc_days }}		</li>
            {% else %}
            <a href="{{ site.sitePath }}/club">{{ locale.me_join }} {{ site.siteName }} {{ locale.me_club_raquo }}</a>		</li>
            {% endif %}
        <li class="activitypoints">
            <a href="{{ site.sitePath }}/credits/pixels">{{ playerDetails.pixels }}</a> {{ locale.me_pixels }}		    </li>
    </ul>

    <div id="habbo-feed">
        <ul id="feed-items">
		{% if hasBirthday %}
                <li id="feed-birthday">
                  <div>
                    {{ locale.me_happy }} <!-- anniversary --> {{ locale.me_birthday }} {{ playerDetails.name }}!<br />
                  </div>
                </li>
		{% endif %}
		{% for alert in alerts %}	
			{% if alert.getAlertType() == 'HC_EXPIRED' %}
			<li id="feed-item-hc-reminder">
				<a href="#" class="remove-feed-item" id="remove-hc-reminder" title="{{ locale.me_remove_notification }}">{{ locale.me_remove_notification }}</a>

				<div>{{ locale.me_your_habbo_club_is_expired_do_you_want_to_extend_your_habbo_club }}	</div>
				<div class="clearfix">
					<table width="100px" style="margin-top:6px; margin-left:-12px">
						<tr>
							<td>
								<a class="new-button" id="subscribe1" href="#" onclick='habboclub.buttonClick(1, "Habbo Club"); return false;'><b>{{ locale.me_one_months }}</b><i></i></a>
							</td>
							<td>
								<a class="new-button" id="subscribe2" href="#" onclick='habboclub.buttonClick(2, "Habbo Club"); return false;' style="margin-left:6px"><b>{{ locale.me_two_months }}</b><i></i></a>
							</td>
							<td>
								<a class="new-button" id="subscribe2" href="#" onclick='habboclub.buttonClick(3, "Habbo Club"); return false;' style="margin-left:6px"><b>{{ locale.me_three_months }}</b><i></i></a>
							</td>
						</tr>
					</table>
				</div>

			</li>
			{% elseif alert.getAlertType() == 'PRESENT' %}
			<li id="feed-item-dailygift" class="contributed">
				<a href="#" class="remove-feed-item" title="{{ locale.me_remove_notification }}">{{ locale.me_remove_notification }}</a>
				<div>{{ alert.getMessage }}</div>
			</li>
			{% elseif alert.getAlertType() == 'TUTOR_SCORE' %}
			<li id="feed-item-tutor-score" class="contributed">
				<a href="#" class="remove-feed-item" title="{{ locale.me_remove_notification }}">{{ locale.me_remove_notification }}</a>
				<div>{{ alert.getMessage }}</div>
			</li>
			{% elseif alert.getAlertType() == 'CREDIT_DONATION' %}
			<li id="feed-item-creditdonation" class="contributed">
				<a href="#" class="remove-feed-item" title="{{ locale.me_remove_notification }}">{{ locale.me_remove_notification }}</a>
				<div>{{ alert.getMessage }}</div>
			</li>
			{% endif %}
		{% endfor %}
			{% if feedFriendRequests > 0 %}
			<li id="feed-notification">
				{{ locale.me_you_have }} <a href="{{ site.sitePath }}/client" onclick="HabboClient.openOrFocus(this); return false;">{{ feedFriendRequests }} {{ locale.me_friend_requests }}</a> {{ locale.me_waiting }}
			</li>
			{% endif %}
			
			{% set num = 1 %}
			{% if (feedFriendsOnline|length) > 0 %}
			<li id="feed-friends">
			{{ locale.me_you_have }} {{ feedFriendsOnline|length }} {{ locale.me_friends_online }}
			<span>
			{% for friend in feedFriendsOnline %}				
				<a href="{{ site.sitePath }}/home/{{ friend.getUsername() }}">{{ friend.getUsername() }}</a>{% if num < (feedFriendsOnline|length) %},{% endif %}{% set num = num + 1 %}
			{% endfor %}
			</span>
			</li>
			{% endif %}
			
			{% if unreadGuestbookMessages > 0 %}
			<li class="small" id="feed-guestbook">
			{{ locale.me_you_have }} <a href="{{ site.sitePath }}/home/{{ playerDetails.getName() }}">{{ unreadGuestbookMessages }} {{ locale.me_new_guestbook_entries }}</a> {{ locale.me_on_your_homepage }}
			</li>
			{% endif %}
			
			{% set num = 1 %}
			{% if pendingMembers > 0 %}
			<li class="small" id="feed-pending-members">
			<strong>{{ pendingMembers }}</strong> {{ locale.me_of_your_groups_have_pending_members }}
			<span>
			{% for group in pendingGroups.entrySet() %}	
				{% set groupId = group.getKey() %}
				{% set groupName = group.getValue() %}
				<a href="{{ site.sitePath }}/groups/{{ groupId }}/id">{{ groupName }}</a>{% if num < (pendingGroups|length) %},{% endif %}{% set num = num + 1 %}
			{% endfor %}
			</span>
			</li>
			{% endif %}
			
			{% set num = 1 %}
			{% if newPostsAmount > 0 %}
			<li class="small" id="feed-group-discussion">
			<strong>{{ newPostsAmount }}</strong> {{ locale.me_of_your_groups_have_new_forum_messages }}
			<span>
			{% for group in newPosts.entrySet() %}	
				{% set groupId = group.getKey() %}
				{% set groupName = group.getValue() %}
				<a href="{{ site.sitePath }}/groups/{{ groupId }}/id/discussions">{{ groupName }}</a>{% if num < (newPosts|length) %},{% endif %}{% set num = num + 1 %}
			{% endfor %}
			</span>
			</li>
			{% endif %}
			
			{% if playerDetails.isTradeEnabled() %}
				<li class="small" id="feed-trading-enabled">{{ locale.me_trading_is_on }} <a href="{{ site.sitePath }}/profile?tab=6" title="">{{ locale.me_click_here_to_turn_it_off }}</a></li>
			{% else %}
				<li class="small" id="feed-trading-disabled">{{ locale.me_trading_is_off }} <a href="{{ site.sitePath }}/profile?tab=6" title="">{{ locale.me_click_here_to_turn_it_on }}</a></li>
			{% endif %}
			<!-- <li class="small" id="feed-flashbeta-invites">{{ locale.me_this_server_is_currently_in_beta_some_features_may_not_be_operating_correctly_you_may_also_expect_spontaneous_maintenance_periods }}</li> -->
            <li class="small" id="feed-lastlogin">
                {{ locale.me_last_signed_in }}
                {{ lastOnline }}            </li>
        </ul>
    </div>

    <p class="last"></p>
</div>

<script type="text/javascript">
    HabboView.add(function() {
        L10N.put("personal_info.motto_editor.spamming", "{{ locale.me_don_t_spam_me_bro|escape('js') }}");
        PersonalInfo.init("");
    });
</script>


                </div>
                <script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
			{% if (newbieRoomLayout == 0) and (site.serverOnline == true) %}
            <div class="habblet-container" id="roomselection">
              <div class="cbb clearfix rooms">
                <h2 class="title">
                  {{ locale.me_select_your_room }}
                  <span class="habblet-close" id="habblet-close-roomselection" onclick="RoomSelectionHabblet.showConfirmation()"></span>
                </h2>
                    <div id="roomselection-plp-intro" class="box-content">
                    {{ locale.me_hey_you_haven_t_chosen_your_pre_decorated_room_which_comes_with_free_furniture_choose_one_below }}
                    </div>
                        <ul id="roomselection-plp" class="clearfix">
                            <li class="top">
                            <a class="roomselection-select new-button green-button" href="client?createRoom=0" target="client" onclick="return RoomSelectionHabblet.create(this, 0);"><b>{{ locale.me_select }}</b><i></i></a>	
                            </li>
                            <li class="top">
                            <a class="roomselection-select new-button green-button" href="client?createRoom=1" target="client" onclick="return RoomSelectionHabblet.create(this, 1);"><b>{{ locale.me_select }}</b><i></i></a>	
                            </li>
                            <li class="top">
                            <a class="roomselection-select new-button green-button" href="client?createRoom=2" target="client" onclick="return RoomSelectionHabblet.create(this, 2);"><b>{{ locale.me_select }}</b><i></i></a>	
                            </li>
                            <li class="bottom">
                            <a class="roomselection-select new-button green-button" href="client?createRoom=3" target="client" onclick="return RoomSelectionHabblet.create(this, 3);"><b>{{ locale.me_select }}</b><i></i></a>	
                            </li>
                            <li class="bottom">
                            <a class="roomselection-select new-button green-button" href="client?createRoom=4" target="client" onclick="return RoomSelectionHabblet.create(this, 4);"><b>{{ locale.me_select }}</b><i></i></a>	
                            </li>
                            <li class="bottom">
                            <a class="roomselection-select new-button green-button" href="client?createRoom=5" target="client" onclick="return RoomSelectionHabblet.create(this, 5);"><b>{{ locale.me_select }}</b><i></i></a>	
                            </li>
                        </ul>
                        <script type="text/javascript">
                        L10N.put("roomselection.hide.title", "{{ locale.me_hide_room_selection|escape('js') }}");
                        L10N.put("roomselection.old_user.done", "{{ locale.me_and_you_re_done_habbo_hotel_will_now_open_in_a_new_window_and_you_ll_be_redirected_to_your_room_in_no_time|escape('js') }}");
                        //HabboView.add(RoomSelectionHabblet.initClosableHabblet);
                        </script>	
					</div>
				</div>
                
                <script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
				{% endif %}
				
				{% if (newbieNextGift > 0) and (newbieRoomLayout > 0) and (newbieNextGift < 4) %}
              <div class="habblet-container " id="giftqueue">
                <div class="cbb clearfix rooms">
                  <h2 class="title">
                    {{ locale.me_your_next_gift }}
					{% if newbieNextGift > 2 %}
                    <span class="habblet-close" id="habblet-close-giftqueue" onclick="GiftQueueHabblet.hide()"></span>
					{% endif %}
                  </h2>
				  <div class="box-content" id="gift-container">
				  {% include "habblet/nextgift.tpl" %}
				  </div>
                </div>
              </div>
				{% endif %}
				<div class="habblet-container ">		
						<div class="cbb clearfix orange ">

	
							<h2 class="title">{{ locale.me_hot_campaigns }}							</h2>
						<div id="hotcampaigns-habblet-list-container">
    <ul id="hotcampaigns-habblet-list">

        <li class="even">
            <div class="hotcampaign-container">
                <a href="{{ site.sitePath }}/articles"><img src="{{ site.staticContentPath }}/c_images/hot_campaign_images_gb/beta.gif" align="left" alt="" /></a>
                <h3>{{ locale.me_under_construction }}</h3>
                <p>{{ locale.me_put_interesting_text_in_here_because_this_text_is_just_useless_sitting_here_otherwise }}</p>
                <p class="link"><a href="{{ site.sitePath }}">{{ locale.me_go_there_raquo }}</a></p>
            </div>
        </li>
        
        <li class="odd">
            <div class="hotcampaign-container">
                <a href="{{ site.sitePath }}/articles"><img src="{{ site.staticContentPath }}/c_images/hot_campaign_images_gb/habbobetahot.gif" align="left" alt="" /></a>
                <h3>{{ locale.me_under_construction }}</h3>
                <p>{{ locale.me_put_interesting_text_in_here_because_this_text_is_just_useless_sitting_here_otherwise }}</p>
                <p class="link"><a href="{{ site.sitePath }}">{{ locale.me_go_there_raquo }}</a></p>
            </div>
        </li>
        
        <!-- 
        <li class="odd">
            <div class="hotcampaign-container">
                <a href="{{ site.sitePath }}/articles"><img src="{{ site.staticContentPath }}/c_images/hot_campaign_images_gb/hc.gif" align="left" alt="" /></a>
                <h3>{{ locale.me_exclusive_furniture }}</h3>
                <p>{{ locale.me_join_habbo_club_today_and_get_access_to_exclusive_furniture }}</p>

                <p class="link"><a href="https://classichabbo.com/credits/club">{{ locale.me_go_there_raquo }}</a></p>
            </div>
        </li>
        -->
    </ul>
</div>
	
						
					</div>
				</div>

				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
				
<div class="habblet-container minimail" id="mail">
                        <div class="cbb clearfix blue ">

                            <h2 class="title">{{ locale.me_my_messages }}                            </h2>
                        <div id="minimail">
		<div class="minimail-contents">
	    {% include "habblet/minimail/minimail_messages.tpl" %}
		</div>
		<div id="message-compose-wait"></div>
	    <form style="display: none" id="message-compose">
	        <div>{{ locale.me_to }}</div>
	        <div id="message-recipients-container" class="input-text" style="width: 426px; margin-bottom: 1em">
	        	<input type="text" value="" id="message-recipients" />
	        	<div class="autocomplete" id="message-recipients-auto">
	        		<div class="default" style="display: none;">{{ locale.me_type_the_name_of_your_friend }}</div>
	        		<ul class="feed" style="display: none;"></ul>

	        	</div>
	        </div>
	        <div>{{ locale.me_subject }}<br/>
	        <input type="text" style="margin: 5px 0" id="message-subject" class="message-text" maxlength="100" tabindex="2" />
	        </div>
	        <div>{{ locale.me_message }}<br/>
	        <textarea style="margin: 5px 0" rows="5" cols="10" id="message-body" class="message-text" tabindex="3"></textarea>

	        </div>
	        <div class="new-buttons clearfix">
	            <a href="#" class="new-button preview"><b>{{ locale.me_preview }}</b><i></i></a>
	            <a href="#" class="new-button send"><b>{{ locale.me_send }}</b><i></i></a>
	        </div>
	    </form>
	</div>
				<script type="text/javascript">
		L10N.put("minimail.compose", "{{ locale.me_compose|escape('js') }}").put("minimail.cancel", "{{ locale.me_cancel|escape('js') }}")
			.put("bbcode.colors.red", "Red").put("bbcode.colors.orange", "Orange")
	    	.put("bbcode.colors.yellow", "Yellow").put("bbcode.colors.green", "Green")
	    	.put("bbcode.colors.cyan", "Cyan").put("bbcode.colors.blue", "Blue")
	    	.put("bbcode.colors.gray", "Gray").put("bbcode.colors.black", "Black")
	    	.put("minimail.empty_body.confirm", "{{ locale.me_are_you_sure_you_want_to_send_the_message_with_an_empty_body|escape('js') }}")
	    	.put("bbcode.colors.label", "Color").put("linktool.find.label", " ")
	    	.put("linktool.scope.habbos", "{{ site.siteName }}s").put("linktool.scope.rooms", "Rooms")
	    	.put("linktool.scope.groups", "Groups").put("minimail.report.title", "{{ locale.me_report_message_to_moderators|escape('js') }}");

	    L10N.put("date.pretty.just_now", "{{ locale.me_just_now|escape('js') }}");
	    L10N.put("date.pretty.one_minute_ago", "{{ locale.me_one_minute_ago|escape('js') }}");
	    L10N.put("date.pretty.minutes_ago", "{{ locale.me_zero_minutes_ago|escape('js') }}");
	    L10N.put("date.pretty.one_hour_ago", "{{ locale.me_one_hour_ago|escape('js') }}");
	    L10N.put("date.pretty.hours_ago", "{{ locale.me_zero_hours_ago|escape('js') }}");
	    L10N.put("date.pretty.yesterday", "{{ locale.me_yesterday|escape('js') }}");
	    L10N.put("date.pretty.days_ago", "{{ locale.me_zero_days_ago|escape('js') }}");
	    L10N.put("date.pretty.one_week_ago", "{{ locale.me_one_week_ago|escape('js') }}");
	    L10N.put("date.pretty.weeks_ago", "{{ locale.me_zero_weeks_ago|escape('js') }}");
		new MiniMail({ pageSize: 10,
		   total: {{ totalMessages }},
		   friendCount: 1,
		   maxRecipients: 50,
		   messageMaxLength: 20,
		   bodyMaxLength: 4096,
		   secondLevel: false});
	</script>
	</div></div>

    <script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>

				<div class="habblet-container ">		
						<div class="cbb clearfix default ">
<div class="box-tabs-container clearfix">
    <h2>{{ site.siteName }}s</h2>
    <ul class="box-tabs">
        <li id="tab-0-4-1"><a href="#">{{ locale.me_search }} {{ site.siteName }}s</a><span class="tab-spacer"></span></li>

        <li id="tab-0-4-2" class="selected"><a href="#">{{ locale.me_invite_friend_s }}</a><span class="tab-spacer"></span></li>
    </ul>
</div>
    <div id="tab-0-4-1-content"  style="display: none">
<div class="habblet-content-info">
    <a name="habbo-search">{{ locale.me_type_in_the_first_characters_of_the_name_to_search_for_other }} {{ site.siteName }}s.</a>
</div>
<div id="habbo-search-error-container" style="display: none;"><div id="habbo-search-error" class="rounded rounded-red"></div></div>
<br clear="all"/>
<div id="avatar-habblet-list-search">
    <input type="text" id="avatar-habblet-search-string"/>

    <a href="#" id="avatar-habblet-search-button" class="new-button"><b>{{ locale.me_search }}</b><i></i></a>
</div>

<br clear="all"/>

<div id="avatar-habblet-content">
<div id="avatar-habblet-list-container" class="habblet-list-container">
        <ul class="habblet-list">
        </ul>

</div>
<script type="text/javascript">
    L10N.put("habblet.search.error.search_string_too_long", "{{ locale.me_the_search_keyword_was_too_long_maximum_length_is_three_zero_characters|escape('js') }}");
    L10N.put("habblet.search.error.search_string_too_short", "{{ locale.me_the_search_keyword_was_too_short_two_characters_required|escape('js') }}");
    L10N.put("habblet.search.add_friend.title", "{{ locale.me_add_to_friend_list|escape('js') }}");
	new HabboSearchHabblet(2, 30);

</script>

</div>

<script type="text/javascript">
    Rounder.addCorners($("habbo-search-error"), 8, 8);
</script>    </div>
    <div id="tab-0-4-2-content" >
<div id="friend-invitation-habblet-container" class="box-content">
    <div style="display: none"> 
    <div id="invitation-form" class="clearfix">
        <textarea name="invitation_message" id="invitation_message" class="invitation-message">{{ locale.me_come_and_hangout_with_me_in }} {{ site.siteName }}.- {{ playerDetails.getName() }}</textarea>
        <div id="invitation-email">
            <div class="invitation-input">1.<input  onkeypress="$('invitation_recipient2').enable()" type="text" name="invitation_recipients" id="invitation_recipient1" value="{{ locale.me_friend_s_email_address }}" class="invitation-input" />

            </div>
            <div class="invitation-input">2.<input disabled onkeypress="$('invitation_recipient3').enable()" type="text" name="invitation_recipients" id="invitation_recipient2" value="{{ locale.me_friend_s_email_address }}" class="invitation-input" />
            </div>
            <div class="invitation-input">3.<input disabled  type="text" name="invitation_recipients" id="invitation_recipient3" value="{{ locale.me_friend_s_email_address }}" class="invitation-input" />
            </div>
        </div>
        <div class="clear"></div>
        <div class="fielderror" id="invitation_message_error" style="display: none;"><div class="rounded"></div></div>

    </div>

    <div class="invitation-buttons clearfix" id="invitation_buttons">
		<a  class="new-button" id="send-friend-invite-button" href="#"><b>{{ locale.me_invite_friend_s }}</b><i></i></a>
    </div>
    
    <hr/>
    </div>
    <div id="invitation-link-container">
        <h3>{{ locale.me_enjoy }} {{ site.siteName }} {{ locale.me_more_with_real_life_friends }}</h3>

        <div class="copytext">
            <p>{{ locale.me_invite_your_friends_to_habbo_and_earn_cool_badges_send_a_link_to_your_friend_and_ask_them_to_register_and_activate_their_email_if_they_are_using_habbo_in_active_way_you_get_rewarded_with_a_badge }}</p>
        </div>
        <div class="invitation-buttons clearfix"> 
            <a  class="new-button" id="getlink-friend-invite-button" href="#"><b>{{ locale.me_click_for_the_invitation_link }}</b><i></i></a>
        </div>
    </div>
</div>
<script type="text/javascript">
    L10N.put("invitation.button.invite", "{{ locale.me_invite_friend_s|escape('js') }}");
    L10N.put("invitation.form.recipient", "{{ locale.me_friend_s_email_address|escape('js') }}");
    L10N.put("invitation.error.message_too_long", "invitation.error.message_limit");
    inviteFriendHabblet = new InviteFriendHabblet(500);   
    $("friend-invitation-habblet-container").select("{{ locale.me_fielderror_rounded|escape('js') }}").each(function(el) {
        Rounder.addCorners(el, 8, 8);
    });

</script>    </div>

					</div>
				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>


<div class="habblet-container ">		
						<div class="cbb clearfix darkred ">
	
							<h2 class="title">{{ locale.me_events }}							</h2>
						<div id="current-events">
	<div class="category-selector">
	<p>{{ locale.me_browse_latest_events_by_their_category }}</p>
	<select id="event-category">
		<option value="1">{{ locale.me_parties_and_music }}</option>
		<option value="2">{{ locale.me_trading }}</option>
		<option value="3">{{ locale.me_games }}</option>
		<option value="4">{{ locale.me_retro_guides }}</option>
		<option value="5">{{ locale.me_debates_and_discussion }}</option>
		<option value="6">{{ locale.me_grand_openings }}</option>
		<option value="7">{{ locale.me_dating }}</option>
		<option value="8">{{ locale.me_jobs }}</option>
		<option value="9">{{ locale.me_group_events }}</option>
		<option value="10">{{ locale.me_performance }}</option>
		<option value="11">{{ locale.me_help_desk }}</option>
	</select>
	</div>
	<div id="event-list">
		{% set eventCategory = 1 %}
		{% include "habblet/load_events.tpl" %}
	</div>
</div>
<script type="text/javascript">
	document.observe('dom:loaded', function() {
		CurrentRoomEvents.init();
	});
</script>
	
						
					</div>
				</div>
</div>
				<script type='text/javascript'>if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
<div id="column2" class="column">
				<div class="habblet-container news-promo">
						<div class="cbb clearfix notitle ">

						<div id="newspromo">
        <div id="topstories">
	        <div class="topstory" style="background-image: url({{ article1.getLiveTopStory() }})">
	            <h4>{{ locale.me_latest_news }} </a></h4>
	            <h3><a href="{{ site.sitePath }}/articles/{{ article1.getUrl() }}">{% if article1.isPublished() == false %}*{% endif %}{{ article1.title }}</a></h3>
	            <p class="summary">
	            {{ article1.shortstory }}	            </p>
	            <p>
	                <a href="{{ site.sitePath }}/articles/{{ article1.getUrl() }}">{{ locale.me_read_more_raquo }}</a>
	            </p>
	        </div>
	        <div class="topstory" style="background-image: url({{ article2.getLiveTopStory() }}); display: none">
	            <h4>{{ locale.me_latest_news }}</a></h4>
	            <h3><a href="{{ site.sitePath }}/articles/{{ article2.getUrl() }}">{% if article2.isPublished() == false %}*{% endif %}{{ article2.title }}</a></h3>
	            <p class="summary">
	            {{ article2.shortstory }}	            </p>
	            <p>
	                <a href="{{ site.sitePath }}/articles/{{ article2.getUrl() }}">{{ locale.me_read_more_raquo }}</a>
	            </p>
	        </div>
	        <div class="topstory" style="background-image: url({{ article3.getLiveTopStory() }}); display: none">
	            <h4>{{ locale.me_latest_news }}</a></h4>
	            <h3><a href="{{ site.sitePath }}/articles/{{ article3.getUrl() }}">{% if article3.isPublished() == false %}*{% endif %}{{ article3.title }}</a></h3>
	            <p class="summary">
	            {{ article3.shortstory }}	            </p>
	            <p>
	                <a href="{{ site.sitePath }}/articles/{{ article3.getUrl() }}">{{ locale.me_read_more_raquo }}</a>
	            </p>
	        </div>
            <div id="topstories-nav" style="display: none"><a href="#" class="prev">{{ locale.me_laquo_previous }}</a><span>1</span> / 3<a href="#" class="next">{{ locale.me_next_raquo }}</a></div>
        </div>
        <ul class="widelist">
            <li class="even">
                <a href="{{ site.sitePath }}/articles/{{ article4.getUrl() }}">{% if article4.isPublished() == false %}*{% endif %}{{ article4.title }}</a><div class="newsitem-date">{{ article4.getDate() }}</div>
            </li>
            <li class="odd">
                <a href="{{ site.sitePath }}/articles/{{ article5.getUrl() }}">{% if article4.isPublished() == false %}*{% endif %}{{ article5.title }}</a><div class="newsitem-date">{{ article5.getDate() }}</div>
            </li>
            <li class="last"><a href="{{ site.sitePath }}/articles">{{ locale.me_more_news_raquo }}</a></li>
        </ul>
</div>
<script type="text/javascript">
	document.observe("dom:loaded", function() { NewsPromo.init(); });
</script>
					</div>

				</div>
					<div>
					<!-- <p><a href="{{ site.sitePath }}/community"><img src="https://i.imgur.com/87IsMuC.png"></a></p> -->
					<!-- <p><a href="{{ site.sitePath }}/community"><img src="https://i.imgur.com/SGFjYN2.gif"></a></p> -->
					<!-- <p><a href="{{ site.sitePath }}"><img src="https://i.imgur.com/9lUdOG1.png"></a></p> -->
					<p><iframe src="https://discordapp.com/widget?id=524768066907668521&theme=light" height="280" allowtransparency="true" frameborder="0"></iframe></p>
					</div>
					<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
				<div class="habblet-container ">		
						<div class="cbb clearfix red ">
<div class="box-tabs-container clearfix">
    <h2>{{ locale.me_staff_picks }}</h2>
    <ul class="box-tabs">
        <li id="tab-1-3-1"><a href="#">{{ locale.me_rooms }}</a><span class="tab-spacer"></span></li>
        <li id="tab-1-3-2" class="selected"><a href="#">{{ locale.me_groups }}</a><span class="tab-spacer"></span></li>
    </ul>

</div>
    <div id="tab-1-3-1-content"  style="display: none">
    		<div class="progressbar"><img src="{{ site.staticContentPath }}/web-gallery/images/progress_bubbles.gif" alt="" width="29" height="6" /></div>
    		<a href="{{ site.sitePath }}/habblet/proxy?hid=h21" class="tab-ajax"></a>
    </div>
    <div id="tab-1-3-2-content" >
<div id="staffpicks-groups-habblet-list-container" class="habblet-list-container groups-list">
    <ul class="habblet-list two-cols clearfix">
		{% set position = "right" %}
				
		{% set i = 1 %}
		{% set lefts = 0 %}
		{% set rights = 0 %}
		{% for group in staffPickGroups %}	
			{% if i % 2 == 0 %}
				{% set position = "right" %}
				{% set rights = rights + 1 %}
			{% else %}
				{% set position = "left" %}
				{% set lefts = lefts + 1 %}
			{% endif %}
			
			{% if lefts % 2 == 0 %}
				{% set status = "even" %}
			{% else %}
				{% set status = "odd" %}
			{% endif %}
	
			{% set i = i + 1 %}
			<li class="{{ status }} {{ position }}" style="background-image: url({{ site.sitePath }}/habbo-imaging/badge/{{ group.badge }}.gif)">
				<a class="item" href="{{ group.generateClickLink() }}">{{ group.getName }}</a>
			</li>
		{% endfor %}
    </ul>


</div>
    </div>

					</div>
				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>

<div class="habblet-container ">        
                        <div class="cbb clearfix blue ">
						<h2 class="title">{{ locale.me_recommended }}                            </h2>
						<div id="promogroups-habblet-list-container" class="habblet-list-container groups-list">
							<ul class="habblet-list two-cols clearfix">
						{% set position = "right" %}
										
						{% set i = 0 %}
						{% set lefts = 0 %}
						{% set rights = 0 %}
						{% for group in recommendedGroups %}	
								{% set i = i + 1 %}
								{% if i % 2 == 0 %}
									{% set position = "right" %}
									{% set rights = rights + 1 %}
								{% else %}
									{% set position = "left" %}
									{% set lefts = lefts + 1 %}
								{% endif %}
								
								{% if lefts % 2 == 0 %}
									{% set status = "even" %}
								{% else %}
									{% set status = "odd" %}
								{% endif %}
								<li class="{{ status }} {{ position }}" style="background-image: url({{ site.sitePath }}/habbo-imaging/badge/{{ group.badge }}.gif)">
									{% if group.getRoomId() > 0 %}
									<a href="{{ site.sitePath }}/client?forwardId=2&amp;roomId=1" onclick="HabboClient.roomForward(this, '1', 'private'); return false;" target="client" class="group-room"></a>     
									{% endif %}
									<a class="item" href="{{ group.generateClickLink() }}">{% autoescape 'html' %}{{ group.name }}{% endautoescape %}</a>
								</li>
							{% endfor %}
							</ul>
						</div>
                    </div>
                </div>
                <script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>

				<div class="habblet-container ">		
						<div class="cbb clearfix green ">
<div class="box-tabs-container clearfix">
    <h2>{{ locale.me_tags }}</h2>

							<ul class="box-tabs">
								<li id="tab-1-5-1"><a href="#">{{ site.siteName }}{{ locale.me_s_like }}</a><span class="tab-spacer"></span></li>
								<li id="tab-1-5-2" class="selected"><a href="#">{{ locale.me_my_tags }}</a><span class="tab-spacer"></span></li>
							</ul>
						</div>
						<div id="tab-1-5-1-content"  style="display: none">
							<div class="progressbar">
								<img src="{{ site.staticContentPath }}/web-gallery/images/progress_bubbles.gif" alt="" width="29" height="6" />
							</div>
							<a href="{{ site.sitePath }}/habblet/proxy?hid=h24" class="tab-ajax"></a>
						</div>
						<div id="tab-1-5-2-content" >
							<div id="my-tag-info" class="habblet-content-info">
							{% if tags|length < 1 %}
							{{ locale.me_you_have_no_tags }}
							{% endif %} {{ locale.me_answer_the_question_below_or_just_add_a_tag_of_your_choice }}		    </div>
							<div class="box-content">
							
							{% include "habblet/myTagList.tpl" %}
							</div>
					

<script type="text/javascript">
document.observe("dom:loaded", function() {
    TagHelper.setTexts({
        tagLimitText: "{{ locale.me_you_ve_reached_the_tag_limit_delete_one_of_your_tags_if_you_want_to_add_a_new_one|escape('js') }}",
        invalidTagText: "{{ locale.me_invalid_tag_the_tag_must_be_less_than_two_zero_characters_and_composed_only_of_alphanumeric_characters|escape('js') }}",
        buttonText: "{{ locale.me_ok|escape('js') }}"
    });
        TagHelper.init('1');
});
</script>
    </div>

					</div>
				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
				
<div class="habblet-container ">
                        <div class="cbb clearfix blue ">
<div class="box-tabs-container clearfix">
    <h2>{{ locale.me_groups }}</h2>
    <ul class="box-tabs">
        <li id="tab-2-1"><a href="#">{{ locale.me_hot_groups }}</a><span class="tab-spacer"></span></li>
        <li id="tab-2-2" class="selected"><a href="#">{{ locale.me_my_groups }}</a><span class="tab-spacer"></span></li>
    </ul>
</div>
    <div id="tab-2-1-content"  style="display: none">
    		<div class="progressbar"><img src="{{ site.staticContentPath }}/web-gallery/images/progress_bubbles.gif" alt="" width="29" height="6" /></div>
    		<a href="{{ site.sitePath }}/habblet/proxy?hid=groups" class="tab-ajax"></a>
    </div>
    <div id="tab-2-2-content" >


         <div id="groups-habblet-info" class="habblet-content-info">
                {{ locale.me_view_the_groups_you_are_in_create_your_own_group_or_get_some_inspiration_from_the_hot_groups_tab }}         </div>

    <div id="groups-habblet-list-container" class="habblet-list-container groups-list">


<ul class="habblet-list two-cols clearfix">         
		{% set position = "right" %}
						
		{% set i = 0 %}
		{% set lefts = 0 %}
		{% set rights = 0 %}
		{% for group in groups %}				
				{% if i % 2 == 0 %}
					{% set position = "right" %}
					{% set rights = rights + 1 %}
				{% else %}
					{% set position = "left" %}
					{% set lefts = lefts + 1 %}
				{% endif %}
				
				{% if lefts % 2 == 0 %}
					{% set status = "odd" %}
				{% else %}
					{% set status = "even" %}
				{% endif %}
				<li class="{{ status }} {{ position }}" style="background-image: url({{ site.sitePath }}/habbo-imaging/badge/{{ group.badge }}.gif)">
						<a class="item" href="{{ group.generateClickLink() }}">{% autoescape 'html' %}{{ group.name }}{% endautoescape %}</a>
        </li>
				{% set i = i + 1 %}
		{% endfor %}
    </ul>
		<div class="habblet-button-row clearfix"><a class="new-button" id="purchase-group-button" href="#"><b>{{ locale.me_create_buy_a_group }}</b><i></i></a></div>
    </div>

    <div id="groups-habblet-group-purchase-button" class="habblet-list-container"></div>

<script type="text/javascript">
    $("purchase-group-button").observe("click", function(e) { Event.stop(e); GroupPurchase.open(); });
</script>






    </div>

					</div>
				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>

</div>

<script type="text/javascript">
	HabboView.add(LoginFormUI.init);
</script>
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