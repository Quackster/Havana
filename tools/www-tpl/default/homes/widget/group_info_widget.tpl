<div class="movable widget GroupInfoWidget" id="widget-{{ sticker.getId() }}" style=" left: {{ sticker.getX() }}px; top: {{ sticker.getY() }}px; z-index: {{ sticker.getZ() }};">
<div class="w_skin_{{ sticker.getSkin() }}">
	<div class="widget-corner" id="widget-{{ sticker.getId() }}-handle">
		<div class="widget-headline"><h3>
		
		{% if editMode %}
		<img src="{{ site.staticContentPath }}/web-gallery/images/myhabbo/icon_edit.gif" width="19" height="18" class="edit-button" id="widget-{{ sticker.getId() }}-edit" />
		<script language="JavaScript" type="text/javascript">
		Event.observe("{{ locale.homes_widget_widget|escape('js') }}{{ sticker.getId() }}{{ locale.homes_widget_edit|escape('js') }}", "click", function(e) { openEditMenu(e, {{ sticker.getId() }}, "widget", "{{ locale.homes_widget_widget|escape('js') }}{{ sticker.getId() }}{{ locale.homes_widget_edit|escape('js') }}"); }, false);
		</script>
		{% endif %}
		
<span class="header-left">&nbsp;</span><span class="header-middle">{{ locale.homes_widget_group_info }}</span><span class="header-right">&nbsp;</span></h3>
		</div>	
	</div>
	<div class="widget-body">
		<div class="widget-content">
		{% autoescape 'html' %}
<div class="group-info-icon"><img src="{{ site.sitePath }}/habbo-imaging/badge/{{ group.getBadge() }}.gif" /></div>
	    <img id="groupname-{{ group.id }}-report" class="report-button report-gn"
			alt="{{ locale.homes_widget_report }}"
			src="{{ site.staticContentPath }}/web-gallery/images/myhabbo/buttons/report_button.gif"
			style="display: none;" />
<h4>{{ group.getName() }}</h4>

<p>{{ locale.homes_widget_group_created }} <b>{{ group.getCreatedDate() }}</b></p>
<p>{{ sticker.getMembersAmount() }}</b> {{ locale.homes_widget_members }}</p>
{% if group.getRoomId() > 0 %}<p><a href="{{ site.sitePath }}/client?forwardId=2&amp;roomId={{ room.getId() }}" onclick="HabboClient.roomForward(this, '{{ room.getId() }}', 'private'); return false;" target="client" class="group-info-room">{% autoescape 'html' %}{{ room.getData().getName() }}{% endautoescape %}</a></p>{% endif %}
<div class="group-info-description">{{ group.getDescription() }}</div>


    <div id="profile-tags-panel">
    <div id="profile-tag-list">
	{% include "../../groups/habblet/listgrouptags.tpl" %}
    </div>
		{% if session.loggedIn %}
{% if group.getOwnerId() == playerDetails.getId() %}
<div id="profile-tags-status-field">
 <div style="display: block;">
  <div class="content-red">
   <div class="content-red-body">
    <span id="tag-limit-message"><img src="{{ site.staticContentPath }}/web-gallery/images/register/icon_error.gif"/> {{ locale.homes_widget_the_limit_is_two_zero_tags }}</span>
    <span id="tag-invalid-message"><img src="{{ site.staticContentPath }}/web-gallery/images/register/icon_error.gif"/> {{ locale.homes_widget_invalid_tag }}</span>
   </div>
  </div>
 <div class="content-red-bottom">
  <div class="content-red-bottom-body"></div>
 </div>
 </div>
</div>        <div class="profile-add-tag">
            <input type="text" id="profile-add-tag-input" maxlength="30"/><br clear="all"/>
            <a href="#" class="new-button" style="float:left;margin:5px 0 0 0;" id="profile-add-tag"><b>{{ locale.homes_widget_add_tag }}</b><i></i></a>
        </div>	{% endif %}{% endif %}
    </div>
<script type="text/javascript">
    document.observe("dom:loaded", function() {
        new GroupInfoWidget('{{ group.id }}', '1');
    });
</script>



	<img id="groupdesc-{{ sticker.id }}-report" class="report-button report-gd"
	    alt="{{ locale.homes_widget_report }}"
	    src="{{ site.staticContentPath }}/web-gallery/images/myhabbo/buttons/report_button.gif"
        style="display: none;" />

		<div class="clear"></div>
		{% endautoescape %}
		</div>
	</div>
</div>
</div>