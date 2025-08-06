{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set infobusPollsActive = " active " %}
	{% include "housekeeping/base/navigation.tpl" %}
     <h1 class="mt-4">Enquetes do Infobus</h1>
		{% include "housekeeping/base/alert.tpl" %}
		<p>Aqui você pode ver todas as enquetes criadas e criar uma nova.</p>
		<p><a href="/{{ site.housekeepingPath }}/infobus_polls/create" class="btn btn-danger">Nova enquete</a></p>
		<p>
			<a href="/{{ site.housekeepingPath }}/infobus_polls/door_status?status=1" class="btn btn-info">Abrir as portas do Infobus</a>
		</p>
		<p>
			<a href="/{{ site.housekeepingPath }}/infobus_polls/door_status?status=0" class="btn btn-info">Fechar as portas do Infobus</a>
		</p>
		<p>
			<a href="/{{ site.housekeepingPath }}/infobus_polls/close_event" class="btn btn-warning">Finalizar Evento</a>
		</p>			
          <div class="table-responsive">
            <table class="table table-striped">
              <thead>
                <tr>
				  <th></th>
				  <th>Pergunta</th>
				  <th>Criado por</th>
				  <th>Criado as</th>
				  <th></th>
				  <th></th>
                </tr>
              </thead>
              <tbody>
				{% for infobusPoll in infobusPolls %}
                <tr>
				  <td><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/infobus_polls/view_results?id={{ infobusPoll.id }}" class="btn btn-success">View Results</a></td>
				  <td>{{ infobusPoll.getPollData().getQuestion() }}</td>
				  <td>{{ infobusPoll.getCreator() }}</td>
				  <td>{{ infobusPoll.getCreatedAtFormatted() }}</td>
				  <td>
				  	<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/infobus_polls/edit?id={{ infobusPoll.id }}" class="btn btn-primary">Editar</a>
				  	<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/infobus_polls/delete?id={{ infobusPoll.id }}" class="btn btn-danger">Deletar</a>
					<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/infobus_polls/clear_results?id={{ infobusPoll.id }}" class="btn btn-warning">Apagar Resultados</a>
				  </td>
				  <td>
					<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/infobus_polls/send_poll?id={{ infobusPoll.id }}" class="btn btn-info">Enviar Enquete</a>
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