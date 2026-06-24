<div class="d-flex" id="wrapper">
    <div class="bg-light border-right" id="sidebar-wrapper">
      <div class="sidebar-heading">Havana Web </div>
      <div class="list-group list-group-flush">
        <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}" class="list-group-item list-group-item-action {{ dashboardActive }}">Painel Inicial</a>
		{% if housekeepingManager.hasPermission(playerDetails.getRank(), 'configuration') %}
        <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/configurations" class="list-group-item list-group-item-action {{ configurationsActive }}">Configurações</a>
		{% endif %}
		
	    {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'bans') %}
        <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/bans" class="list-group-item list-group-item-action {{ bansActive }}">Gestão de banimentos</a>
		{% endif %}
		
		{% if housekeepingManager.hasPermission(playerDetails.getRank(), 'room_ads') %}
        <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/room_ads" class="list-group-item list-group-item-action {{ roomAdsActive }}">Anúncios nos quartos</a>
		<!-- <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/room_ads/create" class="list-group-item list-group-item-action {{ roomCreateAdsActive }}">Criar anúncio</a> -->
		{% endif %}
		
		
		{% if housekeepingManager.hasPermission(playerDetails.getRank(), 'room_badges') %}
        <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/room_badges" class="list-group-item list-group-item-action {{ roomBadgesActive }}">Emblemas de quartos</a>
		<!-- <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/room_badges/create" class="list-group-item list-group-item-action {{ roomCreateBadgesActive }}">Criar novo emblema para quarto</a> -->
		{% endif %}

		{% if housekeepingManager.hasPermission(playerDetails.getRank(), 'infobus') %}
        <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/infobus_polls" class="list-group-item list-group-item-action {{ infobusPollsActive }}">Enquetes do Infobus</a>
		<!-- <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/infobus_polls/create" class="list-group-item list-group-item-action {{ infobusPollsCreateActive }}">Criar nova enquete</a> -->
		{% endif %}
		
		{% if housekeepingManager.hasPermission(playerDetails.getRank(), 'articles/create') %}
        <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/articles" class="list-group-item list-group-item-action {{ articlesActive }}">Notícias</a>
		<!-- <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/articles/create" class="list-group-item list-group-item-action {{ createArticlesActive }}">Criar nova notícia</a> -->
		{% endif %}
		
		{% if housekeepingManager.hasPermission(playerDetails.getRank(), 'users/create') %}
        <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/users/search" class="list-group-item list-group-item-action {{ searchUsersActive }}">Buscar usuário</a>
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/users/create" class="list-group-item list-group-item-action {{ createUserActive }}">Criar novo usuário</a>
		{% endif %}
		
		{% if housekeepingManager.hasPermission(playerDetails.getRank(), 'transaction/lookup') %}
        <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/transaction/lookup" class="list-group-item list-group-item-action {{ searchTransactionsActive }}">Transações</a>
		{% endif %}
		
		{% if housekeepingManager.hasPermission(playerDetails.getRank(), 'catalogue/edit_frontpage') %}
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/edit_frontpage" class="list-group-item list-group-item-action {{ editCatalogueFrontPage }}">Página inicial do Catálogo</a>
		{% endif %}
		{% if housekeepingManager.hasPermission(playerDetails.getRank(), 'catalogue/manage') %}
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/pages" class="list-group-item list-group-item-action {{ cataloguePagesActive }}">Catalogue Pages</a>
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/items" class="list-group-item list-group-item-action {{ catalogueItemsActive }}">Catalogue Items</a>
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/packages" class="list-group-item list-group-item-action {{ cataloguePackagesActive }}">Catalogue Packages</a>
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/sale_badges" class="list-group-item list-group-item-action {{ catalogueSaleBadgesActive }}">Catalogue Sale Badges</a>
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/collectables" class="list-group-item list-group-item-action {{ catalogueCollectablesActive }}">Catalogue Collectables</a>
		{% endif %}
		{% if housekeepingManager.hasPermission(playerDetails.getRank(), 'item_definitions/manage') %}
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/item_definitions" class="list-group-item list-group-item-action {{ itemDefinitionsActive }}">Item Definitions</a>
		{% endif %}
		{% if housekeepingManager.hasPermission(playerDetails.getRank(), 'vouchers/manage') %}
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/vouchers" class="list-group-item list-group-item-action {{ vouchersActive }}">Vouchers</a>
		{% endif %}
		{% if housekeepingManager.hasPermission(playerDetails.getRank(), 'wordfilter/manage') %}
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/wordfilter" class="list-group-item list-group-item-action {{ wordfilterActive }}">Wordfilter</a>
		{% endif %}
		{% if housekeepingManager.hasPermission(playerDetails.getRank(), 'recycler/manage') %}
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/recycler_rewards" class="list-group-item list-group-item-action {{ recyclerRewardsActive }}">Recycler Rewards</a>
		{% endif %}
		{% if housekeepingManager.hasPermission(playerDetails.getRank(), 'room_categories/manage') %}
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/room_categories" class="list-group-item list-group-item-action {{ roomCategoriesActive }}">Room Categories</a>
		{% endif %}
		{% if housekeepingManager.hasPermission(playerDetails.getRank(), 'room_models/manage') %}
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/room_models" class="list-group-item list-group-item-action {{ roomModelsActive }}">Room Models</a>
		{% endif %}
		{% if housekeepingManager.hasPermission(playerDetails.getRank(), 'rooms/manage') %}
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/rooms" class="list-group-item list-group-item-action {{ roomsActive }}">Rooms</a>
		{% endif %}
		{% if housekeepingManager.hasPermission(playerDetails.getRank(), 'groups/manage') %}
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/groups" class="list-group-item list-group-item-action {{ groupsActive }}">Groups</a>
		{% endif %}
		{% if housekeepingManager.hasPermission(playerDetails.getRank(), 'badges') %}
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/badges" class="list-group-item list-group-item-action {{ badgesActive }}">Badges</a>
		{% endif %}
      </div>
    </div>
    <div id="page-content-wrapper">

      <nav class="navbar navbar-expand-lg navbar-light bg-light border-bottom">
        <button class="btn btn-primary" id="menu-toggle">Esconder Menu</button>

        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav ml-auto mt-2 mt-lg-0">
            <li class="nav-item active">
              <a class="nav-link" href="{{ site.sitePath }}/{{ site.housekeepingPath }}">Início</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="{{ site.sitePath }}/{{ site.housekeepingPath }}/logout">Sair</a>
            </li>
            <!-- 
			<li class="nav-item dropdown">
              <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                Dropdown
              </a>
              <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
                <a class="dropdown-item" href="#">Action</a>
                <a class="dropdown-item" href="#">Another action</a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="#">Something else here</a>
              </div>
            </li>
			-->
          </ul>
        </div>
      </nav>

      <div class="container-fluid">
