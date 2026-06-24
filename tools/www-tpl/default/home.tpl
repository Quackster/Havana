
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="{{ locale.global_html_lang }}" lang="{{ locale.global_html_lang }}">
<head>
	<meta http-equiv="content-type" content="text/html" />
	<title>{{ site.siteName }}: {{ user.getName() }} </title>

<script type="text/javascript">
var andSoItBegins = (new Date()).getTime();
</script>
    <link rel="shortcut icon" href="{{ site.staticContentPath }}/web-gallery/v2/favicon.ico" type="image/vnd.microsoft.icon" />
    <link rel="alternate" type="application/rss+xml" title="{{ site.siteName }}{{ locale.home_rss }}" href="{{ site.sitePath }}/articles/rss.xml" />
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
var habboName = "{{ locale.home_session_loggedin_playerdetails_getname|escape('js') }} "" }}";
var ad_keywords = "";
var habboReqPath = "{{ site.sitePath }}";
var habboStaticFilePath = "{{ site.staticContentPath }}/web-gallery";
var habboImagerUrl = "{{ site.staticContentPath }}/habbo-imaging/";
var habboPartner = "";
window.name = "habboMain";
if (typeof HabboClient != "undefined") { HabboClient.windowName = "client"; }

</script>

<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/styles/myhabbo/myhabbo.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/styles/myhabbo/skins.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/styles/myhabbo/dialogs.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/styles/myhabbo/buttons.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/styles/myhabbo/control.textarea.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/styles/myhabbo/boxes.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/myhabbo.css" type="text/css" />
	<link href="{{ site.staticContentPath }}/web-gallery/styles/myhabbo/assets.css" type="text/css" rel="stylesheet" />

<script src="{{ site.staticContentPath }}/web-gallery/static/js/homeview.js" type="text/javascript"></script>
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/lightwindow.css" type="text/css" />

<script src="{{ site.staticContentPath }}/web-gallery/static/js/homeauth.js" type="text/javascript"></script>
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/group.css" type="text/css" />

<!-- HTML5 trax player -->
<script src="{{ site.staticContentPath }}/web-gallery/static/js/webbanditten/traxplayer.js" type="text/javascript"></script>
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/webbanditten/traxplayer.css" type="text/css" />

<style type="text/css">
    #playground, #playground-outer {
	    width: {% if user.hasClubSubscription() %}922{% else %}752{% endif %}px;
	    height: 1360px;
    }

</style>

{% if editMode %}
<script src="{{ site.staticContentPath }}/web-gallery/static/js/homeedit.js" type="text/javascript"></script>
{% endif %}

<script type="text/javascript">
document.observe("dom:loaded", function() { initView({{ user.id }}, {{ user.id }}); });

{% if editMode %}
function isElementLimitReached() {
	if (getElementCount() >= {{ stickerLimit }}) {
		showHabboHomeMessageBox("{{ locale.home_error|escape('js') }}", "{{ locale.home_you_have_already_reached_the_maximum_number_of_elements_on_the_page_remove_a_sticker_note_or_widget_to_be_able_to_place_this_item|escape('js') }}", "{{ locale.home_close|escape('js') }}");
		return true;
	}
	return false;
}

function cancelEditing(expired) {
	location.replace("{{ site.sitePath }}/myhabbo/cancel/{{ user.id }}" + (expired ? "?expired=true" : ""));
}

function getSaveEditingActionName(){
	return '/myhabbo/save';
}

function showEditErrorDialog() {
	var closeEditErrorDialog = function(e) { if (e) { Event.stop(e); } Element.remove($("myhabbo-error")); Overlay.hide(); }
	var dialog = Dialog.createDialog("myhabbo-error", "", false, false, false, closeEditErrorDialog);
	Dialog.setDialogBody(dialog, '{{ locale.home_p_error_occurred_please_try_again_in_couple_of_minutes_p_p_a_href_class_new_button_id_myhabbo_error_close_b_close_b_i_i_a_p_div_class_clear_div|escape('js') }}');
	Event.observe($("myhabbo-error-close"), "click", closeEditErrorDialog);
	Dialog.moveDialogToCenter(dialog);
	Dialog.makeDialogDraggable(dialog);
}


function showSaveOverlay() {
	var invalidPos = getElementsInInvalidPositions();
	if (invalidPos.length > 0) {
	    $A(invalidPos).each(function(el) { Element.scrollTo(el);  Effect.Pulsate(el); });
	    showHabboHomeMessageBox("{{ locale.home_whoops_you_can_t_do_that|escape('js') }}", "{{ locale.home_sorry_but_you_can_t_place_your_stickers_notes_or_widgets_here_close_the_window_to_continue_editing_your_page|escape('js') }}", "{{ locale.home_close|escape('js') }}");
		return false;
	} else {
		Overlay.show(null,'Saving');
		return true;
	}
}

