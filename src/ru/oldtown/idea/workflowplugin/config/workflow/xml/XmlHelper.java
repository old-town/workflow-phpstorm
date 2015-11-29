package ru.oldtown.idea.workflowplugin.config.workflow.xml;

import com.intellij.patterns.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlTag;
import com.intellij.psi.xml.XmlTokenType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

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


    @NotNull
    public static Collection<XmlTag> getXmlFunctionDefinition(PsiFile psiFile) {

        Collection<XmlTag> xmlTags = new ArrayList<XmlTag>();
//
//
//        psiFile.findElementAt()
//
//
//        for(XmlTag xmlTag: PsiTreeUtil.getChildrenOfTypeAsList(psiFile.getFirstChild(), XmlTag.class)) {
//            if(xmlTag.getName().equals("container")) {
//                for(XmlTag servicesTag: xmlTag.getSubTags()) {
//                    if(servicesTag.getName().equals("services")) {
//                        for(XmlTag parameterTag: servicesTag.getSubTags()) {
//                            if(parameterTag.getName().equals("service")) {
//                                xmlTags.add(parameterTag);
//                            }
//                        }
//                    }
//                }
//            }
//        }

        return xmlTags;
    }
}
