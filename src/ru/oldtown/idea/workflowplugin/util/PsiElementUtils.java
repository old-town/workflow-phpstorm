package ru.oldtown.idea.workflowplugin.util;

import com.intellij.psi.PsiElement;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;

/**
 * Created by ������ ���������� on 05.11.2015.
 */
public class PsiElementUtils {
    public static String getMethodParameter(PsiElement parameter) {

        if (!(parameter instanceof StringLiteralExpression)) {
            return null;
        }

        StringLiteralExpression stringLiteralExpression = (StringLiteralExpression) parameter;

        String stringValue = stringLiteralExpression.getText();
        String value = stringValue.substring(stringLiteralExpression.getValueRange().getStartOffset(), stringLiteralExpression.getValueRange().getEndOffset());

        return removeIdeaRuleHack(value);
    }

    public static String removeIdeaRuleHack(String value) {
        // wtf: ???
        // looks like current cursor position is marked :)
        return value.replace("IntellijIdeaRulezzz", "").replace("IntellijIdeaRulezzz ", "").trim();
    }


    public static String trimQuote(String text) {
        return text.replaceAll("^\"|\"$|\'|\'$", "");
    }
}