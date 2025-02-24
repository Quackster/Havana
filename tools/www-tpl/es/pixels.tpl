
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">
<head>
	<meta http-equiv="content-type" content="text/html" />
	<title>{{ site.siteName }}: Píxeles </title>

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



<meta name="description" content="Únase al lugar de reunión virtual más grande del mundo donde pueda conocer y hacer amigos.¡Diseñe sus propias habitaciones, recolecte muebles geniales, organice fiestas y mucho más!¡Crea tu {{ site.siteName }} gratis hoy!" />
<meta name="keywords" content="{{ site.siteName }}, virtual, mundo, unirse, grupos, foros, juegos, juegos, en línea, amigos, adolescentes, coleccionar, redes sociales, crear, recopilar, conectar, muebles, virtuales, productos, compartir, insignias, sociales, redes, lugar de reunión, seguridad, música, celebridad, visitas de celebridades" />

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
				<a href="{{ site.sitePath }}/credits">Monedas</a>			</li>
			<li class="">
				<a href="{{ site.sitePath }}/credits/club">{{ site.siteName }} Club</a>			</li>
			<li class="">
				<a href="{{ site.sitePath }}/credits/collectables">Coleccionables</a>			</li>
    		<li class="selected last">
				<a href="{{ site.sitePath }}/credits/pixels">Píxeles</a>    		</li>
		</ul>
    </div>
</div>
<div id="container">
	<div id="content" style="position: relative" class="clearfix">
   
    <div id="column1" class="column">
			     		
				<div class="habblet-container ">		
						<div class="cbb clearfix pixelblue ">
	
							<h2 class="title">¡Aprende a conseguir tus píxeles y disfruta!
							</h2>
						<div class="pixels-infobox-container">
    <div class="pixels-infobox-text">
            <h3>Puedes ganar píxeles de varias maneras:</h3>
            <ul>
                <li><p>1. Inicie sesión en Habbo una vez al día</p></li>
                <li><p>2. Obtenga recompensado por pasar tiempo todos los días en Habbo, ¡cuanto más tiempo se mantenga, más gane!</p></li>
                <li><p>3. Completar logros, trabajar como guía y respeto a otros usuarios</p></li>
                <li><p>4. Únete al club Habbo!</p></li>
            </ul>
            <p>¿Cómo gastar?¡Mira el catálogo y la nueva tienda de píxeles!</p>
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
	
							<h2 class="title">Alquila algunas cosas!						</h2>

						<div id="pixels-info" class="box-content pixels-info">
    <div class="pixels-info-text clearfix">
        <img class="pixels-image" src="{{ site.staticContentPath }}/web-gallery/v2/images/activitypoints/pixelpage_effectmachine.png" alt=""/>
        <p class="pixels-text">Cree una habitación genial, con estos efectos de la sala de balanceo puede ampliar la experiencia de sus amigos.</p>
    </div>
</div>
	
						
					</div>
				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>

			 

			     		
				<div class="habblet-container ">		
						<div class="cbb clearfix pixellightblue ">
	
							<h2 class="title">Efectos?							</h2>
						<div id="pixels-info" class="box-content pixels-info">
    <div class="pixels-info-text clearfix">
        <img class="pixels-image" src="{{ site.staticContentPath }}/web-gallery/v2/images/activitypoints/pixelpage_personaleffect.png" alt=""/>
        <p class="pixels-text">Sintonice su personaje con efectos geniales que se ajusten a la ocasión.¿Quieres volar con la alfombra roja o estar en el centro de atención?Ahora es tu oportunidad</p>
    </div>

</div>
	
						
					</div>
				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
			 

			     		
				<div class="habblet-container ">		
						<div class="cbb clearfix pixeldarkblue ">
	
							<h2 class="title">Ofertas calientes?							</h2>
						<div id="pixels-info" class="box-content pixels-info">
    <div class="pixels-info-text clearfix">

        <img class="pixels-image" src="{{ site.staticContentPath }}/web-gallery/v2/images/activitypoints/pixelpage_discounts.png" alt=""/>
        <p class="pixels-text">Ganing Píxeles le brinda descuentos en una amplia variedad de muebles: ¡averigüe cuánto puede ahorrar en nuestra sección de ofertas!</p>
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