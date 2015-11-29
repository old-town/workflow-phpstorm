package ru.oldtown.idea.workflowplugin.config.workflow.xml;

import com.intellij.patterns.*;
import com.intellij.psi.PsiFile;
import com.intellij.psi.xml.XmlTag;
import com.intellij.psi.xml.XmlTokenType;


/**
 *
 */
public class XmlHelper {


    public static PsiFilePattern.Capture<PsiFile> getXmlFilePattern() {
        return XmlPatterns.psiFile()
                .withName(XmlPatterns
                        .string().endsWith(".xml")
                );
    }

    public static PsiElementPattern getArgumentFunctionClassNamePattern() {
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
                        .withName("function").withChild(
                                XmlPatterns.xmlAttribute("type").withValue(
                                        XmlPatterns.string().equalTo("class")
                                )
                        )
                ).inFile(getXmlFilePattern());
    }


}
