package main.menu.main_menu;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.SinglePlayerGameWrapper;
import widget.MenuButton;

@SuppressWarnings("static-access")
public class MainMenu
{
	Stage window;
	Scene MainMenu, newGame, loadGame,Leaderboard,Setting,help,SingleIntermediate,TwoIntermediate,
	SingleGameplay,TwoGameplay,SingleSaved,TwoSaved,Control,splashscreen,pauseMenu,SavedIntermediate,ChooseShip;
	StackPane pane;
	private boolean soundOn;
	private int shipNum =1;
	private static final double scale =0.6;
	public int getShipNum()
	{
		return shipNum;
	}
	public void setShipNum(int shipNum)
	{
		this.shipNum = shipNum;
	}
	private SinglePlayerGameWrapper gameWrapper;



	public MainMenu(Stage window)
	{
		this.window = window;
		soundOn=true;


	}
	public Scene getMainMenu() {
//		Image image=new Image("file:res/download.gif");
//		ImageView view=new ImageView(image);
		Label label1=new Label("Allegiance");
		label1.setFont(Font.loadFont("file:res/font/AlphaCentauri500.ttf", 52));
		label1.setTextFill(Color.web("#d5d0d2"));
		MenuButton new_Game=new MenuButton(e->{
			gameWrapper = new SinglePlayerGameWrapper(this);
			window.setScene(gameWrapper.getGame().getScene());
		});
		new_Game .setNormalImageString("file:res/img/ui_elements/new_game_blue.png");
		new_Game.setHoverImageString("file:res/img/ui_elements/new_game_yellow.png");
		new_Game.setClickImageString("file:res/img/ui_elements/new_game_orange.png");
		new_Game.loadImages();
		new_Game.getButton().setScaleX(scale);

		//Button2
		MenuButton load_Game=new MenuButton(e->{
			window.setScene(SavedIntermediate);
		});
		load_Game .setNormalImageString("file:res/img/ui_elements/load_game_blue.png");
		load_Game.setHoverImageString("file:res/img/ui_elements/load_game_yellow.png");
		load_Game.setClickImageString("file:res/img/ui_elements/load_game_orange.png");
		load_Game.loadImages();
		load_Game.getButton().setScaleX(scale);
		//Button3
		MenuButton leaderBoard=new MenuButton(e->{
			window.setScene(Leaderboard);
		});
		leaderBoard.setNormalImageString("file:res/img/ui_elements/leaderboards_blue.png");
		leaderBoard.setHoverImageString("file:res/img/ui_elements/leaderboards_yellow.png");
		leaderBoard.setClickImageString("file:res/img/ui_elements/leaderboards_orange.png");
		leaderBoard.loadImages();
		leaderBoard.getButton().setScaleX(scale);

		//Button4

		MenuButton settings =new MenuButton(e ->
		{
			window.setScene(Setting);
		});
		settings.setNormalImageString("file:res/img/ui_elements/settings_blue.png");
		settings.setHoverImageString("file:res/img/ui_elements/settings_yellow.png");
		settings.setClickImageString("file:res/img/ui_elements/settings_orange.png");
		settings.loadImages();
		settings.getButton().setScaleX(scale);
		//Button5
		MenuButton Help=new MenuButton(e -> { window.setScene(help); });
		Help.setNormalImageString("file:res/img/ui_elements/help_blue.png");
		Help.setHoverImageString("file:res/img/ui_elements/help_yellow.png");
		Help.setClickImageString("file:res/img/ui_elements/help_orange.png");
		Help.loadImages();
		Help.getButton().setScaleX(scale);
		Help.getButton().setScaleY(scale);

		//Button6
		MenuButton Exit=new MenuButton(e -> { window.close(); });
		Exit.setNormalImageString("file:res/img/ui_elements/exit_blue.png");
		Exit.setHoverImageString("file:res/img/ui_elements/exit_yellow.png");
		Exit.setClickImageString("file:res/img/ui_elements/exit_orange.png");
		Exit.loadImages();
		Exit.getButton().setScaleX(scale);
		Exit.getButton().setScaleY(scale);

		// Layout1

		VBox layout1=new VBox(10);
		layout1.getChildren().addAll(new_Game.getButton(),load_Game.getButton(),
				leaderBoard.getButton(), settings.getButton(),Help.getButton(),Exit.getButton());
		layout1.setAlignment(Pos.CENTER);
		label1.setTranslateY(75);
		layout1.setBackground(BackgroundgifClass.getBackground());
		MainMenu =new Scene(layout1 ,500,700);
		return MainMenu;
	}


	public void closeProgram() {
		boolean answer=Box.displayexit("Exit", "Are you sure, you want to Exit?");
		if(answer) {
			window.close();
		}
	}

	public Stage getWindow()
	{
		return window;
	}
}

class StartGameEventHandler implements EventHandler<ActionEvent>
{
	private MainMenu menu;
	public StartGameEventHandler(MainMenu menu)
	{
		this.menu = menu;
	}

	@Override
	public void handle(ActionEvent actionEvent)
	{
            SinglePlayerGameWrapper wrappedGame = new SinglePlayerGameWrapper(menu);
            menu.window.setScene(wrappedGame.getGame().getScene());
	}
}