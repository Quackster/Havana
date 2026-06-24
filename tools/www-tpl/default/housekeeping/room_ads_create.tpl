{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set roomCreateAdsActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
     <h1 class="mt-4">{{ locale.housekeeping_room_create_ad }}</h1>
		{% include "housekeeping/base/alert.tpl" %}
		<p>{{ locale.housekeeping_room_create_a_room_ad_that_will_display_as_a_billboards_from_within_the_hotel }}</p>
		<form class="table-responsive col-md-4" method="post">
			<div class="form-group">
				<label>{{ locale.housekeeping_room_room_id }}</label>
				<input type="text" class="form-control" name="roomid">
			</div>
			<div class="form-group">
				<label>{{ locale.housekeeping_room_url }}</label>
				<input type="text" class="form-control" name="url">
			</div>
			<div class="form-group">
				<label>{{ locale.housekeeping_room_image }}</label>
				<input type="text" class="form-control" name="image">
			</div>
			<div class="form-group">
				<label>{{ locale.housekeeping_room_enabled }}</label>
				<input type="checkbox" name="enabled" checked />
			</div>
			<div class="form-group">
				<label>{{ locale.housekeeping_room_room_loading_intermission_ad }}</label>
				<input type="checkbox" name="loading-ad"/>
			</div>
			<div class="form-group"> 
				<button type="submit" class="btn btn-info">{{ locale.housekeeping_room_create_ad }}</button>
			</div>
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