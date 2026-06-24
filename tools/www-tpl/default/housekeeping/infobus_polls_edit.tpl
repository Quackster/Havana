{% include "housekeeping/base/header.tpl" %}
  <body>
	{% set infobusPollsCreateActive = " active " %}
	{% include "housekeeping/base/navigation.tpl" %}
		<script type="text/javascript">
		var lastID = {{ poll.getPollData().getAnswers().size() }};
		
		function insertAfter(newNode, referenceNode) {
			referenceNode.parentNode.insertBefore(newNode, referenceNode.nextSibling);
		}
				
		function cloneAnswer() {
			var div = document.getElementById('answer' + lastID);
			lastID++;
			clone = div.cloneNode(true); // true means clone all childNodes and all event handlers
			clone.id = "answer" + lastID;
			clone.childNodes[1].id = 'answer' + lastID + 'title';
			insertAfter(clone, div);
			document.getElementById('answer' + lastID + 'title').innerHTML = "{{ locale.housekeeping_infobus_answer|escape('js') }} " + lastID;
		}
		
		function removeLatestAnswer() {
			if (lastID > 1) {
				var div = document.getElementById('answer' + lastID);
				div.parentNode.removeChild(div);
				lastID--;
			}
		}
		</script>
	
		<h1 class="mt-4">{{ locale.housekeeping_infobus_edit_infobus_poll }}</h1>
		{% include "housekeeping/base/alert.tpl" %}
		<p>{{ locale.housekeeping_infobus_edit_an_infobus_poll }}</p>
		<form class="table-responsive col-md-4" method="post">
			<div class="form-group">
				<label>{{ locale.housekeeping_infobus_question_text }}</label>
				<input type="text" class="form-control" id="text" name="question" value="{{ poll.getPollData().getQuestion() }}"></input>
			</div>
			{% set answerCount = 1 %}
			{% for answer in poll.getPollData().getAnswers() %}
			<div class="form-group" id="answer{{ answerCount }}">
				<label id="answer{{ answerCount }}title">{{ locale.housekeeping_infobus_answer }} {{ answerCount }}</label>
				<input name="answers[]" type="text" class="form-control" value="{{ answer }}">
			</div>
			{% set answerCount = answerCount + 1 %}
			{% endfor %}
			
			<p><button type="button" class="btn btn-success" onclick="cloneAnswer()">{{ locale.housekeeping_infobus_add_answer }}</button></p>
			<p><button type="button" class="btn btn-primary" onclick="removeLatestAnswer()">{{ locale.housekeeping_infobus_remove_answer }}</button></p>
			
			<div class="form-group"> 
				<input type="hidden" id="text" name="creator" value="{{ playerDetails.id }}">
				<button type="submit" class="btn btn-info">{{ locale.housekeeping_infobus_save_poll }}</button>
			</div>
		</form>
      </div>
    </div>
  </div>
  <script src="{{ site.staticContentPath }}/public/hk/js/jquery-3.1.1.slim.min.js"></script>
  <script src="{{ site.staticContentPath }}/public/hk/js/bootstrap.bundle.min.js"></script>
  <script>
    $("#menu-toggle").click(function(e) {
      e.preventDefault();
      $("#wrapper").toggleClass("toggled");
    });
  </script>
</body>
</html>