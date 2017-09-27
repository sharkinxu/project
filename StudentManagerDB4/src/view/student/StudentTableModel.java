package view.student;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.Student;

public class StudentTableModel extends AbstractTableModel {

	/**
	 * 用于建立和刷新表格的模型
	 */
	List<Student> stus = new ArrayList<Student>();
	String[] columnNames = { util.Util.STU_ID, util.Util.STU_NAME,
			util.Util.STU_SEX, util.Util.STU_AGE, util.Util.STU_BJ };

	StudentTableModel(List<Student> stus) {
		this.stus = stus;

	}

	@Override
	// 重写的方法,又来控制一共有几行
	public int getRowCount() {
		return stus.size();
	}

	@Override
	// 重写的代码用来控制一共有几列
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	// 刚才已经控制了有几行几列,这个时候我们只需要把值按照先行后列的顺序添加进去就行了
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
	// 重写的方法,用来控制列的名字,如果不重写,列名将会是A,B,C等填充
	public String getColumnName(int column) {

		return columnNames[column];
	}

	// 用来更新数据信息,因为我们 的构造参数已经是一个list类型的所以如果我要将重新定义的list传进来,就只能再定义一个方法.
	public void setDate(List<Student> stus) {
		this.stus = stus;
	}

}
