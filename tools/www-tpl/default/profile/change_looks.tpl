
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="{{ locale.global_html_lang }}" lang="{{ locale.global_html_lang }}">
<head>
	<meta http-equiv="content-type" content="text/html" />
	<title>{{ site.siteName }}{{ locale.profile_change_looks_my_details }} </title>

<script type="text/javascript">
var andSoItBegins = (new Date()).getTime();
</script>
    <link rel="shortcut icon" href="{{ site.staticContentPath }}/web-gallery/v2/favicon.ico" type="image/vnd.microsoft.icon" />
    <link rel="alternate" type="application/rss+xml" title="{{ site.siteName }}{{ locale.profile_change_looks_rss }}" href="{{ site.sitePath }}/articles/rss.xml" />
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
var habboName = "{{ locale.profile_change_looks_session_loggedin_playerdetails_getname|escape('js') }} "" }}";
var ad_keywords = "";
var habboReqPath = "{{ site.sitePath }}";
var habboStaticFilePath = "{{ site.staticContentPath }}/web-gallery";
var habboImagerUrl = "{{ site.staticContentPath }}/habbo-imaging/";
var habboPartner = "";
window.name = "habboMain";
if (typeof HabboClient != "undefined") { HabboClient.windowName = "client"; }

</script>

<script src="{{ site.staticContentPath }}/web-gallery/static/js/settings.js?{{ randomNumber }}" type="text/javascript"></script>
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/settings.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/friendmanagement.css" type="text/css" />


<meta name="description" content="{{ locale.profile_change_looks_join_the_world_s_largest_virtual_hangout_where_you_can_meet_and_make_friends_design_your_own_rooms_collect_cool_furniture_throw_parties_and_so_much_more_create_your_free }} {{ site.siteName }} {{ locale.profile_change_looks_today }}" />
<meta name="keywords" content="{{ site.siteName }}{{ locale.profile_change_looks_virtual_world_join_groups_forums_play_games_online_friends_teens_collecting_social_network_create_collect_connect_furniture_virtual_goods_sharing_badges_social_networking_hangout_safe_music_celebrity_celebrity_visits_cele }}" />

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
<meta name="build" content="{{ locale.profile_change_looks_havanaweb }}" />
</head>

{% include "../base/header.tpl" %}

<div id="content-container">

	<div id="navi2-container" class="pngbg">
    <div id="navi2" class="pngbg clearfix">
		<ul>
			<li class="">
				<a href="{{ site.sitePath }}/me">{{ locale.profile_change_looks_home }}</a>			</li>
    		<li class="">
				<a href="{{ site.sitePath }}/home/{{ playerDetails.getName() }}">{{ locale.profile_change_looks_my_page }}</a>    		</li>
    		<li class="selected">
				{{ locale.profile_change_looks_account_settings }}    		</li>			
			<li class="{% if gameConfig.getInteger('guides.group.id') == 0 %} last{% endif %}">
				<a href="{{ site.sitePath }}/club">{{ site.siteName }} {{ locale.profile_change_looks_club }}</a>
			</li>
			{% if gameConfig.getInteger('guides.group.id') > 0 %}
			<li class=" last">
				<a href="{{ site.sitePath }}/groups/officialhabboguides">{{ locale.profile_change_looks_habbo_guides }}</a>
			</li>
			{% endif %}
		</ul>
    </div>
</div>
	
<div id="container">
	<div id="content" style="position: relative" class="clearfix">
    <div>
<div class="content">
<div class="habblet-container" style="float:left; width:210px;">
<div class="cbb settings">

<h2 class="title">{{ locale.profile_change_looks_account_settings }}</h2>
<div class="box-content">
            <div id="settingsNavigation">
            <ul>
		<li class="selected">{{ locale.profile_change_looks_my_clothing }}
                </li><li><a href="{{ site.sitePath }}/profile?tab=2">{{ locale.profile_change_looks_my_preferences }}</a>
				{% if accountActivated %}
                </li><li><a href="{{ site.sitePath }}/profile?tab=3">{{ locale.profile_change_looks_my_email }}</a>
				{% else %}
				</li><li><a href="{{ site.sitePath }}/profile/verify">{{ locale.profile_change_looks_email_changing_verification }}</a>
				{% endif %}
                </li><li><a href="{{ site.sitePath }}/profile?tab=4">{{ locale.profile_change_looks_my_password }}</a>
                </li><li><a href="{{ site.sitePath }}/profile?tab=5">{{ locale.profile_change_looks_friend_management }}</a>
								</li><li><a href="{{ site.sitePath }}/profile?tab=6">{{ locale.profile_change_looks_trade_settings }}</a>
                </li>            </ul>
            </div>
