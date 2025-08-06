
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="pt-br" lang="pt-br">
<head>
	<meta http-equiv="content-type" content="text/html" />
	<title>{{ site.siteName }}: Credits </title>

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
				Coins			</li>
			<li class="">
				<a href="{{ site.sitePath }}/credits/club">{{ site.siteName }} Club</a>			</li>
			<li class="">
				<a href="{{ site.sitePath }}/credits/collectables">Colecionáveis</a>			</li>
    		<li class=" last">
				<a href="{{ site.sitePath }}/credits/pixels">Pixels</a>    		</li>
		</ul>
    </div>
</div>
<div id="container">
	<div id="content" style="position: relative" class="clearfix">
    <div id="column1" class="column">
			     		
				<div class="habblet-container ">		
						<div class="cbb clearfix green ">
	
							<h2 class="title">Como conseguir moedas
							</h2>
							
						<script src="{{ site.staticContentPath }}/web-gallery/static/js/credits.js" type="text/javascript"></script>
<p class="credits-countries-select">
O bom desse servidor é que os créditos são gratuitos, sim, gratuitos. Você não precisará gastar nada para obter créditos para construir seus quartos favoritos. Basta descobrir usando os métodos abaixo para receber créditos.
</p>
<ul id="credits-methods">
	<li id="credits-type-promo">
		<h4 class="credits-category-promo">Melhor maneira</h4>
		<ul>
<li class="clearfix odd"><div id="method-3" class="credits-method-container">
					<div class="credits-summary" style="background-image: url({{ site.staticContentPath }}/c_images/album2705/logo_sms.png)">
						<h3>Permaneça online!</h3>
						<p>Quanto mais tempo passar jogando, mais moedas você vai ganhar!</p>
						
						<p class="credits-read-more" id="method-show-3" style="display: none">Leia mais</p>
					</div>
					<div id="method-full-3" class="credits-method-full">
							<p><b>Receba moedas estando online</b><br/>Você precisa estar em uma sala, mas todos os dias, se esperar 5 minutos, receberá 120 créditos apenas por estar ativo.</p>
							<p>Isso acontece uma vez a cada 24 horas, então se você fizer a mesma coisa amanhã, receberá mais 120 créditos!</p>
					</div>
					<script type="text/javascript">
					$("method-show-3").show();
					$("method-full-3").hide();
					</script>
				</div></li>
		</ul>
	</li>
	<li id="credits-type-quick_and_easy">
		<h4 class="credits-category-quick_and_easy">Outra maneira</h4>
		<ul>
				
<li class="clearfix odd"><div id="method-1" class="credits-method-container">
					<div class="credits-summary" style="background-image: url({{ site.staticContentPath }}/c_images/album2705/payment_habbo_prepaid.png)">
						<h3>Vouchers</h3>
						<p>Você também pode ganhar vouchers em competições do hotel para gerar moedas.</p>
						
						<p class="credits-read-more" id="method-show-1" style="display: none">Leia mais</p>
					</div>
					<div id="method-full-1" class="credits-method-full">
							<p>Resgate o código do voucher na bolsa do hotel ou nesta página - e você receberá suas moedas imediatamente!</p>
					</div>
					<script type="text/javascript">
					$("method-show-1").show();
					$("method-full-1").hide();
					</script>
				</div></li>
		</ul>
	</li>
	 <li id="credits-type-other">
		<h4 class="credits-category-quick_and_easy">Outra maneira</h4>
		<ul>
				
