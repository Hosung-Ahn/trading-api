package com.kiapi.service.redis;

import com.kiapi.repository.redis.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KiApiAccessTokenService {
    private final RedisRepository redisRepository;

    public void setAccessToken(Long memberId, String accessToken, Long seconds) {
        redisRepository.setWithTimeout(memberId.toString(), accessToken, seconds);
    }

    public String getAccessToken(Long memberId) {
        return redisRepository.get(memberId.toString());
    }

    public boolean isExist(Long memberId) {
        return redisRepository.get(memberId.toString()) != null;
    }
}
