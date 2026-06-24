{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set roomAdsActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
     <h1 class="mt-4">{{ locale.housekeeping_room_edit_room_ads }}</h1>
		{% include "housekeeping/base/alert.tpl" %}
		<p>{{ locale.housekeeping_room_edit_all_the_room_ads_that_display_as_billboards_from_within_the_hotel }}</p>
		<p><a href="/{{ site.housekeepingPath }}/room_ads/create?id={{ advertisement.getId() }}" class="btn btn-danger">{{ locale.housekeeping_room_new_ad }}</a></p>			
          <div class="table-responsive">
		    <form method="post">
            <table class="table table-striped">
              <thead>
                <tr>
				  <th>{{ locale.housekeeping_room_is_loading_ad }}</th>
				  <th>{{ locale.housekeeping_room_room_id }}</th>
				  <th>{{ locale.housekeeping_room_url }}</th>
				  <th>{{ locale.housekeeping_room_image }}</th>
				  <th>{{ locale.housekeeping_room_enabled }}</th>
				  <th></th>
                </tr>
              </thead>
              <tbody>
				{% for advertisement in roomAds %}
				<input type="hidden" name="roomad-id-{{ advertisement.getId() }}" value="{{ advertisement.getId() }}">
                  <tr>
				  <td width="100px">
						<input type="checkbox" name="roomad-{{ advertisement.getId() }}-loading-ad" {% if advertisement.isLoadingAd() %}checked{% endif %}/>
				  </td>
				  <td width="100px">
						<input type="text" name="roomad-{{ advertisement.getId() }}-roomid" class="form-control" id="searchFor" value="{{ advertisement.getRoomId() }}">
				  </td>
				  <td>
						<input type="text" name="roomad-{{ advertisement.getId() }}-url" class="form-control" id="searchFor" value="{{ advertisement.getUrl() }}">
				  </td>
				  <td>
						<input type="text" name="roomad-{{ advertisement.getId() }}-image" class="form-control" id="searchFor" value="{{ advertisement.getImage() }}">
				  </td>
				  <td width="100px">
						<input type="checkbox" name="roomad-{{ advertisement.getId() }}-enabled" {% if advertisement.isEnabled() %}checked{% endif %}/>
				  </td>
				  <td width="100px">
						<a href="/{{ site.housekeepingPath }}/room_ads/delete?id={{ advertisement.getId() }}" class="btn btn-danger">{{ locale.housekeeping_room_delete }}</a>
				  </td>
                  </tr>
			   {% endfor %}
              </tbody>
            </table>
			<div class="form-group"> 
				<button type="submit" class="btn btn-info">{{ locale.housekeeping_room_save_ads }}</button>
			</div>
		</form>
      </div>

{% include "housekeeping/base/footer.tpl" %}