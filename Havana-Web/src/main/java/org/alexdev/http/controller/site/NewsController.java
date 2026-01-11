package org.alexdev.http.controller.site;

import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.util.DateUtil;
import org.alexdev.http.dao.NewsDao;
import org.alexdev.http.game.news.*;
import org.alexdev.http.util.SessionHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class NewsController {

    @GetMapping("/articles")
    public String articles(HttpSession session, Model model) {
        session.setAttribute("page", "community");
        model.addAttribute("newsPage", "news");
        renderNews(session, model, null, null, false);
        return "news_articles";
    }

    @GetMapping("/articles/archive")
    public String articlesArchive(HttpSession session, Model model) {
        session.setAttribute("page", "community");
        model.addAttribute("newsPage", "news");
        renderNews(session, model, null, null, true);
        return "news_articles";
    }

    @GetMapping("/articles/category/{category}")
    public String articlesCategory(@PathVariable String category, HttpSession session, Model model) {
        session.setAttribute("page", "community");
        model.addAttribute("newsPage", "news");
        NewsCategory newsCategory = NewsManager.getInstance().getCategoryByLabel(category);
        renderNews(session, model, newsCategory, null, false);
        return "news_articles";
    }

    @GetMapping("/articles/{slug}-{id}")
    public String articlesById(@PathVariable String slug, @PathVariable int id, HttpSession session, Model model) {
        session.setAttribute("page", "community");
        model.addAttribute("newsPage", "news");
        renderNews(session, model, null, id, false);
        return "news_articles";
    }

    @GetMapping("/community/fansites")
    public String fansites(HttpSession session, Model model) {
        session.setAttribute("page", "community");
        model.addAttribute("newsPage", "fansites");
        NewsCategory category = NewsManager.getInstance().getCategoryByLabel("fansites");
        renderNews(session, model, category, null, false);
        return "news_articles";
    }

    @GetMapping("/community/fansites/archive")
    public String fansitesArchive(HttpSession session, Model model) {
        session.setAttribute("page", "community");
        model.addAttribute("newsPage", "fansites");
        NewsCategory category = NewsManager.getInstance().getCategoryByLabel("fansites");
        renderNews(session, model, category, null, true);
        return "news_articles";
    }

    @GetMapping("/community/fansites/category/{category}")
    public String fansitesCategory(@PathVariable String category, HttpSession session, Model model) {
        session.setAttribute("page", "community");
        model.addAttribute("newsPage", "fansites");
        NewsCategory newsCategory = NewsManager.getInstance().getCategoryByLabel(category);
        renderNews(session, model, newsCategory, null, false);
        return "news_articles";
    }

    @GetMapping("/community/fansites/{slug}-{id}")
    public String fansitesById(@PathVariable String slug, @PathVariable int id, HttpSession session, Model model) {
        session.setAttribute("page", "community");
        model.addAttribute("newsPage", "fansites");
        renderNews(session, model, null, id, false);
        return "news_articles";
    }

    @GetMapping("/community/events")
    public String events(HttpSession session, Model model) {
        session.setAttribute("page", "community");
        model.addAttribute("newsPage", "events");
        NewsCategory category = NewsManager.getInstance().getCategoryByLabel("events");
        renderNews(session, model, category, null, false);
        return "news_articles";
    }

    @GetMapping("/community/events/archive")
    public String eventsArchive(HttpSession session, Model model) {
        session.setAttribute("page", "community");
        model.addAttribute("newsPage", "events");
        NewsCategory category = NewsManager.getInstance().getCategoryByLabel("events");
        renderNews(session, model, category, null, true);
        return "news_articles";
    }

    @GetMapping("/community/events/category/{category}")
    public String eventsCategory(@PathVariable String category, HttpSession session, Model model) {
        session.setAttribute("page", "community");
        model.addAttribute("newsPage", "events");
        NewsCategory newsCategory = NewsManager.getInstance().getCategoryByLabel(category);
        renderNews(session, model, newsCategory, null, false);
        return "news_articles";
    }

    @GetMapping("/community/events/{slug}-{id}")
    public String eventsById(@PathVariable String slug, @PathVariable int id, HttpSession session, Model model) {
        session.setAttribute("page", "community");
        model.addAttribute("newsPage", "events");
        renderNews(session, model, null, id, false);
        return "news_articles";
    }

    private void renderNews(HttpSession session, Model model, NewsCategory override, Integer articleId, boolean isArchive) {
        int newsArticleId = articleId != null ? articleId : 0;
        int filterCategoryId = (override != null ? override.getId() : 0);

        NewsView view = NewsView.DEFAULT;

        model.addAttribute("monthlyView", false);
        model.addAttribute("archiveView", false);
        model.addAttribute("archives", new HashMap<String, List<NewsArticle>>());
        model.addAttribute("months", new HashMap<String, List<NewsArticle>>());
        model.addAttribute("articlesToday", new ArrayList<>());
        model.addAttribute("articlesYesterday", new ArrayList<>());
        model.addAttribute("articlesThisWeek", new ArrayList<>());
        model.addAttribute("articlesThisMonth", new ArrayList<>());
        model.addAttribute("articlesPastYear", new ArrayList<>());

        if (isArchive) {
            model.addAttribute("urlSuffix", "/in/archive");
            model.addAttribute("archiveView", true);
            view = NewsView.ARCHIVE;
        } else if (filterCategoryId > 0) {
            view = NewsView.MONTHS;
            model.addAttribute("monthlyView", true);
        } else {
            model.addAttribute("urlSuffix", "");
        }

        PlayerDetails playerDetails = SessionHelper.getPlayer(session);
        boolean includeUnpublished = playerDetails != null && playerDetails.getRank().getRankId() > 1;
        NewsArticle newsArticle = null;

        if (!NewsDao.exists(newsArticleId)) {
            try {
                var articles = NewsDao.getTop(NewsDateKey.ALL, 1, includeUnpublished, List.of(), filterCategoryId);
                if (!articles.isEmpty()) {
                    newsArticle = articles.get(0);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            newsArticle = NewsDao.get(newsArticleId);
        }

        if (newsArticle == null || (!newsArticle.isPublished() && !includeUnpublished)) {
            newsArticle = new NewsArticle(1,
                    "No news",
                    -1,
                    "Hotel Staff",
                    "",
                    "There is no news.",
                    System.currentTimeMillis() / 1000L,
                    "",
                    "",
                    "",
                    "",
                    true, 0, false);
        }

        model.addAttribute("currentArticle", newsArticle);

        if (view == NewsView.ARCHIVE) {
            var monthlyArticles = NewsDao.getArchive(includeUnpublished);
            model.addAttribute("archives", monthlyArticles);
            model.addAttribute("archiveView", true);
        }

        if (view == NewsView.MONTHS) {
            var monthlyArticles = NewsDao.getPastYear(includeUnpublished, filterCategoryId);
            model.addAttribute("months", monthlyArticles);
            model.addAttribute("monthlyView", true);
        }

        if (view == NewsView.DEFAULT) {
            var articleList = NewsDao.getTop(Integer.MAX_VALUE, includeUnpublished, filterCategoryId);
            model.addAttribute("articlesToday", articleList.get(NewsDateKey.TODAY));
            model.addAttribute("articlesYesterday", articleList.get(NewsDateKey.YESTERDAY));
            model.addAttribute("articlesThisWeek", articleList.get(NewsDateKey.THIS_WEEK));
            model.addAttribute("articlesThisMonth", articleList.get(NewsDateKey.THIS_MONTH));
            model.addAttribute("articlesPastYear",
                    (articleList.get(NewsDateKey.TODAY).isEmpty() &&
                            articleList.get(NewsDateKey.YESTERDAY).isEmpty() &&
                            articleList.get(NewsDateKey.THIS_WEEK).isEmpty() &&
                            articleList.get(NewsDateKey.THIS_MONTH).isEmpty()) ?
                            articleList.get(NewsDateKey.PAST_YEAR) : List.of());
        }
    }

    private enum NewsView {
        DEFAULT, ARCHIVE, MONTHS
    }
}
