<div id="group-logo">
   <img src="{{ site.sitePath }}/web-gallery/images/groups/group_icon.gif" alt="" width="46" height="46" />
</div>

<p id="purchase-result-success">
Felicitaciones: usted es el orgulloso propietario de <b>{{ groupName }}</b>
</p>

<p>

<div class="new-buttons clearfix">
	<a class="new-button" id="group-purchase-cancel-button" href="#" onclick="GroupPurchase.close(); return false;"><b>Más tarde</b><i></i></a>	
	<a class="new-button" href="{{ site.sitePath }}/groups/{{ groupId }}/id"><b>Ir a la pagina</b><i></i></a>
</div>

</p>

<script language="JavaScript" type="text/javascript">
	updateHabboCreditAmounts('{{ deductedCredits }}');
</script>