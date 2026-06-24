{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set roomModelsActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
    <h1 class="mt-4">{% if model != null %}Edit{% else %}Create{% endif %} Room Model</h1>
	{% include "housekeeping/base/alert.tpl" %}
	<form class="table-responsive col-md-9" method="post">
		<div class="form-row">
			<div class="form-group col-md-4"><label>Model ID</label><input type="text" class="form-control" name="model_id" value="{% if model != null %}{{ model.modelId() }}{% endif %}"></div>
			<div class="form-group col-md-4"><label>Name</label><input type="text" class="form-control" name="model_name" value="{% if model != null %}{{ model.modelName() }}{% endif %}"></div>
			<div class="form-group col-md-4">
				<label>Trigger Class</label>
				<select class="form-control" name="trigger_class">
					{% for trigger in triggers %}
					<option value="{{ trigger }}"{% if model != null and model.triggerClass() == trigger %} selected{% endif %}>{{ trigger }}</option>
					{% endfor %}
				</select>
			</div>
		</div>
		<div class="form-row">
			<div class="form-group col-md-3"><label>Door X</label><input type="number" class="form-control" name="door_x" value="{% if model != null %}{{ model.doorX() }}{% else %}0{% endif %}"></div>
			<div class="form-group col-md-3"><label>Door Y</label><input type="number" class="form-control" name="door_y" value="{% if model != null %}{{ model.doorY() }}{% else %}0{% endif %}"></div>
			<div class="form-group col-md-3"><label>Door Z</label><input type="text" class="form-control" name="door_z" value="{% if model != null %}{{ model.doorZ() }}{% else %}0.0{% endif %}"></div>
			<div class="form-group col-md-3"><label>Door Direction</label><input type="number" class="form-control" name="door_dir" value="{% if model != null %}{{ model.doorDir() }}{% else %}0{% endif %}"></div>
		</div>
		<div class="form-group">
			<label>Heightmap</label>
			<textarea class="form-control" name="heightmap" rows="10">{% if model != null %}{{ model.heightmap() }}{% endif %}</textarea>
		</div>
		<button type="submit" class="btn btn-info">Save Model</button>
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/room_models" class="btn btn-secondary">Back</a>
	</form>
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
