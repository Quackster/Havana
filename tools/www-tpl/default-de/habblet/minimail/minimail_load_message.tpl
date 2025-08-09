	<ul class="message-headers">
				<li><a href="#" class="report" title="Nachricht melden"></a></li>
			<li><b>Thema:</b> {{ minimailMessage.getFormattedSubject() }}</li>
			<li><b>Von:</b> {{ minimailMessage.getAuthor().getName() }}</li>
			<li><b>An:</b> {{ minimailMessage.getTarget().getName() }}</li>

		</ul>
		<div class="body-text">{{ minimailMessage.getFormattedMessage() }}<br></div>
		<div class="reply-controls">
			<div>
				<div class="new-buttons clearfix">
				{% if minimailMessage.getConversationId() > 0 %}
				<a href="#" class="related-messages" id="rel-{{ minimailMessage.getConversationId() }}" title="Ganzes Gespräch"></a>
				{% endif %}
				{% if (minimailLabel == "inbox") or (minimailLabel == "sent") %}
					<a href="#" class="new-button red-button delete"><b>Löschen</b><i></i></a>
					<a href="#" class="new-button reply"><b>Antworten</b><i></i></a>
				{% endif %}
				{% if minimailLabel == "trash" %}
					<a href="#" class="new-button undelete"><b>Wiederherstellen</b><i></i></a>
					<a href="#" class="new-button red-button delete"><b>Löschen</b><i></i></a>
				{% endif %}
				</div>
			</div>
			<div style="display: none;">
				<textarea rows="5" cols="10" class="message-text"></textarea><br>
				<div class="new-buttons clearfix">
					<a href="#" class="new-button cancel-reply"><b>Abbrechen</b><i></i></a>
					<a href="#" class="new-button preview"><b>Vorschau</b><i></i></a>

					<a href="#" class="new-button send-reply"><b>Senden</b><i></i></a>
				</div>
			</div>
		</div>