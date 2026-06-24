{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set wordfilterActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
    <h1 class="mt-4">{{ locale.housekeeping_wordfilter_wordfilter }}</h1>
	{% include "housekeeping/base/alert.tpl" %}
	<p><a href="/{{ site.housekeepingPath }}/wordfilter/edit" class="btn btn-danger">{{ locale.housekeeping_wordfilter_new_word }}</a></p>
	<div class="table-responsive">
		<table class="table table-striped">
			<thead><tr><th>{{ locale.housekeeping_wordfilter_id }}</th><th>{{ locale.housekeeping_wordfilter_word }}</th><th>{{ locale.housekeeping_wordfilter_bannable }}</th><th>{{ locale.housekeeping_wordfilter_filterable }}</th><th></th></tr></thead>
			<tbody>
			{% for word in words %}
				<tr><td>{{ word.id() }}</td><td>{{ word.word() }}</td><td>{% if word.bannable() %}{{ locale.housekeeping_wordfilter_yes }}{% else %}{{ locale.housekeeping_wordfilter_no }}{% endif %}</td><td>{% if word.filterable() %}{{ locale.housekeeping_wordfilter_yes }}{% else %}{{ locale.housekeeping_wordfilter_no }}{% endif %}</td><td><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/wordfilter/edit?id={{ word.id() }}" class="btn btn-primary">{{ locale.housekeeping_wordfilter_edit }}</a> <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/wordfilter/delete?id={{ word.id() }}" class="btn btn-danger">{{ locale.housekeeping_wordfilter_delete }}</a></td></tr>
			{% endfor %}
			</tbody>
		</table>
	</div>
{% include "housekeeping/base/footer.tpl" %}
