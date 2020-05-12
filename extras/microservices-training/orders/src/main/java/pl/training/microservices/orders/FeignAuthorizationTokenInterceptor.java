package pl.training.microservices.orders;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
public class FeignAuthorizationTokenInterceptor implements RequestInterceptor {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String TOKEN_TYPE = "bearer ";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(AUTHORIZATION_HEADER, TOKEN_TYPE + getToken());
    }

    private String getToken() {
        return ((OAuth2AuthenticationDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getDetails())
                .getTokenValue();
    }

}
