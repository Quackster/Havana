
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">
<head>
	<meta http-equiv="content-type" content="text/html" />
	<title>{{ site.siteName }}: Monedas </title>

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
			<li class="selected">
				Monedas			</li>
			<li class="">
				<a href="{{ site.sitePath }}/credits/club">{{ site.siteName }} Club</a>			</li>
			<li class="">
				<a href="{{ site.sitePath }}/credits/collectables">Coleccionables</a>			</li>
    		<li class=" last">
				<a href="{{ site.sitePath }}/credits/pixels">Píxeles</a>    		</li>
		</ul>
    </div>
</div>
<div id="container">
	<div id="content" style="position: relative" class="clearfix">
    <div id="column1" class="column">
			     		
				<div class="habblet-container ">		
						<div class="cbb clearfix green ">
	
							<h2 class="title">Cómo obtener créditos
							</h2>
							
						<script src="{{ site.staticContentPath }}/web-gallery/static/js/credits.js" type="text/javascript"></script>
<p class="credits-countries-select">
Lo bueno de este servidor es que los acreditan un gratis, sí, gratis.No tendrá que gastar nada para obtener créditos para construir sus habitaciones favoritas.Simplemente averigüe utilizando los métodos a continuación para recibir créditos.
</p>
<ul id="credits-methods">
	<li id="credits-type-promo">
		<h4 class="credits-category-promo">Mejor manera</h4>
		<ul>
<li class="clearfix odd"><div id="method-3" class="credits-method-container">
					<div class="credits-summary" style="background-image: url({{ site.staticContentPath }}/c_images/album2705/logo_sms.png)">
						<h3>Be Online</h3>
						<p>Just by playing on the server daily you can receive coins!</p>
						
						<p class="credits-read-more" id="method-show-3" style="display: none">Leer más</p>
					</div>
					<div id="method-full-3" class="credits-method-full">
							<p><b>Reciba monedas al estar en línea </b> <br/> Debe estar en una habitación, pero todos los días, si espera 5 minutos, recibirá 120 créditos con solo estar activo.</p>
							<p>Esto sucede una vez cada 24 horas, así que si hace lo mismo mañana, ¡obtendrá otros 120 créditos!</p>
					</div>
					<script type="text/javascript">
					$("method-show-3").show();
					$("method-full-3").hide();
					</script>
				</div></li>
		</ul>
	</li>
	<li id="credits-type-quick_and_easy">
		<h4 class="credits-category-quick_and_easy">Otras maneras</h4>
		<ul>
				
<li class="clearfix odd"><div id="method-1" class="credits-method-container">
					<div class="credits-summary" style="background-image: url({{ site.staticContentPath }}/c_images/album2705/payment_habbo_prepaid.png)">
						<h3>Cupones</h3>
						<p>Puedes obtener códigos especiales para canjear el cupóns</p>
						
						<p class="credits-read-more" id="method-show-1" style="display: none">Leer más</p>
					</div>
					<div id="method-full-1" class="credits-method-full">
							<p>Redime su código de cupón en su bolso de hotel, o en esta página, ¡y obtendrá sus monedas de inmediato!</p>
					</div>
					<script type="text/javascript">
					$("method-show-1").show();
					$("method-full-1").hide();
					</script>
				</div></li>
		</ul>
	</li>
	 <li id="credits-type-other">
		<h4 class="credits-category-quick_and_easy">Otras formas</h4>
		<ul>
				
