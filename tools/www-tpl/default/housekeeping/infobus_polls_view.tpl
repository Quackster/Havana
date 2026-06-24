{% include "housekeeping/base/header.tpl" %}
  <body>
	{% include "housekeeping/base/navigation.tpl" %}
		<h1 class="mt-4">{{ locale.housekeeping_infobus_edit_infobus_poll }}</h1>
		{% include "housekeeping/base/alert.tpl" %}
		<p>{{ locale.housekeeping_infobus_view_infobus_poll_results }}</p>
	
		{% if noAnswers %}
		<p>{{ locale.housekeeping_infobus_there_are_no_answers_to_this_poll_yet }}</p>
		{% else %}
		<p><img src="{{ imageData }}"></p>
		{% endif %}
    </div>
{% include "housekeeping/base/footer.tpl" %}
