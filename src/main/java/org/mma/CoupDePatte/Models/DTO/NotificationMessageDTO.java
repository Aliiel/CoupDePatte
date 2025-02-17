package org.mma.CoupDePatte.Models.DTO;

public class NotificationMessageDTO {

    private String title;
    private String content;
    private Long advertId;

    public NotificationMessageDTO(String title, String content, Long advertId) {
        this.title = title;
        this.content = content;
        this.advertId = advertId;
    }

    public String getTitle() { return title; }
    public String getContent() { return content; }
    public Long getAdvertId() { return advertId; }
}
