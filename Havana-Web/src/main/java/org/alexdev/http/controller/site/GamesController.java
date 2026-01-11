package org.alexdev.http.controller.site;

import jakarta.servlet.http.HttpSession;
import org.alexdev.havana.dao.mysql.HighscoreDao;
import org.alexdev.havana.game.games.enums.GameType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GamesController {
    private static final int HIGHSCORES_LIMIT = 10;

    @GetMapping("/games")
    public String games(HttpSession session, Model model) {
        session.setAttribute("page", "games");

        Integer gameId = (Integer) session.getAttribute("highscoreGameId");
        if (gameId == null) {
            gameId = 1;
            session.setAttribute("highscoreGameId", gameId);
        }

        GameType gameType = getGameType(gameId);
        session.setAttribute("gameScoreViewMonthly", true);

        appendPersonalHighscores(model, gameType, 1, gameId, true);
        return "games";
    }

    @GetMapping("/games/score_all_time")
    public String gamesAllTime(HttpSession session, Model model) {
        session.setAttribute("page", "games");

        Integer gameId = (Integer) session.getAttribute("highscoreGameId");
        if (gameId == null) {
            gameId = 1;
            session.setAttribute("highscoreGameId", gameId);
        }

        GameType gameType = getGameType(gameId);
        session.setAttribute("gameScoreViewMonthly", false);

        appendPersonalHighscores(model, gameType, 1, gameId, false);
        return "games";
    }

    @PostMapping("/habblet/ajax/personalhighscores")
    public String personalHighscores(
            @RequestParam(value = "pageNumber", required = false) String pageNumberStr,
            @RequestParam(value = "gameId", required = false) String gameIdStr,
            HttpSession session,
            Model model) {

        int pageNumber = 1;
        if (pageNumberStr != null && StringUtils.isNumeric(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
            if (pageNumber < 1) {
                pageNumber = 1;
            }
        }

        int gameId = 1;
        GameType gameType = GameType.BATTLEBALL;

        if (gameIdStr != null && StringUtils.isNumeric(gameIdStr)) {
            gameId = Integer.parseInt(gameIdStr);
            gameType = getGameType(gameId);
            session.setAttribute("highscoreGameId", gameId);
        }

        Boolean viewMonthly = (Boolean) session.getAttribute("gameScoreViewMonthly");
        appendPersonalHighscores(model, gameType, pageNumber, gameId, viewMonthly != null && viewMonthly);

        return "habblet/personalhighscores";
    }

    private GameType getGameType(int gameId) {
        return switch (gameId) {
            case 0 -> GameType.WOBBLE_SQUABBLE;
            case 2 -> GameType.SNOWSTORM;
            default -> GameType.BATTLEBALL;
        };
    }

    private void appendPersonalHighscores(Model model, GameType gameType, int pageNumber, int gameId, boolean viewMonthly) {
        model.addAttribute("scoreEntries", HighscoreDao.getScores(HIGHSCORES_LIMIT, gameType, pageNumber, viewMonthly));
        model.addAttribute("gameId", gameId);
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("viewMonthlyScores", viewMonthly);

        boolean hasNextPage = !HighscoreDao.getScores(HIGHSCORES_LIMIT, gameType, pageNumber + 1, viewMonthly).isEmpty();
        model.addAttribute("hasNextPage", hasNextPage);
    }
}
