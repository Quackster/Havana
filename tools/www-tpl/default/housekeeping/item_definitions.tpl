{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set itemDefinitionsActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
    <h1 class="mt-4">{{ locale.housekeeping_item_item_definitions }}</h1>
	{% include "housekeeping/base/alert.tpl" %}
	<p><a href="/{{ site.housekeepingPath }}/item_definitions/edit" class="btn btn-danger">{{ locale.housekeeping_item_new_definition }}</a></p>
	<div class="table-responsive">
		<table class="table table-striped">
			<thead>
				<tr>
					<th>{{ locale.housekeeping_item_id }}</th>
					<th>{{ locale.housekeeping_item_sprite }}</th>
					<th>{{ locale.housekeeping_item_name }}</th>
					<th>{{ locale.housekeeping_item_sprite_id }}</th>
					<th>{{ locale.housekeeping_item_size }}</th>
					<th>{{ locale.housekeeping_item_interactor }}</th>
					<th>{{ locale.housekeeping_item_flags }}</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
			{% for definition in definitions %}
				<tr>
					<td>{{ definition.id() }}</td>
					<td>{{ definition.sprite() }}</td>
					<td>{{ definition.name() }}</td>
					<td>{{ definition.spriteId() }}</td>
					<td>{{ definition.length() }}x{{ definition.width() }}</td>
					<td>{{ definition.interactor() }}</td>
					<td>{% if definition.tradable() %}{{ locale.housekeeping_item_tradable }}{% endif %}{% if definition.recyclable() %} {{ locale.housekeeping_item_recyclable }}{% endif %}</td>
					<td>
						<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/item_definitions/edit?id={{ definition.id() }}" class="btn btn-primary">{{ locale.housekeeping_item_edit }}</a>
						<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/item_definitions/delete?id={{ definition.id() }}" class="btn btn-danger">{{ locale.housekeeping_item_delete }}</a>
					</td>
				</tr>
			{% endfor %}
			</tbody>
		</table>
	</div>
{% include "housekeeping/base/footer.tpl" %}
