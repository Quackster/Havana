	<ul class="message-headers">
				<li><a href="#" class="report" title="Report message to moderators"></a></li>
			<li><b>Assunto:</b> {{ minimailMessage.getFormattedSubject() }}</li>
			<li><b>De:</b> {{ minimailMessage.getAuthor().getName() }}</li>
			<li><b>Para:</b> {{ minimailMessage.getTarget().getName() }}</li>

		</ul>
		<div class="body-text">{{ minimailMessage.getFormattedMessage() }}<br></div>
		<div class="reply-controls">
			<div>
				<div class="new-buttons clearfix">
				{% if minimailMessage.getConversationId() > 0 %}
				<a href="#" class="related-messages" id="rel-{{ minimailMessage.getConversationId() }}" title="Show full conversation"></a>
				{% endif %}
				{% if (minimailLabel == "inbox") or (minimailLabel == "sent") %}
					<a href="#" class="new-button red-button delete"><b>Deletar</b><i></i></a>
					<a href="#" class="new-button reply"><b>Responder</b><i></i></a>
				{% endif %}
				{% if minimailLabel == "trash" %}
					<a href="#" class="new-button undelete"><b>Desfazer</b><i></i></a>
					<a href="#" class="new-button red-button delete"><b>Deletar</b><i></i></a>
				{% endif %}
				</div>
			</div>
			<div style="display: none;">
				<textarea rows="5" cols="10" class="message-text"></textarea><br>
				<div class="new-buttons clearfix">
					<a href="#" class="new-button cancel-reply"><b>Cancelar</b><i></i></a>
					<a href="#" class="new-button preview"><b>Visualizar</b><i></i></a>

					<a href="#" class="new-button send-reply"><b>Enviar</b><i></i></a>
				</div>
			</div>
		</div>