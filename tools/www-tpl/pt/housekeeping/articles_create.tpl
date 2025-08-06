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
      <h1 class="mt-4">Criar Notícia</h1>
		{% include "housekeeping/base/alert.tpl" %}
		<p>Crie uma notícia que, uma vez publicada, será imediatamente publicada no site.</p>
		<form class="table-responsive" method="post">
			<div class="form-group">
				<label>Título</label>
				<input type="text" class="form-control" name="title">
			</div>
			<div class="form-group">
				<label>Categoria <i>(segure CTRL para selecionar múltiplas categorias)</i></label><br />
					<select id="categories" name="categories[]" size="{{ categories|length }}" style="width: 100%" multiple>
					{% for category in categories %}
					<option value="{{ category.getIndex() }}">{{ category.getLabel() }}</option>
					{% endfor %}
					</select>
			</div>
			<div class="form-group">
				<label>Resumo</label>
				<input type="text" class="form-control" name="shortstory">
			</div>
			<div class="form-group">
				<label>Notícia completa</label>
				<p>
					<textarea name="fullstory" id="fullstory" class="form-control" rows="6" style="width: 100%;" onchange="previewChanges();" onkeypress="previewChanges();"></textarea>
				</p>
			</div>
			<div class="form-group">
				<label>Imagem</label>
				<p>
					<select onkeypress="previewTS(this.value);" onchange="previewTS(this.value);" name="topstory" id="topstory">
					{% for image in images %}<option value="{{ image }}"{% if image == randomImage %} selected{% endif %}>{{ image }}</option>{% endfor %}
					</select>
				</p>
			</div>
			<div class="form-group">
				<label>Sobreescrever Imagem</label>
				<input type="text" class="form-control" name="topstoryOverride" onchange="previewTSOverride(this.value);" onkeypress="previewTSOverride(this.value);" onkeydown="previewTSOverride(this.value);">
			</div>
			<div class="form-group">
				<label>Prévia da Imagem</label>
				<div id="ts-preview"><img src="{{ site.staticContentPath }}/c_images/Top_Story_Images/{{ randomImage }}" /></div>
			</div>
			
			<div class="form-group">
				<label>Imagem da notícia</label>
				<input type="text" class="form-control" name="articleimage">
			</div>
			<div class="form-group">
				<label>Marcar como publicado</label>
				<input type="checkbox" name="published" value="true"{% if article.isPublished() %} checked="checked"{% endif %}>
			</div>
			<div class="form-group">
				<label>Data de publicação</label>
				<p><i>(Não altere se quiser que seja a data/hora atual)</i></p>
				<input type="datetime-local" name="datePublished" max="3000-12-31" in="1000-01-01" class="form-control" value="{{ currentDate }}">
			</div>
			<div class="form-group">
				<label>Transmitir ao vivo/publicar nesta data (marque se esta notícia for definida no futuro)</label>
				<input type="checkbox" name="futurePublished" value="true"{% if article.isFuturePublished() %} checked="checked"{% endif %}></label>
			</div>
			<div class="form-group">
				<label>Sobreescrever autor</label>
				<input type="text" class="form-control" name="authorOverride" value="{{ article.authorOverride }}">
			</div>
			<div class="form-group"> 
				<button type="submit" class="btn btn-info">Criar Notícia</button>
			</div>
		</form>
    </div>
	<div style="margin-left:30px" class="col-3">
		<h1 class="mt-4">Criar Notícia</h1>
		<p id="news-preview"><i>Visualize a notícia aqui...</i></p>
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