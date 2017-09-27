package view.banji;

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
import dao.BanJiDao;
import entity.Banji;

/**
 * 本项目是为界面 定义了一个boxLayout布局用来修饰底层panel（centerp） 定义了三个panel（p1,p2,p3）放入centerp
 * 将panel中分别加入相应的控件
 * 
 * @author lenovo
 * 
 */
public class BanjiView {
	JFrame f;
	JPanel centerp, p1, p2, p3;
	JLabel nameLabel, stuNumbLable;
	JTextField nameText, stuNumbText;
	JTable table = null;
	JScrollPane jsp = null;
	CallBack callback;
	JButton sureButton, addButton, alterButton, deleteButton, subjectButton;
	List<Banji> searchList = new ArrayList<Banji>();
	BanJiDao sd = new BanJiDao();
	List<Banji> banjis;
	BanjiTableModel tableModel;
	private static BanjiView instance;

	private BanjiView() {

	}

	public static BanjiView getInstance() {

		if (instance == null) {
			instance = new BanjiView();
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

	public void init() {
		banjis = sd.searchAll();
		if (banjis == null) {
			banjis = new ArrayList<Banji>();
		}
		for (int i = 0; i < banjis.size(); i++) {
			searchList.add(banjis.get(i));
		}
		centerp = new JPanel();
		centerp.setLayout(new BoxLayout(centerp, BoxLayout.Y_AXIS));
		panel1();
		table();
		panel3();
		centerp.add(p1);
		centerp.add(p2);
		centerp.add(p3);


		f.setSize(util.Util.BJ_MAINVIEW_WIDTH, util.Util.BJ_MAINVIEW_HEIGHT);
		f.setLocationRelativeTo(null);
		f.setTitle(util.Util.STU_VIEW_NAME);

		f.add(centerp);
		deleteBanji();
		alterBanji();
		addBanji();
		selectBanji();
		subjectManager();
		f.setVisible(true);
	}

	// 设置第一个panel
	public void panel1() {
		p1 = new JPanel();
		// 重点在于设置他的高度
		p1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 20));
		p1.setPreferredSize(new Dimension(0, 60));

		nameLabel = new JLabel(util.Util.BJ_NAME);
		nameText = new JTextField(8);
		stuNumbLable = new JLabel(util.Util.BJ_STUNUMS);
		stuNumbText = new JTextField(8);
		sureButton = new JButton(util.Util.MES_SURE);
		p1.add(nameLabel);
		p1.add(nameText);
		p1.add(stuNumbLable);
		p1.add(stuNumbText);
		p1.add(sureButton);
	}

	// 设置第二个panel
	public void table() {
		p2 = new JPanel();
		p2.setPreferredSize(new Dimension(0, 440));
		tableModel = new BanjiTableModel(banjis);
		table = new JTable(tableModel);
		jsp = new JScrollPane(table);
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
		subjectButton = new JButton("管理课程");
		p3.add(addButton);
		p3.add(alterButton);
		p3.add(deleteButton);
		p3.add(subjectButton);
	}

	// sureButton按钮事件监听
	public void selectBanji() {
		sureButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = nameText.getText();

				int stuNums;
				if (stuNumbText.getText().equals("")) {
					stuNums = 0;
				} else {
					stuNums = Integer.valueOf(stuNumbText.getText());
				}
				String sql = null;
				String where = "where 1=1 ";

				if (!name.equals("")) {
					where += "and name ='" + name + "'";
				}
				if (stuNums != 0) {
					where += "and stuNums =" + stuNums;
				}
				sql = "select * from Banji " + where;
				System.out.println(sql);
				searchList = sd.search(sql);
				tableModel.setDate(searchList);
				tableModel.fireTableDataChanged();
			}
		});
	}

	
	public void deleteBanji() {
		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() != -1) {
					int index = JOptionPane.showConfirmDialog(null,
							util.Util.MES_DELETE_Y_N);
					if (index == 0) {
						int a = table.getSelectedRow();
						System.out.println(a);
						Banji temp = searchList.get(a);
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
					JOptionPane.showMessageDialog(null, "请选择你要"
							+ util.Util.MES_DELETE + "的数据",
							util.Util.MES_REMIND,
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
	}

	/**
	 * 新建一个窗口,这个窗口只能有一个并且这个窗口内的数据是在表中选择的数据如果没有选择则这个窗口则不能显示并提示警告框
	 * 修改这个窗口中的的信息,则集合中的信息也会被更改 利用回调 将窗口进行保存
	 */
	public void alterBanji() {
		alterButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int index = table.getSelectedRow();// 获取Table的选择的行的索引值,存放在index中,因为表和集合的索引值都是从0开始逐个加一,所以这个索引就相当于集合的索引
				System.out.println(index);
				if (index != -1) {
					AlterBanji as = AlterBanji.getInstance(banjis,
							new CallBack() {

								@Override
								public void call() {
									refreshTable();
								}
							});
					as.createFrame();
					as.setBanji(searchList.get(index));
				} else {
					JOptionPane.showMessageDialog(null, "请选择你要修改的数据", "提示",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		table.revalidate();
	}

	
	/**
	 * 用来新建一个窗口, 这个窗口只能建立一个,并且这个窗口的值需要添加到集合中并保存
	 */
	public void addBanji() {
		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AddBanjiView abv = AddBanjiView
						.getInstance(banjis, new CallBack() {

							@Override
							public void call() {
								// TODO Auto-generated method stub
								refreshTable();
							}
						});
				abv.createFrame();
			}
		});
	}

	public void subjectManager() {
		subjectButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int index = table.getSelectedRow();
				if (index != -1) {
					SubjectManagerView smv = SubjectManagerView
							.getInstance(new CallBack() {

								@Override
								public void call() {
									// TODO Auto-generated method stub
									refreshTable();
								}
							});
					Banji bj = banjis.get(index);
					smv.createFrame(bj);
				} else {
					JOptionPane.showMessageDialog(null, "请选择你要修改的数据", "提示",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

	}

	// showButton按钮事件监听,用来现实所有数据

	// 用来实现回调的接收
	public void refreshTable() {

		System.out.println();
		searchList = sd.searchAll();
		tableModel.setDate(searchList);
		tableModel.fireTableDataChanged();
	}
}