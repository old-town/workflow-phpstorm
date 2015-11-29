package ru.oldtown.idea.workflowplugin.config.workflow.xml;

import com.intellij.codeInsight.completion.*;
import ru.oldtown.idea.workflowplugin.util.completion.PhpClassCompletionProvider;
import ru.oldtown.idea.workflowplugin.util.completion.PhpNamespaceCompletionProvider;


/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class XmlCompletionContributor extends CompletionContributor {

    public XmlCompletionContributor() {
        extend(CompletionType.BASIC, XmlHelper.getArgumentFunctionClassNamePattern(), new PhpClassCompletionProvider());
        extend(CompletionType.BASIC, XmlHelper.getArgumentFunctionClassNamePattern(), new PhpNamespaceCompletionProvider());
    }

}

