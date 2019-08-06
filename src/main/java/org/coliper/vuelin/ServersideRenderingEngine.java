package org.coliper.vuelin;

public interface ServersideRenderingEngine {
    String render(TemplateName templateName, Object renderingData);

}