{% endif %}
</script>


<meta name="description" content="{{ locale.home_join_the_world_s_largest_virtual_hangout_where_you_can_meet_and_make_friends_design_your_own_rooms_collect_cool_furniture_throw_parties_and_so_much_more_create_your_free }} {{ site.siteName }} {{ locale.home_today }}" />
<meta name="keywords" content="{{ site.siteName }}{{ locale.home_virtual_world_join_groups_forums_play_games_online_friends_teens_collecting_social_network_create_collect_connect_furniture_virtual_goods_sharing_badges_social_networking_hangout_safe_music_celebrity_celebrity_visits_cele }}" />

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

<meta name="build" content="{{ locale.home_havanaweb }}" />
</head>

{% if session.loggedIn == false %}
<body id="viewmode" class=" anonymous ">
{% else %}
	{% if editMode %}
	<body id="editmode" class=" ">
	{% else %}
	<body id="viewmode" class=" ">
	{% endif %}
{% endif %}
{% include "base/header.tpl" %}

<div id="content-container">

{% if session.loggedIn %}
{% if user.id == playerDetails.id %}
	<div id="navi2-container" class="pngbg">
		<div id="navi2" class="pngbg clearfix">
			<ul>
				<li class="">
					<a href="{{ site.sitePath }}/me">{{ locale.home_home }}</a>    		</li>
				<li class="selected">
					{{ locale.home_my_page }}    		</li>
				<li class="">
					<a href="{{ site.sitePath }}/profile">{{ locale.home_account_settings }}</a>    		</li>
				<li class="{% if gameConfig.getInteger('guides.group.id') == 0 %} last{% endif %}">
					<a href="{{ site.sitePath }}/club">{{ site.siteName }} {{ locale.home_club }}</a>
				</li>
				{% if gameConfig.getInteger('guides.group.id') > 0 %}
				<li class=" last">
					<a href="{{ site.sitePath }}/groups/officialhabboguides">{{ locale.home_habbo_guides }}</a>
				</li>
				{% endif %}
			</ul>
		</div>
	</div>
{% else %}
	<div id="navi2-container" class="pngbg">
		<div id="navi2" class="pngbg clearfix">
			<ul>
				<li class="">
					<a href="{{ site.sitePath }}/me">{{ locale.home_home }}</a>    		</li>
				<li class="">
					<a href="{{ site.sitePath }}/home/{{ playerDetails.getName() }}">{{ locale.home_my_page }}</a>    		</li>
				<li class="">
					<a href="{{ site.sitePath }}/profile">{{ locale.home_account_settings }}</a>    		</li>
				<li class="{% if gameConfig.getInteger('guides.group.id') == 0 %} last{% endif %}">
					<a href="{{ site.sitePath }}/club">{{ site.siteName }} {{ locale.home_club }}</a>
				</li>
				{% if gameConfig.getInteger('guides.group.id') > 0 %}
				<li class=" last">
					<a href="{{ site.sitePath }}/groups/officialhabboguides">{{ locale.home_habbo_guides }}</a>
				</li>
				{% endif %}
			</ul>
		</div>
	</div>
{% endif %}
{% endif %}

<div id="container">
	<div id="content" style="position: relative" class="clearfix">
    <div id="mypage-wrapper" class="cbb blue">
<div class="box-tabs-container box-tabs-left clearfix">
	{% if session.loggedIn %}
	{% if user.id == playerDetails.id %}
	{% if editMode == false %}
	<a href="{{ site.sitePath }}/myhabbo/startSession/{{ user.id }}" id="edit-button" class="new-button dark-button edit-icon" style="float:left"><b><span></span>{{ locale.home_edit }}</b><i></i></a>
	{% endif %}
	{% endif %}
	{% endif %}
	<div class="myhabbo-view-tools">
	</div>

    <h2 class="page-owner">{{ user.getName() }}</h2>
    <ul class="box-tabs"></ul>
</div>
	<div id="mypage-content">

{% if editMode %}

