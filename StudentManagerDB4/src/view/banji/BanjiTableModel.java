package view.banji;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.Banji;

public class BanjiTableModel extends AbstractTableModel {

	/**
	 * 用于建立和刷新表格的模型
	 */
	List<Banji> banjis = new ArrayList<Banji>();
	String[] columnNames = { util.Util.BJ_ID, util.Util.BJ_NAME,
			util.Util.BJ_STUNUMS };
	String name;
	String sex;
	int age;
	int a = 0;

	BanjiTableModel(List<Banji> banjis) {
		this.banjis = banjis;

	}

	@Override
	public int getRowCount() {
		return banjis.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return banjis.get(rowIndex).getId();
		} else if (columnIndex == 1) {
			return banjis.get(rowIndex).getName();
		} else if (columnIndex == 2) {
			return banjis.get(rowIndex).getStuNums();
		} else {
			return null;
		}
	}

	@Override
	public String getColumnName(int column) {

		return columnNames[column];
	}

	public void setDate(List<Banji> banjis) {
		this.banjis = banjis;
	}

}
