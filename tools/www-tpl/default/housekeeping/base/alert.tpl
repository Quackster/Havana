{% if alert.hasAlert %}

<div class="message-box {{ alert.colour }}">
  {{ alert.message }}
  <button class="close-btn" onclick="closeMessageBox(event)" type="button">x</button>
</div>


{% endif %}
