{% include "housekeeping/base/header.tpl" %}
<body>
    {% set searchUsersActive = " active " %}
	{% include "housekeeping/base/navigation.tpl" %}
        <h1 class="mt-4">Search Users</h1>
		{% include "housekeeping/base/alert.tpl" %}
		<p>Hier kannst du Benutzer nach den von dir angeforderten Eingaben suchen</p>
		<form class="table-responsive col-md-4" method="post">
			<div class="form-group">
				<label for="field">Suche</label>
				<select name="searchField" class="form-control" id="field">
					<option value="username">Benutzername</option>
					<option value="id">ID</option>
					<option value="credits">Münzen</option>
					<option value="pixels">Pixel</option>
					<option value="mission">Status</option>
				</select>
			</div>
			<div class="form-group">
				<label for="field">Suchart</label>
				<select name="searchType" class="form-control" id="field">
					<option value="contains">Enthält</option>
					<option value="starts_with">Startet mit</option>
					<option value="ends_with">Endet mit</option>
					<option value="equals">Gleich</option>
				</select>
			</div>
			<div class="form-group">
				<label for="searchFor">Suchdatum</label>
				<input type="text" name="searchQuery" class="form-control" id="searchFor" placeholder="Looking for...">
			</div>
			<button type="submit" class="btn btn-primary">SUCHEN</button>
		</form>
		<br>
		{% if players|length > 0 %}
		<h2>Suchergebnisse</h2>
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
                    <th>Duckets</th>
				    <th>Zuletzt Online</th>
				    <th>Erstellungsdatum</th>
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
			    % endfor %}
                </tbody>
            </table>
            </div>
		{% endif %}

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