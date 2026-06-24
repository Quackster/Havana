{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set searchUsersActive = " active " %}
	{% include "housekeeping/base/navigation.tpl" %}
     <h1 class="mt-4">{{ locale.housekeeping_users_search_users }}</h1>
		{% include "housekeeping/base/alert.tpl" %}
		<p>{{ locale.housekeeping_users_here_you_can_search_users_by_the_field_of_your_choice_and_the_requested_input_by_you }}</p>
		<form class="table-responsive col-md-4" method="post">
			<div class="form-group">
				<label for="field">{{ locale.housekeeping_users_field }}</label>
				<select name="searchField" class="form-control" id="field">
					<option value="username">{{ locale.housekeeping_users_username }}</option>
					<option value="id">{{ locale.housekeeping_users_id }}</option>
					<option value="credits">{{ locale.housekeeping_users_credits }}</option>
					<option value="pixels">{{ locale.housekeeping_users_pixels }}</option>
					<option value="mission">{{ locale.housekeeping_users_mission }}</option>
				</select>
			</div>
			<div class="form-group">
				<label for="field">{{ locale.housekeeping_users_search_type }}</label>
				<select name="searchType" class="form-control" id="field">
					<option value="contains">{{ locale.housekeeping_users_contains }}</option>
					<option value="starts_with">{{ locale.housekeeping_users_starts_with }}</option>
					<option value="ends_with">{{ locale.housekeeping_users_ends_with }}</option>
					<option value="equals">{{ locale.housekeeping_users_equals }}</option>
				</select>
			</div>
			<div class="form-group">
				<label for="searchFor">{{ locale.housekeeping_users_search_data }}</label>
				<input type="text" name="searchQuery" class="form-control" id="searchFor" placeholder="{{ locale.housekeeping_users_looking_for }}">
			</div>
			<button type="submit" class="btn btn-primary">{{ locale.housekeeping_users_perform_search }}</button>
		</form>
		<br>
		{% if players|length > 0 %}
		<h2>{{ locale.housekeeping_users_search_results }}</h2>
          <div class="table-responsive">
            <table class="table table-striped">
              <thead>
                <tr>
                  <th>{{ locale.housekeeping_users_id }}</th>
                  <th>{{ locale.housekeeping_users_name }}</th>
				  <th>{{ locale.housekeeping_users_email }}</th>
				  <th>{{ locale.housekeeping_users_look }}</th>
                  <th>{{ locale.housekeeping_users_mission }}</th>
                  <th>{{ locale.housekeeping_users_credits }}</th>
                  <th>{{ locale.housekeeping_users_duckets }}</th>
				  <th>{{ locale.housekeeping_users_last_online }}</th>
				  <th>{{ locale.housekeeping_users_date_joined }}</th>
                </tr>
              </thead>
              <tbody>
			    {% set num = 1 %}
				{% for player in players %}
                <tr>
                  <td>{{ player.id }}</td>
                  <td><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/users/edit?id={{ player.id }}">{{ player.name }}</a></td>
				  <td>{{ player.email }}</td>
				  <td><img src="{{ site.sitePath }}/habbo-imaging/avatarimage?figure={{ player.figure }}&size=s"></td>
                  <td>{{ player.mission }}</td>
                  <td>{{ player.credits }}</td>
                  <td>{{ player.pixels }}</td>
				  <td>{{ player.getReadableLastOnline() }}</td>
				  <td>{{ player.getReadableJoinDate() }}</td>
                </tr>
			   {% set num = num + 1 %}
			   {% endfor %}
              </tbody>
            </table>
          </div>
		{% endif %}
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