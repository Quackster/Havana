{% include "housekeeping/base/header.tpl" %}
  <body>
	{% set dashboardActive = " active " %}
	{% include "housekeeping/base/navigation.tpl" %}
     <h1 class="mt-4">Hotel Statistik</h1>
		  <p>Willkommen beim Housekeeping für das {{ site.siteName }} Hotel. Hier kannst du viele Dinge gleichzeitig verwalten, z. B. Benutzer, Neuigkeiten, Seiten Inhalte und die Statistiken des Hotels anzeigen.</p>
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
					<td><strong>Havana Web Version</strong></td>
					<td>1.0</td>
				</tr>
				<tr>
					<td>Benutzer</td>
					<td>{{ stats.userCount }}</td>
				</tr>
				<tr>
					<td>Raum-Gegenstände</td>
					<td>{{ stats.roomItemCount }}</td>
				</tr>
				<tr>
					<td>Inventar-Gegenstände</td>
					<td>{{ stats.inventoryItemsCount }}</td>
				</tr>
				<tr>
					<td>Gruppen</td>
					<td>{{ stats.groupCount }}</td>
				</tr>
				<tr>
					<td>Haustiere</td>
					<td>{{ stats.petCount }}</td>
				</tr>
				<tr>
					<td>Galerie</td>
					<td>{{ stats.photoCount }}</td>
				</tr>
			</tbody>
			</table>
		  </div>
          <h2>Neuster Benutzer</h2>
		  <p>Die Liste der kürzlich beigetretenen Spieler wird unten angezeigt.</p>
		  <div style="margin:10px">
			{% set zeroCoinsValue = '' %}
			{% if zeroCoinsFlag %}
				{% set zeroCoinsValue = '&zerocoins' %}
			{% endif %}
			
			{% if nextPlayers|length > 0 %}
				{% set ourNextPage = page + 1 %}
				<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}?page={{ ourNextPage }}{{ zeroCoinsValue }}&sort={{ sortBy }}"><button type="button" class="btn btn-info">Vor</button></a>
			{% endif %}
			{% if previousPlayers|length > 0 %}
				{% set ourNextPage = page - 1 %}
				<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}?page={{ ourNextPage }}{{ zeroCoinsValue }}&sort={{ sortBy }}"><button type="button" class="btn btn-warning">Zurück</button></a>
			{% endif %}
			
			{% if zeroCoinsFlag %}
				<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}"><button type="button" class="btn btn-warning">Zeige Benutzer mit Münzen</button></a>
			{% else %}
				<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}?zerocoins"><button type="button" class="btn btn-warning">Zeige Benutzer ohne Münzen</button></a>
			{% endif %}
			</div>
          <div class="table-responsive">
            <table class="table table-striped">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Name</th>
				  <th>Email</th>
				  <th>Aussehen</th>
        <th>Status</th>
        <th>Münzen</th>
        <th>Pixel</th>
        <th><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}?page={{ page }}{{ zeroCoinsValue }}&sort=last_online">Zuletzt Online:</a></th>
        <th><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}?page={{ page }}{{ zeroCoinsValue }}&sort=created_at">Erstellt am:</a></th>
				  <th></th>
                </tr>
              </thead>
              <tbody>
			    {% set num = 1 %}
				{% for player in players %}
                <tr>
                  <td>{{ player.id }}</td>
                  <td><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/users/edit?id={{ player.id }}">{{ player.name }}</a> - <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/transaction/lookup?searchQuery={{ player.getName() }}">Transaktionsverlauf</a></td>
				  <td>{{ player.email }}</td>
				  <td><img src="{{ site.sitePath }}/habbo-imaging/avatarimage?figure={{ player.figure }}&size=s"></td>
									{% autoescape 'html' %}
                  <td>{{ player.motto }}</td>
				 					{% endautoescape %}
                  <td>{{ player.credits }}</td>
                  <td>{{ player.pixels }}</td>
				  <td>{{ player.formatLastOnline("dd-MM-yyyy HH:mm:ss") }}</td>
				  <td>{{ player.formatJoinDate("dd-MM-yyyy HH:mm:ss") }}</td>
				  <td><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/api/ban?username={{ player.name }}"><button type="button" class="btn btn-success">PERMANENT BANN</button></a></td>
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