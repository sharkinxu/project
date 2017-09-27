package view.banji;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.Subject;

public class SubjectManagerTableModel extends AbstractTableModel {

	/**
	 * 用于建立和刷新表格的模型
	 */
	List<Subject> subjects = new ArrayList<Subject>();
	String[] columnNames = { util.Util.SUB_NAME};

	SubjectManagerTableModel(List<Subject> subjects) {
		this.subjects = subjects;

	}

	@Override
	public int getRowCount() {
		return subjects.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return subjects.get(rowIndex).getName();
		} else {
			return null;
		}
	}

	@Override
	public String getColumnName(int column) {

		return columnNames[column];
	}

	public void setDate(List<Subject> Subjects) {
		this.subjects = Subjects;
	}

}
