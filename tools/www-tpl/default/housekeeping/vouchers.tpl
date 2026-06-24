{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set vouchersActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
    <h1 class="mt-4">{{ locale.housekeeping_vouchers_vouchers }}</h1>
	{% include "housekeeping/base/alert.tpl" %}
	<p><a href="/{{ site.housekeepingPath }}/vouchers/edit" class="btn btn-danger">{{ locale.housekeeping_vouchers_new_voucher }}</a></p>
	<div class="table-responsive">
		<table class="table table-striped">
			<thead><tr><th>{{ locale.housekeeping_vouchers_code }}</th><th>{{ locale.housekeeping_vouchers_credits }}</th><th>{{ locale.housekeeping_vouchers_expiry }}</th><th>{{ locale.housekeeping_vouchers_single_use }}</th><th>{{ locale.housekeeping_vouchers_new_users }}</th><th></th></tr></thead>
			<tbody>
			{% for voucher in vouchers %}
				<tr>
					<td>{{ voucher.voucherCode() }}</td>
					<td>{{ voucher.credits() }}</td>
					<td>{{ voucher.expiryDate() }}</td>
					<td>{% if voucher.singleUse() %}{{ locale.housekeeping_vouchers_yes }}{% else %}{{ locale.housekeeping_vouchers_no }}{% endif %}</td>
					<td>{% if voucher.allowNewUsers() %}{{ locale.housekeeping_vouchers_allowed }}{% else %}{{ locale.housekeeping_vouchers_blocked }}{% endif %}</td>
					<td><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/vouchers/edit?code={{ voucher.voucherCode() }}" class="btn btn-primary">{{ locale.housekeeping_vouchers_edit }}</a> <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/vouchers/delete?code={{ voucher.voucherCode() }}" class="btn btn-danger">{{ locale.housekeeping_vouchers_delete }}</a></td>
				</tr>
			{% endfor %}
			</tbody>
		</table>
	</div>
	<h2 class="mt-4">{{ locale.housekeeping_vouchers_recent_redemptions }}</h2>
	<div class="table-responsive">
		<table class="table table-striped">
			<thead><tr><th>{{ locale.housekeeping_vouchers_code }}</th><th>{{ locale.housekeeping_vouchers_user_id }}</th><th>{{ locale.housekeeping_vouchers_used_at }}</th><th>{{ locale.housekeeping_vouchers_credits }}</th><th>{{ locale.housekeeping_vouchers_items }}</th></tr></thead>
			<tbody>
			{% for row in history %}
				<tr><td>{{ row.voucherCode() }}</td><td>{{ row.userId() }}</td><td>{{ row.usedAt() }}</td><td>{{ row.creditsRedeemed() }}</td><td>{{ row.itemsRedeemed() }}</td></tr>
			{% endfor %}
			</tbody>
		</table>
	</div>
{% include "housekeeping/base/footer.tpl" %}
