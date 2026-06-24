	<ul class="message-headers">
				<li><a href="#" class="report" title="{{ locale.habblet_minimail_report_message_to_moderators }}"></a></li>
			<li><b>{{ locale.habblet_minimail_subject }}</b> {{ minimailMessage.getFormattedSubject() }}</li>
			<li><b>{{ locale.habblet_minimail_from }}</b> {{ minimailMessage.getAuthor().getName() }}</li>
			<li><b>{{ locale.habblet_minimail_to }}</b> {{ minimailMessage.getTarget().getName() }}</li>

		</ul>
		<div class="body-text">{{ minimailMessage.getFormattedMessage() }}<br></div>
		<div class="reply-controls">
			<div>
				<div class="new-buttons clearfix">
				{% if minimailMessage.getConversationId() > 0 %}
				<a href="#" class="related-messages" id="rel-{{ minimailMessage.getConversationId() }}" title="{{ locale.habblet_minimail_show_full_conversation }}"></a>
				{% endif %}
				{% if (minimailLabel == "inbox") or (minimailLabel == "sent") %}
					<a href="#" class="new-button red-button delete"><b>{{ locale.habblet_minimail_delete }}</b><i></i></a>
					<a href="#" class="new-button reply"><b>{{ locale.habblet_minimail_reply }}</b><i></i></a>
				{% endif %}
				{% if minimailLabel == "trash" %}
					<a href="#" class="new-button undelete"><b>{{ locale.habblet_minimail_undelete }}</b><i></i></a>
					<a href="#" class="new-button red-button delete"><b>{{ locale.habblet_minimail_delete }}</b><i></i></a>
				{% endif %}
				</div>
			</div>
			<div style="display: none;">
				<textarea rows="5" cols="10" class="message-text"></textarea><br>
				<div class="new-buttons clearfix">
					<a href="#" class="new-button cancel-reply"><b>{{ locale.habblet_minimail_cancel }}</b><i></i></a>
					<a href="#" class="new-button preview"><b>{{ locale.habblet_minimail_preview }}</b><i></i></a>

					<a href="#" class="new-button send-reply"><b>{{ locale.habblet_minimail_send }}</b><i></i></a>
				</div>
			</div>
		</div>