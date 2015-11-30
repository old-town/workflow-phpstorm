package ru.oldtown.idea.workflowplugin.util.completion;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.PhpIcons;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.PhpLangUtil;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.PriorityQueue;


public class PhpNamespaceCompletionProvider extends CompletionProvider<CompletionParameters> {

    public void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext context, final @NotNull CompletionResultSet resultSet) {

        PsiElement psiElement = parameters.getOriginalPosition();

        addNamespaceCompletion(parameters, resultSet, psiElement);

    }

    public static void addNamespaceCompletion(CompletionParameters parameters, final CompletionResultSet resultSet, PsiElement psiElement) {
        PsiElement position = parameters.getPosition().getOriginalElement();
        PhpIndex index = PhpIndex.getInstance(position.getProject());

        int cursorOffset = parameters.getOffset();
        int cursorOffsetClean = cursorOffset - psiElement.getTextOffset();

        String content = psiElement.getText();
        if(cursorOffsetClean < 1) {
            content = "\\";
        }

        Integer slashLastIndex = content.lastIndexOf("\\");

        if (slashLastIndex.equals(-1)) {
            return;
        }

        String namespace = "";
        if (slashLastIndex.equals(0)) {
            namespace = "";
        }

        if (slashLastIndex > 0) {
            namespace = content.substring(0, content.lastIndexOf("\\"));
        }

        addSubNamespaces(namespace, resultSet, index);

    }

    public static void addSubNamespaces(@NotNull String namespaceName, @NotNull CompletionResultSet result, @NotNull PhpIndex phpIndex) {
        Collection<String> phpNamespaces = getAllChildNamespaceNames(phpIndex, PhpLangUtil.toFQN(namespaceName));

        for (String namespace : phpNamespaces) {
            String  matchNamespace = namespace;
            if (!matchNamespace.startsWith("\\")) {
                matchNamespace = "\\" + matchNamespace;
            }


            if(!"___PHPSTORM_HELPERS".equals(namespace) && result.getPrefixMatcher().prefixMatches(matchNamespace)) {
                String shortName = PhpLangUtil.toShortName(namespace);
                LookupElementBuilder element = LookupElementBuilder.create(matchNamespace).withPresentableText(shortName).withIcon(PhpIcons.NAMESPACE);
                if(!PhpLangUtil.equalsClassNames(shortName, namespace)) {
                    element = element.withTailText("(" + namespace + ")", true);
                }

                result.addElement(element);
            }
        }


    }

    @NotNull
    private static Collection<String> getAllChildNamespaceNames(@NotNull PhpIndex phpIndex, @NotNull String parent) {
        ArrayList<String> values = new ArrayList<>();
        PriorityQueue<String>  toProcess= new PriorityQueue<>();
        toProcess.add(parent);

        while(!toProcess.isEmpty()) {
            String next = toProcess.poll();
            Collection<String> children = phpIndex.getChildNamespacesByParentName(next);
            for (String child : children) {
                String childQualifiedName = next + child;
                toProcess.add(childQualifiedName + "\\");
                values.add(PhpLangUtil.toPresentableFQN(childQualifiedName));
            }
        }

        return values;
    }
}
