{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set recyclerRewardsActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
    <h1 class="mt-4">{% if reward != null %}{{ locale.housekeeping_recycler_edit }}{% else %}{{ locale.housekeeping_recycler_create }}{% endif %} {{ locale.housekeeping_recycler_recycler_reward }}</h1>
	{% include "housekeeping/base/alert.tpl" %}
	<form class="table-responsive col-md-5" method="post">
		<div class="form-group"><label>{{ locale.housekeeping_recycler_sprite }}</label><input type="text" class="form-control" name="sprite" value="{% if reward != null %}{{ reward.sprite() }}{% endif %}"></div>
		<div class="form-group"><label>{{ locale.housekeeping_recycler_order }}</label><input type="number" class="form-control" name="order_id" value="{% if reward != null %}{{ reward.orderId() }}{% else %}0{% endif %}"></div>
		<div class="form-group"><label>{{ locale.housekeeping_recycler_chance }}</label><input type="number" class="form-control" name="chance" value="{% if reward != null %}{{ reward.chance() }}{% else %}5{% endif %}"></div>
		<button type="submit" class="btn btn-info">{{ locale.housekeeping_recycler_save_reward }}</button>
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/recycler_rewards" class="btn btn-secondary">{{ locale.housekeeping_recycler_back }}</a>
	</form>
{% include "housekeeping/base/footer.tpl" %}
