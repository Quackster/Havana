<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">
<head>
	<meta http-equiv="content-type" content="text/html" />
	<title>{{ site.siteName }}: Inicio </title>

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
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/welcome.css" type="text/css" />

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

<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/personal.css" type="text/css" />
<script src="{{ site.staticContentPath }}/web-gallery/static/js/habboclub.js" type="text/javascript"></script>	
							
								<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/v2/styles/minimail.css" type="text/css" />
<link rel="stylesheet" href="{{ site.staticContentPath }}/web-gallery/styles/myhabbo/control.textarea.css" type="text/css" />
<script src="{{ site.staticContentPath }}/web-gallery/static/js/minimail.js" type="text/javascript"></script>


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
					Inicio			</li>
				<li class="">
					<a href="{{ site.sitePath }}/home/{{ playerDetails.getName() }}">Mi Página</a>    		</li>
				<li class="">
					<a href="{{ site.sitePath }}/profile">Configuraciones de la cuenta</a>    		</li>
				<li class="">
					<a href="{{ site.sitePath }}/club">{{ site.siteName }} Club</a>
				</li>
				<!-- 
				<li class="{% if gameConfig.getInteger('guides.group.id') == 0 %} last{% endif %}">
					<a href="{{ site.sitePath }}/beta_client" target="beta_client" onclick="openOrFocusHabbo(this); return false;" style="color: red">Try Beta Habbo!</a>
				</li>
				{% if gameConfig.getInteger('guides.group.id') > 0 %}
				<li class=" last">
					<a href="{{ site.sitePath }}/groups/officialhabboguides">Guías de Habbo</a>
				</li>
				{% endif %}
				-->
				<li class=" last">
					<a href="{{ site.sitePath }}/groups/officialhabboguides">Guías de Habbo</a>
				</li>
			</ul>
		</div>
	</div>
	
