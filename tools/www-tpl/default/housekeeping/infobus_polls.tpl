{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set infobusPollsActive = " active " %}
	{% include "housekeeping/base/navigation.tpl" %}
     <h1 class="mt-4">{{ locale.housekeeping_infobus_infobus_polls }}</h1>
		{% include "housekeeping/base/alert.tpl" %}
		<p>{{ locale.housekeeping_infobus_this_lists_all_infobus_polls_that_have_been_created_and_used }}</p>
		<p><a href="/{{ site.housekeepingPath }}/infobus_polls/create" class="btn btn-danger">{{ locale.housekeeping_infobus_new_poll }}</a></p>
		<p>
			<a href="/{{ site.housekeepingPath }}/infobus_polls/door_status?status=1" class="btn btn-info">{{ locale.housekeeping_infobus_open_infobus_doors }}</a>
		</p>
		<p>
			<a href="/{{ site.housekeepingPath }}/infobus_polls/door_status?status=0" class="btn btn-info">{{ locale.housekeeping_infobus_close_infobus_doors }}</a>
		</p>
		<p>
			<a href="/{{ site.housekeepingPath }}/infobus_polls/close_event" class="btn btn-warning">{{ locale.housekeeping_infobus_end_event }}</a>
		</p>			
          <div class="table-responsive">
            <table class="table table-striped">
              <thead>
                <tr>
				  <th></th>
				  <th>{{ locale.housekeeping_infobus_question }}</th>
				  <th>{{ locale.housekeeping_infobus_created_by }}</th>
				  <th>{{ locale.housekeeping_infobus_created_at }}</th>
				  <th></th>
				  <th></th>
                </tr>
              </thead>
              <tbody>
				{% for infobusPoll in infobusPolls %}
                <tr>
				  <td><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/infobus_polls/view_results?id={{ infobusPoll.id }}" class="btn btn-success">{{ locale.housekeeping_infobus_view_results }}</a></td>
				  <td>{{ infobusPoll.getPollData().getQuestion() }}</td>
				  <td>{{ infobusPoll.getCreator() }}</td>
				  <td>{{ infobusPoll.getCreatedAtFormatted() }}</td>
				  <td>
				  	<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/infobus_polls/edit?id={{ infobusPoll.id }}" class="btn btn-primary">{{ locale.housekeeping_infobus_edit }}</a>
				  	<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/infobus_polls/delete?id={{ infobusPoll.id }}" class="btn btn-danger">{{ locale.housekeeping_infobus_delete }}</a>
					<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/infobus_polls/clear_results?id={{ infobusPoll.id }}" class="btn btn-warning">{{ locale.housekeeping_infobus_clear_results }}</a>
				  </td>
				  <td>
					<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/infobus_polls/send_poll?id={{ infobusPoll.id }}" class="btn btn-info">{{ locale.housekeeping_infobus_send_poll }}</a>
				  </td>
                </tr>
			   {% endfor %}
              </tbody>
            </table>
      </div>
{% include "housekeeping/base/footer.tpl" %}
