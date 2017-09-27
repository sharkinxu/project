package view.student;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import util.CallBack;
import view.ShowMessage;
import dao.BanJiDao;
import dao.StudentDao;
import entity.Banji;
import entity.Student;

/**
 * ����Ŀ��Ϊ���� ������һ��boxLayout�����������εײ�panel��centerp�� ����������panel��p1,p2,p3������centerp
 * ��panel�зֱ������Ӧ�Ŀؼ�
 * 
 * @author lenovo
 * 
 */
public class StudentView {
	JFrame f;
	JPanel centerp, p1, p2, p3;
	JLabel nameLabel, sexLabel, ageLabel;
	JTextField nameText, sexText, ageText;
	JTable table = null;
	JScrollPane jsp = null;
	CallBack callback;
	JButton sureButton, addButton, alterButton, deleteButton;
	JComboBox jbox;

	StudentDao sd = new StudentDao();// ����ѧ����������
	List<Student> stus;// ����ѧ������
	StudentTableModel tableModel;//
	BanJiDao bd = new BanJiDao();// ����༶��������
	List<Banji> banjis;// �����༶����
	/**
	 * ����ʵ�ֵ���ģʽ
	 */
	private static StudentView instance;

	private StudentView() {
	}

	public static StudentView getInstance() {
		if (instance == null) {
			instance = new StudentView();
		}
		return instance;
	}

	public void createFrame() {

		if (f == null) {
			f = new JFrame();
			init();
		} else {
			refreshTable();
			f.setVisible(true);
		}

	}

	// ��������
	public void init() {

		stus = sd.searchAll();

		centerp = new JPanel();
		centerp.setLayout(new BoxLayout(centerp, BoxLayout.Y_AXIS));

		panel1();
		table();
		panel3();

		centerp.add(p1);
		centerp.add(p2);
		centerp.add(p3);

		f.setSize(util.Util.STU_MAINVIEW_WIDTH, util.Util.STU_MAINVIEW_HEIGHT);
		f.setLocationRelativeTo(null);
		f.setTitle(util.Util.STU_VIEW_NAME);

		f.add(centerp);

		deleteStudent();
		alterStudent();
		addStudent();
		selectStudent();

		f.setVisible(true);
	}

