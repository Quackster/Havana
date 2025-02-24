
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="pt-br" lang="pt-br">
<head>
	<meta http-equiv="content-type" content="text/html" />
	<title>{{ site.siteName }}: Minhas Configurações </title>

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

<h2 class="title">Minha Conta</h2>
<div class="box-content">
            <div id="settingsNavigation">
            <ul>
				<li><a href="{{ site.sitePath }}/profile?tab=1">Meus Visuais</a>
                </li><li><a href="{{ site.sitePath }}/profile?tab=2">Minhas Configurações</a>
				{% if accountActivated %}
                </li><li><a href="{{ site.sitePath }}/profile?tab=3">Meu Email</a>
				{% else %}
				</li><li><a href="{{ site.sitePath }}/profile/verify">Email Changing & Verification</a>
				{% endif %}
                </li><li><a href="{{ site.sitePath }}/profile?tab=4">Minha Senha</a>
                </li><li><a href="{{ site.sitePath }}/profile?tab=5">Configurações de Amigos</a>
								</li><li class="selected">Configurações de Trocas
                </li>            </ul>
            </div>
</div></div>
    {% include "profile/profile_widgets/join_club.tpl" %}
</div>
    <div class="habblet-container " style="float:left; width: 560px;">
        <div class="cbb clearfix settings">

            <h2 class="title">Configurações de Trocas</h2>

<div class="box-content">
{% if alert.hasAlert %}
<div class="rounded rounded-{{ alert.colour }}">{{ alert.message }}<br />	</div><br />
{% endif %}

