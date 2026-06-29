<div id="staffpicks-rooms-habblet-list-container" class="habblet-list-container groups-list">
    <ul class="habblet-list">
{% set num = 0 %}
{% for room in rooms %}
    {% set occupancyLevel = 0 %}
    {% if room.getData().getVisitorsNow() > 0 %}
        {% set occupancyLevel = 2 %}
    {% endif %}
    {% if num % 2 == 0 %}
        <li class="even room-occupancy-{{ occupancyLevel }}" roomid="{{ room.getData().getId() }}">
    {% else %}
        <li class="odd room-occupancy-{{ occupancyLevel }}" roomid="{{ room.getData().getId() }}">
    {% endif %}
            <div>
                <span class="room-name"><a href="{{ site.sitePath }}/client?forwardId=2&amp;roomId={{ room.getData().getId() }}" onclick="HabboClient.roomForward(this, '{{ room.getData().getId() }}', 'private'); return false;" target="client">{{ room.getData().getName() }}</a></span>
                <span class="room-owner"><a href="{{ site.sitePath }}/home/{{ room.getData().getOwnerName() }}">{{ room.getData().getOwnerName() }}</a></span>
                <p>{{ room.getData().getDescription() }}</p>
            </div>
        </li>
    {% set num = num + 1 %}
{% endfor %}
    </ul>
</div>
