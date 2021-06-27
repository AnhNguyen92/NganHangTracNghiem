package vn.com.multiplechoice.business.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.com.multiplechoice.business.service.AbstractService;
import vn.com.multiplechoice.business.service.VerificationCodeService;
import vn.com.multiplechoice.dao.model.VerificationCode;
import vn.com.multiplechoice.dao.repository.VerificationCodeRepository;

@Service
@Transactional
public class VerificationCodeServiceImpl extends AbstractService<VerificationCode, Long> implements VerificationCodeService {
    private VerificationCodeRepository repository;

    @Autowired
    public VerificationCodeServiceImpl(VerificationCodeRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public VerificationCode findByToken(String token) {
        return repository.findByToken(token);
    }
}
