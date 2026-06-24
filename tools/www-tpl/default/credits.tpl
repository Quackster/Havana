
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="{{ locale.global_html_lang }}" lang="{{ locale.global_html_lang }}">
<head>
	<meta http-equiv="content-type" content="text/html" />
	<title>{{ site.siteName }}{{ locale.credits_credits }} </title>

<script type="text/javascript">
var andSoItBegins = (new Date()).getTime();
</script>
    <link rel="shortcut icon" href="{{ site.staticContentPath }}/web-gallery/v2/favicon.ico" type="image/vnd.microsoft.icon" />
    <link rel="alternate" type="application/rss+xml" title="{{ site.siteName }}{{ locale.credits_rss }}" href="{{ site.sitePath }}/articles/rss.xml" />
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
var habboName = "{{ locale.credits_session_loggedin_playerdetails_getname|escape('js') }} "" }}";
var ad_keywords = "";
var habboReqPath = "{{ site.sitePath }}";
var habboStaticFilePath = "{{ site.staticContentPath }}/web-gallery";
var habboImagerUrl = "{{ site.staticContentPath }}/habbo-imaging/";
var habboPartner = "";
window.name = "habboMain";
if (typeof HabboClient != "undefined") { HabboClient.windowName = "client"; }

</script>



<meta name="description" content="{{ locale.credits_join_the_world_s_largest_virtual_hangout_where_you_can_meet_and_make_friends_design_your_own_rooms_collect_cool_furniture_throw_parties_and_so_much_more_create_your_free }} {{ site.siteName }} {{ locale.credits_today }}" />
<meta name="keywords" content="{{ site.siteName }}{{ locale.credits_virtual_world_join_groups_forums_play_games_online_friends_teens_collecting_social_network_create_collect_connect_furniture_virtual_goods_sharing_badges_social_networking_hangout_safe_music_celebrity_celebrity_visits_cele }}" />

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
<meta name="build" content="{{ locale.credits_havanaweb }}" />
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
				{{ locale.credits_coins }}			</li>
			<li class="">
				<a href="{{ site.sitePath }}/credits/club">{{ site.siteName }} {{ locale.credits_club }}</a>			</li>
			<li class="">
				<a href="{{ site.sitePath }}/credits/collectables">{{ locale.credits_collectables }}</a>			</li>
    		<li class=" last">
				<a href="{{ site.sitePath }}/credits/pixels">{{ locale.credits_pixels }}</a>    		</li>
		</ul>
    </div>
</div>
<div id="container">
	<div id="content" style="position: relative" class="clearfix">
    <div id="column1" class="column">
			     		
				<div class="habblet-container ">		
						<div class="cbb clearfix green ">
	
							<h2 class="title">{{ locale.credits_how_to_get_credits }}
							</h2>
							
						<script src="{{ site.staticContentPath }}/web-gallery/static/js/credits.js" type="text/javascript"></script>
<p class="credits-countries-select">
{{ locale.credits_the_good_thing_about_this_server_is_that_credits_a_free_yes_free_you_won_t_have_to_spend_a_thing_to_get_credits_for_building_your_favourite_rooms_just_find_out_by_using_the_methods_below_to_receive_credits }}
</p>
<ul id="credits-methods">
	<li id="credits-type-promo">
		<h4 class="credits-category-promo">{{ locale.credits_best_way }}</h4>
		<ul>
<li class="clearfix odd"><div id="method-3" class="credits-method-container">
					<div class="credits-summary" style="background-image: url({{ site.staticContentPath }}/c_images/album2705/logo_sms.png)">
						<h3>{{ locale.credits_be_online }}</h3>
						<p>{{ locale.credits_just_by_playing_on_the_server_daily_you_can_receive_coins }}</p>
						
						<p class="credits-read-more" id="method-show-3" style="display: none">{{ locale.credits_read_more }}</p>
					</div>
					<div id="method-full-3" class="credits-method-full">
							<p><b>{{ locale.credits_receive_coins_by_being_online }}</b><br/>{{ locale.credits_you_need_to_be_in_a_room_but_every_day_if_you_wait_five_minutes_you_will_recieve_one_two_zero_credits_just_by_being_active }}</p>
							<p>{{ locale.credits_this_happens_once_every_two_four_hours_so_if_you_do_the_same_thing_tomorrow_you_ll_get_another_one_two_zero_credits }}</p>
					</div>
					<script type="text/javascript">
					$("method-show-3").show();
					$("method-full-3").hide();
					</script>
				</div></li>
		</ul>
	</li>
	<li id="credits-type-quick_and_easy">
		<h4 class="credits-category-quick_and_easy">{{ locale.credits_other_ways }}</h4>
		<ul>
				
