{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set roomAdsActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
     <h1 class="mt-4">Anuncios de sala de edición</h1>
		{% include "housekeeping/base/alert.tpl" %}
		<p>Edite todos los anuncios de la habitación que se muestran como vallas publicitarias desde el hotel.</p>
		<p><a href="/{{ site.housekeepingPath }}/room_ads/create?id={{ advertisement.getId() }}" class="btn btn-danger">Nuevo anuncio</a></p>			
          <div class="table-responsive">
		    <form method="post">
            <table class="table table-striped">
              <thead>
                <tr>
				  <th>Está cargando anuncio</th>
				  <th>ID de habitación</th>
				  <th>Url</th>
				  <th>Imagen</th>
				  <th>Activada</th>
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
						<a href="/{{ site.housekeepingPath }}/room_ads/delete?id={{ advertisement.getId() }}" class="btn btn-danger">Borrar</a>
				  </td>
                  </tr>
			   {% endfor %}
              </tbody>
            </table>
			<div class="form-group"> 
				<button type="submit" class="btn btn-info">Guardar anuncios</button>
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