{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set roomBadgesActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
     <h1 class="mt-4">{{ locale.housekeeping_room_edit_room_badges }}</h1>
		{% include "housekeeping/base/alert.tpl" %}
		<p>{{ locale.housekeeping_room_edit_all_the_room_badges_that_are_given_when_entering_the_room }}</p>
		<p><a href="/{{ site.housekeepingPath }}/room_badges/create?id={{ id }}" class="btn btn-danger">{{ locale.housekeeping_room_new_badge }}</a></p>			
          <div class="table-responsive">
		    <form method="post">
            <table class="table table-striped">
              <thead>
                <tr>
				  <th>{{ locale.housekeeping_room_room_id }}</th>
				  <th>{{ locale.housekeeping_room_badge_code }}</th>
				  <th>{{ locale.housekeeping_room_preview }}</th>
				  <th>{{ locale.housekeeping_room_room_name }}</th>
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
						<a href="/{{ site.housekeepingPath }}/room_badges/delete?id={{ id }}" class="btn btn-danger">{{ locale.housekeeping_room_delete }}</a>
				  </td>
                  </tr>
				  {% endfor %}
			   {% endfor %}
              </tbody>
            </table>
			<div class="form-group"> 
				<button type="submit" class="btn btn-info">{{ locale.housekeeping_room_save_badges }}</button>
			</div>
		</form>
      </div>

{% include "housekeeping/base/footer.tpl" %}