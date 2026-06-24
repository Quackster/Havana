{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set groupsActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
    <h1 class="mt-4">Groups</h1>
	{% include "housekeeping/base/alert.tpl" %}
	<form class="form-inline mb-3" method="get">
		<input type="text" class="form-control mr-2" name="query" value="{{ query }}" placeholder="Group ID, name, alias, or owner">
		<button type="submit" class="btn btn-info">Search</button>
	</form>
	<div class="table-responsive">
		<table class="table table-striped">
			<thead>
				<tr>
					<th>ID</th>
					<th>Name</th>
					<th>Owner</th>
					<th>Room</th>
					<th>Members</th>
					<th>Pending</th>
					<th>Threads</th>
					<th>Alias</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
			{% for group in groups %}
				<tr>
					<td>{{ group.id() }}</td>
					<td>{{ group.name() }}</td>
					<td>{{ group.ownerName() }} ({{ group.ownerId() }})</td>
					<td>{{ group.roomId() }}</td>
					<td>{{ group.memberCount() }}</td>
					<td>{{ group.pendingCount() }}</td>
					<td>{{ group.threadCount() }}</td>
					<td>{{ group.alias() }}</td>
					<td>
						<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/groups/edit?id={{ group.id() }}" class="btn btn-primary">Edit</a>
						<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/groups/delete?id={{ group.id() }}" class="btn btn-danger">Delete</a>
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
