package view.banji;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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

import util.CallBack;
import view.ShowMessage;
import view.subject.AddSubjectView;
import view.subject.AlterSubject;
import dao.BanJiDao;
import entity.Banji;
import entity.Subject;

public class SubjectManagerView {
	
	JFrame f;
	CallBack callBack;
	JPanel centerp, p1, p2, p3;
	JLabel banjiNameLabel;
	JTable table = null;
	JScrollPane jsp = null;
	CallBack callback;
	JButton addButton, deleteButton;
	JComboBox<String> subbox ; 
	String name;
	List<Subject> searchList = new ArrayList<Subject>();

	BanJiDao bd = new BanJiDao();
	List<Subject> subjects1,subjects2;
	SubjectManagerTableModel tableModel;
	Banji bj;
	private static SubjectManagerView instance;

	private SubjectManagerView(CallBack callBack) {
		this.callBack = callBack;

	}

	public static SubjectManagerView getInstance(CallBack callBack) {

		if (instance == null) {
			instance = new SubjectManagerView(callBack);
		}
		return instance;
	}

	public void createFrame(Banji bj) {
		this.bj = bj;
		if (f == null) {
			f = new JFrame();
			init();
		} else {
			refreshTable();
			f.setVisible(true);
			System.out.println("班级名称：" + bj.getName());
		}
		banjiNameLabel.setText(bj.getName());
	}

	public void init() {
		subjects1 = bd.searchSubjectAll(bj);

		if (searchList == null) {
			searchList = new ArrayList<Subject>();
		}
		for (int i = 0; i < subjects1.size(); i++) {
			searchList.add(subjects1.get(i));}
		centerp = new JPanel();
		centerp.setLayout(new BoxLayout(centerp, BoxLayout.Y_AXIS));
		panel1();
		table();
		panel3();
		centerp.add(p1);
		centerp.add(p2);
		centerp.add(p3);

		
		f.setSize(util.Util.SUB_MAINVIEW_WIDTH, util.Util.SUB_MAINVIEW_HEIGHT);
		f.setLocationRelativeTo(null);
		f.setTitle(util.Util.SUB_VIEW_NAME);

		f.add(centerp);
		deleteSubject();
		addSubject();

		f.setVisible(true);
	}

	public void panel1() {
		p1 = new JPanel();
		p1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 20));
		p1.setPreferredSize(new Dimension(0, 60));

		banjiNameLabel = new JLabel();
		p1.add(banjiNameLabel);

	}

	public void table() {
		p2 = new JPanel();
		p2.setPreferredSize(new Dimension(0, 440));
		tableModel = new SubjectManagerTableModel(searchList);
		table = new JTable(tableModel);
		jsp = new JScrollPane(table);// 滚动条
		p2.add(jsp);
	}

	public void panel3() {
		p3 = new JPanel();
		p3.setPreferredSize(new Dimension(0, 100));
		p3.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 40));
		subbox=new JComboBox();
		subbox.setPreferredSize(new Dimension(120, 25));
		setSubbox();
		addButton = new JButton(util.Util.MES_ADD);
		deleteButton = new JButton(util.Util.MES_DELETE);
		p3.add(subbox);
		p3.add(addButton);
		p3.add(deleteButton);
	}

	public void deleteSubject() {
		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() != -1) {
					int index = JOptionPane.showConfirmDialog(null,
							util.Util.MES_DELETE_Y_N);
					if (index == 0) {
						int a = table.getSelectedRow();
						System.out.println(a);
						int i = bd.daleteSubject(bj,searchList.get(a));
						boolean flag=false;
						if(i>0){
							flag = true;
						}
						setSubbox();
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
	 * 用来新建一个窗口, 这个窗口只能建立一个,并且这个窗口的值需要添加到集合中并保存
	 */
	public void addSubject() {
		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			int index = subbox.getSelectedIndex();
			
				int i =  bd.addSubject(bj,subjects2.get(index));
				boolean flag = false;
				if(i>0){
					flag = true;
				}
				ShowMessage.Show(flag, util.Util.MES_ADD);
			  setSubbox();
				refreshTable();
			}
		});
	}

	public void setSubbox(){
		 subjects2 = bd.searchSubject(bj);
		 subbox.removeAllItems();
			for (int i = 0; i < subjects2.size(); i++) {
				subbox.addItem(subjects2.get(i).getName());
			}
	}
	public void refreshTable() {

		System.out.println();
		searchList = bd.searchSubjectAll(bj);
		tableModel.setDate(searchList);
		tableModel.fireTableDataChanged();
	}
}
