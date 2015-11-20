package ru.oldtown.idea.workflowplugin.util.completion;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.completion.PhpCompletionUtil;
import com.jetbrains.php.completion.PhpLookupElement;
import com.jetbrains.php.completion.insert.PhpNamespaceInsertHandler;
import com.jetbrains.php.completion.insert.PhpReferenceInsertHandler;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.jetbrains.php.lang.psi.elements.PhpNamedElement;
import org.jetbrains.annotations.NotNull;
import ru.oldtown.idea.workflowplugin.util.PhpElementsUtil;

public class PhpNamespaceCompletionProvider extends CompletionProvider<CompletionParameters> {

    private final boolean withInterface;
    private boolean withLeadingBackslash = false;

    public PhpNamespaceCompletionProvider() {
        this(false);
    }

    public PhpNamespaceCompletionProvider(boolean withInterface) {
        this.withInterface = withInterface;
    }

    public PhpNamespaceCompletionProvider withTrimLeadBackslash(boolean withLeadingBackslash) {
        this.withLeadingBackslash = withLeadingBackslash;
        return this;
    }

    public void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext context, final @NotNull CompletionResultSet resultSet) {

        PsiElement psiElement = parameters.getOriginalPosition();

        addNamespaceCompletion(parameters, resultSet, psiElement, withInterface, withLeadingBackslash);

    }
    public static void addNamespaceCompletion(CompletionParameters parameters, final CompletionResultSet resultSet, PsiElement psiElement, boolean withInterface) {
        addNamespaceCompletion(parameters, resultSet, psiElement, withInterface, false);
    }

    public static void addNamespaceCompletion(CompletionParameters parameters, final CompletionResultSet resultSet, PsiElement psiElement, boolean withInterface, boolean withLeadBackslash) {
        PsiElement position = parameters.getPosition().getOriginalElement();
        PhpIndex index = PhpIndex.getInstance(position.getProject());
//
        int cursorOffset = parameters.getOffset();
        int cursorOffsetClean = cursorOffset - psiElement.getTextOffset();

        String content = psiElement.getText();
        if(cursorOffsetClean < 1) {
            content = "";
        }


        PhpCompletionUtil.addSubNamespaces(content, resultSet, index, PhpNamespaceInsertHandler.getInstance());



    }


}
