package com.xpp.moblie.util;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinSort {
	 

    /**
     * ����ƴ��ת������
     *
     * @param chinese
     * @return
     */
	//ƴ������ĸ��ȫƴ
	public static String getPinYin(String chinese) {
		String pinyinString = "";
		pinyinString=converterToPinYin(chinese)+getStringPinYin(chinese);
		return pinyinString.toLowerCase();
	}
    public static String converterToPinYin(String chinese)
    {
        String pinyinString = "";
        char[] charArray = chinese.toCharArray();
        // ������Ҫ���������ʽ������Ĭ�ϵļ���
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        
        try {
            // �������飬ASC�����128����ת��
            for (int i = 0; i < charArray.length; i++) {
                if (charArray[i] > 128) {
                    // charAt(0)ȡ������ĸ
                    if (charArray[i] >= 0x4e00 && charArray[i] <= 0x9fa5) {    //�ж��Ƿ�����
                    	String[] singlesString=PinyinHelper.toHanyuPinyinStringArray(charArray[i], defaultFormat);
                    	if(singlesString!=null) {
                    		pinyinString +=singlesString[0].charAt(0);
						}
                    	else {
                    		pinyinString +=charArray[i];
						}
//                        pinyinString += PinyinHelper.toHanyuPinyinStringArray(
//                                charArray[i], defaultFormat)[0].charAt(0);
                       
                    }
                    else {                          //�������ĵĴ���δ֪�������޷��������ձ��ȵ���������
                        pinyinString += charArray[i];
                    }
                }
                else {
                    pinyinString += charArray[i];
                }
            }
            return pinyinString;
        }
        catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * �ѵ���Ӣ����ĸ�����ַ���ת��������ASCII��
     *
     * @param input
     * @return
     */
    public static int character2ASCII(String input)
    {
        char[] temp = input.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (char each : temp) {
            builder.append((int) each);
        }
        String result = builder.toString();
        return Integer.parseInt(result);
    }

    /**
     * ������򹤾�
     */
    public class MixComparator implements Comparator<String>
    {
        public int compare(String o1, String o2)
        {
            // �ж��Ƿ�Ϊ��""
            if (isEmpty(o1) && isEmpty(o2))
                return 0;
            if (isEmpty(o1))
                return -1;
            if (isEmpty(o2))
                return 1;
            String str1 = "";
            String str2 = "";
            try {
                str1 = (o1.toUpperCase()).substring(0, 1);
                str2 = (o2.toUpperCase()).substring(0, 1);
            }
            catch (Exception e) {
                System.out.println("ĳ��strΪ\" \" ��");
            }
            if (isWord(str1) && isWord(str2)) {               //��ĸ
                return str1.compareTo(str2);
            }
            else if (isNumeric(str1) && isWord(str2)) {     //������ĸ
                return 1;
            }
            else if (isNumeric(str2) && isWord(str1)) {
                return -1;
            }
            else if (isNumeric(str1) && isNumeric(str2)) {       //��������
                if (Integer.parseInt(str1) > Integer.parseInt(str2)) {
                    return 1;
                }
                else {
                    return -1;
                }
            }
            else if (isAllWord(str1) && (!isAllWord(str2))) {      //������ĸ  �����ַ�
                return -1;
            }
            else if ((!isAllWord(str1)) && isWord(str2)) {
                return 1;
            }
            else {
                return 1;
            }
        }
    }

    /**
     * �жϿ�
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str)
    {
        return "".equals(str.trim());
    }

    /**
     * �ж�����
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str)
    {
        Pattern pattern = Pattern.compile("^[0-9]*$");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * �ж���ĸ
     *
     * @param str
     * @return
     */
    public static boolean isWord(String str)
    {
        Pattern pattern = Pattern.compile("^[A-Za-z]+$");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * �ж���ĸ���ֻ��
     *
     * @param str
     * @return
     */
    public static boolean isAllWord(String str)
    {
        Pattern pattern = Pattern.compile("^[A-Za-z0-9]+$");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        else {
            return true;
        }
    }
    
    //ת�������ַ�

    public static String getCharacterPinYin(char c)

    {
    	 HanyuPinyinOutputFormat format = null;
    	    format = new HanyuPinyinOutputFormat();

            format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

            String[] pinyin=null;

              try

              {

                       pinyin = PinyinHelper.toHanyuPinyinStringArray(c, format);

              }

              catch(BadHanyuPinyinOutputFormatCombination e)

              {

                       e.printStackTrace();

              }

             

              // ���c���Ǻ��֣�toHanyuPinyinStringArray�᷵��null

              if(pinyin == null) return null;

             

              // ֻȡһ������������Ƕ����֣���ȡ��һ������

              return pinyin[0];   

    }

   

    //ת��һ���ַ���

    public static String getStringPinYin(String str)

    {

              StringBuilder sb = new StringBuilder();

              String tempPinyin = null;

              for(int i = 0; i < str.length(); ++i)

              {

                       tempPinyin =getCharacterPinYin(str.charAt(i));

                       if(tempPinyin == null)

                       {

                                // ���str.charAt(i)�Ǻ��֣��򱣳�ԭ��

                                sb.append(str.charAt(i));

                       }

                       else

                       {

                                sb.append(tempPinyin);

                       }

              }

              return sb.toString();

    }


	
}
