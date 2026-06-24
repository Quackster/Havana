{% include "housekeeping/base/header.tpl" %}
  <body>
	{% include "housekeeping/base/navigation.tpl" %}
     <h1 class="mt-4">{{ locale.housekeeping_users_edit_user }}</h1>
		{% include "housekeeping/base/alert.tpl" %}
		<p>{{ locale.housekeeping_users_here_you_can_edit_user_details }}</p>
		{% autoescape 'html' %}
		<form class="table-responsive col-md-4" method="post">
			<div class="form-group">
				<label>{{ locale.housekeeping_users_username_text }}</label>
				<input type="text" class="form-control" id="text" name="username"  value="{{ playerUsername }}">
			</div>
			<div class="form-group">
				<label for="pwd">{{ locale.housekeeping_users_email_text }}</label>
				<input type="email" class="form-control" name="email" value="{{ playerEmail }}">
			</div>
			<div class="form-group">
				<label for="pwd">{{ locale.housekeeping_users_look_figure }}</label>
				<input type="text" class="form-control" name="figure" value="{{ playerFigure }}">
			</div>
			<div class="form-group">
				<label for="pwd">{{ locale.housekeeping_users_motto }}</label>
				<input type="text" class="form-control" name="motto" value="{{ playerMotto }}">
			</div>
			<div class="form-group">
				<label for="pwd">{{ locale.housekeeping_users_credits_text }}</label>
				<input type="text" class="form-control" name="credits" value="{{ playerCredits }}">
			</div>
			<div class="form-group">
				<label for="pwd">{{ locale.housekeeping_users_pixels_text }}</label>
				<input type="text" class="form-control" name="pixels" value="{{ playerPixels }}">
			</div>
			<div class="form-group"> 
				<input type="hidden" id="text" name="id" value="{{ playerId }}">
				<button type="submit" class="btn btn-info">{{ locale.housekeeping_users_save_details }}</button>
			</div>
		</form>
		{% endautoescape %}
      </div>

{% include "housekeeping/base/footer.tpl" %}