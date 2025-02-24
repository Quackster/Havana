{% include "housekeeping/base/header.tpl" %}
  <body>
	{% set dashboardActive = " active " %}
	{% include "housekeeping/base/navigation.tpl" %}
     <h1 class="mt-4">Estatísticas do Hotel</h1>
		  <p>Bem vindo ao gerenciador de sites para {{ site.siteName }} Hotel, aqui você pode gerenciar muitas coisas ao mesmo tempo, como usuários, notícias, conteúdo do site e visualizar as estatísticas do hotel.</p>
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
					<td><strong>Versão do Havana Web</strong></td>
					<td>1.0</td>
				</tr>
				<tr>
					<td>Usuários</td>
					<td>{{ stats.userCount }}</td>
				</tr>
				<tr>
					<td>Itens de quarto</td>
					<td>{{ stats.roomItemCount }}</td>
				</tr>
				<tr>
					<td>Itens do Inventário</td>
					<td>{{ stats.inventoryItemsCount }}</td>
				</tr>
				<tr>
					<td>Grupos</td>
					<td>{{ stats.groupCount }}</td>
				</tr>
				<tr>
					<td>Pets</td>
					<td>{{ stats.petCount }}</td>
				</tr>
				<tr>
					<td>Fotos</td>
					<td>{{ stats.photoCount }}</td>
				</tr>
			</tbody>
			</table>
		  </div>
          <h2>Novos jogadores</h2>
		  <p>A lista de jogadores recém-inscritos é vista abaixo</p>
		  <div style="margin:10px">
			{% set zeroCoinsValue = '' %}
			{% if zeroCoinsFlag %}
				{% set zeroCoinsValue = '&zerocoins' %}
			{% endif %}
			
			{% if nextPlayers|length > 0 %}
				{% set ourNextPage = page + 1 %}
				<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}?page={{ ourNextPage }}{{ zeroCoinsValue }}&sort={{ sortBy }}"><button type="button" class="btn btn-info">Next Page</button></a>
			{% endif %}
			{% if previousPlayers|length > 0 %}
				{% set ourNextPage = page - 1 %}
				<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}?page={{ ourNextPage }}{{ zeroCoinsValue }}&sort={{ sortBy }}"><button type="button" class="btn btn-warning">Go back</button></a>
			{% endif %}
			
			{% if zeroCoinsFlag %}
				<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}"><button type="button" class="btn btn-warning">Ver usuários com moedas</button></a>
			{% else %}
				<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}?zerocoins"><button type="button" class="btn btn-warning">Ver usuários sem moedas</button></a>
			{% endif %}
			</div>
          <div class="table-responsive">
            <table class="table table-striped">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Nome</th>
				  <th>E-mail</th>
				  <th>Avatar</th>
        <th>Status</th>
        <th>Moedas</th>
        <th>Pixels</th>
        <th><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}?page={{ page }}{{ zeroCoinsValue }}&sort=last_online">Último login</a></th>
        <th><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}?page={{ page }}{{ zeroCoinsValue }}&sort=created_at">Registrado em</a></th>
				  <th></th>
                </tr>
              </thead>
              <tbody>
			    {% set num = 1 %}
				{% for player in players %}
                <tr>
                  <td>{{ player.id }}</td>
                  <td><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/users/edit?id={{ player.id }}">{{ player.name }}</a> - <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/transaction/lookup?searchQuery={{ player.getName() }}">Transações</a></td>
				  <td>{{ player.email }}</td>
				  <td><img src="{{ site.sitePath }}/habbo-imaging/avatarimage?figure={{ player.figure }}&size=s"></td>
                  <td>{{ player.motto }}</td>
                  <td>{{ player.credits }}</td>
                  <td>{{ player.pixels }}</td>
				  <td>{{ player.formatLastOnline("dd-MM-yyyy HH:mm:ss") }}</td>
				  <td>{{ player.formatJoinDate("dd-MM-yyyy HH:mm:ss") }}</td>
				  <td><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/api/ban?username={{ player.name }}"><button type="button" class="btn btn-success">Banir usuário permanentemente</button></a></td>
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