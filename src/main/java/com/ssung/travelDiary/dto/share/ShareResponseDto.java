package com.ssung.travelDiary.dto.share;

import com.ssung.travelDiary.domain.share.Share;
import com.ssung.travelDiary.domain.share.ShareBoard;
import com.ssung.travelDiary.domain.share.ShareMember;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class ShareResponseDto {

    private Long id;
    private String title;
    private String creator;
    private List<ShareBoard> shareBoard;
    private List<ShareMember> shareMember;

    public ShareResponseDto(Share entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.creator = entity.getCreator();
        this.shareBoard = entity.getShareBoard();
        this.shareMember = entity.getShareMember();
    }
}
