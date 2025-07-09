
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="pt-br" lang="pt-br">
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

{% if session.currentPage == "credits" %}
<div id="navi2-container" class="pngbg">
    <div id="navi2" class="pngbg clearfix">
		<ul>
			<li class="">
				<a href="{{ site.sitePath }}/credits">Coins</a>			</li>
			<li class="selected">
				{{ site.siteName }} Club			</li>
			<li class="">
				<a href="{{ site.sitePath }}/credits/collectables">Colecionáveis</a>			</li>
    		<li class=" last">
				<a href="{{ site.sitePath }}/credits/pixels">Pixels</a>    		</li>
		</ul>
    </div>
</div>
{% endif %}

{% if session.currentPage == "me" %}
<div id="navi2-container" class="pngbg">
    <div id="navi2" class="pngbg clearfix">
		<ul>
			<li class="">
				<a href="{{ site.sitePath }}/me">Home</a>			</li>
    		<li class="">
				<a href="{{ site.sitePath }}/home/{{ playerDetails.getName() }}">Minha Página</a>    		</li>
			<li class="">
				<a href="{{ site.sitePath }}/profile">Configurações de conta</a>			</li>
				<li class="selected{% if gameConfig.getInteger('guides.group.id') == 0 %} last{% endif %}">
			<a href="{{ site.sitePath }}/club">{{ site.siteName }} Club</a>
				</li>
				{% if gameConfig.getInteger('guides.group.id') > 0 %}
				<li class=" last">
					<a href="{{ site.sitePath }}/groups/officialhabboguides">Habbo Guias</a>
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
	
							<h2 class="title">{{ site.siteName }} Club: torne-se VIP!							</h2>
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

	
							<h2 class="title">Benefits							</h2>
						<div id="habboclub-info" class="box-content">
    <p>{{ site.siteName }} Club é o nosso clube exclusivo para membros VIP - absolutamente nenhuma ralé é admitida! Os membros desfrutam de uma ampla gama de benefícios, incluindo roupas exclusivas, brindes e uma extensa lista de amigos. Veja abaixo todos os motivos brilhantes e atraentes para aderir.</p>
    <h3 class="heading">1. Roupas extra &amp; Acessórios</h3>
    <p class="content habboclub-clothing">Mostre seu novo status com uma variedade de roupas e acessórios extras, além de penteados e cores especiais.        <br /><br /><a href="{{ site.sitePath }}/credits/club/tryout">Try out {{ site.siteName }} Club clothes for yourself!</a>

    </p>
    <h3 class="heading">2. Mobis Gratuitos</h3>
    <p class="content habboclub-furni">Uma vez por mês, todos os meses, você receberá um mobi exclusivo do {{ site.siteName }} Club.</p>        
    <p class="content">Nota importante: o tempo do clube é cumulativo. Isso significa que se você interromper a assinatura e depois voltar, você recomeçará do mesmo lugar onde parou..</p>
    <h3 class="heading">3. Design de quartos exclusivos</h3>
    <p class="content">Layouts especiais de quartos, apenas para membros do {{ site.siteName }} Club. Perfeito para decorar com os seus mobis.</p>
    <p class="habboclub-room" />

    <h3 class="heading">4. Accesso e Quartos Especiais</h3>
    <p class="content">Evite as filas irritantes quando as salas estiverem carregando. E isso não é tudo: você também terá acesso a salas públicas exclusivas para HC.</p>
    <h3 class="heading">5. Páginas Atualizadas</h3>
    <p class="content">Entre no {{ site.siteName }} Club e diga adeus aos anúncios da página inicial! E isso significa que você também pode aproveitar ao máximo as skins e planos de fundo HC.</p>
    <h3 class="heading">6. Mais Amigos</h3>
    <p class="content habboclub-communicator">espaço para 600 pessoas! Agora você poderá adicionar ainda mais amigos ao seu console.</p>

    <h3 class="heading">7. Comandos Especiais</h3>
    <p class="content habboclub-commands right">Use o comando :chooser para ver uma lista clicável de todos os usuários na sala. Muito útil quando você quer sentar ao lado de seu companheiro ou expulsar um encrenqueiro.</p>
    <br />
    <p>Use o comando :furni para listar todos os itens de uma sala. Tudo está listado, até mesmo aqueles itens escondidos sorrateiramente.</p>
</div>
	
						
					</div>
				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>

</div>
<div id="column2" class="column">
			     		
				<div class="habblet-container ">		
						<div class="cbb clearfix hcred ">
	
							<h2 class="title">Minha Assinatura							</h2>
							

						<script src="{{ site.staticContentPath }}/web-gallery/static/js/habboclub.js" type="text/javascript"></script>
						{% include "base/hc_status.tpl" %}				
					</div>
				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
			 

			     		
				<div class="habblet-container ">		
						<div class="cbb clearfix lightbrown ">
	
							<h2 class="title">Presentes Mensais
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