<div id="container">
	<div id="content">
		<div id="column1" class="column">
		<!-- <div class="rounded" style="background-color: red; color: white">
			<strong>Atención!</strong><br />
			Este servidor se encuentra actualmente en beta.Eso significa que puede haber algunas características incompletas, errores y otras formas de explotación.<br />
		</div>
		<br /> -->
				<div class="habblet-container ">
						<div id="new-personal-info" style="background-image:url({{ site.staticContentPath }}/web-gallery/v2/images/personal_info/hotel_views/htlview_au.png)" />

	{% if site.serverOnline %}
    <div class="enter-hotel-btn">
        <div class="open enter-btn">
            <a href="{{ site.sitePath }}/client" target="client" onclick="openOrFocusHabbo(this); return false;">Ingresse {{ site.siteName }} Hotel<i></i></a>
            <b></b>
        </div>
    </div>
    <!-- 
    <div class="enter-beta-btn">
        <div class="open enter-btn">
            <a href="{{ site.sitePath }}/client" target="client" onclick="openOrFocusHabbo(this); return false;">Ingresse {{ site.siteName }} Hotel<i></i></a>
            <b></b>
        </div>
    </div>
    -->
	{% else %}
	<div class="enter-hotel-btn">
		<div class="closed enter-btn">
			<span>{{ site.siteName }} fuera de linea</span>
			<b></b>
		</div>
	</div>
	{% endif %}
	
	<div id="habbo-plate">
		<a href="{{ site.sitePath }}/profile">
			{% if playerDetails.motto.toLowerCase() == "crikey" %}
			<img src='{{ site.staticContentPath }}/web-gallery/images/sticker_croco.gif' style='margin-top: 57px'>
			{% else %}
			<img alt="{{ playerDetails.getName() }}" src="{{ site.sitePath }}/habbo-imaging/avatarimage?figure={{ playerDetails.figure }}&size=b&direction=3&head_direction=3&crr=0&gesture=sml&frame=1" width="64" height="110" />
			{% endif %}
		</a>
	</div>

	<div id="habbo-info">
		<div id="motto-container" class="clearfix">
			<strong>{{ playerDetails.getName() }}:</strong>
			<div>
				{% autoescape 'html' %}
				{% if playerDetails.motto == "" %}
				<span title="Haga clic para ingresar su lema/estado">Haga clic para ingresar su lema/estado</span>
				{% else %}
				<span title="Haga clic para ingresar su lema/estado">{{ playerDetails.motto }}</span>
				{% endif %}
				{% endautoescape %}
				<p style="display: none"><input type="text" length="30" name="motto" value=""/></p>
			</div>
		</div>
		<div id="motto-links" style="display: none"><a href="#" id="motto-cancel">Cancelar</a></div>
	</div>

	<ul id="link-bar" class="clearfix">
        <li class="change-looks"><a href="{{ site.sitePath }}/profile">Cambiar apariencia &raquo;</a></li>
        <li class="credits">
            <a href="{{ site.sitePath }}/credits">{{ playerDetails.credits }}</a> Monedas		</li>
        <li class="club">
            {% if playerDetails.hasClubSubscription() %}
            <a href="{{ site.sitePath }}/club">{{ hcDays }} </a> días HC		</li>
            {% else %}
            <a href="{{ site.sitePath }}/club">Únete al {{ site.siteName }} club &raquo;</a>		</li>
            {% endif %}
        <li class="activitypoints">
            <a href="{{ site.sitePath }}/credits/pixels">{{ playerDetails.pixels }}</a> Píxeles		    </li>
    </ul>

    <div id="habbo-feed">
        <ul id="feed-items">
		{% if hasBirthday %}
                <li id="feed-birthday">
                  <div>
                    Happy <!-- anniversary --> birthday, {{ playerDetails.name }}!<br />
                  </div>
                </li>
		{% endif %}
		{% for alert in alerts %}	
			{% if alert.getAlertType() == 'HC_EXPIRED' %}
			<li id="feed-item-hc-reminder">
				<a href="#" class="remove-feed-item" id="remove-hc-reminder" title="Eliminar notificación">Eliminar notificación</a>

				<div>Your Habbo Club is expired. Do you want to extend your Habbo Club?	</div>
				<div class="clearfix">
					<table width="100px" style="margin-top:6px; margin-left:-12px">
						<tr>
							<td>
								<a class="new-button" id="subscribe1" href="#" onclick='habboclub.buttonClick(1, "Habbo Club"); return false;'><b>1 months</b><i></i></a>
							</td>
							<td>
								<a class="new-button" id="subscribe2" href="#" onclick='habboclub.buttonClick(2, "Habbo Club"); return false;' style="margin-left:6px"><b>2 months</b><i></i></a>
							</td>
							<td>
								<a class="new-button" id="subscribe2" href="#" onclick='habboclub.buttonClick(3, "Habbo Club"); return false;' style="margin-left:6px"><b>3 months</b><i></i></a>
							</td>
						</tr>
					</table>
				</div>

			</li>
			{% elseif alert.getAlertType() == 'PRESENT' %}
			<li id="feed-item-dailygift" class="contributed">
				<a href="#" class="remove-feed-item" title="Eliminar notificación">Eliminar notificación</a>
				<div>{{ alert.getMessage }}</div>
			</li>
			{% elseif alert.getAlertType() == 'TUTOR_SCORE' %}
			<li id="feed-item-tutor-score" class="contributed">
				<a href="#" class="remove-feed-item" title="Eliminar notificación">Eliminar notificación</a>
				<div>{{ alert.getMessage }}</div>
			</li>
			{% elseif alert.getAlertType() == 'CREDIT_DONATION' %}
			<li id="feed-item-creditdonation" class="contributed">
				<a href="#" class="remove-feed-item" title="Eliminar notificación">Eliminar notificación</a>
				<div>{{ alert.getMessage }}</div>
			</li>
			{% endif %}
		{% endfor %}
			{% if feedFriendRequests > 0 %}
			<li id="feed-notification">
				You have <a href="{{ site.sitePath }}/client" onclick="HabboClient.openOrFocus(this); return false;">{{ feedFriendRequests }} friend requests</a> waiting
			</li>
			{% endif %}
			
			{% set num = 1 %}
			{% if (feedFriendsOnline|length) > 0 %}
			<li id="feed-friends">
			You have {{ feedFriendsOnline|length }} Amigos en línea
			<span>
			{% for friend in feedFriendsOnline %}				
				<a href="{{ site.sitePath }}/home/{{ friend.getUsername() }}">{{ friend.getUsername() }}</a>{% if num < (feedFriendsOnline|length) %},{% endif %}{% set num = num + 1 %}
			{% endfor %}
			</span>
			</li>
			{% endif %}
			
			{% if unreadGuestbookMessages > 0 %}
			<li class="small" id="feed-guestbook">
			You have <a href="{{ site.sitePath }}/home/{{ playerDetails.getName() }}">{{ unreadGuestbookMessages }} new guestbook entries</a> on your homepage
			</li>
			{% endif %}
			
			{% set num = 1 %}
			{% if pendingMembers > 0 %}
			<li class="small" id="feed-pending-members">
			<strong>{{ pendingMembers }}</strong> of your groups have pending members:
			<span>
			{% for group in pendingGroups.entrySet() %}	
				{% set groupId = group.getKey() %}
				{% set groupName = group.getValue() %}
				<a href="{{ site.sitePath }}/groups/{{ groupId }}/id">{{ groupName }}</a>{% if num < (pendingGroups|length) %},{% endif %}{% set num = num + 1 %}
			{% endfor %}
			</span>
			</li>
			{% endif %}
			
			{% set num = 1 %}
			{% if newPostsAmount > 0 %}
			<li class="small" id="feed-group-discussion">
			<strong>{{ newPostsAmount }}</strong> of your groups have new forum messages:
			<span>
			{% for group in newPosts.entrySet() %}	
				{% set groupId = group.getKey() %}
				{% set groupName = group.getValue() %}
				<a href="{{ site.sitePath }}/groups/{{ groupId }}/id/discussions">{{ groupName }}</a>{% if num < (newPosts|length) %},{% endif %}{% set num = num + 1 %}
			{% endfor %}
			</span>
			</li>
			{% endif %}
			
			{% if playerDetails.isTradeEnabled() %}
				<li class="small" id="feed-trading-enabled">El comercio está en marcha. <a href="{{ site.sitePath }}/profile?tab=6" title="">Haga clic aquí para desactivarlo</a></li>
			{% else %}
				<li class="small" id="feed-trading-disabled">El comercio está cerrado. <a href="{{ site.sitePath }}/profile?tab=6" title="">Haga clic aquí para activarlo</a></li>
			{% endif %}
			<!-- <li class="small" id="feed-flashbeta-invites">Este servidor se encuentra actualmente en versión beta, es posible que algunas funciones no funcionen correctamente. También puede esperar períodos de mantenimiento espontáneos.</li> -->
            <li class="small" id="feed-lastlogin">
                Último inicio de sesión:
                {{ lastOnline }}            </li>
        </ul>
    </div>

    <p class="last"></p>
