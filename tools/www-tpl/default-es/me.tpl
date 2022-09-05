<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<meta http-equiv="content-type" content="text/html" />
	<title>{{ site.siteName }}: Home </title>

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


<meta name="description" content="Join the world's largest virtual hangout where you can meet and make friends. Design your own rooms, collect cool furniture, throw parties and so much more! Create your FREE {{ site.siteName }} today!" />
<meta name="keywords" content="{{ site.siteName }}, virtual, world, join, groups, forums, play, games, online, friends, teens, collecting, social network, create, collect, connect, furniture, virtual, goods, sharing, badges, social, networking, hangout, safe, music, celebrity, celebrity visits, cele" />

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
					Home			</li>
				<li class="">
					<a href="{{ site.sitePath }}/home/{{ playerDetails.getName() }}">Mi Página</a>    		</li>
				<li class="">
					<a href="{{ site.sitePath }}/profile">Ajustes</a>    		</li>
				<li class="">
					<a href="{{ site.sitePath }}/club">{{ site.siteName }} Club</a>
				</li>
				<!-- 
				<li class="{% if gameConfig.getInteger('guides.group.id') == 0 %} last{% endif %}">
					<a href="{{ site.sitePath }}/beta_client" target="beta_client" onclick="openOrFocusHabbo(this); return false;" style="color: red">Try Beta Habbo!</a>
				</li>
				{% if gameConfig.getInteger('guides.group.id') > 0 %}
				<li class=" last">
					<a href="{{ site.sitePath }}/groups/officialhabboguides">Habbo Guides</a>
				</li>
				{% endif %}
				-->
				<li class=" last">
					<a href="{{ site.sitePath }}/groups/officialhabboguides">Habbo Guias</a>
				</li>
			</ul>
		</div>
	</div>
	
