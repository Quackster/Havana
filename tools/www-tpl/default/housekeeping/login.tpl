<!DOCTYPE html>
<html lang="{{ locale.global_html_lang }}">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../favicon.ico">
    <title>{{ site.siteName }}{{ locale.housekeeping_login_housekeeping }}</title>
    <link href="{{ site.staticContentPath }}/public/hk/css/bootstrap.min.css" rel="stylesheet">
	<link href="{{ site.staticContentPath }}/public/hk/css/bootstrap.login.override.css" rel="stylesheet">
	<link href="{{ site.staticContentPath }}/public/hk/css/sticky-footer.css" rel="stylesheet">
  </head>
  <body>
	
	<div class="container">
		<div class="mt-1">
			<h1>{{ locale.housekeeping_login_housekeeping_text }}</h1>
		</div>
		{% include "housekeeping/base/alert.tpl" %}
		<div class="login-image">
			<p style="margin-left:0">{{ locale.housekeeping_login_please_sign_in_to_access_the_housekeeping }}</p>
			<form class="form-signin" action="/{{ site.housekeepingPath }}/login" method="post">
				<label for="inputUsername" class="sr-only">{{ locale.housekeeping_login_username }}</label>
				<input type="text" name="hkusername" id="inputUsername" class="form-control" placeholder="{{ locale.housekeeping_login_username }}" required autofocus>
				<label for="inputPassword" class="sr-only">{{ locale.housekeeping_login_password }}</label>
				<input type="password" name="hkpassword" id="inputPassword" class="form-control" placeholder="{{ locale.housekeeping_login_password }}" required>
				<button class="btn btn-lg btn-primary btn-block" type="submit">{{ locale.housekeeping_login_sign_in }}</button>
				<div class="checkbox">
					<label>
						<!--<input type="checkbox" value="remember-me"> {{ locale.housekeeping_login_remember_me }}
						<p style="font-size; 5px">{{ locale.housekeeping_login_oops }} <a href="{{ site.sitePath }}">{{ locale.housekeeping_login_take_me_back_please }}</a></p>
					</label>
				</div>
			</form>
		</div>
	</div>
    <footer class="footer">
      <div class="container">
        <span class="text-muted">{{ locale.housekeeping_login_copy_copyright_two_zero_one_eight_alex_miller }}</span>
      </div>
    </footer>
    <script src="{{ site.staticContentPath }}/public/hk/js/ie10-viewport-bug-workaround.js"></script>
  </body>
</html>
