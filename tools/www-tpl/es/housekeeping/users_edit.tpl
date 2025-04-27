{% include "housekeeping/base/header.tpl" %}
  <body>
	{% include "housekeeping/base/navigation.tpl" %}
     <h1 class="mt-4">Editar usuario</h1>
		{% include "housekeeping/base/alert.tpl" %}
		<p>Aquí puede editar los detalles del usuario.</p>
		<form class="table-responsive col-md-4" method="post">
			<div class="form-group">
				<label>Nombre de usuario:</label>
				<input type="text" class="form-control" id="text" name="username"  value="{{ playerUsername }}">
			</div>
			<div class="form-group">
				<label for="pwd">Correo electrónico:</label>
				<input type="email" class="form-control" name="email" value="{{ playerEmail }}">
			</div>
			<div class="form-group">
				<label for="pwd">figura:</label>
				<input type="text" class="form-control" name="figure" value="{{ playerFigure }}">
			</div>
			<div class="form-group">
				<label for="pwd">Lema:</label>
				<input type="text" class="form-control" name="motto" value="{{ playerMotto }}">
			</div>
			<div class="form-group">
				<label for="pwd">Créditos:</label>
				<input type="text" class="form-control" name="credits" value="{{ playerCredits }}">
			</div>
			<div class="form-group">
				<label for="pwd">Píxeles:</label>
				<input type="text" class="form-control" name="pixels" value="{{ playerPixels }}">
			</div>
			<div class="form-group"> 
				<input type="hidden" id="text" name="id" value="{{ playerId }}">
				<button type="submit" class="btn btn-info">Guardar detalles</button>
			</div>
		</form>
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