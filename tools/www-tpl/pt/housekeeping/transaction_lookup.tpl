{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set searchTransactionsActive = " active " %}
	{% include "housekeeping/base/navigation.tpl" %}
     <h1 class="mt-4">Transações</h1>
		{% include "housekeeping/base/alert.tpl" %}
		<p>Transação de pesquisa por um usuário específico, insira seu ID de usuário ou nome de usuário. Irá exibir todas as transações do mês anterior.</p>
		<form class="table-responsive col-md-4" method="post">
			<div class="form-group">
				<label for="searchQuery">Nome do usuário ou ID</label>
				<input type="text" name="searchQuery" class="form-control" id="searchQuery" placeholder="Estou procurando por...">
			</div>
			<button type="submit" class="btn btn-primary">Buscar</button>
		</form>
		<br>
		{% include "housekeeping/transaction/search_results.tpl" %}
      </div>
    </div>
  </div>
  <script src="https://code.jquery.com/jquery-3.1.1.slim.min.js"></script>
  <script src="https://blackrockdigital.github.io/startbootstrap-simple-sidebar/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
  <script>
    $("#menu-toggle").click(function(e) {
      e.preventDefault();
      $("#wrapper").toggleClass("toggled");
    });
  </script>
</body>
</html>

{% include "housekeeping/base/footer.tpl" %}