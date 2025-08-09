
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="de" lang="de">
<head>
	<meta http-equiv="content-type" content="text/html" />
	<title>{{ site.siteName }}: Pixel </title>

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
			<li class="">
				<a href="{{ site.sitePath }}/credits">Münzen</a>			</li>
			<li class="">
				<a href="{{ site.sitePath }}/credits/club">{{ site.siteName }} Club</a>			</li>
			<li class="">
				<a href="{{ site.sitePath }}/credits/collectables">Sammelbares</a>			</li>
    		<li class="selected last">
				<a href="{{ site.sitePath }}/credits/pixels">Pixel</a>    		</li>
		</ul>
    </div>
</div>
<div id="container">
	<div id="content" style="position: relative" class="clearfix">

    <div id="column1" class="column">
				<div class="habblet-container ">		
						<div class="cbb clearfix pixelblue ">
							<h2 class="title">Erfahre jetzt wie du Pixel erhalten kannst!
							</h2>
						<div class="pixels-infobox-container">
    <div class="pixels-infobox-text">
            <h3>Du kannst auf verschiedene Arten Pixel verdienen:</h3>
            <ul>
                <li><p>1. Melde dich einmal täglich bei Habbo an</p></li>
                <li><p>2. Werde belohnt, wenn du jeden Tag Zeit bei Habbo verbringst.. je länger du bleibst, desto mehr verdienst du!</p></li>
                <li><p>3. Erreiche Erfolge, arbeite als Guide und gib Respekt an andere Spieler.</p></li>
                <li><p>4. Trette auch Habbo Club bei!</p></li>
            </ul>
            <p> </p>
            <p><a href="{{ site.sitePath }}/help" target="_blank">FAQ</a></p>
    </div>
</div>
	
						
					</div>
				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>

</div>
<div id="column2" class="column">
				<div class="habblet-container ">		
						<div class="cbb clearfix pixelgreen ">
	
							<h2 class="title">Miete dir coole Sachen!							</h2>

						<div id="pixels-info" class="box-content pixels-info">
    <div class="pixels-info-text clearfix">
        <img class="pixels-image" src="{{ site.staticContentPath }}/web-gallery/v2/images/activitypoints/pixelpage_effectmachine.png" alt=""/>
        <p class="pixels-text">Schaffe den coolsten Raum! Mit diesen Raumeffekten wird wirklich jeder bei dir chillen wollen! Ganz sicher <3 </p>
    </div>
</div>
	
						
					</div>
				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
				<div class="habblet-container ">		
						<div class="cbb clearfix pixellightblue ">
	
							<h2 class="title">Bodyeffekte gefällig?							</h2>
						<div id="pixels-info" class="box-content pixels-info">
    <div class="pixels-info-text clearfix">
        <img class="pixels-image" src="{{ site.staticContentPath }}/web-gallery/v2/images/activitypoints/pixelpage_personaleffect.png" alt=""/>
        <p class="pixels-text">Verpasse deinem Charakter coole Effekte, die zu jedem Anlass passen. Möchtest du über den roten Teppich fliegen oder im Rampenlicht stehen? Jetzt ist deine Chance zu glänzen.. oder doch was anderes?.. </p>
    </div>

</div>
	
						
					</div>
				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
				<div class="habblet-container ">		
						<div class="cbb clearfix pixeldarkblue ">
	
							<h2 class="title">Beste Deals.							</h2>
						<div id="pixels-info" class="box-content pixels-info">
    <div class="pixels-info-text clearfix">

        <img class="pixels-image" src="{{ site.staticContentPath }}/web-gallery/v2/images/activitypoints/pixelpage_discounts.png" alt=""/>
        <p class="pixels-text">Mit dem Sammeln von Pixeln erhälst du Rabatte auf eine große Auswahl von Möbeln. Finde in unserem Bereich Angebote heraus, wie viel du sparen kannst!</p>
    </div>
</div>
	
						
					</div>
				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>

</div>

<script type="text/javascript">
HabboView.run();
</script>
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