<div id="top-toolbar" class="clearfix">
	<ul>
		<li><a href="#" id="inventory-button">{{ locale.home_inventory }}</a></li>
		<li><a href="#" id="webstore-button">{{ locale.home_web_store }}</a></li>
	</ul>
	
	<form action="#" method="get" style="width: 50%">
		<a id="cancel-button" class="new-button red-button cancel-icon" href="#"><b><span></span>{{ locale.home_cancel_editing }}</b><i></i></a>

		<a id="save-button" class="new-button green-button save-icon" href="#"><b><span></span>{{ locale.home_save_changes }}</b><i></i></a>
	</form>
</div>


{% endif %}
	
	
			{% if user.getName() == "Abigail.Ryan" %}
						<div id="mypage-bg" class="b_bg_solid_black">
				<div id="playground">




<div class="movable stickie n_skin_nakedskin-c" style=" left: 114px; top: 8px; z-index: 5;" id="stickie-3188500">
	<div class="n_skin_nakedskin">
		<div class="stickie-header">
			<h3>			</h3>
			<div class="clear"></div>
		</div>
		<div class="stickie-body">
			<div class="stickie-content">
				<div class="stickie-markup"><h2><p style="color:#9D9D9D">{{ locale.home_i_am_the_malady_i_am_the_pain }}</p></h2></div>
				<div class="stickie-footer">
				</div>
			</div>
		</div>
	</div>
</div>




<div class="movable stickie n_skin_nakedskin-c" style=" left: 73px; top: 121px; z-index: 7;" id="stickie-3188552">
	<div class="n_skin_nakedskin">
		<div class="stickie-header">
			<h3>			</h3>
			<div class="clear"></div>
		</div>
		<div class="stickie-body">
			<div class="stickie-content">
				<div class="stickie-markup"><h2><p style="color:#FFFFFF">{{ locale.home_i_am_the_death_that_never_dies }}</p></h2></div>
				<div class="stickie-footer">
				</div>
			</div>
		</div>
	</div>
</div>




<div class="movable stickie n_skin_nakedskin-c" style=" left: 315px; top: 660px; z-index: 8;" id="stickie-3188551">
	<div class="n_skin_nakedskin">
		<div class="stickie-header">
			<h3>			</h3>
			<div class="clear"></div>
		</div>
		<div class="stickie-body">
			<div class="stickie-content">
				<div class="stickie-markup"><h1><p style="color:#007400">{{ locale.home_on_halloween_day_i_shall_come_to_play }}</p></h1></div>
				<div class="stickie-footer">
				</div>
			</div>
		</div>
	</div>
</div>




<div class="movable stickie n_skin_nakedskin-c" style=" left: 425px; top: 55px; z-index: 6;">
	<div class="n_skin_nakedskin">
		<div class="stickie-header">
			<h3>			</h3>
			<div class="clear"></div>
		</div>
		<div class="stickie-body">
			<div class="stickie-content">
				<div class="stickie-markup"><p style="color:#FFFFFF">{{ locale.home_my_life_was_lost_in_the_virus_rain }}</p></div>
				<div class="stickie-footer">
				</div>
			</div>
		</div>
	</div>
</div>




<div class="movable stickie n_skin_nakedskin-c" style=" left: 220px; top: 85px; z-index: 9;">
	<div class="n_skin_nakedskin">
		<div class="stickie-header">
			<h3>			</h3>
			<div class="clear"></div>
		</div>
		<div class="stickie-body">
			<div class="stickie-content">
				<div class="stickie-markup"><h3><p style="color:#727272">{{ locale.home_i_am_the_poor_one_who_cries }}</p></h3></div>
				<div class="stickie-footer">
				</div>
			</div>
		</div>
	</div>
</div>




<div class="movable stickie n_skin_nakedskin-c" style=" left: 14px; top: 200px; z-index: 10;">
	<div class="n_skin_nakedskin">
		<div class="stickie-header">
			<h3>			</h3>
			<div class="clear"></div>
		</div>
		<div class="stickie-body">
			<div class="stickie-content">
				<div class="stickie-markup">
				<div style="background-image: url({{ site.sitePath }}/boo.gif); height: 278px; width: 547px;"></div>
				<audio src="{{ site.sitePath }}/horror.mp3" autoplay loop>
				</audio>
				</div>
				<div class="stickie-footer">
				</div>
			</div>
		</div>
	</div>
