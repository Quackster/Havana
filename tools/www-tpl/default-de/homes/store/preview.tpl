<h4 title=""></h4>

<div id="webstore-preview-box"></div>

<div id="webstore-preview-price">
Preis:<br /><b>
	{% if product.getPrice() > 1 %}{{ product.getPrice() }} credits{% else %}{{ product.getPrice() }} credit{% endif %}
</b>
</div>

<div id="webstore-preview-purse">
{% if playerDetails.credits > 1 %}Du hast:<br /><b>{{ playerDetails.credits }} Münzen</b><br />{% else %}Du hast:<br /><b>{{ playerDetails.credits }} Münzen</b><br />{% endif %}
<a href="{{ site.sitePath }}/credits" target=_blank>Münzen bekommen</a>
</div>

<div id="webstore-preview-purchase" class="clearfix">
	<div class="clearfix">
		<a href="#" class="new-button" id="webstore-purchase"><b>Kaufen</b><i></i></a>
	</div>
</div>

<span id="webstore-preview-bg-text" style="display: none">Vorschau</span>		