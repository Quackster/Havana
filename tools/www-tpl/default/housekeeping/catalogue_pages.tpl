{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set cataloguePagesActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
    <h1 class="mt-4">{{ locale.housekeeping_catalogue_catalogue_pages }}</h1>
	{% include "housekeeping/base/alert.tpl" %}
	<p><a href="/{{ site.housekeepingPath }}/catalogue/pages/edit" class="btn btn-danger">{{ locale.housekeeping_catalogue_new_page }}</a></p>
	<div class="table-responsive">
		<table class="table table-striped">
			<thead>
				<tr>
					<th>{{ locale.housekeeping_catalogue_id }}</th>
					<th>{{ locale.housekeeping_catalogue_parent }}</th>
					<th>{{ locale.housekeeping_catalogue_order }}</th>
					<th>{{ locale.housekeeping_catalogue_name }}</th>
					<th>{{ locale.housekeeping_catalogue_layout }}</th>
					<th>{{ locale.housekeeping_catalogue_rank }}</th>
					<th>{{ locale.housekeeping_catalogue_flags }}</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				{% for page in pages %}
				<tr>
					<td>{{ page.id() }}</td>
					<td>{{ page.parentId() }}</td>
					<td>{{ page.orderId() }}</td>
					<td>{{ page.name() }}</td>
					<td>{{ page.layout() }}</td>
					<td>{{ page.minRole() }}</td>
					<td>{% if page.navigatable() %}{{ locale.housekeeping_catalogue_navigatable }}{% endif %}{% if page.clubOnly() %} {{ locale.housekeeping_catalogue_club }}{% endif %}</td>
					<td>
						<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/pages/edit?id={{ page.id() }}" class="btn btn-primary">{{ locale.housekeeping_catalogue_edit }}</a>
						<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/pages/delete?id={{ page.id() }}" class="btn btn-danger">{{ locale.housekeeping_catalogue_delete }}</a>
					</td>
				</tr>
				{% endfor %}
			</tbody>
		</table>
	</div>
{% include "housekeeping/base/footer.tpl" %}
