<form action="#" method="post" id="webstore-notes-form">

<input type="hidden" name="maxlength" id="webstore-notes-maxlength" value="500" />

<div id="webstore-notes-counter">500</div>

<p>
	<select id="webstore-notes-skin" name="skin">
			<option value="1" id="webstore-notes-skins-select-defaultskin"{{ skin1Selected }}>{{ locale.homes_editor_default }}</option>
			<option value="6" id="webstore-notes-skins-select-goldenskin"{{ skin6Selected }}>{{ locale.homes_editor_golden }}</option>
			<option value="3" id="webstore-notes-skins-select-metalskin"{{ skin3Selected }}>{{ locale.homes_editor_metal }}</option>
			<option value="5" id="webstore-notes-skins-select-notepadskin"{{ skin5Selected }}>{{ locale.homes_editor_notepad }}</option>
			<option value="2" id="webstore-notes-skins-select-speechbubbleskin"{{ skin2Selected }}>{{ locale.homes_editor_speech_bubble }}</option>
			<option value="4" id="webstore-notes-skins-select-noteitskin"{{ skin4Selected }}>{{ locale.homes_editor_stickie_note }}</option>
			
			{% if playerDetails.hasClubSubscription() %}
			<option value="8" id="webstore-notes-skins-select-hc_pillowskin"{{ skin8Selected }}>{{ locale.homes_editor_hc_bling }}</option>
			<option value="7" id="webstore-notes-skins-select-hc_machineskin"{{ skin7Selected }}>{{ locale.homes_editor_hc_scifi }}</option>
			{% endif %}
	</select>
</p>

<p class="warning">{{ locale.homes_editor_note_the_text_is_not_editable_after_you_place_the_note_to_your_page }}</p>

<div id="webstore-notes-edit-container">
<textarea id="webstore-notes-text" rows="7" cols="42" name="noteText">{{ noteText }}</textarea>
    <script type="text/javascript">
        bbcodeToolbar = new Control.TextArea.ToolBar.BBCode("webstore-notes-text");
        bbcodeToolbar.toolbar.toolbar.id = "bbcode_toolbar";
		        var colors = { "red" : ["#d80000", "Red"],
            "orange" : ["#fe6301", "Orange"],
            "yellow" : ["#ffce00", "Yellow"],
            "green" : ["#6cc800", "Green"],
            "cyan" : ["#00c6c4", "Cyan"],
            "blue" : ["#0070d7", "Blue"],
            "gray" : ["#828282", "Gray"],
            "black" : ["#000000", "Black"]
        };
        bbcodeToolbar.addColorSelect("Color", colors, true);
    </script>
<div id="linktool">
    <div id="linktool-scope">
        <label for="linktool-query-input">{{ locale.homes_editor_create_link }}</label>
        <input type="radio" name="scope" class="linktool-scope" value="1" checked="checked"/>{{ locale.homes_editor_habbos }}        <input type="radio" name="scope" class="linktool-scope" value="2"/>{{ locale.homes_editor_rooms }}        <input type="radio" name="scope" class="linktool-scope" value="3"/>{{ locale.homes_editor_groups }}    </div>
    <input id="linktool-query" type="text" name="query" value=""/>
    <a href="#" class="new-button" id="linktool-find"><b>{{ locale.homes_editor_find }}</b><i></i></a>
    <div class="clear" style="height: 0;"><!-- --></div>
    <div id="linktool-results" style="display: none">
    </div>
    <script type="text/javascript">
        linkTool = new LinkTool(bbcodeToolbar.textarea);
    </script>
</div>
</div>

</form>

<p>
<a href="#" class="new-button" id="webstore-confirm-cancel"><b>{{ locale.homes_editor_cancel }}</b><i></i></a>
<a href="#" class="new-button" id="webstore-notes-continue"><b>{{ locale.homes_editor_continue }}</b><i></i></a>
</p>

<div class="clear"></div>