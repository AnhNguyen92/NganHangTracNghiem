package vn.com.multiplechoice.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.com.multiplechoice.business.service.AbstractService;
import vn.com.multiplechoice.business.service.HeaderTemplateService;
import vn.com.multiplechoice.dao.model.HeaderTemplate;
import vn.com.multiplechoice.dao.repository.HeaderTemplateRepository;

@Service
@Transactional
public class HeaderTemplateServiceImpl extends AbstractService<HeaderTemplate, Long> implements HeaderTemplateService {
    private HeaderTemplateRepository headerTemplateRepository;

    @Autowired
    public HeaderTemplateServiceImpl(HeaderTemplateRepository headerTemplateRepository) {
        super(headerTemplateRepository);
        this.headerTemplateRepository = headerTemplateRepository;
    }
}
