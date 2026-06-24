{% autoescape 'html' %}
<div class="habblet" id="my-tags-list">
<ul class="tag-list make-clickable">

{% if tags|length > 0 %}
	{% for tag in tags %}
<li><a href="{{ site.sitePath }}/tag/{{ tag }}" class="tag" style="font-size: 10px;">{{ tag }}</a>
                        <a class="tag-remove-link" title="{{ locale.habblet_mytaglist_remove_tag }}" href="#"></a></li>
	{% endfor %}
{% endif %}							
</ul>

     <form method="post" action="{{ site.sitePath }}/myhabbo/tag/add" onsubmit="TagHelper.addFormTagToMe();return false;" >
    <div class="add-tag-form clearfix">
		<a class="new-button" href="#" id="add-tag-button" onclick="TagHelper.addFormTagToMe();return false;"><b>{{ locale.habblet_mytaglist_add_tag }}</b><i></i></a>
        <input type="text" id="add-tag-input" maxlength="20" style="float: right"/>
        <em class="tag-question">{{ tagRandomQuestion }}</em>
    </div>
    <div style="clear: both"></div> 
    </form>

</div>

<script type="text/javascript">
    TagHelper.setTexts({
        tagLimitText: "{{ locale.habblet_mytaglist_you_ve_reached_the_tag_limit_delete_one_of_your_tags_if_you_want_to_add_a_new_one|escape('js') }}",
        invalidTagText: "{{ locale.habblet_mytaglist_invalid_tag_the_tag_must_be_less_than_two_zero_characters_and_composed_only_of_alphanumeric_characters|escape('js') }}",
        buttonText: "{{ locale.habblet_mytaglist_ok|escape('js') }}"
    });
    TagHelper.bindEventsToTagLists();
</script>
{% endautoescape %}