</div></div>
    {% include "profile/profile_widgets/join_club.tpl" %}
</div>

<div class="habblet-container" style="float:left; width: 560px;">
<div class="cbb clearfix settings">

<h2 class="title">{{ locale.profile_change_looks_change_your_looks }}</h2>
<div class="box-content">

{% if settingsSavedAlert %}
<div class="rounded rounded-green">{{ locale.profile_change_looks_account_settings_updated_successfully }}<br />	</div><br />
{% endif %}
	<div>&nbsp;</div>

<div id="settings-editor">
{{ locale.profile_change_looks_you_need_to_have_a_flash_player_installed_on_your_computer_before_being_able_to_edit_your }} {{ site.siteName }} {{ locale.profile_change_looks_character_you_can_download_the_player_from_here }} <a target="_blank" href="http://www.adobe.com/go/getflashplayer">http://www.adobe.com/go/getflashplayer</a>
</div>

{% if playerDetails.hasClubSubscription() %}
<div id="settings-wardrobe" style="display: none">
<ol id="wardrobe-slots">
	<li>
		<p id="wardrobe-slot-1" style="background-image: url({{ wardrobeUrl1 }})">
	   		<span id="wardrobe-store-1" class="wardrobe-store"></span>
	   		<span id="wardrobe-dress-1" class="wardrobe-dress"></span>
   		</p>
    </li>
	<li>
		<p id="wardrobe-slot-2" style="background-image: url({{ wardrobeUrl2 }})">
	   		<span id="wardrobe-store-2" class="wardrobe-store"></span>
	   		<span id="wardrobe-dress-2" class="wardrobe-dress"></span>
   		</p>
    </li>
	<li>
		<p id="wardrobe-slot-3" style="background-image: url({{ wardrobeUrl3 }})">
	   		<span id="wardrobe-store-3" class="wardrobe-store"></span>
	   		<span id="wardrobe-dress-3" class="wardrobe-dress"></span>
   		</p>
    </li>
	<li>
		<p id="wardrobe-slot-4" style="background-image: url({{ wardrobeUrl4 }})">
	   		<span id="wardrobe-store-4" class="wardrobe-store"></span>
	   		<span id="wardrobe-dress-4" class="wardrobe-dress"></span>
   		</p>
    </li>
	<li>
		<p id="wardrobe-slot-5" style="background-image: url({{ wardrobeUrl5 }})">
	   		<span id="wardrobe-store-5" class="wardrobe-store"></span>
	   		<span id="wardrobe-dress-5" class="wardrobe-dress"></span>
   		</p>
    </li>
</ol>

<script type="text/javascript">
{% if wardrobe1 %}
Wardrobe.add(1, "{{ wardrobeFigure1 }}", "{{ wardrobeSex1 }}", true);
$("wardrobe-dress-1").show();
{% endif %}
{% if wardrobe2 %}
Wardrobe.add(2, "{{ wardrobeFigure2 }}", "{{ wardrobeSex2 }}", true);
$("wardrobe-dress-2").show();
{% endif %}
{% if wardrobe3 %}
Wardrobe.add(3, "{{ wardrobeFigure3 }}", "{{ wardrobeSex3 }}", true);
$("wardrobe-dress-3").show();
{% endif %}
{% if wardrobe4 %}
Wardrobe.add(4, "{{ wardrobeFigure4 }}", "{{ wardrobeSex4 }}", true);
$("wardrobe-dress-4").show();
{% endif %}
{% if wardrobe5 %}
Wardrobe.add(5, "{{ wardrobeFigure5 }}", "{{ wardrobeSex5 }}", true);
$("wardrobe-dress-5").show();
{% endif %}
L10N.put("profile.figure.wardrobe_replace.title", "{{ locale.profile_change_looks_replace|escape('js') }}");
L10N.put("profile.figure.wardrobe_replace.dialog", "{{ locale.profile_change_looks_p_nare_you_sure_you_want_to_replace_the_stored_outfit_with_the_new_one_n_p_n_n_p_n_a_href_class_new_button_id_wardrobe_replace_cancel_b_cancel_b_i_i_a_n_a_href_class_new_button_id_wardrobe_replace_ok_b_ok_b_i_i_a_n_p_n_n_div_class_clear_div_n|escape('js') }}");
L10N.put("profile.figure.wardrobe_invalid_data", "{{ locale.profile_change_looks_error_this_outfit_cannot_be_saved|escape('js') }}");
L10N.put("profile.figure.wardrobe_instructions", "{{ locale.profile_change_looks_press_red_arrows_to_save_up_to_five_outfits_to_your_wardrobe_press_green_arrow_to_select_an_outfit_and_save_changes_to_take_it_into_use|escape('js') }}");
Wardrobe.init();
</script>
</div>
{% endif %}

