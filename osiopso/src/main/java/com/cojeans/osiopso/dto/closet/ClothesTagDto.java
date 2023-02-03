package com.cojeans.osiopso.dto.closet;

import com.cojeans.osiopso.dto.tag.TagDto;
import com.cojeans.osiopso.entity.closet.ClothesTag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class ClothesTagDto {
    private Long id;

    private ClothesDto clothesDto;

    private TagDto tagDto;

    public ClothesTag toEntity(){
        System.out.println("ClothesTag toEntity");

        return ClothesTag.builder()
                .id(id)
                .clothes(clothesDto.toEntity())
                .tag(tagDto.toEntity())
                .build();
    }
}