<li class="clearfix odd"><div id="method-1" class="credits-method-container">
					<div class="credits-summary" style="background-image: url({{ site.staticContentPath }}/c_images/album2705/payment_habbo_prepaid.png)">
						<h3>{{ locale.credits_vouchers }}</h3>
						<p>{{ locale.credits_you_can_get_special_codes_to_redeem_vouchers }}</p>
						
						<p class="credits-read-more" id="method-show-1" style="display: none">{{ locale.credits_read_more }}</p>
					</div>
					<div id="method-full-1" class="credits-method-full">
							<p>{{ locale.credits_redeem_your_voucher_code_in_your_hotel_purse_or_on_this_page_and_you_will_get_your_coins_right_away }}</p>
					</div>
					<script type="text/javascript">
					$("method-show-1").show();
					$("method-full-1").hide();
					</script>
				</div></li>
		</ul>
	</li>
	 <li id="credits-type-other">
		<h4 class="credits-category-quick_and_easy">{{ locale.credits_other_ways }}</h4>
		<ul>
				
<li class="clearfix odd"><div id="method-2" class="credits-method-container">
					<div class="credits-summary" style="background-image: url({{ site.staticContentPath }}/c_images/album2705/byesw_hand.png)">
						<div class="credits-tools">
								<a  class="new-button" id="warn-clear-hand-button" href="#" onclick="warnClearHand()"><b>{{ locale.credits_reset_hand }}</b><i></i></a>
							
						</div>
						<h3>{{ locale.credits_reset_hand }}</h3>
						<p>{{ locale.credits_virtual_hand_too_full_of_furniture_click_here_to_reset_it }}</p>
						
						<!-- <p class="credits-read-more" id="method-show-2" style="display: none">{{ locale.credits_read_more }}</p>
					</div>
					<div id="method-full-2" class="credits-method-full">
							<p>{{ locale.credits_simply_click_the_button_to_reset_your_hand }}</p>
							<p><strong>{{ locale.credits_warning }} </strong> {{ locale.credits_this_action_cannot_be_undone }}</p>
					</div> -->
					{% if session.loggedIn %}
					<script type="text/javascript">
					var responseName = "wiped-hand";
					var responseWarnName = "warn-wiped-hand";
						
						
					function clearHand() {
						const http = new XMLHttpRequest();
						http.open("GET", habboReqPath + "/habblet/ajax/clear_hand");
						http.send();
						
						var responseName = "wiped-hand";
						
						if (document.getElementById(responseName) == null) {
							document.getElementById('method-2').insertAdjacentHTML('afterbegin', '{{ locale.credits_div_id|escape('js') }}' + responseName + '{{ locale.credits_style_border_radius_five_px_background_three_ba_eight_zero_zero_padding_eight_px_color_ffffff_the_hand_has_been_reset_br_div_br|escape('js') }}');
							document.getElementById(responseWarnName).remove();
						}
					}
					
					function warnClearHand() {
						var responseName = "warn-wiped-hand";
						
						if (document.getElementById(responseWarnName) == null) {
							document.getElementById('method-2').insertAdjacentHTML('afterbegin', '{{ locale.credits_div_id|escape('js') }}' + responseWarnName + '{{ locale.credits_style_border_radius_five_px_background_f_two_nine_four_zero_zero_padding_eight_px_color_ffffff_are_you_sure_strong_this_cannot_be_undone_strong_a_class_new_button_id_confirm_clear_hand_button_href_onclick_clearhand_b_yes_clear_it_now_b_i_i_a_br_br_div_br|escape('js') }}');
							document.getElementById("warn-clear-hand-button").remove();
						}
					}
					
					$("method-show-2").show();
					$("method-full-2").hide();
					</script>
					{% else %}
					<script type="text/javascript">
					var responseWarnName = "warn-wiped-hand";
					
					function warnClearHand() {
						var responseName = "warn-wiped-hand";
						
						if (document.getElementById(responseWarnName) == null) {
							document.getElementById('method-2').insertAdjacentHTML('afterbegin', '{{ locale.credits_div_id|escape('js') }}' + responseWarnName + '{{ locale.credits_style_border_radius_five_px_background_red_padding_eight_px_color_ffffff_you_must_be_logged_in_to_do_this_br_div_br|escape('js') }}');
							document.getElementById("warn-clear-hand-button").remove();
						}
					}
					
					$("method-show-2").show();
					$("method-full-2").hide();
					</script>
					{% endif %}
				</div></li>
		</ul>
	</li> 
