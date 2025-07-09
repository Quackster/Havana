<ul>
	<li>Clique no link abaixo para inseri-lo no documento</li>

	{% for kvp in querySearch %}
		{% set key = kvp.getKey() %}
		{% set value = kvp.getValue() %}
							
    <li><a href="#" class="linktool-result" type="{{ type }}" 
    	value="{{ value }}" title="{{ key }}">{{ key }}</a></li>

	{% endfor %}


</ul>