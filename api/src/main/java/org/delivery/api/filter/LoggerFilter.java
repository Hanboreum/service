package org.delivery.api.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Component
@Slf4j
public class LoggerFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        //형변환, request, response 를 HttpServlet 으로 형변환 시켜서 넘긴다.
        var req = new ContentCachingRequestWrapper((HttpServletRequest) request);
        var res = new ContentCachingResponseWrapper((HttpServletResponse) response);
        log.info("INIT URI: {}", req.getRequestURI());
        //컨트롤러, 인터셉터 등 필터 뒤에 있는 것들은 랩핑된 객체를 받는다
        chain.doFilter(req, res);

        //request 정보
        var headerNames = req.getHeaderNames();
        var headerValues = new StringBuilder(); //key value

        headerNames.asIterator().forEachRemaining(headerKey->{
            var headerValue= req.getHeader(headerKey);

            headerValues
                    .append("[")
                    .append(headerKey)
                    .append(":")
                    .append(headerValue)
                    .append("]  ");
        });


        var requestBody = new String(req.getContentAsByteArray());
        var uri = req.getRequestURI();
        var method = req.getMethod();

        //들어왔을 때 로그
        log.info(">>uri : {}, method: {}, header: {}, body : {}", uri, method, headerValues, requestBody);


        //response 정보
        var responseHeaderValues = new StringBuilder();
        res.getHeaderNames().forEach(headerKey->{
            var headerValue = res.getHeader(headerKey);

            responseHeaderValues
                    .append("[")
                    .append(headerKey)
                    .append(":")
                    .append(headerValue)
                    .append("]  ");
        });


        var responseBody  = new String(res.getContentAsByteArray());

        //나갈때 로그
        log.info("<<<uri: {}, method: {}, header: {}, body : {}", uri, method, responseHeaderValues, responseBody);

        //이거 없으면 res body가 없이 간다
        res.copyBodyToResponse();
    }

}
