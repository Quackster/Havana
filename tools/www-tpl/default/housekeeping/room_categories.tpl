{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set roomCategoriesActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
    <h1 class="mt-4">{{ locale.housekeeping_room_room_categories }}</h1>
	{% include "housekeeping/base/alert.tpl" %}
	<p><a href="/{{ site.housekeepingPath }}/room_categories/edit" class="btn btn-danger">{{ locale.housekeeping_room_new_category }}</a></p>
	<div class="table-responsive">
		<table class="table table-striped">
			<thead>
				<tr>
					<th>{{ locale.housekeeping_room_id }}</th>
					<th>{{ locale.housekeeping_room_order }}</th>
					<th>{{ locale.housekeeping_room_parent }}</th>
					<th>{{ locale.housekeeping_room_name }}</th>
					<th>{{ locale.housekeeping_room_access }}</th>
					<th>{{ locale.housekeeping_room_flags }}</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
			{% for category in categories %}
				<tr>
					<td>{{ category.id() }}</td>
					<td>{{ category.orderId() }}</td>
					<td>{{ category.parentId() }}</td>
					<td>{{ category.name() }}</td>
					<td>{{ category.minRoleAccess() }} / {{ category.minRoleSetFlatCat() }}</td>
					<td>{% if category.node() %}{{ locale.housekeeping_room_node }}{% endif %}{% if category.publicSpaces() %} {{ locale.housekeeping_room_public }}{% endif %}{% if category.allowTrading() %} {{ locale.housekeeping_room_trading }}{% endif %}{% if category.clubOnly() %} {{ locale.housekeeping_room_club }}{% endif %}{% if category.topPriority() %} {{ locale.housekeeping_room_priority }}{% endif %}</td>
					<td>
						<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/room_categories/edit?id={{ category.id() }}" class="btn btn-primary">{{ locale.housekeeping_room_edit }}</a>
						<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/room_categories/delete?id={{ category.id() }}" class="btn btn-danger">{{ locale.housekeeping_room_delete }}</a>
					</td>
				</tr>
			{% endfor %}
			</tbody>
		</table>
	</div>
{% include "housekeeping/base/footer.tpl" %}
