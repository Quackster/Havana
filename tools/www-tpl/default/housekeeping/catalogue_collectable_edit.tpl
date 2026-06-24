{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set catalogueCollectablesActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
    <h1 class="mt-4">{% if collectable != null %}{{ locale.housekeeping_catalogue_edit }}{% else %}{{ locale.housekeeping_catalogue_create }}{% endif %} {{ locale.housekeeping_catalogue_catalogue_collectable }}</h1>
	{% include "housekeeping/base/alert.tpl" %}
	<form class="table-responsive col-md-8" method="post">
		<div class="form-row">
			<div class="form-group col-md-4"><label>{{ locale.housekeeping_catalogue_store_page }}</label><input type="number" class="form-control" name="store_page" value="{% if collectable != null %}{{ collectable.storePageId() }}{% else %}0{% endif %}"></div>
			<div class="form-group col-md-4"><label>{{ locale.housekeeping_catalogue_admin_page }}</label><input type="number" class="form-control" name="admin_page" value="{% if collectable != null %}{{ collectable.adminPageId() }}{% else %}0{% endif %}"></div>
			<div class="form-group col-md-4"><label>{{ locale.housekeeping_catalogue_current_position }}</label><input type="number" class="form-control" name="current_position" value="{% if collectable != null %}{{ collectable.currentPosition() }}{% else %}0{% endif %}"></div>
		</div>
		<div class="form-row">
			<div class="form-group col-md-6"><label>{{ locale.housekeeping_catalogue_expiry_timestamp }}</label><input type="number" class="form-control" name="expiry" value="{% if collectable != null %}{{ collectable.expiry() }}{% else %}-1{% endif %}"></div>
			<div class="form-group col-md-6"><label>{{ locale.housekeeping_catalogue_lifetime_seconds }}</label><input type="number" class="form-control" name="lifetime" value="{% if collectable != null %}{{ collectable.lifetime() }}{% else %}2678400{% endif %}"></div>
		</div>
		<div class="form-group"><label>{{ locale.housekeeping_catalogue_class_names }}</label><textarea class="form-control" name="class_names" rows="4">{% if collectable != null %}{{ collectable.classNames() }}{% endif %}</textarea></div>
		<button type="submit" class="btn btn-info">{{ locale.housekeeping_catalogue_save_collectable }}</button>
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/collectables" class="btn btn-secondary">{{ locale.housekeeping_catalogue_back }}</a>
	</form>
{% include "housekeeping/base/footer.tpl" %}
