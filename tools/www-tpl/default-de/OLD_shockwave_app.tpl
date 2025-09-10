
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="de" lang="de">
<head>
	<meta http-equiv="content-type" content="text/html" />
	<title>{{ site.siteName }} Portabler Client</title>

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
}

.text-column {
  float: left;
  margin-top:10px; 
  margin-bottom:10px; 
  margin-left:10px; 
  margin-right:10px;
}

/* Clear floats after the columns */
.row:after {
  content: "";
  display: table;
  clear: both;
}

.collapsible-content {
  padding: 0 10px;
  width:90%;
  max-height: 0;
  overflow: hidden;
  transition: max-height 0.2s ease-out;
}

.old-pc {
image-rendering:-moz-crisp-edges;          /* Firefox        */
image-rendering:-o-crisp-edges;            /* Opera          */
image-rendering:-webkit-optimize-contrast; /* Safari         */
image-rendering:optimize-contrast;         /* CSS3 Proposed  */
-ms-interpolation-mode:nearest-neighbor;   /* IE8+           */
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
			<li>
				<a href="{{ site.sitePath }}/help/install_shockwave">Shockwave Hilfe</a></li>
			<li class="selected">
				Portabler Client			</li>
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
					<h2 class="title">Portabler Shockwave Client</h2>
					<div class="tutorial-text">	
						<div class="row">
							<div class="text-column" style="width: 120px">
								<img class="old-pc" src="https://i.imgur.com/XOTfbl8.png" alt="">
							</div>
							<div class="text-column" style="width: 260px">
								<p>Mit den Jahren entwickeln sich auch die Browser-Plugins weiter. Shockwave ist ein Browser-Plugin, das in den meisten modernen Browsern veraltet ist. Aus diesem Grund ist ein portables Programm verfügbar, das Shockwave Habbo ausführt.</p>
								<p>Dies ist ein mit Director MX 2004 erstelltes Macromedia-Projektorprogramm. Es ist in derselben Sprache geschrieben wie der Shockwave Habbo-Client.</p>
							</div>
						</div>
						<p><b>Wie funktioniert es?</b></p>
						<p>Das Programm ist eine einfache EXE-Datei, die eine Login-Anfrage an unseren Server sendet und anschließend den Client lädt. Du musst die Datei account.ini mit deinen Login-Daten bearbeiten..</p>
						<p>Unabhängig vom Download funktioniert die App mit WINE, was eine Voraussetzung zum Spielen unter macOS und Linux ist.</p>
						<p><b>Warum sollte ich Shockwave verwenden??</b></p>
						<p>Derzeit gibt es zwei Clients, auf denen das Hotel gespielt werden kann: der erste ist Shockwave und der zweite ist Flash.</p>
						<p>Es wird dringend empfohlen, die Shockwave-Version zu spielen, da sie weitaus mehr Funktionen bietet, die auf dem Flash-Client nicht verfügbar sind.</p>
						<p><b>Bilder</b></p>
						<p>Unten siehst du Bilder des Hotels in Aktion.</p>
						<div class="article-body">
						<div id="article-wrapper">
						<div class="article-images clearfix">
						<a href="https://i.imgur.com/OrHYgxr.png" style="background-image: url(https://i.imgur.com/OrHYgxr.png); background-position: -0px -0px"></a>
						<a href="https://i.imgur.com/7yWmicl.png" style="background-image: url(https://i.imgur.com/7yWmicl.png); background-position: -0px -0px"></a>
						<a href="https://i.imgur.com/IXE91fD.png" style="background-image: url(https://i.imgur.com/IXE91fD.png); background-position: -0px -0px"></a></div>
						</div>
						</div>
						
						<script type="text/javascript" language="Javascript">
							document.observe("dom:loaded", function() {
								$$('.article-images a').each(function(a) {
									Event.observe(a, 'click', function(e) {
										Event.stop(e);
										Overlay.lightbox(a.href, "Image is loading");
									});
								});
								
								$$('a.article-5').each(function(a) {
									a.replace(a.innerHTML);
								});
							});
						</script>
					</div>
				</div>

			</div>
			<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
			<div class="habblet-container ">		
				<div class="cbb clearfix ">
					<h2 class="title" style="background-color: lightblue">Changelog</h2>
					<div class="tutorial-text">	
						<div class="row">
							<div class="text-column">
								<img src="https://i.imgur.com/yDbopCD.png" alt="">
							</div>
							<div class="text-column" style="width: 365px;">
								<p>Das Programm existiert seit April 2019 und hat im Laufe der Zeit viele Änderungen erfahren. Du kannst diese unten einsehen.</p>
							</div>
						</div>
						<a class="new-button collapsible" id="warn-clear-hand-button" href="#"><b>Reveal Changelog</b><i></i></a>
						<div class="collapsible-content" style="margin-top: 25px">
						<p><b>Version 0.8</b></p>
						<p>- Fehlerbehebungen für Cloudflare, bzgl. wie Anfragen zurück gesendet werden.</p>
						<br>
						<p><b>Version 0.7</b></p>
						<p>- Korrekturen zum Aktualisieren von Möbeln.</p>
						<br>
						<p><b>Version 0.6</b></p>
						<p>- Möbel werden jetzt von der Festplatte gespeichert und geladen, um die Ladezeiten für Möbel zu verkürzen.</p>
						<p>- Neue Möbel werden beim Laden automatisch heruntergeladen, sodass das anschließende Laden schneller geht.</p>
						<br>
						<p><b>Version 0.5</b></p>
						<br>
						<p>- Unterstützung für die Hotelansicht hinzugefügt. Diese Option kann in deinen Kontoeinstellungen geändert werden..</p>
						<p>- Funktion „Immer im Vordergrund“ hinzugefügt.</p>
						<p>- Die Anmeldung wurde im Client hinzugefügt, ein Opt-in, wenn du der Speicherung der Anmeldedaten im Text nicht vertrauen solltest.</p>
						<br>
						<p><b>Version 0.4</b></p>
						<p>- Patches hinzugefügt, damit SnowStorm spielbar wird, ohne einzufrieren.</p>
						<br>
						<p><b>Version 0.3</b></p>
						<p>- Fix für funktionierende Hyperlinks</p>
						<p>- Fix für die Schaltfläche „Tutorial zurücksetzen“, da die Anfrage nicht ordnungsgemäß an den Server gesendet wurde.</p>
						<br>
						<p><b>Version 0.2</b></p>
						<p>- Fix für verschiedene Symbole, die in Passwörtern nicht erlaubt sind.</p>
						<p>- Joystick-Symbol hinzufügt, erstellt von Copyright.</p>
						<br>
						<p><b>Version 0.1</b></p>
						<p>- Erstveröffentlichung.</p>
						</div>
						<!-- <p>Die in Shockwave enthaltenen Funktionen, die in der Flash-Version nicht vorhanden sind, sind unten aufgeführt.</p>
						<div class="row">
							<div class="column" style="margin-top:10px; margin-bottom:10px; margin-right:10px">
								<img src="{{ site.staticContentPath }}/c_images/stickers/sticker_submarine.gif" alt="">
							</div>
							<div class="column" style="margin-top:10px; margin-bottom:10px; margin-left:10px; width: 275px;">
								<p><b>BattleBall, Diving, Wobble Squabble, Trax Maschine, Jukeboxe, American Idol, Tic Tac Toe, Schach, Battleships, Poker</b> und einige nostalgische Habbo-Komponenten wie die Hand und der Room-o-Matic.</p>
							</div>
						</div> -->
					</div>
				</div>
			</div>
		</div>
		<div id="column2" class="column" style="width: 305px">	     		
			<div class="habblet-container ">		
				<div class="cbb clearfix">
					<h2 class="title" style="background-color: gray">Downloads</h2>
					<div class="tutorial-text">							</h2>
						<p style="margin-top:10px; margin-right:10px">Es stehen zwei Versionen zum Download zur Verfügung: der Standard-Download und der Lite-Download.</p>
						<p><b>Standard Version:</b></p>
						<p>Die Standardversion enthält alle Möbel zum sofortigen Laden. Neue Möbel werden heruntergeladen, wenn die Datei nicht vorhanden ist.</p>
						<p> - <a href="https://classichabbo.com/classichabbo_exe.zip">Klick hier</a> zum download (82 MB)</p>
						<p><b>Lite Version:</b></p>
						<p>Enthält keine Möbel. Wenn die Datei nicht existiert, werden neue Möbel heruntergeladen..</p>
						<p> - <a href="https://classichabbo.com/classichabbo_exe_lite.zip">Klick hier</a> zum download (25 MB)</p>
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
	
	var coll = document.getElementsByClassName("collapsible");
	var i;

	for (i = 0; i < coll.length; i++) {
		coll[i].addEventListener("click", function() {
			this.classList.toggle("active");
			var content = this.nextElementSibling;
			if (content.style.maxHeight){
				content.style.maxHeight = null;
			} else {
				content.style.maxHeight = content.scrollHeight + "px";
			} 
		});
	}

</script>


</body>
</html>