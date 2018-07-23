import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.JTextField;
import javax.swing.JTextArea;
//test
public class main {
	private static JTextField textField;
	
	//POSSIBLE FUTURE UPDATES; //test
	//
	//--------core-------
	//-refresh questions from text file(read in new questions after drawOne set false, change questionOne from next read 
	//-validate user input/throw exceptions 
	//
	//---------extras-------
	//-Difficulty changes ( easy / med / hard, put in buttons to change between, and switch which file the questions read from)
	//-Animations for the meteor blowing up(when drawOne = false, set animation(or a set of them) to be true and show briefly
	//-Add a ship at the bottom to blow up each meteor (when drawOne set false, move ship to X position, then draw beam / particle sending to meteor) 
	//-better graphics (meteor + background space theme) 

	// controls if a meteor is drawn, true = drawn false = not drawn
	public static boolean drawOne = true;
	public static boolean drawTwo = true;
	public static boolean drawThree = true;
	public static boolean drawFour = true;

	//for future holding which line of the text file the reader is on
	public static int textLine = 0;
	
	// declaring our questions / answers which will be read from a text file.
	public static String questionOne = "";
	public static String questionTwo = "";
	public static String questionThree = "";
	public static String questionFour = "";

	public static String answerOne = "";
	public static String answerTwo = "";
	public static String answerThree = "";
	public static String answerFour = "";

	public static int answerOneNumber = 0;
	public static int answerTwoNumber = 0; 
	public static int answerThreeNumber = 0;
	public static int answerFourNumber = 0;
	
	//deciding if the game is over. True = game over, player failed. 
	public static boolean gameOver = false; 



	public static void main(String[] args) throws FileNotFoundException {
		Scanner read = new Scanner(new File("easy.txt")); 
		read.useDelimiter("=");

		while (read.hasNext()) {
			questionOne = read.next();
			answerOne = read.next();
			textLine++;
			questionTwo = read.next();
			answerTwo = read.next();
			textLine++;
			questionThree = read.next();
			answerThree = read.next();
			textLine++;
			questionFour = read.next();
			answerFour = read.next();
			textLine++;
			answerOneNumber = Integer.parseInt(answerOne);
			answerTwoNumber = Integer.parseInt(answerTwo);
			answerThreeNumber = Integer.parseInt(answerThree);
			answerFourNumber = Integer.parseInt(answerFour);
		}
		read.close();
		SwingUtilities.invokeLater(new Runnable() {
			@Override

			public void run() {
				JFrame frame = new JFrame("Meator Game");
				ImagePanel imagePanel = new ImagePanel();
				imagePanel.setForeground(Color.WHITE);
				frame.getContentPane().add(imagePanel);
				imagePanel.setLayout(null);

				textField = new JTextField();
				textField.setForeground(Color.BLACK);
				textField.setBounds(641, 741, 86, 20);
				textField.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						checkAnswer();
					}
				});
				imagePanel.add(textField);
				textField.setColumns(10);

				JTextArea txtrEnterTheAnswer = new JTextArea();
				txtrEnterTheAnswer.setBackground(Color.BLACK);
				txtrEnterTheAnswer.setForeground(Color.WHITE);
				txtrEnterTheAnswer.setText("Enter the answer before the meteor reaches the bottom!");
				txtrEnterTheAnswer.setBounds(306, 708, 456, 22);
				imagePanel.add(txtrEnterTheAnswer);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);

			}

			public void checkAnswer() {
				String answerNumber = textField.getText();
				int answer = Integer.parseInt(answerNumber);

				if (answer == answerOneNumber) {
					drawOne = false;
				}
				if (answer == answerTwoNumber) {
					drawTwo = false;
				}
				if(answer == answerThreeNumber){
					drawThree = false; 
				}
				if(answer == answerFourNumber){
					drawFour = false; 
				}
				textField.requestFocus();
				textField.selectAll();

			}
			
		});
	}

	private static class ImagePanel extends JPanel {

		URL[] urls;
		BufferedImage[] images;
		Random rand = new Random();
		private int x = 0;
		private int y = 0;

		public ImagePanel() {
			urls = new URL[5];
			try {
				urls[0] = new URL("https://img.rt.com/files/news/22/89/d0/00/asteroid.jpg");
				urls[1] = new URL("https://img.rt.com/files/news/22/89/d0/00/asteroid.jpg");
				urls[2] = new URL("https://img.rt.com/files/news/22/89/d0/00/asteroid.jpg");
				urls[3] = new URL("https://img.rt.com/files/news/22/89/d0/00/asteroid.jpg");
				urls[4] = new URL("https://img.rt.com/files/news/22/89/d0/00/asteroid.jpg");

				images = new BufferedImage[5];
				images[0] = ImageIO.read(urls[0]);
				images[1] = ImageIO.read(urls[1]);
				images[2] = ImageIO.read(urls[2]);
				images[3] = ImageIO.read(urls[3]);
				images[4] = ImageIO.read(urls[4]);

			} catch (MalformedURLException ex) {
				ex.printStackTrace();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			setBackground(Color.BLACK);

			Timer timer = new Timer(250, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					x = 0;
					y = y + 13; // how much the meteor goes down each time
					repaint();
					
					//resets meteors if all the meteors are gone
					if(drawOne == false && drawTwo == false && drawThree == false && drawFour == false){
						y = 0;
						drawOne = true;
						drawTwo = true;
						drawThree = true;
						drawFour = true;
					}
					if(y > 800) {
						gameOver = true;
					}

				}
			});

			timer.start();

		}
		
		
		@Override
		protected void paintComponent(Graphics g) {
			String yvalue = Integer.toString(y);
			super.paintComponent(g);
			BufferedImage img = images[0];
			if (drawOne) {
				g.drawImage(img, 25, y, 75, 75, this);
				g.drawString(questionOne, 25, y);
			}
			if (drawTwo) {
				g.drawImage(img, 150, (y - 25), 75, 75, this);
				g.drawString(questionTwo, 150, (y - 25));
			}
			if (drawThree) {
				g.drawImage(img, 275, (y - 40), 75, 75, this);
				g.drawString(questionThree, 275, (y-40));
			}
			if (drawFour) {
				g.drawImage(img, 400, (y - 80), 75, 75, this);
				g.drawString(questionFour, 400, (y-80));
			}
			if(gameOver){
				drawOne = false;
				drawTwo = false;
				drawThree = false;
				drawFour = false;	
				g.drawString("game over!", 400, 400);
			}

		}
		
		
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(800, 800);
		}
	}
}