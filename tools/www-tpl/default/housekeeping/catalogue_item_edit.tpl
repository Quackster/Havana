{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set catalogueItemsActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
    <h1 class="mt-4">{% if item != null %}{{ locale.housekeeping_catalogue_edit }}{% else %}{{ locale.housekeeping_catalogue_create }}{% endif %} {{ locale.housekeeping_catalogue_catalogue_item }}</h1>
	{% include "housekeeping/base/alert.tpl" %}
	<form class="table-responsive col-md-8" method="post">
		<div class="form-row">
			<div class="form-group col-md-6"><label>{{ locale.housekeeping_catalogue_sale_code }}</label><input type="text" class="form-control" name="sale_code" value="{% if item != null %}{{ item.saleCode() }}{% endif %}"></div>
			<div class="form-group col-md-6"><label>{{ locale.housekeeping_catalogue_page_ids }}</label><input type="text" class="form-control" name="page_id" value="{% if item != null %}{{ item.pageId() }}{% endif %}"></div>
		</div>
		<div class="form-row">
			<div class="form-group col-md-3"><label>{{ locale.housekeeping_catalogue_order }}</label><input type="number" class="form-control" name="order_id" value="{% if item != null %}{{ item.orderId() }}{% else %}0{% endif %}"></div>
			<div class="form-group col-md-3"><label>{{ locale.housekeeping_catalogue_coins }}</label><input type="number" class="form-control" name="price_coins" value="{% if item != null %}{{ item.priceCoins() }}{% else %}3{% endif %}"></div>
			<div class="form-group col-md-3"><label>{{ locale.housekeeping_catalogue_pixels }}</label><input type="number" class="form-control" name="price_pixels" value="{% if item != null %}{{ item.pricePixels() }}{% else %}0{% endif %}"></div>
			<div class="form-group col-md-3"><label>{{ locale.housekeeping_catalogue_amount }}</label><input type="number" class="form-control" name="amount" value="{% if item != null %}{{ item.amount() }}{% else %}1{% endif %}"></div>
		</div>
		<div class="form-row">
			<div class="form-group col-md-3"><label>{{ locale.housekeeping_catalogue_seasonal_coins }}</label><input type="number" class="form-control" name="seasonal_coins" value="{% if item != null %}{{ item.seasonalCoins() }}{% else %}0{% endif %}"></div>
			<div class="form-group col-md-3"><label>{{ locale.housekeeping_catalogue_seasonal_pixels }}</label><input type="number" class="form-control" name="seasonal_pixels" value="{% if item != null %}{{ item.seasonalPixels() }}{% else %}0{% endif %}"></div>
			<div class="form-group col-md-3"><label>{{ locale.housekeeping_catalogue_definition_id }}</label><input type="number" class="form-control" name="definition_id" value="{% if item != null %}{{ item.definitionId() }}{% else %}0{% endif %}"></div>
			<div class="form-group col-md-3"><label>{{ locale.housekeeping_catalogue_active_at }}</label><input type="text" class="form-control" name="active_at" value="{% if item != null and item.activeAt() != null %}{{ item.activeAt() }}{% endif %}"></div>
		</div>
		<div class="form-group"><label>{{ locale.housekeeping_catalogue_special_sprite_id }}</label><input type="text" class="form-control" name="item_specialspriteid" value="{% if item != null %}{{ item.itemSpecialSpriteId() }}{% endif %}"></div>
		<div class="form-group form-check"><input type="checkbox" class="form-check-input" name="hidden" {% if item != null and item.hidden() %}checked{% endif %}><label class="form-check-label">{{ locale.housekeeping_catalogue_hidden }}</label></div>
		<div class="form-group form-check"><input type="checkbox" class="form-check-input" name="is_package" {% if item != null and item.packageItem() %}checked{% endif %}><label class="form-check-label">{{ locale.housekeeping_catalogue_package }}</label></div>
		<button type="submit" class="btn btn-info">{{ locale.housekeeping_catalogue_save_item }}</button>
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/items" class="btn btn-secondary">{{ locale.housekeeping_catalogue_back }}</a>
	</form>
{% include "housekeeping/base/footer.tpl" %}
