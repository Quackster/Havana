
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="de" lang="de">
<head>
	<meta http-equiv="content-type" content="text/html" />
	<title>{{ site.siteName }}: Meine Optionen </title>

<script type="text/javascript">
var andSoItBegins = (new Date()).getTime();
</script>
    <link rel="shortcut icon" href="{{ site.staticContentPath }}/web-gallery/v2/favicon.ico" type="image/vnd.microsoft.icon" />
    <link rel="alternate" type="application/rss+xml" title="{{ site.siteName }}: RSS" href="{{ site.sitePath }}/articles/rss.xml" />
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
var habboName = "{{ session.loggedIn ? playerDetails.getName() : "" }}";
var ad_keywords = "";
var habboReqPath = "{{ site.sitePath }}";
var habboStaticFilePath = "{{ site.staticContentPath }}/web-gallery";
var habboImagerUrl = "{{ site.staticContentPath }}/habbo-imaging/";
var habboPartner = "";
window.name = "habboMain";
if (typeof HabboClient != "undefined") { HabboClient.windowName = "client"; }

</script>

<script src="{{ site.staticContentPath }}/web-gallery/static/js/settings.js" type="text/javascript"></script>
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/settings.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/friendmanagement.css" type="text/css" />


<meta name="description" content="Trete dem weltweit größten virtuellen Treffpunkt bei, an dem du Freunde treffen und finden kannst. Gestalte deine eigenen Räume, sammel coole Möbel, veranstalte Partys und vieles mehr! Erstelle dein kostenloses {{ site.siteName }} Konto noch heute!" />
<meta name="keywords" content="{{ site.siteName }}, virtuell, Welt, beitreten, Gruppen, Foren, spielen, Spiele, online, Freunde, Teenager, Sammeln, soziales Netzwerk, erstellen, verbinden, Möbel, virtuell, Waren, teilen, Abzeichen, sozial, Vernetzung, Treffpunkt, Safe, Musik, Berühmtheit, Promi-Besuche" />

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
<meta name="build" content="HavanaWeb" />
</head>

{% include "../base/header.tpl" %}

<div id="content-container">

	<div id="navi2-container" class="pngbg">
    <div id="navi2" class="pngbg clearfix">
		<ul>
			<li class="">
				<a href="{{ site.sitePath }}/me">Start</a>			</li>
    		<li class="">
				<a href="{{ site.sitePath }}/home/{{ playerDetails.getName() }}">Meine Seite</a>    		</li>
    		<li class="selected">
				Einstellungen    		</li>
			<li class=" last">
				<a href="{{ site.sitePath }}/club">{{ site.siteName }} Club</a>
			</li>
		</ul>
    </div>
</div>
	
<div id="container">
	<div id="content" style="position: relative" class="clearfix">
    <div>
<div class="content">
<div class="habblet-container" style="float:left; width:210px;">
<div class="cbb settings">

<h2 class="title">Account-Optionen</h2>
<div class="box-content">
            <div id="settingsNavigation">
            <ul>
				<li><a href="{{ site.sitePath }}/profile?tab=1">Meine Kleidung</a>
                </li><li class="selected">Meine Optionen
				{% if accountActivated %}
                </li><li><a href="{{ site.sitePath }}/profile?tab=3">Meine Email</a>
				{% else %}
				</li><li><a href="{{ site.sitePath }}/profile/verify">Email ändern</a>
				{% endif %}
                </li><li><a href="{{ site.sitePath }}/profile?tab=4">Mein Passwort</a>
                </li><li><a href="{{ site.sitePath }}/profile?tab=5">Freundesliste</a>
								</li><li><a href="{{ site.sitePath }}/profile?tab=6">Tauschoptionen</a>
                </li>            </ul>
            </div>
</div></div>
    {% include "profile/profile_widgets/join_club.tpl" %}
</div>
    <div class="habblet-container " style="float:left; width: 560px;">
        <div class="cbb clearfix settings">

            <h2 class="title">Änder dein Profil</h2>
            <div class="box-content">

