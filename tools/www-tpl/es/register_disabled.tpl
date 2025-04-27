
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">
<head>
	<meta http-equiv="content-type" content="text/html" />
	<title>{{ site.siteName }}: Registro </title>

<script type="text/javascript">
var andSoItBegins = (new Date()).getTime();
</script>
    <link rel="shortcut icon" href="{{ site.staticContentPath }}/web-gallery/v2/favicon.ico" type="image/vnd.microsoft.icon" />
    <link rel="alternate" type="application/rss+xml" title="{{ site.siteName }}: RSS" href="{{ site.sitePath }}/rss" />
	
<script src="{{ site.staticContentPath }}/web-gallery/static/js/libs2.js" type="text/javascript"></script>
<script src="{{ site.staticContentPath }}/web-gallery/static/js/visual.js" type="text/javascript"></script>
<script src="{{ site.staticContentPath }}/web-gallery/static/js/libs.js" type="text/javascript"></script>
<script src="{{ site.staticContentPath }}/web-gallery/static/js/common.js" type="text/javascript"></script>
<script src="{{ site.staticContentPath }}/web-gallery/static/js/fullcontent.js" type="text/javascript"></script>
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/style.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/buttons.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/boxes.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/tooltips.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/process.css" type="text/css" />

<script type="text/javascript">
document.habboLoggedIn = false;
var habboName = null;
var ad_keywords = "";
var habboReqPath = "{{ site.sitePath }}";
var habboStaticFilePath = "{{ site.staticContentPath }}/web-gallery";
var habboImagerUrl = "{{ site.staticContentPath }}/habbo-imaging/";
var habboPartner = "";
window.name = "habboMain";
if (typeof HabboClient != "undefined") { HabboClient.windowName = "client"; }


</script>

