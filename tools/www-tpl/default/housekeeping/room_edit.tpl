{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set roomsActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
    <h1 class="mt-4">{{ locale.housekeeping_room_edit_room }}</h1>
	{% include "housekeeping/base/alert.tpl" %}
	<form class="table-responsive col-md-10" method="post">
		<div class="form-row">
			<div class="form-group col-md-2"><label>{{ locale.housekeeping_room_room_id }}</label><input type="text" class="form-control" value="{{ room.id() }}" disabled></div>
			<div class="form-group col-md-5"><label>{{ locale.housekeeping_room_name }}</label><input type="text" class="form-control" name="name" value="{{ room.name() }}"></div>
			<div class="form-group col-md-5"><label>{{ locale.housekeeping_room_description }}</label><input type="text" class="form-control" name="description" value="{{ room.description() }}"></div>
		</div>
		<div class="form-row">
			<div class="form-group col-md-3"><label>{{ locale.housekeeping_room_owner_id }}</label><input type="number" class="form-control" name="owner_id" value="{{ room.ownerId() }}"></div>
			<div class="form-group col-md-3">
				<label>{{ locale.housekeeping_room_category }}</label>
				<select class="form-control" name="category">
					{% for category in categories %}
					<option value="{{ category.id() }}"{% if room.categoryId() == category.id() %} selected{% endif %}>{{ category.id() }} - {{ category.name() }}</option>
					{% endfor %}
				</select>
			</div>
			<div class="form-group col-md-3">
				<label>{{ locale.housekeeping_room_model }}</label>
				<select class="form-control" name="model">
					{% for model in models %}
					<option value="{{ model.modelId() }}"{% if room.model() == model.modelId() %} selected{% endif %}>{{ model.modelId() }}</option>
					{% endfor %}
				</select>
			</div>
			<div class="form-group col-md-3">
				<label>{{ locale.housekeeping_room_access }}</label>
				<select class="form-control" name="accesstype">
					<option value="0"{% if room.accessType() == 0 %} selected{% endif %}>{{ locale.housekeeping_room_open }}</option>
					<option value="1"{% if room.accessType() == 1 %} selected{% endif %}>{{ locale.housekeeping_room_closed }}</option>
					<option value="2"{% if room.accessType() == 2 %} selected{% endif %}>{{ locale.housekeeping_room_password }}</option>
				</select>
			</div>
		</div>
		<div class="form-row">
			<div class="form-group col-md-3"><label>{{ locale.housekeeping_room_password }}</label><input type="text" class="form-control" name="password" value="{{ room.password() }}"></div>
			<div class="form-group col-md-3"><label>{{ locale.housekeeping_room_visitors_max }}</label><input type="number" class="form-control" name="visitors_max" value="{{ room.visitorsMax() }}"></div>
			<div class="form-group col-md-3"><label>{{ locale.housekeeping_room_rating }}</label><input type="number" class="form-control" name="rating" value="{{ room.rating() }}"></div>
			<div class="form-group col-md-3"><label>{{ locale.housekeeping_room_group_id }}</label><input type="number" class="form-control" name="group_id" value="{{ room.groupId() }}"></div>
		</div>
		<div class="form-row">
			<div class="form-group col-md-2"><label>{{ locale.housekeeping_room_wallpaper }}</label><input type="number" class="form-control" name="wallpaper" value="{{ room.wallpaper() }}"></div>
			<div class="form-group col-md-2"><label>{{ locale.housekeeping_room_floor }}</label><input type="number" class="form-control" name="floor" value="{{ room.floor() }}"></div>
			<div class="form-group col-md-2"><label>{{ locale.housekeeping_room_landscape }}</label><input type="text" class="form-control" name="landscape" value="{{ room.landscape() }}"></div>
			<div class="form-group col-md-3"><label>{{ locale.housekeeping_room_ccts }}</label><input type="text" class="form-control" name="ccts" value="{{ room.ccts() }}"></div>
			<div class="form-group col-md-3"><label>{{ locale.housekeeping_room_icon_data }}</label><input type="text" class="form-control" name="icon_data" value="{{ room.iconData() }}"></div>
		</div>
		<div class="form-row">
			<div class="form-group col-md-2 form-check mt-4"><input type="checkbox" class="form-check-input" name="showname" {% if room.showOwnerName() %}checked{% endif %}><label class="form-check-label">{{ locale.housekeeping_room_show_owner }}</label></div>
			<div class="form-group col-md-2 form-check mt-4"><input type="checkbox" class="form-check-input" name="superusers" {% if room.superUsers() %}checked{% endif %}><label class="form-check-label">{{ locale.housekeeping_room_superusers }}</label></div>
			<div class="form-group col-md-2 form-check mt-4"><input type="checkbox" class="form-check-input" name="is_hidden" {% if room.hidden() %}checked{% endif %}><label class="form-check-label">{{ locale.housekeeping_room_hidden }}</label></div>
		</div>
		<button type="submit" class="btn btn-info">{{ locale.housekeeping_room_save_room }}</button>
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/rooms" class="btn btn-secondary">{{ locale.housekeeping_room_back }}</a>
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/rooms/delete?id={{ room.id() }}" class="btn btn-danger">{{ locale.housekeeping_room_delete_room }}</a>
	</form>
	<hr>
	<div class="row">
		<div class="col-md-3">
			<h4>{{ locale.housekeeping_room_rights }}</h4>
			<ul>
				{% for right in rights %}
				<li>{{ right.username() }} ({{ right.userId() }})</li>
				{% endfor %}
			</ul>
		</div>
		<div class="col-md-3">
			<h4>{{ locale.housekeeping_room_bans }}</h4>
			<ul>
				{% for ban in bans %}
				<li>{{ ban.username() }} ({{ ban.userId() }}{{ locale.housekeeping_room_until }} {{ ban.expireAt() }}</li>
				{% endfor %}
			</ul>
		</div>
		<div class="col-md-3">
			<h4>{{ locale.housekeeping_room_events }}</h4>
			<ul>
				{% for event in events %}
				<li>{{ event.name() }} {{ locale.housekeeping_room_by }} {{ event.username() }} {{ locale.housekeeping_room_until_text }} {{ event.expireTime() }}</li>
				{% endfor %}
			</ul>
		</div>
		<div class="col-md-3">
			<h4>{{ locale.housekeeping_room_entry_badges }}</h4>
			<ul>
				{% for badge in entryBadges %}
				<li>{{ badge }}</li>
				{% endfor %}
			</ul>
		</div>
	</div>
{% include "housekeeping/base/footer.tpl" %}
