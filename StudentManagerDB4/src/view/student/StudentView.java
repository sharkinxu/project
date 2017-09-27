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
 * 本项目是为界面 定义了一个boxLayout布局用来修饰底层panel（centerp） 定义了三个panel（p1,p2,p3）放入centerp
 * 将panel中分别加入相应的控件
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

	StudentDao sd = new StudentDao();// 定义学生数据链接
	List<Student> stus;// 建立学生集合
	StudentTableModel tableModel;//
	BanJiDao bd = new BanJiDao();// 定义班级数据链接
	List<Banji> banjis;// 建立班级集合
	/**
	 * 用于实现单例模式
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

	// 创建窗口
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

	// 设置第一个panel
	public void panel1() {
		p1 = new JPanel();
		// 重点在于设置他的高度
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
		jbox.addItem("请输入班级");
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

	// 设置第二个panel
	public void table() {
		p2 = new JPanel();
		p2.setPreferredSize(new Dimension(0, 440));
		tableModel = new StudentTableModel(stus);
		table = new JTable(tableModel);
		jsp = new JScrollPane(table);// 滚动条
		p2.add(jsp);
	}

	// 设置第三个panel
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

	// sureButton按钮事件监听
	public void selectStudent() {
		sureButton.addActionListener(new ActionListener() {
			/**
			 * (1)创建根据类型四个变量用来接受窗口传入的信息
			 * (2)对int类型的变量的接受进行从String到int的转换(String为空int=0)
			 * (3)对下拉列表的采取获取获取索引值的方式 (4)对获得的变量进行判断,从而拼接成sql语句
			 * (5)在此期间有在学生的属性中生成了一个班级的类,所以我们需要新建立一个班级的类装入学生中
			 * (6)将我们拼接的sql语句传入数据库类的查询方法中 (7)接受查询方法的返回值,调用刷新方法刷新窗口表
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

	// 为delete按钮添加事件
	public void deleteStudent() {
		deleteButton.addActionListener(new ActionListener() {
			/**
			 * (1)对获得表的行数进行判断:如果没有选中(getSelectIndex值为-1)弹出对话框
			 * (2)因为表的索引和集合的索引都是由0开始,所以表中在索引位置的student就是集合中在索引位置上的student
			 * (3)在选中表中某行的情况下,创建一个student对象,用来接受list中查询出的学生信息
			 * (5)由于删除一条信息只需要一个id(唯一确定)就足够了所以传入一个int的id给数据库类的删除方法
			 * (6)接受一个int返回值这个返回值的意思是受影响的行数 (7)对受影响的行数进行判断,并设置弹窗
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
					JOptionPane.showMessageDialog(null, "请选择你要"
							+ util.Util.MES_DELETE + "的数据",
							util.Util.MES_REMIND,
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
	}

	// 为alterButton添加事件监听
	public void alterStudent() {
		alterButton.addActionListener(new ActionListener() {
			/**
			 * (1)对获得表的行数进行判断:如果没有选中(getSelectIndex值为-1)弹出对话框
			 * (2)因为表的索引和集合的索引都是由0开始,所以表中在索引位置的student就是集合中在索引位置上的student
			 * (3)创建一个修改窗口类的实例化as,用于显示新的窗口完成修改
			 * (4)为as的方法传入一个student,就是利用表的索引获取student集合里的学生信息
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
					System.out.println("班级 id   " + stus.get(index).getBj_id());
					as.setStudent(stus.get(index));
				} else {
					JOptionPane.showMessageDialog(null, "请选择你要修改的数据", "提示",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		table.revalidate();
	}
//对addButton按钮的事件监听
	public void addStudent() {
		addButton.addActionListener(new ActionListener() {
			/**
			 * (1)创建一个添加窗口asv
			 * (2)调用asv方法进创建窗口和清空赋值.
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