<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/registration.css" type="text/css" />
<script src="{{ site.staticContentPath }}/web-gallery/static/js/registration.js" type="text/javascript"></script>
    <script type="text/javascript">
        L10N.put("register.tooltip.name", "Su nombre puede contener letras en minúsculas y mayúsculas, números y los caracteres -=?!@:.");
        L10N.put("register.tooltip.password", "Su contraseña debe tener al menos 6 caracteres y debe contener letras y números.");
        L10N.put("register.error.password_required", "porfavor ingrese una contraseña");
        L10N.put("register.error.password_too_short", "Su contraseña debe tener al menos seis caracteres");
        L10N.put("register.error.password_numbers", "Debe tener al menos un número o carácter especial en su contraseña.");
        L10N.put("register.error.password_letters", "Debe tener al menos una letra minúscula o en mayúsculas en su contraseña.");
        L10N.put("register.error.retyped_password_required", "Por favor re ingrese su contraseña");
        L10N.put("register.error.retyped_password_notsame", "Sus contraseñas no coinciden, intente nuevamente");
        L10N.put("register.error.retyped_email_required", "Vuelva a escribir su correo electrónico");
        L10N.put("register.error.retyped_email_notsame", "Los correos electrónicos no coinciden");
        L10N.put("register.tooltip.namecheck", "Haga clic aquí para verificar que su nombre sea gratuito.");
        L10N.put("register.tooltip.retypepassword", "Por favor re ingrese su contraseña."); 
        L10N.put("register.tooltip.personalinfo.disabled", "Elija primero su nombre {{ site.siteName }} (personaje).");
        L10N.put("register.tooltip.namechecksuccess", "¡Felicidades!El nombre está disponible.");
        L10N.put("register.tooltip.passwordsuccess", "Su contraseña ahora es segura.");
        L10N.put("register.tooltip.passwordtooshort", "La contraseña que ha elegido es demasiado corta.");
        L10N.put("register.tooltip.passwordnotsame", "Contraseña no es la misma, por favor vuelva a verla.");
        L10N.put("register.tooltip.invalidpassword", "La contraseña que ha elegido no es válida, elija una nueva contraseña.");
        L10N.put("register.tooltip.email", "Por favor, introduzca su dirección de correo electrónico.Debe activar su cuenta utilizando esta dirección, así que use su dirección real.");
        L10N.put("register.tooltip.retypeemail", "Por favor reintrocuzca su correo electronico.");
        L10N.put("register.tooltip.invalidemail", "Por favor, introduce una dirección de correo electrónico válida.");
        L10N.put("register.tooltip.emailsuccess", "Has proporcionado una dirección de correo electrónico válida, ¡gracias!");
        L10N.put("register.tooltip.emailnotsame", "Su correo electrónico retenido no coincide.");
        L10N.put("register.tooltip.enterpassword", "Porfavor ingrese una contraseña.");
        L10N.put("register.tooltip.entername", "Ingrese un nombre para su {{ site.siteName }}.");
        L10N.put("register.tooltip.enteremail", "Por favor, introduzca su dirección de correo electrónico.");
        L10N.put("register.tooltip.enterbirthday", "Por favor, dé su fecha de nacimiento: necesita esto más tarde para obtener recordatorios de contraseña, etc.");
        L10N.put("register.tooltip.acceptterms", "Acepte los términos y condiciones");
        L10N.put("register.tooltip.invalidbirthday", "Por favor suministre una fecha de nacimiento válida");
        L10N.put("register.tooltip.emailandparentemailsame","El correo electrónico de sus padres y su correo electrónico no pueden ser el mismo, por favor proporcione uno diferente..");
        L10N.put("register.tooltip.entercaptcha","Ingrese el código.");
        L10N.put("register.tooltip.captchavalid","Codigo invalido.");
        L10N.put("register.tooltip.captchainvalid","Código no válido, por favor vuelva a intentarlo.");
		L10N.put("register.error.parent_permission","Necesitas contarles a tus padres sobre este servicio.");

        RegistrationForm.parentEmailAgeLimit = -1;
        L10N.put("register.message.parent_email_js_form", "<div\>\n\t<div class=\"register-label\"\>Debido a que tiene menos de 16 años y está de acuerdo con las pautas de mejores prácticas de la industria, requerimos la dirección de correo electrónico de su padre o tutor.</div\>\n\t<div id=\"parentEmail-error-box\"\>\n        <div class=\"register-error\"\>\n            <div class=\"rounded rounded-blue\"  id=\"parentEmail-error-box-container\"\>\n                <div id=\"parentEmail-error-box-content\"\>\n                    Por favor, introduzca su dirección de correo electrónico.\n                </div\>\n            </div\>\n        </div\>\n\t</div\>\n\t<div class=\"register-label\"\><label for=\"register-parentEmail-bubble\"\>Parent or guardian\'s email address</label\></div\>\n\t<div class=\"register-label\"\><input type=\"text\" name=\"bean.parentEmail\" id=\"register-parentEmail-bubble\" class=\"register-text-black\" size=\"15\" /\></div\>\n\n\n</div\>");

        RegistrationForm.isCaptchaEnabled = true;
         L10N.put("register.message.captcha_js_form", "<div\>\n  <div id=\"recaptcha_image\" class=\"register-label\"\>\n    <img id=\"captcha\" src=\"{{ site.sitePath }}/captcha.jpg?t=1538907557&register=1\" alt=\"\" width=\"200\" height=\"60\" /\>\n  </div\>\n  <div class=\"register-label\" id=\"captcha-reload\"\>\n    <img src=\"{{ site.staticContentPath }}/web-gallery/v2/images/shared_icons/reload_icon.gif\" width=\"15\" height=\"15\"/\>\n    <a href=\"#\"\>¡No puedo leer el código!Por favor, dame otro.</a\>\n  </div\>\n  <div class=\"register-label\"\><label for=\"register-captcha-bubble\"\>Escriba el código de seguridad que se muestra en la imagen de arriba</label\></div\>\n  <div class=\"register-input\"\><input type=\"text\" name=\"bean.captchaResponse\" id=\"register-captcha-bubble\" class=\"register-text-black\" value=\"\" size=\"15\" /\></div\>\n</div\>");

        L10N.put("register.message.age_limit_ban", "<div\>\n<p\>\nLo siento, pero no puedes registrarte, porque eres demasiado joven.Si ingresó una fecha de nacimiento incorrecta por accidente, intente nuevamente en unas pocas horas.\n</p\>\n\n<p style=\"text-align:left\"\>\n<input type=\"button\" class=\"submit\" id=\"register-parentEmail-cancel\" value=\"Cancel\" onclick=\"RegistrationForm.cancel(\'?ageLimit=true\')\" /\>\n</p\>\n</div\>");
        RegistrationForm.ageLimit = -1;
        RegistrationForm.banHours = 24;
        HabboView.add(function() {
            Rounder.addCorners($("register-avatar-editor-title"), 4, 4, "rounded-container");
			{% if captchaInvalid == false %}
            RegistrationForm.init(true);
			{% else %}
			RegistrationForm.init(false);
			{% endif %}
                    });

        HabboView.add(function() {
            var swfobj = new SWFObject("{{ site.sitePath }}/flash/HabboRegistration.swf", "habboreg", "435", "400", "8");
            swfobj.addParam("base", "{{ site.sitePath }}/flash/");
            swfobj.addParam("wmode", "opaque");
            swfobj.addParam("AllowScriptAccess", "always");
            swfobj.addVariable("figuredata_url", "{{ site.sitePath }}/xml/figuredata.xml");
            swfobj.addVariable("draworder_url", "{{ site.sitePath }}/xml/draworder.xml");
            swfobj.addVariable("localization_url", "{{ site.sitePath }}/xml/figure_editor.xml");
            swfobj.addVariable("habbos_url", "{{ site.sitePath }}/xml/promo_habbos_v2.xml");
            swfobj.addVariable("figure", "{{ registerFigure }}");
            swfobj.addVariable("gender", "{{ registerGender }}");

            swfobj.addVariable("showClubSelections", "0");

            swfobj.write("register-avatar-editor");
            window.habboreg = $("habboreg"); // for MSIE and Flash Player 8
        });

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
<body id="register" class="process-template secure-page">

<div id="overlay"></div>

<div id="container">
	<div class="cbb process-template-box clearfix">
		<div id="content">

			{% include "base/frontpage_header.tpl" %}
			<div id="process-content">	        	
			<div id="column1" class="column">


			     		<p>El registro está actualmente deshabilitado, vuelva otra vez.</p>			
		
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>

		</div>	 
</div>
</div>
</div>
</div>

<!--[if lt IE 7]>
<script type="text/javascript">
Pngfix.doPngImageFix();
</script>
<![endif]-->

{% include "base/footer.tpl" %}

<script type="text/javascript">
HabboView.run();
</script>


</body>
</html>