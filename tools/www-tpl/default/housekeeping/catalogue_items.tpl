{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set catalogueItemsActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
    <h1 class="mt-4">{{ locale.housekeeping_catalogue_catalogue_items }}</h1>
	{% include "housekeeping/base/alert.tpl" %}
	<p><a href="/{{ site.housekeepingPath }}/catalogue/items/edit" class="btn btn-danger">{{ locale.housekeeping_catalogue_new_item }}</a></p>
	<div class="table-responsive">
		<table class="table table-striped">
			<thead><tr><th>{{ locale.housekeeping_catalogue_id }}</th><th>{{ locale.housekeeping_catalogue_sale_code }}</th><th>{{ locale.housekeeping_catalogue_pages }}</th><th>{{ locale.housekeeping_catalogue_order }}</th><th>{{ locale.housekeeping_catalogue_price }}</th><th>{{ locale.housekeeping_catalogue_amount }}</th><th>{{ locale.housekeeping_catalogue_definition }}</th><th>{{ locale.housekeeping_catalogue_flags }}</th><th></th></tr></thead>
			<tbody>
				{% for item in items %}
				<tr>
					<td>{{ item.id() }}</td>
					<td>{{ item.saleCode() }}</td>
					<td>{{ item.pageId() }}</td>
					<td>{{ item.orderId() }}</td>
					<td>{{ item.priceCoins() }}c / {{ item.pricePixels() }}{{ locale.housekeeping_catalogue_px }}</td>
					<td>{{ item.amount() }}</td>
					<td>{{ item.definitionId() }}</td>
					<td>{% if item.hidden() %}{{ locale.housekeeping_catalogue_hidden }}{% endif %}{% if item.packageItem() %} {{ locale.housekeeping_catalogue_package }}{% endif %}</td>
					<td><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/items/edit?id={{ item.id() }}" class="btn btn-primary">{{ locale.housekeeping_catalogue_edit }}</a> <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/items/delete?id={{ item.id() }}" class="btn btn-danger">{{ locale.housekeeping_catalogue_delete }}</a></td>
				</tr>
				{% endfor %}
			</tbody>
		</table>
	</div>
{% include "housekeeping/base/footer.tpl" %}