<div id="container">
	<div id="content">
		<div id="column1" class="column">
		<!-- <div class="rounded" style="background-color: red; color: white">
			<strong>Attention!</strong><br />
			This server is currently in beta. That means there may be some incomplete features, bugs and other forms of exploitation.<br />
		</div>
		<br /> -->
				<div class="habblet-container ">
						<div id="new-personal-info" style="background-image:url({{ site.staticContentPath }}/web-gallery/v2/images/personal_info/hotel_views/htlview_au.png)" />

	{% if site.serverOnline %}
    <div class="enter-hotel-btn">
        <div class="open enter-btn">
            <a href="{{ site.sitePath }}/client" target="client" onclick="openOrFocusHabbo(this); return false;">Entrar a {{ site.siteName }} Hotel<i></i></a>
            <b></b>
        </div>
    </div>
    <!-- 
    <div class="enter-beta-btn">
        <div class="open enter-btn">
            <a href="{{ site.sitePath }}/client" target="client" onclick="openOrFocusHabbo(this); return false;">Enter {{ site.siteName }} Hotel<i></i></a>
            <b></b>
        </div>
    </div>
    -->
	{% else %}
	<div class="enter-hotel-btn">
		<div class="closed enter-btn">
			<span>{{ site.siteName }} no esta disponible</span>
			<b></b>
		</div>
	</div>
	{% endif %}
	
	<div id="habbo-plate">
		<a href="{{ site.sitePath }}/profile">
			{% if playerDetails.motto.toLowerCase() == "crikey" %}
			<img src='{{ site.staticContentPath }}/web-gallery/images/sticker_croco.gif' style='margin-top: 57px'>
			{% else %}
			<img alt="{{ playerDetails.getName() }}" src="https://cdn.classichabbo.com/habbo-imaging/avatarimage?figure={{ playerDetails.figure }}&size=b&direction=3&head_direction=3&crr=0&gesture=sml&frame=1" width="64" height="110" />
			{% endif %}
		</a>
	</div>

	<div id="habbo-info">
		<div id="motto-container" class="clearfix">
			<strong>{{ playerDetails.getName() }}:</strong>
			<div>
				{% autoescape 'html' %}
				{% if playerDetails.motto == "" %}
				<span title="Click to enter your motto/ status">Haz clic aquí para cambiar tu misión</span>
				{% else %}
				<span title="Click to enter your motto/ status">{{ playerDetails.motto }}</span>
				{% endif %}
				{% endautoescape %}
				<p style="display: none"><input type="text" length="30" name="motto" value=""/></p>
			</div>
		</div>
		<div id="motto-links" style="display: none"><a href="#" id="motto-cancel">Cancelar</a></div>
	</div>

	<ul id="link-bar" class="clearfix">
        <li class="change-looks"><a href="{{ site.sitePath }}/profile">Cambiar look &raquo;</a></li>
        <li class="credits">
            <a href="{{ site.sitePath }}/credits">{{ playerDetails.credits }}</a> Créditos		</li>
        <li class="club">
            {% if playerDetails.hasClubSubscription() %}
            <a href="{{ site.sitePath }}/club">{{ hcDays }} </a>Días restantes como HC		</li>
            {% else %}
            <a href="{{ site.sitePath }}/club">Unirte al {{ site.siteName }} club &raquo;</a>		</li>
            {% endif %}
        <li class="activitypoints">
            <a href="{{ site.sitePath }}/credits/pixels">{{ playerDetails.pixels }}</a> Pixeles		    </li>
    </ul>

    <div id="habbo-feed">
        <ul id="feed-items">
		{% if hasBirthday %}
                <li id="feed-birthday">
                  <div>
                    ¡Feliz <!-- anniversary --> cumpleaños, {{ playerDetails.name }}!<br />
                  </div>
                </li>
		{% endif %}
		{% for alert in alerts %}	
			{% if alert.getAlertType() == 'HC_EXPIRED' %}
			<li id="feed-item-hc-reminder">
				<a href="#" class="remove-feed-item" id="remove-hc-reminder" title="Remove notification">Remove notification</a>

				<div>Ha expirado tu pertenencia al {{ site.siteName }} Club. ¿Deseas renovar?	</div>
				<div class="clearfix">
					<table width="100px" style="margin-top:6px; margin-left:-12px">
						<tr>
							<td>
								<a class="new-button" id="subscribe1" href="#" onclick='habboclub.buttonClick(1, "Habbo Club"); return false;'><b>1 mes</b><i></i></a>
							</td>
							<td>
								<a class="new-button" id="subscribe2" href="#" onclick='habboclub.buttonClick(2, "Habbo Club"); return false;' style="margin-left:6px"><b>2 meses</b><i></i></a>
							</td>
							<td>
								<a class="new-button" id="subscribe2" href="#" onclick='habboclub.buttonClick(3, "Habbo Club"); return false;' style="margin-left:6px"><b>3 meses</b><i></i></a>
							</td>
						</tr>
					</table>
				</div>

			</li>
			{% elseif alert.getAlertType() == 'PRESENT' %}
			<li id="feed-item-dailygift" class="contributed">
				<a href="#" class="remove-feed-item" title="Remove notification">Cerrar notifiación</a>
				<div>{{ alert.getMessage }}</div>
			</li>
			{% elseif alert.getAlertType() == 'TUTOR_SCORE' %}
			<li id="feed-item-tutor-score" class="contributed">
				<a href="#" class="remove-feed-item" title="Remove notification">Cerrar notifiación</a>
				<div>{{ alert.getMessage }}</div>
			</li>
			{% elseif alert.getAlertType() == 'CREDIT_DONATION' %}
			<li id="feed-item-creditdonation" class="contributed">
				<a href="#" class="remove-feed-item" title="Remove notification">Cerrar notifiación</a>
				<div>{{ alert.getMessage }}</div>
			</li>
			{% endif %}
		{% endfor %}
			{% if feedFriendRequests > 0 %}
			<li id="feed-notification">
				Tienes <a href="{{ site.sitePath }}/client" onclick="HabboClient.openOrFocus(this); return false;"> {{ feedFriendRequests }} peticiones de Amigos</a> esperándote
			</li>
			{% endif %}
			
			{% set num = 1 %}
			{% if (feedFriendsOnline|length) > 0 %}
			<li id="feed-friends">
			{{ feedFriendsOnline|length }} de tus amigos estan conectados
			<span>
			{% for friend in feedFriendsOnline %}				
				<a href="{{ site.sitePath }}/home/{{ friend.getUsername() }}">{{ friend.getUsername() }}</a>{% if num < (feedFriendsOnline|length) %},{% endif %}{% set num = num + 1 %}
			{% endfor %}
			</span>
			</li>
			{% endif %}
			
			{% if unreadGuestbookMessages > 0 %}
			<li class="small" id="feed-guestbook">
			You have <a href="{{ site.sitePath }}/home/{{ playerDetails.getName() }}">tienes {{ unreadGuestbookMessages }} firmas nuevas</a> en tu home
			</li>
			{% endif %}
			
			{% set num = 1 %}
			{% if pendingMembers > 0 %}
			<li class="small" id="feed-pending-members">
			<strong>{{ pendingMembers }}</strong> de tus Grupos tienen miembros pendientes:
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
			<strong>{{ newPostsAmount }}</strong> de tus Grupos tienen nuevos mensajes en sus foros:
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
				<li class="small" id="feed-trading-enabled">Los tradeos están activos. Para desactivarlos haz clic <a href="{{ site.sitePath }}/profile?tab=6" title="">aquí</a></li>
			{% else %}
				<li class="small" id="feed-trading-disabled">Los tradeos están desactivados. Para activarlos haz clic <a href="{{ site.sitePath }}/profile?tab=6" title="">aquí</a></li>
			{% endif %}
			<!-- <li class="small" id="feed-flashbeta-invites">This server is currently in beta, some features may not be operating correctly. You may also expect spontaneous maintenance periods.</li> -->
            <li class="small" id="feed-lastlogin">
                Última Conexión:
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
                  ¡Selecciona tu sala!
                  <span class="habblet-close" id="habblet-close-roomselection" onclick="RoomSelectionHabblet.showConfirmation()"></span>
                </h2>
                    <div id="roomselection-plp-intro" class="box-content">
                    ¡Oye! No has escogido tu sala pre-decorada, la cual viene con furnis gratis. Escoge una de la lista:
                    </div>
                        <ul id="roomselection-plp" class="clearfix">
                            <li class="top">
                            <a class="roomselection-select new-button green-button" href="client?createRoom=0" target="client" onclick="return RoomSelectionHabblet.create(this, 0);"><b>Seleccionar</b><i></i></a>	
                            </li>
                            <li class="top">
                            <a class="roomselection-select new-button green-button" href="client?createRoom=1" target="client" onclick="return RoomSelectionHabblet.create(this, 1);"><b>Seleccionar</b><i></i></a>	
                            </li>
                            <li class="top">
                            <a class="roomselection-select new-button green-button" href="client?createRoom=2" target="client" onclick="return RoomSelectionHabblet.create(this, 2);"><b>Seleccionar</b><i></i></a>	
                            </li>
                            <li class="bottom">
                            <a class="roomselection-select new-button green-button" href="client?createRoom=3" target="client" onclick="return RoomSelectionHabblet.create(this, 3);"><b>Seleccionar</b><i></i></a>	
                            </li>
                            <li class="bottom">
                            <a class="roomselection-select new-button green-button" href="client?createRoom=4" target="client" onclick="return RoomSelectionHabblet.create(this, 4);"><b>Seleccionar</b><i></i></a>	
                            </li>
                            <li class="bottom">
                            <a class="roomselection-select new-button green-button" href="client?createRoom=5" target="client" onclick="return RoomSelectionHabblet.create(this, 5);"><b>Seleccionar</b><i></i></a>	
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
                    Your next gift!
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

	
							<h2 class="title">Novedades							</h2>
						<div id="hotcampaigns-habblet-list-container">
    <ul id="hotcampaigns-habblet-list">

        <li class="even">
            <div class="hotcampaign-container">
                <a href="{{ site.sitePath }}/articles"><img src="{{ site.staticContentPath }}/c_images/hot_campaign_images_gb/beta.gif" align="left" alt="" /></a>
                <h3>Under Construction</h3>
                <p>Put interesting text in here, because this text is just useless sitting here otherwise!</p>
                <p class="link"><a href="{{ site.sitePath }}">Go there &raquo;</a></p>
            </div>
        </li>
        
        <li class="odd">
            <div class="hotcampaign-container">
                <a href="{{ site.sitePath }}/articles"><img src="{{ site.staticContentPath }}/c_images/hot_campaign_images_gb/habbobetahot.gif" align="left" alt="" /></a>
                <h3>Under Construction</h3>
                <p>Put interesting text in here, because this text is just useless sitting here otherwise!</p>
                <p class="link"><a href="{{ site.sitePath }}">Go there &raquo;</a></p>
            </div>
        </li>
        
        <!-- 
        <li class="odd">
            <div class="hotcampaign-container">
                <a href="{{ site.sitePath }}/articles"><img src="{{ site.staticContentPath }}/c_images/hot_campaign_images_gb/hc.gif" align="left" alt="" /></a>
                <h3>Exclusive Furniture!</h3>
                <p>Join Habbo Club today and get access to exclusive furniture!</p>

                <p class="link"><a href="https://classichabbo.com/credits/club">Go there &raquo;</a></p>
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

                            <h2 class="title">bandeja de entrada                            </h2>
                        <div id="minimail">
		<div class="minimail-contents">
	    {% include "habblet/minimail/minimail_messages.tpl" %}
		</div>
		<div id="message-compose-wait"></div>
	    <form style="display: none" id="message-compose">
	        <div>To</div>
	        <div id="message-recipients-container" class="input-text" style="width: 426px; margin-bottom: 1em">
	        	<input type="text" value="" id="message-recipients" />
	        	<div class="autocomplete" id="message-recipients-auto">
	        		<div class="default" style="display: none;">Nombre de tu amig@</div>
	        		<ul class="feed" style="display: none;"></ul>

	        	</div>
	        </div>
	        <div>Asunto<br/>
	        <input type="text" style="margin: 5px 0" id="message-subject" class="message-text" maxlength="100" tabindex="2" />
	        </div>
	        <div>Mensaje<br/>
	        <textarea style="margin: 5px 0" rows="5" cols="10" id="message-body" class="message-text" tabindex="3"></textarea>

	        </div>
	        <div class="new-buttons clearfix">
	            <a href="#" class="new-button preview"><b>Previzualizar</b><i></i></a>
	            <a href="#" class="new-button send"><b>Enviar</b><i></i></a>
	        </div>
	    </form>
	</div>
				<script type="text/javascript">
		L10N.put("minimail.compose", "Nuevo mensaje").put("minimail.cancel", "Cancelar")
			.put("bbcode.colors.red", "Rojo").put("bbcode.colors.orange", "Naranja")
	    	.put("bbcode.colors.yellow", "Amarillo").put("bbcode.colors.green", "Verde")
	    	.put("bbcode.colors.cyan", "Cyan").put("bbcode.colors.blue", "Azul")
	    	.put("bbcode.colors.gray", "Gris").put("bbcode.colors.black", "Negro")
	    	.put("minimail.empty_body.confirm", "¿Seguro que quieres mandar el mensaje vacio?")
	    	.put("bbcode.colors.label", "Color").put("linktool.find.label", " ")
	    	.put("linktool.scope.habbos", "{{ site.siteName }}s").put("linktool.scope.rooms", "Salas")
	    	.put("linktool.scope.groups", "Grupos").put("minimail.report.title", "Reportar mensaje");

	    L10N.put("date.pretty.just_now", "justo ahora");
	    L10N.put("date.pretty.one_minute_ago", "Hace 1 minuto");
	    L10N.put("date.pretty.minutes_ago", "Hace {0} minutos");
	    L10N.put("date.pretty.one_hour_ago", "Hace 1 hora");
	    L10N.put("date.pretty.hours_ago", "Hace {0} horas");
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

        <li id="tab-0-4-2" class="selected"><a href="#">Invitar amigos</a><span class="tab-spacer"></span></li>
    </ul>
