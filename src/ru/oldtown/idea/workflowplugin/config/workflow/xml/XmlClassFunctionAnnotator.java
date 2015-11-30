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
        annotateFunctionServiceInstance(psiElement, holder);
        annotateConditionServiceInstance(psiElement, holder);
        annotateValidatorServiceInstance(psiElement, holder);
        annotateRegisterServiceInstance(psiElement, holder);

    }

    private void annotateFunctionServiceInstance(@NotNull PsiElement psiElement, @NotNull AnnotationHolder holder) {

        if(!XmlHelper.getFunctionPhpClassHandlerPattern().accepts(psiElement)) {
            return;
        }

        String content = psiElement.getText();
        Project project = psiElement.getProject();

        Collection<PhpClass> collectionPhpClass =  PhpIndex.getInstance(project).getClassesByFQN(content);

        if (0 == collectionPhpClass.size()) {
            String msg = "Function class " + content + " not found";
            holder.createErrorAnnotation(psiElement, msg);
        }
    }


    private void annotateConditionServiceInstance(@NotNull PsiElement psiElement, @NotNull AnnotationHolder holder) {

        if(!XmlHelper.getConditionPhpClassHandlerPattern().accepts(psiElement)) {
            return;
        }

        String content = psiElement.getText();
        Project project = psiElement.getProject();

        Collection<PhpClass> collectionPhpClass =  PhpIndex.getInstance(project).getClassesByFQN(content);

        if (0 == collectionPhpClass.size()) {
            String msg = "Condition class " + content + " not found";
            holder.createErrorAnnotation(psiElement, msg);
        }
    }

    private void annotateValidatorServiceInstance(@NotNull PsiElement psiElement, @NotNull AnnotationHolder holder) {

        if(!XmlHelper.getValidatorPhpClassHandlerPattern().accepts(psiElement)) {
            return;
        }

        String content = psiElement.getText();
        Project project = psiElement.getProject();

        Collection<PhpClass> collectionPhpClass =  PhpIndex.getInstance(project).getClassesByFQN(content);

        if (0 == collectionPhpClass.size()) {
            String msg = "Validator class " + content + " not found";
            holder.createErrorAnnotation(psiElement, msg);
        }
    }


    private void annotateRegisterServiceInstance(@NotNull PsiElement psiElement, @NotNull AnnotationHolder holder) {

        if(!XmlHelper.getRegisterPhpClassHandlerPattern().accepts(psiElement)) {
            return;
        }

        String content = psiElement.getText();
        Project project = psiElement.getProject();

        Collection<PhpClass> collectionPhpClass =  PhpIndex.getInstance(project).getClassesByFQN(content);

        if (0 == collectionPhpClass.size()) {
            String msg = "Register class " + content + " not found";
            holder.createErrorAnnotation(psiElement, msg);
        }
    }
}
