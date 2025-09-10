{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set searchTransactionsActive = " active " %}
	{% include "housekeeping/base/navigation.tpl" %}
          <h1 class="mt-4">Transaktionsverlauf</h1>
		{% include "housekeeping/base/alert.tpl" %}
		<p>Suche nach Transaktionen eines bestimmten Benutzers. Gebe dazu entweder dessen Benutzer-ID oder Benutzernamen ein. Es werden alle Transaktionen des letzten Monats angezeigt.</p>
		<form class="table-responsive col-md-4" method="post">
			<div class="form-group">
				<label for="searchQuery">Benutzername oder ID:</label>
				<input type="text" name="searchQuery" class="form-control" id="searchQuery" placeholder="Suche nach...">
			</div>
			<button type="submit" class="btn btn-primary">Verlauf anzeigen</button>
		</form>
		<br>
		{% include "housekeeping/transaction/search_results.tpl" %}
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