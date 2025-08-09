<input type="hidden" name="webwork.token.name" value="webwork.token"/>
<input type="hidden" name="webwork.token" value="HavanaWeb"/>
<div id="group-logo">
   <img src="{{ site.sitePath }}/web-gallery/images/groups/group_icon.gif" alt="" width="46" height="46" />
</div>

<p>
Gruppenname: <b>{{ groupName }}</b>.<br>Preis: <b>{{ groupCost }} Münzen</b>.<br> Du hast: <b>{{ playerDetails.credits }} Münzen</b>.
</p>

<div id="group-confirmation-button-area">	
<div class="new-buttons clearfix">
	<a class="new-button" href="#" onclick="GroupPurchase.close(); return false;"><b>Abbrechen</b><i></i></a>	
	<a class="new-button" href="#" onclick="GroupPurchase.purchase(); return false;"><b>Kaufen</b><i></i></a>
</div>
</div>