
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="pt-br" lang="pt-br">
<head>
	<meta http-equiv="content-type" content="text/html" />
	<title>{{ site.siteName }}: Log in to {{ site.siteName }} </title>

<script type="text/javascript">
var andSoItBegins = (new Date()).getTime();
</script>
    <link rel="shortcut icon" href="{{ site.staticContentPath }}/web-gallery/v2/favicon.ico" type="image/vnd.microsoft.icon" />
    <link rel="alternate" type="application/rss+xml" title="{{ site.siteName }}: RSS" href="{{ site.sitePath }}/articles/rss.xml" />

<script src="{{ site.staticContentPath }}/web-gallery/static/js/libs2.js" type="text/javascript"></script>
<script src="{{ site.staticContentPath }}/web-gallery/static/js/landing.js" type="text/javascript"></script>
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/style.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/buttons.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/boxes.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/tooltips.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/styles/local/com.css" type="text/css" />

<script src="{{ site.staticContentPath }}/web-gallery/js/local/com.js" type="text/javascript"></script>
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/process.css" type="text/css" />

<script type="text/javascript">
document.habboLoggedIn = false;
var habboName = null;
var ad_keywords = "";
var habboReqPath = "{{ site.sitePath }}";
var habboStaticFilePath = "{{ site.staticContentPath }}/web-gallery";
var habboImagerUrl = "{{ site.staticContentPath }}/habbo-imaging/";
var habboPartner = "";
var habboDefaultClientPopupUrl = "{{ site.sitePath }}/client";
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
<body id="reauthenticate" class="process-template">

<div id="overlay"></div>

<div id="container">
	<div class="cbb process-template-box clearfix">
		<div id="content">
            {% include "../base/frontpage_header.tpl" %}
			<div id="process-content">
	        	<div id="column1" class="column">
	<div class="cbb clearfix green">
		<h2 class="title">Please enter your password</h2>

		<div class="box-content">
			<p>You need to enter your password to continue because you have signed in via 'remember-me'.</p>
			<p>If you are not <strong>{{ playerDetails.getName() }}</strong>, please <a href="{{ site.sitePath }}/account/logout?origin=default">sair</a>.</p>
			<p>If you have forgotten your password, please <a href="{{ site.sitePath }}/account/password/forgot">click here</a>.</p>			
		</div>

	</div>					
</div>

<div id="column2" class="column">

<!-- <div class="action-error flash-message"><div class="rounded"><ul>
	<li>xdddddd</li>
</ul></div></div> -->

{% if alert.hasAlert %}

<div class="action-error flash-message"><div class="rounded"><ul>
	<li>{{ alert.message }}</li>
</ul></div></div>

{% endif %}

<div class="cbb gray clearfix">
    <h2 class="title">Sign in</h2>
    
    <div class="box-content clearfix" id="login-habblet">
        <form action="{{ site.sitePath }}/account/reauthenticate" method="post" class="login-habblet">
        	<input type="hidden" name="page" value="/client" />
            <ul>

                <li>
                    <label for="login-username" class="login-text">Username</label>
                    <span class="username">{{ playerDetails.getName() }}</span>
                </li>
                <li>
                    <label for="login-password" class="login-text">Password</label>
                    <input tabindex="2" type="password" class="login-field" name="password" id="login-password" />

                    <input type="submit" value="Sign in" class="submit" id="login-submit-button"/>
					<a style="float: left; margin-left: 0pt; display: none" class="new-button" id="login-submit-new-button" href="#"><b style="padding-left: 10px; padding-right: 7px; width: 55px;">Sign in</b><i></i></a>                    
                </li>
            </ul>
        </form>

    </div>
</div>
</div>
<script type="text/javascript">
	HabboView.add(LoginFormUI.init);
	HabboView.add(RememberMeUI.init);
</script>

<!--[if lt IE 7]>
<script type="text/javascript">
Pngfix.doPngImageFix();
</script>
<![endif]-->

{% include "../base/footer.tpl" %}

<script type="text/javascript">
HabboView.run();
</script>


</body>
</html>