{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set roomsActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
    <h1 class="mt-4">{{ locale.housekeeping_rooms_rooms }}</h1>
	{% include "housekeeping/base/alert.tpl" %}
	<form class="form-inline mb-3" method="get">
		<input type="text" class="form-control mr-2" name="query" value="{{ query }}" placeholder="{{ locale.housekeeping_rooms_room_id_room_name_or_owner }}">
		<button type="submit" class="btn btn-info">{{ locale.housekeeping_rooms_search }}</button>
	</form>
	<div class="table-responsive">
		<table class="table table-striped">
			<thead>
				<tr>
					<th>{{ locale.housekeeping_rooms_id }}</th>
					<th>{{ locale.housekeeping_rooms_name }}</th>
					<th>{{ locale.housekeeping_rooms_owner }}</th>
					<th>{{ locale.housekeeping_rooms_category }}</th>
					<th>{{ locale.housekeeping_rooms_model }}</th>
					<th>{{ locale.housekeeping_rooms_state }}</th>
					<th>{{ locale.housekeeping_rooms_visitors }}</th>
					<th>{{ locale.housekeeping_rooms_rating }}</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
			{% for room in rooms %}
				<tr>
					<td>{{ room.id() }}</td>
					<td>{{ room.name() }}</td>
					<td>{% if room.ownerId() > 0 %}{{ room.ownerName() }} ({{ room.ownerId() }}){% else %}{{ locale.housekeeping_rooms_public }}{% endif %}</td>
					<td>{{ room.categoryId() }}</td>
					<td>{{ room.model() }}</td>
					<td>{% if room.hidden() %}{{ locale.housekeeping_rooms_hidden }}{% else %}{% if room.accessType() == 2 %}{{ locale.housekeeping_rooms_password }}{% elseif room.accessType() == 1 %}{{ locale.housekeeping_rooms_closed }}{% else %}{{ locale.housekeeping_rooms_open }}{% endif %}{% endif %}</td>
					<td>{{ room.visitorsNow() }} / {{ room.visitorsMax() }}</td>
					<td>{{ room.rating() }}</td>
					<td>
						<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/rooms/edit?id={{ room.id() }}" class="btn btn-primary">{{ locale.housekeeping_rooms_edit }}</a>
						{% if room.hidden() %}
						<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/rooms/hide?id={{ room.id() }}&hidden=false" class="btn btn-secondary">{{ locale.housekeeping_rooms_unhide }}</a>
						{% else %}
						<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/rooms/hide?id={{ room.id() }}&hidden=true" class="btn btn-warning">{{ locale.housekeeping_rooms_hide }}</a>
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
