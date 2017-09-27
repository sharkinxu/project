package view.banji;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import util.CallBack;
import util.Util;
import view.ShowMessage;
import dao.BanJiDao;
import entity.Banji;

public class AlterBanji {
	BanJiDao sd = new BanJiDao();

	JLabel nameLabel1;
	JTextField nameText1;
	JFrame alterFrame;
	CallBack callBack;
	JButton sureButton1;
	Banji banji;
	String name1;
	String sex1;
	int stuNums;
	List<Banji> banjis = new ArrayList<Banji>();
	private static AlterBanji instance;

	private AlterBanji( CallBack callBack) {
		this.callBack = callBack;

	}

	public static AlterBanji getInstance(List<Banji> banjis, 
			CallBack callBack) {
		
		if (instance == null) {
			instance = new AlterBanji(callBack);
		}
		return instance;
	}

	public void createFrame() {
		if (alterFrame == null) {
			alterFrame = new JFrame(Util.MES_ALTER + util.Util.STU);
			init();
		} else {
			
			alterFrame.setVisible(true);
		}
	}
	public void setBanji(Banji stu){
		this.banji=stu;
		nameText1.setText(stu.getName());
//		stuNumsText1.setText(String.valueOf(stu.getStuNums()));
		}

	public void init() {
		JPanel p1 = new JPanel();
		JPanel p3 = new JPanel();
		JPanel p5 = new JPanel();

		JPanel addPanel = new JPanel();
		addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.Y_AXIS));
		p5.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 0));
		addPanel.add(Box.createRigidArea(new Dimension(0, 30)));
		addPanel.add(p1);
		addPanel.add(p3);
		addPanel.add(p5);
		nameLabel1 = new JLabel(util.Util.BJ_NAME);
		nameText1 = new JTextField();
		nameText1.setPreferredSize(new Dimension(120, 25));
		
	
		sureButton1 = new JButton(util.Util.MES_SURE);
		alterFrame.add(addPanel);
		p1.add(nameLabel1);
		p1.add(nameText1);
	
		p5.add(sureButton1);
		alterFrame.setSize(util.Util.STU_ALTERVIEW_WIDTH, util.Util.STU_ALTERVIEW_WIDTH);
		alterFrame.setLocationRelativeTo(null);
		alterFrame.setVisible(true);
		alterBanji1();
	}

	public void alterBanji1() {
		sureButton1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				name1 = nameText1.getText();
		
				banji.setName(name1);
				
				int i = sd.motify(banji);
				
				boolean flag =false;
				if(i>0){
					flag=true;
				}
			ShowMessage.Show(flag, Util.MES_ALTER);

				alterFrame.dispose();
				callBack.call();
			}
		});
	}
}
