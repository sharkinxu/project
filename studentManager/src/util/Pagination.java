package util;

public class Pagination {
	// 传入就不用传出去的值
	// 在其他地方没有使用
	private int count;// 数据库的行数(数据总数)
	private int yeMa;// 页码数,页码的list有几个
	private int yeNum;// 一页有多少行数据

	// 需要传出的数据
	private int maxPage;// 最大页码树.通过总数换算
	private int ye;// 页数,通过前端获取
	private int begin;
	private int end;
	private int number;

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * 用来实现分页
	 * 
	 * @param ye
	 *            ,页数
	 * @param count
	 *            ,数据库的总数
	 * @param yeNum
	 *            ,一夜多少行数据
	 * @param yeMa
	 *            ,页码数,页码的list有几个
	 */
	public Pagination(int ye, int count, int yeNum, int yeMa) {
		this.ye = ye;
		this.count = count;
		this.yeNum = yeNum;
		this.yeMa = yeMa;

		if (ye < 1) {
			ye = 1;
		}

		this.number = (ye - 1) * yeNum;
		this.maxPage = count / yeNum == 0 ? count / yeNum  : count / yeNum+1;
		if (ye > maxPage) {
			ye = maxPage;
		}

		this.begin = ye - yeMa / 2;
		this.end = ye + yeMa / 2;
		// 设置情况为1,2的时候
		if (begin < 1) {
			begin = 1;
			end = yeMa;
		}
		// 设置情况为最后的时候
		if (end > maxPage) {
			end = maxPage;
			begin = end - yeMa + 1;
		}
		// 控制最大页不足5的情况
		if (maxPage < yeMa) {
			begin = 1;
			end = maxPage;
		}

	}

	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getYe() {
		return ye;
	}

	public void setYe(int ye) {
		this.ye = ye;
	}

	public int getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}

	public int getYeNum() {
		return yeNum;
	}

	public void setYeNum(int yeNum) {
		this.yeNum = yeNum;
	}

}
