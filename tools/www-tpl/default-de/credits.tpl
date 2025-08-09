
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="de" lang="de">
<head>
	<meta http-equiv="content-type" content="text/html" />
	<title>{{ site.siteName }}: Münzen </title>

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

<div id="navi2-container" class="pngbg">
    <div id="navi2" class="pngbg clearfix">
		<ul>
			<li class="selected">
				Münzen			</li>
			<li class="">
				<a href="{{ site.sitePath }}/credits/club">{{ site.siteName }} Club</a>			</li>
			<li class="">
				<a href="{{ site.sitePath }}/credits/collectables">Sammelbares</a>			</li>
    		<li class=" last">
				<a href="{{ site.sitePath }}/credits/pixels">Pixel</a>    		</li>
		</ul>
    </div>
</div>
<div id="container">
	<div id="content" style="position: relative" class="clearfix">
    <div id="column1" class="column">

				<div class="habblet-container ">		
						<div class="cbb clearfix green ">
	
							<h2 class="title">Wie kriege ich Münzen?
							</h2>
							
						<script src="{{ site.staticContentPath }}/web-gallery/static/js/credits.js" type="text/javascript"></script>
<p class="credits-countries-select">
Das Gute an diesem Server ist, dass die Münzen kostenlos sind. Du musst nichts ausgeben, um Münzen für den Bau deiner Lieblingsräume zu erhalten. Informiere dich einfach über die unten aufgeführten Methoden, oder melde dich beim Hotel Staff!
</p>
<ul id="credits-methods">
	<li id="credits-type-promo">
		<h4 class="credits-category-promo">Bester Weg</h4>
		<ul>
<li class="clearfix odd"><div id="method-3" class="credits-method-container">
					<div class="credits-summary" style="background-image: url({{ site.staticContentPath }}/c_images/album2705/logo_sms.png)">
						<h3>Sei Online</h3>
						<p>Spiel einfach die ganze Zeit und du erhälst in einem gleichmässigen Interval Münzen geschenkt.</p>
						
						<p class="credits-read-more" id="method-show-3" style="display: none">Mehr Lesen</p>
					</div>
					<div id="method-full-3" class="credits-method-full">
							<p><b>Erhalte Münzen, indem du online bist</b><br/>Du musst in einem Raum sein, wenn du jeden Tag 5 Minuten wartest, erhälst du 120 Münzen.</p>
							<p>Das wiederholt sich alle 24 Stunden. Wenn du also morgen wieder da bist, erhälst du weitere 120 Münzen!</p>
					</div>
					<script type="text/javascript">
					$("method-show-3").show();
					$("method-full-3").hide();
					</script>
				</div></li>
		</ul>
	</li>
	<li id="credits-type-quick_and_easy">
		<h4 class="credits-category-quick_and_easy">Andere Wege</h4>
		<ul>
				
<li class="clearfix odd"><div id="method-1" class="credits-method-container">
					<div class="credits-summary" style="background-image: url({{ site.staticContentPath }}/c_images/album2705/payment_habbo_prepaid.png)">
						<h3>Gutscheine</h3>
						<p>Du kannst spezielle Codes zum Einlösen von Gutscheinen erhalten</p>
						
						<p class="credits-read-more" id="method-show-1" style="display: none">Mehr Lesen</p>
					</div>
					<div id="method-full-1" class="credits-method-full">
							<p>Löse deine Gutscheincodes in der Hotelbörse oder auf dieser Seite ein und erhalte sofort deine Münzen!</p>
					</div>
					<script type="text/javascript">
					$("method-show-1").show();
					$("method-full-1").hide();
					</script>
				</div></li>
		</ul>
	</li>
	<li id="credits-type-other">
		<h4 class="credits-category-quick_and_easy">Hand Reset</h4>
		<ul>
				
