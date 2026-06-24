{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set wordfilterActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
    <h1 class="mt-4">Wordfilter</h1>
	{% include "housekeeping/base/alert.tpl" %}
	<p><a href="/{{ site.housekeepingPath }}/wordfilter/edit" class="btn btn-danger">New Word</a></p>
	<div class="table-responsive">
		<table class="table table-striped">
			<thead><tr><th>ID</th><th>Word</th><th>Bannable</th><th>Filterable</th><th></th></tr></thead>
			<tbody>
			{% for word in words %}
				<tr><td>{{ word.id() }}</td><td>{{ word.word() }}</td><td>{% if word.bannable() %}Yes{% else %}No{% endif %}</td><td>{% if word.filterable() %}Yes{% else %}No{% endif %}</td><td><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/wordfilter/edit?id={{ word.id() }}" class="btn btn-primary">Edit</a> <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/wordfilter/delete?id={{ word.id() }}" class="btn btn-danger">Delete</a></td></tr>
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