</div>

<script type="text/javascript">
    HabboView.add(function() {
        L10N.put("personal_info.motto_editor.spamming", "Don\'t spam me, bro!");
        PersonalInfo.init("");
    });
</script>


                </div>
                <script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
			{% if (newbieRoomLayout == 0) and (site.serverOnline == true) %}
            <div class="habblet-container" id="roomselection">
              <div class="cbb clearfix rooms">
                <h2 class="title">
                  Select your room!
                  <span class="habblet-close" id="habblet-close-roomselection" onclick="RoomSelectionHabblet.showConfirmation()"></span>
                </h2>
                    <div id="roomselection-plp-intro" class="box-content">
                    Hey! You haven't chosen your pre-decorated room, which comes with free furniture! Choose one below:
                    </div>
                        <ul id="roomselection-plp" class="clearfix">
                            <li class="top">
                            <a class="roomselection-select new-button green-button" href="client?createRoom=0" target="client" onclick="return RoomSelectionHabblet.create(this, 0);"><b>Select</b><i></i></a>	
                            </li>
                            <li class="top">
                            <a class="roomselection-select new-button green-button" href="client?createRoom=1" target="client" onclick="return RoomSelectionHabblet.create(this, 1);"><b>Select</b><i></i></a>	
                            </li>
                            <li class="top">
                            <a class="roomselection-select new-button green-button" href="client?createRoom=2" target="client" onclick="return RoomSelectionHabblet.create(this, 2);"><b>Select</b><i></i></a>	
                            </li>
                            <li class="bottom">
                            <a class="roomselection-select new-button green-button" href="client?createRoom=3" target="client" onclick="return RoomSelectionHabblet.create(this, 3);"><b>Select</b><i></i></a>	
                            </li>
                            <li class="bottom">
                            <a class="roomselection-select new-button green-button" href="client?createRoom=4" target="client" onclick="return RoomSelectionHabblet.create(this, 4);"><b>Select</b><i></i></a>	
                            </li>
                            <li class="bottom">
                            <a class="roomselection-select new-button green-button" href="client?createRoom=5" target="client" onclick="return RoomSelectionHabblet.create(this, 5);"><b>Select</b><i></i></a>	
                            </li>
                        </ul>
                        <script type="text/javascript">
                        L10N.put("roomselection.hide.title", "Hide room selection");
                        L10N.put("roomselection.old_user.done", "And you\'re done! Habbo Hotel will now open in a new window and you\'ll be redirected to your room in no time!");
                        //HabboView.add(RoomSelectionHabblet.initClosableHabblet);
                        </script>	
					</div>
				</div>
                
                <script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
				{% endif %}
				
				{% if (newbieNextGift > 0) and (newbieRoomLayout > 0) and (newbieNextGift < 4) %}
              <div class="habblet-container " id="giftqueue">
                <div class="cbb clearfix rooms">
                  <h2 class="title">
                    ¡Tu próximo regalo!
					{% if newbieNextGift > 2 %}
                    <span class="habblet-close" id="habblet-close-giftqueue" onclick="GiftQueueHabblet.hide()"></span>
					{% endif %}
                  </h2>
				  <div class="box-content" id="gift-container">
				  {% include "habblet/nextgift.tpl" %}
				  </div>
                </div>
              </div>
				{% endif %}
				<div class="habblet-container ">		
						<div class="cbb clearfix orange ">

	
							<h2 class="title">Campañas							</h2>
						<div id="hotcampaigns-habblet-list-container">
    <ul id="hotcampaigns-habblet-list">

        <li class="even">
            <div class="hotcampaign-container">
                <a href="{{ site.sitePath }}/articles"><img src="{{ site.staticContentPath }}/c_images/hot_campaign_images_gb/beta.gif" align="left" alt="" /></a>
                <h3>En construcción</h3>
                <p>¡Ponga texto interesante aquí, porque de lo contrario este texto sería inútil si estuviera aquí!</p>
                <p class="link"><a href="{{ site.sitePath }}">Ver ahora &raquo;</a></p>
            </div>
        </li>
        
        <li class="odd">
            <div class="hotcampaign-container">
                <a href="{{ site.sitePath }}/articles"><img src="{{ site.staticContentPath }}/c_images/hot_campaign_images_gb/habbobetahot.gif" align="left" alt="" /></a>
                <h3>En construcción</h3>
                <p>¡Ponga texto interesante aquí, porque de lo contrario este texto sería inútil si estuviera aquí!</p>
                <p class="link"><a href="{{ site.sitePath }}">Ver ahora &raquo;</a></p>
            </div>
        </li>
        
        <!-- 
        <li class="odd">
            <div class="hotcampaign-container">
                <a href="{{ site.sitePath }}/articles"><img src="{{ site.staticContentPath }}/c_images/hot_campaign_images_gb/hc.gif" align="left" alt="" /></a>
                <h3>Exclusive Furniture!</h3>
                <p>Join Habbo Club today and get access to exclusive furniture!</p>

                <p class="link"><a href="http://localhost/credits/club">Ver ahora &raquo;</a></p>
            </div>
        </li>
        -->
    </ul>