	// ���õ�һ��panel
	public void panel1() {
		p1 = new JPanel();
		// �ص������������ĸ߶�
		p1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 20));
		p1.setPreferredSize(new Dimension(0, 60));

		nameLabel = new JLabel(util.Util.STU_NAME);
		nameText = new JTextField(8);
		sexLabel = new JLabel(util.Util.STU_SEX);
		sexText = new JTextField(8);
		ageLabel = new JLabel(util.Util.STU_AGE);
		ageText = new JTextField(8);
		jbox = new JComboBox();
		banjis = bd.searchAll();
		jbox.addItem("������༶");
		for (Banji bj : banjis) {
			jbox.addItem(bj.getName());
		}
		sureButton = new JButton(util.Util.MES_SURE);
		p1.add(nameLabel);
		p1.add(nameText);
		p1.add(sexLabel);
		p1.add(sexText);
		p1.add(ageLabel);
		p1.add(ageText);
		p1.add(jbox);
		p1.add(sureButton);

	}

	// ���õڶ���panel
	public void table() {
		p2 = new JPanel();
		p2.setPreferredSize(new Dimension(0, 440));
		tableModel = new StudentTableModel(stus);
		table = new JTable(tableModel);
		jsp = new JScrollPane(table);// ������
		p2.add(jsp);
	}

	// ���õ�����panel
	public void panel3() {
		p3 = new JPanel();
		p3.setPreferredSize(new Dimension(0, 100));
		p3.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 40));
		addButton = new JButton(util.Util.MES_ADD);
		alterButton = new JButton(util.Util.MES_ALTER);
		deleteButton = new JButton(util.Util.MES_DELETE);
		p3.add(addButton);
		p3.add(alterButton);
		p3.add(deleteButton);
	}

	// sureButton��ť�¼�����
	public void selectStudent() {
		sureButton.addActionListener(new ActionListener() {
			/**
			 * (1)�������������ĸ������������ܴ��ڴ������Ϣ
			 * (2)��int���͵ı����Ľ��ܽ��д�String��int��ת��(StringΪ��int=0)
			 * (3)�������б�Ĳ�ȡ��ȡ��ȡ����ֵ�ķ�ʽ (4)�Ի�õı��������ж�,�Ӷ�ƴ�ӳ�sql���
			 * (5)�ڴ��ڼ�����ѧ����������������һ���༶����,����������Ҫ�½���һ���༶����װ��ѧ����
			 * (6)������ƴ�ӵ�sql��䴫�����ݿ���Ĳ�ѯ������ (7)���ܲ�ѯ�����ķ���ֵ,����ˢ�·���ˢ�´��ڱ�
			 */
			@Override
			public void actionPerformed(ActionEvent e) {

				String name = nameText.getText();
				String sex = sexText.getText();
				System.out.println(111);
				int index = jbox.getSelectedIndex();
				Banji bj = new Banji();
				if (index > 0) {
					if (index <= banjis.size()) {
						bj = banjis.get(index - 1);
					} else {
						bj.setId(-1);
					}
				}
				int age;
				if (ageText.getText().equals("")) {
					age = 0;
				} else {
					age = Integer.valueOf(ageText.getText());
				}
				String sql = null;
				String where = " where 1=1 ";

				if (!name.equals("")) {
					where += "and s.name ='" + name + "'";
				}
				if (!sex.equals("")) {
					where += "and s.sex ='" + sex + "'";
				}
				if (age != 0) {
					where += "and s.age =" + age;
				}
				if (index != 0) {
					where += " and  bj_id=" + bj.getId();
				}
				sql = "select s.*,b.name,b.stuNums FROM student as s left join banji as b on b.id=s.bj_id"
						+ where;
				System.out.println(sql);
				stus = sd.search(sql);
				refreshTable(stus);
			}
		});
	}

	// Ϊdelete��ť����¼�
	public void deleteStudent() {
		deleteButton.addActionListener(new ActionListener() {
			/**
			 * (1)�Ի�ñ�����������ж�:���û��ѡ��(getSelectIndexֵΪ-1)�����Ի���
			 * (2)��Ϊ��������ͼ��ϵ�����������0��ʼ,���Ա���������λ�õ�student���Ǽ�����������λ���ϵ�student
			 * (3)��ѡ�б���ĳ�е������,����һ��student����,��������list�в�ѯ����ѧ����Ϣ
			 * (5)����ɾ��һ����Ϣֻ��Ҫһ��id(Ψһȷ��)���㹻�����Դ���һ��int��id�����ݿ����ɾ������
			 * (6)����һ��int����ֵ�������ֵ����˼����Ӱ������� (7)����Ӱ������������ж�,�����õ���
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() != -1) {
					int index = JOptionPane.showConfirmDialog(null,
							util.Util.MES_DELETE_Y_N);
					if (index == 0) {
						int a = table.getSelectedRow();
						Student temp = stus.get(a);
						int id = temp.getId();
						int i = sd.dalete(id);
						boolean flag = false;
						if (i > 0) {
							flag = true;
						}
						ShowMessage.Show(flag, util.Util.MES_DELETE);
						refreshTable();
					}
				} else {
					JOptionPane.showMessageDialog(null, "��ѡ����Ҫ"
							+ util.Util.MES_DELETE + "������",
							util.Util.MES_REMIND,
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
	}

	// ΪalterButton����¼�����
	public void alterStudent() {
		alterButton.addActionListener(new ActionListener() {
			/**
			 * (1)�Ի�ñ�����������ж�:���û��ѡ��(getSelectIndexֵΪ-1)�����Ի���
			 * (2)��Ϊ��������ͼ��ϵ�����������0��ʼ,���Ա���������λ�õ�student���Ǽ�����������λ���ϵ�student
			 * (3)����һ���޸Ĵ������ʵ����as,������ʾ�µĴ�������޸�
			 * (4)Ϊas�ķ�������һ��student,�������ñ��������ȡstudent�������ѧ����Ϣ
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = table.getSelectedRow();

				if (index != -1) {

					AlterStudent as = AlterStudent.getInstance(new CallBack() {

						@Override
						public void call() {
							refreshTable();
						}
					});
					as.createFrame();
					System.out.println("�༶ id   " + stus.get(index).getBj_id());
					as.setStudent(stus.get(index));
				} else {
					JOptionPane.showMessageDialog(null, "��ѡ����Ҫ�޸ĵ�����", "��ʾ",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		table.revalidate();
	}
//��addButton��ť���¼�����
	public void addStudent() {
		addButton.addActionListener(new ActionListener() {
			/**
			 * (1)����һ����Ӵ���asv
			 * (2)����asv�������������ں���ո�ֵ.
			 * 
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				AddStudentView asv = AddStudentView.getInstance(new CallBack() {

					@Override
					public void call() {
						refreshTable();

					}
				});
				asv.createFrame();
				asv.setText();
			}
		});
	}

	public void refreshTable() {

		System.out.println();
		stus = sd.searchAll();
		tableModel.setDate(stus);
		tableModel.fireTableDataChanged();
	}

	public void refreshTable(List stus) {

		tableModel.setDate(stus);
		tableModel.fireTableDataChanged();
	}
}