</div>
    <div class="movable sticker s_hw08_websbl" style="left: 3px; top: 1224px; z-index: 1; background-image:url(data:image/gif;base64,R0lGODlhUgCFAJECAMjIyKenp////wAAACH5BAEAAAIALAAAAABSAIUAAAL/lI8XadsMVXCR0Wpv1MjxvXyVOJIJgAUoZgCUmcCxrLDCWrmx3fINjcDlhEEOEKjzHYgQz4n5sEEZSeXS9mpNo5jtEgjx1paorUwMEEuxClRoxPKmrRG1IIRzhlNDOhzL9GZxV1dn56M2scF19eF4qGSimMPYQ9hhAVmCCcKjZ3lZqeKX8tkBtnIhZDpKimUEBkqxGjrpyjZpS5cKOqZ7y/ISS1UT+AvMdze890BUhex5fJu3Ax25HHelbf3KfbKtCY2NeLPtzXZ+xZvuM55dxm7l3lUeL88+Z083z6y/7x3O3xhoAdOpSKNiAr9eAv8BW+hvXsGGnEhNpDjwHkZk8MsubszIw+PHSslGciRnUlzIlNZgQGQZqh/Mk4Vmtmxi0xsHkTlPHczX8ybDoDR5EsVk9ChIpTSZCnXaFOpDqSqp3nrpFCtTrUe5drU6FSwpr0TJBjWbE21asa7UznT7lq0faXJzwE0pqO6amFLpEuNrtRWloWAFfzNX13DMuxFXONO7gUnSnoIZfxw1WSlQyNk4r/SMDvRg0aNJ4zQtE3UQ1adZL2Vt2WTsy66r1Sapejbt27hR6974G2PwhsOJ8+5turhA5RGPM9f33F50dtOpHwec+3p1g861e+e9/Vx4nd3Lgzd/ezw39VXPo69dAAA7);width:82px;height:133px;left:2px;top:2px;z-index:4;background-repeat: no-repeat; margin: 0px; padding: 0px; ">
	</div>
    </div>
    <div class="movable sticker s_hw08_webstr" style="left: 837px; top: 2px; z-index: 2; background-image:url(data:image/gif;base64,R0lGODlhUACEAJECAMjIyKenp////wAAACH5BAEAAAIALAAAAABQAIQAAAL/jI6py+0PozCy2osRzbz77YXiA47mWZ5qmK7u1b4yFM82U9/6tPdL7psBg64hUWU8opS9JJP13DmjnCkVY73CtLIsN+L90sSrMBl3RqZN5rXGPWrD5W56HQ7Fd+xr/tlPBhiot0dYZcgBgIihuGjR6Fgh+AUZCVFpSZIZgbm50OmZAADqGaBIamn6FjrRiUqoKoD5OocJMqkT+ymKqNsAScsVkNSIS1XM+sOTrABgfDXMrCyd8AxNXY29qr2sbR31Dc7djR3+ZK6EfqS+Ps4e9A7vPu8tW34QvLiRbzhsOsoPT4qAd6ZtqkFwkIOEX4DEiyOBIRVi+iw8PHRBYjtG3Xq8XJTkQaOUECJvyPnYapQpfyZKvuDjsoyKmEtW0BQxadREGafO8bR3c8tPZPJm9MQHDxcwBEElRLPJlFdSP5BKqKPwlOSbWeg2ZE209cdXIROwhiHayazJFGM5batkwOukpy3aLjwAAi7eaje/1rA7Le82cq26kMhBVKrVH03t0YiBdVddHE2N+No7OQYpnS2dFt5LOKoDhDSDeVUUd/SlB5xDemZ6FM1q1iVNr2otmzWns2BAw25gJJ8zsJc2swUZ0coQWsOzRbQ4JQdgBVmdRE/y1/fj0JpgBCgAADs=);width:80px;height:132px;left:3px;top:1224px;z-index:1;background-repeat: no-repeat; margin: 0px; padding: 0px; ">
    </div>
    <div class="movable sticker s_hw08_websbr" style="left: 839px; top: 1224px; z-index: 3; background-image:url(data:image/gif;base64,R0lGODlhUACEAJECAMjIyKenp////wAAACH5BAEAAAIALAAAAABQAIQAAAL/lI+py+0PRYi02osm3rzrDobOJ5amdKYiqbYV68YPLNcKbed4bu+87Pu1gkIVsXg6IldLnrIJekI30immarVgs5EtF+L9jsSpMHlhPifSagO7jYKH3mp6XT7He/R7PtV/BRgoqEX4YniI2KUIAcDY+PjgGMlgdzZJuYCZmcGp6amwCQog6ukIYMn1gWoamrokOsn6SMIya7hZanALiKmLwKun8XvzarQL6haXHEBMa5wFHZ18IG1lPYXdpL1NXe29TM29NF5Ubg4eDnouxP7jrpMOH+/tOC8zee+CqZ9CShogYKs1nGj0C0HsYAdnCv+MQeTsGy4KDSkm8hPxRq9B3cIcysnYoGKoKHLSiES2IiCqf/dALiLj8iWXmBazkBpi5ZSLeTppXmyHzOfPHL4ECB2K70DRIdxyKTUyjsWqJAJ1xFp2dI0GbAFh2Hvq4YO1ql6VGeVAdgK0qihI8AP7AgbbE7y2Aut0lqJBN3SC1YIb58hcgtXMBFM3TGPeSj7sHFanrJRdNESyJrj5CQ3ktq5kLkzo4Ndko2ojWDbqLOKO0rcqf5YEpsGpTUeONoM0I3Q+iaGvgASJA7NE1y9cKpH7S7jsuBWMr4mYUbCUJ2KlPxxRZbqE42Y1PygAADs=);width:80px;height:132px;left:839px;z-index:3;top:1228px;background-repeat: no-repeat; margin: 0px; padding: 0px; ">
    </div>
    <div class="movable sticker s_hw08_webstl" style="left: 2px; top: 2px; z-index: 4; background-image:url(data:image/gif;base64,R0lGODlhUgCFAJECAMjIyKenpwAAAAAAACH5BAEAAAIALAAAAABSAIUAAAL/TISpm8ffHGRSKjuRyQ/zs3HaCHlZKJJpZVKtmmovA8RYUGf5GS+4DfTtFoAhxNgT/ETIztHomCGavaVONCuqpAJqUnv9TGpg2Dg59nbFJWW3wkTrvNm2ewpKqeVlYtuUR8R1J6cD6IQUVcK1V/hG4TMFuTaB4uTIdHORMKSI9WJhickZCtMpaXOoNFiIEohK2OjjITpa6amQ4yDbEfVq++lRI8G72ACcZCVJDBzCimzGDG3wDE0JW5xq3bPTvR3xHSeZjVY9PR5uln60Rh60Hue+As8tz0YfY1+Jj6Y/yc/tHMAvyKgBwFHEXDqFkQbyGcXQob6IDgklq4jJHUWM6uQ2YrR476MjXh5FyiopEuS/lKPUoGRJh6U1VTKRJapprUjCWjhbHuuZUx1Qny+H/jQ6E+m2okpVNnXEFGlUqU8LVm12FWtWTFOHdvW6lWtYqGPJli13Fm3ai2vftf3wtaeyt3/o8rhmdxLJuN+8GTo7hMrBtqVy8cVnJMdgu0gW5411mOOjx0T8ZbV8FXPVyDI1P/XclDNMyuxI0zB9CfVR1U5Nixb5+mNs2axXq56NEbdD3QN5964tlLRvgMPxFad3HDnw1pSTw3O+cDn06MCnf7N+Xbr27bWxJ+XO2rvA6uBvL2f+WHzBAgA7);width:82px;height:133px;left:837px;top:2px;z-index:2;background-repeat: no-repeat; margin: 0px; padding: 0px; ">
    </div>
					
				</div>
				<div id="mypage-ad">
    <div class="habblet ">
    
    </div>
				</div>
			</div>
			</div>
			{% else %}
			<div id="mypage-bg" class="b_{{ home.getBackground() }}">
				{% if editMode %}
				<div id="playground-outer">				<div id="playground">
				{% else %}
				<div id="playground">
				{% endif %}

				{% for sticker in stickers %}
					{% if sticker.getProduct().getData() == "groupinfowidget" %}
						{% include "homes/widget/group_info_widget.tpl" %}
					{% elseif sticker.getProduct().getData() == "guestbookwidget" %}
						{% include "homes/widget/guestbook_widget.tpl" %}
					{% elseif sticker.getProduct().getData() == "stickienote" %}
						{% include "homes/widget/note.tpl" %}
					{% elseif sticker.getProduct().getData() == "memberwidget" %}
						{% include "homes/widget/member_widget.tpl" %}
					{% elseif sticker.getProduct().getData() == "traxplayerwidget" %}
						{% include "homes/widget/trax_player_widget.tpl" %}
					{% elseif sticker.getProduct().getData() == "profilewidget" %}
						{% include "homes/widget/profile_widget.tpl" %}
					{% elseif sticker.getProduct().getData() == "roomswidget" %}
						{% include "homes/widget/rooms_widget.tpl" %}
					{% elseif sticker.getProduct().getData() == "highscoreswidget" %}
						{% include "homes/widget/highscores_widget.tpl" %}
					{% elseif sticker.getProduct().getData() == "badgeswidget" %}
						{% include "homes/widget/badges_widget.tpl" %}
					{% elseif sticker.getProduct().getData() == "ratingwidget" %}
						{% include "homes/widget/rating_widget.tpl" %}
					{% elseif sticker.getProduct().getData() == "friendswidget" %}
						{% include "homes/widget/friends_widget.tpl" %}
					{% elseif sticker.getProduct().getData() == "groupswidget" %}
						{% include "homes/widget/groups_widget.tpl" %}
					{% else %}
						{% include "homes/widget/sticker.tpl" %}
					{% endif %}
				{% endfor %}

