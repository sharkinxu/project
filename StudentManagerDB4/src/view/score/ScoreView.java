package view.score;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import util.CallBack;
import dao.BanJiDao;
import dao.ScoreDao;
import dao.SubjectDao;
import entity.Banji;
import entity.Score;
import entity.Subject;

/**
 * 本项目是为界面 定义了一个boxLayout布局用来修饰底层panel（centerp） 定义了三个panel（p1,p2,p3）放入centerp
 * 将panel中分别加入相应的控件
 * 
 * @author lenovo
 * 
 */
public class ScoreView implements TableModelListener {
	JFrame f;
	JPanel centerp, p1, p2, p3;
	JLabel nameLabel, scoreLabel;
	JTextField nameText, scoreText;
	JTable table = null;
	JScrollPane jsp = null;
	CallBack callback;
	JButton sureButton, alterSureButton;
	JComboBox jbox, subbox;

	ScoreDao sd = new ScoreDao();// 定义学生数据链接
	SubjectDao subd = new SubjectDao();
	List<Score> scores, searchscore;// 建立学生集合
	ScoreTableModel tableModel;//
	BanJiDao bd = new BanJiDao();// 定义班级数据链接
	List<Banji> banjis;// 建立班级集合
	List<Subject> subjects;
	Set<Score> hset = new HashSet<Score>();
	/**
	 * 用于实现单例模式
	 */
	private static ScoreView instance;

	private ScoreView() {
	}

	public static ScoreView getInstance() {
		if (instance == null) {
			instance = new ScoreView();
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

		scores = sd.searchAll();

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
		f.setTitle(util.Util.SCORE_VIEW_NAEN);

		f.add(centerp);

		alterScore();
		selectScore();

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
		jbox = new JComboBox();
		banjis = bd.searchAll();
		jbox.addItem("请输入班级");
		for (Banji bj : banjis) {
			jbox.addItem(bj.getName());
		}
		subbox = new JComboBox();
		subjects = subd.searchAll();
		subbox.addItem("请输入课程");
		for (int i = 0; i < subjects.size(); i++) {
			subbox.addItem(subjects.get(i).getName());
		}
		jbox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				subbox.removeAllItems();
				if (jbox.getSelectedIndex() != 0) {
					subjects = bd.searchSubjectAll(banjis.get(jbox
							.getSelectedIndex() - 1));
				} else {
					subjects = subd.searchAll();
				}
				subbox.addItem("请输入课程");
				for (int i = 0; i < subjects.size(); i++) {
					subbox.addItem(subjects.get(i).getName());
				}
			}
		});
		scoreLabel = new JLabel(util.Util.SCORE);
		scoreText = new JTextField(8);

		System.out.println("jbox.getSelectedIndex()" + jbox.getSelectedIndex());

		sureButton = new JButton(util.Util.MES_SURE);
		p1.add(nameLabel);
		p1.add(nameText);
		p1.add(jbox);
		p1.add(subbox);
		p1.add(scoreLabel);
		p1.add(scoreText);
		p1.add(sureButton);

	}

	// 设置第二个panel
	public void table() {
		p2 = new JPanel();
		p2.setPreferredSize(new Dimension(0, 440));

		tableModel = new ScoreTableModel(scores);
		tableModel.addTableModelListener(this);
		table = new JTable(tableModel);
		table.setPreferredScrollableViewportSize(new Dimension(550, 30));

		jsp = new JScrollPane(table);
		jsp.setPreferredSize(new Dimension(500, 300));
		p2.add(jsp);
	}

	// 设置第三个panel
	public void panel3() {
		p3 = new JPanel();
		p3.setPreferredSize(new Dimension(0, 100));
		p3.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 40));
		alterSureButton = new JButton(util.Util.MES_ALTER);
		p3.add(alterSureButton);
	}

	// sureButton按钮事件监听
	public void selectScore() {
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
				refreshTable();
				String where = "where 1=1 ";
				String name = nameText.getText();
				int indexbj = jbox.getSelectedIndex();
				int indexsub = subbox.getSelectedIndex();
				int score = 0;

				if (!scoreText.getText().equals("")) {
					score = Integer.parseInt(scoreText.getText());
					where += "and sc.score ='" + score + "'";
				}
				if (!name.equals("")) {
					where += "and stu.name ='" + name + "'";
				}

				if (indexbj != 0) {
					where += "and bj.name = '"
							+ banjis.get(indexbj - 1).getName() + "'";
				}
				if (indexsub != 0) {

					where += "and sub.name = '"
							+ subjects.get(indexsub - 1).getName() + "'";
					// System.out.println("课程："+subjects.get(indexsub-1).getName());
				}
				if (score != 0) {
					where += "and sc.score = " + score;
				}
				String sql = "select stu.*,bj.*,sub.* ,sc.* "
						+ "from student as stu " + "left JOIN banji as bj "
						+ "on  bj.id = stu.bj_id " + "inner JOIN m_bj_sub "
						+ "on m_bj_sub.bj_id = bj.id "
						+ "inner JOIN `subject` as sub "
						+ "on sub.id = m_bj_sub.sub_id "
						+ "left JOIN score as sc " + "on sc.sub_id =sub.id "
						+ "and stu.id = sc.stu_id " + where;
				scores = sd.search(sql);
				refreshTable(scores);
			}

		});
	}

	// 为alterButton添加事件监听
	public void alterScore() {
		alterSureButton.addActionListener(new ActionListener() {
			/**
			 * (1)对获得表的行数进行判断:如果没有选中(getSelectIndex值为-1)弹出对话框
			 * (2)因为表的索引和集合的索引都是由0开始,所以表中在索引位置的Score就是集合中在索引位置上的Score
			 * (3)创建一个修改窗口类的实例化as,用于显示新的窗口完成修改
			 * (4)为as的方法传入一个Score,就是利用表的索引获取Score集合里的学生信息
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		table.revalidate();
	}

	// 对addButton按钮的事件监听

	public void refreshTable() {

		System.out.println();
		scores = sd.searchAll();
		tableModel.setDate(scores);
		tableModel.fireTableDataChanged();
		tableModel.fireTableStructureChanged();
	}

	public void refreshTable(List<Score> scores) {

		tableModel.setDate(scores);
		tableModel.fireTableDataChanged();
		tableModel.fireTableStructureChanged();
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		
		int row = e.getFirstRow();
		
		System.out.println("row"+row);
		Score temp = new Score();
		temp =scores.get(row);
		int score = ((Integer) tableModel.getValueAt(row, 3)).intValue();
		if(temp.getId()==0){
			System.out.println(temp);
			
			sd.motifyone(temp, score);
		}else{
			sd.motify(temp, score);
		}
		tableModel.mySetValueAt(score, row, 3);
		
		
	}

}