{% include "housekeeping/base/header.tpl" %}
<body>
    {% set roomCreateBadgesActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
        <h1 class="mt-4">Raumabzeichen erstellen</h1>
			{% include "housekeeping/base/alert.tpl" %}
			<p>Erstelle ein Raumabzeichen, dass dem Benutzer bekommt, sobald er den Raum betritt.</p>
			<form class="table-responsive col-md-4" method="post">
			<div class="form-group">
				<label>Raum ID</label>
				<input type="text" class="form-control" name="roomid">
			</div>
			<div class="form-group">
				<label>Badge Code</label>
				<input type="text" class="form-control" name="badgecode">
			</div>
			<div class="form-group"> 
				<button type="submit" class="btn btn-info">Erstelle Badge</button>
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