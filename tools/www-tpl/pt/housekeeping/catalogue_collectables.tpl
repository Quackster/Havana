{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set catalogueCollectablesActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
    <h1 class="mt-4">Catalogue Collectables</h1>
	{% include "housekeeping/base/alert.tpl" %}
	<p><a href="/{{ site.housekeepingPath }}/catalogue/collectables/edit" class="btn btn-danger">New Collectable Cycle</a></p>
	<div class="table-responsive"><table class="table table-striped"><thead><tr><th>Store Page</th><th>Admin Page</th><th>Expiry</th><th>Lifetime</th><th>Position</th><th>Class Names</th><th></th></tr></thead><tbody>
	{% for collectable in collectables %}
	<tr><td>{{ collectable.storePageId() }}</td><td>{{ collectable.adminPageId() }}</td><td>{{ collectable.expiry() }}</td><td>{{ collectable.lifetime() }}</td><td>{{ collectable.currentPosition() }}</td><td>{{ collectable.classNames() }}</td><td><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/collectables/edit?id={{ collectable.id() }}" class="btn btn-primary">Edit</a> <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/collectables/delete?id={{ collectable.id() }}" class="btn btn-danger">Delete</a></td></tr>
	{% endfor %}
	</tbody></table></div>
{% include "housekeeping/base/footer.tpl" %}
