{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set createUserActive = " active " %}
	{% include "housekeeping/base/navigation.tpl" %}
     <h1 class="mt-4">{{ locale.housekeeping_users_create_user }}</h1>
		{% include "housekeeping/base/alert.tpl" %}
		<p>{{ locale.housekeeping_users_enter_the_details_to_create_a_new_user }}</p>
		<form class="table-responsive col-md-4" method="post">
			<div class="form-group">
				<label>{{ locale.housekeeping_users_username_text }}</label>
				<input type="text" class="form-control" id="text" placeholder="{{ locale.housekeeping_users_enter_username }}" name="username">
			</div>
			<div class="form-group">
				<label>{{ locale.housekeeping_users_password }}</label>
				<input type="password" class="form-control" placeholder="{{ locale.housekeeping_users_enter_password }}" name="password">
			</div>
			<div class="form-group">
				<label>{{ locale.housekeeping_users_confirm_password }}</label>
				<input type="password" class="form-control" placeholder="{{ locale.housekeeping_users_enter_password }}" name="confirmpassword">
			</div>
			<div class="form-group">
				<label for="pwd">{{ locale.housekeeping_users_email_text }}</label>
				<input type="email" class="form-control" placeholder="{{ locale.housekeeping_users_enter_email }}" name="email">
			</div>
			<div class="form-group">
				<label for="pwd">{{ locale.housekeeping_users_look_figure }}</label>
				<input type="text" class="form-control" name="figure" value="{{ defaultFigure }}">
			</div>
			<div class="form-group">
				<label for="pwd">{{ locale.housekeeping_users_mission_text }}</label>
				<input type="text" class="form-control" placeholder="{{ locale.housekeeping_users_enter_mission }}" name="mission" value="{{ defaultMission }}">
			</div>
			<div class="form-group"> 
					<button type="submit" class="btn btn-info">{{ locale.housekeeping_users_submit }}</button>
			</div>
		</form>
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