</ul>

<script type="text/javascript">
L10N.put("credits.navi.read_more", "{{ locale.credits_read_more|escape('js') }}");
L10N.put("credits.navi.close_fulltext", "{{ locale.credits_close_instructions|escape('js') }}");
PaymentMethodHabblet.init();
</script>
	
						
					</div>

				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
			 

</div>
<div id="column2" class="column">
			     		
				<div class="habblet-container ">		
						<div class="cbb clearfix brown ">
	
							<h2 class="title">{{ locale.credits_your_purse }}							</h2>
							{% if session.loggedIn == false %}
								<div class="box-content">{{ locale.credits_you_need_to_sign_in_to_see_the_purse }}</div>
							{% else %}
					
		<div id="purse-habblet">
			<form method="post" action="{{ site.sitePath }}/credits" id="voucher-form">

			<ul>
			<li class="even icon-purse">
			<div>{{ locale.credits_you_currently_have }}</div>
			<span class="purse-balance-amount">{{ playerDetails.credits }} {{ locale.credits_coins }}</span>
			<div class="purse-tx"><a href="{{ site.sitePath }}/credits/history">{{ locale.credits_account_transactions }}</a></div>
			</li>

			<li class="odd">

			<div class="box-content">
			<div>{{ locale.credits_enter_voucher_code_without_spaces }}</div>
			<input type="text" name="voucherCode" value="" id="purse-habblet-redeemcode-string" class="redeemcode" />
			<a href="#" id="purse-redeemcode-button" class="new-button purse-icon" style="float:left"><b><span></span>{{ locale.credits_enter }}</b><i></i></a>
			</div>
			</li>
			</ul>
			<div id="purse-redeem-result">
			</div>	</form>
		</div>
		{% endif %}

<script type="text/javascript">
	new PurseHabblet();
</script>
	
						
					</div>
				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
				
				<div class="habblet-container ">		
						<div class="cbb clearfix orange ">
	
							<h2 class="title">{{ locale.credits_what_are }} {{ site.siteName }} {{ locale.credits_coins_label }}							</h2>

						<div id="credits-promo" class="box-content credits-info">
    <div class="credit-info-text clearfix">
        <img class="credits-image" src="{{ site.staticContentPath }}/web-gallery/v2/images/credits/poor.png" alt="" width="77" height="105" />
        <p class="credits-text">{{ site.siteName }} {{ locale.credits_coins_are_the_hotel_s_currency_you_can_use_them_to_buy_all_kinds_of_things_from_rubber_ducks_and_sofas_to_vip_membership_jukeboxes_and_teleports }}</p>
    </div>
    <p class="credits-text-2">{{ locale.credits_all_legitimate_ways_to_get }} {{ site.siteName }} {{ locale.credits_coins_are_to_the_left_remember }} {{ site.siteName }} {{ locale.credits_coins_are_always_and_always_will_be_free }}</p>
</div>
	
						
					</div>

				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
			 
				<div class="habblet-container ">		
						<div class="cbb clearfix blue ">
	
							<h2 class="title">{{ locale.credits_always_ask_permission_first }}
							</h2>
						<div id="credits-safety" class="box-content credits-info">
    <div class="credit-info-text clearfix"><img class="credits-image" src="{{ site.sitePath }}/web-gallery/v2/images/credits_permission.png" width="114" height="136"/><p class="credits-text">{{ locale.credits_always_ask_permission_from_your_parent_or_guardian_before_you_buy_habbo_coins_if_you_do_not_do_this_and_the_payment_is_later_canceled_or_declined_you_will_be_permanently_banned }}</p></div>
    <p class="credits-text-2">{{ locale.credits_uh_oh }}</p>
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