{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set cataloguePackagesActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
    <h1 class="mt-4">{{ locale.housekeeping_catalogue_catalogue_packages }}</h1>
	{% include "housekeeping/base/alert.tpl" %}
	<p><a href="/{{ site.housekeepingPath }}/catalogue/packages/edit" class="btn btn-danger">{{ locale.housekeeping_catalogue_new_package_row }}</a></p>
	<div class="table-responsive"><table class="table table-striped"><thead><tr><th>{{ locale.housekeeping_catalogue_id }}</th><th>{{ locale.housekeeping_catalogue_sale_code }}</th><th>{{ locale.housekeeping_catalogue_definition }}</th><th>{{ locale.housekeeping_catalogue_special_sprite }}</th><th>{{ locale.housekeeping_catalogue_amount }}</th><th></th></tr></thead><tbody>
	{% for package in packages %}
	<tr><td>{{ package.id() }}</td><td>{{ package.saleCode() }}</td><td>{{ package.definitionId() }}</td><td>{{ package.specialSpriteId() }}</td><td>{{ package.amount() }}</td><td><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/packages/edit?id={{ package.id() }}" class="btn btn-primary">{{ locale.housekeeping_catalogue_edit }}</a> <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/packages/delete?id={{ package.id() }}" class="btn btn-danger">{{ locale.housekeeping_catalogue_delete }}</a></td></tr>
	{% endfor %}
	</tbody></table></div>
{% include "housekeeping/base/footer.tpl" %}
