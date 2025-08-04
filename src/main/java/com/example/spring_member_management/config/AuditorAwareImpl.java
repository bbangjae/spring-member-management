package com.example.spring_member_management.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * JPA Auditing 기능을 위한 Auditor 제공 클래스.
 * 로그인 기능이 없으므로, 시스템 기본값으로 "jae"을 반환한다.
 */
@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("jae");
    }
}