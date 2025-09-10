<ul class="guestbook-entries">
	<li id="guestbook-entry--1" class="guestbook-entry">
		<div class="guestbook-author">
			<img src="{{ site.sitePath }}/habbo-imaging/avatarimage?figure={{ playerDetails.getFigure() }}&size=s&direction=4&head_direction=4&crr=0&gesture=&frame=1" alt="" title=""/>
		</div>
		<div class="guestbook-message">
					<div class="{% if playerDetails.isOnline() %}online{% else %}offline{% endif %}">
				<a href="{{ site.sitePath }}/home/{{ playerDetails.getName() }}">{{ playerDetails.getName() }}</a>
			</div>
			<p>{{ message }}</p>
		</div>
		<div class="guestbook-cleaner">&nbsp;</div>
		<div class="guestbook-entry-footer metadata">{{ formattedDate }}<!-- Juli 28, 2025 83:45:50 PM --></div>
	</li>
</ul>

<div class="guestbook-toolbar clearfix">
<a href="#" class="new-button" id="guestbook-form-continue"><b>Fortsetzen</b><i></i></a>
<a href="#" class="new-button" id="guestbook-form-post"><b>Hinzufügen</b><i></i></a>	
</div>