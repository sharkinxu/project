package view.student;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JTextField;

import util.CallBack;
import util.Util;
import view.ShowMessage;
import dao.BanJiDao;
import dao.StudentDao;
import entity.Banji;
import entity.Student;
/**
 * 本来是一个窗口类,该窗口用来添加数据
 * 本类用单例模式,窗口只能创建一个
 * 由于单例模式的弊端,所有的窗口内容需要重新刷新
 * @author lenovo
 *
 */
public class AddStudentView {
	List<Banji> banjis = new ArrayList<Banji>();
	JFrame addFrame;
	JLabel nameLabel1, sexLabel1, ageLabel1, bj_idabel1;
	JTextField nameText1, sexText1, ageText1 ;
	JComboBox jbox ;
	StudentDao sd = new StudentDao();
	BanJiDao bd = new BanJiDao();
	JButton sureButton1;
	CallBack callBack;
	//单例模式的实现
	private static AddStudentView instance;

	private AddStudentView(CallBack callBack) {
		this.callBack = callBack;
	}
	public void createFrame() {
		if (addFrame == null) {
			addFrame = new JFrame();
			init();
		} else {
			addFrame.setVisible(true);
		}
	}

	public static AddStudentView getInstance(CallBack callBack) {
		if (instance == null) {

			instance = new AddStudentView(callBack);
		}
		return instance;
	}

	// 初始化窗口文本内容为空
	public void setText() {
		nameText1.setText("");
		sexText1.setText("");
		ageText1.setText("");
		jbox.setSelectedIndex(0);
	}
	// 用于创建窗口和布局控件
	public void init() {
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		JPanel p4 = new JPanel();
		JPanel p5 = new JPanel();
		addFrame = new JFrame(util.Util.MES_ADD);
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
		bj_idabel1 = new JLabel(util.Util.STU_BJ);
		/*
		 * 对与JComboBox需要一个初始值
		 * 这里的初始值:第一个是请输入班级,其余的是在班级表中遍历的班级名称
		 * 
		 */
		jbox = new JComboBox();
		jbox.setPreferredSize(new Dimension(120, 25));
		banjis=bd.searchAll();
		jbox.addItem("请输入班级");
		for (Banji bj : banjis) {
			jbox.addItem(bj.getName());
		}
		
		sureButton1 = new JButton(util.Util.MES_SURE);
		addFrame.add(addPanel);
		p1.add(nameLabel1);
		p1.add(nameText1);
		p2.add(sexLabel1);
		p2.add(sexText1);
		p3.add(ageLabel1);
		p3.add(ageText1);
		p4.add(bj_idabel1);
		p4.add(jbox);
		p5.add(sureButton1);
		addFrame.setSize(util.Util.STU_ADDVIEW_WIDTH,
				util.Util.STU_ADDVIEW_HEIGHT);
		addFrame.setLocationRelativeTo(null);
		addFrame.setVisible(true);
		addStudent();

	}

	// 添加学生按钮事件监听
	public void addStudent() {
		sureButton1.addActionListener(new ActionListener() {
			/**
			 * (1)创建四个变量用来接收文本传来的信息
			 * (2)注意的是age的int类型和文本传来的星系不匹配需要进行转换
			 * (3)下拉列表需要获取的是被选取班级索引值
			 * (4)然后对输入输入完整姓进行判断(以后可能会更改)
			 * (5)在所有信息完整的情况下创建一个student对象,将从文本框获取的内容添加到student中
			 * (6)注意的是对于班级的填写并不是我们自己创造的,要创建一个班级对象他的实例化是在班级的集合中通过班级索引得到的
			 * (7)因为我们在班级的列表中加入了第一行,所以我们需要在查找班级索引的时候(-1)
			 * (8)调用数据库类方法添加数据
			 * (9)接受返回的int类型的值,并判断,根据判断显示弹出对话框
			 */
			@SuppressWarnings("null")
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = nameText1.getText();
				String sex = sexText1.getText();
				int age = 0;
				if (!ageText1.getText().equals("")) {
					age = Integer.valueOf(ageText1.getText());
				}
				int bj_id = 0;

				if (name.equals("") || sex.equals("") || age == 0 ) {
					JOptionPane.showMessageDialog(null, "输入数据不完整",
							util.Util.MES_REMIND,
							JOptionPane.INFORMATION_MESSAGE);
				} else {

					Student stu = new Student();
					stu.setName(name);
					stu.setAge(age);
					stu.setSex(sex);
					stu.setBj_id(bj_id);
					int index = jbox.getSelectedIndex();
					Banji bj =null;
					if(index==0){
						bj.setName("");
					}else{
					 bj = banjis.get(index-1);
					stu.setBj(bj);
					}
					boolean flag = false;
					int i = sd.add(stu);
					if (i > 0) {
						flag = true;
					}
					ShowMessage.Show(flag, Util.MES_ADD);
					addFrame.dispose();
					callBack.call();
				}
			}
		});
	}

}
