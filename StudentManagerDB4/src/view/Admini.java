package view;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import view.banji.BanjiView;
import view.score.ScoreView;
import view.student.StudentView;
import view.subject.SubjectView;

public class Admini extends JFrame {
	Admini() {
		JPanel mainPanel = new JPanel();
		JButton studentButton = new JButton("学生表");
		JButton calssButton = new JButton("班级表");
		JButton subjectButton = new JButton("课程表");
		JButton scoreButton = new JButton("成绩表");
		mainPanel.add(studentButton);
		mainPanel.add(calssButton);
		mainPanel.add(subjectButton);
		mainPanel.add(scoreButton);

		this.setContentPane(mainPanel);
		mainPanel.setLayout(new GridLayout(2, 2));

		this.setSize(400, 300);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		studentButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			StudentView sv = StudentView.getInstance();
			sv.createFrame();
			}
		});
		
		
		calssButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				BanjiView bv=BanjiView.getInstance();
				bv.createFrame();
			}
		});
		subjectButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				SubjectView subv=SubjectView.getInstance();
				subv.createFrame();
			}
		});
		scoreButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ScoreView sv = ScoreView.getInstance();
				sv.createFrame();
			}
		});
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Admini();
	}
}
