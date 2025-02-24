
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="pt-br" lang="pt-br">
<head>
	<meta http-equiv="content-type" content="text/html" />
	<title>{{ site.siteName }}: Moedas </title>

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
      <li class="">
         <a href="{{ site.sitePath }}/credits">Coins</a>			
      </li>
      <li class="">
         <a href="{{ site.sitePath }}/credits/club">{{ site.siteName }} Club</a>			
      </li>
      <li class="">
         <a href="{{ site.sitePath }}/credits/collectables">Colecionáveis</a>			
      </li>
      <li class=" last">
         <a href="{{ site.sitePath }}/credits/pixels">Pixels</a>    		
      </li>
		</ul>
    </div>
</div>
<div id="container">
	<div id="content" style="position: relative" class="clearfix">

    <div id="column1" class="column">
			     		
				<div class="habblet-container ">		
						<div class="cbb clearfix brown ">
	
							<h2 class="title">Transações							</h2>
						<div id="tx-log">

<div class="box-content">
Esta é uma visão geral do seu histórico de transações de crédito. Eles são atualizados assim que a transação é realizada.</div>

<ul class="tx-navi">
		{% if canGoNext %}
		<li class="next" id="tx-navi-{{ futureYear }}-{{ futureNumericalMonth }}-01" title="{{ futureMonth }} {{ futureYear }}"><a href="{{ site.sitePath }}/credits/history?period={{ futureYear }}-{{ futureNumericalMonth }}-01">Next &raquo</a></li>
		{% else %}
		<li class="next">Next &raquo</li>
		{% endif %}
		
		<li class="prev" id="tx-navi-{{ previousYear }}-{{ previousNumericalMonth }}-01" title="{{ previousMonth }} {{ previousYear }}"><a href="{{ site.sitePath }}/credits/history?period={{ previousYear }}-{{ previousNumericalMonth }}-01">&laquo Previous</a></li>
		<li class="now">{{ currentMonth }} {{ currentYear }}</li>
</ul>


<p class="last">
{% if transactions|length > 0 %}
<table class="tx-history">
<thead>
	<tr>
		<th class="tx-date">Date</th>
		<th class="tx-amount">Price</th>
		<th class="tx-description">Description</th>
	</tr>
</thead>
<tbody>

{% autoescape 'html' %}
{% set num = 0 %}
{% for transaction in transactions %}
	{% if num % 2 == 0 %}
	<tr class="odd">
	{% else %}
	<tr class="even">
	{% endif %}
	
		<td class="tx-date">{{ transaction.getFormattedDate() }}</td>
		<td class="tx-amount">{{ transaction.getCostCoins() }}</td>
		<td class="tx-description">{{ transaction.getDescription() }}</td>
	</tr>
	{% set num = num + 1 %}
{% endfor %}
{% endautoescape %}
{% else %}
No transactions found.
{% endif %}
</tbody>
</table>
</p>

</div>
	
						
					</div>
				</div>

				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
			 

</div>
<div id="column2" class="column">
				<div class="habblet-container ">		
						<div class="cbb clearfix brown ">
	
							<h2 class="title">Your purse							</h2>
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

    </div>
{% include "base/footer.tpl" %}

<script type="text/javascript">
HabboView.run();
</script>


</body>
</html>