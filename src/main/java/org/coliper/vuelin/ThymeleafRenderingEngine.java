package org.coliper.vuelin;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.beanutils.BeanMap;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import com.google.common.collect.Maps;

public class ThymeleafRenderingEngine implements ServersideRenderingEngine {

    private static final Function<Map.Entry<Object, Object>, Map.Entry<String, Object>> KEY_TO_STRING_CONVERTER =
            e -> Maps.immutableEntry(e.getKey().toString(), e.getValue());

    private final TemplateEngine templateEngine;

    public ThymeleafRenderingEngine() {

        this.templateEngine = new TemplateEngine();
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".thymeleaf.html");
        this.templateEngine.setTemplateResolver(templateResolver);
    }

    @Override
    public String render(TemplateName templateName, Object renderingData) {
        Context context = new Context();
        Map<String, Object> beanMap = convertObjectToMap(renderingData);
        context.setVariables(beanMap);
        return this.templateEngine.process(templateName.getNameAsString(), context);
    }

    private Map<String, Object> convertObjectToMap(Object renderingData) {
        // conversion is necessary as BeanMap is a Map<Object,Object> and we need Map<String,Object>
        return new BeanMap(renderingData).entrySet().stream().map(KEY_TO_STRING_CONVERTER)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}
