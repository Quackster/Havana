{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set roomCreateBadgesActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
     <h1 class="mt-4">{{ locale.housekeeping_room_create_room_badge }}</h1>
		{% include "housekeeping/base/alert.tpl" %}
		<p>{{ locale.housekeeping_room_create_a_room_entry_badge_that_will_be_given_to_the_user_as_soon_as_they_enter_the_room }}</p>
		<form class="table-responsive col-md-4" method="post">
			<div class="form-group">
				<label>{{ locale.housekeeping_room_room_id }}</label>
				<input type="text" class="form-control" name="roomid">
			</div>
			<div class="form-group">
				<label>{{ locale.housekeeping_room_badge_code }}</label>
				<input type="text" class="form-control" name="badgecode">
			</div>
			<div class="form-group"> 
				<button type="submit" class="btn btn-info">{{ locale.housekeeping_room_create_entry_badge }}</button>
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