{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set createArticlesActive = " active " %}
	{% include "housekeeping/base/navigation.tpl" %}
	<script type="text/javascript">
	function previewTS(el) {
		document.getElementById('ts-preview').innerHTML = '<img src="{{ site.staticContentPath }}/c_images/Top_Story_Images/' + el + '" /><br />';
	}
	function previewTSOverride(el) {
		if (!el)
		{
			var element = document.getElementById("topstory");
			var val = element.options[element.selectedIndex].value;
			previewTS(val);
			return;
		}
		
		document.getElementById('ts-preview').innerHTML = '<img src="' + el + '" /><br />';
	}
	</script>
	<div class="row">
    <div class="col-4" width="50%">
      <h1 class="mt-4">Crear artículo</h1>
		{% include "housekeeping/base/alert.tpl" %}
		<p>Cree un artículo de noticias, una vez publicado, se realizará inmediatamente en el sitio.</p>
		<form class="table-responsive" method="post">
			<div class="form-group">
				<label>Title</label>
				<input type="text" class="form-control" name="title">
			</div>
			<div class="form-group">
				<label>Categoría <i>(Mantenga presionado CTRL para seleccionar múltiples)</i></label><br />
					<select id="categories" name="categories[]" size="{{ categories|length }}" style="width: 100%" multiple>
					{% for category in categories %}
					<option value="{{ category.getIndex() }}">{{ category.getLabel() }}</option>
					{% endfor %}
					</select>
			</div>
			<div class="form-group">
				<label>Cuento</label>
				<input type="text" class="form-control" name="shortstory">
			</div>
			<div class="form-group">
				<label>Historia completa</label>
				<p>
					<textarea name="fullstory" id="fullstory" class="form-control" rows="6" style="width: 100%;" onchange="previewChanges();" onkeypress="previewChanges();"></textarea>
				</p>
			</div>
			<div class="form-group">
				<label>Imagen</label>
				<p>
					<select onkeypress="previewTS(this.value);" onchange="previewTS(this.value);" name="topstory" id="topstory">
					{% for image in images %}<option value="{{ image }}"{% if image == randomImage %} selected{% endif %}>{{ image }}</option>{% endfor %}
					</select>
				</p>
			</div>
			<div class="form-group">
				<label>Anular la imagen</label>
				<input type="text" class="form-control" name="topstoryOverride" onchange="previewTSOverride(this.value);" onkeypress="previewTSOverride(this.value);" onkeydown="previewTSOverride(this.value);">
			</div>
			<div class="form-group">
				<label>Vista previa de imagen</label>
				<div id="ts-preview"><img src="{{ site.staticContentPath }}/c_images/Top_Story_Images/{{ randomImage }}" /></div>
			</div>
			
			<div class="form-group">
				<label>Imagen del artículo</label>
				<input type="text" class="form-control" name="articleimage">
			</div>
			<div class="form-group">
				<label>Mark como se publica</label>
				<input type="checkbox" name="published" value="true"{% if article.isPublished() %} checked="checked"{% endif %}>
			</div>
			<div class="form-group">
				<label>Fecha de publicación</label>
				<p><i>(Dejar solo para el artículo actual de publicar el artículo)</i></p>
				<input type="datetime-local" name="datePublished" max="3000-12-31" in="1000-01-01" class="form-control" value="{{ currentDate }}">
			</div>
			<div class="form-group">
				<label>Vaya en vivo/publique en esta fecha (marque si este artículo de noticias está establecido en el futuro)
				<input type="checkbox" name="futurePublished" value="true"{% if article.isFuturePublished() %} checked="checked"{% endif %}></label>
			</div>
			<div class="form-group">
				<label>Anular el autor</label>
				<input type="text" class="form-control" name="authorOverride" value="{{ article.authorOverride }}">
			</div>
			<div class="form-group"> 
				<button type="submit" class="btn btn-info">Crear artículo</button>
			</div>
		</form>
    </div>
	<div style="margin-left:30px" class="col-3">
		<h1 class="mt-4">Crear artículo</h1>
		<p id="news-preview"><i>Vista previa de noticias aquí...</i></p>
    </div>
  </div>
  <script src="https://code.jquery.com/jquery-3.1.1.slim.min.js"></script>
  <script src="https://blackrockdigital.github.io/startbootstrap-simple-sidebar/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
  <script>
    $("#menu-toggle").click(function(e) {
      e.preventDefault();
      $("#wrapper").toggleClass("toggled");
    });
	
	function previewChanges() {
		var previewNewsText = document.getElementById("fullstory").value;
		
		if (previewNewsText.length > 0) {
			postAjax('{{ site.sitePath }}/habblet/ajax/preview_news_article', { body: previewNewsText }, function(data) { 
				document.getElementById("news-preview").innerHTML = data;
			});
		} else {
			document.getElementById("news-preview").innerHTML = "<i>Preview news here...</i>";
		}
	}
	
	function postAjax(url, data, success) {
		var params = typeof data == 'string' ? data : Object.keys(data).map(
				function(k){ return encodeURIComponent(k) + '=' + encodeURIComponent(data[k]) }
			).join('&');

		var xhr = new XMLHttpRequest();
		xhr.open('POST', url);
		xhr.onreadystatechange = function() {
			if (xhr.readyState == XMLHttpRequest.DONE) { 
				success(xhr.responseText); 
			}
		};
		xhr.send(params);
		return xhr;
	}
	
  </script>
</body>
</html>

{% include "housekeeping/base/footer.tpl" %}