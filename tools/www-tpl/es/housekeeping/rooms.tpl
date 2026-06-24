{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set roomsActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
    <h1 class="mt-4">Rooms</h1>
	{% include "housekeeping/base/alert.tpl" %}
	<form class="form-inline mb-3" method="get">
		<input type="text" class="form-control mr-2" name="query" value="{{ query }}" placeholder="Room ID, room name, or owner">
		<button type="submit" class="btn btn-info">Search</button>
	</form>
	<div class="table-responsive">
		<table class="table table-striped">
			<thead>
				<tr>
					<th>ID</th>
					<th>Name</th>
					<th>Owner</th>
					<th>Category</th>
					<th>Model</th>
					<th>State</th>
					<th>Visitors</th>
					<th>Rating</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
			{% for room in rooms %}
				<tr>
					<td>{{ room.id() }}</td>
					<td>{{ room.name() }}</td>
					<td>{% if room.ownerId() > 0 %}{{ room.ownerName() }} ({{ room.ownerId() }}){% else %}Public{% endif %}</td>
					<td>{{ room.categoryId() }}</td>
					<td>{{ room.model() }}</td>
					<td>{% if room.hidden() %}Hidden{% else %}{% if room.accessType() == 2 %}Password{% elseif room.accessType() == 1 %}Closed{% else %}Open{% endif %}{% endif %}</td>
					<td>{{ room.visitorsNow() }} / {{ room.visitorsMax() }}</td>
					<td>{{ room.rating() }}</td>
					<td>
						<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/rooms/edit?id={{ room.id() }}" class="btn btn-primary">Edit</a>
						{% if room.hidden() %}
						<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/rooms/hide?id={{ room.id() }}&hidden=false" class="btn btn-secondary">Unhide</a>
						{% else %}
						<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/rooms/hide?id={{ room.id() }}&hidden=true" class="btn btn-warning">Hide</a>
						{% endif %}
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
