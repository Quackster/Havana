
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="de" lang="de">
<head>
	<meta http-equiv="content-type" content="text/html" />
	<title>{{ site.siteName }}: Club </title>

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
var habboName = "{{ playerDetails.getName() }}";
var ad_keywords = "";
var habboReqPath = "{{ site.sitePath }}";
var habboStaticFilePath = "{{ site.staticContentPath }}/web-gallery";
var habboImagerUrl = "{{ site.staticContentPath }}/habbo-imaging/";
var habboPartner = "";
window.name = "habboMain";
if (typeof HabboClient != "undefined") { HabboClient.windowName = "client"; }

</script>



<meta name="description" content="Trete dem weltweit größten virtuellen Treffpunkt bei, an dem du Freunde treffen und finden kannst. Gestalte deine eigenen Räume, sammel coole Möbel, veranstalte Partys und vieles mehr! Erstelle dein kostenloses {{ site.siteName }} Konto noch heute!" />
<meta name="keywords" content="{{ site.siteName }}, virtuell, Welt, beitreten, Gruppen, Foren, spielen, Spiele, online, Freunde, Teenager, Sammeln, soziales Netzwerk, erstellen, sammeln, verbinden, Möbel, virtuell, Waren, teilen, Abzeichen, sozial, Vernetzung, Treffpunkt, Safe, Musik, Berühmtheit, Promi-Besuche" />

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

{% if session.currentPage == "credits" %}
<div id="navi2-container" class="pngbg">
    <div id="navi2" class="pngbg clearfix">
		<ul>
			<li class="">
				<a href="{{ site.sitePath }}/credits">Münzen</a>			</li>
			<li class="selected">
				{{ site.siteName }} Club			</li>
			<li class="">
				<a href="{{ site.sitePath }}/credits/collectables">Sammelbares</a>			</li>
    		<li class=" last">
				<a href="{{ site.sitePath }}/credits/pixels">Pixel</a>    		</li>
		</ul>
    </div>
</div>
{% endif %}

{% if session.currentPage == "me" %}
<div id="navi2-container" class="pngbg">
    <div id="navi2" class="pngbg clearfix">
		<ul>
			<li class="">
				<a href="{{ site.sitePath }}/me">Start</a>			</li>
    		<li class="">
				<a href="{{ site.sitePath }}/home/{{ playerDetails.getName() }}">Meine Seite</a>    		</li>
			<li class="">
				<a href="{{ site.sitePath }}/profile">Einstellungen</a>			</li>
				<li class="selected{% if gameConfig.getInteger('guides.group.id') == 0 %} last{% endif %}">
			<a href="{{ site.sitePath }}/club">{{ site.siteName }} Club</a>
				</li>
				{% if gameConfig.getInteger('guides.group.id') > 0 %}
				<li class=" last">
					<a href="{{ site.sitePath }}/groups/officialhabboguides">Habbo Guides</a>
				</li>
				{% endif %}
		</ul>
    </div>
</div>
{% endif %}

<div id="container">
	<div id="content" style="position: relative" class="clearfix">
    <div id="column1" class="column">

				<div class="habblet-container ">		
						<div class="cbb clearfix hcred ">
	
							<h2 class="title">{{ site.siteName }} Club: werde jetzt ein VIP!							</h2>
						<div id ="habboclub-products">
    <div id="habboclub-clothes-container">
        <div class="habboclub-extra-image"></div>
        <div class="habboclub-clothes-image"></div>
    </div>

    <div class="clearfix"></div>
    <div id="habboclub-furniture-container">
        <div class="habboclub-furniture-image"></div>
    </div>
</div>
	
						
					</div>
				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
			 

			     		
				<div class="habblet-container ">		
						<div class="cbb clearfix lightbrown ">

	
							<h2 class="title">Deine Vorteile							</h2>
						<div id="habboclub-info" class="box-content">
    <p>{{ site.siteName }} Club ist unser VIP-Bereich nur für VIP Mitglieder, hier ist Pöbel absolut nicht zugelassen! Mitglieder genießen zahlreiche Vorteile, darunter exklusive Kleidung, kostenlose Geschenke und eine erweiterte Freundesliste. Unten findest du alle attraktiven Gründe für deine Mitgliedschaft.</p>
    <h3 class="heading">1. Extra Kleidung &amp; Accessories</h3>
    <p class="content habboclub-clothing">Zeige deinen neuen Status mit einer Vielzahl zusätzlicher Kleidungsstücke und Accessoires sowie besonderen Frisuren und Farben.        <br /><br /><a href="{{ site.sitePath }}/credits/club/tryout">Probiere jetzt {{ site.siteName }} Club Kleidung testweise aus!</a>

    </p>
    <h3 class="heading">2. Kostenlose Möbel</h3>
    <p class="content habboclub-furni">Einmal im Monat und das jeden Monat, erhälst du ein exklusives kostenloses {{ site.siteName }} Clubmöbelstück.</p>        
    <p class="content">Wichtiger Hinweis: Die Clubzeit ist kumulativ. Das bedeutet.. dass du, wenn du deine Mitgliedschaft unterbrichst und dann wieder beitrittst, dort weitermachst, wo du aufgehört hast.</p>
    <h3 class="heading">3. Exklusive Raum Layouts</h3>
    <p class="content">Spezielle Gästezimmer-Layouts, nur für {{ site.siteName }} Club Mitglieder. Perfekt, um deine neuen Möbel zu präsentieren!</p>
    <p class="habboclub-room" />

    <h3 class="heading">4. Zugriff auf alle Bereiche</h3>
    <p class="content">Umgehe die lästigen Warteschlangen beim Laden der Räume. Und das ist noch nicht alles: Du erhälst auch Zugang zu öffentlichen Räumen, die nur für HCs zugänglich sind.</p>
    <h3 class="heading">5. Homepage Upgrades</h3>
    <p class="content">Tritt {{ site.siteName }} Club bei und verabschiede dich von Homepage-Werbung! Und das bedeutet, dass du auch die HC-Skins und Hintergründe optimal nutzen kannst.</p>
    <h3 class="heading">6. Mehr Freunde</h3>
    <p class="content habboclub-communicator">600 Leute! Das sind eine Menge Freunde.. Mehr als man mit einem großen Stock anstupsen kann.</p>

    <h3 class="heading">7. Spezielle Kommandos</h3>
    <p class="content habboclub-commands right">Mit dem Befehl :chooser wird eine anklickbare Liste aller Benutzer im Raum angezeigt. Praktisch, wenn man neben seinem Kumpel sitzen oder einen Unruhestifter rauswerfen möchte.</p>
    <br />
    <p>Mit dem Befehl :furni kannst du alle Gegenstände in einem Raum auflisten.. auch versteckte Gegenstände.</p>
</div>
	
						
					</div>
				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>

</div>
<div id="column2" class="column">
			     		
				<div class="habblet-container ">		
						<div class="cbb clearfix hcred ">
	
							<h2 class="title">Meine Mitgliedschaft							</h2>
							

						<script src="{{ site.staticContentPath }}/web-gallery/static/js/habboclub.js" type="text/javascript"></script>
						{% include "base/hc_status.tpl" %}				
					</div>
				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
			 

			     		
				<div class="habblet-container ">		
						<div class="cbb clearfix lightbrown ">
	
							<h2 class="title">Monatliche Geschenke
							</h2>
						<script src="{{ site.staticContentPath }}/web-gallery/static/js/habboclub.js" type="text/javascript"></script>
<div id="hc-gift-catalog">
  {% include "habblet/habboclubgift.tpl" %}
</div>
	
						
					</div>
				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
			 

</div>
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