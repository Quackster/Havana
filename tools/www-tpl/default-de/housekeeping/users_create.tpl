{% include "housekeeping/base/header.tpl" %}
<body>
    {% set createUserActive = " active " %}
	{% include "housekeeping/base/navigation.tpl" %}
        <h1 class="mt-4">Benutzer erstellen</h1>
			{% include "housekeeping/base/alert.tpl" %}
				<p>Gib die Details ein um ein neuen Benutzer erstellen zu können.</p>
			<form class="table-responsive col-md-4" method="post">
			<div class="form-group">
				<label>Benutzername:</label>
				<input type="text" class="form-control" id="text" placeholder="Enter username" name="username">
			</div>
			<div class="form-group">
				<label>Passwort:</label>
				<input type="password" class="form-control" placeholder="Enter password" name="password">
			</div>
			<div class="form-group">
				<label>Passwort bestätigen:</label>
				<input type="password" class="form-control" placeholder="Enter password" name="confirmpassword">
			</div>
			<div class="form-group">
				<label for="pwd">Email:</label>
				<input type="email" class="form-control" placeholder="Enter email" name="email">
			</div>
			<div class="form-group">
				<label for="pwd">Figur/Aussehen:</label>
				<input type="text" class="form-control" name="figure" value="{{ defaultFigure }}">
			</div>
			<div class="form-group">
				<label for="pwd">Status:</label>
				<input type="text" class="form-control" placeholder="Enter mission" name="mission" value="{{ defaultMission }}">
			</div>
			<div class="form-group"> 
					<button type="submit" class="btn btn-info">Speichern</button>
			</div>
		</form>

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