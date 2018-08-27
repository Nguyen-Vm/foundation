package org.linker.foundation.utils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import javafx.util.Pair;
import okhttp3.RequestBody;
import org.junit.Test;
import org.linker.foundation.JavaBean.YtxResponse;
import org.linker.foundation.dto.DateFormat;
import org.linker.foundation.dto.exception.AppException;
import org.linker.foundation.dto.exception.IMessageCode;
import org.springframework.http.MediaType;

import java.util.List;

/**
 * @author RWM
 * @date 2018/8/27
 */
public class HttpRestUtilsTest {

    @Test
    public void ytxCall() {
        String ytxAppId = "dfa4c32a8ebb444790c2392eee082827";
        String accountSID = "2b63d232e996453381a5fcaea559c848";
        String authToken = "73e5ab6a7a304d7f87d4a4541b82bd00";

        String date = DateUtils.now(DateFormat.NumDateTime);
        String sign = CryptUtils.md5(accountSID + authToken + date);
        String authorization = CryptUtils.encodeBase64(accountSID + "|" + date);

        JSONObject json = new JSONObject();
        json.put("action", "callDailBack");
        json.put("appid", ytxAppId);
        json.put("src", "18621629072");
        json.put("dst", "18817391936");
        json.put("srcclid", "01053189990");
        json.put("dstclid", "01053189990");

        String url = String.format("http://api.ytx.net/201512/sid/%s/call/DailbackCall.wx?Sign=%s", accountSID, sign);
        List<Pair<String, String>> headers = headers(authorization);
        RequestBody body = HttpRestUtils.buildBody(MediaType.APPLICATION_JSON_UTF8, json);

        YtxResponse response = HttpRestUtils.post(url, headers, body, YtxResponse.class);
        if (!"0".equals(response.statusCode)) {
            throw new AppException(new IMessageCode() {
                @Override
                public String code() { return response.statusCode; }
                @Override
                public String msg() { return response.statusMsg; }
            });
        }
        System.out.println(response);

    }

    private static List<Pair<String, String>> headers(String authorization) {
        List<Pair<String, String>> headers = Lists.newArrayList(
                new Pair<>("Accept", "application/json"),
                new Pair<>("Content-Type", "application/json;charset=utf-8"),
                new Pair<>("Authorization", authorization)
        );
        return headers;
    }
}
