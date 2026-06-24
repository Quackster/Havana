{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set recyclerRewardsActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
    <h1 class="mt-4">{% if reward != null %}Edit{% else %}Create{% endif %} Recycler Reward</h1>
	{% include "housekeeping/base/alert.tpl" %}
	<form class="table-responsive col-md-5" method="post">
		<div class="form-group"><label>Sprite</label><input type="text" class="form-control" name="sprite" value="{% if reward != null %}{{ reward.sprite() }}{% endif %}"></div>
		<div class="form-group"><label>Order</label><input type="number" class="form-control" name="order_id" value="{% if reward != null %}{{ reward.orderId() }}{% else %}0{% endif %}"></div>
		<div class="form-group"><label>Chance</label><input type="number" class="form-control" name="chance" value="{% if reward != null %}{{ reward.chance() }}{% else %}5{% endif %}"></div>
		<button type="submit" class="btn btn-info">Save Reward</button>
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/recycler_rewards" class="btn btn-secondary">Back</a>
	</form>
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
