package view.subject;

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
import dao.SubjectDao;
import entity.Subject;

public class AlterSubject {
	SubjectDao sd = new SubjectDao();

	JLabel nameLabel1;
	JTextField nameText1;
	JFrame alterFrame;
	CallBack callBack;
	JButton sureButton1;
	Subject subject;
	String name1;
	String sex1;
	int stuNums;
	List<Subject> subjects = new ArrayList<Subject>();
	private static AlterSubject instance;

	private AlterSubject( CallBack callBack) {
		this.callBack = callBack;

	}

	public static AlterSubject getInstance(List<Subject> Subjects, 
			CallBack callBack) {
		
		if (instance == null) {
			instance = new AlterSubject(callBack);
		}
		return instance;
	}

	public void createFrame() {
		if (alterFrame == null) {
			alterFrame = new JFrame(Util.MES_ALTER + util.Util.SUB);
			init();
		} else {
			
			alterFrame.setVisible(true);
		}
	}
	public void setSubject(Subject stu){
		//System.out.println(444);
		this.subject=stu;
		nameText1.setText(stu.getName());
		//System.out.println("111");
	}

	// 用于创建窗口和布局控件
	public void init() {
	//	System.out.println("1111");
	//	System.out.println();
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
		nameLabel1 = new JLabel(util.Util.SUB_NAME);
		nameText1 = new JTextField();
		nameText1.setPreferredSize(new Dimension(120, 25));
		
		
		
		sureButton1 = new JButton(util.Util.MES_SURE);
		alterFrame.add(addPanel);
		p1.add(nameLabel1);
		p1.add(nameText1);
	
		p5.add(sureButton1);
		alterFrame.setSize(util.Util.SUB_ALTERVIEW_WIDTH, util.Util.SUB_ALTERVIEW_WIDTH);
		alterFrame.setLocationRelativeTo(null);
		alterFrame.setVisible(true);
		// 添加事件方法
		alterSubject1();
	}

	// sureButton1按钮事件监听
	public void alterSubject1() {
		sureButton1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				name1 = nameText1.getText();
				subject.setName(name1);
				int i = sd.motify(subject);
				boolean flag =false;
				if(i>0){
					flag=true;
				}
			ShowMessage.Show(flag, Util.MES_ALTER);

				alterFrame.dispose();
				//alterFrame = null;
				callBack.call();
			//	sd.save(Subjects);
			}
		});
	}
}
