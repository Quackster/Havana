<div id="group-purchase-header">
   <img src="{{ site.sitePath }}/web-gallery/images/groups/group_icon.gif" alt="" width="46" height="46" />
</div>

<p>
Precio: <b>{{ groupCost }} Credits</b>.<br> Tienes: <b>{{ playerDetails.credits }} Credits</b>.
</p>

<form action="#" method="post" id="purchase-group-form-id">

<div id="group-name-area">
    <div id="group_name_message_error" class="error"></div>
    <label for="group_name" id="group_name_text">Nombre del grupo:</label>
    <input type="text" name="group_name" id="group_name" maxlength="30" onKeyUp="GroupUtils.validateGroupElements('group_name', 30, 'Se alcanzó la longitud máxima del nombre del grupo');" value=""/><br />
</div>

<div id="group-description-area">
    <div id="group_description_message_error" class="error"></div>
    <label for="group_description" id="description_text">Group description:</label>
    <span id="description_chars_left"><label for="characters_left">restantes:</label>
    <input id="group_description-counter" type="text" value="255" size="3" readonly="readonly" class="amount" /></span><br/>
    <textarea name="group_description" id="group_description" onKeyUp="GroupUtils.validateGroupElements('group_description', 255, 'Longitud máxima de descripción alcanzada');"></textarea>
</div>
</form>

<div class="new-buttons clearfix">
	<a class="new-button" id="group-purchase-cancel-button" href="#" onclick='GroupPurchase.close(); return false;'><b>Cancelar</b><i></i></a>	
	<a class="new-button" href="#" onclick="GroupPurchase.confirm(); return false;"><b>Comprar este grupo</b><i></i></a>
</div>
