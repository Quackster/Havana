						<div id="highscores-habblet-list-container-h116" class="habblet-list-container">

<div class="box-content">
	<ul id="highscores-habblet-games-h116" class="highscores-habblet-games">
		<li{% if gameId == 1 %} class="selected"{% endif %}><a href="#" id="highscores-habblet-games-h116-1" class="highscores-habblet-game-link">{{ locale.habblet_personalhighscores_battleball_rebound }}</a></li>
		<li{% if gameId == 2 %} class="selected"{% endif %}><a href="#" id="highscores-habblet-games-h116-2" class="highscores-habblet-game-link">{{ locale.habblet_personalhighscores_snowstorm }}</a></li>
		<li{% if gameId == 0 %} class="selected"{% endif %}><a href="#" id="highscores-habblet-games-h116-0" class="highscores-habblet-game-link">{{ locale.habblet_personalhighscores_wobble_squabble }}</a></li>
	</ul>
</div>

<table class="highscores-habblet-scores">
<thead>
	<tr>
		{% if scoreEntries|length > 0 %}
		<th class="scores-position"></th>
		<th class="scores-name"></th>
		<th class="scores-points">{{ locale.habblet_personalhighscores_score }}</th>
		{% endif %}
	</tr>
</thead>
<tbody>
{% if scoreEntries|length == 0 %}
<div class="box-content">
{{ locale.habblet_personalhighscores_there_are_no_scores_recorded_for_this_game }}
</div>
{% else %}
{% set num = 1 %}
{% for scoreEntry in scoreEntries %}
	{% if num % 2 == 0 %}
	<tr class="even">
	{% else %}
	<tr class="odd">
	{% endif %}

		<td class="scores-position">{{ scoreEntry.getPosition() }}.</td>
		<td class="scores-name"><a href="/home/{{ scoreEntry.getPlayerName() }}">{{ scoreEntry.getPlayerName() }}</a></td>
		<td class="scores-points">{{ scoreEntry.getScore() }}</td>
	</tr>
	
{% set num = num + 1 %}
{% endfor %}
{% endif %}
</tbody>
</table>

<div id="habblet-paging-h116" class="highscores-habblet-paging clearfix">
		{% if hasNextPage %}
		<a href="#" class="list-paging-link next" id="h116-list-next">{{ locale.habblet_personalhighscores_next }}</a>
		{% endif %}
		
		{% if pageNumber > 1 %}
		<a href="#" class="list-paging-link previous" id="h116-list-previous">{{ locale.habblet_personalhighscores_previous }}</a>
		{% endif %}
</div>

<input type="hidden" id="h116-pageNumber" value="{{ pageNumber }}"/>
<input type="hidden" id="h116-gameId" value="{{ gameId }}"/>

</div>