<div id="settings-hc" style="display: none">
	<div class="rounded rounded-hcred clearfix">
<a href="{{ site.sitePath }}/club" id="settings-hc-logo"></a>
{{ locale.profile_change_looks_items_marked_with_the }} {{ site.siteName }} {{ locale.profile_change_looks_club_symbol }} <img src="{{ site.staticContentPath }}/web-gallery/v2/images/habboclub/hc_mini.png" /> {{ locale.profile_change_looks_are_available_only_to }} {{ site.siteName }} {{ locale.profile_change_looks_club_members }} <a href="{{ site.sitePath }}/club">{{ locale.profile_change_looks_join_now }}</a>
	</div>
</div>

<div id="settings-oldfigure" style="display: none">
	<div class="rounded rounded-lightbrown clearfix">
{{ locale.profile_change_looks_your }} {{ site.siteName }} {{ locale.profile_change_looks_had_clothes_or_colors_that_are_not_selectable_anymore_please_save_your_new_looks_here }}	</div>
</div>

<form method="post" action="{{ site.sitePath }}/profile/characterupdate" id="settings-form" style="display: none">
<input type="hidden" name="tab" value="1" />
<input type="hidden" name="__app_key" value="{{ locale.profile_change_looks_keplerweb }}" />
<input type="hidden" name="figureData" id="settings-figure" value="{{ playerDetails.figure }}" />
<input type="hidden" name="newGender" id="settings-gender" value="{{ playerDetails.gender }}" />
<input type="hidden" name="editorState" id="settings-state" value="" />
<a href="#" id="settings-submit" class="new-button disabled-button"><b>{{ locale.profile_change_looks_save_changes }}</b><i></i></a>

<script type="text/javascript" language="JavaScript">
var swfobj = new SWFObject("{{ site.sitePath }}/flash/HabboRegistration.swf", "habboreg", "435", "400", "8");
swfobj.addParam("base", "{{ site.sitePath }}/flash/");
swfobj.addParam("wmode", "opaque");
swfobj.addParam("AllowScriptAccess", "always");
swfobj.addVariable("figuredata_url", "{{ site.sitePath }}/xml/figuredata.xml");
swfobj.addVariable("draworder_url", "{{ site.sitePath }}/xml/draworder.xml");
swfobj.addVariable("localization_url", "{{ site.sitePath }}/xml/figure_editor.xml");
swfobj.addVariable("figure", "{{ playerDetails.figure }}");
swfobj.addVariable("gender", "{{ playerDetails.sex }}");

swfobj.addVariable("showClubSelections", "1");

{% if playerDetails.hasClubSubscription() %}
swfobj.addVariable("userHasClub", "1");
{% endif %}

if (deconcept.SWFObjectUtil.getPlayerVersion()["major"] >= 8) {
	$("settings-editor").setStyle({ textAlign: "center"});	swfobj.write("settings-editor");
	$("settings-form").show();
	
	{% if playerDetails.hasClubSubscription() %}
		$("settings-wardrobe").show();}
	{% else %}
		}
	{% endif %}
</script>

</form>

</div>

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

{% include "../base/footer.tpl" %}
	
<script type="text/javascript">
HabboView.run();
</script>


</body>
</html>