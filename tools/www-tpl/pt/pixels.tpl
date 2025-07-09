
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="pt-br" lang="pt-br">
<head>
	<meta http-equiv="content-type" content="text/html" />
	<title>{{ site.siteName }}: Pixels </title>

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
				<a href="{{ site.sitePath }}/credits">Coins</a>			</li>
			<li class="">
				<a href="{{ site.sitePath }}/credits/club">{{ site.siteName }} Club</a>			</li>
			<li class="">
				<a href="{{ site.sitePath }}/credits/collectables">Colecionáveis</a>			</li>
    		<li class="selected last">
				<a href="{{ site.sitePath }}/credits/pixels">Pixels</a>    		</li>
		</ul>
    </div>
</div>
<div id="container">
	<div id="content" style="position: relative" class="clearfix">
   
    <div id="column1" class="column">
			     		
				<div class="habblet-container ">		
						<div class="cbb clearfix pixelblue ">
	
							<h2 class="title">Aprenda como conseguir pixels!
							</h2>
						<div class="pixels-infobox-container">
    <div class="pixels-infobox-text">
            <h3>Você pode ganhar pixels de muitas maneiras:</h3>
            <ul>
                <li><p>1. Entre no hotel pelo menos uma vez por dia.</p></li>
                <li><p>2. Seja recompensado por passar algum tempo todos os dias no Habbo - quanto mais tempo você ficar, mais você ganha!</p></li>
                <li><p>3. Conclua conquistas, trabalhe como um guia e respeite os outros usuários</p></li>
                <li><p>4. Entre no {{ site.siteName }} Club</p></li>
            </ul>
            <p>Como usar? <br>Entre na seção pixels do catálogo!</p>
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
	
							<h2 class="title">Alugue mobis!							</h2>

						<div id="pixels-info" class="box-content pixels-info">
    <div class="pixels-info-text clearfix">
        <img class="pixels-image" src="{{ site.staticContentPath }}/web-gallery/v2/images/activitypoints/pixelpage_effectmachine.png" alt=""/>
        <p class="pixels-text">Crie uma sala legal, com esses efeitos de sala de balanço você pode expandir a experiência de seus amigos.</p>
    </div>
</div>
	
						
					</div>
				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>

			 

			     		
				<div class="habblet-container ">		
						<div class="cbb clearfix pixellightblue ">
	
							<h2 class="title">Efeitos!							</h2>
						<div id="pixels-info" class="box-content pixels-info">
    <div class="pixels-info-text clearfix">
        <img class="pixels-image" src="{{ site.staticContentPath }}/web-gallery/v2/images/activitypoints/pixelpage_personaleffect.png" alt=""/>
        <p class="pixels-text">Tune your character with cool effects that fit the occasion. Do you want to fly away with the red carpet or be in the spotlight? Now is your chance</p>
    </div>

</div>
	
						
					</div>
				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
			 

			     		
				<div class="habblet-container ">		
						<div class="cbb clearfix pixeldarkblue ">
	
							<h2 class="title">Ofertas Especiais?							</h2>
						<div id="pixels-info" class="box-content pixels-info">
    <div class="pixels-info-text clearfix">

        <img class="pixels-image" src="{{ site.staticContentPath }}/web-gallery/v2/images/activitypoints/pixelpage_discounts.png" alt=""/>
        <p class="pixels-text">Ganhar Pixels dá descontos em uma grande variedade de Mobis - descubra quanto você pode economizar em nossa seção de ofertas!</p>
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