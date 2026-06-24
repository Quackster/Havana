{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set groupsActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
    <h1 class="mt-4">{{ locale.housekeeping_group_edit_group }}</h1>
	{% include "housekeeping/base/alert.tpl" %}
	<form class="table-responsive col-md-10" method="post">
		<div class="form-row">
			<div class="form-group col-md-2"><label>{{ locale.housekeeping_group_group_id }}</label><input type="text" class="form-control" value="{{ group.id() }}" disabled></div>
			<div class="form-group col-md-4"><label>{{ locale.housekeeping_group_name }}</label><input type="text" class="form-control" name="name" value="{{ group.name() }}"></div>
			<div class="form-group col-md-3"><label>{{ locale.housekeeping_group_owner_id }}</label><input type="number" class="form-control" name="owner_id" value="{{ group.ownerId() }}"></div>
			<div class="form-group col-md-3"><label>{{ locale.housekeeping_group_room_id }}</label><input type="number" class="form-control" name="room_id" value="{{ group.roomId() }}"></div>
		</div>
		<div class="form-row">
			<div class="form-group col-md-4"><label>{{ locale.housekeeping_group_badge }}</label><input type="text" class="form-control" name="badge" value="{{ group.badge() }}"></div>
			<div class="form-group col-md-4"><label>{{ locale.housekeeping_group_background }}</label><input type="text" class="form-control" name="background" value="{{ group.background() }}"></div>
			<div class="form-group col-md-4"><label>{{ locale.housekeeping_group_alias }}</label><input type="text" class="form-control" name="alias" value="{{ group.alias() }}"></div>
		</div>
		<div class="form-row">
			<div class="form-group col-md-3"><label>{{ locale.housekeeping_group_views }}</label><input type="number" class="form-control" name="views" value="{{ group.views() }}"></div>
			<div class="form-group col-md-3"><label>{{ locale.housekeeping_group_topics }}</label><input type="number" class="form-control" name="topics" value="{{ group.topics() }}"></div>
			<div class="form-group col-md-3"><label>{{ locale.housekeeping_group_group_type }}</label><input type="number" class="form-control" name="group_type" value="{{ group.groupType() }}"></div>
			<div class="form-group col-md-3 form-check mt-4"><input type="checkbox" class="form-check-input" name="recommended" {% if group.recommended() %}checked{% endif %}><label class="form-check-label">{{ locale.housekeeping_group_recommended }}</label></div>
		</div>
		<div class="form-row">
			<div class="form-group col-md-4">
				<label>{{ locale.housekeeping_group_forum_type }}</label>
				<select class="form-control" name="forum_type">
					<option value="0"{% if group.forumType() == 0 %} selected{% endif %}>{{ locale.housekeeping_group_public }}</option>
					<option value="1"{% if group.forumType() == 1 %} selected{% endif %}>{{ locale.housekeeping_group_private }}</option>
				</select>
			</div>
			<div class="form-group col-md-4">
				<label>{{ locale.housekeeping_group_post_permission }}</label>
				<select class="form-control" name="forum_premission">
					<option value="0"{% if group.forumPermission() == 0 %} selected{% endif %}>{{ locale.housekeeping_group_everyone }}</option>
					<option value="1"{% if group.forumPermission() == 1 %} selected{% endif %}>{{ locale.housekeeping_group_members }}</option>
					<option value="2"{% if group.forumPermission() == 2 %} selected{% endif %}>{{ locale.housekeeping_group_admins }}</option>
				</select>
			</div>
		</div>
		<div class="form-group">
			<label>{{ locale.housekeeping_group_description }}</label>
			<textarea class="form-control" name="description" rows="4">{{ group.description() }}</textarea>
		</div>
		<button type="submit" class="btn btn-info">{{ locale.housekeeping_group_save_group }}</button>
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/groups" class="btn btn-secondary">{{ locale.housekeeping_group_back }}</a>
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/groups/delete?id={{ group.id() }}" class="btn btn-danger">{{ locale.housekeeping_group_delete_group }}</a>
	</form>
	<hr>
	<h3>{{ locale.housekeeping_group_members }}</h3>
	<div class="table-responsive">
		<table class="table table-striped">
			<thead><tr><th>{{ locale.housekeeping_group_user }}</th><th>{{ locale.housekeeping_group_rank }}</th><th>{{ locale.housekeeping_group_pending }}</th><th>{{ locale.housekeeping_group_joined }}</th><th></th></tr></thead>
			<tbody>
			{% for member in members %}
				<tr>
					<form method="post" action="{{ site.sitePath }}/{{ site.housekeepingPath }}/groups/member">
					<input type="hidden" name="group_id" value="{{ group.id() }}">
					<input type="hidden" name="user_id" value="{{ member.userId() }}">
					<td>{{ member.username() }} ({{ member.userId() }})</td>
					<td>
						<select class="form-control" name="member_rank">
							<option value="1"{% if member.memberRank() == 1 %} selected{% endif %}>{{ locale.housekeeping_group_member }}</option>
							<option value="2"{% if member.memberRank() == 2 %} selected{% endif %}>{{ locale.housekeeping_group_admin }}</option>
							<option value="3"{% if member.memberRank() == 3 %} selected{% endif %}>{{ locale.housekeeping_group_owner }}</option>
						</select>
					</td>
					<td><input type="checkbox" name="is_pending" {% if member.pending() %}checked{% endif %}></td>
					<td>{{ member.createdAt() }}</td>
					<td><button type="submit" class="btn btn-primary">{{ locale.housekeeping_group_save }}</button> <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/groups/member?group_id={{ group.id() }}&user_id={{ member.userId() }}&delete=true" class="btn btn-danger">{{ locale.housekeeping_group_remove }}</a></td>
					</form>
				</tr>
			{% endfor %}
			</tbody>
		</table>
	</div>
	<h3>{{ locale.housekeeping_group_threads }}</h3>
	<div class="table-responsive">
		<table class="table table-striped">
			<thead><tr><th>{{ locale.housekeeping_group_id }}</th><th>{{ locale.housekeeping_group_title }}</th><th>{{ locale.housekeeping_group_poster }}</th><th>{{ locale.housekeeping_group_replies }}</th><th>{{ locale.housekeeping_group_flags }}</th><th></th></tr></thead>
			<tbody>
			{% for thread in threads %}
				<tr>
					<form method="post" action="{{ site.sitePath }}/{{ site.housekeepingPath }}/groups/thread">
					<input type="hidden" name="group_id" value="{{ group.id() }}">
					<input type="hidden" name="thread_id" value="{{ thread.id() }}">
					<td>{{ thread.id() }}</td>
					<td><input type="text" class="form-control" name="topic_title" value="{{ thread.topicTitle() }}"></td>
					<td>{{ thread.posterName() }} ({{ thread.posterId() }})</td>
					<td>{{ thread.replyCount() }}</td>
					<td><label><input type="checkbox" name="is_open" {% if thread.open() %}checked{% endif %}> {{ locale.housekeeping_group_open }}</label> <label><input type="checkbox" name="is_stickied" {% if thread.stickied() %}checked{% endif %}> {{ locale.housekeeping_group_sticky }}</label></td>
					<td><button type="submit" class="btn btn-primary">{{ locale.housekeeping_group_save }}</button> <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/groups/thread?group_id={{ group.id() }}&thread_id={{ thread.id() }}&delete=true" class="btn btn-danger">{{ locale.housekeeping_group_delete }}</a></td>
					</form>
				</tr>
			{% endfor %}
			</tbody>
		</table>
	</div>
	<h3>{{ locale.housekeeping_group_recent_replies }}</h3>
	<div class="table-responsive">
		<table class="table table-striped">
			<thead><tr><th>{{ locale.housekeeping_group_id }}</th><th>{{ locale.housekeeping_group_thread }}</th><th>{{ locale.housekeeping_group_poster }}</th><th>{{ locale.housekeeping_group_message }}</th><th>{{ locale.housekeeping_group_status }}</th><th></th></tr></thead>
			<tbody>
			{% for reply in replies %}
				<tr>
					<td>{{ reply.id() }}</td>
					<td>{{ reply.topicTitle() }}</td>
					<td>{{ reply.posterName() }} ({{ reply.posterId() }})</td>
					<td>{{ reply.message() }}</td>
					<td>{% if reply.deleted() %}{{ locale.housekeeping_group_deleted }}{% else %}{{ locale.housekeeping_group_visible }}{% endif %}{% if reply.edited() %} {{ locale.housekeeping_group_edited }}{% endif %}</td>
					<td>
						{% if reply.deleted() %}
						<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/groups/reply?group_id={{ group.id() }}&reply_id={{ reply.id() }}&deleted=false" class="btn btn-secondary">{{ locale.housekeeping_group_restore }}</a>
						{% else %}
						<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/groups/reply?group_id={{ group.id() }}&reply_id={{ reply.id() }}&deleted=true" class="btn btn-warning">{{ locale.housekeeping_group_hide }}</a>
						{% endif %}
						<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/groups/reply?group_id={{ group.id() }}&reply_id={{ reply.id() }}&delete=true" class="btn btn-danger">{{ locale.housekeeping_group_delete }}</a>
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
