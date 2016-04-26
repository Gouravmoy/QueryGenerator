package com.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import com.jtattoo.plaf.acryl.AcrylLookAndFeel;
import com.util.PropsLoader;

public class Tesla00 extends JFrame {
	public Tesla00() {
	}

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		try {

			//splashScreen();
			Properties props = new Properties();
			props.put("logoString", "VIGO");
			AcrylLookAndFeel.setCurrentTheme(props);
			UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");

		} catch (Exception e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// PropsLoader.loadProps();
					PropsLoader loader = new PropsLoader();
					loader.loadProps();
					Tesla0 frame = new Tesla0();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private static void splashScreen() {
		JWindow window = new JWindow();
		window.getContentPane().setBackground(new Color(51, 43, 51));
		window.getContentPane()
				.add(new JLabel("", new ImageIcon(Tesla00.class.getResource("/png/main2.gif")), SwingConstants.CENTER));
		window.setSize(400, 400);

		window.setLocationRelativeTo(null);

		window.setVisible(true);
		try {
			/*Thread.sleep(6500);*/
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		window.setVisible(false);
		window.dispose();
	}

}
