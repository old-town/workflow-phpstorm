package ru.oldtown.idea.workflowplugin.config.workflow.xml.inspection;

import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.ide.highlighter.XmlFileType;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiFile;
import com.intellij.psi.xml.XmlTag;
import org.jetbrains.annotations.NotNull;
import ru.oldtown.idea.workflowplugin.config.workflow.xml.XmlHelper;

public class InvalidFunctionClassNameInspection extends LocalInspectionTool {

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(final @NotNull ProblemsHolder holder, boolean isOnTheFly) {

        PsiFile psiFile = holder.getFile();
        if(psiFile.getFileType() != XmlFileType.INSTANCE ) {
            return super.buildVisitor(holder, isOnTheFly);
        }

        for (XmlTag xmlTag : XmlHelper.getXmlFunctionDefinition(psiFile)) {
            //visitService(xmlTag, holder);
        }


        return super.buildVisitor(holder, isOnTheFly);
    }

}
