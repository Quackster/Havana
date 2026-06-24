<ul>
	<li>{{ locale.homes_editor_click_on_link_below_to_insert_it_into_the_document }}</li>

	{% autoescape 'html' %}
	{% for kvp in querySearch %}
		{% set key = kvp.getKey() %}
		{% set value = kvp.getValue() %}
							
    <li><a href="#" class="linktool-result" type="{{ type }}" 
    	value="{{ value }}" title="{{ key }}">{{ key }}</a></li>

	{% endfor %}
	{% endautoescape %}


</ul>