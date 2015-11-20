package ru.oldtown.idea.workflowplugin.config.workflow.xml;

import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceRegistrar;
import ru.oldtown.idea.workflowplugin.config.workflow.xml.provider.ServiceReferenceProvider;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class XmlReferenceContributor extends PsiReferenceContributor {

    @Override
    public void registerReferenceProviders(PsiReferenceRegistrar registrar) {

        registrar.registerReferenceProvider(
                XmlHelper.getArgumentFunctionClassNamePattern(),
                new ServiceReferenceProvider()
        );

    }

}