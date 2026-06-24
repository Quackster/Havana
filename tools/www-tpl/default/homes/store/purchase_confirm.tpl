<div class="webstore-item-preview {{ product.getCssClass() }}">
	<div class="webstore-item-mask">
		
	</div>
</div>

{% if noCredits %}
<p>
{{ locale.homes_store_you_do_not_have_enough_credits_to_purchase_this }}</p>

<div class="clear"></div>
{% else %}
<p>
{{ locale.homes_store_are_you_sure_you_want_to_purchase_this_product }}</p>

<p class="new-buttons">
<a href="#" class="new-button" id="webstore-confirm-cancel"><b>{{ locale.homes_store_cancel }}</b><i></i></a>
<a href="#" class="new-button" id="webstore-confirm-submit"><b>{{ locale.homes_store_continue }}</b><i></i></a>
</p>

<div class="clear"></div>
{% endif %}