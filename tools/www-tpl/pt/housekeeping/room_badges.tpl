{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set roomBadgesActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
     <h1 class="mt-4">Editar Emblemas de Quartos</h1>
		{% include "housekeeping/base/alert.tpl" %}
		<p>Edite todos os emblemas dados ao entrar em um quarto.</p>
		<p><a href="/{{ site.housekeepingPath }}/room_badges/create?id={{ id }}" class="btn btn-danger">Novo emblema</a></p>			
          <div class="table-responsive">
		    <form method="post">
            <table class="table table-striped">
              <thead>
                <tr>
				  <th>ID do quarto</th>
				  <th>Código do emblema</th>
				  <th>Visualizar</th>
				  <th>Nome do Quarto</th>
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
						<a href="/{{ site.housekeepingPath }}/room_badges/delete?id={{ id }}" class="btn btn-danger">Delete</a>
				  </td>
                  </tr>
				  {% endfor %}
			   {% endfor %}
              </tbody>
            </table>
			<div class="form-group"> 
				<button type="submit" class="btn btn-info">Salvar Emblemas</button>
			</div>
		</form>
      </div>
    </div>
  </div>
  <script src="https://code.jquery.com/jquery-3.1.1.slim.min.js"></script>
  <script src="https://blackrockdigital.github.io/startbootstrap-simple-sidebar/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
  <script>
    $("#menu-toggle").click(function(e) {
      e.preventDefault();
      $("#wrapper").toggleClass("toggled");
    });
  </script>
</body>
</html>

{% include "housekeeping/base/footer.tpl" %}