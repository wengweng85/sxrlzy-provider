package com.insigma.mvc.serviceimp.resources.SXJY_RLZYSC_001_005;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.annotations.common.util.StringHelper;

public class PrimaryGenerater  {

	private static PrimaryGenerater primaryGenerater = null;

    private PrimaryGenerater() {
    }

    /**
     * ȡ��PrimaryGenerater�ĵ���ʵ��
     *
     * @return
     */
    public static PrimaryGenerater getInstance() {
        if (primaryGenerater == null) {
            synchronized (PrimaryGenerater.class) {
                if (primaryGenerater == null) {
                    primaryGenerater = new PrimaryGenerater();
                }
            }
        }
        return primaryGenerater;
    }

    /**
     *  ������һ�����
     * @param sno    �����ı�ţ����ܷ����ۼӺ��!
     * @param prefix ���ɵı��ǰ׺
     * @return
     */
    public synchronized String generaterNextNumber(String sno,String prefix) {
        String id = null;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        if (StringHelper.isEmpty(sno)) {
            sno = prefix+"20170000";// ��һ�γ�ʼ�����
        }
        DecimalFormat df = new DecimalFormat("0000");
        id = prefix+(formatter.format(date)).substring(2,4)+ df.format(1 + Integer.parseInt(sno.substring(10, sno.length())));

        return id;
    }

    public static void main(String[] args) {
    	
        System.out.println(getInstance().generaterNextNumber("610027170001","610527"));
    }
}
