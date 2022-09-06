{% if playerDetails.hasClubSubscription() == false %}
	<div class="cbb habboclub-tryout">
        <h2 class="title">Unete al {{ site.siteName }} Club</h2>
        <div class="box-content">
            <div class="habboclub-banner-container habboclub-clothes-banner"></div>
            <p class="habboclub-header">{{ site.siteName }} Club es nuestro club para usuarios VIP: ¡No se admite a cualquiera! Los miembros disfrutan de una variedad de beneficios, incluyendo ropa exclusiva, regalos gratis y una lista de amigos extendida.</p>

            <p class="habboclub-link"><a href="{{ site.sitePath }}/club">Dale un vistazo al {{ site.siteName }} Club &gt;&gt;</a></p>
        </div>
    </div>
{% endif %}