
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="pt-br" lang="pt-br">
<head>
	<meta http-equiv="content-type" content="text/html" />
	<title>{{ site.siteName }}:  </title>

<script type="text/javascript">
var andSoItBegins = (new Date()).getTime();
</script>
    <link rel="shortcut icon" href="{{ site.staticContentPath }}/web-gallery/v2/favicon.ico" type="image/vnd.microsoft.icon" />
    <link rel="alternate" type="application/rss+xml" title="{{ site.siteName }}: RSS" href="{{ site.sitePath }}/articles/rss.xml" />
<script src="{{ site.staticContentPath }}/web-gallery/static/js/visual.js" type="text/javascript"></script>

<script src="{{ site.staticContentPath }}/web-gallery/static/js/libs.js" type="text/javascript"></script>
<script src="{{ site.staticContentPath }}/web-gallery/static/js/common.js" type="text/javascript"></script>
<script src="{{ site.staticContentPath }}/web-gallery/static/js/fullcontent.js" type="text/javascript"></script>
<script src="{{ site.staticContentPath }}/web-gallery/static/js/libs2.js" type="text/javascript"></script>
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
<body id="faq" class="plain-template">
<script src="{{ site.staticContentPath }}/web-gallery/static/js/faq.js" type="text/javascript"></script>
<div id="faq" class="clearfix">
<div id="faq-header" class="clearfix"><img src="{{ site.staticContentPath }}/web-gallery/v2/images/faq/faq_header.png" /><form method="post" action="{{ site.sitePath }}/help/faqsearch" class="search-box"><input type="text" id="faq-search" name="query" class="search-box-query search-box-onfocus" size="50" value="Search..."/><input type="submit" value="" title="Search" class="search" /></form></div>
<div id="faq-container" class="clearfix">
<div id="faq-category-list">
<ul class="faq">
<li><a href="{{ site.sitePath }}/help/1" name=""><span class="faq-link">Contate-nos</span></a></li>
</ul>
</div>
<div id="faq-category-content" class="clearfix" >
<p class="faq-category-description"></p>
<h4 id="faq-item-header-2" class="faq-item-header faq-toggle "><span class="faq-toggle selected" id="faq-header-text-2">How do I contact {{ site.siteName }}?</span></h4>
	<div id="faq-item-content-2" class="faq-item-content clearfix">
	    <div class="faq-item-content clearfix">Por favor use a <a href="{{ site.sitePath }}/iot/go">Ferramenta de Ajuda</a> para falar conosco!</div>
	<div class="faq-close-container">
	<div id="faq-close-button-2" class="faq-close-button clearfix faq-toggle" style="display:none">Fechar FAQ <img id="faq-close-image-2" class="faq-toggle" src="{{ site.staticContentPath }}/web-gallery/v2/images/faq/close_btn.png"/></div>
	</div>
	</div>

	<script type="text/javascript">
	    
	    $("faq-close-button-2").show();
	</script>
<h4 id="faq-item-header-3" class="faq-item-header faq-toggle "><span class="faq-toggle selected" id="faq-header-text-3">Will sending my issue twice get a faster reply?</span></h4>
	<div id="faq-item-content-3" class="faq-item-content clearfix">
	    <div class="faq-item-content clearfix">Enviar mais de um e-mail irá atrasar a equipe de Suporte ao Jogador, pois eles terão mais e-mails para ler. Se você não recebeu resposta após uma semana, verifique sua pasta de spam/lixo eletrônico. Se ainda não houver resposta, deve ter havido uma falha técnica e você deverá enviar seu e-mail novamente.</div>
	<div class="faq-close-container">
	<div id="faq-close-button-3" class="faq-close-button clearfix faq-toggle" style="display:none">Fechar FAQ <img id="faq-close-image-3" class="faq-toggle" src="{{ site.staticContentPath }}/web-gallery/v2/images/faq/close_btn.png"/></div>
	</div>
	</div>

	<script type="text/javascript">
	    $("faq-item-content-3").hide();
	    $("faq-close-button-3").show();
	</script>
<script type="text/javascript">
    FaqItems.init();
    SearchBoxHelper.init();
</script>
</div>

</div>

<div id="faq-footer" class="clearfix"><p><a href="http://localhost/" target="_self">Homepage</a> | <a href="http://localhost/papers/disclaimer" target="_self">Disclaimer</a> | <a href="http://localhost/papers/privacy" target="_self">Política de privacidade</a> | <a href="http://localhost/help/1" target="_new">Contact Us</a></p>
		<p>Este é um fansite não oficial do Habbo para ver como o Habbo era em 2009.<br />HABBO é uma marca registrada da Sulake Corporation. Todos os direitos reservados Para seus respectivos proprietarios.</p>
	</div>			</div>
        </div>
    </div>
	</div>
<script type="text/javascript">
HabboView.run();
</script>


</body>
</html>