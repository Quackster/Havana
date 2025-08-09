<div class="webstore-item-preview {{ product.getCssClass() }}">
	<div class="webstore-item-mask">
		
	</div>
</div>

{% if noCredits %}
<p>
Du hast nicht genügend Münzen um das zu kaufen...</p>

<div class="clear"></div>
{% else %}
<p>
Möchtest du das wirklich kaufen?</p>

<p class="new-buttons">
<a href="#" class="new-button" id="webstore-confirm-cancel"><b>Abbrechen</b><i></i></a>
<a href="#" class="new-button" id="webstore-confirm-submit"><b>Kaufen</b><i></i></a>
</p>

<div class="clear"></div>
{% endif %}