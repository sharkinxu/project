package view.student;

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
import javax.swing.JComboBox;
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
import dao.StudentDao;
import entity.Banji;
import entity.Student;

/**
 * 本来是一个窗口类,该窗口用来修改数据 本类用单例模式,窗口只能创建一个 由于单例模式的弊端,所有的窗口内容需要重新刷新
 * 
 * @author lenovo
 * 
 */
public class AlterStudent {
	StudentDao sd = new StudentDao();

	JLabel nameLabel1, sexLabel1, ageLabel1;
	JTextField nameText1, sexText1, ageText1;
	JFrame alterFrame;
	CallBack callBack;
	JButton sureButton1;
	Student stu;
	String name1;
	String sex1;
	int age1;
	String banji1;
	JComboBox jbox;

	BanJiDao bd = new BanJiDao();
	List<Banji> banjis = new ArrayList<Banji>();

	// 单例模式的应用
	private static AlterStudent instance;

	private AlterStudent(CallBack callBack) {
		this.callBack = callBack;
	}

	public static AlterStudent getInstance(CallBack callBack) {
		if (instance == null) {
			instance = new AlterStudent(callBack);
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

	// 传入一个student的参数,用来刷新单例模式下的窗口信息
	public void setStudent(Student stu) {
		this.stu = stu;
		nameText1.setText(stu.getName());
		sexText1.setText(stu.getSex());
		ageText1.setText(String.valueOf(stu.getAge()));
		setJBox();
	}

	// 用于创建窗口和布局控件
	public void init() {

		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		JPanel p4 = new JPanel();
		JPanel p5 = new JPanel();

		JPanel addPanel = new JPanel();
		addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.Y_AXIS));
		p5.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 0));
		addPanel.add(Box.createRigidArea(new Dimension(0, 30)));
		addPanel.add(p1);
		addPanel.add(p2);
		addPanel.add(p3);
		addPanel.add(p4);
		addPanel.add(p5);
		nameLabel1 = new JLabel(util.Util.STU_NAME);
		nameText1 = new JTextField();
		nameText1.setPreferredSize(new Dimension(120, 25));
		sexLabel1 = new JLabel(util.Util.STU_SEX);
		sexText1 = new JTextField();
		sexText1.setPreferredSize(new Dimension(120, 25));
		ageLabel1 = new JLabel(util.Util.STU_AGE);
		ageText1 = new JTextField();
		ageText1.setPreferredSize(new Dimension(120, 25));
		jbox = new JComboBox();
		jbox.setPreferredSize(new Dimension(120, 25));

		/*
		 * 对与JComboBox需要一个初始值 这里的初始值:第一个是请输入班级,其余的是在班级表中遍历的班级名称
		 */
		banjis = bd.searchAll();
		jbox.addItem("请输入班级");
		for (Banji bj : banjis) {
			jbox.addItem(bj.getName());

		}

		sureButton1 = new JButton(util.Util.MES_SURE);
		alterFrame.add(addPanel);
		p1.add(nameLabel1);
		p1.add(nameText1);
		p2.add(sexLabel1);
		p2.add(sexText1);
		p3.add(ageLabel1);
		p3.add(ageText1);
		p4.add(jbox);
		p5.add(sureButton1);
		alterFrame.setSize(util.Util.STU_ALTERVIEW_WIDTH,
				util.Util.STU_ALTERVIEW_WIDTH);
		alterFrame.setLocationRelativeTo(null);
		alterFrame.setVisible(true);
		alterStudent1();
	}

	// sureButton1按钮事件监听
	public void alterStudent1() {
		sureButton1.addActionListener(new ActionListener() {
			/**
			 * (1)重新将传入的stu赋值,这个时候赋的值是我们修改的新数据 (2)对下拉列表的采取获取获取索引值的方式
			 * (3)对获得的变量进行重新封装 (3)在此期间有在学生的属性中生成了一个班级的类,所以我们需要新建立一个班级的类装入学生中
			 * (4)将封装好的student对象传入数据库类的查询方法中 (5)接受查询方法的返回值用来判断修改是否成功,弹出窗口
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				name1 = nameText1.getText();
				sex1 = sexText1.getText();
				age1 = Integer.valueOf(ageText1.getText());

				stu.setName(name1);
				stu.setSex(sex1);
				stu.setAge(age1);
				int index = jbox.getSelectedIndex();
				Banji bj = new Banji();
				if (index > 0) {

					bj = banjis.get(index - 1);

				} else {
					bj.setId(0);
				}
				stu.setBj(bj);
				int i = sd.motify(stu);

				boolean flag = false;
				if (i > 0) {
					flag = true;
				}
				ShowMessage.Show(flag, Util.MES_ALTER);

				alterFrame.dispose();
				callBack.call();
			}
		});
	}

	/**
	 * 本方法用来在单例模式下获得正确的班级信息
	 */
	private void setJBox() {
		jbox.setSelectedIndex(0);
		for (int i = 0; i < banjis.size(); i++) {
			System.out.println("班级：id" + banjis.get(i).getId());
			if (banjis.get(i).getId() == stu.getBj().getId()) {
				jbox.setSelectedIndex(i + 1);
			}
		}
	}
}
