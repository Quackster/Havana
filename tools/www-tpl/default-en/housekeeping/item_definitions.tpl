{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set itemDefinitionsActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
    <h1 class="mt-4">Item Definitions</h1>
	{% include "housekeeping/base/alert.tpl" %}
	<p><a href="/{{ site.housekeepingPath }}/item_definitions/edit" class="btn btn-danger">New Definition</a></p>
	<div class="table-responsive">
		<table class="table table-striped">
			<thead>
				<tr>
					<th>ID</th>
					<th>Sprite</th>
					<th>Name</th>
					<th>Sprite ID</th>
					<th>Size</th>
					<th>Interactor</th>
					<th>Flags</th>
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
					<td>{% if definition.tradable() %}Tradable{% endif %}{% if definition.recyclable() %} Recyclable{% endif %}</td>
					<td>
						<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/item_definitions/edit?id={{ definition.id() }}" class="btn btn-primary">Edit</a>
						<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/item_definitions/delete?id={{ definition.id() }}" class="btn btn-danger">Delete</a>
					</td>
				</tr>
			{% endfor %}
			</tbody>
		</table>
	</div>
    </div>
  </div>
  <script src="{{ site.staticContentPath }}/public/hk/js/jquery-3.1.1.slim.min.js"></script>
  <script src="{{ site.staticContentPath }}/public/hk/js/bootstrap.bundle.min.js"></script>
  <script>
    $("#menu-toggle").click(function(e) {
      e.preventDefault();
      $("#wrapper").toggleClass("toggled");
    });
  </script>
</body>
</html>
{% include "housekeeping/base/footer.tpl" %}
