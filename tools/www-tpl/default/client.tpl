<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="{{ locale.global_html_lang }}" lang="{{ locale.global_html_lang }}">
<head>
	<meta http-equiv="content-type" content="text/html" />
	<title>{{ site.siteName }}:  </title>

<script type="text/javascript">
var andSoItBegins = (new Date()).getTime();
</script>
    <link rel="shortcut icon" href="{{ site.staticContentPath }}/web-gallery/v2/favicon.ico" type="image/vnd.microsoft.icon" />
    <link rel="alternate" type="application/rss+xml" title="{{ site.siteName }}{{ locale.client_rss }}" href="{{ site.sitePath }}/articles/rss.xml" />
<script src="{{ site.staticContentPath }}/web-gallery/static/js/libs2.js" type="text/javascript"></script>
<script src="{{ site.staticContentPath }}/web-gallery/static/js/visual.js" type="text/javascript"></script>
<script src="{{ site.staticContentPath }}/web-gallery/static/js/libs.js" type="text/javascript"></script>
<script src="{{ site.staticContentPath }}/web-gallery/static/js/common.js" type="text/javascript"></script>
<script src="{{ site.staticContentPath }}/web-gallery/static/js/fullcontent.js" type="text/javascript"></script>
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/style.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/buttons.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/boxes.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/tooltips.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/habboclient.css" type="text/css" />
<script src="{{ site.staticContentPath }}/web-gallery/static/js/habboclient.js" type="text/javascript"></script>

<script type="text/javascript">
document.habboLoggedIn = {{ session.loggedIn }};
var habboName = "{{ locale.client_session_loggedin_playerdetails_getname|escape('js') }} "" }}";
var ad_keywords = "";
var habboReqPath = "{{ site.sitePath }}";
var habboStaticFilePath = "{{ site.staticContentPath }}/web-gallery";
var habboImagerUrl = "{{ site.staticContentPath }}/habbo-imaging/";
var habboPartner = "";
window.name = "client";
if (typeof HabboClient != "undefined") { HabboClient.windowName = "client"; }

</script>

<script type="text/javascript">
var habboClient = true;
HabboClientUtils.init({remoteCallsEnabled : true, taggingGameEnabled : false});
ffec.c("chrome://havvocmini/content/HavvocIcon.PNG");
log(4);
</script>

<meta name="description" content="{{ locale.client_join_the_world_s_largest_virtual_hangout_where_you_can_meet_and_make_friends_design_your_own_rooms_collect_cool_furniture_throw_parties_and_so_much_more_create_your_free }} {{ site.siteName }} {{ locale.client_today }}" />
<meta name="keywords" content="{{ site.siteName }}{{ locale.client_virtual_world_join_groups_forums_play_games_online_friends_teens_collecting_social_network_create_collect_connect_furniture_virtual_goods_sharing_badges_social_networking_hangout_safe_music_celebrity_celebrity_visits_cele }}" />

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
<meta name="build" content="{{ locale.client_havanaweb }}" />
</head>
<body id="client" class="wide">

<div id="client-topbar" style="display:none">

  <div class="logo"><img src="{{ site.staticContentPath }}/web-gallery/images/popup/popup_topbar_habbologo.gif" alt="" align="middle"/></div>
  <div class="habbocount"><div id="habboCountUpdateTarget">
{{ site.formattedUsersOnline }} {{ locale.client_members_online }}</div>
	<script language="JavaScript" type="text/javascript">
		setTimeout(function() {
			HabboCounter.init(600);
		}, 20000);
	</script>
</div>
  <div class="logout"><a href="{{ site.sitePath }}/account/logout?origin=popup" onclick="self.close(); return false;">{{ locale.client_close_hotel }}</a></div>
</div>
<noscript>
  <img src="{{ site.sitePath }}/clientlog/nojs" border="0" width="1" height="1" alt="" style="position: absolute; top:0; left: 0"/>
</noscript>

<div id="clientembed-container">
<div id="clientembed-loader" class="loader-image" style="display:none">
    <div class="loader-image-inner">
        <b class="loading-text">{{ locale.client_loading }} {{ site.siteName }}...</b>
    </div>