<!-- <div class="rounded-container"><div style="background-color: rgb(255, 255, 255);"><div style="margin: 0px 4px; height: 1px; overflow: hidden; background-color: rgb(255, 255, 255);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(238, 107, 122);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(231, 40, 62);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(227, 8, 33);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(226, 0, 26);"></div></div></div></div></div><div style="margin: 0px 2px; height: 1px; overflow: hidden; background-color: rgb(255, 255, 255);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(238, 105, 121);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(226, 1, 27);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(226, 0, 26);"></div></div></div></div><div style="margin: 0px 1px; height: 1px; overflow: hidden; background-color: rgb(255, 255, 255);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(233, 64, 83);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(226, 0, 26);"></div></div></div><div style="margin: 0px 1px; height: 1px; overflow: hidden; background-color: rgb(238, 105, 121);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(226, 0, 26);"></div></div><div style="margin: 0px; height: 1px; overflow: hidden; background-color: rgb(255, 255, 255);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(226, 1, 27);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(226, 0, 26);"></div></div></div><div style="margin: 0px; height: 1px; overflow: hidden; background-color: rgb(238, 107, 122);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(226, 0, 26);"></div></div><div style="margin: 0px; height: 1px; overflow: hidden; background-color: rgb(231, 40, 62);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(226, 0, 26);"></div></div><div style="margin: 0px; height: 1px; overflow: hidden; background-color: rgb(227, 8, 33);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(226, 0, 26);"></div></div></div><div class="rounded-red rounded-done"><font>The information provided does not match the information recorded.</font></font><br>
	</div><div style="background-color: rgb(255, 255, 255);"><div style="margin: 0px; height: 1px; overflow: hidden; background-color: rgb(227, 8, 33);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(226, 0, 26);"></div></div><div style="margin: 0px; height: 1px; overflow: hidden; background-color: rgb(231, 40, 62);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(226, 0, 26);"></div></div><div style="margin: 0px; height: 1px; overflow: hidden; background-color: rgb(238, 107, 122);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(226, 0, 26);"></div></div><div style="margin: 0px; height: 1px; overflow: hidden; background-color: rgb(255, 255, 255);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(226, 1, 27);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(226, 0, 26);"></div></div></div><div style="margin: 0px 1px; height: 1px; overflow: hidden; background-color: rgb(238, 105, 121);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(226, 0, 26);"></div></div><div style="margin: 0px 1px; height: 1px; overflow: hidden; background-color: rgb(255, 255, 255);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(233, 64, 83);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(226, 0, 26);"></div></div></div><div style="margin: 0px 2px; height: 1px; overflow: hidden; background-color: rgb(255, 255, 255);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(238, 105, 121);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(226, 1, 27);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(226, 0, 26);"></div></div></div></div><div style="margin: 0px 4px; height: 1px; overflow: hidden; background-color: rgb(255, 255, 255);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(238, 107, 122);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(231, 40, 62);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(227, 8, 33);"><div style="height: 1px; overflow: hidden; margin: 0px 1px; background-color: rgb(226, 0, 26);"></div></div></div></div></div></div></div>
	t" /><div>&nbsp;</div> -->
		<h3>Trade</h3>
		<p>Conta com senha de troca</p>
		<h3><font>Alterar suas configurações de Trocas</h3>
			<form action="{{ site.sitePath }}/profile/securitysettingupdate" method="post" id="securitySettingForm">
				<input type="hidden" name="tab" value="7">
				<input type="hidden" name="__app_key" value="HoloWeb">
				{% if (canUseTrade == false) %}
				<br>
				<!-- <h3 style="color: #ff6600;">A negociação agora aguarda um período de segurança antes que você possa negociar. Você pode cancelar a ativação a qualquer momento durante o período de segurança. A negociação será habilitada quando sua conta tiver 3 dias e tiver passado 1 hora no hotel.</h3> -->
				<h3 style="color: #ff6600;">A negociação agora aguarda um período de segurança antes que você possa negociar. A negociação será habilitada assim que seu e-mail for verificado.</h3>
				<br>
				{% else %}
				{% if playerDetails.isTradeEnabled() %}
				<p>Trading is now active. To deactivate the pass, choose 'Deactivate', confirm your password and save the changes.</p>
				{% else %}
				<p>A negociação agora está inativa. Para ativar o passe, escolha ‘Ativar’, confirme sua senha e salve as alterações.</p>
				{% endif %}
				{% endif %}
				<span id="enableTrading">
					<input type="radio" id="enableTradingOption" name="tradingsetting" value="true" {{ ("tradeEnabled" is present) ? tradeEnabled : "" }}>
					Ativar
					<input type="radio" id="disableTradingOption" name="tradingsetting" value="false" {{ ("tradeDisabled" is present) ? tradeDisabled : "" }}>
					Desativar
					<br>
					<br>
				</span>
				<p>
					<br>
					<label for="currentpassword">Current password:</label><br>
					<input type="password" size="32" maxlength="32" name="password" id="currentpassword" class="currentpassword">
				</p>

				<div class="settings-buttons">
				<a href="#" class="new-button" style="" id="securitySettingForm-submit">
					<b>Salvar alterações</b>
					<i></i></a>
				<noscript><input type="submit" class="submit"></noscript>
				</div>
				</form>
					<script type="text/javascript">
							var checkAndSubmitForm = function(e) {
								{% if (playerDetails.isTradeEnabled()) and (canUseTrade == false) %}
									$("securitySettingForm").submit();
								{% else %}
								if($("disableTradingOption").checked) {
									Dialog.showConfirmDialog("A negociação agora está inativa. Para ativar o passe, escolha ‘Ativar’, confirme sua senha e salve as alterações.", {
										okHandler: function() { $("securitySettingForm").submit();},
										headerText: "Deactivate trade",
										buttonText: "OK",
										cancelButtonText: "Cancel"
									});
								} else {
									$("securitySettingForm").submit();
								}
								{% endif %}
							};
							$("securitySettingForm-submit").observe("click", checkAndSubmitForm);
							$("securitySettingForm").observe("submit", checkAndSubmitForm);
							$("securitySettingForm-submit").show();
					</script>
				</div>
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

{% include "../base/footer.tpl" %}
	
<script type="text/javascript">
HabboView.run();
</script>


</body>
</html>