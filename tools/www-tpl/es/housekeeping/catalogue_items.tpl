{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set catalogueItemsActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
    <h1 class="mt-4">Catalogue Items</h1>
	{% include "housekeeping/base/alert.tpl" %}
	<p><a href="/{{ site.housekeepingPath }}/catalogue/items/edit" class="btn btn-danger">New Item</a></p>
	<div class="table-responsive">
		<table class="table table-striped">
			<thead><tr><th>ID</th><th>Sale Code</th><th>Pages</th><th>Order</th><th>Price</th><th>Amount</th><th>Definition</th><th>Flags</th><th></th></tr></thead>
			<tbody>
				{% for item in items %}
				<tr>
					<td>{{ item.id() }}</td>
					<td>{{ item.saleCode() }}</td>
					<td>{{ item.pageId() }}</td>
					<td>{{ item.orderId() }}</td>
					<td>{{ item.priceCoins() }}c / {{ item.pricePixels() }}px</td>
					<td>{{ item.amount() }}</td>
					<td>{{ item.definitionId() }}</td>
					<td>{% if item.hidden() %}Hidden{% endif %}{% if item.packageItem() %} Package{% endif %}</td>
					<td><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/items/edit?id={{ item.id() }}" class="btn btn-primary">Edit</a> <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/items/delete?id={{ item.id() }}" class="btn btn-danger">Delete</a></td>
				</tr>
				{% endfor %}
			</tbody>
		</table>
	</div>
{% include "housekeeping/base/footer.tpl" %}