<li class="clearfix odd"><div id="method-2" class="credits-method-container">
					<div class="credits-summary" style="background-image: url({{ site.staticContentPath }}/c_images/album2705/byesw_hand.png)">
						<div class="credits-tools">
								<a  class="new-button" id="warn-clear-hand-button" href="#" onclick="warnClearHand()"><b>Apagar mobis</b><i></i></a>
							
						</div>
						<h3>Resetar Mão Gigante</h3>
						<p>Sua mão virtual está cheia de mobis? resete ela!</p>
						
						<!-- <p class="credits-read-more" id="method-show-2" style="display: none">Read more</p>
					</div>
					<div id="method-full-2" class="credits-method-full">
							<p>Simply click the button to reset your hand.</p>
							<p><strong>Warning: </strong> This action cannot be undone.</p>
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
							document.getElementById('method-2').insertAdjacentHTML('afterbegin', '<div id="' + responseName + '" style="border-radius:5px; background: #3ba800; padding: 8px; color: #FFFFFF;">Mão Gigante resetada com sucesso.</br></div><br />');
							document.getElementById(responseWarnName).remove();
						}
					}
					
					function warnClearHand() {
						var responseName = "warn-wiped-hand";
						
						if (document.getElementById(responseWarnName) == null) {
							document.getElementById('method-2').insertAdjacentHTML('afterbegin', '<div id="' + responseWarnName + '" style="border-radius:5px; background: #f29400; padding: 8px; color: #FFFFFF;">Tem certeza? <strong>Isto não pode ser desfeito!</strong><a class="new-button" id="confirm-clear-hand-button" href="#" onclick="clearHand()"><b>Sim, apagar!</b><i></i></a><br /><br /></div><br />');
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
							document.getElementById('method-2').insertAdjacentHTML('afterbegin', '<div id="' + responseWarnName + '" style="border-radius:5px; background: red; padding: 8px; color: #FFFFFF;">You must be logged in to do this<br /></div><br />');
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
L10N.put("credits.navi.read_more", "Read more");
L10N.put("credits.navi.close_fulltext", "Close instructions");
PaymentMethodHabblet.init();
</script>
	
						
					</div>

				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
			 

</div>
<div id="column2" class="column">
			     		
				<div class="habblet-container ">		
						<div class="cbb clearfix brown ">
	
							<h2 class="title">Sua carteira							</h2>
							{% if session.loggedIn == false %}
								<div class="box-content">You need to sign in to see the purse</div>
							{% else %}
					
		<div id="purse-habblet">
			<form method="post" action="{{ site.sitePath }}/credits" id="voucher-form">

			<ul>
			<li class="even icon-purse">
			<div>Você tem atualmente:</div>
			<span class="purse-balance-amount">{{ playerDetails.credits }} Moedas</span>
			<div class="purse-tx"><a href="{{ site.sitePath }}/credits/history">Transações da Conta</a></div>
			</li>

			<li class="odd">

			<div class="box-content">
			<div>Digite seu voucher (sem espaços):</div>
			<input type="text" name="voucherCode" value="" id="purse-habblet-redeemcode-string" class="redeemcode" />
			<a href="#" id="purse-redeemcode-button" class="new-button purse-icon" style="float:left"><b><span></span>Gerar</b><i></i></a>
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
	
							<h2 class="title">O que são {{ site.siteName }} Moedas?							</h2>

						<div id="credits-promo" class="box-content credits-info">
    <div class="credit-info-text clearfix">
        <img class="credits-image" src="{{ site.staticContentPath }}/web-gallery/v2/images/credits/poor.png" alt="" width="77" height="105" />
        <p class="credits-text">{{ site.siteName }} Moedas são a moeda do Hotel. Você pode usá-los para comprar todos os tipos de coisas, desde patos de borracha e sofás até assinaturas VIP, jukeboxes e teletransportes..</p>
    </div>
    <p class="credits-text-2">Todas as maneiras corretas de obter {{ site.siteName }} moedas estão a esquerda. Lembre-se: {{ site.siteName }} Moedas serão sempre gratuitas!</p>
</div>
	
						
					</div>

				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
			 
				<div class="habblet-container ">		
						<div class="cbb clearfix blue ">
	
							<h2 class="title">Sempre peça permissão!
							</h2>
						<div id="credits-safety" class="box-content credits-info">
    <div class="credit-info-text clearfix"><img class="credits-image" src="{{ site.sitePath }}/web-gallery/v2/images/credits_permission.png" width="114" height="136"/><p class="credits-text">Sempre peça permissão aos seus pais ou responsáveis antes de comprar Habbo Coins. Se você não fizer isso e o pagamento for posteriormente cancelado ou recusado, você será banido permanentemente.</p></div>
    <p class="credits-text-2">Uh-oh!</p>
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