</div>
    <div id="tab-0-4-1-content"  style="display: none">
<div class="habblet-content-info">
    <a name="habbo-search">Escribe las primeras letras del nombre del otro {{ site.siteName }}.</a>
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
		<a  class="new-button" id="send-friend-invite-button" href="#"><b>Invite Friend(s)</b><i></i></a>
    </div>
    
    <hr/>
    </div>
    <div id="invitation-link-container">
        <h3>¡Disfruta de {{ site.siteName }} mas con amigos de la vida real!</h3>

        <div class="copytext">
            <p>¡Invita a tus amigos a Habbo y obtén placas increíbles! Mándales el link y diles que se registren y confirmen su email. Si visitan Habbo de una manera activa serás recompensado con una placa.</p>
        </div>
        <div class="invitation-buttons clearfix"> 
            <a  class="new-button" id="getlink-friend-invite-button" href="#"><b>¡Clic aquí para obtener el link!</b><i></i></a>
        </div>
    </div>
</div>
<script type="text/javascript">
    L10N.put("invitation.button.invite", "Invite Friend(s)");
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
	<p>¡Mira los eventos del hotel por su categoria!</p>
	<select id="event-category">
		<option value="1">Fiesta y Música</option>
		<option value="2">Tradeos</option>
		<option value="3">Juegos</option>
		<option value="4">Eventos Habbo Guías</option>
		<option value="5">Debates y Discusiones</option>
		<option value="6">Grandes Aperturas</option>
		<option value="7">Citas</option>
		<option value="8">Trabajos</option>
		<option value="9">Eventos de Grupos</option>
		<option value="10">Performance</option>
		<option value="11">Ayuda</option>
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
	            <h4>Últimas Noticias </a></h4>
	            <h3><a href="{{ site.sitePath }}/articles/{{ article1.getUrl() }}">{% if article1.isPublished() == false %}*{% endif %}{{ article1.title }}</a></h3>
	            <p class="summary">
	            {{ article1.shortstory }}	            </p>
	            <p>
	                <a href="{{ site.sitePath }}/articles/{{ article1.getUrl() }}">Leer más &raquo;</a>
	            </p>
	        </div>
	        <div class="topstory" style="background-image: url({{ article2.getLiveTopStory() }}); display: none">
	            <h4>Últimas Noticias</a></h4>
	            <h3><a href="{{ site.sitePath }}/articles/{{ article2.getUrl() }}">{% if article2.isPublished() == false %}*{% endif %}{{ article2.title }}</a></h3>
	            <p class="summary">
	            {{ article2.shortstory }}	            </p>
	            <p>
	                <a href="{{ site.sitePath }}/articles/{{ article2.getUrl() }}">Leer más &raquo;</a>
	            </p>
	        </div>
	        <div class="topstory" style="background-image: url({{ article3.getLiveTopStory() }}); display: none">
	            <h4>Últimas Noticias</a></h4>
	            <h3><a href="{{ site.sitePath }}/articles/{{ article3.getUrl() }}">{% if article3.isPublished() == false %}*{% endif %}{{ article3.title }}</a></h3>
	            <p class="summary">
	            {{ article3.shortstory }}	            </p>
	            <p>
	                <a href="{{ site.sitePath }}/articles/{{ article3.getUrl() }}">Leer más &raquo;</a>
	            </p>
	        </div>
            <div id="topstories-nav" style="display: none"><a href="#" class="prev">&laquo; Atras</a><span>1</span> / 3<a href="#" class="next">Sig. &raquo</a></div>
        </div>
        <ul class="widelist">
            <li class="even">
                <a href="{{ site.sitePath }}/articles/{{ article4.getUrl() }}">{% if article4.isPublished() == false %}*{% endif %}{{ article4.title }}</a><div class="newsitem-date">{{ article4.getDate() }}</div>
            </li>
            <li class="odd">
                <a href="{{ site.sitePath }}/articles/{{ article5.getUrl() }}">{% if article4.isPublished() == false %}*{% endif %}{{ article5.title }}</a><div class="newsitem-date">{{ article5.getDate() }}</div>
            </li>
            <li class="last"><a href="{{ site.sitePath }}/articles">Más noticias &raquo;</a></li>
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
					<!-- <p><iframe src="https://discordapp.com/widget?id=524768066907668521&theme=light" height="280" allowtransparency="true" frameborder="0"></iframe></p> -->
					</div>
					<script type="text/javascript">if (!$(document.body).hasClassName('process-template')) { Rounder.init(); }</script>
				<div class="habblet-container ">		
						<div class="cbb clearfix red ">
