{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set wordfilterActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
    <h1 class="mt-4">{% if word != null %}Edit{% else %}Create{% endif %} Wordfilter Entry</h1>
	{% include "housekeeping/base/alert.tpl" %}
	<form class="table-responsive col-md-5" method="post">
		<div class="form-group"><label>Word</label><input type="text" class="form-control" name="word" value="{% if word != null %}{{ word.word() }}{% endif %}"></div>
		<div class="form-group form-check"><input type="checkbox" class="form-check-input" name="is_bannable" {% if word != null and word.bannable() %}checked{% endif %}><label class="form-check-label">Bannable</label></div>
		<div class="form-group form-check"><input type="checkbox" class="form-check-input" name="is_filterable" {% if word == null or word.filterable() %}checked{% endif %}><label class="form-check-label">Filterable</label></div>
		<button type="submit" class="btn btn-info">Save Word</button>
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/wordfilter" class="btn btn-secondary">Back</a>
	</form>
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
