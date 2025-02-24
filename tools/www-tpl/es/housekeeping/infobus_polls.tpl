{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set infobusPollsActive = " active " %}
	{% include "housekeeping/base/navigation.tpl" %}
     <h1 class="mt-4">Encuestas de infobus</h1>
		{% include "housekeeping/base/alert.tpl" %}
		<p>Esto enumera todas las encuestas de Infobus que se han creado y usado.</p>
		<p><a href="/{{ site.housekeepingPath }}/infobus_polls/create" class="btn btn-danger">Nueva encuesta</a></p>
		<p>
			<a href="/{{ site.housekeepingPath }}/infobus_polls/door_status?status=1" class="btn btn-info">Puertas de infobus abiertas</a>
		</p>
		<p>
			<a href="/{{ site.housekeepingPath }}/infobus_polls/door_status?status=0" class="btn btn-info">Cerrar la puerta de Infobuss</a>
		</p>
		<p>
			<a href="/{{ site.housekeepingPath }}/infobus_polls/close_event" class="btn btn-warning">Evento final</a>
		</p>			
          <div class="table-responsive">
            <table class="table table-striped">
              <thead>
                <tr>
				  <th></th>
				  <th>Pregunta</th>
				  <th>Creado por</th>
				  <th>Creado en</th>
				  <th></th>
				  <th></th>
                </tr>
              </thead>
              <tbody>
				{% for infobusPoll in infobusPolls %}
                <tr>
				  <td><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/infobus_polls/view_results?id={{ infobusPoll.id }}" class="btn btn-success">Ver resultados</a></td>
				  <td>{{ infobusPoll.getPollData().getQuestion() }}</td>
				  <td>{{ infobusPoll.getCreator() }}</td>
				  <td>{{ infobusPoll.getCreatedAtFormatted() }}</td>
				  <td>
				  	<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/infobus_polls/edit?id={{ infobusPoll.id }}" class="btn btn-primary">Editar</a>
				  	<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/infobus_polls/delete?id={{ infobusPoll.id }}" class="btn btn-danger">Borrar</a>
					<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/infobus_polls/clear_results?id={{ infobusPoll.id }}" class="btn btn-warning">Resultados claros</a>
				  </td>
				  <td>
					<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/infobus_polls/send_poll?id={{ infobusPoll.id }}" class="btn btn-info">Enviar encuesta</a>
				  </td>
                </tr>
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