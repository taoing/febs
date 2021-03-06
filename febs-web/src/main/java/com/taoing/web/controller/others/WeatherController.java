package com.taoing.web.controller.others;

import com.taoing.common.annotation.Log;
import com.taoing.common.domain.FebsConstant;
import com.taoing.common.domain.ResponseBo;
import com.taoing.common.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WeatherController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Log("获取天气信息")
    @RequestMapping("weather")
    @PreAuthorize("hasAuthority('weather:list')")
    public String weather() {
        return "others/weather/weather";
    }

    @RequestMapping("weather/query")
    @ResponseBody
    public ResponseBo queryWeather(String areaId) {
        try {
            String data = HttpUtils.sendPost(FebsConstant.MEIZU_WEATHER_URL,
                    "cityIds=" + areaId);
            return ResponseBo.ok(data);
        } catch (Exception e) {
            log.error("查询天气失败", e);
            return ResponseBo.error("查询天气失败, 请联系网站管理员!");
        }
    }
}
