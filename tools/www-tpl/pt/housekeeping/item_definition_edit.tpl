{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set itemDefinitionsActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
    <h1 class="mt-4">{% if definition != null %}Edit{% else %}Create{% endif %} Item Definition</h1>
	{% include "housekeeping/base/alert.tpl" %}
	<form class="table-responsive col-md-9" method="post">
		<div class="form-row">
			<div class="form-group col-md-4"><label>Sprite</label><input type="text" class="form-control" name="sprite" value="{% if definition != null %}{{ definition.sprite() }}{% endif %}"></div>
			<div class="form-group col-md-4"><label>Name</label><input type="text" class="form-control" name="name" value="{% if definition != null %}{{ definition.name() }}{% endif %}"></div>
			<div class="form-group col-md-4"><label>Description</label><input type="text" class="form-control" name="description" value="{% if definition != null %}{{ definition.description() }}{% endif %}"></div>
		</div>
		<div class="form-row">
			<div class="form-group col-md-2"><label>Sprite ID</label><input type="number" class="form-control" name="sprite_id" value="{% if definition != null %}{{ definition.spriteId() }}{% else %}0{% endif %}"></div>
			<div class="form-group col-md-2"><label>Length</label><input type="number" class="form-control" name="length" value="{% if definition != null %}{{ definition.length() }}{% else %}1{% endif %}"></div>
			<div class="form-group col-md-2"><label>Width</label><input type="number" class="form-control" name="width" value="{% if definition != null %}{{ definition.width() }}{% else %}1{% endif %}"></div>
			<div class="form-group col-md-2"><label>Top Height</label><input type="text" class="form-control" name="top_height" value="{% if definition != null %}{{ definition.topHeight() }}{% else %}0.001{% endif %}"></div>
			<div class="form-group col-md-2"><label>Max Status</label><input type="text" class="form-control" name="max_status" value="{% if definition != null %}{{ definition.maxStatus() }}{% else %}0{% endif %}"></div>
			<div class="form-group col-md-2"><label>Rental Time</label><input type="number" class="form-control" name="rental_time" value="{% if definition != null %}{{ definition.rentalTime() }}{% else %}-1{% endif %}"></div>
		</div>
		<div class="form-row">
			<div class="form-group col-md-6">
				<label>Interactor</label>
				<select class="form-control" name="interactor">
					{% for interactor in interactors %}
					<option value="{{ interactor }}"{% if definition != null and definition.interactor() == interactor %} selected{% endif %}>{{ interactor }}</option>
					{% endfor %}
				</select>
			</div>
			<div class="form-group col-md-3 form-check mt-4"><input type="checkbox" class="form-check-input" name="is_tradable" {% if definition == null or definition.tradable() %}checked{% endif %}><label class="form-check-label">Tradable</label></div>
			<div class="form-group col-md-3 form-check mt-4"><input type="checkbox" class="form-check-input" name="is_recyclable" {% if definition == null or definition.recyclable() %}checked{% endif %}><label class="form-check-label">Recyclable</label></div>
		</div>
		<div class="form-group">
			<label>Behaviour CSV</label>
			<input type="text" class="form-control" name="behaviour" value="{% if definition != null %}{{ definition.behaviour() }}{% endif %}">
			<small class="form-text text-muted">Known behaviours: {% for behaviour in behaviours %}{{ behaviour }}, {% endfor %}</small>
		</div>
		<div class="form-row">
			<div class="form-group col-md-4"><label>Drink IDs CSV</label><input type="text" class="form-control" name="drink_ids" value="{% if definition != null and definition.drinkIds() != null %}{{ definition.drinkIds() }}{% endif %}"></div>
			<div class="form-group col-md-4"><label>Allowed Rotations CSV</label><input type="text" class="form-control" name="allowed_rotations" value="{% if definition != null %}{{ definition.allowedRotations() }}{% endif %}"></div>
			<div class="form-group col-md-4"><label>Heights CSV</label><input type="text" class="form-control" name="heights" value="{% if definition != null and definition.heights() != null %}{{ definition.heights() }}{% endif %}"></div>
		</div>
		<button type="submit" class="btn btn-info">Save Definition</button>
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/item_definitions" class="btn btn-secondary">Back</a>
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
