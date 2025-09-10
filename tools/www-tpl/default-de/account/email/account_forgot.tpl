
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="de" lang="de">
<head>
	<meta http-equiv="content-type" content="text/html" />
	<title>{{ site.siteName }}: Daten vergessen </title>

<script type="text/javascript">
var andSoItBegins = (new Date()).getTime();
</script>
    <link rel="shortcut icon" href="{{ site.staticContentPath }}/web-gallery/v2/favicon.ico" type="image/vnd.microsoft.icon" />
    <link rel="alternate" type="application/rss+xml" title="{{ site.siteName }}: RSS" href="{{ site.sitePath }}/articles/rss.xml" />

<script src="{{ site.staticContentPath }}/web-gallery/static/js/libs2.js" type="text/javascript"></script>
<script src="{{ site.staticContentPath }}/web-gallery/static/js/landing.js" type="text/javascript"></script>
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/style.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/buttons.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/boxes.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/tooltips.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/styles/local/com.css" type="text/css" />

<script src="{{ site.staticContentPath }}/web-gallery/js/local/com.js" type="text/javascript"></script>
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/process.css" type="text/css" />

<script type="text/javascript">
document.habboLoggedIn = false;
var habboName = null;
var ad_keywords = "";
var habboReqPath = "{{ site.sitePath }}";
var habboStaticFilePath = "{{ site.staticContentPath }}/web-gallery";
var habboImagerUrl = "{{ site.staticContentPath }}/habbo-imaging/";
var habboPartner = "";
var habboDefaultClientPopupUrl = "{{ site.sitePath }}/client";
window.name = "habboMain";
if (typeof HabboClient != "undefined") { HabboClient.windowName = "client"; }

</script>


<meta name="description" content="Trete dem weltweit größten virtuellen Treffpunkt bei, an dem du Freunde treffen und finden kannst. Gestalte deine eigenen Räume, sammel coole Möbel, veranstalte Partys und vieles mehr! Erstelle dein kostenloses {{ site.siteName }} Konto noch heute!" />
<meta name="keywords" content="Habbo, virtuell, Welt, beitreten, Gruppen, Foren, spielen, Spiele, online, Freunde, Teenager, Sammeln, soziales Netzwerk, erstellen, verbinden, Möbel, virtuell, Waren, teilen, Abzeichen, sozial, Vernetzung, Treffpunkt, Safe, Musik, Berühmtheit, Promi-Besuche" />

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
<body  class="process-template">

<div id="overlay"></div>

<div id="container">
	<div class="cbb process-template-box clearfix">
		<div id="content">
			{% include "../../base/frontpage_header.tpl" %}
			<div id="process-content">
<style type="text/css">
		div.left-column { float: left; width: 50% }
		div.right-column { float: right; width: 49% }
		label { display: block }
		input { width: 98% }
		input.process-button { width: auto; float: right }
	</style>

			<div id="process-content">
	        	<div class="left-column">
<div class="cbb clearfix">
    <h2 class="title">Passwort vergessen?</h2>
    <div class="box-content">
		{% if invalidForgetPassword %}
		<div class="rounded rounded-red">
                Ungültiger Benutzername oder ungültige E-Mail <br />
        </div>
        <div class="clear"></div>
		{% endif %}
		{% if validForgetPassword %}
		<div class="rounded rounded-green">
                Eine E-Mail mit Wiederherstellungsdetails wurde gesendet <br />
        </div>
        <div class="clear"></div>
		{% endif %}
        <p>Keine Panik! Bitte gebe unten deine Kontoinformationen ein. Wir senden dir dann eine E-Mail mit Informationen zum Zurücksetzen deines Passworts.</p>

        <div class="clear"></div>

        <form method="post" action="forgot" id="forgottenpw-form">
            <p>
            <label for="forgottenpw-username">Benutzername</label>
            <input type="text" name="forgottenpw-username" id="forgottenpw-username" value="" />
            </p>

            <p>
            <label for="forgottenpw-email">Email Adresse</label>
            <input type="text" name="forgottenpw-email" id="forgottenpw-email" value="" />
            </p>

            <p>
            <input type="submit" value="Request password email" name="actionForgot" class="submit process-button" id="forgottenpw-submit" />
            </p>
            <input type="hidden" value="default" name="origin" />
        </form>
    </div>
</div>

</div>


<div class="right-column">

<div class="cbb clearfix">
    <h2 class="title">Habboname vergessen?</h2>
    <div class="box-content">
		{% if invalidForgetName %}
		<div class="rounded rounded-red">
                Ungültiger Benutzername oder ungültige E-Mail <br />
        </div>
        <div class="clear"></div>
		{% endif %}
		{% if validForgetName %}
		<div class="rounded rounded-green">
                Eine E-Mail mit Wiederherstellungsdetails wurde gesendet <br />
        </div>
        <div class="clear"></div>
		{% endif %}
        <p>Kein Problem – gebe unten einfach deine E-Mail-Adresse ein und wir senden dir eine Liste deiner Konten.</p>

        <div class="clear"></div>

        <form method="post" action="forgot" id="accountlist-form">
            <p>

            <label for="accountlist-owner-email">Email Adresse</label>
            <input type="text" name="ownerEmailAddress" id="accountlist-owner-email" value="" />
            </p>

            <p>
            <input type="submit" value="Get my accounts" name="actionList" class="submit process-button" id="accountlist-submit" />
            </p>
            <input type="hidden" value="default" name="origin" />
        </form>

    </div>
</div>

<div class="cbb clearfix">
    <h2 class="title">Falscher Alarm!</h2>
    <div class="box-content">
        <p>Wenn du dich an dein Passwort erinnerst oder nur zufällig hierher gekommen bist, klicke auf den Link unten, um zur Homepage zurückzukehren.</p>
        <p><a href="{{ site.sitePath }}">Zurück zur Homepage &raquo;</a></p>
    </div>
</div>

</div>


<!--[if lt IE 7]>
<script type="text/javascript">
Pngfix.doPngImageFix();
</script>
<![endif]-->

{% include "../../base/footer.tpl" %}

<script type="text/javascript">
HabboView.run();
</script>


</body>
</html>