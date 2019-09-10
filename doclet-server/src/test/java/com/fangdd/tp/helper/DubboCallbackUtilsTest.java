package com.fangdd.tp.helper;

import com.alibaba.fastjson.JSONObject;
import com.fangdd.tp.dto.request.DubboGenericInvokeDto;
import org.junit.Test;

/**
 * @author xuwenzhen
 * @date 19/1/21
 */
public class DubboCallbackUtilsTest {
    @Test
    public void invoke() throws Exception {
        String address = "zookeeper://10.12.21.117:2181";
        String interfaceName = "com.fangdd.tp.dict.service.ScanService";
        String methodName = "matchForbiddenWords";
        String[] methodParamType = {String.class.getName()};
        Object[] methodParams = {"习大大最新在广东"};
        String version = "1.0.0";
        for (int i = 0; i < 10; i++) {
            DubboGenericInvokeDto invokeData = new DubboGenericInvokeDto();
            invokeData.setDubboRegistUri(address);
            invokeData.setInterfaceName(interfaceName);
            invokeData.setMethodName(methodName);
            invokeData.setVersion(version);
            invokeData.setMethodParamTypes(methodParamType);
            invokeData.setMethodParams(methodParams);

            Object obj = DubboGenericInvoker.invoke(invokeData);
            System.out.println(JSONObject.toJSONString(obj));
        }
    }
}