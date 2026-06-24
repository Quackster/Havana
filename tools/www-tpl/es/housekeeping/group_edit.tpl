{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set groupsActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
    <h1 class="mt-4">Edit Group</h1>
	{% include "housekeeping/base/alert.tpl" %}
	<form class="table-responsive col-md-10" method="post">
		<div class="form-row">
			<div class="form-group col-md-2"><label>Group ID</label><input type="text" class="form-control" value="{{ group.id() }}" disabled></div>
			<div class="form-group col-md-4"><label>Name</label><input type="text" class="form-control" name="name" value="{{ group.name() }}"></div>
			<div class="form-group col-md-3"><label>Owner ID</label><input type="number" class="form-control" name="owner_id" value="{{ group.ownerId() }}"></div>
			<div class="form-group col-md-3"><label>Room ID</label><input type="number" class="form-control" name="room_id" value="{{ group.roomId() }}"></div>
		</div>
		<div class="form-row">
			<div class="form-group col-md-4"><label>Badge</label><input type="text" class="form-control" name="badge" value="{{ group.badge() }}"></div>
			<div class="form-group col-md-4"><label>Background</label><input type="text" class="form-control" name="background" value="{{ group.background() }}"></div>
			<div class="form-group col-md-4"><label>Alias</label><input type="text" class="form-control" name="alias" value="{{ group.alias() }}"></div>
		</div>
		<div class="form-row">
			<div class="form-group col-md-3"><label>Views</label><input type="number" class="form-control" name="views" value="{{ group.views() }}"></div>
			<div class="form-group col-md-3"><label>Topics</label><input type="number" class="form-control" name="topics" value="{{ group.topics() }}"></div>
			<div class="form-group col-md-3"><label>Group Type</label><input type="number" class="form-control" name="group_type" value="{{ group.groupType() }}"></div>
			<div class="form-group col-md-3 form-check mt-4"><input type="checkbox" class="form-check-input" name="recommended" {% if group.recommended() %}checked{% endif %}><label class="form-check-label">Recommended</label></div>
		</div>
		<div class="form-row">
			<div class="form-group col-md-4">
				<label>Forum Type</label>
				<select class="form-control" name="forum_type">
					<option value="0"{% if group.forumType() == 0 %} selected{% endif %}>Public</option>
					<option value="1"{% if group.forumType() == 1 %} selected{% endif %}>Private</option>
				</select>
			</div>
			<div class="form-group col-md-4">
				<label>Post Permission</label>
				<select class="form-control" name="forum_premission">
					<option value="0"{% if group.forumPermission() == 0 %} selected{% endif %}>Everyone</option>
					<option value="1"{% if group.forumPermission() == 1 %} selected{% endif %}>Members</option>
					<option value="2"{% if group.forumPermission() == 2 %} selected{% endif %}>Admins</option>
				</select>
			</div>
		</div>
		<div class="form-group">
			<label>Description</label>
			<textarea class="form-control" name="description" rows="4">{{ group.description() }}</textarea>
		</div>
		<button type="submit" class="btn btn-info">Save Group</button>
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/groups" class="btn btn-secondary">Back</a>
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/groups/delete?id={{ group.id() }}" class="btn btn-danger">Delete Group</a>
	</form>
	<hr>
	<h3>Members</h3>
	<div class="table-responsive">
		<table class="table table-striped">
			<thead><tr><th>User</th><th>Rank</th><th>Pending</th><th>Joined</th><th></th></tr></thead>
			<tbody>
			{% for member in members %}
				<tr>
					<form method="post" action="{{ site.sitePath }}/{{ site.housekeepingPath }}/groups/member">
					<input type="hidden" name="group_id" value="{{ group.id() }}">
					<input type="hidden" name="user_id" value="{{ member.userId() }}">
					<td>{{ member.username() }} ({{ member.userId() }})</td>
					<td>
						<select class="form-control" name="member_rank">
							<option value="1"{% if member.memberRank() == 1 %} selected{% endif %}>Member</option>
							<option value="2"{% if member.memberRank() == 2 %} selected{% endif %}>Admin</option>
							<option value="3"{% if member.memberRank() == 3 %} selected{% endif %}>Owner</option>
						</select>
					</td>
					<td><input type="checkbox" name="is_pending" {% if member.pending() %}checked{% endif %}></td>
					<td>{{ member.createdAt() }}</td>
					<td><button type="submit" class="btn btn-primary">Save</button> <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/groups/member?group_id={{ group.id() }}&user_id={{ member.userId() }}&delete=true" class="btn btn-danger">Remove</a></td>
					</form>
				</tr>
			{% endfor %}
			</tbody>
		</table>
	</div>
	<h3>Threads</h3>
	<div class="table-responsive">
		<table class="table table-striped">
			<thead><tr><th>ID</th><th>Title</th><th>Poster</th><th>Replies</th><th>Flags</th><th></th></tr></thead>
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
					<td><label><input type="checkbox" name="is_open" {% if thread.open() %}checked{% endif %}> Open</label> <label><input type="checkbox" name="is_stickied" {% if thread.stickied() %}checked{% endif %}> Sticky</label></td>
					<td><button type="submit" class="btn btn-primary">Save</button> <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/groups/thread?group_id={{ group.id() }}&thread_id={{ thread.id() }}&delete=true" class="btn btn-danger">Delete</a></td>
					</form>
				</tr>
			{% endfor %}
			</tbody>
		</table>
	</div>
	<h3>Recent Replies</h3>
	<div class="table-responsive">
		<table class="table table-striped">
			<thead><tr><th>ID</th><th>Thread</th><th>Poster</th><th>Message</th><th>Status</th><th></th></tr></thead>
			<tbody>
			{% for reply in replies %}
				<tr>
					<td>{{ reply.id() }}</td>
					<td>{{ reply.topicTitle() }}</td>
					<td>{{ reply.posterName() }} ({{ reply.posterId() }})</td>
					<td>{{ reply.message() }}</td>
					<td>{% if reply.deleted() %}Deleted{% else %}Visible{% endif %}{% if reply.edited() %} Edited{% endif %}</td>
					<td>
						{% if reply.deleted() %}
						<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/groups/reply?group_id={{ group.id() }}&reply_id={{ reply.id() }}&deleted=false" class="btn btn-secondary">Restore</a>
						{% else %}
						<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/groups/reply?group_id={{ group.id() }}&reply_id={{ reply.id() }}&deleted=true" class="btn btn-warning">Hide</a>
						{% endif %}
						<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/groups/reply?group_id={{ group.id() }}&reply_id={{ reply.id() }}&delete=true" class="btn btn-danger">Delete</a>
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
