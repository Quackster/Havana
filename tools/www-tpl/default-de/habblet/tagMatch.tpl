{% autoescape 'html' %}
{% if errorMsg != "" %}
<div class="tag-match-error">
	{{ errorMsg }}
</div>
{% else %}
Bald verfügbar.
{% endif %}							
{% endautoescape %}