</div>	
				{% if editMode %}
				</div>				<div id="mypage-ad">
				{% else %}
				<div id="mypage-ad">
				{% endif %}
<div class="habblet ">
	<div class="ad-container">
	{% if (user.hasClubSubscription() == false) %}
		{% if (editMode == true) %}
			<a href="{{ site.sitePath }}/club.php"><img src="{{ site.staticContentPath }}/web-gallery/album1/hc_habbohome_banner_holo.png" id="galleryImage" border="0" alt="{{ locale.home_hc_habbohome_banner_holo }}"></a>
		{% else %}
			<a href="{{ site.sitePath }}/club.php"><img src="{{ site.staticContentPath }}/c_images/banners/160x600/{{ homeBannerAd }}" id="galleryImage" border="0" alt="{{ locale.home_hc_habbohome_banner_holo }}"></a>
		{% endif %}
	{% endif %}
			</div>
</div>
				</div>
			</div>
			{% endif %}
	</div>
</div>

<script language="JavaScript" type="text/javascript">
{% if editMode %}
initEditToolbar();
initMovableItems();
document.observe("dom:loaded", initDraggableDialogs);
Utils.setAllEmbededObjectsVisibility('hidden');
{% else %}
	Event.observe(window, "load", observeAnim);
	document.observe("dom:loaded", function() {
		initDraggableDialogs();
	});
{% endif %}
</script>


    </div>
	    </div>
		    </div>
{% if editMode %}
<div id="edit-save" style="display:none;"></div>    </div>{% endif %}
{% include "base/footer.tpl" %}

