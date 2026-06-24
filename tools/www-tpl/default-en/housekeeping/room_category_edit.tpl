{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set roomCategoriesActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
    <h1 class="mt-4">{% if category != null %}Edit{% else %}Create{% endif %} Room Category</h1>
	{% include "housekeeping/base/alert.tpl" %}
	<form class="table-responsive col-md-8" method="post">
		<div class="form-row">
			<div class="form-group col-md-2"><label>Order</label><input type="number" class="form-control" name="order_id" value="{% if category != null %}{{ category.orderId() }}{% else %}0{% endif %}"></div>
			<div class="form-group col-md-4">
				<label>Parent</label>
				<select class="form-control" name="parent_id">
					<option value="0">No parent</option>
					{% for parent in categories %}
					<option value="{{ parent.id() }}"{% if category != null and category.parentId() == parent.id() %} selected{% endif %}>{{ parent.id() }} - {{ parent.name() }}</option>
					{% endfor %}
				</select>
			</div>
			<div class="form-group col-md-6"><label>Name</label><input type="text" class="form-control" name="name" value="{% if category != null %}{{ category.name() }}{% endif %}"></div>
		</div>
		<div class="form-row">
			<div class="form-group col-md-6">
				<label>Minimum Access Rank</label>
				<select class="form-control" name="minrole_access">
					{% for rank in ranks %}
					<option value="{{ rank.getRankId() }}"{% if category != null and category.minRoleAccess() == rank.getRankId() %} selected{% endif %}>{{ rank.getRankId() }} - {{ rank.getName() }}</option>
					{% endfor %}
				</select>
			</div>
			<div class="form-group col-md-6">
				<label>Minimum Set Category Rank</label>
				<select class="form-control" name="minrole_setflatcat">
					{% for rank in ranks %}
					<option value="{{ rank.getRankId() }}"{% if category != null and category.minRoleSetFlatCat() == rank.getRankId() %} selected{% endif %}>{{ rank.getRankId() }} - {{ rank.getName() }}</option>
					{% endfor %}
				</select>
			</div>
		</div>
		<div class="form-row">
			<div class="form-group col-md-2 form-check mt-4"><input type="checkbox" class="form-check-input" name="isnode" {% if category != null and category.node() %}checked{% endif %}><label class="form-check-label">Node</label></div>
			<div class="form-group col-md-2 form-check mt-4"><input type="checkbox" class="form-check-input" name="public_spaces" {% if category != null and category.publicSpaces() %}checked{% endif %}><label class="form-check-label">Public Spaces</label></div>
			<div class="form-group col-md-2 form-check mt-4"><input type="checkbox" class="form-check-input" name="allow_trading" {% if category == null or category.allowTrading() %}checked{% endif %}><label class="form-check-label">Trading</label></div>
			<div class="form-group col-md-2 form-check mt-4"><input type="checkbox" class="form-check-input" name="club_only" {% if category != null and category.clubOnly() %}checked{% endif %}><label class="form-check-label">Club Only</label></div>
			<div class="form-group col-md-2 form-check mt-4"><input type="checkbox" class="form-check-input" name="is_top_priority" {% if category != null and category.topPriority() %}checked{% endif %}><label class="form-check-label">Top Priority</label></div>
		</div>
		<button type="submit" class="btn btn-info">Save Category</button>
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/room_categories" class="btn btn-secondary">Back</a>
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
