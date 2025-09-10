
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="de" lang="de">
<head>
	<meta http-equiv="content-type" content="text/html" />
	<title>{{ site.siteName }}: Willkommen </title>

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

<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/welcome.css" type="text/css" />


<meta name="description" content="Trete dem weltweit größten virtuellen Treffpunkt bei, an dem du Freunde treffen und finden kannst. Gestalte deine eigenen Räume, sammle coole Möbel, veranstalte Partys und vieles mehr! Erstelle dein kostenloses {{ site.siteName }} Konto noch heute!" />
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
<meta name="build" content="KeplerWeb" />
</head>
<body id="welcome" class=" ">
<div id="overlay"></div>
<div id="header-container">
	<div id="header" class="clearfix">
		<h1><a href="{{ site.sitePath }}/"></a></h1>
        <div id="subnavi">
			<div id="subnavi-user">
				<ul>
					<li id="myfriends"><a href="#"><span>Meine Freunde</span></a><span class="r"></span></li>
					<li id="mygroups"><a href="#"><span>Meine Gruppen</span></a><span class="r"></span></li>
					<li id="myrooms"><a href="#"><span>Meine Räume</span></a><span class="r"></span></li>
				</ul>
			</div>
            <div id="subnavi-search">
                <div id="subnavi-search-upper">

                <ul id="subnavi-search-links">
                    <li><a href="{{ site.sitePath }}/help" target="habbohelp" onclick="openOrFocusHelp(this); return false">Hilfe</a></li>
					<li><a href="{{ site.sitePath }}/account/logout" class="userlink" id="signout">Abmelden</a></li>
				</ul>
                </div>
            </div>
            <div id="to-hotel">
					    <a href="{{ site.sitePath }}/client" class="new-button green-button" target="client" onclick="HabboClient.openOrFocus(this); return false;"><b> {{ site.siteName }} Hotel beitreten</b><i></i></a>
			</div>
        </div>
		<script type="text/javascript">
		L10N.put("purchase.group.title", "Gruppe erstellen");
		document.observe("dom:loaded", function() {
            $("signout").observe("click", function() {
                HabboClient.close();
            });
        });
        </script>
<ul id="navi">
		        <li class="selected">
			<strong>Eduard </strong>			<span></span>
		</li>
				<li>
			<a href="{{ site.sitePath }}/community">Community</a>			<span></span>
		</li>
		<li>
			<a href="{{ site.sitePath }}/credits">Münzen</a>			<span></span>
		</li>
		</ul>

        <div id="habbos-online"><div class="rounded"><span>0 {{ site.siteName }}s Online</span></div></div>
	</div>
</div>

<div id="content-container">

	<div id="navi2-container" class="pngbg">
    <div id="navi2" class="pngbg clearfix">
		<ul>
			<li class="">
				<a href="{{ site.sitePath }}/me">Start</a>			</li>
    		<li class="">
				<a href="{{ site.sitePath }}/home/Alex">Meine Seite</a>    		</li>
    		<li class="">
				<a href="{{ site.sitePath }}/profile">Einstellungen</a>    		</li>
			<li class=" last">
				<a href="{{ site.sitePath }}/club">{{ site.siteName }} Club</a>
			</li>
		</ul>
    </div>
</div>
	
<div id="container">
	<div id="content" style="position: relative" class="clearfix">
    <div id="column1" class="column">
				<div class="habblet-container ">
						<div class="cbb clearfix lightgreen ">

							<h2 class="title">Wähle einen Raum und erhalte kostenlose Möbel!
							</h2>
						<div id="roomselection-welcome-intro" class="box-content">
    Wähle das Zimmer aus, das dir am besten gefällt damit du in deiner ersten Woche bei {{ site.siteName }} jeden Tag ein neues Möbelstück bekommst!
</div>
<ul class="roomselection-welcome clearfix">
	<li class="odd">
	<a class="roomselection-select new-button" href="client?createRoom=0" target="client" onclick="return RoomSelectionHabblet.create(this, 0);"><b>Wählen</b><i></i></a>
	</li>
	<li class="even">
	<a class="roomselection-select new-button" href="client?createRoom=1" target="client" onclick="return RoomSelectionHabblet.create(this, 1);"><b>Wählen</b><i></i></a>
	</li>
	<li class="odd">
	<a class="roomselection-select new-button" href="client?createRoom=2" target="client" onclick="return RoomSelectionHabblet.create(this, 2);"><b>Wählen</b><i></i></a>
	</li>
	<li class="even">
	<a class="roomselection-select new-button" href="client?createRoom=3" target="client" onclick="return RoomSelectionHabblet.create(this, 3);"><b>Wählen</b><i></i></a>
	</li>
	<li class="odd">
	<a class="roomselection-select new-button" href="client?createRoom=4" target="client" onclick="return RoomSelectionHabblet.create(this, 4);"><b>Wählen</b><i></i></a>
	</li>
	<li class="even">
	<a class="roomselection-select new-button" href="client?createRoom=5" target="client" onclick="return RoomSelectionHabblet.create(this, 5);"><b>Wählen</b><i></i></a>
	</li>
</ul>



					</div>
				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>

</div>
<div id="column2" class="column">
				<div class="habblet-container ">

						<div class="cbb clearfix lightgreen">

<div class="welcome-intro clearfix">
	<img alt="{{ site.playerName }}" src="{{ site.sitePath }}/habbo-imaging/avatarimage?figure={{ playerDetails.figure }}&size=b&direction=3&head_direction=3&crr=667&gesture=srp&frame=1" width="64" height="110" class="welcome-habbo" />
    <div id="welcome-intro-welcome-user"  >Willkommen {{ playerDetails.getName() }}!</div>
    <div id="welcome-intro-welcome-party" class="box-content">Wenn du in deinem Zimmer ankommst, wirst du gefragt, ob du die {{ site.siteName }}-Guides treffen möchtest. {{ site.siteName }}-Guides sind sehr erfahrene {{ site.siteName }}-Spieler.</div>
    </div>
</div>

</div>	
					
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>

				<div class="habblet-container ">		
						<div class="cbb clearfix green ">
	
							<h2 class="title">Shockwave?
							</h2>
						<div class="welcome-shockwave clearfix box-content">
    <div id="welcome-shockwave-text">Wenn du {{ site.siteName }} Hotel zum ersten mal öffnest, musst du möglicherweise Shockwave installieren. Aber keine Sorge, nichts besonderes!</div>
    <div id="welcome-shockwave-logo"><img src="{{ site.staticContentPath }}/web-gallery/v2/images/welcome/shockwave.gif" alt="shockwave" /></div>
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