</div>
	
						
					</div>
				</div>

				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
				
<div class="habblet-container minimail" id="mail">
                        <div class="cbb clearfix blue ">

                            <h2 class="title">Mis mensajes                            </h2>
                        <div id="minimail">
		<div class="minimail-contents">
	    {% include "habblet/minimail/minimail_messages.tpl" %}
		</div>
		<div id="message-compose-wait"></div>
	    <form style="display: none" id="message-compose">
	        <div>A</div>
	        <div id="message-recipients-container" class="input-text" style="width: 426px; margin-bottom: 1em">
	        	<input type="text" value="" id="message-recipients" />
	        	<div class="autocomplete" id="message-recipients-auto">
	        		<div class="default" style="display: none;">Escribe el nombre de tu amigo</div>
	        		<ul class="feed" style="display: none;"></ul>

	        	</div>
	        </div>
	        <div>Sujeto<br/>
	        <input type="text" style="margin: 5px 0" id="message-subject" class="message-text" maxlength="100" tabindex="2" />
	        </div>
	        <div>Mensaje<br/>
	        <textarea style="margin: 5px 0" rows="5" cols="10" id="message-body" class="message-text" tabindex="3"></textarea>

	        </div>
	        <div class="new-buttons clearfix">
	            <a href="#" class="new-button preview"><b>Vista</b><i></i></a>
	            <a href="#" class="new-button send"><b>Enviar</b><i></i></a>
	        </div>
	    </form>
	</div>
				<script type="text/javascript">
		L10N.put("minimail.compose", "Componer").put("minimail.cancel", "Cancela")
			.put("bbcode.colors.red", "Rojo").put("bbcode.colors.orange", "Naranja")
	    	.put("bbcode.colors.yellow", "Amarillo").put("bbcode.colors.green", "Verde")
	    	.put("bbcode.colors.cyan", "Cian").put("bbcode.colors.blue", "Azul")
	    	.put("bbcode.colors.gray", "Gris").put("bbcode.colors.black", "Negro")
	    	.put("minimail.empty_body.confirm", "¿Estás seguro de que quieres enviar el mensaje con el cuerpo vacío?")
	    	.put("bbcode.colors.label", "Color").put("linktool.find.label", " ")
	    	.put("linktool.scope.habbos", "{{ site.siteName }}s").put("linktool.scope.rooms", "Habitaciones")
	    	.put("linktool.scope.groups", "Grupo").put("minimail.report.title", "Informar mensaje a los moderadores");

	    L10N.put("date.pretty.just_now", "ahora");
	    L10N.put("date.pretty.one_minute_ago", "Hace 1 minuto");
	    L10N.put("date.pretty.minutes_ago", "Hace {0} minutos");
	    L10N.put("date.pretty.one_hour_ago", "1 Hora antes");
	    L10N.put("date.pretty.hours_ago", "Hace {0} Horas");
	    L10N.put("date.pretty.yesterday", "ayer");
	    L10N.put("date.pretty.days_ago", "Hace {0} dias");
	    L10N.put("date.pretty.one_week_ago", "Hace 1 semana");
	    L10N.put("date.pretty.weeks_ago", "Hace {0} semanas");
		new MiniMail({ pageSize: 10,
		   total: {{ totalMessages }},
		   friendCount: 1,
		   maxRecipients: 50,
		   messageMaxLength: 20,
		   bodyMaxLength: 4096,
		   secondLevel: false});
	</script>
	</div></div>

    <script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>

				<div class="habblet-container ">		
						<div class="cbb clearfix default ">
