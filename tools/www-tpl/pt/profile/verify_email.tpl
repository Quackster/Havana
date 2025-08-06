
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="pt-br" lang="pt-br">
<head>
	<meta http-equiv="content-type" content="text/html" />
	<title>{{ site.siteName }}: Verify Email </title>

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


<meta name="description" content="Participe do maior ponto de encontro virtual do mundo onde você pode conhecer e fazer amigos. Projete seus próprios quartos, colecione móveis legais, dê festas e muito mais! Crie o seu {{ site.siteName }} GRÁTIS hoje!" />
<meta name="keywords" content="{{ site.siteName }}, virtual, mundo, participar, grupos, fóruns, brincar, jogos, on-line, amigos, adolescentes, colecionar, rede social, criar, colecionar, conectar, móveis, virtual, mercadorias, compartilhamento, emblemas, social, networking, hangout, seguro, música , celebridade, visitas de celebridades" />


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
				<a href="{{ site.sitePath }}/me">Home</a>			</li>
    		<li class="">
				<a href="{{ site.sitePath }}/home/{{ playerDetails.getName() }}">Minha Página</a>    		</li>
    		<li class="selected">
				Configurações de conta    		</li>
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

<h2 class="title">Configurações de conta</h2>
<div class="box-content">
            <div id="settingsNavigation">
            <ul>
				<li><a href="{{ site.sitePath }}/profile?tab=1">Meus Visuais</a>
                </li><li><a href="{{ site.sitePath }}/profile?tab=2">Minhas Configurações</a>
                </li><li class="selected">{% if accountActivated %}Meu Emial{% else %}Mudança de email & Verificações{% endif %}
                </li><li><a href="{{ site.sitePath }}/profile?tab=4">Minha Senha</a>
                </li><li><a href="{{ site.sitePath }}/profile?tab=5">Configurações de Amigos</a>
								</li><li><a href="{{ site.sitePath }}/profile?tab=6">Configurações de Trocas</a>
                </li>            </ul>
            </div>
</div></div>
    {% include "profile/profile_widgets/join_club.tpl" %}
</div>
    <div class="habblet-container " style="float:left; width: 560px;">
        <div class="cbb clearfix settings">

            <h2 class="title">Change and verify your email</h2>
            <div class="box-content">

{% if alert.hasAlert %}
<div class="rounded rounded-{{ alert.colour }}">{{ alert.message }}<br />	</div><br />
{% endif %}

<form action="{{ site.sitePath }}/profile/send_email" method="post" id="emailform">
<input type="hidden" name="tab" value="3" />
<input type="hidden" name="__app_key" value="HavanaWeb" />

<div class="settings-step">

	<h4>You haven't confirmed that your email is valid!</h4><br />
	<p>How to activate and confirm my email? Click the button and send a confirmation email to your email account. Click the link from the email message and you are done! Click the button below if you want us to send you another account activation message.</p>
	<div class="settings-buttons">
		<a href="#" class="new-button" style="display: none" id="emailform-submit"><b>Activate my email-address</b><i></i></a>
		<noscript><input type="submit" value="Activate my email-address" name="save" class="submit" /></noscript>
	</div>
</div>
<br /><br /><hr>
<div class="settings-step">
	<p>We have noticed that you have logged in with your Habbo account. In order to change your email you need to go to the account management settings page. Click the link below to enter the Habbo account change email page.</p>
	<p><a href="{{ site.sitePath }}/profile?tab=3">Go to the Habbo account change email page</a></p>
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