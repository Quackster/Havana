{% if playerDetails.hasClubSubscription() == false %}
	<div class="cbb habboclub-tryout">
        <h2 class="title">Trete {{ site.siteName }} Club bei</h2>
        <div class="box-content">
            <div class="habboclub-banner-container habboclub-clothes-banner"></div>
            <p class="habboclub-header">{{ site.siteName }} Club ist unser VIP-Bereich nur für VIP Mitglieder, hier ist Pöbel absolut nicht zugelassen! Mitglieder genießen zahlreiche Vorteile, darunter exklusive Kleidung, kostenlose Geschenke und eine erweiterte Freundesliste. Unten findest du alle attraktiven Gründe für deine Mitgliedschaft.</p>

            <p class="habboclub-link"><a href="{{ site.sitePath }}/club">Entdecke {{ site.siteName }} Club &gt;&gt;</a></p>
        </div>
    </div>
{% endif %}