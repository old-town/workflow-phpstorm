package ru.oldtown.idea.workflowplugin.config.workflow.xml;

import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceRegistrar;
import org.jetbrains.annotations.NotNull;
import ru.oldtown.idea.workflowplugin.config.workflow.xml.provider.ServiceReferenceProvider;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class XmlReferenceContributor extends PsiReferenceContributor {

    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {

        registrar.registerReferenceProvider(
                XmlHelper.getFunctionPhpClassHandlerPattern(),
                new ServiceReferenceProvider()
        );

        registrar.registerReferenceProvider(
                XmlHelper.getConditionPhpClassHandlerPattern(),
                new ServiceReferenceProvider()
        );

        registrar.registerReferenceProvider(
                XmlHelper.getValidatorPhpClassHandlerPattern(),
                new ServiceReferenceProvider()
        );


        registrar.registerReferenceProvider(
                XmlHelper.getRegisterPhpClassHandlerPattern(),
                new ServiceReferenceProvider()
        );
    }

}