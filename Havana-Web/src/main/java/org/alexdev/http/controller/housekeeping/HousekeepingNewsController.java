package org.alexdev.http.controller.housekeeping;

import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.util.DateUtil;
import org.alexdev.http.Routes;
import org.alexdev.http.dao.NewsDao;
import org.alexdev.http.game.housekeeping.HousekeepingManager;
import org.alexdev.http.game.news.NewsArticle;
import org.alexdev.http.game.news.NewsCategory;
import org.alexdev.http.game.news.NewsDateKey;
import org.alexdev.http.game.news.NewsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Controller
@RequestMapping("/" + Routes.HOUSEKEEPING_PATH)
public class HousekeepingNewsController {

    private static final int MAX_NEWS_TO_DISPLAY = 250;

    @GetMapping("/articles")
    public String articles(HttpSession session, Model model) {
        if (!HousekeepingUtil.isLoggedIn(session)) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        PlayerDetails playerDetails = HousekeepingUtil.getPlayerDetails(session);

        if (!HousekeepingManager.getInstance().hasPermission(playerDetails.getRank(), "articles/create")) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        model.addAttribute("housekeepingManager", HousekeepingManager.getInstance());
        model.addAttribute("pageName", "View News");
        model.addAttribute("articles", NewsDao.getTop(NewsDateKey.ALL, MAX_NEWS_TO_DISPLAY, true, List.of(), 0));

        session.removeAttribute("alertMessage");

        return "housekeeping/articles";
    }

    @GetMapping("/articles/create")
    public String createGet(HttpSession session, Model model) {
        if (!HousekeepingUtil.isLoggedIn(session)) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        PlayerDetails playerDetails = HousekeepingUtil.getPlayerDetails(session);

        if (!HousekeepingManager.getInstance().hasPermission(playerDetails.getRank(), "articles/create")) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        List<String> images = NewsDao.getTopStoryImages();

        model.addAttribute("housekeepingManager", HousekeepingManager.getInstance());
        model.addAttribute("pageName", "Create News");
        model.addAttribute("images", images);
        model.addAttribute("randomImage", images.isEmpty() ? "" : images.get(ThreadLocalRandom.current().nextInt(images.size())));
        model.addAttribute("currentDate", DateUtil.getDate(DateUtil.getCurrentTimeSeconds(), "yyyy-MM-dd'T'HH:mm"));
        model.addAttribute("categories", NewsManager.getInstance().getCategories());

        session.removeAttribute("alertMessage");

        return "housekeeping/articles_create";
    }

    @PostMapping("/articles/create")
    public String createPost(
            @RequestParam(value = "title", defaultValue = "") String title,
            @RequestParam(value = "shortstory", defaultValue = "") String shortStory,
            @RequestParam(value = "fullstory", defaultValue = "") String fullStory,
            @RequestParam(value = "topstory", defaultValue = "") String topStory,
            @RequestParam(value = "topstoryOverride", defaultValue = "") String topStoryOverride,
            @RequestParam(value = "authorOverride", defaultValue = "") String authorOverride,
            @RequestParam(value = "category", defaultValue = "") String category,
            @RequestParam(value = "articleimage", defaultValue = "") String articleImage,
            @RequestParam(value = "datePublished", defaultValue = "") String datePublished,
            @RequestParam(value = "futurePublished", defaultValue = "false") String futurePublished,
            @RequestParam(value = "published", defaultValue = "false") String published,
            @RequestParam(value = "categories[]", required = false) List<String> categoriesList,
            HttpSession session) {

        if (!HousekeepingUtil.isLoggedIn(session)) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        PlayerDetails playerDetails = HousekeepingUtil.getPlayerDetails(session);

        if (!HousekeepingManager.getInstance().hasPermission(playerDetails.getRank(), "articles/create")) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        long publishDate = DateUtil.getFromFormat("yyyy-MM-dd'T'HH:mm", datePublished);

        List<NewsCategory> categories = new ArrayList<>();
        if (categoriesList != null) {
            for (String data : categoriesList) {
                var newsCategory = NewsManager.getInstance().getCategoryByLabel(data);
                if (newsCategory != null) {
                    categories.add(newsCategory);
                }
            }
        }

        int articleId = NewsDao.create(
                title, shortStory, fullStory, topStory, topStoryOverride,
                playerDetails.getId(), authorOverride, category, articleImage,
                publishDate, futurePublished.equals("true"), published.equals("true")
        );

        NewsDao.insertCategories(articleId, categories);

        session.setAttribute("alertColour", "success");
        session.setAttribute("alertMessage", "The submission of the news article was successful");

        return "redirect:/" + Routes.HOUSEKEEPING_PATH + "/articles";
    }

