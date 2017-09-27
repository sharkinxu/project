package view.student;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.Student;

public class StudentTableModel extends AbstractTableModel {

	/**
	 * ���ڽ�����ˢ�±���ģ��
	 */
	List<Student> stus = new ArrayList<Student>();
	String[] columnNames = { util.Util.STU_ID, util.Util.STU_NAME,
			util.Util.STU_SEX, util.Util.STU_AGE, util.Util.STU_BJ };

	StudentTableModel(List<Student> stus) {
		this.stus = stus;

	}

	@Override
	// ��д�ķ���,��������һ���м���
	public int getRowCount() {
		return stus.size();
	}

	@Override
	// ��д�Ĵ�����������һ���м���
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	// �ղ��Ѿ��������м��м���,���ʱ������ֻ��Ҫ��ֵ�������к��е�˳����ӽ�ȥ������
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return stus.get(rowIndex).getId();
		} else if (columnIndex == 1) {
			return stus.get(rowIndex).getName();
		} else if (columnIndex == 2) {
			return stus.get(rowIndex).getSex();
		} else if (columnIndex == 3) {
			return stus.get(rowIndex).getAge();
		} else if (columnIndex == 4) {
			return stus.get(rowIndex).getBj().getName();
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
	public void setDate(List<Student> stus) {
		this.stus = stus;
	}

}
