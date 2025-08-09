{% include "base/email_header.tpl" %}
<p style="font-size: 18px;"><b>Account Wiederherstellung</b></p>
<p>Hallo {{ playerName }},</a></p>
<p><b><a style="color: #66b3ff" href="{{ site.sitePath }}/account/password/recovery?id={{ playerId }}&code={{ recoveryCode }}">Account wiederherstellen</a>.</b></p>
<p>Dieser Link kann nur einmal verwendet werden und führt dich zu einer Seite, auf der du dein Passwort festlegen kannst. Wenn du ihn nicht verwendest, passiert nichts. Dieser verfällt nach einem Tag.</p>
<hr>
<p>Falls du keine Kontowiederherstellung angefordert hast, kannst du diese E-Mail ignorieren.</p>
{% include "base/email_footer.tpl" %}