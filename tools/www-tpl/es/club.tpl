
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">
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

{% if session.currentPage == "credits" %}
<div id="navi2-container" class="pngbg">
    <div id="navi2" class="pngbg clearfix">
		<ul>
			<li class="">
				<a href="{{ site.sitePath }}/credits">Monedas</a>			</li>
			<li class="selected">
				{{ site.siteName }} Club			</li>
			<li class="">
				<a href="{{ site.sitePath }}/credits/collectables">Coleccionables</a>			</li>
    		<li class=" last">
				<a href="{{ site.sitePath }}/credits/pixels">Píxeles</a>    		</li>
		</ul>
    </div>
</div>
{% endif %}

{% if session.currentPage == "me" %}
<div id="navi2-container" class="pngbg">
    <div id="navi2" class="pngbg clearfix">
		<ul>
			<li class="">
				<a href="{{ site.sitePath }}/me">Inicio</a>			</li>
    		<li class="">
				<a href="{{ site.sitePath }}/home/{{ playerDetails.getName() }}">Mi Página</a>    		</li>
			<li class="">
				<a href="{{ site.sitePath }}/profile">Configuraciones de la cuenta</a>			</li>
				<li class="selected{% if gameConfig.getInteger('guides.group.id') == 0 %} last{% endif %}">
			<a href="{{ site.sitePath }}/club">{{ site.siteName }} Club</a>
				</li>
				{% if gameConfig.getInteger('guides.group.id') > 0 %}
				<li class=" last">
					<a href="{{ site.sitePath }}/groups/officialhabboguides">Guías de Habbo</a>
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
	
							<h2 class="title">{{ site.siteName }} Club: ¡Conviértete en un VIP!							</h2>
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
    <p>{{ site.siteName }} Club es nuestro club VIP solo para miembros, ¡absolutamente ningún riff-raff admitió!Los miembros disfrutan de una amplia gama de beneficios, que incluyen ropa exclusiva, regalos gratuitos y una lista de amigos extendidos.Vea a continuación todas las razones brillantes y atractivas para unirse.</p>
    <h3 class="heading">1. Ropa y accesorios adicionales</h3>
    <p class="content habboclub-clothing">Muestre su nuevo estado con una variedad de ropa y accesorios adicionales, junto con peinados y colores especiales.        <br /><br /><a href="{{ site.sitePath }}/credits/club/tryout">¡Prueba la ropa de {{ site.siteName }} Club ahora!</a>

    </p>
    <h3 class="heading">2. Muebles gratis</h3>
    <p class="content habboclub-furni">Una vez al mes, cada mes, obtendrá una pieza exclusiva de muebles de {{ site.siteName }} club.</p>        
    <p class="content">Nota importante: el tiempo del club es acumulativo.Esto significa que si tiene un descanso en la membresía, y luego se unirá, comenzará en el mismo lugar que dejó.</p>
    <h3 class="heading">3. Diseños de habitación exclusivos</h3>
    <p class="content">Diseños especiales de habitaciones, solo para miembros del {{ site.siteName }} club.¡Perfecto para mostrar tu nuevo mobiliario!</p>
    <p class="habboclub-room" />

    <h3 class="heading">4. Acceso a todas las areas</h3>
    <p class="content">Salta las colas molestas cuando las habitaciones se están cargando.Y eso no es todo, también tendrá acceso a habitaciones públicas solo HC.</p>
    <h3 class="heading">5. Actualizaciones de la página de inicio</h3>
    <p class="content">¡Únete al {{ site.siteName }} club y dile adiós a los anuncios de la página de inicio!Y esto significa que también puedes aprovechar al máximo las pieles y fondos HC.</p>
    <h3 class="heading">6. Más amigos</h3>
    <p class="content habboclub-communicator">¡600 personas!Ahora que son muchos amigos, sin embargo, lo miras.Más de lo que puedes pinchar con un palo de tamaño mediano o un palo pequeño de gran tamaño.</p>

    <h3 class="heading">7. Comandos especiales</h3>
    <p class="content habboclub-commands right">Use el comando: Chooser para ver una lista de clics de todos los usuarios en la sala.Bastante a mano cuando quieras sentarte junto a tu pareja, o echar un alborotador.</p>
    <br />
    <p>Use el comando: Furni para enumerar todos los elementos en una habitación.Todo está en la lista, incluso esos elementos furtivamente ocultos.</p>
</div>
	
						
					</div>
				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>

</div>
<div id="column2" class="column">
			     		
				<div class="habblet-container ">		
						<div class="cbb clearfix hcred ">
	
							<h2 class="title">Mi membresía							</h2>
							

						<script src="{{ site.staticContentPath }}/web-gallery/static/js/habboclub.js" type="text/javascript"></script>
						{% include "base/hc_status.tpl" %}				
					</div>
				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
			 

			     		
				<div class="habblet-container ">		
						<div class="cbb clearfix lightbrown ">
	
							<h2 class="title">Regalos mensuales
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