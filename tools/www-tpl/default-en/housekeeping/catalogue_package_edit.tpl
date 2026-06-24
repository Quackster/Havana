{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set cataloguePackagesActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
    <h1 class="mt-4">{% if cataloguePackage != null %}Edit{% else %}Create{% endif %} Catalogue Package Row</h1>
	{% include "housekeeping/base/alert.tpl" %}
	<form class="table-responsive col-md-6" method="post">
		<div class="form-group"><label>Sale Code</label><input type="text" class="form-control" name="salecode" value="{% if cataloguePackage != null %}{{ cataloguePackage.saleCode() }}{% endif %}"></div>
		<div class="form-group"><label>Definition ID</label><input type="number" class="form-control" name="definition_id" value="{% if cataloguePackage != null %}{{ cataloguePackage.definitionId() }}{% else %}0{% endif %}"></div>
		<div class="form-group"><label>Special Sprite ID</label><input type="text" class="form-control" name="special_sprite_id" value="{% if cataloguePackage != null %}{{ cataloguePackage.specialSpriteId() }}{% endif %}"></div>
		<div class="form-group"><label>Amount</label><input type="number" class="form-control" name="amount" value="{% if cataloguePackage != null %}{{ cataloguePackage.amount() }}{% else %}1{% endif %}"></div>
		<button type="submit" class="btn btn-info">Save Package Row</button>
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/packages" class="btn btn-secondary">Back</a>
	</form>
{% include "housekeeping/base/footer.tpl" %}
