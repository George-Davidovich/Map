import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class  Medieval extends Application {
    @Override
    public void start(Stage primaryStag) {
        Pane root = new Pane();
        Image image = new Image(getClass().getResourceAsStream("Map_Of_Europe.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(600);
        imageView.setFitWidth(900);
        root.getChildren().add(imageView);

        MenuItem newGame = new MenuItem("ОДИН ИГРОК");
        MenuItem continuumGame = new MenuItem("ПРОДОЛЖЕНИЕ");
        MenuItem options = new MenuItem("НАСТРОЙКИ");
        MenuItem exitGame = new MenuItem("ВЫХОД");
        SubMenu mainMenu = new SubMenu(newGame, continuumGame, options, exitGame);

        MenuItem NG1 = new MenuItem("ОСНОВНАЯ КОМПАНИЯ");
        MenuItem NG2 = new MenuItem("БИТВА");
        MenuItem NG3 = new MenuItem("ИСТОРИЧЕСКАЯ БИТВА");
        MenuItem NG4 = new MenuItem("НАЗАД");
        SubMenu newGameMenu = new SubMenu(NG1, NG2, NG3, NG4);

        MenuItem third = new MenuItem("ИЗОБРАЖЕНИЕ");
        MenuItem sound = new MenuItem("ЗВУК");
        MenuItem keys = new MenuItem("УПРАВЛЕНИЕ");
        MenuItem gameProcess = new MenuItem("ИГРОВОЙ ПРОЦЕСС");
        MenuItem optionsBack = new MenuItem("НАЗАД");
        SubMenu optionsMenu = new SubMenu(third, sound, keys, gameProcess, optionsBack);

        MenuBox menuBox = new MenuBox(mainMenu);
        newGame.setOnMouseClicked(event -> menuBox.setSubMenu(newGameMenu));
        options.setOnMouseClicked(event -> menuBox.setSubMenu(optionsMenu));
        exitGame.setOnMouseClicked(event ->  System.exit(0));
        optionsBack.setOnMouseClicked(event -> menuBox.setSubMenu(mainMenu));
        NG4.setOnMouseClicked(event -> menuBox.setSubMenu(mainMenu));
        root.getChildren().addAll(menuBox);

        Scene scene = new Scene(root, 900, 600);
        scene.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ESCAPE){
                FadeTransition ft = new FadeTransition(Duration.seconds(1), menuBox);
                if(! menuBox.isVisible()){
                    ft.setFromValue(0);
                    ft.setToValue(1);
                    ft.play();
                    menuBox.setVisible(true);
                }
                else {
                    ft.setFromValue(1);
                    ft.setToValue(0);
                    ft.setOnFinished(evt -> menuBox.setVisible(false));
                    ft.play();
                }
            }
        });


        primaryStag.setTitle("Medieval");
        primaryStag.setScene(scene);
        primaryStag.show();
    }

    private static class MenuItem extends StackPane{
        public MenuItem(String name){
            Rectangle bg = new Rectangle(250, 30, Color.ANTIQUEWHITE);
            bg.setOpacity(0.5);

            Text text = new Text(name);
            text.setFill(Color.CHARTREUSE);
            text.setFont(Font.font("Times New Roman", FontWeight.BOLD, 16));

            setAlignment(Pos.CENTER);
            getChildren().addAll(bg, text);
            FillTransition st = new FillTransition(Duration.seconds(0.5), bg);
            setOnMouseEntered(event -> {
                st.setFromValue(Color.SILVER);
                st.setToValue(Color.CORAL);
                st.setCycleCount(Animation.INDEFINITE);
                st.setAutoReverse(true);
                st.play();
            });
            setOnMouseExited(event -> {
                st.stop();
                bg.setFill(Color.ANTIQUEWHITE);
            });
        }
    }

    private static class MenuBox extends Pane{
        static SubMenu subMenu;
        public MenuBox(SubMenu subMenu){
            MenuBox.subMenu = subMenu;

            setVisible(false);
            Rectangle bg = new Rectangle(900, 600, Color.FIREBRICK);
            bg.setOpacity(0.4);
            getChildren().addAll(bg, subMenu);
        }

        public void setSubMenu(SubMenu subMenu){
            getChildren().remove(MenuBox.subMenu);
            MenuBox.subMenu = subMenu;
            getChildren().add(MenuBox.subMenu);
        }
    }

    private static class SubMenu extends VBox{
        public SubMenu(MenuItem ... items){
            setSpacing(15);
            for(MenuItem item : items){
                setTranslateY(120);
                setTranslateX(60);
                getChildren().addAll(item);
            }
        }
    }
}