<div class="box-tabs-container clearfix">
    <h2>Selección Staff</h2>
    <ul class="box-tabs">
        <li id="tab-1-3-1"><a href="#">Salas</a><span class="tab-spacer"></span></li>
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
			<li class="{{ status }} {{ position }}" style="background-image: url({{ site.habboImagingPath }}/habbo-imaging/badge/{{ group.badge }}.gif)">
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
						<h2 class="title">Recomendaciones                            </h2>
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
								<li class="{{ status }} {{ position }}" style="background-image: url({{ site.habboImagingPath }}/habbo-imaging/badge/{{ group.badge }}.gif)">
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
    <h2>YoSoy     </h2>

							<ul class="box-tabs">
								<li id="tab-1-5-1"><a href="#">Loc@s por:</a><span class="tab-spacer"></span></li>
								<li id="tab-1-5-2" class="selected"><a href="#">Mis 'YoSoys'</a><span class="tab-spacer"></span></li>
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
							No tienes YoSoy
							{% endif %} Responde la pregunta de abajo o añade YoSoy de tu selección.		    </div>
							<div class="box-content">
							
							{% include "habblet/myTagList.tpl" %}
							</div>
					

<script type="text/javascript">
document.observe("dom:loaded", function() {
    TagHelper.setTexts({
        tagLimitText: "You\'ve reached the tag limit - delete one of your tags if you want to add a new one.",
        invalidTagText: "Invalid tag, the tag must be less than 20 characters and composed only of alphanumeric characters.",
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
        <li id="tab-2-1"><a href="#">Grupos populares</a><span class="tab-spacer"></span></li>
        <li id="tab-2-2" class="selected"><a href="#">Mis Grupos</a><span class="tab-spacer"></span></li>
    </ul>
</div>
    <div id="tab-2-1-content"  style="display: none">
    		<div class="progressbar"><img src="{{ site.staticContentPath }}/web-gallery/images/progress_bubbles.gif" alt="" width="29" height="6" /></div>
    		<a href="{{ site.sitePath }}/habblet/proxy?hid=groups" class="tab-ajax"></a>
    </div>
    <div id="tab-2-2-content" >


         <div id="groups-habblet-info" class="habblet-content-info">
                Mira los grupos en los que haces parte, crea tu propio grupo, o inspirate de la sección de 'Grupos populares'         </div>

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
				<li class="{{ status }} {{ position }}" style="background-image: url({{ site.habboImagingPath }}/habbo-imaging/badge/{{ group.badge }}.gif)">
						<a class="item" href="{{ group.generateClickLink() }}">{% autoescape 'html' %}{{ group.name }}{% endautoescape %}</a>
        </li>
				{% set i = i + 1 %}
		{% endfor %}
    </ul>
		<div class="habblet-button-row clearfix"><a class="new-button" id="purchase-group-button" href="#"><b>Create/buy a Group</b><i></i></a></div>
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