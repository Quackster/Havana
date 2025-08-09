{% include "housekeeping/base/header.tpl" %}
  <body>
	{% include "housekeeping/base/navigation.tpl" %}
     <h1 class="mt-4">Benutzer bearbeiten</h1>
		{% include "housekeeping/base/alert.tpl" %}
		<p>Hier kannst du Benutzerdetails bearbeiten.</p>
		{% autoescape 'html' %}
		<form class="table-responsive col-md-4" method="post">
			<div class="form-group">
				<label>Benutzername:</label>
				<input type="text" class="form-control" id="text" name="username"  value="{{ playerUsername }}">
			</div>
			<div class="form-group">
				<label for="pwd">Email:</label>
				<input type="email" class="form-control" name="email" value="{{ playerEmail }}">
			</div>
			<div class="form-group">
				<label for="pwd">Aussehen:</label>
				<input type="text" class="form-control" name="figure" value="{{ playerFigure }}">
			</div>
			<div class="form-group">
				<label for="pwd">Status:</label>
				<input type="text" class="form-control" name="motto" value="{{ playerMotto }}">
			</div>
			<div class="form-group">
				<label for="pwd">Münzen:</label>
				<input type="text" class="form-control" name="credits" value="{{ playerCredits }}">
			</div>
			<div class="form-group">
				<label for="pwd">Pixel:</label>
				<input type="text" class="form-control" name="pixels" value="{{ playerPixels }}">
			</div>
			<div class="form-group"> 
				<input type="hidden" id="text" name="id" value="{{ playerId }}">
				<button type="submit" class="btn btn-info">SPEICHERN</button>
			</div>
		</form>
		{% endautoescape %}
      </div>
    </div>
  </div>
  <script src="https://code.jquery.com/jquery-3.1.1.slim.min.js"></script>
  <script src="https://blackrockdigital.github.io/startbootstrap-simple-sidebar/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
  <script>
    $("#menu-toggle").click(function(e) {
      e.preventDefault();
      $("#wrapper").toggleClass("toggled");
    });
  </script>
</body>
</html>

{% include "housekeeping/base/footer.tpl" %}