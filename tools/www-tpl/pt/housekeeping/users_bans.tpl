{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set bansActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
     <h1 class="mt-4">Gestor de banimentos</h1>
		{% include "housekeeping/base/alert.tpl" %}
		<p>Gerencie todos os banimentos ativos do hotel</p>
			<div style="margin:10px">
			{% if nextBans|length > 0 %}
				{% set ourNextPage = page + 1 %}
				<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/bans?page={{ ourNextPage }}&sort={{ sortBy }}"><button type="button" class="btn btn-info">Próxima página</button></a>
			{% endif %}
			{% if previousBans|length > 0 %}
				{% set ourNextPage = page - 1 %}
				<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/bans?page={{ ourNextPage }}&sort={{ sortBy }}"><button type="button" class="btn btn-warning">Voltar</button></a>
			{% endif %}
			</div>
		  <div class="table-responsive">
		    <form method="post">
            <table class="table table-striped">
              <thead>
                <tr>
				  <th>Tipo</th>
				  <th>Valor</th>
				  <th>Mensagem</th>
				  <th><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/bans?page={{ page }}&sort=banned_until">Banido até</a></th>
				  <th><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/bans?page={{ page }}&sort=banned_at">Banido as</a></th>
				  <th>Banido por</th>
                </tr>
              </thead>
              <tbody>
			      {% for ban in bans %}
				  <tr>
				  <td>
						{% if (ban.getBanType().name() == 'MACHINE_ID') %}
						Machine
						{% endif %}
						{% if (ban.getBanType().name() == 'USER_ID') %}
						User
						{% endif %}
				  </td>
				  <td>
						{% if (ban.getBanType().name() == 'MACHINE_ID') %}
							{% set bannedName = ban.getName() %}
							{{ ban.getValue() }}
							{% if bannedName != "" %}
								&nbsp;-&nbsp;{{ bannedName }}	
							{% endif %}
						{% endif %}
						{% if (ban.getBanType().name() == 'USER_ID') %}
						{% set bannedName = ban.getName() %}
							{% if bannedName != "" %}
								{{ bannedName }}	
							{% endif %}
						{% endif %}
				  </td>
				  <td>
						{{ ban.getMessage() }}
				  </td>
				  <td>
						{{ ban.getBannedUtil() }}
				  </td>
				  <td>
						{{ ban.getBannedAt() }}
				  </td>
				  <td>
						{{ ban.getBannedBy() }}
				  </td>
				  </tr>
                  {% endfor %}
              </tbody>
            </table>
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