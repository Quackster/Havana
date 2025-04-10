{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set searchUsersActive = " active " %}
	{% include "housekeeping/base/navigation.tpl" %}
     <h1 class="mt-4">Los usuarios de búsqueda</h1>
		{% include "housekeeping/base/alert.tpl" %}
		<p>Aquí puede buscar a los usuarios en el campo de su elección y la entrada solicitada por usted</p>
		<form class="table-responsive col-md-4" method="post">
			<div class="form-group">
				<label for="field">Field</label>
				<select name="searchField" class="form-control" id="field">
					<option value="username">Nombre de usuario</option>
					<option value="id">ID</option>
					<option value="credits">Monedas</option>
					<option value="pixels">Píxeles</option>
					<option value="mission">Misión</option>
				</select>
			</div>
			<div class="form-group">
				<label for="field">Tipo de búsqueda</label>
				<select name="searchType" class="form-control" id="field">
					<option value="contains">Contiene</option>
					<option value="starts_with">Comienza con</option>
					<option value="ends_with">Termina con</option>
					<option value="equals">Iguales</option>
				</select>
			</div>
			<div class="form-group">
				<label for="searchFor">Search data</label>
				<input type="text" name="searchQuery" class="form-control" id="searchFor" placeholder="Buscando...">
			</div>
			<button type="submit" class="btn btn-primary">Realizar la búsqueda</button>
		</form>
		<br>
		{% if players|length > 0 %}
		<h2>Resultados de la búsqueda</h2>
          <div class="table-responsive">
            <table class="table table-striped">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Nombre</th>
				  <th>Correo electrónico</th>
				  <th>Imagen</th>
                  <th>Mission</th>
                  <th>Monedas</th>
                  <th>Duckets</th>
				  <th>Último en línea</th>
				  <th>Fecha de Registro</th>
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