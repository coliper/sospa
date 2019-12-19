package org.coliper.sospa;

public interface ServersideRenderingEngine {
    String render(TemplateName templateName, Object renderingData);

}
