{% include "housekeeping/base/header.tpl" %}
  <body>
	{% include "housekeeping/base/navigation.tpl" %}
		<h1 class="mt-4">Editar encuesta de infobus</h1>
		{% include "housekeeping/base/alert.tpl" %}
		<p>VResultados de la encuesta de Iew Infobus</p>
	
		{% if noAnswers %}
		<p>Todavía no hay respuestas a esta encuesta</p>
		{% else %}
		<p><img src="{{ imageData }}"></p>
		{% endif %}
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