</div>
<div id="clientembed">
<script type="text/javascript" language="javascript">
	HabboClientUtils.extWrite("{{ locale.client_object_classid_clsid_one_six_six_b_one_bca_three_f_nine_c_one_one_cf_eight_zero_seven_five_four_four_four_five_five_three_five_four_zero_zero_zero_zero_codebase_http_download_macromedia_com_pub_shockwave_cabs_director_sw_cab_version_one_zero_zero_zero_zero_id_theme_width_nine_six_zero_height_five_four_zero_n_param_name_src_value|escape('js') }}{{ site.loaderDcr }}{{ locale.client_n_param_name_swremote_value_swsaveenabled_true_swvolume_true_swrestart_false_swpauseplay_false_swfastforward_false_swtitle_themehotel_swcontextmenu_true_n_param_name_swstretchstyle_value_stage_n_param_name_swtext_value_n_param_name_bgcolor_value_zero_zero_zero_zero_zero_zero_n_param_name_sw_six_value_client_connection_failed_url|escape('js') }}{{ site.sitePath }}{{ locale.client_clientutils_php_key_connection_failed_external_variables_txt|escape('js') }}{{ site.loaderVariables }}country={{ preferredCountry }}\"\>\n  {{ forwardScript }}{{ locale.client_n_param_name_sw_eight_value_use_sso_ticket_one_sso_ticket|escape('js') }}{{ ssoTicket }}{{ locale.client_n_param_name_sw_two_value_connection_info_host|escape('js') }}{{ site.loaderGameIp }};connection.info.port={{ site.loaderGamePort }}{{ locale.client_n_param_name_sw_nine_value|escape('js') }}{{ shortcut }}account_id={{ playerDetails.id }}{{ locale.client_n_param_name_sw_four_value_site_url|escape('js') }}{{ site.sitePath }};url.prefix={{ site.sitePath }}{{ locale.client_n_param_name_sw_three_value_connection_mus_host|escape('js') }}{{ site.loaderMusIp }};connection.mus.port={{ site.loaderMusPort }}{{ locale.client_n_param_name_sw_one_value_client_allow_cross_domain_one_client_notify_cross_domain_zero_n_param_name_sw_seven_value_external_texts_txt|escape('js') }}{{ site.loaderTexts }}{{ locale.client_n_param_name_sw_five_value_client_reload_url|escape('js') }}{{ site.sitePath }}{{ locale.client_client_php_x_reauthenticate_client_fatal_error_url|escape('js') }}{{ site.sitePath }}{{ locale.client_clientutils_php_key_error_n_embed_src|escape('js') }}{{ site.loaderDcr }}{{ locale.client_bgcolor_zero_zero_zero_zero_zero_zero_width_nine_six_zero_height_five_four_zero_swremote_swsaveenabled_true_swvolume_true_swrestart_false_swpauseplay_false_swfastforward_false_swtitle_habbo_hotel_swcontextmenu_true_swstretchstyle_stage_swtext_type_application_x_director_pluginspage_http_www_macromedia_com_shockwave_download_n_sw_six_client_connection_failed_url|escape('js') }}{{ site.sitePath }}{{ locale.client_clientutils_php_key_connection_failed_external_variables_txt|escape('js') }}{{ site.loaderVariables }}country={{ preferredCountry }}\"\n  {{ forwardSubScript }} {{ locale.client_n_sw_eight_use_sso_ticket_one_sso_ticket|escape('js') }}{{ ssoTicket }}{{ locale.client_n_sw_two_connection_info_host|escape('js') }}{{ site.loaderGameIp }};connection.info.port={{ site.loaderGamePort }}{{ locale.client_n_sw_nine|escape('js') }}{{ shortcut }}account_id={{ playerDetails.id }}{{ locale.client_n_sw_four_site_url|escape('js') }}{{ site.sitePath }}\;url.prefix={{ site.sitePath }}{{ locale.client_n_sw_three_connection_mus_host|escape('js') }}{{ site.loaderMusIp }};connection.mus.port={{ site.loaderMusPort }}{{ locale.client_n_sw_one_client_allow_cross_domain_one_client_notify_cross_domain_zero_n_sw_seven_external_texts_txt|escape('js') }}{{ site.loaderTexts }}{{ locale.client_n_n_sw_five_client_reload_url|escape('js') }}{{ site.sitePath }}{{ locale.client_client_php_x_reauthenticate_client_fatal_error_url_text|escape('js') }}{{ site.sitePath }}{{ locale.client_clientutils_php_key_error_embed_n_noembed_client_pluginerror_message_noembed_n_object|escape('js') }}");