{% if settingsSavedAlert %}
<div class="rounded rounded-green">Kontoeinstellungen erfolgreich aktualisiert.<br />	</div><br />
{% endif %}

<form action="{{ site.sitePath }}/profile/profileupdate" method="post" id="profileForm">
<input type="hidden" name="tab" value="2" />
<input type="hidden" name="__app_key" value="HavanaWeb" />


<h3>Dein Status</h3>

<p>
Dein Status ist das, was andere {{ site.siteName }} auf deiner {{ site.siteName }}-Homepage und unter deinem {{ site.siteName }} im Hotel sehen.</p>
{% autoescape 'html' %}
<p>
<span class="label">Status:</span>
<input type="text" name="motto" size="32" maxlength="32" value="{{ playerDetails.motto }}" id="avatarmotto" />
</p>
{% endautoescape %}

<h3>Client Optionen</h3>

<p>
Wähle das zu verwendende Plugin für das Beitreten des Hotels aus.<br />
<label><input type="radio" name="clientpreference" value="SHOCKWAVE" {{ SHOCKWAVEenabled }} />Shockwave</label>

<label><input type="radio" name="clientpreference" value="FLASH" {{ FLASHenabled }} />Flash</label>
</p>

<h3>Deine Seite</h3>

<p>
Wer kann deine Seite sehen?<br />
<label><input type="radio" name="visibility" value="EVERYONE" {{ profileVisibleEnabled }} />Jeder</label>

<label><input type="radio" name="visibility" value="NOBODY" {{ profileVisibleDisabled }} />Niemand</label>
</p>

<!-- <h3>Email Alarme</h3>

<p>
<label><input type="checkbox" name="emailMiniMailAlertEnabled" value="true" checked="checked" />Erhalte Benachrichtigungen zu Minimail-Nachrichten.</label> <br />
<label><input type="checkbox" name="emailFriendRequestAlertEnabled" value="true" checked="checked" />Erhalte Benachrichtigungen zu Freundschaftsanfragen.</label>
</p>
-->
<h3>Wortfilter</h3>
<p>
<label><input type="checkbox" name="wordFilterSetting" value="false" {{ wordFilterSetting }}> Wortfilter ausschalten</label>
</p>

<h3>Freundesanfragen</h3>
<p>
<label><input type="checkbox" name="allowFriendRequests" value="true" {{ allowFriendRequests }}> Freundesanfragen erlauben</label>
</p>

<h3>Folgende Freunde</h3>
<p>
Möglichkeit für deine Freunde, dir zwischen Räumen zu folgen:<br />
<label><input type="radio" name="followFriendSetting" value="true" {{ followFriendEnabled }} />Freunde</label>
<label><input type="radio" name="followFriendSetting" value="false" {{ followFriendDisabled }} />Niemand</label>
</p>

<h3>Online Status</h3>
<p>
Wer kann deinen Online Status sehen?<br />
<label><input type="radio" name="showOnlineStatus" value="true" {{ onlineStatusEnabled }} />Jeder</label>
<label><input type="radio" name="showOnlineStatus" value="false" {{ onlineStatusDisabled }} />Niemand</label>
</p>

<div class="settings-buttons">
<a href="#" class="new-button" style="display: none" id="profileForm-submit"><b>Änderung speichern</b><i></i></a>
<noscript><input type="submit" value="Save changes" name="save" class="submit" /></noscript>
</div>

</form>

<script type="text/javascript">
$("profileForm-submit").observe("click", function(e) { e.stop(); $("profileForm").submit(); });
$("profileForm-submit").show();

$("profileForm-genToken").observe("click", function(e) { e.stop();
	new Ajax.Request(habboReqPath + "/habblet/ajax/token_generate", {
        onComplete: function(response) {
            document.getElementById('authenticationToken').value = response.responseText;
		}
    });
});
$("profileForm-genToken").show();

</script>

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