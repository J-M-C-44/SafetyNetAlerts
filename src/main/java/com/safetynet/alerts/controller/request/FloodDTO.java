package com.safetynet.alerts.controller.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO : à virer - car ajoute un champ mapFoodDTO au JSO dassn la réponse
public class FloodDTO {
    private Map<String, List<PersonFloodDTO>> mapFloodDTO;

//    public FloodDTO(Map<String, List<PersonFloodDTO>> mapFloodDTO) {
//        this.mapFloodDTO = mapFloodDTO;
//    }

    public FloodDTO() {
        this.mapFloodDTO = new HashMap<>();
    }

    public void putMapFloodDTO(String key, List<PersonFloodDTO> value) {
        this.mapFloodDTO.put(key, value);
    }

    public Map<String, List<PersonFloodDTO>> getMapFloodDTO() {
        return mapFloodDTO;
    }

    @Override
    public String toString() {
        return "FloodDTO{" +
                "mapFloodDTO=" + mapFloodDTO +
                '}';
    }
}