<div class="box-tabs-container clearfix">
    <h2>{{ site.siteName }}s</h2>
    <ul class="box-tabs">
        <li id="tab-0-4-1"><a href="#">Buscar {{ site.siteName }}s</a><span class="tab-spacer"></span></li>

        <li id="tab-0-4-2" class="selected"><a href="#">Invitar amigo(s)</a><span class="tab-spacer"></span></li>
    </ul>
</div>
    <div id="tab-0-4-1-content"  style="display: none">
<div class="habblet-content-info">
    <a name="habbo-search">Escribe los primeros caracteres del nombre para buscar otros {{ site.siteName }}s.</a>
</div>
<div id="habbo-search-error-container" style="display: none;"><div id="habbo-search-error" class="rounded rounded-red"></div></div>
<br clear="all"/>
<div id="avatar-habblet-list-search">
    <input type="text" id="avatar-habblet-search-string"/>

    <a href="#" id="avatar-habblet-search-button" class="new-button"><b>Buscar</b><i></i></a>
</div>

<br clear="all"/>

<div id="avatar-habblet-content">
<div id="avatar-habblet-list-container" class="habblet-list-container">
        <ul class="habblet-list">
        </ul>

</div>
<script type="text/javascript">
    L10N.put("habblet.search.error.search_string_too_long", "The search keyword was too long. Maximum length is 30 characters.");
    L10N.put("habblet.search.error.search_string_too_short", "The search keyword was too short. 2 characters required.");
    L10N.put("habblet.search.add_friend.title", "Add to friend list");
	new HabboSearchHabblet(2, 30);

</script>

</div>

<script type="text/javascript">
    Rounder.addCorners($("habbo-search-error"), 8, 8);
</script>    </div>
    <div id="tab-0-4-2-content" >
<div id="friend-invitation-habblet-container" class="box-content">
    <div style="display: none"> 
    <div id="invitation-form" class="clearfix">
        <textarea name="invitation_message" id="invitation_message" class="invitation-message">Come and hangout with me in {{ site.siteName }}.- {{ playerDetails.getName() }}</textarea>
        <div id="invitation-email">
            <div class="invitation-input">1.<input  onkeypress="$('invitation_recipient2').enable()" type="text" name="invitation_recipients" id="invitation_recipient1" value="Friend's email address:" class="invitation-input" />

            </div>
            <div class="invitation-input">2.<input disabled onkeypress="$('invitation_recipient3').enable()" type="text" name="invitation_recipients" id="invitation_recipient2" value="Friend's email address:" class="invitation-input" />
            </div>
            <div class="invitation-input">3.<input disabled  type="text" name="invitation_recipients" id="invitation_recipient3" value="Friend's email address:" class="invitation-input" />
            </div>
        </div>
        <div class="clear"></div>
        <div class="fielderror" id="invitation_message_error" style="display: none;"><div class="rounded"></div></div>

    </div>

    <div class="invitation-buttons clearfix" id="invitation_buttons">
		<a  class="new-button" id="send-friend-invite-button" href="#"><b>Invitar amigo(s)</b><i></i></a>
    </div>
    
    <hr/>
    </div>
    <div id="invitation-link-container">
        <h3>¡Disfruta más de {{ site.siteName }} con amigas de la vida real!</h3>

        <div class="copytext">
            <p>¡Invita a tus amigos a Habbo y gana insignias geniales! Envía un enlace a tu amigo y pídele que se registre y active su correo electrónico. Si usan Habbo de forma activa, serás recompensado con una insignia.</p>
        </div>
        <div class="invitation-buttons clearfix"> 
            <a  class="new-button" id="getlink-friend-invite-button" href="#"><b>¡Obtén el enlace de invitación!</b><i></i></a>
        </div>
    </div>
