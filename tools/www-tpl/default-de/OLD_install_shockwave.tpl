
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="de" lang="de">
<head>
	<meta http-equiv="content-type" content="text/html" />
	<title>{{ site.siteName }}: Shockwave Hilfe</title>

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

<style>
.tutorial-text {
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
				Shockwave Hilfe</li>
			<li>
				<a href="{{ site.sitePath }}/help/shockwave_app">Portabler Client</a></li>
			<li class=" last">
				<a href="{{ site.sitePath }}/help/1">FAQ</a>
			</li>
		</ul>
    </div>
</div>

<div id="container">
	<div id="content" style="position: relative" class="clearfix">
		<div id="column1" class="column" style="width: 50%">
			<div class="habblet-container ">		
				<div class="cbb clearfix blue ">
					<h2 class="title">So verwendest du Shockwave</h2>
					<div class="tutorial-text">	
						<p style="margin-top: 10px">In order to load the Shockwave hotel, you must follow these steps and ensure you have the prequisities required.</p>
						<p><b>Pale Moon</b></p>
						<p>Pale Moon ist eine Notwendigkeit um Shockwave korrekt auszuführen, da es einer der wenigen Browser ist, der NPAPI-Plugins noch korrekt unterstützt.</p>
						<p>Da Shockwave recht alt ist, wird die <b>32-Bit</b>-Version von Pale Moon benötigt, hier kannst du die <a href="https://www.palemoon.org/download.shtml#Portable_versions">Portable Version</a> oder die <a href="https://www.palemoon.org/download.shtml">Vollversion</a> downloaden.</p>
						<p><b>Shockwave 12</b></p>
						<p>Du musst zuerst Shockwave 12 MSI installieren und dann mit der Installation von Visual Studio 2008 C++ x86 Redist fortfahren..</p>
						<p><i>Download liste</i></p>
						<p>Adobe Shockwave 12.3 MSI: <a href="https://alex-dev.org/shockwave/12.3/sw_lic_full_installer.msi" target="_blank">Download</a></p>
						<p>Microsoft Visual C++ 2008 Redistributable Package (x86): <a href="https://www.microsoft.com/en-au/download/details.aspx?id=29" target="_blank">Download</a></p>
						<p>Stelle außerdem sicher, dass bei der Installation von Shockwave MSI <b>kein Browser geöffnet ist</b>, da die aktuelle Installation bei geöffnetem Browser sonst abbricht.</p>
						<p><b>Shockwave 11</b></p>
						<p>Obwohl Shockwave 11.6 älter ist als die Version Shockwave 12, kommt es bei der neuesten Version zu Absturzproblemen beim Abspielen von Musik von der Traxmaschine oder Jukebox und außerdem bleiben Nachrichten im Instant-Messenger immer bei 12:00 Uhr hängen.</p>
						<p>Aus diesem Grund wird die Installation von Shockwave 11 empfohlen, da hier die Probleme Version nicht auftreten.</p>
						<p>Du kannst das offizielle Shockwave 11.6-Installer MSI <a href="https://alex-dev.org/shockwave/11.6/sw_lic_full_installer.msi">Hier</a> downloaden.</p>
						
						<!-- <p><b>Shockwave 12</b></p>
						<p>Wenn die Schritte von Shockwave 11 nicht funktionieren, gibt es noch die Möglichkeit, Shockwave 12 zu installieren. Diese Version bringt jedoch die o.g. Probleme mit sich.</p>
						<p>Du kannst das offizielle Shockwave 12 Full-Installationsprogramm <a href="https://www.palemoon.org/download.shtml">Hier</a> herunterladen.</p>
						<p>Bitte lade die <a href="">Xtras</a> herunter und extrahiere diese Ordner nach <i>C:/Windows</i>. Diese wenden die Shockwave-Dateien an, die sonst automatisch heruntergeladen würden..</p>
						<p></p> -->
					</div>
				</div>

			</div>
			<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
			<div class="habblet-container ">		
				<div class="cbb clearfix red ">
					<h2 class="title">Warum sollte ich Shockwave nutzen?</h2>
					<div class="tutorial-text">	
						<p style="margin-top: 10px">Derzeit gibt es zwei Clients, auf denen das Hotel gespielt werden kann. Der erste ist das Shockwave-Hotel und der zweite ist das Flash-Hotel.</p>
						<p>Es wird dringend empfohlen die Shockwave-Version zu spielen, da sie weitaus mehr Funktionen bietet, die auf dem Flash-Client leider nicht verfügbar sind..</p>
						<p>Die in Shockwave enthaltenen Funktionen, die in der Flash-Version nicht vorhanden sind, sind unten aufgeführt..</p>
						<div class="row">
							<div class="column" style="margin-top:10px; margin-bottom:10px; margin-right:10px">
								<img src="{{ site.staticContentPath }}/c_images/stickers/sticker_submarine.gif" alt="">
							</div>
							<div class="column" style="margin-top:10px; margin-bottom:10px; margin-left:10px; width: 275px;">
								<p><b>BattleBall, Diving, Wobble Squabble, Trax Maschine, Jukeboxes, American Idol, Tic Tac Toe, Schach, Battleships, Poker</b> und einige nostalgische Habbo-Komponenten wie die Hand und der Room-o-Matic.</p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="column2" class="column" style="width: 305px">	     		
			<div class="habblet-container ">		
				<div class="cbb clearfix">
					<h2 class="title" style="background-color: darkorange">Voraussetzungen</h2>
					<div class="tutorial-text">							</h2>
						<div class="row">
							<div class="column" style="width: 100px; margin-top:10px; margin-bottom:10px; margin-right:10px">
								<img class="credits-image" src="https://i.imgur.com/6zrNiqZ.gif" alt="" width="100" height="100">
							</div>
							<div class="column" style="width: 165px;">
								<p style="margin-top: 10px">Die folgenden Elemente sind für die Verwendung von Shockwave erforderlich.</p>
								<p>Wenn du diese Voraussetzungen nicht erfüllst, kannst du leider nur die Flash-Version spielen.</p>
							</div>
						</div>
						<p><b>Voraussetzungen</b></p> 
						<p> - Microsoft Windows; oder</p>
						<p> - WINE für Linux und macOS (wird vom Classic-Personal nicht unterstützt, da möglicherweise unzuverlässig)</p>
						<p> - Shockwave (mindestens 11,6 oder höher)</p>
						<p> - Pale Moon 32-bit</p>
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