    @GetMapping("/articles/delete")
    public String delete(
            @RequestParam(value = "id", defaultValue = "0") int articleId,
            HttpSession session,
            Model model) {

        if (!HousekeepingUtil.isLoggedIn(session)) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        PlayerDetails playerDetails = HousekeepingUtil.getPlayerDetails(session);
        NewsArticle article = NewsDao.get(articleId);

        if (article != null && article.getAuthorId() != playerDetails.getId()) {
            if (!HousekeepingManager.getInstance().hasPermission(playerDetails.getRank(), "articles/delete_any")) {
                return "redirect:/" + Routes.HOUSEKEEPING_PATH;
            }
        }

        if (!HousekeepingManager.getInstance().hasPermission(playerDetails.getRank(), "articles/delete_own")) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        model.addAttribute("housekeepingManager", HousekeepingManager.getInstance());

        if (articleId == 0) {
            session.setAttribute("alertColour", "danger");
            session.setAttribute("alertMessage", "There was no article selected to delete");
        } else if (!NewsDao.exists(articleId)) {
            session.setAttribute("alertColour", "danger");
            session.setAttribute("alertMessage", "The article does not exist");
        } else {
            session.setAttribute("alertColour", "success");
            session.setAttribute("alertMessage", "Successfully deleted the article");
            NewsDao.delete(articleId);
        }

        model.addAttribute("pageName", "Delete News");
        model.addAttribute("articles", NewsDao.getTop(NewsDateKey.ALL, MAX_NEWS_TO_DISPLAY, true, List.of(), 0));

        session.removeAttribute("alertMessage");

        return "housekeeping/articles";
    }

    @GetMapping("/articles/edit")
    public String editGet(
            @RequestParam(value = "id", defaultValue = "0") int articleId,
            HttpSession session,
            Model model) {

        if (!HousekeepingUtil.isLoggedIn(session)) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        PlayerDetails playerDetails = HousekeepingUtil.getPlayerDetails(session);

        if (!HousekeepingManager.getInstance().hasPermission(playerDetails.getRank(), "articles/edit_own")) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        model.addAttribute("housekeepingManager", HousekeepingManager.getInstance());
        model.addAttribute("images", NewsDao.getTopStoryImages());

        if (articleId == 0) {
            session.setAttribute("alertColour", "danger");
            session.setAttribute("alertMessage", "There was no article selected to edit");
        } else if (!NewsDao.exists(articleId)) {
            session.setAttribute("alertColour", "danger");
            session.setAttribute("alertMessage", "The article does not exist");
        } else {
            NewsArticle article = NewsDao.get(articleId);

            if (article.getAuthorId() != playerDetails.getId()) {
                if (!HousekeepingManager.getInstance().hasPermission(playerDetails.getRank(), "articles/edit_any")) {
                    return "redirect:/" + Routes.HOUSEKEEPING_PATH;
                }
            }

            model.addAttribute("currentDate", DateUtil.getDate(article.getTimestamp(), "yyyy-MM-dd'T'HH:mm"));
            model.addAttribute("article", article);
            model.addAttribute("categories", NewsManager.getInstance().getCategories());
        }

        session.removeAttribute("alertMessage");

        return "housekeeping/articles_edit";
    }

