package view.subject;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import util.CallBack;
import view.ShowMessage;
import dao.SubjectDao;
import entity.Subject;

/**
 * 本项目是为界面 定义了一个boxLayout布局用来修饰底层panel（centerp） 定义了三个panel（p1,p2,p3）放入centerp
 * 将panel中分别加入相应的控件
 * 
 * @author lenovo
 * 
 */
public class SubjectView {
	// 定义成员变量
	JFrame f ;
	JPanel centerp, p1, p2, p3;// 布局中间容器
	JLabel nameLabel;// label控件
	JTextField nameText;// text控件
	JTable table = null;// table控件
	JScrollPane jsp = null;// 滚动条控件
	CallBack callback;// 建立callBack用于回掉
	JButton sureButton, addButton, alterButton, deleteButton;// 建立button控件
	String name;// 用于在控制台存储姓名
	List<Subject> searchList = new ArrayList<Subject>();// 建立中间集合用于表格显示
//	int stuNum;// 用于在控制台存储年龄
	SubjectDao sd = new SubjectDao();// 实例化SubjectDao类,调用里面的方法用于存储和加载
	List<Subject> subjects;// 建立学生集合索引
	SubjectTableModel tableModel;// 建立SubjectTableModel索引
	private static SubjectView instance;
	private SubjectView() {

	}

	public static SubjectView getInstance() {

		if (instance == null) {
			instance = new SubjectView();
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
	// 构造方法用于构建界面和添加事件
	public void init() {
		// 载入缓存学生信息
		subjects = sd.searchAll();// 调用加载方法将文件里的集合加载进sstu集合中,应征了如果想要实例化一个对象,不仅可以new一个,还可以从文件中获取
		if (subjects == null) {// 判断是否为空
			subjects = new ArrayList<Subject>();// 如果为空则实例化一个stus集合,没有这个文件或者文件中没有数据而产生的读数据异常
		}
		for (int i = 0; i < subjects.size(); i++) {
			searchList.add(subjects.get(i));// 将stus集合的实例化单个索引赋值到searchList集合,这样的操作就是重新建立了一个集合,集合所开辟空间不同,但是集合中的存储的stus的内存地址相同
		}
		// 定义主画板和其属性
		centerp = new JPanel();
		centerp.setLayout(new BoxLayout(centerp, BoxLayout.Y_AXIS));
		// 给第二层容器添加控件
		panel1();
		table();
		panel3();
		// 将二层中间容器和控件加入底层容器
		centerp.add(p1);
		centerp.add(p2);
		centerp.add(p3);

		// 创建窗口并设置窗口属性
	
		f.setSize(util.Util.SUB_MAINVIEW_WIDTH, util.Util.SUB_MAINVIEW_HEIGHT);
		f.setLocationRelativeTo(null);
		f.setTitle(util.Util.SUB_VIEW_NAME);
		
		f.add(centerp);
		// 添加事件的方法
		deleteSubject();
		alterSubject();
		addSubject();
		selectSubject();
		// 设置窗口可见
		f.setVisible(true);
	}
	

	// 设置第一个panel
	public void panel1() {
		p1 = new JPanel();
		// 重点在于设置他的高度
		p1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 20));
		p1.setPreferredSize(new Dimension(0, 60));

		nameLabel = new JLabel(util.Util.SUB_NAME);
		nameText = new JTextField(8);
		
		sureButton = new JButton(util.Util.MES_SURE);
		p1.add(nameLabel);
		p1.add(nameText);
		
		p1.add(sureButton);
	}

	// 设置第二个panel
	public void table() {
		p2 = new JPanel();
		p2.setPreferredSize(new Dimension(0, 440));
		tableModel = new SubjectTableModel(subjects);
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
	public void selectSubject() {
		sureButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// 定义三个中间变量用于接收文本框输入的信息
				String name = nameText.getText();

				
			//	searchList.clear();// 由于下面的添加方法是在后面添加,所以调用clear方法,防止重复添加
				String sql=null;
				String where = "where 1=1 ";

				if (!name.equals("")) {
					where += "and name ='" + name + "'";
				}
				
				sql = "select * from Subject " + where;
				System.out.println(sql);
				searchList = sd.search(sql);
				tableModel.setDate(searchList);
				tableModel.fireTableDataChanged();
			}
		});
	}

	// deleteButton按钮事件监听
	public void deleteSubject() {
		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() != -1) {
					int index = JOptionPane.showConfirmDialog(null, util.Util.MES_DELETE_Y_N);
					if (index == 0) {
						int a = table.getSelectedRow();
						System.out.println(a);
						Subject temp = searchList.get(a);
						System.out.println(temp.getId());
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
					JOptionPane.showMessageDialog(null, "请选择你要"+util.Util.MES_DELETE+"的数据", util.Util.MES_REMIND,
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
	}

	// alterButton按钮事件监听
	/**
	 * 新建一个窗口,这个窗口只能有一个并且这个窗口内的数据是在表中选择的数据如果没有选择则这个窗口则不能显示并提示警告框
	 * 修改这个窗口中的的信息,则集合中的信息也会被更改 利用回调 将窗口进行保存
	 */
	public void alterSubject() {
		alterButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int index = table.getSelectedRow();
				System.out.println(index);
				if (index != -1) {
					AlterSubject as = AlterSubject.getInstance(subjects,
							new CallBack() {

								@Override
								public void call() {
									refreshTable();
								}
							});
					as.createFrame();
					as.setSubject(searchList.get(index));
				} else {
					JOptionPane.showMessageDialog(null, "请选择你要修改的数据", "提示",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		table.revalidate();
	}

	// addButton按钮事件监听
	/**
	 * 用来新建一个窗口, 这个窗口只能建立一个,并且这个窗口的值需要添加到集合中并保存
	 */
	public void addSubject() {
		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AddSubjectView asv = AddSubjectView.getInstance(new CallBack() {

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


	// 用来实现回调的接收
	public void refreshTable() {
		
		System.out.println();
		searchList = sd.searchAll();
		tableModel.setDate(searchList);
		tableModel.fireTableDataChanged();
	}
}