{% if widgets.length == 0 %}
<div class="webstore-frank">
	<div class="blackbubble">
		<div class="blackbubble-body">

<p><b>¡Su inventario para esta categoría está completamente vacío!</b></p>
<p>Para poder comprar calcomanías, fondos y notas, haga clic en la pestaña de la tienda web y seleccione una categoría y un producto, luego haga clic en Comprar.</p>

		<div class="clear"></div>
		</div>
	</div>
	<div class="blackbubble-bottom">
		<div class="blackbubble-bottom-body">
			<img src="{{ site.staticContentPath }}/web-gallery/images/box-scale/bubble_tail_small.gif" alt="" width="12" height="21" class="invitation-tail" />
		</div>
	</div>
	<div class="webstore-frank-image"><img src="{{ site.staticContentPath }}/web-gallery/images/frank/sorry.gif" alt="" width="57" height="88" /></div>
</div>
{% endif%}

<ul id="inventory-item-list">

{% for widget in widgets %}
	<li id={% if widgetMode %}"inventory-item-p-{{ widget.getId() }}"{% else %}"inventory-item-{{ widget.getId() }}"{% endif %} title="{{ widget.getProduct().getName() }}" {% if widgetMode %}class="webstore-widget-item {% if widget.isPlaced() %}webstore-widget-disabled{% endif %}"{% endif %}>
		<div class="webstore-item-preview {{ widget.getProduct().getCssClass() }}">
			<div class="webstore-item-mask">
			{% if widget.getAmount() > 1 %}
				<div class="webstore-item-count"><div>x{{ widget.getAmount() }}</div></div>	
			{% endif %}
			</div>
		</div>
			{% if widgetMode %}
			<div class="webstore-widget-description">
				<h3>{{ widget.getProduct().getName() }}</h3>
				<p>{{ widget.getProduct().getDescription() }}</p>
			</div>
			{% endif %}
	</li>
{% endfor %}

{% if emptyBoxes|length > 0 %}
{% for box in emptyBoxes %}
	<li class="webstore-item-empty"></li>
{% endfor %}
{% endif %}

</ul>
