package com.geekplus.test.robotconfig.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.geekplus.test.robotconfig.common.Dom4jUtil;
import com.geekplus.test.robotconfig.common.HttpUtil;
import com.geekplus.test.robotconfig.entity.MapCell;
import com.geekplus.test.robotconfig.entity.TestJob;

import com.geekplus.test.robotconfig.service.GetMapCellService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/robot")
public class GetRobotConfig {
    private static final Logger logger = LoggerFactory.getLogger(GetRobotConfig.class);
    @Autowired
    private GetMapCellService getMapCellService;

    @RequestMapping("/config")
    @ResponseBody
    public String config(@RequestBody TestJob testJob) throws IOException {
        int robotNum = testJob.getRobotNum();
        String server = testJob.getServer();
        String mode = testJob.getMode();

        JSONObject request = new JSONObject();
        JSONObject requestJson = new JSONObject();
        JSONObject response = new JSONObject();
        JSONObject body = new JSONObject();
        body.put("instruction", "MAP");
        requestJson.put("body", body);
        requestJson.put("header", HttpUtil.getRequestHeder());
        request.put("request", requestJson);
        request.put("id", "clientid");
        request.put("msgType", "com.geekplus.athena.api.msg.req.QueryInstructionRequestMsg");
        response = HttpUtil.postJsonData("http://" + server + ":8895/", request);



        /*获取整个whmapcells集合嵌套jsonarry*/
        JSONArray whMapCellsList = response.getJSONObject("response").getJSONObject("body").getJSONObject("map").getJSONArray("whMapCells");
        /*遍历whmapcells,放入每个新的jsonarry*/
//    whMapCellsList.getJSONArray(0).toJavaList(MapCell.class);
        List<Map<String, String>> shelfList = new ArrayList<>();
        for (int i = 0; i < whMapCellsList.size(); i++) {

            List<MapCell> list = JSONObject.parseArray(whMapCellsList.getJSONArray(i).toJSONString(), MapCell.class);
            for (int j = 0; j < list.size(); j++) {
                // if (mode.equals("XML"))
                if (list.get(j).getCellType().equals("SHELF_CELL")) {
                    if (mode.equals("xml")) {
                        System.out.println(mode);
                        shelfList.add(list.get(j).getIndex());

                    } else if (mode.equals("database")) {
                        shelfList.add(list.get(j).getLocation());
                    }
                }


            }

        }

        if (robotNum <= shelfList.size()) {
            String str = Dom4jUtil.addRobot(shelfList, robotNum);

            return str;
        } else {
            return "输入数量过大！";
        }


        // JSONArray array = new JSONArray();


    }


}
