<div class="d-flex" id="wrapper">
    <div class="bg-light border-right" id="sidebar-wrapper">
      <div class="sidebar-heading">{{ locale.housekeeping_base_navigation_havana_web }} </div>
      <div class="list-group list-group-flush">
        <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}" class="list-group-item list-group-item-action {{ dashboardActive }}">{{ locale.housekeeping_base_navigation_dashboard }}</a>
		{% if housekeepingManager.hasPermission(playerDetails.getRank(), 'configuration') %}
        <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/configurations" class="list-group-item list-group-item-action {{ configurationsActive }}">{{ locale.housekeeping_base_navigation_configurations }}</a>
		{% endif %}
		
	    {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'bans') %}
        <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/bans" class="list-group-item list-group-item-action {{ bansActive }}">{{ locale.housekeeping_base_navigation_ban_management }}</a>
		{% endif %}
		
		{% if housekeepingManager.hasPermission(playerDetails.getRank(), 'room_ads') %}
        <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/room_ads" class="list-group-item list-group-item-action {{ roomAdsActive }}">{{ locale.housekeeping_base_navigation_room_advertisements }}</a>
		<!-- <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/room_ads/create" class="list-group-item list-group-item-action {{ roomCreateAdsActive }}">{{ locale.housekeeping_base_navigation_create_room_advertisements }}</a> -->
		{% endif %}
		
		
		{% if housekeepingManager.hasPermission(playerDetails.getRank(), 'room_badges') %}
        <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/room_badges" class="list-group-item list-group-item-action {{ roomBadgesActive }}">{{ locale.housekeeping_base_navigation_room_badges }}</a>
		<!-- <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/room_badges/create" class="list-group-item list-group-item-action {{ roomCreateBadgesActive }}">{{ locale.housekeeping_base_navigation_create_room_entry_badge }}</a> -->
		{% endif %}

		{% if housekeepingManager.hasPermission(playerDetails.getRank(), 'infobus') %}
        <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/infobus_polls" class="list-group-item list-group-item-action {{ infobusPollsActive }}">{{ locale.housekeeping_base_navigation_infobus_polls }}</a>
		<!-- <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/infobus_polls/create" class="list-group-item list-group-item-action {{ infobusPollsCreateActive }}">{{ locale.housekeeping_base_navigation_create_infobus_polls }}</a> -->
		{% endif %}
		
		{% if housekeepingManager.hasPermission(playerDetails.getRank(), 'articles/create') %}
        <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/articles" class="list-group-item list-group-item-action {{ articlesActive }}">{{ locale.housekeeping_base_navigation_news_articles }}</a>
		<!-- <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/articles/create" class="list-group-item list-group-item-action {{ createArticlesActive }}">{{ locale.housekeeping_base_navigation_post_news_article }}</a> -->
		{% endif %}
		
		{% if housekeepingManager.hasPermission(playerDetails.getRank(), 'users/create') %}
        <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/users/search" class="list-group-item list-group-item-action {{ searchUsersActive }}">{{ locale.housekeeping_base_navigation_search_users }}</a>
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/users/create" class="list-group-item list-group-item-action {{ createUserActive }}">{{ locale.housekeeping_base_navigation_create_new_user }}</a>
		{% endif %}
		
		{% if housekeepingManager.hasPermission(playerDetails.getRank(), 'transaction/lookup') %}
        <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/transaction/lookup" class="list-group-item list-group-item-action {{ searchTransactionsActive }}">{{ locale.housekeeping_base_navigation_transaction_lookup }}</a>
		{% endif %}
		
		{% if housekeepingManager.hasPermission(playerDetails.getRank(), 'catalogue/edit_frontpage') %}
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/edit_frontpage" class="list-group-item list-group-item-action {{ editCatalogueFrontPage }}">{{ locale.housekeeping_base_navigation_catalogue_frontpage }}</a>
		{% endif %}
		{% if housekeepingManager.hasPermission(playerDetails.getRank(), 'catalogue/manage') %}
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/pages" class="list-group-item list-group-item-action {{ cataloguePagesActive }}">{{ locale.housekeeping_base_navigation_catalogue_pages }}</a>
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/items" class="list-group-item list-group-item-action {{ catalogueItemsActive }}">{{ locale.housekeeping_base_navigation_catalogue_items }}</a>
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/packages" class="list-group-item list-group-item-action {{ cataloguePackagesActive }}">{{ locale.housekeeping_base_navigation_catalogue_packages }}</a>
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/sale_badges" class="list-group-item list-group-item-action {{ catalogueSaleBadgesActive }}">{{ locale.housekeeping_base_navigation_catalogue_sale_badges }}</a>
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/collectables" class="list-group-item list-group-item-action {{ catalogueCollectablesActive }}">{{ locale.housekeeping_base_navigation_catalogue_collectables }}</a>
		{% endif %}
		{% if housekeepingManager.hasPermission(playerDetails.getRank(), 'item_definitions/manage') %}
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/item_definitions" class="list-group-item list-group-item-action {{ itemDefinitionsActive }}">{{ locale.housekeeping_base_navigation_item_definitions }}</a>
		{% endif %}
		{% if housekeepingManager.hasPermission(playerDetails.getRank(), 'vouchers/manage') %}
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/vouchers" class="list-group-item list-group-item-action {{ vouchersActive }}">{{ locale.housekeeping_base_navigation_vouchers }}</a>
		{% endif %}
		{% if housekeepingManager.hasPermission(playerDetails.getRank(), 'wordfilter/manage') %}
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/wordfilter" class="list-group-item list-group-item-action {{ wordfilterActive }}">{{ locale.housekeeping_base_navigation_wordfilter }}</a>
		{% endif %}
		{% if housekeepingManager.hasPermission(playerDetails.getRank(), 'recycler/manage') %}
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/recycler_rewards" class="list-group-item list-group-item-action {{ recyclerRewardsActive }}">{{ locale.housekeeping_base_navigation_recycler_rewards }}</a>
		{% endif %}
		{% if housekeepingManager.hasPermission(playerDetails.getRank(), 'room_categories/manage') %}
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/room_categories" class="list-group-item list-group-item-action {{ roomCategoriesActive }}">{{ locale.housekeeping_base_navigation_room_categories }}</a>
		{% endif %}
		{% if housekeepingManager.hasPermission(playerDetails.getRank(), 'room_models/manage') %}
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/room_models" class="list-group-item list-group-item-action {{ roomModelsActive }}">{{ locale.housekeeping_base_navigation_room_models }}</a>
		{% endif %}
		{% if housekeepingManager.hasPermission(playerDetails.getRank(), 'rooms/manage') %}
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/rooms" class="list-group-item list-group-item-action {{ roomsActive }}">{{ locale.housekeeping_base_navigation_rooms }}</a>
		{% endif %}
		{% if housekeepingManager.hasPermission(playerDetails.getRank(), 'groups/manage') %}
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/groups" class="list-group-item list-group-item-action {{ groupsActive }}">{{ locale.housekeeping_base_navigation_groups }}</a>
		{% endif %}
		{% if housekeepingManager.hasPermission(playerDetails.getRank(), 'badges') %}
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/badges" class="list-group-item list-group-item-action {{ badgesActive }}">{{ locale.housekeeping_base_navigation_badges }}</a>
		{% endif %}
      </div>
    </div>
    <div id="page-content-wrapper">

      <nav class="navbar navbar-expand-lg navbar-light bg-light border-bottom">
        <button class="btn btn-primary" id="menu-toggle">{{ locale.housekeeping_base_navigation_toggle_menu }}</button>

        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="{{ locale.housekeeping_base_navigation_toggle_navigation }}">
          <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav ml-auto mt-2 mt-lg-0">
            <li class="nav-item active">
              <a class="nav-link" href="{{ site.sitePath }}/{{ site.housekeepingPath }}">{{ locale.housekeeping_base_navigation_home }}</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="{{ site.sitePath }}/{{ site.housekeepingPath }}/logout">{{ locale.housekeeping_base_navigation_logout }}</a>
            </li>
            <!-- 
			<li class="nav-item dropdown">
              <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                {{ locale.housekeeping_base_navigation_dropdown }}
              </a>
              <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
                <a class="dropdown-item" href="#">{{ locale.housekeeping_base_navigation_action }}</a>
                <a class="dropdown-item" href="#">{{ locale.housekeeping_base_navigation_another_action }}</a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="#">{{ locale.housekeeping_base_navigation_something_else_here }}</a>
              </div>
            </li>
			-->
          </ul>
        </div>
      </nav>

      <div class="container-fluid">
