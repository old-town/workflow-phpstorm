package ru.oldtown.idea.workflowplugin.config.workflow.xml;

import com.intellij.patterns.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.xml.XmlTag;
import com.intellij.psi.xml.XmlTokenType;


/**
 *
 */
public class XmlHelper {

    public final static String functionTag = "function";
    public final static String conditionTag = "condition";
    public final static String validatorTag = "validator";
    public final static String registerTag = "register";

    public static PsiFilePattern.Capture<PsiFile> getXmlFilePattern() {
        return XmlPatterns.psiFile()
                .withName(XmlPatterns
                        .string().endsWith(".xml")
                );
    }

    public static ElementPattern<PsiElement> getPhpClassReferencePattern(String tag) {
        return XmlPatterns
                .psiElement(XmlTokenType.XML_DATA_CHARACTERS)
                .withParent(XmlPatterns
                        .xmlText()
                        .withParent(

                                XmlPatterns
                                        .xmlTag()
                                        .withName("arg").withChild(
                                        XmlPatterns.xmlAttribute("name").withValue(
                                                XmlPatterns.string().equalTo("class.name")
                                        )
                                )


                        )
                ).inside(XmlPatterns
                        .psiElement(XmlTag.class)
                        .withName(tag).withChild(
                                XmlPatterns.xmlAttribute("type").withValue(
                                        XmlPatterns.string().equalTo("class")
                                )
                        )
                ).inFile(getXmlFilePattern());
    }


    public static ElementPattern<PsiElement> getFunctionPhpClassHandlerPattern() {
        return getPhpClassReferencePattern(functionTag);
    }

    public static ElementPattern<PsiElement> getConditionPhpClassHandlerPattern() {
        return getPhpClassReferencePattern(conditionTag);
    }

    public static ElementPattern<PsiElement> getValidatorPhpClassHandlerPattern() {
        return getPhpClassReferencePattern(validatorTag);
    }


    public static ElementPattern<PsiElement> getRegisterPhpClassHandlerPattern() {
        return getPhpClassReferencePattern(registerTag);
    }
}
