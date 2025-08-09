<form action="#" method="post" id="webstore-notes-form">

<input type="hidden" name="maxlength" id="webstore-notes-maxlength" value="500" />

<div id="webstore-notes-counter">500</div>

<p>
	<select id="webstore-notes-skin" name="skin">
			<option value="1" id="webstore-notes-skins-select-defaultskin"{{ skin1Selected }}>Ohne Rand</option>
			<option value="6" id="webstore-notes-skins-select-goldenskin"{{ skin6Selected }}>Gold</option>
			<option value="3" id="webstore-notes-skins-select-metalskin"{{ skin3Selected }}>Metall</option>
			<option value="5" id="webstore-notes-skins-select-notepadskin"{{ skin5Selected }}>Notizblock</option>
			<option value="2" id="webstore-notes-skins-select-speechbubbleskin"{{ skin2Selected }}>Sprechblase</option>
			<option value="4" id="webstore-notes-skins-select-noteitskin"{{ skin4Selected }}>Klebezettel</option>
			
			{% if playerDetails.hasClubSubscription() %}
			<option value="8" id="webstore-notes-skins-select-hc_pillowskin"{{ skin8Selected }}>HC Pink</option>
			<option value="7" id="webstore-notes-skins-select-hc_machineskin"{{ skin7Selected }}>HC Rare</option>
			{% endif %}
	</select>
</p>

<p class="warning">Bedenke! Der Text kann nicht mehr bearbeitet werden, nachdem du die Notiz auf deiner Seite platziert hast.</p>

<div id="webstore-notes-edit-container">
<textarea id="webstore-notes-text" rows="7" cols="42" name="noteText">{{ noteText }}</textarea>
    <script type="text/javascript">
        bbcodeToolbar = new Control.TextArea.ToolBar.BBCode("webstore-notes-text");
        bbcodeToolbar.toolbar.toolbar.id = "bbcode_toolbar";
		        var colors = { "red" : ["#d80000", "Rot"],
            "orange" : ["#fe6301", "Orange"],
            "yellow" : ["#ffce00", "Gelb"],
            "green" : ["#6cc800", "Grün"],
            "cyan" : ["#00c6c4", "Cyan"],
            "blue" : ["#0070d7", "Blau"],
            "gray" : ["#828282", "Grau"],
            "black" : ["#000000", "Schwarz"]
        };
        bbcodeToolbar.addColorSelect("Farbe", colors, true);
    </script>
<div id="linktool">
    <div id="linktool-scope">
        <label for="linktool-query-input">Link:</label>
        <input type="radio" name="scope" class="linktool-scope" value="1" checked="checked"/>Habbos        <input type="radio" name="scope" class="linktool-scope" value="2"/>Räume        <input type="radio" name="scope" class="linktool-scope" value="3"/>Gruppen    </div>
    <input id="linktool-query" type="text" name="query" value=""/>
    <a href="#" class="new-button" id="linktool-find"><b>Suche</b><i></i></a>
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
<a href="#" class="new-button" id="webstore-confirm-cancel"><b>Abbrechen</b><i></i></a>
<a href="#" class="new-button" id="webstore-notes-continue"><b>Posten</b><i></i></a>
</p>

<div class="clear"></div>