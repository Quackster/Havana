{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set badgesActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
    <h1 class="mt-4">{{ locale.housekeeping_badges_badges }}</h1>
	{% include "housekeeping/base/alert.tpl" %}
	<form class="form-inline mb-3" method="get">
		<input type="text" class="form-control mr-2" name="query" value="{{ query }}" placeholder="{{ locale.housekeeping_badges_user_id_username_or_badge_code }}">
		<button type="submit" class="btn btn-info">{{ locale.housekeeping_badges_search }}</button>
	</form>
	<form class="form-inline mb-4" method="post" action="{{ site.sitePath }}/{{ site.housekeepingPath }}/badges/grant">
		<input type="number" class="form-control mr-2" name="user_id" placeholder="{{ locale.housekeeping_badges_user_id }}">
		<input type="text" class="form-control mr-2" name="badge" placeholder="{{ locale.housekeeping_badges_badge_code }}">
		<button type="submit" class="btn btn-danger">{{ locale.housekeeping_badges_grant_badge }}</button>
	</form>
	<h3>{{ locale.housekeeping_badges_assignments }}</h3>
	<div class="table-responsive">
		<table class="table table-striped">
			<thead><tr><th>{{ locale.housekeeping_badges_user }}</th><th>{{ locale.housekeeping_badges_badge }}</th><th>{{ locale.housekeeping_badges_equipped }}</th><th>{{ locale.housekeeping_badges_slot }}</th><th></th></tr></thead>
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
					<td><button type="submit" class="btn btn-primary">{{ locale.housekeeping_badges_save }}</button> <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/badges/remove?user_id={{ assignment.userId() }}&badge={{ assignment.badge() }}" class="btn btn-danger">{{ locale.housekeeping_badges_remove }}</a></td>
					</form>
				</tr>
			{% endfor %}
			</tbody>
		</table>
	</div>
	<h3>{{ locale.housekeeping_badges_badge_catalogue }}</h3>
	<div class="table-responsive">
		<table class="table table-striped">
			<thead><tr><th>{{ locale.housekeeping_badges_badge }}</th><th>{{ locale.housekeeping_badges_assignments }}</th><th>{{ locale.housekeeping_badges_rank_badge }}</th></tr></thead>
			<tbody>
			{% for badge in catalogue %}
				<tr><td>{{ badge.badge() }}</td><td>{{ badge.assignmentCount() }}</td><td>{% if badge.rankBadge() %}{{ locale.housekeeping_badges_yes }}{% else %}{{ locale.housekeeping_badges_no }}{% endif %}</td></tr>
			{% endfor %}
			</tbody>
		</table>
	</div>
	<h3>{{ locale.housekeeping_badges_recent_badge_audit }}</h3>
	<div class="table-responsive">
		<table class="table table-striped">
			<thead><tr><th>{{ locale.housekeeping_badges_action }}</th><th>{{ locale.housekeeping_badges_staff }}</th><th>{{ locale.housekeeping_badges_target }}</th><th>{{ locale.housekeeping_badges_badge }}</th><th>{{ locale.housekeeping_badges_notes }}</th><th>{{ locale.housekeeping_badges_created }}</th></tr></thead>
			<tbody>
			{% for log in audit %}
				<tr><td>{{ log.action() }}</td><td>{{ log.staffId() }}</td><td>{{ log.targetId() }}</td><td>{{ log.message() }}</td><td>{{ log.extraNotes() }}</td><td>{{ log.createdAt() }}</td></tr>
			{% endfor %}
			</tbody>
		</table>
	</div>
{% include "housekeeping/base/footer.tpl" %}