{% if editMode %}


<div id="edit-menu" class="menu">
	<div class="menu-header">
		<div class="menu-exit" id="edit-menu-exit"><img src="{{ site.staticContentPath }}/web-gallery/images/dialogs/menu-exit.gif" alt="" width="11" height="11" /></div>
		<h3>{{ locale.home_edit }}</h3>

	</div>
	<div class="menu-body">
		<div class="menu-content">
			<form action="#" onsubmit="return false;">
				<div id="edit-menu-skins">
	<select id="edit-menu-skins-select">
				<option value="1" id="edit-menu-skins-select-defaultskin">{{ locale.home_default }}</option>
			<option value="6" id="edit-menu-skins-select-goldenskin">{{ locale.home_golden }}</option>
			{% if playerDetails.getRank().getRankId() >= 5 %}
			<option value="9" id="edit-menu-skins-select-default">{{ locale.home_no_skin }}</option>	
			{% endif %}
			<option value="3" id="edit-menu-skins-select-metalskin">{{ locale.home_metal }}</option>
			<option value="5" id="edit-menu-skins-select-notepadskin">{{ locale.home_notepad }}</option>
			<option value="2" id="edit-menu-skins-select-speechbubbleskin">{{ locale.home_speech_bubble }}</option>
			<option value="4" id="edit-menu-skins-select-noteitskin">{{ locale.home_stickie_note }}</option>
			{% if playerDetails.hasClubSubscription() %}
			<option value="8" id="edit-menu-skins-select-hc_pillowskin">{{ locale.home_hc_bling }}</option>
			<option value="7" id="edit-menu-skins-select-hc_machineskin">{{ locale.home_hc_scifi }}</option>
			{% endif %}
	</select>
				</div>
				<div id="edit-menu-stickie">

					<p>{{ locale.home_warning_if_you_click_remove_the_note_will_be_permanently_deleted }}</p>
				</div>
				<div id="rating-edit-menu">
					<input type="button" id="ratings-reset-link" 
						value="{{ locale.home_reset_rating }}" />
				</div>
				<div id="highscorelist-edit-menu" style="display:none">
					<select id="highscorelist-game">
						<option value="">{{ locale.home_select_game }}</option>
												<option value="1">{{ locale.home_battle_ball_rebound }}</option>
						<option value="2">{{ locale.home_snowstorm }}</option>
						<option value="0">{{ locale.home_wobble_squabble }}</option>
					</select>
				</div>
				<div id="edit-menu-remove-group-warning">
					<p>{{ locale.home_this_item_belongs_to_another_user_if_you_remove_it_it_will_return_to_their_inventory }}</p>

				</div>
				<div id="edit-menu-gb-availability">
					<select id="guestbook-privacy-options">
						<option value="private"{% if (editMode == true) and (guestbookSetting == "private") %} selected{% endif %}>{{ locale.home_friends_only }}</option>
						<option value="public"{% if (editMode == true) and (guestbookSetting == "public") %} selected{% endif %}>{{ locale.home_public }}</option>
					</select>
				</div>
				<div id="edit-menu-trax-select">

					<select id="trax-select-options"></select>
				</div>
				<div id="edit-menu-remove">
					<input type="button" id="edit-menu-remove-button" value="{{ locale.home_remove }}" />
				</div>
			</form>
			<div class="clear"></div>
		</div>
	</div>

	<div class="menu-bottom"></div>
