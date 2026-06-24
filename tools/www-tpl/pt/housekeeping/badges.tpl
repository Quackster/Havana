{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set badgesActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
    <h1 class="mt-4">Badges</h1>
	{% include "housekeeping/base/alert.tpl" %}
	<form class="form-inline mb-3" method="get">
		<input type="text" class="form-control mr-2" name="query" value="{{ query }}" placeholder="User ID, username, or badge code">
		<button type="submit" class="btn btn-info">Search</button>
	</form>
	<form class="form-inline mb-4" method="post" action="{{ site.sitePath }}/{{ site.housekeepingPath }}/badges/grant">
		<input type="number" class="form-control mr-2" name="user_id" placeholder="User ID">
		<input type="text" class="form-control mr-2" name="badge" placeholder="Badge code">
		<button type="submit" class="btn btn-danger">Grant Badge</button>
	</form>
	<h3>Assignments</h3>
	<div class="table-responsive">
		<table class="table table-striped">
			<thead><tr><th>User</th><th>Badge</th><th>Equipped</th><th>Slot</th><th></th></tr></thead>
			<tbody>
			{% for assignment in assignments %}
				<tr>
					<form method="post" action="{{ site.sitePath }}/{{ site.housekeepingPath }}/badges/update">
					<input type="hidden" name="user_id" value="{{ assignment.userId() }}">
					<input type="hidden" name="badge" value="{{ assignment.badge() }}">
					<td>{{ assignment.username() }} ({{ assignment.userId() }})</td>
					<td>{{ assignment.badge() }}</td>
					<td><input type="checkbox" name="equipped" {% if assignment.equipped() %}checked{% endif %}></td>
					<td><input type="number" class="form-control" name="slot_id" value="{{ assignment.slotId() }}"></td>
					<td><button type="submit" class="btn btn-primary">Save</button> <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/badges/remove?user_id={{ assignment.userId() }}&badge={{ assignment.badge() }}" class="btn btn-danger">Remove</a></td>
					</form>
				</tr>
			{% endfor %}
			</tbody>
		</table>
	</div>
	<h3>Badge Catalogue</h3>
	<div class="table-responsive">
		<table class="table table-striped">
			<thead><tr><th>Badge</th><th>Assignments</th><th>Rank Badge</th></tr></thead>
			<tbody>
			{% for badge in catalogue %}
				<tr><td>{{ badge.badge() }}</td><td>{{ badge.assignmentCount() }}</td><td>{% if badge.rankBadge() %}Yes{% else %}No{% endif %}</td></tr>
			{% endfor %}
			</tbody>
		</table>
	</div>
	<h3>Recent Badge Audit</h3>
	<div class="table-responsive">
		<table class="table table-striped">
			<thead><tr><th>Action</th><th>Staff</th><th>Target</th><th>Badge</th><th>Notes</th><th>Created</th></tr></thead>
			<tbody>
			{% for log in audit %}
				<tr><td>{{ log.action() }}</td><td>{{ log.staffId() }}</td><td>{{ log.targetId() }}</td><td>{{ log.message() }}</td><td>{{ log.extraNotes() }}</td><td>{{ log.createdAt() }}</td></tr>
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
