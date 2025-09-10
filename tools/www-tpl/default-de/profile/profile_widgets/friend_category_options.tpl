{% autoescape 'html' %}
		<div id="category-options" class="clearfix">
			<select id="category-list-select" name="category-list">
				<option value="0">Freunde</option>
				{% for category in categories %}
					<option value="{{ category.getId() }}">{{ category.getName() }}</option>
				{% endfor %}
			</select>

<div class="friend-del"><a class="new-button red-button cancel-icon" href="#" id="delete-friends"><b><span></span>Lösche ausgewählte</b><i></i></a></div>
<div class="friend-move"><a class="new-button" href="#" id="move-friend-button"><b><span></span>Bewegen</b><i></i></a></div>     
        </div>
{% endautoescape %}