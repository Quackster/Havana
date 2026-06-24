{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set cataloguePagesActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
    <h1 class="mt-4">Catalogue Pages</h1>
	{% include "housekeeping/base/alert.tpl" %}
	<p><a href="/{{ site.housekeepingPath }}/catalogue/pages/edit" class="btn btn-danger">New Page</a></p>
	<div class="table-responsive">
		<table class="table table-striped">
			<thead>
				<tr>
					<th>ID</th>
					<th>Parent</th>
					<th>Order</th>
					<th>Name</th>
					<th>Layout</th>
					<th>Rank</th>
					<th>Flags</th>
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
					<td>{% if page.navigatable() %}Navigatable{% endif %}{% if page.clubOnly() %} Club{% endif %}</td>
					<td>
						<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/pages/edit?id={{ page.id() }}" class="btn btn-primary">Edit</a>
						<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/pages/delete?id={{ page.id() }}" class="btn btn-danger">Delete</a>
					</td>
				</tr>
				{% endfor %}
			</tbody>
		</table>
	</div>
{% include "housekeeping/base/footer.tpl" %}
