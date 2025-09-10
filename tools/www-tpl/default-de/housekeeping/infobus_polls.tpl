{% include "housekeeping/base/header.tpl" %}
<body>
    {% set infobusPollsActive = " active " %}
	{% include "housekeeping/base/navigation.tpl" %}
        <h1 class="mt-4">Infobus-Umfragen</h1>
			{% include "housekeeping/base/alert.tpl" %}
				<p>Hier werden alle erstellten und verwendeten Infobus-Umfragen aufgelistet.</p>
				<p><a href="/{{ site.housekeepingPath }}/infobus_polls/create" class="btn btn-danger">Neue Umfrage</a></p>
			<p>
				<a href="/{{ site.housekeepingPath }}/infobus_polls/door_status?status=1" class="btn btn-info">Infobustüren öffnen</a>
			</p>
			<p>
				<a href="/{{ site.housekeepingPath }}/infobus_polls/door_status?status=0" class="btn btn-info">Infobustüren schließen</a>
			</p>
			<p>
				<a href="/{{ site.housekeepingPath }}/infobus_polls/close_event" class="btn btn-warning">Event beenden</a>
		</p>			
            <div class="table-responsive">
            <table class="table table-striped">
                <thead>
                <tr>
				    <th></th>
				    <th>Frage</th>
				    <th>Erstellt von</th>
				    <th>Erstellt am</th>
				    <th></th>
				    <th></th>
                </tr>
                </thead>
                <tbody>
				{% for infobusPoll in infobusPolls %}
                <tr>
				    <td><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/infobus_polls/view_results?id={{ infobusPoll.id }}" class="btn btn-success">Ergebnisse anzeigen</a></td>
				    <td>{{ infobusPoll.getPollData().getQuestion() }}</td>
				    <td>{{ infobusPoll.getCreator() }}</td>
				    <td>{{ infobusPoll.getCreatedAtFormatted() }}</td>
				    <td>
						<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/infobus_polls/edit?id={{ infobusPoll.id }}" class="btn btn-primary">Bearbeiten</a>
						<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/infobus_polls/delete?id={{ infobusPoll.id }}" class="btn btn-danger">Löschen</a>
						<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/infobus_polls/clear_results?id={{ infobusPoll.id }}" class="btn btn-warning">Ergebnisse löschen</a>
				    </td>
				    <td>
						<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/infobus_polls/send_poll?id={{ infobusPoll.id }}" class="btn btn-info">Umfrage senden</a>
				    </td>
                </tr>
					{% endfor %}
                </tbody>
            </table>
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