</script>
<noscript>
<object classid="clsid:166B1BCA-3F9C-11CF-8075-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/director/sw.cab#version=10,0,0,0" id="Theme" width="960" height="540">
<param name="src" value="{{ site.loaderDcr }}">
<param name="swRemote" value="swSaveEnabled='true' swVolume='true' swRestart='false' swPausePlay='false' swFastForward='false' swTitle='Themehotel' swContextMenu='true' ">
<param name="swStretchStyle" value="stage">
<param name="swText" value="">
<param name="bgColor" value="#000000">
   <param name="sw6" value="client.connection.failed.url={{ site.sitePath }}/clientutils.php?key=connection_failed;external.variables.txt={{ site.loaderVariables }}country={{ preferredCountry }}">
   {{ forward }}
   <param name="sw9" value="{{ shortcut }}account_id={{ playerDetails.id }}">
   <param name="sw8" value="use.sso.ticket=1;sso.ticket={{ ssoTicket }}">
   <param name="sw2" value="connection.info.host={{ site.loaderGameIp }};connection.info.port={{ site.loaderGamePort }}">
   <param name="sw4" value="site.url={{ site.sitePath }};url.prefix={{ site.sitePath }}">
   <param name="sw3" value="connection.mus.host={{ site.loaderMusIp }};connection.mus.port={{ site.loaderMusPort }}">
   <param name="sw1" value="client.allow.cross.domain=1;client.notify.cross.domain=0">
   <param name="sw7" value="external.texts.txt={{ site.loaderTexts }}">
   <param name="sw5" value="client.reload.url={{ site.sitePath }}/client.php?x=reauthenticate;client.fatal.error.url={{ site.sitePath }}/clientutils.php?key=error">
<!--[if IE]>client.pluginerror.message<![endif]-->
<embed src="{{ site.loaderDcr }}" bgColor="#000000" width="960" height="540" swRemote="swSaveEnabled='true' swVolume='true' swRestart='false' swPausePlay='false' swFastForward='false' swTitle='Themehotel' swContextMenu='true'" swStretchStyle="stage" swText="" type="application/x-director" pluginspage="http://www.macromedia.com/shockwave/download/"
    {{ forwardSub }}
    sw6="client.connection.failed.url={{ site.sitePath }}/clientutils.php?key=connection_failed;external.variables.txt={{ site.loaderVariables }}country={{ preferredCountry }}"
	sw9="{{ shortcut }}account_id={{ playerDetails.id }}"
    sw8="use.sso.ticket=1;sso.ticket={{ ssoTicket }}"
    sw2="connection.info.host={{ site.loaderGameIp }};connection.info.port={{ site.loaderGamePort }}"
    sw4="site.url={{ site.sitePath }};url.prefix={{ site.sitePath }}"
    sw3="connection.mus.host={{ site.loaderMusIp }};connection.mus.port={{ site.loaderMusPort }}"
    sw1="client.allow.cross.domain=1;client.notify.cross.domain=0"
    sw7="external.texts.txt={{ site.loaderTexts }}"
	    sw5="client.reload.url={{ site.sitePath }}/client.php?x=reauthenticate;client.fatal.error.url={{ site.sitePath }}/clientutils.php?key=error" ></embed>
<noembed>client.pluginerror.message</noembed>
</object>
</noscript>

</div>
<script type="text/javascript">
HabboClientUtils.loaderTimeout = 10 * 1000;
HabboClientUtils.showLoader(["{{ locale.client_loading|escape('js') }} {{ site.siteName }}", "{{ locale.client_loading|escape('js') }} {{ site.siteName }}.", "{{ locale.client_loading|escape('js') }} {{ site.siteName }}..", "{{ locale.client_loading|escape('js') }} {{ site.siteName }}..."]);
</script>

<script type="text/javascript" language="javascript">
try {
var _shockwaveDetectionSuccessful = true;
_shockwaveDetectionSuccessful = ShockwaveInstallation.swDetectionCheck();
if (!_shockwaveDetectionSuccessful) {
    log(50);
}
if (_shockwaveDetectionSuccessful) {
  HabboClientUtils.cacheCheck();
}
} catch(e) {
    try {
		HabboClientUtils.logClientJavascriptError(e);
	} catch(e2) {}
}
</script>

</body>
</html>