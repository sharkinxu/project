package view;


import javax.swing.JOptionPane;

import util.Util;

public class ShowMessage {
public static void Show(boolean flag,String message) {
	
		if(flag){
			JOptionPane.showMessageDialog(null,message+Util.MES_SUCCESS);
		}else{
			JOptionPane.showMessageDialog(null,message+Util.MES_FAIL);
		}
	}
}