<li class="clearfix odd"><div id="method-2" class="credits-method-container">
					<div class="credits-summary" style="background-image: url({{ site.staticContentPath }}/c_images/album2705/byesw_hand.png)">
						<div class="credits-tools">
								<a  class="new-button" id="warn-clear-hand-button" href="#" onclick="warnClearHand()"><b>Hand leeren</b><i></i></a>
							
						</div>
						<h3>Die Hand aufräumen</h3>
						<p>Virtuelle Hand zu voll mit Möbeln? Klicke hier, um sie zurückzusetzen.</p>
						
						<!-- <p class="credits-read-more" id="method-show-2" style="display: none">Mehr Lesen</p>
					</div>
					<div id="method-full-2" class="credits-method-full">
							<p>Klicke einfach auf die Schaltfläche, um deine Hand zurückzusetzen.</p>
							<p><strong>WARNUNG: </strong> Der Reset kann nicht rückgängig gemacht werden</p>
					</div> -->
					{% if session.loggedIn %}
					<script type="text/javascript">
					var responseName = "wiped-hand";
					var responseWarnName = "warn-wiped-hand";
						
						
					function clearHand() {
						const http = new XMLHttpRequest();
						http.open("GET", habboReqPath + "/habblet/ajax/clear_hand");
						http.send();
						
						var responseName = "wiped-hand";
						
						if (document.getElementById(responseName) == null) {
							document.getElementById('method-2').insertAdjacentHTML('afterbegin', '<div id="' + responseName + '" style="border-radius:5px; background: #3ba800; padding: 8px; color: #FFFFFF;">Deine Hand wurde geleert.</br></div><br />');
							document.getElementById(responseWarnName).remove();
						}
					}
					
					function warnClearHand() {
						var responseName = "warn-wiped-hand";
						
						if (document.getElementById(responseWarnName) == null) {
							document.getElementById('method-2').insertAdjacentHTML('afterbegin', '<div id="' + responseWarnName + '" style="border-radius:5px; background: #f29400; padding: 8px; color: #FFFFFF;">Sicher? <strong>Es gibt kein zurück!</strong><a class="new-button" id="confirm-clear-hand-button" href="#" onclick="clearHand()"><b>Ja! Leer machen!</b><i></i></a><br /><br /></div><br />');
							document.getElementById("warn-clear-hand-button").remove();
						}
					}
					
					$("method-show-2").show();
					$("method-full-2").hide();
					</script>
					{% else %}
					<script type="text/javascript">
					var responseWarnName = "warn-wiped-hand";
					
					function warnClearHand() {
						var responseName = "warn-wiped-hand";
						
						if (document.getElementById(responseWarnName) == null) {
							document.getElementById('method-2').insertAdjacentHTML('afterbegin', '<div id="' + responseWarnName + '" style="border-radius:5px; background: red; padding: 8px; color: #FFFFFF;">Du musst dazu angemeldet sein<br /></div><br />');
							document.getElementById("warn-clear-hand-button").remove();
						}
					}
					
					$("method-show-2").show();
					$("method-full-2").hide();
					</script>
					{% endif %}
				</div></li>
		</ul>
	</li> 
</ul>

<script type="text/javascript">
L10N.put("credits.navi.read_more", "Öffnen");
L10N.put("credits.navi.close_fulltext", "Schließen");
PaymentMethodHabblet.init();
</script>
	
						
					</div>

				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>

</div>
<div id="column2" class="column">
				<div class="habblet-container ">		
						<div class="cbb clearfix brown ">
	
							<h2 class="title">Dein Guthaben							</h2>
							{% if session.loggedIn == false %}
								<div class="box-content">Du musst eingeloggt sein um dein Guthaben sehen zu können</div>
							{% else %}
					
		<div id="purse-habblet">
			<form method="post" action="{{ site.sitePath }}/credits" id="voucher-form">

			<ul>
			<li class="even icon-purse">
			<div>Du hast zurzeit:</div>
			<span class="purse-balance-amount">{{ playerDetails.credits }} Münzen</span>
			<div class="purse-tx"><a href="{{ site.sitePath }}/credits/history">Dein Transaktionsverlauf</a></div>
			</li>

			<li class="odd">

			<div class="box-content">
			<div>Gib dein Code ohne Leerzeichen ein:</div>
			<input type="text" name="voucherCode" value="" id="purse-habblet-redeemcode-string" class="redeemcode" />
			<a href="#" id="purse-redeemcode-button" class="new-button purse-icon" style="float:left"><b><span></span>Bestätigen</b><i></i></a>
			</div>
			</li>
			</ul>
			<div id="purse-redeem-result">
			</div>	</form>
		</div>
		{% endif %}

<script type="text/javascript">
	new PurseHabblet();
</script>
	
						
					</div>
				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
				
				<div class="habblet-container ">		
						<div class="cbb clearfix orange ">
	
							<h2 class="title">Was sind {{ site.siteName }} Münzen?							</h2>

						<div id="credits-promo" class="box-content credits-info">
    <div class="credit-info-text clearfix">
        <img class="credits-image" src="{{ site.staticContentPath }}/web-gallery/v2/images/credits/poor.png" alt="" width="77" height="105" />
        <p class="credits-text">{{ site.siteName }} Münzen sind die Währung des Hotels. Mit ihnen kannst du alles Mögliche kaufen.. von Gummienten und Sofas bis hin zu VIP-Mitgliedschaften, Jukeboxen und Teleportern.</p>
    </div>
    <p class="credits-text-2">Alle Wege zu {{ site.siteName }} Münzen findest du links. Denk dran: {{ site.siteName }} Münzen sind und bleiben Kostenlos!</p>
</div>
	
						
					</div>

				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>

				<div class="habblet-container ">		
						<div class="cbb clearfix blue ">
	
							<h2 class="title">Frage immer um Erlaubnis!
							</h2>
						<div id="credits-safety" class="box-content credits-info">
    <div class="credit-info-text clearfix"><img class="credits-image" src="{{ site.sitePath }}/web-gallery/v2/images/credits_permission.png" width="114" height="136"/><p class="credits-text">Bitte vor dem Kauf von Habbo-Münzen immer deine Eltern oder Erziehungsberechtigten um Erlaubnis. Wenn du das nicht tust und die Zahlung später storniert oder abgelehnt wird, wirst du dauerhaft gesperrt.</p></div>
    <p class="credits-text-2">Oh-Oh..!</p>
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

<script type="text/javascript">
HabboView.run();
</script>


</body>
</html>