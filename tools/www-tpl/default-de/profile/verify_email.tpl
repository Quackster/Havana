
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="de" lang="de">
<head>
	<meta http-equiv="content-type" content="text/html" />
	<title>{{ site.siteName }}: Email bestätigen </title>

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


<meta name="description" content="Join the world's largest virtual hangout where you can meet and make friends. Design your own rooms, collect cool furniture, throw parties and so much more! Create your FREE {{ site.siteName }} today!" />
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
				<a href="{{ site.sitePath }}/home/{{ playerDetails.getName() }}">Meine Email</a>    		</li>
    		<li class="selected">
				Account Einstellungen  		</li>
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

<h2 class="title">Account Settings</h2>
<div class="box-content">
            <div id="settingsNavigation">
            <ul>
				<li><a href="{{ site.sitePath }}/profile?tab=1">Meine Kleidung</a>
                </li><li><a href="{{ site.sitePath }}/profile?tab=2">Meine Optionen</a>
                </li><li class="selected">{% if accountActivated %}Meine Email{% else %}ändern{% endif %}
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

            <h2 class="title">Ändere und verifiziere deine Email</h2>
            <div class="box-content">

{% if alert.hasAlert %}
<div class="rounded rounded-{{ alert.colour }}">{{ alert.message }}<br />	</div><br />
{% endif %}

<form action="{{ site.sitePath }}/profile/send_email" method="post" id="emailform">
<input type="hidden" name="tab" value="3" />
<input type="hidden" name="__app_key" value="HavanaWeb" />

<div class="settings-step">

	<h4>Du hast noch keine bestätigte Email!</h4><br />
	<p>Wie aktiviere und bestätige ich meine E-Mail? Klicke auf die Schaltfläche und es wir eine Bestätigungs-Email an deine Email gesendet. Klicke auf den Link in der Email und fertig! Klicke auf die Schaltfläche unten, wenn du eine weitere Aaktivierungsnachricht erhalten möchtest.</p>
	<div class="settings-buttons">
		<a href="#" class="new-button" style="display: none" id="emailform-submit"><b>Email bestätigen</b><i></i></a>
		<noscript><input type="submit" value="Activate my email-address" name="save" class="submit" /></noscript>
	</div>
</div>
<br /><br /><hr>
<div class="settings-step">
	<p>Wir haben festgestellt, dass du dich mit deinem Habbo-Konto angemeldet hast. Um deine E-Mail-Adresse zu ändern, musst du die Seite mit den Kontoverwaltungseinstellungen aufrufen. Klicke auf den untenstehenden Link, dann wirst du auf die Seite weitergeleitet.</p>
	<p><a href="{{ site.sitePath }}/profile?tab=3">Email ändern</a></p>
</div>
</div>
</div>
</form>

<script type="text/javascript">
$("emailform-submit").observe("click", function(e) { e.stop(); $("emailform").submit(); });
$("emailform-submit").show();
</script>
<script type="text/javascript">
$("confirmform-texts").hide();
</script>                
</div></div></div></div>
</div>

<script type="text/javascript">
HabboView.run();

</script>


</body>
</html>