</div>
<script type="text/javascript">
    L10N.put("invitation.button.invite", "Invitar amigo(s)");
    L10N.put("invitation.form.recipient", "Friend's email address:");
    L10N.put("invitation.error.message_too_long", "invitation.error.message_limit");
    inviteFriendHabblet = new InviteFriendHabblet(500);   
    $("friend-invitation-habblet-container").select(".fielderror .rounded").each(function(el) {
        Rounder.addCorners(el, 8, 8);
    });

</script>    </div>

					</div>
				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>


<div class="habblet-container ">		
						<div class="cbb clearfix darkred ">
	
							<h2 class="title">Eventos							</h2>
						<div id="current-events">
	<div class="category-selector">
	<p>Explorar los últimos eventos por categoría</p>
	<select id="event-category">
		<option value="1">Fiestas y Musica</option>
		<option value="2">Comercio</option>
		<option value="3">Juegos</option>
		<option value="4">Guías retro</option>
		<option value="5">Debates y Discusiones</option>
		<option value="6">Grandes Inauguraciones</option>
		<option value="7">Encuentros</option>
		<option value="8">Trabajos</option>
		<option value="9">Eventos grupales</option>
		<option value="10">Actuación</option>
		<option value="11">Mesa de ayuda</option>
	</select>
	</div>
	<div id="event-list">
		{% set eventCategory = 1 %}
		{% include "habblet/load_events.tpl" %}
	</div>
</div>
<script type="text/javascript">
	document.observe('dom:loaded', function() {
		CurrentRoomEvents.init();
	});
</script>
	
						
					</div>
				</div>
</div>
				<script type='text/javascript'>if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
<div id="column2" class="column">
				<div class="habblet-container news-promo">
						<div class="cbb clearfix notitle ">

						<div id="newspromo">
        <div id="topstories">
	        <div class="topstory" style="background-image: url({{ article1.getLiveTopStory() }})">
	            <h4>Últimas noticias </a></h4>
	            <h3><a href="{{ site.sitePath }}/articles/{{ article1.getUrl() }}">{% if article1.isPublished() == false %}*{% endif %}{{ article1.title }}</a></h3>
	            <p class="summary">
	            {{ article1.shortstory }}	            </p>
	            <p>
	                <a href="{{ site.sitePath }}/articles/{{ article1.getUrl() }}">Leer más &raquo;</a>
	            </p>
	        </div>
	        <div class="topstory" style="background-image: url({{ article2.getLiveTopStory() }}); display: none">
	            <h4>Últimas noticias</a></h4>
	            <h3><a href="{{ site.sitePath }}/articles/{{ article2.getUrl() }}">{% if article2.isPublished() == false %}*{% endif %}{{ article2.title }}</a></h3>
	            <p class="summary">
	            {{ article2.shortstory }}	            </p>
	            <p>
	                <a href="{{ site.sitePath }}/articles/{{ article2.getUrl() }}">Leer más &raquo;</a>
	            </p>
	        </div>
	        <div class="topstory" style="background-image: url({{ article3.getLiveTopStory() }}); display: none">
	            <h4>Últimas noticias</a></h4>
	            <h3><a href="{{ site.sitePath }}/articles/{{ article3.getUrl() }}">{% if article3.isPublished() == false %}*{% endif %}{{ article3.title }}</a></h3>
	            <p class="summary">
	            {{ article3.shortstory }}	            </p>
	            <p>
	                <a href="{{ site.sitePath }}/articles/{{ article3.getUrl() }}">Leer más &raquo;</a>
	            </p>
	        </div>
            <div id="topstories-nav" style="display: none"><a href="#" class="prev">&laquo; Anterior</a><span>1</span> / 3<a href="#" class="next">Siguiente &raquo</a></div>
        </div>
        <ul class="widelist">
            <li class="even">
                <a href="{{ site.sitePath }}/articles/{{ article4.getUrl() }}">{% if article4.isPublished() == false %}*{% endif %}{{ article4.title }}</a><div class="newsitem-date">{{ article4.getDate() }}</div>
            </li>
            <li class="odd">
                <a href="{{ site.sitePath }}/articles/{{ article5.getUrl() }}">{% if article4.isPublished() == false %}*{% endif %}{{ article5.title }}</a><div class="newsitem-date">{{ article5.getDate() }}</div>
            </li>
            <li class="last"><a href="{{ site.sitePath }}/articles">Mas noticias &raquo;</a></li>
        </ul>
