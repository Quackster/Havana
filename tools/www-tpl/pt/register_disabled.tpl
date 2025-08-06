
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="pt-br" lang="pt-br">
<head>
	<meta http-equiv="content-type" content="text/html" />
	<title>{{ site.siteName }}: Register </title>

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
        L10N.put("register.tooltip.name", "Seu nome pode conter letras minúsculas e maiúsculas, números e os caracteres -=?!@:.");
        L10N.put("register.tooltip.password", "Sua senha deve ter pelo menos 6 caracteres e conter letras e números.");
        L10N.put("register.error.password_required", "Por favor insira uma senha");
        L10N.put("register.error.password_too_short", "Sua senha deve ter pelo menos seis caracteres");
        L10N.put("register.error.password_numbers", "Você precisa ter pelo menos um número ou caractere especial em sua senha.");
        L10N.put("register.error.password_letters", "Você precisa ter pelo menos uma letra minúscula ou MAIÚSCULA em sua senha.");
        L10N.put("register.error.retyped_password_required", "Por favor digite novamente sua senha");
        L10N.put("register.error.retyped_password_notsame", "Suas senhas não são iguais, tente novamente");
        L10N.put("register.error.retyped_email_required", "Por favor digite seu e-mail novamente");
        L10N.put("register.error.retyped_email_notsame", "Os E-mails não são iguais");
        L10N.put("register.tooltip.namecheck", "Clique aqui para verificar se o nome está disponível");
        L10N.put("register.tooltip.retypepassword", "Por favor insira novamente a sua senha.");
        L10N.put("register.tooltip.personalinfo.disabled", "Por favor escoha seu nome {{ site.siteName }} primeiro");
        L10N.put("register.tooltip.namechecksuccess", "Congratulations! The name is available.");
        L10N.put("register.tooltip.passwordsuccess", "Sua senha não é segura");
        L10N.put("register.tooltip.passwordtooshort", "A senha que você escolheu é muito curta");
        L10N.put("register.tooltip.passwordnotsame", "As senhas não são iguais, tente novamente");
        L10N.put("register.tooltip.invalidpassword", "A senha que você escolheu é inválida, escolha uma nova senha.");
        L10N.put("register.tooltip.email", "Por favor, indique o seu endereço de e-mail. Você precisa ativar sua conta usando este endereço, então use seu endereço real.");
        L10N.put("register.tooltip.retypeemail", "Por favor, digite novamente seu endereço de e-mail.");
        L10N.put("register.tooltip.invalidemail", "Por favor insira um endereço de e-mail válido.");
        L10N.put("register.tooltip.emailsuccess", "Você forneceu um endereço de e-mail válido, obrigado!");
        L10N.put("register.tooltip.emailnotsame", "Seu e-mail digitado novamente é igual ao anterior.");
        L10N.put("register.tooltip.enterpassword", "Por favor digite uma senha");
        L10N.put("register.tooltip.entername", "Por favor digite um nome para o seu {{ site.siteName }}.");
        L10N.put("register.tooltip.enteremail", "Por favor digite o seu e-mail.");
        L10N.put("register.tooltip.enterbirthday", "Por favor forneça sua data de nascimento - você precisará disto depois para recuperação de senha e etc...");
        L10N.put("register.tooltip.acceptterms", "Por favor aceite os termos e condições");
        L10N.put("register.tooltip.invalidbirthday", "Por favor forneça uma data de nascimento válida");
        L10N.put("register.tooltip.emailandparentemailsame","O e-mail dos seus pais e o seu e-mail não podem ser iguais. Forneça um diferente.");
        L10N.put("register.tooltip.entercaptcha","Digite o código.");
        L10N.put("register.tooltip.captchavalid","Código inválido.");
        L10N.put("register.tooltip.captchainvalid","Código inválido, tente novamente.");
		L10N.put("register.error.parent_permission","Você precisa contar aos seus pais sobre este serviço");

        RegistrationForm.parentEmailAgeLimit = -1;
        L10N.put("register.message.parent_email_js_form", "<div\>\n\t<div class=\"register-label\"\>Como você tem menos de 16 anos e está de acordo com as diretrizes de práticas recomendadas do setor, solicitamos o endereço de e-mail de seus pais ou responsáveis.</div\>\n\t<div id=\"parentEmail-error-box\"\>\n        <div class=\"register-error\"\>\n            <div class=\"rounded rounded-blue\"  id=\"parentEmail-error-box-container\"\>\n                <div id=\"parentEmail-error-box-content\"\>\n                    Por favor digite o seu e-mail.\n                </div\>\n            </div\>\n        </div\>\n\t</div\>\n\t<div class=\"register-label\"\><label for=\"register-parentEmail-bubble\"\>Parent or guardian\'s email address</label\></div\>\n\t<div class=\"register-label\"\><input type=\"text\" name=\"bean.parentEmail\" id=\"register-parentEmail-bubble\" class=\"register-text-black\" size=\"15\" /\></div\>\n\n\n</div\>");

        RegistrationForm.isCaptchaEnabled = true;
         L10N.put("register.message.captcha_js_form", "<div\>\n  <div id=\"recaptcha_image\" class=\"register-label\"\>\n    <img id=\"captcha\" src=\"{{ site.sitePath }}/captcha.jpg?t=1538907557&register=1\" alt=\"\" width=\"200\" height=\"60\" /\>\n  </div\>\n  <div class=\"register-label\" id=\"captcha-reload\"\>\n    <img src=\"{{ site.staticContentPath }}/web-gallery/v2/images/shared_icons/reload_icon.gif\" width=\"15\" height=\"15\"/\>\n    <a href=\"#\"\>Não consigo ler o código, crie outro.</a\>\n  </div\>\n  <div class=\"register-label\"\><label for=\"register-captcha-bubble\"\>Digite o código de segurança fornecido acima</label\></div\>\n  <div class=\"register-input\"\><input type=\"text\" name=\"bean.captchaResponse\" id=\"register-captcha-bubble\" class=\"register-text-black\" value=\"\" size=\"15\" /\></div\>\n</div\>");

        L10N.put("register.message.age_limit_ban", "<div\>\n<p\>\nDesculpe, mas você não pode se registrar porque é muito jovem. Se você inseriu uma data de nascimento incorreta por acidente, tente novamente em algumas horas.\n</p\>\n\n<p style=\"text-align:left\"\>\n<input type=\"button\" class=\"submit\" id=\"register-parentEmail-cancel\" value=\"Cancel\" onclick=\"RegistrationForm.cancel(\'?ageLimit=true\')\" /\>\n</p\>\n</div\>");
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
<body id="register" class="process-template secure-page">

<div id="overlay"></div>

<div id="container">
	<div class="cbb process-template-box clearfix">
		<div id="content">

			{% include "base/frontpage_header.tpl" %}
			<div id="process-content">	        	
			<div id="column1" class="column">


			     		<p>O Registro de novos usuários está desabilitado, tente novamente mais tarde. entre em contato com o suporte para mais informações.</p>			
		
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