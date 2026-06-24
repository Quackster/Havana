{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set wordfilterActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
    <h1 class="mt-4">{% if word != null %}{{ locale.housekeeping_wordfilter_edit }}{% else %}{{ locale.housekeeping_wordfilter_create }}{% endif %} {{ locale.housekeeping_wordfilter_wordfilter_entry }}</h1>
	{% include "housekeeping/base/alert.tpl" %}
	<form class="table-responsive col-md-5" method="post">
		<div class="form-group"><label>{{ locale.housekeeping_wordfilter_word }}</label><input type="text" class="form-control" name="word" value="{% if word != null %}{{ word.word() }}{% endif %}"></div>
		<div class="form-group form-check"><input type="checkbox" class="form-check-input" name="is_bannable" {% if word != null and word.bannable() %}checked{% endif %}><label class="form-check-label">{{ locale.housekeeping_wordfilter_bannable }}</label></div>
		<div class="form-group form-check"><input type="checkbox" class="form-check-input" name="is_filterable" {% if word == null or word.filterable() %}checked{% endif %}><label class="form-check-label">{{ locale.housekeeping_wordfilter_filterable }}</label></div>
		<button type="submit" class="btn btn-info">{{ locale.housekeeping_wordfilter_save_word }}</button>
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/wordfilter" class="btn btn-secondary">{{ locale.housekeeping_wordfilter_back }}</a>
	</form>
{% include "housekeeping/base/footer.tpl" %}
