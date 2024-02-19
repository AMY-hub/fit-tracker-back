package com.fitTracker.fit.service.template.impl;

import com.fitTracker.fit.service.template.TemplateService;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.StringWriter;

@Service
@NoArgsConstructor
public class TemplateServiceImpl implements TemplateService {
    private final MustacheFactory mustacheFactory = new DefaultMustacheFactory();;
    @Override
    public String compileTemplateFromPath(String path, Object data) {
        Mustache template = this.mustacheFactory.compile(path);
        return execute(template, data);
    }

    private String execute(Mustache template, Object data) {
        StringWriter writer = new StringWriter();
        template.execute(writer, data);
        return writer.toString();
    }
}
