{% if session.loggedIn %}

<p>
{% if playerDetails.hasClubSubscription() %}
Te quedan {{ hcDays }} días de Club {{ site.siteName }}.
{% else %}
No eres miembro del club {{ site.siteName }}.

{% endif %}
</p>

{% endif %}