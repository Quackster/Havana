{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set cataloguePagesActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
    <h1 class="mt-4">{% if page != null %}Edit{% else %}Create{% endif %} Catalogue Page</h1>
	{% include "housekeeping/base/alert.tpl" %}
	<form class="table-responsive col-md-8" method="post">
		<div class="form-row">
			<div class="form-group col-md-3"><label>Old ID</label><input type="number" class="form-control" name="old_id" value="{% if page != null %}{{ page.oldId() }}{% else %}0{% endif %}"></div>
			<div class="form-group col-md-3"><label>Parent ID</label><input type="number" class="form-control" name="parent_id" value="{% if page != null %}{{ page.parentId() }}{% else %}-1{% endif %}"></div>
			<div class="form-group col-md-3"><label>Order</label><input type="number" class="form-control" name="order_id" value="{% if page != null %}{{ page.orderId() }}{% else %}1{% endif %}"></div>
			<div class="form-group col-md-3"><label>Minimum Rank</label><select class="form-control" name="min_role">{% for rank in ranks %}<option value="{{ rank.getRankId() }}"{% if page != null and page.minRole() == rank.getRankId() %} selected{% endif %}>{{ rank.getName() }}</option>{% endfor %}</select></div>
		</div>
		<div class="form-group"><label>Name</label><input type="text" class="form-control" name="name" value="{% if page != null %}{{ page.name() }}{% endif %}"></div>
		<div class="form-row">
			<div class="form-group col-md-4"><label>Icon</label><input type="number" class="form-control" name="icon" value="{% if page != null %}{{ page.icon() }}{% else %}0{% endif %}"></div>
			<div class="form-group col-md-4"><label>Colour</label><input type="number" class="form-control" name="colour" value="{% if page != null %}{{ page.colour() }}{% else %}0{% endif %}"></div>
			<div class="form-group col-md-4"><label>Layout</label><input type="text" class="form-control" name="layout" value="{% if page != null %}{{ page.layout() }}{% endif %}"></div>
		</div>
		<div class="form-group form-check"><input type="checkbox" class="form-check-input" name="is_navigatable" {% if page != null and page.navigatable() %}checked{% endif %}><label class="form-check-label">Navigatable</label></div>
		<div class="form-group form-check"><input type="checkbox" class="form-check-input" name="is_club_only" {% if page != null and page.clubOnly() %}checked{% endif %}><label class="form-check-label">Club Only</label></div>
		<div class="form-group"><label>Images JSON Array</label><textarea class="form-control" name="images" rows="4">{% if page != null %}{{ page.images() }}{% else %}[]{% endif %}</textarea></div>
		<div class="form-group"><label>Texts JSON Array</label><textarea class="form-control" name="texts" rows="6">{% if page != null %}{{ page.texts() }}{% else %}[]{% endif %}</textarea></div>
		<div class="form-row">
			<div class="form-group col-md-6"><label>Seasonal Start</label><input type="text" class="form-control" name="seasonal_start" value="{% if page != null and page.seasonalStart() != null %}{{ page.seasonalStart() }}{% endif %}"></div>
			<div class="form-group col-md-6"><label>Seasonal Length Seconds</label><input type="number" class="form-control" name="seasonal_length" value="{% if page != null %}{{ page.seasonalLength() }}{% else %}0{% endif %}"></div>
		</div>
		<button type="submit" class="btn btn-info">Save Page</button>
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/pages" class="btn btn-secondary">Back</a>
	</form>
{% include "housekeeping/base/footer.tpl" %}
