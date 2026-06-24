<form action="#" method="post" id="group-settings-form">

  <div id="group-settings">
    <div id="group-settings-data" class="group-settings-pane">
      <div id="group-logo">
        <img src="{{ site.sitePath }}/habbo-imaging/badge/b0503Xs09114s05013s05015.gif" />
      </div>
      <div id="group-identity-area">
        <div id="group-name-area">
          <div id="group_name_message_error" class="error"></div>
          <label for="group_name" id="group_name_text">{{ locale.groups_habblet_edit_group_name }}</label>
          {% autoescape 'html' %}
          <input type="text" name="group_name" id="group_name" onKeyUp="GroupUtils.validateGroupElements('group_name', 30, 'Maximum Group name length reached');" value="{{ group.getName }}"/><br />
          {% endautoescape %}
        </div>

        <div id="group-url-area">
          <div id="group_url_message_error" class="error"></div>
            <label for="group_url" id="group_url_text">{{ locale.groups_habblet_edit_group_url }}</label><br/>
						
			{% if group.getAlias() == "" %}
            <input type="text" name="group_url" id="group_url" onKeyUp="GroupUtils.validateGroupElements('group_url', 30, 'URL limit reached');" value=""/><br />
            <input type="hidden" name="group_url_edited" id="group_url_edited" value="1"/>
			{% else %}
			<span id="group_url_text"><a href="{{ group.generateClickLink() }}">/groups/{{ group.getAlias() }}</a></span><br/>
            <input type="hidden" name="group_url" id="group_url" value="{{ group.getAlias() }}"/>
            <input type="hidden" name="group_url_edited" id="group_url_edited" value="0"/>
			{% endif %}
			
			          </div>
        </div>

        <div id="group-description-area">
          <div id="group_description_message_error" class="error"></div>
          <label for="group_description" id="description_text">{{ locale.groups_habblet_edit_text }}</label>
          <span id="description_chars_left">
            <label for="characters_left">{{ locale.groups_habblet_characters_left }}</label>
            <input id="group_description-counter" type="text" value="{{ charactersLeft }}" size="3" readonly="readonly" class="amount" />
          </span>
          <textarea name="group_description" id="group_description" onKeyUp="GroupUtils.validateGroupElements('group_description', 255, 'Description limit reached');">{{ group.getDescription() }}</textarea>
        </div>
      </div>
      <div id="group-settings-type" class="group-settings-pane group-settings-selection">
		{% if group.getGroupType() == 3 %}
        <label for="group_type">{{ locale.groups_habblet_edit_group_type }}</label>
        <input type="radio" name="group_type" id="group_type" value="0" disabled="disabled" />
        <div class="description">
          <div class="group-type-normal">{{ locale.groups_habblet_regular }}</div>
          <p>{{ locale.groups_habblet_anyone_can_join_five_zero_zero_zero_member_limit }}</p>
        </div>
        <input type="radio" name="group_type" id="group_type" value="1" disabled="disabled" />
        <div class="description">
          <div class="group-type-exclusive">{{ locale.groups_habblet_exclusive }}</div>
          <p>{{ locale.groups_habblet_group_admin_controls_who_can_join }}</p>
        </div>
        <input type="radio" name="group_type" id="group_type" value="2" disabled="disabled" />
        <div class="description">
          <div class="group-type-private">{{ locale.groups_habblet_private }}</div>
          <p>{{ locale.groups_habblet_no_one_can_join }}</p>
        </div>
        <input type="radio" name="group_type" id="group_type" value="3" checked="checked" disabled="disabled" />
        <div class="description">
          <div class="group-type-large">{{ locale.groups_habblet_unlimited }}</div>
          <p>{{ locale.groups_habblet_anyone_can_join_no_membership_limit_unable_to_browse_members }}</p>
          <p class="description-note">{{ locale.groups_habblet_note_if_you_choose_this_option_you_can_t_change_it_later }}</p>
        </div>
        <input type="hidden" id="initial_group_type" value="0">
		{% else %}
        <label for="group_type">{{ locale.groups_habblet_edit_group_type }}</label>
        <input type="radio" name="group_type" id="group_type" value="0"{{ selected0GroupType}} />
        <div class="description">
          <div class="group-type-normal">{{ locale.groups_habblet_regular }}</div>
          <p>{{ locale.groups_habblet_anyone_can_join_five_zero_zero_zero_member_limit }}</p>
        </div>
        <input type="radio" name="group_type" id="group_type" value="1"{{ selected1GroupType}} />
        <div class="description">
          <div class="group-type-exclusive">{{ locale.groups_habblet_exclusive }}</div>
          <p>{{ locale.groups_habblet_group_admin_controls_who_can_join }}</p>
        </div>
        <input type="radio" name="group_type" id="group_type" value="2"{{ selected2GroupType}} />
        <div class="description">
          <div class="group-type-private">{{ locale.groups_habblet_private }}</div>
          <p>{{ locale.groups_habblet_no_one_can_join }}</p>
        </div>
        <input type="radio" name="group_type" id="group_type" value="3"{{ selected3GroupType}} />
        <div class="description">
          <div class="group-type-large">{{ locale.groups_habblet_unlimited }}</div>
          <p>{{ locale.groups_habblet_anyone_can_join_no_membership_limit_unable_to_browse_members }}</p>
          <p class="description-note">{{ locale.groups_habblet_note_if_you_choose_this_option_you_can_t_change_it_later }}</p>
        </div>
        <input type="hidden" id="initial_group_type" value="0">
		{% endif %}
      </div>
    </div>


    <div id="forum-settings" style="display: none;">

      <div id="forum-settings-type" class="group-settings-pane group-settings-selection">
        <label for="forum_type">{{ locale.groups_habblet_edit_forum_type }}</label>
        <input type="radio" name="forum_type" id="forum_type" value="0"{{ selected0ForumType}} />
        <div class="description">
          {{ locale.groups_habblet_public }}<br />
          <p>{{ locale.groups_habblet_anyone_can_read_forum_posts }}</p>
        </div>
        <input type="radio" name="forum_type" id="forum_type" value="1"{{ selected1ForumType}} />
        <div class="description">
          {{ locale.groups_habblet_private }}<br />
          <p>{{ locale.groups_habblet_only_group_members_can_read_forum_posts }}</p>
        </div>
      </div>

      <div id="forum-settings-topics" class="group-settings-pane group-settings-selection">
        <label for="new_topic_permission">{{ locale.groups_habblet_edit_new_threads }}</label>
        <input type="radio" name="new_topic_permission" id="new_topic_permission" value="2"{{ selected2ForumPermissionType}} />
        <div class="description">
          {{ locale.groups_habblet_admin }}<br />
          <p>{{ locale.groups_habblet_only_admins_can_start_new_threads }}</p>
        </div>
        <input type="radio" name="new_topic_permission" id="new_topic_permission" value="1"{{ selected1ForumPermissionType}} />
        <div class="description">
          {{ locale.groups_habblet_members }}<br />
          <p>{{ locale.groups_habblet_only_group_members_can_start_new_threads }}</p>
        </div>
        <input type="radio" name="new_topic_permission" id="new_topic_permission" value="0"{{ selected0ForumPermissionType}} />
        <div class="description">
          {{ locale.groups_habblet_everyone }}<br />
          <p>{{ locale.groups_habblet_anyone_can_start_a_new_thread }}</p>
        </div>
      </div>
    </div>


    <div id="room-settings" style="display: none;">
      <label>{{ locale.groups_habblet_select_a_room_for_your_group }}</label>
      <div id="room-settings-id" class="group-settings-pane-wide group-settings-selection">
        <ul>
          <li><input type="radio" name="roomId" value="" {% if group.getRoomId() == 0 %}checked="checked" {% endif %}/><div>{{ locale.groups_habblet_no_room }}</div></li>

    {% autoescape 'html' %}
		{% set num = 0 %}
		{% for room in rooms %}
			{% if num % 2 == 0 %}
			<li class="even">
			{% else %}
			<li class="odd">
			{% endif %}
		  
            <input type="radio" name="roomId" value="{{ room.getId() }}" {% if group.getRoomId() == room.getId() %}checked="checked" {% endif %}/>
            <a href="{{ site.sitePath }}/client?forwardId=2&amp;roomId={{ room.getId() }}" onclick="HabboClient.roomForward(this, '{{ room.getId() }}', 'private'); return false;" target="client" class="room-enter">{{ locale.groups_habblet_enter }}</a>
            <div>
              {{ room.getData().getName() }}<br />
              <span class="room-description">{{ room.getData().getDescription() }}</br></span>
            </div>
          </li>
		  {% set num = num + 1 %}
		  {% endfor %}
      {% endautoescape %}
        </ul>
      </div>
    </div>

    <div id="group-button-area">
      <a href="#" id="group-settings-update-button" class="new-button" onclick="showGroupSettingsConfirmation('{{ group.getId() }}');">
        <b>{{ locale.groups_habblet_save_changes }}</b><i></i>
      </a>
      <a id="group-delete-button" href="#" class="new-button red-button" onclick="openGroupActionDialog('/groups/actions/confirm_delete_group', '/groups/actions/delete_group', null , '{{ group.getId() }}', null);">
        <b>{{ locale.groups_habblet_delete_group }}</b><i></i>
      </a>
      <a href="#" id="group-settings-close-button" class="new-button" onclick="closeGroupSettings(); return false;"><b>{{ locale.groups_habblet_cancel }}</b><i></i></a>
    </div>
  </div>
