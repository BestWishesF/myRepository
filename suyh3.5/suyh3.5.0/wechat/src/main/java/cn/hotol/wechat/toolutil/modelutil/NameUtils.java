package cn.hotol.wechat.toolutil.modelutil;

import org.apache.commons.lang.StringUtils;

/**
 * login: Hill Pan
 * Date: 2/10/12
 * Time: 1:53 PM
 */
public class NameUtils {

    private static final String UNDERSCORE_REGEX = "_";

    public static String camel2Underscore(String name) {

        if (name == null) return null;

        if (name.length() == 0) return name;

        String[] words = StringUtils.splitByCharacterTypeCamelCase(name);
        StringBuilder stringBuilder = new StringBuilder();
        for (String word : words) {
            stringBuilder.append("_").append(StringUtils.uncapitalize(word));
        }
        stringBuilder.replace(1,1,"`");
        stringBuilder.append("`");
        return stringBuilder.substring(1);
    }


    public static String underscore2Camel(String name, boolean capitalize) {

        if (name == null) return null;

        String[] words = name.split(UNDERSCORE_REGEX);

        return camelCaseConcat(words, capitalize);
    }


    public static String camelCaseConcat(String[] words, boolean capitalize) {

        if (words == null || words.length == 0) return null;

        String firstWord = capitalize ? StringUtils.capitalize(words[0]) : StringUtils.uncapitalize(words[0]);

        if (words.length == 1) {
            return firstWord;
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(firstWord);
        for(int i = 1; i < words.length; i ++) {
            stringBuilder.append(StringUtils.capitalize(words[i]));
        }
        return stringBuilder.toString();
    }
}
