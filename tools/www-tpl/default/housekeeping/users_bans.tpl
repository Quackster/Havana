{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set bansActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
     <h1 class="mt-4">{{ locale.housekeeping_users_view_and_manage_bans }}</h1>
		{% include "housekeeping/base/alert.tpl" %}
		<p>{{ locale.housekeeping_users_manage_all_currently_active_bans_on_the_hotel }}</p>
			<div style="margin:10px">
			{% if nextBans|length > 0 %}
				{% set ourNextPage = page + 1 %}
				<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/bans?page={{ ourNextPage }}&sort={{ sortBy }}"><button type="button" class="btn btn-info">{{ locale.housekeeping_users_next_page }}</button></a>
			{% endif %}
			{% if previousBans|length > 0 %}
				{% set ourNextPage = page - 1 %}
				<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/bans?page={{ ourNextPage }}&sort={{ sortBy }}"><button type="button" class="btn btn-warning">{{ locale.housekeeping_users_go_back }}</button></a>
			{% endif %}
			</div>
		  <div class="table-responsive">
		    <form method="post">
            <table class="table table-striped">
              <thead>
                <tr>
				  <th>{{ locale.housekeeping_users_type }}</th>
				  <th>{{ locale.housekeeping_users_value }}</th>
				  <th>{{ locale.housekeeping_users_message }}</th>
				  <th><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/bans?page={{ page }}&sort=banned_until">{{ locale.housekeeping_users_banned_util }}</a></th>
				  <th><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/bans?page={{ page }}&sort=banned_at">{{ locale.housekeeping_users_banned_at }}</a></th>
				  <th>{{ locale.housekeeping_users_banned_by }}</th>
                </tr>
              </thead>
              <tbody>
			      {% for ban in bans %}
				  <tr>
				  <td>
						{% if (ban.getBanType().name() == 'MACHINE_ID') %}
						{{ locale.housekeeping_users_machine }}
						{% endif %}
						{% if (ban.getBanType().name() == 'USER_ID') %}
						{{ locale.housekeeping_users_user }}
						{% endif %}
				  </td>
				  <td>
						{% if (ban.getBanType().name() == 'MACHINE_ID') %}
							{% set bannedName = ban.getName() %}
							{{ ban.getValue() }}
							{% if bannedName != "" %}
								&nbsp;-&nbsp;{{ bannedName }}	
							{% endif %}
						{% endif %}
						{% if (ban.getBanType().name() == 'USER_ID') %}
						{% set bannedName = ban.getName() %}
							{% if bannedName != "" %}
								{{ bannedName }}	
							{% endif %}
						{% endif %}
				  </td>
				  <td>
						{{ ban.getMessage() }}
				  </td>
				  <td>
						{{ ban.getBannedUtil() }}
				  </td>
				  <td>
						{{ ban.getBannedAt() }}
				  </td>
				  <td>
						{{ ban.getBannedBy() }}
				  </td>
				  </tr>
                  {% endfor %}
              </tbody>
            </table>
		</form>
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