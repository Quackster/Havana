<div class="d-flex" id="wrapper">
    <div class="bg-light border-right" id="sidebar-wrapper">
      <div class="sidebar-heading">Hausverwaltung</div>
      <div class="list-group list-group-flush">
        <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}" class="list-group-item list-group-item-action {{ dashboardActive }}">Dashboard</a>
		{% if housekeepingManager.hasPermission(playerDetails.getRank(), 'configuration') %}
        <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/configurations" class="list-group-item list-group-item-action {{ configurationsActive }}">Einstellung</a>
		{% endif %}
		
	    {% if housekeepingManager.hasPermission(playerDetails.getRank(), 'bans') %}
        <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/bans" class="list-group-item list-group-item-action {{ bansActive }}">Bann-Management</a>
		{% endif %}
		
		{% if housekeepingManager.hasPermission(playerDetails.getRank(), 'room_ads') %}
        <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/room_ads" class="list-group-item list-group-item-action {{ roomAdsActive }}">Raum-Werbung</a>
		<!-- <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/room_ads/create" class="list-group-item list-group-item-action {{ roomCreateAdsActive }}">Erstelle Raumwerbung..</a> -->
		{% endif %}
		
		
		{% if housekeepingManager.hasPermission(playerDetails.getRank(), 'room_badges') %}
        <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/room_badges" class="list-group-item list-group-item-action {{ roomBadgesActive }}">Raum-Abzeichen</a>
		<!-- <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/room_badges/create" class="list-group-item list-group-item-action {{ roomCreateBadgesActive }}">Erstelle Raumabzeichen..</a> -->
		{% endif %}

		{% if housekeepingManager.hasPermission(playerDetails.getRank(), 'infobus') %}
        <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/infobus_polls" class="list-group-item list-group-item-action {{ infobusPollsActive }}">Infobus-Umfragen</a>
		<!-- <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/infobus_polls/create" class="list-group-item list-group-item-action {{ infobusPollsCreateActive }}">Erstelle Infobus Umfragen</a> -->
		{% endif %}
		
		{% if housekeepingManager.hasPermission(playerDetails.getRank(), 'articles/create') %}
        <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/articles" class="list-group-item list-group-item-action {{ articlesActive }}">Nachrichten</a>
		<!-- <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/articles/create" class="list-group-item list-group-item-action {{ createArticlesActive }}">Erstelle Artikel hier..</a> -->
		{% endif %}
		
		{% if housekeepingManager.hasPermission(playerDetails.getRank(), 'users/create') %}
        <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/users/search" class="list-group-item list-group-item-action {{ searchUsersActive }}">Suche Spieler</a>
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/users/create" class="list-group-item list-group-item-action {{ createUserActive }}">Spieler erstellen</a>
		{% endif %}
		
		{% if housekeepingManager.hasPermission(playerDetails.getRank(), 'transaction/lookup') %}
        <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/transaction/lookup" class="list-group-item list-group-item-action {{ searchTransactionsActive }}">Transaktionsverlauf</a>
		{% endif %}
		
		{% if housekeepingManager.hasPermission(playerDetails.getRank(), 'catalogue/edit_frontpage') %}
		<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/catalogue/edit_frontpage" class="list-group-item list-group-item-action {{ editCatalogueFrontPage }}">Katalog Startseite</a>
		{% endif %}
      </div>
    </div>
    <div id="page-content-wrapper">

      <nav class="navbar navbar-expand-lg navbar-light bg-light border-bottom">
        <button class="btn btn-primary" id="menu-toggle">Menü verbergen</button>

        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav ml-auto mt-2 mt-lg-0">
            <li class="nav-item active">
              <a class="nav-link" href="{{ site.sitePath }}/{{ site.housekeepingPath }}">Start</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="{{ site.sitePath }}/{{ site.housekeepingPath }}/logout">Abmelden</a>
            </li>
            <!-- 
			<li class="nav-item dropdown">
              <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                Dropdown
              </a>
              <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
                <a class="dropdown-item" href="#">Aktion</a>
                <a class="dropdown-item" href="#">Andere Aktion</a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="#">Irgendwas anderes hier..</a>
              </div>
            </li>
			-->
          </ul>
        </div>
      </nav>

      <div class="container-fluid">
