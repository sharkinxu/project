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
 * ������һ��������,�ô��������޸����� �����õ���ģʽ,����ֻ�ܴ���һ�� ���ڵ���ģʽ�ı׶�,���еĴ���������Ҫ����ˢ��
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

	// ����ģʽ��Ӧ��
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

	// ����һ��student�Ĳ���,����ˢ�µ���ģʽ�µĴ�����Ϣ
	public void setStudent(Student stu) {
		this.stu = stu;
		nameText1.setText(stu.getName());
		sexText1.setText(stu.getSex());
		ageText1.setText(String.valueOf(stu.getAge()));
		setJBox();
	}

	// ���ڴ������ںͲ��ֿؼ�
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
		 * ����JComboBox��Ҫһ����ʼֵ ����ĳ�ʼֵ:��һ����������༶,��������ڰ༶���б����İ༶����
		 */
		banjis = bd.searchAll();
		jbox.addItem("������༶");
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

	// sureButton1��ť�¼�����
	public void alterStudent1() {
		sureButton1.addActionListener(new ActionListener() {
			/**
			 * (1)���½������stu��ֵ,���ʱ�򸳵�ֵ�������޸ĵ������� (2)�������б�Ĳ�ȡ��ȡ��ȡ����ֵ�ķ�ʽ
			 * (3)�Ի�õı����������·�װ (3)�ڴ��ڼ�����ѧ����������������һ���༶����,����������Ҫ�½���һ���༶����װ��ѧ����
			 * (4)����װ�õ�student���������ݿ���Ĳ�ѯ������ (5)���ܲ�ѯ�����ķ���ֵ�����ж��޸��Ƿ�ɹ�,��������
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
	 * �����������ڵ���ģʽ�»����ȷ�İ༶��Ϣ
	 */
	private void setJBox() {
		jbox.setSelectedIndex(0);
		for (int i = 0; i < banjis.size(); i++) {
			System.out.println("�༶��id" + banjis.get(i).getId());
			if (banjis.get(i).getId() == stu.getBj().getId()) {
				jbox.setSelectedIndex(i + 1);
			}
		}
	}
}