</form>

<div class="clear"></div>

<script type="text/javascript" language="JavaScript">
    L10N.put("group.settings.title.text", "{{ locale.groups_habblet_edit_group_settings|escape('js') }}");
    L10N.put("group.settings.group_type_change_warning.normal", "{{ locale.groups_habblet_are_you_sure_you_want_to_change_the_group_type_to_strong_normal_strong|escape('js') }}");
    L10N.put("group.settings.group_type_change_warning.exclusive", "{{ locale.groups_habblet_are_you_sure_you_want_to_change_the_group_type_to_strong_exclusive_strong|escape('js') }}");
    L10N.put("group.settings.group_type_change_warning.closed", "{{ locale.groups_habblet_are_you_sure_you_want_to_change_the_group_type_to_strong_private_strong|escape('js') }}");
    L10N.put("group.settings.group_type_change_warning.large", "{{ locale.groups_habblet_are_you_sure_you_want_to_change_the_group_type_to_strong_large_strong_if_you_continue_you_can_t_change_it_back_later|escape('js') }}");
    L10N.put("myhabbo.groups.confirmation_ok", "{{ locale.groups_habblet_ok|escape('js') }}");
    L10N.put("myhabbo.groups.confirmation_cancel", "{{ locale.groups_habblet_cancel|escape('js') }}");
    switchGroupSettingsTab(null, "group");
</script>
