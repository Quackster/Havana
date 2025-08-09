{% include "base/email_header.tpl" %}
<p style="font-size: 18px;"><b>Willkommen bei {{ site.siteName}}, {{ playerName }}!</b></p>
<p>Danke fürs aktivieren deiner Email bei <a style="color: #66b3ff" href="{{ site.sitePath }}/?email">{{ site.emailHotelName }}</a>.</p>
<p><b><a style="color: #66b3ff" href="{{ site.sitePath }}/account/activate?id={{ playerId }}&code={{ activationCode }}">Bitte aktiviere dein Konto, indem du hier klickst</a>.</b></p>
<p>Hier sind deine Daten:</p>
<p style="margin-left: 30px;"><b>{{ site.siteName}} Name:</b> {{ playerName }}</p>
<p style="margin-left: 30px;"><b>Email:</b> {{ playerEmail }}</p>
<p>Bewahre diese Informationen sicher auf. Du benötigst deinen Benutzernamen und deine E-Mail-Adresse, um dein Passwort zurückzusetzen.</br>
<hr>
<p>Hier sind noch andere Dinge, die du vielleicht tun möchtest:</p>
</ul>
	<li style="padding-left: 30px;"><a style="color: #66b3ff" href="{{ site.sitePath }}/profile?tab=2">Accounteinstellung ändern</a>.</li>
	<li style="padding-left: 30px;"><a style="color: #66b3ff" href="{{ site.sitePath }}/?emaildelete">Lösche diese E-Mail-Adresse vollständig aus deinem Profil</a>.</li>
</ul>
{% include "base/email_footer.tpl" %}