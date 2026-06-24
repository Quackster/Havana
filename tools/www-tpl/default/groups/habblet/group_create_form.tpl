<div id="group-purchase-header">
   <img src="{{ site.sitePath }}/web-gallery/images/groups/group_icon.gif" alt="" width="46" height="46" />
</div>

<p>
{{ locale.groups_habblet_price }} <b>{{ groupCost }} {{ locale.groups_habblet_credits }}</b>.<br> {{ locale.groups_habblet_you_have }} <b>{{ playerDetails.credits }} {{ locale.groups_habblet_credits }}</b>.
</p>

<form action="#" method="post" id="purchase-group-form-id">

<div id="group-name-area">
    <div id="group_name_message_error" class="error"></div>
    <label for="group_name" id="group_name_text">{{ locale.groups_habblet_group_name }}</label>
    <input type="text" name="group_name" id="group_name" maxlength="30" onKeyUp="GroupUtils.validateGroupElements('group_name', 30, 'Maximum Group name length reached');" value=""/><br />
</div>

<div id="group-description-area">
    <div id="group_description_message_error" class="error"></div>
    <label for="group_description" id="description_text">{{ locale.groups_habblet_group_description }}</label>
    <span id="description_chars_left"><label for="characters_left">{{ locale.groups_habblet_characters_left }}</label>
    <input id="group_description-counter" type="text" value="255" size="3" readonly="readonly" class="amount" /></span><br/>
    <textarea name="group_description" id="group_description" onKeyUp="GroupUtils.validateGroupElements('group_description', 255, 'Maximum description length reached');"></textarea>
</div>
</form>

<div class="new-buttons clearfix">
	<a class="new-button" id="group-purchase-cancel-button" href="#" onclick='GroupPurchase.close(); return false;'><b>{{ locale.groups_habblet_cancel }}</b><i></i></a>	
	<a class="new-button" href="#" onclick="GroupPurchase.confirm(); return false;"><b>{{ locale.groups_habblet_buy_this_group }}</b><i></i></a>
</div>
