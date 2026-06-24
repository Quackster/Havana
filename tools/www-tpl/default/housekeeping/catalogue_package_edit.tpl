{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set cataloguePackagesActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
    <h1 class="mt-4">{% if cataloguePackage != null %}{{ locale.housekeeping_catalogue_edit }}{% else %}{{ locale.housekeeping_catalogue_create }}{% endif %} {{ locale.housekeeping_catalogue_catalogue_package_row }}</h1>
	{% include "housekeeping/base/alert.tpl" %}
	<form class="table-responsive col-md-6" method="post">
		<div class="form-group"><label>{{ locale.housekeeping_catalogue_sale_code }}</label><input type="text" class="form-control" name="salecode" value="{% if cataloguePackage != null %}{{ cataloguePackage.saleCode() }}{% endif %}"></div>
		<div class="form-group"><label>{{ locale.housekeeping_catalogue_definition_id }}</label><input type="number" class="form-control" name="definition_id" value="{% if cataloguePackage != null %}{{ cataloguePackage.definitionId() }}{% else %}0{% endif %}"></div>
		<div class="form-group"><label>{{ locale.housekeeping_catalogue_special_sprite_id }}</label><input type="text" class="form-control" name="special_sprite_id" value="{% if cataloguePackage != null %}{{ cataloguePackage.specialSpriteId() }}{% endif %}"></div>
		<div class="form-group"><label>{{ locale.housekeeping_catalogue_amount }}</label><input type="number" class="form-control" name="amount" value="{% if cataloguePackage != null %}{{ cataloguePackage.amount() }}{% else %}1{% endif %}"></div>
		<button type="submit" class="btn btn-info">{{ locale.housekeeping_catalogue_save_package_row }}</button>
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/packages" class="btn btn-secondary">{{ locale.housekeeping_catalogue_back }}</a>
	</form>
{% include "housekeeping/base/footer.tpl" %}
