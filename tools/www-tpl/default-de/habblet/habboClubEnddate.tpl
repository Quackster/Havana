{% if session.loggedIn %}

<p>
{% if playerDetails.hasClubSubscription() %}
Du hast noch {{ hcDays }} {{ site.siteName }} Club Tage.
{% else %}
Du bist kein Mitglied vom {{ site.siteName }} Club

{% endif %}
</p>

{% endif %}