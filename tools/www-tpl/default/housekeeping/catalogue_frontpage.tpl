{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set editCatalogueFrontPage = " active " %}
	{% include "housekeeping/base/navigation.tpl" %}
	<script type="text/javascript">
	function previewTS(el) {
		document.getElementById('ts-preview').innerHTML = '{{ locale.housekeeping_catalogue_img_src|escape('js') }}{{ site.staticContentPath }}/c_images/Top_Story_Images/' + el + '{{ locale.housekeeping_catalogue_br|escape('js') }}';
	}
	</script>
      <h1 class="mt-4">{{ locale.housekeeping_catalogue_edit_catalogue_page }}</h1>
		{% include "housekeeping/base/alert.tpl" %}
		<p>{{ locale.housekeeping_catalogue_edit_the_catalogue_front_page_data }}</p>
		<form class="{{ locale.housekeeping_catalogue_table_responsive_col_md_four|escape('js') }}" method="post">
			<div class="form-group">
				<label>{{ locale.housekeeping_catalogue_header }}</label>
				<input type="text" class="form-control" name="header" value="{{ frontpageText2 }}">
			</div>
			<div class="form-group">
				<label>{{ locale.housekeeping_catalogue_sub_text }}</label>
				<input type="text" class="form-control" name="subtext" value="{{ frontpageText3 }}">
			</div>
			<div class="form-group">
				<label>{{ locale.housekeeping_catalogue_web_link_optional }}</label>
				<input type="text" class="form-control" name="link" value="{{ frontpageText4 }}">
			</div>
			<div class="form-group">
				<label>{{ locale.housekeeping_catalogue_image }}</label>
				<p>
					<select onkeypress="previewTS(this.value);" onchange="previewTS(this.value);" name="image" id="image">
					{% for image in images %}<option value="{{ image }}"{% if image == frontpageText1 %} selected{% endif %}>{{ image }}</option>{% endfor %}
					</select>
				</p>
			</div>
			<div class="form-group">
				<label>{{ locale.housekeeping_catalogue_image_preview }}</label>
				<div id="ts-preview"><img src="{{ site.staticContentPath }}/c_images/Top_Story_Images/{{ frontpageText1 }}" /></div>
			</div>
			
			<div class="form-group"> 
				<button type="submit" class="{{ locale.housekeeping_catalogue_btn_btn_info|escape('js') }}">{{ locale.housekeeping_catalogue_save_frontpage }}</button>
			</div>
		</form>
{% include "housekeeping/base/footer.tpl" %}
