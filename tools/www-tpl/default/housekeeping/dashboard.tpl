{% include "housekeeping/base/header.tpl" %}
  <body>
	{% set dashboardActive = " active " %}
	{% include "housekeeping/base/navigation.tpl" %}
     <h1 class="mt-4">{{ locale.housekeeping_dashboard_hotel_statistics }}</h1>
		  <p>{{ locale.housekeeping_dashboard_welcome_to_the_housekeeping_for }} {{ site.siteName }} {{ locale.housekeeping_dashboard_hotel_here_you_can_manage_a_lot_of_things_at_once_such_as_users_news_site_content_and_view_the_statistics_of_the_hotel }}</p>
		   <div class="table-responsive col-md-4">
            <table class="table table-striped">
			<thead>
				<tr>
					<td></td>
					<td></td>
				</tr>
			</thead>
			<tbody class="col-md-4">
				<tr>
					<td><strong>{{ locale.housekeeping_dashboard_havana_web_version }}</strong></td>
					<td>1.0</td>
				</tr>
				<tr>
					<td>{{ locale.housekeeping_dashboard_users }}</td>
					<td>{{ stats.userCount }}</td>
				</tr>
				<tr>
					<td>{{ locale.housekeeping_dashboard_room_items }}</td>
					<td>{{ stats.roomItemCount }}</td>
				</tr>
				<tr>
					<td>{{ locale.housekeeping_dashboard_inventory_items }}</td>
					<td>{{ stats.inventoryItemsCount }}</td>
				</tr>
				<tr>
					<td>{{ locale.housekeeping_dashboard_groups }}</td>
					<td>{{ stats.groupCount }}</td>
				</tr>
				<tr>
					<td>{{ locale.housekeeping_dashboard_pets }}</td>
					<td>{{ stats.petCount }}</td>
				</tr>
				<tr>
					<td>{{ locale.housekeeping_dashboard_photos }}</td>
					<td>{{ stats.photoCount }}</td>
				</tr>
			</tbody>
			</table>
		  </div>
          <h2>{{ locale.housekeeping_dashboard_newest_players }}</h2>
		  <p>{{ locale.housekeeping_dashboard_the_recently_joined_player_list_is_seen_below }}</p>
		  <div style="margin:10px">
			{% set zeroCoinsValue = '' %}
			{% if zeroCoinsFlag %}
				{% set zeroCoinsValue = '&zerocoins' %}
			{% endif %}
			
			{% if nextPlayers|length > 0 %}
				{% set ourNextPage = page + 1 %}
				<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}?page={{ ourNextPage }}{{ zeroCoinsValue }}&sort={{ sortBy }}"><button type="button" class="btn btn-info">{{ locale.housekeeping_dashboard_next_page }}</button></a>
			{% endif %}
			{% if previousPlayers|length > 0 %}
				{% set ourNextPage = page - 1 %}
				<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}?page={{ ourNextPage }}{{ zeroCoinsValue }}&sort={{ sortBy }}"><button type="button" class="btn btn-warning">{{ locale.housekeeping_dashboard_go_back }}</button></a>
			{% endif %}
			
			{% if zeroCoinsFlag %}
				<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}"><button type="button" class="btn btn-warning">{{ locale.housekeeping_dashboard_view_players_with_coins }}</button></a>
			{% else %}
				<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}?zerocoins"><button type="button" class="btn btn-warning">{{ locale.housekeeping_dashboard_view_players_without_coins }}</button></a>
			{% endif %}
			</div>
          <div class="table-responsive">
            <table class="table table-striped">
              <thead>
                <tr>
                  <th>{{ locale.housekeeping_dashboard_id }}</th>
                  <th>{{ locale.housekeeping_dashboard_name }}</th>
				  <th>{{ locale.housekeeping_dashboard_email }}</th>
				  <th>{{ locale.housekeeping_dashboard_look }}</th>
        <th>{{ locale.housekeeping_dashboard_motto }}</th>
        <th>{{ locale.housekeeping_dashboard_credits }}</th>
        <th>{{ locale.housekeeping_dashboard_pixels }}</th>
        <th><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}?page={{ page }}{{ zeroCoinsValue }}&sort=last_online">{{ locale.housekeeping_dashboard_last_online }}</a></th>
        <th><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}?page={{ page }}{{ zeroCoinsValue }}&sort=created_at">{{ locale.housekeeping_dashboard_date_joined }}</a></th>
				  <th></th>
                </tr>
              </thead>
              <tbody>
			    {% set num = 1 %}
				{% for player in players %}
                <tr>
                  <td>{{ player.id }}</td>
                  <td><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/users/edit?id={{ player.id }}">{{ player.name }}</a> - <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/transaction/lookup?searchQuery={{ player.getName() }}">{{ locale.housekeeping_dashboard_transactions }}</a></td>
				  <td>{{ player.email }}</td>
				  <td><img src="{{ site.sitePath }}/habbo-imaging/avatarimage?figure={{ player.figure }}&size=s"></td>
									{% autoescape 'html' %}
                  <td>{{ player.motto }}</td>
				 					{% endautoescape %}
                  <td>{{ player.credits }}</td>
                  <td>{{ player.pixels }}</td>
				  <td>{{ player.formatLastOnline("dd-MM-yyyy HH:mm:ss") }}</td>
				  <td>{{ player.formatJoinDate("dd-MM-yyyy HH:mm:ss") }}</td>
				  <td><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/api/ban?username={{ player.name }}"><button type="button" class="btn btn-success">{{ locale.housekeeping_dashboard_permanently_ban_user }}</button></a></td>
                </tr>
			   {% set num = num + 1 %}
			   {% endfor %}
              </tbody>
            </table>
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