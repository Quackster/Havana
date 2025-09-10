{% include "housekeeping/base/header.tpl" %}
<body>
	{% autoescape 'html' %}
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
        <h1 class="mt-4">Artikel bearbeiten</h1>
		{% include "housekeeping/base/alert.tpl" %}
		<p>Bearbeite einen vorhandenen Artikel, der bereits auf der Website veröffentlicht wurde.</p>
		<form class="table-responsive" method="post">
			<div class="form-group">
				<label>Titel</label>
				<input type="text" class="form-control" name="title" value="{{ article.title }}">
			</div>
			<div class="form-group">
				<label>Kategorie <i>(Halte die STRG-Taste gedrückt um mehrere auszuwählen)</i></label><br />
					<select id="categories" name="categories[]" size="{{ categories|length }}" style="width: 100%" multiple>
					{% for category in categories %}
					<option value="{{ category.getIndex() }}"{% if article.hasCategory(category.id) %} selected{% endif %}>{{ category.getLabel() }}</option>
					{% endfor %}
					</select>
			</div>
			<div class="form-group">
				<label>Kurze Story</label>
				<input type="text" class="form-control" name="shortstory" value="{{ article.shortstory }}">
			</div>
			<div class="form-group">
				<label>Ganze Story</label>
				<p>
					<textarea name="fullstory" id="fullstory" class="form-control" rows="6" style="width: 100%; white-space: pre-wrap" onchange="previewChanges();" onkeypress="previewChanges();">{{ article.getFullStory() }}</textarea>
				</p>
			</div>
			<div class="form-group">
				<label>Bild</label>
				<p>
					<select onkeypress="previewTS(this.value);" onchange="previewTS(this.value);" name="topstory" id="topstory">
					{% for image in images %}<option value="{{ image }}"{% if article.topstory == image %} selected{% endif %}>{{ image }}</option>{% endfor %}
					</select>
				</p>
			</div>
			<div class="form-group">
				<label>Bild überschreiben</label>
				<input type="text" class="form-control" name="topstoryOverride" onchange="previewTSOverride(this.value);" onkeypress="previewTSOverride(this.value);" onkeydown="previewTSOverride(this.value);"  value="{{ article.topstoryOverride }}">
			</div>
			<div class="form-group">
				<label>Bildvorschau</label>
				<div id="ts-preview"><img src="{{ article.getLiveTopStory() }}" /></div>
			</div>
			<div class="form-group">
				<label>Artikelbild</label>
				<input type="text" class="form-control" name="articleimage" value="{{ article.articleImage }}">
			</div>
			<div class="form-group">
				<label>Als veröffentlicht markieren</label>
				<input type="checkbox" name="published" value="true"{% if article.isPublished() %} checked="checked"{% endif %}>
			</div>
			<div class="form-group">
				<label>Veröffentlichungsdatum</label>
				<p><i>(Lasse es für den aktuellen Veröffentlichungszeitpunkt unverändert)</i></p>
				<input type="datetime-local" name="datePublished" max="3000-12-31" in="1000-01-01" class="form-control" value="{{ currentDate }}">
			</div>
			<div class="form-group">
				<label>An diesem Datum veröffentlichen: 
				<input type="checkbox" name="futurePublished" value="true"{% if article.isFuturePublished() %} checked="checked"{% endif %}></label>
			</div>
			<div class="form-group">
				<label>Autor überschreiben</label>
				<input type="text" class="form-control" name="authorOverride" value="{{ article.authorOverride }}">
			</div>
			<div class="form-group"> 
				<button type="submit" class="btn btn-info">Artikel speichern</button>
			</div>
		</form>
    </div>
	{% endautoescape %}
		<div style="margin-left:30px" class="col-3">
			<h1 class="mt-4">Artikelvorschau</h1>
		<p id="news-preview">{{ article.getEscapedStory() }}</p>
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
			document.getElementById("news-preview").innerHTML = "<i>Vorschau der Nachricht hier ...</i>";
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