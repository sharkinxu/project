package view.subject;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import util.CallBack;
import util.Util;
import view.ShowMessage;
import dao.SubjectDao;
import entity.Subject;

public class AddSubjectView {
	List<Subject> subjects;
	JFrame addFrame;
	JLabel nameLabel1;
	JTextField nameText1;
	SubjectDao subd = new SubjectDao();
	JButton sureButton1;
	CallBack callBack;
	private static AddSubjectView instance;

	private AddSubjectView(CallBack callBack) {
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

	public void setText() {
		nameText1.setText("");

	}

	public static AddSubjectView getInstance(CallBack callBack) {
		if (instance == null) {

			instance = new AddSubjectView(callBack);

		}
		// 返回这个值,这个值只有一个
		return instance;
	}

	// 用于创建窗口和布局控件
	public void init() {
		JPanel p1 = new JPanel();
		JPanel p5 = new JPanel();
		addFrame = new JFrame(util.Util.MES_ADD);
		JPanel addPanel = new JPanel();
		addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.Y_AXIS));
		p5.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 0));
		addPanel.add(Box.createRigidArea(new Dimension(0, 30)));
		addPanel.add(p1);
		addPanel.add(p5);
		nameLabel1 = new JLabel(util.Util.BJ_NAME);
		nameText1 = new JTextField(8);
		nameText1.setPreferredSize(new Dimension(120, 25));
		sureButton1 = new JButton(util.Util.MES_SURE);
		addFrame.add(addPanel);
		p1.add(nameLabel1);
		p1.add(nameText1);
		p5.add(sureButton1);
		addFrame.setSize(util.Util.BJ_ADDVIEW_WIDTH, util.Util.BJ_ADDVIEW_HEIGHT);
		addFrame.setLocationRelativeTo(null);
		addFrame.setVisible(true);
		addSubject();

	}

	// 添加学生按钮事件监听
	public void addSubject() {
		sureButton1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// 
				String name = nameText1.getText();
				
				
				if (name.equals("")) {
					JOptionPane.showMessageDialog(null, "输入数据不完整", util.Util.MES_REMIND,
							JOptionPane.INFORMATION_MESSAGE);
				} else {

					Subject sub = new Subject();
					sub.setName(name);
					
					boolean flag = false;
					int i = subd.add(sub);
					if (i >0) {
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
