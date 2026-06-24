{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set catalogueItemsActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
    <h1 class="mt-4">{% if item != null %}Edit{% else %}Create{% endif %} Catalogue Item</h1>
	{% include "housekeeping/base/alert.tpl" %}
	<form class="table-responsive col-md-8" method="post">
		<div class="form-row">
			<div class="form-group col-md-6"><label>Sale Code</label><input type="text" class="form-control" name="sale_code" value="{% if item != null %}{{ item.saleCode() }}{% endif %}"></div>
			<div class="form-group col-md-6"><label>Page IDs</label><input type="text" class="form-control" name="page_id" value="{% if item != null %}{{ item.pageId() }}{% endif %}"></div>
		</div>
		<div class="form-row">
			<div class="form-group col-md-3"><label>Order</label><input type="number" class="form-control" name="order_id" value="{% if item != null %}{{ item.orderId() }}{% else %}0{% endif %}"></div>
			<div class="form-group col-md-3"><label>Coins</label><input type="number" class="form-control" name="price_coins" value="{% if item != null %}{{ item.priceCoins() }}{% else %}3{% endif %}"></div>
			<div class="form-group col-md-3"><label>Pixels</label><input type="number" class="form-control" name="price_pixels" value="{% if item != null %}{{ item.pricePixels() }}{% else %}0{% endif %}"></div>
			<div class="form-group col-md-3"><label>Amount</label><input type="number" class="form-control" name="amount" value="{% if item != null %}{{ item.amount() }}{% else %}1{% endif %}"></div>
		</div>
		<div class="form-row">
			<div class="form-group col-md-3"><label>Seasonal Coins</label><input type="number" class="form-control" name="seasonal_coins" value="{% if item != null %}{{ item.seasonalCoins() }}{% else %}0{% endif %}"></div>
			<div class="form-group col-md-3"><label>Seasonal Pixels</label><input type="number" class="form-control" name="seasonal_pixels" value="{% if item != null %}{{ item.seasonalPixels() }}{% else %}0{% endif %}"></div>
			<div class="form-group col-md-3"><label>Definition ID</label><input type="number" class="form-control" name="definition_id" value="{% if item != null %}{{ item.definitionId() }}{% else %}0{% endif %}"></div>
			<div class="form-group col-md-3"><label>Active At</label><input type="text" class="form-control" name="active_at" value="{% if item != null and item.activeAt() != null %}{{ item.activeAt() }}{% endif %}"></div>
		</div>
		<div class="form-group"><label>Special Sprite ID</label><input type="text" class="form-control" name="item_specialspriteid" value="{% if item != null %}{{ item.itemSpecialSpriteId() }}{% endif %}"></div>
		<div class="form-group form-check"><input type="checkbox" class="form-check-input" name="hidden" {% if item != null and item.hidden() %}checked{% endif %}><label class="form-check-label">Hidden</label></div>
		<div class="form-group form-check"><input type="checkbox" class="form-check-input" name="is_package" {% if item != null and item.packageItem() %}checked{% endif %}><label class="form-check-label">Package</label></div>
		<button type="submit" class="btn btn-info">Save Item</button>
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/items" class="btn btn-secondary">Back</a>
	</form>
{% include "housekeeping/base/footer.tpl" %}
