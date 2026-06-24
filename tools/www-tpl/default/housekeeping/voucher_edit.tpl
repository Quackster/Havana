{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set vouchersActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
    <h1 class="mt-4">{% if voucher != null %}{{ locale.housekeeping_voucher_edit }}{% else %}{{ locale.housekeeping_voucher_create }}{% endif %} {{ locale.housekeeping_voucher_voucher }}</h1>
	{% include "housekeeping/base/alert.tpl" %}
	<form class="table-responsive col-md-7" method="post">
		<div class="form-row">
			<div class="form-group col-md-6"><label>{{ locale.housekeeping_voucher_voucher_code }}</label><input type="text" class="form-control" name="voucher_code" value="{% if voucher != null %}{{ voucher.voucherCode() }}{% endif %}"></div>
			<div class="form-group col-md-3"><label>{{ locale.housekeeping_voucher_credits }}</label><input type="number" class="form-control" name="credits" value="{% if voucher != null %}{{ voucher.credits() }}{% else %}0{% endif %}"></div>
			<div class="form-group col-md-3"><label>{{ locale.housekeeping_voucher_expiry_date }}</label><input type="text" class="form-control" name="expiry_date" value="{% if voucher != null and voucher.expiryDate() != null %}{{ voucher.expiryDate() }}{% endif %}"></div>
		</div>
		<div class="form-group form-check"><input type="checkbox" class="form-check-input" name="is_single_use" {% if voucher == null or voucher.singleUse() %}checked{% endif %}><label class="form-check-label">{{ locale.housekeeping_voucher_single_use }}</label></div>
		<div class="form-group form-check"><input type="checkbox" class="form-check-input" name="allow_new_users" {% if voucher != null and voucher.allowNewUsers() %}checked{% endif %}><label class="form-check-label">{{ locale.housekeeping_voucher_allow_new_users }}</label></div>
		<div class="form-group">
			<label>{{ locale.housekeeping_voucher_catalogue_sale_codes }}</label>
			<textarea class="form-control" name="catalogue_sale_codes" rows="6">{% for item in voucherItems %}{{ item.catalogueSaleCode() }}
{% endfor %}</textarea>
		</div>
		<button type="submit" class="btn btn-info">{{ locale.housekeeping_voucher_save_voucher }}</button>
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/vouchers" class="btn btn-secondary">{{ locale.housekeeping_voucher_back }}</a>
	</form>
	{% if voucher != null %}
	<h2 class="mt-4">{{ locale.housekeeping_voucher_redemption_history }}</h2>
	<div class="table-responsive">
		<table class="table table-striped">
			<thead><tr><th>{{ locale.housekeeping_voucher_user_id }}</th><th>{{ locale.housekeeping_voucher_used_at }}</th><th>{{ locale.housekeeping_voucher_credits }}</th><th>{{ locale.housekeeping_voucher_items }}</th></tr></thead>
			<tbody>{% for row in history %}<tr><td>{{ row.userId() }}</td><td>{{ row.usedAt() }}</td><td>{{ row.creditsRedeemed() }}</td><td>{{ row.itemsRedeemed() }}</td></tr>{% endfor %}</tbody>
		</table>
	</div>
	{% endif %}
{% include "housekeeping/base/footer.tpl" %}