</div>
<script type="text/javascript">
	document.observe("dom:loaded", function() { NewsPromo.init(); });
</script>
					</div>

				</div>
					<div>
					<!-- <p><a href="{{ site.sitePath }}/community"><img src="https://i.imgur.com/87IsMuC.png"></a></p> -->
					<!-- <p><a href="{{ site.sitePath }}/community"><img src="https://i.imgur.com/SGFjYN2.gif"></a></p> -->
					<!-- <p><a href="{{ site.sitePath }}"><img src="https://i.imgur.com/9lUdOG1.png"></a></p> -->
					<p><iframe src="https://discordapp.com/widget?id=524768066907668521&theme=light" height="280" allowtransparency="true" frameborder="0"></iframe></p>
					</div>
					<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
				<div class="habblet-container ">		
						<div class="cbb clearfix red ">
<div class="box-tabs-container clearfix">
    <h2>Equipo</h2>
    <ul class="box-tabs">
        <li id="tab-1-3-1"><a href="#">Habitaciones</a><span class="tab-spacer"></span></li>
        <li id="tab-1-3-2" class="selected"><a href="#">Grupos</a><span class="tab-spacer"></span></li>
    </ul>

</div>
    <div id="tab-1-3-1-content"  style="display: none">
    		<div class="progressbar"><img src="{{ site.staticContentPath }}/web-gallery/images/progress_bubbles.gif" alt="" width="29" height="6" /></div>
    		<a href="{{ site.sitePath }}/habblet/proxy?hid=h21" class="tab-ajax"></a>
    </div>
    <div id="tab-1-3-2-content" >
<div id="staffpicks-groups-habblet-list-container" class="habblet-list-container groups-list">
    <ul class="habblet-list two-cols clearfix">
		{% set position = "right" %}
				
		{% set i = 1 %}
		{% set lefts = 0 %}
		{% set rights = 0 %}
		{% for group in staffPickGroups %}	
			{% if i % 2 == 0 %}
				{% set position = "right" %}
				{% set rights = rights + 1 %}
			{% else %}
				{% set position = "left" %}
				{% set lefts = lefts + 1 %}
			{% endif %}
			
			{% if lefts % 2 == 0 %}
				{% set status = "even" %}
			{% else %}
				{% set status = "odd" %}
			{% endif %}
	
			{% set i = i + 1 %}
			<li class="{{ status }} {{ position }}" style="background-image: url({{ site.sitePath }}/habbo-imaging/badge/{{ group.badge }}.gif)">
				<a class="item" href="{{ group.generateClickLink() }}">{{ group.getName }}</a>
			</li>
		{% endfor %}
    </ul>


</div>
    </div>

					</div>
				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>

<div class="habblet-container ">        
                        <div class="cbb clearfix blue ">
						<h2 class="title">Recomendado                            </h2>
						<div id="promogroups-habblet-list-container" class="habblet-list-container groups-list">
							<ul class="habblet-list two-cols clearfix">
						{% set position = "right" %}
										
						{% set i = 0 %}
						{% set lefts = 0 %}
						{% set rights = 0 %}
						{% for group in recommendedGroups %}	
								{% set i = i + 1 %}
								{% if i % 2 == 0 %}
									{% set position = "right" %}
									{% set rights = rights + 1 %}
								{% else %}
									{% set position = "left" %}
									{% set lefts = lefts + 1 %}
								{% endif %}
								
								{% if lefts % 2 == 0 %}
									{% set status = "even" %}
								{% else %}
									{% set status = "odd" %}
								{% endif %}
								<li class="{{ status }} {{ position }}" style="background-image: url({{ site.sitePath }}/habbo-imaging/badge/{{ group.badge }}.gif)">
									{% if group.getRoomId() > 0 %}
									<a href="{{ site.sitePath }}/client?forwardId=2&amp;roomId=1" onclick="HabboClient.roomForward(this, '1', 'private'); return false;" target="client" class="group-room"></a>     
									{% endif %}
									<a class="item" href="{{ group.generateClickLink() }}">{% autoescape 'html' %}{{ group.name }}{% endautoescape %}</a>
								</li>
							{% endfor %}
							</ul>
						</div>
                    </div>
                </div>
                <script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>

				<div class="habblet-container ">		
						<div class="cbb clearfix green ">
