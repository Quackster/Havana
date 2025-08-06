{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set articlesActive = " active " %}
	{% include "housekeeping/base/navigation.tpl" %}
    <h1 class="mt-4">Artículos publicados</h1>
		{% include "housekeeping/base/alert.tpl" %}
		<p>Esto incluye los artículos más recientes publicados en el sitio, puede editarlos o eliminarlos si lo desea.</p>
		<p><a href="/{{ site.housekeepingPath }}/articles/create" class="btn btn-danger">Nuevo artículo de noticias</a></p>			
          <div class="table-responsive">
            <table class="table table-striped">
              <thead>
                <tr>
				  <th>Name</th>
				  <th>Author</th>
				  <th>Short Story</th>
				  <th>Date</th>
				  <th>Views</th>
				  <th></th>
                </tr>
              </thead>
              <tbody>
				{% for article in articles %}
                <tr>
				  <td>{{ article.title }}</td>
				  <td>{{ article.author }}</td>
				  <td>{{ article.shortstory }}</td>
				  <td>{{ article.getDate() }}</td>
				  <td>{{ article.views }}</td>
				  <td>
				  	<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/articles/edit?id={{ article.id }}" class="btn btn-primary">Editar</a>
				  	<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/articles/delete?id={{ article.id }}" class="btn btn-danger">Delete</a>
				  </td>
                </tr>
			   {% endfor %}
              </tbody>
            </table>
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