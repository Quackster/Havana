{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set searchTransactionsActive = " active " %}
	{% include "housekeeping/base/navigation.tpl" %}
     <h1 class="mt-4">{{ locale.housekeeping_transaction_transaction_lookup }}</h1>
		{% include "housekeeping/base/alert.tpl" %}
		<p>{{ locale.housekeeping_transaction_lookup_transaction_by_a_specific_user_either_enter_their_user_id_or_username_will_display_all_transaction_in_the_past_month }}</p>
		<form class="table-responsive col-md-4" method="post">
			<div class="form-group">
				<label for="searchQuery">{{ locale.housekeeping_transaction_player_name_or_id }}</label>
				<input type="text" name="searchQuery" class="form-control" id="searchQuery" placeholder="{{ locale.housekeeping_transaction_looking_for }}">
			</div>
			<button type="submit" class="btn btn-primary">{{ locale.housekeeping_transaction_perform_search }}</button>
		</form>
		<br>
		{% include "housekeeping/transaction/search_results.tpl" %}
      </div>
    </div>
  </div>
  <script src="{{ site.staticContentPath }}/public/hk/js/jquery-3.1.1.slim.min.js"></script>
  <script src="{{ site.staticContentPath }}/public/hk/js/bootstrap.bundle.min.js"></script>
  <script>
    $("#menu-toggle").click(function(e) {
      e.preventDefault();
      $("#wrapper").toggleClass("toggled");
    });
  </script>
</body>
</html>

{% include "housekeeping/base/footer.tpl" %}