<li class="clearfix odd"><div id="method-2" class="credits-method-container">
					<div class="credits-summary" style="background-image: url({{ site.staticContentPath }}/c_images/album2705/byesw_hand.png)">
						<div class="credits-tools">
								<a  class="new-button" id="warn-clear-hand-button" href="#" onclick="warnClearHand()"><b>Restablecer</b><i></i></a>
							
						</div>
						<h3>Restablecer la mano</h3>
						<p>¿Mano virtual demasiado lleno de muebles?Haga clic aquí para restablecerla.</p>
						
						<!-- <p class="credits-read-more" id="method-show-2" style="display: none">Leer más</p>
					</div>
					<div id="method-full-2" class="credits-method-full">
							<p>Simplemente haga clic en el botón para restablecer su mano.</p>
							<p><strong>Advertencia: </strong>Esta acción no se puede deshacer.</p>
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
							document.getElementById('method-2').insertAdjacentHTML('afterbegin', '<div id="' + responseName + '" style="border-radius:5px; background: #3ba800; padding: 8px; color: #FFFFFF;">La mano ha sido reiniciada.</br></div><br />');
							document.getElementById(responseWarnName).remove();
						}
					}
					
					function warnClearHand() {
						var responseName = "warn-wiped-hand";
						
						if (document.getElementById(responseWarnName) == null) {
							document.getElementById('method-2').insertAdjacentHTML('afterbegin', '<div id="' + responseWarnName + '" style="border-radius:5px; background: #f29400; padding: 8px; color: #FFFFFF;">¿Está seguro? <strong>¡Esto no se puede deshacer!</strong><a class="new-button" id="confirm-clear-hand-button" href="#" onclick="clearHand()"><b>¡Sí, claro ahora!</b><i></i></a><br /><br /></div><br />');
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
							document.getElementById('method-2').insertAdjacentHTML('afterbegin', '<div id="' + responseWarnName + '" style="border-radius:5px; background: red; padding: 8px; color: #FFFFFF;">Debes iniciar sesión para hacer esto<br /></div><br />');
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
L10N.put("credits.navi.read_more", "Leer más");
L10N.put("credits.navi.close_fulltext", "Cerrar instrucciones");
PaymentMethodHabblet.init();
</script>
	
						
					</div>

				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
			 

</div>
<div id="column2" class="column">
			     		
				<div class="habblet-container ">		
						<div class="cbb clearfix brown ">
	
							<h2 class="title">Tu monedero						</h2>
							{% if session.loggedIn == false %}
								<div class="box-content">Necesitas iniciar sesión para ver el bolso</div>
							{% else %}
					
		<div id="purse-habblet">
			<form method="post" action="{{ site.sitePath }}/credits" id="voucher-form">

			<ul>
			<li class="even icon-purse">
			<div>You Currently Have:</div>
			<span class="purse-balance-amount">{{ playerDetails.credits }} Monedas</span>
			<div class="purse-tx"><a href="{{ site.sitePath }}/credits/history">Transacciones de cuenta</a></div>
			</li>

			<li class="odd">

			<div class="box-content">
			<div>Ingrese el código del cupón (sin espacios):</div>
			<input type="text" name="voucherCode" value="" id="purse-habblet-redeemcode-string" class="redeemcode" />
			<a href="#" id="purse-redeemcode-button" class="new-button purse-icon" style="float:left"><b><span></span>OK</b><i></i></a>
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
	
							<h2 class="title">¿Qué son {{ site.siteName }} Monedas?							</h2>

						<div id="credits-promo" class="box-content credits-info">
    <div class="credit-info-text clearfix">
        <img class="credits-image" src="{{ site.staticContentPath }}/web-gallery/v2/images/credits/poor.png" alt="" width="77" height="105" />
        <p class="credits-text">{{ site.siteName }} Monedas son la moneda del hotel.Puede usarlos para comprar todo tipo de cosas, desde patos de goma y sofás, hasta membresía VIP, jukeboxes y teletransportes.</p>
    </div>
    <p class="credits-text-2">Todas las formas legítimas de obtener {{ site.siteName }} monedas son a la izquierda. Recuerde: las {{ site.siteName }} monedas son siempre y siempre serán gratuitas.</p>
</div>
	
						
					</div>

				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
			 
				<div class="habblet-container ">		
						<div class="cbb clearfix blue ">
	
							<h2 class="title">¡Siempre pida permiso primero!
							</h2>
						<div id="credits-safety" class="box-content credits-info">
    <div class="credit-info-text clearfix"><img class="credits-image" src="{{ site.sitePath }}/web-gallery/v2/images/credits_permission.png" width="114" height="136"/><p class="credits-text">Siempre solicite permiso a su padre o tutor antes de comprar Habbo Monedas.Si no hace esto y el pago se cancela o rechazó, será prohibido permanentemente.</p></div>
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