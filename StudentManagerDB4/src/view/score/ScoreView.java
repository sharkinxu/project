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
 * ����Ŀ��Ϊ���� ������һ��boxLayout�����������εײ�panel��centerp�� ����������panel��p1,p2,p3������centerp
 * ��panel�зֱ������Ӧ�Ŀؼ�
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

	ScoreDao sd = new ScoreDao();// ����ѧ����������
	SubjectDao subd = new SubjectDao();
	List<Score> scores, searchscore;// ����ѧ������
	ScoreTableModel tableModel;//
	BanJiDao bd = new BanJiDao();// ����༶��������
	List<Banji> banjis;// �����༶����
	List<Subject> subjects;
	Set<Score> hset = new HashSet<Score>();
	/**
	 * ����ʵ�ֵ���ģʽ
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

	// ��������
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

	// ���õ�һ��panel
	public void panel1() {
		p1 = new JPanel();
		// �ص������������ĸ߶�
		p1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 20));
		p1.setPreferredSize(new Dimension(0, 60));

		nameLabel = new JLabel(util.Util.STU_NAME);
		nameText = new JTextField(8);
		jbox = new JComboBox();
		banjis = bd.searchAll();
		jbox.addItem("������༶");
		for (Banji bj : banjis) {
			jbox.addItem(bj.getName());
		}
		subbox = new JComboBox();
		subjects = subd.searchAll();
		subbox.addItem("������γ�");
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
				subbox.addItem("������γ�");
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

	// ���õڶ���panel
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

	// ���õ�����panel
	public void panel3() {
		p3 = new JPanel();
		p3.setPreferredSize(new Dimension(0, 100));
		p3.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 40));
		alterSureButton = new JButton(util.Util.MES_ALTER);
		p3.add(alterSureButton);
	}

	// sureButton��ť�¼�����
	public void selectScore() {
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
					// System.out.println("�γ̣�"+subjects.get(indexsub-1).getName());
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

	// ΪalterButton����¼�����
	public void alterScore() {
		alterSureButton.addActionListener(new ActionListener() {
			/**
			 * (1)�Ի�ñ�����������ж�:���û��ѡ��(getSelectIndexֵΪ-1)�����Ի���
			 * (2)��Ϊ��������ͼ��ϵ�����������0��ʼ,���Ա���������λ�õ�Score���Ǽ�����������λ���ϵ�Score
			 * (3)����һ���޸Ĵ������ʵ����as,������ʾ�µĴ�������޸�
			 * (4)Ϊas�ķ�������һ��Score,�������ñ��������ȡScore�������ѧ����Ϣ
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		table.revalidate();
	}

	// ��addButton��ť���¼�����

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