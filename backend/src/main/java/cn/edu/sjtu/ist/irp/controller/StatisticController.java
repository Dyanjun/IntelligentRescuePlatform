package cn.edu.sjtu.ist.irp.controller;

import cn.edu.sjtu.ist.irp.entity.MissingPerson;
import cn.edu.sjtu.ist.irp.entity.dto.LostCaseDTO;
import cn.edu.sjtu.ist.irp.entity.dto.MissingPersonDTO;
import cn.edu.sjtu.ist.irp.service.LostCaseService;
import cn.edu.sjtu.ist.irp.service.MissingPersonService;
import cn.edu.sjtu.ist.irp.util.response.Result;
import cn.edu.sjtu.ist.irp.util.response.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author dyanjun
 * @date 2021/12/15 20:08
 */
@RestController
@RequestMapping("/data")
public class StatisticController {

    @Autowired
    LostCaseService lostCaseService;

    @Autowired
    MissingPersonService missingPersonService;

    @GetMapping("/age")
    public Result statisticByAge(){
        List<LostCaseDTO> lostCaseDTOList = lostCaseService.getAllLostCase();
        // 统计
        List<LostCaseDTO> missingPersonListFemale = lostCaseDTOList.stream().filter(u -> "female".equals(u.getMissingPerson().getGender())).collect(Collectors.toList());
        List<LostCaseDTO> missingPersonListMale = lostCaseDTOList.stream().filter(u -> "male".equals(u.getMissingPerson().getGender())).collect(Collectors.toList());;

        Map<Integer, Long> ageCountMapFemale = missingPersonListFemale.stream().collect(Collectors.groupingBy(x -> x.getMissingPerson().getAge() , Collectors.counting()));
        Map<Integer, Long> ageCountMapMale = missingPersonListMale.stream().collect(Collectors.groupingBy(x -> x.getMissingPerson().getAge() , Collectors.counting()));

        Map<String, Long> mapFemale = new HashMap<>();
        Map<String, Long> mapMale = new HashMap<>();
        Map<String, Long> mapAll = new HashMap<>();
        initAgeMap(mapFemale);
        initAgeMap(mapMale);
        initAgeMap(mapAll);
        countAgeMap(ageCountMapFemale, mapFemale, mapAll);
        countAgeMap(ageCountMapMale, mapMale, mapAll);
        Map<String,Map> result = new HashMap<>();
        result.put("female", mapFemale);
        result.put("male", mapMale);
        result.put("all", mapAll);
        return ResultUtil.success(result);
    }

    private void countAgeMap(Map<Integer, Long> ageCountMapMale, Map<String, Long> mapMale, Map<String, Long> mapAll) {
        for (Map.Entry<Integer, Long> entry : ageCountMapMale.entrySet()) {
            Integer age = entry.getKey();
            String key = "";
            if(age<=60){
                key = "60岁及以下";
            }
            if(age>60 && age<=65){
                key = "61-65岁";
            }
            if(age>65 && age<=70){
                key = "66-70岁";
            }
            if(age>70 && age<=75){
                key = "71-75岁";
            }
            if(age>75 && age<=80){
                key = "76-80岁";
            }
            if(age>80 && age<=85){
                key = "81-85岁";
            }
            if(age>85){
                key = "86岁及以上";
            }
            mapMale.put(key,mapMale.get(key)+entry.getValue());
            mapAll.put(key,mapAll.get(key)+entry.getValue());
        }
    }

    private void initAgeMap(Map<String, Long> mapFemale) {
        mapFemale.put("60岁及以下",0L);
        mapFemale.put("61-65岁",0L);
        mapFemale.put("66-70岁",0L);
        mapFemale.put("71-75岁",0L);
        mapFemale.put("76-80岁",0L);
        mapFemale.put("81-85岁",0L);
        mapFemale.put("86岁及以上",0L);
    }

    @GetMapping("/place")
    public Result<List<List<Number>>> statisticByPlace(){
        List<LostCaseDTO> lostCaseDTOList = lostCaseService.getAllLostCase();
        // 统计
        Map<String, Long> placeCountMap = lostCaseDTOList.stream().collect(Collectors.groupingBy(x -> x.getPlace().getLongitude() + "#" + x.getPlace().getLatitude() , Collectors.counting()));
        List<List<Number>> result = new ArrayList<>();
        for (Map.Entry<String, Long> entry : placeCountMap.entrySet()) {
            String[] p = entry.getKey().split("#");
            List<Number> data = new ArrayList<>();
            data.add(Double.parseDouble(p[0]));
            data.add(Double.parseDouble(p[1]));
            data.add(entry.getValue());
            result.add(data);
        }
        return ResultUtil.success(result);
    }
}
