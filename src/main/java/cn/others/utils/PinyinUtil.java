package cn.others.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 获取汉字拼音工具类
 *
 * @author hongzhou.wei
 * @date 2020/6/28
 */
public class PinyinUtil {
    public static void main(String[] args) {
        String name = "泓舟子子";
        // 获取拼音首字母
        Set<String> out = getHeadPinyin(name);
        System.out.println(out.toString());
        // 获取完整拼音，但没有每个字分割开
        Set<String> pinyin = getPinyin(name);
        System.out.println(pinyin.toString());
        // 获取完整拼音或首字母，每个字分隔开
        String[][] pingyinMetadata = getPingyinMetadata(name, false);
        String[][] pingyinMetadata1 = getPingyinMetadata(name, true);
        System.out.println(Arrays.deepToString(pingyinMetadata));
        System.out.println(Arrays.deepToString(pingyinMetadata1));
    }

    /**
     * 获取汉字的首字母拼音（大写）
     *
     * @param src 输入的汉字
     * @return Set<String>
     */
    public static Set<String> getHeadPinyin(String src) {
        if (src != null && !src.trim().equalsIgnoreCase("")) {
            String[] pingyinArray = doExchange(getPingyinMetadata(src, true));
            Set<String> pinyinSet = new HashSet<String>();
            for (int i = 0; i < pingyinArray.length; i++) {
                pinyinSet.add(pingyinArray[i].toUpperCase());
            }
            return pinyinSet;
        }
        return null;
    }

    /**
     * 获取拼音集合
     *
     * @param src 输入的汉字
     * @return Set<String>
     */
    public static Set<String> getPinyin(String src) {
        if (src != null && !src.trim().equalsIgnoreCase("")) {
            String[] pingyinArray = doExchange(getPingyinMetadata(src, false));
            Set<String> pinyinSet = new HashSet<String>();
            for (int i = 0; i < pingyinArray.length; i++) {
                pinyinSet.add(pingyinArray[i]);
            }
            return pinyinSet;
        }
        return null;
    }

    /**
     * 获取拼音集合，每个汉字的拼音位于数组内
     *
     * @param src     输入的汉字
     * @param subHead 是否只返回拼音首字母
     * @return
     */
    private static String[][] getPingyinMetadata(String src, boolean subHead) {
        String[][] temp = null;
        if (src != null && !src.trim().equalsIgnoreCase("")) {
            char[] srcChar;
            srcChar = src.toCharArray();
            // 汉语拼音格式输出类
            HanyuPinyinOutputFormat hanYuPinOutputFormat = new HanyuPinyinOutputFormat();

            // 输出设置，大小写，音标方式等
            hanYuPinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
            hanYuPinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            hanYuPinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);

            temp = new String[src.length()][];
            for (int i = 0; i < srcChar.length; i++) {
                char c = srcChar[i];
                // 是中文或者a-z或者A-Z转换拼音(我的需求，是保留中文或者a-z或者A-Z)
                if (String.valueOf(c).matches("[\\u4E00-\\u9FA5]+")) {
                    try {
                        String[] tem = PinyinHelper.toHanyuPinyinStringArray(srcChar[i], hanYuPinOutputFormat);
                        if (tem != null) {
                            if (subHead) {
                                for (int x = 0; x < tem.length; x++) {
                                    tem[x] = tem[x].substring(0, 1);
                                }
                            }
                            temp[i] = tem;
                        } else {
                            temp[i] = new String[]{""};
                        }
                    } catch (BadHanyuPinyinOutputFormatCombination e) {
                        // log.error(e.getMessage(),e);
                    }
                } else if (((int) c >= 65 && (int) c <= 90) || ((int) c >= 97 && (int) c <= 122)) {
                    temp[i] = new String[]{String.valueOf(srcChar[i])};
                } else {
                    temp[i] = new String[]{""};
                }
            }
        }
        return temp;
    }

    /**
     * 递归
     *
     * @param strJaggedArray
     * @return
     */
    private static String[] doExchange(String[][] strJaggedArray) {
        int len = strJaggedArray.length;
        if (len >= 2) {
            int len1 = strJaggedArray[0].length;
            int len2 = strJaggedArray[1].length;
            int newlen = len1 * len2;
            String[] temp = new String[newlen];
            int index = 0;
            for (int i = 0; i < len1; i++) {
                for (int j = 0; j < len2; j++) {
                    temp[index] = strJaggedArray[0][i] + strJaggedArray[1][j];
                    index++;
                }
            }
            String[][] newArray = new String[len - 1][];
            for (int i = 2; i < len; i++) {
                newArray[i - 1] = strJaggedArray[i];
            }
            newArray[0] = temp;
            return doExchange(newArray);
        } else {
            return strJaggedArray[0];
        }
    }
}