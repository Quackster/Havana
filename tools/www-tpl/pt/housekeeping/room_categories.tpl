{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set roomCategoriesActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
    <h1 class="mt-4">Room Categories</h1>
	{% include "housekeeping/base/alert.tpl" %}
	<p><a href="/{{ site.housekeepingPath }}/room_categories/edit" class="btn btn-danger">New Category</a></p>
	<div class="table-responsive">
		<table class="table table-striped">
			<thead>
				<tr>
					<th>ID</th>
					<th>Order</th>
					<th>Parent</th>
					<th>Name</th>
					<th>Access</th>
					<th>Flags</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
			{% for category in categories %}
				<tr>
					<td>{{ category.id() }}</td>
					<td>{{ category.orderId() }}</td>
					<td>{{ category.parentId() }}</td>
					<td>{{ category.name() }}</td>
					<td>{{ category.minRoleAccess() }} / {{ category.minRoleSetFlatCat() }}</td>
					<td>{% if category.node() %}Node{% endif %}{% if category.publicSpaces() %} Public{% endif %}{% if category.allowTrading() %} Trading{% endif %}{% if category.clubOnly() %} Club{% endif %}{% if category.topPriority() %} Priority{% endif %}</td>
					<td>
						<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/room_categories/edit?id={{ category.id() }}" class="btn btn-primary">Edit</a>
						<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/room_categories/delete?id={{ category.id() }}" class="btn btn-danger">Delete</a>
					</td>
				</tr>
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
