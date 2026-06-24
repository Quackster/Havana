{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set recyclerRewardsActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
    <h1 class="mt-4">{{ locale.housekeeping_recycler_recycler_rewards }}</h1>
	{% include "housekeeping/base/alert.tpl" %}
	<p><a href="/{{ site.housekeepingPath }}/recycler_rewards/edit" class="btn btn-danger">{{ locale.housekeeping_recycler_new_reward }}</a></p>
	<div class="table-responsive">
		<table class="table table-striped">
			<thead><tr><th>{{ locale.housekeeping_recycler_sprite }}</th><th>{{ locale.housekeeping_recycler_order }}</th><th>{{ locale.housekeeping_recycler_chance }}</th><th></th></tr></thead>
			<tbody>
			{% for reward in rewards %}
				<tr><td>{{ reward.sprite() }}</td><td>{{ reward.orderId() }}</td><td>{{ reward.chance() }}</td><td><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/recycler_rewards/edit?sprite={{ reward.sprite() }}" class="btn btn-primary">{{ locale.housekeeping_recycler_edit }}</a> <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/recycler_rewards/delete?sprite={{ reward.sprite() }}" class="btn btn-danger">{{ locale.housekeeping_recycler_delete }}</a></td></tr>
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
{% include "housekeeping/base/footer.tpl" %}
