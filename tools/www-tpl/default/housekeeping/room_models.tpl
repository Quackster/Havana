{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set roomModelsActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
    <h1 class="mt-4">{{ locale.housekeeping_room_room_models }}</h1>
	{% include "housekeeping/base/alert.tpl" %}
	<p><a href="/{{ site.housekeepingPath }}/room_models/edit" class="btn btn-danger">{{ locale.housekeeping_room_new_model }}</a></p>
	<div class="table-responsive">
		<table class="table table-striped">
			<thead>
				<tr>
					<th>{{ locale.housekeeping_room_id }}</th>
					<th>{{ locale.housekeeping_room_model_id }}</th>
					<th>{{ locale.housekeeping_room_name }}</th>
					<th>{{ locale.housekeeping_room_door }}</th>
					<th>{{ locale.housekeeping_room_trigger }}</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
			{% for model in models %}
				<tr>
					<td>{{ model.id() }}</td>
					<td>{{ model.modelId() }}</td>
					<td>{{ model.modelName() }}</td>
					<td>{{ model.doorX() }}, {{ model.doorY() }}, {{ model.doorZ() }} {{ locale.housekeeping_room_dir }} {{ model.doorDir() }}</td>
					<td>{{ model.triggerClass() }}</td>
					<td>
						<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/room_models/edit?id={{ model.id() }}" class="btn btn-primary">{{ locale.housekeeping_room_edit }}</a>
						<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/room_models/delete?id={{ model.id() }}" class="btn btn-danger">{{ locale.housekeeping_room_delete }}</a>
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
