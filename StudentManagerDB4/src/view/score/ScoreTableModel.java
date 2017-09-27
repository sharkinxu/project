package view.score;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.Score;

public class ScoreTableModel extends AbstractTableModel {

	/**
	 * 用于建立和刷新表格的模型
	 */
	List<Score> temp = new ArrayList<Score>();
	List<Score> scores = new ArrayList<Score>();
	String[] columnNames = { util.Util.STU_NAME, util.Util.BJ, util.Util.SUB,
			util.Util.SCORE };

	ScoreTableModel(List<Score> stus) {
		this.scores = stus;
	}

	@Override
	public int getRowCount() {
		return scores.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return scores.get(rowIndex).getStu().getName();
		} else if (columnIndex == 1) {
			return scores.get(rowIndex).getStu().getBj().getName();
		} else if (columnIndex == 2) {
			return scores.get(rowIndex).getSub().getName();
		} else if (columnIndex == 3) {
			return scores.get(rowIndex).getScore();
		} else {
			return null;
		}
	}

	@Override
	public String getColumnName(int column) {

		return columnNames[column];
	}

	public void setDate(List<Score> stus) {
		this.scores = stus;
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == 3) {
			return true;
		}
		return false;
	}
//	public Class getColumnClass(int c) {
//        return getValueAt(0, c).getClass();
//    }
//	public Class getRowClass(int c){
//		return getValueAt(c,0).getClass();
//	}

    @Override
    public void setValueAt(Object value, int row, int col) {
    	Score sc = new Score();
    	sc.setId(scores.get(row).getId());
    	sc.setGrade(scores.get(row).getGrade());
    	sc.setStu_id(scores.get(row).getStu_id());
    	sc.setSub_id(scores.get(row).getSub_id());
    	sc.setStu(scores.get(row).getStu());
    	sc.setSub(scores.get(row).getSub());
    	sc.setScore(Integer.parseInt((String) value));
     //  scores.set(row, sc);
       System.out.println("66666666666");
   //     fireTableCellUpdated(row, col);
    }

    public void mySetValueAt(Object value, int row, int col) {
    	Score sc = new Score();
    	sc.setId(scores.get(row).getId());
    	sc.setGrade(scores.get(row).getGrade());
    	sc.setStu_id(scores.get(row).getStu_id());
    	sc.setSub_id(scores.get(row).getSub_id());
    	sc.setStu(scores.get(row).getStu());
    	sc.setSub(scores.get(row).getSub());
    	sc.setScore((int)value);
       scores.set(row, sc);
    }

}
