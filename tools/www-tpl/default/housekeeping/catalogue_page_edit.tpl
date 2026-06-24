{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set cataloguePagesActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
    <h1 class="mt-4">{% if page != null %}{{ locale.housekeeping_catalogue_edit }}{% else %}{{ locale.housekeeping_catalogue_create }}{% endif %} {{ locale.housekeeping_catalogue_catalogue_page }}</h1>
	{% include "housekeeping/base/alert.tpl" %}
	<form class="table-responsive col-md-8" method="post">
		<div class="form-row">
			<div class="form-group col-md-3"><label>{{ locale.housekeeping_catalogue_old_id }}</label><input type="number" class="form-control" name="old_id" value="{% if page != null %}{{ page.oldId() }}{% else %}0{% endif %}"></div>
			<div class="form-group col-md-3"><label>{{ locale.housekeeping_catalogue_parent_id }}</label><input type="number" class="form-control" name="parent_id" value="{% if page != null %}{{ page.parentId() }}{% else %}-1{% endif %}"></div>
			<div class="form-group col-md-3"><label>{{ locale.housekeeping_catalogue_order }}</label><input type="number" class="form-control" name="order_id" value="{% if page != null %}{{ page.orderId() }}{% else %}1{% endif %}"></div>
			<div class="form-group col-md-3"><label>{{ locale.housekeeping_catalogue_minimum_rank }}</label><select class="form-control" name="min_role">{% for rank in ranks %}<option value="{{ rank.getRankId() }}"{% if page != null and page.minRole() == rank.getRankId() %} selected{% endif %}>{{ rank.getName() }}</option>{% endfor %}</select></div>
		</div>
		<div class="form-group"><label>{{ locale.housekeeping_catalogue_name }}</label><input type="text" class="form-control" name="name" value="{% if page != null %}{{ page.name() }}{% endif %}"></div>
		<div class="form-row">
			<div class="form-group col-md-4"><label>{{ locale.housekeeping_catalogue_icon }}</label><input type="number" class="form-control" name="icon" value="{% if page != null %}{{ page.icon() }}{% else %}0{% endif %}"></div>
			<div class="form-group col-md-4"><label>{{ locale.housekeeping_catalogue_colour }}</label><input type="number" class="form-control" name="colour" value="{% if page != null %}{{ page.colour() }}{% else %}0{% endif %}"></div>
			<div class="form-group col-md-4"><label>{{ locale.housekeeping_catalogue_layout }}</label><input type="text" class="form-control" name="layout" value="{% if page != null %}{{ page.layout() }}{% endif %}"></div>
		</div>
		<div class="form-group form-check"><input type="checkbox" class="form-check-input" name="is_navigatable" {% if page != null and page.navigatable() %}checked{% endif %}><label class="form-check-label">{{ locale.housekeeping_catalogue_navigatable }}</label></div>
		<div class="form-group form-check"><input type="checkbox" class="form-check-input" name="is_club_only" {% if page != null and page.clubOnly() %}checked{% endif %}><label class="form-check-label">{{ locale.housekeeping_catalogue_club_only }}</label></div>
		<div class="form-group"><label>{{ locale.housekeeping_catalogue_images_json_array }}</label><textarea class="form-control" name="images" rows="4">{% if page != null %}{{ page.images() }}{% else %}[]{% endif %}</textarea></div>
		<div class="form-group"><label>{{ locale.housekeeping_catalogue_texts_json_array }}</label><textarea class="form-control" name="texts" rows="6">{% if page != null %}{{ page.texts() }}{% else %}[]{% endif %}</textarea></div>
		<div class="form-row">
			<div class="form-group col-md-6"><label>{{ locale.housekeeping_catalogue_seasonal_start }}</label><input type="text" class="form-control" name="seasonal_start" value="{% if page != null and page.seasonalStart() != null %}{{ page.seasonalStart() }}{% endif %}"></div>
			<div class="form-group col-md-6"><label>{{ locale.housekeeping_catalogue_seasonal_length_seconds }}</label><input type="number" class="form-control" name="seasonal_length" value="{% if page != null %}{{ page.seasonalLength() }}{% else %}0{% endif %}"></div>
		</div>
		<button type="submit" class="btn btn-info">{{ locale.housekeeping_catalogue_save_page }}</button>
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/pages" class="btn btn-secondary">{{ locale.housekeeping_catalogue_back }}</a>
	</form>
{% include "housekeeping/base/footer.tpl" %}
