{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set roomsActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
    <h1 class="mt-4">Edit Room</h1>
	{% include "housekeeping/base/alert.tpl" %}
	<form class="table-responsive col-md-10" method="post">
		<div class="form-row">
			<div class="form-group col-md-2"><label>Room ID</label><input type="text" class="form-control" value="{{ room.id() }}" disabled></div>
			<div class="form-group col-md-5"><label>Name</label><input type="text" class="form-control" name="name" value="{{ room.name() }}"></div>
			<div class="form-group col-md-5"><label>Description</label><input type="text" class="form-control" name="description" value="{{ room.description() }}"></div>
		</div>
		<div class="form-row">
			<div class="form-group col-md-3"><label>Owner ID</label><input type="number" class="form-control" name="owner_id" value="{{ room.ownerId() }}"></div>
			<div class="form-group col-md-3">
				<label>Category</label>
				<select class="form-control" name="category">
					{% for category in categories %}
					<option value="{{ category.id() }}"{% if room.categoryId() == category.id() %} selected{% endif %}>{{ category.id() }} - {{ category.name() }}</option>
					{% endfor %}
				</select>
			</div>
			<div class="form-group col-md-3">
				<label>Model</label>
				<select class="form-control" name="model">
					{% for model in models %}
					<option value="{{ model.modelId() }}"{% if room.model() == model.modelId() %} selected{% endif %}>{{ model.modelId() }}</option>
					{% endfor %}
				</select>
			</div>
			<div class="form-group col-md-3">
				<label>Access</label>
				<select class="form-control" name="accesstype">
					<option value="0"{% if room.accessType() == 0 %} selected{% endif %}>Open</option>
					<option value="1"{% if room.accessType() == 1 %} selected{% endif %}>Closed</option>
					<option value="2"{% if room.accessType() == 2 %} selected{% endif %}>Password</option>
				</select>
			</div>
		</div>
		<div class="form-row">
			<div class="form-group col-md-3"><label>Password</label><input type="text" class="form-control" name="password" value="{{ room.password() }}"></div>
			<div class="form-group col-md-3"><label>Visitors Max</label><input type="number" class="form-control" name="visitors_max" value="{{ room.visitorsMax() }}"></div>
			<div class="form-group col-md-3"><label>Rating</label><input type="number" class="form-control" name="rating" value="{{ room.rating() }}"></div>
			<div class="form-group col-md-3"><label>Group ID</label><input type="number" class="form-control" name="group_id" value="{{ room.groupId() }}"></div>
		</div>
		<div class="form-row">
			<div class="form-group col-md-2"><label>Wallpaper</label><input type="number" class="form-control" name="wallpaper" value="{{ room.wallpaper() }}"></div>
			<div class="form-group col-md-2"><label>Floor</label><input type="number" class="form-control" name="floor" value="{{ room.floor() }}"></div>
			<div class="form-group col-md-2"><label>Landscape</label><input type="text" class="form-control" name="landscape" value="{{ room.landscape() }}"></div>
			<div class="form-group col-md-3"><label>CCTS</label><input type="text" class="form-control" name="ccts" value="{{ room.ccts() }}"></div>
			<div class="form-group col-md-3"><label>Icon Data</label><input type="text" class="form-control" name="icon_data" value="{{ room.iconData() }}"></div>
		</div>
		<div class="form-row">
			<div class="form-group col-md-2 form-check mt-4"><input type="checkbox" class="form-check-input" name="showname" {% if room.showOwnerName() %}checked{% endif %}><label class="form-check-label">Show Owner</label></div>
			<div class="form-group col-md-2 form-check mt-4"><input type="checkbox" class="form-check-input" name="superusers" {% if room.superUsers() %}checked{% endif %}><label class="form-check-label">Superusers</label></div>
			<div class="form-group col-md-2 form-check mt-4"><input type="checkbox" class="form-check-input" name="is_hidden" {% if room.hidden() %}checked{% endif %}><label class="form-check-label">Hidden</label></div>
		</div>
		<button type="submit" class="btn btn-info">Save Room</button>
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/rooms" class="btn btn-secondary">Back</a>
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/rooms/delete?id={{ room.id() }}" class="btn btn-danger">Delete Room</a>
	</form>
	<hr>
	<div class="row">
		<div class="col-md-3">
			<h4>Rights</h4>
			<ul>
				{% for right in rights %}
				<li>{{ right.username() }} ({{ right.userId() }})</li>
				{% endfor %}
			</ul>
		</div>
		<div class="col-md-3">
			<h4>Bans</h4>
			<ul>
				{% for ban in bans %}
				<li>{{ ban.username() }} ({{ ban.userId() }}) until {{ ban.expireAt() }}</li>
				{% endfor %}
			</ul>
		</div>
		<div class="col-md-3">
			<h4>Events</h4>
			<ul>
				{% for event in events %}
				<li>{{ event.name() }} by {{ event.username() }} until {{ event.expireTime() }}</li>
				{% endfor %}
			</ul>
		</div>
		<div class="col-md-3">
			<h4>Entry Badges</h4>
			<ul>
				{% for badge in entryBadges %}
				<li>{{ badge }}</li>
				{% endfor %}
			</ul>
		</div>
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
