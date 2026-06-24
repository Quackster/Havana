{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set groupsActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
    <h1 class="mt-4">{{ locale.housekeeping_groups_groups }}</h1>
	{% include "housekeeping/base/alert.tpl" %}
	<form class="form-inline mb-3" method="get">
		<input type="text" class="form-control mr-2" name="query" value="{{ query }}" placeholder="{{ locale.housekeeping_groups_group_id_name_alias_or_owner }}">
		<button type="submit" class="btn btn-info">{{ locale.housekeeping_groups_search }}</button>
	</form>
	<div class="table-responsive">
		<table class="table table-striped">
			<thead>
				<tr>
					<th>{{ locale.housekeeping_groups_id }}</th>
					<th>{{ locale.housekeeping_groups_name }}</th>
					<th>{{ locale.housekeeping_groups_owner }}</th>
					<th>{{ locale.housekeeping_groups_room }}</th>
					<th>{{ locale.housekeeping_groups_members }}</th>
					<th>{{ locale.housekeeping_groups_pending }}</th>
					<th>{{ locale.housekeeping_groups_threads }}</th>
					<th>{{ locale.housekeeping_groups_alias }}</th>
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
						<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/groups/edit?id={{ group.id() }}" class="btn btn-primary">{{ locale.housekeeping_groups_edit }}</a>
						<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/groups/delete?id={{ group.id() }}" class="btn btn-danger">{{ locale.housekeeping_groups_delete }}</a>
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
