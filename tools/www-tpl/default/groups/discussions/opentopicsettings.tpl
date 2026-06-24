<form action="#" method="post" id="topic-settings-form">
	<div id="topic-name-area">
	    	<div class="topic-name-input">
	    		<span class="topic-name-text" id="topic_name_text">{{ locale.groups_discussions_topic_max_three_two_characters }}</span>
	    	</div>
	    	<div class="topic-name-input">
				{% autoescape 'html' %}
	    		<input type="text" size="38" maxlength="32" name="topic_name" id="topic_name" onKeyUp="GroupUtils.validateGroupElements('topic_name', 32, 'myhabbo.topic.name.max.length.exceeded');" value="{{ topic.getTopicTitle() }}"/>
				{% endautoescape %}
			</div>
            <div id="topic-name-error"></div>
            <div id="topic_name_message_error" class="error"></div>
    </div>
	<div id="topic-type-area">
		<div class="topic-type-label">
			<span class="topic-type-label">{{ locale.groups_discussions_type }}</span>
		</div>
	    <div class="topic-type-input">
	    	<input type="radio" name="topic_type" id="topic_open" value="0" {% if topic.isOpen %}checked="true" {% endif %}/> {{ locale.groups_discussions_open }}
			<br />
			<input type="radio" name="topic_sticky" id="topic_normal" value="0" {% if topic.isStickied() == false %}checked="true" {% endif %}/> {{ locale.groups_discussions_normal }}	    </div>
	    <div class="topic-type-input">
	    	 <input type="radio" name="topic_type" id="topic_closed" value="1" {% if topic.isOpen == false %}checked="true" {% endif %}/> {{ locale.groups_discussions_closed }}
			 <br />
			 <input type="radio" name="topic_sticky" id="topic_sticky" value="1" {% if topic.isStickied() %}checked="true" {% endif %}/> {{ locale.groups_discussions_sticky_thread }}	    </div>
	</div>
	<br clear="all"/>
	<br clear="all"/>
	<div id="topic-button-area" class="topic-button-area">
		<a href="#" class="new-button cancel-topic-settings" id="cancel-topic-settings"><b>{{ locale.groups_discussions_cancel }}</b><i></i></a>
		<a href="#" class="new-button delete-topic" id="delete-topic"><b>{{ locale.groups_discussions_delete }}</b><i></i></a>		<a href="#" class="new-button save-topic-settings" style="float:left; margin:0px;" id="save-topic-settings"><b>{{ locale.groups_discussions_ok }}</b><i></i></a>
	</div>
</form>
<div class="clear"></div>