package util;

public class Pagination {
	// ����Ͳ��ô���ȥ��ֵ
	// �������ط�û��ʹ��
	private int count;// ���ݿ������(��������)
	private int yeMa;// ҳ����,ҳ���list�м���
	private int yeNum;// һҳ�ж���������

	// ��Ҫ����������
	private int maxPage;// ���ҳ����.ͨ����������
	private int ye;// ҳ��,ͨ��ǰ�˻�ȡ
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
	 * ����ʵ�ַ�ҳ
	 * 
	 * @param ye
	 *            ,ҳ��
	 * @param count
	 *            ,���ݿ������
	 * @param yeNum
	 *            ,һҹ����������
	 * @param yeMa
	 *            ,ҳ����,ҳ���list�м���
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
		// �������Ϊ1,2��ʱ��
		if (begin < 1) {
			begin = 1;
			end = yeMa;
		}
		// �������Ϊ����ʱ��
		if (end > maxPage) {
			end = maxPage;
			begin = end - yeMa + 1;
		}
		// �������ҳ����5�����
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
