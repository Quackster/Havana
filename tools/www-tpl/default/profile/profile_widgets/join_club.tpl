{% if playerDetails.hasClubSubscription() == false %}
	<div class="cbb habboclub-tryout">
        <h2 class="title">{{ locale.profile_profile_widgets_join }} {{ site.siteName }} {{ locale.profile_profile_widgets_club }}</h2>
        <div class="box-content">
            <div class="habboclub-banner-container habboclub-clothes-banner"></div>
            <p class="habboclub-header">{{ site.siteName }} {{ locale.profile_profile_widgets_club_is_our_vip_members_only_club_absolutely_no_riff_raff_admitted_members_enjoy_a_wide_range_of_benefits_including_exclusive_clothes_free_gifts_and_an_extended_friend_list }}</p>

            <p class="habboclub-link"><a href="{{ site.sitePath }}/club">{{ locale.profile_profile_widgets_check_out }} {{ site.siteName }} {{ locale.profile_profile_widgets_club_gt_gt }}</a></p>
        </div>
    </div>
{% endif %}