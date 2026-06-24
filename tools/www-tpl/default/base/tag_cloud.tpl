{% autoescape 'html' %}
{% if tagCloud|length > 0 %}
<ul class="tag-list">
{% for kvp in tagCloud %}
{% set tag = kvp.getKey() %}
{% set size = kvp.getValue() %}
<li><a href="{{ site.sitePath }}/tag/{{ tag }}" class="tag" style="font-size:{{ size }}px">{{ tag }}</a> </li>
{% endfor %}
</ul>
{% else %}
{{ locale.base_tag_cloud_no_tags_to_display }}
{% endif %}
{% endautoescape %}