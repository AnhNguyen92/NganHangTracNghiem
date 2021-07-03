package vn.com.multiplechoice.business.service;

import vn.com.multiplechoice.dao.model.VerificationCode;

public interface VerificationCodeService extends IGenericService<VerificationCode, Long> {
    public VerificationCode findByToken(String token);

    public boolean verifyToken(String code);
}
