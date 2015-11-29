package ru.oldtown.idea.workflowplugin.config.workflow.xml;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import org.jetbrains.annotations.NotNull;
import com.intellij.openapi.project.Project;

import java.util.Collection;

public class XmlClassFunctionAnnotator implements Annotator {

    @Override
    public void annotate(@NotNull PsiElement psiElement, @NotNull AnnotationHolder holder) {
        annotateServiceInstance(psiElement, holder);

    }

    private void annotateServiceInstance(@NotNull PsiElement psiElement, @NotNull AnnotationHolder holder) {

        if(!XmlHelper.getArgumentFunctionClassNamePattern().accepts(psiElement)) {
            return;
        }

        String content = psiElement.getText();
        Project project = psiElement.getProject();

        Collection<PhpClass> collectionPhpClass =  PhpIndex.getInstance(project).getClassesByFQN(content);

        if (0 == collectionPhpClass.size()) {
            String msg = "Class " + content + " not found";
            holder.createErrorAnnotation(psiElement, "class not found");
        }
    }


}
