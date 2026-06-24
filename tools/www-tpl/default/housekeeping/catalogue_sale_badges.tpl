{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set catalogueSaleBadgesActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
    <h1 class="mt-4">{{ locale.housekeeping_catalogue_catalogue_sale_badges }}</h1>
	{% include "housekeeping/base/alert.tpl" %}
	<form class="form-inline mb-3" method="post">
		<input type="text" class="form-control mr-2" name="sale_code" placeholder="{{ locale.housekeeping_catalogue_sale_code_text }}">
		<input type="text" class="form-control mr-2" name="badge_code" placeholder="{{ locale.housekeeping_catalogue_badge_code }}">
		<button type="submit" class="btn btn-info">{{ locale.housekeeping_catalogue_add_reward }}</button>
	</form>
	<div class="table-responsive"><table class="table table-striped"><thead><tr><th>{{ locale.housekeeping_catalogue_sale_code }}</th><th>{{ locale.housekeeping_catalogue_badge_code_text }}</th><th></th></tr></thead><tbody>
	{% for reward in saleBadges %}
	<tr><td>{{ reward.saleCode() }}</td><td>{{ reward.badgeCode() }}</td><td><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/sale_badges/delete?sale_code={{ reward.saleCode() }}&badge_code={{ reward.badgeCode() }}" class="btn btn-danger">{{ locale.housekeeping_catalogue_delete }}</a></td></tr>
	{% endfor %}
	</tbody></table></div>
{% include "housekeeping/base/footer.tpl" %}
