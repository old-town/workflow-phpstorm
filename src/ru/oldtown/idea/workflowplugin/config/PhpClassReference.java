package ru.oldtown.idea.workflowplugin.config;

import com.intellij.codeInsight.completion.impl.CamelHumpMatcher;
import com.intellij.codeInsight.completion.impl.BetterPrefixMatcher;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementResolveResult;
import com.intellij.psi.PsiPolyVariantReferenceBase;
import com.intellij.psi.ResolveResult;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.completion.PhpClassLookupElement;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import ru.oldtown.idea.workflowplugin.util.PsiElementUtils;
import ru.oldtown.idea.workflowplugin.util.completion.PhpClassReferenceInsertHandler ;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;



public class PhpClassReference extends PsiPolyVariantReferenceBase<PsiElement> {

    private String classFQN;

    private boolean useClasses = true;
    private boolean useInterfaces = false;

    public PhpClassReference(@NotNull StringLiteralExpression element) {
        super(element);
        this.classFQN = PsiElementUtils.getMethodParameter(element);
    }

    public PhpClassReference(@NotNull PsiElement element, String classFQN) {
        super(element);
        this.classFQN = PsiElementUtils.removeIdeaRuleHack(PsiElementUtils.trimQuote(classFQN));
    }


    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean incompleteCode) {

        List<ResolveResult> results = new ArrayList<ResolveResult>();
        PhpIndex phpIndex = PhpIndex.getInstance(getElement().getProject());

        if(this.useClasses) {
            this.attachPhpClassResolveResults(phpIndex.getClassesByFQN(classFQN), results);
        }

        if(this.useInterfaces) {
            this.attachPhpClassResolveResults(phpIndex.getInterfacesByFQN(classFQN.startsWith("\\") ? classFQN : "\\" + classFQN), results);
        }

        return results.toArray(new ResolveResult[results.size()]);
    }

    private void attachPhpClassResolveResults(Collection<PhpClass> phpClasses, List<ResolveResult> results) {
        for (PhpClass phpClass : phpClasses) {
            results.add(new PsiElementResolveResult(phpClass));
        }
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        return new Object[0];
    }


}