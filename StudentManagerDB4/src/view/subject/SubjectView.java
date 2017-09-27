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
 * ����Ŀ��Ϊ���� ������һ��boxLayout�����������εײ�panel��centerp�� ����������panel��p1,p2,p3������centerp
 * ��panel�зֱ������Ӧ�Ŀؼ�
 * 
 * @author lenovo
 * 
 */
public class SubjectView {
	// �����Ա����
	JFrame f ;
	JPanel centerp, p1, p2, p3;// �����м�����
	JLabel nameLabel;// label�ؼ�
	JTextField nameText;// text�ؼ�
	JTable table = null;// table�ؼ�
	JScrollPane jsp = null;// �������ؼ�
	CallBack callback;// ����callBack���ڻص�
	JButton sureButton, addButton, alterButton, deleteButton;// ����button�ؼ�
	String name;// �����ڿ���̨�洢����
	List<Subject> searchList = new ArrayList<Subject>();// �����м伯�����ڱ����ʾ
//	int stuNum;// �����ڿ���̨�洢����
	SubjectDao sd = new SubjectDao();// ʵ����SubjectDao��,��������ķ������ڴ洢�ͼ���
	List<Subject> subjects;// ����ѧ����������
	SubjectTableModel tableModel;// ����SubjectTableModel����
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
	// ���췽�����ڹ������������¼�
	public void init() {
		// ���뻺��ѧ����Ϣ
		subjects = sd.searchAll();// ���ü��ط������ļ���ļ��ϼ��ؽ�sstu������,Ӧ���������Ҫʵ����һ������,��������newһ��,�����Դ��ļ��л�ȡ
		if (subjects == null) {// �ж��Ƿ�Ϊ��
			subjects = new ArrayList<Subject>();// ���Ϊ����ʵ����һ��stus����,û������ļ������ļ���û�����ݶ������Ķ������쳣
		}
		for (int i = 0; i < subjects.size(); i++) {
			searchList.add(subjects.get(i));// ��stus���ϵ�ʵ��������������ֵ��searchList����,�����Ĳ����������½�����һ������,���������ٿռ䲻ͬ,���Ǽ����еĴ洢��stus���ڴ��ַ��ͬ
		}
		// �����������������
		centerp = new JPanel();
		centerp.setLayout(new BoxLayout(centerp, BoxLayout.Y_AXIS));
		// ���ڶ���������ӿؼ�
		panel1();
		table();
		panel3();
		// �������м������Ϳؼ�����ײ�����
		centerp.add(p1);
		centerp.add(p2);
		centerp.add(p3);

		// �������ڲ����ô�������
	
		f.setSize(util.Util.SUB_MAINVIEW_WIDTH, util.Util.SUB_MAINVIEW_HEIGHT);
		f.setLocationRelativeTo(null);
		f.setTitle(util.Util.SUB_VIEW_NAME);
		
		f.add(centerp);
		// ����¼��ķ���
		deleteSubject();
		alterSubject();
		addSubject();
		selectSubject();
		// ���ô��ڿɼ�
		f.setVisible(true);
	}
	

	// ���õ�һ��panel
	public void panel1() {
		p1 = new JPanel();
		// �ص������������ĸ߶�
		p1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 20));
		p1.setPreferredSize(new Dimension(0, 60));

		nameLabel = new JLabel(util.Util.SUB_NAME);
		nameText = new JTextField(8);
		
		sureButton = new JButton(util.Util.MES_SURE);
		p1.add(nameLabel);
		p1.add(nameText);
		
		p1.add(sureButton);
	}

	// ���õڶ���panel
	public void table() {
		p2 = new JPanel();
		p2.setPreferredSize(new Dimension(0, 440));
		tableModel = new SubjectTableModel(subjects);
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
	public void selectSubject() {
		sureButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// ���������м�������ڽ����ı����������Ϣ
				String name = nameText.getText();

				
			//	searchList.clear();// �����������ӷ������ں������,���Ե���clear����,��ֹ�ظ����
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

	// deleteButton��ť�¼�����
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
					JOptionPane.showMessageDialog(null, "��ѡ����Ҫ"+util.Util.MES_DELETE+"������", util.Util.MES_REMIND,
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
	}

	// alterButton��ť�¼�����
	/**
	 * �½�һ������,�������ֻ����һ��������������ڵ��������ڱ���ѡ����������û��ѡ�����������������ʾ����ʾ�����
	 * �޸���������еĵ���Ϣ,�򼯺��е���ϢҲ�ᱻ���� ���ûص� �����ڽ��б���
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
					JOptionPane.showMessageDialog(null, "��ѡ����Ҫ�޸ĵ�����", "��ʾ",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		table.revalidate();
	}

	// addButton��ť�¼�����
	/**
	 * �����½�һ������, �������ֻ�ܽ���һ��,����������ڵ�ֵ��Ҫ��ӵ������в�����
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


	// ����ʵ�ֻص��Ľ���
	public void refreshTable() {
		
		System.out.println();
		searchList = sd.searchAll();
		tableModel.setDate(searchList);
		tableModel.fireTableDataChanged();
	}
}