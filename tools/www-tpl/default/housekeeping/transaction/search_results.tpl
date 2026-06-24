{% if transactions|length > 0 %}
		<h2>{{ locale.housekeeping_transaction_search_results }}</h2>
          <div class="table-responsive">
            <table class="table table-striped">
              <thead>
                <tr>
				  <th></th>
				  <th>{{ locale.housekeeping_transaction_item_id }}</th>
                  <th>{{ locale.housekeeping_transaction_description }}</th>
                  <th>{{ locale.housekeeping_transaction_coins }}</th>
				  <th>{{ locale.housekeeping_transaction_pixels }}</th>
				  <th>{{ locale.housekeeping_transaction_amount }}</th>
                  <th>{{ locale.housekeeping_transaction_created_at }}</th>
                </tr>
              </thead>
              <tbody>
			    {% set num = 1 %}
				{% for transaction in transactions %}
                <tr>
                  <td><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/transaction/track_item?id={{ transaction.getItemId() }}">{{ locale.housekeeping_transaction_track_this_item }}</a></td>
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