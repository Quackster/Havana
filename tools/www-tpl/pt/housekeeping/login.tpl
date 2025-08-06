<!DOCTYPE html>
<html lang="pt-br">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../favicon.ico">
    <title>{{ site.siteName }}: Gestor de site</title>
    <link href="{{ site.staticContentPath }}/public/hk/css/bootstrap.min.css" rel="stylesheet">
	<link href="{{ site.staticContentPath }}/public/hk/css/bootstrap.login.override.css" rel="stylesheet">
	<link href="{{ site.staticContentPath }}/public/hk/css/sticky-footer.css" rel="stylesheet">
  </head>
  <body>
	
	<div class="container">
		<div class="mt-1">
			<h1>Gestor de site</h1>
		</div>
		{% include "housekeeping/base/alert.tpl" %}
		<div class="login-image">
			<p style="margin-left:0">Por favor entre para utilizar o gestor de site</p>
			<form class="form-signin" action="/{{ site.housekeepingPath }}/login" method="post">
				<label for="inputUsername" class="sr-only">Nome</label>
				<input type="text" name="hkusername" id="inputUsername" class="form-control" placeholder="Nome" required autofocus>
				<label for="inputPassword" class="sr-only">Senha</label>
				<input type="password" name="hkpassword" id="inputPassword" class="form-control" placeholder="Senha" required>
				<button class="btn btn-lg btn-primary btn-block" type="submit">Entrar</button>
				<div class="checkbox">
					<label>
						<!--<input type="checkbox" value="remember-me"> Remember me-->
						<p style="font-size; 5px">Oops, <a href="{{ site.sitePath }}">leve-me de volta!</a></p>
					</label>
				</div>
			</form>
		</div>
	</div>
    <footer class="footer">
      <div class="container">
        <span class="text-muted">&copy; Copyright 2018 - Alex Miller</span>
      </div>
    </footer>
    <script src="{{ site.staticContentPath }}/public/hk/js/ie10-viewport-bug-workaround.js"></script>
  </body>
</html>
