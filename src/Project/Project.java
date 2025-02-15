package Project;

import java.io.File;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.StackPane;

public class Project extends Application {
	private MediaPlayer piero;
	private MediaPlayer sakura;
	private MediaPlayer bgm;
	AudioClip audioclip=new AudioClip(new File("src/music/hit.wav").toURI().toString());//打擊聲
	AudioClip buttonsound=new AudioClip(new File("src/music/choose.wav").toURI().toString());//按鈕聲
	PauseTransition keep=new PauseTransition(Duration.seconds(0.3));//label顯示0.5秒後消失
	PauseTransition start_wait=new PauseTransition(Duration.seconds(5));//進入第二頁面後等待2秒開始遊戲
	PauseTransition end_wait=new PauseTransition(Duration.seconds(2));//跳到結算畫面
	PauseTransition songtext_keep=new PauseTransition(Duration.seconds(0.1));//選取頁面難度晚點顯示
	PauseTransition change_wait=new PauseTransition(Duration.seconds(2));//轉場頁面停留
	PauseTransition press_wait_up=new PauseTransition(Duration.seconds(0.12));//打擊顯示特效持續
	PauseTransition press_wait_down=new PauseTransition(Duration.seconds(0.12));//打擊顯示特效持續
	int prefect=0;//計算打擊判斷
	int miss=0;
	int good=0;
	int score=0;
	int combo=0;
	int maxcombo=0;
	int full_combo=1;
	Text label_miss=new Text("miss");//顯示打擊判斷
	Text label_good=new Text("good");
	Text label_prefect=new Text("prefect");	
	Text label_combo=new Text("Combo "+combo);
	
	Circle c1 = new Circle(950,120,40);//創造移動的圓圈
	Circle c2 = new Circle(950,120,40);	
	Circle c3 = new Circle(950,120,40);
	Circle c4 = new Circle(950,120,40);
	Circle c5 = new Circle(950,120,40);
	Circle c6 = new Circle(950,330,40);
	Circle c7 = new Circle(950,330,40);
	Circle c8 = new Circle(950,330,40);
	Circle c9 = new Circle(950,330,40);
	Circle c10 = new Circle(950,330,40);
	
	Circle long_c1=new Circle(950,50,40);//長鍵
	Rectangle long_rec=new Rectangle(950,20,300,60);
	Circle long_c2=new Circle(1250,50,30);
	Group long_group=new Group(long_rec,long_c2,long_c1);
	Circle long_c1_2=new Circle(950,50,40);
	Rectangle long_rec_2=new Rectangle(950,20,300,60);
	Circle long_c2_2=new Circle(1250,50,30);
	Group long_group_2=new Group(long_rec_2,long_c2_2,long_c1_2);
	//音樂播放
	@Override
	public void init() throws Exception {
		File bgm_file = new File("src/music/bgm.mp3");
	    Media bgm_media = new Media(bgm_file.toURI().toString());
	    bgm = new MediaPlayer(bgm_media);
	    File file = new File("src/music/piero.mp3");
	    Media media = new Media(file.toURI().toString());
	    piero = new MediaPlayer(media);
	    File file2 = new File("src/music/sakura.mp3");
	    Media media2 = new Media(file2.toURI().toString());
	    sakura = new MediaPlayer(media2);
	}
	
