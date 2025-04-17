{% include "housekeeping/base/header.tpl" %}
  <body>
	{% set dashboardActive = " active " %}
	{% include "housekeeping/base/navigation.tpl" %}
     <h1 class="mt-4">Estadísticas de hotel</h1>
		  <p>Bienvenido al gerente del sitio web del hotel {{ site.siteName }}, aquí puede administrar muchas cosas a la vez, como usuarios, noticias, contenido del sitio y ver las estadísticas del hotel.</p>
		   <div class="table-responsive col-md-4">
            <table class="table table-striped">
			<thead>
				<tr>
					<td></td>
					<td></td>
				</tr>
			</thead>
			<tbody class="col-md-4">
				<tr>
					<td><strong>Versión de Habana Web</strong></td>
					<td>1.0</td>
				</tr>
				<tr>
					<td>Usuarios</td>
					<td>{{ stats.userCount }}</td>
				</tr>
				<tr>
					<td>Artículos para la habitación</td>
					<td>{{ stats.roomItemCount }}</td>
				</tr>
				<tr>
					<td>Artículos de inventario</td>
					<td>{{ stats.inventoryItemsCount }}</td>
				</tr>
				<tr>
					<td>Grupos</td>
					<td>{{ stats.groupCount }}</td>
				</tr>
				<tr>
					<td>Mascotas</td>
					<td>{{ stats.petCount }}</td>
				</tr>
				<tr>
					<td>Fotos</td>
					<td>{{ stats.photoCount }}</td>
				</tr>
			</tbody>
			</table>
		  </div>
          <h2>Jugadores más nuevos</h2>
		  <p>La lista de jugadores recientemente unidos se ve a continuación</p>
		  <div style="margin:10px">
			{% set zeroMonedasValue = '' %}
			{% if zeroMonedasFlag %}
				{% set zeroMonedasValue = '&zerocoins' %}
			{% endif %}
			
			{% if nextPlayers|length > 0 %}
				{% set ourNextPage = page + 1 %}
				<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}?page={{ ourNextPage }}{{ zeroMonedasValue }}&sort={{ sortBy }}"><button type="button" class="btn btn-info">Siguiente página</button></a>
			{% endif %}
			{% if previousPlayers|length > 0 %}
				{% set ourNextPage = page - 1 %}
				<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}?page={{ ourNextPage }}{{ zeroMonedasValue }}&sort={{ sortBy }}"><button type="button" class="btn btn-warning">Regresa</button></a>
			{% endif %}
			
			{% if zeroMonedasFlag %}
				<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}"><button type="button" class="btn btn-warning">Ver jugadores con monedas</button></a>
			{% else %}
				<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}?zerocoins"><button type="button" class="btn btn-warning">Ver jugadores sin monedas</button></a>
			{% endif %}
			</div>
          <div class="table-responsive">
            <table class="table table-striped">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Nombre</th>
				  <th>Correo electrónico</th>
				  <th>Imagen</th>
        <th>Lema</th>
        <th>Monedas</th>
        <th>Píxeles</th>
        <th><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}?page={{ page }}{{ zeroMonedasValue }}&sort=last_online">Último en línea</a></th>
        <th><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}?page={{ page }}{{ zeroMonedasValue }}&sort=created_at">Fecha de Registro</a></th>
				  <th></th>
                </tr>
              </thead>
              <tbody>
			    {% set num = 1 %}
				{% for player in players %}
                <tr>
                  <td>{{ player.id }}</td>
                  <td><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/users/edit?id={{ player.id }}">{{ player.name }}</a> - <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/transaction/lookup?searchQuery={{ player.getName() }}">Actas</a></td>
				  <td>{{ player.email }}</td>
				  <td><img src="{{ site.sitePath }}/habbo-imaging/avatarimage?figure={{ player.figure }}&size=s"></td>
                  <td>{{ player.motto }}</td>
                  <td>{{ player.credits }}</td>
                  <td>{{ player.pixels }}</td>
				  <td>{{ player.formatLastOnline("dd-MM-yyyy HH:mm:ss") }}</td>
				  <td>{{ player.formatJoinDate("dd-MM-yyyy HH:mm:ss") }}</td>
				  <td><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/api/ban?username={{ player.name }}"><button type="button" class="btn btn-success">Prohibir permanentemente al usuario</button></a></td>
                </tr>
			   {% set num = num + 1 %}
			   {% endfor %}
              </tbody>
            </table>
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