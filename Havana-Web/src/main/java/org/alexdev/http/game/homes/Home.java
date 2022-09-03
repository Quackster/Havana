package org.alexdev.http.game.homes;

import org.alexdev.http.dao.HomesDao;

public class Home {
    private int userId;
    private String background;

    public Home(int userId, String background) {
        this.userId = userId;
        this.background = background;
    }

    public int getUserId() {
        return userId;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public void saveBackground() {
        HomesDao.saveBackground(userId, background);
    }
}
