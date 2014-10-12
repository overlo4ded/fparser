package com.overlo4ded.fol.reader.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;

import javax.swing.JTextField;

import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JProgressBar;

import com.overlo4ded.fol.reader.ParserListener;
import com.overlo4ded.fol.reader.Reader;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SwingUI implements ParserListener{

	private JFrame frame;
	private JTextField txtFolUrl;
	private JTextField txtLastPage;
	private JLabel lblProgress;
	
	
	private Reader reader = new Reader();
	private int start=0;
	private int end=0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SwingUI window = new SwingUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SwingUI() {
		initialize();
		reader.addParserListener(this);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblFolUrl = new JLabel("FOL URL");
		GridBagConstraints gbc_lblFolUrl = new GridBagConstraints();
		gbc_lblFolUrl.insets = new Insets(0, 0, 5, 5);
		gbc_lblFolUrl.anchor = GridBagConstraints.EAST;
		gbc_lblFolUrl.gridx = 0;
		gbc_lblFolUrl.gridy = 2;
		panel.add(lblFolUrl, gbc_lblFolUrl);
		
		txtFolUrl = new JTextField();
		GridBagConstraints gbc_txtFolUrl = new GridBagConstraints();
		gbc_txtFolUrl.insets = new Insets(0, 0, 5, 0);
		gbc_txtFolUrl.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFolUrl.gridx = 1;
		gbc_txtFolUrl.gridy = 2;
		panel.add(txtFolUrl, gbc_txtFolUrl);
		txtFolUrl.setColumns(10);
		
		JLabel lblLastPage = new JLabel("Last Page");
		GridBagConstraints gbc_lblLastPage = new GridBagConstraints();
		gbc_lblLastPage.anchor = GridBagConstraints.EAST;
		gbc_lblLastPage.insets = new Insets(0, 0, 5, 5);
		gbc_lblLastPage.gridx = 0;
		gbc_lblLastPage.gridy = 3;
		panel.add(lblLastPage, gbc_lblLastPage);
		
		txtLastPage = new JTextField();
		GridBagConstraints gbc_txtLastPage = new GridBagConstraints();
		gbc_txtLastPage.insets = new Insets(0, 0, 5, 0);
		gbc_txtLastPage.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLastPage.gridx = 1;
		gbc_txtLastPage.gridy = 3;
		panel.add(txtLastPage, gbc_txtLastPage);
		txtLastPage.setColumns(10);
		
		JButton btnRun = new JButton("Run");
		btnRun.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				reader.read(txtFolUrl.getText(),txtLastPage.getText());
			}
		});
		GridBagConstraints gbc_btnRun = new GridBagConstraints();
		gbc_btnRun.insets = new Insets(0, 0, 5, 0);
		gbc_btnRun.gridx = 1;
		gbc_btnRun.gridy = 4;
		panel.add(btnRun, gbc_btnRun);
		
		lblProgress = new JLabel("");
		GridBagConstraints gbc_lblProgress = new GridBagConstraints();
		gbc_lblProgress.insets = new Insets(0, 0, 5, 0);
		gbc_lblProgress.gridx = 1;
		gbc_lblProgress.gridy = 5;
		panel.add(lblProgress, gbc_lblProgress);
	}

	@Override
	public void completed(String page) {
		end++;
		lblProgress.setText("processing "+start+" of "+end);
	}

	@Override
	public void computedIterations(int min, int max) {
		start=min;
		end=max;
		lblProgress.setText("processing "+start+" of "+end);
		
	}

}