</div>
<script language="JavaScript" type="text/javascript">
Event.observe(window, "resize", function() { if (editMenuOpen) closeEditMenu(); }, false);
Event.observe(document, "click", function() { if (editMenuOpen) closeEditMenu(); }, false);
Event.observe("edit-menu", "click", Event.stop, false);
Event.observe("edit-menu-exit", "click", function() { closeEditMenu(); }, false);
Event.observe("edit-menu-remove-button", "click", handleEditRemove, false);
Event.observe("edit-menu-skins-select", "click", Event.stop, false);
Event.observe("edit-menu-skins-select", "change", handleEditSkinChange, false);
Event.observe("guestbook-privacy-options", "click", Event.stop, false);
Event.observe("guestbook-privacy-options", "change", handleGuestbookPrivacySettings, false);
Event.observe("trax-select-options", "click", Event.stop, false);
Event.observe("trax-select-options", "change", handleTraxplayerTrackChange, false);
</script>

{% else %}

<div class="cbb topdialog" id="guestbook-form-dialog">
	<h2 class="title dialog-handle">{{ locale.home_edit_guestbook_entry }}</h2>
	
	<a class="topdialog-exit" href="#" id="guestbook-form-dialog-exit">X</a>
	<div class="topdialog-body" id="guestbook-form-dialog-body">

<div id="guestbook-form-tab">
<form method="post" id="guestbook-form">
    <p>
        {{ locale.home_note_the_message_length_must_not_exceed_two_zero_zero_characters }}        <input type="hidden" name="ownerId" value="1" />
	</p>
	<div>
	    <textarea cols="15" rows="5" name="message" id="guestbook-message"></textarea>
    <script type="text/javascript">
        bbcodeToolbar = new Control.TextArea.ToolBar.BBCode("guestbook-message");
        bbcodeToolbar.toolbar.toolbar.id = "bbcode_toolbar";
		        var colors = { "red" : ["#d80000", "Red"],
            "orange" : ["#fe6301", "Orange"],
            "yellow" : ["#ffce00", "Yellow"],
            "green" : ["#6cc800", "Green"],
            "cyan" : ["#00c6c4", "Cyan"],
            "blue" : ["#0070d7", "Blue"],
            "gray" : ["#828282", "Gray"],
            "black" : ["#000000", "Black"]
        };
        bbcodeToolbar.addColorSelect("Color", colors, true);
    </script>

<div id="linktool">
    <div id="linktool-scope">
        <label for="linktool-query-input">{{ locale.home_create_link }}</label>
        <input type="radio" name="scope" class="linktool-scope" value="1" checked="checked"/>{{ locale.home_habbos }}        <input type="radio" name="scope" class="linktool-scope" value="2"/>{{ locale.home_rooms }}        <input type="radio" name="scope" class="linktool-scope" value="3"/>{{ locale.home_groups }}    </div>
    <input id="linktool-query" type="text" name="query" value=""/>
    <a href="#" class="new-button" id="linktool-find"><b>{{ locale.home_find }}</b><i></i></a>
    <div class="clear" style="height: 0;"><!-- --></div>

    <div id="linktool-results" style="display: none">
    </div>
    <script type="text/javascript">
        linkTool = new LinkTool(bbcodeToolbar.textarea);
    </script>
</div>
    </div>

	<div class="guestbook-toolbar clearfix">
		<a href="#" class="new-button" id="guestbook-form-cancel"><b>{{ locale.home_cancel }}</b><i></i></a>
		<a href="#" class="new-button" id="guestbook-form-preview"><b>{{ locale.home_preview }}</b><i></i></a>	
	</div>

