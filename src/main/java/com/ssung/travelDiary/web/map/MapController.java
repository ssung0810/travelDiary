package com.ssung.travelDiary.web.map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssung.travelDiary.service.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class MapController {

    private final MapService mapService;

    @GetMapping("/map")
    public String mapForm(Model model) {
//        MapDto dto = mapService.searchPlace("중국집");
//
//        model.addAttribute("result", dto);
//        model.addAttribute("items", dto.getItems().get(0));

        return "maps";
    }

    @ResponseBody
    @GetMapping("/search")
    public MapDto search(@RequestParam String param) throws JsonProcessingException {
        return mapService.searchPlace(param);
    }
}
