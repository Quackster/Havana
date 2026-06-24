{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set vouchersActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
    <h1 class="mt-4">Vouchers</h1>
	{% include "housekeeping/base/alert.tpl" %}
	<p><a href="/{{ site.housekeepingPath }}/vouchers/edit" class="btn btn-danger">New Voucher</a></p>
	<div class="table-responsive">
		<table class="table table-striped">
			<thead><tr><th>Code</th><th>Credits</th><th>Expiry</th><th>Single Use</th><th>New Users</th><th></th></tr></thead>
			<tbody>
			{% for voucher in vouchers %}
				<tr>
					<td>{{ voucher.voucherCode() }}</td>
					<td>{{ voucher.credits() }}</td>
					<td>{{ voucher.expiryDate() }}</td>
					<td>{% if voucher.singleUse() %}Yes{% else %}No{% endif %}</td>
					<td>{% if voucher.allowNewUsers() %}Allowed{% else %}Blocked{% endif %}</td>
					<td><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/vouchers/edit?code={{ voucher.voucherCode() }}" class="btn btn-primary">Edit</a> <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/vouchers/delete?code={{ voucher.voucherCode() }}" class="btn btn-danger">Delete</a></td>
				</tr>
			{% endfor %}
			</tbody>
		</table>
	</div>
	<h2 class="mt-4">Recent Redemptions</h2>
	<div class="table-responsive">
		<table class="table table-striped">
			<thead><tr><th>Code</th><th>User ID</th><th>Used At</th><th>Credits</th><th>Items</th></tr></thead>
			<tbody>
			{% for row in history %}
				<tr><td>{{ row.voucherCode() }}</td><td>{{ row.userId() }}</td><td>{{ row.usedAt() }}</td><td>{{ row.creditsRedeemed() }}</td><td>{{ row.itemsRedeemed() }}</td></tr>
			{% endfor %}
			</tbody>
		</table>
	</div>
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
