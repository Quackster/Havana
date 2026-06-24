{% set groupsList = sticker.getOwnerGroups() %}

<div class="movable widget GroupsWidget" id="widget-{{ sticker.getId() }}" style="left: {{ sticker.getX() }}px; top: {{ sticker.getY() }}px; z-index: {{ sticker.getZ() }}">
<div class="w_skin_{{ sticker.getSkin() }}">
	<div class="widget-corner" id="widget-{{ sticker.getId() }}-handle">
		<div class="widget-headline"><h3>
{% if editMode %}
<img src="{{ site.staticContentPath }}/web-gallery/images/myhabbo/icon_edit.gif" width="19" height="18" class="edit-button" id="widget-{{ sticker.getId() }}-edit" />
<script language="JavaScript" type="text/javascript">
Event.observe("{{ locale.homes_widget_widget|escape('js') }}{{ sticker.getId() }}{{ locale.homes_widget_edit|escape('js') }}", "click", function(e) { openEditMenu(e, {{ sticker.getId() }}, "widget", "{{ locale.homes_widget_widget|escape('js') }}{{ sticker.getId() }}{{ locale.homes_widget_edit|escape('js') }}"); }, false);
</script>
{% endif %}
		<span class="header-left">&nbsp;</span><span class="header-middle">{{ locale.homes_widget_my_groups }}{{ groupsList|length }})</span><span class="header-right">&nbsp;</span></h3>
		</div>	
	</div>
	<div class="widget-body">
		<div class="widget-content">

		{% if groupsList|length == 0 %}
		<div class="groups-list-none">
{{ locale.homes_widget_you_are_not_a_member_of_any_groups }}</div>
		{% else %}
<div class="groups-list-container">
<ul class="groups-list">

{% autoescape 'html' %}
{% for group in groupsList %}
<li title="{{ group.getName() }}" id="groups-list-{{ sticker.getId() }}-{{ group.getId() }}">
		<div class="groups-list-icon"><a href="{{ group.generateClickLink() }}"><img src="{{ site.sitePath }}/habbo-imaging/badge/{{ group.getBadge() }}.gif"/></a></div>
		<div class="groups-list-open"></div>
		<h4>
		<a href="{{ group.generateClickLink() }}">{{ group.getName() }}</a>
				</h4>
		<p>
		{{ locale.homes_widget_group_created }}<br /> 
		<b>{{ group.getCreatedDate() }}</b>
		{% set member = group.getMember(user.id) %}
		
		{% if member.isFavourite(group.id) %}
			<img src="{{ site.staticContentPath }}/web-gallery/images/groups/favourite_group_icon.gif" width="15" height="15" class="groups-list-icon" alt="{{ locale.homes_widget_favorite }}" title="{{ locale.homes_widget_favorite }}" />
		{% endif %}
		{% if member.getMemberRank().getRankId() == 3 %}
			<img src="{{ site.staticContentPath }}/web-gallery/images/groups/owner_icon.gif" width="15" height="15" class="groups-list-icon" alt="{{ locale.homes_widget_owner }}" title="{{ locale.homes_widget_owner }}" />
		{% endif %}
		{% if member.getMemberRank().getRankId() == 2 %}
			<img src="{{ site.staticContentPath }}/web-gallery/images/groups/administrator_icon.gif" width="15" height="15" class="groups-list-icon" alt="{{ locale.homes_widget_admin }}" title="{{ locale.homes_widget_admin }}" />
		{% endif %}
		</p>
		<div class="clear"></div>
	</li>
{% endfor %}
{% endautoescape %}

</ul></div>
		{% endif %}
<div class="groups-list-loading"><div><a href="#" class="groups-loading-close"></a></div><div class="clear"></div><p style="text-align:center"><img src="{{ site.staticContentPath }}/web-gallery/images/progress_bubbles.gif" alt="" width="29" height="6" /></p></div>
<div class="groups-list-info"></div>

		<div class="clear"></div>
		</div>
	</div>
</div>
</div>

<script type="text/javascript">	

	new GroupsWidget('{{ sticker.getUserId }}', '{{ sticker.getId() }}');

</script>