</form>
</div>
<div id="guestbook-preview-tab">&nbsp;</div>
	</div>
</div>	
<div class="cbb topdialog" id="guestbook-delete-dialog">
	<h2 class="title dialog-handle">{{ locale.home_delete_entry }}</h2>
	
	<a class="topdialog-exit" href="#" id="guestbook-delete-dialog-exit">X</a>
	<div class="topdialog-body" id="guestbook-delete-dialog-body">
<form method="post" id="guestbook-delete-form">
	<input type="hidden" name="entryId" id="guestbook-delete-id" value="" />

	<p>{{ locale.home_are_you_sure_you_want_to_delete_this_entry }}</p>
	<p>
		<a href="#" id="guestbook-delete-cancel" class="new-button"><b>{{ locale.home_cancel }}</b><i></i></a>
		<a href="#" id="guestbook-delete" class="new-button"><b>{{ locale.home_delete }}</b><i></i></a>
	</p>
</form>
	</div>
</div>

<div class="cbb topdialog black" id="dialog-group-settings">
	
	<div class="box-tabs-container">
<ul class="box-tabs">
	<li class="selected" id="group-settings-link-group"><a href="#">{{ locale.home_group_settings }}</a><span class="tab-spacer"></span></li>
	<li id="group-settings-link-forum"><a href="#">{{ locale.home_forum_settings }}</a><span class="tab-spacer"></span></li>
	<li id="group-settings-link-room"><a href="#">{{ locale.home_room_settings }}</a><span class="tab-spacer"></span></li>
</ul>
</div>

	<a class="topdialog-exit" href="#" id="dialog-group-settings-exit">X</a>
	<div class="topdialog-body" id="dialog-group-settings-body">
<p style="text-align:center"><img src="{{ site.staticContentPath }}/web-gallery/images/progress_bubbles.gif" alt="" width="29" height="6" /></p>
	</div>
</div>	

<script language="JavaScript" type="text/javascript">
Event.observe("dialog-group-settings-exit", "click", function(e) {
    Event.stop(e);
    closeGroupSettings();
}, false);
</script><div class="cbb topdialog black" id="group-memberlist">
	
	<div class="box-tabs-container">
<ul class="box-tabs">
	<li class="selected" id="group-memberlist-link-members"><a href="#">{{ locale.home_members }}</a><span class="tab-spacer"></span></li>
	<li id="group-memberlist-link-pending"><a href="#">{{ locale.home_pending_members }}</a><span class="tab-spacer"></span></li>
</ul>
</div>

	<a class="topdialog-exit" href="#" id="group-memberlist-exit">X</a>
	<div class="topdialog-body" id="group-memberlist-body">
<div id="group-memberlist-members-search" class="clearfix">
    
    <a id="group-memberlist-members-search-button" href="#" class="new-button"><b>{{ locale.home_search }}</b><i></i></a>
    <input type="text" id="group-memberlist-members-search-string"/>
</div>
<div id="group-memberlist-members" style="clear: both"></div>
<div id="group-memberlist-members-buttons" class="clearfix">
	<a href="#" class="new-button group-memberlist-button-disabled" id="group-memberlist-button-give-rights"><b>{{ locale.home_give_rights }}</b><i></i></a>
	<a href="#" class="new-button group-memberlist-button-disabled" id="group-memberlist-button-revoke-rights"><b>{{ locale.home_revoke_rights }}</b><i></i></a>
	<a href="#" class="new-button group-memberlist-button-disabled" id="group-memberlist-button-remove"><b>{{ locale.home_remove }}</b><i></i></a>
	<a href="#" class="new-button group-memberlist-button" id="group-memberlist-button-close"><b>{{ locale.home_close }}</b><i></i></a>
</div>
<div id="group-memberlist-pending" style="clear: both"></div>
<div id="group-memberlist-pending-buttons" class="clearfix">
	<a href="#" class="new-button group-memberlist-button-disabled" id="group-memberlist-button-accept"><b>{{ locale.home_accept }}</b><i></i></a>
	<a href="#" class="new-button group-memberlist-button-disabled" id="group-memberlist-button-decline"><b>{{ locale.home_reject }}</b><i></i></a>
	<a href="#" class="new-button group-memberlist-button" id="group-memberlist-button-close2"><b>{{ locale.home_close }}</b><i></i></a>
</div>
	</div>
</div>


{% endif %}



<script type="text/javascript">
HabboView.run();
</script>


</body>
</html>