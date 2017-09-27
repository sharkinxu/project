package view.subject;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.Subject;

public class SubjectTableModel extends AbstractTableModel {

	/**
	 * ���ڽ�����ˢ�±���ģ��
	 */
	List<Subject> subjects = new ArrayList<Subject>();// �½�list������װSubject����
	String[] columnNames = { util.Util.SUB_ID, util.Util.SUB_NAME};// �����е�����,�����Ҫ���й��еĶ���������������,�����Կ����������еĳ���.
	String name;
	String sex;
	int age;
	int a = 0;

	// ���췽��,�������ܴ������,��ʵ�ֳ�ʼ��(��������������÷�Χ������ƪ�ĵ�)
	SubjectTableModel(List<Subject> subjects) {
		this.subjects = subjects;

	}

	@Override
	// ��д�ķ���,��������һ���м���
	public int getRowCount() {
		// TODO Auto-generated method stub
		return subjects.size();
	}

	@Override
	// ��д�Ĵ�����������һ���м���
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnNames.length;
	}

	@Override
	// �ղ��Ѿ��������м��м���,���ʱ������ֻ��Ҫ��ֵ�������к��е�˳����ӽ�ȥ������
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return subjects.get(rowIndex).getId();
		} else if (columnIndex == 1) {
			return subjects.get(rowIndex).getName();
		} else {
			return null;
		}
	}

	@Override
	// ��д�ķ���,���������е�����,�������д,����������A,B,C�����
	public String getColumnName(int column) {

		return columnNames[column];
	}

	// ��������������Ϣ,��Ϊ���� �Ĺ�������Ѿ���һ��list���͵����������Ҫ�����¶����list������,��ֻ���ٶ���һ������.
	public void setDate(List<Subject> Subjects) {
		this.subjects = Subjects;
	}

}
