<form action="#" method="post" id="group-settings-form">

  <div id="group-settings">
    <div id="group-settings-data" class="group-settings-pane">
      <div id="group-logo">
        <img src="{{ site.sitePath }}/habbo-imaging/badge/b0503Xs09114s05013s05015.gif" />
      </div>
      <div id="group-identity-area">
        <div id="group-name-area">
          <div id="group_name_message_error" class="error"></div>
          <label for="group_name" id="group_name_text">Gruppenname:</label>
          {% autoescape 'html' %}
          <input type="text" name="group_name" id="group_name" onKeyUp="GroupUtils.validateGroupElements('group_name', 30, 'Maximum Group name length reached');" value="{{ group.getName }}"/><br />
          {% endautoescape %}
        </div>

        <div id="group-url-area">
          <div id="group_url_message_error" class="error"></div>
            <label for="group_url" id="group_url_text">Gruppen URL:</label><br/>
						
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
          <label for="group_description" id="description_text">Text:</label>
          <span id="description_chars_left">
            <label for="characters_left">Zeichen übrig:</label>
            <input id="group_description-counter" type="text" value="{{ charactersLeft }}" size="3" readonly="readonly" class="amount" />
          </span>
          <textarea name="group_description" id="group_description" onKeyUp="GroupUtils.validateGroupElements('group_description', 255, 'Description limit reached');">{{ group.getDescription() }}</textarea>
        </div>
      </div>
      <div id="group-settings-type" class="group-settings-pane group-settings-selection">
		{% if group.getGroupType() == 3 %}
        <label for="group_type">Gruppenart bearbeiten:</label>
        <input type="radio" name="group_type" id="group_type" value="0" disabled="disabled" />
        <div class="description">
          <div class="group-type-normal">Normal</div>
          <p>Jeder kann mitmachen. Mitgliederlimit: 5.000.</p>
        </div>
        <input type="radio" name="group_type" id="group_type" value="1" disabled="disabled" />
        <div class="description">
          <div class="group-type-exclusive">Exklusiv</div>
          <p>Der Gruppenadmin steuert, wer beitreten kann.</p>
        </div>
        <input type="radio" name="group_type" id="group_type" value="2" disabled="disabled" />
        <div class="description">
          <div class="group-type-private">Privat</div>
          <p>Niemand kann beitreten.</p>
        </div>
        <input type="radio" name="group_type" id="group_type" value="3" checked="checked" disabled="disabled" />
        <div class="description">
          <div class="group-type-large">Unendlich</div>
          <p>Jeder kann beitreten. Keine Mitgliederbeschränkung. Mitglieder können nicht gesucht werden..</p>
          <p class="description-note">Hinweis: Wenn du diese Option wählst, kannst du sie später nicht mehr ändern!</p>
        </div>
        <input type="hidden" id="initial_group_type" value="0">
		{% else %}
        <label for="group_type">Gruppenart bearbeiten:</label>
        <input type="radio" name="group_type" id="group_type" value="0"{{ selected0GroupType}} />
        <div class="description">
          <div class="group-type-normal">Normal</div>
          <p>Jeder kann mitmachen. Mitgliederlimit: 5.000.</p>
        </div>
        <input type="radio" name="group_type" id="group_type" value="1"{{ selected1GroupType}} />
        <div class="description">
          <div class="group-type-exclusive">Exklusiv</div>
          <p>Der Gruppenadmin steuert, wer beitreten kann.</p>
        </div>
        <input type="radio" name="group_type" id="group_type" value="2"{{ selected2GroupType}} />
        <div class="description">
          <div class="group-type-private">Privat</div>
          <p>Niemand kann beitreten.</p>
        </div>
        <input type="radio" name="group_type" id="group_type" value="3"{{ selected3GroupType}} />
        <div class="description">
          <div class="group-type-large">Unendlich</div>
          <p>Jeder kann beitreten. Keine Mitgliederbeschränkung. Mitglieder können nicht gesucht werden.</p>
          <p class="description-note">Hinweis: Wenn du diese Option wählst, kannst du sie später nicht mehr ändern!</p>
        </div>
        <input type="hidden" id="initial_group_type" value="0">
		{% endif %}
      </div>
    </div>


    <div id="forum-settings" style="display: none;">

      <div id="forum-settings-type" class="group-settings-pane group-settings-selection">
        <label for="forum_type">Forumart bearbeiten:</label>
        <input type="radio" name="forum_type" id="forum_type" value="0"{{ selected0ForumType}} />
        <div class="description">
          Offen<br />
          <p>Jeder kann Beiträge lesen.</p>
        </div>
        <input type="radio" name="forum_type" id="forum_type" value="1"{{ selected1ForumType}} />
        <div class="description">
          Privat<br />
          <p>Nur Mitglieder können Beiträge lesen.</p>
        </div>
      </div>

      <div id="forum-settings-topics" class="group-settings-pane group-settings-selection">
        <label for="new_topic_permission">Neue Threads bearbeiten:</label>
        <input type="radio" name="new_topic_permission" id="new_topic_permission" value="2"{{ selected2ForumPermissionType}} />
        <div class="description">
          Admin<br />
          <p>Nur Admins können neue Threads erstellen.</p>
        </div>
        <input type="radio" name="new_topic_permission" id="new_topic_permission" value="1"{{ selected1ForumPermissionType}} />
        <div class="description">
          Mitglieder<br />
          <p>Nur Mitglieder können neue Threads erstellen.</p>
        </div>
        <input type="radio" name="new_topic_permission" id="new_topic_permission" value="0"{{ selected0ForumPermissionType}} />
        <div class="description">
          Alle<br />
          <p>Jeder kann ein Thread erstellen.</p>
        </div>
      </div>
    </div>


    <div id="room-settings" style="display: none;">
      <label>Wähle einen Raum für deine Gruppe:</label>
      <div id="room-settings-id" class="group-settings-pane-wide group-settings-selection">
        <ul>
          <li><input type="radio" name="roomId" value="" {% if group.getRoomId() == 0 %}checked="checked" {% endif %}/><div>Kein Raum</div></li>

    {% autoescape 'html' %}
		{% set num = 0 %}
		{% for room in rooms %}
			{% if num % 2 == 0 %}
			<li class="even">
			{% else %}
			<li class="odd">
			{% endif %}
		  
            <input type="radio" name="roomId" value="{{ room.getId() }}" {% if group.getRoomId() == room.getId() %}checked="checked" {% endif %}/>
            <a href="{{ site.sitePath }}/client?forwardId=2&amp;roomId={{ room.getId() }}" onclick="HabboClient.roomForward(this, '{{ room.getId() }}', 'private'); return false;" target="client" class="room-enter">Betreten</a>
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
        <b>Speichern</b><i></i>
      </a>
      <a id="group-delete-button" href="#" class="new-button red-button" onclick="openGroupActionDialog('/groups/actions/confirm_delete_group', '/groups/actions/delete_group', null , '{{ group.getId() }}', null);">
        <b>Gruppe löschen</b><i></i>
      </a>
      <a href="#" id="group-settings-close-button" class="new-button" onclick="closeGroupSettings(); return false;"><b>Abbrechen</b><i></i></a>
    </div>
  </div>
</form>

<div class="clear"></div>

<script type="text/javascript" language="JavaScript">
    L10N.put("group.settings.title.text", "Edit Group Settings");
    L10N.put("group.settings.group_type_change_warning.normal", "Möchtest du den Gruppentyp wirklich auf <strong\>Normal</strong\> ändern?");
    L10N.put("group.settings.group_type_change_warning.exclusive", "Möchtest du den Gruppentyp wirklich auf <strong \>Exklusiv</strong\> ändern?");
    L10N.put("group.settings.group_type_change_warning.closed", "Möchtest du den Gruppentyp wirklich auf <strong\>Privat</strong\> ändern?");
    L10N.put("group.settings.group_type_change_warning.large", "Möchtest du den Gruppentyp wirklich auf <strong\>Unendlich</strong\> ändern? Unwiederruflich!");
    L10N.put("myhabbo.groups.confirmation_ok", "Ok");
    L10N.put("myhabbo.groups.confirmation_cancel", "Abbrechen");
    switchGroupSettingsTab(null, "group");
</script>
