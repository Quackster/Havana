{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set createArticlesActive = " active " %}
	{% include "housekeeping/base/navigation.tpl" %}
	<script type="text/javascript"><script src="{{ site.staticContentPath }}/public/hk/js/jquery-3.1.1.slim.min.js">
	function previewTS(el) {
		document.getElementById('ts-preview').innerHTML = '{{ locale.housekeeping_articles_img_src|escape('js') }}{{ site.staticContentPath }}/c_images/Top_Story_Images/' + el + '{{ locale.housekeeping_articles_br|escape('js') }}';
	}
	function previewTSOverride(el) {
		if (!el)
		{
			var element = document.getElementById("topstory");
			var val = element.options[element.selectedIndex].value;
			previewTS(val);
			return;
		}
		
		document.getElementById('ts-preview').innerHTML = '{{ locale.housekeeping_articles_img_src|escape('js') }}' + el + '{{ locale.housekeeping_articles_br|escape('js') }}';
	}
	</script>
	<div class="row">
    <div class="col-4" width="50%">
      <h1 class="mt-4">{{ locale.housekeeping_articles_create_article }}</h1>
		{% include "housekeeping/base/alert.tpl" %}
		<p>{{ locale.housekeeping_articles_create_a_news_article_once_posted_it_will_be_immediately_live_on_the_site }}</p>
		<form class="table-responsive" method="post">
			<div class="form-group">
				<label>{{ locale.housekeeping_articles_title }}</label>
				<input type="text" class="form-control" name="title">
			</div>
			<div class="form-group">
				<label>{{ locale.housekeeping_articles_category }} <i>{{ locale.housekeeping_articles_hold_down_ctrl_to_select_multiple }}</i></label><br />
					<select id="categories" name="categories[]" size="{{ categories|length }}" style="{{ locale.housekeeping_articles_width_one_zero_zero|escape('js') }}" multiple>
					{% for category in categories %}
					<option value="{{ category.getIndex() }}">{{ category.getLabel() }}</option>
					{% endfor %}
					</select>
			</div>
			<div class="form-group">
				<label>{{ locale.housekeeping_articles_short_story_text }}</label>
				<input type="text" class="form-control" name="shortstory">
			</div>
			<div class="form-group">
				<label>{{ locale.housekeeping_articles_full_story }}</label>
				<p>
					<textarea name="fullstory" id="fullstory" class="form-control" rows="6" style="{{ locale.housekeeping_articles_width_one_zero_zero_text|escape('js') }}" onchange="previewChanges();" onkeypress="previewChanges();"></textarea>
				</p>
			</div>
			<div class="form-group">
				<label>{{ locale.housekeeping_articles_image }}</label>
				<p>
					<select onkeypress="previewTS(this.value);" onchange="previewTS(this.value);" name="topstory" id="topstory">
					{% for image in images %}<option value="{{ image }}"{% if image == randomImage %} selected{% endif %}>{{ image }}</option>{% endfor %}
					</select>
				</p>
			</div>
			<div class="form-group">
				<label>{{ locale.housekeeping_articles_override_image }}</label>
				<input type="text" class="form-control" name="topstoryOverride" onchange="previewTSOverride(this.value);" onkeypress="previewTSOverride(this.value);" onkeydown="previewTSOverride(this.value);">
			</div>
			<div class="form-group">
				<label>{{ locale.housekeeping_articles_image_preview }}</label>
				<div id="ts-preview"><img src="{{ site.staticContentPath }}/c_images/Top_Story_Images/{{ randomImage }}" /></div>
			</div>
			
			<div class="form-group">
				<label>{{ locale.housekeeping_articles_article_image }}</label>
				<input type="text" class="form-control" name="articleimage">
			</div>
			<div class="form-group">
				<label>{{ locale.housekeeping_articles_mark_as_published }}</label>
				<input type="checkbox" name="published" value="true"{% if article.isPublished() %} checked="checked"{% endif %}>
			</div>
			<div class="form-group">
				<label>{{ locale.housekeeping_articles_publish_date }}</label>
				<p><i>{{ locale.housekeeping_articles_leave_alone_for_current_article_publish_time }}</i></p>
				<input type="datetime-local" name="datePublished" max="3000-12-31" in="1000-01-01" class="form-control" value="{{ currentDate }}">
			</div>
			<div class="form-group">
				<label>{{ locale.housekeeping_articles_go_live_publish_at_this_date_tick_if_this_news_article_is_set_in_future }} 
				<input type="checkbox" name="futurePublished" value="true"{% if article.isFuturePublished() %} checked="checked"{% endif %}></label>
			</div>
			<div class="form-group">
				<label>{{ locale.housekeeping_articles_override_author }}</label>
				<input type="text" class="form-control" name="authorOverride" value="{{ article.authorOverride }}">
			</div>
			<div class="form-group"> 
				<button type="submit" class="{{ locale.housekeeping_articles_btn_btn_info|escape('js') }}">{{ locale.housekeeping_articles_create_article }}</button>
			</div>
		</form>
    </div>
	<div style="margin-left:30px" class="col-3">
		<h1 class="mt-4">{{ locale.housekeeping_articles_create_article }}</h1>
		<p id="news-preview"><i>{{ locale.housekeeping_articles_preview_news_here }}</i></p>
    </div>
  </div>
  </script>
  <script src="{{ site.staticContentPath }}/public/hk/js/bootstrap.bundle.min.js"></script>
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
			document.getElementById("news-preview").innerHTML = "{{ locale.housekeeping_articles_i_preview_news_here_i|escape('js') }}";
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