{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set roomModelsActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
    <h1 class="mt-4">{% if model != null %}{{ locale.housekeeping_room_edit }}{% else %}{{ locale.housekeeping_room_create }}{% endif %} {{ locale.housekeeping_room_room_model }}</h1>
	{% include "housekeeping/base/alert.tpl" %}
	<form class="table-responsive col-md-9" method="post">
		<div class="form-row">
			<div class="form-group col-md-4"><label>{{ locale.housekeeping_room_model_id }}</label><input type="text" class="form-control" name="model_id" value="{% if model != null %}{{ model.modelId() }}{% endif %}"></div>
			<div class="form-group col-md-4"><label>{{ locale.housekeeping_room_name }}</label><input type="text" class="form-control" name="model_name" value="{% if model != null %}{{ model.modelName() }}{% endif %}"></div>
			<div class="form-group col-md-4">
				<label>{{ locale.housekeeping_room_trigger_class }}</label>
				<select class="form-control" name="trigger_class">
					{% for trigger in triggers %}
					<option value="{{ trigger }}"{% if model != null and model.triggerClass() == trigger %} selected{% endif %}>{{ trigger }}</option>
					{% endfor %}
				</select>
			</div>
		</div>
		<div class="form-row">
			<div class="form-group col-md-3"><label>{{ locale.housekeeping_room_door_x }}</label><input type="number" class="form-control" name="door_x" value="{% if model != null %}{{ model.doorX() }}{% else %}0{% endif %}"></div>
			<div class="form-group col-md-3"><label>{{ locale.housekeeping_room_door_y }}</label><input type="number" class="form-control" name="door_y" value="{% if model != null %}{{ model.doorY() }}{% else %}0{% endif %}"></div>
			<div class="form-group col-md-3"><label>{{ locale.housekeeping_room_door_z }}</label><input type="text" class="form-control" name="door_z" value="{% if model != null %}{{ model.doorZ() }}{% else %}0.0{% endif %}"></div>
			<div class="form-group col-md-3"><label>{{ locale.housekeeping_room_door_direction }}</label><input type="number" class="form-control" name="door_dir" value="{% if model != null %}{{ model.doorDir() }}{% else %}0{% endif %}"></div>
		</div>
		<div class="form-group">
			<label>{{ locale.housekeeping_room_heightmap }}</label>
			<textarea class="form-control" name="heightmap" rows="10">{% if model != null %}{{ model.heightmap() }}{% endif %}</textarea>
		</div>
		<button type="submit" class="btn btn-info">{{ locale.housekeeping_room_save_model }}</button>
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/room_models" class="btn btn-secondary">{{ locale.housekeeping_room_back }}</a>
	</form>
{% include "housekeeping/base/footer.tpl" %}
