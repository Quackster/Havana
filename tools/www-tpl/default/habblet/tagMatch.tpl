{% autoescape 'html' %}
{% if errorMsg != "" %}
<div class="tag-match-error">
	{{ errorMsg }}
</div>
{% else %}
{{ locale.habblet_tagmatch_coming_soon }}
{% endif %}							
{% endautoescape %}