    @PostMapping("/articles/edit")
    public String editPost(
            @RequestParam(value = "id", defaultValue = "0") int articleId,
            @RequestParam(value = "title", defaultValue = "") String title,
            @RequestParam(value = "shortstory", defaultValue = "") String shortStory,
            @RequestParam(value = "fullstory", defaultValue = "") String fullStory,
            @RequestParam(value = "topstory", defaultValue = "") String topStory,
            @RequestParam(value = "topstoryOverride", defaultValue = "") String topStoryOverride,
            @RequestParam(value = "authorOverride", defaultValue = "") String authorOverride,
            @RequestParam(value = "articleimage", defaultValue = "") String articleImage,
            @RequestParam(value = "datePublished", defaultValue = "") String datePublished,
            @RequestParam(value = "futurePublished", defaultValue = "false") String futurePublished,
            @RequestParam(value = "published", defaultValue = "false") String published,
            @RequestParam(value = "categories[]", required = false) List<String> categoriesList,
            HttpSession session,
            Model model) {

        if (!HousekeepingUtil.isLoggedIn(session)) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        PlayerDetails playerDetails = HousekeepingUtil.getPlayerDetails(session);

        if (!HousekeepingManager.getInstance().hasPermission(playerDetails.getRank(), "articles/edit_own")) {
            return "redirect:/" + Routes.HOUSEKEEPING_PATH;
        }

        model.addAttribute("housekeepingManager", HousekeepingManager.getInstance());
        model.addAttribute("images", NewsDao.getTopStoryImages());

        if (!NewsDao.exists(articleId)) {
            session.setAttribute("alertColour", "danger");
            session.setAttribute("alertMessage", "The article does not exist");
            return "redirect:/" + Routes.HOUSEKEEPING_PATH + "/articles";
        }

        NewsArticle article = NewsDao.get(articleId);

        if (article.getAuthorId() != playerDetails.getId()) {
            if (!HousekeepingManager.getInstance().hasPermission(playerDetails.getRank(), "articles/edit_any")) {
                return "redirect:/" + Routes.HOUSEKEEPING_PATH;
            }
        }

        long publishDate = DateUtil.getFromFormat("yyyy-MM-dd'T'HH:mm", datePublished);

        List<NewsCategory> categories = new ArrayList<>();
        if (categoriesList != null) {
            for (String data : categoriesList) {
                var newsCategory = NewsManager.getInstance().getCategoryByLabel(data);
                if (newsCategory != null) {
                    categories.add(newsCategory);
                }
            }
        }

        NewsDao.insertCategories(article.getId(), categories);

        article.setTitle(title);
        article.setShortStory(shortStory);
        article.setFullStory(fullStory);
        article.setTopStory(topStory);
        article.setTopstoryOverride(topStoryOverride);
        article.setAuthorOverride(authorOverride);
        article.setArticleImage(articleImage);
        article.setPublished(published.equals("true"));
        article.setFuturePublished(futurePublished.equals("true"));
        article.setTimestamp(publishDate);

        article.getCategories().clear();
        article.getCategories().addAll(categories);

        NewsDao.save(article);

        session.setAttribute("alertColour", "success");
        session.setAttribute("alertMessage", "The article was successfully saved");

        model.addAttribute("currentDate", DateUtil.getDate(article.getTimestamp(), "yyyy-MM-dd'T'HH:mm"));
        model.addAttribute("article", article);
        model.addAttribute("categories", NewsManager.getInstance().getCategories());

        session.removeAttribute("alertMessage");

        return "housekeeping/articles_edit";
    }

    @PostMapping("/articles/preview")
    @ResponseBody
    public String previewNewsArticle(@RequestParam(value = "body", defaultValue = "") String body) {
        return new org.alexdev.http.util.HousekeepingUtil().formatNewsStory(body);
    }
}
