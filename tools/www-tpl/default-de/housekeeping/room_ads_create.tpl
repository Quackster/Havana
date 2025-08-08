{% include "housekeeping/base/header.tpl" %}
<body>
    {% set roomCreateAdsActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
        <h1 class="mt-4">Anzeige erstellen</h1>
		{% include "housekeeping/base/alert.tpl" %}
			<p>Erstelle eine Anzeige, die als Werbung im Hotel angezeigt wird.</p>
		<form class="table-responsive col-md-4" method="post">
			<div class="form-group">
				<label>Raum ID</label>
				<input type="text" class="form-control" name="roomid">
			</div>
			<div class="form-group">
				<label>URL</label>
				<input type="text" class="form-control" name="url">
			</div>
			<div class="form-group">
				<label>Bild</label>
				<input type="text" class="form-control" name="image">
			</div>
			<div class="form-group">
				<label>Aktiviert</label>
				<input type="checkbox" name="enabled" checked />
			</div>
			<div class="form-group">
				<label>Raumanzeige/Ladeanzeige</label>
				<input type="checkbox" name="loading-ad"/>
			</div>
			<div class="form-group"> 
				<button type="submit" class="btn btn-info">Anzeige erstellen</button>
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