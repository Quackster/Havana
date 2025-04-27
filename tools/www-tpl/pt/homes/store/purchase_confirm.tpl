<div class="webstore-item-preview {{ product.getCssClass() }}">
	<div class="webstore-item-mask">
		
	</div>
</div>

{% if noCredits %}
<p>
Você não tem créditos suficientes para comprar isto</p>

<div class="clear"></div>
{% else %}
<p>
Tem certeza de que quer comprar este produto?</p>

<p class="new-buttons">
<a href="#" class="new-button" id="webstore-confirm-cancel"><b>Cancelar</b><i></i></a>
<a href="#" class="new-button" id="webstore-confirm-submit"><b>Continuar</b><i></i></a>
</p>

<div class="clear"></div>
{% endif %}