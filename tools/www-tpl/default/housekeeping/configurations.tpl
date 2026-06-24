{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set configurationsActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
     <h1 class="mt-4">{{ locale.housekeeping_configurations_edit_configurations }}</h1>
		{% include "housekeeping/base/alert.tpl" %}
		<p>{{ locale.housekeeping_configurations_edit_all_the_configuration_that_is_on_the_hotel }}</p>
          <div class="table-responsive">
		    <form method="post">
            <table class="table table-striped">
              <thead>
                <tr>
				  <th>{{ locale.housekeeping_configurations_name }}</th>
				  <th>{{ locale.housekeeping_configurations_value }}</th>
				  <th></th>
                </tr>
              </thead>
              <tbody>
				{% for config in configs %}
                <tr>
				  <td>{{ config.getKey() }}</td>
				  <td>
						<input type="text" name="{{ config.getKey() }}" class="form-control" id="searchFor" value="{{ config.getValue() }}">
				  </td>
                  </tr>
			   {% endfor %}
              </tbody>
            </table>
			<div class="form-group"> 
				<button type="submit" class="btn btn-info">{{ locale.housekeeping_configurations_save_configuration }}</button>
			</div>
		</form>
      </div>
{% include "housekeeping/base/footer.tpl" %}
