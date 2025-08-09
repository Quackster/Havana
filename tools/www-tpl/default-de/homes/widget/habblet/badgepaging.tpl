    <div id="badgelist-content">	
		<ul class="clearfix">
		{% for badge in badgeList %}
			<li style="background-image: url({{ site.staticContentPath }}/c_images/album1584/{{ badge.getBadgeCode() }}.gif)"></li>
		{% endfor %}
		</ul>
	

		<div id="badge-list-paging">
	{{ badgeList|length }} - {{ currentPage }} / {{ pages }}<br />
	{% if currentPage != 1 %}
    <a href="#" id="badge-list-search-first">Erste</a> |
    <a href="#" id="badge-list-search-previous">&lt;&lt;</a> |
	{% else %}
	Erste | &lt;&lt; |
	{% endif %}
	
	{% if showLast %}
	<a href="#" id="badge-list-search-next">&gt;&gt;</a> |
    <a href="#" id="badge-list-search-last">Letzte</a>
	{% else %}
	&gt;&gt; | Letzte
	{% endif %}
	<input type="hidden" id="badgeListPageNumber" value="{{ currentPage }}" />
    <input type="hidden" id="badgeListTotalPages" value="{{ pages }}" />
		<script type="text/javascript">
	document.observe('dom:loaded', function() {
	window.badgesWidget{{ sticker.getId() }} = new BadgesWidget('{{ user.id }}', '{{ sticker.getId() }}');
	});
		</script>
		</div>
    </div>
		<div class="clear"></div>
