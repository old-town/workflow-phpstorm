package ru.oldtown.idea.workflowplugin.config.workflow.xml.provider;


import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import ru.oldtown.idea.workflowplugin.config.PhpClassReference;

public class ServiceReferenceProvider extends PsiReferenceProvider {

    public static final String TYPE_CLASS = "class";

    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(@NotNull PsiElement psiElement, @NotNull ProcessingContext processingContext) {
        String text = psiElement.getText();
        return new PsiReference[]{ new PhpClassReference(psiElement, text) };
    }

}
