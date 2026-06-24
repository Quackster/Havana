<div id="hc_confirm_box">

    <img src="{{ site.staticContentPath }}/web-gallery/images/piccolo_happy.gif" alt="" align="left" style="margin:10px;" />
<p><b>{{ locale.habblet_habboclubconfirm_confirmation }}</b></p>
<p>{{ clubMonths }} {{ site.siteName }} {{ locale.habblet_habboclubconfirm_club_month }}{{ clubDays }} {{ locale.habblet_habboclubconfirm_days_costs }} {{ clubCredits }} {{ locale.habblet_habboclubconfirm_coins_you_currently_have }} {{ playerDetails.credits }} {{ locale.habblet_habboclubconfirm_coins }}</p>

<p>
<a href="#" class="new-button" onclick="habboclub.closeSubscriptionWindow(); return false;">
<b>{{ locale.habblet_habboclubconfirm_cancel }}</b><i></i></a>
<a href="#" ondblclick="habboclub.showSubscriptionResultWindow({{ clubType }},''); return false;" onclick="habboclub.showSubscriptionResultWindow({{ clubType }},''); return false;" class="new-button">
<b>{{ locale.habblet_habboclubconfirm_ok }}</b><i></i></a>
</p>

</div>

<div class="clear"></div>