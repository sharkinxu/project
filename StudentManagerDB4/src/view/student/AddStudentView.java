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
 * ������һ��������,�ô��������������
 * �����õ���ģʽ,����ֻ�ܴ���һ��
 * ���ڵ���ģʽ�ı׶�,���еĴ���������Ҫ����ˢ��
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
	//����ģʽ��ʵ��
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

	// ��ʼ�������ı�����Ϊ��
	public void setText() {
		nameText1.setText("");
		sexText1.setText("");
		ageText1.setText("");
		jbox.setSelectedIndex(0);
	}
	// ���ڴ������ںͲ��ֿؼ�
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
		 * ����JComboBox��Ҫһ����ʼֵ
		 * ����ĳ�ʼֵ:��һ����������༶,��������ڰ༶���б����İ༶����
		 * 
		 */
		jbox = new JComboBox();
		jbox.setPreferredSize(new Dimension(120, 25));
		banjis=bd.searchAll();
		jbox.addItem("������༶");
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

	// ���ѧ����ť�¼�����
	public void addStudent() {
		sureButton1.addActionListener(new ActionListener() {
			/**
			 * (1)�����ĸ��������������ı���������Ϣ
			 * (2)ע�����age��int���ͺ��ı���������ϵ��ƥ����Ҫ����ת��
			 * (3)�����б���Ҫ��ȡ���Ǳ�ѡȡ�༶����ֵ
			 * (4)Ȼ����������������ս����ж�(�Ժ���ܻ����)
			 * (5)��������Ϣ����������´���һ��student����,�����ı����ȡ��������ӵ�student��
			 * (6)ע����Ƕ��ڰ༶����д�����������Լ������,Ҫ����һ���༶��������ʵ�������ڰ༶�ļ�����ͨ���༶�����õ���
			 * (7)��Ϊ�����ڰ༶���б��м����˵�һ��,����������Ҫ�ڲ��Ұ༶������ʱ��(-1)
			 * (8)�������ݿ��෽���������
			 * (9)���ܷ��ص�int���͵�ֵ,���ж�,�����ж���ʾ�����Ի���
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
					JOptionPane.showMessageDialog(null, "�������ݲ�����",
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
