<h4 title=""></h4>

<div id="webstore-preview-box"></div>

<div id="webstore-preview-price">
{{ locale.homes_store_price }}<br /><b>
	{% if product.getPrice() > 1 %}{{ product.getPrice() }} {{ locale.homes_store_credits }}{% else %}{{ product.getPrice() }} {{ locale.homes_store_credit }}{% endif %}
</b>
</div>

<div id="webstore-preview-purse">
{% if playerDetails.credits > 1 %}{{ locale.homes_store_you_have }}<br /><b>{{ playerDetails.credits }} {{ locale.homes_store_credits }}</b><br />{% else %}{{ locale.homes_store_you_have }}<br /><b>{{ playerDetails.credits }} {{ locale.homes_store_credit }}</b><br />{% endif %}
<a href="{{ site.sitePath }}/credits" target=_blank>{{ locale.homes_store_get_credits }}</a>
</div>

<div id="webstore-preview-purchase" class="clearfix">
	<div class="clearfix">
		<a href="#" class="new-button" id="webstore-purchase"><b>{{ locale.homes_store_purchase }}</b><i></i></a>
	</div>
</div>

<span id="webstore-preview-bg-text" style="display: none">{{ locale.homes_store_preview }}</span>		