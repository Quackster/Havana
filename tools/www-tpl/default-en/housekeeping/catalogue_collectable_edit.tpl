{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set catalogueCollectablesActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
    <h1 class="mt-4">{% if collectable != null %}Edit{% else %}Create{% endif %} Catalogue Collectable</h1>
	{% include "housekeeping/base/alert.tpl" %}
	<form class="table-responsive col-md-8" method="post">
		<div class="form-row">
			<div class="form-group col-md-4"><label>Store Page</label><input type="number" class="form-control" name="store_page" value="{% if collectable != null %}{{ collectable.storePageId() }}{% else %}0{% endif %}"></div>
			<div class="form-group col-md-4"><label>Admin Page</label><input type="number" class="form-control" name="admin_page" value="{% if collectable != null %}{{ collectable.adminPageId() }}{% else %}0{% endif %}"></div>
			<div class="form-group col-md-4"><label>Current Position</label><input type="number" class="form-control" name="current_position" value="{% if collectable != null %}{{ collectable.currentPosition() }}{% else %}0{% endif %}"></div>
		</div>
		<div class="form-row">
			<div class="form-group col-md-6"><label>Expiry Timestamp</label><input type="number" class="form-control" name="expiry" value="{% if collectable != null %}{{ collectable.expiry() }}{% else %}-1{% endif %}"></div>
			<div class="form-group col-md-6"><label>Lifetime Seconds</label><input type="number" class="form-control" name="lifetime" value="{% if collectable != null %}{{ collectable.lifetime() }}{% else %}2678400{% endif %}"></div>
		</div>
		<div class="form-group"><label>Class Names</label><textarea class="form-control" name="class_names" rows="4">{% if collectable != null %}{{ collectable.classNames() }}{% endif %}</textarea></div>
		<button type="submit" class="btn btn-info">Save Collectable</button>
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/collectables" class="btn btn-secondary">Back</a>
	</form>
{% include "housekeeping/base/footer.tpl" %}
