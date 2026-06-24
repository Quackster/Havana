{% if session.loggedIn %}

<p>
{% if playerDetails.hasClubSubscription() %}
{{ locale.habblet_habboclubenddate_you_have }} {{ hcDays }} {{ site.siteName }} {{ locale.habblet_habboclubenddate_club_day_s_left }}
{% else %}
{{ locale.habblet_habboclubenddate_you_are_not_a_member_of }} {{ site.siteName }} {{ locale.habblet_habboclubenddate_club }}

{% endif %}
</p>

{% endif %}