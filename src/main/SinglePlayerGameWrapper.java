package main;

import main.menu.main_menu.MenuManager;

public class SinglePlayerGameWrapper
{
    private SinglePlayerGame game;

    public SinglePlayerGame getGame()
    {
        return game;
    }

    public void setGame(SinglePlayerGame game)
    {
        this.game = game;
    }

    public SinglePlayerGameWrapper(MenuManager menu)
    {
        this.game = new SinglePlayerGame(menu, this);
    }
}
