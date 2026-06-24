{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set catalogueCollectablesActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
    <h1 class="mt-4">{{ locale.housekeeping_catalogue_catalogue_collectables }}</h1>
	{% include "housekeeping/base/alert.tpl" %}
	<p><a href="/{{ site.housekeepingPath }}/catalogue/collectables/edit" class="btn btn-danger">{{ locale.housekeeping_catalogue_new_collectable_cycle }}</a></p>
	<div class="table-responsive"><table class="table table-striped"><thead><tr><th>{{ locale.housekeeping_catalogue_store_page }}</th><th>{{ locale.housekeeping_catalogue_admin_page }}</th><th>{{ locale.housekeeping_catalogue_expiry }}</th><th>{{ locale.housekeeping_catalogue_lifetime }}</th><th>{{ locale.housekeeping_catalogue_position }}</th><th>{{ locale.housekeeping_catalogue_class_names }}</th><th></th></tr></thead><tbody>
	{% for collectable in collectables %}
	<tr><td>{{ collectable.storePageId() }}</td><td>{{ collectable.adminPageId() }}</td><td>{{ collectable.expiry() }}</td><td>{{ collectable.lifetime() }}</td><td>{{ collectable.currentPosition() }}</td><td>{{ collectable.classNames() }}</td><td><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/collectables/edit?id={{ collectable.id() }}" class="btn btn-primary">{{ locale.housekeeping_catalogue_edit }}</a> <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/collectables/delete?id={{ collectable.id() }}" class="btn btn-danger">{{ locale.housekeeping_catalogue_delete }}</a></td></tr>
	{% endfor %}
	</tbody></table></div>
{% include "housekeeping/base/footer.tpl" %}
