{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set articlesActive = " active " %}
	{% include "housekeeping/base/navigation.tpl" %}
    <h1 class="mt-4">{{ locale.housekeeping_articles_posted_articles }}</h1>
		{% include "housekeeping/base/alert.tpl" %}
		<p>{{ locale.housekeeping_articles_this_includes_the_most_recent_articles_posted_on_the_site_you_may_edit_or_delete_them_if_you_wish }}</p>
		<p><a href="/{{ site.housekeepingPath }}/articles/create" class="btn btn-danger">{{ locale.housekeeping_articles_new_news_article }}</a></p>			
          <div class="table-responsive">
            <table class="table table-striped">
              <thead>
                <tr>
				  <th>{{ locale.housekeeping_articles_name }}</th>
				  <th>{{ locale.housekeeping_articles_author }}</th>
				  <th>{{ locale.housekeeping_articles_short_story }}</th>
				  <th>{{ locale.housekeeping_articles_date }}</th>
				  <th>{{ locale.housekeeping_articles_views }}</th>
				  <th></th>
                </tr>
              </thead>
              <tbody>
				{% for article in articles %}
                <tr>
				  <td>{{ article.title }}</td>
				  <td>{{ article.author }}</td>
				  <td>{{ article.shortstory }}</td>
				  <td>{{ article.getDate() }}</td>
				  <td>{{ article.views }}</td>
				  <td>
				  	<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/articles/edit?id={{ article.id }}" class="btn btn-primary">{{ locale.housekeeping_articles_edit }}</a>
				  	<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/articles/delete?id={{ article.id }}" class="btn btn-danger">{{ locale.housekeeping_articles_delete }}</a>
				  </td>
                </tr>
			   {% endfor %}
              </tbody>
            </table>
      </div>
{% include "housekeeping/base/footer.tpl" %}
