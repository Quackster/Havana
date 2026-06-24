{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set searchUsersActive = " active " %}
	{% include "housekeeping/base/navigation.tpl" %}
     <h1 class="mt-4">{{ locale.housekeeping_transaction_transaction_item_lookup }}</h1>
		{% include "housekeeping/base/alert.tpl" %}
		<br>
		{% include "housekeeping/transaction/search_results.tpl" %}
      </div>

{% include "housekeeping/base/footer.tpl" %}