<div class="box-tabs-container clearfix">
    <h2>Etiquetas</h2>

							<ul class="box-tabs">
								<li id="tab-1-5-1"><a href="#">{{ site.siteName }}s Gustan...</a><span class="tab-spacer"></span></li>
								<li id="tab-1-5-2" class="selected"><a href="#">Mis Etiquetas</a><span class="tab-spacer"></span></li>
							</ul>
						</div>
						<div id="tab-1-5-1-content"  style="display: none">
							<div class="progressbar">
								<img src="{{ site.staticContentPath }}/web-gallery/images/progress_bubbles.gif" alt="" width="29" height="6" />
							</div>
							<a href="{{ site.sitePath }}/habblet/proxy?hid=h24" class="tab-ajax"></a>
						</div>
						<div id="tab-1-5-2-content" >
							<div id="my-tag-info" class="habblet-content-info">
							{% if tags|length < 1 %}
							No tienes etiquetas.
							{% endif %} Responda la siguiente pregunta o simplemente agregue una etiqueta de su elección.		    </div>
							<div class="box-content">
							
							{% include "habblet/myTagList.tpl" %}
							</div>
					

<script type="text/javascript">
document.observe("dom:loaded", function() {
    TagHelper.setTexts({
        tagLimitText: "Has alcanzado el límite de etiquetas: elimina una de tus etiquetas si quieres agregar una nueva.",
        invalidTagText: "Etiqueta no válida, la etiqueta debe tener menos de 20 caracteres y estar compuesta únicamente por caracteres alfanuméricos.",
        buttonText: "OK"
    });
        TagHelper.init('1');
});
</script>
    </div>

					</div>
				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
				
<div class="habblet-container ">
                        <div class="cbb clearfix blue ">
<div class="box-tabs-container clearfix">
    <h2>Grupos</h2>
    <ul class="box-tabs">
        <li id="tab-2-1"><a href="#">Top Grupos</a><span class="tab-spacer"></span></li>
        <li id="tab-2-2" class="selected"><a href="#">Mis Grupos</a><span class="tab-spacer"></span></li>
    </ul>
</div>
    <div id="tab-2-1-content"  style="display: none">
    		<div class="progressbar"><img src="{{ site.staticContentPath }}/web-gallery/images/progress_bubbles.gif" alt="" width="29" height="6" /></div>
    		<a href="{{ site.sitePath }}/habblet/proxy?hid=groups" class="tab-ajax"></a>
    </div>
    <div id="tab-2-2-content" >


         <div id="groups-habblet-info" class="habblet-content-info">
                Mira los grupos en los que estás, crea tu propio grupo o inspírate en la pestaña 'Grupos populares'.         </div>

    <div id="groups-habblet-list-container" class="habblet-list-container groups-list">


<ul class="habblet-list two-cols clearfix">         
		{% set position = "right" %}
						
		{% set i = 0 %}
		{% set lefts = 0 %}
		{% set rights = 0 %}
		{% for group in groups %}				
				{% if i % 2 == 0 %}
					{% set position = "right" %}
					{% set rights = rights + 1 %}
				{% else %}
					{% set position = "left" %}
					{% set lefts = lefts + 1 %}
				{% endif %}
				
				{% if lefts % 2 == 0 %}
					{% set status = "odd" %}
				{% else %}
					{% set status = "even" %}
				{% endif %}
				<li class="{{ status }} {{ position }}" style="background-image: url({{ site.sitePath }}/habbo-imaging/badge/{{ group.badge }}.gif)">
						<a class="item" href="{{ group.generateClickLink() }}">{% autoescape 'html' %}{{ group.name }}{% endautoescape %}</a>
        </li>
				{% set i = i + 1 %}
		{% endfor %}
    </ul>
		<div class="habblet-button-row clearfix"><a class="new-button" id="purchase-group-button" href="#"><b>Crear/comprar un grupo</b><i></i></a></div>
    </div>

    <div id="groups-habblet-group-purchase-button" class="habblet-list-container"></div>

<script type="text/javascript">
    $("purchase-group-button").observe("click", function(e) { Event.stop(e); GroupPurchase.open(); });
</script>






    </div>

					</div>
				</div>
				<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>

</div>

<script type="text/javascript">
	HabboView.add(LoginFormUI.init);
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
    </div>
{% include "base/footer.tpl" %}

<script type="text/javascript">
HabboView.run();
</script>


</body>
</html>