
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">
<head>
	<meta http-equiv="content-type" content="text/html" />
	<title>{{ site.siteName }}: My preferences </title>

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

{% include "../base/header.tpl" %}

<div id="content-container">

	<div id="navi2-container" class="pngbg">
    <div id="navi2" class="pngbg clearfix">
		<ul>
			<li class="">
				<a href="{{ site.sitePath }}/me">Inicio</a>			</li>
    		<li class="">
				<a href="{{ site.sitePath }}/home/{{ playerDetails.getName() }}">Mi Página</a>    		</li>
    		<li class="selected">
				Configuraciones de la cuenta    		</li>
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

<h2 class="title">Cuenta</h2>
<div class="box-content">
            <div id="settingsNavigation">
            <ul>
				<li><a href="{{ site.sitePath }}/profile?tab=1">Mi Ropa</a>
                </li><li class="selected">Mis Preferencias
				{% if accountActivated %}
                </li><li><a href="{{ site.sitePath }}/profile?tab=3">Mi Email</a>
				{% else %}
				</li><li><a href="{{ site.sitePath }}/profile/verify">Cambio y verificación de correo electrónico</a>
				{% endif %}
                </li><li><a href="{{ site.sitePath }}/profile?tab=4">Mi Contraseña</a>
                </li><li><a href="{{ site.sitePath }}/profile?tab=5">Gestión de Amigos</a>
								</li><li><a href="{{ site.sitePath }}/profile?tab=6">Configuración Comercial</a>
                </li>            </ul>
            </div>
</div></div>
    {% include "profile/profile_widgets/join_club.tpl" %}
</div>
    <div class="habblet-container " style="float:left; width: 560px;">
        <div class="cbb clearfix settings">

            <h2 class="title">Cambia tu perfil</h2>
            <div class="box-content">

{% if settingsSavedAlert %}
<div class="rounded rounded-green">Configuraciones de la cuenta actualizados con éxito.<br />	</div><br />
{% endif %}

<form action="{{ site.sitePath }}/profile/profileupdate" method="post" id="profileForm">
<input type="hidden" name="tab" value="2" />
<input type="hidden" name="__app_key" value="HavanaWeb" />


<h3>Tu lema</h3>

<p>
Su lema es lo que otros {{ site.siteName }}s verán en su página de inicio de {{ site.siteName }} y debajo de su {{ site.siteName }} en el hotel.</p>
{% autoescape 'html' %}
<p>
<span class="label">Lema:</span>
<input type="text" name="motto" size="32" maxlength="32" value="{{ playerDetails.motto }}" id="avatarmotto" />
</p>
{% endautoescape %}

<h3>Preferencia del cliente</h3>

<p>
El complemento a usar al hacer clic en los botones de ir a hotel (ir a la habitación, etc.)<br />
<label><input type="radio" name="clientpreference" value="SHOCKWAVE" {{ SHOCKWAVEenabled }} />Shockwave</label>

<label><input type="radio" name="clientpreference" value="FLASH" {{ FLASHenabled }} />Flash</label>
</p>

<h3>Tu pagina</h3>

<p>
¿Quién puede ver su página de inicio?:<br />
<label><input type="radio" name="visibility" value="EVERYONE" {{ profileVisibleEnabled }} />Visible a todos</label>

<label><input type="radio" name="visibility" value="NOBODY" {{ profileVisibleDisabled }} />Invisible a todos</label>
</p>

<!-- <h3>Alertas de correo electrónico</h3>

<p>
<label><input type="checkbox" name="emailMiniMailAlertEnabled" value="true" checked="checked" />Recibir notificaciones en mensajes minoresel.</label> <br />
<label><input type="checkbox" name="emailFriendRequestAlertEnabled" value="true" checked="checked" />Recibir notificaciones sobre solicitudes de amistad.</label>
</p>
-->
<h3>Filtro de palabras</h3>
<p>
<label><input type="checkbox" name="wordFilterSetting" value="false" {{ wordFilterSetting }}> Gire el filtro de lenguaje malo def.</label>
</p>

<h3>Solicitud de amistads</h3>
<p>
<label><input type="checkbox" name="allowFriendRequests" value="true" {{ allowFriendRequests }}> Solicitudes de amistad habilitadas</label>
</p>

<h3>Amigo sigue</h3>
<p>
Capacidad para que otras personas sigan a los usuarios entre habitaciones:<br />
<label><input type="radio" name="followFriendSetting" value="true" {{ followFriendEnabled }} />Amigos</label>
<label><input type="radio" name="followFriendSetting" value="false" {{ followFriendDisabled }} />Nadie</label>
</p>

<h3>Estado en línea</h3>
<p>
Seleccione quién puede ver su estado en línea:<br />
<label><input type="radio" name="showOnlineStatus" value="true" {{ onlineStatusEnabled }} />Todos</label>
<label><input type="radio" name="showOnlineStatus" value="false" {{ onlineStatusDisabled }} />Nadie</label>
</p>

<div class="settings-buttons">
<a href="#" class="new-button" style="display: none" id="profileForm-submit"><b>Guardar cambios</b><i></i></a>
<noscript><input type="submit" value="Save changes" name="save" class="submit" /></noscript>
</div>

</form>

<script type="text/javascript">
$("profileForm-submit").observe("click", function(e) { e.stop(); $("profileForm").submit(); });
$("profileForm-submit").show();

$("profileForm-genToken").observe("click", function(e) { e.stop();
	new Ajax.Request(habboReqPath + "/habblet/ajax/token_generate", {
        onComplete: function(response) {
            document.getElementById('authenticationToken').value = response.responseText;
		}
    });
 });
$("profileForm-genToken").show();

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