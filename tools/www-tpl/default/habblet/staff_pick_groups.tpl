<div id="staffpicks-groups-habblet-list-container" class="habblet-list-container groups-list">
    <ul class="habblet-list two-cols clearfix">
        {% set position = "right" %}

        {% set i = 1 %}
        {% set lefts = 0 %}
        {% set rights = 0 %}
        {% for group in groups %}
            {% if i % 2 == 0 %}
                {% set position = "right" %}
                {% set rights = rights + 1 %}
            {% else %}
                {% set position = "left" %}
                {% set lefts = lefts + 1 %}
            {% endif %}

            {% if lefts % 2 == 0 %}
                {% set status = "even" %}
            {% else %}
                {% set status = "odd" %}
            {% endif %}

            {% set i = i + 1 %}
            <li class="{{ status }} {{ position }}" style="background-image: url({{ site.sitePath }}/habbo-imaging/badge/{{ group.badge }}.gif)">
                <a class="item" href="{{ group.generateClickLink() }}">{{ group.getName }}</a>
            </li>
        {% endfor %}
    </ul>
</div>
