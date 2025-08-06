<div id="hc_confirm_box">

    <img src="{{ site.staticContentPath }}/web-gallery/images/piccolo_happy.gif" alt="" align="left" style="margin:10px;" />
<p><b>Confirmação</b></p>
<p>{{ clubMonths }} {{ clubDays }} dias de {{ site.siteName }} Club  custa {{ clubCredits }} Moedas. Você atualmente tem: {{ playerDetails.credits }} Moedas.</p>

<p>
<a href="#" class="new-button" onclick="habboclub.closeSubscriptionWindow(); return false;">
<b>Cancelar</b><i></i></a>
<a href="#" ondblclick="habboclub.showSubscriptionResultWindow({{ clubType }},''); return false;" onclick="habboclub.showSubscriptionResultWindow({{ clubType }},''); return false;" class="new-button">
<b>Ok</b><i></i></a>
</p>

</div>

<div class="clear"></div>