package en.tedu.sp11.fallback;

import cn.tedu.web.util.JsonResult;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Haitao
 * @date 2020/11/27 11:39
 */
public class ItemFB implements FallbackProvider {
    /**
     * 返回 service-id
     * 针对哪个服务进行降级
     *
     * "*"或 null是针对说有服务进行降级
     * @return
     */
    @Override
    public String getRoute() {
        return "item-service";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }
            @Override
            public int getRawStatusCode() throws IOException {
                return HttpStatus.OK.value();
            }
            @Override
            public String getStatusText() throws IOException {
                return HttpStatus.OK.getReasonPhrase();
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() throws IOException {
                String json = JsonResult.ok().msg("调用后台商品服务失败").toString();
                return new ByteArrayInputStream(json.getBytes("UTF-8"));
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders header = new HttpHeaders();
                header.add("Content-Type","application/json;charsset=UTF-8");
                return header;
            }
        };
    }


}
