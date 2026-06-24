<div class="hk-wrapper">
  <div class="topbar">
    Logged in as: <strong>{{ playerDetails.getName() }}</strong>
    <a href="{{ site.sitePath }}">{{ locale.housekeeping_base_navigation_havana_web }}</a>
  </div>

  <div class="tabs">
    <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}" class="tab{% if dashboardActive %} active{% endif %}">{{ locale.housekeeping_base_navigation_dashboard }}</a>
    <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/users/search" class="tab{% if searchUsersActive or createUserActive or bansActive or searchTransactionsActive or badgesActive %} active{% endif %}">{{ locale.housekeeping_base_navigation_users }}</a>
    <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/pages" class="tab{% if cataloguePagesActive or catalogueItemsActive or cataloguePackagesActive or catalogueSaleBadgesActive or catalogueCollectablesActive or editCatalogueFrontPage or itemDefinitionsActive or vouchersActive %} active{% endif %}">{{ locale.housekeeping_base_navigation_catalogue }}</a>
    <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/articles" class="tab{% if configurationsActive or wordfilterActive or recyclerRewardsActive or roomAdsActive or roomBadgesActive or infobusPollsActive %} active{% endif %}">{{ locale.housekeeping_base_navigation_site }}</a>
  </div>

  <div class="container">
    <div class="sidebar">
      <div class="box">
        <p>{{ locale.housekeeping_base_navigation_dashboard }}</p>
        <ul>
          <li><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}">{{ locale.housekeeping_base_navigation_home }}</a></li>
          <li><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/logout">{{ locale.housekeeping_base_navigation_logout }}</a></li>
        </ul>
      </div>

      {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'user/create') or housekeepingManager.hasPermission(playerDetails.getRank(), 'user/search') or housekeepingManager.hasPermission(playerDetails.getRank(), 'bans') or housekeepingManager.hasPermission(playerDetails.getRank(), 'transaction/lookup') or housekeepingManager.hasPermission(playerDetails.getRank(), 'badges') %}
      <div class="box">
        <p>{{ locale.housekeeping_base_navigation_users }}</p>
        <ul>
          {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'user/search') %}
          <li><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/users/search">{{ locale.housekeeping_base_navigation_search_users }}</a></li>
          {% endif %}
          {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'user/create') %}
          <li><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/users/create">{{ locale.housekeeping_base_navigation_create_new_user }}</a></li>
          {% endif %}
          {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'bans') %}
          <li><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/bans">{{ locale.housekeeping_base_navigation_ban_management }}</a></li>
          {% endif %}
          {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'transaction/lookup') %}
          <li><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/transaction/lookup">{{ locale.housekeeping_base_navigation_transaction_lookup }}</a></li>
          {% endif %}
          {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'badges') %}
          <li><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/badges">{{ locale.housekeeping_base_navigation_badges }}</a></li>
          {% endif %}
        </ul>
      </div>
      {% endif %}

      {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'catalogue/manage') or housekeepingManager.hasPermission(playerDetails.getRank(), 'catalogue/edit_frontpage') or housekeepingManager.hasPermission(playerDetails.getRank(), 'item_definitions/manage') or housekeepingManager.hasPermission(playerDetails.getRank(), 'vouchers/manage') %}
      <div class="box">
        <p>{{ locale.housekeeping_base_navigation_catalogue }}</p>
        <ul>
          {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'catalogue/edit_frontpage') %}
          <li><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/edit_frontpage">{{ locale.housekeeping_base_navigation_catalogue_frontpage }}</a></li>
          {% endif %}
          {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'catalogue/manage') %}
          <li><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/pages">{{ locale.housekeeping_base_navigation_catalogue_pages }}</a></li>
          <li><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/items">{{ locale.housekeeping_base_navigation_catalogue_items }}</a></li>
          <li><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/packages">{{ locale.housekeeping_base_navigation_catalogue_packages }}</a></li>
          <li><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/sale_badges">{{ locale.housekeeping_base_navigation_catalogue_sale_badges }}</a></li>
          <li><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/collectables">{{ locale.housekeeping_base_navigation_catalogue_collectables }}</a></li>
          {% endif %}
          {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'item_definitions/manage') %}
          <li><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/item_definitions">{{ locale.housekeeping_base_navigation_item_definitions }}</a></li>
          {% endif %}
          {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'vouchers/manage') %}
          <li><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/vouchers">{{ locale.housekeeping_base_navigation_vouchers }}</a></li>
          {% endif %}
        </ul>
      </div>
      {% endif %}

      {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'configuration') or housekeepingManager.hasPermission(playerDetails.getRank(), 'articles/create') or housekeepingManager.hasPermission(playerDetails.getRank(), 'wordfilter/manage') or housekeepingManager.hasPermission(playerDetails.getRank(), 'recycler/manage') %}
      <div class="box">
        <p>{{ locale.housekeeping_base_navigation_site }}</p>
        <ul>
          {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'configuration') %}
          <li><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/configurations">{{ locale.housekeeping_base_navigation_configurations }}</a></li>
          {% endif %}
          {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'articles/create') %}
          <li><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/articles">{{ locale.housekeeping_base_navigation_news_articles }}</a></li>
          {% endif %}
          {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'wordfilter/manage') %}
          <li><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/wordfilter">{{ locale.housekeeping_base_navigation_wordfilter }}</a></li>
          {% endif %}
          {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'recycler/manage') %}
          <li><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/recycler_rewards">{{ locale.housekeeping_base_navigation_recycler_rewards }}</a></li>
          {% endif %}
        </ul>
      </div>
      {% endif %}

      {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'room_ads') or housekeepingManager.hasPermission(playerDetails.getRank(), 'room_badges') or housekeepingManager.hasPermission(playerDetails.getRank(), 'infobus') or housekeepingManager.hasPermission(playerDetails.getRank(), 'room_categories/manage') or housekeepingManager.hasPermission(playerDetails.getRank(), 'room_models/manage') or housekeepingManager.hasPermission(playerDetails.getRank(), 'rooms/manage') or housekeepingManager.hasPermission(playerDetails.getRank(), 'groups/manage') %}
      <div class="box">
        <p>{{ locale.housekeeping_base_navigation_hotel }}</p>
        <ul>
          {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'room_ads') %}
          <li><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/room_ads">{{ locale.housekeeping_base_navigation_room_advertisements }}</a></li>
          {% endif %}
          {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'room_badges') %}
          <li><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/room_badges">{{ locale.housekeeping_base_navigation_room_badges }}</a></li>
          {% endif %}
          {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'infobus') %}
          <li><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/infobus_polls">{{ locale.housekeeping_base_navigation_infobus_polls }}</a></li>
          {% endif %}
          {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'room_categories/manage') %}
          <li><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/room_categories">{{ locale.housekeeping_base_navigation_room_categories }}</a></li>
          {% endif %}
          {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'room_models/manage') %}
          <li><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/room_models">{{ locale.housekeeping_base_navigation_room_models }}</a></li>
          {% endif %}
          {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'rooms/manage') %}
          <li><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/rooms">{{ locale.housekeeping_base_navigation_rooms }}</a></li>
          {% endif %}
          {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'groups/manage') %}
          <li><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/groups">{{ locale.housekeeping_base_navigation_groups }}</a></li>
          {% endif %}
        </ul>
      </div>
      {% endif %}

      <div class="time-box">
        <strong>{{ locale.housekeeping_base_navigation_havana_web }}</strong><br>
        {{ locale.housekeeping_base_header_housekeeping }}<br><br>
        <strong>{{ pageName }}</strong>
      </div>
    </div>

    <main class="column1">
      <h4>{{ pageName }}</h4>
