{% if transactions|length > 0 %}
		<h2>Suchergebnis</h2>
          <div class="table-responsive">
            <table class="table table-striped">
              <thead>
                <tr>
				  <th></th>
				  <th>Gegenstands-ID</th>
                  <th>Beschreibung</th>
                  <th>Münzen</th>
				  <th>Pixel</th>
				  <th>Menge</th>
                  <th>Erstellt am:</th>
                </tr>
              </thead>
              <tbody>
			    {% set num = 1 %}
				{% for transaction in transactions %}
                <tr>
                  <td><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/transaction/track_item?id={{ transaction.getItemId() }}">Tracken</a></td>
				  <td>{{ transaction.getItemId() }}</td>
				  <td>{{ transaction.description }}</td>
                  <td>{{ transaction.costCoins }}</td>
                  <td>{{ transaction.costPixels }}</td>
				  <td>{{ transaction.amount }}</td>
				  <td>{{ transaction.getFormattedDate() }}</td>
                </tr>
			    {% set num = num + 1 %}
			    {% endfor %}
              </tbody>
            </table>
          </div>
		{% endif %}