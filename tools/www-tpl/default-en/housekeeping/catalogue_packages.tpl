{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set cataloguePackagesActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
    <h1 class="mt-4">Catalogue Packages</h1>
	{% include "housekeeping/base/alert.tpl" %}
	<p><a href="/{{ site.housekeepingPath }}/catalogue/packages/edit" class="btn btn-danger">New Package Row</a></p>
	<div class="table-responsive"><table class="table table-striped"><thead><tr><th>ID</th><th>Sale Code</th><th>Definition</th><th>Special Sprite</th><th>Amount</th><th></th></tr></thead><tbody>
	{% for package in packages %}
	<tr><td>{{ package.id() }}</td><td>{{ package.saleCode() }}</td><td>{{ package.definitionId() }}</td><td>{{ package.specialSpriteId() }}</td><td>{{ package.amount() }}</td><td><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/packages/edit?id={{ package.id() }}" class="btn btn-primary">Edit</a> <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/packages/delete?id={{ package.id() }}" class="btn btn-danger">Delete</a></td></tr>
	{% endfor %}
	</tbody></table></div>
{% include "housekeeping/base/footer.tpl" %}
