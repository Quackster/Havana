<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="de" lang="de">
<head>
	<meta http-equiv="content-type" content="text/html" />
	<title>{{ site.siteName }} Spiele </title>

<script type="text/javascript">
var andSoItBegins = (new Date()).getTime();
</script>
    <link rel="shortcut icon" href="{{ site.staticContentPath }}/web-gallery/v2/favicon.ico" type="image/vnd.microsoft.icon" />
    <link rel="alternate" type="application/rss+xml" title="Habbo: RSS" href="https://classichabbo.com/articles/rss.xml" />
<script src="{{ site.staticContentPath }}/web-gallery/static/js/libs2.js" type="text/javascript"></script>
<script src="{{ site.staticContentPath }}/web-gallery/static/js/visual.js" type="text/javascript"></script>
<script src="{{ site.staticContentPath }}/web-gallery/static/js/libs.js" type="text/javascript"></script>
<script src="{{ site.staticContentPath }}/web-gallery/static/js/common.js" type="text/javascript"></script>
<script src="{{ site.staticContentPath }}/web-gallery/static/js/fullcontent.js" type="text/javascript"></script>
<script src="{{ site.staticContentPath }}/web-gallery/static/js/libs2.js" type="text/javascript"></script>
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/style.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/buttons.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/boxes.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/tooltips.css" type="text/css" />
<script src="/js/local/com.js" type="text/javascript"></script>


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
.habblet-text {
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
<meta name="keywords" content="habbo, virtuell, Welt, beitreten, Gruppen, Foren, spielen, Spiele, online, Freunde, Teenager, Sammeln, soziales Netzwerk, erstellen, verbinden, Möbel, virtuell, Waren, teilen, Abzeichen, sozial, Vernetzung, Treffpunkt, Safe, Musik, Berühmtheit, Promi-Besuche" />

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
body { behavior: url(/js/csshover.htc); }
</style>
<![endif]-->
<meta name="build" content="21.0.53 - 20080403054049 - com" />
</head>
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
				Spiele
				
			</li>
    		<li class="">
				<a href="/groups/battleball_rebound">BattleBall: Rebound!</a>
    		</li>
    		<li class="">
				<a href="/groups/snow_storm">SnowStorm</a>
    		</li>
    		<li class="">
				<a href="/groups/wobble_squabble">Wobble Squabble</a>
    		</li>
    		<li class=" last">
				<a href="/groups/lido">Lido Diving</a>
    		</li>
	</ul>
    </div>
</div>

<div id="container">
	<div id="content" style="position: relative" class="clearfix">
    <div id="column1" class="column">
				<div class="habblet-container ">		
						<div class="cbb clearfix green ">
	
							<h2 class="title">Empfohlene Spiele
							</h2>
						<div class="game-links-top">
<div><a href="/groups/battleball_rebound"><img src="{{ site.staticContentPath }}/web-gallery/v2/images/games/battleball.png" alt="BattleBall: Rebound! »" width="450" height="99" /></a></div>
<div><a href="/groups/snow_storm"><img src="{{ site.staticContentPath }}/web-gallery/v2/images/games/snowstorm.png" alt="SnowStorm »" width="450" height="99" /></a></div>
</div>

<ul class="game-links">
	<li><a href="/groups/battleball_rebound">BattleBall: Rebound! »</a></li>
	<li><a href="/groups/snow_storm">SnowStorm »</a></li>
	<li><a href="/groups/wobble_squabble">Wobble Squabble »</a></li>
	<li><a href="/groups/lido">Lido Diving »</a></li>
</ul>
	
						
					</div>
				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
			 
				<div class="habblet-container ">		
						<div class="cbb clearfix orange ">
	
							<h2 class="title">High-Scores
							</h2>
{% include "habblet/personalhighscores.tpl" %}

<script type="text/javascript">
new HighscoreHabblet("h116");
</script>
	
						
					</div>
				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
			 
</div>
<div id="column2" class="column">
				<div class="habblet-container ">		
						<div class="cbb clearfix green ">
	
							<h2 class="title">Dein Ticket zur Spannung!
							</h2>
						<div class="box-content">
	<div id="game-promo">
		Spieltickets kosten 1 Münze für 2, oder 20 Tickets für 6 Münzen.
	</div>
</div>
	
						
					</div>
				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
				
			<div class="habblet-container ">		
				<div class="cbb clearfix">
					<h2 class="title" style="background-color: lightblue">Wertung</h2>
					<div class="habblet-text">							</h2>
						<div class="row">
							<div class="column" style="width: 100px; margin-top:10px; margin-bottom:10px; margin-right:6px">
								<img class="credits-image" src="https://i.imgur.com/AOd2pRV.gif" alt="">
							</div>
							<div class="column" style="width: 170px;">
								{% if viewMonthlyScores %}
								<p style="margin-top: 10px">Die folgenden Ergebnisse sind die von Monat zu Monat erzielten Punktzahlen.</p>
								<p>Um die seit der Erfassung der Spielpunkte erzielten Punkte anzuzeigen, klicke bitte <a href="{{ site.sitePath }}/games/score_all_time">Hier</a>.</p>
								{% else %}
								<p style="margin-top: 10px">Die folgenden Ergebnisse sind die erzielten Punktzahlen aller Zeiten.</p>
								<p>Um die seit der Erfassung der Spielwertung monatlich erzielten Punkte anzuzeigen, klicke bitte <a href="{{ site.sitePath }}/games">Hier</a>.</p>								
								{% endif %}
							</div>
						</div>
					</div>
				</div>
			</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
			 
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
{% include "base/footer.tpl" %}


</body>
</html>