{% autoescape 'html' %}
<div class="habblet" id="my-tags-list">
<ul class="tag-list make-clickable">

{% if tags|length > 0 %}
	{% for tag in tags %}
<li><a href="{{ site.sitePath }}/tag/{{ tag }}" class="tag" style="font-size: 10px;">{{ tag }}</a>
                        <a class="tag-remove-link" title="Enttaggen" href="#"></a></li>
	{% endfor %}
{% endif %}							
</ul>

    <form method="post" action="{{ site.sitePath }}/myhabbo/tag/add" onsubmit="TagHelper.addFormTagToMe();return false;" >
    <div class="add-tag-form clearfix">
		<a class="new-button" href="#" id="add-tag-button" onclick="TagHelper.addFormTagToMe();return false;"><b>Taggen</b><i></i></a>
        <input type="text" id="add-tag-input" maxlength="20" style="float: right"/>
        <em class="tag-question">{{ tagRandomQuestion }}</em>
    </div>
    <div style="clear: both"></div> 
    </form>

</div>

<script type="text/javascript">
    TagHelper.setTexts({
        tagLimitText: "Du hast das Limit erreicht, lösche eines um ein neues hinzufügen zu können.",
        invalidTagText: "Ungültiger Tag. Das Tag muss weniger als 20 Zeichen lang sein und darf nur aus alphanumerischen Zeichen bestehen.",
        buttonText: "OK"
    });
    TagHelper.bindEventsToTagLists();
</script>
{% endautoescape %}
