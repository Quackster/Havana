{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set vouchersActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
    <h1 class="mt-4">{% if voucher != null %}Edit{% else %}Create{% endif %} Voucher</h1>
	{% include "housekeeping/base/alert.tpl" %}
	<form class="table-responsive col-md-7" method="post">
		<div class="form-row">
			<div class="form-group col-md-6"><label>Voucher Code</label><input type="text" class="form-control" name="voucher_code" value="{% if voucher != null %}{{ voucher.voucherCode() }}{% endif %}"></div>
			<div class="form-group col-md-3"><label>Credits</label><input type="number" class="form-control" name="credits" value="{% if voucher != null %}{{ voucher.credits() }}{% else %}0{% endif %}"></div>
			<div class="form-group col-md-3"><label>Expiry Date</label><input type="text" class="form-control" name="expiry_date" value="{% if voucher != null and voucher.expiryDate() != null %}{{ voucher.expiryDate() }}{% endif %}"></div>
		</div>
		<div class="form-group form-check"><input type="checkbox" class="form-check-input" name="is_single_use" {% if voucher == null or voucher.singleUse() %}checked{% endif %}><label class="form-check-label">Single Use</label></div>
		<div class="form-group form-check"><input type="checkbox" class="form-check-input" name="allow_new_users" {% if voucher != null and voucher.allowNewUsers() %}checked{% endif %}><label class="form-check-label">Allow New Users</label></div>
		<div class="form-group">
			<label>Catalogue Sale Codes</label>
			<textarea class="form-control" name="catalogue_sale_codes" rows="6">{% for item in voucherItems %}{{ item.catalogueSaleCode() }}
{% endfor %}</textarea>
		</div>
		<button type="submit" class="btn btn-info">Save Voucher</button>
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/vouchers" class="btn btn-secondary">Back</a>
	</form>
	{% if voucher != null %}
	<h2 class="mt-4">Redemption History</h2>
	<div class="table-responsive">
		<table class="table table-striped">
			<thead><tr><th>User ID</th><th>Used At</th><th>Credits</th><th>Items</th></tr></thead>
			<tbody>{% for row in history %}<tr><td>{{ row.userId() }}</td><td>{{ row.usedAt() }}</td><td>{{ row.creditsRedeemed() }}</td><td>{{ row.itemsRedeemed() }}</td></tr>{% endfor %}</tbody>
		</table>
	</div>
	{% endif %}
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
