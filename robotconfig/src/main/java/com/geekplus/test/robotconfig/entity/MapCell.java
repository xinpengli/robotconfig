package com.geekplus.test.robotconfig.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Getter
@Setter

public class MapCell {
//   Map<String,Object> mapCelllist;

        Map<String,String> index;
        Map<String,String> location;
        String cellType;
        String cellStatus;
        Object direction;
        Object dirUnload;
        Object allocatedRobotId;
        Object occupyRobotId;
        Object occupiedShelfCode;















}
