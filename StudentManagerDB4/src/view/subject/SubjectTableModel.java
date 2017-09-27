package view.subject;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.Subject;

public class SubjectTableModel extends AbstractTableModel {

	/**
	 * 用于建立和刷新表格的模型
	 */
	List<Subject> subjects = new ArrayList<Subject>();// 新建list用来承装Subject集合
	String[] columnNames = { util.Util.SUB_ID, util.Util.SUB_NAME};// 定义列的名字,如果想要改有关列的东西都可以在这里,他可以控制列名和列的长度.
	String name;
	String sex;
	int age;
	int a = 0;

	// 构造方法,用来接受传入参数,并实现初始化(将传入参数的运用范围扩大到整篇文档)
	SubjectTableModel(List<Subject> subjects) {
		this.subjects = subjects;

	}

	@Override
	// 重写的方法,又来控制一共有几行
	public int getRowCount() {
		// TODO Auto-generated method stub
		return subjects.size();
	}

	@Override
	// 重写的代码用来控制一共有几列
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnNames.length;
	}

	@Override
	// 刚才已经控制了有几行几列,这个时候我们只需要把值按照先行后列的顺序添加进去就行了
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
	// 重写的方法,用来控制列的名字,如果不重写,列名将会是A,B,C等填充
	public String getColumnName(int column) {

		return columnNames[column];
	}

	// 用来更新数据信息,因为我们 的构造参数已经是一个list类型的所以如果我要将重新定义的list传进来,就只能再定义一个方法.
	public void setDate(List<Subject> Subjects) {
		this.subjects = Subjects;
	}

}