	@ Override
	public void start(Stage primaryStage) throws Exception{	
		//顯示打擊判斷	
		label_miss.setVisible(false);
		label_good.setVisible(false);
		label_prefect.setVisible(false);
		label_combo.setVisible(false);
		label_miss.setFont(Font.loadFont(new File("src/font/勘亭流.TTC").toURI().toString(), 25));
		label_miss.setFill(Color.WHITE);
		label_good.setFill(Color.web("#aeda49"));
		label_good.setStroke(Color.WHITE);  label_good.setStrokeWidth(0.5);
		label_good.setFont(Font.loadFont(new File("src/font/勘亭流.TTC").toURI().toString(), 25));
		label_prefect.setFont(Font.loadFont(new File("src/font/勘亭流.TTC").toURI().toString(), 25));
		label_prefect.setFill(Color.web("#feec61"));
		label_prefect.setStroke(Color.WHITE); label_prefect.setStrokeWidth(0.5);
		label_combo.setStyle("-fx-font-size:30;-fx-translate-x:380;-fx-translate-y:40");	
		label_combo.setFill(Color.WHITE);
		label_combo.setFont(Font.loadFont(new File("src/font/勘亭流.TTC").toURI().toString(), 30));
		
		//成績結算文字
		Text text_prefect=new Text();
		Text text_good=new Text();
		Text text_miss=new Text();
		Text text_combo=new Text();
		Text text_maxcombo=new Text();
		Text text_score=new Text();
		//初始頁面
		Image image=new Image("image/IMG_1844.PNG");//背景圖片
		ImageView imageview=new ImageView(image);
		imageview.setX(0);
		imageview.setY(0);
		imageview.setFitHeight(550);
		imageview.setFitWidth(900);
		Pane ori_pane=new Pane();
		ori_pane.getChildren().addAll(imageview);
		
		bgm.play();
		bgm.setVolume(0.5);
		Scene ori_scene=new Scene(ori_pane, 900, 550);
		primaryStage.setResizable(false);
		primaryStage.setScene(ori_scene);
		primaryStage.show();	
		//第一頁面
		Rectangle close_door1=new Rectangle(-450,0,450,550);//轉場圖案
		Rectangle close_door2=new Rectangle(900,0,450,550);
		ImagePattern close_imagePattern1 = new ImagePattern(new Image("image/1.jpg"));
		close_door1.setFill(close_imagePattern1);
		ImagePattern close_imagePattern2 = new ImagePattern(new Image("image/2.jpg"));
		close_door2.setFill(close_imagePattern2);
		
		Timeline close_move1=new Timeline(new KeyFrame(Duration.seconds(0.8),new KeyValue(close_door1.xProperty(),0)),new KeyFrame(Duration.seconds(2.5),new KeyValue(close_door1.xProperty(),0)));
		Timeline close_move2=new Timeline(new KeyFrame(Duration.seconds(0.8),new KeyValue(close_door2.xProperty(),450)),new KeyFrame(Duration.seconds(2.5),new KeyValue(close_door2.xProperty(),450)));	
		Image image1=new Image("image/background1.jpg");//背景圖片
		ImageView imageview1=new ImageView(image1);
		imageview1.setX(0);
		imageview1.setY(0);
		imageview1.setFitHeight(550);
		imageview1.setFitWidth(1700);
		Pane pane1=new Pane();
		Scene scene1=new Scene(pane1, 900, 550);
		
		//第二頁面
		Pane pane2=new Pane();
		Scene scene2=new Scene(pane2, 900, 550);	
		Rectangle open_door1=new Rectangle(0,0,450,550);//拉門轉場
		Rectangle open_door2=new Rectangle(450,0,450,550);
		ImagePattern open_imagePattern1 = new ImagePattern(new Image("image/1.jpg"));
		open_door1.setFill(open_imagePattern1);
		ImagePattern open_imagePattern2 = new ImagePattern(new Image("image/2.jpg"));
		open_door2.setFill(open_imagePattern2);
		Timeline open_move1=new Timeline(new KeyFrame(Duration.seconds(1.5),new KeyValue(open_door1.xProperty(),-450)));
		Timeline open_move2=new Timeline(new KeyFrame(Duration.seconds(1.5),new KeyValue(open_door2.xProperty(),900)));
		
		Circle judge_cir=new Circle(100,120,30);//判斷標準線
		judge_cir.setFill(Color.TRANSPARENT);
		judge_cir.setStroke(Color.BLACK);
		Circle judge_cirbig=new Circle(100,120,45);//判斷標準線
		judge_cirbig.setFill(Color.TRANSPARENT);
		judge_cirbig.setStroke(Color.BLACK);
		Circle judge_cir2=new Circle(100,330,30);
		judge_cir2.setFill(Color.TRANSPARENT);
		judge_cir2.setStroke(Color.BLACK);
		Circle judge_cirbig2=new Circle(100,330,45);//判斷標準線
		judge_cirbig2.setFill(Color.TRANSPARENT);
		judge_cirbig2.setStroke(Color.BLACK);
		
		Image image_background2=new Image("image/background2.png");//背景圖片
		ImageView imageview_background2=new ImageView(image_background2);
		imageview_background2.setX(0);
		imageview_background2.setY(0);
		imageview_background2.setFitHeight(550);
		imageview_background2.setFitWidth(900);
		
		Circle hit_star_up=new Circle(100,120,0);//打擊時的星星效果
		ImagePattern imagepattern_star_up=new ImagePattern(new Image("image/star.png"));
		hit_star_up.setFill(imagepattern_star_up);
		Timeline star_timeline_up=new Timeline(new KeyFrame(Duration.seconds(0),new KeyValue(hit_star_up.radiusProperty(),40)), new KeyFrame(Duration.seconds(0),new KeyValue(hit_star_up.opacityProperty(),1)),new KeyFrame(Duration.seconds(0.1),new KeyValue(hit_star_up.radiusProperty(),70)),  new KeyFrame(Duration.seconds(0.2),new KeyValue(hit_star_up.opacityProperty(),0)));
		Circle hit_star_down=new Circle(100,330,0);//打擊時的星星效果
		ImagePattern imagepattern_star_down=new ImagePattern(new Image("image/star.png"));
		hit_star_down.setFill(imagepattern_star_down);
		Timeline star_timeline_down=new Timeline(new KeyFrame(Duration.seconds(0),new KeyValue(hit_star_down.radiusProperty(),40)), new KeyFrame(Duration.seconds(0),new KeyValue(hit_star_down.opacityProperty(),1)),new KeyFrame(Duration.seconds(0.1),new KeyValue(hit_star_down.radiusProperty(),70)),  new KeyFrame(Duration.seconds(0.2),new KeyValue(hit_star_down.opacityProperty(),0)));
		
		long_group.setTranslateY(70);
		long_group_2.setTranslateY(280);
		pane2.getChildren().addAll(imageview_background2,hit_star_up,hit_star_down,judge_cir,judge_cir2,judge_cirbig,judge_cirbig2,label_miss,label_good,label_prefect,label_combo,c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,long_group,long_group_2,open_door1,open_door2);		
		//第三頁面
		Image image_background3=new Image("image/score.png");//背景圖片
		ImageView imageview_background3=new ImageView(image_background3);
		imageview_background3.setX(-80);
		imageview_background3.setY(0);
		imageview_background3.setFitHeight(550);
		imageview_background3.setFitWidth(1050);
		Text grade_text=new Text("成績發表");
		grade_text.setFont(Font.loadFont(new File("src/font/勘亭流.TTC").toURI().toString(), 40));
		grade_text.setStyle("-fx-line-spacing: 5px;");
		grade_text.setX(50);  grade_text.setY(70);
		Pane pane3=new Pane();
		Scene scene3=new Scene(pane3, 900, 550);
		
		Rectangle return_block=new Rectangle(325,20,250,70);
		ImagePattern return_block1 = new ImagePattern(new Image("image/return.png"));
		return_block.setFill(return_block1);
		Text reverse_text=new Text("再玩一首");
		reverse_text.setFont(Font.loadFont(new File("src/font/勘亭流.TTC").toURI().toString(), 40));
		reverse_text.setStyle("-fx-line-spacing: 5px;");
		reverse_text.setX(370);  reverse_text.setY(68);
		StackPane stackPane1 = new StackPane(return_block, reverse_text);
		stackPane1.setLayoutX(325);
        stackPane1.setLayoutY(20);
        
		Rectangle exit_block=new Rectangle(620,20,250,70);
		ImagePattern exit_block1 = new ImagePattern(new Image("image/leave.png"));
		exit_block.setFill(exit_block1);
		Text exit_text=new Text("結束遊戲");
		exit_text.setFont(Font.loadFont(new File("src/font/勘亭流.TTC").toURI().toString(), 40));
		exit_text.setStyle("-fx-line-spacing: 5px;");
		exit_text.setX(665);  exit_text.setY(68);
		StackPane stackPane2 = new StackPane(exit_block, exit_text);
		stackPane2.setLayoutX(620);
        stackPane2.setLayoutY(20);
		pane3.getChildren().addAll(imageview_background3,grade_text,text_prefect,text_good,text_miss,text_combo,text_maxcombo,text_score,stackPane1,stackPane2);
		
//----------------------------------------------------------------------------------
				
		//按鍵效果音	
		piero.setVolume(0.25);
		sakura.setVolume(0.25);	
		Media media1 = new Media(new File("src/music/piero_clip.mp3").toURI().toString());
		MediaPlayer piero_clip = new MediaPlayer(media1);
		piero_clip.setVolume(0.2);
		Media media2 = new Media(new File("src/music/sakura_clip.mp3").toURI().toString());
		MediaPlayer sakura_clip = new MediaPlayer(media2);
		sakura_clip.setVolume(0.2);
		//歌曲選擇頁面
		Text song1_text=new Text("か\n"+"ら\n"+"く\n"+"り\n"+"ピ\n"+"エ\n"+"ロ\n");
		song1_text.setFont(Font.loadFont(new File("src/font/勘亭流.TTC").toURI().toString(), 25));
		song1_text.setStyle("-fx-line-spacing: 5px;");
		song1_text.setX(335);  song1_text.setY(150);	
		Text song1_text_diff=new Text("難\n"+"度\n"+"★\n"+"★\n"+"★\n");
		song1_text_diff.setStyle("-fx-line-spacing: 3px;");
		song1_text_diff.setFont(Font.loadFont(new File("src/font/勘亭流.TTC").toURI().toString(), 25));
		song1_text_diff.setX(410);  song1_text_diff.setY(300);	
		song1_text_diff.setVisible(false);
		Text song2_text=new Text("千\n"+"本\n"+"櫻\n");
		song2_text.setFont(Font.loadFont(new File("src/font/勘亭流.TTC").toURI().toString(), 25));
		song2_text.setStyle("-fx-line-spacing: 5px;");
		song2_text.setX(535);  song2_text.setY(150);		
		Text song2_text_diff=new Text("難\n"+"度\n"+"★\n"+"★\n"+"★\n"+"★\n"+"★\n");
		song2_text_diff.setStyle("-fx-line-spacing: 3px;");
		song2_text_diff.setFont(Font.loadFont(new File("src/font/勘亭流.TTC").toURI().toString(), 25));
		song2_text_diff.setX(610);  song2_text_diff.setY(250);
		song2_text_diff.setVisible(false);
		
		Rectangle song1_rec=new Rectangle(300,100,100,350);
		ImagePattern imagepattern_song1=new ImagePattern(new Image("image/按鍵1.png"));
		song1_rec.setFill(imagepattern_song1);
		Rectangle song2_rec=new Rectangle(500,100,100,350);
		ImagePattern imagepattern_song2=new ImagePattern(new Image("image/按鍵2.png"));
		song2_rec.setFill(imagepattern_song2);
		song1_rec.setStroke(Color.BLACK);
		song2_rec.setStroke(Color.BLACK);
		song1_rec.setStrokeWidth(3);
		song2_rec.setStrokeWidth(3);
		
		Text songchoose_text=new Text("歌曲選擇");
		songchoose_text.setFont(Font.loadFont(new File("src/font/勘亭流.TTC").toURI().toString(), 40));
		songchoose_text.setX(50);  songchoose_text.setY(70);
		Timeline timeline_song1=new Timeline(new KeyFrame(Duration.seconds(0.2),new KeyValue(song1_rec.widthProperty(),200)));
		Timeline timeline_song1_2=new Timeline(new KeyFrame(Duration.seconds(0.2),new KeyValue(song2_rec.xProperty(),600)));	
		Timeline timeline_song1_3=new Timeline(new KeyFrame(Duration.seconds(0.2),new KeyValue(song2_text.xProperty(),635)));
		Timeline timeline_song2=new Timeline(new KeyFrame(Duration.seconds(0.2),new KeyValue(song2_rec.widthProperty(),200)));
		Timeline timeline_song1_back=new Timeline(new KeyFrame(Duration.seconds(0.2),new KeyValue(song1_rec.widthProperty(),100)));
		Timeline timeline_song1_2_back=new Timeline(new KeyFrame(Duration.seconds(0.2),new KeyValue(song2_rec.xProperty(),500)));
		Timeline timeline_song1_3_back=new Timeline(new KeyFrame(Duration.seconds(0.2),new KeyValue(song2_text.xProperty(),535)));		
		Timeline timeline_song2_back=new Timeline(new KeyFrame(Duration.seconds(0.2),new KeyValue(song2_rec.widthProperty(),100)));
		song1_rec.setOnMouseEntered(e->{
			song1_rec.setStroke(Color.YELLOW);
			song1_rec.setStrokeWidth(4);
		});
		song1_rec.setOnMouseExited(e->{
			song1_rec.setStroke(Color.BLACK);
			song1_rec.setStrokeWidth(3);
		});
				
		song2_rec.setOnMouseEntered(e->{
			song2_rec.setStroke(Color.YELLOW);
			song2_rec.setStrokeWidth(4);
		});
		song2_rec.setOnMouseExited(e->{
			song2_rec.setStroke(Color.BLACK);
			song2_rec.setStrokeWidth(3);
		});
			
		scene1.setOnMouseClicked(e->{
			if(song1_rec.getWidth()==200) {
				piero_clip.stop();
				double x = e.getX();
	            double y = e.getY();
	            if (!song1_rec.contains(x, y)) {
	            	timeline_song1_back.play();
	            	timeline_song1_2_back.play();
	            	timeline_song1_3_back.play();
	            	song1_text_diff.setVisible(false);
	            }			
			}
			if(song2_rec.getWidth()==200) {
				sakura_clip.stop();
				double x = e.getX();
	            double y = e.getY();
	            if (!song2_rec.contains(x, y)) {
	            	timeline_song2_back.play();
	            	song2_text_diff.setVisible(false);
	            }			
			}		
		});
		pane1.getChildren().addAll(imageview1,songchoose_text,song1_rec,song2_rec,song1_text,song2_text,song1_text_diff,song2_text_diff,close_door1,close_door2);
		
		stackPane1.setOnMouseClicked(e->{
			buttonsound.play();
			primaryStage.setScene(scene1);
			Timeline closedoor_reserse1=new Timeline(new KeyFrame(Duration.seconds(1),new KeyValue(close_door1.xProperty(),-450)));
			Timeline closedoor_reserse2=new Timeline(new KeyFrame(Duration.seconds(1),new KeyValue(close_door2.xProperty(),900)));	
			closedoor_reserse1.play();
			closedoor_reserse2.play();
			Timeline opendoor_reserse1=new Timeline(new KeyFrame(Duration.seconds(1),new KeyValue(open_door1.xProperty(),0)));
			Timeline opendoor_reserse2=new Timeline(new KeyFrame(Duration.seconds(1),new KeyValue(open_door2.xProperty(),450)));
			opendoor_reserse1.play();
			opendoor_reserse2.play();
			Timeline rec_reserse1=new Timeline(new KeyFrame(Duration.seconds(0.01),new KeyValue(song1_rec.widthProperty(),100)));
			Timeline rec_reserse2=new Timeline(new KeyFrame(Duration.seconds(0.01),new KeyValue(song2_rec.widthProperty(),100)));
			rec_reserse1.play();
			rec_reserse2.play();
			Timeline rec_x_reserse1=new Timeline(new KeyFrame(Duration.seconds(0.01),new KeyValue(song1_rec.xProperty(),300)));
			Timeline rec_x_reserse2=new Timeline(new KeyFrame(Duration.seconds(0.01),new KeyValue(song2_rec.xProperty(),500)));
			rec_x_reserse1.play();
			rec_x_reserse2.play();
			Timeline text_reserse1=new Timeline(new KeyFrame(Duration.seconds(0.01),new KeyValue(song1_text.xProperty(),335)));
			Timeline text_reserse2=new Timeline(new KeyFrame(Duration.seconds(0.01),new KeyValue(song2_text.xProperty(),535)));
			text_reserse1.play();
			text_reserse2.play();
			song1_text_diff.setVisible(false);
			song2_text_diff.setVisible(false);
			
			label_combo.setVisible(false);
			prefect=0;
			miss=0;
			good=0;
			score=0;
			combo=0;
			maxcombo=0;
			full_combo=1;
		});
		stackPane2.setOnMouseClicked(e->{
			Stage stage = (Stage) exit_block.getScene().getWindow();
            stage.close();
		});
//-----------------------------------------------------------------------------------------------
		//設定圓形樣式
		set_circle_style(c1);
		set_circle_style(c2);
		set_circle_style(c3);
		set_circle_style(c4);
		set_circle_style(c5);
		set_circle_style(c6);
		set_circle_style(c7);
		set_circle_style(c8);
		set_circle_style(c9);
		set_circle_style(c10);
		ImagePattern longcir_imagePattern1 = new ImagePattern(new Image("image/yellow_hit.png"));
		long_c1.setFill(longcir_imagePattern1);
		long_rec.setFill(Color.web("#fed169"));
		long_c2.setFill(Color.web("#fed169"));
		long_c1_2.setFill(longcir_imagePattern1);
		long_rec_2.setFill(Color.web("#fed169"));
		long_c2_2.setFill(Color.web("#fed169"));
		//主要的節拍設定在這
		ori_scene.setOnMouseClicked(event -> {  //按下初始畫面後啟動
			bgm.stop();
			buttonsound.play();
			buttonsound.setVolume(0.5);
			primaryStage.setScene(scene1);
		});	
		
		song1_rec.setOnMouseClicked(e->{  //按下歌曲1選項
			if(song1_rec.getWidth()==100) {
				timeline_song1.play();
				timeline_song1_2.play();
				timeline_song1_3.play();
				piero_clip.play();
				songtext_keep.play();
				songtext_keep.setOnFinished(e2->song1_text_diff.setVisible(true) );
			}
			
			if(song1_rec.getWidth()==200) {  //點擊進入第一首歌曲
				buttonsound.play();
				close_move1.play();
				close_move2.play();
				change_wait.play();
				change_wait.setOnFinished(e2->{
					primaryStage.setScene(scene2);
					open_move1.play();
					open_move2.play();
				});
				
				//------------------------------------------
				start_wait.play();
				start_wait.setOnFinished(e2->{    //進入第二頁面後等待2秒開始遊戲
					piero.play();				
					scene2.setOnKeyPressed(e1->{  //按下按鍵後的判定
			        	audioclip.play();  //播放音效
						if(e1.getCode()==KeyCode.LEFT ) {							
							judge(c1);
							judge(c2);
							judge(c3);	
							judge(c4);	
							judge(c5);
							long_judge(long_group);
							star_timeline_up.play();
							judge_cirbig.setStroke(Color.web("#cba616"));
							judge_cir.setStroke(Color.web("#cba616"));
							press_wait_up.play();
							press_wait_up.setOnFinished(e3->{
								judge_cir.setStroke(Color.BLACK);
								judge_cirbig.setStroke(Color.BLACK);
							});
						}	
						if(e1.getCode()==KeyCode.RIGHT ) {								
							judge(c6);
							judge(c7);
							judge(c8);
							judge(c9);
							judge(c10);
							long_judge(long_group_2);
							star_timeline_down.play();
							judge_cirbig2.setStroke(Color.web("#cba616"));
							judge_cir2.setStroke(Color.web("#cba616"));
							press_wait_down.play();
							press_wait_down.setOnFinished(e3->{
								judge_cir2.setStroke(Color.BLACK);
								judge_cirbig2.setStroke(Color.BLACK);
							});
						}					
			        });
					//歌曲1的譜	
					create_circle_up(c1,761.0,pane2,scene2);
					create_circle_down(c7,3145.0,pane2,scene2);
					create_circle_up(c2,5530.0,pane2,scene2);
					create_circle_up(c3,6326.0,pane2,scene2);
					create_circle_down(c7,6326.0,pane2,scene2);
					create_circle_up(c4,6581.0,pane2,scene2);
					create_circle_down(c9,6581.0,pane2,scene2);
					create_circle_up(c5,7930.0,pane2,scene2);
					create_circle_down(c7,8505.0,pane2,scene2);
					create_circle_up(c1,8985.0,pane2,scene2);
					create_circle_down(c9,10265.5,pane2,scene2); 
					create_circle_down(c6,10800.6,pane2,scene2);
					create_circle_down(c7,11370.1,pane2,scene2);
					create_circle_up(c2,12601.3,pane2,scene2);
					create_circle_down(c7,13225.6,pane2,scene2);
					create_circle_up(c1,13785.0,pane2,scene2);
					create_circle_up(c3,14985.8,pane2,scene2);
					create_circle_up(c4,15577.8,pane2,scene2);
					create_circle_up(c5,16170.0,pane2,scene2);
					create_circle_up(c1,17305.0,pane2,scene2);
					create_circle_down(c7,17913.0,pane2,scene2);
					create_circle_up(c2,18442.0,pane2,scene2);
					create_circle_up(c3,19673.0,pane2,scene2);
					create_circle_up(c4,20265.0,pane2,scene2);
					create_circle_up(c5,20810.0,pane2,scene2);
					create_circle_up(c1,21993.0,pane2,scene2);
					create_circle_down(c7,22585.0,pane2,scene2);
					create_circle_up(c2,23097.5,pane2,scene2);
					create_circle_up(c3,24377.8,pane2,scene2);
					create_circle_up(c4,24921.0,pane2,scene2);
					create_circle_up(c5,25513.0,pane2,scene2);
					create_circle_down(c7,26676.8,pane2,scene2);
					create_circle_up(c1,26676.8,pane2,scene2);
					
					create_circle_up(c2,27849.6,pane2,scene2);
					create_circle_up(c3,28105.6,pane2,scene2);
					create_circle_up(c4,28678.6,pane2,scene2);
					create_circle_down(c7,29129.3,pane2,scene2);
					create_circle_down(c9,30184.6,pane2,scene2);
					create_circle_down(c6,30489.0,pane2,scene2);
					create_circle_up(c1,30745.5,pane2,scene2);
					create_circle_up(c3,31449.1,pane2,scene2);
					create_circle_up(c2,32536.6,pane2,scene2);
					create_circle_up(c1,32808.8,pane2,scene2);
					create_circle_down(c9,33112.5,pane2,scene2);
					create_circle_down(c6,33817.3,pane2,scene2);
					create_circle_down(c7,34376.8,pane2,scene2);
					create_circle_up(c3,34905.0,pane2,scene2);
					create_circle_up(c1,35513.3,pane2,scene2);
					create_circle_down(c7,36121.0,pane2,scene2);
					create_circle_up(c3,37209.1,pane2,scene2);
					create_circle_up(c1,37497.3,pane2,scene2);
					create_circle_up(c2,37832.8,pane2,scene2);
					create_circle_down(c7,38489.5,pane2,scene2);
					create_circle_up(c1,39577.5,pane2,scene2);
					create_circle_up(c2,39880.6,pane2,scene2);
					create_circle_up(c3,40169.3,pane2,scene2);
					create_circle_down(c9,40873.0,pane2,scene2);
					create_circle_up(c2,41928.8,pane2,scene2);
					create_circle_up(c4,42233.3,pane2,scene2);
					create_circle_up(c1,42521.0,pane2,scene2);
					create_circle_down(c6,43208.8,pane2,scene2);
					create_circle_up(c3,43769.1,pane2,scene2);
					create_circle_up(c1,44660.6,pane2,scene2);
					create_circle_up(c2,44920.6,pane2,scene2);
					create_circle_down(c9,45544.8,pane2,scene2);
					create_circle_up(c1,46761.1,pane2,scene2);
					create_circle_down(c6,47320.6,pane2,scene2);
					create_circle_up(c3,47896.6,pane2,scene2);
					create_circle_down(c7,49112.6,pane2,scene2);
					create_circle_up(c1,49641.5,pane2,scene2);  
					create_circle_down(c9,50186.0,pane2,scene2);
					create_circle_up(c1,51433.8,pane2,scene2);
					create_circle_up(c2,52649.0,pane2,scene2);
					create_circle_down(c6,52649.0,pane2,scene2);
					create_circle_down(c7,53226.1,pane2,scene2);
					create_circle_up(c1,53768.6,pane2,scene2);
					create_circle_down(c9,53768.6,pane2,scene2);
					create_circle_up(c2,54392.8,pane2,scene2);
					create_circle_up(c3,54952.8,pane2,scene2);
					create_circle_down(c6,54952.8,pane2,scene2);
					create_circle_down(c7,56153.3,pane2,scene2);
					create_circle_down(c9,56760.8,pane2,scene2);
					create_circle_down(c6,57321.3,pane2,scene2);
					create_circle_up(c1,58521.5,pane2,scene2);
					create_circle_up(c2,59064.8,pane2,scene2);
					create_circle_up(c3,59672.8,pane2,scene2);
					create_circle_up(c1,60826.5,pane2,scene2);
					create_circle_down(c7,62008.8,pane2,scene2);
					create_circle_up(c3,62632.6,pane2,scene2);
					create_circle_down(c9,63209.1,pane2,scene2);
					create_circle_up(c2,63785.6,pane2,scene2);
					create_circle_down(c6,64361.3,pane2,scene2);
					
					create_circle_up(c1,64345.0,pane2,scene2);
					create_circle_down(c7,64969.1,pane2,scene2);
					create_circle_down(c9,65528.8,pane2,scene2);
					create_circle_up(c2,66137.3,pane2,scene2);
					create_circle_up(c3,66696.8,pane2,scene2);
					create_circle_down(c6,67320.3,pane2,scene2);
					create_circle_up(c1,67880.1,pane2,scene2);
					create_circle_down(c7,68520.1,pane2,scene2);
					create_circle_down(c9,68824.1,pane2,scene2);
					create_circle_up(c2,69112.1,pane2,scene2);
					create_circle_down(c6,69704.8,pane2,scene2);
					create_circle_up(c3,70264.8,pane2,scene2);
					create_circle_up(c1,70857.1,pane2,scene2);
					create_circle_down(c7,71434.0,pane2,scene2);
					create_circle_up(c2,72040.1,pane2,scene2);
					create_circle_down(c9,72584.6,pane2,scene2);
					create_circle_up(c3,73208.3,pane2,scene2);
					create_circle_up(c1,73512.5,pane2,scene2);
					create_circle_down(c6,73784.6,pane2,scene2);
					create_circle_down(c7,74376.3,pane2,scene2);
					create_circle_up(c2,74969.1,pane2,scene2);
					create_circle_up(c3,75496.3,pane2,scene2);
					create_circle_down(c9,76120.5,pane2,scene2);
					create_circle_up(c1,76712.5,pane2,scene2);
					create_circle_up(c2,77305.3,pane2,scene2);
					create_circle_down(c6,77913.5,pane2,scene2);
					create_circle_up(c3,78216.8,pane2,scene2);
					create_circle_up(c1,79145.3,pane2,scene2);
					create_circle_down(c7,80264.5,pane2,scene2);
					create_circle_down(c9,80840.5,pane2,scene2);
					create_circle_up(c2,81138.5,pane2,scene2);
					create_circle_up(c3,81432.1,pane2,scene2);
					create_circle_down(c6,81592.8,pane2,scene2);
					create_circle_up(c1,81897.6,pane2,scene2);

					create_circle_down(c7,82681.5,pane2,scene2);
					create_circle_down(c9,82881.5,pane2,scene2);
					create_circle_up(c3,83224.8,pane2,scene2);
					create_circle_down(c6,83800.8,pane2,scene2);
					create_circle_up(c2,84360.6,pane2,scene2);
					create_circle_up(c3,85577.5,pane2,scene2);
					create_circle_down(c7,86105.0,pane2,scene2);
					create_circle_up(c1,86393.0,pane2,scene2);
					create_circle_down(c9,86649.3,pane2,scene2);
					create_circle_up(c3,87897.3,pane2,scene2);
					create_circle_down(c6,88520.6,pane2,scene2);
					create_circle_up(c2,89033.1,pane2,scene2);
					create_circle_up(c3,90248.8,pane2,scene2);
					create_circle_down(c7,90809.3,pane2,scene2);
					create_circle_up(c2,91097.1,pane2,scene2);
					create_circle_down(c9,91353.0,pane2,scene2);
					create_circle_down(c6,92633.0,pane2,scene2);
					create_circle_up(c2,93256.6,pane2,scene2);
					create_circle_up(c3,93720.8,pane2,scene2);
					create_circle_up(c1,94968.5,pane2,scene2);
					create_circle_down(c7,95481.5,pane2,scene2);
					create_circle_up(c3,95785.3,pane2,scene2);
					create_circle_down(c6,96040.5,pane2,scene2);
					create_circle_up(c2,97273.1,pane2,scene2);
					create_circle_down(c7,97880.6,pane2,scene2);
					create_circle_down(c9,98376.6,pane2,scene2);
					create_circle_up(c2,99705.1,pane2,scene2);
					create_circle_up(c3,100200.6,pane2,scene2);
					create_circle_down(c7,100472.8,pane2,scene2);
					create_circle_down(c9,100777.0,pane2,scene2);
					create_circle_up(c3,101824.5,pane2,scene2);
					create_circle_down(c6,101824.5,pane2,scene2);

				});

				
			}		
		});	
		
		song2_rec.setOnMouseClicked(e->{  //按下歌曲2選項
			if(song2_rec.getWidth()==100) {
				timeline_song2.play();
				sakura_clip.play();
				songtext_keep.play();
				songtext_keep.setOnFinished(e2->song2_text_diff.setVisible(true) );
			}
			if(song2_rec.getWidth()==200) {
				buttonsound.play();
				close_move1.play();
				close_move2.play();
				change_wait.play();
				change_wait.setOnFinished(e2->{
					primaryStage.setScene(scene2);
					open_move1.play();
					open_move2.play();
				});
				//------------------------------------------
				start_wait.play();
				start_wait.setOnFinished(e2->{    //進入第二頁面後等待2秒開始遊戲
					sakura.play();				
					scene2.setOnKeyPressed(e1->{  //按下按鍵後的判定
			        	audioclip.play();  //播放音效
						if(e1.getCode()==KeyCode.LEFT ) {
							judge(c1);
							judge(c2);
							judge(c3);	
							judge(c4);	
							judge(c5);
							long_judge(long_group);
							star_timeline_up.play();
							judge_cirbig.setStroke(Color.web("#cba616"));
							judge_cir.setStroke(Color.web("#cba616"));
							press_wait_up.play();
							press_wait_up.setOnFinished(e3->{
								judge_cirbig.setStroke(Color.BLACK);
								judge_cir.setStroke(Color.BLACK);
							});
						}	
						if(e1.getCode()==KeyCode.RIGHT ) {								
							judge(c6);
							judge(c7);
							judge(c8);
							judge(c9);
							judge(c10);
							long_judge(long_group_2);
							star_timeline_down.play();
							judge_cirbig2.setStroke(Color.web("#cba616"));
							judge_cir2.setStroke(Color.web("#cba616"));
							press_wait_down.play();
							press_wait_down.setOnFinished(e3->{
								judge_cirbig2.setStroke(Color.BLACK);
								judge_cir2.setStroke(Color.BLACK);
							});
						}					
			        });
					//歌曲2的譜
					create_circle_up(c1,136.0,pane2,scene2);
					create_circle_up(c2,391.0,pane2,scene2);
					create_circle_down(c6,647.0,pane2,scene2);
					create_circle_down(c7,888.0,pane2,scene2);
					create_circle_down(c8,1160.1,pane2,scene2);
					create_circle_up(c3,1432.1,pane2,scene2);
					create_circle_up(c4,1640.1,pane2,scene2);
					create_circle_up(c5,1929.0,pane2,scene2);
					create_circle_down(c9,2217.1,pane2,scene2);
					create_circle_up(c1,2440.1,pane2,scene2);
					create_circle_up(c2,2824.1,pane2,scene2);		
					create_circle_down(c6,2824.1,pane2,scene2);				
					create_circle_up(c3,3209.3,pane2,scene2);
					create_circle_up(c4,3496.3,pane2,scene2);
					create_circle_down(c7,3800.8,pane2,scene2);
					create_circle_down(c8,3976.3,pane2,scene2);
					create_circle_down(c9,4248.5,pane2,scene2);
					create_circle_up(c5,4531.5,pane2,scene2);
					create_circle_up(c1,4755.0,pane2,scene2);
					create_circle_up(c2,5142.0,pane2,scene2);
					create_circle_down(c10,5529.3,pane2,scene2);
					create_circle_down(c6,5922.8,pane2,scene2);									
					
					create_circle_up(c3,6288.3,pane2,scene2);
					create_circle_down(c7,6472.6,pane2,scene2);
					create_circle_up(c4,6664.6,pane2,scene2);
					create_circle_up(c5,7080.6,pane2,scene2);
					create_circle_down(c8,7288.6,pane2,scene2);
					create_circle_up(c1,7465.0,pane2,scene2);
					create_circle_up(c2,7880.8,pane2,scene2);
					create_circle_down(c6,8072.6,pane2,scene2);
					create_circle_up(c3,8249.1,pane2,scene2);
					create_circle_up(c4,8680.6,pane2,scene2);
					create_circle_up(c5,9049.1,pane2,scene2);
					create_circle_down(c8,9432.8,pane2,scene2);
					create_circle_up(c1,9625.3,pane2,scene2);
					create_circle_down(c9,9801.0,pane2,scene2);
					create_circle_down(c10,10232.8,pane2,scene2);
					create_circle_up(c2,10425.5,pane2,scene2);
					create_circle_down(c6,10600.6,pane2,scene2);
					create_circle_up(c3,11000.8,pane2,scene2);
					create_circle_up(c4,11384.6,pane2,scene2);
					create_rec_up(long_group,11770.5,pane2,scene2);					
					create_circle_down(c7,12569.0,pane2,scene2);
					create_circle_up(c5,12744.8,pane2,scene2);
					create_circle_down(c8,12952.0,pane2,scene2);
					create_circle_down(c9,13336.0,pane2,scene2);
					create_circle_up(c1,13544.5,pane2,scene2);
					create_circle_down(c10,13736.0,pane2,scene2);
					create_circle_down(c6,14105.3,pane2,scene2);
					create_circle_up(c2,14296.3,pane2,scene2);
					create_circle_down(c7,14520.0,pane2,scene2);
					create_circle_up(c3,14888.0,pane2,scene2);
					create_circle_up(c4,15288.3,pane2,scene2);
					create_circle_down(c8,15656.0,pane2,scene2);
					create_circle_down(c9,16072.5,pane2,scene2);
					create_circle_up(c5,16440.1,pane2,scene2);
					create_circle_up(c1,16856.8,pane2,scene2);
					create_rec_down(long_group_2,17225,pane2,scene2);	
					
					create_circle_up(c2,18784.8,pane2,scene2);
					create_circle_down(c10,18999.6,pane2,scene2);
					create_circle_up(c3,19208.1,pane2,scene2);
					create_circle_up(c4,19591.3,pane2,scene2);
					create_circle_down(c6,19783.6,pane2,scene2);
					create_circle_up(c5,19959.6,pane2,scene2);
					create_circle_up(c1,20359.3,pane2,scene2);
					create_circle_down(c7,20551.5,pane2,scene2);
					create_circle_up(c2,20743.8,pane2,scene2);
					create_circle_up(c3,21127.5,pane2,scene2);
					create_circle_up(c4,21511.3,pane2,scene2);
					create_circle_down(c8,21911.8,pane2,scene2);
					create_circle_up(c5,22104.1,pane2,scene2);
					create_circle_down(c9,22311.8,pane2,scene2);
					create_circle_down(c10,22696.1,pane2,scene2);
					create_circle_up(c1,22872.3,pane2,scene2);
					create_circle_down(c6,23080.5,pane2,scene2);
					create_circle_up(c2,23479.6,pane2,scene2);
					create_circle_up(c3,23880.3,pane2,scene2);
					create_rec_up(long_group,24248,pane2,scene2);	

					create_circle_down(c7,25032.3,pane2,scene2);
					create_circle_up(c4,25208.1,pane2,scene2);
					create_circle_down(c8,25432.0,pane2,scene2);
					create_circle_down(c9,25816.8,pane2,scene2);
					create_circle_up(c5,26007.8,pane2,scene2);
					create_circle_down(c10,26200.1,pane2,scene2);
					create_circle_down(c6,26616.8,pane2,scene2);
					create_circle_up(c1,26808.1,pane2,scene2);
					create_circle_down(c7,27000.0,pane2,scene2);
					create_circle_down(c8,27384.3,pane2,scene2);
					create_circle_down(c9,27736.3,pane2,scene2);
					create_circle_up(c2,28152.5,pane2,scene2);
					create_circle_up(c3,28520.5,pane2,scene2);
					create_circle_down(c10,28920.3,pane2,scene2);
					create_circle_down(c6,29320.5,pane2,scene2);
					create_circle_up(c1,29688.1,pane2,scene2);
					create_circle_down(c7,30008.3,pane2,scene2);
					create_circle_up(c4,30312.1,pane2,scene2);
					create_circle_up(c5,30503.8,pane2,scene2);
					create_circle_down(c8,30312.1,pane2,scene2);
					create_circle_down(c9,30503.8,pane2,scene2);
					
					create_circle_up(c1,31255.8,pane2,scene2);
					create_circle_up(c2,31623.8,pane2,scene2);
					create_circle_up(c3,32055.5,pane2,scene2);
					create_circle_down(c6,32248.5,pane2,scene2);
					create_circle_up(c4,32440.1,pane2,scene2);
					create_circle_down(c7,32808.3,pane2,scene2);
					create_circle_down(c8,33208.3,pane2,scene2);
					create_circle_down(c9,33607.6,pane2,scene2);
					create_circle_up(c5,33767.6,pane2,scene2);
					create_circle_down(c10,33959.8,pane2,scene2);
					create_circle_up(c1,34375.5,pane2,scene2);
					create_circle_up(c2,34759.6,pane2,scene2);
					create_circle_up(c3,35161.3,pane2,scene2);
					create_circle_down(c6,35319.6,pane2,scene2);
					create_circle_up(c4,35495.5,pane2,scene2);
					create_circle_down(c7,35863.6,pane2,scene2);
					create_circle_down(c8,36312.5,pane2,scene2);
					create_circle_up(c5,36679.8,pane2,scene2);
					create_circle_up(c1,37096.5,pane2,scene2);
					create_circle_up(c2,37495.6,pane2,scene2);
					create_circle_up(c3,37863.8,pane2,scene2);
					create_circle_up(c4,38279.5,pane2,scene2);
					create_circle_down(c9,38487.6,pane2,scene2);
					create_circle_up(c5,38647.6,pane2,scene2);
					create_circle_down(c10,39047.8,pane2,scene2);
					create_circle_down(c6,39432.8,pane2,scene2);
					create_circle_down(c7,39863.8,pane2,scene2);
					create_circle_up(c1,40039.6,pane2,scene2);
					create_circle_down(c8,40216.5,pane2,scene2);
					create_circle_up(c2,40631.8,pane2,scene2);
					create_circle_up(c3,41047.6,pane2,scene2);
					create_circle_up(c4,41384.5,pane2,scene2);
					create_circle_down(c9,41576.3,pane2,scene2);
					create_circle_up(c5,41752.0,pane2,scene2);
					create_circle_down(c10,42167.5,pane2,scene2);
					create_circle_down(c6,42568.1,pane2,scene2);
					create_circle_up(c1,42967.8,pane2,scene2);
					create_circle_up(c2,43319.8,pane2,scene2);
					create_circle_down(c7,43735.5,pane2,scene2);
					create_circle_down(c8,44520.5,pane2,scene2);
					create_circle_down(c9,45304.5,pane2,scene2);
					create_circle_up(c3,45703.5,pane2,scene2);
					create_circle_up(c4,45896.3,pane2,scene2);
					create_circle_up(c5,46087.6,pane2,scene2);
					create_circle_down(c10,46856.3,pane2,scene2);
					create_circle_down(c6,47624.1,pane2,scene2);
					create_circle_down(c7,48391.8,pane2,scene2);
					create_circle_up(c1,48760.5,pane2,scene2);
					create_circle_up(c2,48967.6,pane2,scene2);
					create_circle_up(c3,49175.5,pane2,scene2);
					create_circle_up(c4,49927.6,pane2,scene2);
					create_circle_down(c8,50728.5,pane2,scene2);
					create_circle_up(c1,51528.3,pane2,scene2);
					create_circle_down(c9,51895.5,pane2,scene2);
					create_circle_down(c10,52119.5,pane2,scene2);
					create_circle_down(c6,52312.8,pane2,scene2);
					create_circle_up(c2,53079.8,pane2,scene2);
					create_circle_down(c7,53288.0,pane2,scene2);
					create_circle_up(c3,53672.6,pane2,scene2);
					create_circle_up(c4,53863.8,pane2,scene2);
					create_circle_down(c8,54232.8,pane2,scene2);
					create_circle_down(c9,54680.6,pane2,scene2);
					create_circle_up(c5,55063.6,pane2,scene2);
					create_circle_up(c1,55255.8,pane2,scene2);
					create_circle_down(c10,55063.6,pane2,scene2);
					create_circle_down(c6,55255.8,pane2,scene2);
					
					create_circle_up(c2,56136.3,pane2,scene2);
					create_circle_up(c3,56536.5,pane2,scene2);
					create_circle_up(c4,56722.6,pane2,scene2);
					create_circle_up(c5,56919.6,pane2,scene2);
					create_circle_down(c7,57320.8,pane2,scene2);
					create_circle_down(c8,57720.3,pane2,scene2);
					create_circle_down(c9,57895.8,pane2,scene2);
					create_circle_down(c10,58104.1,pane2,scene2);
					create_circle_up(c1,58503.8,pane2,scene2);					
					create_circle_up(c2,59305.3,pane2,scene2);
					create_circle_up(c3,59720.8,pane2,scene2);
					create_circle_up(c4,59916.0,pane2,scene2);
					create_circle_up(c5,60119.8,pane2,scene2);
					create_circle_down(c6,60520.3,pane2,scene2);
					create_circle_down(c7,60920.0,pane2,scene2);
					create_circle_down(c8,61112.0,pane2,scene2);
					create_circle_down(c9,61287.8,pane2,scene2);
					create_circle_up(c1,61672.0,pane2,scene2);					
					create_circle_up(c2,62440.6,pane2,scene2);
					create_circle_up(c3,62807.8,pane2,scene2);
					create_circle_up(c4,63000.5,pane2,scene2);
					create_circle_up(c5,63208.0,pane2,scene2);
					create_circle_down(c10,63591.8,pane2,scene2);
					create_circle_down(c6,63992.1,pane2,scene2);
					create_circle_down(c7,64200.0,pane2,scene2);
					create_circle_down(c8,64407.8,pane2,scene2);
					create_circle_up(c2,64745.3,pane2,scene2);					
					create_circle_up(c3,65560.3,pane2,scene2);
					create_circle_up(c4,65943.8,pane2,scene2);
					create_circle_down(c9,66344.8,pane2,scene2);
					create_circle_down(c10,66711.8,pane2,scene2);
					create_circle_up(c5,67111.8,pane2,scene2);
					create_circle_up(c1,67512.3,pane2,scene2);
					create_circle_up(c2,67880.0,pane2,scene2);					
					create_circle_down(c6,68664.5,pane2,scene2);
					create_circle_down(c7,69032.8,pane2,scene2);
					create_circle_down(c8,69237.8,pane2,scene2);
					create_circle_down(c9,69431.8,pane2,scene2);
					create_circle_up(c3,69815.8,pane2,scene2);
					create_circle_up(c4,70216.0,pane2,scene2);
					create_circle_up(c5,70422.1,pane2,scene2);
					create_circle_up(c1,70631.8,pane2,scene2);
					create_circle_down(c10,71000.8,pane2,scene2);					
					create_circle_down(c6,71784.0,pane2,scene2);
					create_circle_down(c7,72152.0,pane2,scene2);
					create_circle_down(c8,72343.8,pane2,scene2);
					create_circle_down(c9,72552.0,pane2,scene2);
					create_circle_up(c2,72936.0,pane2,scene2);
					create_circle_up(c3,73352.5,pane2,scene2);
					create_circle_up(c4,73528.3,pane2,scene2);
					create_circle_up(c5,73736.0,pane2,scene2);
					create_circle_down(c10,74120.1,pane2,scene2);					
					create_circle_down(c6,74888.0,pane2,scene2);
					create_circle_down(c7,75305.1,pane2,scene2);
					create_circle_down(c8,75484.1,pane2,scene2);
					create_circle_down(c9,75671.8,pane2,scene2);
					create_circle_up(c1,76023.8,pane2,scene2);
					create_circle_up(c2,76424.0,pane2,scene2);
					create_circle_up(c3,76632.5,pane2,scene2);
					create_circle_up(c4,76839.6,pane2,scene2);
					create_circle_down(c10,77240.0,pane2,scene2);					
					create_circle_up(c5,78008.5,pane2,scene2);
					create_circle_up(c1,78407.8,pane2,scene2);
					create_circle_down(c6,78776.0,pane2,scene2);
					create_circle_down(c7,79159.8,pane2,scene2);
					create_circle_up(c2,79560.1,pane2,scene2);
					create_circle_up(c3,79959.8,pane2,scene2);
					create_circle_up(c4,80345.1,pane2,scene2);
					
				});	
			}
		});

		piero.setOnEndOfMedia(new Runnable() {  //第一首音樂結束跳到結算頁面
		    @Override
		    public void run() {
		    	piero.stop();
		    	end_wait.play();
		    	end_wait.setOnFinished(e->{
		        	if(combo>maxcombo) {maxcombo=combo;}
		        	primaryStage.setScene(scene3);	        	
		        	text_score.setText("score: "+score);
		    		text_score.setFill(Color.WHITE);
		        	set_text_style(text_score,210,195,33);
		        	text_prefect.setText("prefect: "+prefect);
		        	set_text_style(text_prefect,230,300,28);
		        	text_good.setText("good: "+good);
		        	set_text_style(text_good,230,350,28);
		        	text_miss.setText("miss: "+miss);
		        	set_text_style(text_miss,230,400,28);
		        	text_maxcombo.setText("max combo: "+maxcombo);
		        	set_text_style(text_maxcombo,470,300,28);
		        	        	
		        });    	
		    }
		});
		sakura.setOnEndOfMedia(new Runnable() {  //第二首音樂結束跳到結算頁面
		    @Override
		    public void run() {
		    	sakura.stop();
		        end_wait.play();
		        end_wait.setOnFinished(e->{
		        	if(combo>maxcombo) {maxcombo=combo;}
		        	primaryStage.setScene(scene3);	        	
		        	text_score.setText("score: "+score);
		    		text_score.setFill(Color.WHITE);
		        	set_text_style(text_score,210,195,33);
		        	text_prefect.setText("prefect: "+prefect);
		        	set_text_style(text_prefect,230,300,28);
		        	text_good.setText("good: "+good);
		        	set_text_style(text_good,230,350,28);
		        	text_miss.setText("miss: "+miss);
		        	set_text_style(text_miss,230,400,28);
		        	text_maxcombo.setText("max combo: "+maxcombo);
		        	set_text_style(text_maxcombo,470,300,28);
		        	        	
		        });    	
		    }
		});
		
	
	}
	public void set_text_style(Text t,int x,int y,int n) {
		t.setFont(Font.loadFont(new File("src/font/勘亭流.TTC").toURI().toString(),n));	
		t.setTranslateX(x);
		t.setTranslateY(y);		
	}
	public void set_circle_style(Circle c) {
		if(c.equals(c1)||c.equals(c2)||c.equals(c3)||c.equals(c4)||c.equals(c5)) {
			ImagePattern cir_imagePattern1 = new ImagePattern(new Image("image/red_hit.png"));
			c.setFill(cir_imagePattern1);
		}
		else {
			ImagePattern cir_imagePattern2 = new ImagePattern(new Image("image/blue_hit.png"));
			c.setFill(cir_imagePattern2);
		}
		
	}
	public void judge(Circle c) {  //按下按鍵後的判定
		if(c.equals(c1)||c.equals(c2)||c.equals(c3)||c.equals(c4)||c.equals(c5)) {
			label_miss.setStyle("-fx-translate-x:77;-fx-translate-y:63");
			label_good.setStyle("-fx-translate-x:77;-fx-translate-y:63");
			label_prefect.setStyle("-fx-translate-x:60;-fx-translate-y:63");
		}
		else {
			label_miss.setStyle("-fx-translate-x:77;-fx-translate-y:273");
			label_good.setStyle("-fx-translate-x:77;-fx-translate-y:273");
			label_prefect.setStyle("-fx-translate-x:60;-fx-translate-y:273");
		}
		if(c.getTranslateX()>=-880 && c.getTranslateX()<=-820) {
			c.setVisible(false);
			label_prefect.setVisible(true);
			label_good.setVisible(false);
			label_miss.setVisible(false);
			keep.play();
			keep.setOnFinished(e3->label_prefect.setVisible(false));
			prefect+=1;
			combo+=1;
			score+=200;
			label_combo.setText("Combo "+combo);
			label_combo.setVisible(true);					
			}	
		else if(c.getTranslateX()>=-910 && c.getTranslateX()<=-790) {
			c.setVisible(false);
			label_good.setVisible(true);
			label_miss.setVisible(false);
			label_prefect.setVisible(false);
			keep.play();
			keep.setOnFinished(e3->label_good.setVisible(false));
			good+=1;
			combo+=1;
			score+=100;
			label_combo.setText("Combo "+combo);
			label_combo.setVisible(true);
		}
	}
	public void long_judge(Group g) {
		if(g.getTranslateX()<=-850 && g.getTranslateX()>=-1250 ) {
    		combo+=1;
    		prefect+=1;
    		label_combo.setText("Combo "+combo);
			label_combo.setVisible(true); 
    	}
	}
	//節拍移動上排
	public void create_circle_up(Circle c,double num,Pane p,Scene scene) {	
		PauseTransition wait=new PauseTransition(Duration.millis(num));
		if(c.equals(c1)) {
			TranslateTransition t=new TranslateTransition(Duration.seconds(2),c1);
			t.setFromX(0);
	        t.setToX(-1000);
	        wait.play();
	        wait.setOnFinished(e->{	
	        	t.play();        	
	        });
	        t.setOnFinished(e->{
			if(c1.getTranslateX()<=-1000 && c1.isVisible()) {
				label_miss.setStyle("-fx-translate-x:77;-fx-translate-y:60");
				label_miss.setVisible(true);
				label_good.setVisible(false);
				label_prefect.setVisible(false);
				keep.play();
				keep.setOnFinished(event->label_miss.setVisible(false));
				miss+=1;
				if(combo>maxcombo) {maxcombo=combo;}
				combo=0;
				full_combo=0;
				label_combo.setVisible(false);		
			}
				c1.setVisible(true);
	        });	
	        
		}
		if(c.equals(c2)) {
			TranslateTransition t=new TranslateTransition(Duration.seconds(2),c2);
			t.setFromX(0);
	        t.setToX(-1000);
	        wait.play();
	        wait.setOnFinished(e->{	
	        	t.play();        	
	        });
	        t.setOnFinished(e->{
				if(c2.getTranslateX()<=-1000 && c2.isVisible()) {
					label_miss.setStyle("-fx-translate-x:77;-fx-translate-y:60");
					label_miss.setVisible(true);
					label_good.setVisible(false);
					label_prefect.setVisible(false);
					keep.play();
					keep.setOnFinished(event->label_miss.setVisible(false));
					miss+=1;
					if(combo>maxcombo) {maxcombo=combo;}
					combo=0;
					full_combo=0;
					label_combo.setVisible(false);		
				}
				c2.setVisible(true);
			});	
		} 
		if(c.equals(c3)) {
			TranslateTransition t=new TranslateTransition(Duration.seconds(2),c3);
			t.setFromX(0);
	        t.setToX(-1000);
	        wait.play();
	        wait.setOnFinished(e->{	
	        	t.play();        	
	        });
	        t.setOnFinished(e->{
				if(c3.getTranslateX()<=-1000 && c3.isVisible()) {
					label_miss.setStyle("-fx-translate-x:77;-fx-translate-y:60");
					label_miss.setVisible(true);
					label_good.setVisible(false);
					label_prefect.setVisible(false);
					keep.play();
					keep.setOnFinished(event->label_miss.setVisible(false));
					miss+=1;
					if(combo>maxcombo) {maxcombo=combo;}
					combo=0;
					full_combo=0;
					label_combo.setVisible(false);	
				}
				c3.setVisible(true);
			});	
		}
		if(c.equals(c4)) {
			TranslateTransition t=new TranslateTransition(Duration.seconds(2),c4);
			t.setFromX(0);
	        t.setToX(-1000);
	        wait.play();
	        wait.setOnFinished(e->{	
	        	t.play();        	
	        });
	        t.setOnFinished(e->{
				if(c4.getTranslateX()<=-1000 && c4.isVisible()) {
					label_miss.setStyle("-fx-translate-x:77;-fx-translate-y:60");
					label_miss.setVisible(true);
					label_good.setVisible(false);
					label_prefect.setVisible(false);
					keep.play();
					keep.setOnFinished(event->label_miss.setVisible(false));
					miss+=1;
					if(combo>maxcombo) {maxcombo=combo;}
					combo=0;
					full_combo=0;
					label_combo.setVisible(false);		
				}
				c4.setVisible(true);
			});	
		}
		if(c.equals(c5)) {
			TranslateTransition t=new TranslateTransition(Duration.seconds(2),c5);
			t.setFromX(0);
	        t.setToX(-1000);
	        wait.play();
	        wait.setOnFinished(e->{	
	        	t.play();        	
	        });
	        t.setOnFinished(e->{
				if(c5.getTranslateX()<=-1000 && c5.isVisible()) {
					label_miss.setStyle("-fx-translate-x:77;-fx-translate-y:60");
					label_miss.setVisible(true);
					label_good.setVisible(false);
					label_prefect.setVisible(false);
					keep.play();
					keep.setOnFinished(event->label_miss.setVisible(false));
					miss+=1;
					if(combo>maxcombo) {maxcombo=combo;}
					combo=0;
					full_combo=0;
					label_combo.setVisible(false);		
				}
				c5.setVisible(true);
			});	
		}
	}
	public void create_rec_up(Group g,double num,Pane p,Scene scene) {
		PauseTransition wait=new PauseTransition(Duration.millis(num));
		TranslateTransition t=new TranslateTransition(Duration.seconds(2.6),g);
		t.setFromX(0);
        t.setToX(-1300);
        wait.play();
        wait.setOnFinished(e->{	
        	t.play();        	
        });
	}
	//節拍移動下排
	public void create_circle_down(Circle c,double num,Pane p,Scene scene) {	
		PauseTransition wait=new PauseTransition(Duration.millis(num));
		if(c.equals(c6)) {
			TranslateTransition t=new TranslateTransition(Duration.seconds(2),c6);
			t.setFromX(0);
	        t.setToX(-1000);
	        wait.play();
	        wait.setOnFinished(e->{	
	        	t.play();        	
	        });
	        t.setOnFinished(e->{
			if(c6.getTranslateX()<=-1000 && c6.isVisible()) {
				label_miss.setStyle("-fx-translate-x:77;-fx-translate-y:270");
				label_miss.setVisible(true);
				label_good.setVisible(false);
				label_prefect.setVisible(false);
				keep.play();
				keep.setOnFinished(event->label_miss.setVisible(false));
				miss+=1;
				if(combo>maxcombo) {maxcombo=combo;}
				combo=0;
				full_combo=0;
				label_combo.setVisible(false);		
			}
				c6.setVisible(true);
	        });	
	        
		}
		if(c.equals(c7)) {
			TranslateTransition t=new TranslateTransition(Duration.seconds(2),c7);
			t.setFromX(0);
	        t.setToX(-1000);
	        wait.play();
	        wait.setOnFinished(e->{	
	        	t.play();        	
	        });
	        t.setOnFinished(e->{
				if(c7.getTranslateX()<=-1000 && c7.isVisible()) {
					label_miss.setStyle("-fx-translate-x:77;-fx-translate-y:270");
					label_miss.setVisible(true);
					label_good.setVisible(false);
					label_prefect.setVisible(false);
					keep.play();
					keep.setOnFinished(event->label_miss.setVisible(false));
					miss+=1;
					if(combo>maxcombo) {maxcombo=combo;}
					combo=0;
					full_combo=0;
					label_combo.setVisible(false);		
				}
				c7.setVisible(true);
	        });	     
		}
		if(c.equals(c8)) {
			TranslateTransition t=new TranslateTransition(Duration.seconds(2),c8);
			t.setFromX(0);
	        t.setToX(-1000);
	        wait.play();
	        wait.setOnFinished(e->{	
	        	t.play();        	
	        });
	        t.setOnFinished(e->{
				if(c8.getTranslateX()<=-1000 && c8.isVisible()) {
					label_miss.setStyle("-fx-translate-x:77;-fx-translate-y:270");
					label_miss.setVisible(true);
					label_good.setVisible(false);
					label_prefect.setVisible(false);
					keep.play();
					keep.setOnFinished(event->label_miss.setVisible(false));
					miss+=1;
					if(combo>maxcombo) {maxcombo=combo;}
					combo=0;
					full_combo=0;
					label_combo.setVisible(false);		
				}
				c8.setVisible(true);
	        });	        
		}
		if(c.equals(c9)) {
			TranslateTransition t=new TranslateTransition(Duration.seconds(2),c9);
			t.setFromX(0);
	        t.setToX(-1000);
	        wait.play();
	        wait.setOnFinished(e->{	
	        	t.play();        	
	        });
	        t.setOnFinished(e->{
				if(c9.getTranslateX()<=-1000 && c9.isVisible()) {
					label_miss.setStyle("-fx-translate-x:77;-fx-translate-y:270");
					label_miss.setVisible(true);
					label_good.setVisible(false);
					label_prefect.setVisible(false);
					keep.play();
					keep.setOnFinished(event->label_miss.setVisible(false));
					miss+=1;
					if(combo>maxcombo) {maxcombo=combo;}
					combo=0;
					full_combo=0;
					label_combo.setVisible(false);		
				}
				c9.setVisible(true);
			});	
		} 
		if(c.equals(c10)) {
			TranslateTransition t=new TranslateTransition(Duration.seconds(2),c10);
			t.setFromX(0);
	        t.setToX(-1000);
	        wait.play();
	        wait.setOnFinished(e->{	
	        	t.play();        	
	        });
	        t.setOnFinished(e->{
				if(c10.getTranslateX()<=-1000 && c10.isVisible()) {
					label_miss.setStyle("-fx-translate-x:77;-fx-translate-y:270");
					label_miss.setVisible(true);
					label_good.setVisible(false);
					label_prefect.setVisible(false);
					keep.play();
					keep.setOnFinished(event->label_miss.setVisible(false));
					miss+=1;
					if(combo>maxcombo) {maxcombo=combo;}
					combo=0;
					full_combo=0;
					label_combo.setVisible(false);		
				}
				c10.setVisible(true);
	        });	        
		}
	}
	public void create_rec_down(Group g,double num,Pane p,Scene scene) {
		PauseTransition wait=new PauseTransition(Duration.millis(num));
		TranslateTransition t=new TranslateTransition(Duration.seconds(2.6),g);
		t.setFromX(0);
        t.setToX(-1300);
        wait.play();
        wait.setOnFinished(e->{	
        	t.play();        	
        });
	}
	public static void main(String[] args) {
		launch(args);
	}
}
	