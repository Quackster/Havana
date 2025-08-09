
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="de" lang="de">
<head>
	<meta http-equiv="content-type" content="text/html" />
	<title>{{ site.siteName }}: Registrierung </title>

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
        L10N.put("register.tooltip.name", "Dein Name darf Klein- und Großbuchstaben, Zahlen und die Zeichen -=?!@: enthalten.");
        L10N.put("register.tooltip.password", "Dein Passwort muss mindestens 6 Zeichen lang sein und sowohl Buchstaben als auch Zahlen enthalten.");
        L10N.put("register.error.password_required", "Bitte erstell ein Passwort");
        L10N.put("register.error.password_too_short", "Dein Passwort sollte mindestens sechs Zeichen lang sein");
        L10N.put("register.error.password_numbers", "YDein Passwort muss mindestens eine Zahl oder ein Sonderzeichen enthalten.");
        L10N.put("register.error.password_letters", "Dein Passwort muss mindestens einen Klein- oder GROSSBUCHSTABEN enthalten.");
        L10N.put("register.error.retyped_password_required", "Bitte wiederhole dein Passwort");
        L10N.put("register.error.retyped_password_notsame", "Deine Passwörter stimmen nicht überein. Bitte versuche es erneut.");
        L10N.put("register.error.retyped_email_required", "Bitte wiederhole deine Email");
        L10N.put("register.error.retyped_email_notsame", "Emails stimmen nicht überein");
        L10N.put("register.tooltip.namecheck", "Klicke hier, um zu prüfen, ob dein Name frei ist.");
        L10N.put("register.tooltip.retypepassword", "Bitte wiederhole dein Passwort..");
        L10N.put("register.tooltip.personalinfo.disabled", "Bitte wähle deinen {{ site.siteName }} (character) Namen erst.");
        L10N.put("register.tooltip.namechecksuccess", "Bester Name.. und er ist sogar noch frei!.");
        L10N.put("register.tooltip.passwordsuccess", "Dein Passwort ist jetzt sicher.");
        L10N.put("register.tooltip.passwordtooshort", "Dein Passwort ist zu kurz.");
        L10N.put("register.tooltip.passwordnotsame", "Passwörter stimmen nicht überein, wiederhole die bitte.");
        L10N.put("register.tooltip.invalidpassword", "Das von dir gewählte Passwort ist ungültig, bitte wähle ein neues Passwort.");
        L10N.put("register.tooltip.email", "Bitte gebe deine EMail-Adresse ein. Du musst dein Konto mit dieser Adresse aktivieren. Verwende daher bitte eine aktuelle Email..");
        L10N.put("register.tooltip.retypeemail", "Bitte wiederhole deine Email.");
        L10N.put("register.tooltip.invalidemail", "Bitte gebe eine gültige EMail-Adresse ein.");
        L10N.put("register.tooltip.emailsuccess", "Du hast eine gültige EMail-Adresse angegeben, Danke!");
        L10N.put("register.tooltip.emailnotsame", "Emails stimmen nicht überein.");
        L10N.put("register.tooltip.enterpassword", "Passwort eingeben.");
        L10N.put("register.tooltip.entername", "Bitte gib ein Namen ein für {{ site.siteName }} (character).");
        L10N.put("register.tooltip.enteremail", "Email eingeben.");
        L10N.put("register.tooltip.enterbirthday", "Bitte gebe dein Geburtsdatum an, du benötigst dieses später, um Passwortresets o.ä. zu erhalten.");
        L10N.put("register.tooltip.acceptterms", "Bitte akzeptiere die allgemeinen Geschäftsbedingungen");
        L10N.put("register.tooltip.invalidbirthday", "Bitte gebe ein gültiges Geburtsdatum ein");
        L10N.put("register.tooltip.emailandparentemailsame","Die EMail-Adresse deiner Eltern und deine EMail-Adresse dürfen nicht identisch sein. Gebe bitte eine andere E-Mail-Adresse an..");
        L10N.put("register.tooltip.entercaptcha","Code eingeben.");
        L10N.put("register.tooltip.captchavalid","Ungültiger Code.");
        L10N.put("register.tooltip.captchainvalid","Ungültiger Code, bitte versuche es erneut.");
		L10N.put("register.error.parent_permission","Du musst deinen Eltern vum Erlaubnis bitten");

        RegistrationForm.parentEmailAgeLimit = -1;
        L10N.put("register.message.parent_email_js_form", "<div\>\n\t<div class=\"register-label\"\>Da du unter 16 Jahre alt bist und gemäß den Best Practice-Richtlinien der Branche, benötigen wir die E-Mail-Adresse deiner Eltern oder Erziehungsberechtigten.</div\>\n\t<div id=\"parentEmail-error-box\"\>\n        <div class=\"register-error\"\>\n            <div class=\"rounded rounded-blue\"  id=\"parentEmail-error-box-container\"\>\n                <div id=\"parentEmail-error-box-content\"\>\n                    Bitte gib deine Emailadresse ein.\n                </div\>\n            </div\>\n        </div\>\n\t</div\>\n\t<div class=\"register-label\"\><label for=\"register-parentEmail-bubble\"\>E-Mail-Adresse der Eltern oder Erziehungsberechtigten</label\></div\>\n\t<div class=\"register-label\"\><input type=\"text\" name=\"bean.parentEmail\" id=\"register-parentEmail-bubble\" class=\"register-text-black\" size=\"15\" /\></div\>\n\n\n</div\>");

        RegistrationForm.isCaptchaEnabled = true;
        L10N.put("register.message.captcha_js_form", "<div\>\n  <div id=\"recaptcha_image\" class=\"register-label\"\>\n    <img id=\"captcha\" src=\"{{ site.sitePath }}/captcha.jpg?t=1538907557&register=1\" alt=\"\" width=\"200\" height=\"60\" /\>\n  </div\>\n  <div class=\"register-label\" id=\"captcha-reload\"\>\n    <img src=\"{{ site.staticContentPath }}/web-gallery/v2/images/shared_icons/reload_icon.gif\" width=\"15\" height=\"15\"/\>\n    <a href=\"#\"\>Ich kann den Code nicht lesen! Ich brauch einen anderen.</a\>\n  </div\>\n  <div class=\"register-label\"\><label for=\"register-captcha-bubble\"\>Gebe den im Bild oben angezeigten Sicherheitscode ein</label\></div\>\n  <div class=\"register-input\"\><input type=\"text\" name=\"bean.captchaResponse\" id=\"register-captcha-bubble\" class=\"register-text-black\" value=\"\" size=\"15\" /\></div\>\n</div\>");

        L10N.put("register.message.age_limit_ban", "<div\>\n<p\>\nLeider kannst du dich nicht registrieren, da du zu jung bist. Solltest du versehentlich ein falsches Geburtsdatum eingegeben haben, versuche es bitte in ein paar Stunden erneut..\n</p\>\n\n<p style=\"text-align:left\"\>\n<input type=\"button\" class=\"submit\" id=\"register-parentEmail-cancel\" value=\"Cancel\" onclick=\"RegistrationForm.cancel(\'?ageLimit=true\')\" /\>\n</p\>\n</div\>");
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


<meta name="description" content="Trete dem weltweit größten virtuellen Treffpunkt bei, an dem du Freunde treffen und finden kannst. Gestalte deine eigenen Räume, sammle coole Möbel, veranstalte Partys und vieles mehr! Erstelle dein kostenloses {{ site.siteName }} Konto noch heute!" />
<meta name="keywords" content="{{ site.siteName }}, virtuell, Welt, beitreten, Gruppen, Foren, spielen, Spiele, online, Freunde, Teenager, Sammeln, soziales Netzwerk, erstellen, verbinden, Möbel, virtuell, Waren, teilen, Abzeichen, sozial, Vernetzung, Treffpunkt, Safe, Musik, Berühmtheit, Promi-Besuche" />

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

            <p>Es tut uns unendlich Leid..</p>
			<p>Aber die Registrierung ist aufgrund von Arbeiten an der Datenbank deaktiviert. Bitte komme ein anderes Mal wieder :(..</p>
            <p></p>
            <p>    Dein HabboTeam!   </p>			
		
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