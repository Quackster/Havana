<input type="hidden" name="webwork.token.name" value="webwork.token"/>
<input type="hidden" name="webwork.token" value="{{ locale.groups_habblet_havanaweb }}"/>
<div id="group-logo">
   <img src="{{ site.sitePath }}/web-gallery/images/groups/group_icon.gif" alt="" width="46" height="46" />
</div>

<p>
{{ locale.groups_habblet_group_name }} <b>{{ groupName }}</b>.<br>{{ locale.groups_habblet_price }} <b>{{ groupCost }} {{ locale.groups_habblet_credits }}</b>.<br> {{ locale.groups_habblet_you_have }} <b>{{ playerDetails.credits }} {{ locale.groups_habblet_credits }}</b>.
</p>

<div id="group-confirmation-button-area">	
<div class="new-buttons clearfix">
	<a class="new-button" href="#" onclick="GroupPurchase.close(); return false;"><b>{{ locale.groups_habblet_cancel }}</b><i></i></a>	
	<a class="new-button" href="#" onclick="GroupPurchase.purchase(); return false;"><b>{{ locale.groups_habblet_buy_this }}</b><i></i></a>
</div>
</div>