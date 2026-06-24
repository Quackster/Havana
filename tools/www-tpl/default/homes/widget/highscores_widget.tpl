<div class="movable widget HighScoresWidget" id="widget-{{ sticker.getId() }}" style="left: {{ sticker.getX() }}px; top: {{ sticker.getY() }}px; z-index: {{ sticker.getZ() }}">
<div class="w_skin_{{ sticker.getSkin() }}">
	<div class="widget-corner" id="widget-{{ sticker.getId() }}-handle">
		<div class="widget-headline"><h3>
{% if editMode %}
<img src="{{ site.staticContentPath }}/web-gallery/images/myhabbo/icon_edit.gif" width="19" height="18" class="edit-button" id="widget-{{ sticker.getId() }}-edit" />
<script language="JavaScript" type="text/javascript">
Event.observe("{{ locale.homes_widget_widget|escape('js') }}{{ sticker.getId() }}{{ locale.homes_widget_edit|escape('js') }}", "click", function(e) { openEditMenu(e, {{ sticker.getId() }}, "widget", "{{ locale.homes_widget_widget|escape('js') }}{{ sticker.getId() }}{{ locale.homes_widget_edit|escape('js') }}"); }, false);
</script>
{% endif %}
		<span class="header-left">&nbsp;</span><span class="header-middle">{{ locale.homes_widget_high_scores }}</span><span class="header-right">&nbsp;</span></h3>
		</div>	
	</div>
	<div class="widget-body">
		<div class="widget-content">
	<table>
				<tr>
				<td>{{ locale.homes_widget_you_do_not_have_any_high_scores }}</td>
			</tr>
		</table>
		<div class="clear"></div>
		</div>
	</div>
</div>
</div>