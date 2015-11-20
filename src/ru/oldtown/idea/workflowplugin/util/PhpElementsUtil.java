package ru.oldtown.idea.workflowplugin.util;



import com.intellij.psi.PsiElement;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import ru.oldtown.idea.workflowplugin.util.completion.StringUtils;
import java.util.*;

public class PhpElementsUtil {



    /**
     * Try to visit possible class name for PsiElements with text like "Foo\|Bar", "Foo|\Bar", "\Foo|\Bar"
     * Cursor must have position in PsiElement
     *
     * @param psiElement the element context, cursor should be in it
     * @param cursorOffset current cursor editor eg from completion context
     * @param visitor callback on matching class
     */
    public static void visitNamespaceClassForCompletion(PsiElement psiElement, int cursorOffset, ClassForCompletionVisitor visitor) {

        int cursorOffsetClean = cursorOffset - psiElement.getTextOffset();
        if(cursorOffsetClean < 1) {
            return;
        }

        String content = psiElement.getText();
        int length = content.length();
        if(!(length >= cursorOffsetClean)) {
            return;
        }

        String beforeCursor = content.substring(0, cursorOffsetClean);
        boolean isValid;

        // espend\|Container, espend\Cont|ainer <- fallback to last full namespace
        // espend|\Container <- only on known namespace "espend"
        String namespace = beforeCursor;

        // if no backslash or its equal in first position, fallback on namespace completion
        int lastSlash = beforeCursor.lastIndexOf("\\");
        if(lastSlash <= 0) {
            isValid = PhpIndexUtil.hasNamespace(psiElement.getProject(), beforeCursor);
        } else {
            isValid = true;
            namespace = beforeCursor.substring(0, lastSlash);
        }

        if(!isValid) {
            return;
        }

        // format namespaces and add prefix for fluent completion
        String prefix = "";
        if(namespace.startsWith("\\")) {
            prefix = "\\";
        } else {
            namespace = "\\" + namespace;
        }

        // search classes in current namespace and child namespaces
        for(PhpClass phpClass: PhpIndexUtil.getPhpClassInsideNamespace(psiElement.getProject(), namespace)) {
            String presentableFQN = phpClass.getPresentableFQN();
            if(presentableFQN != null && StringUtils.startWithEqualClassname(presentableFQN, beforeCursor)) {
                visitor.visit(phpClass, presentableFQN, prefix);
            }

        }

    }


    public static interface ClassForCompletionVisitor {
        public void visit(PhpClass phpClass, String presentableFQN, String prefix);
    }
}
