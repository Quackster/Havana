<div class="panel">
  <div class="header_left">&nbsp;<br />&nbsp;<br />&nbsp;<br /><a href="{{ site.sitePath }}"><img src="{{ site.staticContentPath }}/housekeeping/images/header_logo.png" alt="{{ site.siteName }}"></a></div>
  <div class="header_right"><img src="{{ site.staticContentPath }}/housekeeping/images/header_tm1.gif" alt=""></div>

  <div class="panel_title">
    <span class="text">{{ locale.housekeeping_base_navigation_havana_web }} {{ locale.housekeeping_base_header_housekeeping }}</span>
    <div class="close_button"><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/logout"><img src="{{ site.staticContentPath }}/housekeeping/images/button_close.gif" alt="{{ locale.housekeeping_base_navigation_logout }}"></a></div>
  </div>

  <div class="panel_header">
    <ul {% if dashboardActive %}class="selected"{% endif %}>
      <li class="top"><div><a href="#">{{ locale.housekeeping_base_navigation_dashboard }}</a></div></li>
      <li class="item"><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}">{{ locale.housekeeping_base_navigation_home }}</a></li>
      <li class="item"><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/logout">{{ locale.housekeeping_base_navigation_logout }}</a></li>
    </ul>
    <div class="border"></div>

    <ul {% if configurationsActive or wordfilterActive or recyclerRewardsActive %}class="selected"{% endif %}>
      <li class="top"><div><a href="#">{{ locale.housekeeping_base_navigation_site }}</a></div></li>
      {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'configuration') %}
      <li class="item"><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/configurations">{{ locale.housekeeping_base_navigation_configurations }}</a></li>
      {% endif %}
      {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'articles/create') %}
      <li class="item"><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/articles">{{ locale.housekeeping_base_navigation_news_articles }}</a></li>
      {% endif %}
      {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'wordfilter/manage') %}
      <li class="item"><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/wordfilter">{{ locale.housekeeping_base_navigation_wordfilter }}</a></li>
      {% endif %}
      {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'recycler/manage') %}
      <li class="item"><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/recycler_rewards">{{ locale.housekeeping_base_navigation_recycler_rewards }}</a></li>
      {% endif %}
    </ul>
    <div class="border"></div>

    <ul {% if cataloguePagesActive or catalogueItemsActive or cataloguePackagesActive or catalogueSaleBadgesActive or catalogueCollectablesActive or editCatalogueFrontPage or itemDefinitionsActive or vouchersActive %}class="selected"{% endif %}>
      <li class="top"><div><a href="#">{{ locale.housekeeping_base_navigation_catalogue }}</a></div></li>
      {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'catalogue/edit_frontpage') %}
      <li class="item"><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/edit_frontpage">{{ locale.housekeeping_base_navigation_catalogue_frontpage }}</a></li>
      {% endif %}
      {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'catalogue/manage') %}
      <li class="item"><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/pages">{{ locale.housekeeping_base_navigation_catalogue_pages }}</a></li>
      <li class="item"><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/items">{{ locale.housekeeping_base_navigation_catalogue_items }}</a></li>
      <li class="item"><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/packages">{{ locale.housekeeping_base_navigation_catalogue_packages }}</a></li>
      <li class="item"><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/sale_badges">{{ locale.housekeeping_base_navigation_catalogue_sale_badges }}</a></li>
      <li class="item"><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/collectables">{{ locale.housekeeping_base_navigation_catalogue_collectables }}</a></li>
      {% endif %}
      {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'item_definitions/manage') %}
      <li class="item"><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/item_definitions">{{ locale.housekeeping_base_navigation_item_definitions }}</a></li>
      {% endif %}
      {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'vouchers/manage') %}
      <li class="item"><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/vouchers">{{ locale.housekeeping_base_navigation_vouchers }}</a></li>
      {% endif %}
    </ul>
    <div class="border"></div>

    <ul {% if bansActive or searchUsersActive or createUserActive or searchTransactionsActive or roomAdsActive or roomBadgesActive or infobusPollsActive or roomCategoriesActive or roomModelsActive or roomsActive or groupsActive or badgesActive %}class="selected"{% endif %}>
      <li class="top"><div><a href="#">{{ locale.housekeeping_base_navigation_hotel }}</a></div></li>
      {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'bans') %}
      <li class="item"><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/bans">{{ locale.housekeeping_base_navigation_ban_management }}</a></li>
      {% endif %}
      {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'users/create') %}
      <li class="item"><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/users/search">{{ locale.housekeeping_base_navigation_search_users }}</a></li>
      <li class="item"><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/users/create">{{ locale.housekeeping_base_navigation_create_new_user }}</a></li>
      {% endif %}
      {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'transaction/lookup') %}
      <li class="item"><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/transaction/lookup">{{ locale.housekeeping_base_navigation_transaction_lookup }}</a></li>
      {% endif %}
      {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'room_ads') %}
      <li class="item"><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/room_ads">{{ locale.housekeeping_base_navigation_room_advertisements }}</a></li>
      {% endif %}
      {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'room_badges') %}
      <li class="item"><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/room_badges">{{ locale.housekeeping_base_navigation_room_badges }}</a></li>
      {% endif %}
      {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'infobus') %}
      <li class="item"><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/infobus_polls">{{ locale.housekeeping_base_navigation_infobus_polls }}</a></li>
      {% endif %}
      {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'room_categories/manage') %}
      <li class="item"><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/room_categories">{{ locale.housekeeping_base_navigation_room_categories }}</a></li>
      {% endif %}
      {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'room_models/manage') %}
      <li class="item"><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/room_models">{{ locale.housekeeping_base_navigation_room_models }}</a></li>
      {% endif %}
      {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'rooms/manage') %}
      <li class="item"><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/rooms">{{ locale.housekeeping_base_navigation_rooms }}</a></li>
      {% endif %}
      {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'groups/manage') %}
      <li class="item"><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/groups">{{ locale.housekeeping_base_navigation_groups }}</a></li>
      {% endif %}
      {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'badges') %}
      <li class="item"><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/badges">{{ locale.housekeeping_base_navigation_badges }}</a></li>
      {% endif %}
    </ul>
  </div>
  <div class="clear"></div>
  <div class="topborder"></div>
  <div class="page_title">
    <img src="{{ site.staticContentPath }}/housekeeping/images/icons/dashboard.png" class="pticon" alt="">
    <span class="page_name_shadow">{{ pageName }}</span>
    <span class="page_name">{{ pageName }}</span>
  </div>
  <div class="page_main">
    <table class="page_main_layout" border="0" cellpadding="0" cellspacing="0" height="100%">
      <tbody>
        <tr height="100%">
          <td class="page_main_left">
            <div class="left_date">{{ pageName }}</div>
            <div class="hr"></div>
            <div class="loginuser">{{ locale.housekeeping_base_navigation_havana_web }}</div>
            <div class="hr"></div>
            <div class="text">
              <b>{{ locale.housekeeping_base_navigation_dashboard }}</b><br />
              <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}">{{ locale.housekeeping_base_navigation_home }}</a><br />
              <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/logout">{{ locale.housekeeping_base_navigation_logout }}</a>
            </div>
            {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'users/create') or housekeepingManager.hasPermission(playerDetails.getRank(), 'bans') or housekeepingManager.hasPermission(playerDetails.getRank(), 'transaction/lookup') %}
            <div class="hr"></div>
            <div class="text">
              <b>{{ locale.housekeeping_base_navigation_users }}</b><br />
              {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'users/create') %}
              <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/users/search">{{ locale.housekeeping_base_navigation_search_users }}</a><br />
              <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/users/create">{{ locale.housekeeping_base_navigation_create_new_user }}</a><br />
              {% endif %}
              {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'bans') %}
              <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/bans">{{ locale.housekeeping_base_navigation_ban_management }}</a><br />
              {% endif %}
              {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'transaction/lookup') %}
              <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/transaction/lookup">{{ locale.housekeeping_base_navigation_transaction_lookup }}</a>
              {% endif %}
            </div>
            {% endif %}
            {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'catalogue/manage') or housekeepingManager.hasPermission(playerDetails.getRank(), 'catalogue/edit_frontpage') or housekeepingManager.hasPermission(playerDetails.getRank(), 'vouchers/manage') %}
            <div class="hr"></div>
            <div class="text">
              <b>{{ locale.housekeeping_base_navigation_catalogue }}</b><br />
              {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'catalogue/edit_frontpage') %}
              <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/edit_frontpage">{{ locale.housekeeping_base_navigation_catalogue_frontpage }}</a><br />
              {% endif %}
              {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'catalogue/manage') %}
              <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/pages">{{ locale.housekeeping_base_navigation_catalogue_pages }}</a><br />
              <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/items">{{ locale.housekeeping_base_navigation_catalogue_items }}</a><br />
              <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/packages">{{ locale.housekeeping_base_navigation_catalogue_packages }}</a><br />
              {% endif %}
              {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'vouchers/manage') %}
              <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/vouchers">{{ locale.housekeeping_base_navigation_vouchers }}</a>
              {% endif %}
            </div>
            {% endif %}
            {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'configuration') or housekeepingManager.hasPermission(playerDetails.getRank(), 'articles/create') or housekeepingManager.hasPermission(playerDetails.getRank(), 'wordfilter/manage') %}
            <div class="hr"></div>
            <div class="text">
              <b>{{ locale.housekeeping_base_navigation_site }}</b><br />
              {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'configuration') %}
              <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/configurations">{{ locale.housekeeping_base_navigation_configurations }}</a><br />
              {% endif %}
              {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'articles/create') %}
              <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/articles">{{ locale.housekeeping_base_navigation_news_articles }}</a><br />
              {% endif %}
              {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'wordfilter/manage') %}
              <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/wordfilter">{{ locale.housekeeping_base_navigation_wordfilter }}</a>
              {% endif %}
            </div>
            {% endif %}
          </td>
          <td class="page_main_right">
            <div class="page_content">
