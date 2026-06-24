		{% if minimailClient == false %}<a href="#" class="new-button compose"><b>{{ locale.habblet_minimail_compose }}</b><i></i></a>{% endif %}
	<div class="clearfix labels nostandard">
		<ul class="box-tabs">
			<li{% if minimailLabel == "inbox" %} class="selected"{% endif %}><a href="#" label="inbox">{{ locale.habblet_minimail_inbox }}</a><span class="tab-spacer"></span></li>
			<li{% if minimailLabel == "sent" %} class="selected"{% endif %}><a href="#" label="sent">{{ locale.habblet_minimail_sent }}</a><span class="tab-spacer"></span></li>
			<li{% if minimailLabel == "trash" %} class="selected"{% endif %}><a href="#" label="trash">{{ locale.habblet_minimail_trash }}</a><span class="tab-spacer"></span></li>
		</ul>

	</div>
	<div id="message-list" class="label-sent">
	<div class="new-buttons clearfix">
		<div class="labels inbox-refresh"><a href="#" class="new-button green-button" label="inbox" style="float: left; margin: 0"><b>{{ locale.habblet_minimail_refresh }}</b><i></i></a></div>
	</div>

	<div style="clear: both; height: 1px"></div>
		{% if minimailLabel == "conversation" %}
		<div class="trash-controls notice">
			{{ locale.habblet_minimail_you_are_reading_a_conversation_click_the_tabs_above_to_go_back_to_your_folders }}
		</div>
		{% endif %}
		{% if minimailLabel == "trash" %}
		{% if minimailMessages|length > 0 %}
			<div class="trash-controls notice">{{ locale.habblet_minimail_messages_in_this_folder_that_are_older_than_three_zero_days_are_deleted_automatically }} <a href="#" class="empty-trash">{{ locale.habblet_minimail_empty_trash }}</a></div>
		{% endif %}
		{% endif %}
		<div class="navigation">
			{% if minimailLabel == "inbox" %}
			<div class="unread-selector"><input type="checkbox" class="unread-only"{% if unreadOnly %} checked="checked"{% endif %}/> {{ locale.habblet_minimail_only_unread }}</div>
			{% endif %}
			<!-- <div class="progress"></div> -->
			{% if minimailMessages|length > 0 %}
			<p> 
				{% if showNewest %}<a href="#" class="newest">{{ locale.habblet_minimail_newest }}</a>{% endif %}
				{% if showNewer %}<a href="#" class="newer">{{ locale.habblet_minimail_newer }}</a>{% endif %}  {{ startPage }} - {{ endPage }} {{ locale.habblet_minimail_of }} {{ totalMessages }}
				{% if showOlder %}<a href="#" class="older">{{ locale.habblet_minimail_older }}</a>{% endif %}
				{% if showOldest %}<a href="#" class="oldest">{{ locale.habblet_minimail_oldest }}</a>{% endif %}
			</p>
			{% endif %}
		{% if minimailMessages|length == 0 %}
			{% if minimailLabel == "trash" %}
			<p class="no-messages">{{ locale.habblet_minimail_no_deleted_messages }}</p>
			{% elseif minimailLabel == "sent" %}
			<p class="no-messages">{{ locale.habblet_minimail_no_sent_messages }}</p>
			{% elseif minimailLabel == "conversation" %}
			<p class="no-messages">{{ locale.habblet_minimail_no_conversation_messages }}</p>
			{% else %}
			<p class="no-messages">{{ locale.habblet_minimail_no_messages }}</p>
			{% endif %}
		{% endif %}
		</div>
		{% if minimailMessages|length > 0 %}
		{% for minimailMessage in minimailMessages %}
		<div class="message-item {% if minimailLabel == "inbox" %}{% if minimailMessage.isRead() %}read{% else %}unread{% endif %}{% else %}read{% endif %}" id="msg-{{ minimailMessage.getId() }}">
			<div class="message-preview" status="{% if minimailLabel == "inbox" %}{% if minimailMessage.isRead() %}read{% else %}unread{% endif %}{% else %}read{% endif %}">

				<span class="message-tstamp" isotime="{{ minimailMessage.getIsoDate() }}" title="{{ minimailMessage.getDate() }}">
					{{ minimailMessage.getDate() }}
				</span>
				<img src="{{ site.sitePath }}/habbo-imaging/avatarimage?figure={{ minimailMessage.getAuthor().getFigure() }}&size=s&direction=9&head_direction=2&crr=0&gesture=sml&frame=1" />
				{% if minimailLabel == "sent" %}
				<span class="message-sender" title="To: {{ minimailMessage.getTarget().getName() }}">{{ locale.habblet_minimail_to }} {{ minimailMessage.getTarget().getName() }}</span>
				{% elseif minimailLabel == "inbox" %}
				<span class="message-sender" title="{{ minimailMessage.getAuthor().getName() }}">{{ minimailMessage.getAuthor().getName() }}</span>
				{% endif %}
				
				<span class="message-subject" title="{{ minimailMessage.getFormattedSubject() }}">&ldquo;{{ minimailMessage.getFormattedSubject() }}&rdquo;</span>
			</div>
			<div class="message-body" style="display: none;">
				<div class="contents"></div>

				<div class="message-body-bottom"></div>
			</div>
		</div>
		{% endfor %}
		{% endif %}

	<div class="navigation">
			<div class="progress"></div>
			{% if minimailMessages|length > 0 %}
			<p> 
				{% if showNewest %}<a href="#" class="newest">{{ locale.habblet_minimail_newest }}</a>{% endif %}
				{% if showNewer %}<a href="#" class="newer">{{ locale.habblet_minimail_newer }}</a>{% endif %}  {{ startPage }} - {{ endPage }} {{ locale.habblet_minimail_of }} {{ totalMessages }}
				{% if showOlder %}<a href="#" class="older">{{ locale.habblet_minimail_older }}</a>{% endif %}
				{% if showOldest %}<a href="#" class="oldest">{{ locale.habblet_minimail_oldest }}</a>{% endif %}
			</p>
			{% endif %}
			</div>
		</div>