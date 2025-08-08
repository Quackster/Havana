{% include "housekeeping/base/header.tpl" %}
<body>
    {% set roomBadgesActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
        <h1 class="mt-4">Raumabzeichen bearbeiten</h1>
			{% include "housekeeping/base/alert.tpl" %}
			<p>Bearbeite alle Raumabzeichen, die beim Betreten des Raums vergeben werden.</p>
			<p><a href="/{{ site.housekeepingPath }}/room_badges/create?id={{ id }}" class="btn btn-danger">Neues Abzeichen</a></p>			
            	<div class="table-responsive">
		    <form method="post">
            <table class="table table-striped">
                <thead>
                <tr>
				    <th>Raum ID</th>
				    <th>Badge Code</th>
				    <th>Vorschau</th>
				    <th>Raum Name</th>
				    <th></th>
                </tr>
                </thead>
                <tbody>
			    {% for badgeData in roomBadges.entrySet() %}
				{% for badge in badgeData.getValue() %}
				{% set id = (badgeData.getKey()) + ('_') +  (badge) %}
				
				<input type="hidden" name="roombadge-id-{{ id }}" value="{{ id }}">
                <tr>
				    <td width="100px">
						<input type="text" name="roomad-{{ id }}-roomid" class="form-control" id="searchFor" value="{{badgeData.getKey()  }}">
				    </td>
				    <td>
						<input type="text" name="roomad-{{ id }}-badge" class="form-control" id="searchFor" value="{{ badge }}">
				    </td>
				    <td>
						<img src="{{ site.staticContentPath }}/c_images/album1584/{{ badge }}.gif">
				    </td>				  
				    <td>
						<p>{{ util.getRoomName(badgeData.getKey()) }}</p>
				    </td>
				    <td>
						<a href="/{{ site.housekeepingPath }}/room_badges/delete?id={{ id }}" class="btn btn-danger">Löschen</a>
				    </td>
                    </tr>
				    	{% endfor %}
					{% endfor %}
                </tbody>
            </table>
			<div class="form-group"> 
				<button type="submit" class="btn btn-info">Abzeichen speichern</button>
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